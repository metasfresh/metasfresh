
package de.metas.banking.camt53.jaxb.camt053_001_04;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EntryDetails3 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EntryDetails3"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Btch" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.04}BatchInformation2" minOccurs="0"/&gt;
 *         &lt;element name="TxDtls" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.04}EntryTransaction4" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EntryDetails3", propOrder = {
    "btch",
    "txDtls"
})
public class EntryDetails3 {

    @XmlElement(name = "Btch")
    protected BatchInformation2 btch;
    @XmlElement(name = "TxDtls")
    protected List<EntryTransaction4> txDtls;

    /**
     * Gets the value of the btch property.
     * 
     * @return
     *     possible object is
     *     {@link BatchInformation2 }
     *     
     */
    public BatchInformation2 getBtch() {
        return btch;
    }

    /**
     * Sets the value of the btch property.
     * 
     * @param value
     *     allowed object is
     *     {@link BatchInformation2 }
     *     
     */
    public void setBtch(BatchInformation2 value) {
        this.btch = value;
    }

    /**
     * Gets the value of the txDtls property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the txDtls property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTxDtls().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EntryTransaction4 }
     * 
     * 
     */
    public List<EntryTransaction4> getTxDtls() {
        if (txDtls == null) {
            txDtls = new ArrayList<EntryTransaction4>();
        }
        return this.txDtls;
    }

}
