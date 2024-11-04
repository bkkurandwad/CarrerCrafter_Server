package com.majorproject.CareerforIT;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.internal.MongoClientImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.bson.Document;

@SpringBootApplication
public class CareerforItApplication {

	public static void main(String[] args) {
		SpringApplication.run(CareerforItApplication.class, args);
	}
}


