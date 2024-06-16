package de.fhws.fiw.fds.PartnerUniAdministrator.server.api.states.university_modules;

import de.fhws.fiw.fds.PartnerUniAdministrator.Start;

public interface UniversityModuleURI {
//      String SHOW_ALL_PARAMETER = "showAll";
      String PATH_ELEMENT = "universities/{id}/modules";

      String REL_PATH = Start.CONTEXT_PATH + Start.APPLICATION_PATH + PATH_ELEMENT;
//      String REL_PATH_SHOW_ALL = Start.CONTEXT_PATH + "/api1/" + PATH_ELEMENT + "?" + SHOW_ALL_PARAMETER + "=true";
//      String REL_PATH_SHOW_ONLY_LINKED = Start.CONTEXT_PATH + "/api1/" + PATH_ELEMENT + "?" + SHOW_ALL_PARAMETER + "=false";
      String REL_PATH_ID = REL_PATH + "/{id}";
}
