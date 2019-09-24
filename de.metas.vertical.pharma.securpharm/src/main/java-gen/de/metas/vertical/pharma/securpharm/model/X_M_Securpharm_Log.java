/** Generated Model - DO NOT CHANGE */
package de.metas.vertical.pharma.securpharm.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_Securpharm_Log
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_Securpharm_Log extends org.compiere.model.PO implements I_M_Securpharm_Log, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1445916513L;

    /** Standard Constructor */
    public X_M_Securpharm_Log (Properties ctx, int M_Securpharm_Log_ID, String trxName)
    {
      super (ctx, M_Securpharm_Log_ID, trxName);
      /** if (M_Securpharm_Log_ID == 0)
        {
			setIsError (false); // N
			setM_Securpharm_Log_ID (0);
        } */
    }

    /** Load Constructor */
    public X_M_Securpharm_Log (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_Issue getAD_Issue()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Issue_ID, org.compiere.model.I_AD_Issue.class);
	}

	@Override
	public void setAD_Issue(org.compiere.model.I_AD_Issue AD_Issue)
	{
		set_ValueFromPO(COLUMNNAME_AD_Issue_ID, org.compiere.model.I_AD_Issue.class, AD_Issue);
	}

	/** Set System-Problem.
		@param AD_Issue_ID 
		Automatically created or manually entered System Issue
	  */
	@Override
	public void setAD_Issue_ID (int AD_Issue_ID)
	{
		if (AD_Issue_ID < 1) 
			set_Value (COLUMNNAME_AD_Issue_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Issue_ID, Integer.valueOf(AD_Issue_ID));
	}

	/** Get System-Problem.
		@return Automatically created or manually entered System Issue
	  */
	@Override
	public int getAD_Issue_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Issue_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Fehler.
		@param IsError 
		Ein Fehler ist bei der Durchführung aufgetreten
	  */
	@Override
	public void setIsError (boolean IsError)
	{
		set_ValueNoCheck (COLUMNNAME_IsError, Boolean.valueOf(IsError));
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

	@Override
	public de.metas.vertical.pharma.securpharm.model.I_M_Securpharm_Action_Result getM_Securpharm_Action_Result()
	{
		return get_ValueAsPO(COLUMNNAME_M_Securpharm_Action_Result_ID, de.metas.vertical.pharma.securpharm.model.I_M_Securpharm_Action_Result.class);
	}

	@Override
	public void setM_Securpharm_Action_Result(de.metas.vertical.pharma.securpharm.model.I_M_Securpharm_Action_Result M_Securpharm_Action_Result)
	{
		set_ValueFromPO(COLUMNNAME_M_Securpharm_Action_Result_ID, de.metas.vertical.pharma.securpharm.model.I_M_Securpharm_Action_Result.class, M_Securpharm_Action_Result);
	}

	/** Set Securpharm Aktion Ergebnise.
		@param M_Securpharm_Action_Result_ID Securpharm Aktion Ergebnise	  */
	@Override
	public void setM_Securpharm_Action_Result_ID (int M_Securpharm_Action_Result_ID)
	{
		if (M_Securpharm_Action_Result_ID < 1) 
			set_Value (COLUMNNAME_M_Securpharm_Action_Result_ID, null);
		else 
			set_Value (COLUMNNAME_M_Securpharm_Action_Result_ID, Integer.valueOf(M_Securpharm_Action_Result_ID));
	}

	/** Get Securpharm Aktion Ergebnise.
		@return Securpharm Aktion Ergebnise	  */
	@Override
	public int getM_Securpharm_Action_Result_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Securpharm_Action_Result_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Securpharm-Protokoll.
		@param M_Securpharm_Log_ID Securpharm-Protokoll	  */
	@Override
	public void setM_Securpharm_Log_ID (int M_Securpharm_Log_ID)
	{
		if (M_Securpharm_Log_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Securpharm_Log_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Securpharm_Log_ID, Integer.valueOf(M_Securpharm_Log_ID));
	}

	/** Get Securpharm-Protokoll.
		@return Securpharm-Protokoll	  */
	@Override
	public int getM_Securpharm_Log_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Securpharm_Log_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.vertical.pharma.securpharm.model.I_M_Securpharm_Productdata_Result getM_Securpharm_Productdata_Result()
	{
		return get_ValueAsPO(COLUMNNAME_M_Securpharm_Productdata_Result_ID, de.metas.vertical.pharma.securpharm.model.I_M_Securpharm_Productdata_Result.class);
	}

	@Override
	public void setM_Securpharm_Productdata_Result(de.metas.vertical.pharma.securpharm.model.I_M_Securpharm_Productdata_Result M_Securpharm_Productdata_Result)
	{
		set_ValueFromPO(COLUMNNAME_M_Securpharm_Productdata_Result_ID, de.metas.vertical.pharma.securpharm.model.I_M_Securpharm_Productdata_Result.class, M_Securpharm_Productdata_Result);
	}

	/** Set Securpharm Produktdaten Ergebnise.
		@param M_Securpharm_Productdata_Result_ID Securpharm Produktdaten Ergebnise	  */
	@Override
	public void setM_Securpharm_Productdata_Result_ID (int M_Securpharm_Productdata_Result_ID)
	{
		if (M_Securpharm_Productdata_Result_ID < 1) 
			set_Value (COLUMNNAME_M_Securpharm_Productdata_Result_ID, null);
		else 
			set_Value (COLUMNNAME_M_Securpharm_Productdata_Result_ID, Integer.valueOf(M_Securpharm_Productdata_Result_ID));
	}

	/** Get Securpharm Produktdaten Ergebnise.
		@return Securpharm Produktdaten Ergebnise	  */
	@Override
	public int getM_Securpharm_Productdata_Result_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Securpharm_Productdata_Result_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Request Method.
		@param RequestMethod Request Method	  */
	@Override
	public void setRequestMethod (java.lang.String RequestMethod)
	{
		set_Value (COLUMNNAME_RequestMethod, RequestMethod);
	}

	/** Get Request Method.
		@return Request Method	  */
	@Override
	public java.lang.String getRequestMethod () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_RequestMethod);
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

	/** Set Abfrage.
		@param RequestUrl Abfrage	  */
	@Override
	public void setRequestUrl (java.lang.String RequestUrl)
	{
		set_ValueNoCheck (COLUMNNAME_RequestUrl, RequestUrl);
	}

	/** Get Abfrage.
		@return Abfrage	  */
	@Override
	public java.lang.String getRequestUrl () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_RequestUrl);
	}

	/** Set Antwort .
		@param ResponseCode Antwort 	  */
	@Override
	public void setResponseCode (int ResponseCode)
	{
		set_Value (COLUMNNAME_ResponseCode, Integer.valueOf(ResponseCode));
	}

	/** Get Antwort .
		@return Antwort 	  */
	@Override
	public int getResponseCode () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ResponseCode);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Antwort-Text.
		@param ResponseText 
		Anfrage-Antworttext
	  */
	@Override
	public void setResponseText (java.lang.String ResponseText)
	{
		set_Value (COLUMNNAME_ResponseText, ResponseText);
	}

	/** Get Antwort-Text.
		@return Anfrage-Antworttext
	  */
	@Override
	public java.lang.String getResponseText () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ResponseText);
	}

	/** Set TransaktionsID Client.
		@param TransactionIDClient TransaktionsID Client	  */
	@Override
	public void setTransactionIDClient (java.lang.String TransactionIDClient)
	{
		set_ValueNoCheck (COLUMNNAME_TransactionIDClient, TransactionIDClient);
	}

	/** Get TransaktionsID Client.
		@return TransaktionsID Client	  */
	@Override
	public java.lang.String getTransactionIDClient () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_TransactionIDClient);
	}

	/** Set TransaktionsID Server.
		@param TransactionIDServer TransaktionsID Server	  */
	@Override
	public void setTransactionIDServer (java.lang.String TransactionIDServer)
	{
		set_ValueNoCheck (COLUMNNAME_TransactionIDServer, TransactionIDServer);
	}

	/** Get TransaktionsID Server.
		@return TransaktionsID Server	  */
	@Override
	public java.lang.String getTransactionIDServer () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_TransactionIDServer);
	}
}