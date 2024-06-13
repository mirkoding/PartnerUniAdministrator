package de.fhws.fiw.fds.PartnerUniAdministrator.server;

import com.github.javafaker.Faker;
import de.fhws.fiw.fds.PartnerUniAdministrator.client.models.UniversityClientModel;
import de.fhws.fiw.fds.PartnerUniAdministrator.client.rest.UniRestClient;
import de.fhws.fiw.fds.PartnerUniAdministrator.server.api.states.universities.UniversityRelTypes;
import de.fhws.fiw.fds.sutton.client.utils.Link;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestPuaAppIT {
      final private Faker faker = new Faker();
      private UniRestClient client;

      @BeforeEach
      public void setup() throws IOException {
            client = new UniRestClient();
            this.client.resetDatabase();
      }

      @Test
      public void test_dispatcher_is_available() throws IOException {
            client.start();
            assertEquals(200, client.getLastStatusCode());
      }

      @Test
      public void test_dispatcher_is_get_all_universities_allowed() throws IOException {
            client.start();
            assertTrue(client.isGetAllUniversitiesAllowed());
      }

      @Test
      public void test_dispatcher_is_get_all_universities_by_filter_allowed() throws IOException {
            client.start();
            Map<String, Link> states = client.getPossibleNextStates();
            assertTrue(states.containsKey(UniversityRelTypes.GET_ALL_UNIVERSITIES_BY_FILTER));
      }

      @Test
      public void test_dispatcher_is_create_university_allowed() throws IOException {
            client.start();
            assertTrue(client.isCreateUniversityAllowed());
      }

      @Test
      public void test_initialize_database() throws IOException {
            client.start();
            assertTrue(client.getPossibleNextStates().containsKey("InitializeDatabase"));

            client.initializeDatabase();
            assertEquals(201, client.getLastStatusCode());

            client.start();
            client.getAllUniversities();
            assertEquals(15, client.universityData().size()); // pagination default value is 15
      }

      @Test
      public void test_create_university() throws IOException {
            client.start();

            UniversityClientModel uni = new UniversityClientModel(
                  "Technische Hochschule Würzburg Schweinfurt",
                  "Germany",
                  "Computer Science",
                  "https://fiw.thws.de",
                  "Prof. Dr. Peter Braun",
                  20,
                  20,
                  LocalDate.of(2025, 3, 1),
                  LocalDate.of(2024, 10, 3)
            );

            client.createUniversity(uni);
            assertEquals(201, client.getLastStatusCode());
      }

      @Test
      public void test_create_and_get_new_university() throws IOException {
            client.start();
            UniversityClientModel uni = new UniversityClientModel(
                  "Technische Hochschule Würzburg Schweinfurt",
                  "Germany",
                  "Computer Science",
                  "https://fiw.thws.de",
                  "Prof. Dr. Peter Braun",
                  20,
                  20,
                  LocalDate.of(2025, 3, 1),
                  LocalDate.of(2024, 10, 3)
            );

            client.createUniversity(uni);
            assertEquals(201, client.getLastStatusCode());
            assertTrue(client.isGetSingleUniversityAllowed());

            client.getSingleUniversity();
            assertEquals(200, client.getLastStatusCode());

            UniversityClientModel receivedUni = client.universityData().getFirst();
            assertEquals(uni, receivedUni);
      }

      @Test
      public void test_create_5_unis_and_get_all() throws IOException {
            for(int i=0; i<5; i++) {
                  client.start();

                  UniversityClientModel model = new UniversityClientModel();
                  model.setPartnerUniName(faker.name().name());
                  model.setPartnerUniCountry(faker.country().name());
                  model.setPartnerUniDepartment("Computer Science");
                  model.setPartnerUniDepartmentURL(faker.internet().url());
                  model.setContactPersonName(faker.name().fullName());
                  model.setAmountStudentsToSend(20);
                  model.setAmountStudentsToReceive(20);
                  model.setStartNextSpringSem(LocalDate.of(2025, 3, 1));
                  model.setStartNextAutumnSem(LocalDate.of(2024, 10, 3));

                  client.createUniversity(model);
                  assertEquals(201, client.getLastStatusCode());
            }

            client.start();
            assertTrue(client.isGetAllUniversitiesAllowed());

            client.getAllUniversities();
            assertEquals(200, client.getLastStatusCode());
            assertEquals(5, client.universityData().size());

            client.setUniCursor(0);
            client.getSingleUniversity();
            assertEquals(200, client.getLastStatusCode());
      }

      @Test
      public void test_get_all_universities_filtered_by_name() throws IOException {
            client.start();
            assertTrue(client.getPossibleNextStates().containsKey("InitializeDatabase"));

            client.initializeDatabase();
            assertEquals(201, client.getLastStatusCode());
            client.start(); // Call for dispatcher to get URL to get and filter all universities

            assertTrue(client.getPossibleNextStates().containsKey(UniversityRelTypes.GET_ALL_UNIVERSITIES_BY_FILTER));
            client.getCollectionOfFilteredUniversities("m", 0, 0); // testing fulltext search

            assertEquals(200, client.getLastStatusCode());
            assertEquals(8, client.universityData().size());
      }

      @Test
      public void test_get_all_pagination() throws IOException {
            client.start();
            assertTrue(client.getPossibleNextStates().containsKey("InitializeDatabase"));

            client.initializeDatabase();
            assertEquals(201, client.getLastStatusCode());
            client.start(); // Call for dispatcher to get URL to get and filter all universities

            assertTrue(client.isGetAllUniversitiesAllowed());
            client.getAllUniversities();
            assertEquals(200, client.getLastStatusCode());
            assertEquals(15, client.universityData().size()); // default pagination size
            assertTrue(client.getPossibleNextStates().containsKey("next")); // isGetNextPageAllowed
            client.getNextPageOfUniversities();
            assertEquals(200, client.getLastStatusCode());
            assertEquals(8, client.universityData().size());
      }

      // by now the dispatcher should be fully tested

      @Test
      public void test_get_all_pagination_with_custom_size() throws IOException {
            client.start();
            assertTrue(client.getPossibleNextStates().containsKey("InitializeDatabase"));
            client.initializeDatabase();
            assertEquals(201, client.getLastStatusCode());
            client.start();
            assertTrue(client.getPossibleNextStates().containsKey(UniversityRelTypes.GET_ALL_UNIVERSITIES_BY_FILTER));
            client.getAllUniversities(0, 5);
            assertEquals(200, client.getLastStatusCode());
            assertEquals(5, client.universityData().size());
      }

      @Test
      public void test_get_all_pagination_with_custom_offset() throws IOException {
            client.start();
            assertTrue(client.getPossibleNextStates().containsKey("InitializeDatabase"));
            client.initializeDatabase();
            assertEquals(201, client.getLastStatusCode());

            client.start();
            assertTrue(client.getPossibleNextStates().containsKey(UniversityRelTypes.GET_ALL_UNIVERSITIES_BY_FILTER));
            client.getAllUniversities(5, 0);
            assertEquals(200, client.getLastStatusCode());
            assertEquals(10, client.universityData().size()); // 10 because size is 15 by default

            client.setUniCursor(0);
            client.getSingleUniversity();
            assertEquals(200, client.getLastStatusCode());
            UniversityClientModel receivedUni = client.universityData().getFirst(); // received uni should be uni5 from initialize db method
            UniversityClientModel uni5 = new UniversityClientModel(
                  "HM",
                  "Germany",
                  "Computer Science",
                  "www.jmu.bin.de",
                  "Prof. Dr. Fertig",
                  10,
                  10,
                  LocalDate.of(2025, 2, 1),
                  LocalDate.of(2024, 9, 1)
            );
            assertEquals(uni5, receivedUni);
      }
}