
package de.metas.shipper.gateway.dpd.generated;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for parcelInformationType complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="parcelInformationType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="parcelLabelNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="dpdReference" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "parcelInformationType", propOrder = {
    "parcelLabelNumber",
    "dpdReference"
})
public class ParcelInformationType {

    /**
     * The parcel label number of the corresponding parcel.
     * 
     */
    protected String parcelLabelNumber;
    /**
     * The DPD reference for this parcel.
     * 
     */
    protected String dpdReference;

    /**
     * The parcel label number of the corresponding parcel.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParcelLabelNumber() {
        return parcelLabelNumber;
    }

    /**
     * Sets the value of the parcelLabelNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getParcelLabelNumber()
     */
    public void setParcelLabelNumber(String value) {
        this.parcelLabelNumber = value;
    }

    /**
     * The DPD reference for this parcel.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDpdReference() {
        return dpdReference;
    }

    /**
     * Sets the value of the dpdReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getDpdReference()
     */
    public void setDpdReference(String value) {
        this.dpdReference = value;
    }

}
