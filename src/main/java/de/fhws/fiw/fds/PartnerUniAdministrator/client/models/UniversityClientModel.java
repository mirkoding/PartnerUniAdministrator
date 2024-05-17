package de.fhws.fiw.fds.PartnerUniAdministrator.client.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import de.fhws.fiw.fds.sutton.client.model.AbstractClientModel;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.time.LocalDateTime;

@JsonRootName("university")
@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlRootElement(name="university")
public class UniversityClientModel extends AbstractClientModel {

      private String partnerUniName;
      private String partnerUniCountry;
      private String partnerUniDepartment;
      private String partnerUniDepartmentURL;
      private String contactPersonName;
      private int amountStudentsToSend;
      private int amountStudentsToReceive;
      private LocalDateTime startNextSpringSem;
      private LocalDateTime startNextAutumnSem;


}
