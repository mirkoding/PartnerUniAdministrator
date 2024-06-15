package de.fhws.fiw.fds.PartnerUniAdministrator.server.api.states.universities;

import de.fhws.fiw.fds.PartnerUniAdministrator.server.api.models.University;
import de.fhws.fiw.fds.PartnerUniAdministrator.server.database.DaoFactory;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter.JerseyResponse;
import de.fhws.fiw.fds.sutton.server.api.services.ServiceContext;
import de.fhws.fiw.fds.sutton.server.api.states.delete.AbstractDeleteState;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class DeleteSingleUniversity extends AbstractDeleteState<Response, University> {

      public DeleteSingleUniversity(ServiceContext serviceContext, final long modelIdToDelete) {
            super(serviceContext, modelIdToDelete);
            this.suttonResponse = new JerseyResponse<>();
      }

      @Override
      protected SingleModelResult<University> loadModel() {
            return DaoFactory.getInstance().getUniversityDAO().readById(this.modelIdToDelete);
      }

      @Override
      protected NoContentResult deleteModel() {
            return DaoFactory.getInstance().getUniversityDAO().delete(this.modelIdToDelete);
      }

      @Override
      protected void defineTransitionLinks() {
            addLink(UniversityURI.REL_PATH, UniversityRelTypes.GET_ALL_UNIVERSITIES, getAcceptRequestHeader());
      }


}
