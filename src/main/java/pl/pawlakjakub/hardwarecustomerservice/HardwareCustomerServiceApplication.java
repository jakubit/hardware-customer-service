package pl.pawlakjakub.hardwarecustomerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import pl.pawlakjakub.hardwarecustomerservice.model.Category;
import pl.pawlakjakub.hardwarecustomerservice.model.Hardware;
import pl.pawlakjakub.hardwarecustomerservice.repository.CategoryRepository;
import pl.pawlakjakub.hardwarecustomerservice.repository.HardwareRepository;

import static pl.pawlakjakub.hardwarecustomerservice.model.Category.CategoryName.REFRIGERATOR;

@SpringBootApplication
public class HardwareCustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HardwareCustomerServiceApplication.class, args);
    }

}
