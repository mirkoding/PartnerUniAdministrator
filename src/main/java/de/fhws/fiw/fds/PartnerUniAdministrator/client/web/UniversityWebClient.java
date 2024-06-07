package de.fhws.fiw.fds.PartnerUniAdministrator.client.web;

import de.fhws.fiw.fds.PartnerUniAdministrator.client.models.UniversityClientModel;
import de.fhws.fiw.fds.sutton.client.web.GenericWebClient;
import de.fhws.fiw.fds.sutton.client.web.WebApiResponse;

import java.io.IOException;

public class UniversityWebClient {
      private GenericWebClient<UniversityClientModel> client;

      public UniversityWebClient() {
            this.client = new GenericWebClient<>();
      }

      public UniversityWebResponse getDispatcher(String url) throws IOException {
            return createResponse(this.client.sendGetSingleRequest(url, UniversityClientModel.class));
      }

      public UniversityWebResponse getSingleUniversity(String url) throws IOException {
            return createResponse(this.client.sendGetSingleRequest(url, UniversityClientModel.class));
      }

      public UniversityWebResponse getCollectionOfUniversities(String url) throws IOException {
            return createResponse(this.client.sendGetCollectionRequest(url, UniversityClientModel.class));
      }

      public UniversityWebResponse postNewUniversity(String url, UniversityClientModel uni) throws IOException {
            return createResponse(this.client.sendPostRequest(url, uni));
      }

      public UniversityWebResponse putUniversity(String url, UniversityClientModel uni) throws IOException {
            return createResponse(this.client.sendPutRequest(url, uni));
      }

      public UniversityWebResponse deleteUniversity(String url) throws IOException {
            return createResponse(this.client.sendDeleteRequest(url));
      }

      public UniversityWebResponse resetDatabaseOnServer(String url) throws IOException {
            return createResponse(this.client.sendGetSingleRequest(url + "/resetdatabase"));
      }

      private UniversityWebResponse createResponse(WebApiResponse<UniversityClientModel> response) {
            return new UniversityWebResponse(response.getResponseData(), response.getResponseHeaders(), response.getLastStatusCode());
      }
}
