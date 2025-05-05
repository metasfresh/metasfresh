// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_ForeignExchangeContract_Alloc
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_ForeignExchangeContract_Alloc extends org.compiere.model.PO implements I_C_ForeignExchangeContract_Alloc, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -820261468L;

    /** Standard Constructor */
    public X_C_ForeignExchangeContract_Alloc (final Properties ctx, final int C_ForeignExchangeContract_Alloc_ID, @Nullable final String trxName)
    {
      super (ctx, C_ForeignExchangeContract_Alloc_ID, trxName);
    }

    /** Load Constructor */
    public X_C_ForeignExchangeContract_Alloc (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAllocatedAmt (final BigDecimal AllocatedAmt)
	{
		set_Value (COLUMNNAME_AllocatedAmt, AllocatedAmt);
	}

	@Override
	public BigDecimal getAllocatedAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_AllocatedAmt);
		return bd != null ? bd : BigDecimal.ZERO;
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
	public void setC_ForeignExchangeContract_Alloc_ID (final int C_ForeignExchangeContract_Alloc_ID)
	{
		if (C_ForeignExchangeContract_Alloc_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_ForeignExchangeContract_Alloc_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_ForeignExchangeContract_Alloc_ID, C_ForeignExchangeContract_Alloc_ID);
	}

	@Override
	public int getC_ForeignExchangeContract_Alloc_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_ForeignExchangeContract_Alloc_ID);
	}

	@Override
	public org.compiere.model.I_C_ForeignExchangeContract getC_ForeignExchangeContract()
	{
		return get_ValueAsPO(COLUMNNAME_C_ForeignExchangeContract_ID, org.compiere.model.I_C_ForeignExchangeContract.class);
	}

	@Override
	public void setC_ForeignExchangeContract(final org.compiere.model.I_C_ForeignExchangeContract C_ForeignExchangeContract)
	{
		set_ValueFromPO(COLUMNNAME_C_ForeignExchangeContract_ID, org.compiere.model.I_C_ForeignExchangeContract.class, C_ForeignExchangeContract);
	}

	@Override
	public void setC_ForeignExchangeContract_ID (final int C_ForeignExchangeContract_ID)
	{
		if (C_ForeignExchangeContract_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_ForeignExchangeContract_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_ForeignExchangeContract_ID, C_ForeignExchangeContract_ID);
	}

	@Override
	public int getC_ForeignExchangeContract_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_ForeignExchangeContract_ID);
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
	public void setGrandTotal (final BigDecimal GrandTotal)
	{
		set_ValueNoCheck (COLUMNNAME_GrandTotal, GrandTotal);
	}

	@Override
	public BigDecimal getGrandTotal() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_GrandTotal);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public org.compiere.model.I_M_SectionCode getM_SectionCode()
	{
		return get_ValueAsPO(COLUMNNAME_M_SectionCode_ID, org.compiere.model.I_M_SectionCode.class);
	}

	@Override
	public void setM_SectionCode(final org.compiere.model.I_M_SectionCode M_SectionCode)
	{
		set_ValueFromPO(COLUMNNAME_M_SectionCode_ID, org.compiere.model.I_M_SectionCode.class, M_SectionCode);
	}

	@Override
	public void setM_SectionCode_ID (final int M_SectionCode_ID)
	{
		if (M_SectionCode_ID < 1) 
			set_Value (COLUMNNAME_M_SectionCode_ID, null);
		else 
			set_Value (COLUMNNAME_M_SectionCode_ID, M_SectionCode_ID);
	}

	@Override
	public int getM_SectionCode_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_SectionCode_ID);
	}
}