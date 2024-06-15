package de.fhws.fiw.fds.PartnerUniAdministrator.client.web;

import de.fhws.fiw.fds.PartnerUniAdministrator.client.models.ModuleClientModel;
import de.fhws.fiw.fds.sutton.client.web.WebApiResponse;
import okhttp3.Headers;

import java.util.Collection;

public class ModuleWebResponse extends WebApiResponse<ModuleClientModel> {
      public ModuleWebResponse(final Collection<ModuleClientModel> responseData, final Headers headers, final int lastStatusCode) {
            super(responseData, headers, lastStatusCode);
      }
}
