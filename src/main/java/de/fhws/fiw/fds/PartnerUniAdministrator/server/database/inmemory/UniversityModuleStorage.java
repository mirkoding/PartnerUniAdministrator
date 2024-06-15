package de.fhws.fiw.fds.PartnerUniAdministrator.server.database.inmemory;

import de.fhws.fiw.fds.PartnerUniAdministrator.server.api.models.Module;
import de.fhws.fiw.fds.PartnerUniAdministrator.server.database.ModuleDAO;
import de.fhws.fiw.fds.PartnerUniAdministrator.server.database.UniversityModuleDAO;
import de.fhws.fiw.fds.sutton.server.database.IDatabaseAccessObject;
import de.fhws.fiw.fds.sutton.server.database.SearchParameter;
import de.fhws.fiw.fds.sutton.server.database.inmemory.AbstractInMemoryRelationStorage;
import de.fhws.fiw.fds.sutton.server.database.inmemory.InMemoryPaging;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;

public class UniversityModuleStorage extends AbstractInMemoryRelationStorage<Module> implements UniversityModuleDAO {

      final private ModuleDAO moduleStorage;

      public UniversityModuleStorage(ModuleDAO moduleDAO) {this.moduleStorage = moduleDAO;}

      @Override
      protected IDatabaseAccessObject<Module> getSecondaryStorage() {return this.moduleStorage;}

      @Override
      public CollectionModelResult<Module> readByModuleName(long primaryId, String moduleName, SearchParameter searchParameter) {
            return InMemoryPaging.page(
                  this.readAllByPredicate(primaryId, (p) -> moduleName.isEmpty() || p.getModuleName().equals(moduleName)),
                  searchParameter.getOffset(),
                  searchParameter.getSize()
            );
      }

      @Override
      public CollectionModelResult<Module> readAllLinked(long primaryId, SearchParameter searchParameter) {
            return InMemoryPaging.page(
                  this.readAllLinkedByPredicate(primaryId, (p) -> true),
                  searchParameter.getOffset(),
                  searchParameter.getSize()
            );
      }

      @Override
      public void resetDatabase() {
            moduleStorage.resetDatabase();
      }

      @Override
      public void initializeDatabase() {}
}
