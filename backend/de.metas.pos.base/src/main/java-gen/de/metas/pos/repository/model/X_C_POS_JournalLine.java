// Generated Model - DO NOT CHANGE
package de.metas.pos.repository.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_POS_JournalLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_POS_JournalLine extends org.compiere.model.PO implements I_C_POS_JournalLine, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 372286947L;

    /** Standard Constructor */
    public X_C_POS_JournalLine (final Properties ctx, final int C_POS_JournalLine_ID, @Nullable final String trxName)
    {
      super (ctx, C_POS_JournalLine_ID, trxName);
    }

    /** Load Constructor */
    public X_C_POS_JournalLine (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAmount (final BigDecimal Amount)
	{
		set_Value (COLUMNNAME_Amount, Amount);
	}

	@Override
	public BigDecimal getAmount() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Amount);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setCashier_ID (final int Cashier_ID)
	{
		if (Cashier_ID < 1) 
			set_Value (COLUMNNAME_Cashier_ID, null);
		else 
			set_Value (COLUMNNAME_Cashier_ID, Cashier_ID);
	}

	@Override
	public int getCashier_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Cashier_ID);
	}

	@Override
	public de.metas.pos.repository.model.I_C_POS_Journal getC_POS_Journal()
	{
		return get_ValueAsPO(COLUMNNAME_C_POS_Journal_ID, de.metas.pos.repository.model.I_C_POS_Journal.class);
	}

	@Override
	public void setC_POS_Journal(final de.metas.pos.repository.model.I_C_POS_Journal C_POS_Journal)
	{
		set_ValueFromPO(COLUMNNAME_C_POS_Journal_ID, de.metas.pos.repository.model.I_C_POS_Journal.class, C_POS_Journal);
	}

	@Override
	public void setC_POS_Journal_ID (final int C_POS_Journal_ID)
	{
		if (C_POS_Journal_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_POS_Journal_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_POS_Journal_ID, C_POS_Journal_ID);
	}

	@Override
	public int getC_POS_Journal_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_POS_Journal_ID);
	}

	@Override
	public void setC_POS_JournalLine_ID (final int C_POS_JournalLine_ID)
	{
		if (C_POS_JournalLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_POS_JournalLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_POS_JournalLine_ID, C_POS_JournalLine_ID);
	}

	@Override
	public int getC_POS_JournalLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_POS_JournalLine_ID);
	}

	@Override
	public de.metas.pos.repository.model.I_C_POS_Order getC_POS_Order()
	{
		return get_ValueAsPO(COLUMNNAME_C_POS_Order_ID, de.metas.pos.repository.model.I_C_POS_Order.class);
	}

	@Override
	public void setC_POS_Order(final de.metas.pos.repository.model.I_C_POS_Order C_POS_Order)
	{
		set_ValueFromPO(COLUMNNAME_C_POS_Order_ID, de.metas.pos.repository.model.I_C_POS_Order.class, C_POS_Order);
	}

	@Override
	public void setC_POS_Order_ID (final int C_POS_Order_ID)
	{
		if (C_POS_Order_ID < 1) 
			set_Value (COLUMNNAME_C_POS_Order_ID, null);
		else 
			set_Value (COLUMNNAME_C_POS_Order_ID, C_POS_Order_ID);
	}

	@Override
	public int getC_POS_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_POS_Order_ID);
	}

	@Override
	public de.metas.pos.repository.model.I_C_POS_Payment getC_POS_Payment()
	{
		return get_ValueAsPO(COLUMNNAME_C_POS_Payment_ID, de.metas.pos.repository.model.I_C_POS_Payment.class);
	}

	@Override
	public void setC_POS_Payment(final de.metas.pos.repository.model.I_C_POS_Payment C_POS_Payment)
	{
		set_ValueFromPO(COLUMNNAME_C_POS_Payment_ID, de.metas.pos.repository.model.I_C_POS_Payment.class, C_POS_Payment);
	}

	@Override
	public void setC_POS_Payment_ID (final int C_POS_Payment_ID)
	{
		if (C_POS_Payment_ID < 1) 
			set_Value (COLUMNNAME_C_POS_Payment_ID, null);
		else 
			set_Value (COLUMNNAME_C_POS_Payment_ID, C_POS_Payment_ID);
	}

	@Override
	public int getC_POS_Payment_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_POS_Payment_ID);
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

	/** 
	 * Type AD_Reference_ID=541892
	 * Reference name: C_POS_JournalLine_Type
	 */
	public static final int TYPE_AD_Reference_ID=541892;
	/** CashPayment = CASH_PAY */
	public static final String TYPE_CashPayment = "CASH_PAY";
	/** CardPayment = CARD_PAY */
	public static final String TYPE_CardPayment = "CARD_PAY";
	/** CashInOut = CASH_INOUT */
	public static final String TYPE_CashInOut = "CASH_INOUT";
	/** CashClosingDifference = CASH_DIFF */
	public static final String TYPE_CashClosingDifference = "CASH_DIFF";
	@Override
	public void setType (final java.lang.String Type)
	{
		set_Value (COLUMNNAME_Type, Type);
	}

	@Override
	public java.lang.String getType() 
	{
		return get_ValueAsString(COLUMNNAME_Type);
	}
}