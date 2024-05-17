package de.fhws.fiw.fds.PartnerUniAdministrator.server.database.inmemory;

import de.fhws.fiw.fds.PartnerUniAdministrator.server.api.models.University;
import de.fhws.fiw.fds.PartnerUniAdministrator.server.database.UniversityDAO;
import de.fhws.fiw.fds.sutton.server.database.SearchParameter;
import de.fhws.fiw.fds.sutton.server.database.inmemory.AbstractInMemoryStorage;
import de.fhws.fiw.fds.sutton.server.database.inmemory.InMemoryPaging;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;

import java.util.function.Predicate;

public class UniversityStorage extends AbstractInMemoryStorage<University> implements UniversityDAO {

      @Override
      public CollectionModelResult<University> readByNameOfUniversity(String name, SearchParameter searchParameter) {
            return InMemoryPaging.page(
                  this.readAllByPredicate(byNameOfUniversity(name), searchParameter),
                  searchParameter.getOffset(),
                  searchParameter.getSize());
      }

      public void add(long primaryId, University university) {
            this.storage.put(primaryId, university);
      }

      public void resetDatabase() {this.storage.clear();}

      private Predicate<University> byNameOfUniversity(String name) {
            return p -> p.getPartnerUniName().equals(name) || name.isEmpty();
      }
}
