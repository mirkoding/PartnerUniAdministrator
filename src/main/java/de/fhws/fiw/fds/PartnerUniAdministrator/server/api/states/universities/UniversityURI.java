package de.fhws.fiw.fds.PartnerUniAdministrator.server.api.states.universities;


import de.fhws.fiw.fds.PartnerUniAdministrator.Start;

public interface UniversityURI {
      String PATH_ELEMENT = "universities";
      String REL_PATH = Start.CONTEXT_PATH + "/api1/" + PATH_ELEMENT;
      String REL_PATH_ID = REL_PATH + "/id";
}
