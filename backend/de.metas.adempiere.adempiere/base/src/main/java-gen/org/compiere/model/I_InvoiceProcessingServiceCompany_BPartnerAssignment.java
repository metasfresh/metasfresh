package org.compiere.model;


/** Generated Interface for InvoiceProcessingServiceCompany_BPartnerAssignment
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_InvoiceProcessingServiceCompany_BPartnerAssignment 
{

    /** TableName=InvoiceProcessingServiceCompany_BPartnerAssignment */
    public static final String Table_Name = "InvoiceProcessingServiceCompany_BPartnerAssignment";

    /** AD_Table_ID=541494 */
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
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_InvoiceProcessingServiceCompany_BPartnerAssignment, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_InvoiceProcessingServiceCompany_BPartnerAssignment, Object>(I_InvoiceProcessingServiceCompany_BPartnerAssignment.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_InvoiceProcessingServiceCompany_BPartnerAssignment, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_InvoiceProcessingServiceCompany_BPartnerAssignment, Object>(I_InvoiceProcessingServiceCompany_BPartnerAssignment.class, "Description", null);
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
    public static final org.adempiere.model.ModelColumn<I_InvoiceProcessingServiceCompany_BPartnerAssignment, Object> COLUMN_FeePercentageOfGrandTotal = new org.adempiere.model.ModelColumn<I_InvoiceProcessingServiceCompany_BPartnerAssignment, Object>(I_InvoiceProcessingServiceCompany_BPartnerAssignment.class, "FeePercentageOfGrandTotal", null);
    /** Column name FeePercentageOfGrandTotal */
    public static final String COLUMNNAME_FeePercentageOfGrandTotal = "FeePercentageOfGrandTotal";

	/**
	 * Set Assigned Customers.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setInvoiceProcessingServiceCompany_BPartnerAssignment_ID (int InvoiceProcessingServiceCompany_BPartnerAssignment_ID);

	/**
	 * Get Assigned Customers.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getInvoiceProcessingServiceCompany_BPartnerAssignment_ID();

    /** Column definition for InvoiceProcessingServiceCompany_BPartnerAssignment_ID */
    public static final org.adempiere.model.ModelColumn<I_InvoiceProcessingServiceCompany_BPartnerAssignment, Object> COLUMN_InvoiceProcessingServiceCompany_BPartnerAssignment_ID = new org.adempiere.model.ModelColumn<I_InvoiceProcessingServiceCompany_BPartnerAssignment, Object>(I_InvoiceProcessingServiceCompany_BPartnerAssignment.class, "InvoiceProcessingServiceCompany_BPartnerAssignment_ID", null);
    /** Column name InvoiceProcessingServiceCompany_BPartnerAssignment_ID */
    public static final String COLUMNNAME_InvoiceProcessingServiceCompany_BPartnerAssignment_ID = "InvoiceProcessingServiceCompany_BPartnerAssignment_ID";

	/**
	 * Set Invoice Processing Service Company.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setInvoiceProcessingServiceCompany_ID (int InvoiceProcessingServiceCompany_ID);

	/**
	 * Get Invoice Processing Service Company.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getInvoiceProcessingServiceCompany_ID();

	public org.compiere.model.I_InvoiceProcessingServiceCompany getInvoiceProcessingServiceCompany();

	public void setInvoiceProcessingServiceCompany(org.compiere.model.I_InvoiceProcessingServiceCompany InvoiceProcessingServiceCompany);

    /** Column definition for InvoiceProcessingServiceCompany_ID */
    public static final org.adempiere.model.ModelColumn<I_InvoiceProcessingServiceCompany_BPartnerAssignment, org.compiere.model.I_InvoiceProcessingServiceCompany> COLUMN_InvoiceProcessingServiceCompany_ID = new org.adempiere.model.ModelColumn<I_InvoiceProcessingServiceCompany_BPartnerAssignment, org.compiere.model.I_InvoiceProcessingServiceCompany>(I_InvoiceProcessingServiceCompany_BPartnerAssignment.class, "InvoiceProcessingServiceCompany_ID", org.compiere.model.I_InvoiceProcessingServiceCompany.class);
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
    public static final org.adempiere.model.ModelColumn<I_InvoiceProcessingServiceCompany_BPartnerAssignment, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_InvoiceProcessingServiceCompany_BPartnerAssignment, Object>(I_InvoiceProcessingServiceCompany_BPartnerAssignment.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

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
    public static final org.adempiere.model.ModelColumn<I_InvoiceProcessingServiceCompany_BPartnerAssignment, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_InvoiceProcessingServiceCompany_BPartnerAssignment, Object>(I_InvoiceProcessingServiceCompany_BPartnerAssignment.class, "Updated", null);
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
