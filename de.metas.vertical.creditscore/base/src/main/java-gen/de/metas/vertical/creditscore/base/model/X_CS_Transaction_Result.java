/** Generated Model - DO NOT CHANGE */
package de.metas.vertical.creditscore.base.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for CS_Transaction_Result
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_CS_Transaction_Result extends org.compiere.model.PO implements I_CS_Transaction_Result, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -66188413L;

    /** Standard Constructor */
    public X_CS_Transaction_Result (Properties ctx, int CS_Transaction_Result_ID, String trxName)
    {
      super (ctx, CS_Transaction_Result_ID, trxName);
      /** if (CS_Transaction_Result_ID == 0)
        {
			setCS_Transaction_Result_ID (0);
			setPaymentRule (null);
			setRequestEndTime (new Timestamp( System.currentTimeMillis() ));
			setRequestStartTime (new Timestamp( System.currentTimeMillis() ));
			setResponseCode (null);
        } */
    }

    /** Load Constructor */
    public X_CS_Transaction_Result (Properties ctx, ResultSet rs, String trxName)
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
		{
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		}
		else
		{
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
		}
	}

	/** Get Geschäftspartner.
		@return Bezeichnet einen Geschäftspartner
	  */
	@Override
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
		{
			return 0;
		}
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
	public org.compiere.model.I_C_Order getC_Order() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
	public void setC_Order(org.compiere.model.I_C_Order C_Order)
	{
		set_ValueFromPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class, C_Order);
	}

	/** Set Auftrag.
		@param C_Order_ID 
		Auftrag
	  */
	@Override
	public void setC_Order_ID (int C_Order_ID)
	{
		if (C_Order_ID < 1)
		{
			set_Value (COLUMNNAME_C_Order_ID, null);
		}
		else
		{
			set_Value (COLUMNNAME_C_Order_ID, Integer.valueOf(C_Order_ID));
		}
	}

	/** Get Auftrag.
		@return Auftrag
	  */
	@Override
	public int getC_Order_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Order_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	/** Set CS_Transaction_Result.
		@param CS_Transaction_Result_ID CS_Transaction_Result	  */
	@Override
	public void setCS_Transaction_Result_ID (int CS_Transaction_Result_ID)
	{
		if (CS_Transaction_Result_ID < 1)
		{
			set_ValueNoCheck (COLUMNNAME_CS_Transaction_Result_ID, null);
		}
		else
		{
			set_ValueNoCheck (COLUMNNAME_CS_Transaction_Result_ID, Integer.valueOf(CS_Transaction_Result_ID));
		}
	}

	/** Get CS_Transaction_Result.
		@return CS_Transaction_Result	  */
	@Override
	public int getCS_Transaction_Result_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CS_Transaction_Result_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
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

	/** Set Anfrage Ende.
		@param RequestEndTime Anfrage Ende	  */
	@Override
	public void setRequestEndTime (java.sql.Timestamp RequestEndTime)
	{
		set_ValueNoCheck (COLUMNNAME_RequestEndTime, RequestEndTime);
	}

	/** Get Anfrage Ende.
		@return Anfrage Ende	  */
	@Override
	public java.sql.Timestamp getRequestEndTime () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_RequestEndTime);
	}

	/** Set Preis der Überprüfung.
		@param RequestPrice Preis der Überprüfung	  */
	@Override
	public void setRequestPrice (java.math.BigDecimal RequestPrice)
	{
		set_Value (COLUMNNAME_RequestPrice, RequestPrice);
	}

	/** Get Preis der Überprüfung.
		@return Preis der Überprüfung	  */
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

	/** Set Anfrage Start .
		@param RequestStartTime Anfrage Start 	  */
	@Override
	public void setRequestStartTime (java.sql.Timestamp RequestStartTime)
	{
		set_ValueNoCheck (COLUMNNAME_RequestStartTime, RequestStartTime);
	}

	/** Get Anfrage Start .
		@return Anfrage Start 	  */
	@Override
	public java.sql.Timestamp getRequestStartTime () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_RequestStartTime);
	}

	/** 
	 * ResponseCode AD_Reference_ID=540961
	 * Reference name: _CreditPassResult
	 */
	public static final int RESPONSECODE_AD_Reference_ID=540961;
	/** Nicht authorisiert = N */
	public static final String RESPONSECODE_NichtAuthorisiert = "N";
	/** Authorisiert = P */
	public static final String RESPONSECODE_Authorisiert = "P";
	/** Fehler = E */
	public static final String RESPONSECODE_Fehler = "E";
	/** Manuell überprüfen = M */
	public static final String RESPONSECODE_Manuellueberpruefen = "M";
	/** Set Antwort .
		@param ResponseCode Antwort 	  */
	@Override
	public void setResponseCode (java.lang.String ResponseCode)
	{

		set_ValueNoCheck (COLUMNNAME_ResponseCode, ResponseCode);
	}

	/** Get Antwort .
		@return Antwort 	  */
	@Override
	public java.lang.String getResponseCode () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ResponseCode);
	}

	/** 
	 * ResponseCodeEffective AD_Reference_ID=540961
	 * Reference name: _CreditPassResult
	 */
	public static final int RESPONSECODEEFFECTIVE_AD_Reference_ID=540961;
	/** Nicht authorisiert = N */
	public static final String RESPONSECODEEFFECTIVE_NichtAuthorisiert = "N";
	/** Authorisiert = P */
	public static final String RESPONSECODEEFFECTIVE_Authorisiert = "P";
	/** Fehler = E */
	public static final String RESPONSECODEEFFECTIVE_Fehler = "E";
	/** Manuell überprüfen = M */
	public static final String RESPONSECODEEFFECTIVE_Manuellueberpruefen = "M";
	/** Set Antwort eff..
		@param ResponseCodeEffective Antwort eff.	  */
	@Override
	public void setResponseCodeEffective (java.lang.String ResponseCodeEffective)
	{

		set_ValueNoCheck (COLUMNNAME_ResponseCodeEffective, ResponseCodeEffective);
	}

	/** Get Antwort eff..
		@return Antwort eff.	  */
	@Override
	public java.lang.String getResponseCodeEffective () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ResponseCodeEffective);
	}

	/** 
	 * ResponseCodeOverride AD_Reference_ID=540961
	 * Reference name: _CreditPassResult
	 */
	public static final int RESPONSECODEOVERRIDE_AD_Reference_ID=540961;
	/** Nicht authorisiert = N */
	public static final String RESPONSECODEOVERRIDE_NichtAuthorisiert = "N";
	/** Authorisiert = P */
	public static final String RESPONSECODEOVERRIDE_Authorisiert = "P";
	/** Fehler = E */
	public static final String RESPONSECODEOVERRIDE_Fehler = "E";
	/** Manuell überprüfen = M */
	public static final String RESPONSECODEOVERRIDE_Manuellueberpruefen = "M";
	/** Set Antwort abw..
		@param ResponseCodeOverride Antwort abw.	  */
	@Override
	public void setResponseCodeOverride (java.lang.String ResponseCodeOverride)
	{

		set_ValueNoCheck (COLUMNNAME_ResponseCodeOverride, ResponseCodeOverride);
	}

	/** Get Antwort abw..
		@return Antwort abw.	  */
	@Override
	public java.lang.String getResponseCodeOverride () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ResponseCodeOverride);
	}

	/** Set Antwort Text.
		@param ResponseCodeText Antwort Text	  */
	@Override
	public void setResponseCodeText (java.lang.String ResponseCodeText)
	{
		set_Value (COLUMNNAME_ResponseCodeText, ResponseCodeText);
	}

	/** Get Antwort Text.
		@return Antwort Text	  */
	@Override
	public java.lang.String getResponseCodeText () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ResponseCodeText);
	}

	/** Set Antwort Details.
		@param ResponseDetails Antwort Details	  */
	@Override
	public void setResponseDetails (java.lang.String ResponseDetails)
	{
		set_ValueNoCheck (COLUMNNAME_ResponseDetails, ResponseDetails);
	}

	/** Get Antwort Details.
		@return Antwort Details	  */
	@Override
	public java.lang.String getResponseDetails () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ResponseDetails);
	}

	/** Set Transaktionsreferenz Kunde .
		@param TransactionCustomerId Transaktionsreferenz Kunde 	  */
	@Override
	public void setTransactionCustomerId (java.lang.String TransactionCustomerId)
	{
		set_ValueNoCheck (COLUMNNAME_TransactionCustomerId, TransactionCustomerId);
	}

	/** Get Transaktionsreferenz Kunde .
		@return Transaktionsreferenz Kunde 	  */
	@Override
	public java.lang.String getTransactionCustomerId () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_TransactionCustomerId);
	}

	/** Set Transaktionsreferenz API.
		@param TransactionIdAPI Transaktionsreferenz API	  */
	@Override
	public void setTransactionIdAPI (java.lang.String TransactionIdAPI)
	{
		set_ValueNoCheck (COLUMNNAME_TransactionIdAPI, TransactionIdAPI);
	}

	/** Get Transaktionsreferenz API.
		@return Transaktionsreferenz API	  */
	@Override
	public java.lang.String getTransactionIdAPI () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_TransactionIdAPI);
	}
}