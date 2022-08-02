// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_Interim_Invoice_Settings
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Interim_Invoice_Settings extends org.compiere.model.PO implements I_C_Interim_Invoice_Settings, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -468973685L;

    /** Standard Constructor */
    public X_C_Interim_Invoice_Settings (final Properties ctx, final int C_Interim_Invoice_Settings_ID, @Nullable final String trxName)
    {
      super (ctx, C_Interim_Invoice_Settings_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Interim_Invoice_Settings (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public org.compiere.model.I_C_Calendar getC_Harvesting_Calendar()
	{
		return get_ValueAsPO(COLUMNNAME_C_Harvesting_Calendar_ID, org.compiere.model.I_C_Calendar.class);
	}

	@Override
	public void setC_Harvesting_Calendar(final org.compiere.model.I_C_Calendar C_Harvesting_Calendar)
	{
		set_ValueFromPO(COLUMNNAME_C_Harvesting_Calendar_ID, org.compiere.model.I_C_Calendar.class, C_Harvesting_Calendar);
	}

	@Override
	public void setC_Harvesting_Calendar_ID (final int C_Harvesting_Calendar_ID)
	{
		if (C_Harvesting_Calendar_ID < 1) 
			set_Value (COLUMNNAME_C_Harvesting_Calendar_ID, null);
		else 
			set_Value (COLUMNNAME_C_Harvesting_Calendar_ID, C_Harvesting_Calendar_ID);
	}

	@Override
	public int getC_Harvesting_Calendar_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Harvesting_Calendar_ID);
	}

	@Override
	public void setC_Interim_Invoice_Settings_ID (final int C_Interim_Invoice_Settings_ID)
	{
		if (C_Interim_Invoice_Settings_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Interim_Invoice_Settings_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Interim_Invoice_Settings_ID, C_Interim_Invoice_Settings_ID);
	}

	@Override
	public int getC_Interim_Invoice_Settings_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Interim_Invoice_Settings_ID);
	}

	@Override
	public void setM_Product_Witholding_ID (final int M_Product_Witholding_ID)
	{
		if (M_Product_Witholding_ID < 1) 
			set_Value (COLUMNNAME_M_Product_Witholding_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_Witholding_ID, M_Product_Witholding_ID);
	}

	@Override
	public int getM_Product_Witholding_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_Witholding_ID);
	}
}