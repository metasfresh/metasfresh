package org.compiere.model;


/** Generated Interface for M_Package
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_Package 
{

    /** TableName=M_Package */
    public static final String Table_Name = "M_Package";

    /** AD_Table_ID=664 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(1);

    /** Load Meta Data */

	/**
	 * Get Mandant.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Package, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_Package, org.compiere.model.I_AD_Client>(I_M_Package.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

	public org.compiere.model.I_AD_Org getAD_Org();

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Package, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_Package, org.compiere.model.I_AD_Org>(I_M_Package.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner();

	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner);

    /** Column definition for C_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Package, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_M_Package, org.compiere.model.I_C_BPartner>(I_M_Package.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Standort.
	 * Identifiziert die (Liefer-) Adresse des Geschäftspartners
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Standort.
	 * Identifiziert die (Liefer-) Adresse des Geschäftspartners
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_Location_ID();

	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location();

	public void setC_BPartner_Location(org.compiere.model.I_C_BPartner_Location C_BPartner_Location);

    /** Column definition for C_BPartner_Location_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Package, org.compiere.model.I_C_BPartner_Location> COLUMN_C_BPartner_Location_ID = new org.adempiere.model.ModelColumn<I_M_Package, org.compiere.model.I_C_BPartner_Location>(I_M_Package.class, "C_BPartner_Location_ID", org.compiere.model.I_C_BPartner_Location.class);
    /** Column name C_BPartner_Location_ID */
    public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/**
	 * Get Erstellt.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_M_Package, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_Package, Object>(I_M_Package.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_M_Package, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_Package, org.compiere.model.I_AD_User>(I_M_Package.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Eingangsdatum.
	 * Date a product was received
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateReceived (java.sql.Timestamp DateReceived);

	/**
	 * Get Eingangsdatum.
	 * Date a product was received
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateReceived();

    /** Column definition for DateReceived */
    public static final org.adempiere.model.ModelColumn<I_M_Package, Object> COLUMN_DateReceived = new org.adempiere.model.ModelColumn<I_M_Package, Object>(I_M_Package.class, "DateReceived", null);
    /** Column name DateReceived */
    public static final String COLUMNNAME_DateReceived = "DateReceived";

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
    public static final org.adempiere.model.ModelColumn<I_M_Package, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_M_Package, Object>(I_M_Package.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

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
    public static final org.adempiere.model.ModelColumn<I_M_Package, Object> COLUMN_DocumentNo = new org.adempiere.model.ModelColumn<I_M_Package, Object>(I_M_Package.class, "DocumentNo", null);
    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/**
	 * Set Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_M_Package, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_Package, Object>(I_M_Package.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Lieferung/Wareneingang.
	 * Material Shipment Document
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_InOut_ID (int M_InOut_ID);

	/**
	 * Get Lieferung/Wareneingang.
	 * Material Shipment Document
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_InOut_ID();

	public org.compiere.model.I_M_InOut getM_InOut();

	public void setM_InOut(org.compiere.model.I_M_InOut M_InOut);

    /** Column definition for M_InOut_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Package, org.compiere.model.I_M_InOut> COLUMN_M_InOut_ID = new org.adempiere.model.ModelColumn<I_M_Package, org.compiere.model.I_M_InOut>(I_M_Package.class, "M_InOut_ID", org.compiere.model.I_M_InOut.class);
    /** Column name M_InOut_ID */
    public static final String COLUMNNAME_M_InOut_ID = "M_InOut_ID";

	/**
	 * Set Packstück.
	 * Shipment Package
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Package_ID (int M_Package_ID);

	/**
	 * Get Packstück.
	 * Shipment Package
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Package_ID();

    /** Column definition for M_Package_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Package, Object> COLUMN_M_Package_ID = new org.adempiere.model.ModelColumn<I_M_Package, Object>(I_M_Package.class, "M_Package_ID", null);
    /** Column name M_Package_ID */
    public static final String COLUMNNAME_M_Package_ID = "M_Package_ID";

	/**
	 * Set Lieferweg.
	 * Method or manner of product delivery
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Shipper_ID (int M_Shipper_ID);

	/**
	 * Get Lieferweg.
	 * Method or manner of product delivery
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Shipper_ID();

	public org.compiere.model.I_M_Shipper getM_Shipper();

	public void setM_Shipper(org.compiere.model.I_M_Shipper M_Shipper);

    /** Column definition for M_Shipper_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Package, org.compiere.model.I_M_Shipper> COLUMN_M_Shipper_ID = new org.adempiere.model.ModelColumn<I_M_Package, org.compiere.model.I_M_Shipper>(I_M_Package.class, "M_Shipper_ID", org.compiere.model.I_M_Shipper.class);
    /** Column name M_Shipper_ID */
    public static final String COLUMNNAME_M_Shipper_ID = "M_Shipper_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_Package, Object> COLUMN_PackageNetTotal = new org.adempiere.model.ModelColumn<I_M_Package, Object>(I_M_Package.class, "PackageNetTotal", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_Package, Object> COLUMN_PackageWeight = new org.adempiere.model.ModelColumn<I_M_Package, Object>(I_M_Package.class, "PackageWeight", null);
    /** Column name PackageWeight */
    public static final String COLUMNNAME_PackageWeight = "PackageWeight";

	/**
	 * Set Info Received.
	 * Information of the receipt of the package (acknowledgement)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setReceivedInfo (java.lang.String ReceivedInfo);

	/**
	 * Get Info Received.
	 * Information of the receipt of the package (acknowledgement)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getReceivedInfo();

    /** Column definition for ReceivedInfo */
    public static final org.adempiere.model.ModelColumn<I_M_Package, Object> COLUMN_ReceivedInfo = new org.adempiere.model.ModelColumn<I_M_Package, Object>(I_M_Package.class, "ReceivedInfo", null);
    /** Column name ReceivedInfo */
    public static final String COLUMNNAME_ReceivedInfo = "ReceivedInfo";

	/**
	 * Set Lieferdatum.
	 * Shipment Date/Time
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setShipDate (java.sql.Timestamp ShipDate);

	/**
	 * Get Lieferdatum.
	 * Shipment Date/Time
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getShipDate();

    /** Column definition for ShipDate */
    public static final org.adempiere.model.ModelColumn<I_M_Package, Object> COLUMN_ShipDate = new org.adempiere.model.ModelColumn<I_M_Package, Object>(I_M_Package.class, "ShipDate", null);
    /** Column name ShipDate */
    public static final String COLUMNNAME_ShipDate = "ShipDate";

	/**
	 * Set Tracking Info.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setTrackingInfo (java.lang.String TrackingInfo);

	/**
	 * Get Tracking Info.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getTrackingInfo();

    /** Column definition for TrackingInfo */
    public static final org.adempiere.model.ModelColumn<I_M_Package, Object> COLUMN_TrackingInfo = new org.adempiere.model.ModelColumn<I_M_Package, Object>(I_M_Package.class, "TrackingInfo", null);
    /** Column name TrackingInfo */
    public static final String COLUMNNAME_TrackingInfo = "TrackingInfo";

	/**
	 * Get Aktualisiert.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_M_Package, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_Package, Object>(I_M_Package.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_M_Package, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_Package, org.compiere.model.I_AD_User>(I_M_Package.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
