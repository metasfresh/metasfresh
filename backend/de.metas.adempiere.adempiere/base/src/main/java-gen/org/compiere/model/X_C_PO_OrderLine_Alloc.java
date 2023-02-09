// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_PO_OrderLine_Alloc
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_PO_OrderLine_Alloc extends org.compiere.model.PO implements I_C_PO_OrderLine_Alloc, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -961524213L;

    /** Standard Constructor */
    public X_C_PO_OrderLine_Alloc (final Properties ctx, final int C_PO_OrderLine_Alloc_ID, @Nullable final String trxName)
    {
      super (ctx, C_PO_OrderLine_Alloc_ID, trxName);
    }

    /** Load Constructor */
    public X_C_PO_OrderLine_Alloc (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_C_Order getC_OrderPO()
	{
		return get_ValueAsPO(COLUMNNAME_C_OrderPO_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
	public void setC_OrderPO(final org.compiere.model.I_C_Order C_OrderPO)
	{
		set_ValueFromPO(COLUMNNAME_C_OrderPO_ID, org.compiere.model.I_C_Order.class, C_OrderPO);
	}

	@Override
	public void setC_OrderPO_ID (final int C_OrderPO_ID)
	{
		if (C_OrderPO_ID < 1) 
			set_Value (COLUMNNAME_C_OrderPO_ID, null);
		else 
			set_Value (COLUMNNAME_C_OrderPO_ID, C_OrderPO_ID);
	}

	@Override
	public int getC_OrderPO_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_OrderPO_ID);
	}

	@Override
	public org.compiere.model.I_C_Order getC_OrderSO()
	{
		return get_ValueAsPO(COLUMNNAME_C_OrderSO_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
	public void setC_OrderSO(final org.compiere.model.I_C_Order C_OrderSO)
	{
		set_ValueFromPO(COLUMNNAME_C_OrderSO_ID, org.compiere.model.I_C_Order.class, C_OrderSO);
	}

	@Override
	public void setC_OrderSO_ID (final int C_OrderSO_ID)
	{
		if (C_OrderSO_ID < 1) 
			set_Value (COLUMNNAME_C_OrderSO_ID, null);
		else 
			set_Value (COLUMNNAME_C_OrderSO_ID, C_OrderSO_ID);
	}

	@Override
	public int getC_OrderSO_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_OrderSO_ID);
	}

	@Override
	public void setC_PO_OrderLine_Alloc_ID (final int C_PO_OrderLine_Alloc_ID)
	{
		if (C_PO_OrderLine_Alloc_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_PO_OrderLine_Alloc_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_PO_OrderLine_Alloc_ID, C_PO_OrderLine_Alloc_ID);
	}

	@Override
	public int getC_PO_OrderLine_Alloc_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_PO_OrderLine_Alloc_ID);
	}

	@Override
	public org.compiere.model.I_C_OrderLine getC_PO_OrderLine()
	{
		return get_ValueAsPO(COLUMNNAME_C_PO_OrderLine_ID, org.compiere.model.I_C_OrderLine.class);
	}

	@Override
	public void setC_PO_OrderLine(final org.compiere.model.I_C_OrderLine C_PO_OrderLine)
	{
		set_ValueFromPO(COLUMNNAME_C_PO_OrderLine_ID, org.compiere.model.I_C_OrderLine.class, C_PO_OrderLine);
	}

	@Override
	public void setC_PO_OrderLine_ID (final int C_PO_OrderLine_ID)
	{
		if (C_PO_OrderLine_ID < 1) 
			set_Value (COLUMNNAME_C_PO_OrderLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_PO_OrderLine_ID, C_PO_OrderLine_ID);
	}

	@Override
	public int getC_PO_OrderLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_PO_OrderLine_ID);
	}

	@Override
	public org.compiere.model.I_C_OrderLine getC_SO_OrderLine()
	{
		return get_ValueAsPO(COLUMNNAME_C_SO_OrderLine_ID, org.compiere.model.I_C_OrderLine.class);
	}

	@Override
	public void setC_SO_OrderLine(final org.compiere.model.I_C_OrderLine C_SO_OrderLine)
	{
		set_ValueFromPO(COLUMNNAME_C_SO_OrderLine_ID, org.compiere.model.I_C_OrderLine.class, C_SO_OrderLine);
	}

	@Override
	public void setC_SO_OrderLine_ID (final int C_SO_OrderLine_ID)
	{
		if (C_SO_OrderLine_ID < 1) 
			set_Value (COLUMNNAME_C_SO_OrderLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_SO_OrderLine_ID, C_SO_OrderLine_ID);
	}

	@Override
	public int getC_SO_OrderLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_SO_OrderLine_ID);
	}
}