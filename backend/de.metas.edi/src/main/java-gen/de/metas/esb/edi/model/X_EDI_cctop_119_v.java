/** Generated Model - DO NOT CHANGE */
package de.metas.esb.edi.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for EDI_cctop_119_v
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_EDI_cctop_119_v extends org.compiere.model.PO implements I_EDI_cctop_119_v, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 838980454L;

    /** Standard Constructor */
    public X_EDI_cctop_119_v (Properties ctx, int EDI_cctop_119_v_ID, String trxName)
    {
      super (ctx, EDI_cctop_119_v_ID, trxName);
      /** if (EDI_cctop_119_v_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_EDI_cctop_119_v (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Adresszeile 1.
		@param Address1 
		Adresszeile 1 für diesen Standort
	  */
	@Override
	public void setAddress1 (java.lang.String Address1)
	{
		set_Value (COLUMNNAME_Address1, Address1);
	}

	/** Get Adresszeile 1.
		@return Adresszeile 1 für diesen Standort
	  */
	@Override
	public java.lang.String getAddress1 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Address1);
	}

	/** Set Adresszeile 2.
		@param Address2 
		Adresszeile 2 für diesen Standort
	  */
	@Override
	public void setAddress2 (java.lang.String Address2)
	{
		set_Value (COLUMNNAME_Address2, Address2);
	}

	/** Get Adresszeile 2.
		@return Adresszeile 2 für diesen Standort
	  */
	@Override
	public java.lang.String getAddress2 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Address2);
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
	public org.compiere.model.I_C_Invoice getC_Invoice()
	{
		return get_ValueAsPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class);
	}

	@Override
	public void setC_Invoice(org.compiere.model.I_C_Invoice C_Invoice)
	{
		set_ValueFromPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class, C_Invoice);
	}

	/** Set Invoice.
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

	/** Get Invoice.
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

	/** Set Ort.
		@param City 
		Name des Ortes
	  */
	@Override
	public void setCity (java.lang.String City)
	{
		set_Value (COLUMNNAME_City, City);
	}

	/** Get Ort.
		@return Name des Ortes
	  */
	@Override
	public java.lang.String getCity () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_City);
	}

	/** Set ISO Ländercode.
		@param CountryCode 
		Zweibuchstabiger ISO Ländercode gemäß ISO 3166-1 - http://www.chemie.fu-berlin.de/diverse/doc/ISO_3166.html
	  */
	@Override
	public void setCountryCode (java.lang.String CountryCode)
	{
		set_Value (COLUMNNAME_CountryCode, CountryCode);
	}

	/** Get ISO Ländercode.
		@return Zweibuchstabiger ISO Ländercode gemäß ISO 3166-1 - http://www.chemie.fu-berlin.de/diverse/doc/ISO_3166.html
	  */
	@Override
	public java.lang.String getCountryCode () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CountryCode);
	}

	/** Set eancom_locationtype.
		@param eancom_locationtype eancom_locationtype	  */
	@Override
	public void seteancom_locationtype (java.lang.String eancom_locationtype)
	{
		set_Value (COLUMNNAME_eancom_locationtype, eancom_locationtype);
	}

	/** Get eancom_locationtype.
		@return eancom_locationtype	  */
	@Override
	public java.lang.String geteancom_locationtype () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_eancom_locationtype);
	}

	/** Set EDI_cctop_119_v.
		@param EDI_cctop_119_v_ID EDI_cctop_119_v	  */
	@Override
	public void setEDI_cctop_119_v_ID (int EDI_cctop_119_v_ID)
	{
		if (EDI_cctop_119_v_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_119_v_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_119_v_ID, Integer.valueOf(EDI_cctop_119_v_ID));
	}

	/** Get EDI_cctop_119_v.
		@return EDI_cctop_119_v	  */
	@Override
	public int getEDI_cctop_119_v_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_EDI_cctop_119_v_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.esb.edi.model.I_EDI_cctop_invoic_v getEDI_cctop_invoic_v()
	{
		return get_ValueAsPO(COLUMNNAME_EDI_cctop_invoic_v_ID, de.metas.esb.edi.model.I_EDI_cctop_invoic_v.class);
	}

	@Override
	public void setEDI_cctop_invoic_v(de.metas.esb.edi.model.I_EDI_cctop_invoic_v EDI_cctop_invoic_v)
	{
		set_ValueFromPO(COLUMNNAME_EDI_cctop_invoic_v_ID, de.metas.esb.edi.model.I_EDI_cctop_invoic_v.class, EDI_cctop_invoic_v);
	}

	/** Set EDI_cctop_invoic_v.
		@param EDI_cctop_invoic_v_ID EDI_cctop_invoic_v	  */
	@Override
	public void setEDI_cctop_invoic_v_ID (int EDI_cctop_invoic_v_ID)
	{
		if (EDI_cctop_invoic_v_ID < 1) 
			set_Value (COLUMNNAME_EDI_cctop_invoic_v_ID, null);
		else 
			set_Value (COLUMNNAME_EDI_cctop_invoic_v_ID, Integer.valueOf(EDI_cctop_invoic_v_ID));
	}

	/** Get EDI_cctop_invoic_v.
		@return EDI_cctop_invoic_v	  */
	@Override
	public int getEDI_cctop_invoic_v_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_EDI_cctop_invoic_v_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Fax.
		@param Fax 
		Faxnummer
	  */
	@Override
	public void setFax (java.lang.String Fax)
	{
		set_Value (COLUMNNAME_Fax, Fax);
	}

	/** Get Fax.
		@return Faxnummer
	  */
	@Override
	public java.lang.String getFax () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Fax);
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

	@Override
	public org.compiere.model.I_M_InOut getM_InOut()
	{
		return get_ValueAsPO(COLUMNNAME_M_InOut_ID, org.compiere.model.I_M_InOut.class);
	}

	@Override
	public void setM_InOut(org.compiere.model.I_M_InOut M_InOut)
	{
		set_ValueFromPO(COLUMNNAME_M_InOut_ID, org.compiere.model.I_M_InOut.class, M_InOut);
	}

	/** Set Lieferung/Wareneingang.
		@param M_InOut_ID 
		Material Shipment Document
	  */
	@Override
	public void setM_InOut_ID (int M_InOut_ID)
	{
		if (M_InOut_ID < 1) 
			set_Value (COLUMNNAME_M_InOut_ID, null);
		else 
			set_Value (COLUMNNAME_M_InOut_ID, Integer.valueOf(M_InOut_ID));
	}

	/** Get Lieferung/Wareneingang.
		@return Material Shipment Document
	  */
	@Override
	public int getM_InOut_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_InOut_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Name Zusatz.
		@param Name2 
		Zusätzliche Bezeichnung
	  */
	@Override
	public void setName2 (java.lang.String Name2)
	{
		set_Value (COLUMNNAME_Name2, Name2);
	}

	/** Get Name Zusatz.
		@return Zusätzliche Bezeichnung
	  */
	@Override
	public java.lang.String getName2 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name2);
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

	/** Set PLZ.
		@param Postal 
		Postleitzahl
	  */
	@Override
	public void setPostal (java.lang.String Postal)
	{
		set_Value (COLUMNNAME_Postal, Postal);
	}

	/** Get PLZ.
		@return Postleitzahl
	  */
	@Override
	public java.lang.String getPostal () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Postal);
	}

	/** Set Referenznummer.
		@param ReferenceNo 
		Ihre Kunden- oder Lieferantennummer beim Geschäftspartner
	  */
	@Override
	public void setReferenceNo (java.lang.String ReferenceNo)
	{
		set_Value (COLUMNNAME_ReferenceNo, ReferenceNo);
	}

	/** Get Referenznummer.
		@return Ihre Kunden- oder Lieferantennummer beim Geschäftspartner
	  */
	@Override
	public java.lang.String getReferenceNo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ReferenceNo);
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

	/** Set Umsatzsteuer ID.
		@param VATaxID Umsatzsteuer ID	  */
	@Override
	public void setVATaxID (java.lang.String VATaxID)
	{
		set_Value (COLUMNNAME_VATaxID, VATaxID);
	}

	/** Get Umsatzsteuer ID.
		@return Umsatzsteuer ID	  */
	@Override
	public java.lang.String getVATaxID () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_VATaxID);
	}
}