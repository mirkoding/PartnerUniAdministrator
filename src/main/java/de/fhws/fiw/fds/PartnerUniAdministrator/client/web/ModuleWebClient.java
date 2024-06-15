package de.fhws.fiw.fds.PartnerUniAdministrator.client.web;

import de.fhws.fiw.fds.PartnerUniAdministrator.client.models.ModuleClientModel;
import de.fhws.fiw.fds.sutton.client.web.GenericWebClient;
import de.fhws.fiw.fds.sutton.client.web.WebApiResponse;

import java.io.IOException;

public class ModuleWebClient {
      private GenericWebClient<ModuleClientModel> client;

      public ModuleWebClient() {
            this.client = new GenericWebClient<>();
      }

      public ModuleWebResponse getSingleModule(String url) throws IOException {
            return createResponse(this.client.sendGetSingleRequest(url, ModuleClientModel.class));
      }

      public ModuleWebResponse getCollectionOfModules(String url) throws IOException {
            return createResponse(this.client.sendGetCollectionRequest(url, ModuleClientModel.class));
      }

      public ModuleWebResponse postNewModule(String url, ModuleClientModel uni) throws IOException {
            return createResponse(this.client.sendPostRequest(url, uni));
      }

      public ModuleWebResponse putModule(String url, ModuleClientModel uni) throws IOException {
            return createResponse(this.client.sendPutRequest(url, uni));
      }

      public ModuleWebResponse deleteModule(String url) throws IOException {
            return createResponse(this.client.sendDeleteRequest(url));
      }

      private ModuleWebResponse createResponse(WebApiResponse<ModuleClientModel> response) {
            return new ModuleWebResponse(response.getResponseData(), response.getResponseHeaders(), response.getLastStatusCode());
      }
}
