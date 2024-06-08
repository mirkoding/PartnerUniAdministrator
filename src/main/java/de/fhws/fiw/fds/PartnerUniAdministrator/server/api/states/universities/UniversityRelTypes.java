package de.fhws.fiw.fds.PartnerUniAdministrator.server.api.states.universities;

public interface UniversityRelTypes {
      //TODO prüfen, ob ich für jeden media type einen eigenen rel type brauche
      //aktuell bekomme ich immer einen Fehler, weil in einer Klasse (vergessen welche) die rel types gemapped werden und es dann den selben key schonmal gibt.
      String CREATE_UNIVERSITY = "createUniversity";
      String GET_ALL_UNIVERSITIES = "getAllUniversities";
      String UPDATE_SINGLE_UNIVERSITY = "updateUniversity";
      String DELETE_SINGLE_UNIVERSITY = "deleteUniversity";
      String GET_SINGLE_UNIVERSITY = "getUniversity";
      String GET_ALL_UNIVERSITIES_BY_FILTER = "getFilteredUniversities";
}
