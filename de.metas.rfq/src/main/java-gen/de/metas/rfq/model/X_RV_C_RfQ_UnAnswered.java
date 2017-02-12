/** Generated Model - DO NOT CHANGE */
package de.metas.rfq.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for RV_C_RfQ_UnAnswered
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_RV_C_RfQ_UnAnswered extends org.compiere.model.PO implements I_RV_C_RfQ_UnAnswered, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 2033106006L;

    /** Standard Constructor */
    public X_RV_C_RfQ_UnAnswered (Properties ctx, int RV_C_RfQ_UnAnswered_ID, String trxName)
    {
      super (ctx, RV_C_RfQ_UnAnswered_ID, trxName);
      /** if (RV_C_RfQ_UnAnswered_ID == 0)
        {
			setC_BPartner_ID (0);
			setC_BPartner_Location_ID (0);
			setC_Currency_ID (0);
			setC_RfQ_ID (0);
			setC_RfQ_Topic_ID (0);
			setDateResponse (new Timestamp( System.currentTimeMillis() ));
			setIsQuoteAllQty (false);
			setIsQuoteTotalAmt (false);
			setIsRfQResponseAccepted (false);
			setName (null);
			setQuoteType (null);
			setSalesRep_ID (0);
        } */
    }

    /** Load Constructor */
    public X_RV_C_RfQ_UnAnswered (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_User getAD_User() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_User_ID, org.compiere.model.I_AD_User.class);
	}

	@Override
	public void setAD_User(org.compiere.model.I_AD_User AD_User)
	{
		set_ValueFromPO(COLUMNNAME_AD_User_ID, org.compiere.model.I_AD_User.class, AD_User);
	}

	/** Set Ansprechpartner.
		@param AD_User_ID 
		User within the system - Internal or Business Partner Contact
	  */
	@Override
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	/** Get Ansprechpartner.
		@return User within the system - Internal or Business Partner Contact
	  */
	@Override
	public int getAD_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_ID);
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
		Identifies a Business Partner
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
		@return Identifies a Business Partner
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
	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_Location_ID, org.compiere.model.I_C_BPartner_Location.class);
	}

	@Override
	public void setC_BPartner_Location(org.compiere.model.I_C_BPartner_Location C_BPartner_Location)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_Location_ID, org.compiere.model.I_C_BPartner_Location.class, C_BPartner_Location);
	}

	/** Set Standort.
		@param C_BPartner_Location_ID 
		Identifies the (ship to) address for this Business Partner
	  */
	@Override
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Location_ID, Integer.valueOf(C_BPartner_Location_ID));
	}

	/** Get Standort.
		@return Identifies the (ship to) address for this Business Partner
	  */
	@Override
	public int getC_BPartner_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Location_ID);
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
		The Currency for this record
	  */
	@Override
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Currency_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Währung.
		@return The Currency for this record
	  */
	@Override
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.rfq.model.I_C_RfQ getC_RfQ() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_RfQ_ID, de.metas.rfq.model.I_C_RfQ.class);
	}

	@Override
	public void setC_RfQ(de.metas.rfq.model.I_C_RfQ C_RfQ)
	{
		set_ValueFromPO(COLUMNNAME_C_RfQ_ID, de.metas.rfq.model.I_C_RfQ.class, C_RfQ);
	}

	/** Set Ausschreibung.
		@param C_RfQ_ID 
		Request for Quotation
	  */
	@Override
	public void setC_RfQ_ID (int C_RfQ_ID)
	{
		if (C_RfQ_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_RfQ_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_RfQ_ID, Integer.valueOf(C_RfQ_ID));
	}

	/** Get Ausschreibung.
		@return Request for Quotation
	  */
	@Override
	public int getC_RfQ_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_RfQ_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.rfq.model.I_C_RfQ_Topic getC_RfQ_Topic() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_RfQ_Topic_ID, de.metas.rfq.model.I_C_RfQ_Topic.class);
	}

	@Override
	public void setC_RfQ_Topic(de.metas.rfq.model.I_C_RfQ_Topic C_RfQ_Topic)
	{
		set_ValueFromPO(COLUMNNAME_C_RfQ_Topic_ID, de.metas.rfq.model.I_C_RfQ_Topic.class, C_RfQ_Topic);
	}

	/** Set Ausschreibungs-Thema.
		@param C_RfQ_Topic_ID 
		Topic for Request for Quotations
	  */
	@Override
	public void setC_RfQ_Topic_ID (int C_RfQ_Topic_ID)
	{
		if (C_RfQ_Topic_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_RfQ_Topic_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_RfQ_Topic_ID, Integer.valueOf(C_RfQ_Topic_ID));
	}

	/** Get Ausschreibungs-Thema.
		@return Topic for Request for Quotations
	  */
	@Override
	public int getC_RfQ_Topic_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_RfQ_Topic_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Antwort-datum.
		@param DateResponse 
		Date of the Response
	  */
	@Override
	public void setDateResponse (java.sql.Timestamp DateResponse)
	{
		set_ValueNoCheck (COLUMNNAME_DateResponse, DateResponse);
	}

	/** Get Antwort-datum.
		@return Date of the Response
	  */
	@Override
	public java.sql.Timestamp getDateResponse () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateResponse);
	}

	/** Set Arbeit fertiggestellt.
		@param DateWorkComplete 
		Date when work is (planned to be) complete
	  */
	@Override
	public void setDateWorkComplete (java.sql.Timestamp DateWorkComplete)
	{
		set_ValueNoCheck (COLUMNNAME_DateWorkComplete, DateWorkComplete);
	}

	/** Get Arbeit fertiggestellt.
		@return Date when work is (planned to be) complete
	  */
	@Override
	public java.sql.Timestamp getDateWorkComplete () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateWorkComplete);
	}

	/** Set Arbeitsbeginn.
		@param DateWorkStart 
		Date when work is (planned to be) started
	  */
	@Override
	public void setDateWorkStart (java.sql.Timestamp DateWorkStart)
	{
		set_ValueNoCheck (COLUMNNAME_DateWorkStart, DateWorkStart);
	}

	/** Get Arbeitsbeginn.
		@return Date when work is (planned to be) started
	  */
	@Override
	public java.sql.Timestamp getDateWorkStart () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateWorkStart);
	}

	/** Set Auslieferungstage.
		@param DeliveryDays 
		Number of Days (planned) until Delivery
	  */
	@Override
	public void setDeliveryDays (int DeliveryDays)
	{
		set_ValueNoCheck (COLUMNNAME_DeliveryDays, Integer.valueOf(DeliveryDays));
	}

	/** Get Auslieferungstage.
		@return Number of Days (planned) until Delivery
	  */
	@Override
	public int getDeliveryDays () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DeliveryDays);
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

	/** Set Kommentar/Hilfe.
		@param Help 
		Comment or Hint
	  */
	@Override
	public void setHelp (java.lang.String Help)
	{
		set_ValueNoCheck (COLUMNNAME_Help, Help);
	}

	/** Get Kommentar/Hilfe.
		@return Comment or Hint
	  */
	@Override
	public java.lang.String getHelp () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Help);
	}

	/** Set Quote All Quantities.
		@param IsQuoteAllQty 
		Suppliers are requested to provide responses for all quantities
	  */
	@Override
	public void setIsQuoteAllQty (boolean IsQuoteAllQty)
	{
		set_ValueNoCheck (COLUMNNAME_IsQuoteAllQty, Boolean.valueOf(IsQuoteAllQty));
	}

	/** Get Quote All Quantities.
		@return Suppliers are requested to provide responses for all quantities
	  */
	@Override
	public boolean isQuoteAllQty () 
	{
		Object oo = get_Value(COLUMNNAME_IsQuoteAllQty);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Quote Total Amt.
		@param IsQuoteTotalAmt 
		The respnse can have just the total amount for the RfQ
	  */
	@Override
	public void setIsQuoteTotalAmt (boolean IsQuoteTotalAmt)
	{
		set_ValueNoCheck (COLUMNNAME_IsQuoteTotalAmt, Boolean.valueOf(IsQuoteTotalAmt));
	}

	/** Get Quote Total Amt.
		@return The respnse can have just the total amount for the RfQ
	  */
	@Override
	public boolean isQuoteTotalAmt () 
	{
		Object oo = get_Value(COLUMNNAME_IsQuoteTotalAmt);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Responses Accepted.
		@param IsRfQResponseAccepted 
		Are Resonses to the Request for Quotation accepted
	  */
	@Override
	public void setIsRfQResponseAccepted (boolean IsRfQResponseAccepted)
	{
		set_ValueNoCheck (COLUMNNAME_IsRfQResponseAccepted, Boolean.valueOf(IsRfQResponseAccepted));
	}

	/** Get Responses Accepted.
		@return Are Resonses to the Request for Quotation accepted
	  */
	@Override
	public boolean isRfQResponseAccepted () 
	{
		Object oo = get_Value(COLUMNNAME_IsRfQResponseAccepted);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	@Override
	public void setName (java.lang.String Name)
	{
		set_ValueNoCheck (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	@Override
	public java.lang.String getName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}

	/** 
	 * QuoteType AD_Reference_ID=314
	 * Reference name: C_RfQ QuoteType
	 */
	public static final int QUOTETYPE_AD_Reference_ID=314;
	/** Quote Total only = T */
	public static final String QUOTETYPE_QuoteTotalOnly = "T";
	/** Quote Selected Lines = S */
	public static final String QUOTETYPE_QuoteSelectedLines = "S";
	/** Quote All Lines = A */
	public static final String QUOTETYPE_QuoteAllLines = "A";
	/** Set RfQ Type.
		@param QuoteType 
		Request for Quotation Type
	  */
	@Override
	public void setQuoteType (java.lang.String QuoteType)
	{

		set_ValueNoCheck (COLUMNNAME_QuoteType, QuoteType);
	}

	/** Get RfQ Type.
		@return Request for Quotation Type
	  */
	@Override
	public java.lang.String getQuoteType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_QuoteType);
	}

	@Override
	public org.compiere.model.I_AD_User getSalesRep() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_SalesRep_ID, org.compiere.model.I_AD_User.class);
	}

	@Override
	public void setSalesRep(org.compiere.model.I_AD_User SalesRep)
	{
		set_ValueFromPO(COLUMNNAME_SalesRep_ID, org.compiere.model.I_AD_User.class, SalesRep);
	}

	/** Set Vertriebsbeauftragter.
		@param SalesRep_ID 
		Sales Representative or Company Agent
	  */
	@Override
	public void setSalesRep_ID (int SalesRep_ID)
	{
		if (SalesRep_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_SalesRep_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_SalesRep_ID, Integer.valueOf(SalesRep_ID));
	}

	/** Get Vertriebsbeauftragter.
		@return Sales Representative or Company Agent
	  */
	@Override
	public int getSalesRep_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SalesRep_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}