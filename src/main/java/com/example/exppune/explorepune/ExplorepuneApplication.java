package com.example.exppune.explorepune;

import com.example.exppune.service.TourPackageService;
import com.example.exppune.service.TourService;
import com.example.exppune.domain.Difficulty;
import com.example.exppune.domain.Region;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.PropertyAccessor.FIELD;

@SpringBootApplication
@ComponentScan(basePackages = "com.example")
@EntityScan("com.example.exppune.domain")
@EnableJpaRepositories("com.example.exppune.repo")
public class ExplorepuneApplication implements CommandLineRunner {

	@Value("${ep.importfile}")
	private String importFile;

	@Autowired
	private TourService tourService;

	@Autowired
	private TourPackageService tourPackageService;


	public static void main(String[] args) {
		SpringApplication.run(ExplorepuneApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		//create Tour Packages
		createTourPackages();
		long numOfTourPackages = tourPackageService.total();
		//create Tours
		createTours(importFile);
		long numOfTours = tourService.total();
	}

	public void createTourPackages(){
		tourPackageService.createTourPackage("BC","Backpack Cal");
		tourPackageService.createTourPackage("CC","California Calm");
		tourPackageService.createTourPackage("CH", "California Hot springs");
		tourPackageService.createTourPackage("CY", "Cycle California");
		tourPackageService.createTourPackage("DS", "From Desert to Sea");
		tourPackageService.createTourPackage("KC", "Kids California");
		tourPackageService.createTourPackage("NW", "Nature Watch");
		tourPackageService.createTourPackage("SC", "Snowboard Cali");
		tourPackageService.createTourPackage("TC", "Taste of California");
	}

	private void createTours(String fileToImport) throws IOException{
		TourFromFile.read(fileToImport).forEach(importedTour ->
				tourService.createTour(importedTour.getTitle(),
						importedTour.getDescription(),
						importedTour.getBlurb(),
						importedTour.getPrice(),
						importedTour.getLength(),
						importedTour.getBullets(),
						importedTour.getKeywords(),
						importedTour.getPackageType(),
						importedTour.getDifficulty(),
						importedTour.getRegion()));

	}

	//Helper class to import ExplorePune.json
	public static class TourFromFile{
		//fields
		private String packageType, title, blurb, description, bullets, difficulty, length, price, region, keywords;
		//reader
		static List<TourFromFile> read(String fileToImport) throws IOException{
			return new ObjectMapper().setVisibility(FIELD, ANY).
					readValue(new FileInputStream(fileToImport), new TypeReference<List<TourFromFile>>() {
					});
		}

		protected TourFromFile() {
		}

		public String getPackageType() {
			return packageType;
		}

		public String getTitle() {
			return title;
		}

		public String getBlurb() {
			return blurb;
		}

		public String getDescription() {
			return description;
		}

		public String getBullets() {
			return bullets;
		}

		public Difficulty getDifficulty() {
			return Difficulty.valueOf(difficulty);
		}

		public String getLength() {
			return length;
		}

		public Integer getPrice() {
			return Integer.parseInt(price);
		}

		public Region getRegion() {
			return Region.findByLabel(region);
		}

		public String getKeywords() {
			return keywords;
		}
	}
}
