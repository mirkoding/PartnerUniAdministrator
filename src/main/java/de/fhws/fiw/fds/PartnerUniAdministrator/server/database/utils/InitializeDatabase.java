package de.fhws.fiw.fds.PartnerUniAdministrator.server.database.utils;

import de.fhws.fiw.fds.PartnerUniAdministrator.server.database.DaoFactory;

public class InitializeDatabase {

      public void initializeDBWithRelations() {
            DaoFactory.getInstance().getUniversityModuleDAO().initializeDatabase();
      }
}
