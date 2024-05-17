package de.fhws.fiw.fds.PartnerUniAdministrator.client.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.fhws.fiw.fds.sutton.client.converters.ClientLinkJsonConverter;
import de.fhws.fiw.fds.sutton.client.model.AbstractClientModel;
import de.fhws.fiw.fds.sutton.client.utils.Link;

public class ModuleClientModel extends AbstractClientModel {

      private String moduleName;
      private int semesterWhenModuleIsOffered;
      private int numberOfCredits;

      @JsonDeserialize(using = ClientLinkJsonConverter.class)
      private transient Link selfLinkOnSecond;

      @JsonDeserialize(using = ClientLinkJsonConverter.class)
      private transient Link selfLink;

      public Link getSelfLink() {
            return selfLink;
      }

      public void setSelfLink(Link selfLink) {
            this.selfLink = selfLink;
      }

      public Link getSelfLinkOnSecond() {
            return selfLinkOnSecond;
      }

      public void setSelfLinkOnSecond(Link selfLinkOnSecond) {
            this.selfLinkOnSecond = selfLinkOnSecond;
      }

      public String getModuleName() {
            return moduleName;
      }

      public void setModuleName(String moduleName) {
            this.moduleName = moduleName;
      }

      public int getNumberOfCredits() {
            return numberOfCredits;
      }

      public void setNumberOfCredits(int numberOfCredits) {
            this.numberOfCredits = numberOfCredits;
      }

      public int getSemesterWhenModuleIsOffered() {
            return semesterWhenModuleIsOffered;
      }

      public void setSemesterWhenModuleIsOffered(int semesterWhenModuleIsOffered) {
            this.semesterWhenModuleIsOffered = semesterWhenModuleIsOffered;
      }
}
