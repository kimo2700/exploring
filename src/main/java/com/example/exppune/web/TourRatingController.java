package com.example.exppune.web;

import com.example.exppune.domain.Tour;
import com.example.exppune.domain.TourRating;
import com.example.exppune.domain.TourRatingPk;
import com.example.exppune.repo.TourRatingRepository;
import com.example.exppune.repo.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;

@RequestMapping(path = "/tours/{tourid}/ratings")
public class TourRatingController {

    private TourRatingRepository tourRatingRepository;
    private TourRepository tourRepository;

    @Autowired
    public TourRatingController(TourRatingRepository tourRatingRepository, TourRepository tourRepository) {
        this.tourRatingRepository = tourRatingRepository;
        this.tourRepository = tourRepository;
    }

    private TourRatingController() {
    }

    public void createTourRating(int tourId, RatingDto ratingDto){
        Tour tour = verifyTour(tourId);

    }

    private Tour verifyTour(int tourId) throws NoSuchElementException{
        return tourRepository.findById(tourId).orElseThrow(()-> new NoSuchElementException("Tour does not exist"+tourId));

    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String return400(NoSuchElementException ex){
        return ex.getMessage();
    }
}
