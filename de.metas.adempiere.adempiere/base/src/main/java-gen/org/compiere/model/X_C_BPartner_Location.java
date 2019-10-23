/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_BPartner_Location
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_BPartner_Location extends org.compiere.model.PO implements I_C_BPartner_Location, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1004078185L;

    /** Standard Constructor */
    public X_C_BPartner_Location (Properties ctx, int C_BPartner_Location_ID, String trxName)
    {
      super (ctx, C_BPartner_Location_ID, trxName);
      /** if (C_BPartner_Location_ID == 0)
        {
			setC_BPartner_ID (0);
			setC_BPartner_Location_ID (0);
			setC_Location_ID (0);
			setIsBillTo (true); // Y
			setIsHandOverLocation (true); // Y
			setIsPayFrom (true); // Y
			setIsRemitTo (false); // N
			setIsShipTo (true); // Y
			setName (null); // .
			setVisitorsAddress (false); // N
        } */
    }

    /** Load Constructor */
    public X_C_BPartner_Location (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Adresse.
		@param Address 
		Anschrift
	  */
	@Override
	public void setAddress (java.lang.String Address)
	{
		set_Value (COLUMNNAME_Address, Address);
	}

	/** Get Adresse.
		@return Anschrift
	  */
	@Override
	public java.lang.String getAddress () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Address);
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
			set_Value (COLUMNNAME_C_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_Location_ID, Integer.valueOf(C_Location_ID));
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
	public org.compiere.model.I_C_SalesRegion getC_SalesRegion() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_SalesRegion_ID, org.compiere.model.I_C_SalesRegion.class);
	}

	@Override
	public void setC_SalesRegion(org.compiere.model.I_C_SalesRegion C_SalesRegion)
	{
		set_ValueFromPO(COLUMNNAME_C_SalesRegion_ID, org.compiere.model.I_C_SalesRegion.class, C_SalesRegion);
	}

	/** Set Vertriebsgebiet.
		@param C_SalesRegion_ID 
		Sales coverage region
	  */
	@Override
	public void setC_SalesRegion_ID (int C_SalesRegion_ID)
	{
		if (C_SalesRegion_ID < 1) 
			set_Value (COLUMNNAME_C_SalesRegion_ID, null);
		else 
			set_Value (COLUMNNAME_C_SalesRegion_ID, Integer.valueOf(C_SalesRegion_ID));
	}

	/** Get Vertriebsgebiet.
		@return Sales coverage region
	  */
	@Override
	public int getC_SalesRegion_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_SalesRegion_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set eMail.
		@param EMail 
		EMail-Adresse
	  */
	@Override
	public void setEMail (java.lang.String EMail)
	{
		set_Value (COLUMNNAME_EMail, EMail);
	}

	/** Get eMail.
		@return EMail-Adresse
	  */
	@Override
	public java.lang.String getEMail () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EMail);
	}

	/** Set Alternative eMail.
		@param EMail2 
		EMail-Adresse
	  */
	@Override
	public void setEMail2 (java.lang.String EMail2)
	{
		set_Value (COLUMNNAME_EMail2, EMail2);
	}

	/** Get Alternative eMail.
		@return EMail-Adresse
	  */
	@Override
	public java.lang.String getEMail2 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EMail2);
	}

	/** Set External ID.
		@param ExternalId External ID	  */
	@Override
	public void setExternalId (java.lang.String ExternalId)
	{
		set_Value (COLUMNNAME_ExternalId, ExternalId);
	}

	/** Get External ID.
		@return External ID	  */
	@Override
	public java.lang.String getExternalId () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ExternalId);
	}

	/** Set Fax.
		@param Fax 
		Facsimile number
	  */
	@Override
	public void setFax (java.lang.String Fax)
	{
		set_Value (COLUMNNAME_Fax, Fax);
	}

	/** Get Fax.
		@return Facsimile number
	  */
	@Override
	public java.lang.String getFax () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Fax);
	}

	/** Set Alternative Fax.
		@param Fax2 
		Faxnummer
	  */
	@Override
	public void setFax2 (java.lang.String Fax2)
	{
		set_Value (COLUMNNAME_Fax2, Fax2);
	}

	/** Get Alternative Fax.
		@return Faxnummer
	  */
	@Override
	public java.lang.String getFax2 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Fax2);
	}

	/** Set GLN.
		@param GLN GLN	  */
	@Override
	public void setGLN (java.lang.String GLN)
	{
		set_Value (COLUMNNAME_GLN, GLN);
	}

	/** Get GLN.
		@return GLN	  */
	@Override
	public java.lang.String getGLN () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_GLN);
	}

	/** Set Vorbelegung Rechnung.
		@param IsBillTo 
		Rechnungs-Adresse für diesen Geschäftspartner
	  */
	@Override
	public void setIsBillTo (boolean IsBillTo)
	{
		set_Value (COLUMNNAME_IsBillTo, Boolean.valueOf(IsBillTo));
	}

	/** Get Vorbelegung Rechnung.
		@return Rechnungs-Adresse für diesen Geschäftspartner
	  */
	@Override
	public boolean isBillTo () 
	{
		Object oo = get_Value(COLUMNNAME_IsBillTo);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Rechnung Standard Adresse.
		@param IsBillToDefault Rechnung Standard Adresse	  */
	@Override
	public void setIsBillToDefault (boolean IsBillToDefault)
	{
		set_Value (COLUMNNAME_IsBillToDefault, Boolean.valueOf(IsBillToDefault));
	}

	/** Get Rechnung Standard Adresse.
		@return Rechnung Standard Adresse	  */
	@Override
	public boolean isBillToDefault () 
	{
		Object oo = get_Value(COLUMNNAME_IsBillToDefault);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Provisionsadresse.
		@param IsCommissionTo 
		Provisionsabrechnungen werden hierhin geschickt
	  */
	@Override
	public void setIsCommissionTo (boolean IsCommissionTo)
	{
		set_Value (COLUMNNAME_IsCommissionTo, Boolean.valueOf(IsCommissionTo));
	}

	/** Get Provisionsadresse.
		@return Provisionsabrechnungen werden hierhin geschickt
	  */
	@Override
	public boolean isCommissionTo () 
	{
		Object oo = get_Value(COLUMNNAME_IsCommissionTo);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Provision Standard Adresse.
		@param IsCommissionToDefault Provision Standard Adresse	  */
	@Override
	public void setIsCommissionToDefault (boolean IsCommissionToDefault)
	{
		set_Value (COLUMNNAME_IsCommissionToDefault, Boolean.valueOf(IsCommissionToDefault));
	}

	/** Get Provision Standard Adresse.
		@return Provision Standard Adresse	  */
	@Override
	public boolean isCommissionToDefault () 
	{
		Object oo = get_Value(COLUMNNAME_IsCommissionToDefault);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set ISDN.
		@param ISDN 
		ISDN or modem line
	  */
	@Override
	public void setISDN (java.lang.String ISDN)
	{
		set_Value (COLUMNNAME_ISDN, ISDN);
	}

	/** Get ISDN.
		@return ISDN or modem line
	  */
	@Override
	public java.lang.String getISDN () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ISDN);
	}

	/** Set Abladeort.
		@param IsHandOverLocation Abladeort	  */
	@Override
	public void setIsHandOverLocation (boolean IsHandOverLocation)
	{
		set_Value (COLUMNNAME_IsHandOverLocation, Boolean.valueOf(IsHandOverLocation));
	}

	/** Get Abladeort.
		@return Abladeort	  */
	@Override
	public boolean isHandOverLocation () 
	{
		Object oo = get_Value(COLUMNNAME_IsHandOverLocation);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Zahlungs-Adresse.
		@param IsPayFrom 
		Business Partner pays from that address and we'll send dunning letters there
	  */
	@Override
	public void setIsPayFrom (boolean IsPayFrom)
	{
		set_Value (COLUMNNAME_IsPayFrom, Boolean.valueOf(IsPayFrom));
	}

	/** Get Zahlungs-Adresse.
		@return Business Partner pays from that address and we'll send dunning letters there
	  */
	@Override
	public boolean isPayFrom () 
	{
		Object oo = get_Value(COLUMNNAME_IsPayFrom);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Erstattungs-Adresse.
		@param IsRemitTo 
		Business Partner payment address
	  */
	@Override
	public void setIsRemitTo (boolean IsRemitTo)
	{
		set_Value (COLUMNNAME_IsRemitTo, Boolean.valueOf(IsRemitTo));
	}

	/** Get Erstattungs-Adresse.
		@return Business Partner payment address
	  */
	@Override
	public boolean isRemitTo () 
	{
		Object oo = get_Value(COLUMNNAME_IsRemitTo);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Lieferstandard.
		@param IsShipTo 
		Liefer-Adresse für den Geschäftspartner
	  */
	@Override
	public void setIsShipTo (boolean IsShipTo)
	{
		set_Value (COLUMNNAME_IsShipTo, Boolean.valueOf(IsShipTo));
	}

	/** Get Lieferstandard.
		@return Liefer-Adresse für den Geschäftspartner
	  */
	@Override
	public boolean isShipTo () 
	{
		Object oo = get_Value(COLUMNNAME_IsShipTo);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Liefer Standard Adresse.
		@param IsShipToDefault Liefer Standard Adresse	  */
	@Override
	public void setIsShipToDefault (boolean IsShipToDefault)
	{
		set_Value (COLUMNNAME_IsShipToDefault, Boolean.valueOf(IsShipToDefault));
	}

	/** Get Liefer Standard Adresse.
		@return Liefer Standard Adresse	  */
	@Override
	public boolean isShipToDefault () 
	{
		Object oo = get_Value(COLUMNNAME_IsShipToDefault);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Abo Adresse.
		@param IsSubscriptionTo 
		An diese Adresse werden Abos geschickt
	  */
	@Override
	public void setIsSubscriptionTo (boolean IsSubscriptionTo)
	{
		set_Value (COLUMNNAME_IsSubscriptionTo, Boolean.valueOf(IsSubscriptionTo));
	}

	/** Get Abo Adresse.
		@return An diese Adresse werden Abos geschickt
	  */
	@Override
	public boolean isSubscriptionTo () 
	{
		Object oo = get_Value(COLUMNNAME_IsSubscriptionTo);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Abo Standard Adresse.
		@param IsSubscriptionToDefault Abo Standard Adresse	  */
	@Override
	public void setIsSubscriptionToDefault (boolean IsSubscriptionToDefault)
	{
		set_Value (COLUMNNAME_IsSubscriptionToDefault, Boolean.valueOf(IsSubscriptionToDefault));
	}

	/** Get Abo Standard Adresse.
		@return Abo Standard Adresse	  */
	@Override
	public boolean isSubscriptionToDefault () 
	{
		Object oo = get_Value(COLUMNNAME_IsSubscriptionToDefault);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Telefon.
		@param Phone 
		Beschreibt eine Telefon Nummer
	  */
	@Override
	public void setPhone (java.lang.String Phone)
	{
		set_Value (COLUMNNAME_Phone, Phone);
	}

	/** Get Telefon.
		@return Beschreibt eine Telefon Nummer
	  */
	@Override
	public java.lang.String getPhone () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Phone);
	}

	/** Set Mobil.
		@param Phone2 
		Alternative Mobile Telefonnummer
	  */
	@Override
	public void setPhone2 (java.lang.String Phone2)
	{
		set_Value (COLUMNNAME_Phone2, Phone2);
	}

	/** Get Mobil.
		@return Alternative Mobile Telefonnummer
	  */
	@Override
	public java.lang.String getPhone2 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Phone2);
	}

	/** Set Visitors Address.
		@param VisitorsAddress Visitors Address	  */
	@Override
	public void setVisitorsAddress (boolean VisitorsAddress)
	{
		set_Value (COLUMNNAME_VisitorsAddress, Boolean.valueOf(VisitorsAddress));
	}

	/** Get Visitors Address.
		@return Visitors Address	  */
	@Override
	public boolean isVisitorsAddress () 
	{
		Object oo = get_Value(COLUMNNAME_VisitorsAddress);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
}