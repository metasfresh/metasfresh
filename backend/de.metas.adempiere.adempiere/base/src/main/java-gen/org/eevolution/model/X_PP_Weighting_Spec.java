// Generated Model - DO NOT CHANGE
package org.eevolution.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for PP_Weighting_Spec
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_PP_Weighting_Spec extends org.compiere.model.PO implements I_PP_Weighting_Spec, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -810691136L;

    /** Standard Constructor */
    public X_PP_Weighting_Spec (final Properties ctx, final int PP_Weighting_Spec_ID, @Nullable final String trxName)
    {
      super (ctx, PP_Weighting_Spec_ID, trxName);
    }

    /** Load Constructor */
    public X_PP_Weighting_Spec (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setPP_Weighting_Spec_ID (final int PP_Weighting_Spec_ID)
	{
		if (PP_Weighting_Spec_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Weighting_Spec_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Weighting_Spec_ID, PP_Weighting_Spec_ID);
	}

	@Override
	public int getPP_Weighting_Spec_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Weighting_Spec_ID);
	}

	@Override
	public void setTolerance_Perc (final BigDecimal Tolerance_Perc)
	{
		set_Value (COLUMNNAME_Tolerance_Perc, Tolerance_Perc);
	}

	@Override
	public BigDecimal getTolerance_Perc() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Tolerance_Perc);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setWeightChecksRequired (final int WeightChecksRequired)
	{
		set_Value (COLUMNNAME_WeightChecksRequired, WeightChecksRequired);
	}

	@Override
	public int getWeightChecksRequired() 
	{
		return get_ValueAsInt(COLUMNNAME_WeightChecksRequired);
	}
}