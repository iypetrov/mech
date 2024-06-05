package com.example.mech.cars;

import com.example.mech.common.BaseService;
import com.example.mech.common.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CarService extends BaseService {
    private final Mapper mapper;
    private final CarRepository carRepository;

    public CarService(Mapper mapperUtil, CarRepository carRepository) {
        this.mapper = mapperUtil;
        this.carRepository = carRepository;
    }

    public List<CarModel> getCars(Optional<String> brand) {
        List<CarModel> cars = mapper.map(carRepository.findAll(), CarModel.class);
        return brand.map(string -> cars.stream()
                .filter(s -> s.getBrand().startsWith(string))
                .toList())
                .orElse(cars);
    }

    public CarModel createCar(CarModel car) {
        CarEntity carEntity = mapper.map(car, CarEntity.class);
        return mapper.map(carRepository.save(carEntity), CarModel.class);
    }

    public CarModel updateCar(CarModel car) {
        CarEntity carEntity = carRepository.findById(car.getId()).orElseThrow(() -> new RuntimeException("Car not found"));
        if (Objects.nonNull(car.getBrand())) {
            carEntity.setBrand(car.getBrand());
        }
        if (Objects.nonNull(car.getYear())) {
            carEntity.setYear(car.getYear());
        }
        return mapper.map(carRepository.save(carEntity), CarModel.class);
    }

    public String deleteCar(String id) {
        carRepository.deleteById(id);
        return id;
    }
}
