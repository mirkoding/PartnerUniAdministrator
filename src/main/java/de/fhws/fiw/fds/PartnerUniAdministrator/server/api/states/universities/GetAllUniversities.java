package de.fhws.fiw.fds.PartnerUniAdministrator.server.api.states.universities;

import de.fhws.fiw.fds.PartnerUniAdministrator.server.api.models.University;
import de.fhws.fiw.fds.sutton.server.api.queries.AbstractQuery;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter.JerseyResponse;
import de.fhws.fiw.fds.sutton.server.api.services.ServiceContext;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetCollectionState;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class GetAllUniversities extends AbstractGetCollectionState<Response, University> {

      public GetAllUniversities(ServiceContext serviceContext, AbstractQuery<Response, University> query) {
            super(serviceContext, query);
            this.suttonResponse = new JerseyResponse<>();
      }

      @Override
      protected void defineTransitionLinks() {
            addLink(UniversityURI.REL_PATH, UniversityRelTypes.CREATE_UNIVERSITY, getAcceptRequestHeader());
            addLink(UniversityURI.REL_PATH + "?search={SEARCH}&offset={OFFSET}&size={SIZE}",
                  UniversityRelTypes.GET_ALL_UNIVERSITIES_BY_FILTER, MediaType.APPLICATION_JSON, getAcceptRequestHeader());
      }
}
