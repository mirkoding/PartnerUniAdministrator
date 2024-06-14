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

            // pagination default value is 15
            assertEquals(15, client.universityData().size());
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
            client.start();

            assertTrue(client.isGetAllUniversitiesByFilterAllowed());

            // testing fulltext search
            client.getCollectionOfFilteredUniversities("m");
            assertEquals(200, client.getLastStatusCode());
            assertEquals(8, client.universityData().size());
      }

      @Test
      public void test_get_all_pagination() throws IOException {
            client.start();
            assertTrue(client.getPossibleNextStates().containsKey("InitializeDatabase"));

            client.initializeDatabase();
            assertEquals(201, client.getLastStatusCode());
            client.start();

            assertTrue(client.isGetAllUniversitiesAllowed());
            client.getAllUniversities();
            assertEquals(200, client.getLastStatusCode());

            // default pagination size
            assertEquals(15, client.universityData().size());

            // isGetNextPageAllowed
            assertTrue(client.getPossibleNextStates().containsKey("next"));
            client.getNextPageOfUniversities();
            assertEquals(200, client.getLastStatusCode());

            // Initializing the db populates it with 23 records
            // Since the first page contains 15 records, the second page is expected to have 8 records
            assertEquals(8, client.universityData().size());
      }

      @Test
      public void test_ordering_of_search_ascending() throws IOException {
            client.start();
            client.initializeDatabase();
            client.start();
            client.getCollectionOfFilteredUniversitiesOrdered("ü", '+');
            assertEquals(200, client.getLastStatusCode());
            assertEquals(3, client.universityData().size());

            client.setUniCursor(0);
            client.getSingleUniversity();
            assertEquals(200, client.getLastStatusCode());
            UniversityClientModel receivedUni = client.universityData().getFirst();

            UniversityClientModel expectedUni = new UniversityClientModel(
                  "Hochschule für Wirtschaft und Recht Berlin",
                  "Germany",
                  "Computer Science",
                  "www.jmu.bin.de",
                  "Prof. Dr. Rot",
                  10,
                  10,
                  LocalDate.of(2025, 2, 1),
                  LocalDate.of(2024, 9, 1)
            );

            assertEquals(expectedUni, receivedUni);
      }

      @Test
      public void test_ordering_of_search_descending() throws IOException {
            client.start();
            client.initializeDatabase();
            client.start();
            client.getCollectionOfFilteredUniversitiesOrdered("ü", '-');
            assertEquals(200, client.getLastStatusCode());
            assertEquals(3, client.universityData().size());

            client.setUniCursor(0);
            client.getSingleUniversity();
            assertEquals(200, client.getLastStatusCode());
            UniversityClientModel receivedUni = client.universityData().getFirst();
            UniversityClientModel expectedUni = new UniversityClientModel(
                  "Berliner Hochschule für Technik",
                  "Germany",
                  "Computer Science",
                  "www.jmu.bin.de",
                  "Prof. Dr. Biedermann",
                  10,
                  10,
                  LocalDate.of(2025, 2, 1),
                  LocalDate.of(2024, 9, 1)
            );

            assertEquals(expectedUni, receivedUni);
      }


}
