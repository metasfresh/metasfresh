/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_BankStatement
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_BankStatement extends org.compiere.model.PO implements I_C_BankStatement, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1754782029L;

    /** Standard Constructor */
    public X_C_BankStatement (Properties ctx, int C_BankStatement_ID, String trxName)
    {
      super (ctx, C_BankStatement_ID, trxName);
      /** if (C_BankStatement_ID == 0)
        {
			setC_BankStatement_ID (0);
			setC_BP_BankAccount_ID (0);
			setDocAction (null); // CO
			setDocStatus (null); // DR
			setDocumentNo (null);
			setEndingBalance (BigDecimal.ZERO);
			setIsApproved (false); // N
			setIsManual (true); // Y
			setName (null); // @#Date@
			setPosted (false); // N
			setProcessed (false);
			setStatementDate (new Timestamp( System.currentTimeMillis() )); // @Date@
        } */
    }

    /** Load Constructor */
    public X_C_BankStatement (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Anfangssaldo.
		@param BeginningBalance 
		Balance prior to any transactions
	  */
	@Override
	public void setBeginningBalance (java.math.BigDecimal BeginningBalance)
	{
		set_Value (COLUMNNAME_BeginningBalance, BeginningBalance);
	}

	/** Get Anfangssaldo.
		@return Balance prior to any transactions
	  */
	@Override
	public java.math.BigDecimal getBeginningBalance () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_BeginningBalance);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Bankauszug.
		@param C_BankStatement_ID 
		Bank Statement of account
	  */
	@Override
	public void setC_BankStatement_ID (int C_BankStatement_ID)
	{
		if (C_BankStatement_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BankStatement_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BankStatement_ID, Integer.valueOf(C_BankStatement_ID));
	}

	/** Get Bankauszug.
		@return Bank Statement of account
	  */
	@Override
	public int getC_BankStatement_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BankStatement_ID);
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

	/** Set Belegart.
		@param C_DocType_ID 
		Belegart oder Verarbeitungsvorgaben
	  */
	@Override
	public void setC_DocType_ID (int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_Value (COLUMNNAME_C_DocType_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
	}

	/** Get Belegart.
		@return Belegart oder Verarbeitungsvorgaben
	  */
	@Override
	public int getC_DocType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Position(en) kopieren von.
		@param CreateFrom 
		Process which will generate a new document lines based on an existing document
	  */
	@Override
	public void setCreateFrom (java.lang.String CreateFrom)
	{
		set_Value (COLUMNNAME_CreateFrom, CreateFrom);
	}

	/** Get Position(en) kopieren von.
		@return Process which will generate a new document lines based on an existing document
	  */
	@Override
	public java.lang.String getCreateFrom () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CreateFrom);
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

	/** 
	 * DocAction AD_Reference_ID=135
	 * Reference name: _Document Action
	 */
	public static final int DOCACTION_AD_Reference_ID=135;
	/** Complete = CO */
	public static final String DOCACTION_Complete = "CO";
	/** Approve = AP */
	public static final String DOCACTION_Approve = "AP";
	/** Reject = RJ */
	public static final String DOCACTION_Reject = "RJ";
	/** Post = PO */
	public static final String DOCACTION_Post = "PO";
	/** Void = VO */
	public static final String DOCACTION_Void = "VO";
	/** Close = CL */
	public static final String DOCACTION_Close = "CL";
	/** Reverse_Correct = RC */
	public static final String DOCACTION_Reverse_Correct = "RC";
	/** Reverse_Accrual = RA */
	public static final String DOCACTION_Reverse_Accrual = "RA";
	/** Invalidate = IN */
	public static final String DOCACTION_Invalidate = "IN";
	/** Re_Activate = RE */
	public static final String DOCACTION_Re_Activate = "RE";
	/** None = -- */
	public static final String DOCACTION_None = "--";
	/** Prepare = PR */
	public static final String DOCACTION_Prepare = "PR";
	/** Unlock = XL */
	public static final String DOCACTION_Unlock = "XL";
	/** WaitComplete = WC */
	public static final String DOCACTION_WaitComplete = "WC";
	/** UnClose = UC */
	public static final String DOCACTION_UnClose = "UC";
	/** Set Belegverarbeitung.
		@param DocAction 
		The targeted status of the document
	  */
	@Override
	public void setDocAction (java.lang.String DocAction)
	{

		set_Value (COLUMNNAME_DocAction, DocAction);
	}

	/** Get Belegverarbeitung.
		@return The targeted status of the document
	  */
	@Override
	public java.lang.String getDocAction () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocAction);
	}

	/** 
	 * DocStatus AD_Reference_ID=131
	 * Reference name: _Document Status
	 */
	public static final int DOCSTATUS_AD_Reference_ID=131;
	/** Drafted = DR */
	public static final String DOCSTATUS_Drafted = "DR";
	/** Completed = CO */
	public static final String DOCSTATUS_Completed = "CO";
	/** Approved = AP */
	public static final String DOCSTATUS_Approved = "AP";
	/** NotApproved = NA */
	public static final String DOCSTATUS_NotApproved = "NA";
	/** Voided = VO */
	public static final String DOCSTATUS_Voided = "VO";
	/** Invalid = IN */
	public static final String DOCSTATUS_Invalid = "IN";
	/** Reversed = RE */
	public static final String DOCSTATUS_Reversed = "RE";
	/** Closed = CL */
	public static final String DOCSTATUS_Closed = "CL";
	/** Unknown = ?? */
	public static final String DOCSTATUS_Unknown = "??";
	/** InProgress = IP */
	public static final String DOCSTATUS_InProgress = "IP";
	/** WaitingPayment = WP */
	public static final String DOCSTATUS_WaitingPayment = "WP";
	/** WaitingConfirmation = WC */
	public static final String DOCSTATUS_WaitingConfirmation = "WC";
	/** Set Belegstatus.
		@param DocStatus 
		The current status of the document
	  */
	@Override
	public void setDocStatus (java.lang.String DocStatus)
	{

		set_Value (COLUMNNAME_DocStatus, DocStatus);
	}

	/** Get Belegstatus.
		@return The current status of the document
	  */
	@Override
	public java.lang.String getDocStatus () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocStatus);
	}

	/** Set Nr..
		@param DocumentNo 
		Document sequence number of the document
	  */
	@Override
	public void setDocumentNo (java.lang.String DocumentNo)
	{
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Nr..
		@return Document sequence number of the document
	  */
	@Override
	public java.lang.String getDocumentNo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocumentNo);
	}

	/** Set Auszugsdatum ELV.
		@param EftStatementDate 
		Electronic Funds Transfer Statement Date
	  */
	@Override
	public void setEftStatementDate (java.sql.Timestamp EftStatementDate)
	{
		set_Value (COLUMNNAME_EftStatementDate, EftStatementDate);
	}

	/** Get Auszugsdatum ELV.
		@return Electronic Funds Transfer Statement Date
	  */
	@Override
	public java.sql.Timestamp getEftStatementDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_EftStatementDate);
	}

	/** Set ELV Auszugs-Referenz.
		@param EftStatementReference 
		Electronic Funds Transfer Statement Reference
	  */
	@Override
	public void setEftStatementReference (java.lang.String EftStatementReference)
	{
		set_Value (COLUMNNAME_EftStatementReference, EftStatementReference);
	}

	/** Get ELV Auszugs-Referenz.
		@return Electronic Funds Transfer Statement Reference
	  */
	@Override
	public java.lang.String getEftStatementReference () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EftStatementReference);
	}

	/** Set Endsaldo.
		@param EndingBalance 
		Ending  or closing balance
	  */
	@Override
	public void setEndingBalance (java.math.BigDecimal EndingBalance)
	{
		set_Value (COLUMNNAME_EndingBalance, EndingBalance);
	}

	/** Get Endsaldo.
		@return Ending  or closing balance
	  */
	@Override
	public java.math.BigDecimal getEndingBalance () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_EndingBalance);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Freigegeben.
		@param IsApproved 
		Indicates if this document requires approval
	  */
	@Override
	public void setIsApproved (boolean IsApproved)
	{
		set_Value (COLUMNNAME_IsApproved, Boolean.valueOf(IsApproved));
	}

	/** Get Freigegeben.
		@return Indicates if this document requires approval
	  */
	@Override
	public boolean isApproved () 
	{
		Object oo = get_Value(COLUMNNAME_IsApproved);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Manuell.
		@param IsManual 
		This is a manual process
	  */
	@Override
	public void setIsManual (boolean IsManual)
	{
		set_Value (COLUMNNAME_IsManual, Boolean.valueOf(IsManual));
	}

	/** Get Manuell.
		@return This is a manual process
	  */
	@Override
	public boolean isManual () 
	{
		Object oo = get_Value(COLUMNNAME_IsManual);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Match Statement.
		@param MatchStatement Match Statement	  */
	@Override
	public void setMatchStatement (java.lang.String MatchStatement)
	{
		set_Value (COLUMNNAME_MatchStatement, MatchStatement);
	}

	/** Get Match Statement.
		@return Match Statement	  */
	@Override
	public java.lang.String getMatchStatement () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_MatchStatement);
	}

	/** Set Name.
		@param Name Name	  */
	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Name	  */
	@Override
	public java.lang.String getName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}

	/** Set Verbucht.
		@param Posted 
		Posting status
	  */
	@Override
	public void setPosted (boolean Posted)
	{
		set_Value (COLUMNNAME_Posted, Boolean.valueOf(Posted));
	}

	/** Get Verbucht.
		@return Posting status
	  */
	@Override
	public boolean isPosted () 
	{
		Object oo = get_Value(COLUMNNAME_Posted);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Verarbeiten.
		@param Processing Verarbeiten	  */
	@Override
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Verarbeiten.
		@return Verarbeiten	  */
	@Override
	public boolean isProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Auszugsdatum.
		@param StatementDate 
		Date of the statement
	  */
	@Override
	public void setStatementDate (java.sql.Timestamp StatementDate)
	{
		set_Value (COLUMNNAME_StatementDate, StatementDate);
	}

	/** Get Auszugsdatum.
		@return Date of the statement
	  */
	@Override
	public java.sql.Timestamp getStatementDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_StatementDate);
	}

	/** Set Auszugsdifferenz.
		@param StatementDifference 
		Difference between statement ending balance and actual ending balance
	  */
	@Override
	public void setStatementDifference (java.math.BigDecimal StatementDifference)
	{
		set_Value (COLUMNNAME_StatementDifference, StatementDifference);
	}

	/** Get Auszugsdifferenz.
		@return Difference between statement ending balance and actual ending balance
	  */
	@Override
	public java.math.BigDecimal getStatementDifference () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_StatementDifference);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}
}