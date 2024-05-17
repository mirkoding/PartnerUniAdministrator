package de.fhws.fiw.fds.PartnerUniAdministrator.server.api.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import de.fhws.fiw.fds.sutton.client.utils.Link;
import de.fhws.fiw.fds.sutton.server.api.hyperlinks.annotations.SuttonLink;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.time.LocalDateTime;

@JsonRootName("university")
@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlRootElement(name = "university")
public class University extends AbstractModel {

      private String partnerUniName;
      private String partnerUniCountry;
      private String partnerUniDepartment;
      private String partnerUniDepartmentURL;
      private String contactPersonName;
      private int amountStudentsToSend;
      private int amountStudentsToReceive;
      private LocalDateTime startNextSpringSem;
      private LocalDateTime startNextAutumnSem;

      @SuttonLink(
            value = "partnerUniversities/${id}",
            rel = "self"
      )
      private transient Link selfLink;

      @SuttonLink(
            value = "partnerUniversities/${id}/modules",
            rel = "getModulesOfPartnerUniversities"
      )
      private transient Link module;

      public University() {}

      public University(
            String contactPersonName,
            int amountStudentsToReceive,
            int amountStudentsToSend,
            String partnerUniCountry,
            String partnerUniDepartment,
            String partnerUniDepartmentURL,
            String partnerUniName,
            LocalDateTime startNextAutumnSem,
            LocalDateTime startNextSpringSem
      )
      {
            this.contactPersonName = contactPersonName;
            this.amountStudentsToReceive = amountStudentsToReceive;
            this.amountStudentsToSend = amountStudentsToSend;
            this.partnerUniCountry = partnerUniCountry;
            this.partnerUniDepartment = partnerUniDepartment;
            this.partnerUniDepartmentURL = partnerUniDepartmentURL;
            this.partnerUniName = partnerUniName;
            this.startNextAutumnSem = startNextAutumnSem;
            this.startNextSpringSem = startNextSpringSem;
      }

      public int getAmountStudentsToReceive() {
            return amountStudentsToReceive;
      }

      public void setAmountStudentsToReceive(int amountStudentsToReceive) {
            this.amountStudentsToReceive = amountStudentsToReceive;
      }

      public int getAmountStudentsToSend() {
            return amountStudentsToSend;
      }

      public void setAmountStudentsToSend(int amountStudentsToSend) {
            this.amountStudentsToSend = amountStudentsToSend;
      }

      public String getContactPersonName() {
            return contactPersonName;
      }

      public void setContactPersonName(String contactPersonName) {
            this.contactPersonName = contactPersonName;
      }

      public Link getModule() {
            return module;
      }

      public void setModule(Link module) {
            this.module = module;
      }

      public String getPartnerUniCountry() {
            return partnerUniCountry;
      }

      public void setPartnerUniCountry(String partnerUniCountry) {
            this.partnerUniCountry = partnerUniCountry;
      }

      public String getPartnerUniDepartment() {
            return partnerUniDepartment;
      }

      public void setPartnerUniDepartment(String partnerUniDepartment) {
            this.partnerUniDepartment = partnerUniDepartment;
      }

      public String getPartnerUniDepartmentURL() {
            return partnerUniDepartmentURL;
      }

      public void setPartnerUniDepartmentURL(String partnerUniDepartmentURL) {
            this.partnerUniDepartmentURL = partnerUniDepartmentURL;
      }

      public String getPartnerUniName() {
            return partnerUniName;
      }

      public void setPartnerUniName(String partnerUniName) {
            this.partnerUniName = partnerUniName;
      }

      public Link getSelfLink() {
            return selfLink;
      }

      public void setSelfLink(Link selfLink) {
            this.selfLink = selfLink;
      }

      public LocalDateTime getStartNextAutumnSem() {
            return startNextAutumnSem;
      }

      public void setStartNextAutumnSem(LocalDateTime startNextAutumnSem) {
            this.startNextAutumnSem = startNextAutumnSem;
      }

      public LocalDateTime getStartNextSpringSem() {
            return startNextSpringSem;
      }

      public void setStartNextSpringSem(LocalDateTime startNextSpringSem) {
            this.startNextSpringSem = startNextSpringSem;
      }

      @Override
      public String toString() {
            return      "Name of University: " + partnerUniName + "\n" +
                  "Country: " + partnerUniCountry + "\n" +
                  "Department of University: " + partnerUniDepartment + " URL: " + partnerUniDepartmentURL + "\n" +
                  "Contact Person: " + contactPersonName + "\n" +
                  "Amount Students to Send: " + amountStudentsToSend + "\n" +
                  "Amount Students to Receive: " + amountStudentsToReceive + "\n" +
                  "Start Next Spring Semester: " + startNextSpringSem + "\n" +
                  "Start Next Autumn Semester: " + startNextAutumnSem + "\n";
      }
}
