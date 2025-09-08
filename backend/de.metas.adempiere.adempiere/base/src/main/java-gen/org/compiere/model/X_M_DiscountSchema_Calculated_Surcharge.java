// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_DiscountSchema_Calculated_Surcharge
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_DiscountSchema_Calculated_Surcharge extends org.compiere.model.PO implements I_M_DiscountSchema_Calculated_Surcharge, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1307637385L;

    /** Standard Constructor */
    public X_M_DiscountSchema_Calculated_Surcharge (final Properties ctx, final int M_DiscountSchema_Calculated_Surcharge_ID, @Nullable final String trxName)
    {
      super (ctx, M_DiscountSchema_Calculated_Surcharge_ID, trxName);
    }

    /** Load Constructor */
    public X_M_DiscountSchema_Calculated_Surcharge (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setM_DiscountSchema_Calculated_Surcharge_ID (final int M_DiscountSchema_Calculated_Surcharge_ID)
	{
		if (M_DiscountSchema_Calculated_Surcharge_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_DiscountSchema_Calculated_Surcharge_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_DiscountSchema_Calculated_Surcharge_ID, M_DiscountSchema_Calculated_Surcharge_ID);
	}

	@Override
	public int getM_DiscountSchema_Calculated_Surcharge_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_DiscountSchema_Calculated_Surcharge_ID);
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

	@Override
	public void setSurcharge_Calc_SQL (final java.lang.String Surcharge_Calc_SQL)
	{
		set_Value (COLUMNNAME_Surcharge_Calc_SQL, Surcharge_Calc_SQL);
	}

	@Override
	public java.lang.String getSurcharge_Calc_SQL() 
	{
		return get_ValueAsString(COLUMNNAME_Surcharge_Calc_SQL);
	}
}