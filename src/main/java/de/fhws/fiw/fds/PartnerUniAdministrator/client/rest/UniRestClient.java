package de.fhws.fiw.fds.PartnerUniAdministrator.client.rest;

import de.fhws.fiw.fds.PartnerUniAdministrator.client.models.UniversityClientModel;
import de.fhws.fiw.fds.PartnerUniAdministrator.client.web.UniversityWebClient;
import de.fhws.fiw.fds.sutton.client.rest2.AbstractRestClient;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class UniRestClient extends AbstractRestClient {
      private static final String BASE_URL = "http://localhost:8080/uni/api1";
      private static final String GET_ALL_UNIS = "getAllUniversities";
      private static final String CREATE_UNI  = "createUniversity";

      private List<UniversityClientModel> currentUniData;
      private int cursorUniData = 0;

      private final UniversityWebClient client;

      public UniRestClient() {
            super();
            this.client = new UniversityWebClient();
            this.currentUniData = Collections.EMPTY_LIST;
      }

      public void resetDatabase() throws IOException {
            processResponse(this.client.resetDatabaseOnServer(BASE_URL), (response -> {
            }));
      }

      public void  start() throws IOException {
            processResponse(this.client.getDispatcher(BASE_URL), (response -> {
            }));
      }

      public boolean isCreatePersonAllowed() {
            return isLinkAvailable(CREATE_UNI);
      }

      public void createUniversity(UniversityClientModel universityClientModel) throws IOException {
            if(isCreatePersonAllowed()) {
                  processResponse(this.client.postNewUniversity(getUrl(CREATE_UNI), universityClientModel), (response -> {
                        this.currentUniData = Collections.EMPTY_LIST;
                        this.cursorUniData = 0;
                  }));
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
                        this.currentUniData = Collections.EMPTY_LIST;
                        this.cursorUniData = 0;
                  });
            }
            else {
                  throw new IllegalStateException();
            }
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

      public void setPersonCursor(int index) {
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
                  this.currentUniData = new LinkedList(response.getResponseData());
                  this.cursorUniData = 0;
            }));
      }

      public static void main(String[] args) throws IOException {
            UniRestClient uniRestClient = new UniRestClient();
            uniRestClient.start();
            System.out.println(uniRestClient.getLastStatusCode());
      }
}
