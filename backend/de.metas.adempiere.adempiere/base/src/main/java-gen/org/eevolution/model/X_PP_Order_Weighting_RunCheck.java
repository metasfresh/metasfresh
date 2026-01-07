// Generated Model - DO NOT CHANGE
package org.eevolution.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for PP_Order_Weighting_RunCheck
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_PP_Order_Weighting_RunCheck extends org.compiere.model.PO implements I_PP_Order_Weighting_RunCheck, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -26059892L;

    /** Standard Constructor */
    public X_PP_Order_Weighting_RunCheck (final Properties ctx, final int PP_Order_Weighting_RunCheck_ID, @Nullable final String trxName)
    {
      super (ctx, PP_Order_Weighting_RunCheck_ID, trxName);
    }

    /** Load Constructor */
    public X_PP_Order_Weighting_RunCheck (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_UOM_ID (final int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, C_UOM_ID);
	}

	@Override
	public int getC_UOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_UOM_ID);
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
	public void setIsToleranceExceeded (final boolean IsToleranceExceeded)
	{
		set_Value (COLUMNNAME_IsToleranceExceeded, IsToleranceExceeded);
	}

	@Override
	public boolean isToleranceExceeded() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsToleranceExceeded);
	}

	@Override
	public void setLine (final int Line)
	{
		set_Value (COLUMNNAME_Line, Line);
	}

	@Override
	public int getLine() 
	{
		return get_ValueAsInt(COLUMNNAME_Line);
	}

	@Override
	public void setPP_Order_Weighting_RunCheck_ID (final int PP_Order_Weighting_RunCheck_ID)
	{
		if (PP_Order_Weighting_RunCheck_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Order_Weighting_RunCheck_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Order_Weighting_RunCheck_ID, PP_Order_Weighting_RunCheck_ID);
	}

	@Override
	public int getPP_Order_Weighting_RunCheck_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Order_Weighting_RunCheck_ID);
	}

	@Override
	public org.eevolution.model.I_PP_Order_Weighting_Run getPP_Order_Weighting_Run()
	{
		return get_ValueAsPO(COLUMNNAME_PP_Order_Weighting_Run_ID, org.eevolution.model.I_PP_Order_Weighting_Run.class);
	}

	@Override
	public void setPP_Order_Weighting_Run(final org.eevolution.model.I_PP_Order_Weighting_Run PP_Order_Weighting_Run)
	{
		set_ValueFromPO(COLUMNNAME_PP_Order_Weighting_Run_ID, org.eevolution.model.I_PP_Order_Weighting_Run.class, PP_Order_Weighting_Run);
	}

	@Override
	public void setPP_Order_Weighting_Run_ID (final int PP_Order_Weighting_Run_ID)
	{
		if (PP_Order_Weighting_Run_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Order_Weighting_Run_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Order_Weighting_Run_ID, PP_Order_Weighting_Run_ID);
	}

	@Override
	public int getPP_Order_Weighting_Run_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Order_Weighting_Run_ID);
	}

	@Override
	public void setWeight (final BigDecimal Weight)
	{
		set_Value (COLUMNNAME_Weight, Weight);
	}

	@Override
	public BigDecimal getWeight() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Weight);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}