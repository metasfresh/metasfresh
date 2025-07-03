// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_Product_Category_MaxNetAmount
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_Product_Category_MaxNetAmount extends org.compiere.model.PO implements I_M_Product_Category_MaxNetAmount, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1209190109L;

    /** Standard Constructor */
    public X_M_Product_Category_MaxNetAmount (final Properties ctx, final int M_Product_Category_MaxNetAmount_ID, @Nullable final String trxName)
    {
      super (ctx, M_Product_Category_MaxNetAmount_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Product_Category_MaxNetAmount (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_Currency_ID (final int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, C_Currency_ID);
	}

	@Override
	public int getC_Currency_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Currency_ID);
	}

	@Override
	public void setMaxNetAmount (final BigDecimal MaxNetAmount)
	{
		set_Value (COLUMNNAME_MaxNetAmount, MaxNetAmount);
	}

	@Override
	public BigDecimal getMaxNetAmount() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_MaxNetAmount);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setM_Product_Category_ID (final int M_Product_Category_ID)
	{
		if (M_Product_Category_ID < 1) 
			set_Value (COLUMNNAME_M_Product_Category_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_Category_ID, M_Product_Category_ID);
	}

	@Override
	public int getM_Product_Category_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_Category_ID);
	}

	@Override
	public void setM_Product_Category_MaxNetAmount_ID (final int M_Product_Category_MaxNetAmount_ID)
	{
		if (M_Product_Category_MaxNetAmount_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_Category_MaxNetAmount_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_Category_MaxNetAmount_ID, M_Product_Category_MaxNetAmount_ID);
	}

	@Override
	public int getM_Product_Category_MaxNetAmount_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_Category_MaxNetAmount_ID);
	}
}