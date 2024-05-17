package de.fhws.fiw.fds.PartnerUniAdministrator.server.database;

import de.fhws.fiw.fds.PartnerUniAdministrator.server.api.models.University;
import de.fhws.fiw.fds.sutton.server.database.IDatabaseAccessObject;
import de.fhws.fiw.fds.sutton.server.database.SearchParameter;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;

public interface UniversityDAO extends IDatabaseAccessObject<University> {
      CollectionModelResult<University> readByNameOfUniversity(String search, SearchParameter searchParameter);
      void resetDatabase();
}
