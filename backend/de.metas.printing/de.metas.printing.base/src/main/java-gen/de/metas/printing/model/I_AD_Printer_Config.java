package de.metas.printing.model;

import org.adempiere.model.ModelColumn;
import org.compiere.model.I_C_Workplace;

import javax.annotation.Nullable;

/** Generated Interface for AD_Printer_Config
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_Printer_Config
{

    /** TableName=AD_Printer_Config */
    public static final String Table_Name = "AD_Printer_Config";

    /** AD_Table_ID=540637 */
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
	 * Set Printer Matching Config.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Printer_Config_ID (int AD_Printer_Config_ID);

	/**
	 * Get Printer Matching Config.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Printer_Config_ID();

    /** Column definition for AD_Printer_Config_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Printer_Config, Object> COLUMN_AD_Printer_Config_ID = new org.adempiere.model.ModelColumn<I_AD_Printer_Config, Object>(I_AD_Printer_Config.class, "AD_Printer_Config_ID", null);
    /** Column name AD_Printer_Config_ID */
    public static final String COLUMNNAME_AD_Printer_Config_ID = "AD_Printer_Config_ID";

	/**
	 * Set Benutzte Konfiguration.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Printer_Config_Shared_ID (int AD_Printer_Config_Shared_ID);

	/**
	 * Get Benutzte Konfiguration.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Printer_Config_Shared_ID();

	public de.metas.printing.model.I_AD_Printer_Config getAD_Printer_Config_Shared();

	public void setAD_Printer_Config_Shared(de.metas.printing.model.I_AD_Printer_Config AD_Printer_Config_Shared);

    /** Column definition for AD_Printer_Config_Shared_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Printer_Config, de.metas.printing.model.I_AD_Printer_Config> COLUMN_AD_Printer_Config_Shared_ID = new org.adempiere.model.ModelColumn<I_AD_Printer_Config, de.metas.printing.model.I_AD_Printer_Config>(I_AD_Printer_Config.class, "AD_Printer_Config_Shared_ID", de.metas.printing.model.I_AD_Printer_Config.class);
    /** Column name AD_Printer_Config_Shared_ID */
    public static final String COLUMNNAME_AD_Printer_Config_Shared_ID = "AD_Printer_Config_Shared_ID";

	/**
	 * Set User.
	 * Nutzer, für den die betreffende Zuordnung gilt. Druckanweisugen werden für den betreffenden Nutzer erstellt.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_User_PrinterMatchingConfig_ID (int AD_User_PrinterMatchingConfig_ID);

	/**
	 * Get User.
	 * Nutzer, für den die betreffende Zuordnung gilt. Druckanweisugen werden für den betreffenden Nutzer erstellt.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_User_PrinterMatchingConfig_ID();

    /** Column name AD_User_PrinterMatchingConfig_ID */
    public static final String COLUMNNAME_AD_User_PrinterMatchingConfig_ID = "AD_User_PrinterMatchingConfig_ID";

	/**
	 * Set Host key.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setConfigHostKey (java.lang.String ConfigHostKey);

	/**
	 * Get Host key.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getConfigHostKey();

    /** Column definition for ConfigHostKey */
    public static final org.adempiere.model.ModelColumn<I_AD_Printer_Config, Object> COLUMN_ConfigHostKey = new org.adempiere.model.ModelColumn<I_AD_Printer_Config, Object>(I_AD_Printer_Config.class, "ConfigHostKey", null);
    /** Column name ConfigHostKey */
    public static final String COLUMNNAME_ConfigHostKey = "ConfigHostKey";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Printer_Config, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_Printer_Config, Object>(I_AD_Printer_Config.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Printer_Config, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_Printer_Config, Object>(I_AD_Printer_Config.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Geteilt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsSharedPrinterConfig (boolean IsSharedPrinterConfig);

	/**
	 * Get Geteilt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isSharedPrinterConfig();

    /** Column definition for IsSharedPrinterConfig */
    public static final org.adempiere.model.ModelColumn<I_AD_Printer_Config, Object> COLUMN_IsSharedPrinterConfig = new org.adempiere.model.ModelColumn<I_AD_Printer_Config, Object>(I_AD_Printer_Config.class, "IsSharedPrinterConfig", null);
    /** Column name IsSharedPrinterConfig */
    public static final String COLUMNNAME_IsSharedPrinterConfig = "IsSharedPrinterConfig";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Printer_Config, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_Printer_Config, Object>(I_AD_Printer_Config.class, "Updated", null);
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
	 * Set Workplace.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Workplace_ID (int C_Workplace_ID);

	/**
	 * Get Workplace.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Workplace_ID();

	@Nullable
	org.compiere.model.I_C_Workplace getC_Workplace();

	void setC_Workplace(@Nullable org.compiere.model.I_C_Workplace C_Workplace);

	ModelColumn<I_AD_Printer_Config, I_C_Workplace> COLUMN_C_Workplace_ID = new ModelColumn<>(I_AD_Printer_Config.class, "C_Workplace_ID", org.compiere.model.I_C_Workplace.class);
	String COLUMNNAME_C_Workplace_ID = "C_Workplace_ID";

}
