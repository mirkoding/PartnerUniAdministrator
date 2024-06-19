package de.fhws.fiw.fds.PartnerUniAdministrator.server.api.states.universities;

public interface UniversityRelTypes {
      String CREATE_UNIVERSITY = "createUniversity";
      String GET_ALL_UNIVERSITIES = "getAllUniversities";
      String GET_ALL_UNIVERSITIES_ASCENDING = "getAllUniversitiesAscending";
      String GET_ALL_UNIVERSITIES_DESCENDING = "getAllUniversitiesDescending";
      String UPDATE_SINGLE_UNIVERSITY = "updateUniversity";
      String DELETE_SINGLE_UNIVERSITY = "deleteUniversity";
      String GET_SINGLE_UNIVERSITY = "getUniversity";
      String GET_ALL_UNIVERSITIES_BY_FILTER = "getFilteredUniversities";
      String GET_ALL_UNIVERSITIES_BY_FILTER_ASCENDING = "getFilteredUniversitiesAscending";
      String GET_ALL_UNIVERSITIES_BY_FILTER_DESCENDING = "getFilteredUniversitiesDescending";

      String GET_PAGE_OF_UNIVERSITIES_BY_FILTER = "getFilteredPageOfUniversities";
      String GET_PAGE_OF_UNIVERSITIES_BY_FILTER_ASCENDING = "getFilteredPageOfUniversitiesAscending";
      String GET_PAGE_OF_UNIVERSITIES_BY_FILTER_DESCENDING = "getFilteredPageOfUniversitiesDescending";
}
