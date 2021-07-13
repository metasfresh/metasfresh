package org.compiere.model;


/** Generated Interface for InvoiceProcessingServiceCompany
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_InvoiceProcessingServiceCompany 
{

    /** TableName=InvoiceProcessingServiceCompany */
    public static final String Table_Name = "InvoiceProcessingServiceCompany";

    /** AD_Table_ID=541493 */
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
	 * Get Created.
	 * Date this record was created
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
	 * Set Description.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescription (java.lang.String Description);

	/**
	 * Get Description.
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
	 * Get Updated.
	 * Date this record was updated
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

	/**
	 * Set Valid From.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setValidFrom (java.sql.Timestamp ValidFrom);

	/**
	 * Get Valid From.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getValidFrom();

    /** Column definition for ValidFrom */
    public static final org.adempiere.model.ModelColumn<I_InvoiceProcessingServiceCompany, Object> COLUMN_ValidFrom = new org.adempiere.model.ModelColumn<I_InvoiceProcessingServiceCompany, Object>(I_InvoiceProcessingServiceCompany.class, "ValidFrom", null);
    /** Column name ValidFrom */
    public static final String COLUMNNAME_ValidFrom = "ValidFrom";
}
