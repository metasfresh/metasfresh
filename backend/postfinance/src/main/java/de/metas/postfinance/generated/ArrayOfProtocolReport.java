
package de.metas.postfinance.generated;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfProtocolReport complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="ArrayOfProtocolReport">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="ProtocolReport" type="{http://swisspost_ch.ebs.ebill.b2bservice}ProtocolReport" maxOccurs="unbounded" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfProtocolReport", namespace = "http://swisspost_ch.ebs.ebill.b2bservice", propOrder = {
    "protocolReport"
})
public class ArrayOfProtocolReport {

    @XmlElement(name = "ProtocolReport", nillable = true)
    protected List<ProtocolReport> protocolReport;

    /**
     * Gets the value of the protocolReport property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the protocolReport property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProtocolReport().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProtocolReport }
     * 
     * 
     * @return
     *     The value of the protocolReport property.
     */
    public List<ProtocolReport> getProtocolReport() {
        if (protocolReport == null) {
            protocolReport = new ArrayList<>();
        }
        return this.protocolReport;
    }

}
