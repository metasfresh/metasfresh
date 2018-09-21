/** Generated Model - DO NOT CHANGE */
package de.metas.dunning.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_DunningDoc
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_DunningDoc extends org.compiere.model.PO implements I_C_DunningDoc, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1280603864L;

    /** Standard Constructor */
    public X_C_DunningDoc (Properties ctx, int C_DunningDoc_ID, String trxName)
    {
      super (ctx, C_DunningDoc_ID, trxName);
      /** if (C_DunningDoc_ID == 0)
        {
			setC_BPartner_ID (0);
			setC_BPartner_Location_ID (0);
			setC_DunningDoc_ID (0);
			setC_DunningLevel_ID (0);
			setDocAction (null); // CO
			setDocStatus (null); // DR
			setDocumentNo (null);
			setDunningDate (new Timestamp( System.currentTimeMillis() ));
			setIsUseBPartnerAddress (false); // N
			setProcessed (false); // N
        } */
    }

    /** Load Constructor */
    public X_C_DunningDoc (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Anschrift-Text.
		@param BPartnerAddress Anschrift-Text	  */
	@Override
	public void setBPartnerAddress (java.lang.String BPartnerAddress)
	{
		set_Value (COLUMNNAME_BPartnerAddress, BPartnerAddress);
	}

	/** Get Anschrift-Text.
		@return Anschrift-Text	  */
	@Override
	public java.lang.String getBPartnerAddress () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_BPartnerAddress);
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
		Identifiziert die (Liefer-) Adresse des Geschäftspartners
	  */
	@Override
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, Integer.valueOf(C_BPartner_Location_ID));
	}

	/** Get Standort.
		@return Identifiziert die (Liefer-) Adresse des Geschäftspartners
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
	public org.compiere.model.I_C_DocType getC_DocType() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_DocType_ID, org.compiere.model.I_C_DocType.class);
	}

	@Override
	public void setC_DocType(org.compiere.model.I_C_DocType C_DocType)
	{
		set_ValueFromPO(COLUMNNAME_C_DocType_ID, org.compiere.model.I_C_DocType.class, C_DocType);
	}

	/** Set Belegart.
		@param C_DocType_ID 
		Belegart oder Verarbeitungsvorgaben
	  */
	@Override
	public void setC_DocType_ID (int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_C_DocType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
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

	@Override
	public org.compiere.model.I_AD_User getC_Dunning_Contact() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Dunning_Contact_ID, org.compiere.model.I_AD_User.class);
	}

	@Override
	public void setC_Dunning_Contact(org.compiere.model.I_AD_User C_Dunning_Contact)
	{
		set_ValueFromPO(COLUMNNAME_C_Dunning_Contact_ID, org.compiere.model.I_AD_User.class, C_Dunning_Contact);
	}

	/** Set Mahnkontakt.
		@param C_Dunning_Contact_ID Mahnkontakt	  */
	@Override
	public void setC_Dunning_Contact_ID (int C_Dunning_Contact_ID)
	{
		if (C_Dunning_Contact_ID < 1) 
			set_Value (COLUMNNAME_C_Dunning_Contact_ID, null);
		else 
			set_Value (COLUMNNAME_C_Dunning_Contact_ID, Integer.valueOf(C_Dunning_Contact_ID));
	}

	/** Get Mahnkontakt.
		@return Mahnkontakt	  */
	@Override
	public int getC_Dunning_Contact_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Dunning_Contact_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Dunning Document.
		@param C_DunningDoc_ID Dunning Document	  */
	@Override
	public void setC_DunningDoc_ID (int C_DunningDoc_ID)
	{
		if (C_DunningDoc_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_DunningDoc_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_DunningDoc_ID, Integer.valueOf(C_DunningDoc_ID));
	}

	/** Get Dunning Document.
		@return Dunning Document	  */
	@Override
	public int getC_DunningDoc_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DunningDoc_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_DunningLevel getC_DunningLevel() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_DunningLevel_ID, org.compiere.model.I_C_DunningLevel.class);
	}

	@Override
	public void setC_DunningLevel(org.compiere.model.I_C_DunningLevel C_DunningLevel)
	{
		set_ValueFromPO(COLUMNNAME_C_DunningLevel_ID, org.compiere.model.I_C_DunningLevel.class, C_DunningLevel);
	}

	/** Set Mahnstufe.
		@param C_DunningLevel_ID Mahnstufe	  */
	@Override
	public void setC_DunningLevel_ID (int C_DunningLevel_ID)
	{
		if (C_DunningLevel_ID < 1) 
			set_Value (COLUMNNAME_C_DunningLevel_ID, null);
		else 
			set_Value (COLUMNNAME_C_DunningLevel_ID, Integer.valueOf(C_DunningLevel_ID));
	}

	/** Get Mahnstufe.
		@return Mahnstufe	  */
	@Override
	public int getC_DunningLevel_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DunningLevel_ID);
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
		Der zukünftige Status des Belegs
	  */
	@Override
	public void setDocAction (java.lang.String DocAction)
	{

		set_Value (COLUMNNAME_DocAction, DocAction);
	}

	/** Get Belegverarbeitung.
		@return Der zukünftige Status des Belegs
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

	/** Set Dunning Date.
		@param DunningDate 
		Date of Dunning
	  */
	@Override
	public void setDunningDate (java.sql.Timestamp DunningDate)
	{
		set_Value (COLUMNNAME_DunningDate, DunningDate);
	}

	/** Get Dunning Date.
		@return Date of Dunning
	  */
	@Override
	public java.sql.Timestamp getDunningDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DunningDate);
	}

	/** Set Benutze abw. Adresse.
		@param IsUseBPartnerAddress Benutze abw. Adresse	  */
	@Override
	public void setIsUseBPartnerAddress (boolean IsUseBPartnerAddress)
	{
		set_Value (COLUMNNAME_IsUseBPartnerAddress, Boolean.valueOf(IsUseBPartnerAddress));
	}

	/** Get Benutze abw. Adresse.
		@return Benutze abw. Adresse	  */
	@Override
	public boolean isUseBPartnerAddress () 
	{
		Object oo = get_Value(COLUMNNAME_IsUseBPartnerAddress);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Massenaustritt.
		@param IsWriteOff Massenaustritt	  */
	@Override
	public void setIsWriteOff (boolean IsWriteOff)
	{
		throw new IllegalArgumentException ("IsWriteOff is virtual column");	}

	/** Get Massenaustritt.
		@return Massenaustritt	  */
	@Override
	public boolean isWriteOff () 
	{
		Object oo = get_Value(COLUMNNAME_IsWriteOff);
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

	/** Set Process Now.
		@param Processing Process Now	  */
	@Override
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Process Now.
		@return Process Now	  */
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
}