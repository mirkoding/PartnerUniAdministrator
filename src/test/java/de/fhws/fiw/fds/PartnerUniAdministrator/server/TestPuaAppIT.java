package de.fhws.fiw.fds.PartnerUniAdministrator.server;

import com.github.javafaker.Faker;
import de.fhws.fiw.fds.PartnerUniAdministrator.client.models.ModuleClientModel;
import de.fhws.fiw.fds.PartnerUniAdministrator.client.models.UniversityClientModel;
import de.fhws.fiw.fds.PartnerUniAdministrator.client.rest.PUARestClient;
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
      private PUARestClient client;

      private final UniversityClientModel testUni = new UniversityClientModel(
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

      private ModuleClientModel testModule;

      @BeforeEach
      public void setup() throws IOException {
            client = new PUARestClient();
            this.client.resetDatabase();
            this.testModule = new ModuleClientModel();
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
            client.createUniversity(testUni);
            assertEquals(201, client.getLastStatusCode());
      }

      @Test
      public void test_create_and_get_new_university() throws IOException {
            client.start();
            client.createUniversity(testUni);
            assertEquals(201, client.getLastStatusCode());
            assertTrue(client.isGetSingleUniversityAllowed());

            client.getSingleUniversity();
            assertEquals(200, client.getLastStatusCode());

            UniversityClientModel receivedUni = client.universityData().getFirst();
            assertEquals(testUni, receivedUni);
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
      public void test_get_all_universities_ordered_ascending() throws IOException {
            client.start();
            client.initializeDatabase();

            client.start();
            assertTrue(client.isGetAllUniversitiesOrderedAllowed());
            client.getAllUniversitiesOrdered('+');
            assertEquals(200, client.getLastStatusCode());

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

            client.setUniCursor(0);
            client.getSingleUniversity();
            assertEquals(200, client.getLastStatusCode());

            assertEquals(expectedUni, client.universityData().getFirst());
      }

      @Test
      public void test_get_all_universities_ordered_descending() throws IOException {
            client.start();
            client.initializeDatabase();

            client.start();
            assertTrue(client.isGetAllUniversitiesOrderedAllowed());
            client.getAllUniversitiesOrdered('-');
            assertEquals(200, client.getLastStatusCode());

            UniversityClientModel expectedUni = new UniversityClientModel(
                  "Universität Regensburg",
                  "Germany",
                  "Computer Science",
                  "www.jmu.bin.de",
                  "Prof. Dr. Wedlich",
                  10,
                  10,
                  LocalDate.of(2025, 2, 1),
                  LocalDate.of(2024, 9, 1)
            );

            client.setUniCursor(0);
            client.getSingleUniversity();
            assertEquals(200, client.getLastStatusCode());
            assertEquals(expectedUni, client.universityData().getFirst());
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

      @Test
      public void test_create_and_update_university() throws IOException {
            client.start();
            assertTrue(client.isCreateUniversityAllowed());
            client.createUniversity(testUni);
            assertEquals(201, client.getLastStatusCode());
            client.getSingleUniversity();
            assertEquals(200, client.getLastStatusCode());

            UniversityClientModel receivedUni = client.universityData().getFirst();
            receivedUni.setContactPersonName("TestName");

            client.setUniCursor(0);
            client.updateUniversity(receivedUni);
            assertEquals(204, client.getLastStatusCode());

            client.getSingleUniversity();
            assertEquals(200, client.getLastStatusCode());
            UniversityClientModel receivedUni2 = client.universityData().getFirst();
            assertEquals("TestName", receivedUni2.getContactPersonName());
      }

      @Test
      public void test_create_and_delete_university() throws IOException {
            client.start();
            assertTrue(client.isCreateUniversityAllowed());
            client.createUniversity(testUni);
            assertEquals(201, client.getLastStatusCode());

            client.start();
            client.createUniversity(testUni);
            assertEquals(201, client.getLastStatusCode());

            client.start();
            client.getAllUniversities();
            assertEquals(2, this.client.universityData().size());

            client.setUniCursor(0);
            client.getSingleUniversity();
            assertEquals(200, client.getLastStatusCode());
            client.deleteUniversity();
            assertEquals(204, client.getLastStatusCode());

            client.start();
            client.getAllUniversities();
            assertEquals(200, client.getLastStatusCode());
            assertEquals(1, this.client.universityData().size());
      }


      // relation tests

      @Test
      public void test_create_module_for_university() throws IOException {
            client.start();
            assertTrue(client.isCreateUniversityAllowed());
            client.createUniversity(testUni);
            assertEquals(201, client.getLastStatusCode());

            testModule.setModuleName("Foundations of Distributed Systems");
            testModule.setSemesterWhenModuleIsOffered(4);
            testModule.setNumberOfCredits(5);

            client.getSingleUniversity();
            assertEquals(200, client.getLastStatusCode());
            client.setUniCursor(0);
            client.createAndLinkModuleToUniversity(testModule);
            assertEquals(201, client.getLastStatusCode());
      }

      @Test
      public void test_create_and_get_collection_of_modules_of_university() throws IOException {
            client.start();
            assertTrue(client.isCreateUniversityAllowed());
            client.createUniversity(testUni);
            assertEquals(201, client.getLastStatusCode());
            client.getSingleUniversity();
            assertEquals(200, client.getLastStatusCode());

            for(int i=0; i<5; i++) {
                  testModule = new ModuleClientModel();
                  testModule.setModuleName(faker.educator().course());
                  testModule.setSemesterWhenModuleIsOffered(i);
                  testModule.setNumberOfCredits(faker.number().numberBetween(1, 7));

                  client.start();
                  client.getAllUniversities();
                  client.setUniCursor(0);
                  client.getSingleUniversity();
                  client.createAndLinkModuleToUniversity(testModule);
                  assertEquals(201, client.getLastStatusCode());
            }

            client.start();
            client.getAllUniversities();
            assertEquals(200, client.getLastStatusCode());
            client.setUniCursor(0);
            client.getSingleUniversity();
            client.getAllModulesOfUniversity();
            assertEquals(200, client.getLastStatusCode());
            assertEquals(5, this.client.moduleData().size());
      }

      @Test
      public void test_create_and_get_single_module_of_university() throws IOException {
            client.start();
            client.createUniversity(testUni);
            ModuleClientModel expectedModule = new ModuleClientModel();

            for(int i=0; i<5; i++) {
                  testModule = new ModuleClientModel();
                  testModule.setModuleName(faker.educator().course());
                  testModule.setSemesterWhenModuleIsOffered(i);
                  testModule.setNumberOfCredits(faker.number().numberBetween(1, 7));

                  client.start();
                  client.getAllUniversities();
                  client.setUniCursor(0);
                  client.getSingleUniversity();
                  client.createAndLinkModuleToUniversity(testModule);
                  assertEquals(201, client.getLastStatusCode());
                  if(i==1) expectedModule = testModule;
            }

            client.start();
            client.getAllUniversities();
            assertEquals(200, client.getLastStatusCode());
            client.setUniCursor(0);
            client.getSingleUniversity();
            client.getAllModulesOfUniversity();
            assertEquals(200, client.getLastStatusCode());
            assertEquals(5, this.client.moduleData().size());

            client.setModuleCursor(1);
            client.getSingleModuleOfUniversity();
            assertEquals(200, client.getLastStatusCode());

            ModuleClientModel receivedModule = client.moduleData().getFirst();
            assertEquals(expectedModule, receivedModule);
      }
}
