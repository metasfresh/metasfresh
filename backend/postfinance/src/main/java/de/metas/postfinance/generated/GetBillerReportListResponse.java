
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
 *         <element name="GetBillerReportListResult" type="{http://swisspost_ch.ebs.ebill.b2bservice}ArrayOfBillerReport" minOccurs="0"/>
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
    "getBillerReportListResult"
})
@XmlRootElement(name = "GetBillerReportListResponse")
public class GetBillerReportListResponse {

    @XmlElementRef(name = "GetBillerReportListResult", namespace = "http://ch.swisspost.ebill.b2bservice", type = JAXBElement.class, required = false)
    protected JAXBElement<ArrayOfBillerReport> getBillerReportListResult;

    /**
     * Gets the value of the getBillerReportListResult property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfBillerReport }{@code >}
     *     
     */
    public JAXBElement<ArrayOfBillerReport> getGetBillerReportListResult() {
        return getBillerReportListResult;
    }

    /**
     * Sets the value of the getBillerReportListResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfBillerReport }{@code >}
     *     
     */
    public void setGetBillerReportListResult(JAXBElement<ArrayOfBillerReport> value) {
        this.getBillerReportListResult = value;
    }

}
