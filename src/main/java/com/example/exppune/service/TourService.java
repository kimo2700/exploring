package com.example.exppune.service;

import com.example.exppune.repo.TourPackageRepository;
import com.example.exppune.repo.TourRepository;
import com.example.exppune.domain.Difficulty;
import com.example.exppune.domain.Region;
import com.example.exppune.domain.Tour;
import com.example.exppune.domain.TourPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class TourService {


    @Autowired
    private TourRepository tourRepository;
    @Autowired
    private TourPackageRepository tourPackageRepository;



    public Tour createTour(String title, String description, String blurb, Integer price, String duration, String bullets, String keywords, String tourPackageName, Difficulty difficulty, Region region){
        TourPackage tourPackage = tourPackageRepository.findByName(tourPackageName)
                .orElseThrow(() -> new RuntimeException("Tour Package does not exist" +tourPackageName));
        return tourRepository.save(new Tour(title, description, blurb, price, duration, bullets, keywords, tourPackage, difficulty, region));
    }

    public long total(){
        return tourRepository.count();
    }
}
