// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_InOut_Cost
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_InOut_Cost extends org.compiere.model.PO implements I_M_InOut_Cost, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1211716258L;

    /** Standard Constructor */
    public X_M_InOut_Cost (final Properties ctx, final int M_InOut_Cost_ID, @Nullable final String trxName)
    {
      super (ctx, M_InOut_Cost_ID, trxName);
    }

    /** Load Constructor */
    public X_M_InOut_Cost (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_BPartner_ID (final int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, C_BPartner_ID);
	}

	@Override
	public int getC_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
	}

	@Override
	public org.compiere.model.I_C_Cost_Type getC_Cost_Type()
	{
		return get_ValueAsPO(COLUMNNAME_C_Cost_Type_ID, org.compiere.model.I_C_Cost_Type.class);
	}

	@Override
	public void setC_Cost_Type(final org.compiere.model.I_C_Cost_Type C_Cost_Type)
	{
		set_ValueFromPO(COLUMNNAME_C_Cost_Type_ID, org.compiere.model.I_C_Cost_Type.class, C_Cost_Type);
	}

	@Override
	public void setC_Cost_Type_ID (final int C_Cost_Type_ID)
	{
		if (C_Cost_Type_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Cost_Type_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Cost_Type_ID, C_Cost_Type_ID);
	}

	@Override
	public int getC_Cost_Type_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Cost_Type_ID);
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
	public org.compiere.model.I_C_Order_Cost_Detail getC_Order_Cost_Detail()
	{
		return get_ValueAsPO(COLUMNNAME_C_Order_Cost_Detail_ID, org.compiere.model.I_C_Order_Cost_Detail.class);
	}

	@Override
	public void setC_Order_Cost_Detail(final org.compiere.model.I_C_Order_Cost_Detail C_Order_Cost_Detail)
	{
		set_ValueFromPO(COLUMNNAME_C_Order_Cost_Detail_ID, org.compiere.model.I_C_Order_Cost_Detail.class, C_Order_Cost_Detail);
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
	public org.compiere.model.I_C_Order getC_Order()
	{
		return get_ValueAsPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
	public void setC_Order(final org.compiere.model.I_C_Order C_Order)
	{
		set_ValueFromPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class, C_Order);
	}

	@Override
	public void setC_Order_ID (final int C_Order_ID)
	{
		if (C_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, C_Order_ID);
	}

	@Override
	public int getC_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Order_ID);
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
	public void setCostAmountInvoiced (final BigDecimal CostAmountInvoiced)
	{
		set_Value (COLUMNNAME_CostAmountInvoiced, CostAmountInvoiced);
	}

	@Override
	public BigDecimal getCostAmountInvoiced() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_CostAmountInvoiced);
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
	public void setIsInvoiced (final boolean IsInvoiced)
	{
		set_Value (COLUMNNAME_IsInvoiced, IsInvoiced);
	}

	@Override
	public boolean isInvoiced() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsInvoiced);
	}

	@Override
	public void setIsSOTrx (final boolean IsSOTrx)
	{
		set_Value (COLUMNNAME_IsSOTrx, IsSOTrx);
	}

	@Override
	public boolean isSOTrx() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSOTrx);
	}

	@Override
	public org.compiere.model.I_M_CostElement getM_CostElement()
	{
		return get_ValueAsPO(COLUMNNAME_M_CostElement_ID, org.compiere.model.I_M_CostElement.class);
	}

	@Override
	public void setM_CostElement(final org.compiere.model.I_M_CostElement M_CostElement)
	{
		set_ValueFromPO(COLUMNNAME_M_CostElement_ID, org.compiere.model.I_M_CostElement.class, M_CostElement);
	}

	@Override
	public void setM_CostElement_ID (final int M_CostElement_ID)
	{
		if (M_CostElement_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_CostElement_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_CostElement_ID, M_CostElement_ID);
	}

	@Override
	public int getM_CostElement_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_CostElement_ID);
	}

	@Override
	public void setM_InOut_Cost_ID (final int M_InOut_Cost_ID)
	{
		if (M_InOut_Cost_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_InOut_Cost_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_InOut_Cost_ID, M_InOut_Cost_ID);
	}

	@Override
	public int getM_InOut_Cost_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_InOut_Cost_ID);
	}

	@Override
	public org.compiere.model.I_M_InOut getM_InOut()
	{
		return get_ValueAsPO(COLUMNNAME_M_InOut_ID, org.compiere.model.I_M_InOut.class);
	}

	@Override
	public void setM_InOut(final org.compiere.model.I_M_InOut M_InOut)
	{
		set_ValueFromPO(COLUMNNAME_M_InOut_ID, org.compiere.model.I_M_InOut.class, M_InOut);
	}

	@Override
	public void setM_InOut_ID (final int M_InOut_ID)
	{
		if (M_InOut_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_InOut_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_InOut_ID, M_InOut_ID);
	}

	@Override
	public int getM_InOut_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_InOut_ID);
	}

	@Override
	public org.compiere.model.I_M_InOutLine getM_InOutLine()
	{
		return get_ValueAsPO(COLUMNNAME_M_InOutLine_ID, org.compiere.model.I_M_InOutLine.class);
	}

	@Override
	public void setM_InOutLine(final org.compiere.model.I_M_InOutLine M_InOutLine)
	{
		set_ValueFromPO(COLUMNNAME_M_InOutLine_ID, org.compiere.model.I_M_InOutLine.class, M_InOutLine);
	}

	@Override
	public void setM_InOutLine_ID (final int M_InOutLine_ID)
	{
		if (M_InOutLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_InOutLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_InOutLine_ID, M_InOutLine_ID);
	}

	@Override
	public int getM_InOutLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_InOutLine_ID);
	}

	@Override
	public void setQty (final BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	@Override
	public BigDecimal getQty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Qty);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public org.compiere.model.I_M_InOut_Cost getReversal()
	{
		return get_ValueAsPO(COLUMNNAME_Reversal_ID, org.compiere.model.I_M_InOut_Cost.class);
	}

	@Override
	public void setReversal(final org.compiere.model.I_M_InOut_Cost Reversal)
	{
		set_ValueFromPO(COLUMNNAME_Reversal_ID, org.compiere.model.I_M_InOut_Cost.class, Reversal);
	}

	@Override
	public void setReversal_ID (final int Reversal_ID)
	{
		if (Reversal_ID < 1) 
			set_Value (COLUMNNAME_Reversal_ID, null);
		else 
			set_Value (COLUMNNAME_Reversal_ID, Reversal_ID);
	}

	@Override
	public int getReversal_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Reversal_ID);
	}
}