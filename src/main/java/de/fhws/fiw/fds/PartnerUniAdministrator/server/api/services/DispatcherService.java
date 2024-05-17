package de.fhws.fiw.fds.PartnerUniAdministrator.server.api.services;

import de.fhws.fiw.fds.PartnerUniAdministrator.server.api.models.University;
import de.fhws.fiw.fds.PartnerUniAdministrator.server.api.states.dispatcher.GetDispatcher;
import de.fhws.fiw.fds.PartnerUniAdministrator.server.api.states.universities.UniversityURI;
import de.fhws.fiw.fds.PartnerUniAdministrator.server.database.DaoFactory;
import de.fhws.fiw.fds.PartnerUniAdministrator.server.database.UniversityDAO;
import de.fhws.fiw.fds.PartnerUniAdministrator.server.database.inmemory.UniversityStorage;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.Exceptions.SuttonWebAppException;
import de.fhws.fiw.fds.sutton.server.api.services.AbstractJerseyService;
import de.fhws.fiw.fds.suttondemo.server.database.utils.InitializeDatabase;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDate;

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

      @GET
      @Path("initializedatabase")
      @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
      public Response initializeDatabase() {
            System.out.println("Initializing Database");
            University uni1 = new University(
                  "Prof. Dr. Peter Braun",
                  10,
                  10,
                  "Germany",
                  "Computer Science",
                  "www.thws.bin.de",
                  "THWS",
                  LocalDate.of(2024, 9, 1),
                  LocalDate.of(2025, 2, 1)
            );

            University uni2 = new University(
                  "Prof. Dr. Peter Schwarz",
                  10,
                  10,
                  "Germany",
                  "Computer Science",
                  "www.jmu.bin.de",
                  "JMU",
                  LocalDate.of(2024, 9, 1),
                  LocalDate.of(2025, 2, 1)
            );

            UniversityDAO universityDAO = DaoFactory.getInstance().getUniversityDAO();
            universityDAO.create(uni1);
            universityDAO.create(uni2);
            return Response.created(uriInfo.getAbsolutePathBuilder().build()).build();
      }
}
