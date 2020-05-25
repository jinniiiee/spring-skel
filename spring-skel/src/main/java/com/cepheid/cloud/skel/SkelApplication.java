package com.cepheid.cloud.skel;

import java.util.ArrayList;
import java.util.stream.Stream;

import com.cepheid.cloud.skel.controller.DescriptionController;
import com.cepheid.cloud.skel.model.State;
import com.cepheid.cloud.skel.repository.DescriptionRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.cepheid.cloud.skel.controller.ItemController;
import com.cepheid.cloud.skel.model.Item;
import com.cepheid.cloud.skel.repository.ItemRepository;

@SpringBootApplication(scanBasePackageClasses = { ItemController.class, DescriptionController.class, SkelApplication.class })
@EnableJpaRepositories(basePackageClasses = { ItemRepository.class, DescriptionRepository.class})
public class SkelApplication {

  public static void main(String[] args) {
    SpringApplication.run(SkelApplication.class, args);
  }

}
