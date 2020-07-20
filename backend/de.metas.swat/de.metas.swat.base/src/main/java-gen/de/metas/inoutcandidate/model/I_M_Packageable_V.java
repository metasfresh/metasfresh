package de.metas.inoutcandidate.model;


/** Generated Interface for M_Packageable_V
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_Packageable_V 
{

    /** TableName=M_Packageable_V */
    public static final String Table_Name = "M_Packageable_V";

    /** AD_Table_ID=540823 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Anschrift-Text abw..
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBPartnerAddress_Override (java.lang.String BPartnerAddress_Override);

	/**
	 * Get Anschrift-Text abw..
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getBPartnerAddress_Override();

    /** Column definition for BPartnerAddress_Override */
    public static final org.adempiere.model.ModelColumn<I_M_Packageable_V, Object> COLUMN_BPartnerAddress_Override = new org.adempiere.model.ModelColumn<I_M_Packageable_V, Object>(I_M_Packageable_V.class, "BPartnerAddress_Override", null);
    /** Column name BPartnerAddress_Override */
    public static final String COLUMNNAME_BPartnerAddress_Override = "BPartnerAddress_Override";

	/**
	 * Set BPartner location name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBPartnerLocationName (java.lang.String BPartnerLocationName);

	/**
	 * Get BPartner location name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getBPartnerLocationName();

    /** Column definition for BPartnerLocationName */
    public static final org.adempiere.model.ModelColumn<I_M_Packageable_V, Object> COLUMN_BPartnerLocationName = new org.adempiere.model.ModelColumn<I_M_Packageable_V, Object>(I_M_Packageable_V.class, "BPartnerLocationName", null);
    /** Column name BPartnerLocationName */
    public static final String COLUMNNAME_BPartnerLocationName = "BPartnerLocationName";

	/**
	 * Set Partner Name.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBPartnerName (java.lang.String BPartnerName);

	/**
	 * Get Partner Name.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getBPartnerName();

    /** Column definition for BPartnerName */
    public static final org.adempiere.model.ModelColumn<I_M_Packageable_V, Object> COLUMN_BPartnerName = new org.adempiere.model.ModelColumn<I_M_Packageable_V, Object>(I_M_Packageable_V.class, "BPartnerName", null);
    /** Column name BPartnerName */
    public static final String COLUMNNAME_BPartnerName = "BPartnerName";

	/**
	 * Set Geschäftspartner-Schlüssel.
	 * Key of the Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBPartnerValue (java.lang.String BPartnerValue);

	/**
	 * Get Geschäftspartner-Schlüssel.
	 * Key of the Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getBPartnerValue();

    /** Column definition for BPartnerValue */
    public static final org.adempiere.model.ModelColumn<I_M_Packageable_V, Object> COLUMN_BPartnerValue = new org.adempiere.model.ModelColumn<I_M_Packageable_V, Object>(I_M_Packageable_V.class, "BPartnerValue", null);
    /** Column name BPartnerValue */
    public static final String COLUMNNAME_BPartnerValue = "BPartnerValue";

	/**
	 * Set Business Partner .
	 * Identifies a Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_Customer_ID (int C_BPartner_Customer_ID);

	/**
	 * Get Business Partner .
	 * Identifies a Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_Customer_ID();

    /** Column name C_BPartner_Customer_ID */
    public static final String COLUMNNAME_C_BPartner_Customer_ID = "C_BPartner_Customer_ID";

	/**
	 * Set Location.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Location.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_Location_ID();

    /** Column name C_BPartner_Location_ID */
    public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/**
	 * Set Currency.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Currency.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Currency_ID();

    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set Auftragsposition.
	 * Auftragsposition
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_OrderLineSO_ID (int C_OrderLineSO_ID);

	/**
	 * Get Auftragsposition.
	 * Auftragsposition
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_OrderLineSO_ID();

	public org.compiere.model.I_C_OrderLine getC_OrderLineSO();

	public void setC_OrderLineSO(org.compiere.model.I_C_OrderLine C_OrderLineSO);

    /** Column definition for C_OrderLineSO_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Packageable_V, org.compiere.model.I_C_OrderLine> COLUMN_C_OrderLineSO_ID = new org.adempiere.model.ModelColumn<I_M_Packageable_V, org.compiere.model.I_C_OrderLine>(I_M_Packageable_V.class, "C_OrderLineSO_ID", org.compiere.model.I_C_OrderLine.class);
    /** Column name C_OrderLineSO_ID */
    public static final String COLUMNNAME_C_OrderLineSO_ID = "C_OrderLineSO_ID";

	/**
	 * Set Auftrag.
	 * Auftrag
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_OrderSO_ID (int C_OrderSO_ID);

	/**
	 * Get Auftrag.
	 * Auftrag
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_OrderSO_ID();

	public org.compiere.model.I_C_Order getC_OrderSO();

	public void setC_OrderSO(org.compiere.model.I_C_Order C_OrderSO);

    /** Column definition for C_OrderSO_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Packageable_V, org.compiere.model.I_C_Order> COLUMN_C_OrderSO_ID = new org.adempiere.model.ModelColumn<I_M_Packageable_V, org.compiere.model.I_C_Order>(I_M_Packageable_V.class, "C_OrderSO_ID", org.compiere.model.I_C_Order.class);
    /** Column name C_OrderSO_ID */
    public static final String COLUMNNAME_C_OrderSO_ID = "C_OrderSO_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_M_Packageable_V, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_Packageable_V, Object>(I_M_Packageable_V.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set UOM.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get UOM.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_UOM_ID();

    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Set Date.
	 * Date of Order
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateOrdered (java.sql.Timestamp DateOrdered);

	/**
	 * Get Date.
	 * Date of Order
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateOrdered();

    /** Column definition for DateOrdered */
    public static final org.adempiere.model.ModelColumn<I_M_Packageable_V, Object> COLUMN_DateOrdered = new org.adempiere.model.ModelColumn<I_M_Packageable_V, Object>(I_M_Packageable_V.class, "DateOrdered", null);
    /** Column name DateOrdered */
    public static final String COLUMNNAME_DateOrdered = "DateOrdered";

	/**
	 * Set Shipmentdate.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDeliveryDate (java.sql.Timestamp DeliveryDate);

	/**
	 * Get Shipmentdate.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDeliveryDate();

    /** Column definition for DeliveryDate */
    public static final org.adempiere.model.ModelColumn<I_M_Packageable_V, Object> COLUMN_DeliveryDate = new org.adempiere.model.ModelColumn<I_M_Packageable_V, Object>(I_M_Packageable_V.class, "DeliveryDate", null);
    /** Column name DeliveryDate */
    public static final String COLUMNNAME_DeliveryDate = "DeliveryDate";

	/**
	 * Set Lieferung.
	 * Wie der Auftrag geliefert wird
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDeliveryViaRule (java.lang.String DeliveryViaRule);

	/**
	 * Get Lieferung.
	 * Wie der Auftrag geliefert wird
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDeliveryViaRule();

    /** Column definition for DeliveryViaRule */
    public static final org.adempiere.model.ModelColumn<I_M_Packageable_V, Object> COLUMN_DeliveryViaRule = new org.adempiere.model.ModelColumn<I_M_Packageable_V, Object>(I_M_Packageable_V.class, "DeliveryViaRule", null);
    /** Column name DeliveryViaRule */
    public static final String COLUMNNAME_DeliveryViaRule = "DeliveryViaRule";

	/**
	 * Set Doc Sub Type.
	 * Document Sub Type
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDocSubType (java.lang.String DocSubType);

	/**
	 * Get Doc Sub Type.
	 * Document Sub Type
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocSubType();

    /** Column definition for DocSubType */
    public static final org.adempiere.model.ModelColumn<I_M_Packageable_V, Object> COLUMN_DocSubType = new org.adempiere.model.ModelColumn<I_M_Packageable_V, Object>(I_M_Packageable_V.class, "DocSubType", null);
    /** Column name DocSubType */
    public static final String COLUMNNAME_DocSubType = "DocSubType";

	/**
	 * Set Frachtkostenberechnung.
	 * Methode zur Berechnung von Frachtkosten
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setFreightCostRule (java.lang.String FreightCostRule);

	/**
	 * Get Frachtkostenberechnung.
	 * Methode zur Berechnung von Frachtkosten
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getFreightCostRule();

    /** Column definition for FreightCostRule */
    public static final org.adempiere.model.ModelColumn<I_M_Packageable_V, Object> COLUMN_FreightCostRule = new org.adempiere.model.ModelColumn<I_M_Packageable_V, Object>(I_M_Packageable_V.class, "FreightCostRule", null);
    /** Column name FreightCostRule */
    public static final String COLUMNNAME_FreightCostRule = "FreightCostRule";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_M_Packageable_V, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_Packageable_V, Object>(I_M_Packageable_V.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Displayed.
	 * Determines, if this field is displayed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsDisplayed (boolean IsDisplayed);

	/**
	 * Get Displayed.
	 * Determines, if this field is displayed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isDisplayed();

    /** Column definition for IsDisplayed */
    public static final org.adempiere.model.ModelColumn<I_M_Packageable_V, Object> COLUMN_IsDisplayed = new org.adempiere.model.ModelColumn<I_M_Packageable_V, Object>(I_M_Packageable_V.class, "IsDisplayed", null);
    /** Column name IsDisplayed */
    public static final String COLUMNNAME_IsDisplayed = "IsDisplayed";

	/**
	 * Set Line Net Amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLineNetAmt (java.math.BigDecimal LineNetAmt);

	/**
	 * Get Line Net Amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getLineNetAmt();

    /** Column definition for LineNetAmt */
    public static final org.adempiere.model.ModelColumn<I_M_Packageable_V, Object> COLUMN_LineNetAmt = new org.adempiere.model.ModelColumn<I_M_Packageable_V, Object>(I_M_Packageable_V.class, "LineNetAmt", null);
    /** Column name LineNetAmt */
    public static final String COLUMNNAME_LineNetAmt = "LineNetAmt";

	/**
	 * Set lockedby_user_id.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLockedBy_User_ID (int LockedBy_User_ID);

	/**
	 * Get lockedby_user_id.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getLockedBy_User_ID();

    /** Column name LockedBy_User_ID */
    public static final String COLUMNNAME_LockedBy_User_ID = "LockedBy_User_ID";

	/**
	 * Set Merkmale.
	 * Merkmals Ausprägungen zum Produkt
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID);

	/**
	 * Get Merkmale.
	 * Merkmals Ausprägungen zum Produkt
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_AttributeSetInstance_ID();

	public org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance();

	public void setM_AttributeSetInstance(org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance);

    /** Column definition for M_AttributeSetInstance_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Packageable_V, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new org.adempiere.model.ModelColumn<I_M_Packageable_V, org.compiere.model.I_M_AttributeSetInstance>(I_M_Packageable_V.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
    /** Column name M_AttributeSetInstance_ID */
    public static final String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/**
	 * Set Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Product_ID();

    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Shipment Candidate.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_ShipmentSchedule_ID (int M_ShipmentSchedule_ID);

	/**
	 * Get Shipment Candidate.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_ShipmentSchedule_ID();

    /** Column definition for M_ShipmentSchedule_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Packageable_V, Object> COLUMN_M_ShipmentSchedule_ID = new org.adempiere.model.ModelColumn<I_M_Packageable_V, Object>(I_M_Packageable_V.class, "M_ShipmentSchedule_ID", null);
    /** Column name M_ShipmentSchedule_ID */
    public static final String COLUMNNAME_M_ShipmentSchedule_ID = "M_ShipmentSchedule_ID";

	/**
	 * Set Shipper.
	 * Method or manner of product delivery
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Shipper_ID (int M_Shipper_ID);

	/**
	 * Get Shipper.
	 * Method or manner of product delivery
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Shipper_ID();

	public org.compiere.model.I_M_Shipper getM_Shipper();

	public void setM_Shipper(org.compiere.model.I_M_Shipper M_Shipper);

    /** Column definition for M_Shipper_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Packageable_V, org.compiere.model.I_M_Shipper> COLUMN_M_Shipper_ID = new org.adempiere.model.ModelColumn<I_M_Packageable_V, org.compiere.model.I_M_Shipper>(I_M_Packageable_V.class, "M_Shipper_ID", org.compiere.model.I_M_Shipper.class);
    /** Column name M_Shipper_ID */
    public static final String COLUMNNAME_M_Shipper_ID = "M_Shipper_ID";

	/**
	 * Set Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Warehouse_ID (int M_Warehouse_ID);

	/**
	 * Get Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Warehouse_ID();

    /** Column name M_Warehouse_ID */
    public static final String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/**
	 * Set Warehouse Type.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Warehouse_Type_ID (int M_Warehouse_Type_ID);

	/**
	 * Get Warehouse Type.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Warehouse_Type_ID();

	public org.compiere.model.I_M_Warehouse_Type getM_Warehouse_Type();

	public void setM_Warehouse_Type(org.compiere.model.I_M_Warehouse_Type M_Warehouse_Type);

    /** Column definition for M_Warehouse_Type_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Packageable_V, org.compiere.model.I_M_Warehouse_Type> COLUMN_M_Warehouse_Type_ID = new org.adempiere.model.ModelColumn<I_M_Packageable_V, org.compiere.model.I_M_Warehouse_Type>(I_M_Packageable_V.class, "M_Warehouse_Type_ID", org.compiere.model.I_M_Warehouse_Type.class);
    /** Column name M_Warehouse_Type_ID */
    public static final String COLUMNNAME_M_Warehouse_Type_ID = "M_Warehouse_Type_ID";

	/**
	 * Set Order Document No.
	 * Document Number of the Order
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setOrderDocumentNo (java.lang.String OrderDocumentNo);

	/**
	 * Get Order Document No.
	 * Document Number of the Order
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getOrderDocumentNo();

    /** Column definition for OrderDocumentNo */
    public static final org.adempiere.model.ModelColumn<I_M_Packageable_V, Object> COLUMN_OrderDocumentNo = new org.adempiere.model.ModelColumn<I_M_Packageable_V, Object>(I_M_Packageable_V.class, "OrderDocumentNo", null);
    /** Column name OrderDocumentNo */
    public static final String COLUMNNAME_OrderDocumentNo = "OrderDocumentNo";

	/**
	 * Set Pick From Order.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPickFrom_Order_ID (int PickFrom_Order_ID);

	/**
	 * Get Pick From Order.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getPickFrom_Order_ID();

	public org.eevolution.model.I_PP_Order getPickFrom_Order();

	public void setPickFrom_Order(org.eevolution.model.I_PP_Order PickFrom_Order);

    /** Column definition for PickFrom_Order_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Packageable_V, org.eevolution.model.I_PP_Order> COLUMN_PickFrom_Order_ID = new org.adempiere.model.ModelColumn<I_M_Packageable_V, org.eevolution.model.I_PP_Order>(I_M_Packageable_V.class, "PickFrom_Order_ID", org.eevolution.model.I_PP_Order.class);
    /** Column name PickFrom_Order_ID */
    public static final String COLUMNNAME_PickFrom_Order_ID = "PickFrom_Order_ID";

	/**
	 * Set Order Reference.
	 * Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPOReference (java.lang.String POReference);

	/**
	 * Get Order Reference.
	 * Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPOReference();

    /** Column definition for POReference */
    public static final org.adempiere.model.ModelColumn<I_M_Packageable_V, Object> COLUMN_POReference = new org.adempiere.model.ModelColumn<I_M_Packageable_V, Object>(I_M_Packageable_V.class, "POReference", null);
    /** Column name POReference */
    public static final String COLUMNNAME_POReference = "POReference";

	/**
	 * Set Bereitstellungsdatum.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPreparationDate (java.sql.Timestamp PreparationDate);

	/**
	 * Get Bereitstellungsdatum.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getPreparationDate();

    /** Column definition for PreparationDate */
    public static final org.adempiere.model.ModelColumn<I_M_Packageable_V, Object> COLUMN_PreparationDate = new org.adempiere.model.ModelColumn<I_M_Packageable_V, Object>(I_M_Packageable_V.class, "PreparationDate", null);
    /** Column name PreparationDate */
    public static final String COLUMNNAME_PreparationDate = "PreparationDate";

	/**
	 * Set Priorität.
	 * Priority of a document
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPriorityRule (java.lang.String PriorityRule);

	/**
	 * Get Priorität.
	 * Priority of a document
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPriorityRule();

    /** Column definition for PriorityRule */
    public static final org.adempiere.model.ModelColumn<I_M_Packageable_V, Object> COLUMN_PriorityRule = new org.adempiere.model.ModelColumn<I_M_Packageable_V, Object>(I_M_Packageable_V.class, "PriorityRule", null);
    /** Column name PriorityRule */
    public static final String COLUMNNAME_PriorityRule = "PriorityRule";

	/**
	 * Set Produktname.
	 * Name des Produktes
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProductName (java.lang.String ProductName);

	/**
	 * Get Produktname.
	 * Name des Produktes
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getProductName();

    /** Column definition for ProductName */
    public static final org.adempiere.model.ModelColumn<I_M_Packageable_V, Object> COLUMN_ProductName = new org.adempiere.model.ModelColumn<I_M_Packageable_V, Object>(I_M_Packageable_V.class, "ProductName", null);
    /** Column name ProductName */
    public static final String COLUMNNAME_ProductName = "ProductName";

	/**
	 * Set Gelieferte Menge.
	 * Gelieferte Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyDelivered (java.math.BigDecimal QtyDelivered);

	/**
	 * Get Gelieferte Menge.
	 * Gelieferte Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyDelivered();

    /** Column definition for QtyDelivered */
    public static final org.adempiere.model.ModelColumn<I_M_Packageable_V, Object> COLUMN_QtyDelivered = new org.adempiere.model.ModelColumn<I_M_Packageable_V, Object>(I_M_Packageable_V.class, "QtyDelivered", null);
    /** Column name QtyDelivered */
    public static final String COLUMNNAME_QtyDelivered = "QtyDelivered";

	/**
	 * Set Qty Ordered.
	 * Qty Ordered
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyOrdered (java.math.BigDecimal QtyOrdered);

	/**
	 * Get Qty Ordered.
	 * Qty Ordered
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyOrdered();

    /** Column definition for QtyOrdered */
    public static final org.adempiere.model.ModelColumn<I_M_Packageable_V, Object> COLUMN_QtyOrdered = new org.adempiere.model.ModelColumn<I_M_Packageable_V, Object>(I_M_Packageable_V.class, "QtyOrdered", null);
    /** Column name QtyOrdered */
    public static final String COLUMNNAME_QtyOrdered = "QtyOrdered";

	/**
	 * Set Menge kommissioniert und geliefert.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyPickedAndDelivered (java.math.BigDecimal QtyPickedAndDelivered);

	/**
	 * Get Menge kommissioniert und geliefert.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyPickedAndDelivered();

    /** Column definition for QtyPickedAndDelivered */
    public static final org.adempiere.model.ModelColumn<I_M_Packageable_V, Object> COLUMN_QtyPickedAndDelivered = new org.adempiere.model.ModelColumn<I_M_Packageable_V, Object>(I_M_Packageable_V.class, "QtyPickedAndDelivered", null);
    /** Column name QtyPickedAndDelivered */
    public static final String COLUMNNAME_QtyPickedAndDelivered = "QtyPickedAndDelivered";

	/**
	 * Set Qty Picked.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyPickedNotDelivered (java.math.BigDecimal QtyPickedNotDelivered);

	/**
	 * Get Qty Picked.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyPickedNotDelivered();

    /** Column definition for QtyPickedNotDelivered */
    public static final org.adempiere.model.ModelColumn<I_M_Packageable_V, Object> COLUMN_QtyPickedNotDelivered = new org.adempiere.model.ModelColumn<I_M_Packageable_V, Object>(I_M_Packageable_V.class, "QtyPickedNotDelivered", null);
    /** Column name QtyPickedNotDelivered */
    public static final String COLUMNNAME_QtyPickedNotDelivered = "QtyPickedNotDelivered";

	/**
	 * Set Menge kommissioniert oder geliefert.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyPickedOrDelivered (java.math.BigDecimal QtyPickedOrDelivered);

	/**
	 * Get Menge kommissioniert oder geliefert.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyPickedOrDelivered();

    /** Column definition for QtyPickedOrDelivered */
    public static final org.adempiere.model.ModelColumn<I_M_Packageable_V, Object> COLUMN_QtyPickedOrDelivered = new org.adempiere.model.ModelColumn<I_M_Packageable_V, Object>(I_M_Packageable_V.class, "QtyPickedOrDelivered", null);
    /** Column name QtyPickedOrDelivered */
    public static final String COLUMNNAME_QtyPickedOrDelivered = "QtyPickedOrDelivered";

	/**
	 * Set Qty picked (planned).
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyPickedPlanned (java.math.BigDecimal QtyPickedPlanned);

	/**
	 * Get Qty picked (planned).
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyPickedPlanned();

    /** Column definition for QtyPickedPlanned */
    public static final org.adempiere.model.ModelColumn<I_M_Packageable_V, Object> COLUMN_QtyPickedPlanned = new org.adempiere.model.ModelColumn<I_M_Packageable_V, Object>(I_M_Packageable_V.class, "QtyPickedPlanned", null);
    /** Column name QtyPickedPlanned */
    public static final String COLUMNNAME_QtyPickedPlanned = "QtyPickedPlanned";

	/**
	 * Set Ausliefermenge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyToDeliver (java.math.BigDecimal QtyToDeliver);

	/**
	 * Get Ausliefermenge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyToDeliver();

    /** Column definition for QtyToDeliver */
    public static final org.adempiere.model.ModelColumn<I_M_Packageable_V, Object> COLUMN_QtyToDeliver = new org.adempiere.model.ModelColumn<I_M_Packageable_V, Object>(I_M_Packageable_V.class, "QtyToDeliver", null);
    /** Column name QtyToDeliver */
    public static final String COLUMNNAME_QtyToDeliver = "QtyToDeliver";

	/**
	 * Set Best Before Policy.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setShipmentAllocation_BestBefore_Policy (java.lang.String ShipmentAllocation_BestBefore_Policy);

	/**
	 * Get Best Before Policy.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getShipmentAllocation_BestBefore_Policy();

    /** Column definition for ShipmentAllocation_BestBefore_Policy */
    public static final org.adempiere.model.ModelColumn<I_M_Packageable_V, Object> COLUMN_ShipmentAllocation_BestBefore_Policy = new org.adempiere.model.ModelColumn<I_M_Packageable_V, Object>(I_M_Packageable_V.class, "ShipmentAllocation_BestBefore_Policy", null);
    /** Column name ShipmentAllocation_BestBefore_Policy */
    public static final String COLUMNNAME_ShipmentAllocation_BestBefore_Policy = "ShipmentAllocation_BestBefore_Policy";

	/**
	 * Set Shipper name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setShipperName (java.lang.String ShipperName);

	/**
	 * Get Shipper name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getShipperName();

    /** Column definition for ShipperName */
    public static final org.adempiere.model.ModelColumn<I_M_Packageable_V, Object> COLUMN_ShipperName = new org.adempiere.model.ModelColumn<I_M_Packageable_V, Object>(I_M_Packageable_V.class, "ShipperName", null);
    /** Column name ShipperName */
    public static final String COLUMNNAME_ShipperName = "ShipperName";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_M_Packageable_V, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_Packageable_V, Object>(I_M_Packageable_V.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Lager.
	 * Lagerbezeichnung
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setWarehouseName (java.lang.String WarehouseName);

	/**
	 * Get Lager.
	 * Lagerbezeichnung
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getWarehouseName();

    /** Column definition for WarehouseName */
    public static final org.adempiere.model.ModelColumn<I_M_Packageable_V, Object> COLUMN_WarehouseName = new org.adempiere.model.ModelColumn<I_M_Packageable_V, Object>(I_M_Packageable_V.class, "WarehouseName", null);
    /** Column name WarehouseName */
    public static final String COLUMNNAME_WarehouseName = "WarehouseName";
}
