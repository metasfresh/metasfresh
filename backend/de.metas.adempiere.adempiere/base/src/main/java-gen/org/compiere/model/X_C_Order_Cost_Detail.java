// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_Order_Cost_Detail
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Order_Cost_Detail extends org.compiere.model.PO implements I_C_Order_Cost_Detail, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 628572673L;

    /** Standard Constructor */
    public X_C_Order_Cost_Detail (final Properties ctx, final int C_Order_Cost_Detail_ID, @Nullable final String trxName)
    {
      super (ctx, C_Order_Cost_Detail_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Order_Cost_Detail (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_Order_Cost_Detail_ID (final int C_Order_Cost_Detail_ID)
	{
		if (C_Order_Cost_Detail_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Order_Cost_Detail_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Order_Cost_Detail_ID, C_Order_Cost_Detail_ID);
	}

	@Override
	public int getC_Order_Cost_Detail_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Order_Cost_Detail_ID);
	}

	@Override
	public org.compiere.model.I_C_Order_Cost getC_Order_Cost()
	{
		return get_ValueAsPO(COLUMNNAME_C_Order_Cost_ID, org.compiere.model.I_C_Order_Cost.class);
	}

	@Override
	public void setC_Order_Cost(final org.compiere.model.I_C_Order_Cost C_Order_Cost)
	{
		set_ValueFromPO(COLUMNNAME_C_Order_Cost_ID, org.compiere.model.I_C_Order_Cost.class, C_Order_Cost);
	}

	@Override
	public void setC_Order_Cost_ID (final int C_Order_Cost_ID)
	{
		if (C_Order_Cost_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Order_Cost_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Order_Cost_ID, C_Order_Cost_ID);
	}

	@Override
	public int getC_Order_Cost_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Order_Cost_ID);
	}

	@Override
	public org.compiere.model.I_C_OrderLine getC_OrderLine()
	{
		return get_ValueAsPO(COLUMNNAME_C_OrderLine_ID, org.compiere.model.I_C_OrderLine.class);
	}

	@Override
	public void setC_OrderLine(final org.compiere.model.I_C_OrderLine C_OrderLine)
	{
		set_ValueFromPO(COLUMNNAME_C_OrderLine_ID, org.compiere.model.I_C_OrderLine.class, C_OrderLine);
	}

	@Override
	public void setC_OrderLine_ID (final int C_OrderLine_ID)
	{
		if (C_OrderLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_OrderLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_OrderLine_ID, C_OrderLine_ID);
	}

	@Override
	public int getC_OrderLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_OrderLine_ID);
	}

	@Override
	public void setCostAmount (final BigDecimal CostAmount)
	{
		set_Value (COLUMNNAME_CostAmount, CostAmount);
	}

	@Override
	public BigDecimal getCostAmount() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_CostAmount);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setCostAmountReceived (final BigDecimal CostAmountReceived)
	{
		set_Value (COLUMNNAME_CostAmountReceived, CostAmountReceived);
	}

	@Override
	public BigDecimal getCostAmountReceived() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_CostAmountReceived);
		return bd != null ? bd : BigDecimal.ZERO;
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
	public void setLineNetAmt (final BigDecimal LineNetAmt)
	{
		set_Value (COLUMNNAME_LineNetAmt, LineNetAmt);
	}

	@Override
	public BigDecimal getLineNetAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_LineNetAmt);
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
	public void setQtyOrdered (final BigDecimal QtyOrdered)
	{
		set_Value (COLUMNNAME_QtyOrdered, QtyOrdered);
	}

	@Override
	public BigDecimal getQtyOrdered() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyOrdered);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyReceived (final BigDecimal QtyReceived)
	{
		set_Value (COLUMNNAME_QtyReceived, QtyReceived);
	}

	@Override
	public BigDecimal getQtyReceived() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyReceived);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}