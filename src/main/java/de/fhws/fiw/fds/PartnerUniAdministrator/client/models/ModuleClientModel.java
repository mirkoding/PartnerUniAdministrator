package de.fhws.fiw.fds.PartnerUniAdministrator.client.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.fhws.fiw.fds.sutton.client.converters.ClientLinkJsonConverter;
import de.fhws.fiw.fds.sutton.client.model.AbstractClientModel;
import de.fhws.fiw.fds.sutton.client.utils.Link;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ModuleClientModel extends AbstractClientModel {

      private String moduleName;
      private int semesterWhenModuleIsOffered;
      private int numberOfCredits;

      @JsonDeserialize(using = ClientLinkJsonConverter.class)
      private transient Link selfLinkOnSecond;

      @JsonDeserialize(using = ClientLinkJsonConverter.class)
      private transient Link selfLink;

      @Override
      public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ModuleClientModel that = (ModuleClientModel) o;
            return semesterWhenModuleIsOffered == that.semesterWhenModuleIsOffered
                  && numberOfCredits == that.numberOfCredits
                  && moduleName.equals(that.moduleName);
      }

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

      @Override
      public String toString() {
            return "ModuleClientModel{"
                  + "moduleName='" + moduleName + '\''
                  + ", semesterWhenModuleIsOffered=" + semesterWhenModuleIsOffered
                  + ", numberOfCredits=" + numberOfCredits
                  + ", selfLinkOnSecond=" + selfLinkOnSecond
                  + ", selfLink=" + selfLink;
      }
}
