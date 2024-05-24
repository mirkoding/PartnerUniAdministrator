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
                  "Prof. Dr. Braun",
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
                  "Prof. Dr. Schwarz",
                  10,
                  10,
                  "Germany",
                  "Computer Science",
                  "www.jmu.bin.de",
                  "JMU",
                  LocalDate.of(2024, 9, 1),
                  LocalDate.of(2025, 2, 1)
            );

            University uni3 = new University(
                  "Prof. Dr. Balzer",
                  10,
                  10,
                  "Germany",
                  "Computer Science",
                  "www.jmu.bin.de",
                  "LMU",
                  LocalDate.of(2024, 9, 1),
                  LocalDate.of(2025, 2, 1)
            );

            University uni4 = new University(
                  "Prof. Dr. Heinzl",
                  10,
                  10,
                  "Germany",
                  "Computer Science",
                  "www.jmu.bin.de",
                  "TUM",
                  LocalDate.of(2024, 9, 1),
                  LocalDate.of(2025, 2, 1)
            );

            University uni5 = new University(
                  "Prof. Dr. Fertig",
                  10,
                  10,
                  "Germany",
                  "Computer Science",
                  "www.jmu.bin.de",
                  "HM",
                  LocalDate.of(2024, 9, 1),
                  LocalDate.of(2025, 2, 1)
            );

            University uni6 = new University(
                  "Prof. Dr. Deinzer",
                  10,
                  10,
                  "Germany",
                  "Computer Science",
                  "www.jmu.bin.de",
                  "TU Dresden",
                  LocalDate.of(2024, 9, 1),
                  LocalDate.of(2025, 2, 1)
            );

            University uni7 = new University(
                  "Prof. Dr. John",
                  10,
                  10,
                  "Germany",
                  "Computer Science",
                  "www.jmu.bin.de",
                  "Humboldt-Universität zu Berlin",
                  LocalDate.of(2024, 9, 1),
                  LocalDate.of(2025, 2, 1)
            );

            University uni8 = new University(
                  "Prof. Dr. Schleif",
                  10,
                  10,
                  "Germany",
                  "Computer Science",
                  "www.jmu.bin.de",
                  "THA",
                  LocalDate.of(2024, 9, 1),
                  LocalDate.of(2025, 2, 1)
            );

            University uni9 = new University(
                  "Prof. Dr. Heß",
                  10,
                  10,
                  "Germany",
                  "Computer Science",
                  "www.jmu.bin.de",
                  "Friedrich-Alexander-Universität Erlangen-Nürnberg",
                  LocalDate.of(2024, 9, 1),
                  LocalDate.of(2025, 2, 1)
            );

            University uni10 = new University(
                  "Prof. Dr. Wedlich",
                  10,
                  10,
                  "Germany",
                  "Computer Science",
                  "www.jmu.bin.de",
                  "Universität Regensburg",
                  LocalDate.of(2024, 9, 1),
                  LocalDate.of(2025, 2, 1)
            );

            University uni11 = new University(
                  "Prof. Dr. Biedermann",
                  10,
                  10,
                  "Germany",
                  "Computer Science",
                  "www.jmu.bin.de",
                  "Berliner Hochschule für Technik",
                  LocalDate.of(2024, 9, 1),
                  LocalDate.of(2025, 2, 1)
            );

            University uni12 = new University(
                  "Prof. Dr. Bachmeier",
                  10,
                  10,
                  "Germany",
                  "Computer Science",
                  "www.jmu.bin.de",
                  "Digital Business University of Applied Sciences",
                  LocalDate.of(2024, 9, 1),
                  LocalDate.of(2025, 2, 1)
            );

            University uni13 = new University(
                  "Prof. Dr. Lila",
                  10,
                  10,
                  "Germany",
                  "Computer Science",
                  "www.jmu.bin.de",
                  "Freie Universität Berlin",
                  LocalDate.of(2024, 9, 1),
                  LocalDate.of(2025, 2, 1)
            );

            University uni14 = new University(
                  "Prof. Dr. Rot",
                  10,
                  10,
                  "Germany",
                  "Computer Science",
                  "www.jmu.bin.de",
                  "Hochschule für Wirtschaft und Recht Berlin",
                  LocalDate.of(2024, 9, 1),
                  LocalDate.of(2025, 2, 1)
            );

            University uni15 = new University(
                  "Prof. Dr. Blau",
                  10,
                  10,
                  "Germany",
                  "Computer Science",
                  "www.jmu.bin.de",
                  "Medical School Berlin",
                  LocalDate.of(2024, 9, 1),
                  LocalDate.of(2025, 2, 1)
            );

            University uni16 = new University(
                  "Prof. Dr. Pink",
                  10,
                  10,
                  "Germany",
                  "Computer Science",
                  "www.jmu.bin.de",
                  "Technische Hochschule Wildau",
                  LocalDate.of(2024, 9, 1),
                  LocalDate.of(2025, 2, 1)
            );

            University uni17 = new University(
                  "Prof. Dr. Rosa",
                  10,
                  10,
                  "Germany",
                  "Computer Science",
                  "www.jmu.bin.de",
                  "Brand University of Applied Sciences",
                  LocalDate.of(2024, 9, 1),
                  LocalDate.of(2025, 2, 1)
            );

            University uni18 = new University(
                  "Prof. Dr. Gelb",
                  10,
                  10,
                  "Germany",
                  "Computer Science",
                  "www.jmu.bin.de",
                  "Fernuni Hagen",
                  LocalDate.of(2024, 9, 1),
                  LocalDate.of(2025, 2, 1)
            );

            University uni19 = new University(
                  "Prof. Dr. Türkis",
                  10,
                  10,
                  "Germany",
                  "Computer Science",
                  "www.jmu.bin.de",
                  "Helmut-Schmidt-Universität der Bundeswehr Hamburg",
                  LocalDate.of(2024, 9, 1),
                  LocalDate.of(2025, 2, 1)
            );

            University uni20 = new University(
                  "Prof. Dr. Müller",
                  10,
                  10,
                  "Germany",
                  "Computer Science",
                  "www.jmu.bin.de",
                  "Universität Hamburg",
                  LocalDate.of(2024, 9, 1),
                  LocalDate.of(2025, 2, 1)
            );

            University uni21 = new University(
                  "Prof. Dr. Meier",
                  10,
                  10,
                  "Germany",
                  "Computer Science",
                  "www.jmu.bin.de",
                  "Frankfurt University of Applied Sciences",
                  LocalDate.of(2024, 9, 1),
                  LocalDate.of(2025, 2, 1)
            );

            University uni22 = new University(
                  "Prof. Dr. Kek",
                  10,
                  10,
                  "Germany",
                  "Computer Science",
                  "www.jmu.bin.de",
                  "Johann Wolfgang Goethe-Universität",
                  LocalDate.of(2024, 9, 1),
                  LocalDate.of(2025, 2, 1)
            );

            University uni23 = new University(
                  "Prof. Dr. Schwanz",
                  10,
                  10,
                  "Germany",
                  "Computer Science",
                  "www.jmu.bin.de",
                  "Harvard",
                  LocalDate.of(2024, 9, 1),
                  LocalDate.of(2025, 2, 1)
            );

            UniversityDAO universityDAO = DaoFactory.getInstance().getUniversityDAO();
            universityDAO.create(uni1);
            universityDAO.create(uni2);
            universityDAO.create(uni3);
            universityDAO.create(uni4);
            universityDAO.create(uni5);
            universityDAO.create(uni6);
            universityDAO.create(uni7);
            universityDAO.create(uni8);
            universityDAO.create(uni9);
            universityDAO.create(uni10);
            universityDAO.create(uni11);
            universityDAO.create(uni12);
            universityDAO.create(uni13);
            universityDAO.create(uni14);
            universityDAO.create(uni15);
            universityDAO.create(uni16);
            universityDAO.create(uni17);
            universityDAO.create(uni18);
            universityDAO.create(uni19);
            universityDAO.create(uni20);
            universityDAO.create(uni21);
            universityDAO.create(uni22);
            universityDAO.create(uni23);
            return Response.created(uriInfo.getAbsolutePathBuilder().build()).build();
      }
}
