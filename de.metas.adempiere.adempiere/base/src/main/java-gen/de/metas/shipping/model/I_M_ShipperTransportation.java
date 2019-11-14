package de.metas.shipping.model;


/** Generated Interface for M_ShipperTransportation
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_ShipperTransportation 
{

    /** TableName=M_ShipperTransportation */
    public static final String Table_Name = "M_ShipperTransportation";

    /** AD_Table_ID=540030 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(1);

    /** Load Meta Data */

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
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Ladeliste erstellen.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBillLadingReport (java.lang.String BillLadingReport);

	/**
	 * Get Ladeliste erstellen.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getBillLadingReport();

    /** Column definition for BillLadingReport */
    public static final org.adempiere.model.ModelColumn<I_M_ShipperTransportation, Object> COLUMN_BillLadingReport = new org.adempiere.model.ModelColumn<I_M_ShipperTransportation, Object>(I_M_ShipperTransportation.class, "BillLadingReport", null);
    /** Column name BillLadingReport */
    public static final String COLUMNNAME_BillLadingReport = "BillLadingReport";

	/**
	 * Set Belegart.
	 * Belegart oder Verarbeitungsvorgaben
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_DocType_ID (int C_DocType_ID);

	/**
	 * Get Belegart.
	 * Belegart oder Verarbeitungsvorgaben
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_DocType_ID();

    /** Column name C_DocType_ID */
    public static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/**
	 * Set Sammelrechnung erstellen.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCollectiveBillReport (java.lang.String CollectiveBillReport);

	/**
	 * Get Sammelrechnung erstellen.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCollectiveBillReport();

    /** Column definition for CollectiveBillReport */
    public static final org.adempiere.model.ModelColumn<I_M_ShipperTransportation, Object> COLUMN_CollectiveBillReport = new org.adempiere.model.ModelColumn<I_M_ShipperTransportation, Object>(I_M_ShipperTransportation.class, "CollectiveBillReport", null);
    /** Column name CollectiveBillReport */
    public static final String COLUMNNAME_CollectiveBillReport = "CollectiveBillReport";

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
    public static final org.adempiere.model.ModelColumn<I_M_ShipperTransportation, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_ShipperTransportation, Object>(I_M_ShipperTransportation.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Packstücke erfassen.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCreateShippingPackages (java.lang.String CreateShippingPackages);

	/**
	 * Get Packstücke erfassen.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCreateShippingPackages();

    /** Column definition for CreateShippingPackages */
    public static final org.adempiere.model.ModelColumn<I_M_ShipperTransportation, Object> COLUMN_CreateShippingPackages = new org.adempiere.model.ModelColumn<I_M_ShipperTransportation, Object>(I_M_ShipperTransportation.class, "CreateShippingPackages", null);
    /** Column name CreateShippingPackages */
    public static final String COLUMNNAME_CreateShippingPackages = "CreateShippingPackages";

	/**
	 * Set Belegdatum.
	 * Datum des Belegs
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDateDoc (java.sql.Timestamp DateDoc);

	/**
	 * Get Belegdatum.
	 * Datum des Belegs
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateDoc();

    /** Column definition for DateDoc */
    public static final org.adempiere.model.ModelColumn<I_M_ShipperTransportation, Object> COLUMN_DateDoc = new org.adempiere.model.ModelColumn<I_M_ShipperTransportation, Object>(I_M_ShipperTransportation.class, "DateDoc", null);
    /** Column name DateDoc */
    public static final String COLUMNNAME_DateDoc = "DateDoc";

	/**
	 * Set Abholung am.
	 * Date and time to fetch
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateToBeFetched (java.sql.Timestamp DateToBeFetched);

	/**
	 * Get Abholung am.
	 * Date and time to fetch
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateToBeFetched();

    /** Column definition for DateToBeFetched */
    public static final org.adempiere.model.ModelColumn<I_M_ShipperTransportation, Object> COLUMN_DateToBeFetched = new org.adempiere.model.ModelColumn<I_M_ShipperTransportation, Object>(I_M_ShipperTransportation.class, "DateToBeFetched", null);
    /** Column name DateToBeFetched */
    public static final String COLUMNNAME_DateToBeFetched = "DateToBeFetched";

	/**
	 * Set Beschreibung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescription (java.lang.String Description);

	/**
	 * Get Beschreibung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_M_ShipperTransportation, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_M_ShipperTransportation, Object>(I_M_ShipperTransportation.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Document Action.
	 * The targeted status of the document
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDocAction (java.lang.String DocAction);

	/**
	 * Get Document Action.
	 * The targeted status of the document
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocAction();

    /** Column definition for DocAction */
    public static final org.adempiere.model.ModelColumn<I_M_ShipperTransportation, Object> COLUMN_DocAction = new org.adempiere.model.ModelColumn<I_M_ShipperTransportation, Object>(I_M_ShipperTransportation.class, "DocAction", null);
    /** Column name DocAction */
    public static final String COLUMNNAME_DocAction = "DocAction";

	/**
	 * Set Belegstatus.
	 * The current status of the document
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDocStatus (java.lang.String DocStatus);

	/**
	 * Get Belegstatus.
	 * The current status of the document
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocStatus();

    /** Column definition for DocStatus */
    public static final org.adempiere.model.ModelColumn<I_M_ShipperTransportation, Object> COLUMN_DocStatus = new org.adempiere.model.ModelColumn<I_M_ShipperTransportation, Object>(I_M_ShipperTransportation.class, "DocStatus", null);
    /** Column name DocStatus */
    public static final String COLUMNNAME_DocStatus = "DocStatus";

	/**
	 * Set Nr..
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDocumentNo (java.lang.String DocumentNo);

	/**
	 * Get Nr..
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocumentNo();

    /** Column definition for DocumentNo */
    public static final org.adempiere.model.ModelColumn<I_M_ShipperTransportation, Object> COLUMN_DocumentNo = new org.adempiere.model.ModelColumn<I_M_ShipperTransportation, Object>(I_M_ShipperTransportation.class, "DocumentNo", null);
    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

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
    public static final org.adempiere.model.ModelColumn<I_M_ShipperTransportation, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_ShipperTransportation, Object>(I_M_ShipperTransportation.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Approved.
	 * Indicates if this document requires approval
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsApproved (boolean IsApproved);

	/**
	 * Get Approved.
	 * Indicates if this document requires approval
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isApproved();

    /** Column definition for IsApproved */
    public static final org.adempiere.model.ModelColumn<I_M_ShipperTransportation, Object> COLUMN_IsApproved = new org.adempiere.model.ModelColumn<I_M_ShipperTransportation, Object>(I_M_ShipperTransportation.class, "IsApproved", null);
    /** Column name IsApproved */
    public static final String COLUMNNAME_IsApproved = "IsApproved";

	/**
	 * Set Lieferweg.
	 * Methode oder Art der Warenlieferung
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Shipper_ID (int M_Shipper_ID);

	/**
	 * Get Lieferweg.
	 * Methode oder Art der Warenlieferung
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Shipper_ID();

	public org.compiere.model.I_M_Shipper getM_Shipper();

	public void setM_Shipper(org.compiere.model.I_M_Shipper M_Shipper);

    /** Column definition for M_Shipper_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ShipperTransportation, org.compiere.model.I_M_Shipper> COLUMN_M_Shipper_ID = new org.adempiere.model.ModelColumn<I_M_ShipperTransportation, org.compiere.model.I_M_Shipper>(I_M_ShipperTransportation.class, "M_Shipper_ID", org.compiere.model.I_M_Shipper.class);
    /** Column name M_Shipper_ID */
    public static final String COLUMNNAME_M_Shipper_ID = "M_Shipper_ID";

	/**
	 * Set Transport Auftrag.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_ShipperTransportation_ID (int M_ShipperTransportation_ID);

	/**
	 * Get Transport Auftrag.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_ShipperTransportation_ID();

    /** Column definition for M_ShipperTransportation_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ShipperTransportation, Object> COLUMN_M_ShipperTransportation_ID = new org.adempiere.model.ModelColumn<I_M_ShipperTransportation, Object>(I_M_ShipperTransportation.class, "M_ShipperTransportation_ID", null);
    /** Column name M_ShipperTransportation_ID */
    public static final String COLUMNNAME_M_ShipperTransportation_ID = "M_ShipperTransportation_ID";

	/**
	 * Set Tour.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Tour_ID (int M_Tour_ID);

	/**
	 * Get Tour.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Tour_ID();

    /** Column definition for M_Tour_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ShipperTransportation, Object> COLUMN_M_Tour_ID = new org.adempiere.model.ModelColumn<I_M_ShipperTransportation, Object>(I_M_ShipperTransportation.class, "M_Tour_ID", null);
    /** Column name M_Tour_ID */
    public static final String COLUMNNAME_M_Tour_ID = "M_Tour_ID";

	/**
	 * Set Package Net Total.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPackageNetTotal (java.math.BigDecimal PackageNetTotal);

	/**
	 * Get Package Net Total.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPackageNetTotal();

    /** Column definition for PackageNetTotal */
    public static final org.adempiere.model.ModelColumn<I_M_ShipperTransportation, Object> COLUMN_PackageNetTotal = new org.adempiere.model.ModelColumn<I_M_ShipperTransportation, Object>(I_M_ShipperTransportation.class, "PackageNetTotal", null);
    /** Column name PackageNetTotal */
    public static final String COLUMNNAME_PackageNetTotal = "PackageNetTotal";

	/**
	 * Set Package Weight.
	 * Weight of a package
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPackageWeight (java.math.BigDecimal PackageWeight);

	/**
	 * Get Package Weight.
	 * Weight of a package
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPackageWeight();

    /** Column definition for PackageWeight */
    public static final org.adempiere.model.ModelColumn<I_M_ShipperTransportation, Object> COLUMN_PackageWeight = new org.adempiere.model.ModelColumn<I_M_ShipperTransportation, Object>(I_M_ShipperTransportation.class, "PackageWeight", null);
    /** Column name PackageWeight */
    public static final String COLUMNNAME_PackageWeight = "PackageWeight";

	/**
	 * Set Pickup Time From.
	 *
	 * <br>Type: Time
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPickupTimeFrom (java.sql.Timestamp PickupTimeFrom);

	/**
	 * Get Pickup Time From.
	 *
	 * <br>Type: Time
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getPickupTimeFrom();

    /** Column definition for PickupTimeFrom */
    public static final org.adempiere.model.ModelColumn<I_M_ShipperTransportation, Object> COLUMN_PickupTimeFrom = new org.adempiere.model.ModelColumn<I_M_ShipperTransportation, Object>(I_M_ShipperTransportation.class, "PickupTimeFrom", null);
    /** Column name PickupTimeFrom */
    public static final String COLUMNNAME_PickupTimeFrom = "PickupTimeFrom";

	/**
	 * Set Pickup Time To.
	 *
	 * <br>Type: Time
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPickupTimeTo (java.sql.Timestamp PickupTimeTo);

	/**
	 * Get Pickup Time To.
	 *
	 * <br>Type: Time
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getPickupTimeTo();

    /** Column definition for PickupTimeTo */
    public static final org.adempiere.model.ModelColumn<I_M_ShipperTransportation, Object> COLUMN_PickupTimeTo = new org.adempiere.model.ModelColumn<I_M_ShipperTransportation, Object>(I_M_ShipperTransportation.class, "PickupTimeTo", null);
    /** Column name PickupTimeTo */
    public static final String COLUMNNAME_PickupTimeTo = "PickupTimeTo";

	/**
	 * Set Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setProcessed (boolean Processed);

	/**
	 * Get Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isProcessed();

    /** Column definition for Processed */
    public static final org.adempiere.model.ModelColumn<I_M_ShipperTransportation, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_M_ShipperTransportation, Object>(I_M_ShipperTransportation.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Process Now.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProcessing (boolean Processing);

	/**
	 * Get Process Now.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isProcessing();

    /** Column definition for Processing */
    public static final org.adempiere.model.ModelColumn<I_M_ShipperTransportation, Object> COLUMN_Processing = new org.adempiere.model.ModelColumn<I_M_ShipperTransportation, Object>(I_M_ShipperTransportation.class, "Processing", null);
    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Kundenbetreuer.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSalesRep_ID (int SalesRep_ID);

	/**
	 * Get Kundenbetreuer.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getSalesRep_ID();

    /** Column name SalesRep_ID */
    public static final String COLUMNNAME_SalesRep_ID = "SalesRep_ID";

	/**
	 * Set Shipper Partner.
	 * Business Partner to be used as shipper
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setShipper_BPartner_ID (int Shipper_BPartner_ID);

	/**
	 * Get Shipper Partner.
	 * Business Partner to be used as shipper
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getShipper_BPartner_ID();

    /** Column name Shipper_BPartner_ID */
    public static final String COLUMNNAME_Shipper_BPartner_ID = "Shipper_BPartner_ID";

	/**
	 * Set Shipper Location.
	 * Business Partner Location for Shipper
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setShipper_Location_ID (int Shipper_Location_ID);

	/**
	 * Get Shipper Location.
	 * Business Partner Location for Shipper
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getShipper_Location_ID();

    /** Column name Shipper_Location_ID */
    public static final String COLUMNNAME_Shipper_Location_ID = "Shipper_Location_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_ShipperTransportation, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_ShipperTransportation, Object>(I_M_ShipperTransportation.class, "Updated", null);
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
