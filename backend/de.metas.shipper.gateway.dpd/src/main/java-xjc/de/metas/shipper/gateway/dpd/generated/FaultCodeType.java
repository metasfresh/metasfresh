
package de.metas.shipper.gateway.dpd.generated;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for faultCodeType complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="faultCodeType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="faultCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="message" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "faultCodeType", propOrder = {
    "faultCode",
    "message"
})
public class FaultCodeType {

    /**
     * Possible error codes are:
     * 
     * SHIPPING_1 - pickup address is unknown for customer
     * SHIPPING_2 - parcel label print fails
     * COMMON_1 - unexpected runtime error
     * COMMON_2 - mandatory input field is empty
     * COMMON_3 - length of data for field does not fit
     * COMMON_4 - input data contains invalid special character
     * COMMON_5 - a swap parcel is ordered, but there is more than one parcel
     * COMMON_6 - some input data field values result in an invalid combination
     * COMMON_7 - input data contains invalid value
     * COMMON_8 - a field is to be stored in database, but there is not database field mapped for it
     * DATABASE_6 - transfer state of order is to be changed but order is already committed
     * DATABASE_7 - update fails on database level during order storing
     * DATABASE_8 - delete fails on database level during rollback after some other error
     * DATABASE_21 - order is to be inserted in databse, but has already a database id
     * DATABASE_22 - order is to be committed after inserting, but there is no database id for update given
     * DATABASE_24 - deletion is necessary because of some other error, but no database id for record is given
     * MPSEXP_1 - parcel label number is already in use
     * ROUTING_1 - no route could be found for depot and feature
     * ROUTING_2 - invalid input data
     * ROUTING_3 - the connection to the database failed
     * ROUTING_4 - an internal failure occurs
     * ROUTING_5 - no depot could be found
     * ROUTING_6 - no pickup date was given
     * ROUTING_7 - internal extension rule error
     * ROUTING_8 - malformed zip code
     * ROUTING_9 - missing country code
     * ROUTING_10 - missing routing place
     * ROUTING_11 - missing service code
     * ROUTING_12 - routing place error
     * ROUTING_13 - sending date error
     * ROUTING_14 - service code error
     * ROUTING_15 - invalid relation of service codes
     * ROUTING_16 - unknown country code
     * ROUTING_17 - unknown destination depot
     * ROUTING_18 - unknown zip code
     * ROUTING_19 - unknown routing place
     * ROUTING_20 - unknown service code
     * ROUTING_21 - invalid service code
     * ROUTING_21 - parcel is labelled as return parcel but returns is not possible
     * ROUTING_22 - missing allow
     * ROUTING_23 - missing routing place or destination depot
     * 
     */
    @XmlElement(required = true)
    protected String faultCode;
    /**
     * Message with detailed information for the fault (e.g. incorrect field).
     * 
     */
    @XmlElement(required = true)
    protected String message;

    /**
     * Possible error codes are:
     * 
     * SHIPPING_1 - pickup address is unknown for customer
     * SHIPPING_2 - parcel label print fails
     * COMMON_1 - unexpected runtime error
     * COMMON_2 - mandatory input field is empty
     * COMMON_3 - length of data for field does not fit
     * COMMON_4 - input data contains invalid special character
     * COMMON_5 - a swap parcel is ordered, but there is more than one parcel
     * COMMON_6 - some input data field values result in an invalid combination
     * COMMON_7 - input data contains invalid value
     * COMMON_8 - a field is to be stored in database, but there is not database field mapped for it
     * DATABASE_6 - transfer state of order is to be changed but order is already committed
     * DATABASE_7 - update fails on database level during order storing
     * DATABASE_8 - delete fails on database level during rollback after some other error
     * DATABASE_21 - order is to be inserted in databse, but has already a database id
     * DATABASE_22 - order is to be committed after inserting, but there is no database id for update given
     * DATABASE_24 - deletion is necessary because of some other error, but no database id for record is given
     * MPSEXP_1 - parcel label number is already in use
     * ROUTING_1 - no route could be found for depot and feature
     * ROUTING_2 - invalid input data
     * ROUTING_3 - the connection to the database failed
     * ROUTING_4 - an internal failure occurs
     * ROUTING_5 - no depot could be found
     * ROUTING_6 - no pickup date was given
     * ROUTING_7 - internal extension rule error
     * ROUTING_8 - malformed zip code
     * ROUTING_9 - missing country code
     * ROUTING_10 - missing routing place
     * ROUTING_11 - missing service code
     * ROUTING_12 - routing place error
     * ROUTING_13 - sending date error
     * ROUTING_14 - service code error
     * ROUTING_15 - invalid relation of service codes
     * ROUTING_16 - unknown country code
     * ROUTING_17 - unknown destination depot
     * ROUTING_18 - unknown zip code
     * ROUTING_19 - unknown routing place
     * ROUTING_20 - unknown service code
     * ROUTING_21 - invalid service code
     * ROUTING_21 - parcel is labelled as return parcel but returns is not possible
     * ROUTING_22 - missing allow
     * ROUTING_23 - missing routing place or destination depot
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFaultCode() {
        return faultCode;
    }

    /**
     * Sets the value of the faultCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getFaultCode()
     */
    public void setFaultCode(String value) {
        this.faultCode = value;
    }

    /**
     * Message with detailed information for the fault (e.g. incorrect field).
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the value of the message property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getMessage()
     */
    public void setMessage(String value) {
        this.message = value;
    }

}
