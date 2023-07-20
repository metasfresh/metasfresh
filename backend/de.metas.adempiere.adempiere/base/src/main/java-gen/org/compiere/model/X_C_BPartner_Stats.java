// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_BPartner_Stats
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_BPartner_Stats extends org.compiere.model.PO implements I_C_BPartner_Stats, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 357566475L;

    /** Standard Constructor */
    public X_C_BPartner_Stats (final Properties ctx, final int C_BPartner_Stats_ID, @Nullable final String trxName)
    {
      super (ctx, C_BPartner_Stats_ID, trxName);
    }

    /** Load Constructor */
    public X_C_BPartner_Stats (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setActualLifeTimeValue (final @Nullable BigDecimal ActualLifeTimeValue)
	{
		set_Value (COLUMNNAME_ActualLifeTimeValue, ActualLifeTimeValue);
	}

	@Override
	public BigDecimal getActualLifeTimeValue() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ActualLifeTimeValue);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setC_BPartner_ID (final int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, C_BPartner_ID);
	}

	@Override
	public int getC_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
	}

	@Override
	public void setC_BPartner_Stats_ID (final int C_BPartner_Stats_ID)
	{
		if (C_BPartner_Stats_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Stats_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Stats_ID, C_BPartner_Stats_ID);
	}

	@Override
	public int getC_BPartner_Stats_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Stats_ID);
	}

	@Override
	public void setC_Currency_ID (final int C_Currency_ID)
	{
		throw new IllegalArgumentException ("C_Currency_ID is virtual column");	}

	@Override
	public int getC_Currency_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Currency_ID);
	}

	@Override
	public void setCreditLimitIndicator (final @Nullable java.lang.String CreditLimitIndicator)
	{
		set_Value (COLUMNNAME_CreditLimitIndicator, CreditLimitIndicator);
	}

	@Override
	public java.lang.String getCreditLimitIndicator() 
	{
		return get_ValueAsString(COLUMNNAME_CreditLimitIndicator);
	}

	@Override
	public void setDeliveryCreditLimitIndicator (final @Nullable java.lang.String DeliveryCreditLimitIndicator)
	{
		set_Value (COLUMNNAME_DeliveryCreditLimitIndicator, DeliveryCreditLimitIndicator);
	}

	@Override
	public java.lang.String getDeliveryCreditLimitIndicator() 
	{
		return get_ValueAsString(COLUMNNAME_DeliveryCreditLimitIndicator);
	}

	/** 
	 * Delivery_CreditStatus AD_Reference_ID=289
	 * Reference name: C_BPartner SOCreditStatus
	 */
	public static final int DELIVERY_CREDITSTATUS_AD_Reference_ID=289;
	/** CreditStop = S */
	public static final String DELIVERY_CREDITSTATUS_CreditStop = "S";
	/** CreditHold = H */
	public static final String DELIVERY_CREDITSTATUS_CreditHold = "H";
	/** CreditWatch = W */
	public static final String DELIVERY_CREDITSTATUS_CreditWatch = "W";
	/** NoCreditCheck = X */
	public static final String DELIVERY_CREDITSTATUS_NoCreditCheck = "X";
	/** CreditOK = O */
	public static final String DELIVERY_CREDITSTATUS_CreditOK = "O";
	/** NurEineRechnung = I */
	public static final String DELIVERY_CREDITSTATUS_NurEineRechnung = "I";
	@Override
	public void setDelivery_CreditStatus (final @Nullable java.lang.String Delivery_CreditStatus)
	{
		set_Value (COLUMNNAME_Delivery_CreditStatus, Delivery_CreditStatus);
	}

	@Override
	public java.lang.String getDelivery_CreditStatus() 
	{
		return get_ValueAsString(COLUMNNAME_Delivery_CreditStatus);
	}

	@Override
	public void setDelivery_CreditUsed (final @Nullable BigDecimal Delivery_CreditUsed)
	{
		set_Value (COLUMNNAME_Delivery_CreditUsed, Delivery_CreditUsed);
	}

	@Override
	public BigDecimal getDelivery_CreditUsed() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Delivery_CreditUsed);
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

	@Override
	public void setOpenItems (final @Nullable BigDecimal OpenItems)
	{
		set_Value (COLUMNNAME_OpenItems, OpenItems);
	}

	@Override
	public BigDecimal getOpenItems() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_OpenItems);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	/** 
	 * SOCreditStatus AD_Reference_ID=289
	 * Reference name: C_BPartner SOCreditStatus
	 */
	public static final int SOCREDITSTATUS_AD_Reference_ID=289;
	/** CreditStop = S */
	public static final String SOCREDITSTATUS_CreditStop = "S";
	/** CreditHold = H */
	public static final String SOCREDITSTATUS_CreditHold = "H";
	/** CreditWatch = W */
	public static final String SOCREDITSTATUS_CreditWatch = "W";
	/** NoCreditCheck = X */
	public static final String SOCREDITSTATUS_NoCreditCheck = "X";
	/** CreditOK = O */
	public static final String SOCREDITSTATUS_CreditOK = "O";
	/** NurEineRechnung = I */
	public static final String SOCREDITSTATUS_NurEineRechnung = "I";
	@Override
	public void setSOCreditStatus (final @Nullable java.lang.String SOCreditStatus)
	{
		set_Value (COLUMNNAME_SOCreditStatus, SOCreditStatus);
	}

	@Override
	public java.lang.String getSOCreditStatus() 
	{
		return get_ValueAsString(COLUMNNAME_SOCreditStatus);
	}

	@Override
	public void setSO_CreditUsed (final @Nullable BigDecimal SO_CreditUsed)
	{
		set_Value (COLUMNNAME_SO_CreditUsed, SO_CreditUsed);
	}

	@Override
	public BigDecimal getSO_CreditUsed() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_SO_CreditUsed);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}