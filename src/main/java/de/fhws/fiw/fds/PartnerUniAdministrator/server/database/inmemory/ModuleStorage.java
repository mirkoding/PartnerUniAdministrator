package de.fhws.fiw.fds.PartnerUniAdministrator.server.database.inmemory;

import de.fhws.fiw.fds.PartnerUniAdministrator.server.api.models.Module;
import de.fhws.fiw.fds.PartnerUniAdministrator.server.database.ModuleDAO;
import de.fhws.fiw.fds.sutton.server.database.inmemory.AbstractInMemoryStorage;

public class ModuleStorage extends AbstractInMemoryStorage<Module> implements ModuleDAO {
      public void resetDatabase() {
            deleteAll();
      }
}
