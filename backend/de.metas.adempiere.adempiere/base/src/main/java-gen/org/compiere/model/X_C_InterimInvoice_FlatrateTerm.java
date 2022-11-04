// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_InterimInvoice_FlatrateTerm
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_InterimInvoice_FlatrateTerm extends org.compiere.model.PO implements I_C_InterimInvoice_FlatrateTerm, org.compiere.model.I_Persistent
{

	private static final long serialVersionUID = -162909973L;

    /** Standard Constructor */
    public X_C_InterimInvoice_FlatrateTerm (final Properties ctx, final int C_InterimInvoice_FlatrateTerm_ID, @Nullable final String trxName)
    {
      super (ctx, C_InterimInvoice_FlatrateTerm_ID, trxName);
    }

    /** Load Constructor */
    public X_C_InterimInvoice_FlatrateTerm (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_Flatrate_Term_ID (final int C_Flatrate_Term_ID)
	{
		if (C_Flatrate_Term_ID < 1)
			set_Value (COLUMNNAME_C_Flatrate_Term_ID, null);
		else
			set_Value (COLUMNNAME_C_Flatrate_Term_ID, C_Flatrate_Term_ID);
	}

	@Override
	public int getC_Flatrate_Term_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_Flatrate_Term_ID);
	}

	@Override
	public void setC_Interim_Invoice_Candidate_ID (final int C_Interim_Invoice_Candidate_ID)
	{
		if (C_Interim_Invoice_Candidate_ID < 1)
			set_Value (COLUMNNAME_C_Interim_Invoice_Candidate_ID, null);
		else
			set_Value (COLUMNNAME_C_Interim_Invoice_Candidate_ID, C_Interim_Invoice_Candidate_ID);
	}

	@Override
	public int getC_Interim_Invoice_Candidate_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_Interim_Invoice_Candidate_ID);
	}

	@Override
	public void setC_InterimInvoice_FlatrateTerm_ID (final int C_InterimInvoice_FlatrateTerm_ID)
	{
		if (C_InterimInvoice_FlatrateTerm_ID < 1)
			set_ValueNoCheck (COLUMNNAME_C_InterimInvoice_FlatrateTerm_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_InterimInvoice_FlatrateTerm_ID, C_InterimInvoice_FlatrateTerm_ID);
	}

	@Override
	public int getC_InterimInvoice_FlatrateTerm_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_InterimInvoice_FlatrateTerm_ID);
	}

	@Override
	public void setC_Invoice_Candidate_Withholding_ID (final int C_Invoice_Candidate_Withholding_ID)
	{
		if (C_Invoice_Candidate_Withholding_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_Candidate_Withholding_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_Candidate_Withholding_ID, C_Invoice_Candidate_Withholding_ID);
	}

	@Override
	public int getC_Invoice_Candidate_Withholding_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Invoice_Candidate_Withholding_ID);
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
			set_Value (COLUMNNAME_C_Order_ID, null);
		else 
			set_Value (COLUMNNAME_C_Order_ID, C_Order_ID);
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
			set_Value (COLUMNNAME_C_OrderLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_OrderLine_ID, C_OrderLine_ID);
	}

	@Override
	public int getC_OrderLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_OrderLine_ID);
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
	public void setPriceActual (final @Nullable BigDecimal PriceActual)
	{
		set_Value (COLUMNNAME_PriceActual, PriceActual);
	}

	@Override
	public BigDecimal getPriceActual() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PriceActual);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyDeliveredInUOM (final @Nullable BigDecimal QtyDeliveredInUOM)
	{
		set_Value (COLUMNNAME_QtyDeliveredInUOM, QtyDeliveredInUOM);
	}

	@Override
	public BigDecimal getQtyDeliveredInUOM()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyDeliveredInUOM);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyInvoiced (final @Nullable BigDecimal QtyInvoiced)
	{
		set_Value (COLUMNNAME_QtyInvoiced, QtyInvoiced);
	}

	@Override
	public BigDecimal getQtyInvoiced() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyInvoiced);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyOrdered (final @Nullable BigDecimal QtyOrdered)
	{
		set_Value (COLUMNNAME_QtyOrdered, QtyOrdered);
	}

	@Override
	public BigDecimal getQtyOrdered() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyOrdered);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}