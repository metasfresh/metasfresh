/** Generated Model - DO NOT CHANGE */
package de.metas.banking.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_RecurrentPaymentLine
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_RecurrentPaymentLine extends org.compiere.model.PO implements I_C_RecurrentPaymentLine, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -529131929L;

    /** Standard Constructor */
    public X_C_RecurrentPaymentLine (Properties ctx, int C_RecurrentPaymentLine_ID, String trxName)
    {
      super (ctx, C_RecurrentPaymentLine_ID, trxName);
      /** if (C_RecurrentPaymentLine_ID == 0)
        {
			setC_BP_BankAccount_ID (0);
			setC_BP_BankAccount_Own_ID (0);
			setC_Currency_ID (0); // @C_Currency_ID@
			setC_PaymentTerm_ID (0);
			setC_RecurrentPayment_ID (0);
			setC_RecurrentPaymentLine_ID (0);
			setDateFrom (new Timestamp( System.currentTimeMillis() ));
			setFrequency (0);
			setFrequencyType (null);
			setPayAmt (BigDecimal.ZERO); // 0
        } */
    }

    /** Load Constructor */
    public X_C_RecurrentPaymentLine (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Ansprechpartner.
		@param AD_User_ID 
		User within the system - Internal or Business Partner Contact
	  */
	@Override
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 0) 
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	/** Get Ansprechpartner.
		@return User within the system - Internal or Business Partner Contact
	  */
	@Override
	public int getAD_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BP_BankAccount getC_BP_BankAccount()
	{
		return get_ValueAsPO(COLUMNNAME_C_BP_BankAccount_ID, org.compiere.model.I_C_BP_BankAccount.class);
	}

	@Override
	public void setC_BP_BankAccount(org.compiere.model.I_C_BP_BankAccount C_BP_BankAccount)
	{
		set_ValueFromPO(COLUMNNAME_C_BP_BankAccount_ID, org.compiere.model.I_C_BP_BankAccount.class, C_BP_BankAccount);
	}

	/** Set Bankverbindung.
		@param C_BP_BankAccount_ID 
		Bank Account of the Business Partner
	  */
	@Override
	public void setC_BP_BankAccount_ID (int C_BP_BankAccount_ID)
	{
		if (C_BP_BankAccount_ID < 1) 
			set_Value (COLUMNNAME_C_BP_BankAccount_ID, null);
		else 
			set_Value (COLUMNNAME_C_BP_BankAccount_ID, Integer.valueOf(C_BP_BankAccount_ID));
	}

	/** Get Bankverbindung.
		@return Bank Account of the Business Partner
	  */
	@Override
	public int getC_BP_BankAccount_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BP_BankAccount_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BP_BankAccount getC_BP_BankAccount_Own()
	{
		return get_ValueAsPO(COLUMNNAME_C_BP_BankAccount_Own_ID, org.compiere.model.I_C_BP_BankAccount.class);
	}

	@Override
	public void setC_BP_BankAccount_Own(org.compiere.model.I_C_BP_BankAccount C_BP_BankAccount_Own)
	{
		set_ValueFromPO(COLUMNNAME_C_BP_BankAccount_Own_ID, org.compiere.model.I_C_BP_BankAccount.class, C_BP_BankAccount_Own);
	}

	/** Set eigenes Bankkonto.
		@param C_BP_BankAccount_Own_ID eigenes Bankkonto	  */
	@Override
	public void setC_BP_BankAccount_Own_ID (int C_BP_BankAccount_Own_ID)
	{
		if (C_BP_BankAccount_Own_ID < 1) 
			set_Value (COLUMNNAME_C_BP_BankAccount_Own_ID, null);
		else 
			set_Value (COLUMNNAME_C_BP_BankAccount_Own_ID, Integer.valueOf(C_BP_BankAccount_Own_ID));
	}

	/** Get eigenes Bankkonto.
		@return eigenes Bankkonto	  */
	@Override
	public int getC_BP_BankAccount_Own_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BP_BankAccount_Own_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Charge getC_Charge()
	{
		return get_ValueAsPO(COLUMNNAME_C_Charge_ID, org.compiere.model.I_C_Charge.class);
	}

	@Override
	public void setC_Charge(org.compiere.model.I_C_Charge C_Charge)
	{
		set_ValueFromPO(COLUMNNAME_C_Charge_ID, org.compiere.model.I_C_Charge.class, C_Charge);
	}

	/** Set Charge.
		@param C_Charge_ID 
		Additional document charges
	  */
	@Override
	public void setC_Charge_ID (int C_Charge_ID)
	{
		if (C_Charge_ID < 1) 
			set_Value (COLUMNNAME_C_Charge_ID, null);
		else 
			set_Value (COLUMNNAME_C_Charge_ID, Integer.valueOf(C_Charge_ID));
	}

	/** Get Charge.
		@return Additional document charges
	  */
	@Override
	public int getC_Charge_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Charge_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Currency.
		@param C_Currency_ID 
		The Currency for this record
	  */
	@Override
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Currency.
		@return The Currency for this record
	  */
	@Override
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_PaymentTerm getC_PaymentTerm()
	{
		return get_ValueAsPO(COLUMNNAME_C_PaymentTerm_ID, org.compiere.model.I_C_PaymentTerm.class);
	}

	@Override
	public void setC_PaymentTerm(org.compiere.model.I_C_PaymentTerm C_PaymentTerm)
	{
		set_ValueFromPO(COLUMNNAME_C_PaymentTerm_ID, org.compiere.model.I_C_PaymentTerm.class, C_PaymentTerm);
	}

	/** Set Zahlungsbedingung.
		@param C_PaymentTerm_ID 
		Die Bedingungen für die Bezahlung dieses Vorgangs
	  */
	@Override
	public void setC_PaymentTerm_ID (int C_PaymentTerm_ID)
	{
		if (C_PaymentTerm_ID < 1) 
			set_Value (COLUMNNAME_C_PaymentTerm_ID, null);
		else 
			set_Value (COLUMNNAME_C_PaymentTerm_ID, Integer.valueOf(C_PaymentTerm_ID));
	}

	/** Get Zahlungsbedingung.
		@return Die Bedingungen für die Bezahlung dieses Vorgangs
	  */
	@Override
	public int getC_PaymentTerm_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_PaymentTerm_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.banking.model.I_C_RecurrentPayment getC_RecurrentPayment()
	{
		return get_ValueAsPO(COLUMNNAME_C_RecurrentPayment_ID, de.metas.banking.model.I_C_RecurrentPayment.class);
	}

	@Override
	public void setC_RecurrentPayment(de.metas.banking.model.I_C_RecurrentPayment C_RecurrentPayment)
	{
		set_ValueFromPO(COLUMNNAME_C_RecurrentPayment_ID, de.metas.banking.model.I_C_RecurrentPayment.class, C_RecurrentPayment);
	}

	/** Set Recurrent Payment.
		@param C_RecurrentPayment_ID Recurrent Payment	  */
	@Override
	public void setC_RecurrentPayment_ID (int C_RecurrentPayment_ID)
	{
		if (C_RecurrentPayment_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_RecurrentPayment_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_RecurrentPayment_ID, Integer.valueOf(C_RecurrentPayment_ID));
	}

	/** Get Recurrent Payment.
		@return Recurrent Payment	  */
	@Override
	public int getC_RecurrentPayment_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_RecurrentPayment_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Recurrent Payment Line.
		@param C_RecurrentPaymentLine_ID Recurrent Payment Line	  */
	@Override
	public void setC_RecurrentPaymentLine_ID (int C_RecurrentPaymentLine_ID)
	{
		if (C_RecurrentPaymentLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_RecurrentPaymentLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_RecurrentPaymentLine_ID, Integer.valueOf(C_RecurrentPaymentLine_ID));
	}

	/** Get Recurrent Payment Line.
		@return Recurrent Payment Line	  */
	@Override
	public int getC_RecurrentPaymentLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_RecurrentPaymentLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Date From.
		@param DateFrom 
		Starting date for a range
	  */
	@Override
	public void setDateFrom (java.sql.Timestamp DateFrom)
	{
		set_Value (COLUMNNAME_DateFrom, DateFrom);
	}

	/** Get Date From.
		@return Starting date for a range
	  */
	@Override
	public java.sql.Timestamp getDateFrom () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateFrom);
	}

	/** Set Date To.
		@param DateTo 
		End date of a date range
	  */
	@Override
	public void setDateTo (java.sql.Timestamp DateTo)
	{
		set_Value (COLUMNNAME_DateTo, DateTo);
	}

	/** Get Date To.
		@return End date of a date range
	  */
	@Override
	public java.sql.Timestamp getDateTo () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateTo);
	}

	/** Set Beschreibung.
		@param Description Beschreibung	  */
	@Override
	public void setDescription (java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Beschreibung.
		@return Beschreibung	  */
	@Override
	public java.lang.String getDescription () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	/** Set Frequency.
		@param Frequency 
		Frequency of events
	  */
	@Override
	public void setFrequency (int Frequency)
	{
		set_Value (COLUMNNAME_Frequency, Integer.valueOf(Frequency));
	}

	/** Get Frequency.
		@return Frequency of events
	  */
	@Override
	public int getFrequency () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Frequency);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * FrequencyType AD_Reference_ID=540067
	 * Reference name: Recurrent Payment Frequency Type
	 */
	public static final int FREQUENCYTYPE_AD_Reference_ID=540067;
	/** Day = D */
	public static final String FREQUENCYTYPE_Day = "D";
	/** Month = M */
	public static final String FREQUENCYTYPE_Month = "M";
	/** Set Frequency Type.
		@param FrequencyType 
		Frequency of event
	  */
	@Override
	public void setFrequencyType (java.lang.String FrequencyType)
	{

		set_Value (COLUMNNAME_FrequencyType, FrequencyType);
	}

	/** Get Frequency Type.
		@return Frequency of event
	  */
	@Override
	public java.lang.String getFrequencyType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_FrequencyType);
	}

	/** Set Next Payment Date.
		@param NextPaymentDate Next Payment Date	  */
	@Override
	public void setNextPaymentDate (java.sql.Timestamp NextPaymentDate)
	{
		set_ValueNoCheck (COLUMNNAME_NextPaymentDate, NextPaymentDate);
	}

	/** Get Next Payment Date.
		@return Next Payment Date	  */
	@Override
	public java.sql.Timestamp getNextPaymentDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_NextPaymentDate);
	}

	/** Set Payment amount.
		@param PayAmt 
		Amount being paid
	  */
	@Override
	public void setPayAmt (java.math.BigDecimal PayAmt)
	{
		set_Value (COLUMNNAME_PayAmt, PayAmt);
	}

	/** Get Payment amount.
		@return Amount being paid
	  */
	@Override
	public java.math.BigDecimal getPayAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PayAmt);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Aussendienst.
		@param SalesRep_ID Aussendienst	  */
	@Override
	public void setSalesRep_ID (int SalesRep_ID)
	{
		if (SalesRep_ID < 1) 
			set_Value (COLUMNNAME_SalesRep_ID, null);
		else 
			set_Value (COLUMNNAME_SalesRep_ID, Integer.valueOf(SalesRep_ID));
	}

	/** Get Aussendienst.
		@return Aussendienst	  */
	@Override
	public int getSalesRep_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SalesRep_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}