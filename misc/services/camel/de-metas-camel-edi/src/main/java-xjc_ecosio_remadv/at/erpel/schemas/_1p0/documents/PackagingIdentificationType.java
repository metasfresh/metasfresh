
package at.erpel.schemas._1p0.documents;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PackagingIdentificationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PackagingIdentificationType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="StorageLocation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="PlantCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="StorageBin" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="PlantName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="PlantCity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DeliverTo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ProductionDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="KanbanCardNumberRange1Start" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="KanbanCardNumberRange1End" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PackagingIdentificationType", propOrder = {
    "storageLocation",
    "plantCode",
    "storageBin",
    "plantName",
    "plantCity",
    "deliverTo",
    "productionDescription",
    "kanbanCardNumberRange1Start",
    "kanbanCardNumberRange1End"
})
public class PackagingIdentificationType {

    @XmlElement(name = "StorageLocation")
    protected String storageLocation;
    @XmlElement(name = "PlantCode")
    protected String plantCode;
    @XmlElement(name = "StorageBin")
    protected String storageBin;
    @XmlElement(name = "PlantName")
    protected String plantName;
    @XmlElement(name = "PlantCity")
    protected String plantCity;
    @XmlElement(name = "DeliverTo")
    protected String deliverTo;
    @XmlElement(name = "ProductionDescription")
    protected String productionDescription;
    @XmlElement(name = "KanbanCardNumberRange1Start")
    protected String kanbanCardNumberRange1Start;
    @XmlElement(name = "KanbanCardNumberRange1End")
    protected String kanbanCardNumberRange1End;

    /**
     * Gets the value of the storageLocation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStorageLocation() {
        return storageLocation;
    }

    /**
     * Sets the value of the storageLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStorageLocation(String value) {
        this.storageLocation = value;
    }

    /**
     * Gets the value of the plantCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlantCode() {
        return plantCode;
    }

    /**
     * Sets the value of the plantCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlantCode(String value) {
        this.plantCode = value;
    }

    /**
     * Gets the value of the storageBin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStorageBin() {
        return storageBin;
    }

    /**
     * Sets the value of the storageBin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStorageBin(String value) {
        this.storageBin = value;
    }

    /**
     * Gets the value of the plantName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlantName() {
        return plantName;
    }

    /**
     * Sets the value of the plantName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlantName(String value) {
        this.plantName = value;
    }

    /**
     * Gets the value of the plantCity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlantCity() {
        return plantCity;
    }

    /**
     * Sets the value of the plantCity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlantCity(String value) {
        this.plantCity = value;
    }

    /**
     * Gets the value of the deliverTo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeliverTo() {
        return deliverTo;
    }

    /**
     * Sets the value of the deliverTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeliverTo(String value) {
        this.deliverTo = value;
    }

    /**
     * Gets the value of the productionDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductionDescription() {
        return productionDescription;
    }

    /**
     * Sets the value of the productionDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductionDescription(String value) {
        this.productionDescription = value;
    }

    /**
     * Gets the value of the kanbanCardNumberRange1Start property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKanbanCardNumberRange1Start() {
        return kanbanCardNumberRange1Start;
    }

    /**
     * Sets the value of the kanbanCardNumberRange1Start property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKanbanCardNumberRange1Start(String value) {
        this.kanbanCardNumberRange1Start = value;
    }

    /**
     * Gets the value of the kanbanCardNumberRange1End property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKanbanCardNumberRange1End() {
        return kanbanCardNumberRange1End;
    }

    /**
     * Sets the value of the kanbanCardNumberRange1End property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKanbanCardNumberRange1End(String value) {
        this.kanbanCardNumberRange1End = value;
    }

}
