// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_CostRevaluationLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_CostRevaluationLine extends org.compiere.model.PO implements I_M_CostRevaluationLine, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1869329450L;

    /** Standard Constructor */
    public X_M_CostRevaluationLine (final Properties ctx, final int M_CostRevaluationLine_ID, @Nullable final String trxName)
    {
      super (ctx, M_CostRevaluationLine_ID, trxName);
    }

    /** Load Constructor */
    public X_M_CostRevaluationLine (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setCurrentCostPrice (final @Nullable BigDecimal CurrentCostPrice)
	{
		set_Value (COLUMNNAME_CurrentCostPrice, CurrentCostPrice);
	}

	@Override
	public BigDecimal getCurrentCostPrice() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_CurrentCostPrice);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setCurrentQty (final @Nullable BigDecimal CurrentQty)
	{
		set_Value (COLUMNNAME_CurrentQty, CurrentQty);
	}

	@Override
	public BigDecimal getCurrentQty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_CurrentQty);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public org.compiere.model.I_M_CostRevaluation getM_CostRevaluation()
	{
		return get_ValueAsPO(COLUMNNAME_M_CostRevaluation_ID, org.compiere.model.I_M_CostRevaluation.class);
	}

	@Override
	public void setM_CostRevaluation(final org.compiere.model.I_M_CostRevaluation M_CostRevaluation)
	{
		set_ValueFromPO(COLUMNNAME_M_CostRevaluation_ID, org.compiere.model.I_M_CostRevaluation.class, M_CostRevaluation);
	}

	@Override
	public void setM_CostRevaluation_ID (final int M_CostRevaluation_ID)
	{
		if (M_CostRevaluation_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_CostRevaluation_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_CostRevaluation_ID, M_CostRevaluation_ID);
	}

	@Override
	public int getM_CostRevaluation_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_CostRevaluation_ID);
	}

	@Override
	public void setM_CostRevaluationLine_ID (final int M_CostRevaluationLine_ID)
	{
		if (M_CostRevaluationLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_CostRevaluationLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_CostRevaluationLine_ID, M_CostRevaluationLine_ID);
	}

	@Override
	public int getM_CostRevaluationLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_CostRevaluationLine_ID);
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
	public void setNewCostPrice (final BigDecimal NewCostPrice)
	{
		set_Value (COLUMNNAME_NewCostPrice, NewCostPrice);
	}

	@Override
	public BigDecimal getNewCostPrice() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_NewCostPrice);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}