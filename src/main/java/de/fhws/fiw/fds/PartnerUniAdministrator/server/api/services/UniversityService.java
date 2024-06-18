package de.fhws.fiw.fds.PartnerUniAdministrator.server.api.services;

import de.fhws.fiw.fds.PartnerUniAdministrator.server.api.models.Module;
import de.fhws.fiw.fds.PartnerUniAdministrator.server.api.models.University;
import de.fhws.fiw.fds.PartnerUniAdministrator.server.api.queries.QueryAllUniversities;
import de.fhws.fiw.fds.PartnerUniAdministrator.server.api.queries.QueryByModuleName;
import de.fhws.fiw.fds.PartnerUniAdministrator.server.api.queries.QueryBySearch;
import de.fhws.fiw.fds.PartnerUniAdministrator.server.api.states.universities.*;
import de.fhws.fiw.fds.PartnerUniAdministrator.server.api.states.university_modules.*;
import de.fhws.fiw.fds.PartnerUniAdministrator.server.database.DaoFactory;
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

      // ------------------------------- Bearbeitung von Requests für University -------------------------------

      @GET
      @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
      public Response getUniversities(
            @DefaultValue("") @QueryParam("search") final String search,
            @DefaultValue("") @QueryParam("order") final String order,
            @DefaultValue("0") @QueryParam("offset") final int offset,
            @DefaultValue("15") @QueryParam("size") final int size)
      {
            try {
                  if(search == null || search.isEmpty()) {
                        return new GetAllUniversities(this.serviceContext, new QueryAllUniversities<>(order, offset, size)).execute();
                  }
                  return new GetAllUniversities(this.serviceContext, new QueryBySearch<>(search, order, offset, size)).execute();
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
                  if(DaoFactory.getInstance().getUniversityDAO().doesUniversityExist(id))
                        return new PutSingleUniversity(this.serviceContext, id, universityModel).execute();
                  else throw new WebApplicationException();
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

      // ------------------------------- Bearbeitung von Requests für Modules (linked with Universities) -------------------------------

      @GET
      @Path("{universityId: \\d+}/modules")
      @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
      public Response getModulesFromUniversity(@PathParam("universityId") final long universityId,
                                               @DefaultValue("") @QueryParam("moduleName") final String moduleName,
                                               @DefaultValue("0") @QueryParam("offset") int offset,
                                               @DefaultValue("15") @QueryParam("size") int size)
      {
            try {
                  return new GetAllModulesFromUniversity(this.serviceContext, universityId,
                        new QueryByModuleName(universityId, moduleName, offset, size)).execute();
            }
            catch(SuttonWebAppException e) {
                  throw new WebApplicationException(Response.status(e.getStatus().getCode()).entity(e.getExceptionMessage()).build());
            }
      }

      @GET
      @Path("{universityId: \\d+}/modules/{moduleId: \\d+}")
      @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
      public Response getSingleModuleFromUniversity(@PathParam("universityId") final long universityId,
                                                    @PathParam("moduleId") final long moduleId)
      {
            try {
                  return new GetSingleModuleFromUniversity(this.serviceContext, universityId, moduleId).execute();
            }
            catch(SuttonWebAppException e) {
                  throw new WebApplicationException(Response.status(e.getStatus().getCode()).entity(e.getExceptionMessage()).build());
            }
      }

      @POST
      @Path("{universityId: \\d+}/modules")
      @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
      public Response createNewModuleForUniversity(@PathParam("universityId") final long universityId,
                                                   final Module moduleModel)
      {
            try {
                  if(DaoFactory.getInstance().getUniversityDAO().doesUniversityExist(universityId))
                        return new PostNewModuleOfUniversity(this.serviceContext, universityId, moduleModel).execute();
                  else
                        throw new WebApplicationException();
            }
            catch(SuttonWebAppException e) {
                  throw new WebApplicationException(Response.status(e.getStatus().getCode()).entity(e.getExceptionMessage()).build());
            }
      }

      @PUT
      @Path("{universityId: \\d+}/modules/{moduleId: \\d+}")
      @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
      public Response updateModuleFromUniversity(@PathParam("universityId") final long universityId,
                                                 @PathParam("moduleId") final long moduleId,
                                                 final Module moduleModel)
      {
            try {
                  return new PutSingleModuleFromUniversity(this.serviceContext, universityId, moduleId, moduleModel).execute();
            }
            catch(SuttonWebAppException e) {
                  throw new WebApplicationException(Response.status(e.getStatus().getCode()).entity(e.getExceptionMessage()).build());
            }
      }

      @DELETE
      @Path("{universityId: \\d+}/modules/{moduleId: \\d+}")
      public Response deleteModuleFromUniversity(@PathParam("universityId") final long universityId,
                                                 @PathParam("moduleId") final long moduleId)
      {
            try {
                  return new DeleteSingleModuleFromUniversity(this.serviceContext, moduleId, universityId).execute();
            }
            catch(SuttonWebAppException e) {
                  throw new WebApplicationException(Response.status(e.getStatus().getCode()).entity(e.getExceptionMessage()).build());
            }
      }

}
