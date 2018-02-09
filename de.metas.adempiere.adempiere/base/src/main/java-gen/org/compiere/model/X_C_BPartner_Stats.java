/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_BPartner_Stats
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_BPartner_Stats extends org.compiere.model.PO implements I_C_BPartner_Stats, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -2136749943L;

    /** Standard Constructor */
    public X_C_BPartner_Stats (Properties ctx, int C_BPartner_Stats_ID, String trxName)
    {
      super (ctx, C_BPartner_Stats_ID, trxName);
      /** if (C_BPartner_Stats_ID == 0)
        {
			setC_BPartner_ID (0);
			setC_BPartner_Stats_ID (0);
        } */
    }

    /** Load Constructor */
    public X_C_BPartner_Stats (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_Name, get_TrxName());
      return poi;
    }

	/** Set Aktueller Gesamtertrag.
		@param ActualLifeTimeValue 
		Aktueller Gesamtertrag
	  */
	@Override
	public void setActualLifeTimeValue (java.math.BigDecimal ActualLifeTimeValue)
	{
		set_Value (COLUMNNAME_ActualLifeTimeValue, ActualLifeTimeValue);
	}

	/** Get Aktueller Gesamtertrag.
		@return Aktueller Gesamtertrag
	  */
	@Override
	public java.math.BigDecimal getActualLifeTimeValue () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualLifeTimeValue);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	@Override
	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_ID, org.compiere.model.I_C_BPartner.class);
	}

	@Override
	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_ID, org.compiere.model.I_C_BPartner.class, C_BPartner);
	}

	/** Set Geschäftspartner.
		@param C_BPartner_ID 
		Bezeichnet einen Geschäftspartner
	  */
	@Override
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Geschäftspartner.
		@return Bezeichnet einen Geschäftspartner
	  */
	@Override
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_BPartner_Stats.
		@param C_BPartner_Stats_ID C_BPartner_Stats	  */
	@Override
	public void setC_BPartner_Stats_ID (int C_BPartner_Stats_ID)
	{
		if (C_BPartner_Stats_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Stats_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Stats_ID, Integer.valueOf(C_BPartner_Stats_ID));
	}

	/** Get C_BPartner_Stats.
		@return C_BPartner_Stats	  */
	@Override
	public int getC_BPartner_Stats_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Stats_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Credit limit indicator %.
		@param CreditLimitIndicator 
		Percent of Credit used from the limit
	  */
	@Override
	public void setCreditLimitIndicator (java.lang.String CreditLimitIndicator)
	{
		set_Value (COLUMNNAME_CreditLimitIndicator, CreditLimitIndicator);
	}

	/** Get Credit limit indicator %.
		@return Percent of Credit used from the limit
	  */
	@Override
	public java.lang.String getCreditLimitIndicator () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CreditLimitIndicator);
	}

	/** Set Offene Posten.
		@param OpenItems Offene Posten	  */
	@Override
	public void setOpenItems (java.math.BigDecimal OpenItems)
	{
		set_Value (COLUMNNAME_OpenItems, OpenItems);
	}

	/** Get Offene Posten.
		@return Offene Posten	  */
	@Override
	public java.math.BigDecimal getOpenItems () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_OpenItems);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Kredit gewährt.
		@param SO_CreditUsed 
		Gegenwärtiger Aussenstand
	  */
	@Override
	public void setSO_CreditUsed (java.math.BigDecimal SO_CreditUsed)
	{
		set_Value (COLUMNNAME_SO_CreditUsed, SO_CreditUsed);
	}

	/** Get Kredit gewährt.
		@return Gegenwärtiger Aussenstand
	  */
	@Override
	public java.math.BigDecimal getSO_CreditUsed () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SO_CreditUsed);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
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
	/** Set Kreditstatus.
		@param SOCreditStatus 
		Kreditstatus des Geschäftspartners
	  */
	@Override
	public void setSOCreditStatus (java.lang.String SOCreditStatus)
	{

		set_Value (COLUMNNAME_SOCreditStatus, SOCreditStatus);
	}

	/** Get Kreditstatus.
		@return Kreditstatus des Geschäftspartners
	  */
	@Override
	public java.lang.String getSOCreditStatus () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_SOCreditStatus);
	}
}