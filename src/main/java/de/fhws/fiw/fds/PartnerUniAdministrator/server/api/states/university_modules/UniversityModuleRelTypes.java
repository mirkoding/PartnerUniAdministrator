package de.fhws.fiw.fds.PartnerUniAdministrator.server.api.states.university_modules;

public interface UniversityModuleRelTypes {
      String CREATE_AND_LINK_MODULE = "createModuleOfUniversity";
      String GET_ALL_LINKED_MODULES = "getAllModulesOfUniversity";
      String GET_ALL_LINKED_MODULES_BY_FILTER = "getAllModulesOfUniversityByFilter";
      String UPDATE_SINGLE_MODULE = "updateModuleOfUniversity";
      String DELETE_LINK_FROM_UNIVERSITY_TO_MODULE = "unlinkUniversityToModule";
      String GET_SINGLE_MODULE = "getModuleOfUniversity";
}
