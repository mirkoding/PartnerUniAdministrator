package de.fhws.fiw.fds.PartnerUniAdministrator.server.database.utils;

import de.fhws.fiw.fds.PartnerUniAdministrator.server.database.DaoFactory;

public class ResetDatabase {

      public static void resetAll() {
            DaoFactory.getInstance().getUniversityDAO().resetDatabase();
      }
}
