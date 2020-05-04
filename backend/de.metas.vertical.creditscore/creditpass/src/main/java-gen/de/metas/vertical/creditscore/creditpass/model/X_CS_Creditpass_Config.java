/** Generated Model - DO NOT CHANGE */
package de.metas.vertical.creditscore.creditpass.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for CS_Creditpass_Config
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_CS_Creditpass_Config extends org.compiere.model.PO implements I_CS_Creditpass_Config, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -235112829L;

    /** Standard Constructor */
    public X_CS_Creditpass_Config (Properties ctx, int CS_Creditpass_Config_ID, String trxName)
    {
      super (ctx, CS_Creditpass_Config_ID, trxName);
      /** if (CS_Creditpass_Config_ID == 0)
        {
			setAuthId (null);
			setCS_Creditpass_Config_ID (0);
			setDefaultCheckResult (null);
			setManual_Check_User_ID (0);
			setPassword (null);
			setRequestReason (null); // ABK
			setRestApiBaseURL (null);
			setRetryAfterDays (BigDecimal.ZERO); // 0
        } */
    }

    /** Load Constructor */
    public X_CS_Creditpass_Config (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Berechtigungs ID.
		@param AuthId Berechtigungs ID	  */
	@Override
	public void setAuthId (java.lang.String AuthId)
	{
		set_Value (COLUMNNAME_AuthId, AuthId);
	}

	/** Get Berechtigungs ID.
		@return Berechtigungs ID	  */
	@Override
	public java.lang.String getAuthId () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AuthId);
	}

	/** Get Creditpass Einstellung.
		@return Creditpass Einstellung	  */
	@Override
	public int getCS_Creditpass_Config_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CS_Creditpass_Config_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Creditpass Einstellung.
		@param CS_Creditpass_Config_ID Creditpass Einstellung	  */
	@Override
	public void setCS_Creditpass_Config_ID (int CS_Creditpass_Config_ID)
	{
		if (CS_Creditpass_Config_ID < 1)
			set_ValueNoCheck (COLUMNNAME_CS_Creditpass_Config_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_CS_Creditpass_Config_ID, Integer.valueOf(CS_Creditpass_Config_ID));
	}

	/** 
	 * DefaultCheckResult AD_Reference_ID=540961
	 * Reference name: _CreditPassResult
	 */
	public static final int DEFAULTCHECKRESULT_AD_Reference_ID=540961;
	/** Nicht authorisiert = N */
	public static final String DEFAULTCHECKRESULT_NichtAuthorisiert = "N";
	/** Authorisiert = P */
	public static final String DEFAULTCHECKRESULT_Authorisiert = "P";
	/** Fehler = E */
	public static final String DEFAULTCHECKRESULT_Fehler = "E";
	/** Manuell überprüfen = M */
	public static final String DEFAULTCHECKRESULT_Manuellueberpruefen = "M";
	/** Set Standard Ergebnis.
		@param DefaultCheckResult 
		Bestellungen wie ausgewählt abschließen
	  */
	@Override
	public void setDefaultCheckResult (java.lang.String DefaultCheckResult)
	{

		set_Value (COLUMNNAME_DefaultCheckResult, DefaultCheckResult);
	}

	/** Get Standard Ergebnis.
		@return Bestellungen wie ausgewählt abschließen
	  */
	@Override
	public java.lang.String getDefaultCheckResult () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DefaultCheckResult);
	}

	@Override
	public org.compiere.model.I_AD_User getManual_Check_User() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_Manual_Check_User_ID, org.compiere.model.I_AD_User.class);
	}

	@Override
	public void setManual_Check_User(org.compiere.model.I_AD_User Manual_Check_User)
	{
		set_ValueFromPO(COLUMNNAME_Manual_Check_User_ID, org.compiere.model.I_AD_User.class, Manual_Check_User);
	}

	/** Set Benutzer für manuelle Prüfung.
		@param Manual_Check_User_ID 
		Benutzer für die Benachrichtigung zur manuellen Prüfung
	  */
	@Override
	public void setManual_Check_User_ID (int Manual_Check_User_ID)
	{
		if (Manual_Check_User_ID < 1) 
			set_Value (COLUMNNAME_Manual_Check_User_ID, null);
		else 
			set_Value (COLUMNNAME_Manual_Check_User_ID, Integer.valueOf(Manual_Check_User_ID));
	}

	/** Get Benutzer für manuelle Prüfung.
		@return Benutzer für die Benachrichtigung zur manuellen Prüfung
	  */
	@Override
	public int getManual_Check_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Manual_Check_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Kennwort.
		@param Password Kennwort	  */
	@Override
	public void setPassword (java.lang.String Password)
	{
		set_Value (COLUMNNAME_Password, Password);
	}

	/** Get Kennwort.
		@return Kennwort	  */
	@Override
	public java.lang.String getPassword () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Password);
	}

	/** Set Grund der Anfrage.
		@param RequestReason Grund der Anfrage	  */
	@Override
	public void setRequestReason (java.lang.String RequestReason)
	{
		set_Value (COLUMNNAME_RequestReason, RequestReason);
	}

	/** Get Grund der Anfrage.
		@return Grund der Anfrage	  */
	@Override
	public java.lang.String getRequestReason () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_RequestReason);
	}

	/** Set REST API URL.
		@param RestApiBaseURL REST API URL	  */
	@Override
	public void setRestApiBaseURL (java.lang.String RestApiBaseURL)
	{
		set_Value (COLUMNNAME_RestApiBaseURL, RestApiBaseURL);
	}

	/** Get REST API URL.
		@return REST API URL	  */
	@Override
	public java.lang.String getRestApiBaseURL () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_RestApiBaseURL);
	}

	/** Set Creditpass-Prüfung wiederholen .
		@param RetryAfterDays Creditpass-Prüfung wiederholen 	  */
	@Override
	public void setRetryAfterDays (java.math.BigDecimal RetryAfterDays)
	{
		set_Value (COLUMNNAME_RetryAfterDays, RetryAfterDays);
	}

	/** Get Creditpass-Prüfung wiederholen .
		@return Creditpass-Prüfung wiederholen 	  */
	@Override
	public java.math.BigDecimal getRetryAfterDays () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_RetryAfterDays);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Get Reihenfolge.
		@return Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst
	  */
	@Override
	public int getSeqNo ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Reihenfolge.
		@param SeqNo
		Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst
	  */
	@Override
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}
}
