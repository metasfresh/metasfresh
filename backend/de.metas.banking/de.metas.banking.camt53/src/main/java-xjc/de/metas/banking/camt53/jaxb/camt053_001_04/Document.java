
package de.metas.banking.camt53.jaxb.camt053_001_04;

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
 *         &lt;element name="BkToCstmrStmt" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.04}BankToCustomerStatementV04"/&gt;
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
    "bkToCstmrStmt"
})
public class Document {

    @XmlElement(name = "BkToCstmrStmt", required = true)
    protected BankToCustomerStatementV04 bkToCstmrStmt;

    /**
     * Gets the value of the bkToCstmrStmt property.
     * 
     * @return
     *     possible object is
     *     {@link BankToCustomerStatementV04 }
     *     
     */
    public BankToCustomerStatementV04 getBkToCstmrStmt() {
        return bkToCstmrStmt;
    }

    /**
     * Sets the value of the bkToCstmrStmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BankToCustomerStatementV04 }
     *     
     */
    public void setBkToCstmrStmt(BankToCustomerStatementV04 value) {
        this.bkToCstmrStmt = value;
    }

}
