/** Generated Model - DO NOT CHANGE */
package de.metas.printing.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_Printer_Config
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_Printer_Config extends org.compiere.model.PO implements I_AD_Printer_Config, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -643463172L;

    /** Standard Constructor */
    public X_AD_Printer_Config (Properties ctx, int AD_Printer_Config_ID, String trxName)
    {
      super (ctx, AD_Printer_Config_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_Printer_Config (Properties ctx, ResultSet rs, String trxName)
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
	public de.metas.printing.model.I_AD_Printer_Config getAD_Printer_Config_Shared()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Printer_Config_Shared_ID, de.metas.printing.model.I_AD_Printer_Config.class);
	}

	@Override
	public void setAD_Printer_Config_Shared(de.metas.printing.model.I_AD_Printer_Config AD_Printer_Config_Shared)
	{
		set_ValueFromPO(COLUMNNAME_AD_Printer_Config_Shared_ID, de.metas.printing.model.I_AD_Printer_Config.class, AD_Printer_Config_Shared);
	}

	@Override
	public void setAD_Printer_Config_Shared_ID (int AD_Printer_Config_Shared_ID)
	{
		if (AD_Printer_Config_Shared_ID < 1) 
			set_Value (COLUMNNAME_AD_Printer_Config_Shared_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Printer_Config_Shared_ID, Integer.valueOf(AD_Printer_Config_Shared_ID));
	}

	@Override
	public int getAD_Printer_Config_Shared_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Printer_Config_Shared_ID);
	}

	@Override
	public void setAD_User_PrinterMatchingConfig_ID (int AD_User_PrinterMatchingConfig_ID)
	{
		if (AD_User_PrinterMatchingConfig_ID < 1) 
			set_Value (COLUMNNAME_AD_User_PrinterMatchingConfig_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_PrinterMatchingConfig_ID, Integer.valueOf(AD_User_PrinterMatchingConfig_ID));
	}

	@Override
	public int getAD_User_PrinterMatchingConfig_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_User_PrinterMatchingConfig_ID);
	}

	@Override
	public void setConfigHostKey (java.lang.String ConfigHostKey)
	{
		set_Value (COLUMNNAME_ConfigHostKey, ConfigHostKey);
	}

	@Override
	public java.lang.String getConfigHostKey() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ConfigHostKey);
	}

	@Override
	public void setIsSharedPrinterConfig (boolean IsSharedPrinterConfig)
	{
		set_Value (COLUMNNAME_IsSharedPrinterConfig, Boolean.valueOf(IsSharedPrinterConfig));
	}

	@Override
	public boolean isSharedPrinterConfig() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSharedPrinterConfig);
	}

	@Override
	public org.compiere.model.I_C_Workplace getC_Workplace()
	{
		return get_ValueAsPO(COLUMNNAME_C_Workplace_ID, org.compiere.model.I_C_Workplace.class);
	}

	@Override
	public void setC_Workplace(final org.compiere.model.I_C_Workplace C_Workplace)
	{
		set_ValueFromPO(COLUMNNAME_C_Workplace_ID, org.compiere.model.I_C_Workplace.class, C_Workplace);
	}

	@Override
	public void setC_Workplace_ID (final int C_Workplace_ID)
	{
		if (C_Workplace_ID < 1)
			set_Value (COLUMNNAME_C_Workplace_ID, null);
		else
			set_Value (COLUMNNAME_C_Workplace_ID, C_Workplace_ID);
	}

	@Override
	public int getC_Workplace_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_Workplace_ID);
	}

}