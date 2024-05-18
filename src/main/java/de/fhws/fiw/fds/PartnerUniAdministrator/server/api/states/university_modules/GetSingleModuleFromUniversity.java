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
import jakarta.ws.rs.core.Response;

public class GetSingleModuleFromUniversity extends AbstractGetRelationState<Response, Module> {

      public GetSingleModuleFromUniversity(ServiceContext serviceContext, long primaryId, long requestedId) {
            super(serviceContext, primaryId, requestedId);
            this.suttonResponse = new JerseyResponse<>();
      }

      @Override
      protected void defineTransitionLinks() {

      }

      @Override
      protected SingleModelResult<Module> loadModel() {
            SingleModelResult<Module> module = DaoFactory.getInstance().getModuleDAO().readById(this.requestedId);
            if(isModuleLinkedToThisUniversity()) {
                  module.getResult().setPrimaryId(this.primaryId);
            }
            return module;
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
