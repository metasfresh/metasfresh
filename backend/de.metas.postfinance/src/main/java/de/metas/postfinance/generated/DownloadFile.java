
package de.metas.postfinance.generated;

import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DownloadFile complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="DownloadFile">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Data" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         <element name="Filename" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DownloadFile", namespace = "http://swisspost_ch.ebs.ebill.b2bservice", propOrder = {
    "data",
    "filename"
})
public class DownloadFile {

    @XmlElementRef(name = "Data", namespace = "http://swisspost_ch.ebs.ebill.b2bservice", type = JAXBElement.class, required = false)
    protected JAXBElement<byte[]> data;
    @XmlElementRef(name = "Filename", namespace = "http://swisspost_ch.ebs.ebill.b2bservice", type = JAXBElement.class, required = false)
    protected JAXBElement<String> filename;

    /**
     * Gets the value of the data property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     *     
     */
    public JAXBElement<byte[]> getData() {
        return data;
    }

    /**
     * Sets the value of the data property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     *     
     */
    public void setData(JAXBElement<byte[]> value) {
        this.data = value;
    }

    /**
     * Gets the value of the filename property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getFilename() {
        return filename;
    }

    /**
     * Sets the value of the filename property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setFilename(JAXBElement<String> value) {
        this.filename = value;
    }

}
