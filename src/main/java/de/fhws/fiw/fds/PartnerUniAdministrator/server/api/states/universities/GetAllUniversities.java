package de.fhws.fiw.fds.PartnerUniAdministrator.server.api.states.universities;

import de.fhws.fiw.fds.PartnerUniAdministrator.server.api.models.University;
import de.fhws.fiw.fds.sutton.server.api.queries.AbstractQuery;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter.JerseyResponse;
import de.fhws.fiw.fds.sutton.server.api.services.ServiceContext;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetCollectionState;
import jakarta.ws.rs.core.Response;

public class GetAllUniversities extends AbstractGetCollectionState<Response, University> {

      private final String search;

      public GetAllUniversities(ServiceContext serviceContext, AbstractQuery<Response, University> query, String search) {
            super(serviceContext, query);
            this.suttonResponse = new JerseyResponse<>();
            this.search = search;
      }

      @Override
      protected void defineTransitionLinks() {
            addLink(UniversityURI.REL_PATH, UniversityRelTypes.CREATE_UNIVERSITY, getAcceptRequestHeader());

            // Get page of results of search
            addLink(UniversityURI.REL_PATH + "?search={SEARCH}",
                  UniversityRelTypes.GET_PAGE_OF_UNIVERSITIES_BY_FILTER, getAcceptRequestHeader());
            addLink(UniversityURI.REL_PATH + "?search={SEARCH}&order=ascending",
                  UniversityRelTypes.GET_PAGE_OF_UNIVERSITIES_BY_FILTER_ASCENDING, getAcceptRequestHeader());
            addLink(UniversityURI.REL_PATH + "?search={SEARCH}&order=descending",
                  UniversityRelTypes.GET_PAGE_OF_UNIVERSITIES_BY_FILTER_DESCENDING, getAcceptRequestHeader());

            if(this.result.getResult().size() < this.result.getTotalNumberOfResult()) {
                  // Get all results
                  addLink(UniversityURI.REL_PATH + "?offset=0&size=" + this.result.getTotalNumberOfResult(),
                        UniversityRelTypes.GET_ALL_UNIVERSITIES, getAcceptRequestHeader());
                  addLink(UniversityURI.REL_PATH + "?offset=0&size=" + this.result.getTotalNumberOfResult() + "&order=ascending",
                        UniversityRelTypes.GET_ALL_UNIVERSITIES_ASCENDING, getAcceptRequestHeader());
                  addLink(UniversityURI.REL_PATH + "?offset=0&size=" + this.result.getTotalNumberOfResult() + "&order=descending",
                        UniversityRelTypes.GET_ALL_UNIVERSITIES_DESCENDING, getAcceptRequestHeader());

                  // Get all results of search
                  if(query.getClass().toString().contains("QueryBySearch")) {
                        addLink(UniversityURI.REL_PATH + "?search=" + search + "&offset=0&size=" + this.result.getTotalNumberOfResult(),
                              UniversityRelTypes.GET_ALL_UNIVERSITIES_BY_FILTER, getAcceptRequestHeader());
                        addLink(UniversityURI.REL_PATH + "?search=" + search + "&offset=0&size=" + this.result.getTotalNumberOfResult() + "&order=ascending",
                              UniversityRelTypes.GET_ALL_UNIVERSITIES_BY_FILTER_ASCENDING, getAcceptRequestHeader());
                        addLink(UniversityURI.REL_PATH + "?search=" + search + "&offset=0&size=" + this.result.getTotalNumberOfResult() + "&order=descending",
                              UniversityRelTypes.GET_ALL_UNIVERSITIES_BY_FILTER_DESCENDING, getAcceptRequestHeader());
                  }
            }
      }
}
