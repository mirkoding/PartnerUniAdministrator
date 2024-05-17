package de.fhws.fiw.fds.PartnerUniAdministrator.server.api.services;

import de.fhws.fiw.fds.PartnerUniAdministrator.server.api.models.University;
import de.fhws.fiw.fds.PartnerUniAdministrator.server.api.queries.QueryBySearch;
import de.fhws.fiw.fds.PartnerUniAdministrator.server.api.states.universities.*;
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
            try {
                  return new GetAllUniversities(this.serviceContext, new QueryBySearch<>(search, offset, size)).execute();
            }
            catch(SuttonWebAppException e) {
                  throw new WebApplicationException(e.getExceptionMessage(), e.getStatus().getCode());
            }
      }

      @GET
      @Path("{id: \\d+}")
      @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
      public Response getUniversity(@PathParam("id") final long id) {
            try {
                  return new GetSingleUniversity(this.serviceContext, id).execute();
            }
            catch(SuttonWebAppException e) {
                  throw new WebApplicationException(Response.status(e.getStatus().getCode()).entity(e.getExceptionMessage()).build());
            }
      }

      @POST
      @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
      public Response createSingleUniversity(final University universityModel) {
            try {
                  return new PostNewUniversity(this.serviceContext, universityModel).execute();
            }
            catch(SuttonWebAppException e) {
                  throw new WebApplicationException(Response.status(e.getStatus().getCode()).entity(e.getExceptionMessage()).build());
            }
      }

      @PUT
      @Path("{id: \\d+}")
      @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
      public Response updateSingleUniversity(@PathParam("id") final long id, final University universityModel) {
            try {
                  return new PutSingleUniversity(this.serviceContext, id, universityModel).execute();
            }
            catch(SuttonWebAppException e) {
                  throw new WebApplicationException(Response.status(e.getStatus().getCode()).entity(e.getExceptionMessage()).build());
            }
      }

      @DELETE
      @Path("{id: \\d+}")
      public Response deleteSingleUniversity(@PathParam("id") final long id) {
            try {
                  return new DeleteSingleUniversity(this.serviceContext, id).execute();
            }
            catch(SuttonWebAppException e) {
                  throw new WebApplicationException(Response.status(e.getStatus().getCode()).entity(e.getExceptionMessage()).build());
            }
      }
}
