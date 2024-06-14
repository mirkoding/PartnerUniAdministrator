package de.fhws.fiw.fds.PartnerUniAdministrator.server.api.queries;

import de.fhws.fiw.fds.PartnerUniAdministrator.server.api.models.University;
import de.fhws.fiw.fds.PartnerUniAdministrator.server.database.DaoFactory;
import de.fhws.fiw.fds.sutton.server.api.queries.AbstractQuery;
import de.fhws.fiw.fds.sutton.server.api.queries.PagingBehaviorUsingOffsetSize;
import de.fhws.fiw.fds.sutton.server.database.DatabaseException;
import de.fhws.fiw.fds.sutton.server.database.SearchParameter;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.ArrayList;

public class QueryAllUniversities<R> extends AbstractQuery<R, University> {

      private final String order;

      public QueryAllUniversities(String order, int offset, int size) {
            this.pagingBehavior = new PagingBehaviorUsingOffsetSize<>(offset, size);
            this.order = order;
      }

      @Override
      protected CollectionModelResult<University> doExecuteQuery(SearchParameter searchParameter) throws DatabaseException {
            if(order.isEmpty() || !order.equals("ascending") && !order.equals("descending")) {
                  return DaoFactory.getInstance().getUniversityDAO().readAllUniversities(searchParameter);
            }

            CollectionModelResult<University> result = DaoFactory.getInstance().getUniversityDAO().readAllUniversities(searchParameter);
            Stream<University> orderedResult;
            if(order.equals("ascending")) {
                  orderedResult = result.getResult().stream().sorted((uni1, uni2) -> uni1.getPartnerUniName().compareToIgnoreCase(uni2.getPartnerUniName()));
            }
            else {
                  orderedResult = result.getResult().stream().sorted((uni1, uni2) -> uni2.getPartnerUniName().compareToIgnoreCase(uni1.getPartnerUniName()));
            }
            return new CollectionModelResult<>(orderedResult.collect(Collectors.toCollection(ArrayList::new)));
      }


}
