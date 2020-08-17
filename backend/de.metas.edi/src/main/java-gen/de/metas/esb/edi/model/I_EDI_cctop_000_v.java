package de.metas.esb.edi.model;


/** Generated Interface for EDI_cctop_000_v
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_EDI_cctop_000_v 
{

    /** TableName=EDI_cctop_000_v */
    public static final String Table_Name = "EDI_cctop_000_v";

    /** AD_Table_ID=540464 */
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
	 * Set Location.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Location.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_Location_ID();

    /** Column name C_BPartner_Location_ID */
    public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

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
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_000_v, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_EDI_cctop_000_v, Object>(I_EDI_cctop_000_v.class, "Created", null);
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
	 * Set EDI_cctop_000_v.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEDI_cctop_000_v_ID (int EDI_cctop_000_v_ID);

	/**
	 * Get EDI_cctop_000_v.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getEDI_cctop_000_v_ID();

    /** Column definition for EDI_cctop_000_v_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_000_v, Object> COLUMN_EDI_cctop_000_v_ID = new org.adempiere.model.ModelColumn<I_EDI_cctop_000_v, Object>(I_EDI_cctop_000_v.class, "EDI_cctop_000_v_ID", null);
    /** Column name EDI_cctop_000_v_ID */
    public static final String COLUMNNAME_EDI_cctop_000_v_ID = "EDI_cctop_000_v_ID";

	/**
	 * Set EDI-ID des Dateiempfängers.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEdiRecipientGLN (java.lang.String EdiRecipientGLN);

	/**
	 * Get EDI-ID des Dateiempfängers.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEdiRecipientGLN();

    /** Column definition for EdiRecipientGLN */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_000_v, Object> COLUMN_EdiRecipientGLN = new org.adempiere.model.ModelColumn<I_EDI_cctop_000_v, Object>(I_EDI_cctop_000_v.class, "EdiRecipientGLN", null);
    /** Column name EdiRecipientGLN */
    public static final String COLUMNNAME_EdiRecipientGLN = "EdiRecipientGLN";

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
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_000_v, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_EDI_cctop_000_v, Object>(I_EDI_cctop_000_v.class, "IsActive", null);
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
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_000_v, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_EDI_cctop_000_v, Object>(I_EDI_cctop_000_v.class, "Updated", null);
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
