package org.compiere.model;


/** Generated Interface for InvoiceProcessingServiceCompany
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_InvoiceProcessingServiceCompany 
{

    /** TableName=InvoiceProcessingServiceCompany */
    public static final String Table_Name = "InvoiceProcessingServiceCompany";

    /** AD_Table_ID=541493 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 2 - Client
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(2);

    /** Load Meta Data */

	/**
	 * Get Mandant.
	 * Mandant für diese Installation.
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
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
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
	 * Get Erstellt.
	 * Datum, an dem dieser Eintrag erstellt wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_InvoiceProcessingServiceCompany, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_InvoiceProcessingServiceCompany, Object>(I_InvoiceProcessingServiceCompany.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * Nutzer, der diesen Eintrag erstellt hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Beschreibung.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescription (java.lang.String Description);

	/**
	 * Get Beschreibung.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_InvoiceProcessingServiceCompany, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_InvoiceProcessingServiceCompany, Object>(I_InvoiceProcessingServiceCompany.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Fee Percentage of Invoice Grand Total.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setFeePercentageOfGrandTotal (java.math.BigDecimal FeePercentageOfGrandTotal);

	/**
	 * Get Fee Percentage of Invoice Grand Total.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getFeePercentageOfGrandTotal();

    /** Column definition for FeePercentageOfGrandTotal */
    public static final org.adempiere.model.ModelColumn<I_InvoiceProcessingServiceCompany, Object> COLUMN_FeePercentageOfGrandTotal = new org.adempiere.model.ModelColumn<I_InvoiceProcessingServiceCompany, Object>(I_InvoiceProcessingServiceCompany.class, "FeePercentageOfGrandTotal", null);
    /** Column name FeePercentageOfGrandTotal */
    public static final String COLUMNNAME_FeePercentageOfGrandTotal = "FeePercentageOfGrandTotal";

	/**
	 * Set Invoice Processing Service Company.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setInvoiceProcessingServiceCompany_ID (int InvoiceProcessingServiceCompany_ID);

	/**
	 * Get Invoice Processing Service Company.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getInvoiceProcessingServiceCompany_ID();

    /** Column definition for InvoiceProcessingServiceCompany_ID */
    public static final org.adempiere.model.ModelColumn<I_InvoiceProcessingServiceCompany, Object> COLUMN_InvoiceProcessingServiceCompany_ID = new org.adempiere.model.ModelColumn<I_InvoiceProcessingServiceCompany, Object>(I_InvoiceProcessingServiceCompany.class, "InvoiceProcessingServiceCompany_ID", null);
    /** Column name InvoiceProcessingServiceCompany_ID */
    public static final String COLUMNNAME_InvoiceProcessingServiceCompany_ID = "InvoiceProcessingServiceCompany_ID";

	/**
	 * Set Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_InvoiceProcessingServiceCompany, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_InvoiceProcessingServiceCompany, Object>(I_InvoiceProcessingServiceCompany.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Service Company.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setServiceCompany_BPartner_ID (int ServiceCompany_BPartner_ID);

	/**
	 * Get Service Company.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getServiceCompany_BPartner_ID();

    /** Column name ServiceCompany_BPartner_ID */
    public static final String COLUMNNAME_ServiceCompany_BPartner_ID = "ServiceCompany_BPartner_ID";

	/**
	 * Set Service Fee Product.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setServiceFee_Product_ID (int ServiceFee_Product_ID);

	/**
	 * Get Service Fee Product.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getServiceFee_Product_ID();

    /** Column name ServiceFee_Product_ID */
    public static final String COLUMNNAME_ServiceFee_Product_ID = "ServiceFee_Product_ID";

	/**
	 * Set Service Invoice Doc Type.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setServiceInvoice_DocType_ID (int ServiceInvoice_DocType_ID);

	/**
	 * Get Service Invoice Doc Type.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getServiceInvoice_DocType_ID();

    /** Column name ServiceInvoice_DocType_ID */
    public static final String COLUMNNAME_ServiceInvoice_DocType_ID = "ServiceInvoice_DocType_ID";

	/**
	 * Get Aktualisiert.
	 * Datum, an dem dieser Eintrag aktualisiert wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_InvoiceProcessingServiceCompany, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_InvoiceProcessingServiceCompany, Object>(I_InvoiceProcessingServiceCompany.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * Nutzer, der diesen Eintrag aktualisiert hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
