/** Generated Model - DO NOT CHANGE */
package de.metas.printing.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_Printer_Matching
 *  @author Adempiere (generated)
 */
@SuppressWarnings("javadoc")
public class X_AD_Printer_Matching extends org.compiere.model.PO implements I_AD_Printer_Matching, org.compiere.model.I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = -568370510L;

    /** Standard Constructor */
    public X_AD_Printer_Matching (Properties ctx, int AD_Printer_Matching_ID, String trxName)
    {
      super (ctx, AD_Printer_Matching_ID, trxName);
      /** if (AD_Printer_Matching_ID == 0)
        {
			setAD_Printer_Config_ID (0);
			setAD_PrinterHW_ID (0);
			setAD_Printer_ID (0);
			setAD_Printer_Matching_ID (0);
        } */
    }

    /** Load Constructor */
    public X_AD_Printer_Matching (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      final org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_Name, get_TrxName());
      return poi;
    }

	@Override
	public de.metas.printing.model.I_AD_Printer_Config getAD_Printer_Config() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Printer_Config_ID, de.metas.printing.model.I_AD_Printer_Config.class);
	}

	@Override
	public void setAD_Printer_Config(de.metas.printing.model.I_AD_Printer_Config AD_Printer_Config)
	{
		set_ValueFromPO(COLUMNNAME_AD_Printer_Config_ID, de.metas.printing.model.I_AD_Printer_Config.class, AD_Printer_Config);
	}

	/** Set Printer Matching Config.
		@param AD_Printer_Config_ID Printer Matching Config	  */
	@Override
	public void setAD_Printer_Config_ID (int AD_Printer_Config_ID)
	{
		if (AD_Printer_Config_ID < 1)
			set_ValueNoCheck (COLUMNNAME_AD_Printer_Config_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_AD_Printer_Config_ID, Integer.valueOf(AD_Printer_Config_ID));
	}

	/** Get Printer Matching Config.
		@return Printer Matching Config	  */
	@Override
	public int getAD_Printer_Config_ID ()
	{
		final Integer ii = (Integer)get_Value(COLUMNNAME_AD_Printer_Config_ID);
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
		final Integer ii = (Integer)get_Value(COLUMNNAME_AD_PrinterHW_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Logischer Drucker.
		@param AD_Printer_ID Logischer Drucker	  */
	@Override
	public void setAD_Printer_ID (int AD_Printer_ID)
	{
		if (AD_Printer_ID < 1)
			set_ValueNoCheck (COLUMNNAME_AD_Printer_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_AD_Printer_ID, Integer.valueOf(AD_Printer_ID));
	}

	/** Get Logischer Drucker.
		@return Logischer Drucker	  */
	@Override
	public int getAD_Printer_ID ()
	{
		final Integer ii = (Integer)get_Value(COLUMNNAME_AD_Printer_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Printer matching.
		@param AD_Printer_Matching_ID Printer matching	  */
	@Override
	public void setAD_Printer_Matching_ID (int AD_Printer_Matching_ID)
	{
		if (AD_Printer_Matching_ID < 1)
			set_ValueNoCheck (COLUMNNAME_AD_Printer_Matching_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_AD_Printer_Matching_ID, Integer.valueOf(AD_Printer_Matching_ID));
	}

	/** Get Printer matching.
		@return Printer matching	  */
	@Override
	public int getAD_Printer_Matching_ID ()
	{
		final Integer ii = (Integer)get_Value(COLUMNNAME_AD_Printer_Matching_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set AD_Tray_Matching_IncludedTab.
		@param AD_Tray_Matching_IncludedTab AD_Tray_Matching_IncludedTab	  */
	@Override
	public void setAD_Tray_Matching_IncludedTab (java.lang.String AD_Tray_Matching_IncludedTab)
	{
		set_Value (COLUMNNAME_AD_Tray_Matching_IncludedTab, AD_Tray_Matching_IncludedTab);
	}

	/** Get AD_Tray_Matching_IncludedTab.
		@return AD_Tray_Matching_IncludedTab	  */
	@Override
	public java.lang.String getAD_Tray_Matching_IncludedTab ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_AD_Tray_Matching_IncludedTab);
	}

	/** Set Beschreibung.
		@param Description Beschreibung	  */
	@Override
	public void setDescription (java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Beschreibung.
		@return Beschreibung	  */
	@Override
	public java.lang.String getDescription ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}
}