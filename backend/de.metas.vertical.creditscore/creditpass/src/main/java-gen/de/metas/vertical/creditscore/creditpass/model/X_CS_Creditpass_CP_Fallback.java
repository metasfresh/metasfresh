/** Generated Model - DO NOT CHANGE */
package de.metas.vertical.creditscore.creditpass.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for CS_Creditpass_CP_Fallback
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_CS_Creditpass_CP_Fallback extends org.compiere.model.PO implements I_CS_Creditpass_CP_Fallback, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1077950722L;

    /** Standard Constructor */
    public X_CS_Creditpass_CP_Fallback (Properties ctx, int CS_Creditpass_CP_Fallback_ID, String trxName)
    {
      super (ctx, CS_Creditpass_CP_Fallback_ID, trxName);
      /** if (CS_Creditpass_CP_Fallback_ID == 0)
        {
			setCS_Creditpass_Config_PaymentRule_ID (0);
			setCS_Creditpass_CP_Fallback_ID (0);
        } */
    }

    /** Load Constructor */
    public X_CS_Creditpass_CP_Fallback (Properties ctx, ResultSet rs, String trxName)
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
	public de.metas.vertical.creditscore.creditpass.model.I_CS_Creditpass_Config_PaymentRule getCS_Creditpass_Config_PaymentRule() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_CS_Creditpass_Config_PaymentRule_ID, de.metas.vertical.creditscore.creditpass.model.I_CS_Creditpass_Config_PaymentRule.class);
	}

	@Override
	public void setCS_Creditpass_Config_PaymentRule(de.metas.vertical.creditscore.creditpass.model.I_CS_Creditpass_Config_PaymentRule CS_Creditpass_Config_PaymentRule)
	{
		set_ValueFromPO(COLUMNNAME_CS_Creditpass_Config_PaymentRule_ID, de.metas.vertical.creditscore.creditpass.model.I_CS_Creditpass_Config_PaymentRule.class, CS_Creditpass_Config_PaymentRule);
	}

	/** Get Zahlungsart ID.
		@return Zahlungsart ID	  */
	@Override
	public int getCS_Creditpass_Config_PaymentRule_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CS_Creditpass_Config_PaymentRule_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Zahlungsart ID.
		@param CS_Creditpass_Config_PaymentRule_ID Zahlungsart ID	  */
	@Override
	public void setCS_Creditpass_Config_PaymentRule_ID (int CS_Creditpass_Config_PaymentRule_ID)
	{
		if (CS_Creditpass_Config_PaymentRule_ID < 1)
			set_ValueNoCheck (COLUMNNAME_CS_Creditpass_Config_PaymentRule_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_CS_Creditpass_Config_PaymentRule_ID, Integer.valueOf(CS_Creditpass_Config_PaymentRule_ID));
	}

	/** Set CS Creditpass Configuration payment rule fallback.
		@param CS_Creditpass_CP_Fallback_ID CS Creditpass Configuration payment rule fallback	  */
	@Override
	public void setCS_Creditpass_CP_Fallback_ID (int CS_Creditpass_CP_Fallback_ID)
	{
		if (CS_Creditpass_CP_Fallback_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_CS_Creditpass_CP_Fallback_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_CS_Creditpass_CP_Fallback_ID, Integer.valueOf(CS_Creditpass_CP_Fallback_ID));
	}

	/** Get CS Creditpass Configuration payment rule fallback.
		@return CS Creditpass Configuration payment rule fallback	  */
	@Override
	public int getCS_Creditpass_CP_Fallback_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CS_Creditpass_CP_Fallback_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * FallbackPaymentRule AD_Reference_ID=195
	 * Reference name: _Payment Rule
	 */
	public static final int FALLBACKPAYMENTRULE_AD_Reference_ID=195;
	/** Cash = B */
	public static final String FALLBACKPAYMENTRULE_Cash = "B";
	/** CreditCard = K */
	public static final String FALLBACKPAYMENTRULE_CreditCard = "K";
	/** DirectDeposit = T */
	public static final String FALLBACKPAYMENTRULE_DirectDeposit = "T";
	/** Check = S */
	public static final String FALLBACKPAYMENTRULE_Check = "S";
	/** OnCredit = P */
	public static final String FALLBACKPAYMENTRULE_OnCredit = "P";
	/** DirectDebit = D */
	public static final String FALLBACKPAYMENTRULE_DirectDebit = "D";
	/** Mixed = M */
	public static final String FALLBACKPAYMENTRULE_Mixed = "M";
	/** Rückerstattung = E */
	public static final String PAYMENTRULE_Reimbursement = "E";
	/** Verrechnung = F */
	public static final String PAYMENTRULE_Settlement = "F";
	/** Set Zahlart Rückgriff.
		@param FallbackPaymentRule Zahlart Rückgriff	  */
	@Override
	public void setFallbackPaymentRule (java.lang.String FallbackPaymentRule)
	{

		set_Value (COLUMNNAME_FallbackPaymentRule, FallbackPaymentRule);
	}

	/** Get Zahlart Rückgriff.
		@return Zahlart Rückgriff	  */
	@Override
	public java.lang.String getFallbackPaymentRule () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_FallbackPaymentRule);
	}
}
