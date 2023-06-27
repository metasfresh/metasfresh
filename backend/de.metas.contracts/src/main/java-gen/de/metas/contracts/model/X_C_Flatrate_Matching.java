// Generated Model - DO NOT CHANGE
package de.metas.contracts.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Flatrate_Matching
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Flatrate_Matching extends org.compiere.model.PO implements I_C_Flatrate_Matching, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -867299975L;

    /** Standard Constructor */
    public X_C_Flatrate_Matching (final Properties ctx, final int C_Flatrate_Matching_ID, @Nullable final String trxName)
    {
      super (ctx, C_Flatrate_Matching_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Flatrate_Matching (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_Charge_ID (final int C_Charge_ID)
	{
		if (C_Charge_ID < 1) 
			set_Value (COLUMNNAME_C_Charge_ID, null);
		else 
			set_Value (COLUMNNAME_C_Charge_ID, C_Charge_ID);
	}

	@Override
	public int getC_Charge_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Charge_ID);
	}

	@Override
	public de.metas.contracts.model.I_C_Flatrate_Conditions getC_Flatrate_Conditions()
	{
		return get_ValueAsPO(COLUMNNAME_C_Flatrate_Conditions_ID, de.metas.contracts.model.I_C_Flatrate_Conditions.class);
	}

	@Override
	public void setC_Flatrate_Conditions(final de.metas.contracts.model.I_C_Flatrate_Conditions C_Flatrate_Conditions)
	{
		set_ValueFromPO(COLUMNNAME_C_Flatrate_Conditions_ID, de.metas.contracts.model.I_C_Flatrate_Conditions.class, C_Flatrate_Conditions);
	}

	@Override
	public void setC_Flatrate_Conditions_ID (final int C_Flatrate_Conditions_ID)
	{
		if (C_Flatrate_Conditions_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_Conditions_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_Conditions_ID, C_Flatrate_Conditions_ID);
	}

	@Override
	public int getC_Flatrate_Conditions_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Flatrate_Conditions_ID);
	}

	@Override
	public void setC_Flatrate_Matching_ID (final int C_Flatrate_Matching_ID)
	{
		if (C_Flatrate_Matching_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_Matching_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_Matching_ID, C_Flatrate_Matching_ID);
	}

	@Override
	public int getC_Flatrate_Matching_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Flatrate_Matching_ID);
	}

	@Override
	public de.metas.contracts.model.I_C_Flatrate_Transition getC_Flatrate_Transition()
	{
		return get_ValueAsPO(COLUMNNAME_C_Flatrate_Transition_ID, de.metas.contracts.model.I_C_Flatrate_Transition.class);
	}

	@Override
	public void setC_Flatrate_Transition(final de.metas.contracts.model.I_C_Flatrate_Transition C_Flatrate_Transition)
	{
		set_ValueFromPO(COLUMNNAME_C_Flatrate_Transition_ID, de.metas.contracts.model.I_C_Flatrate_Transition.class, C_Flatrate_Transition);
	}

	@Override
	public void setC_Flatrate_Transition_ID (final int C_Flatrate_Transition_ID)
	{
		if (C_Flatrate_Transition_ID < 1) 
			set_Value (COLUMNNAME_C_Flatrate_Transition_ID, null);
		else 
			set_Value (COLUMNNAME_C_Flatrate_Transition_ID, C_Flatrate_Transition_ID);
	}

	@Override
	public int getC_Flatrate_Transition_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Flatrate_Transition_ID);
	}

	@Override
	public void setM_PricingSystem_ID (final int M_PricingSystem_ID)
	{
		if (M_PricingSystem_ID < 1) 
			set_Value (COLUMNNAME_M_PricingSystem_ID, null);
		else 
			set_Value (COLUMNNAME_M_PricingSystem_ID, M_PricingSystem_ID);
	}

	@Override
	public int getM_PricingSystem_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_PricingSystem_ID);
	}

	@Override
	public void setM_Product_Category_Matching_ID (final int M_Product_Category_Matching_ID)
	{
		if (M_Product_Category_Matching_ID < 1) 
			set_Value (COLUMNNAME_M_Product_Category_Matching_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_Category_Matching_ID, M_Product_Category_Matching_ID);
	}

	@Override
	public int getM_Product_Category_Matching_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_Category_Matching_ID);
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
	public void setQtyPerDelivery (final BigDecimal QtyPerDelivery)
	{
		set_Value (COLUMNNAME_QtyPerDelivery, QtyPerDelivery);
	}

	@Override
	public BigDecimal getQtyPerDelivery() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyPerDelivery);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setSeqNo (final int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, SeqNo);
	}

	@Override
	public int getSeqNo() 
	{
		return get_ValueAsInt(COLUMNNAME_SeqNo);
	}
}