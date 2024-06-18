package de.fhws.fiw.fds.PartnerUniAdministrator.server.database.inmemory;

import de.fhws.fiw.fds.PartnerUniAdministrator.server.api.models.University;
import de.fhws.fiw.fds.PartnerUniAdministrator.server.database.UniversityDAO;
import de.fhws.fiw.fds.sutton.server.database.DatabaseException;
import de.fhws.fiw.fds.sutton.server.database.SearchParameter;
import de.fhws.fiw.fds.sutton.server.database.inmemory.AbstractInMemoryStorage;
import de.fhws.fiw.fds.sutton.server.database.inmemory.InMemoryPaging;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class UniversityStorage extends AbstractInMemoryStorage<University> implements UniversityDAO {

      @Override
      public CollectionModelResult<University> readByNameOfUniversity(String name, SearchParameter searchParameter, String order) throws DatabaseException {
            if(order.isEmpty()) {
                  return this.readAllByPredicate(
                        byNameOfUniversity(name), searchParameter);
            }
            else {
                  return orderResult(this.readAllByPredicate(byNameOfUniversity(name), searchParameter), order);
            }
      }

      @Override
      public CollectionModelResult<University> readAllUniversities(SearchParameter searchParameter, String order) throws DatabaseException {
            if(order.isEmpty()) {
                  return InMemoryPaging.page(
                        this.readAll(searchParameter),
                        searchParameter.getOffset(),
                        searchParameter.getSize()
                  );
            }
            else {
                  return orderResult(this.readAll(searchParameter), order);
            }
      }

      private CollectionModelResult<University> orderResult(CollectionModelResult<University> collectionModelResultToOrder, String order) throws DatabaseException {
            int totalNumberOfResults = collectionModelResultToOrder.getTotalNumberOfResult();
            Comparator<University> comparator;

            if(order.equals("ascending")) {
                  comparator = Comparator.comparing(University::getPartnerUniName);

            }
            else if(order.equals("descending")){
                  comparator = (uni1, uni2) -> uni2.getPartnerUniName().compareTo(uni1.getPartnerUniName());
            }
            else throw new DatabaseException("Invalid order: " + order);


            CollectionModelResult<University> toReturn = new CollectionModelResult<>(
                  collectionModelResultToOrder
                        .getResult()
                        .stream()
                        .sorted(comparator)
                        .collect(Collectors.toCollection(LinkedList::new)));

            toReturn.setTotalNumberOfResult(totalNumberOfResults);
            return toReturn;
      }

      public void resetDatabase() {
            deleteAll();
      }

      public boolean doesUniversityExist(long id) {
            return this.storage.containsKey(id);
      }

      private Predicate<University> byNameOfUniversity(String name) {
            return p -> p.getPartnerUniName().equals(name) || name.isEmpty() || p.getPartnerUniName().toLowerCase().contains(name.toLowerCase());
      }
}
