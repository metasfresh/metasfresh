
package de.metas.shipper.gateway.dpd.generated;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * Bundles cash on delivery data.
 * 
 * <p>Java class for cod complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="cod">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="amount">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}long">
 *               <maxInclusive value="9999999999"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="currency">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <length value="3"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="inkasso">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *               <enumeration value="0"/>
 *               <enumeration value="1"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="purpose" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="14"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="bankCode" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="25"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="bankName" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="27"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="bankAccountNumber" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="25"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="bankAccountHolder" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="30"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="iban" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="50"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="bic" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="50"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cod", propOrder = {
    "amount",
    "currency",
    "inkasso",
    "purpose",
    "bankCode",
    "bankName",
    "bankAccountNumber",
    "bankAccountHolder",
    "iban",
    "bic"
})
public class Cod {

    /**
     * COD amount in the currency of the destination country. The amount is specified as integer value e.g. 300.00 becomes 30000.
     * Please note the national ceilings for cod in the corresponding relations.
     * 
     */
    protected long amount;
    /**
     * ISO 4217 alpha-3 currency code (destination country).
     * 
     */
    @XmlElement(required = true)
    protected String currency;
    /**
     * Defines collection type. Possible values are:
     *  0 = cash
     *  1 = crossed cheque
     * 
     */
    protected int inkasso;
    /**
     * Purpose of use.
     * 
     */
    protected String purpose;
    /**
     * Bank code.
     * If this value is filled, parameters for bankName, bankAccountNumber and bankAccountHolder must also be filled.
     * 
     */
    protected String bankCode;
    /**
     * Bank name.
     * If this value is filled, parameters for bankCode, bankAccountNumber and bankAccountHolder must also be filled.
     * 
     */
    protected String bankName;
    /**
     * Bank account number.
     * If this value is filled, parameters for bankCode, bankName and bankAccountHolder must also be filled.
     * 
     */
    protected String bankAccountNumber;
    /**
     * Account holder.
     * If this value is filled, parameters for bankCode, bankName and bankAccountNumber must also be filled.
     * 
     */
    protected String bankAccountHolder;
    /**
     * International bank account number .
     * If this value is filled, parameters for bankCode, bankName, bankAccountNumber and bankAccountHolder must also be filled.
     * 
     */
    protected String iban;
    /**
     * Bank identifier code.
     * If this value is filled, parameters for bankCode, bankName, bankAccountNumber and bankAccountHolder must also be filled.
     * 
     */
    protected String bic;

    /**
     * COD amount in the currency of the destination country. The amount is specified as integer value e.g. 300.00 becomes 30000.
     * Please note the national ceilings for cod in the corresponding relations.
     * 
     */
    public long getAmount() {
        return amount;
    }

    /**
     * Sets the value of the amount property.
     * 
     */
    public void setAmount(long value) {
        this.amount = value;
    }

    /**
     * ISO 4217 alpha-3 currency code (destination country).
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Sets the value of the currency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getCurrency()
     */
    public void setCurrency(String value) {
        this.currency = value;
    }

    /**
     * Defines collection type. Possible values are:
     *  0 = cash
     *  1 = crossed cheque
     * 
     */
    public int getInkasso() {
        return inkasso;
    }

    /**
     * Sets the value of the inkasso property.
     * 
     */
    public void setInkasso(int value) {
        this.inkasso = value;
    }

    /**
     * Purpose of use.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPurpose() {
        return purpose;
    }

    /**
     * Sets the value of the purpose property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getPurpose()
     */
    public void setPurpose(String value) {
        this.purpose = value;
    }

    /**
     * Bank code.
     * If this value is filled, parameters for bankName, bankAccountNumber and bankAccountHolder must also be filled.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankCode() {
        return bankCode;
    }

    /**
     * Sets the value of the bankCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getBankCode()
     */
    public void setBankCode(String value) {
        this.bankCode = value;
    }

    /**
     * Bank name.
     * If this value is filled, parameters for bankCode, bankAccountNumber and bankAccountHolder must also be filled.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * Sets the value of the bankName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getBankName()
     */
    public void setBankName(String value) {
        this.bankName = value;
    }

    /**
     * Bank account number.
     * If this value is filled, parameters for bankCode, bankName and bankAccountHolder must also be filled.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    /**
     * Sets the value of the bankAccountNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getBankAccountNumber()
     */
    public void setBankAccountNumber(String value) {
        this.bankAccountNumber = value;
    }

    /**
     * Account holder.
     * If this value is filled, parameters for bankCode, bankName and bankAccountNumber must also be filled.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankAccountHolder() {
        return bankAccountHolder;
    }

    /**
     * Sets the value of the bankAccountHolder property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getBankAccountHolder()
     */
    public void setBankAccountHolder(String value) {
        this.bankAccountHolder = value;
    }

    /**
     * International bank account number .
     * If this value is filled, parameters for bankCode, bankName, bankAccountNumber and bankAccountHolder must also be filled.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIban() {
        return iban;
    }

    /**
     * Sets the value of the iban property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getIban()
     */
    public void setIban(String value) {
        this.iban = value;
    }

    /**
     * Bank identifier code.
     * If this value is filled, parameters for bankCode, bankName, bankAccountNumber and bankAccountHolder must also be filled.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBic() {
        return bic;
    }

    /**
     * Sets the value of the bic property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getBic()
     */
    public void setBic(String value) {
        this.bic = value;
    }

}
