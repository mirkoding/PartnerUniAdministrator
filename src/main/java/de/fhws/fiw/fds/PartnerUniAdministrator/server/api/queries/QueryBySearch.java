package de.fhws.fiw.fds.PartnerUniAdministrator.server.api.queries;

import de.fhws.fiw.fds.PartnerUniAdministrator.server.api.models.University;
import de.fhws.fiw.fds.PartnerUniAdministrator.server.database.DaoFactory;
import de.fhws.fiw.fds.sutton.server.api.queries.AbstractQuery;
import de.fhws.fiw.fds.sutton.server.api.queries.PagingBehaviorUsingOffsetSize;
import de.fhws.fiw.fds.sutton.server.database.DatabaseException;
import de.fhws.fiw.fds.sutton.server.database.SearchParameter;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;


public class QueryBySearch<R> extends AbstractQuery<R, University> {

      private final String search;
      private final String order;

      public QueryBySearch(String search, String order, int offset, int size) {
            this.search = search;
            this.order = order;
            this.pagingBehavior = new PagingBehaviorUsingOffsetSize<>(offset, size);
      }

      @Override
      protected CollectionModelResult<University> doExecuteQuery(SearchParameter searchParameter) throws DatabaseException {
            return DaoFactory.getInstance().getUniversityDAO().readByNameOfUniversity(search, searchParameter, order);
      }
}
