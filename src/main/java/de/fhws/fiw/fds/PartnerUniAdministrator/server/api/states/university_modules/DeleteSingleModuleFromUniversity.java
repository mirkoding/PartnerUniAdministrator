package de.fhws.fiw.fds.PartnerUniAdministrator.server.api.states.university_modules;

import de.fhws.fiw.fds.PartnerUniAdministrator.server.api.models.Module;
import de.fhws.fiw.fds.PartnerUniAdministrator.server.database.DaoFactory;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter.JerseyResponse;
import de.fhws.fiw.fds.sutton.server.api.services.ServiceContext;
import de.fhws.fiw.fds.sutton.server.api.states.delete.AbstractDeleteRelationState;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class DeleteSingleModuleFromUniversity extends AbstractDeleteRelationState<Response, Module> {

      public DeleteSingleModuleFromUniversity(ServiceContext serviceContext, long modelIdToDelete, long primaryId) {
            super(serviceContext, modelIdToDelete, primaryId);
            this.suttonResponse = new JerseyResponse<>();
      }

      @Override
      protected void defineTransitionLinks() {
            addLink(UniversityModuleURI.REL_PATH, UniversityModuleRelTypes.GET_ALL_LINKED_MODULES, MediaType.APPLICATION_JSON, getAcceptRequestHeader());
      }

      @Override
      protected SingleModelResult<Module> loadModel() {
            return DaoFactory.getInstance().getUniversityModuleDAO().readById(this.primaryId, this.modelIdToDelete);
      }

      @Override
      protected NoContentResult deleteModel() {
            return DaoFactory.getInstance().getUniversityModuleDAO().deleteRelation(this.primaryId, this.modelIdToDelete);
      }
}
