package de.fhws.fiw.fds.PartnerUniAdministrator.server.api.queries;

import de.fhws.fiw.fds.PartnerUniAdministrator.server.api.models.University;
import de.fhws.fiw.fds.PartnerUniAdministrator.server.database.DaoFactory;
import de.fhws.fiw.fds.sutton.server.api.queries.AbstractQuery;
import de.fhws.fiw.fds.sutton.server.api.queries.PagingBehaviorUsingOffsetSize;
import de.fhws.fiw.fds.sutton.server.database.DatabaseException;
import de.fhws.fiw.fds.sutton.server.database.SearchParameter;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QueryBySearch<R> extends AbstractQuery<R, University> {

      private String search;
      private final String order;

      public QueryBySearch(String search, String order, int offset, int size) {
            this.search = search;
            this.order = order;
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
            if(order.isEmpty() || !order.equals("descending") && !order.equals("ascending")) {
                  return DaoFactory.getInstance().getUniversityDAO().readByNameOfUniversity(this.search, searchParameter);
            }

            CollectionModelResult<University> test = DaoFactory.getInstance().getUniversityDAO().readByNameOfUniversity(this.search, searchParameter);
            Stream<University> result;
            if(order.equals("descending"))
                  result = test.getResult().stream().sorted((uni1, uni2) -> uni1.getPartnerUniName().compareToIgnoreCase(uni2.getPartnerUniName()));
            else // when order is ascending
                  result = test.getResult().stream().sorted((uni1, uni2) -> uni2.getPartnerUniName().compareToIgnoreCase(uni1.getPartnerUniName()));
            return new CollectionModelResult<>(result.collect(Collectors.toCollection(ArrayList::new)));
      }
}
