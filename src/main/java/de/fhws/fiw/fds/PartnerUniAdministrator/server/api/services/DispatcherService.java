package de.fhws.fiw.fds.PartnerUniAdministrator.server.api.services;

import de.fhws.fiw.fds.PartnerUniAdministrator.server.api.states.dispatcher.GetDispatcher;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.Exceptions.SuttonWebAppException;
import de.fhws.fiw.fds.sutton.server.api.services.AbstractJerseyService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;


@Path("")
public class DispatcherService extends AbstractJerseyService {

      @GET
      public Response getDispatcher() {
            try {
                  return new GetDispatcher(this.serviceContext).execute();
            }
            catch(SuttonWebAppException e) {
                  throw new WebApplicationException(Response.status(e.getStatus().getCode()).entity(e.getExceptionMessage()).build());
            }
      }
}
