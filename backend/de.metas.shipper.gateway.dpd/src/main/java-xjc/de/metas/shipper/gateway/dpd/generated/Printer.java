
package de.metas.shipper.gateway.dpd.generated;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import java.math.BigDecimal;


/**
 * Information about the printer, if direct printing is selected.
 * 
 * <p>Java class for printer complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="printer">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="manufacturer" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="model" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="revision" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="offsetX" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         <element name="offsetY" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         <element name="connectionType">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <enumeration value="SERIAL"/>
 *               <enumeration value="PARALLEL"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="barcodeCapable2D" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "printer", propOrder = {
    "manufacturer",
    "model",
    "revision",
    "offsetX",
    "offsetY",
    "connectionType",
    "barcodeCapable2D"
})
public class Printer {

    /**
     * The printer's manufacturer. At the moment only for informational purposes.
     * 
     */
    protected String manufacturer;
    /**
     * The printer's model. At the moment only for informational purposes.
     * 
     */
    protected String model;
    /**
     * The printer's revision / version. At the moment only for informational purposes.
     * 
     */
    protected String revision;
    /**
     * The horizontal offset in mm for the direct printer file (Not used with normal PDF output).
     * 
     */
    @XmlElement(required = true)
    protected BigDecimal offsetX;
    /**
     * The vertical offset in mm for the direct printer file (Not used with normal PDF output).
     * 
     */
    @XmlElement(required = true)
    protected BigDecimal offsetY;
    /**
     * The connection type of the printer: serial or parallel connection.
     * 
     */
    @XmlElement(required = true)
    protected String connectionType;
    /**
     * If the printer can print AZTEC barcodes, set this flag to true.
     * 
     */
    protected boolean barcodeCapable2D;

    /**
     * The printer's manufacturer. At the moment only for informational purposes.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * Sets the value of the manufacturer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getManufacturer()
     */
    public void setManufacturer(String value) {
        this.manufacturer = value;
    }

    /**
     * The printer's model. At the moment only for informational purposes.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets the value of the model property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getModel()
     */
    public void setModel(String value) {
        this.model = value;
    }

    /**
     * The printer's revision / version. At the moment only for informational purposes.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRevision() {
        return revision;
    }

    /**
     * Sets the value of the revision property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getRevision()
     */
    public void setRevision(String value) {
        this.revision = value;
    }

    /**
     * The horizontal offset in mm for the direct printer file (Not used with normal PDF output).
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getOffsetX() {
        return offsetX;
    }

    /**
     * Sets the value of the offsetX property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     * @see #getOffsetX()
     */
    public void setOffsetX(BigDecimal value) {
        this.offsetX = value;
    }

    /**
     * The vertical offset in mm for the direct printer file (Not used with normal PDF output).
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getOffsetY() {
        return offsetY;
    }

    /**
     * Sets the value of the offsetY property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     * @see #getOffsetY()
     */
    public void setOffsetY(BigDecimal value) {
        this.offsetY = value;
    }

    /**
     * The connection type of the printer: serial or parallel connection.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConnectionType() {
        return connectionType;
    }

    /**
     * Sets the value of the connectionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getConnectionType()
     */
    public void setConnectionType(String value) {
        this.connectionType = value;
    }

    /**
     * If the printer can print AZTEC barcodes, set this flag to true.
     * 
     */
    public boolean isBarcodeCapable2D() {
        return barcodeCapable2D;
    }

    /**
     * Sets the value of the barcodeCapable2D property.
     * 
     */
    public void setBarcodeCapable2D(boolean value) {
        this.barcodeCapable2D = value;
    }

}
