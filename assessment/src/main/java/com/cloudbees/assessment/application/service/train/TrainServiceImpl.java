package com.cloudbees.assessment.application.service.train;

import com.cloudbees.assessment.domain.entity.Train;
import com.cloudbees.assessment.infrastructure.repository.TrainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor(onConstructor = @__({ @Autowired}))
public class TrainServiceImpl implements TrainService {
    
    private final TrainRepository trainRepository;

    @Override
    public List<Train> getAllTrains() {
        return trainRepository.findAll();
    }

    @Override
    public Train createTrain(Train train) {
        return trainRepository.save(train);
    }

    @Override
    public Train updateTrain(Train train) {
        if(trainRepository.existsById(train.getId())) {
            return trainRepository.save(train);
        } else {
            throw new NoSuchElementException("Train with id " + train.getId() + " doesn't exist");
        }
    }

    @Override
    public void deleteTrain(Long id) {
        if(trainRepository.existsById(id)) {
            trainRepository.deleteById(id);
        } else {
            throw new NoSuchElementException("Train with id " + id + " doesn't exist");
        }
    }
}
