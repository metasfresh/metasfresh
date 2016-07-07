/** Generated Model - DO NOT CHANGE */
package de.metas.rfq.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_RfQResponse_v
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_RfQResponse_v extends org.compiere.model.PO implements I_C_RfQResponse_v, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 332033577L;

    /** Standard Constructor */
    public X_C_RfQResponse_v (Properties ctx, int C_RfQResponse_v_ID, String trxName)
    {
      super (ctx, C_RfQResponse_v_ID, trxName);
      /** if (C_RfQResponse_v_ID == 0)
        {
			setBPName (null);
			setC_BPartner_ID (0);
			setC_BPartner_Location_ID (0);
			setC_Currency_ID (0);
			setC_RfQ_ID (0);
			setC_RfQResponse_ID (0);
			setDateResponse (new Timestamp( System.currentTimeMillis() ));
			setISO_Code (null);
			setName (null);
			setTaxID (null);
        } */
    }

    /** Load Constructor */
    public X_C_RfQResponse_v (Properties ctx, ResultSet rs, String trxName)
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

	/** 
	 * AD_Language AD_Reference_ID=106
	 * Reference name: AD_Language
	 */
	public static final int AD_LANGUAGE_AD_Reference_ID=106;
	/** Set Sprache.
		@param AD_Language 
		Language for this entity
	  */
	@Override
	public void setAD_Language (java.lang.String AD_Language)
	{

		set_ValueNoCheck (COLUMNNAME_AD_Language, AD_Language);
	}

	/** Get Sprache.
		@return Language for this entity
	  */
	@Override
	public java.lang.String getAD_Language () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AD_Language);
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

	/** Set Name.
		@param BPName 
		Name des Sponsors.
	  */
	@Override
	public void setBPName (java.lang.String BPName)
	{
		set_ValueNoCheck (COLUMNNAME_BPName, BPName);
	}

	/** Get Name.
		@return Name des Sponsors.
	  */
	@Override
	public java.lang.String getBPName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_BPName);
	}

	/** Set BP Name2.
		@param BPName2 BP Name2	  */
	@Override
	public void setBPName2 (java.lang.String BPName2)
	{
		set_ValueNoCheck (COLUMNNAME_BPName2, BPName2);
	}

	/** Get BP Name2.
		@return BP Name2	  */
	@Override
	public java.lang.String getBPName2 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_BPName2);
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
	public org.compiere.model.I_C_Location getC_Location() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Location_ID, org.compiere.model.I_C_Location.class);
	}

	@Override
	public void setC_Location(org.compiere.model.I_C_Location C_Location)
	{
		set_ValueFromPO(COLUMNNAME_C_Location_ID, org.compiere.model.I_C_Location.class, C_Location);
	}

	/** Set Anschrift.
		@param C_Location_ID 
		Location or Address
	  */
	@Override
	public void setC_Location_ID (int C_Location_ID)
	{
		if (C_Location_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Location_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Location_ID, Integer.valueOf(C_Location_ID));
	}

	/** Get Anschrift.
		@return Location or Address
	  */
	@Override
	public int getC_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Location_ID);
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

	/** Set Ausschreibungs-Antwort.
		@param C_RfQResponse_ID 
		Request for Quotation Response from a potential Vendor
	  */
	@Override
	public void setC_RfQResponse_ID (int C_RfQResponse_ID)
	{
		if (C_RfQResponse_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_RfQResponse_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_RfQResponse_ID, Integer.valueOf(C_RfQResponse_ID));
	}

	/** Get Ausschreibungs-Antwort.
		@return Request for Quotation Response from a potential Vendor
	  */
	@Override
	public int getC_RfQResponse_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_RfQResponse_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Kontakt-Name.
		@param ContactName 
		Business Partner Contact Name
	  */
	@Override
	public void setContactName (java.lang.String ContactName)
	{
		set_ValueNoCheck (COLUMNNAME_ContactName, ContactName);
	}

	/** Get Kontakt-Name.
		@return Business Partner Contact Name
	  */
	@Override
	public java.lang.String getContactName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ContactName);
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

	/** Set ISO Währungscode.
		@param ISO_Code 
		Three letter ISO 4217 Code of the Currency
	  */
	@Override
	public void setISO_Code (java.lang.String ISO_Code)
	{
		set_ValueNoCheck (COLUMNNAME_ISO_Code, ISO_Code);
	}

	/** Get ISO Währungscode.
		@return Three letter ISO 4217 Code of the Currency
	  */
	@Override
	public java.lang.String getISO_Code () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ISO_Code);
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

	@Override
	public org.compiere.model.I_C_Location getOrg_Location() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_Org_Location_ID, org.compiere.model.I_C_Location.class);
	}

	@Override
	public void setOrg_Location(org.compiere.model.I_C_Location Org_Location)
	{
		set_ValueFromPO(COLUMNNAME_Org_Location_ID, org.compiere.model.I_C_Location.class, Org_Location);
	}

	/** Set Org Address.
		@param Org_Location_ID 
		Organization Location/Address
	  */
	@Override
	public void setOrg_Location_ID (int Org_Location_ID)
	{
		if (Org_Location_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Org_Location_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Org_Location_ID, Integer.valueOf(Org_Location_ID));
	}

	/** Get Org Address.
		@return Organization Location/Address
	  */
	@Override
	public int getOrg_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Org_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Phone.
		@param Phone 
		Identifies a telephone number
	  */
	@Override
	public void setPhone (java.lang.String Phone)
	{
		set_ValueNoCheck (COLUMNNAME_Phone, Phone);
	}

	/** Get Phone.
		@return Identifies a telephone number
	  */
	@Override
	public java.lang.String getPhone () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Phone);
	}

	/** Set Steuer-ID.
		@param TaxID 
		Tax Identification
	  */
	@Override
	public void setTaxID (java.lang.String TaxID)
	{
		set_ValueNoCheck (COLUMNNAME_TaxID, TaxID);
	}

	/** Get Steuer-ID.
		@return Tax Identification
	  */
	@Override
	public java.lang.String getTaxID () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_TaxID);
	}

	/** Set Titel.
		@param Title 
		Name this entity is referred to as
	  */
	@Override
	public void setTitle (java.lang.String Title)
	{
		set_ValueNoCheck (COLUMNNAME_Title, Title);
	}

	/** Get Titel.
		@return Name this entity is referred to as
	  */
	@Override
	public java.lang.String getTitle () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Title);
	}
}