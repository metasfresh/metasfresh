// Generated Model - DO NOT CHANGE
package org.eevolution.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for PP_Order_Weighting_Run
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_PP_Order_Weighting_Run extends org.compiere.model.PO implements I_PP_Order_Weighting_Run, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1369996105L;

    /** Standard Constructor */
    public X_PP_Order_Weighting_Run (final Properties ctx, final int PP_Order_Weighting_Run_ID, @Nullable final String trxName)
    {
      super (ctx, PP_Order_Weighting_Run_ID, trxName);
    }

    /** Load Constructor */
    public X_PP_Order_Weighting_Run (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setDateDoc (final java.sql.Timestamp DateDoc)
	{
		set_Value (COLUMNNAME_DateDoc, DateDoc);
	}

	@Override
	public java.sql.Timestamp getDateDoc() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateDoc);
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
	public void setMaxWeight (final BigDecimal MaxWeight)
	{
		set_Value (COLUMNNAME_MaxWeight, MaxWeight);
	}

	@Override
	public BigDecimal getMaxWeight() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_MaxWeight);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setMinWeight (final BigDecimal MinWeight)
	{
		set_Value (COLUMNNAME_MinWeight, MinWeight);
	}

	@Override
	public BigDecimal getMinWeight() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_MinWeight);
		return bd != null ? bd : BigDecimal.ZERO;
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
	public org.eevolution.model.I_PP_Order_BOMLine getPP_Order_BOMLine()
	{
		return get_ValueAsPO(COLUMNNAME_PP_Order_BOMLine_ID, org.eevolution.model.I_PP_Order_BOMLine.class);
	}

	@Override
	public void setPP_Order_BOMLine(final org.eevolution.model.I_PP_Order_BOMLine PP_Order_BOMLine)
	{
		set_ValueFromPO(COLUMNNAME_PP_Order_BOMLine_ID, org.eevolution.model.I_PP_Order_BOMLine.class, PP_Order_BOMLine);
	}

	@Override
	public void setPP_Order_BOMLine_ID (final int PP_Order_BOMLine_ID)
	{
		if (PP_Order_BOMLine_ID < 1) 
			set_Value (COLUMNNAME_PP_Order_BOMLine_ID, null);
		else 
			set_Value (COLUMNNAME_PP_Order_BOMLine_ID, PP_Order_BOMLine_ID);
	}

	@Override
	public int getPP_Order_BOMLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Order_BOMLine_ID);
	}

	@Override
	public org.eevolution.model.I_PP_Order getPP_Order()
	{
		return get_ValueAsPO(COLUMNNAME_PP_Order_ID, org.eevolution.model.I_PP_Order.class);
	}

	@Override
	public void setPP_Order(final org.eevolution.model.I_PP_Order PP_Order)
	{
		set_ValueFromPO(COLUMNNAME_PP_Order_ID, org.eevolution.model.I_PP_Order.class, PP_Order);
	}

	@Override
	public void setPP_Order_ID (final int PP_Order_ID)
	{
		if (PP_Order_ID < 1) 
			set_Value (COLUMNNAME_PP_Order_ID, null);
		else 
			set_Value (COLUMNNAME_PP_Order_ID, PP_Order_ID);
	}

	@Override
	public int getPP_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Order_ID);
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
	public org.eevolution.model.I_PP_Weighting_Spec getPP_Weighting_Spec()
	{
		return get_ValueAsPO(COLUMNNAME_PP_Weighting_Spec_ID, org.eevolution.model.I_PP_Weighting_Spec.class);
	}

	@Override
	public void setPP_Weighting_Spec(final org.eevolution.model.I_PP_Weighting_Spec PP_Weighting_Spec)
	{
		set_ValueFromPO(COLUMNNAME_PP_Weighting_Spec_ID, org.eevolution.model.I_PP_Weighting_Spec.class, PP_Weighting_Spec);
	}

	@Override
	public void setPP_Weighting_Spec_ID (final int PP_Weighting_Spec_ID)
	{
		if (PP_Weighting_Spec_ID < 1) 
			set_Value (COLUMNNAME_PP_Weighting_Spec_ID, null);
		else 
			set_Value (COLUMNNAME_PP_Weighting_Spec_ID, PP_Weighting_Spec_ID);
	}

	@Override
	public int getPP_Weighting_Spec_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Weighting_Spec_ID);
	}

	@Override
	public void setProcessed (final boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Processed);
	}

	@Override
	public boolean isProcessed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processed);
	}

	@Override
	public void setTargetWeight (final BigDecimal TargetWeight)
	{
		set_Value (COLUMNNAME_TargetWeight, TargetWeight);
	}

	@Override
	public BigDecimal getTargetWeight() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_TargetWeight);
		return bd != null ? bd : BigDecimal.ZERO;
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