// Generated Model - DO NOT CHANGE
package de.metas.contracts.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_SubscrDiscount_Line
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_SubscrDiscount_Line extends org.compiere.model.PO implements I_C_SubscrDiscount_Line, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1552124129L;

    /** Standard Constructor */
    public X_C_SubscrDiscount_Line (final Properties ctx, final int C_SubscrDiscount_Line_ID, @Nullable final String trxName)
    {
      super (ctx, C_SubscrDiscount_Line_ID, trxName);
    }

    /** Load Constructor */
    public X_C_SubscrDiscount_Line (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public de.metas.contracts.model.I_C_SubscrDiscount getC_SubscrDiscount()
	{
		return get_ValueAsPO(COLUMNNAME_C_SubscrDiscount_ID, de.metas.contracts.model.I_C_SubscrDiscount.class);
	}

	@Override
	public void setC_SubscrDiscount(final de.metas.contracts.model.I_C_SubscrDiscount C_SubscrDiscount)
	{
		set_ValueFromPO(COLUMNNAME_C_SubscrDiscount_ID, de.metas.contracts.model.I_C_SubscrDiscount.class, C_SubscrDiscount);
	}

	@Override
	public void setC_SubscrDiscount_ID (final int C_SubscrDiscount_ID)
	{
		if (C_SubscrDiscount_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_SubscrDiscount_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_SubscrDiscount_ID, C_SubscrDiscount_ID);
	}

	@Override
	public int getC_SubscrDiscount_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_SubscrDiscount_ID);
	}

	@Override
	public void setC_SubscrDiscount_Line_ID (final int C_SubscrDiscount_Line_ID)
	{
		if (C_SubscrDiscount_Line_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_SubscrDiscount_Line_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_SubscrDiscount_Line_ID, C_SubscrDiscount_Line_ID);
	}

	@Override
	public int getC_SubscrDiscount_Line_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_SubscrDiscount_Line_ID);
	}

	@Override
	public void setDiscount (final BigDecimal Discount)
	{
		set_Value (COLUMNNAME_Discount, Discount);
	}

	@Override
	public BigDecimal getDiscount() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Discount);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	/** 
	 * IfForeignDiscountsExist AD_Reference_ID=541401
	 * Reference name: C_SubscrDiscount_Line_IfForeignDiscountsExist
	 */
	public static final int IFFOREIGNDISCOUNTSEXIST_AD_Reference_ID=541401;
	/** Use_Other_Discount = Use_Other_Discount */
	public static final String IFFOREIGNDISCOUNTSEXIST_Use_Other_Discount = "Use_Other_Discount";
	/** Use_Our_Discount = Use_Our_Discount */
	public static final String IFFOREIGNDISCOUNTSEXIST_Use_Our_Discount = "Use_Our_Discount";
	@Override
	public void setIfForeignDiscountsExist (final java.lang.String IfForeignDiscountsExist)
	{
		set_Value (COLUMNNAME_IfForeignDiscountsExist, IfForeignDiscountsExist);
	}

	@Override
	public java.lang.String getIfForeignDiscountsExist() 
	{
		return get_ValueAsString(COLUMNNAME_IfForeignDiscountsExist);
	}

	@Override
	public void setMatchIfTermEndsWithCalendarYear (final boolean MatchIfTermEndsWithCalendarYear)
	{
		set_Value (COLUMNNAME_MatchIfTermEndsWithCalendarYear, MatchIfTermEndsWithCalendarYear);
	}

	@Override
	public boolean isMatchIfTermEndsWithCalendarYear() 
	{
		return get_ValueAsBoolean(COLUMNNAME_MatchIfTermEndsWithCalendarYear);
	}

	@Override
	public void setM_Product_Category_ID (final int M_Product_Category_ID)
	{
		if (M_Product_Category_ID < 1) 
			set_Value (COLUMNNAME_M_Product_Category_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_Category_ID, M_Product_Category_ID);
	}

	@Override
	public int getM_Product_Category_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_Category_ID);
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
	public void setSeqNo (final int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, SeqNo);
	}

	@Override
	public int getSeqNo() 
	{
		return get_ValueAsInt(COLUMNNAME_SeqNo);
	}

	@Override
	public void setStartDay (final int StartDay)
	{
		set_Value (COLUMNNAME_StartDay, StartDay);
	}

	@Override
	public int getStartDay() 
	{
		return get_ValueAsInt(COLUMNNAME_StartDay);
	}

	@Override
	public void setStartMonth (final int StartMonth)
	{
		set_Value (COLUMNNAME_StartMonth, StartMonth);
	}

	@Override
	public int getStartMonth() 
	{
		return get_ValueAsInt(COLUMNNAME_StartMonth);
	}
}