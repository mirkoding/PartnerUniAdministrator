package de.fhws.fiw.fds.PartnerUniAdministrator.server.api.states.universities;

import de.fhws.fiw.fds.PartnerUniAdministrator.server.api.models.University;
import de.fhws.fiw.fds.PartnerUniAdministrator.server.database.DaoFactory;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter.JerseyResponse;
import de.fhws.fiw.fds.sutton.server.api.services.ServiceContext;
import de.fhws.fiw.fds.sutton.server.api.states.post.AbstractPostState;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import jakarta.ws.rs.core.Response;

public class PostNewUniversity extends AbstractPostState<Response, University> {

      public PostNewUniversity(ServiceContext serviceContext, University universityModelToStore) {
            super(serviceContext, universityModelToStore);
            this.suttonResponse = new JerseyResponse<>();
      }

      @Override
      protected NoContentResult saveModel() {
            return DaoFactory.getInstance().getUniversityDAO().create(this.modelToStore);
      }

      @Override
      protected void defineTransitionLinks() {
      }
}
