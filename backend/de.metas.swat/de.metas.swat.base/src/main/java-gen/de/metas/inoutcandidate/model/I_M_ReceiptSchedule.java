package de.metas.inoutcandidate.model;


/** Generated Interface for M_ReceiptSchedule
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_ReceiptSchedule 
{

    /** TableName=M_ReceiptSchedule */
    public static final String Table_Name = "M_ReceiptSchedule";

    /** AD_Table_ID=540524 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
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
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Table.
	 * Database Table information
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get Table.
	 * Database Table information
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Table_ID();

    /** Column name AD_Table_ID */
    public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * Set Contact.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_User_ID (int AD_User_ID);

	/**
	 * Get Contact.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_User_ID();

    /** Column name AD_User_ID */
    public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/**
	 * Set Ansprechpartner abw..
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_User_Override_ID (int AD_User_Override_ID);

	/**
	 * Get Ansprechpartner abw..
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_User_Override_ID();

    /** Column name AD_User_Override_ID */
    public static final String COLUMNNAME_AD_User_Override_ID = "AD_User_Override_ID";

	/**
	 * Set Address.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBPartnerAddress (java.lang.String BPartnerAddress);

	/**
	 * Get Address.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getBPartnerAddress();

    /** Column definition for BPartnerAddress */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_BPartnerAddress = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object>(I_M_ReceiptSchedule.class, "BPartnerAddress", null);
    /** Column name BPartnerAddress */
    public static final String COLUMNNAME_BPartnerAddress = "BPartnerAddress";

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
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_BPartnerAddress_Override = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object>(I_M_ReceiptSchedule.class, "BPartnerAddress_Override", null);
    /** Column name BPartnerAddress_Override */
    public static final String COLUMNNAME_BPartnerAddress_Override = "BPartnerAddress_Override";

	/**
	 * Set Can be exported from.
	 * Timestamp from which onwards the record may be exported
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCanBeExportedFrom (java.sql.Timestamp CanBeExportedFrom);

	/**
	 * Get Can be exported from.
	 * Timestamp from which onwards the record may be exported
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCanBeExportedFrom();

    /** Column definition for CanBeExportedFrom */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_CanBeExportedFrom = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object>(I_M_ReceiptSchedule.class, "CanBeExportedFrom", null);
    /** Column name CanBeExportedFrom */
    public static final String COLUMNNAME_CanBeExportedFrom = "CanBeExportedFrom";

	/**
	 * Set Catch UOM.
	 * Catch weight UOM as taken from the product master data.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCatch_UOM_ID (int Catch_UOM_ID);

	/**
	 * Get Catch UOM.
	 * Catch weight UOM as taken from the product master data.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getCatch_UOM_ID();

    /** Column name Catch_UOM_ID */
    public static final String COLUMNNAME_Catch_UOM_ID = "Catch_UOM_ID";

	/**
	 * Set Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Location.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Location.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_Location_ID();

    /** Column name C_BPartner_Location_ID */
    public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/**
	 * Set Geschäftspartner abw..
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_Override_ID (int C_BPartner_Override_ID);

	/**
	 * Get Geschäftspartner abw..
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_Override_ID();

    /** Column name C_BPartner_Override_ID */
    public static final String COLUMNNAME_C_BPartner_Override_ID = "C_BPartner_Override_ID";

	/**
	 * Set Standort abw..
	 * Identifiziert die (Liefer-) Adresse des Geschäftspartners
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BP_Location_Override_ID (int C_BP_Location_Override_ID);

	/**
	 * Get Standort abw..
	 * Identifiziert die (Liefer-) Adresse des Geschäftspartners
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BP_Location_Override_ID();

    /** Column name C_BP_Location_Override_ID */
    public static final String COLUMNNAME_C_BP_Location_Override_ID = "C_BP_Location_Override_ID";

	/**
	 * Set Document Type.
	 * Document type or rules
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_DocType_ID (int C_DocType_ID);

	/**
	 * Get Document Type.
	 * Document type or rules
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_DocType_ID();

    /** Column name C_DocType_ID */
    public static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/**
	 * Set Sales order.
	 * Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Order_ID (int C_Order_ID);

	/**
	 * Get Sales order.
	 * Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Order_ID();

	public org.compiere.model.I_C_Order getC_Order();

	public void setC_Order(org.compiere.model.I_C_Order C_Order);

    /** Column definition for C_Order_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, org.compiere.model.I_C_Order>(I_M_ReceiptSchedule.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
    /** Column name C_Order_ID */
    public static final String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/**
	 * Set Orderline.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_OrderLine_ID (int C_OrderLine_ID);

	/**
	 * Get Orderline.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_OrderLine_ID();

	public org.compiere.model.I_C_OrderLine getC_OrderLine();

	public void setC_OrderLine(org.compiere.model.I_C_OrderLine C_OrderLine);

    /** Column definition for C_OrderLine_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, org.compiere.model.I_C_OrderLine> COLUMN_C_OrderLine_ID = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, org.compiere.model.I_C_OrderLine>(I_M_ReceiptSchedule.class, "C_OrderLine_ID", org.compiere.model.I_C_OrderLine.class);
    /** Column name C_OrderLine_ID */
    public static final String COLUMNNAME_C_OrderLine_ID = "C_OrderLine_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object>(I_M_ReceiptSchedule.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
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
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateOrdered (java.sql.Timestamp DateOrdered);

	/**
	 * Get Date.
	 * Date of Order
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateOrdered();

    /** Column definition for DateOrdered */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_DateOrdered = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object>(I_M_ReceiptSchedule.class, "DateOrdered", null);
    /** Column name DateOrdered */
    public static final String COLUMNNAME_DateOrdered = "DateOrdered";

	/**
	 * Set Lieferart.
	 * Definiert die zeitliche Steuerung von Lieferungen
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDeliveryRule (java.lang.String DeliveryRule);

	/**
	 * Get Lieferart.
	 * Definiert die zeitliche Steuerung von Lieferungen
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDeliveryRule();

    /** Column definition for DeliveryRule */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_DeliveryRule = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object>(I_M_ReceiptSchedule.class, "DeliveryRule", null);
    /** Column name DeliveryRule */
    public static final String COLUMNNAME_DeliveryRule = "DeliveryRule";

	/**
	 * Set Lieferart abw..
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDeliveryRule_Override (java.lang.String DeliveryRule_Override);

	/**
	 * Get Lieferart abw..
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDeliveryRule_Override();

    /** Column definition for DeliveryRule_Override */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_DeliveryRule_Override = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object>(I_M_ReceiptSchedule.class, "DeliveryRule_Override", null);
    /** Column name DeliveryRule_Override */
    public static final String COLUMNNAME_DeliveryRule_Override = "DeliveryRule_Override";

	/**
	 * Set Lieferung.
	 * Wie der Auftrag geliefert wird
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDeliveryViaRule (java.lang.String DeliveryViaRule);

	/**
	 * Get Lieferung.
	 * Wie der Auftrag geliefert wird
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDeliveryViaRule();

    /** Column definition for DeliveryViaRule */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_DeliveryViaRule = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object>(I_M_ReceiptSchedule.class, "DeliveryViaRule", null);
    /** Column name DeliveryViaRule */
    public static final String COLUMNNAME_DeliveryViaRule = "DeliveryViaRule";

	/**
	 * Set Lieferung durch abw..
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDeliveryViaRule_Override (java.lang.String DeliveryViaRule_Override);

	/**
	 * Get Lieferung durch abw..
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDeliveryViaRule_Override();

    /** Column definition for DeliveryViaRule_Override */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_DeliveryViaRule_Override = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object>(I_M_ReceiptSchedule.class, "DeliveryViaRule_Override", null);
    /** Column name DeliveryViaRule_Override */
    public static final String COLUMNNAME_DeliveryViaRule_Override = "DeliveryViaRule_Override";

	/**
	 * Set Export Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setExportStatus (java.lang.String ExportStatus);

	/**
	 * Get Export Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getExportStatus();

    /** Column definition for ExportStatus */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_ExportStatus = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object>(I_M_ReceiptSchedule.class, "ExportStatus", null);
    /** Column name ExportStatus */
    public static final String COLUMNNAME_ExportStatus = "ExportStatus";

	/**
	 * Set Kopf-Aggregationsmerkmal.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setHeaderAggregationKey (java.lang.String HeaderAggregationKey);

	/**
	 * Get Kopf-Aggregationsmerkmal.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getHeaderAggregationKey();

    /** Column definition for HeaderAggregationKey */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_HeaderAggregationKey = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object>(I_M_ReceiptSchedule.class, "HeaderAggregationKey", null);
    /** Column name HeaderAggregationKey */
    public static final String COLUMNNAME_HeaderAggregationKey = "HeaderAggregationKey";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object>(I_M_ReceiptSchedule.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set abw. Anschrift.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsBPartnerAddress_Override (boolean IsBPartnerAddress_Override);

	/**
	 * Get abw. Anschrift.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isBPartnerAddress_Override();

    /** Column definition for IsBPartnerAddress_Override */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_IsBPartnerAddress_Override = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object>(I_M_ReceiptSchedule.class, "IsBPartnerAddress_Override", null);
    /** Column name IsBPartnerAddress_Override */
    public static final String COLUMNNAME_IsBPartnerAddress_Override = "IsBPartnerAddress_Override";

	/**
	 * Set Packaging Material .
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsPackagingMaterial (boolean IsPackagingMaterial);

	/**
	 * Get Packaging Material .
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isPackagingMaterial();

    /** Column definition for IsPackagingMaterial */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_IsPackagingMaterial = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object>(I_M_ReceiptSchedule.class, "IsPackagingMaterial", null);
    /** Column name IsPackagingMaterial */
    public static final String COLUMNNAME_IsPackagingMaterial = "IsPackagingMaterial";

	/**
	 * Set Ausprägung Merkmals-Satz.
	 * Merkmals Ausprägungen zum Produkt
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID);

	/**
	 * Get Ausprägung Merkmals-Satz.
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
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, org.compiere.model.I_M_AttributeSetInstance>(I_M_ReceiptSchedule.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
    /** Column name M_AttributeSetInstance_ID */
    public static final String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/**
	 * Set M_IolCandHandler.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_IolCandHandler_ID (int M_IolCandHandler_ID);

	/**
	 * Get M_IolCandHandler.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_IolCandHandler_ID();

	public de.metas.inoutcandidate.model.I_M_IolCandHandler getM_IolCandHandler();

	public void setM_IolCandHandler(de.metas.inoutcandidate.model.I_M_IolCandHandler M_IolCandHandler);

    /** Column definition for M_IolCandHandler_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, de.metas.inoutcandidate.model.I_M_IolCandHandler> COLUMN_M_IolCandHandler_ID = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, de.metas.inoutcandidate.model.I_M_IolCandHandler>(I_M_ReceiptSchedule.class, "M_IolCandHandler_ID", de.metas.inoutcandidate.model.I_M_IolCandHandler.class);
    /** Column name M_IolCandHandler_ID */
    public static final String COLUMNNAME_M_IolCandHandler_ID = "M_IolCandHandler_ID";

	/**
	 * Set Bewegungs-Datum.
	 * Datum, an dem eine Produkt in oder aus dem Bestand bewegt wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMovementDate (java.sql.Timestamp MovementDate);

	/**
	 * Get Bewegungs-Datum.
	 * Datum, an dem eine Produkt in oder aus dem Bestand bewegt wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getMovementDate();

    /** Column definition for MovementDate */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_MovementDate = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object>(I_M_ReceiptSchedule.class, "MovementDate", null);
    /** Column name MovementDate */
    public static final String COLUMNNAME_MovementDate = "MovementDate";

	/**
	 * Set Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Product_ID();

    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Material Receipt Candidates.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_ReceiptSchedule_ID (int M_ReceiptSchedule_ID);

	/**
	 * Get Material Receipt Candidates.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_ReceiptSchedule_ID();

    /** Column definition for M_ReceiptSchedule_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_M_ReceiptSchedule_ID = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object>(I_M_ReceiptSchedule.class, "M_ReceiptSchedule_ID", null);
    /** Column name M_ReceiptSchedule_ID */
    public static final String COLUMNNAME_M_ReceiptSchedule_ID = "M_ReceiptSchedule_ID";

	/**
	 * Set Destination warehouse locator.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Warehouse_Dest_ID (int M_Warehouse_Dest_ID);

	/**
	 * Get Destination warehouse locator.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Warehouse_Dest_ID();

    /** Column name M_Warehouse_Dest_ID */
    public static final String COLUMNNAME_M_Warehouse_Dest_ID = "M_Warehouse_Dest_ID";

	/**
	 * Set Warehouse Eff..
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setM_Warehouse_Effective_ID (int M_Warehouse_Effective_ID);

	/**
	 * Get Warehouse Eff..
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	public int getM_Warehouse_Effective_ID();

    /** Column name M_Warehouse_Effective_ID */
    public static final String COLUMNNAME_M_Warehouse_Effective_ID = "M_Warehouse_Effective_ID";

	/**
	 * Set Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Warehouse_ID (int M_Warehouse_ID);

	/**
	 * Get Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Warehouse_ID();

    /** Column name M_Warehouse_ID */
    public static final String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/**
	 * Set Lager abw..
	 * Lager oder Ort für Dienstleistung
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Warehouse_Override_ID (int M_Warehouse_Override_ID);

	/**
	 * Get Lager abw..
	 * Lager oder Ort für Dienstleistung
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Warehouse_Override_ID();

    /** Column name M_Warehouse_Override_ID */
    public static final String COLUMNNAME_M_Warehouse_Override_ID = "M_Warehouse_Override_ID";

	/**
	 * Set OnMaterialReceiptWithDestWarehouse.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setOnMaterialReceiptWithDestWarehouse (java.lang.String OnMaterialReceiptWithDestWarehouse);

	/**
	 * Get OnMaterialReceiptWithDestWarehouse.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getOnMaterialReceiptWithDestWarehouse();

    /** Column definition for OnMaterialReceiptWithDestWarehouse */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_OnMaterialReceiptWithDestWarehouse = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object>(I_M_ReceiptSchedule.class, "OnMaterialReceiptWithDestWarehouse", null);
    /** Column name OnMaterialReceiptWithDestWarehouse */
    public static final String COLUMNNAME_OnMaterialReceiptWithDestWarehouse = "OnMaterialReceiptWithDestWarehouse";

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
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_PriorityRule = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object>(I_M_ReceiptSchedule.class, "PriorityRule", null);
    /** Column name PriorityRule */
    public static final String COLUMNNAME_PriorityRule = "PriorityRule";

	/**
	 * Set Priorität Abw..
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPriorityRule_Override (java.lang.String PriorityRule_Override);

	/**
	 * Get Priorität Abw..
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPriorityRule_Override();

    /** Column definition for PriorityRule_Override */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_PriorityRule_Override = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object>(I_M_ReceiptSchedule.class, "PriorityRule_Override", null);
    /** Column name PriorityRule_Override */
    public static final String COLUMNNAME_PriorityRule_Override = "PriorityRule_Override";

	/**
	 * Set Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setProcessed (boolean Processed);

	/**
	 * Get Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isProcessed();

    /** Column definition for Processed */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object>(I_M_ReceiptSchedule.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Bewegte Menge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyMoved (java.math.BigDecimal QtyMoved);

	/**
	 * Get Bewegte Menge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyMoved();

    /** Column definition for QtyMoved */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_QtyMoved = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object>(I_M_ReceiptSchedule.class, "QtyMoved", null);
    /** Column name QtyMoved */
    public static final String COLUMNNAME_QtyMoved = "QtyMoved";

	/**
	 * Set Moved catch quantity.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyMovedInCatchUOM (java.math.BigDecimal QtyMovedInCatchUOM);

	/**
	 * Get Moved catch quantity.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyMovedInCatchUOM();

    /** Column definition for QtyMovedInCatchUOM */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_QtyMovedInCatchUOM = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object>(I_M_ReceiptSchedule.class, "QtyMovedInCatchUOM", null);
    /** Column name QtyMovedInCatchUOM */
    public static final String COLUMNNAME_QtyMovedInCatchUOM = "QtyMovedInCatchUOM";

	/**
	 * Set Qty Moved (With Issues).
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyMovedWithIssues (java.math.BigDecimal QtyMovedWithIssues);

	/**
	 * Get Qty Moved (With Issues).
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyMovedWithIssues();

    /** Column definition for QtyMovedWithIssues */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_QtyMovedWithIssues = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object>(I_M_ReceiptSchedule.class, "QtyMovedWithIssues", null);
    /** Column name QtyMovedWithIssues */
    public static final String COLUMNNAME_QtyMovedWithIssues = "QtyMovedWithIssues";

	/**
	 * Set Moved catch quantity with issues.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyMovedWithIssuesInCatchUOM (java.math.BigDecimal QtyMovedWithIssuesInCatchUOM);

	/**
	 * Get Moved catch quantity with issues.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyMovedWithIssuesInCatchUOM();

    /** Column definition for QtyMovedWithIssuesInCatchUOM */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_QtyMovedWithIssuesInCatchUOM = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object>(I_M_ReceiptSchedule.class, "QtyMovedWithIssuesInCatchUOM", null);
    /** Column name QtyMovedWithIssuesInCatchUOM */
    public static final String COLUMNNAME_QtyMovedWithIssuesInCatchUOM = "QtyMovedWithIssuesInCatchUOM";

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
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_QtyOrdered = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object>(I_M_ReceiptSchedule.class, "QtyOrdered", null);
    /** Column name QtyOrdered */
    public static final String COLUMNNAME_QtyOrdered = "QtyOrdered";

	/**
	 * Set QtyOrderedOverUnder.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyOrderedOverUnder (java.math.BigDecimal QtyOrderedOverUnder);

	/**
	 * Get QtyOrderedOverUnder.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyOrderedOverUnder();

    /** Column definition for QtyOrderedOverUnder */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_QtyOrderedOverUnder = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object>(I_M_ReceiptSchedule.class, "QtyOrderedOverUnder", null);
    /** Column name QtyOrderedOverUnder */
    public static final String COLUMNNAME_QtyOrderedOverUnder = "QtyOrderedOverUnder";

	/**
	 * Set QtyOrderedTU.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setQtyOrderedTU (java.math.BigDecimal QtyOrderedTU);

	/**
	 * Get QtyOrderedTU.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	public java.math.BigDecimal getQtyOrderedTU();

    /** Column definition for QtyOrderedTU */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_QtyOrderedTU = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object>(I_M_ReceiptSchedule.class, "QtyOrderedTU", null);
    /** Column name QtyOrderedTU */
    public static final String COLUMNNAME_QtyOrderedTU = "QtyOrderedTU";

	/**
	 * Set Menge zu bewegen.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyToMove (java.math.BigDecimal QtyToMove);

	/**
	 * Get Menge zu bewegen.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyToMove();

    /** Column definition for QtyToMove */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_QtyToMove = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object>(I_M_ReceiptSchedule.class, "QtyToMove", null);
    /** Column name QtyToMove */
    public static final String COLUMNNAME_QtyToMove = "QtyToMove";

	/**
	 * Set Menge zu bewegen abw..
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyToMove_Override (java.math.BigDecimal QtyToMove_Override);

	/**
	 * Get Menge zu bewegen abw..
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyToMove_Override();

    /** Column definition for QtyToMove_Override */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_QtyToMove_Override = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object>(I_M_ReceiptSchedule.class, "QtyToMove_Override", null);
    /** Column name QtyToMove_Override */
    public static final String COLUMNNAME_QtyToMove_Override = "QtyToMove_Override";

	/**
	 * Set Qualitätsabzug %.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQualityDiscountPercent (java.math.BigDecimal QualityDiscountPercent);

	/**
	 * Get Qualitätsabzug %.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQualityDiscountPercent();

    /** Column definition for QualityDiscountPercent */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_QualityDiscountPercent = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object>(I_M_ReceiptSchedule.class, "QualityDiscountPercent", null);
    /** Column name QualityDiscountPercent */
    public static final String COLUMNNAME_QualityDiscountPercent = "QualityDiscountPercent";

	/**
	 * Set Qualität-Notiz.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQualityNote (java.lang.String QualityNote);

	/**
	 * Get Qualität-Notiz.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getQualityNote();

    /** Column definition for QualityNote */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_QualityNote = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object>(I_M_ReceiptSchedule.class, "QualityNote", null);
    /** Column name QualityNote */
    public static final String COLUMNNAME_QualityNote = "QualityNote";

	/**
	 * Set Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRecord_ID (int Record_ID);

	/**
	 * Get Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getRecord_ID();

    /** Column definition for Record_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_Record_ID = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object>(I_M_ReceiptSchedule.class, "Record_ID", null);
    /** Column name Record_ID */
    public static final String COLUMNNAME_Record_ID = "Record_ID";

	/**
	 * Set Status.
	 * Status of the currently running check
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setStatus (java.lang.String Status);

	/**
	 * Get Status.
	 * Status of the currently running check
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getStatus();

    /** Column definition for Status */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_Status = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object>(I_M_ReceiptSchedule.class, "Status", null);
    /** Column name Status */
    public static final String COLUMNNAME_Status = "Status";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object>(I_M_ReceiptSchedule.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
