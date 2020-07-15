package de.metas.printing.model;


/** Generated Interface for C_Print_PackageInfo
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Print_PackageInfo 
{

    /** TableName=C_Print_PackageInfo */
    public static final String Table_Name = "C_Print_PackageInfo";

    /** AD_Table_ID=540459 */
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
	 * Set Hardware-Drucker.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_PrinterHW_ID (int AD_PrinterHW_ID);

	/**
	 * Get Hardware-Drucker.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_PrinterHW_ID();

	public de.metas.printing.model.I_AD_PrinterHW getAD_PrinterHW();

	public void setAD_PrinterHW(de.metas.printing.model.I_AD_PrinterHW AD_PrinterHW);

    /** Column definition for AD_PrinterHW_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Print_PackageInfo, de.metas.printing.model.I_AD_PrinterHW> COLUMN_AD_PrinterHW_ID = new org.adempiere.model.ModelColumn<I_C_Print_PackageInfo, de.metas.printing.model.I_AD_PrinterHW>(I_C_Print_PackageInfo.class, "AD_PrinterHW_ID", de.metas.printing.model.I_AD_PrinterHW.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_Print_PackageInfo, de.metas.printing.model.I_AD_PrinterHW_MediaTray> COLUMN_AD_PrinterHW_MediaTray_ID = new org.adempiere.model.ModelColumn<I_C_Print_PackageInfo, de.metas.printing.model.I_AD_PrinterHW_MediaTray>(I_C_Print_PackageInfo.class, "AD_PrinterHW_MediaTray_ID", de.metas.printing.model.I_AD_PrinterHW_MediaTray.class);
    /** Column name AD_PrinterHW_MediaTray_ID */
    public static final String COLUMNNAME_AD_PrinterHW_MediaTray_ID = "AD_PrinterHW_MediaTray_ID";

	/**
	 * Set Kalibrierung-X.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCalX (int CalX);

	/**
	 * Get Kalibrierung-X.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getCalX();

    /** Column definition for CalX */
    public static final org.adempiere.model.ModelColumn<I_C_Print_PackageInfo, Object> COLUMN_CalX = new org.adempiere.model.ModelColumn<I_C_Print_PackageInfo, Object>(I_C_Print_PackageInfo.class, "CalX", null);
    /** Column name CalX */
    public static final String COLUMNNAME_CalX = "CalX";

	/**
	 * Set Kalibrierung-Y.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCalY (int CalY);

	/**
	 * Get Kalibrierung-Y.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getCalY();

    /** Column definition for CalY */
    public static final org.adempiere.model.ModelColumn<I_C_Print_PackageInfo, Object> COLUMN_CalY = new org.adempiere.model.ModelColumn<I_C_Print_PackageInfo, Object>(I_C_Print_PackageInfo.class, "CalY", null);
    /** Column name CalY */
    public static final String COLUMNNAME_CalY = "CalY";

	/**
	 * Set Druckpaket.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Print_Package_ID (int C_Print_Package_ID);

	/**
	 * Get Druckpaket.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Print_Package_ID();

	public de.metas.printing.model.I_C_Print_Package getC_Print_Package();

	public void setC_Print_Package(de.metas.printing.model.I_C_Print_Package C_Print_Package);

    /** Column definition for C_Print_Package_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Print_PackageInfo, de.metas.printing.model.I_C_Print_Package> COLUMN_C_Print_Package_ID = new org.adempiere.model.ModelColumn<I_C_Print_PackageInfo, de.metas.printing.model.I_C_Print_Package>(I_C_Print_PackageInfo.class, "C_Print_Package_ID", de.metas.printing.model.I_C_Print_Package.class);
    /** Column name C_Print_Package_ID */
    public static final String COLUMNNAME_C_Print_Package_ID = "C_Print_Package_ID";

	/**
	 * Set Druckpaket-Info.
	 * Contains details for the print package, like printer, tray, pages from/to and print service name.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Print_PackageInfo_ID (int C_Print_PackageInfo_ID);

	/**
	 * Get Druckpaket-Info.
	 * Contains details for the print package, like printer, tray, pages from/to and print service name.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Print_PackageInfo_ID();

    /** Column definition for C_Print_PackageInfo_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Print_PackageInfo, Object> COLUMN_C_Print_PackageInfo_ID = new org.adempiere.model.ModelColumn<I_C_Print_PackageInfo, Object>(I_C_Print_PackageInfo.class, "C_Print_PackageInfo_ID", null);
    /** Column name C_Print_PackageInfo_ID */
    public static final String COLUMNNAME_C_Print_PackageInfo_ID = "C_Print_PackageInfo_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_Print_PackageInfo, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_Print_PackageInfo, Object>(I_C_Print_PackageInfo.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Search
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
    public static final org.adempiere.model.ModelColumn<I_C_Print_PackageInfo, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_Print_PackageInfo, Object>(I_C_Print_PackageInfo.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setName (java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	public java.lang.String getName();

    /** Column definition for Name */
    public static final org.adempiere.model.ModelColumn<I_C_Print_PackageInfo, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_C_Print_PackageInfo, Object>(I_C_Print_PackageInfo.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Von Seite.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPageFrom (int PageFrom);

	/**
	 * Get Von Seite.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getPageFrom();

    /** Column definition for PageFrom */
    public static final org.adempiere.model.ModelColumn<I_C_Print_PackageInfo, Object> COLUMN_PageFrom = new org.adempiere.model.ModelColumn<I_C_Print_PackageInfo, Object>(I_C_Print_PackageInfo.class, "PageFrom", null);
    /** Column name PageFrom */
    public static final String COLUMNNAME_PageFrom = "PageFrom";

	/**
	 * Set Bis Seite.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPageTo (int PageTo);

	/**
	 * Get Bis Seite.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getPageTo();

    /** Column definition for PageTo */
    public static final org.adempiere.model.ModelColumn<I_C_Print_PackageInfo, Object> COLUMN_PageTo = new org.adempiere.model.ModelColumn<I_C_Print_PackageInfo, Object>(I_C_Print_PackageInfo.class, "PageTo", null);
    /** Column name PageTo */
    public static final String COLUMNNAME_PageTo = "PageTo";

	/**
	 * Set Print service name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setPrintServiceName (java.lang.String PrintServiceName);

	/**
	 * Get Print service name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	public java.lang.String getPrintServiceName();

    /** Column definition for PrintServiceName */
    public static final org.adempiere.model.ModelColumn<I_C_Print_PackageInfo, Object> COLUMN_PrintServiceName = new org.adempiere.model.ModelColumn<I_C_Print_PackageInfo, Object>(I_C_Print_PackageInfo.class, "PrintServiceName", null);
    /** Column name PrintServiceName */
    public static final String COLUMNNAME_PrintServiceName = "PrintServiceName";

	/**
	 * Set Schachtnummer.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setTrayNumber (int TrayNumber);

	/**
	 * Get Schachtnummer.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	public int getTrayNumber();

    /** Column definition for TrayNumber */
    public static final org.adempiere.model.ModelColumn<I_C_Print_PackageInfo, Object> COLUMN_TrayNumber = new org.adempiere.model.ModelColumn<I_C_Print_PackageInfo, Object>(I_C_Print_PackageInfo.class, "TrayNumber", null);
    /** Column name TrayNumber */
    public static final String COLUMNNAME_TrayNumber = "TrayNumber";

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
    public static final org.adempiere.model.ModelColumn<I_C_Print_PackageInfo, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_Print_PackageInfo, Object>(I_C_Print_PackageInfo.class, "Updated", null);
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
