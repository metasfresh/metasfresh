// Generated Model - DO NOT CHANGE
package org.eevolution.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for DD_Order_Candidate_DDOrder
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_DD_Order_Candidate_DDOrder extends org.compiere.model.PO implements I_DD_Order_Candidate_DDOrder, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -174702267L;

    /** Standard Constructor */
    public X_DD_Order_Candidate_DDOrder (final Properties ctx, final int DD_Order_Candidate_DDOrder_ID, @Nullable final String trxName)
    {
      super (ctx, DD_Order_Candidate_DDOrder_ID, trxName);
    }

    /** Load Constructor */
    public X_DD_Order_Candidate_DDOrder (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setDD_Order_Candidate_DDOrder_ID (final int DD_Order_Candidate_DDOrder_ID)
	{
		if (DD_Order_Candidate_DDOrder_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DD_Order_Candidate_DDOrder_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DD_Order_Candidate_DDOrder_ID, DD_Order_Candidate_DDOrder_ID);
	}

	@Override
	public int getDD_Order_Candidate_DDOrder_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DD_Order_Candidate_DDOrder_ID);
	}

	@Override
	public org.eevolution.model.I_DD_Order_Candidate getDD_Order_Candidate()
	{
		return get_ValueAsPO(COLUMNNAME_DD_Order_Candidate_ID, org.eevolution.model.I_DD_Order_Candidate.class);
	}

	@Override
	public void setDD_Order_Candidate(final org.eevolution.model.I_DD_Order_Candidate DD_Order_Candidate)
	{
		set_ValueFromPO(COLUMNNAME_DD_Order_Candidate_ID, org.eevolution.model.I_DD_Order_Candidate.class, DD_Order_Candidate);
	}

	@Override
	public void setDD_Order_Candidate_ID (final int DD_Order_Candidate_ID)
	{
		if (DD_Order_Candidate_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DD_Order_Candidate_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DD_Order_Candidate_ID, DD_Order_Candidate_ID);
	}

	@Override
	public int getDD_Order_Candidate_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DD_Order_Candidate_ID);
	}

	@Override
	public org.eevolution.model.I_DD_Order getDD_Order()
	{
		return get_ValueAsPO(COLUMNNAME_DD_Order_ID, org.eevolution.model.I_DD_Order.class);
	}

	@Override
	public void setDD_Order(final org.eevolution.model.I_DD_Order DD_Order)
	{
		set_ValueFromPO(COLUMNNAME_DD_Order_ID, org.eevolution.model.I_DD_Order.class, DD_Order);
	}

	@Override
	public void setDD_Order_ID (final int DD_Order_ID)
	{
		if (DD_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DD_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DD_Order_ID, DD_Order_ID);
	}

	@Override
	public int getDD_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DD_Order_ID);
	}

	@Override
	public org.eevolution.model.I_DD_OrderLine getDD_OrderLine()
	{
		return get_ValueAsPO(COLUMNNAME_DD_OrderLine_ID, org.eevolution.model.I_DD_OrderLine.class);
	}

	@Override
	public void setDD_OrderLine(final org.eevolution.model.I_DD_OrderLine DD_OrderLine)
	{
		set_ValueFromPO(COLUMNNAME_DD_OrderLine_ID, org.eevolution.model.I_DD_OrderLine.class, DD_OrderLine);
	}

	@Override
	public void setDD_OrderLine_ID (final int DD_OrderLine_ID)
	{
		if (DD_OrderLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DD_OrderLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DD_OrderLine_ID, DD_OrderLine_ID);
	}

	@Override
	public int getDD_OrderLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DD_OrderLine_ID);
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
}