package de.metas.shipping.model;


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
	 * Set Business Partner .
	 * Identifies a Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Business Partner .
	 * Identifies a Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Partner Location.
	 * Identifies the (ship to) address for this Business Partner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Partner Location.
	 * Identifies the (ship to) address for this Business Partner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_Location_ID();

    /** Column name C_BPartner_Location_ID */
    public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/**
	 * Set Auftrag.
	 * Auftrag
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Order_ID (int C_Order_ID);

	/**
	 * Get Auftrag.
	 * Auftrag
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Order_ID();

	public org.compiere.model.I_C_Order getC_Order();

	public void setC_Order(org.compiere.model.I_C_Order C_Order);

    /** Column definition for C_Order_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ShippingPackage, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new org.adempiere.model.ModelColumn<I_M_ShippingPackage, org.compiere.model.I_C_Order>(I_M_ShippingPackage.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
    /** Column name C_Order_ID */
    public static final String COLUMNNAME_C_Order_ID = "C_Order_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_ShippingPackage, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_ShippingPackage, Object>(I_M_ShippingPackage.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_ShippingPackage, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_ShippingPackage, Object>(I_M_ShippingPackage.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Abholung.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsToBeFetched (boolean IsToBeFetched);

	/**
	 * Get Abholung.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isToBeFetched();

    /** Column definition for IsToBeFetched */
    public static final org.adempiere.model.ModelColumn<I_M_ShippingPackage, Object> COLUMN_IsToBeFetched = new org.adempiere.model.ModelColumn<I_M_ShippingPackage, Object>(I_M_ShippingPackage.class, "IsToBeFetched", null);
    /** Column name IsToBeFetched */
    public static final String COLUMNNAME_IsToBeFetched = "IsToBeFetched";

	/**
	 * Set Lieferung/Wareneingang.
	 * Material Shipment Document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_InOut_ID (int M_InOut_ID);

	/**
	 * Get Lieferung/Wareneingang.
	 * Material Shipment Document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_InOut_ID();

	public org.compiere.model.I_M_InOut getM_InOut();

	public void setM_InOut(org.compiere.model.I_M_InOut M_InOut);

    /** Column definition for M_InOut_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ShippingPackage, org.compiere.model.I_M_InOut> COLUMN_M_InOut_ID = new org.adempiere.model.ModelColumn<I_M_ShippingPackage, org.compiere.model.I_M_InOut>(I_M_ShippingPackage.class, "M_InOut_ID", org.compiere.model.I_M_InOut.class);
    /** Column name M_InOut_ID */
    public static final String COLUMNNAME_M_InOut_ID = "M_InOut_ID";

	/**
	 * Set Package.
	 * Shipment Package
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Package_ID (int M_Package_ID);

	/**
	 * Get Package.
	 * Shipment Package
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Package_ID();

	public org.compiere.model.I_M_Package getM_Package();

	public void setM_Package(org.compiere.model.I_M_Package M_Package);

    /** Column definition for M_Package_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ShippingPackage, org.compiere.model.I_M_Package> COLUMN_M_Package_ID = new org.adempiere.model.ModelColumn<I_M_ShippingPackage, org.compiere.model.I_M_Package>(I_M_ShippingPackage.class, "M_Package_ID", org.compiere.model.I_M_Package.class);
    /** Column name M_Package_ID */
    public static final String COLUMNNAME_M_Package_ID = "M_Package_ID";

	/**
	 * Set Transport Auftrag.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_ShipperTransportation_ID (int M_ShipperTransportation_ID);

	/**
	 * Get Transport Auftrag.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_ShipperTransportation_ID();

	public de.metas.shipping.model.I_M_ShipperTransportation getM_ShipperTransportation();

	public void setM_ShipperTransportation(de.metas.shipping.model.I_M_ShipperTransportation M_ShipperTransportation);

    /** Column definition for M_ShipperTransportation_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ShippingPackage, de.metas.shipping.model.I_M_ShipperTransportation> COLUMN_M_ShipperTransportation_ID = new org.adempiere.model.ModelColumn<I_M_ShippingPackage, de.metas.shipping.model.I_M_ShipperTransportation>(I_M_ShippingPackage.class, "M_ShipperTransportation_ID", de.metas.shipping.model.I_M_ShipperTransportation.class);
    /** Column name M_ShipperTransportation_ID */
    public static final String COLUMNNAME_M_ShipperTransportation_ID = "M_ShipperTransportation_ID";

	/**
	 * Set Shipping Package.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_ShippingPackage_ID (int M_ShippingPackage_ID);

	/**
	 * Get Shipping Package.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_ShippingPackage_ID();

    /** Column definition for M_ShippingPackage_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ShippingPackage, Object> COLUMN_M_ShippingPackage_ID = new org.adempiere.model.ModelColumn<I_M_ShippingPackage, Object>(I_M_ShippingPackage.class, "M_ShippingPackage_ID", null);
    /** Column name M_ShippingPackage_ID */
    public static final String COLUMNNAME_M_ShippingPackage_ID = "M_ShippingPackage_ID";

	/**
	 * Set Notiz.
	 * Optional weitere Information
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setNote (java.lang.String Note);

	/**
	 * Get Notiz.
	 * Optional weitere Information
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getNote();

    /** Column definition for Note */
    public static final org.adempiere.model.ModelColumn<I_M_ShippingPackage, Object> COLUMN_Note = new org.adempiere.model.ModelColumn<I_M_ShippingPackage, Object>(I_M_ShippingPackage.class, "Note", null);
    /** Column name Note */
    public static final String COLUMNNAME_Note = "Note";

	/**
	 * Set Package Net Total.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPackageNetTotal (java.math.BigDecimal PackageNetTotal);

	/**
	 * Get Package Net Total.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPackageNetTotal();

    /** Column definition for PackageNetTotal */
    public static final org.adempiere.model.ModelColumn<I_M_ShippingPackage, Object> COLUMN_PackageNetTotal = new org.adempiere.model.ModelColumn<I_M_ShippingPackage, Object>(I_M_ShippingPackage.class, "PackageNetTotal", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_ShippingPackage, Object> COLUMN_PackageWeight = new org.adempiere.model.ModelColumn<I_M_ShippingPackage, Object>(I_M_ShippingPackage.class, "PackageWeight", null);
    /** Column name PackageWeight */
    public static final String COLUMNNAME_PackageWeight = "PackageWeight";

	/**
	 * Set Verarbeitet.
	 * Checkbox sagt aus, ob der Datensatz verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setProcessed (boolean Processed);

	/**
	 * Get Verarbeitet.
	 * Checkbox sagt aus, ob der Datensatz verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isProcessed();

    /** Column definition for Processed */
    public static final org.adempiere.model.ModelColumn<I_M_ShippingPackage, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_M_ShippingPackage, Object>(I_M_ShippingPackage.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

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
    public static final org.adempiere.model.ModelColumn<I_M_ShippingPackage, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_ShippingPackage, Object>(I_M_ShippingPackage.class, "Updated", null);
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
