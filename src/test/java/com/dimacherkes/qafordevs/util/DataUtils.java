package com.dimacherkes.qafordevs.util;

import com.dimacherkes.qafordevs.dto.DeveloperDto;
import com.dimacherkes.qafordevs.entity.DeveloperEntity;
import com.dimacherkes.qafordevs.entity.Status;

public class DataUtils {

    public static DeveloperEntity getJohnDoeTransient() {
        return DeveloperEntity.builder()
                .firstName("Jonh")
                .lastName("Doe")
                .email("jonh.doe@gmail.com")
                .specialty("Java")
                .status(Status.ACTIVE)
                .build();
    }

    public static DeveloperEntity getMikeSmithTransient() {
        return DeveloperEntity.builder()
                .firstName("Mike")
                .lastName("Smith")
                .email("mike.smith@gmail.com")
                .specialty("Java")
                .status(Status.ACTIVE)
                .build();
    }

    public static DeveloperEntity getFrankJonesTransient() {
        return DeveloperEntity.builder()
                .firstName("Frank")
                .lastName("Jones")
                .email("frank.jones@gmail.com")
                .specialty("Java")
                .status(Status.DELETED)
                .build();
    }

    public static DeveloperEntity getJohnDoePersisted() {
        return DeveloperEntity.builder()
                .id(1)
                .firstName("John")
                .lastName("Doe")
                .email("jonh.doe@gmail.com")
                .specialty("Java")
                .status(Status.ACTIVE)
                .build();
    }

    public static DeveloperEntity getMikeSmithPersisted() {
        return DeveloperEntity.builder()
                .id(2)
                .firstName("Mike")
                .lastName("Smith")
                .email("mike.smith@gmail.com")
                .specialty("Python")
                .status(Status.ACTIVE)
                .build();
    }

    public static DeveloperEntity getFrankJonesPersisted() {
        return DeveloperEntity.builder()
                .id(3)
                .firstName("Frank")
                .lastName("Jones")
                .email("frank.jones@gmail.com")
                .specialty("PHP")
                .status(Status.DELETED)
                .build();
    }

    public static DeveloperDto getJohnDoeDtoTransient() {
        return DeveloperDto.builder()
                .firstName("Jonh")
                .lastName("Doe")
                .email("jonh.doe@gmail.com")
                .specialty("Java")
                .status(Status.ACTIVE)
                .build();
    }

    public static DeveloperDto getMikeSmithDtoTransient() {
        return DeveloperDto.builder()
                .firstName("Mike")
                .lastName("Smith")
                .email("mike.smith@gmail.com")
                .specialty("Java")
                .status(Status.ACTIVE)
                .build();
    }

    public static DeveloperDto getFrankJonesDtoTransient() {
        return DeveloperDto.builder()
                .firstName("Frank")
                .lastName("Jones")
                .email("frank.jones@gmail.com")
                .specialty("Java")
                .status(Status.DELETED)
                .build();
    }

    public static DeveloperDto getJohnDoeDtoPersisted() {
        return DeveloperDto.builder()
                .id(1)
                .firstName("Jonh")
                .lastName("Doe")
                .email("jonh.doe@gmail.com")
                .specialty("Java")
                .status(Status.ACTIVE)
                .build();
    }

    public static DeveloperDto getMikeSmithDtoPersisted() {
        return DeveloperDto.builder()
                .id(2)
                .firstName("Mike")
                .lastName("Smith")
                .email("mike.smith@gmail.com")
                .specialty("Python")
                .status(Status.ACTIVE)
                .build();
    }

    public static DeveloperDto getFrankJonesDtoPersisted() {
        return DeveloperDto.builder()
                .id(3)
                .firstName("Frank")
                .lastName("Jones")
                .email("frank.jones@gmail.com")
                .specialty("PHP")
                .status(Status.DELETED)
                .build();
    }

}
