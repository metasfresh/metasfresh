// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_Product_PrintFormat
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_Product_PrintFormat extends org.compiere.model.PO implements I_M_Product_PrintFormat, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1870500207L;

    /** Standard Constructor */
    public X_M_Product_PrintFormat (final Properties ctx, final int M_Product_PrintFormat_ID, @Nullable final String trxName)
    {
      super (ctx, M_Product_PrintFormat_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Product_PrintFormat (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_AD_PrintFormat getAD_PrintFormat()
	{
		return get_ValueAsPO(COLUMNNAME_AD_PrintFormat_ID, org.compiere.model.I_AD_PrintFormat.class);
	}

	@Override
	public void setAD_PrintFormat(final org.compiere.model.I_AD_PrintFormat AD_PrintFormat)
	{
		set_ValueFromPO(COLUMNNAME_AD_PrintFormat_ID, org.compiere.model.I_AD_PrintFormat.class, AD_PrintFormat);
	}

	@Override
	public void setAD_PrintFormat_ID (final int AD_PrintFormat_ID)
	{
		if (AD_PrintFormat_ID < 1) 
			set_Value (COLUMNNAME_AD_PrintFormat_ID, null);
		else 
			set_Value (COLUMNNAME_AD_PrintFormat_ID, AD_PrintFormat_ID);
	}

	@Override
	public int getAD_PrintFormat_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_PrintFormat_ID);
	}

	@Override
	public void setM_Product_ID (final int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, M_Product_ID);
	}

	@Override
	public int getM_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_ID);
	}

	@Override
	public void setM_Product_PrintFormat_ID (final int M_Product_PrintFormat_ID)
	{
		if (M_Product_PrintFormat_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_PrintFormat_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_PrintFormat_ID, M_Product_PrintFormat_ID);
	}

	@Override
	public int getM_Product_PrintFormat_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_PrintFormat_ID);
	}
}