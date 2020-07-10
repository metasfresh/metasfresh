package de.metas.printing.model;


/** Generated Interface for C_Print_Job_Detail
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Print_Job_Detail 
{

    /** TableName=C_Print_Job_Detail */
    public static final String Table_Name = "C_Print_Job_Detail";

    /** AD_Table_ID=540457 */
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
	 * Set Drucker-Zuordnung.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_PrinterRouting_ID (int AD_PrinterRouting_ID);

	/**
	 * Get Drucker-Zuordnung.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_PrinterRouting_ID();

    /** Column definition for AD_PrinterRouting_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Print_Job_Detail, Object> COLUMN_AD_PrinterRouting_ID = new org.adempiere.model.ModelColumn<I_C_Print_Job_Detail, Object>(I_C_Print_Job_Detail.class, "AD_PrinterRouting_ID", null);
    /** Column name AD_PrinterRouting_ID */
    public static final String COLUMNNAME_AD_PrinterRouting_ID = "AD_PrinterRouting_ID";

	/**
	 * Set Druck-Job Detail.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Print_Job_Detail_ID (int C_Print_Job_Detail_ID);

	/**
	 * Get Druck-Job Detail.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Print_Job_Detail_ID();

    /** Column definition for C_Print_Job_Detail_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Print_Job_Detail, Object> COLUMN_C_Print_Job_Detail_ID = new org.adempiere.model.ModelColumn<I_C_Print_Job_Detail, Object>(I_C_Print_Job_Detail.class, "C_Print_Job_Detail_ID", null);
    /** Column name C_Print_Job_Detail_ID */
    public static final String COLUMNNAME_C_Print_Job_Detail_ID = "C_Print_Job_Detail_ID";

	/**
	 * Set Druck-Job Position.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Print_Job_Line_ID (int C_Print_Job_Line_ID);

	/**
	 * Get Druck-Job Position.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Print_Job_Line_ID();

	public de.metas.printing.model.I_C_Print_Job_Line getC_Print_Job_Line();

	public void setC_Print_Job_Line(de.metas.printing.model.I_C_Print_Job_Line C_Print_Job_Line);

    /** Column definition for C_Print_Job_Line_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Print_Job_Detail, de.metas.printing.model.I_C_Print_Job_Line> COLUMN_C_Print_Job_Line_ID = new org.adempiere.model.ModelColumn<I_C_Print_Job_Detail, de.metas.printing.model.I_C_Print_Job_Line>(I_C_Print_Job_Detail.class, "C_Print_Job_Line_ID", de.metas.printing.model.I_C_Print_Job_Line.class);
    /** Column name C_Print_Job_Line_ID */
    public static final String COLUMNNAME_C_Print_Job_Line_ID = "C_Print_Job_Line_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_Print_Job_Detail, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_Print_Job_Detail, Object>(I_C_Print_Job_Detail.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Print_Job_Detail, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_Print_Job_Detail, Object>(I_C_Print_Job_Detail.class, "IsActive", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Print_Job_Detail, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_Print_Job_Detail, Object>(I_C_Print_Job_Detail.class, "Updated", null);
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
