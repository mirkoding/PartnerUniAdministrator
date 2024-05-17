package de.fhws.fiw.fds.PartnerUniAdministrator.server.api.services;

import de.fhws.fiw.fds.PartnerUniAdministrator.server.api.queries.QueryBySearch;
import de.fhws.fiw.fds.PartnerUniAdministrator.server.api.states.universities.GetAllUniversities;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.Exceptions.SuttonWebAppException;
import de.fhws.fiw.fds.sutton.server.api.services.AbstractJerseyService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("universities")
public class UniversityService extends AbstractJerseyService {

      public UniversityService() {
            super();
      }

      @GET
      @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
      public Response getUniversities(
            @DefaultValue("") @QueryParam("search") final String search,
            @DefaultValue("0") @QueryParam("offset") final int offset,
            @DefaultValue("15") @QueryParam("size") final int size)
      {
            try{
                  return new GetAllUniversities(this.serviceContext, new QueryBySearch<>(search, offset, size)).execute();
            }
            catch(SuttonWebAppException e) {
                  throw new WebApplicationException(e.getExceptionMessage(), e.getStatus().getCode());
            }
      }
}
