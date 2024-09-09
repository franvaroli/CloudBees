package com.cloudbees.assessment.application.service.train;

import com.cloudbees.assessment.domain.dto.TrainDto;
import com.cloudbees.assessment.domain.entity.Train;
import com.cloudbees.assessment.domain.mapper.TrainMapper;
import com.cloudbees.assessment.infrastructure.repository.TicketRepository;
import com.cloudbees.assessment.infrastructure.repository.TrainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class TrainServiceImpl implements TrainService {

    private final TrainRepository trainRepository;
    private final TicketRepository ticketRepository;

    @Override
    public List<Train> getAllTrains() {
        return trainRepository.findAll();
    }

    @Override
    public Train createTrain(Train train) {
        return trainRepository.save(train);
    }

    @Override
    public Train updateTrain(Long id, TrainDto trainDto) {
        var train = trainRepository.findById(id);
        if (train.isPresent()) {
            return trainRepository.save(TrainMapper.fromDto(id, trainDto, train.get()));
        } else {
            throw new NoSuchElementException("Train with id " + id + " doesn't exist");
        }
    }

    @Override
    public void deleteTrain(Long id) {
        if (trainRepository.existsById(id)) {
            if (!ticketRepository.existsTicketByTrainId(id)) {
                trainRepository.deleteById(id);
            } else {
                throw new NoSuchElementException("There is at least one ticket for user with id " + id);
            }
        } else {
            throw new NoSuchElementException("Train with id " + id + " doesn't exist");
        }
    }
}
