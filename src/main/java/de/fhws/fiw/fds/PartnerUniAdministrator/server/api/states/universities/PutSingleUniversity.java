package de.fhws.fiw.fds.PartnerUniAdministrator.server.api.states.universities;

import de.fhws.fiw.fds.PartnerUniAdministrator.server.api.models.University;
import de.fhws.fiw.fds.PartnerUniAdministrator.server.database.DaoFactory;
import de.fhws.fiw.fds.sutton.server.api.caching.CachingUtils;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter.JerseyResponse;
import de.fhws.fiw.fds.sutton.server.api.services.ServiceContext;
import de.fhws.fiw.fds.sutton.server.api.states.put.AbstractPutState;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class PutSingleUniversity extends AbstractPutState<Response, University> {

      public PutSingleUniversity(ServiceContext serviceContext, long requestedId, University modelToUpdate) {
            super(serviceContext, requestedId, modelToUpdate);
            this.suttonResponse = new JerseyResponse<>();
      }

      @Override
      protected SingleModelResult<University> loadModel() {
            return DaoFactory.getInstance().getUniversityDAO().readById(this.modelToUpdate.getId());
      }

      @Override
      protected NoContentResult updateModel() {
            return DaoFactory.getInstance().getUniversityDAO().update(this.modelToUpdate);
      }

      @Override
      protected boolean clientDoesNotKnowCurrentModelState(AbstractModel modelFromDatabase) {
            return this.suttonRequest.clientKnowsCurrentModel(modelFromDatabase);
      }

      @Override
      protected void defineHttpCaching() {
            this.suttonResponse.cacheControl(CachingUtils.create30SecondsPublicCaching());
      }

      @Override
      protected void defineTransitionLinks() {
            addLink(UniversityURI.REL_PATH_ID, UniversityRelTypes.GET_SINGLE_UNIVERSITY, MediaType.APPLICATION_JSON, getAcceptRequestHeader(), this.modelToUpdate.getId());
      }
}
