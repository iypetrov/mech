package com.example.mech.cars;

import com.example.mech.codegen.types.Car;
import com.example.mech.codegen.types.RequestCreateCar;
import com.example.mech.codegen.types.RequestUpdateCar;
import com.example.mech.common.Mapper;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;

import java.util.List;
import java.util.Optional;

@DgsComponent
public class CarsDataFetcher {
    private final Mapper mapper;
    private final CarService carService;

    public CarsDataFetcher(Mapper mapperUtil, CarService carService) {
        this.mapper = mapperUtil;
        this.carService = carService;
    }

    @DgsQuery
    public List<Car> cars(@InputArgument String brandFilter) {
        return mapper.map(carService.getCars(Optional.ofNullable(brandFilter)), Car.class);
    }

    @DgsMutation
    public Car createCar(@InputArgument RequestCreateCar request) {
        return mapper.map(carService.createCar(mapper.map(request, CarModel.class)), Car.class);
    }

    @DgsMutation
    public Car updateCar(@InputArgument RequestUpdateCar request) {
        return mapper.map(carService.updateCar(mapper.map(request, CarModel.class)), Car.class);
    }

    @DgsMutation
    public String deleteCar(@InputArgument String id) {
        return carService.deleteCar(id);
    }
}
