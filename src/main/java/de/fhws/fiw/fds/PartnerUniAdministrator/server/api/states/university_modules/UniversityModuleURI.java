package de.fhws.fiw.fds.PartnerUniAdministrator.server.api.states.university_modules;

import de.fhws.fiw.fds.PartnerUniAdministrator.Start;

public interface UniversityModuleURI {
      String PATH_ELEMENT = "universities/{id}/modules";
      String REL_PATH = Start.CONTEXT_PATH + "/" + Start.APPLICATION_PATH + "/" + PATH_ELEMENT;
      String REL_PATH_ID = REL_PATH + "/{id}";
}
