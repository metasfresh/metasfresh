
package de.metas.edi.esb.jaxb.stepcom.invoic;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Document complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Document"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Xrech4h" type="{}Xrech4h" maxOccurs="9999"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Document", propOrder = {
    "xrech4H"
})
public class Document {

    @XmlElement(name = "Xrech4h", required = true)
    protected List<Xrech4H> xrech4H;

    /**
     * Gets the value of the xrech4H property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the xrech4H property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getXrech4H().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Xrech4H }
     * 
     * 
     */
    public List<Xrech4H> getXrech4H() {
        if (xrech4H == null) {
            xrech4H = new ArrayList<Xrech4H>();
        }
        return this.xrech4H;
    }

}
