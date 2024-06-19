package de.fhws.fiw.fds.PartnerUniAdministrator.server.api.services;

import de.fhws.fiw.fds.PartnerUniAdministrator.server.api.models.University;
import de.fhws.fiw.fds.PartnerUniAdministrator.server.api.states.dispatcher.GetDispatcher;
import de.fhws.fiw.fds.PartnerUniAdministrator.server.database.DaoFactory;
import de.fhws.fiw.fds.PartnerUniAdministrator.server.database.UniversityDAO;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.Exceptions.SuttonWebAppException;
import de.fhws.fiw.fds.sutton.server.api.services.AbstractJerseyService;
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
      public Response getDispatcher() { // wurde getestet
            try {
                  return new GetDispatcher(this.serviceContext).execute();
            }
            catch(SuttonWebAppException e) {
                  throw new WebApplicationException(Response.status(e.getStatus().getCode()).entity(e.getExceptionMessage()).build());
            }
      }

      @GET
      @Path("resetdatabase")
      public Response resetDatabase() { // Wurde getestet
            System.out.println("Resetting database");
            DaoFactory.getInstance().getUniversityDAO().resetDatabase();
            DaoFactory.getInstance().getUniversityModuleDAO().resetDatabase();
            DaoFactory.getInstance().getModuleDAO().resetDatabase();
            return Response.ok().build();
      }

      @GET
      @Path("initializedatabase")
      @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
      public Response initializeDatabase() { // wurde getestet
            System.out.println("Initializing Database");
            University[] universities = {
            new University(
                  "Prof. Dr. Braun",
                  10,
                  10,
                  "Germany",
                  "Computer Science",
                  "www.thws.bin.de",
                  "THWS",
                  LocalDate.of(2024, 9, 1),
                  LocalDate.of(2025, 2, 1)
            ),

            new University(
                  "Prof. Dr. Waitz",
                  10,
                  10,
                  "Germany",
                  "Computer Science",
                  "www.jmu.bin.de",
                  "JMU",
                  LocalDate.of(2024, 9, 1),
                  LocalDate.of(2025, 2, 1)
            ),

            new University(
                  "Prof. Dr. Balzer",
                  10,
                  10,
                  "Germany",
                  "Computer Science",
                  "www.lmu.bin.de",
                  "LMU",
                  LocalDate.of(2024, 9, 1),
                  LocalDate.of(2025, 2, 1)
            ),

            new University(
                  "Prof. Dr. Heinzl",
                  10,
                  10,
                  "Germany",
                  "Computer Science",
                  "www.tum.bin.de",
                  "TUM",
                  LocalDate.of(2024, 9, 1),
                  LocalDate.of(2025, 2, 1)
            ),

            new University(
                  "Prof. Dr. Fertig",
                  10,
                  10,
                  "Germany",
                  "Computer Science",
                  "www.hm.bin.de",
                  "HM",
                  LocalDate.of(2024, 9, 1),
                  LocalDate.of(2025, 2, 1)
            ),

            new University(
                  "Prof. Dr. Deinzer",
                  10,
                  10,
                  "Germany",
                  "Computer Science",
                  "www.tud.bin.de",
                  "TU Dresden",
                  LocalDate.of(2024, 9, 1),
                  LocalDate.of(2025, 2, 1)
            ),

            new University(
                  "Prof. Dr. John",
                  10,
                  10,
                  "Germany",
                  "Computer Science",
                  "www.hub.bin.de",
                  "Humboldt-Universität zu Berlin",
                  LocalDate.of(2024, 9, 1),
                  LocalDate.of(2025, 2, 1)
            ),

            new University(
                  "Prof. Dr. Schleif",
                  10,
                  10,
                  "Germany",
                  "Computer Science",
                  "www.tha.bin.de",
                  "THA",
                  LocalDate.of(2024, 9, 1),
                  LocalDate.of(2025, 2, 1)
            ),

            new University(
                  "Prof. Dr. Heß",
                  10,
                  10,
                  "Germany",
                  "Computer Science",
                  "www.fau.bin.de",
                  "Friedrich-Alexander-Universität Erlangen-Nürnberg",
                  LocalDate.of(2024, 9, 1),
                  LocalDate.of(2025, 2, 1)
            ),

            new University(
                  "Prof. Dr. Wedlich",
                  10,
                  10,
                  "Germany",
                  "Computer Science",
                  "www.ur.bin.de",
                  "Universität Regensburg",
                  LocalDate.of(2024, 9, 1),
                  LocalDate.of(2025, 2, 1)
            ),

            new University(
                  "Prof. Dr. Biedermann",
                  10,
                  10,
                  "Germany",
                  "Computer Science",
                  "www.bht.bin.de",
                  "Berliner Hochschule für Technik",
                  LocalDate.of(2024, 9, 1),
                  LocalDate.of(2025, 2, 1)
            ),

            new University(
                  "Prof. Dr. Bachmeier",
                  10,
                  10,
                  "Germany",
                  "Computer Science",
                  "www.dbu.bin.de",
                  "Digital Business University of Applied Sciences",
                  LocalDate.of(2024, 9, 1),
                  LocalDate.of(2025, 2, 1)
            ),

            new University(
                  "Prof. Dr. Tanenbaum",
                  10,
                  10,
                  "Germany",
                  "Computer Science",
                  "www.fub.bin.de",
                  "Freie Universität Berlin",
                  LocalDate.of(2024, 9, 1),
                  LocalDate.of(2025, 2, 1)
            ),

            new University(
                  "Prof. Dr. Fielding",
                  10,
                  10,
                  "Germany",
                  "Computer Science",
                  "www.hwr.bin.de",
                  "Hochschule für Wirtschaft und Recht Berlin",
                  LocalDate.of(2024, 9, 1),
                  LocalDate.of(2025, 2, 1)
            ),

            new University(
                  "Prof. Dr. Burners-Lee",
                  10,
                  10,
                  "Germany",
                  "Computer Science",
                  "www.msb.bin.de",
                  "Medical School Berlin",
                  LocalDate.of(2024, 9, 1),
                  LocalDate.of(2025, 2, 1)
            ),

            new University(
                  "Prof. Dr. Turing",
                  10,
                  10,
                  "Germany",
                  "Computer Science",
                  "www.thw.bin.de",
                  "Technische Hochschule Wildau",
                  LocalDate.of(2024, 9, 1),
                  LocalDate.of(2025, 2, 1)
            ),

            new University(
                  "Prof. Dr. Jobs",
                  10,
                  10,
                  "Germany",
                  "Computer Science",
                  "www.bu.bin.de",
                  "Brand University of Applied Sciences",
                  LocalDate.of(2024, 9, 1),
                  LocalDate.of(2025, 2, 1)
            ),

            new University(
                  "Prof. Dr. Wayne",
                  10,
                  10,
                  "Germany",
                  "Computer Science",
                  "www.fu.bin.de",
                  "Fernuni Hagen",
                  LocalDate.of(2024, 9, 1),
                  LocalDate.of(2025, 2, 1)
            ),

            new University(
                  "Prof. Dr. Wozniak",
                  10,
                  10,
                  "Germany",
                  "Computer Science",
                  "www.hsu.bin.de",
                  "Helmut-Schmidt-Universität der Bundeswehr Hamburg",
                  LocalDate.of(2024, 9, 1),
                  LocalDate.of(2025, 2, 1)
            ),

            new University(
                  "Prof. Dr. Zuse",
                  10,
                  10,
                  "Germany",
                  "Computer Science",
                  "www.uh.bin.de",
                  "Universität Hamburg",
                  LocalDate.of(2024, 9, 1),
                  LocalDate.of(2025, 2, 1)
            ),

            new University(
                  "Prof. Dr. Page",
                  10,
                  10,
                  "Germany",
                  "Computer Science",
                  "www.fuas.bin.de",
                  "Frankfurt University of Applied Sciences",
                  LocalDate.of(2024, 9, 1),
                  LocalDate.of(2025, 2, 1)
            ),

            new University(
                  "Prof. Dr. Brin",
                  10,
                  10,
                  "Germany",
                  "Computer Science",
                  "www.jwgu.bin.de",
                  "Johann Wolfgang Goethe-Universität",
                  LocalDate.of(2024, 9, 1),
                  LocalDate.of(2025, 2, 1)
            ),

            new University(
                  "Prof. Dr. Gates",
                  10,
                  10,
                  "Germany",
                  "Computer Science",
                  "www.harvard.bin.com",
                  "Harvard",
                  LocalDate.of(2024, 9, 1),
                  LocalDate.of(2025, 2, 1)
            )};

            UniversityDAO universityDAO = DaoFactory.getInstance().getUniversityDAO();
            for(University uni : universities) {
                  universityDAO.create(uni);
            }
            return Response.created(uriInfo.getAbsolutePathBuilder().build()).build();
      }
}
