package de.metas.shipping.model;

<<<<<<< HEAD

/** Generated Interface for M_ShippingPackage
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_ShippingPackage 
{

    /** TableName=M_ShippingPackage */
    public static final String Table_Name = "M_ShippingPackage";

    /** AD_Table_ID=540031 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(1);

    /** Load Meta Data */
=======
import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_ShippingPackage
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_ShippingPackage 
{

	String Table_Name = "M_ShippingPackage";

//	/** AD_Table_ID=540031 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Act Delivered Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setActualDischargeQuantity (BigDecimal ActualDischargeQuantity);

	/**
	 * Get Act Delivered Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getActualDischargeQuantity();

	ModelColumn<I_M_ShippingPackage, Object> COLUMN_ActualDischargeQuantity = new ModelColumn<>(I_M_ShippingPackage.class, "ActualDischargeQuantity", null);
	String COLUMNNAME_ActualDischargeQuantity = "ActualDischargeQuantity";

	/**
	 * Set Act Load Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setActualLoadQty (BigDecimal ActualLoadQty);

	/**
	 * Get Act Load Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getActualLoadQty();

	ModelColumn<I_M_ShippingPackage, Object> COLUMN_ActualLoadQty = new ModelColumn<>(I_M_ShippingPackage.class, "ActualLoadQty", null);
	String COLUMNNAME_ActualLoadQty = "ActualLoadQty";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getAD_Client_ID();

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
=======
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
=======
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getAD_Org_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Business Partner .
	 * Identifies a Business Partner
=======
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Batch.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBatch (@Nullable java.lang.String Batch);

	/**
	 * Get Batch.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getBatch();

	ModelColumn<I_M_ShippingPackage, Object> COLUMN_Batch = new ModelColumn<>(I_M_ShippingPackage.class, "Batch", null);
	String COLUMNNAME_Batch = "Batch";

	/**
	 * Set Business Partner.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Business Partner .
	 * Identifies a Business Partner
=======
	void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Business Partner.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getC_BPartner_ID();

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Partner Location.
	 * Identifies the (ship to) address for this Business Partner
=======
	int getC_BPartner_ID();

	String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Location.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Partner Location.
	 * Identifies the (ship to) address for this Business Partner
=======
	void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Location.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getC_BPartner_Location_ID();

    /** Column name C_BPartner_Location_ID */
    public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/**
	 * Set Auftrag.
	 * Auftrag
=======
	int getC_BPartner_Location_ID();

	String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/**
	 * Set Sales order.
	 * Order
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setC_Order_ID (int C_Order_ID);

	/**
	 * Get Auftrag.
	 * Auftrag
=======
	void setC_Order_ID (int C_Order_ID);

	/**
	 * Get Sales order.
	 * Order
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getC_Order_ID();

	public org.compiere.model.I_C_Order getC_Order();

	public void setC_Order(org.compiere.model.I_C_Order C_Order);

    /** Column definition for C_Order_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ShippingPackage, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new org.adempiere.model.ModelColumn<I_M_ShippingPackage, org.compiere.model.I_C_Order>(I_M_ShippingPackage.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
    /** Column name C_Order_ID */
    public static final String COLUMNNAME_C_Order_ID = "C_Order_ID";
=======
	int getC_Order_ID();

	@Nullable org.compiere.model.I_C_Order getC_Order();

	void setC_Order(@Nullable org.compiere.model.I_C_Order C_Order);

	ModelColumn<I_M_ShippingPackage, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new ModelColumn<>(I_M_ShippingPackage.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
	String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/**
	 * Set Orderline.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_OrderLine_ID (int C_OrderLine_ID);

	/**
	 * Get Orderline.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_OrderLine_ID();

	@Nullable org.compiere.model.I_C_OrderLine getC_OrderLine();

	void setC_OrderLine(@Nullable org.compiere.model.I_C_OrderLine C_OrderLine);

	ModelColumn<I_M_ShippingPackage, org.compiere.model.I_C_OrderLine> COLUMN_C_OrderLine_ID = new ModelColumn<>(I_M_ShippingPackage.class, "C_OrderLine_ID", org.compiere.model.I_C_OrderLine.class);
	String COLUMNNAME_C_OrderLine_ID = "C_OrderLine_ID";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_M_ShippingPackage, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_ShippingPackage, Object>(I_M_ShippingPackage.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";
=======
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_ShippingPackage, Object> COLUMN_Created = new ModelColumn<>(I_M_ShippingPackage.class, "Created", null);
	String COLUMNNAME_Created = "Created";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getCreatedBy();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";
=======
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set UOM.
	 * Unit of Measure
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get UOM.
	 * Unit of Measure
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_UOM_ID();

	String COLUMNNAME_C_UOM_ID = "C_UOM_ID";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setIsActive (boolean IsActive);
=======
	void setIsActive (boolean IsActive);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_M_ShippingPackage, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_ShippingPackage, Object>(I_M_ShippingPackage.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Abholung.
=======
	boolean isActive();

	ModelColumn<I_M_ShippingPackage, Object> COLUMN_IsActive = new ModelColumn<>(I_M_ShippingPackage.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Fetched.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setIsToBeFetched (boolean IsToBeFetched);

	/**
	 * Get Abholung.
=======
	void setIsToBeFetched (boolean IsToBeFetched);

	/**
	 * Get Fetched.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public boolean isToBeFetched();

    /** Column definition for IsToBeFetched */
    public static final org.adempiere.model.ModelColumn<I_M_ShippingPackage, Object> COLUMN_IsToBeFetched = new org.adempiere.model.ModelColumn<I_M_ShippingPackage, Object>(I_M_ShippingPackage.class, "IsToBeFetched", null);
    /** Column name IsToBeFetched */
    public static final String COLUMNNAME_IsToBeFetched = "IsToBeFetched";

	/**
	 * Set Lieferung/Wareneingang.
=======
	boolean isToBeFetched();

	ModelColumn<I_M_ShippingPackage, Object> COLUMN_IsToBeFetched = new ModelColumn<>(I_M_ShippingPackage.class, "IsToBeFetched", null);
	String COLUMNNAME_IsToBeFetched = "IsToBeFetched";

	/**
	 * Set Shipment/ Receipt.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Material Shipment Document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setM_InOut_ID (int M_InOut_ID);

	/**
	 * Get Lieferung/Wareneingang.
=======
	void setM_InOut_ID (int M_InOut_ID);

	/**
	 * Get Shipment/ Receipt.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Material Shipment Document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getM_InOut_ID();

	public org.compiere.model.I_M_InOut getM_InOut();

	public void setM_InOut(org.compiere.model.I_M_InOut M_InOut);

    /** Column definition for M_InOut_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ShippingPackage, org.compiere.model.I_M_InOut> COLUMN_M_InOut_ID = new org.adempiere.model.ModelColumn<I_M_ShippingPackage, org.compiere.model.I_M_InOut>(I_M_ShippingPackage.class, "M_InOut_ID", org.compiere.model.I_M_InOut.class);
    /** Column name M_InOut_ID */
    public static final String COLUMNNAME_M_InOut_ID = "M_InOut_ID";
=======
	int getM_InOut_ID();

	@Nullable org.compiere.model.I_M_InOut getM_InOut();

	void setM_InOut(@Nullable org.compiere.model.I_M_InOut M_InOut);

	ModelColumn<I_M_ShippingPackage, org.compiere.model.I_M_InOut> COLUMN_M_InOut_ID = new ModelColumn<>(I_M_ShippingPackage.class, "M_InOut_ID", org.compiere.model.I_M_InOut.class);
	String COLUMNNAME_M_InOut_ID = "M_InOut_ID";

	/**
	 * Set Locator.
	 * Warehouse Locator
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Locator_ID (int M_Locator_ID);

	/**
	 * Get Locator.
	 * Warehouse Locator
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Locator_ID();

	String COLUMNNAME_M_Locator_ID = "M_Locator_ID";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Package.
	 * Shipment Package
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setM_Package_ID (int M_Package_ID);
=======
	void setM_Package_ID (int M_Package_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Package.
	 * Shipment Package
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getM_Package_ID();

	public org.compiere.model.I_M_Package getM_Package();

	public void setM_Package(org.compiere.model.I_M_Package M_Package);

    /** Column definition for M_Package_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ShippingPackage, org.compiere.model.I_M_Package> COLUMN_M_Package_ID = new org.adempiere.model.ModelColumn<I_M_ShippingPackage, org.compiere.model.I_M_Package>(I_M_ShippingPackage.class, "M_Package_ID", org.compiere.model.I_M_Package.class);
    /** Column name M_Package_ID */
    public static final String COLUMNNAME_M_Package_ID = "M_Package_ID";

	/**
	 * Set Transport Auftrag.
=======
	int getM_Package_ID();

	org.compiere.model.I_M_Package getM_Package();

	void setM_Package(org.compiere.model.I_M_Package M_Package);

	ModelColumn<I_M_ShippingPackage, org.compiere.model.I_M_Package> COLUMN_M_Package_ID = new ModelColumn<>(I_M_ShippingPackage.class, "M_Package_ID", org.compiere.model.I_M_Package.class);
	String COLUMNNAME_M_Package_ID = "M_Package_ID";

	/**
	 * Set Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Product_ID();

	String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Transportation Order.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setM_ShipperTransportation_ID (int M_ShipperTransportation_ID);

	/**
	 * Get Transport Auftrag.
=======
	void setM_ShipperTransportation_ID (int M_ShipperTransportation_ID);

	/**
	 * Get Transportation Order.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getM_ShipperTransportation_ID();

	public de.metas.shipping.model.I_M_ShipperTransportation getM_ShipperTransportation();

	public void setM_ShipperTransportation(de.metas.shipping.model.I_M_ShipperTransportation M_ShipperTransportation);

    /** Column definition for M_ShipperTransportation_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ShippingPackage, de.metas.shipping.model.I_M_ShipperTransportation> COLUMN_M_ShipperTransportation_ID = new org.adempiere.model.ModelColumn<I_M_ShippingPackage, de.metas.shipping.model.I_M_ShipperTransportation>(I_M_ShippingPackage.class, "M_ShipperTransportation_ID", de.metas.shipping.model.I_M_ShipperTransportation.class);
    /** Column name M_ShipperTransportation_ID */
    public static final String COLUMNNAME_M_ShipperTransportation_ID = "M_ShipperTransportation_ID";
=======
	int getM_ShipperTransportation_ID();

	de.metas.shipping.model.I_M_ShipperTransportation getM_ShipperTransportation();

	void setM_ShipperTransportation(de.metas.shipping.model.I_M_ShipperTransportation M_ShipperTransportation);

	ModelColumn<I_M_ShippingPackage, de.metas.shipping.model.I_M_ShipperTransportation> COLUMN_M_ShipperTransportation_ID = new ModelColumn<>(I_M_ShippingPackage.class, "M_ShipperTransportation_ID", de.metas.shipping.model.I_M_ShipperTransportation.class);
	String COLUMNNAME_M_ShipperTransportation_ID = "M_ShipperTransportation_ID";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Shipping Package.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setM_ShippingPackage_ID (int M_ShippingPackage_ID);
=======
	void setM_ShippingPackage_ID (int M_ShippingPackage_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Shipping Package.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getM_ShippingPackage_ID();

    /** Column definition for M_ShippingPackage_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ShippingPackage, Object> COLUMN_M_ShippingPackage_ID = new org.adempiere.model.ModelColumn<I_M_ShippingPackage, Object>(I_M_ShippingPackage.class, "M_ShippingPackage_ID", null);
    /** Column name M_ShippingPackage_ID */
    public static final String COLUMNNAME_M_ShippingPackage_ID = "M_ShippingPackage_ID";

	/**
	 * Set Notiz.
	 * Optional weitere Information
=======
	int getM_ShippingPackage_ID();

	ModelColumn<I_M_ShippingPackage, Object> COLUMN_M_ShippingPackage_ID = new ModelColumn<>(I_M_ShippingPackage.class, "M_ShippingPackage_ID", null);
	String COLUMNNAME_M_ShippingPackage_ID = "M_ShippingPackage_ID";

	/**
	 * Set Note.
	 * Optional additional user defined information
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setNote (java.lang.String Note);

	/**
	 * Get Notiz.
	 * Optional weitere Information
=======
	void setNote (@Nullable java.lang.String Note);

	/**
	 * Get Note.
	 * Optional additional user defined information
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getNote();

    /** Column definition for Note */
    public static final org.adempiere.model.ModelColumn<I_M_ShippingPackage, Object> COLUMN_Note = new org.adempiere.model.ModelColumn<I_M_ShippingPackage, Object>(I_M_ShippingPackage.class, "Note", null);
    /** Column name Note */
    public static final String COLUMNNAME_Note = "Note";
=======
	@Nullable java.lang.String getNote();

	ModelColumn<I_M_ShippingPackage, Object> COLUMN_Note = new ModelColumn<>(I_M_ShippingPackage.class, "Note", null);
	String COLUMNNAME_Note = "Note";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Package Net Total.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setPackageNetTotal (java.math.BigDecimal PackageNetTotal);
=======
	void setPackageNetTotal (BigDecimal PackageNetTotal);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Package Net Total.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.math.BigDecimal getPackageNetTotal();

    /** Column definition for PackageNetTotal */
    public static final org.adempiere.model.ModelColumn<I_M_ShippingPackage, Object> COLUMN_PackageNetTotal = new org.adempiere.model.ModelColumn<I_M_ShippingPackage, Object>(I_M_ShippingPackage.class, "PackageNetTotal", null);
    /** Column name PackageNetTotal */
    public static final String COLUMNNAME_PackageNetTotal = "PackageNetTotal";

	/**
	 * Set Package Weight.
=======
	BigDecimal getPackageNetTotal();

	ModelColumn<I_M_ShippingPackage, Object> COLUMN_PackageNetTotal = new ModelColumn<>(I_M_ShippingPackage.class, "PackageNetTotal", null);
	String COLUMNNAME_PackageNetTotal = "PackageNetTotal";

	/**
	 * Set Weight.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Weight of a package
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setPackageWeight (java.math.BigDecimal PackageWeight);

	/**
	 * Get Package Weight.
=======
	void setPackageWeight (@Nullable BigDecimal PackageWeight);

	/**
	 * Get Weight.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Weight of a package
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.math.BigDecimal getPackageWeight();

    /** Column definition for PackageWeight */
    public static final org.adempiere.model.ModelColumn<I_M_ShippingPackage, Object> COLUMN_PackageWeight = new org.adempiere.model.ModelColumn<I_M_ShippingPackage, Object>(I_M_ShippingPackage.class, "PackageWeight", null);
    /** Column name PackageWeight */
    public static final String COLUMNNAME_PackageWeight = "PackageWeight";

	/**
	 * Set Verarbeitet.
	 * Checkbox sagt aus, ob der Datensatz verarbeitet wurde.
=======
	BigDecimal getPackageWeight();

	ModelColumn<I_M_ShippingPackage, Object> COLUMN_PackageWeight = new ModelColumn<>(I_M_ShippingPackage.class, "PackageWeight", null);
	String COLUMNNAME_PackageWeight = "PackageWeight";

	/**
	 * Set Processed.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setProcessed (boolean Processed);

	/**
	 * Get Verarbeitet.
	 * Checkbox sagt aus, ob der Datensatz verarbeitet wurde.
=======
	void setProcessed (boolean Processed);

	/**
	 * Get Processed.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public boolean isProcessed();

    /** Column definition for Processed */
    public static final org.adempiere.model.ModelColumn<I_M_ShippingPackage, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_M_ShippingPackage, Object>(I_M_ShippingPackage.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";
=======
	boolean isProcessed();

	ModelColumn<I_M_ShippingPackage, Object> COLUMN_Processed = new ModelColumn<>(I_M_ShippingPackage.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Product Name.
	 * Name of the Product
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setProductName (@Nullable java.lang.String ProductName);

	/**
	 * Get Product Name.
	 * Name of the Product
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	@Nullable java.lang.String getProductName();

	ModelColumn<I_M_ShippingPackage, Object> COLUMN_ProductName = new ModelColumn<>(I_M_ShippingPackage.class, "ProductName", null);
	String COLUMNNAME_ProductName = "ProductName";

	/**
	 * Set Product Value.
	 * Product identifier;
 "val-<search key>", "ext-<external id>" or internal M_Product_ID
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setProductValue (@Nullable java.lang.String ProductValue);

	/**
	 * Get Product Value.
	 * Product identifier;
 "val-<search key>", "ext-<external id>" or internal M_Product_ID
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	@Nullable java.lang.String getProductValue();

	ModelColumn<I_M_ShippingPackage, Object> COLUMN_ProductValue = new ModelColumn<>(I_M_ShippingPackage.class, "ProductValue", null);
	String COLUMNNAME_ProductValue = "ProductValue";

	/**
	 * Set Number of LUs.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyLU (@Nullable BigDecimal QtyLU);

	/**
	 * Get Number of LUs.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyLU();

	ModelColumn<I_M_ShippingPackage, Object> COLUMN_QtyLU = new ModelColumn<>(I_M_ShippingPackage.class, "QtyLU", null);
	String COLUMNNAME_QtyLU = "QtyLU";

	/**
	 * Set Number of TUs.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyTU (@Nullable BigDecimal QtyTU);

	/**
	 * Get Number of TUs.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyTU();

	ModelColumn<I_M_ShippingPackage, Object> COLUMN_QtyTU = new ModelColumn<>(I_M_ShippingPackage.class, "QtyTU", null);
	String COLUMNNAME_QtyTU = "QtyTU";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_M_ShippingPackage, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_ShippingPackage, Object>(I_M_ShippingPackage.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";
=======
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_ShippingPackage, Object> COLUMN_Updated = new ModelColumn<>(I_M_ShippingPackage.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getUpdatedBy();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
=======
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}
