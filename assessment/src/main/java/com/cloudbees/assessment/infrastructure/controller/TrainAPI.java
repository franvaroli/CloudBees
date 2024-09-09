package com.cloudbees.assessment.infrastructure.controller;

import com.cloudbees.assessment.application.service.train.TrainService;
import com.cloudbees.assessment.domain.dto.TrainDto;
import com.cloudbees.assessment.domain.entity.Train;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
@Tag(name = "TrainApi", description = "Train API")
@RequestMapping(value = "/trains")
@RestController
public class TrainAPI {

    private final TrainService trainService;

    @GetMapping(value = "/")
    public ResponseEntity<List<Train>> getAllTrains() {
        log.info("Call for getAllTrains");
        var trains = trainService.getAllTrains();
        return new ResponseEntity<>(trains, HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Train> createTrain(@Parameter(description = "From") @Size(min = 1, max = 20) String from, @Parameter(description = "Destination") @Size(min = 1, max = 20) String to, @Parameter(description = "price") @Min(1) @Max(10000) double price) {
        var train = new Train(null, from, to, price);
        log.info("Call for createTrain with params:{}", train);
        var trainResponse = trainService.createTrain(train);
        return new ResponseEntity<>(trainResponse, HttpStatus.CREATED);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Train> updateTrain(@Parameter(description = "Train Id") @Min(value = 1) @Max(value = 10) Long id,
                                             @Valid @RequestBody TrainDto trainDto) {
        log.info("Call for updateTrain with id: " + id + " and params:{}", trainDto);
        var trainResponse = trainService.updateTrain(id, trainDto);
        return new ResponseEntity<>(trainResponse, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<HttpStatus> deleteTrain(@Parameter(description = "Id") Long id) {
        log.info("Call for deleteTrain with params:{}", id);
        trainService.deleteTrain(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleException(NoSuchElementException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
