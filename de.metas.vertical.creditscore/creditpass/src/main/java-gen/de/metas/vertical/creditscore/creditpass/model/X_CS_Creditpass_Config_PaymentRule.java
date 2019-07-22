/** Generated Model - DO NOT CHANGE */
package de.metas.vertical.creditscore.creditpass.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for CS_Creditpass_Config_PaymentRule
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_CS_Creditpass_Config_PaymentRule extends org.compiere.model.PO implements I_CS_Creditpass_Config_PaymentRule, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1313795496L;

    /** Standard Constructor */
    public X_CS_Creditpass_Config_PaymentRule (Properties ctx, int CS_Creditpass_Config_PaymentRule_ID, String trxName)
    {
      super (ctx, CS_Creditpass_Config_PaymentRule_ID, trxName);
      /** if (CS_Creditpass_Config_PaymentRule_ID == 0)
        {
			setCS_Creditpass_Config_ID (0);
			setCS_Creditpass_Config_PaymentRule_ID (0);
			setPaymentRule (null);
			setPurchaseType (BigDecimal.ZERO);
        } */
    }

    /** Load Constructor */
    public X_CS_Creditpass_Config_PaymentRule (Properties ctx, ResultSet rs, String trxName)
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
		if (C_Currency_ID < 1)
		{
			set_Value (COLUMNNAME_C_Currency_ID, null);
		}
		else
		{
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
		}
	}

	/** Get Währung.
		@return Die Währung für diesen Eintrag
	  */
	@Override
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	@Override
	public de.metas.vertical.creditscore.creditpass.model.I_CS_Creditpass_Config getCS_Creditpass_Config() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_CS_Creditpass_Config_ID, de.metas.vertical.creditscore.creditpass.model.I_CS_Creditpass_Config.class);
	}

	@Override
	public void setCS_Creditpass_Config(de.metas.vertical.creditscore.creditpass.model.I_CS_Creditpass_Config CS_Creditpass_Config)
	{
		set_ValueFromPO(COLUMNNAME_CS_Creditpass_Config_ID, de.metas.vertical.creditscore.creditpass.model.I_CS_Creditpass_Config.class, CS_Creditpass_Config);
	}

	/** Get Creditpass Einstellung.
		@return Creditpass Einstellung	  */
	@Override
	public int getCS_Creditpass_Config_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CS_Creditpass_Config_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	/** Set Creditpass Einstellung.
		@param CS_Creditpass_Config_ID Creditpass Einstellung	  */
	@Override
	public void setCS_Creditpass_Config_ID (int CS_Creditpass_Config_ID)
	{
		if (CS_Creditpass_Config_ID < 1)
		{
			set_ValueNoCheck (COLUMNNAME_CS_Creditpass_Config_ID, null);
		}
		else
		{
			set_ValueNoCheck (COLUMNNAME_CS_Creditpass_Config_ID, Integer.valueOf(CS_Creditpass_Config_ID));
		}
	}

	/** Get Zahlungsart ID.
		@return Zahlungsart ID	  */
	@Override
	public int getCS_Creditpass_Config_PaymentRule_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CS_Creditpass_Config_PaymentRule_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	/** Set Zahlungsart ID.
		@param CS_Creditpass_Config_PaymentRule_ID Zahlungsart ID	  */
	@Override
	public void setCS_Creditpass_Config_PaymentRule_ID (int CS_Creditpass_Config_PaymentRule_ID)
	{
		if (CS_Creditpass_Config_PaymentRule_ID < 1)
		{
			set_ValueNoCheck (COLUMNNAME_CS_Creditpass_Config_PaymentRule_ID, null);
		}
		else
		{
			set_ValueNoCheck (COLUMNNAME_CS_Creditpass_Config_PaymentRule_ID, Integer.valueOf(CS_Creditpass_Config_PaymentRule_ID));
		}
	}

//	/** 
//	 * PaymentRule AD_Reference_ID=195
//	 * Reference name: _Payment Rule
//	 */
//	public static final int PAYMENTRULE_AD_Reference_ID=195;
//	/** Cash = B */
//	public static final String PAYMENTRULE_Cash = "B";
//	/** CreditCard = K */
//	public static final String PAYMENTRULE_CreditCard = "K";
//	/** DirectDeposit = T */
//	public static final String PAYMENTRULE_DirectDeposit = "T";
//	/** Check = S */
//	public static final String PAYMENTRULE_Check = "S";
//	/** OnCredit = P */
//	public static final String PAYMENTRULE_OnCredit = "P";
//	/** DirectDebit = D */
//	public static final String PAYMENTRULE_DirectDebit = "D";
//	/** Mixed = M */
//	public static final String PAYMENTRULE_Mixed = "M";
	/** Set Zahlungsweise.
		@param PaymentRule 
		Wie die Rechnung bezahlt wird
	  */
	@Override
	public void setPaymentRule (java.lang.String PaymentRule)
	{

		set_Value (COLUMNNAME_PaymentRule, PaymentRule);
	}

	/** Get Zahlungsweise.
		@return Wie die Rechnung bezahlt wird
	  */
	@Override
	public java.lang.String getPaymentRule () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PaymentRule);
	}

	/** Set Kaufstyp.
		@param PurchaseType Kaufstyp	  */
	@Override
	public void setPurchaseType (java.math.BigDecimal PurchaseType)
	{
		set_Value (COLUMNNAME_PurchaseType, PurchaseType);
	}

	/** Get Kaufstyp.
		@return Kaufstyp	  */
	@Override
	public java.math.BigDecimal getPurchaseType () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PurchaseType);
		if (bd == null)
		{
			return BigDecimal.ZERO;
		}
		return bd;
	}

	/** Get Preis per Überprüfung.
		@return Preis per Überprüfung	  */
	@Override
	public java.math.BigDecimal getRequestPrice ()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_RequestPrice);
		if (bd == null)
		{
			return BigDecimal.ZERO;
		}
		return bd;
	}

	/** Set Preis per Überprüfung.
		@param RequestPrice Preis per Überprüfung	  */
	@Override
	public void setRequestPrice (java.math.BigDecimal RequestPrice)
	{
		set_Value (COLUMNNAME_RequestPrice, RequestPrice);
	}
}
