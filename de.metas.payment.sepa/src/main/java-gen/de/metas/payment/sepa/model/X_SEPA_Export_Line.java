/** Generated Model - DO NOT CHANGE */
package de.metas.payment.sepa.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for SEPA_Export_Line
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_SEPA_Export_Line extends org.compiere.model.PO implements I_SEPA_Export_Line, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 298725425L;

    /** Standard Constructor */
    public X_SEPA_Export_Line (Properties ctx, int SEPA_Export_Line_ID, String trxName)
    {
      super (ctx, SEPA_Export_Line_ID, trxName);
      /** if (SEPA_Export_Line_ID == 0)
        {
			setAD_Table_ID (0);
			setAmt (BigDecimal.ZERO);
			setC_BPartner_ID (0);
			setC_Currency_ID (0);
			setRecord_ID (0);
			setSEPA_Export_ID (0);
			setSEPA_Export_Line_ID (0);
        } */
    }

    /** Load Constructor */
    public X_SEPA_Export_Line (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_Table getAD_Table() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Table_ID, org.compiere.model.I_AD_Table.class);
	}

	@Override
	public void setAD_Table(org.compiere.model.I_AD_Table AD_Table)
	{
		set_ValueFromPO(COLUMNNAME_AD_Table_ID, org.compiere.model.I_AD_Table.class, AD_Table);
	}

	/** Set DB-Tabelle.
		@param AD_Table_ID 
		Database Table information
	  */
	@Override
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get DB-Tabelle.
		@return Database Table information
	  */
	@Override
	public int getAD_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Betrag.
		@param Amt 
		Betrag
	  */
	@Override
	public void setAmt (java.math.BigDecimal Amt)
	{
		set_Value (COLUMNNAME_Amt, Amt);
	}

	/** Get Betrag.
		@return Betrag
	  */
	@Override
	public java.math.BigDecimal getAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amt);
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

	@Override
	public org.compiere.model.I_C_BP_BankAccount getC_BP_BankAccount() throws RuntimeException
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
		Bankverbindung des Geschäftspartners
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
		@return Bankverbindung des Geschäftspartners
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
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

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

	/** Set Fehlermeldung.
		@param ErrorMsg Fehlermeldung	  */
	@Override
	public void setErrorMsg (java.lang.String ErrorMsg)
	{
		set_Value (COLUMNNAME_ErrorMsg, ErrorMsg);
	}

	/** Get Fehlermeldung.
		@return Fehlermeldung	  */
	@Override
	public java.lang.String getErrorMsg () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ErrorMsg);
	}

	/** Set IBAN.
		@param IBAN 
		International Bank Account Number
	  */
	@Override
	public void setIBAN (java.lang.String IBAN)
	{
		set_Value (COLUMNNAME_IBAN, IBAN);
	}

	/** Get IBAN.
		@return International Bank Account Number
	  */
	@Override
	public java.lang.String getIBAN () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_IBAN);
	}

	/** Set Fehler.
		@param IsError 
		Ein Fehler ist bei der Durchführung aufgetreten
	  */
	@Override
	public void setIsError (boolean IsError)
	{
		set_Value (COLUMNNAME_IsError, Boolean.valueOf(IsError));
	}

	/** Get Fehler.
		@return Ein Fehler ist bei der Durchführung aufgetreten
	  */
	@Override
	public boolean isError () 
	{
		Object oo = get_Value(COLUMNNAME_IsError);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set OtherAccountIdentification.
		@param OtherAccountIdentification 
		Alternative account ID
	  */
	@Override
	public void setOtherAccountIdentification (java.lang.String OtherAccountIdentification)
	{
		set_Value (COLUMNNAME_OtherAccountIdentification, OtherAccountIdentification);
	}

	/** Get OtherAccountIdentification.
		@return Alternative account ID
	  */
	@Override
	public java.lang.String getOtherAccountIdentification () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_OtherAccountIdentification);
	}

	/** Set Datensatz-ID.
		@param Record_ID 
		Direct internal record ID
	  */
	@Override
	public void setRecord_ID (int Record_ID)
	{
		if (Record_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_Record_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
	}

	/** Get Datensatz-ID.
		@return Direct internal record ID
	  */
	@Override
	public int getRecord_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Record_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.payment.sepa.model.I_SEPA_Export getSEPA_Export() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_SEPA_Export_ID, de.metas.payment.sepa.model.I_SEPA_Export.class);
	}

	@Override
	public void setSEPA_Export(de.metas.payment.sepa.model.I_SEPA_Export SEPA_Export)
	{
		set_ValueFromPO(COLUMNNAME_SEPA_Export_ID, de.metas.payment.sepa.model.I_SEPA_Export.class, SEPA_Export);
	}

	/** Set SEPA Export.
		@param SEPA_Export_ID SEPA Export	  */
	@Override
	public void setSEPA_Export_ID (int SEPA_Export_ID)
	{
		if (SEPA_Export_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_SEPA_Export_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_SEPA_Export_ID, Integer.valueOf(SEPA_Export_ID));
	}

	/** Get SEPA Export.
		@return SEPA Export	  */
	@Override
	public int getSEPA_Export_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SEPA_Export_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set SEPA_Export_Line_ID.
		@param SEPA_Export_Line_ID SEPA_Export_Line_ID	  */
	@Override
	public void setSEPA_Export_Line_ID (int SEPA_Export_Line_ID)
	{
		if (SEPA_Export_Line_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_SEPA_Export_Line_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_SEPA_Export_Line_ID, Integer.valueOf(SEPA_Export_Line_ID));
	}

	/** Get SEPA_Export_Line_ID.
		@return SEPA_Export_Line_ID	  */
	@Override
	public int getSEPA_Export_Line_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SEPA_Export_Line_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.payment.sepa.model.I_SEPA_Export_Line getSEPA_Export_Line_Retry() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_SEPA_Export_Line_Retry_ID, de.metas.payment.sepa.model.I_SEPA_Export_Line.class);
	}

	@Override
	public void setSEPA_Export_Line_Retry(de.metas.payment.sepa.model.I_SEPA_Export_Line SEPA_Export_Line_Retry)
	{
		set_ValueFromPO(COLUMNNAME_SEPA_Export_Line_Retry_ID, de.metas.payment.sepa.model.I_SEPA_Export_Line.class, SEPA_Export_Line_Retry);
	}

	/** Set SEPA_Export_Line_Retry_ID.
		@param SEPA_Export_Line_Retry_ID SEPA_Export_Line_Retry_ID	  */
	@Override
	public void setSEPA_Export_Line_Retry_ID (int SEPA_Export_Line_Retry_ID)
	{
		if (SEPA_Export_Line_Retry_ID < 1) 
			set_Value (COLUMNNAME_SEPA_Export_Line_Retry_ID, null);
		else 
			set_Value (COLUMNNAME_SEPA_Export_Line_Retry_ID, Integer.valueOf(SEPA_Export_Line_Retry_ID));
	}

	/** Get SEPA_Export_Line_Retry_ID.
		@return SEPA_Export_Line_Retry_ID	  */
	@Override
	public int getSEPA_Export_Line_Retry_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SEPA_Export_Line_Retry_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set SEPA_MandateRefNo.
		@param SEPA_MandateRefNo SEPA_MandateRefNo	  */
	@Override
	public void setSEPA_MandateRefNo (java.lang.String SEPA_MandateRefNo)
	{
		set_Value (COLUMNNAME_SEPA_MandateRefNo, SEPA_MandateRefNo);
	}

	/** Get SEPA_MandateRefNo.
		@return SEPA_MandateRefNo	  */
	@Override
	public java.lang.String getSEPA_MandateRefNo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_SEPA_MandateRefNo);
	}

	/** Set StructuredRemittanceInfo.
		@param StructuredRemittanceInfo 
		Structured Remittance Information
	  */
	@Override
	public void setStructuredRemittanceInfo (java.lang.String StructuredRemittanceInfo)
	{
		set_Value (COLUMNNAME_StructuredRemittanceInfo, StructuredRemittanceInfo);
	}

	/** Get StructuredRemittanceInfo.
		@return Structured Remittance Information
	  */
	@Override
	public java.lang.String getStructuredRemittanceInfo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_StructuredRemittanceInfo);
	}

	/** Set Swift code.
		@param SwiftCode 
		Swift Code or BIC
	  */
	@Override
	public void setSwiftCode (java.lang.String SwiftCode)
	{
		set_Value (COLUMNNAME_SwiftCode, SwiftCode);
	}

	/** Get Swift code.
		@return Swift Code or BIC
	  */
	@Override
	public java.lang.String getSwiftCode () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_SwiftCode);
	}
}