package de.fhws.fiw.fds.PartnerUniAdministrator.client.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.fhws.fiw.fds.sutton.client.converters.ClientLinkJsonConverter;
import de.fhws.fiw.fds.sutton.client.model.AbstractClientModel;
import de.fhws.fiw.fds.sutton.client.utils.Link;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@XmlRootElement
public class UniversityClientModel extends AbstractClientModel {

      private String partnerUniName;
      private String partnerUniCountry;
      private String partnerUniDepartment;
      private String partnerUniDepartmentURL;
      private String contactPersonName;
      private int amountStudentsToSend;
      private int amountStudentsToReceive;
      private LocalDate startNextSpringSem;
      private LocalDate startNextAutumnSem;

      @JsonDeserialize(using = ClientLinkJsonConverter.class)
      private Link selfLink;

      @JsonDeserialize(using = ClientLinkJsonConverter.class)
      private Link module;

      public UniversityClientModel() {}

      public UniversityClientModel(
            String partnerUniName,
            String partnerUniCountry,
            String partnerUniDepartment,
            String partnerUniDepartmentURL,
            String contactPersonName,
            int amountStudentsToSend,
            int amountStudentsToReceive,
            LocalDate startNextSpringSem,
            LocalDate startNextAutumnSem
      )
      {
            this.partnerUniName = partnerUniName;
            this.partnerUniCountry = partnerUniCountry;
            this.partnerUniDepartment = partnerUniDepartment;
            this.partnerUniDepartmentURL = partnerUniDepartmentURL;
            this.contactPersonName = contactPersonName;
            this.amountStudentsToSend = amountStudentsToSend;
            this.amountStudentsToReceive = amountStudentsToReceive;
            this.startNextSpringSem = startNextSpringSem;
            this.startNextAutumnSem = startNextAutumnSem;
      }

      @Override
      public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            UniversityClientModel that = (UniversityClientModel) o;
            return amountStudentsToSend == that.amountStudentsToSend &&
                  amountStudentsToReceive == that.amountStudentsToReceive &&
                  Objects.equals(partnerUniName, that.partnerUniName) &&
                  Objects.equals(partnerUniCountry, that.partnerUniCountry) &&
                  Objects.equals(partnerUniDepartment, that.partnerUniDepartment) &&
                  Objects.equals(partnerUniDepartmentURL, that.partnerUniDepartmentURL) &&
                  Objects.equals(contactPersonName, that.contactPersonName) &&
                  Objects.equals(startNextSpringSem, that.startNextSpringSem) &&
                  Objects.equals(startNextAutumnSem, that.startNextAutumnSem);
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

      @JsonIgnore
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

      @JsonIgnore
      public Link getSelfLink() {
            return selfLink;
      }

      public void setSelfLink(Link selfLink) {
            this.selfLink = selfLink;
      }

      public LocalDate getStartNextAutumnSem() {
            return startNextAutumnSem;
      }

      public void setStartNextAutumnSem(LocalDate startNextAutumnSem) {
            this.startNextAutumnSem = startNextAutumnSem;
      }

      public LocalDate getStartNextSpringSem() {
            return startNextSpringSem;
      }

      public void setStartNextSpringSem(LocalDate startNextSpringSem) {
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
