package com.dimacherkes.qafordevs.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.dimacherkes.qafordevs.dto.DeveloperDto;
import com.dimacherkes.qafordevs.entity.DeveloperEntity;
import com.dimacherkes.qafordevs.exception.DeveloperNotFoundException;
import com.dimacherkes.qafordevs.exception.DeveloperWithDuplicateEmailException;
import com.dimacherkes.qafordevs.service.DeveloperService;
import com.dimacherkes.qafordevs.util.DataUtils;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest
public class DeveloperRestControllerV1Test {

    // для генерации запросов
    @Autowired
    private MockMvc mockMvc;

    // для работы с DTO
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DeveloperService developerService;

    @Test
    @DisplayName("Test create developer functionality")
    public void givenDeveloperDto_whenCreateDeveloper_thenSuccessResponse() throws Exception {
        //given
        DeveloperDto dto = DataUtils.getJohnDoeDtoTransient();
        DeveloperEntity entity = DataUtils.getJohnDoePersisted();
        BDDMockito.given(developerService.saveDeveloper(any(DeveloperEntity.class)))
                .willReturn(entity);
        //when
        ResultActions result = mockMvc.perform(post("/api/v1/developers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));
        //then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is("John")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is("Doe")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is("ACTIVE")));
    }

    @Test
    @DisplayName("Test create developer with duplicate email functionality")
    public void givenDeveloperDtoWithDuplicateEmail_whenCreateDeveloper_thenErrorResponse() throws Exception {
        //given
        DeveloperDto dto = DataUtils.getJohnDoeDtoTransient();
        BDDMockito.given(developerService.saveDeveloper(any(DeveloperEntity.class)))
                .willThrow(new DeveloperWithDuplicateEmailException("Developer with defined email is already exists"));
        //when
        ResultActions result = mockMvc.perform(post("/api/v1/developers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));
        //then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("Developer with defined email is already exists")));
    }

    @Test
    @DisplayName("Test update developer functionality")
    public void givenDeveloperDto_whenUpdateDeveloper_thenSuccessResponse() throws Exception {
        //given
        DeveloperDto dto = DataUtils.getJohnDoeDtoTransient();
        DeveloperEntity entity = DataUtils.getJohnDoePersisted();
        BDDMockito.given(developerService.updateDeveloper(any(DeveloperEntity.class)))
                .willReturn(entity);
        //when
        ResultActions result = mockMvc.perform(put("/api/v1/developers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));
        //then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is("John")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is("Doe")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is("ACTIVE")));
    }

    @Test
    @DisplayName("Test update developer by incorrect id functionality")
    public void givenDeveloperDtoWithIncorrectId_whenUpdateDeveloper_thenErrorResponse() throws Exception {
        //given
        DeveloperDto dto = DataUtils.getJohnDoeDtoTransient();
        BDDMockito.given(developerService.updateDeveloper(any(DeveloperEntity.class)))
                .willThrow(new DeveloperNotFoundException("Developer not found"));
        //when
        ResultActions result = mockMvc.perform(put("/api/v1/developers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));
        //then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("Developer not found")));
    }

    @Test
    @DisplayName("Test get developer by id functionality")
    public void givenId_whenGetDeveloperById_thenSuccessResponse() throws Exception{
        // given
        DeveloperEntity entity = DataUtils.getJohnDoePersisted();
        BDDMockito.given(developerService.getDeveloperById(anyInt()))
                .willReturn(entity);
        // when
        ResultActions result = mockMvc.perform(get("/api/v1/developers/1")
                .contentType(MediaType.APPLICATION_JSON));
        // then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is("John")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is("Doe")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is("ACTIVE")));
    }

    @Test
    @DisplayName("Test get developer by incorrect id functionality")
    public void givenIncorrectId_whenGetDeveloperById_thenErrorResponse() throws Exception{
        // given
        BDDMockito.given(developerService.getDeveloperById(anyInt()))
                .willThrow(new DeveloperNotFoundException("Developer not found"));
        // when
        ResultActions result = mockMvc.perform(get("/api/v1/developers/1")
                .contentType(MediaType.APPLICATION_JSON));
        // then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(404)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("Developer not found")));
    }

    @Test
    @DisplayName("Test soft delete by id functionality")
    public void givenId_whenSoftDelete_thenSuccessResponse() throws Exception {
        // given
        BDDMockito.doNothing().when(developerService).softDeleteById(anyInt());
        // when
        ResultActions result = mockMvc.perform(delete("/api/v1/developers/1")
                .contentType(MediaType.APPLICATION_JSON));
        // then
        verify(developerService, times(1)).softDeleteById(anyInt());
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Test soft delete by incorrect id functionality")
    public void givenIncorrectId_whenSoftDelete_thenErrorResponse() throws Exception {
        // given
        BDDMockito.doThrow(new DeveloperNotFoundException("Developer not found"))
                .when(developerService).softDeleteById(anyInt());
        // when
        ResultActions result = mockMvc.perform(delete("/api/v1/developers/1")
                .contentType(MediaType.APPLICATION_JSON));
        // then
        verify(developerService, times(1)).softDeleteById(anyInt());
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Test hard delete by id functionality")
    public void givenId_whenHardDelete_thenSuccessResponse() throws Exception {
        // given
        BDDMockito.doNothing().when(developerService).hardDeleteById(anyInt());
        // when
        ResultActions result = mockMvc.perform(delete("/api/v1/developers/1?isHard=true")
                .contentType(MediaType.APPLICATION_JSON));
        // then
        verify(developerService, times(1)).hardDeleteById(anyInt());
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Test hard delete by incorrect id functionality")
    public void givenIncorrectId_whenHardDelete_thenErrorResponse() throws Exception {
        // given
        BDDMockito.doThrow(new DeveloperNotFoundException("Developer not found"))
                .when(developerService).hardDeleteById(anyInt());
        // when
        ResultActions result = mockMvc.perform(delete("/api/v1/developers/1?isHard=true")
                .contentType(MediaType.APPLICATION_JSON));
        // then
        verify(developerService, times(1)).hardDeleteById(anyInt());
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}