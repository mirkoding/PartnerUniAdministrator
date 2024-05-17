package de.fhws.fiw.fds.PartnerUniAdministrator.server.database;

import de.fhws.fiw.fds.PartnerUniAdministrator.server.database.inmemory.ModuleStorage;
import de.fhws.fiw.fds.PartnerUniAdministrator.server.database.inmemory.UniversityModuleStorage;
import de.fhws.fiw.fds.PartnerUniAdministrator.server.database.inmemory.UniversityStorage;

public class DaoFactory {

      private static DaoFactory INSTANCE;

      public static DaoFactory getInstance() {
            if (INSTANCE == null) {
                  INSTANCE = new DaoFactory();
            }
            return INSTANCE;
      }

      private final UniversityDAO universityDAO;
      private final ModuleDAO moduleDAO;
      private final UniversityModuleDAO universityModuleDAO;

      private DaoFactory() {
            this.universityDAO = new UniversityStorage();
            this.moduleDAO = new ModuleStorage();
            this.universityModuleDAO = new UniversityModuleStorage(this.moduleDAO);
      }

      public UniversityDAO getUniversityDAO() {
            return this.universityDAO;
      }

      public ModuleDAO getModuleDAO() {
            return this.moduleDAO;
      }

      public UniversityModuleDAO getUniversityModuleDAO() {
            return this.universityModuleDAO;
      }
}
