
package de.metas.shipper.gateway.dpd.generated;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

import java.util.ArrayList;
import java.util.List;


/**
 * Bundles shipment response data.
 * 
 * <p>Java class for shipmentResponse complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="shipmentResponse">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="identificationNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="mpsId" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="25"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="parcelInformation" type="{http://dpd.com/common/service/types/ShipmentService/3.2}parcelInformationType" maxOccurs="unbounded" minOccurs="0"/>
 *         <element name="faults" type="{http://dpd.com/common/service/types/ShipmentService/3.2}faultCodeType" maxOccurs="3" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "shipmentResponse", propOrder = {
    "identificationNumber",
    "mpsId",
    "parcelInformation",
    "faults"
})
public class ShipmentResponse {

    /**
     * Serves as unique alphanumeric key of the shipment used by customer.
     * 
     */
    protected String identificationNumber;
    /**
     * The shipment number for consignment data.
     * If ordertype is pickup information, the shipment number is an internal database id,
     * which is necessary for technical support requests at DPD.
     * 
     */
    protected String mpsId;
    /**
     * Contains information about the single parcels.
     * 
     */
    protected List<ParcelInformationType> parcelInformation;
    /**
     * Contains information about errors during shipment order processing.
     * 
     */
    protected List<FaultCodeType> faults;

    /**
     * Serves as unique alphanumeric key of the shipment used by customer.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificationNumber() {
        return identificationNumber;
    }

    /**
     * Sets the value of the identificationNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getIdentificationNumber()
     */
    public void setIdentificationNumber(String value) {
        this.identificationNumber = value;
    }

    /**
     * The shipment number for consignment data.
     * If ordertype is pickup information, the shipment number is an internal database id,
     * which is necessary for technical support requests at DPD.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMpsId() {
        return mpsId;
    }

    /**
     * Sets the value of the mpsId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getMpsId()
     */
    public void setMpsId(String value) {
        this.mpsId = value;
    }

    /**
     * Contains information about the single parcels.
     * 
     * Gets the value of the parcelInformation property.
     * 
     * <p>This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the parcelInformation property.</p>
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * </p>
     * <pre>
     * getParcelInformation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ParcelInformationType }
     * </p>
     * 
     * 
     * @return
     *     The value of the parcelInformation property.
     */
    public List<ParcelInformationType> getParcelInformation() {
        if (parcelInformation == null) {
            parcelInformation = new ArrayList<>();
        }
        return this.parcelInformation;
    }

    /**
     * Contains information about errors during shipment order processing.
     * 
     * Gets the value of the faults property.
     * 
     * <p>This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the faults property.</p>
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * </p>
     * <pre>
     * getFaults().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FaultCodeType }
     * </p>
     * 
     * 
     * @return
     *     The value of the faults property.
     */
    public List<FaultCodeType> getFaults() {
        if (faults == null) {
            faults = new ArrayList<>();
        }
        return this.faults;
    }

}
