package de.metas.printing.model;


/** Generated Interface for C_Printing_Queue_PrintInfo_v
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Printing_Queue_PrintInfo_v 
{

    /** TableName=C_Printing_Queue_PrintInfo_v */
    public static final String Table_Name = "C_Printing_Queue_PrintInfo_v";

    /** AD_Table_ID=541002 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Archiv.
	 * Archiv für Belege und Berichte
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Archive_ID (int AD_Archive_ID);

	/**
	 * Get Archiv.
	 * Archiv für Belege und Berichte
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Archive_ID();

	public org.compiere.model.I_AD_Archive getAD_Archive();

	public void setAD_Archive(org.compiere.model.I_AD_Archive AD_Archive);

    /** Column definition for AD_Archive_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Printing_Queue_PrintInfo_v, org.compiere.model.I_AD_Archive> COLUMN_AD_Archive_ID = new org.adempiere.model.ModelColumn<I_C_Printing_Queue_PrintInfo_v, org.compiere.model.I_AD_Archive>(I_C_Printing_Queue_PrintInfo_v.class, "AD_Archive_ID", org.compiere.model.I_AD_Archive.class);
    /** Column name AD_Archive_ID */
    public static final String COLUMNNAME_AD_Archive_ID = "AD_Archive_ID";

	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set ad_org_print_job_instructions_id.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_Print_Job_Instructions_ID (int AD_Org_Print_Job_Instructions_ID);

	/**
	 * Get ad_org_print_job_instructions_id.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_Print_Job_Instructions_ID();

    /** Column name AD_Org_Print_Job_Instructions_ID */
    public static final String COLUMNNAME_AD_Org_Print_Job_Instructions_ID = "AD_Org_Print_Job_Instructions_ID";

	/**
	 * Set Hardware-Drucker.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_PrinterHW_ID (int AD_PrinterHW_ID);

	/**
	 * Get Hardware-Drucker.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_PrinterHW_ID();

	public de.metas.printing.model.I_AD_PrinterHW getAD_PrinterHW();

	public void setAD_PrinterHW(de.metas.printing.model.I_AD_PrinterHW AD_PrinterHW);

    /** Column definition for AD_PrinterHW_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Printing_Queue_PrintInfo_v, de.metas.printing.model.I_AD_PrinterHW> COLUMN_AD_PrinterHW_ID = new org.adempiere.model.ModelColumn<I_C_Printing_Queue_PrintInfo_v, de.metas.printing.model.I_AD_PrinterHW>(I_C_Printing_Queue_PrintInfo_v.class, "AD_PrinterHW_ID", de.metas.printing.model.I_AD_PrinterHW.class);
    /** Column name AD_PrinterHW_ID */
    public static final String COLUMNNAME_AD_PrinterHW_ID = "AD_PrinterHW_ID";

	/**
	 * Set Hardware-Schacht.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_PrinterHW_MediaTray_ID (int AD_PrinterHW_MediaTray_ID);

	/**
	 * Get Hardware-Schacht.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_PrinterHW_MediaTray_ID();

	public de.metas.printing.model.I_AD_PrinterHW_MediaTray getAD_PrinterHW_MediaTray();

	public void setAD_PrinterHW_MediaTray(de.metas.printing.model.I_AD_PrinterHW_MediaTray AD_PrinterHW_MediaTray);

    /** Column definition for AD_PrinterHW_MediaTray_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Printing_Queue_PrintInfo_v, de.metas.printing.model.I_AD_PrinterHW_MediaTray> COLUMN_AD_PrinterHW_MediaTray_ID = new org.adempiere.model.ModelColumn<I_C_Printing_Queue_PrintInfo_v, de.metas.printing.model.I_AD_PrinterHW_MediaTray>(I_C_Printing_Queue_PrintInfo_v.class, "AD_PrinterHW_MediaTray_ID", de.metas.printing.model.I_AD_PrinterHW_MediaTray.class);
    /** Column name AD_PrinterHW_MediaTray_ID */
    public static final String COLUMNNAME_AD_PrinterHW_MediaTray_ID = "AD_PrinterHW_MediaTray_ID";

	/**
	 * Set Session Printpackage ID.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Session_Printpackage_ID (int AD_Session_Printpackage_ID);

	/**
	 * Get Session Printpackage ID.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Session_Printpackage_ID();

	public org.compiere.model.I_AD_Session getAD_Session_Printpackage();

	public void setAD_Session_Printpackage(org.compiere.model.I_AD_Session AD_Session_Printpackage);

    /** Column definition for AD_Session_Printpackage_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Printing_Queue_PrintInfo_v, org.compiere.model.I_AD_Session> COLUMN_AD_Session_Printpackage_ID = new org.adempiere.model.ModelColumn<I_C_Printing_Queue_PrintInfo_v, org.compiere.model.I_AD_Session>(I_C_Printing_Queue_PrintInfo_v.class, "AD_Session_Printpackage_ID", org.compiere.model.I_AD_Session.class);
    /** Column name AD_Session_Printpackage_ID */
    public static final String COLUMNNAME_AD_Session_Printpackage_ID = "AD_Session_Printpackage_ID";

	/**
	 * Set Druck-Warteschlangendatensatz.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Printing_Queue_ID (int C_Printing_Queue_ID);

	/**
	 * Get Druck-Warteschlangendatensatz.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Printing_Queue_ID();

    /** Column definition for C_Printing_Queue_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Printing_Queue_PrintInfo_v, de.metas.printing.model.I_C_Printing_Queue> COLUMN_C_Printing_Queue_ID = new org.adempiere.model.ModelColumn<I_C_Printing_Queue_PrintInfo_v, de.metas.printing.model.I_C_Printing_Queue>(I_C_Printing_Queue_PrintInfo_v.class, "C_Printing_Queue_ID", de.metas.printing.model.I_C_Printing_Queue.class);
    /** Column name C_Printing_Queue_ID */
    public static final String COLUMNNAME_C_Printing_Queue_ID = "C_Printing_Queue_ID";

	/**
	 * Set Druck-Job Anweisung.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Print_Job_Instructions_ID (int C_Print_Job_Instructions_ID);

	/**
	 * Get Druck-Job Anweisung.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Print_Job_Instructions_ID();

	public de.metas.printing.model.I_C_Print_Job_Instructions getC_Print_Job_Instructions();

	public void setC_Print_Job_Instructions(de.metas.printing.model.I_C_Print_Job_Instructions C_Print_Job_Instructions);

    /** Column definition for C_Print_Job_Instructions_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Printing_Queue_PrintInfo_v, de.metas.printing.model.I_C_Print_Job_Instructions> COLUMN_C_Print_Job_Instructions_ID = new org.adempiere.model.ModelColumn<I_C_Printing_Queue_PrintInfo_v, de.metas.printing.model.I_C_Print_Job_Instructions>(I_C_Printing_Queue_PrintInfo_v.class, "C_Print_Job_Instructions_ID", de.metas.printing.model.I_C_Print_Job_Instructions.class);
    /** Column name C_Print_Job_Instructions_ID */
    public static final String COLUMNNAME_C_Print_Job_Instructions_ID = "C_Print_Job_Instructions_ID";

	/**
	 * Set Druckpaket.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Print_Package_ID (int C_Print_Package_ID);

	/**
	 * Get Druckpaket.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Print_Package_ID();

	public de.metas.printing.model.I_C_Print_Package getC_Print_Package();

	public void setC_Print_Package(de.metas.printing.model.I_C_Print_Package C_Print_Package);

    /** Column definition for C_Print_Package_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Printing_Queue_PrintInfo_v, de.metas.printing.model.I_C_Print_Package> COLUMN_C_Print_Package_ID = new org.adempiere.model.ModelColumn<I_C_Printing_Queue_PrintInfo_v, de.metas.printing.model.I_C_Print_Package>(I_C_Printing_Queue_PrintInfo_v.class, "C_Print_Package_ID", de.metas.printing.model.I_C_Print_Package.class);
    /** Column name C_Print_Package_ID */
    public static final String COLUMNNAME_C_Print_Package_ID = "C_Print_Package_ID";

	/**
	 * Set Druckpaket-Info.
	 * Contains details for the print package, like printer, tray, pages from/to and print service name.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Print_PackageInfo_ID (int C_Print_PackageInfo_ID);

	/**
	 * Get Druckpaket-Info.
	 * Contains details for the print package, like printer, tray, pages from/to and print service name.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Print_PackageInfo_ID();

	public de.metas.printing.model.I_C_Print_PackageInfo getC_Print_PackageInfo();

	public void setC_Print_PackageInfo(de.metas.printing.model.I_C_Print_PackageInfo C_Print_PackageInfo);

    /** Column definition for C_Print_PackageInfo_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Printing_Queue_PrintInfo_v, de.metas.printing.model.I_C_Print_PackageInfo> COLUMN_C_Print_PackageInfo_ID = new org.adempiere.model.ModelColumn<I_C_Printing_Queue_PrintInfo_v, de.metas.printing.model.I_C_Print_PackageInfo>(I_C_Printing_Queue_PrintInfo_v.class, "C_Print_PackageInfo_ID", de.metas.printing.model.I_C_Print_PackageInfo.class);
    /** Column name C_Print_PackageInfo_ID */
    public static final String COLUMNNAME_C_Print_PackageInfo_ID = "C_Print_PackageInfo_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_C_Printing_Queue_PrintInfo_v, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_Printing_Queue_PrintInfo_v, Object>(I_C_Printing_Queue_PrintInfo_v.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Set createdby_print_job_instructions.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCreatedby_Print_Job_Instructions (int Createdby_Print_Job_Instructions);

	/**
	 * Get createdby_print_job_instructions.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getCreatedby_Print_Job_Instructions();

    /** Column definition for Createdby_Print_Job_Instructions */
    public static final org.adempiere.model.ModelColumn<I_C_Printing_Queue_PrintInfo_v, Object> COLUMN_Createdby_Print_Job_Instructions = new org.adempiere.model.ModelColumn<I_C_Printing_Queue_PrintInfo_v, Object>(I_C_Printing_Queue_PrintInfo_v.class, "Createdby_Print_Job_Instructions", null);
    /** Column name Createdby_Print_Job_Instructions */
    public static final String COLUMNNAME_Createdby_Print_Job_Instructions = "Createdby_Print_Job_Instructions";

	/**
	 * Set created_print_job_instructions.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCreated_Print_Job_Instructions (java.sql.Timestamp Created_Print_Job_Instructions);

	/**
	 * Get created_print_job_instructions.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated_Print_Job_Instructions();

    /** Column definition for Created_Print_Job_Instructions */
    public static final org.adempiere.model.ModelColumn<I_C_Printing_Queue_PrintInfo_v, Object> COLUMN_Created_Print_Job_Instructions = new org.adempiere.model.ModelColumn<I_C_Printing_Queue_PrintInfo_v, Object>(I_C_Printing_Queue_PrintInfo_v.class, "Created_Print_Job_Instructions", null);
    /** Column name Created_Print_Job_Instructions */
    public static final String COLUMNNAME_Created_Print_Job_Instructions = "Created_Print_Job_Instructions";

	/**
	 * Set Print service name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPrintServiceName (java.lang.String PrintServiceName);

	/**
	 * Get Print service name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPrintServiceName();

    /** Column definition for PrintServiceName */
    public static final org.adempiere.model.ModelColumn<I_C_Printing_Queue_PrintInfo_v, Object> COLUMN_PrintServiceName = new org.adempiere.model.ModelColumn<I_C_Printing_Queue_PrintInfo_v, Object>(I_C_Printing_Queue_PrintInfo_v.class, "PrintServiceName", null);
    /** Column name PrintServiceName */
    public static final String COLUMNNAME_PrintServiceName = "PrintServiceName";

	/**
	 * Set Print Service Tray.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPrintServiceTray (java.lang.String PrintServiceTray);

	/**
	 * Get Print Service Tray.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPrintServiceTray();

    /** Column definition for PrintServiceTray */
    public static final org.adempiere.model.ModelColumn<I_C_Printing_Queue_PrintInfo_v, Object> COLUMN_PrintServiceTray = new org.adempiere.model.ModelColumn<I_C_Printing_Queue_PrintInfo_v, Object>(I_C_Printing_Queue_PrintInfo_v.class, "PrintServiceTray", null);
    /** Column name PrintServiceTray */
    public static final String COLUMNNAME_PrintServiceTray = "PrintServiceTray";

	/**
	 * Set Status Print Job Instructions.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setStatus_Print_Job_Instructions (boolean Status_Print_Job_Instructions);

	/**
	 * Get Status Print Job Instructions.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isStatus_Print_Job_Instructions();

    /** Column definition for Status_Print_Job_Instructions */
    public static final org.adempiere.model.ModelColumn<I_C_Printing_Queue_PrintInfo_v, Object> COLUMN_Status_Print_Job_Instructions = new org.adempiere.model.ModelColumn<I_C_Printing_Queue_PrintInfo_v, Object>(I_C_Printing_Queue_PrintInfo_v.class, "Status_Print_Job_Instructions", null);
    /** Column name Status_Print_Job_Instructions */
    public static final String COLUMNNAME_Status_Print_Job_Instructions = "Status_Print_Job_Instructions";

	/**
	 * Set Schachtnummer.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setTrayNumber (int TrayNumber);

	/**
	 * Get Schachtnummer.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getTrayNumber();

    /** Column definition for TrayNumber */
    public static final org.adempiere.model.ModelColumn<I_C_Printing_Queue_PrintInfo_v, Object> COLUMN_TrayNumber = new org.adempiere.model.ModelColumn<I_C_Printing_Queue_PrintInfo_v, Object>(I_C_Printing_Queue_PrintInfo_v.class, "TrayNumber", null);
    /** Column name TrayNumber */
    public static final String COLUMNNAME_TrayNumber = "TrayNumber";

	/**
	 * Set updatedby_print_job_instructions.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setUpdatedby_Print_Job_Instructions (int Updatedby_Print_Job_Instructions);

	/**
	 * Get updatedby_print_job_instructions.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getUpdatedby_Print_Job_Instructions();

    /** Column definition for Updatedby_Print_Job_Instructions */
    public static final org.adempiere.model.ModelColumn<I_C_Printing_Queue_PrintInfo_v, Object> COLUMN_Updatedby_Print_Job_Instructions = new org.adempiere.model.ModelColumn<I_C_Printing_Queue_PrintInfo_v, Object>(I_C_Printing_Queue_PrintInfo_v.class, "Updatedby_Print_Job_Instructions", null);
    /** Column name Updatedby_Print_Job_Instructions */
    public static final String COLUMNNAME_Updatedby_Print_Job_Instructions = "Updatedby_Print_Job_Instructions";

	/**
	 * Set updated_print_job_instructions.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setUpdated_Print_Job_Instructions (java.sql.Timestamp Updated_Print_Job_Instructions);

	/**
	 * Get updated_print_job_instructions.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated_Print_Job_Instructions();

    /** Column definition for Updated_Print_Job_Instructions */
    public static final org.adempiere.model.ModelColumn<I_C_Printing_Queue_PrintInfo_v, Object> COLUMN_Updated_Print_Job_Instructions = new org.adempiere.model.ModelColumn<I_C_Printing_Queue_PrintInfo_v, Object>(I_C_Printing_Queue_PrintInfo_v.class, "Updated_Print_Job_Instructions", null);
    /** Column name Updated_Print_Job_Instructions */
    public static final String COLUMNNAME_Updated_Print_Job_Instructions = "Updated_Print_Job_Instructions";
}
