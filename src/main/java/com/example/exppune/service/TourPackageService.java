package com.example.exppune.service;

import com.example.exppune.repo.TourPackageRepository;
import com.example.exppune.domain.TourPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TourPackageService {

    @Autowired
    private TourPackageRepository tourPackageRepository;



    public TourPackage createTourPackage(String code, String name){
        return tourPackageRepository.findById(code)
                .orElse(tourPackageRepository.save(new TourPackage(code, name)));
    }

    public Iterable<TourPackage> lookup(){
        return tourPackageRepository.findAll();
    }

    public long total(){
        return tourPackageRepository.count();
    }

}
