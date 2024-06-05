package com.example.mech.common;

import jakarta.annotation.Resource;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Mapper {
    @Resource
    private ModelMapper modelMapper;

    public <T> T map(Object sourceObject, Class<T> targetClass) {
        return modelMapper.map(sourceObject, targetClass);
    }

    public <T> List<T> map(List<?> sourceObjects, Class<T> targetClass) {
        return sourceObjects
                .stream()
                .map(obj -> modelMapper.map(obj, targetClass))
                .toList();
    }
}
