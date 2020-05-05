package org.compiere.model;


/** Generated Interface for InvoiceProcessingServiceCompany_BPartnerAssignment
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_InvoiceProcessingServiceCompany_BPartnerAssignment 
{

    /** TableName=InvoiceProcessingServiceCompany_BPartnerAssignment */
    public static final String Table_Name = "InvoiceProcessingServiceCompany_BPartnerAssignment";

    /** AD_Table_ID=541494 */
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
	 * Set Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

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
    public static final org.adempiere.model.ModelColumn<I_InvoiceProcessingServiceCompany_BPartnerAssignment, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_InvoiceProcessingServiceCompany_BPartnerAssignment, Object>(I_InvoiceProcessingServiceCompany_BPartnerAssignment.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_InvoiceProcessingServiceCompany_BPartnerAssignment, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_InvoiceProcessingServiceCompany_BPartnerAssignment, Object>(I_InvoiceProcessingServiceCompany_BPartnerAssignment.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Invoice Processing Service Company BPartner Assignment.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setInvoiceProcessingServiceCompany_BPartnerAssignment_ID (int InvoiceProcessingServiceCompany_BPartnerAssignment_ID);

	/**
	 * Get Invoice Processing Service Company BPartner Assignment.
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
    public static final org.adempiere.model.ModelColumn<I_InvoiceProcessingServiceCompany_BPartnerAssignment, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_InvoiceProcessingServiceCompany_BPartnerAssignment, Object>(I_InvoiceProcessingServiceCompany_BPartnerAssignment.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

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
    public static final org.adempiere.model.ModelColumn<I_InvoiceProcessingServiceCompany_BPartnerAssignment, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_InvoiceProcessingServiceCompany_BPartnerAssignment, Object>(I_InvoiceProcessingServiceCompany_BPartnerAssignment.class, "Updated", null);
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
