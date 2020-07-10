/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_BPartner_CreditLimit
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_BPartner_CreditLimit extends org.compiere.model.PO implements I_C_BPartner_CreditLimit, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1332095921L;

    /** Standard Constructor */
    public X_C_BPartner_CreditLimit (Properties ctx, int C_BPartner_CreditLimit_ID, String trxName)
    {
      super (ctx, C_BPartner_CreditLimit_ID, trxName);
      /** if (C_BPartner_CreditLimit_ID == 0)
        {
			setAmount (BigDecimal.ZERO);
			setC_BPartner_CreditLimit_ID (0);
			setC_CreditLimit_Type_ID (0);
			setProcessed (false); // N
        } */
    }

    /** Load Constructor */
    public X_C_BPartner_CreditLimit (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Betrag.
		@param Amount 
		Betrag in einer definierten Währung
	  */
	@Override
	public void setAmount (java.math.BigDecimal Amount)
	{
		set_Value (COLUMNNAME_Amount, Amount);
	}

	/** Get Betrag.
		@return Betrag in einer definierten Währung
	  */
	@Override
	public java.math.BigDecimal getAmount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amount);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	@Override
	public org.compiere.model.I_AD_User getApprovedBy() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_ApprovedBy_ID, org.compiere.model.I_AD_User.class);
	}

	@Override
	public void setApprovedBy(org.compiere.model.I_AD_User ApprovedBy)
	{
		set_ValueFromPO(COLUMNNAME_ApprovedBy_ID, org.compiere.model.I_AD_User.class, ApprovedBy);
	}

	/** Set Approved By.
		@param ApprovedBy_ID Approved By	  */
	@Override
	public void setApprovedBy_ID (int ApprovedBy_ID)
	{
		if (ApprovedBy_ID < 1) 
			set_Value (COLUMNNAME_ApprovedBy_ID, null);
		else 
			set_Value (COLUMNNAME_ApprovedBy_ID, Integer.valueOf(ApprovedBy_ID));
	}

	/** Get Approved By.
		@return Approved By	  */
	@Override
	public int getApprovedBy_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ApprovedBy_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Bussines Partner Credit Limit.
		@param C_BPartner_CreditLimit_ID Bussines Partner Credit Limit	  */
	@Override
	public void setC_BPartner_CreditLimit_ID (int C_BPartner_CreditLimit_ID)
	{
		if (C_BPartner_CreditLimit_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_CreditLimit_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_CreditLimit_ID, Integer.valueOf(C_BPartner_CreditLimit_ID));
	}

	/** Get Bussines Partner Credit Limit.
		@return Bussines Partner Credit Limit	  */
	@Override
	public int getC_BPartner_CreditLimit_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_CreditLimit_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
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

	@Override
	public org.compiere.model.I_C_CreditLimit_Type getC_CreditLimit_Type() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_CreditLimit_Type_ID, org.compiere.model.I_C_CreditLimit_Type.class);
	}

	@Override
	public void setC_CreditLimit_Type(org.compiere.model.I_C_CreditLimit_Type C_CreditLimit_Type)
	{
		set_ValueFromPO(COLUMNNAME_C_CreditLimit_Type_ID, org.compiere.model.I_C_CreditLimit_Type.class, C_CreditLimit_Type);
	}

	/** Set Credit Limit Type.
		@param C_CreditLimit_Type_ID Credit Limit Type	  */
	@Override
	public void setC_CreditLimit_Type_ID (int C_CreditLimit_Type_ID)
	{
		if (C_CreditLimit_Type_ID < 1) 
			set_Value (COLUMNNAME_C_CreditLimit_Type_ID, null);
		else 
			set_Value (COLUMNNAME_C_CreditLimit_Type_ID, Integer.valueOf(C_CreditLimit_Type_ID));
	}

	/** Get Credit Limit Type.
		@return Credit Limit Type	  */
	@Override
	public int getC_CreditLimit_Type_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_CreditLimit_Type_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Currency getC_Currency() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Currency_ID, org.compiere.model.I_C_Currency.class);
	}

	@Override
	public void setC_Currency(org.compiere.model.I_C_Currency C_Currency)
	{
		set_ValueFromPO(COLUMNNAME_C_Currency_ID, org.compiere.model.I_C_Currency.class, C_Currency);
	}

	/** Set Währung.
		@param C_Currency_ID 
		Die Währung für diesen Eintrag
	  */
	@Override
	public void setC_Currency_ID (int C_Currency_ID)
	{
		throw new IllegalArgumentException ("C_Currency_ID is virtual column");	}

	/** Get Währung.
		@return Die Währung für diesen Eintrag
	  */
	@Override
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Datum von.
		@param DateFrom 
		Startdatum eines Abschnittes
	  */
	@Override
	public void setDateFrom (java.sql.Timestamp DateFrom)
	{
		set_Value (COLUMNNAME_DateFrom, DateFrom);
	}

	/** Get Datum von.
		@return Startdatum eines Abschnittes
	  */
	@Override
	public java.sql.Timestamp getDateFrom () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateFrom);
	}

	/** Set Freigegeben.
		@param Processed 
		Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Freigegeben.
		@return Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
}