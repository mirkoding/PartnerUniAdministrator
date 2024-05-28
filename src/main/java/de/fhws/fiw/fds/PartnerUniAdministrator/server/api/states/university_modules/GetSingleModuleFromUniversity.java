package de.fhws.fiw.fds.PartnerUniAdministrator.server.api.states.university_modules;

import de.fhws.fiw.fds.PartnerUniAdministrator.server.api.models.Module;
import de.fhws.fiw.fds.PartnerUniAdministrator.server.database.DaoFactory;
import de.fhws.fiw.fds.sutton.server.api.caching.CachingUtils;
import de.fhws.fiw.fds.sutton.server.api.caching.EtagGenerator;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter.JerseyResponse;
import de.fhws.fiw.fds.sutton.server.api.services.ServiceContext;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetRelationState;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class GetSingleModuleFromUniversity extends AbstractGetRelationState<Response, Module> {

      public GetSingleModuleFromUniversity(ServiceContext serviceContext, long primaryId, long requestedId) {
            super(serviceContext, primaryId, requestedId);
            this.suttonResponse = new JerseyResponse<>();
      }

      @Override
      protected void defineTransitionLinks() {
            addLink(UniversityModuleURI.REL_PATH, UniversityModuleRelTypes.GET_ALL_LINKED_MODULES, MediaType.APPLICATION_JSON, getAcceptRequestHeader());
            addLink(UniversityModuleURI.REL_PATH, UniversityModuleRelTypes.GET_ALL_LINKED_MODULES, MediaType.APPLICATION_XML, getAcceptRequestHeader());
            addLink(UniversityModuleURI.REL_PATH_ID, UniversityModuleRelTypes.UPDATE_SINGLE_MODULE, MediaType.APPLICATION_JSON, getAcceptRequestHeader());
            addLink(UniversityModuleURI.REL_PATH_ID, UniversityModuleRelTypes.UPDATE_SINGLE_MODULE, MediaType.APPLICATION_XML, getAcceptRequestHeader());
            addLink(UniversityModuleURI.REL_PATH_ID, UniversityModuleRelTypes.DELETE_LINK_FROM_UNIVERSITY_TO_MODULE, getAcceptRequestHeader());
      }

      @Override
      protected SingleModelResult<Module> loadModel() {
            SingleModelResult<Module> module = DaoFactory.getInstance().getModuleDAO().readById(this.requestedId);
            System.out.println(module.getResult());
            if(isModuleLinkedToThisUniversity()) {
                  module.getResult().setPrimaryId(this.primaryId);
                  return module;
            }
            return new SingleModelResult<>();
      }

      @Override
      protected boolean clientKnowsCurrentModelState(AbstractModel modelFromDatabase) {
            return this.suttonRequest.clientKnowsCurrentModel(modelFromDatabase);
      }

      @Override
      protected void defineHttpCaching() {
            final String modelFromDBEtag = EtagGenerator.createEtag(this.requestedModel.getResult());
            this.suttonResponse.entityTag(modelFromDBEtag);
            this.suttonResponse.cacheControl(CachingUtils.create30SecondsPublicCaching());
      }

      private boolean isModuleLinkedToThisUniversity() {
            return !DaoFactory.getInstance()
                  .getUniversityModuleDAO()
                  .readById(this.primaryId, this.requestedId)
                  .isEmpty();
      }
}
