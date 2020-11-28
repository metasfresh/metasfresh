/** Generated Model - DO NOT CHANGE */
package de.metas.printing.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_Printer_Tray
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_Printer_Tray extends org.compiere.model.PO implements I_AD_Printer_Tray, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -593882204L;

    /** Standard Constructor */
    public X_AD_Printer_Tray (Properties ctx, int AD_Printer_Tray_ID, String trxName)
    {
      super (ctx, AD_Printer_Tray_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_Printer_Tray (Properties ctx, ResultSet rs, String trxName)
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
	public void setAD_Printer_ID (int AD_Printer_ID)
	{
		if (AD_Printer_ID < 1) 
			set_Value (COLUMNNAME_AD_Printer_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Printer_ID, Integer.valueOf(AD_Printer_ID));
	}

	@Override
	public int getAD_Printer_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Printer_ID);
	}

	@Override
	public void setAD_Printer_Tray_ID (int AD_Printer_Tray_ID)
	{
		if (AD_Printer_Tray_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Printer_Tray_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Printer_Tray_ID, Integer.valueOf(AD_Printer_Tray_ID));
	}

	@Override
	public int getAD_Printer_Tray_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Printer_Tray_ID);
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

	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}
}