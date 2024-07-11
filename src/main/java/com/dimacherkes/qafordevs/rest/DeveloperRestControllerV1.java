package com.dimacherkes.qafordevs.rest;

import com.dimacherkes.qafordevs.dto.DeveloperDto;
import com.dimacherkes.qafordevs.dto.ErrorDto;
import com.dimacherkes.qafordevs.entity.DeveloperEntity;
import com.dimacherkes.qafordevs.exception.DeveloperNotFoundException;
import com.dimacherkes.qafordevs.service.DeveloperService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/developers")
@RequiredArgsConstructor
public class DeveloperRestControllerV1 {

    private final DeveloperService developerService;

    @PostMapping
    public ResponseEntity<?> createDeveloper(@RequestBody DeveloperDto dto) {
        try {
            DeveloperEntity entity = dto.toEntity();
            DeveloperEntity createdDeveloper = developerService.saveDeveloper(entity);
            DeveloperDto result = DeveloperDto.fromEntity(createdDeveloper);
            return ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ErrorDto.builder()
                            .status(400)
                            .message(e.getMessage())
                            .build());
        }
    }

    @PutMapping
    public ResponseEntity<?> updateDeveloper(@RequestBody DeveloperDto dto) {
        try {
            DeveloperEntity entity = dto.toEntity();
            DeveloperEntity updatedEntity = developerService.updateDeveloper(entity);
            DeveloperDto result = DeveloperDto.fromEntity(updatedEntity);
            return ok(result);
        } catch (DeveloperNotFoundException e) {
            return ResponseEntity.badRequest()
                    .body(ErrorDto.builder()
                            .status(400)
                            .message(e.getMessage())
                            .build());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDeveloperById(@PathVariable("id") Integer id) {
        try {
            DeveloperEntity entity = developerService.getDeveloperById(id);
            DeveloperDto result = DeveloperDto.fromEntity(entity);
            return ok(result);
        } catch (DeveloperNotFoundException e) {
            return ResponseEntity
                    .status(404)
                    .body(ErrorDto.builder()
                            .status(404)
                            .message(e.getMessage())
                            .build());
        }
    }

    @GetMapping()
    public ResponseEntity<?> getAllDevelopers() {
        List<DeveloperEntity> allDevelopers = developerService.getAllDevelopers();
        List<DeveloperDto> result = allDevelopers.stream()
                .map(DeveloperDto::fromEntity)
                .toList();
        return ok(result);
    }

    @GetMapping("/specialty/{specialty}")
    public ResponseEntity<?> getAllDevelopersBySpecialty(@PathVariable("specialty") String specialty) {
        List<DeveloperEntity> entities = developerService.getAllActiveBySpecialty(specialty);
        List<DeveloperDto> result = entities.stream()
                .map(DeveloperDto::fromEntity)
                .toList();
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDeveloperById(@PathVariable("id") int id, @RequestParam(value = "isHard", defaultValue = "false") boolean isHard) {
        try {
            if (isHard) {
                developerService.hardDelete(id);
            } else {
                developerService.softDelete(id);
            }
            return ResponseEntity.ok().build();
        } catch (DeveloperNotFoundException e) {
            return ResponseEntity.badRequest()
                    .body(ErrorDto.builder()
                            .status(400)
                            .message(e.getMessage())
                            .build());
        }
    }


}
