/** Generated Model - DO NOT CHANGE */
package de.metas.printing.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_PrinterHW_Calibration
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_PrinterHW_Calibration extends org.compiere.model.PO implements I_AD_PrinterHW_Calibration, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1935363164L;

    /** Standard Constructor */
    public X_AD_PrinterHW_Calibration (Properties ctx, int AD_PrinterHW_Calibration_ID, String trxName)
    {
      super (ctx, AD_PrinterHW_Calibration_ID, trxName);
      /** if (AD_PrinterHW_Calibration_ID == 0)
        {
			setAD_PrinterHW_Calibration_ID (0);
			setAD_PrinterHW_ID (0);
			setAD_PrinterHW_MediaSize_ID (0);
			setAD_PrinterHW_MediaTray_ID (0);
			setIsManualCalibration (false); // N
        } */
    }

    /** Load Constructor */
    public X_AD_PrinterHW_Calibration (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_Name, get_TrxName());
      return poi;
    }

	/** Set Hardware printer calibration.
		@param AD_PrinterHW_Calibration_ID Hardware printer calibration	  */
	@Override
	public void setAD_PrinterHW_Calibration_ID (int AD_PrinterHW_Calibration_ID)
	{
		if (AD_PrinterHW_Calibration_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_PrinterHW_Calibration_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_PrinterHW_Calibration_ID, Integer.valueOf(AD_PrinterHW_Calibration_ID));
	}

	/** Get Hardware printer calibration.
		@return Hardware printer calibration	  */
	@Override
	public int getAD_PrinterHW_Calibration_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_PrinterHW_Calibration_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.printing.model.I_AD_PrinterHW getAD_PrinterHW() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_PrinterHW_ID, de.metas.printing.model.I_AD_PrinterHW.class);
	}

	@Override
	public void setAD_PrinterHW(de.metas.printing.model.I_AD_PrinterHW AD_PrinterHW)
	{
		set_ValueFromPO(COLUMNNAME_AD_PrinterHW_ID, de.metas.printing.model.I_AD_PrinterHW.class, AD_PrinterHW);
	}

	/** Set Hardware-Drucker.
		@param AD_PrinterHW_ID Hardware-Drucker	  */
	@Override
	public void setAD_PrinterHW_ID (int AD_PrinterHW_ID)
	{
		if (AD_PrinterHW_ID < 1) 
			set_Value (COLUMNNAME_AD_PrinterHW_ID, null);
		else 
			set_Value (COLUMNNAME_AD_PrinterHW_ID, Integer.valueOf(AD_PrinterHW_ID));
	}

	/** Get Hardware-Drucker.
		@return Hardware-Drucker	  */
	@Override
	public int getAD_PrinterHW_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_PrinterHW_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.printing.model.I_AD_PrinterHW_MediaSize getAD_PrinterHW_MediaSize() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_PrinterHW_MediaSize_ID, de.metas.printing.model.I_AD_PrinterHW_MediaSize.class);
	}

	@Override
	public void setAD_PrinterHW_MediaSize(de.metas.printing.model.I_AD_PrinterHW_MediaSize AD_PrinterHW_MediaSize)
	{
		set_ValueFromPO(COLUMNNAME_AD_PrinterHW_MediaSize_ID, de.metas.printing.model.I_AD_PrinterHW_MediaSize.class, AD_PrinterHW_MediaSize);
	}

	/** Set Papiergröße.
		@param AD_PrinterHW_MediaSize_ID Papiergröße	  */
	@Override
	public void setAD_PrinterHW_MediaSize_ID (int AD_PrinterHW_MediaSize_ID)
	{
		if (AD_PrinterHW_MediaSize_ID < 1) 
			set_Value (COLUMNNAME_AD_PrinterHW_MediaSize_ID, null);
		else 
			set_Value (COLUMNNAME_AD_PrinterHW_MediaSize_ID, Integer.valueOf(AD_PrinterHW_MediaSize_ID));
	}

	/** Get Papiergröße.
		@return Papiergröße	  */
	@Override
	public int getAD_PrinterHW_MediaSize_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_PrinterHW_MediaSize_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.printing.model.I_AD_PrinterHW_MediaTray getAD_PrinterHW_MediaTray() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_PrinterHW_MediaTray_ID, de.metas.printing.model.I_AD_PrinterHW_MediaTray.class);
	}

	@Override
	public void setAD_PrinterHW_MediaTray(de.metas.printing.model.I_AD_PrinterHW_MediaTray AD_PrinterHW_MediaTray)
	{
		set_ValueFromPO(COLUMNNAME_AD_PrinterHW_MediaTray_ID, de.metas.printing.model.I_AD_PrinterHW_MediaTray.class, AD_PrinterHW_MediaTray);
	}

	/** Set Hardware-Schacht.
		@param AD_PrinterHW_MediaTray_ID Hardware-Schacht	  */
	@Override
	public void setAD_PrinterHW_MediaTray_ID (int AD_PrinterHW_MediaTray_ID)
	{
		if (AD_PrinterHW_MediaTray_ID < 1) 
			set_Value (COLUMNNAME_AD_PrinterHW_MediaTray_ID, null);
		else 
			set_Value (COLUMNNAME_AD_PrinterHW_MediaTray_ID, Integer.valueOf(AD_PrinterHW_MediaTray_ID));
	}

	/** Get Hardware-Schacht.
		@return Hardware-Schacht	  */
	@Override
	public int getAD_PrinterHW_MediaTray_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_PrinterHW_MediaTray_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Kalibrierung-X.
		@param CalX Kalibrierung-X	  */
	@Override
	public void setCalX (int CalX)
	{
		set_Value (COLUMNNAME_CalX, Integer.valueOf(CalX));
	}

	/** Get Kalibrierung-X.
		@return Kalibrierung-X	  */
	@Override
	public int getCalX () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CalX);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Kalibrierung-Y.
		@param CalY Kalibrierung-Y	  */
	@Override
	public void setCalY (int CalY)
	{
		set_Value (COLUMNNAME_CalY, Integer.valueOf(CalY));
	}

	/** Get Kalibrierung-Y.
		@return Kalibrierung-Y	  */
	@Override
	public int getCalY () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CalY);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Host key.
		@param HostKey 
		Unique identifier of a host
	  */
	@Override
	public void setHostKey (java.lang.String HostKey)
	{
		throw new IllegalArgumentException ("HostKey is virtual column");	}

	/** Get Host key.
		@return Unique identifier of a host
	  */
	@Override
	public java.lang.String getHostKey () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_HostKey);
	}

	/** Set Kalibrierungsdaten selbst eingeben.
		@param IsManualCalibration Kalibrierungsdaten selbst eingeben	  */
	@Override
	public void setIsManualCalibration (boolean IsManualCalibration)
	{
		set_Value (COLUMNNAME_IsManualCalibration, Boolean.valueOf(IsManualCalibration));
	}

	/** Get Kalibrierungsdaten selbst eingeben.
		@return Kalibrierungsdaten selbst eingeben	  */
	@Override
	public boolean isManualCalibration () 
	{
		Object oo = get_Value(COLUMNNAME_IsManualCalibration);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Messung-X.
		@param MeasurementX 
		Mit dem Lineal gemessener X-Wert in mm
	  */
	@Override
	public void setMeasurementX (java.math.BigDecimal MeasurementX)
	{
		set_Value (COLUMNNAME_MeasurementX, MeasurementX);
	}

	/** Get Messung-X.
		@return Mit dem Lineal gemessener X-Wert in mm
	  */
	@Override
	public java.math.BigDecimal getMeasurementX () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MeasurementX);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Messung-Y.
		@param MeasurementY 
		Mit dem Lineal gemessener Y-Wert in mm
	  */
	@Override
	public void setMeasurementY (java.math.BigDecimal MeasurementY)
	{
		set_Value (COLUMNNAME_MeasurementY, MeasurementY);
	}

	/** Get Messung-Y.
		@return Mit dem Lineal gemessener Y-Wert in mm
	  */
	@Override
	public java.math.BigDecimal getMeasurementY () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MeasurementY);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}
}