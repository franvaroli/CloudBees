package com.cloudbees.assessment.application.service.train;

import com.cloudbees.assessment.domain.entity.Train;

import java.util.List;

public interface TrainService {

    List<Train> getAllTrains();
    Train createTrain(Train train);
    Train updateTrain(Train train);
    void deleteTrain(Long id);
}
