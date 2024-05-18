package de.fhws.fiw.fds.PartnerUniAdministrator.server.api.states.university_modules;

import de.fhws.fiw.fds.PartnerUniAdministrator.server.api.models.Module;
import de.fhws.fiw.fds.PartnerUniAdministrator.server.api.queries.QueryByModuleName;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter.JerseyResponse;
import de.fhws.fiw.fds.sutton.server.api.services.ServiceContext;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetCollectionRelationState;
import jakarta.ws.rs.core.Response;


public class GetAllModulesFromUniversity extends AbstractGetCollectionRelationState<Response, Module> {

      public GetAllModulesFromUniversity(ServiceContext serviceContext, long primaryId, QueryByModuleName query) {
            super(serviceContext, primaryId, query);
            this.suttonResponse = new JerseyResponse<>();
      }

      @Override
      protected void defineTransitionLinks() {

      }

}
