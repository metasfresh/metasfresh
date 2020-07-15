/** Generated Model - DO NOT CHANGE */
package de.metas.printing.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_PrinterHW_Calibration
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_PrinterHW_Calibration extends org.compiere.model.PO implements I_AD_PrinterHW_Calibration, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1765557009L;

    /** Standard Constructor */
    public X_AD_PrinterHW_Calibration (Properties ctx, int AD_PrinterHW_Calibration_ID, String trxName)
    {
      super (ctx, AD_PrinterHW_Calibration_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_PrinterHW_Calibration (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setAD_PrinterHW_Calibration_ID (int AD_PrinterHW_Calibration_ID)
	{
		if (AD_PrinterHW_Calibration_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_PrinterHW_Calibration_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_PrinterHW_Calibration_ID, Integer.valueOf(AD_PrinterHW_Calibration_ID));
	}

	@Override
	public int getAD_PrinterHW_Calibration_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_PrinterHW_Calibration_ID);
	}

	@Override
	public de.metas.printing.model.I_AD_PrinterHW getAD_PrinterHW()
	{
		return get_ValueAsPO(COLUMNNAME_AD_PrinterHW_ID, de.metas.printing.model.I_AD_PrinterHW.class);
	}

	@Override
	public void setAD_PrinterHW(de.metas.printing.model.I_AD_PrinterHW AD_PrinterHW)
	{
		set_ValueFromPO(COLUMNNAME_AD_PrinterHW_ID, de.metas.printing.model.I_AD_PrinterHW.class, AD_PrinterHW);
	}

	@Override
	public void setAD_PrinterHW_ID (int AD_PrinterHW_ID)
	{
		if (AD_PrinterHW_ID < 1) 
			set_Value (COLUMNNAME_AD_PrinterHW_ID, null);
		else 
			set_Value (COLUMNNAME_AD_PrinterHW_ID, Integer.valueOf(AD_PrinterHW_ID));
	}

	@Override
	public int getAD_PrinterHW_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_PrinterHW_ID);
	}

	@Override
	public de.metas.printing.model.I_AD_PrinterHW_MediaSize getAD_PrinterHW_MediaSize()
	{
		return get_ValueAsPO(COLUMNNAME_AD_PrinterHW_MediaSize_ID, de.metas.printing.model.I_AD_PrinterHW_MediaSize.class);
	}

	@Override
	public void setAD_PrinterHW_MediaSize(de.metas.printing.model.I_AD_PrinterHW_MediaSize AD_PrinterHW_MediaSize)
	{
		set_ValueFromPO(COLUMNNAME_AD_PrinterHW_MediaSize_ID, de.metas.printing.model.I_AD_PrinterHW_MediaSize.class, AD_PrinterHW_MediaSize);
	}

	@Override
	public void setAD_PrinterHW_MediaSize_ID (int AD_PrinterHW_MediaSize_ID)
	{
		if (AD_PrinterHW_MediaSize_ID < 1) 
			set_Value (COLUMNNAME_AD_PrinterHW_MediaSize_ID, null);
		else 
			set_Value (COLUMNNAME_AD_PrinterHW_MediaSize_ID, Integer.valueOf(AD_PrinterHW_MediaSize_ID));
	}

	@Override
	public int getAD_PrinterHW_MediaSize_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_PrinterHW_MediaSize_ID);
	}

	@Override
	public de.metas.printing.model.I_AD_PrinterHW_MediaTray getAD_PrinterHW_MediaTray()
	{
		return get_ValueAsPO(COLUMNNAME_AD_PrinterHW_MediaTray_ID, de.metas.printing.model.I_AD_PrinterHW_MediaTray.class);
	}

	@Override
	public void setAD_PrinterHW_MediaTray(de.metas.printing.model.I_AD_PrinterHW_MediaTray AD_PrinterHW_MediaTray)
	{
		set_ValueFromPO(COLUMNNAME_AD_PrinterHW_MediaTray_ID, de.metas.printing.model.I_AD_PrinterHW_MediaTray.class, AD_PrinterHW_MediaTray);
	}

	@Override
	public void setAD_PrinterHW_MediaTray_ID (int AD_PrinterHW_MediaTray_ID)
	{
		if (AD_PrinterHW_MediaTray_ID < 1) 
			set_Value (COLUMNNAME_AD_PrinterHW_MediaTray_ID, null);
		else 
			set_Value (COLUMNNAME_AD_PrinterHW_MediaTray_ID, Integer.valueOf(AD_PrinterHW_MediaTray_ID));
	}

	@Override
	public int getAD_PrinterHW_MediaTray_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_PrinterHW_MediaTray_ID);
	}

	@Override
	public void setCalX (int CalX)
	{
		set_Value (COLUMNNAME_CalX, Integer.valueOf(CalX));
	}

	@Override
	public int getCalX() 
	{
		return get_ValueAsInt(COLUMNNAME_CalX);
	}

	@Override
	public void setCalY (int CalY)
	{
		set_Value (COLUMNNAME_CalY, Integer.valueOf(CalY));
	}

	@Override
	public int getCalY() 
	{
		return get_ValueAsInt(COLUMNNAME_CalY);
	}

	@Override
	public void setHostKey (java.lang.String HostKey)
	{
		throw new IllegalArgumentException ("HostKey is virtual column");	}

	@Override
	public java.lang.String getHostKey() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_HostKey);
	}

	@Override
	public void setIsManualCalibration (boolean IsManualCalibration)
	{
		set_Value (COLUMNNAME_IsManualCalibration, Boolean.valueOf(IsManualCalibration));
	}

	@Override
	public boolean isManualCalibration() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsManualCalibration);
	}

	@Override
	public void setMeasurementX (java.math.BigDecimal MeasurementX)
	{
		set_Value (COLUMNNAME_MeasurementX, MeasurementX);
	}

	@Override
	public java.math.BigDecimal getMeasurementX() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_MeasurementX);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setMeasurementY (java.math.BigDecimal MeasurementY)
	{
		set_Value (COLUMNNAME_MeasurementY, MeasurementY);
	}

	@Override
	public java.math.BigDecimal getMeasurementY() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_MeasurementY);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}