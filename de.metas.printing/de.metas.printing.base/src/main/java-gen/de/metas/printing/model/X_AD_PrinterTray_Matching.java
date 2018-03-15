/** Generated Model - DO NOT CHANGE */
package de.metas.printing.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_PrinterTray_Matching
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_PrinterTray_Matching extends org.compiere.model.PO implements I_AD_PrinterTray_Matching, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -378696838L;

    /** Standard Constructor */
    public X_AD_PrinterTray_Matching (Properties ctx, int AD_PrinterTray_Matching_ID, String trxName)
    {
      super (ctx, AD_PrinterTray_Matching_ID, trxName);
      /** if (AD_PrinterTray_Matching_ID == 0)
        {
			setAD_PrinterHW_MediaTray_ID (0);
			setAD_Printer_Tray_ID (0);
			setAD_PrinterTray_Matching_ID (0);
        } */
    }

    /** Load Constructor */
    public X_AD_PrinterTray_Matching (Properties ctx, ResultSet rs, String trxName)
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

	@Override
	public de.metas.printing.model.I_AD_Printer_Matching getAD_Printer_Matching() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Printer_Matching_ID, de.metas.printing.model.I_AD_Printer_Matching.class);
	}

	@Override
	public void setAD_Printer_Matching(de.metas.printing.model.I_AD_Printer_Matching AD_Printer_Matching)
	{
		set_ValueFromPO(COLUMNNAME_AD_Printer_Matching_ID, de.metas.printing.model.I_AD_Printer_Matching.class, AD_Printer_Matching);
	}

	/** Set Printer matching.
		@param AD_Printer_Matching_ID Printer matching	  */
	@Override
	public void setAD_Printer_Matching_ID (int AD_Printer_Matching_ID)
	{
		if (AD_Printer_Matching_ID < 1) 
			set_Value (COLUMNNAME_AD_Printer_Matching_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Printer_Matching_ID, Integer.valueOf(AD_Printer_Matching_ID));
	}

	/** Get Printer matching.
		@return Printer matching	  */
	@Override
	public int getAD_Printer_Matching_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Printer_Matching_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.printing.model.I_AD_Printer_Tray getAD_Printer_Tray() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Printer_Tray_ID, de.metas.printing.model.I_AD_Printer_Tray.class);
	}

	@Override
	public void setAD_Printer_Tray(de.metas.printing.model.I_AD_Printer_Tray AD_Printer_Tray)
	{
		set_ValueFromPO(COLUMNNAME_AD_Printer_Tray_ID, de.metas.printing.model.I_AD_Printer_Tray.class, AD_Printer_Tray);
	}

	/** Set Logischer Schacht.
		@param AD_Printer_Tray_ID Logischer Schacht	  */
	@Override
	public void setAD_Printer_Tray_ID (int AD_Printer_Tray_ID)
	{
		if (AD_Printer_Tray_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Printer_Tray_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Printer_Tray_ID, Integer.valueOf(AD_Printer_Tray_ID));
	}

	/** Get Logischer Schacht.
		@return Logischer Schacht	  */
	@Override
	public int getAD_Printer_Tray_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Printer_Tray_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Printer tray matching.
		@param AD_PrinterTray_Matching_ID Printer tray matching	  */
	@Override
	public void setAD_PrinterTray_Matching_ID (int AD_PrinterTray_Matching_ID)
	{
		if (AD_PrinterTray_Matching_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_PrinterTray_Matching_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_PrinterTray_Matching_ID, Integer.valueOf(AD_PrinterTray_Matching_ID));
	}

	/** Get Printer tray matching.
		@return Printer tray matching	  */
	@Override
	public int getAD_PrinterTray_Matching_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_PrinterTray_Matching_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}