/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for I_Request
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_I_Request extends org.compiere.model.PO implements I_I_Request, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -663486873L;

    /** Standard Constructor */
    public X_I_Request (Properties ctx, int I_Request_ID, String trxName)
    {
      super (ctx, I_Request_ID, trxName);
      /** if (I_Request_ID == 0)
        {
			setI_IsImported (null); // N
			setI_Request_ID (0);
			setProcessed (false); // N
        } */
    }

    /** Load Constructor */
    public X_I_Request (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Date next action.
		@param DateNextAction 
		Date that this request should be acted on
	  */
	@Override
	public void setDateNextAction (java.sql.Timestamp DateNextAction)
	{
		set_Value (COLUMNNAME_DateNextAction, DateNextAction);
	}

	/** Get Date next action.
		@return Date that this request should be acted on
	  */
	@Override
	public java.sql.Timestamp getDateNextAction () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateNextAction);
	}

	/** Set Vorgangsdatum.
		@param DateTrx 
		Vorgangsdatum
	  */
	@Override
	public void setDateTrx (java.sql.Timestamp DateTrx)
	{
		set_Value (COLUMNNAME_DateTrx, DateTrx);
	}

	/** Get Vorgangsdatum.
		@return Vorgangsdatum
	  */
	@Override
	public java.sql.Timestamp getDateTrx () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateTrx);
	}

	/** Set Beleg Nr..
		@param DocumentNo 
		Document sequence number of the document
	  */
	@Override
	public void setDocumentNo (java.lang.String DocumentNo)
	{
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Beleg Nr..
		@return Document sequence number of the document
	  */
	@Override
	public java.lang.String getDocumentNo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocumentNo);
	}

	/** Set Import-Fehlermeldung.
		@param I_ErrorMsg 
		Meldungen, die durch den Importprozess generiert wurden
	  */
	@Override
	public void setI_ErrorMsg (java.lang.String I_ErrorMsg)
	{
		set_Value (COLUMNNAME_I_ErrorMsg, I_ErrorMsg);
	}

	/** Get Import-Fehlermeldung.
		@return Meldungen, die durch den Importprozess generiert wurden
	  */
	@Override
	public java.lang.String getI_ErrorMsg () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_I_ErrorMsg);
	}

	/** 
	 * I_IsImported AD_Reference_ID=540745
	 * Reference name: I_IsImported
	 */
	public static final int I_ISIMPORTED_AD_Reference_ID=540745;
	/** NotImported = N */
	public static final String I_ISIMPORTED_NotImported = "N";
	/** Imported = Y */
	public static final String I_ISIMPORTED_Imported = "Y";
	/** ImportFailed = E */
	public static final String I_ISIMPORTED_ImportFailed = "E";
	/** Set Importiert.
		@param I_IsImported 
		Ist dieser Import verarbeitet worden?
	  */
	@Override
	public void setI_IsImported (java.lang.String I_IsImported)
	{

		set_Value (COLUMNNAME_I_IsImported, I_IsImported);
	}

	/** Get Importiert.
		@return Ist dieser Import verarbeitet worden?
	  */
	@Override
	public java.lang.String getI_IsImported () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_I_IsImported);
	}

	/** Set Import Request.
		@param I_Request_ID Import Request	  */
	@Override
	public void setI_Request_ID (int I_Request_ID)
	{
		if (I_Request_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_I_Request_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_I_Request_ID, Integer.valueOf(I_Request_ID));
	}

	/** Get Import Request.
		@return Import Request	  */
	@Override
	public int getI_Request_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_I_Request_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Verarbeitet.
		@param Processed 
		Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Verarbeitet.
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

	@Override
	public org.compiere.model.I_R_Request getR_Request() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_R_Request_ID, org.compiere.model.I_R_Request.class);
	}

	@Override
	public void setR_Request(org.compiere.model.I_R_Request R_Request)
	{
		set_ValueFromPO(COLUMNNAME_R_Request_ID, org.compiere.model.I_R_Request.class, R_Request);
	}

	/** Set Aufgabe.
		@param R_Request_ID 
		Request from a Business Partner or Prospect
	  */
	@Override
	public void setR_Request_ID (int R_Request_ID)
	{
		if (R_Request_ID < 1) 
			set_Value (COLUMNNAME_R_Request_ID, null);
		else 
			set_Value (COLUMNNAME_R_Request_ID, Integer.valueOf(R_Request_ID));
	}

	/** Get Aufgabe.
		@return Request from a Business Partner or Prospect
	  */
	@Override
	public int getR_Request_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_Request_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_R_RequestType getR_RequestType() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_R_RequestType_ID, org.compiere.model.I_R_RequestType.class);
	}

	@Override
	public void setR_RequestType(org.compiere.model.I_R_RequestType R_RequestType)
	{
		set_ValueFromPO(COLUMNNAME_R_RequestType_ID, org.compiere.model.I_R_RequestType.class, R_RequestType);
	}

	/** Set Request Type.
		@param R_RequestType_ID 
		Type of request (e.g. Inquiry, Complaint, ..)
	  */
	@Override
	public void setR_RequestType_ID (int R_RequestType_ID)
	{
		if (R_RequestType_ID < 1) 
			set_Value (COLUMNNAME_R_RequestType_ID, null);
		else 
			set_Value (COLUMNNAME_R_RequestType_ID, Integer.valueOf(R_RequestType_ID));
	}

	/** Get Request Type.
		@return Type of request (e.g. Inquiry, Complaint, ..)
	  */
	@Override
	public int getR_RequestType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_RequestType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_R_Status getR_Status() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_R_Status_ID, org.compiere.model.I_R_Status.class);
	}

	@Override
	public void setR_Status(org.compiere.model.I_R_Status R_Status)
	{
		set_ValueFromPO(COLUMNNAME_R_Status_ID, org.compiere.model.I_R_Status.class, R_Status);
	}

	/** Set Status.
		@param R_Status_ID 
		Request Status
	  */
	@Override
	public void setR_Status_ID (int R_Status_ID)
	{
		if (R_Status_ID < 1) 
			set_Value (COLUMNNAME_R_Status_ID, null);
		else 
			set_Value (COLUMNNAME_R_Status_ID, Integer.valueOf(R_Status_ID));
	}

	/** Get Status.
		@return Request Status
	  */
	@Override
	public int getR_Status_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_Status_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Anfrageart.
		@param RequestType Anfrageart	  */
	@Override
	public void setRequestType (java.lang.String RequestType)
	{
		set_Value (COLUMNNAME_RequestType, RequestType);
	}

	/** Get Anfrageart.
		@return Anfrageart	  */
	@Override
	public java.lang.String getRequestType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_RequestType);
	}

	/** Set Ergebnis.
		@param Result 
		Result of the action taken
	  */
	@Override
	public void setResult (java.lang.String Result)
	{
		set_Value (COLUMNNAME_Result, Result);
	}

	/** Get Ergebnis.
		@return Result of the action taken
	  */
	@Override
	public java.lang.String getResult () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Result);
	}

	/** Set Status.
		@param Status Status	  */
	@Override
	public void setStatus (java.lang.String Status)
	{
		set_Value (COLUMNNAME_Status, Status);
	}

	/** Get Status.
		@return Status	  */
	@Override
	public java.lang.String getStatus () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Status);
	}

	/** Set Summary.
		@param Summary 
		Textual summary of this request
	  */
	@Override
	public void setSummary (java.lang.String Summary)
	{
		set_Value (COLUMNNAME_Summary, Summary);
	}

	/** Get Summary.
		@return Textual summary of this request
	  */
	@Override
	public java.lang.String getSummary () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Summary);
	}

	/** Set Suchschlüssel.
		@param Value 
		Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein
	  */
	@Override
	public void setValue (java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Suchschlüssel.
		@return Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein
	  */
	@Override
	public java.lang.String getValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Value);
	}
}