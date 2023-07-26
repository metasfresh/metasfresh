/** Generated Model - DO NOT CHANGE */
package de.metas.datev.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for DATEV_ExportLine
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_DATEV_ExportLine extends org.compiere.model.PO implements I_DATEV_ExportLine, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -691992968L;

    /** Standard Constructor */
    public X_DATEV_ExportLine (Properties ctx, int DATEV_ExportLine_ID, String trxName)
    {
      super (ctx, DATEV_ExportLine_ID, trxName);
      /** if (DATEV_ExportLine_ID == 0)
        {
			setAmt (BigDecimal.ZERO);
			setCR_Account (null);
			setDATEV_Export_ID (0);
			setDATEV_ExportLine_ID (0);
			setDR_Account (null);
        } */
    }

    /** Load Constructor */
    public X_DATEV_ExportLine (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Activity Name.
		@param ActivityName Activity Name	  */
	@Override
	public void setActivityName (java.lang.String ActivityName)
	{
		set_ValueNoCheck (COLUMNNAME_ActivityName, ActivityName);
	}

	/** Get Activity Name.
		@return Activity Name	  */
	@Override
	public java.lang.String getActivityName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ActivityName);
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

	/** Set Name.
		@param BPName 
		Name des Sponsors.
	  */
	@Override
	public void setBPName (java.lang.String BPName)
	{
		set_Value (COLUMNNAME_BPName, BPName);
	}

	/** Get Name.
		@return Name des Sponsors.
	  */
	@Override
	public java.lang.String getBPName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_BPName);
	}

	/** Set Nr..
		@param BPValue 
		Sponsor-Nr.
	  */
	@Override
	public void setBPValue (java.lang.String BPValue)
	{
		set_Value (COLUMNNAME_BPValue, BPValue);
	}

	/** Get Nr..
		@return Sponsor-Nr.
	  */
	@Override
	public java.lang.String getBPValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_BPValue);
	}

	@Override
	public org.compiere.model.I_C_Activity getC_Activity() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Activity_ID, org.compiere.model.I_C_Activity.class);
	}

	@Override
	public void setC_Activity(org.compiere.model.I_C_Activity C_Activity)
	{
		set_ValueFromPO(COLUMNNAME_C_Activity_ID, org.compiere.model.I_C_Activity.class, C_Activity);
	}

	/** Set Kostenstelle.
		@param C_Activity_ID 
		Kostenstelle
	  */
	@Override
	public void setC_Activity_ID (int C_Activity_ID)
	{
		if (C_Activity_ID < 1) 
			set_Value (COLUMNNAME_C_Activity_ID, null);
		else 
			set_Value (COLUMNNAME_C_Activity_ID, Integer.valueOf(C_Activity_ID));
	}

	/** Get Kostenstelle.
		@return Kostenstelle
	  */
	@Override
	public int getC_Activity_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Activity_ID);
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
	public org.compiere.model.I_C_Invoice getC_Invoice() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class);
	}

	@Override
	public void setC_Invoice(org.compiere.model.I_C_Invoice C_Invoice)
	{
		set_ValueFromPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class, C_Invoice);
	}

	/** Set Rechnung.
		@param C_Invoice_ID 
		Invoice Identifier
	  */
	@Override
	public void setC_Invoice_ID (int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
	}

	/** Get Rechnung.
		@return Invoice Identifier
	  */
	@Override
	public int getC_Invoice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Credit Account.
		@param CR_Account Credit Account	  */
	@Override
	public void setCR_Account (java.lang.String CR_Account)
	{
		set_Value (COLUMNNAME_CR_Account, CR_Account);
	}

	/** Get Credit Account.
		@return Credit Account	  */
	@Override
	public java.lang.String getCR_Account () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CR_Account);
	}

	/** Set Buchungsdatum.
		@param DateAcct 
		Accounting Date
	  */
	@Override
	public void setDateAcct (java.sql.Timestamp DateAcct)
	{
		set_Value (COLUMNNAME_DateAcct, DateAcct);
	}

	/** Get Buchungsdatum.
		@return Accounting Date
	  */
	@Override
	public java.sql.Timestamp getDateAcct () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateAcct);
	}

	@Override
	public de.metas.datev.model.I_DATEV_Export getDATEV_Export() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_DATEV_Export_ID, de.metas.datev.model.I_DATEV_Export.class);
	}

	@Override
	public void setDATEV_Export(de.metas.datev.model.I_DATEV_Export DATEV_Export)
	{
		set_ValueFromPO(COLUMNNAME_DATEV_Export_ID, de.metas.datev.model.I_DATEV_Export.class, DATEV_Export);
	}

	/** Set DATEV Export.
		@param DATEV_Export_ID DATEV Export	  */
	@Override
	public void setDATEV_Export_ID (int DATEV_Export_ID)
	{
		if (DATEV_Export_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DATEV_Export_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DATEV_Export_ID, Integer.valueOf(DATEV_Export_ID));
	}

	/** Get DATEV Export.
		@return DATEV Export	  */
	@Override
	public int getDATEV_Export_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DATEV_Export_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DATEV Export Line.
		@param DATEV_ExportLine_ID DATEV Export Line	  */
	@Override
	public void setDATEV_ExportLine_ID (int DATEV_ExportLine_ID)
	{
		if (DATEV_ExportLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DATEV_ExportLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DATEV_ExportLine_ID, Integer.valueOf(DATEV_ExportLine_ID));
	}

	/** Get DATEV Export Line.
		@return DATEV Export Line	  */
	@Override
	public int getDATEV_ExportLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DATEV_ExportLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Beschreibung.
		@param Description Beschreibung	  */
	@Override
	public void setDescription (java.lang.String Description)
	{
		set_ValueNoCheck (COLUMNNAME_Description, Description);
	}

	/** Get Beschreibung.
		@return Beschreibung	  */
	@Override
	public java.lang.String getDescription () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	/** Set Dokument Basis Typ.
		@param DocBaseType Dokument Basis Typ	  */
	@Override
	public void setDocBaseType (java.lang.String DocBaseType)
	{
		set_Value (COLUMNNAME_DocBaseType, DocBaseType);
	}

	/** Get Dokument Basis Typ.
		@return Dokument Basis Typ	  */
	@Override
	public java.lang.String getDocBaseType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocBaseType);
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

	/** Set Debit Account.
		@param DR_Account Debit Account	  */
	@Override
	public void setDR_Account (java.lang.String DR_Account)
	{
		set_Value (COLUMNNAME_DR_Account, DR_Account);
	}

	/** Get Debit Account.
		@return Debit Account	  */
	@Override
	public java.lang.String getDR_Account () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DR_Account);
	}

	/** Set Datum Fälligkeit.
		@param DueDate 
		Datum, zu dem Zahlung fällig wird
	  */
	@Override
	public void setDueDate (java.sql.Timestamp DueDate)
	{
		set_ValueNoCheck (COLUMNNAME_DueDate, DueDate);
	}

	/** Get Datum Fälligkeit.
		@return Datum, zu dem Zahlung fällig wird
	  */
	@Override
	public java.sql.Timestamp getDueDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DueDate);
	}

	@Override
	public org.compiere.model.I_Fact_Acct getFact_Acct() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_Fact_Acct_ID, org.compiere.model.I_Fact_Acct.class);
	}

	@Override
	public void setFact_Acct(org.compiere.model.I_Fact_Acct Fact_Acct)
	{
		set_ValueFromPO(COLUMNNAME_Fact_Acct_ID, org.compiere.model.I_Fact_Acct.class, Fact_Acct);
	}

	/** Set Accounting Fact.
		@param Fact_Acct_ID Accounting Fact	  */
	@Override
	public void setFact_Acct_ID (int Fact_Acct_ID)
	{
		if (Fact_Acct_ID < 1) 
			set_Value (COLUMNNAME_Fact_Acct_ID, null);
		else 
			set_Value (COLUMNNAME_Fact_Acct_ID, Integer.valueOf(Fact_Acct_ID));
	}

	/** Get Accounting Fact.
		@return Accounting Fact	  */
	@Override
	public int getFact_Acct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Fact_Acct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}