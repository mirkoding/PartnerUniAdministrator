package de.fhws.fiw.fds.PartnerUniAdministrator.server.api.queries;

import de.fhws.fiw.fds.PartnerUniAdministrator.server.api.models.Module;
import de.fhws.fiw.fds.PartnerUniAdministrator.server.database.DaoFactory;
import de.fhws.fiw.fds.sutton.server.api.queries.AbstractRelationQuery;
import de.fhws.fiw.fds.sutton.server.api.queries.PagingBehaviorUsingOffsetSize;
import de.fhws.fiw.fds.sutton.server.database.SearchParameter;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;
import jakarta.ws.rs.core.Response;

public class QueryByModuleName extends AbstractRelationQuery<Response, Module> {

      private String moduleName;

      public QueryByModuleName(long primaryId, String moduleName, int offset, int size) {
            super(primaryId);
            this.moduleName = moduleName;
            this.pagingBehavior = new PagingBehaviorUsingOffsetSize<>(offset, size);
      }

      public String getModuleName() {
            return moduleName;
      }

      public void setModuleName(String moduleName) {
            this.moduleName = moduleName;
      }

      @Override
      protected CollectionModelResult<Module> doExecuteQuery(SearchParameter searchParameter) {
            if(!moduleName.isEmpty())
                  return DaoFactory.getInstance().getUniversityModuleDAO().readByModuleName(this.primaryId, this.moduleName, searchParameter);
            return DaoFactory.getInstance().getUniversityModuleDAO().readAllLinked(this.primaryId);
      }
}
