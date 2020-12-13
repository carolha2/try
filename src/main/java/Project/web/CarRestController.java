package Project.web;

import Project.domain.AppUser;
import Project.domain.Car;
import Project.domain.Parameters;
import Project.repository.CarRepository;
import Project.repository.ParametersRepository;
import Project.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class CarRestController
{
    @Autowired
    CarService service;

    @Autowired
    CarRepository carRepository;

    @Autowired
    ParametersRepository paramsRepository;

    /**
     * Web service for getting all the appUsers in the application.
     *
     * @return list of all cars
     */
    @RequestMapping(value = "/cars", method = RequestMethod.GET)
    public List<Car> getAllCars() {
        List<Car> cars = service.getAllCars();
        return (List<Car>) cars;
        }


    /**
     * Web service for getting a car by its ID
     *
     * @param id Car cid
     * @return car
     */
    @RequestMapping(value = "/cars/{id}", method = RequestMethod.GET)
    public ResponseEntity<Car> getCarById(@PathVariable("id") Long id)
            throws RuntimeException {
        Car car = service.getCarById(id);
        if(car==null){
            return new ResponseEntity<Car>(HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity<Car>(car, HttpStatus.OK);
        }
    }

    /**
     * Method for adding a car
     *
     * @param car
     * @return
     */
    @RequestMapping(value = "/cars", method = RequestMethod.POST)
    public ResponseEntity<Car> createCar(@RequestBody Car car) {
        if(car.getSeats() == null)
        {
            Optional<Parameters> def = paramsRepository.findById((long) 1);
            //car.setParams((Parameters)def);
            //car.setSeats(paramsRepository.findById(1).getSeats());
        }
        return new ResponseEntity<Car>(carRepository.save(car), HttpStatus.CREATED);
    }


    /**
     * Method for editing an car details
     *
     * @param car
     * @return modified car
     */
    @RequestMapping(value = "/cars", method = RequestMethod.PUT)
    public Car updateCar(@RequestBody Car car) {
        return carRepository.save(car);
    }


    /**
     * Method for deleting a car by its ID
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/cars/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Car> deleteUser(@PathVariable Long id) {
        Car car = service.getCarById(id);
        if (car == null) {
            return new ResponseEntity<Car>(HttpStatus.NO_CONTENT);
        } else {
            carRepository.delete(car);
            return new ResponseEntity<Car>(car, HttpStatus.OK);
        }

    }


}