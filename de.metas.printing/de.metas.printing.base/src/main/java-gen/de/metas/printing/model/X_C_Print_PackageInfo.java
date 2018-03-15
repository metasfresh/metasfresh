/** Generated Model - DO NOT CHANGE */
package de.metas.printing.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Print_PackageInfo
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Print_PackageInfo extends org.compiere.model.PO implements I_C_Print_PackageInfo, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -434827618L;

    /** Standard Constructor */
    public X_C_Print_PackageInfo (Properties ctx, int C_Print_PackageInfo_ID, String trxName)
    {
      super (ctx, C_Print_PackageInfo_ID, trxName);
      /** if (C_Print_PackageInfo_ID == 0)
        {
			setAD_PrinterHW_ID (0);
			setC_Print_Package_ID (0);
			setC_Print_PackageInfo_ID (0);
			setPageFrom (0);
			setPageTo (0);
        } */
    }

    /** Load Constructor */
    public X_C_Print_PackageInfo (Properties ctx, ResultSet rs, String trxName)
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

	@Override
	public de.metas.printing.model.I_C_Print_Package getC_Print_Package() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Print_Package_ID, de.metas.printing.model.I_C_Print_Package.class);
	}

	@Override
	public void setC_Print_Package(de.metas.printing.model.I_C_Print_Package C_Print_Package)
	{
		set_ValueFromPO(COLUMNNAME_C_Print_Package_ID, de.metas.printing.model.I_C_Print_Package.class, C_Print_Package);
	}

	/** Set Druckpaket.
		@param C_Print_Package_ID Druckpaket	  */
	@Override
	public void setC_Print_Package_ID (int C_Print_Package_ID)
	{
		if (C_Print_Package_ID < 1) 
			set_Value (COLUMNNAME_C_Print_Package_ID, null);
		else 
			set_Value (COLUMNNAME_C_Print_Package_ID, Integer.valueOf(C_Print_Package_ID));
	}

	/** Get Druckpaket.
		@return Druckpaket	  */
	@Override
	public int getC_Print_Package_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Print_Package_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Druckpaket-Info.
		@param C_Print_PackageInfo_ID 
		Contains details for the print package, like printer, tray, pages from/to and print service name.
	  */
	@Override
	public void setC_Print_PackageInfo_ID (int C_Print_PackageInfo_ID)
	{
		if (C_Print_PackageInfo_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Print_PackageInfo_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Print_PackageInfo_ID, Integer.valueOf(C_Print_PackageInfo_ID));
	}

	/** Get Druckpaket-Info.
		@return Contains details for the print package, like printer, tray, pages from/to and print service name.
	  */
	@Override
	public int getC_Print_PackageInfo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Print_PackageInfo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Von Seite.
		@param PageFrom Von Seite	  */
	@Override
	public void setPageFrom (int PageFrom)
	{
		set_Value (COLUMNNAME_PageFrom, Integer.valueOf(PageFrom));
	}

	/** Get Von Seite.
		@return Von Seite	  */
	@Override
	public int getPageFrom () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PageFrom);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Bis Seite.
		@param PageTo Bis Seite	  */
	@Override
	public void setPageTo (int PageTo)
	{
		set_Value (COLUMNNAME_PageTo, Integer.valueOf(PageTo));
	}

	/** Get Bis Seite.
		@return Bis Seite	  */
	@Override
	public int getPageTo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PageTo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Print service name.
		@param PrintServiceName Print service name	  */
	@Override
	public void setPrintServiceName (java.lang.String PrintServiceName)
	{
		throw new IllegalArgumentException ("PrintServiceName is virtual column");	}

	/** Get Print service name.
		@return Print service name	  */
	@Override
	public java.lang.String getPrintServiceName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PrintServiceName);
	}

	/** Set PrintServiceTray.
		@param PrintServiceTray 
		Alphanumeric identifier of the entity
	  */
	@Override
	public void setPrintServiceTray (java.lang.String PrintServiceTray)
	{
		throw new IllegalArgumentException ("PrintServiceTray is virtual column");	}

	/** Get PrintServiceTray.
		@return Alphanumeric identifier of the entity
	  */
	@Override
	public java.lang.String getPrintServiceTray () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PrintServiceTray);
	}

	/** Set Schachtnummer.
		@param TrayNumber Schachtnummer	  */
	@Override
	public void setTrayNumber (int TrayNumber)
	{
		throw new IllegalArgumentException ("TrayNumber is virtual column");	}

	/** Get Schachtnummer.
		@return Schachtnummer	  */
	@Override
	public int getTrayNumber () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_TrayNumber);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}