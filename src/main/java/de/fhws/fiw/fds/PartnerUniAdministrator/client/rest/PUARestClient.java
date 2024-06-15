package de.fhws.fiw.fds.PartnerUniAdministrator.client.rest;

import de.fhws.fiw.fds.PartnerUniAdministrator.client.models.ModuleClientModel;
import de.fhws.fiw.fds.PartnerUniAdministrator.client.models.UniversityClientModel;
import de.fhws.fiw.fds.PartnerUniAdministrator.client.web.ModuleWebClient;
import de.fhws.fiw.fds.PartnerUniAdministrator.client.web.UniversityWebClient;
import de.fhws.fiw.fds.sutton.client.rest2.AbstractRestClient;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class PUARestClient extends AbstractRestClient {
      private static final String BASE_URL = "http://localhost:8080/uni/api1";
      private static final String GET_ALL_UNIS = "getAllUniversities";
      private static final String GET_ALL_UNIS_ASC = "getAllUniversitiesAscending";
      private static final String GET_ALL_UNIS_DSC = "getAllUniversitiesDescending";
      private static final String GET_ALL_UNIS_FILTER = "getFilteredUniversities";
      private static final String GET_ALL_UNIS_FILTER_ASCENDING = "getFilteredUniversitiesAscending";
      private static final String GET_ALL_UNIS_FILTER_DESCENDING = "getFilteredUniversitiesDescending";
      private static final String GET_UNI = "getUniversity";
      private static final String CREATE_UNI  = "createUniversity";
      private static final String DEL_UNI = "deleteUniversity";
      private static final String UPDATE_UNI = "updateUniversity";

      private static final String CREATE_MODULE = "createModuleOfUniversity";
      private static final String GET_ALL_LINKED_MODULES = "getAllModulesOfUniversity";

      private List<UniversityClientModel> currentUniData;
      private int cursorUniData = 0;

      private List<ModuleClientModel> currentModuleData;
      private int cursorModuleData = 0;

      private final UniversityWebClient uniClient;
      private final ModuleWebClient moduleClient;

      public PUARestClient() {
            super();
            this.uniClient = new UniversityWebClient();
            this.moduleClient = new ModuleWebClient();
            this.currentUniData = Collections.EMPTY_LIST;
      }


      // -------------------- Utility Methods --------------------

      public void resetDatabase() throws IOException {
            processResponse(this.uniClient.resetDatabaseOnServer(BASE_URL), (response) -> {
            });
      }

      public void initializeDatabase() throws IOException {
            processResponse(this.uniClient.initializeDatabaseOnServer(BASE_URL), (response) -> {
            });
      }

      public void  start() throws IOException {
            processResponse(this.uniClient.getDispatcher(BASE_URL), (response) -> {
            });
      }

      public List<UniversityClientModel> universityData() {
            if(this.currentUniData.isEmpty()) {
                  throw new IllegalStateException();
            }
            return this.currentUniData;
      }

      public void setUniCursor(int index) {
            if(0 <= index && index < this.currentUniData.size()) {
                  this.cursorUniData = index;
            }
            else {
                  throw new IllegalStateException();
            }
      }

      public List<ModuleClientModel> moduleData() {
            if(this.currentModuleData.isEmpty()) {
                  throw new IllegalStateException();
            }
            return this.currentModuleData;
      }

      public void setModuleCursor(int index) {
            if(0 <= index && index < this.currentModuleData.size()) {
                  this.cursorModuleData = index;
            }
            else {
                 throw new IllegalStateException();
            }
      }

      public void getNextPageOfUniversities() throws IOException {
            String url;
            if((url = getUrl("next")) == null) {
                  throw new IllegalStateException();
            }
            processResponse(this.uniClient.getCollectionOfUniversities(url), (response) -> {
                  this.currentUniData = response.getResponseData().stream().toList();
                  this.cursorUniData = 0;
            });
      }


      // -------------------- Various CRUD-Operations --------------------

      public void createUniversity(UniversityClientModel universityClientModel) throws IOException {
            if(isCreateUniversityAllowed()) {
                  processResponse(this.uniClient.postNewUniversity(getUrl(CREATE_UNI), universityClientModel), (response) -> {
                        this.currentUniData = Collections.EMPTY_LIST;
                        this.cursorUniData = 0;
                  });
            }
            else {
                  throw new IllegalStateException();
            }
      }

      public void createAndLinkModuleToUniversity(ModuleClientModel moduleClientModel) throws IOException {
            if(isCreateModuleAllowed()) {
                  processResponse(this.moduleClient.postNewModule(this.currentUniData.get(this.cursorUniData).getModule().getUrl(), moduleClientModel), (response) -> {
                        this.currentModuleData = Collections.EMPTY_LIST;
                        this.cursorModuleData = 0;
                  });
            }
            else {
                  throw new IllegalStateException();
            }
      }

      public void getAllUniversities() throws IOException {
            if(isGetAllUniversitiesAllowed()) {
                  processResponse(this.uniClient.getCollectionOfUniversities(getUrl(GET_ALL_UNIS)), (response) -> {
                        this.currentUniData = response.getResponseData().stream().toList();
                        this.cursorUniData = 0;
                  });
            }
            else {
                  throw new IllegalStateException();
            }
      }

      public void getAllUniversitiesOrdered(char order) throws IOException {
            String url = null;
            if(order == '+') {
                  url = getUrl(GET_ALL_UNIS_ASC);
            }
            else if(order == '-') {
                  url = getUrl(GET_ALL_UNIS_DSC);
            }
            if(url == null) {
                  throw new IllegalStateException();
            }

            processResponse(this.uniClient.getCollectionOfUniversities(url), (response) -> {
                  this.currentUniData = response.getResponseData().stream().toList();
                  this.cursorUniData = 0;
            });
      }

      public void getCollectionOfFilteredUniversities(String search) throws IOException {
            String url;
            if((url = getUrl(GET_ALL_UNIS_FILTER)) == null) {
                  throw new IllegalStateException();
            }
            processResponse(this.uniClient.getCollectionOfUniversities(url.replace("{SEARCH}", search)),
                  (response) -> {
                  this.currentUniData = response.getResponseData().stream().toList();
                  this.cursorUniData = 0;
            });
      }

      public void getCollectionOfFilteredUniversitiesOrdered(String search, char order) throws IOException {
            String url = null;
            if(order == '+') {
                  url = getUrl(GET_ALL_UNIS_FILTER_ASCENDING);
            }
            else if(order == '-') {
                  url = getUrl(GET_ALL_UNIS_FILTER_DESCENDING);
            }
            if(url == null) {
                  throw new IllegalStateException();
            }

            processResponse(this.uniClient.getCollectionOfUniversities(url.replace("{SEARCH}", search)),
                  (response) -> {
                  this.currentUniData = response.getResponseData().stream().toList();
                  this.cursorUniData = 0;
            });
      }

      public void getAllModulesOfUniversity() throws IOException {
            if(isGetAllModulesAllowed()) {
                  processResponse(this.moduleClient.getCollectionOfModules(this.currentUniData.get(this.cursorUniData).getModule().getUrl()), (response) -> {
                        this.currentModuleData = response.getResponseData().stream().toList();
                        this.cursorModuleData = 0;
                  });
            }
            else {
                  throw new IllegalStateException();
            }
      }

      public void getSingleUniversity() throws IOException {
            if(isGetSingleUniversityAllowed()) {
                  if(isLocationHeaderAvailable()) {
                        getSingleUniversity(getLocationHeaderURL());
                  }
                  else if(isLinkAvailable(GET_UNI)) {
                        getSingleUniversity(getUrl(GET_UNI));
                  }
                  else if(!this.currentUniData.isEmpty()) {
                        getSingleUniversity(this.cursorUniData);
                  }
            }
            else {
                  throw new IllegalStateException();
            }
      }

      public void getSingleUniversity(int index) throws IOException {
            getSingleUniversity(this.currentUniData.get(index).getSelfLink().getUrl());
      }

      public void getSingleUniversity(String url) throws IOException {
            processResponse(this.uniClient.getSingleUniversity(url), (response -> {
                  this.currentUniData = new LinkedList<>(response.getResponseData());
                  this.cursorUniData = 0;
            }));
      }

      public void getSingleModuleOfUniversity() throws IOException {
            processResponse(this.moduleClient.getSingleModule(this.currentModuleData.get(this.cursorModuleData).getSelfLinkOnSecond().getUrl()), (response) -> {
                  this.currentModuleData = new LinkedList<>(response.getResponseData());
                  this.cursorModuleData = 0;
            });
      }

      public void updateUniversity(UniversityClientModel updatedUniversity) throws IOException {
            if(isUpdateUniversityAllowed()) {
                  processResponse(this.uniClient.putUniversity(
                        this.currentUniData.get(this.cursorUniData).getSelfLink().getUrl(), updatedUniversity),
                        (response) -> {
                              this.currentUniData = Collections.emptyList();
                              this.cursorUniData = 0;
                  });
            }
            else {
                  throw new IllegalStateException();
            }
      }

      public void deleteUniversity() throws IOException {
            if(isDeleteUniversityAllowed()) {
                  if(isLocationHeaderAvailable()) {
                        processResponse(this.uniClient.deleteUniversity(getLocationHeaderURL()), response -> {
                              this.currentUniData = Collections.emptyList();
                              this.cursorUniData = 0;
                        });
                  }
                  else if(!this.currentUniData.isEmpty()) {
                        processResponse(this.uniClient.deleteUniversity(this.currentUniData.get(this.cursorUniData).getSelfLink().getUrl()), response -> {
                              this.currentUniData = Collections.emptyList();
                              this.cursorUniData = 0;
                        });
                  }
                  else {
                        processResponse(this.uniClient.deleteUniversity(getUrl(DEL_UNI)), response -> {
                              this.currentUniData = Collections.emptyList();
                              this.cursorUniData = 0;
                        });
                  }
            }
            else {
                  throw new IllegalStateException();
            }
      }


      // is XX allowed methods

      public boolean isCreateUniversityAllowed() {
            return isLinkAvailable(CREATE_UNI);
      }

      public boolean isGetAllUniversitiesAllowed() {
            return isLinkAvailable(GET_ALL_UNIS);
      }

      public boolean isGetAllUniversitiesByFilterAllowed() {
            return isLinkAvailable(GET_ALL_UNIS_FILTER);
      }

      public boolean isGetSingleUniversityAllowed() {
            return !this.currentUniData.isEmpty() || isLocationHeaderAvailable() || isLinkAvailable(GET_UNI);
      }

      public boolean isDeleteUniversityAllowed() {
            return isLinkAvailable(DEL_UNI);
      }

      public boolean isUpdateUniversityAllowed() {
            return isLinkAvailable(UPDATE_UNI);
      }

      public boolean isGetAllUniversitiesOrderedAllowed() {
            return isLinkAvailable(GET_ALL_UNIS_ASC) && isLinkAvailable(GET_ALL_UNIS_DSC);
      }

      public boolean isCreateModuleAllowed() {
            return isLinkAvailable(CREATE_MODULE);
      }

      public boolean isGetAllModulesAllowed() {
            return isLinkAvailable(GET_ALL_LINKED_MODULES);
      }
}
