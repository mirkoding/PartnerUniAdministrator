package de.fhws.fiw.fds.PartnerUniAdministrator.server.api.states.university_modules;

import de.fhws.fiw.fds.PartnerUniAdministrator.server.api.models.Module;
import de.fhws.fiw.fds.PartnerUniAdministrator.server.database.DaoFactory;
import de.fhws.fiw.fds.sutton.server.api.caching.CachingUtils;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter.JerseyResponse;
import de.fhws.fiw.fds.sutton.server.api.services.ServiceContext;
import de.fhws.fiw.fds.sutton.server.api.states.put.AbstractPutRelationState;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;
import jakarta.ws.rs.core.Response;

public class PutSingleModuleFromUniversity extends AbstractPutRelationState<Response, Module> {

      public PutSingleModuleFromUniversity(ServiceContext serviceContext, long primaryId, long requestedId, Module moduleModelToUpdate) {
            super(serviceContext, primaryId, requestedId, moduleModelToUpdate);
            this.suttonResponse = new JerseyResponse<>();
      }

      @Override
      protected void defineTransitionLinks() {

      }

      @Override
      protected SingleModelResult<Module> loadModel() {
            return DaoFactory.getInstance().getModuleDAO().readById(this.requestedId);
      }

      @Override
      protected NoContentResult updateModel() {
            return DaoFactory.getInstance().getUniversityModuleDAO().update(this.primaryId, this.modelToUpdate);
      }

      @Override
      protected boolean clientDoesNotKnowCurrentModelState(AbstractModel modelFromDatabase) {
            return this.suttonRequest.clientKnowsCurrentModel(modelFromDatabase);
      }

      @Override
      protected void defineHttpCaching() {
            this.suttonResponse.cacheControl(CachingUtils.create30SecondsPublicCaching());
      }
}
