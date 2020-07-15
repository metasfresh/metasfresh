package de.metas.printing.model;


/** Generated Interface for AD_PrinterHW_Calibration
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_PrinterHW_Calibration 
{

    /** TableName=AD_PrinterHW_Calibration */
    public static final String Table_Name = "AD_PrinterHW_Calibration";

    /** AD_Table_ID=540448 */
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
	 * Set Hardware printer calibration.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_PrinterHW_Calibration_ID (int AD_PrinterHW_Calibration_ID);

	/**
	 * Get Hardware printer calibration.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_PrinterHW_Calibration_ID();

    /** Column definition for AD_PrinterHW_Calibration_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_PrinterHW_Calibration, Object> COLUMN_AD_PrinterHW_Calibration_ID = new org.adempiere.model.ModelColumn<I_AD_PrinterHW_Calibration, Object>(I_AD_PrinterHW_Calibration.class, "AD_PrinterHW_Calibration_ID", null);
    /** Column name AD_PrinterHW_Calibration_ID */
    public static final String COLUMNNAME_AD_PrinterHW_Calibration_ID = "AD_PrinterHW_Calibration_ID";

	/**
	 * Set Hardware-Drucker.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_PrinterHW_ID (int AD_PrinterHW_ID);

	/**
	 * Get Hardware-Drucker.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_PrinterHW_ID();

	public de.metas.printing.model.I_AD_PrinterHW getAD_PrinterHW();

	public void setAD_PrinterHW(de.metas.printing.model.I_AD_PrinterHW AD_PrinterHW);

    /** Column definition for AD_PrinterHW_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_PrinterHW_Calibration, de.metas.printing.model.I_AD_PrinterHW> COLUMN_AD_PrinterHW_ID = new org.adempiere.model.ModelColumn<I_AD_PrinterHW_Calibration, de.metas.printing.model.I_AD_PrinterHW>(I_AD_PrinterHW_Calibration.class, "AD_PrinterHW_ID", de.metas.printing.model.I_AD_PrinterHW.class);
    /** Column name AD_PrinterHW_ID */
    public static final String COLUMNNAME_AD_PrinterHW_ID = "AD_PrinterHW_ID";

	/**
	 * Set Papiergröße.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_PrinterHW_MediaSize_ID (int AD_PrinterHW_MediaSize_ID);

	/**
	 * Get Papiergröße.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_PrinterHW_MediaSize_ID();

	public de.metas.printing.model.I_AD_PrinterHW_MediaSize getAD_PrinterHW_MediaSize();

	public void setAD_PrinterHW_MediaSize(de.metas.printing.model.I_AD_PrinterHW_MediaSize AD_PrinterHW_MediaSize);

    /** Column definition for AD_PrinterHW_MediaSize_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_PrinterHW_Calibration, de.metas.printing.model.I_AD_PrinterHW_MediaSize> COLUMN_AD_PrinterHW_MediaSize_ID = new org.adempiere.model.ModelColumn<I_AD_PrinterHW_Calibration, de.metas.printing.model.I_AD_PrinterHW_MediaSize>(I_AD_PrinterHW_Calibration.class, "AD_PrinterHW_MediaSize_ID", de.metas.printing.model.I_AD_PrinterHW_MediaSize.class);
    /** Column name AD_PrinterHW_MediaSize_ID */
    public static final String COLUMNNAME_AD_PrinterHW_MediaSize_ID = "AD_PrinterHW_MediaSize_ID";

	/**
	 * Set Hardware-Schacht.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_PrinterHW_MediaTray_ID (int AD_PrinterHW_MediaTray_ID);

	/**
	 * Get Hardware-Schacht.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_PrinterHW_MediaTray_ID();

	public de.metas.printing.model.I_AD_PrinterHW_MediaTray getAD_PrinterHW_MediaTray();

	public void setAD_PrinterHW_MediaTray(de.metas.printing.model.I_AD_PrinterHW_MediaTray AD_PrinterHW_MediaTray);

    /** Column definition for AD_PrinterHW_MediaTray_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_PrinterHW_Calibration, de.metas.printing.model.I_AD_PrinterHW_MediaTray> COLUMN_AD_PrinterHW_MediaTray_ID = new org.adempiere.model.ModelColumn<I_AD_PrinterHW_Calibration, de.metas.printing.model.I_AD_PrinterHW_MediaTray>(I_AD_PrinterHW_Calibration.class, "AD_PrinterHW_MediaTray_ID", de.metas.printing.model.I_AD_PrinterHW_MediaTray.class);
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
    public static final org.adempiere.model.ModelColumn<I_AD_PrinterHW_Calibration, Object> COLUMN_CalX = new org.adempiere.model.ModelColumn<I_AD_PrinterHW_Calibration, Object>(I_AD_PrinterHW_Calibration.class, "CalX", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_PrinterHW_Calibration, Object> COLUMN_CalY = new org.adempiere.model.ModelColumn<I_AD_PrinterHW_Calibration, Object>(I_AD_PrinterHW_Calibration.class, "CalY", null);
    /** Column name CalY */
    public static final String COLUMNNAME_CalY = "CalY";

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
    public static final org.adempiere.model.ModelColumn<I_AD_PrinterHW_Calibration, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_PrinterHW_Calibration, Object>(I_AD_PrinterHW_Calibration.class, "Created", null);
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
	 * Set Host key.
	 * Unique identifier of a host
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setHostKey (java.lang.String HostKey);

	/**
	 * Get Host key.
	 * Unique identifier of a host
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public java.lang.String getHostKey();

    /** Column definition for HostKey */
    public static final org.adempiere.model.ModelColumn<I_AD_PrinterHW_Calibration, Object> COLUMN_HostKey = new org.adempiere.model.ModelColumn<I_AD_PrinterHW_Calibration, Object>(I_AD_PrinterHW_Calibration.class, "HostKey", null);
    /** Column name HostKey */
    public static final String COLUMNNAME_HostKey = "HostKey";

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
    public static final org.adempiere.model.ModelColumn<I_AD_PrinterHW_Calibration, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_PrinterHW_Calibration, Object>(I_AD_PrinterHW_Calibration.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Kalibrierungsdaten selbst eingeben.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsManualCalibration (boolean IsManualCalibration);

	/**
	 * Get Kalibrierungsdaten selbst eingeben.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isManualCalibration();

    /** Column definition for IsManualCalibration */
    public static final org.adempiere.model.ModelColumn<I_AD_PrinterHW_Calibration, Object> COLUMN_IsManualCalibration = new org.adempiere.model.ModelColumn<I_AD_PrinterHW_Calibration, Object>(I_AD_PrinterHW_Calibration.class, "IsManualCalibration", null);
    /** Column name IsManualCalibration */
    public static final String COLUMNNAME_IsManualCalibration = "IsManualCalibration";

	/**
	 * Set Messung-X.
	 * Mit dem Lineal gemessener X-Wert in mm
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMeasurementX (java.math.BigDecimal MeasurementX);

	/**
	 * Get Messung-X.
	 * Mit dem Lineal gemessener X-Wert in mm
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getMeasurementX();

    /** Column definition for MeasurementX */
    public static final org.adempiere.model.ModelColumn<I_AD_PrinterHW_Calibration, Object> COLUMN_MeasurementX = new org.adempiere.model.ModelColumn<I_AD_PrinterHW_Calibration, Object>(I_AD_PrinterHW_Calibration.class, "MeasurementX", null);
    /** Column name MeasurementX */
    public static final String COLUMNNAME_MeasurementX = "MeasurementX";

	/**
	 * Set Messung-Y.
	 * Mit dem Lineal gemessener Y-Wert in mm
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMeasurementY (java.math.BigDecimal MeasurementY);

	/**
	 * Get Messung-Y.
	 * Mit dem Lineal gemessener Y-Wert in mm
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getMeasurementY();

    /** Column definition for MeasurementY */
    public static final org.adempiere.model.ModelColumn<I_AD_PrinterHW_Calibration, Object> COLUMN_MeasurementY = new org.adempiere.model.ModelColumn<I_AD_PrinterHW_Calibration, Object>(I_AD_PrinterHW_Calibration.class, "MeasurementY", null);
    /** Column name MeasurementY */
    public static final String COLUMNNAME_MeasurementY = "MeasurementY";

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
    public static final org.adempiere.model.ModelColumn<I_AD_PrinterHW_Calibration, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_PrinterHW_Calibration, Object>(I_AD_PrinterHW_Calibration.class, "Updated", null);
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
