package de.metas.printing.model;


/** Generated Interface for C_Order_MFGWarehouse_Report_PrintInfo_v
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Order_MFGWarehouse_Report_PrintInfo_v 
{

    /** TableName=C_Order_MFGWarehouse_Report_PrintInfo_v */
    public static final String Table_Name = "C_Order_MFGWarehouse_Report_PrintInfo_v";

    /** AD_Table_ID=541001 */
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
    public static final org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report_PrintInfo_v, org.compiere.model.I_AD_Archive> COLUMN_AD_Archive_ID = new org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report_PrintInfo_v, org.compiere.model.I_AD_Archive>(I_C_Order_MFGWarehouse_Report_PrintInfo_v.class, "AD_Archive_ID", org.compiere.model.I_AD_Archive.class);
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
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report_PrintInfo_v, de.metas.printing.model.I_AD_PrinterHW> COLUMN_AD_PrinterHW_ID = new org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report_PrintInfo_v, de.metas.printing.model.I_AD_PrinterHW>(I_C_Order_MFGWarehouse_Report_PrintInfo_v.class, "AD_PrinterHW_ID", de.metas.printing.model.I_AD_PrinterHW.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report_PrintInfo_v, de.metas.printing.model.I_AD_PrinterHW_MediaTray> COLUMN_AD_PrinterHW_MediaTray_ID = new org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report_PrintInfo_v, de.metas.printing.model.I_AD_PrinterHW_MediaTray>(I_C_Order_MFGWarehouse_Report_PrintInfo_v.class, "AD_PrinterHW_MediaTray_ID", de.metas.printing.model.I_AD_PrinterHW_MediaTray.class);
    /** Column name AD_PrinterHW_MediaTray_ID */
    public static final String COLUMNNAME_AD_PrinterHW_MediaTray_ID = "AD_PrinterHW_MediaTray_ID";

	/**
	 * Set ad_session_printpackage_id.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Session_Printpackage_ID (int AD_Session_Printpackage_ID);

	/**
	 * Get ad_session_printpackage_id.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Session_Printpackage_ID();

	public org.compiere.model.I_AD_Session getAD_Session_Printpackage();

	public void setAD_Session_Printpackage(org.compiere.model.I_AD_Session AD_Session_Printpackage);

    /** Column definition for AD_Session_Printpackage_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report_PrintInfo_v, org.compiere.model.I_AD_Session> COLUMN_AD_Session_Printpackage_ID = new org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report_PrintInfo_v, org.compiere.model.I_AD_Session>(I_C_Order_MFGWarehouse_Report_PrintInfo_v.class, "AD_Session_Printpackage_ID", org.compiere.model.I_AD_Session.class);
    /** Column name AD_Session_Printpackage_ID */
    public static final String COLUMNNAME_AD_Session_Printpackage_ID = "AD_Session_Printpackage_ID";

	/**
	 * Set Verantwortlicher Benutzer.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_User_Responsible_ID (int AD_User_Responsible_ID);

	/**
	 * Get Verantwortlicher Benutzer.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_User_Responsible_ID();

    /** Column name AD_User_Responsible_ID */
    public static final String COLUMNNAME_AD_User_Responsible_ID = "AD_User_Responsible_ID";

	/**
	 * Set Sales order.
	 * Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Order_ID (int C_Order_ID);

	/**
	 * Get Sales order.
	 * Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Order_ID();

	public org.compiere.model.I_C_Order getC_Order();

	public void setC_Order(org.compiere.model.I_C_Order C_Order);

    /** Column definition for C_Order_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report_PrintInfo_v, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report_PrintInfo_v, org.compiere.model.I_C_Order>(I_C_Order_MFGWarehouse_Report_PrintInfo_v.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
    /** Column name C_Order_ID */
    public static final String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/**
	 * Set Order / MFG Warehouse report.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Order_MFGWarehouse_Report_ID (int C_Order_MFGWarehouse_Report_ID);

	/**
	 * Get Order / MFG Warehouse report.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Order_MFGWarehouse_Report_ID();

    /** Column definition for C_Order_MFGWarehouse_Report_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report_PrintInfo_v, Object> COLUMN_C_Order_MFGWarehouse_Report_ID = new org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report_PrintInfo_v, Object>(I_C_Order_MFGWarehouse_Report_PrintInfo_v.class, "C_Order_MFGWarehouse_Report_ID", null);
    /** Column name C_Order_MFGWarehouse_Report_ID */
    public static final String COLUMNNAME_C_Order_MFGWarehouse_Report_ID = "C_Order_MFGWarehouse_Report_ID";

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

	public de.metas.printing.model.I_C_Printing_Queue getC_Printing_Queue();

	public void setC_Printing_Queue(de.metas.printing.model.I_C_Printing_Queue C_Printing_Queue);

    /** Column definition for C_Printing_Queue_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report_PrintInfo_v, de.metas.printing.model.I_C_Printing_Queue> COLUMN_C_Printing_Queue_ID = new org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report_PrintInfo_v, de.metas.printing.model.I_C_Printing_Queue>(I_C_Order_MFGWarehouse_Report_PrintInfo_v.class, "C_Printing_Queue_ID", de.metas.printing.model.I_C_Printing_Queue.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report_PrintInfo_v, de.metas.printing.model.I_C_Print_Job_Instructions> COLUMN_C_Print_Job_Instructions_ID = new org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report_PrintInfo_v, de.metas.printing.model.I_C_Print_Job_Instructions>(I_C_Order_MFGWarehouse_Report_PrintInfo_v.class, "C_Print_Job_Instructions_ID", de.metas.printing.model.I_C_Print_Job_Instructions.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report_PrintInfo_v, de.metas.printing.model.I_C_Print_Package> COLUMN_C_Print_Package_ID = new org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report_PrintInfo_v, de.metas.printing.model.I_C_Print_Package>(I_C_Order_MFGWarehouse_Report_PrintInfo_v.class, "C_Print_Package_ID", de.metas.printing.model.I_C_Print_Package.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report_PrintInfo_v, de.metas.printing.model.I_C_Print_PackageInfo> COLUMN_C_Print_PackageInfo_ID = new org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report_PrintInfo_v, de.metas.printing.model.I_C_Print_PackageInfo>(I_C_Order_MFGWarehouse_Report_PrintInfo_v.class, "C_Print_PackageInfo_ID", de.metas.printing.model.I_C_Print_PackageInfo.class);
    /** Column name C_Print_PackageInfo_ID */
    public static final String COLUMNNAME_C_Print_PackageInfo_ID = "C_Print_PackageInfo_ID";

	/**
	 * Set Element Queue.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Queue_Element_ID (int C_Queue_Element_ID);

	/**
	 * Get Element Queue.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Queue_Element_ID();

    /** Column definition for C_Queue_Element_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report_PrintInfo_v, Object> COLUMN_C_Queue_Element_ID = new org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report_PrintInfo_v, Object>(I_C_Order_MFGWarehouse_Report_PrintInfo_v.class, "C_Queue_Element_ID", null);
    /** Column name C_Queue_Element_ID */
    public static final String COLUMNNAME_C_Queue_Element_ID = "C_Queue_Element_ID";

	/**
	 * Set WorkPackage Queue.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Queue_WorkPackage_ID (int C_Queue_WorkPackage_ID);

	/**
	 * Get WorkPackage Queue.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Queue_WorkPackage_ID();

    /** Column definition for C_Queue_WorkPackage_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report_PrintInfo_v, Object> COLUMN_C_Queue_WorkPackage_ID = new org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report_PrintInfo_v, Object>(I_C_Order_MFGWarehouse_Report_PrintInfo_v.class, "C_Queue_WorkPackage_ID", null);
    /** Column name C_Queue_WorkPackage_ID */
    public static final String COLUMNNAME_C_Queue_WorkPackage_ID = "C_Queue_WorkPackage_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report_PrintInfo_v, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report_PrintInfo_v, Object>(I_C_Order_MFGWarehouse_Report_PrintInfo_v.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Warehouse_ID (int M_Warehouse_ID);

	/**
	 * Get Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Warehouse_ID();

    /** Column name M_Warehouse_ID */
    public static final String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report_PrintInfo_v, Object> COLUMN_PrintServiceName = new org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report_PrintInfo_v, Object>(I_C_Order_MFGWarehouse_Report_PrintInfo_v.class, "PrintServiceName", null);
    /** Column name PrintServiceName */
    public static final String COLUMNNAME_PrintServiceName = "PrintServiceName";

	/**
	 * Set printservicetray.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPrintServiceTray (java.lang.String PrintServiceTray);

	/**
	 * Get printservicetray.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPrintServiceTray();

    /** Column definition for PrintServiceTray */
    public static final org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report_PrintInfo_v, Object> COLUMN_PrintServiceTray = new org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report_PrintInfo_v, Object>(I_C_Order_MFGWarehouse_Report_PrintInfo_v.class, "PrintServiceTray", null);
    /** Column name PrintServiceTray */
    public static final String COLUMNNAME_PrintServiceTray = "PrintServiceTray";

	/**
	 * Set Ressource.
	 * Ressource
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setS_Resource_ID (int S_Resource_ID);

	/**
	 * Get Ressource.
	 * Ressource
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getS_Resource_ID();

	public org.compiere.model.I_S_Resource getS_Resource();

	public void setS_Resource(org.compiere.model.I_S_Resource S_Resource);

    /** Column definition for S_Resource_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report_PrintInfo_v, org.compiere.model.I_S_Resource> COLUMN_S_Resource_ID = new org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report_PrintInfo_v, org.compiere.model.I_S_Resource>(I_C_Order_MFGWarehouse_Report_PrintInfo_v.class, "S_Resource_ID", org.compiere.model.I_S_Resource.class);
    /** Column name S_Resource_ID */
    public static final String COLUMNNAME_S_Resource_ID = "S_Resource_ID";

	/**
	 * Set status_print_job_instructions.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setStatus_Print_Job_Instructions (boolean Status_Print_Job_Instructions);

	/**
	 * Get status_print_job_instructions.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isStatus_Print_Job_Instructions();

    /** Column definition for Status_Print_Job_Instructions */
    public static final org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report_PrintInfo_v, Object> COLUMN_Status_Print_Job_Instructions = new org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report_PrintInfo_v, Object>(I_C_Order_MFGWarehouse_Report_PrintInfo_v.class, "Status_Print_Job_Instructions", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report_PrintInfo_v, Object> COLUMN_TrayNumber = new org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report_PrintInfo_v, Object>(I_C_Order_MFGWarehouse_Report_PrintInfo_v.class, "TrayNumber", null);
    /** Column name TrayNumber */
    public static final String COLUMNNAME_TrayNumber = "TrayNumber";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report_PrintInfo_v, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report_PrintInfo_v, Object>(I_C_Order_MFGWarehouse_Report_PrintInfo_v.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set wp_iserror.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setWP_IsError (boolean WP_IsError);

	/**
	 * Get wp_iserror.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isWP_IsError();

    /** Column definition for WP_IsError */
    public static final org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report_PrintInfo_v, Object> COLUMN_WP_IsError = new org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report_PrintInfo_v, Object>(I_C_Order_MFGWarehouse_Report_PrintInfo_v.class, "WP_IsError", null);
    /** Column name WP_IsError */
    public static final String COLUMNNAME_WP_IsError = "WP_IsError";

	/**
	 * Set wp_isprocessed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setWP_IsProcessed (boolean WP_IsProcessed);

	/**
	 * Get wp_isprocessed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isWP_IsProcessed();

    /** Column definition for WP_IsProcessed */
    public static final org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report_PrintInfo_v, Object> COLUMN_WP_IsProcessed = new org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report_PrintInfo_v, Object>(I_C_Order_MFGWarehouse_Report_PrintInfo_v.class, "WP_IsProcessed", null);
    /** Column name WP_IsProcessed */
    public static final String COLUMNNAME_WP_IsProcessed = "WP_IsProcessed";
}
