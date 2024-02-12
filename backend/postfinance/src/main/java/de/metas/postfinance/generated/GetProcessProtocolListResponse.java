
package de.metas.postfinance.generated;

import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType>
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="GetProcessProtocolListResult" type="{http://swisspost_ch.ebs.ebill.b2bservice}ArrayOfProtocolReport" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "getProcessProtocolListResult"
})
@XmlRootElement(name = "GetProcessProtocolListResponse")
public class GetProcessProtocolListResponse {

    @XmlElementRef(name = "GetProcessProtocolListResult", namespace = "http://ch.swisspost.ebill.b2bservice", type = JAXBElement.class, required = false)
    protected JAXBElement<ArrayOfProtocolReport> getProcessProtocolListResult;

    /**
     * Gets the value of the getProcessProtocolListResult property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfProtocolReport }{@code >}
     *     
     */
    public JAXBElement<ArrayOfProtocolReport> getGetProcessProtocolListResult() {
        return getProcessProtocolListResult;
    }

    /**
     * Sets the value of the getProcessProtocolListResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfProtocolReport }{@code >}
     *     
     */
    public void setGetProcessProtocolListResult(JAXBElement<ArrayOfProtocolReport> value) {
        this.getProcessProtocolListResult = value;
    }

}
