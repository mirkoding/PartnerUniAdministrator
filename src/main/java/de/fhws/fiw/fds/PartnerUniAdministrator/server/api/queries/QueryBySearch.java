package de.fhws.fiw.fds.PartnerUniAdministrator.server.api.queries;

import de.fhws.fiw.fds.PartnerUniAdministrator.server.api.models.University;
import de.fhws.fiw.fds.PartnerUniAdministrator.server.database.DaoFactory;
import de.fhws.fiw.fds.sutton.server.api.queries.AbstractQuery;
import de.fhws.fiw.fds.sutton.server.api.queries.PagingBehaviorUsingOffsetSize;
import de.fhws.fiw.fds.sutton.server.database.DatabaseException;
import de.fhws.fiw.fds.sutton.server.database.SearchParameter;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;

public class QueryBySearch<R> extends AbstractQuery<R, University> {

      private String search;

      public QueryBySearch(String search, int offset, int size) {
            this.search = search;
            this.pagingBehavior = new PagingBehaviorUsingOffsetSize<>(offset, size);
      }

      public String getSearch() {
            return search;
      }

      public void setSearch(String search) {
            this.search = search;
      }

      @Override
      protected CollectionModelResult<University> doExecuteQuery(SearchParameter searchParameter) throws DatabaseException {
            return DaoFactory.getInstance().getUniversityDAO().readByNameOfUniversity(this.search, searchParameter);
      }
}
