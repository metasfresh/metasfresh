// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_DiscountSchema_Calculated_Surcharge_Price
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_DiscountSchema_Calculated_Surcharge_Price extends org.compiere.model.PO implements I_M_DiscountSchema_Calculated_Surcharge_Price, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -412826932L;

    /** Standard Constructor */
    public X_M_DiscountSchema_Calculated_Surcharge_Price (final Properties ctx, final int M_DiscountSchema_Calculated_Surcharge_Price_ID, @Nullable final String trxName)
    {
      super (ctx, M_DiscountSchema_Calculated_Surcharge_Price_ID, trxName);
    }

    /** Load Constructor */
    public X_M_DiscountSchema_Calculated_Surcharge_Price (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_C_Region getC_Region()
	{
		return get_ValueAsPO(COLUMNNAME_C_Region_ID, org.compiere.model.I_C_Region.class);
	}

	@Override
	public void setC_Region(final org.compiere.model.I_C_Region C_Region)
	{
		set_ValueFromPO(COLUMNNAME_C_Region_ID, org.compiere.model.I_C_Region.class, C_Region);
	}

	@Override
	public void setC_Region_ID (final int C_Region_ID)
	{
		if (C_Region_ID < 1) 
			set_Value (COLUMNNAME_C_Region_ID, null);
		else 
			set_Value (COLUMNNAME_C_Region_ID, C_Region_ID);
	}

	@Override
	public int getC_Region_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Region_ID);
	}

	@Override
	public void setDescription (final @Nullable java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	@Override
	public void setFreight_Cost_Calc_Price (final BigDecimal Freight_Cost_Calc_Price)
	{
		set_Value (COLUMNNAME_Freight_Cost_Calc_Price, Freight_Cost_Calc_Price);
	}

	@Override
	public BigDecimal getFreight_Cost_Calc_Price() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Freight_Cost_Calc_Price);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setM_DiscountSchema_Calculated_Surcharge_Price_ID (final int M_DiscountSchema_Calculated_Surcharge_Price_ID)
	{
		if (M_DiscountSchema_Calculated_Surcharge_Price_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_DiscountSchema_Calculated_Surcharge_Price_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_DiscountSchema_Calculated_Surcharge_Price_ID, M_DiscountSchema_Calculated_Surcharge_Price_ID);
	}

	@Override
	public int getM_DiscountSchema_Calculated_Surcharge_Price_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_DiscountSchema_Calculated_Surcharge_Price_ID);
	}

	@Override
	public void setName (final java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}
}