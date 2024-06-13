package de.fhws.fiw.fds.PartnerUniAdministrator.server.api.states.dispatcher;

import de.fhws.fiw.fds.PartnerUniAdministrator.server.api.states.universities.UniversityRelTypes;
import de.fhws.fiw.fds.PartnerUniAdministrator.server.api.states.universities.UniversityURI;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter.JerseyResponse;
import de.fhws.fiw.fds.sutton.server.api.services.ServiceContext;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetDispatcherState;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class GetDispatcher extends AbstractGetDispatcherState<Response> {

      public GetDispatcher(ServiceContext serviceContext) {
            super(serviceContext);
            this.suttonResponse = new JerseyResponse<>();
      }

      @Override
      protected void defineTransitionLinks() {
            addLink(UniversityURI.REL_PATH , UniversityRelTypes.GET_ALL_UNIVERSITIES, MediaType.APPLICATION_JSON, getAcceptRequestHeader());
            addLink(UniversityURI.REL_PATH , UniversityRelTypes.GET_ALL_UNIVERSITIES_BY_FILTER, MediaType.APPLICATION_JSON, getAcceptRequestHeader());
            addLink(UniversityURI.REL_PATH, UniversityRelTypes.CREATE_UNIVERSITY, MediaType.APPLICATION_JSON, getAcceptRequestHeader());
            addLink(UniversityURI.REL_PATH + "initializeDatabase", "InitializeDatabase", MediaType.APPLICATION_JSON, getAcceptRequestHeader());
      }
}
