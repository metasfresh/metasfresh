package de.metas.printing.model;


/** Generated Interface for C_PrintPackageData
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_PrintPackageData 
{

    /** TableName=C_PrintPackageData */
    public static final String Table_Name = "C_PrintPackageData";

    /** AD_Table_ID=540460 */
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
	 * Set Print package data.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_PrintPackageData_ID (int C_PrintPackageData_ID);

	/**
	 * Get Print package data.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_PrintPackageData_ID();

    /** Column definition for C_PrintPackageData_ID */
    public static final org.adempiere.model.ModelColumn<I_C_PrintPackageData, Object> COLUMN_C_PrintPackageData_ID = new org.adempiere.model.ModelColumn<I_C_PrintPackageData, Object>(I_C_PrintPackageData.class, "C_PrintPackageData_ID", null);
    /** Column name C_PrintPackageData_ID */
    public static final String COLUMNNAME_C_PrintPackageData_ID = "C_PrintPackageData_ID";

	/**
	 * Set Druckpaket.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Print_Package_ID (int C_Print_Package_ID);

	/**
	 * Get Druckpaket.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Print_Package_ID();

	public de.metas.printing.model.I_C_Print_Package getC_Print_Package();

	public void setC_Print_Package(de.metas.printing.model.I_C_Print_Package C_Print_Package);

    /** Column definition for C_Print_Package_ID */
    public static final org.adempiere.model.ModelColumn<I_C_PrintPackageData, de.metas.printing.model.I_C_Print_Package> COLUMN_C_Print_Package_ID = new org.adempiere.model.ModelColumn<I_C_PrintPackageData, de.metas.printing.model.I_C_Print_Package>(I_C_PrintPackageData.class, "C_Print_Package_ID", de.metas.printing.model.I_C_Print_Package.class);
    /** Column name C_Print_Package_ID */
    public static final String COLUMNNAME_C_Print_Package_ID = "C_Print_Package_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_PrintPackageData, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_PrintPackageData, Object>(I_C_PrintPackageData.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_PrintPackageData, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_PrintPackageData, Object>(I_C_PrintPackageData.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Print data.
	 *
	 * <br>Type: Binary
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPrintData (byte[] PrintData);

	/**
	 * Get Print data.
	 *
	 * <br>Type: Binary
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public byte[] getPrintData();

    /** Column definition for PrintData */
    public static final org.adempiere.model.ModelColumn<I_C_PrintPackageData, Object> COLUMN_PrintData = new org.adempiere.model.ModelColumn<I_C_PrintPackageData, Object>(I_C_PrintPackageData.class, "PrintData", null);
    /** Column name PrintData */
    public static final String COLUMNNAME_PrintData = "PrintData";

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
    public static final org.adempiere.model.ModelColumn<I_C_PrintPackageData, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_PrintPackageData, Object>(I_C_PrintPackageData.class, "Updated", null);
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
