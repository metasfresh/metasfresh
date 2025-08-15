
package de.metas.shipper.gateway.dpd.generated;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * Options how to return the parcel labels
 * 
 * <p>Java class for printOptions complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="printOptions">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="printerLanguage">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <enumeration value="PDF"/>
 *               <enumeration value="ZPL"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="paperFormat">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <enumeration value="A4"/>
 *               <enumeration value="A6"/>
 *               <enumeration value="A7"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="printer" type="{http://dpd.com/common/service/types/ShipmentService/3.2}printer" minOccurs="0"/>
 *         <element name="startPosition" type="{http://dpd.com/common/service/types/ShipmentService/3.2}startPosition" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "printOptions", propOrder = {
    "printerLanguage",
    "paperFormat",
    "printer",
    "startPosition"
})
public class PrintOptions {

    /**
     * The language in which the parcel labels should be returned. PDF as file output, ZPL for direct printing. In any case the output is base64 encoded.
     * 
     */
    @XmlElement(required = true)
    protected String printerLanguage;
    /**
     * Declares the paper format for parcel label print, either "A4" or "A6". For direct printing the format has to be set to "A6". "A7" only for return labels, other type are not allowed.
     * 
     */
    @XmlElement(required = true)
    protected String paperFormat;
    /**
     * Information about the printer. Enabled if direct printing is selected.
     * 
     */
    protected Printer printer;
    /**
     * Start position for print on A4 paper.
     * 
     */
    @XmlSchemaType(name = "string")
    protected StartPosition startPosition;

    /**
     * The language in which the parcel labels should be returned. PDF as file output, ZPL for direct printing. In any case the output is base64 encoded.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrinterLanguage() {
        return printerLanguage;
    }

    /**
     * Sets the value of the printerLanguage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getPrinterLanguage()
     */
    public void setPrinterLanguage(String value) {
        this.printerLanguage = value;
    }

    /**
     * Declares the paper format for parcel label print, either "A4" or "A6". For direct printing the format has to be set to "A6". "A7" only for return labels, other type are not allowed.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaperFormat() {
        return paperFormat;
    }

    /**
     * Sets the value of the paperFormat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getPaperFormat()
     */
    public void setPaperFormat(String value) {
        this.paperFormat = value;
    }

    /**
     * Information about the printer. Enabled if direct printing is selected.
     * 
     * @return
     *     possible object is
     *     {@link Printer }
     *     
     */
    public Printer getPrinter() {
        return printer;
    }

    /**
     * Sets the value of the printer property.
     * 
     * @param value
     *     allowed object is
     *     {@link Printer }
     *     
     * @see #getPrinter()
     */
    public void setPrinter(Printer value) {
        this.printer = value;
    }

    /**
     * Start position for print on A4 paper.
     * 
     * @return
     *     possible object is
     *     {@link StartPosition }
     *     
     */
    public StartPosition getStartPosition() {
        return startPosition;
    }

    /**
     * Sets the value of the startPosition property.
     * 
     * @param value
     *     allowed object is
     *     {@link StartPosition }
     *     
     * @see #getStartPosition()
     */
    public void setStartPosition(StartPosition value) {
        this.startPosition = value;
    }

}
