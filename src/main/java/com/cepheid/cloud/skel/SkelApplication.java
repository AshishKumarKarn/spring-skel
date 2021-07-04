package com.cepheid.cloud.skel;

import com.cepheid.cloud.skel.controller.ItemController;
import com.cepheid.cloud.skel.model.Description;
import com.cepheid.cloud.skel.model.Item;
import com.cepheid.cloud.skel.repository.ItemRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.stream.Stream;

@SpringBootApplication(scanBasePackageClasses = {ItemController.class, SkelApplication.class})
@EnableJpaRepositories(basePackageClasses = {ItemRepository.class})
public class SkelApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkelApplication.class, args);
    }

    @Bean
    ApplicationRunner initItems(ItemRepository repository) {
        return args -> {

            Stream.of("Lord of the rings", "Hobbit", "Silmarillion", "Unfinished Tales and The History of Middle-earth")
                    .forEach(name -> {
                        Item item = new Item();
                        item.setName(name);
                        Description description11 = new Description();
                        description11.setDescriptionComment("First description of movie " + name);
                        description11.setItem(item);
                        Description description12 = new Description();
                        description12.setDescriptionComment("Second description of movie " + name);
                        item.setState(Item.State.VALID);
                        item.addDescription(description11);
                        item.addDescription(description12);
                        repository.save(item);
                    });

            repository.findAll().forEach(System.out::println);
        };
    }

}
