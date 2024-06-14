package de.fhws.fiw.fds.PartnerUniAdministrator.client.rest;

import de.fhws.fiw.fds.PartnerUniAdministrator.client.models.UniversityClientModel;
import de.fhws.fiw.fds.PartnerUniAdministrator.client.web.UniversityWebClient;
import de.fhws.fiw.fds.PartnerUniAdministrator.server.api.states.universities.UniversityRelTypes;
import de.fhws.fiw.fds.sutton.client.rest2.AbstractRestClient;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class UniRestClient extends AbstractRestClient {
      private static final String BASE_URL = "http://localhost:8080/uni/api1";
      private static final String GET_ALL_UNIS = "getAllUniversities";
      private static final String GET_ALL_UNIS_ASC = "getAllUniversitiesAscending";
      private static final String GET_ALL_UNIS_DSC = "getAllUniversitiesDescending";
      private static final String CREATE_UNI  = "createUniversity";
      private static final String GET_ALL_UNIS_FILTER = "getFilteredUniversities";
      private static final String GET_ALL_UNIS_FILTER_ASCENDING = "getFilteredUniversitiesAscending";
      private static final String GET_ALL_UNIS_FILTER_DESCENDING = "getFilteredUniversitiesDescending";

      private List<UniversityClientModel> currentUniData;
      private int cursorUniData = 0;

      private final UniversityWebClient client;

      public UniRestClient() {
            super();
            this.client = new UniversityWebClient();
            this.currentUniData = Collections.EMPTY_LIST;
      }

      public void resetDatabase() throws IOException {
            processResponse(this.client.resetDatabaseOnServer(BASE_URL), (response) -> {
            });
      }

      public void initializeDatabase() throws IOException {
            processResponse(this.client.initializeDatabaseOnServer(BASE_URL), (response) -> {
            });
      }

      public void  start() throws IOException {
            processResponse(this.client.getDispatcher(BASE_URL), (response) -> {
            });
      }

      public boolean isCreateUniversityAllowed() {
            return isLinkAvailable(CREATE_UNI);
      }

      public void createUniversity(UniversityClientModel universityClientModel) throws IOException {
            if(isCreateUniversityAllowed()) {
                  processResponse(this.client.postNewUniversity(getUrl(CREATE_UNI), universityClientModel), (response) -> {
                        this.currentUniData = Collections.EMPTY_LIST;
                        this.cursorUniData = 0;
                  });
            }
            else {
                  throw new IllegalStateException();
            }
      }

      public boolean isGetAllUniversitiesAllowed() {
            return isLinkAvailable(GET_ALL_UNIS);
      }

      public void getAllUniversities() throws IOException {
            if(isGetAllUniversitiesAllowed()) {
                  processResponse(this.client.getCollectionOfUniversities(getUrl(GET_ALL_UNIS)), (response) -> {
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

            processResponse(this.client.getCollectionOfUniversities(url), (response) -> {
                  this.currentUniData = response.getResponseData().stream().toList();
                  this.cursorUniData = 0;
            });
      }

      public boolean isGetAllUniversitiesByFilterAllowed() {
            return isLinkAvailable(GET_ALL_UNIS_FILTER);
      }

      public void getCollectionOfFilteredUniversities(String search) throws IOException {
            String url;
            if((url = getUrl(GET_ALL_UNIS_FILTER)) == null) {
                  throw new IllegalStateException();
            }
            processResponse(this.client.getCollectionOfUniversities(url.replace("{SEARCH}", search)),
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

            processResponse(this.client.getCollectionOfUniversities(url.replace("{SEARCH}", search)),
                  (response) -> {
                  this.currentUniData = response.getResponseData().stream().toList();
                  this.cursorUniData = 0;
            });
      }

      public void getNextPageOfUniversities() throws IOException {
            String url;
            if((url = getUrl("next")) == null) {
                  throw new IllegalStateException();
            }
            processResponse(this.client.getCollectionOfUniversities(url), (response) -> {
                  this.currentUniData = response.getResponseData().stream().toList();
                  this.cursorUniData = 0;
            });
      }


      public boolean isGetSingleUniversityAllowed() {
            return !this.currentUniData.isEmpty() || isLocationHeaderAvailable();
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

      public void getSingleUniversity() throws IOException {
            if(isLocationHeaderAvailable()) {
                  getSingleUniversity(getLocationHeaderURL());
            }
            else if(!this.currentUniData.isEmpty()) {
                  getSingleUniversity(this.cursorUniData);
            }
            else {
                  throw new IllegalStateException();
            }
      }

      public void getSingleUniversity(int index) throws IOException {
            getSingleUniversity(this.currentUniData.get(index).getSelfLink().getUrl());
      }

      public void getSingleUniversity(String url) throws IOException {
            processResponse(this.client.getSingleUniversity(url), (response -> {
                  this.currentUniData = new LinkedList<>(response.getResponseData());
                  this.cursorUniData = 0;
            }));
      }

      public void updateUniversity(UniversityClientModel updatedUniversity) throws IOException {
            processResponse(this.client.putUniversity(
                  this.currentUniData.get(this.cursorUniData).getSelfLink().getUrl(), updatedUniversity),
                  (response) -> {
                        this.currentUniData = Collections.EMPTY_LIST;
                        this.cursorUniData = 0;
            });
      }

      public void deleteUniversity() throws IOException {
            processResponse(this.client.deleteUniversity(this.currentUniData.get(this.cursorUniData).getSelfLink().getUrl()), response -> {
                  this.currentUniData = Collections.EMPTY_LIST;
                  this.cursorUniData = 0;
            });
      }

}
