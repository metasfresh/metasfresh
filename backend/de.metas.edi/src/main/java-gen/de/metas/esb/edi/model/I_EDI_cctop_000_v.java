package de.metas.esb.edi.model;

<<<<<<< HEAD
=======
import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

/** Generated Interface for EDI_cctop_000_v
 *  @author metasfresh (generated) 
 */
<<<<<<< HEAD
@SuppressWarnings("javadoc")
public interface I_EDI_cctop_000_v 
{

    /** TableName=EDI_cctop_000_v */
    public static final String Table_Name = "EDI_cctop_000_v";

    /** AD_Table_ID=540464 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);
=======
@SuppressWarnings("unused")
public interface I_EDI_cctop_000_v 
{

	String Table_Name = "EDI_cctop_000_v";

//	/** AD_Table_ID=540464 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getAD_Client_ID();

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";
=======
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setAD_Org_ID (int AD_Org_ID);
=======
	void setAD_Org_ID (int AD_Org_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getAD_Org_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";
=======
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Location.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);
=======
	void setC_BPartner_Location_ID (int C_BPartner_Location_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Location.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getC_BPartner_Location_ID();

    /** Column name C_BPartner_Location_ID */
    public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";
=======
	int getC_BPartner_Location_ID();

	String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_000_v, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_EDI_cctop_000_v, Object>(I_EDI_cctop_000_v.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";
=======
	java.sql.Timestamp getCreated();

	ModelColumn<I_EDI_cctop_000_v, Object> COLUMN_Created = new ModelColumn<>(I_EDI_cctop_000_v.class, "Created", null);
	String COLUMNNAME_Created = "Created";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getCreatedBy();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";
=======
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set EDI_cctop_000_v.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setEDI_cctop_000_v_ID (int EDI_cctop_000_v_ID);
=======
	void setEDI_cctop_000_v_ID (int EDI_cctop_000_v_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get EDI_cctop_000_v.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getEDI_cctop_000_v_ID();

    /** Column definition for EDI_cctop_000_v_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_000_v, Object> COLUMN_EDI_cctop_000_v_ID = new org.adempiere.model.ModelColumn<I_EDI_cctop_000_v, Object>(I_EDI_cctop_000_v.class, "EDI_cctop_000_v_ID", null);
    /** Column name EDI_cctop_000_v_ID */
    public static final String COLUMNNAME_EDI_cctop_000_v_ID = "EDI_cctop_000_v_ID";

	/**
	 * Set EDI-ID des Dateiempfängers.
=======
	int getEDI_cctop_000_v_ID();

	ModelColumn<I_EDI_cctop_000_v, Object> COLUMN_EDI_cctop_000_v_ID = new ModelColumn<>(I_EDI_cctop_000_v.class, "EDI_cctop_000_v_ID", null);
	String COLUMNNAME_EDI_cctop_000_v_ID = "EDI_cctop_000_v_ID";

	/**
	 * Set EDI-ID des INVOIC-Empfängers.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setEdiDesadvRecipientGLN (java.lang.String EdiDesadvRecipientGLN);

	/**
	 * Get EDI-ID des Dateiempfängers.
=======
	void setEdiInvoicRecipientGLN (@Nullable java.lang.String EdiInvoicRecipientGLN);

	/**
	 * Get EDI-ID des INVOIC-Empfängers.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getEdiDesadvRecipientGLN();

    /** Column definition for EdiDesadvRecipientGLN */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_000_v, Object> COLUMN_EdiDesadvRecipientGLN = new org.adempiere.model.ModelColumn<I_EDI_cctop_000_v, Object>(I_EDI_cctop_000_v.class, "EdiDesadvRecipientGLN", null);
    /** Column name EdiDesadvRecipientGLN */
    public static final String COLUMNNAME_EdiDesadvRecipientGLN = "EdiDesadvRecipientGLN";
=======
	@Nullable java.lang.String getEdiInvoicRecipientGLN();

	ModelColumn<I_EDI_cctop_000_v, Object> COLUMN_EdiInvoicRecipientGLN = new ModelColumn<>(I_EDI_cctop_000_v.class, "EdiInvoicRecipientGLN", null);
	String COLUMNNAME_EdiInvoicRecipientGLN = "EdiInvoicRecipientGLN";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setIsActive (boolean IsActive);
=======
	void setIsActive (boolean IsActive);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_000_v, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_EDI_cctop_000_v, Object>(I_EDI_cctop_000_v.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";
=======
	boolean isActive();

	ModelColumn<I_EDI_cctop_000_v, Object> COLUMN_IsActive = new ModelColumn<>(I_EDI_cctop_000_v.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_000_v, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_EDI_cctop_000_v, Object>(I_EDI_cctop_000_v.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";
=======
	java.sql.Timestamp getUpdated();

	ModelColumn<I_EDI_cctop_000_v, Object> COLUMN_Updated = new ModelColumn<>(I_EDI_cctop_000_v.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getUpdatedBy();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
=======
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}
