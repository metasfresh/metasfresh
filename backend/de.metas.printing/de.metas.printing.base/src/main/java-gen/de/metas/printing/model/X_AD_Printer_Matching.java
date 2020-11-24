/** Generated Model - DO NOT CHANGE */
package de.metas.printing.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_Printer_Matching
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_Printer_Matching extends org.compiere.model.PO implements I_AD_Printer_Matching, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 47126885L;

    /** Standard Constructor */
    public X_AD_Printer_Matching (Properties ctx, int AD_Printer_Matching_ID, String trxName)
    {
      super (ctx, AD_Printer_Matching_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_Printer_Matching (Properties ctx, ResultSet rs, String trxName)
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
	public de.metas.printing.model.I_AD_Printer_Config getAD_Printer_Config()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Printer_Config_ID, de.metas.printing.model.I_AD_Printer_Config.class);
	}

	@Override
	public void setAD_Printer_Config(de.metas.printing.model.I_AD_Printer_Config AD_Printer_Config)
	{
		set_ValueFromPO(COLUMNNAME_AD_Printer_Config_ID, de.metas.printing.model.I_AD_Printer_Config.class, AD_Printer_Config);
	}

	@Override
	public void setAD_Printer_Config_ID (int AD_Printer_Config_ID)
	{
		if (AD_Printer_Config_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Printer_Config_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Printer_Config_ID, Integer.valueOf(AD_Printer_Config_ID));
	}

	@Override
	public int getAD_Printer_Config_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Printer_Config_ID);
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
	public void setAD_Printer_ID (int AD_Printer_ID)
	{
		if (AD_Printer_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Printer_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Printer_ID, Integer.valueOf(AD_Printer_ID));
	}

	@Override
	public int getAD_Printer_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Printer_ID);
	}

	@Override
	public void setAD_Printer_Matching_ID (int AD_Printer_Matching_ID)
	{
		if (AD_Printer_Matching_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Printer_Matching_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Printer_Matching_ID, Integer.valueOf(AD_Printer_Matching_ID));
	}

	@Override
	public int getAD_Printer_Matching_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Printer_Matching_ID);
	}

	@Override
	public void setAD_Tray_Matching_IncludedTab (java.lang.String AD_Tray_Matching_IncludedTab)
	{
		set_Value (COLUMNNAME_AD_Tray_Matching_IncludedTab, AD_Tray_Matching_IncludedTab);
	}

	@Override
	public java.lang.String getAD_Tray_Matching_IncludedTab() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AD_Tray_Matching_IncludedTab);
	}

	@Override
	public void setDescription (java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}
}