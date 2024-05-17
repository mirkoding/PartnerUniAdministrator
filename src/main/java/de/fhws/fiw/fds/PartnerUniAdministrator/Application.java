package de.fhws.fiw.fds.PartnerUniAdministrator;

import de.fhws.fiw.fds.sutton.server.api.AbstractJerseyApplication;
import jakarta.ws.rs.ApplicationPath;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("api")
public class Application extends AbstractJerseyApplication {

      @Override
      protected Set<Class<?>> getServiceClasses() {
            final Set<Class<?>> returnValue = new HashSet<>();

            //TODO die ganzen Serviceklassen adden.

            return returnValue;
      }

}
