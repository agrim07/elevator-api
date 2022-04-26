package org.genesis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GenesisApplication {

  public static final Logger log = LoggerFactory.getLogger(GenesisApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(GenesisApplication.class, args);
    log.info("Genesis Elevator Application has started...");
  }
}
