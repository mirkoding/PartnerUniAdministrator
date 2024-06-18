package de.fhws.fiw.fds.PartnerUniAdministrator.server.api.queries;

import de.fhws.fiw.fds.PartnerUniAdministrator.server.api.models.University;
import de.fhws.fiw.fds.PartnerUniAdministrator.server.database.DaoFactory;
import de.fhws.fiw.fds.sutton.server.api.queries.AbstractQuery;
import de.fhws.fiw.fds.sutton.server.api.queries.PagingBehaviorUsingOffsetSize;
import de.fhws.fiw.fds.sutton.server.database.DatabaseException;
import de.fhws.fiw.fds.sutton.server.database.SearchParameter;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;


public class QueryAllUniversities<R> extends AbstractQuery<R, University> {

      private final String order;

      public QueryAllUniversities(String order, int offset, int size) {
            this.pagingBehavior = new PagingBehaviorUsingOffsetSize<>(offset, size);
            this.order = order;
      }

      @Override
      protected CollectionModelResult<University> doExecuteQuery(SearchParameter searchParameter) throws DatabaseException {
            return DaoFactory.getInstance().getUniversityDAO().readAllUniversities(searchParameter, order);
      }


}
