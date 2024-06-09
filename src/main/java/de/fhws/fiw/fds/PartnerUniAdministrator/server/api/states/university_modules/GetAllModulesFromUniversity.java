package de.fhws.fiw.fds.PartnerUniAdministrator.server.api.states.university_modules;

import de.fhws.fiw.fds.PartnerUniAdministrator.server.api.models.Module;
import de.fhws.fiw.fds.PartnerUniAdministrator.server.api.queries.QueryByModuleName;
import de.fhws.fiw.fds.PartnerUniAdministrator.server.api.states.universities.UniversityRelTypes;
import de.fhws.fiw.fds.PartnerUniAdministrator.server.api.states.universities.UniversityURI;
import de.fhws.fiw.fds.sutton.server.api.queries.AbstractRelationQuery;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter.JerseyResponse;
import de.fhws.fiw.fds.sutton.server.api.services.ServiceContext;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetCollectionRelationState;
import de.fhws.fiw.fds.suttondemo.server.api.models.Location;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


public class GetAllModulesFromUniversity extends AbstractGetCollectionRelationState<Response, Module> {

      public GetAllModulesFromUniversity(ServiceContext serviceContext, long primaryId, AbstractRelationQuery<Response, Module> query) {
            super(serviceContext, primaryId, query);
            this.suttonResponse = new JerseyResponse<>();
      }

      @Override
      protected void defineTransitionLinks() {
            addLink(UniversityModuleURI.REL_PATH, UniversityModuleRelTypes.GET_ALL_LINKED_MODULES_BY_FILTER, MediaType.APPLICATION_JSON, getAcceptRequestHeader());
            addLink(UniversityModuleURI.REL_PATH_ID, UniversityModuleRelTypes.GET_SINGLE_MODULE, MediaType.APPLICATION_JSON, getAcceptRequestHeader());
            addLink(UniversityModuleURI.REL_PATH, UniversityModuleRelTypes.CREATE_AND_LINK_MODULE, MediaType.APPLICATION_JSON, getAcceptRequestHeader());
            addLink(UniversityURI.REL_PATH_ID, UniversityRelTypes.GET_SINGLE_UNIVERSITY, MediaType.APPLICATION_JSON, getAcceptRequestHeader());
      }

}
