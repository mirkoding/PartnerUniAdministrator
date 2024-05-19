package de.fhws.fiw.fds.PartnerUniAdministrator.server.api.states.universities;

import de.fhws.fiw.fds.PartnerUniAdministrator.server.api.models.University;
import de.fhws.fiw.fds.PartnerUniAdministrator.server.api.states.university_modules.UniversityModuleRelTypes;
import de.fhws.fiw.fds.PartnerUniAdministrator.server.api.states.university_modules.UniversityModuleURI;
import de.fhws.fiw.fds.PartnerUniAdministrator.server.database.DaoFactory;
import de.fhws.fiw.fds.sutton.server.api.caching.CachingUtils;
import de.fhws.fiw.fds.sutton.server.api.caching.EtagGenerator;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter.JerseyResponse;
import de.fhws.fiw.fds.sutton.server.api.services.ServiceContext;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetState;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class GetSingleUniversity extends AbstractGetState<Response, University> {

      public GetSingleUniversity(ServiceContext serviceContext, long requestedId) {
            super(serviceContext, requestedId);
            this.suttonResponse = new JerseyResponse<>();
      }

      @Override
      protected SingleModelResult<University> loadModel() {
            return DaoFactory.getInstance().getUniversityDAO().readById(this.requestedId);
      }

      @Override
      protected boolean clientKnowsCurrentModelState(AbstractModel modelFromDatabase) {
            return this.suttonRequest.clientKnowsCurrentModel(modelFromDatabase);
      }

      @Override
      protected void defineHttpCaching() {
            final String eTagOfModel = EtagGenerator.createEtag(this.requestedModel.getResult());
            this.suttonResponse.entityTag(eTagOfModel);
            this.suttonResponse.cacheControl(CachingUtils.create30SecondsPublicCaching());
      }

      @Override
      protected void defineTransitionLinks() {
            addLink(UniversityURI.REL_PATH_ID, UniversityRelTypes.UPDATE_SINGLE_UNIVERSITY, MediaType.APPLICATION_JSON, getAcceptRequestHeader());
            addLink(UniversityURI.REL_PATH_ID, UniversityRelTypes.UPDATE_SINGLE_UNIVERSITY, MediaType.APPLICATION_XML, getAcceptRequestHeader());
            addLink(UniversityURI.REL_PATH_ID, UniversityRelTypes.DELETE_SINGLE_UNIVERSITY, getAcceptRequestHeader());
            addLink(UniversityModuleURI.REL_PATH, UniversityModuleRelTypes.CREATE_AND_LINK_MODULE, MediaType.APPLICATION_JSON, getAcceptRequestHeader());
            addLink(UniversityModuleURI.REL_PATH, UniversityModuleRelTypes.CREATE_AND_LINK_MODULE, MediaType.APPLICATION_XML, getAcceptRequestHeader());
            addLink(UniversityModuleURI.REL_PATH, UniversityModuleRelTypes.GET_ALL_LINKED_MODULES, MediaType.APPLICATION_JSON, getAcceptRequestHeader());
            addLink(UniversityModuleURI.REL_PATH, UniversityModuleRelTypes.GET_ALL_LINKED_MODULES, MediaType.APPLICATION_XML, getAcceptRequestHeader());

            //TODO Filter Links nochmal überdenken, ob die so passen
            addLink(UniversityURI.REL_PATH + "?search={SEARCH}", UniversityRelTypes.GET_ALL_UNIVERSITIES_BY_FILTER, MediaType.APPLICATION_JSON, getAcceptRequestHeader());
            addLink(UniversityURI.REL_PATH + "?search={SEARCH}", UniversityRelTypes.GET_ALL_UNIVERSITIES_BY_FILTER, MediaType.APPLICATION_XML, getAcceptRequestHeader());
            addLink(UniversityURI.REL_PATH, UniversityRelTypes.GET_ALL_UNIVERSITIES, MediaType.APPLICATION_JSON, getAcceptRequestHeader());
            addLink(UniversityURI.REL_PATH, UniversityRelTypes.GET_ALL_UNIVERSITIES, MediaType.APPLICATION_XML, getAcceptRequestHeader());
      }
}
