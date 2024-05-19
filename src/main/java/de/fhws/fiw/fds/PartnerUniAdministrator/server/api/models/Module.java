package de.fhws.fiw.fds.PartnerUniAdministrator.server.api.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import de.fhws.fiw.fds.sutton.server.api.hyperlinks.Link;
import de.fhws.fiw.fds.sutton.server.api.hyperlinks.annotations.SecondarySelfLink;
import de.fhws.fiw.fds.sutton.server.api.hyperlinks.annotations.SelfLink;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;
import jakarta.xml.bind.annotation.XmlRootElement;

@JsonRootName("module")
@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlRootElement(name = "module")
public class Module extends AbstractModel {

      private String moduleName;
      private int semesterWhenModuleIsOffered;
      private int numberOfCredits;

      @SecondarySelfLink(
            primaryPathElement = "partnerUniversities",
            secondaryPathElement = "modules"
      )
      private transient Link selfLinkOnSecond;

      @SelfLink(pathElement = "modules")
      private transient Link selfLink;

      public Module() {}

      public Module(
            String moduleName,
            int numberOfCredits,
            Link selfLink,
            Link selfLinkOnSecond,
            int semesterWhenModuleIsOffered
      )
      {
            this.moduleName = moduleName;
            this.numberOfCredits = numberOfCredits;
            this.selfLink = selfLink;
            this.selfLinkOnSecond = selfLinkOnSecond;
            this.semesterWhenModuleIsOffered = semesterWhenModuleIsOffered;
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

      public int getSemesterWhenModuleIsOffered() {
            return semesterWhenModuleIsOffered;
      }

      public void setSemesterWhenModuleIsOffered(int semesterWhenModuleIsOffered) {
            this.semesterWhenModuleIsOffered = semesterWhenModuleIsOffered;
      }

      @Override
      public String toString() {
            return moduleName + " (" + numberOfCredits + ")" + " semester: " + semesterWhenModuleIsOffered;
      }
}
