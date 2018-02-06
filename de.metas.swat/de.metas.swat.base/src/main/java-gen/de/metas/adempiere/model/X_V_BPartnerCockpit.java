/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2007 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package de.metas.adempiere.model;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.Env;

/** Generated Model for V_BPartnerCockpit
 *  @author Adempiere (generated)
 *  @version Release 3.5.4a#464 - $Id$ */
@SuppressWarnings("javadoc")
public class X_V_BPartnerCockpit extends org.compiere.model.PO implements I_V_BPartnerCockpit, org.compiere.model.I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1791933974L;

    /** Standard Constructor */
    public X_V_BPartnerCockpit (final Properties ctx, final int V_BPartnerCockpit_ID, final String trxName)
    {
      super (ctx, V_BPartnerCockpit_ID, trxName);
      /** if (V_BPartnerCockpit_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_V_BPartnerCockpit (final Properties ctx, final ResultSet rs, final String trxName)
    {
      super (ctx, rs, trxName);
    }


    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (final Properties ctx)
    {
      final org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_Name, get_TrxName());
      return poi;
    }

    @Override
    public String toString()
    {
      final StringBuffer sb = new StringBuffer ("X_V_BPartnerCockpit[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Sprache.
		@param AD_Language
		Sprache für diesen Eintrag
	  */
	@Override
	public void setAD_Language (final java.lang.String AD_Language)
	{
		set_ValueNoCheck (COLUMNNAME_AD_Language, AD_Language);
	}

	/** Get Sprache.
		@return Sprache für diesen Eintrag
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
	public void setAD_User(final org.compiere.model.I_AD_User AD_User)
	{
		set_ValueFromPO(COLUMNNAME_AD_User_ID, org.compiere.model.I_AD_User.class, AD_User);
	}

	/** Set Ansprechpartner.
		@param AD_User_ID
		User within the system - Internal or Business Partner Contact
	  */
	@Override
	public void setAD_User_ID (final int AD_User_ID)
	{
		if (AD_User_ID < 1)
		{
			set_Value (COLUMNNAME_AD_User_ID, null);
		}
		else
		{
			set_Value (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
		}
	}

	/** Get Ansprechpartner.
		@return User within the system - Internal or Business Partner Contact
	  */
	@Override
	public int getAD_User_ID ()
	{
		final Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	/** Set Straße und Nr..
		@param Address1
		Adresszeile 1 für diesen Standort
	  */
	@Override
	public void setAddress1 (final java.lang.String Address1)
	{
		set_ValueNoCheck (COLUMNNAME_Address1, Address1);
	}

	/** Get Straße und Nr..
		@return Adresszeile 1 für diesen Standort
	  */
	@Override
	public java.lang.String getAddress1 ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Address1);
	}

	/** Set _.
		@param Address2
		Adresszeile 2 für diesen Standort
	  */
	@Override
	public void setAddress2 (final java.lang.String Address2)
	{
		set_ValueNoCheck (COLUMNNAME_Address2, Address2);
	}

	/** Get _.
		@return Adresszeile 2 für diesen Standort
	  */
	@Override
	public java.lang.String getAddress2 ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Address2);
	}

	/** Set Adresszeile 3.
		@param Address3
		Adresszeilee 3 für diesen Standort
	  */
	@Override
	public void setAddress3 (final java.lang.String Address3)
	{
		set_ValueNoCheck (COLUMNNAME_Address3, Address3);
	}

	/** Get Adresszeile 3.
		@return Adresszeilee 3 für diesen Standort
	  */
	@Override
	public java.lang.String getAddress3 ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Address3);
	}

	/** Set Adresszusatz.
		@param Address4
		Adresszeile 4 für diesen Standort
	  */
	@Override
	public void setAddress4 (final java.lang.String Address4)
	{
		set_ValueNoCheck (COLUMNNAME_Address4, Address4);
	}

	/** Get Adresszusatz.
		@return Adresszeile 4 für diesen Standort
	  */
	@Override
	public java.lang.String getAddress4 ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Address4);
	}

	/** Set Anschrifttyp.
		@param anschrifttyp Anschrifttyp	  */
	@Override
	public void setanschrifttyp (final java.lang.String anschrifttyp)
	{
		set_Value (COLUMNNAME_anschrifttyp, anschrifttyp);
	}

	/** Get Anschrifttyp.
		@return Anschrifttyp	  */
	@Override
	public java.lang.String getanschrifttyp ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_anschrifttyp);
	}

	/** Set Anzahl Abos.
		@param AnzahlAbos Anzahl Abos	  */
	@Override
	public void setAnzahlAbos (final int AnzahlAbos)
	{
		set_ValueNoCheck (COLUMNNAME_AnzahlAbos, Integer.valueOf(AnzahlAbos));
	}

	/** Get Anzahl Abos.
		@return Anzahl Abos	  */
	@Override
	public int getAnzahlAbos ()
	{
		final Integer ii = (Integer)get_Value(COLUMNNAME_AnzahlAbos);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	/** Set Auto Suche.
		@param AutoSuche Auto Suche	  */
	@Override
	public void setAutoSuche (final java.lang.String AutoSuche)
	{
		set_ValueNoCheck (COLUMNNAME_AutoSuche, AutoSuche);
	}

	/** Get Auto Suche.
		@return Auto Suche	  */
	@Override
	public java.lang.String getAutoSuche ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_AutoSuche);
	}

	@Override
	public org.compiere.model.I_C_Greeting getBPContactGreet() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_BPContactGreeting, org.compiere.model.I_C_Greeting.class);
	}

	@Override
	public void setBPContactGreet(final org.compiere.model.I_C_Greeting BPContactGreet)
	{
		set_ValueFromPO(COLUMNNAME_BPContactGreeting, org.compiere.model.I_C_Greeting.class, BPContactGreet);
	}

	/** Set Anrede.
		@param BPContactGreeting
		Anrede für den Geschäftspartner-Kontakt
	  */
	@Override
	public void setBPContactGreeting (final int BPContactGreeting)
	{
		set_ValueNoCheck (COLUMNNAME_BPContactGreeting, Integer.valueOf(BPContactGreeting));
	}

	/** Get Anrede.
		@return Anrede für den Geschäftspartner-Kontakt
	  */
	@Override
	public int getBPContactGreeting ()
	{
		final Integer ii = (Integer)get_Value(COLUMNNAME_BPContactGreeting);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BP_Group getC_BP_Group() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BP_Group_ID, org.compiere.model.I_C_BP_Group.class);
	}

	@Override
	public void setC_BP_Group(final org.compiere.model.I_C_BP_Group C_BP_Group)
	{
		set_ValueFromPO(COLUMNNAME_C_BP_Group_ID, org.compiere.model.I_C_BP_Group.class, C_BP_Group);
	}

	/** Set Geschäftspartnergruppe.
		@param C_BP_Group_ID
		Geschäftspartnergruppe
	  */
	@Override
	public void setC_BP_Group_ID (final int C_BP_Group_ID)
	{
		if (C_BP_Group_ID < 1)
		{
			set_ValueNoCheck (COLUMNNAME_C_BP_Group_ID, null);
		}
		else
		{
			set_ValueNoCheck (COLUMNNAME_C_BP_Group_ID, Integer.valueOf(C_BP_Group_ID));
		}
	}

	/** Get Geschäftspartnergruppe.
		@return Geschäftspartnergruppe
	  */
	@Override
	public int getC_BP_Group_ID ()
	{
		final Integer ii = (Integer)get_Value(COLUMNNAME_C_BP_Group_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	/** Set Geschäftspartner.
		@param C_BPartner_ID
		Bezeichnet einen Geschäftspartner
	  */
	@Override
	public void setC_BPartner_ID (final int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1)
		{
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		}
		else
		{
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
		}
	}

	/** Get Geschäftspartner.
		@return Bezeichnet einen Geschäftspartner
	  */
	@Override
	public int getC_BPartner_ID ()
	{
		final Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_Location_ID, org.compiere.model.I_C_BPartner_Location.class);
	}

	@Override
	public void setC_BPartner_Location(final org.compiere.model.I_C_BPartner_Location C_BPartner_Location)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_Location_ID, org.compiere.model.I_C_BPartner_Location.class, C_BPartner_Location);
	}

	/** Set Standort.
		@param C_BPartner_Location_ID
		Identifiziert die (Liefer-) Adresse des Geschäftspartners
	  */
	@Override
	public void setC_BPartner_Location_ID (final int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1)
		{
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Location_ID, null);
		}
		else
		{
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Location_ID, Integer.valueOf(C_BPartner_Location_ID));
		}
	}

	/** Get Standort.
		@return Identifiziert die (Liefer-) Adresse des Geschäftspartners
	  */
	@Override
	public int getC_BPartner_Location_ID ()
	{
		final Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Location_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Country getC_Country() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Country_ID, org.compiere.model.I_C_Country.class);
	}

	@Override
	public void setC_Country(final org.compiere.model.I_C_Country C_Country)
	{
		set_ValueFromPO(COLUMNNAME_C_Country_ID, org.compiere.model.I_C_Country.class, C_Country);
	}

	/** Set Land.
		@param C_Country_ID
		Land
	  */
	@Override
	public void setC_Country_ID (final int C_Country_ID)
	{
		if (C_Country_ID < 1)
		{
			set_ValueNoCheck (COLUMNNAME_C_Country_ID, null);
		}
		else
		{
			set_ValueNoCheck (COLUMNNAME_C_Country_ID, Integer.valueOf(C_Country_ID));
		}
	}

	/** Get Land.
		@return Land
	  */
	@Override
	public int getC_Country_ID ()
	{
		final Integer ii = (Integer)get_Value(COLUMNNAME_C_Country_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Dunning getC_Dunning() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Dunning_ID, org.compiere.model.I_C_Dunning.class);
	}

	@Override
	public void setC_Dunning(final org.compiere.model.I_C_Dunning C_Dunning)
	{
		set_ValueFromPO(COLUMNNAME_C_Dunning_ID, org.compiere.model.I_C_Dunning.class, C_Dunning);
	}

	/** Set Mahnung.
		@param C_Dunning_ID
		Dunning Rules for overdue invoices
	  */
	@Override
	public void setC_Dunning_ID (final int C_Dunning_ID)
	{
		if (C_Dunning_ID < 1)
		{
			set_ValueNoCheck (COLUMNNAME_C_Dunning_ID, null);
		}
		else
		{
			set_ValueNoCheck (COLUMNNAME_C_Dunning_ID, Integer.valueOf(C_Dunning_ID));
		}
	}

	/** Get Mahnung.
		@return Dunning Rules for overdue invoices
	  */
	@Override
	public int getC_Dunning_ID ()
	{
		final Integer ii = (Integer)get_Value(COLUMNNAME_C_Dunning_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Greeting getC_Greeting() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Greeting_ID, org.compiere.model.I_C_Greeting.class);
	}

	@Override
	public void setC_Greeting(final org.compiere.model.I_C_Greeting C_Greeting)
	{
		set_ValueFromPO(COLUMNNAME_C_Greeting_ID, org.compiere.model.I_C_Greeting.class, C_Greeting);
	}

	/** Set Anrede.
		@param C_Greeting_ID
		Anrede zum Druck auf Korrespondenz
	  */
	@Override
	public void setC_Greeting_ID (final int C_Greeting_ID)
	{
		if (C_Greeting_ID < 1)
		{
			set_ValueNoCheck (COLUMNNAME_C_Greeting_ID, null);
		}
		else
		{
			set_ValueNoCheck (COLUMNNAME_C_Greeting_ID, Integer.valueOf(C_Greeting_ID));
		}
	}

	/** Get Anrede.
		@return Anrede zum Druck auf Korrespondenz
	  */
	@Override
	public int getC_Greeting_ID ()
	{
		final Integer ii = (Integer)get_Value(COLUMNNAME_C_Greeting_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_PaymentTerm getC_PaymentTerm() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_PaymentTerm_ID, org.compiere.model.I_C_PaymentTerm.class);
	}

	@Override
	public void setC_PaymentTerm(final org.compiere.model.I_C_PaymentTerm C_PaymentTerm)
	{
		set_ValueFromPO(COLUMNNAME_C_PaymentTerm_ID, org.compiere.model.I_C_PaymentTerm.class, C_PaymentTerm);
	}

	/** Set Zahlungskondition.
		@param C_PaymentTerm_ID
		Die Bedingungen für die Bezahlung dieses Vorgangs
	  */
	@Override
	public void setC_PaymentTerm_ID (final int C_PaymentTerm_ID)
	{
		if (C_PaymentTerm_ID < 1)
		{
			set_ValueNoCheck (COLUMNNAME_C_PaymentTerm_ID, null);
		}
		else
		{
			set_ValueNoCheck (COLUMNNAME_C_PaymentTerm_ID, Integer.valueOf(C_PaymentTerm_ID));
		}
	}

	/** Get Zahlungskondition.
		@return Die Bedingungen für die Bezahlung dieses Vorgangs
	  */
	@Override
	public int getC_PaymentTerm_ID ()
	{
		final Integer ii = (Integer)get_Value(COLUMNNAME_C_PaymentTerm_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Region getC_Region() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Region_ID, org.compiere.model.I_C_Region.class);
	}

	@Override
	public void setC_Region(final org.compiere.model.I_C_Region C_Region)
	{
		set_ValueFromPO(COLUMNNAME_C_Region_ID, org.compiere.model.I_C_Region.class, C_Region);
	}

	/** Set Region.
		@param C_Region_ID
		Identifiziert eine geographische Region
	  */
	@Override
	public void setC_Region_ID (final int C_Region_ID)
	{
		if (C_Region_ID < 1)
		{
			set_ValueNoCheck (COLUMNNAME_C_Region_ID, null);
		}
		else
		{
			set_ValueNoCheck (COLUMNNAME_C_Region_ID, Integer.valueOf(C_Region_ID));
		}
	}

	/** Get Region.
		@return Identifiziert eine geographische Region
	  */
	@Override
	public int getC_Region_ID ()
	{
		final Integer ii = (Integer)get_Value(COLUMNNAME_C_Region_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	/** Set Ort.
		@param City
		Name des Ortes
	  */
	@Override
	public void setCity (final java.lang.String City)
	{
		set_ValueNoCheck (COLUMNNAME_City, City);
	}

	/** Get Ort.
		@return Name des Ortes
	  */
	@Override
	public java.lang.String getCity ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_City);
	}

	/** Set Bemerkungen.
		@param Comments
		Kommentar oder zusätzliche Information
	  */
	@Override
	public void setComments (final java.lang.String Comments)
	{
		set_ValueNoCheck (COLUMNNAME_Comments, Comments);
	}

	/** Get Bemerkungen.
		@return Kommentar oder zusätzliche Information
	  */
	@Override
	public java.lang.String getComments ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Comments);
	}

	/** Set Firmenname.
		@param Companyname Firmenname	  */
	@Override
	public void setCompanyname (final java.lang.String Companyname)
	{
		set_ValueNoCheck (COLUMNNAME_Companyname, Companyname);
	}

	/** Get Firmenname.
		@return Firmenname	  */
	@Override
	public java.lang.String getCompanyname ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Companyname);
	}

	/** Set Kontakt-Beschreibung.
		@param ContactDescription
		Beschreibung des Kontaktes
	  */
	@Override
	public void setContactDescription (final java.lang.String ContactDescription)
	{
		set_ValueNoCheck (COLUMNNAME_ContactDescription, ContactDescription);
	}

	/** Get Kontakt-Beschreibung.
		@return Beschreibung des Kontaktes
	  */
	@Override
	public java.lang.String getContactDescription ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_ContactDescription);
	}

	/** Set Nachname.
		@param ContactName
		Name des Geschäftspartner-Kontaktes
	  */
	@Override
	public void setContactName (final java.lang.String ContactName)
	{
		set_ValueNoCheck (COLUMNNAME_ContactName, ContactName);
	}

	/** Get Nachname.
		@return Name des Geschäftspartner-Kontaktes
	  */
	@Override
	public java.lang.String getContactName ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_ContactName);
	}

	/** Set Land.
		@param CountryName
		Land
	  */
	@Override
	public void setCountryName (final java.lang.String CountryName)
	{
		set_ValueNoCheck (COLUMNNAME_CountryName, CountryName);
	}

	/** Get Land.
		@return Land
	  */
	@Override
	public java.lang.String getCountryName ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_CountryName);
	}

	/** Set Auftrag anlegen.
		@param CreateSO Auftrag anlegen	  */
	@Override
	public void setCreateSO (final java.lang.String CreateSO)
	{
		set_Value (COLUMNNAME_CreateSO, CreateSO);
	}

	/** Get Auftrag anlegen.
		@return Auftrag anlegen	  */
	@Override
	public java.lang.String getCreateSO ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_CreateSO);
	}

	/** Set creditused.
		@param creditused creditused	  */
	@Override
	public void setcreditused (final java.math.BigDecimal creditused)
	{
		set_ValueNoCheck (COLUMNNAME_creditused, creditused);
	}

	/** Get creditused.
		@return creditused	  */
	@Override
	public java.math.BigDecimal getcreditused ()
	{
		final BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_creditused);
		if (bd == null)
		{
			return Env.ZERO;
		}
		return bd;
	}

	/** Set Lieferung durch.
		@param DeliveryViaRule
		Wie der Auftrag geliefert wird
	  */
	@Override
	public void setDeliveryViaRule (final boolean DeliveryViaRule)
	{
		set_ValueNoCheck (COLUMNNAME_DeliveryViaRule, Boolean.valueOf(DeliveryViaRule));
	}

	/** Get Lieferung durch.
		@return Wie der Auftrag geliefert wird
	  */
	@Override
	public boolean isDeliveryViaRule ()
	{
		final Object oo = get_Value(COLUMNNAME_DeliveryViaRule);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
			{
				return ((Boolean)oo).booleanValue();
			}
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Beschreibung.
		@param Description Beschreibung	  */
	@Override
	public void setDescription (final java.lang.String Description)
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

	/** Set Downline ENKD.
		@param dl_endk Downline ENKD	  */
	@Override
	public void setdl_endk (final int dl_endk)
	{
		set_ValueNoCheck (COLUMNNAME_dl_endk, Integer.valueOf(dl_endk));
	}

	/** Get Downline ENKD.
		@return Downline ENKD	  */
	@Override
	public int getdl_endk ()
	{
		final Integer ii = (Integer)get_Value(COLUMNNAME_dl_endk);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	/** Set Downline VP.
		@param dl_vp Downline VP	  */
	@Override
	public void setdl_vp (final int dl_vp)
	{
		set_ValueNoCheck (COLUMNNAME_dl_vp, Integer.valueOf(dl_vp));
	}

	/** Get Downline VP.
		@return Downline VP	  */
	@Override
	public int getdl_vp ()
	{
		final Integer ii = (Integer)get_Value(COLUMNNAME_dl_vp);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	/** Set Ebenenbonus Punkte.
		@param ebenenbonus Ebenenbonus Punkte	  */
	@Override
	public void setebenenbonus (final java.math.BigDecimal ebenenbonus)
	{
		set_ValueNoCheck (COLUMNNAME_ebenenbonus, ebenenbonus);
	}

	/** Get Ebenenbonus Punkte.
		@return Ebenenbonus Punkte	  */
	@Override
	public java.math.BigDecimal getebenenbonus ()
	{
		final BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ebenenbonus);
		if (bd == null)
		{
			return Env.ZERO;
		}
		return bd;
	}

	/** Set EMail.
		@param EMail
		EMail-Adresse
	  */
	@Override
	public void setEMail (final java.lang.String EMail)
	{
		set_ValueNoCheck (COLUMNNAME_EMail, EMail);
	}

	/** Get EMail.
		@return EMail-Adresse
	  */
	@Override
	public java.lang.String getEMail ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_EMail);
	}

	/** Set EMail Nutzer-ID.
		@param EMailUser
		Nutzer-Name/Konto (ID) im EMail-System
	  */
	@Override
	public void setEMailUser (final java.lang.String EMailUser)
	{
		set_ValueNoCheck (COLUMNNAME_EMailUser, EMailUser);
	}

	/** Get EMail Nutzer-ID.
		@return Nutzer-Name/Konto (ID) im EMail-System
	  */
	@Override
	public java.lang.String getEMailUser ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_EMailUser);
	}

	/** Set Fax.
		@param Fax
		Faxnummer
	  */
	@Override
	public void setFax (final java.lang.String Fax)
	{
		set_ValueNoCheck (COLUMNNAME_Fax, Fax);
	}

	/** Get Fax.
		@return Faxnummer
	  */
	@Override
	public java.lang.String getFax ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Fax);
	}

	/** Set Vorname.
		@param Firstname
		Vorname
	  */
	@Override
	public void setFirstname (final java.lang.String Firstname)
	{
		set_ValueNoCheck (COLUMNNAME_Firstname, Firstname);
	}

	/** Get Vorname.
		@return Vorname
	  */
	@Override
	public java.lang.String getFirstname ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Firstname);
	}

	/** Set Erster Verkauf.
		@param FirstSale
		Datum des Ersten Verkaufs
	  */
	@Override
	public void setFirstSale (final java.sql.Timestamp FirstSale)
	{
		set_ValueNoCheck (COLUMNNAME_FirstSale, FirstSale);
	}

	/** Get Erster Verkauf.
		@return Datum des Ersten Verkaufs
	  */
	@Override
	public java.sql.Timestamp getFirstSale ()
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_FirstSale);
	}

	/** Set Frachtkostenberechnung.
		@param FreightCostRule
		Methode zur Berechnung von Frachtkosten
	  */
	@Override
	public void setFreightCostRule (final boolean FreightCostRule)
	{
		set_ValueNoCheck (COLUMNNAME_FreightCostRule, Boolean.valueOf(FreightCostRule));
	}

	/** Get Frachtkostenberechnung.
		@return Methode zur Berechnung von Frachtkosten
	  */
	@Override
	public boolean isFreightCostRule ()
	{
		final Object oo = get_Value(COLUMNNAME_FreightCostRule);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
			{
				return ((Boolean)oo).booleanValue();
			}
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Rechnungs-Adresse.
		@param IsBillTo
		Rechnungs-Adresse für diesen Geschäftspartner
	  */
	@Override
	public void setIsBillTo (final boolean IsBillTo)
	{
		set_ValueNoCheck (COLUMNNAME_IsBillTo, Boolean.valueOf(IsBillTo));
	}

	/** Get Rechnungs-Adresse.
		@return Rechnungs-Adresse für diesen Geschäftspartner
	  */
	@Override
	public boolean isBillTo ()
	{
		final Object oo = get_Value(COLUMNNAME_IsBillTo);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
			{
				return ((Boolean)oo).booleanValue();
			}
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Rechnung Standard Adresse.
		@param IsBillToDefault Rechnung Standard Adresse	  */
	@Override
	public void setIsBillToDefault (final boolean IsBillToDefault)
	{
		set_ValueNoCheck (COLUMNNAME_IsBillToDefault, Boolean.valueOf(IsBillToDefault));
	}

	/** Get Rechnung Standard Adresse.
		@return Rechnung Standard Adresse	  */
	@Override
	public boolean isBillToDefault ()
	{
		final Object oo = get_Value(COLUMNNAME_IsBillToDefault);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
			{
				return ((Boolean)oo).booleanValue();
			}
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsCompany.
		@param IsCompany IsCompany	  */
	@Override
	public void setIsCompany (final boolean IsCompany)
	{
		set_ValueNoCheck (COLUMNNAME_IsCompany, Boolean.valueOf(IsCompany));
	}

	/** Get IsCompany.
		@return IsCompany	  */
	@Override
	public boolean isCompany ()
	{
		final Object oo = get_Value(COLUMNNAME_IsCompany);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
			{
				return ((Boolean)oo).booleanValue();
			}
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Kunde.
		@param IsCustomer
		Zeigt an, ob dieser Geschäftspartner ein Kunde ist
	  */
	@Override
	public void setIsCustomer (final boolean IsCustomer)
	{
		set_Value (COLUMNNAME_IsCustomer, Boolean.valueOf(IsCustomer));
	}

	/** Get Kunde.
		@return Zeigt an, ob dieser Geschäftspartner ein Kunde ist
	  */
	@Override
	public boolean isCustomer ()
	{
		final Object oo = get_Value(COLUMNNAME_IsCustomer);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
			{
				return ((Boolean)oo).booleanValue();
			}
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Standard-Ansprechpartner.
		@param IsDefaultContact Standard-Ansprechpartner	  */
	@Override
	public void setIsDefaultContact (final boolean IsDefaultContact)
	{
		set_ValueNoCheck (COLUMNNAME_IsDefaultContact, Boolean.valueOf(IsDefaultContact));
	}

	/** Get Standard-Ansprechpartner.
		@return Standard-Ansprechpartner	  */
	@Override
	public boolean isDefaultContact ()
	{
		final Object oo = get_Value(COLUMNNAME_IsDefaultContact);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
			{
				return ((Boolean)oo).booleanValue();
			}
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Default Location.
		@param IsDefaultLocation Default Location	  */
	@Override
	public void setIsDefaultLocation (final boolean IsDefaultLocation)
	{
		set_ValueNoCheck (COLUMNNAME_IsDefaultLocation, Boolean.valueOf(IsDefaultLocation));
	}

	/** Get Default Location.
		@return Default Location	  */
	@Override
	public boolean isDefaultLocation ()
	{
		final Object oo = get_Value(COLUMNNAME_IsDefaultLocation);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
			{
				return ((Boolean)oo).booleanValue();
			}
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set One time transaction.
		@param IsOneTime One time transaction	  */
	@Override
	public void setIsOneTime (final boolean IsOneTime)
	{
		set_ValueNoCheck (COLUMNNAME_IsOneTime, Boolean.valueOf(IsOneTime));
	}

	/** Get One time transaction.
		@return One time transaction	  */
	@Override
	public boolean isOneTime ()
	{
		final Object oo = get_Value(COLUMNNAME_IsOneTime);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
			{
				return ((Boolean)oo).booleanValue();
			}
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Aktiver Interessent/Kunde.
		@param IsProspect
		Kennzeichnet einen Interessenten oder Kunden
	  */
	@Override
	public void setIsProspect (final boolean IsProspect)
	{
		set_ValueNoCheck (COLUMNNAME_IsProspect, Boolean.valueOf(IsProspect));
	}

	/** Get Aktiver Interessent/Kunde.
		@return Kennzeichnet einen Interessenten oder Kunden
	  */
	@Override
	public boolean isProspect ()
	{
		final Object oo = get_Value(COLUMNNAME_IsProspect);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
			{
				return ((Boolean)oo).booleanValue();
			}
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Vertriebsbeauftragter.
		@param IsSalesRep
		Indicates if  the business partner is a sales representative or company agent
	  */
	@Override
	public void setIsSalesRep (final boolean IsSalesRep)
	{
		set_Value (COLUMNNAME_IsSalesRep, Boolean.valueOf(IsSalesRep));
	}

	/** Get Vertriebsbeauftragter.
		@return Indicates if  the business partner is a sales representative or company agent
	  */
	@Override
	public boolean isSalesRep ()
	{
		final Object oo = get_Value(COLUMNNAME_IsSalesRep);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
			{
				return ((Boolean)oo).booleanValue();
			}
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Liefer-Adresse.
		@param IsShipTo
		Liefer-Adresse für den Geschäftspartner
	  */
	@Override
	public void setIsShipTo (final boolean IsShipTo)
	{
		set_ValueNoCheck (COLUMNNAME_IsShipTo, Boolean.valueOf(IsShipTo));
	}

	/** Get Liefer-Adresse.
		@return Liefer-Adresse für den Geschäftspartner
	  */
	@Override
	public boolean isShipTo ()
	{
		final Object oo = get_Value(COLUMNNAME_IsShipTo);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
			{
				return ((Boolean)oo).booleanValue();
			}
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Liefer Standard Adresse.
		@param IsShipToDefault Liefer Standard Adresse	  */
	@Override
	public void setIsShipToDefault (final boolean IsShipToDefault)
	{
		set_ValueNoCheck (COLUMNNAME_IsShipToDefault, Boolean.valueOf(IsShipToDefault));
	}

	/** Get Liefer Standard Adresse.
		@return Liefer Standard Adresse	  */
	@Override
	public boolean isShipToDefault ()
	{
		final Object oo = get_Value(COLUMNNAME_IsShipToDefault);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
			{
				return ((Boolean)oo).booleanValue();
			}
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Lieferant.
		@param IsVendor
		Zeigt an, ob dieser Geschaäftspartner ein Lieferant ist
	  */
	@Override
	public void setIsVendor (final boolean IsVendor)
	{
		set_Value (COLUMNNAME_IsVendor, Boolean.valueOf(IsVendor));
	}

	/** Get Lieferant.
		@return Zeigt an, ob dieser Geschaäftspartner ein Lieferant ist
	  */
	@Override
	public boolean isVendor ()
	{
		final Object oo = get_Value(COLUMNNAME_IsVendor);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
			{
				return ((Boolean)oo).booleanValue();
			}
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Nachname.
		@param Lastname Nachname	  */
	@Override
	public void setLastname (final java.lang.String Lastname)
	{
		set_ValueNoCheck (COLUMNNAME_Lastname, Lastname);
	}

	/** Get Nachname.
		@return Nachname	  */
	@Override
	public java.lang.String getLastname ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Lastname);
	}

	/** Set Leistungsbonus Punkte.
		@param leistungsbonus Leistungsbonus Punkte	  */
	@Override
	public void setleistungsbonus (final java.math.BigDecimal leistungsbonus)
	{
		set_ValueNoCheck (COLUMNNAME_leistungsbonus, leistungsbonus);
	}

	/** Get Leistungsbonus Punkte.
		@return Leistungsbonus Punkte	  */
	@Override
	public java.math.BigDecimal getleistungsbonus ()
	{
		final BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_leistungsbonus);
		if (bd == null)
		{
			return Env.ZERO;
		}
		return bd;
	}

	/** Set lifetimevalue.
		@param lifetimevalue lifetimevalue	  */
	@Override
	public void setlifetimevalue (final java.math.BigDecimal lifetimevalue)
	{
		set_ValueNoCheck (COLUMNNAME_lifetimevalue, lifetimevalue);
	}

	/** Get lifetimevalue.
		@return lifetimevalue	  */
	@Override
	public java.math.BigDecimal getlifetimevalue ()
	{
		final BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_lifetimevalue);
		if (bd == null)
		{
			return Env.ZERO;
		}
		return bd;
	}

	@Override
	public org.compiere.model.I_M_DiscountSchema getM_DiscountSchema() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_DiscountSchema_ID, org.compiere.model.I_M_DiscountSchema.class);
	}

	@Override
	public void setM_DiscountSchema(final org.compiere.model.I_M_DiscountSchema M_DiscountSchema)
	{
		set_ValueFromPO(COLUMNNAME_M_DiscountSchema_ID, org.compiere.model.I_M_DiscountSchema.class, M_DiscountSchema);
	}

	/** Set Rabatt-Schema.
		@param M_DiscountSchema_ID
		Schema um den prozentualen Rabatt zu berechnen
	  */
	@Override
	public void setM_DiscountSchema_ID (final int M_DiscountSchema_ID)
	{
		if (M_DiscountSchema_ID < 1)
		{
			set_ValueNoCheck (COLUMNNAME_M_DiscountSchema_ID, null);
		}
		else
		{
			set_ValueNoCheck (COLUMNNAME_M_DiscountSchema_ID, Integer.valueOf(M_DiscountSchema_ID));
		}
	}

	/** Get Rabatt-Schema.
		@return Schema um den prozentualen Rabatt zu berechnen
	  */
	@Override
	public int getM_DiscountSchema_ID ()
	{
		final Integer ii = (Integer)get_Value(COLUMNNAME_M_DiscountSchema_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	/** Set Name 2.
		@param Name2
		Zusätzliche Bezeichnung
	  */
	@Override
	public void setName2 (final java.lang.String Name2)
	{
		set_ValueNoCheck (COLUMNNAME_Name2, Name2);
	}

	/** Get Name 2.
		@return Zusätzliche Bezeichnung
	  */
	@Override
	public java.lang.String getName2 ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name2);
	}

	/** Set Benachrichtigungs-Art.
		@param NotificationType
		Art der Benachrichtigung
	  */
	@Override
	public void setNotificationType (final boolean NotificationType)
	{
		set_ValueNoCheck (COLUMNNAME_NotificationType, Boolean.valueOf(NotificationType));
	}

	/** Get Benachrichtigungs-Art.
		@return Art der Benachrichtigung
	  */
	@Override
	public boolean isNotificationType ()
	{
		final Object oo = get_Value(COLUMNNAME_NotificationType);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
			{
				return ((Boolean)oo).booleanValue();
			}
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Open Amount.
		@param OpenAmt
		Open item amount
	  */
	@Override
	public void setOpenAmt (final java.math.BigDecimal OpenAmt)
	{
		set_ValueNoCheck (COLUMNNAME_OpenAmt, OpenAmt);
	}

	/** Get Open Amount.
		@return Open item amount
	  */
	@Override
	public java.math.BigDecimal getOpenAmt ()
	{
		final BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_OpenAmt);
		if (bd == null)
		{
			return Env.ZERO;
		}
		return bd;
	}

	/** Set Phone.
		@param Phone
		Identifies a telephone number
	  */
	@Override
	public void setPhone (final java.lang.String Phone)
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

	/** Set Telefon (alternativ).
		@param Phone2
		Alternative Telefonnummer
	  */
	@Override
	public void setPhone2 (final java.lang.String Phone2)
	{
		set_ValueNoCheck (COLUMNNAME_Phone2, Phone2);
	}

	/** Get Telefon (alternativ).
		@return Alternative Telefonnummer
	  */
	@Override
	public java.lang.String getPhone2 ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Phone2);
	}

	/** Set PLZ.
		@param Postal
		Postleitzahl
	  */
	@Override
	public void setPostal (final java.lang.String Postal)
	{
		set_ValueNoCheck (COLUMNNAME_Postal, Postal);
	}

	/** Get PLZ.
		@return Postleitzahl
	  */
	@Override
	public java.lang.String getPostal ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Postal);
	}

	/** Set Provision Kennz..
		@param prov Provision Kennz.	  */
	@Override
	public void setprov (final java.lang.String prov)
	{
		set_ValueNoCheck (COLUMNNAME_prov, prov);
	}

	/** Get Provision Kennz..
		@return Provision Kennz.	  */
	@Override
	public java.lang.String getprov ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_prov);
	}

	/** Set revenued30.
		@param revenued30 revenued30	  */
	@Override
	public void setrevenued30 (final java.math.BigDecimal revenued30)
	{
		set_ValueNoCheck (COLUMNNAME_revenued30, revenued30);
	}

	/** Get revenued30.
		@return revenued30	  */
	@Override
	public java.math.BigDecimal getrevenued30 ()
	{
		final BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_revenued30);
		if (bd == null)
		{
			return Env.ZERO;
		}
		return bd;
	}

	/** Set revenuem0.
		@param revenuem0 revenuem0	  */
	@Override
	public void setrevenuem0 (final java.math.BigDecimal revenuem0)
	{
		set_ValueNoCheck (COLUMNNAME_revenuem0, revenuem0);
	}

	/** Get revenuem0.
		@return revenuem0	  */
	@Override
	public java.math.BigDecimal getrevenuem0 ()
	{
		final BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_revenuem0);
		if (bd == null)
		{
			return Env.ZERO;
		}
		return bd;
	}

	/** Set revenuem1.
		@param revenuem1 revenuem1	  */
	@Override
	public void setrevenuem1 (final java.math.BigDecimal revenuem1)
	{
		set_ValueNoCheck (COLUMNNAME_revenuem1, revenuem1);
	}

	/** Get revenuem1.
		@return revenuem1	  */
	@Override
	public java.math.BigDecimal getrevenuem1 ()
	{
		final BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_revenuem1);
		if (bd == null)
		{
			return Env.ZERO;
		}
		return bd;
	}

	/** Set revenuem10.
		@param revenuem10 revenuem10	  */
	@Override
	public void setrevenuem10 (final java.math.BigDecimal revenuem10)
	{
		set_ValueNoCheck (COLUMNNAME_revenuem10, revenuem10);
	}

	/** Get revenuem10.
		@return revenuem10	  */
	@Override
	public java.math.BigDecimal getrevenuem10 ()
	{
		final BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_revenuem10);
		if (bd == null)
		{
			return Env.ZERO;
		}
		return bd;
	}

	/** Set revenuem11.
		@param revenuem11 revenuem11	  */
	@Override
	public void setrevenuem11 (final java.math.BigDecimal revenuem11)
	{
		set_ValueNoCheck (COLUMNNAME_revenuem11, revenuem11);
	}

	/** Get revenuem11.
		@return revenuem11	  */
	@Override
	public java.math.BigDecimal getrevenuem11 ()
	{
		final BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_revenuem11);
		if (bd == null)
		{
			return Env.ZERO;
		}
		return bd;
	}

	/** Set revenuem12.
		@param revenuem12 revenuem12	  */
	@Override
	public void setrevenuem12 (final java.math.BigDecimal revenuem12)
	{
		set_ValueNoCheck (COLUMNNAME_revenuem12, revenuem12);
	}

	/** Get revenuem12.
		@return revenuem12	  */
	@Override
	public java.math.BigDecimal getrevenuem12 ()
	{
		final BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_revenuem12);
		if (bd == null)
		{
			return Env.ZERO;
		}
		return bd;
	}

	/** Set revenuem2.
		@param revenuem2 revenuem2	  */
	@Override
	public void setrevenuem2 (final java.math.BigDecimal revenuem2)
	{
		set_ValueNoCheck (COLUMNNAME_revenuem2, revenuem2);
	}

	/** Get revenuem2.
		@return revenuem2	  */
	@Override
	public java.math.BigDecimal getrevenuem2 ()
	{
		final BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_revenuem2);
		if (bd == null)
		{
			return Env.ZERO;
		}
		return bd;
	}

	/** Set revenuem3.
		@param revenuem3 revenuem3	  */
	@Override
	public void setrevenuem3 (final java.math.BigDecimal revenuem3)
	{
		set_ValueNoCheck (COLUMNNAME_revenuem3, revenuem3);
	}

	/** Get revenuem3.
		@return revenuem3	  */
	@Override
	public java.math.BigDecimal getrevenuem3 ()
	{
		final BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_revenuem3);
		if (bd == null)
		{
			return Env.ZERO;
		}
		return bd;
	}

	/** Set revenuem4.
		@param revenuem4 revenuem4	  */
	@Override
	public void setrevenuem4 (final java.math.BigDecimal revenuem4)
	{
		set_ValueNoCheck (COLUMNNAME_revenuem4, revenuem4);
	}

	/** Get revenuem4.
		@return revenuem4	  */
	@Override
	public java.math.BigDecimal getrevenuem4 ()
	{
		final BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_revenuem4);
		if (bd == null)
		{
			return Env.ZERO;
		}
		return bd;
	}

	/** Set revenuem5.
		@param revenuem5 revenuem5	  */
	@Override
	public void setrevenuem5 (final java.math.BigDecimal revenuem5)
	{
		set_ValueNoCheck (COLUMNNAME_revenuem5, revenuem5);
	}

	/** Get revenuem5.
		@return revenuem5	  */
	@Override
	public java.math.BigDecimal getrevenuem5 ()
	{
		final BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_revenuem5);
		if (bd == null)
		{
			return Env.ZERO;
		}
		return bd;
	}

	/** Set revenuem6.
		@param revenuem6 revenuem6	  */
	@Override
	public void setrevenuem6 (final java.math.BigDecimal revenuem6)
	{
		set_ValueNoCheck (COLUMNNAME_revenuem6, revenuem6);
	}

	/** Get revenuem6.
		@return revenuem6	  */
	@Override
	public java.math.BigDecimal getrevenuem6 ()
	{
		final BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_revenuem6);
		if (bd == null)
		{
			return Env.ZERO;
		}
		return bd;
	}

	/** Set revenuem7.
		@param revenuem7 revenuem7	  */
	@Override
	public void setrevenuem7 (final java.math.BigDecimal revenuem7)
	{
		set_ValueNoCheck (COLUMNNAME_revenuem7, revenuem7);
	}

	/** Get revenuem7.
		@return revenuem7	  */
	@Override
	public java.math.BigDecimal getrevenuem7 ()
	{
		final BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_revenuem7);
		if (bd == null)
		{
			return Env.ZERO;
		}
		return bd;
	}

	/** Set revenuem8.
		@param revenuem8 revenuem8	  */
	@Override
	public void setrevenuem8 (final java.math.BigDecimal revenuem8)
	{
		set_ValueNoCheck (COLUMNNAME_revenuem8, revenuem8);
	}

	/** Get revenuem8.
		@return revenuem8	  */
	@Override
	public java.math.BigDecimal getrevenuem8 ()
	{
		final BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_revenuem8);
		if (bd == null)
		{
			return Env.ZERO;
		}
		return bd;
	}

	/** Set revenuem9.
		@param revenuem9 revenuem9	  */
	@Override
	public void setrevenuem9 (final java.math.BigDecimal revenuem9)
	{
		set_ValueNoCheck (COLUMNNAME_revenuem9, revenuem9);
	}

	/** Get revenuem9.
		@return revenuem9	  */
	@Override
	public java.math.BigDecimal getrevenuem9 ()
	{
		final BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_revenuem9);
		if (bd == null)
		{
			return Env.ZERO;
		}
		return bd;
	}

	/** Set Umsatz 12 Monate.
		@param RevenueOneYear Umsatz 12 Monate	  */
	@Override
	public void setRevenueOneYear (final java.math.BigDecimal RevenueOneYear)
	{
		set_Value (COLUMNNAME_RevenueOneYear, RevenueOneYear);
	}

	/** Get Umsatz 12 Monate.
		@return Umsatz 12 Monate	  */
	@Override
	public java.math.BigDecimal getRevenueOneYear ()
	{
		final BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_RevenueOneYear);
		if (bd == null)
		{
			return Env.ZERO;
		}
		return bd;
	}

	/** Set revenueq0.
		@param revenueq0 revenueq0	  */
	@Override
	public void setrevenueq0 (final java.math.BigDecimal revenueq0)
	{
		set_ValueNoCheck (COLUMNNAME_revenueq0, revenueq0);
	}

	/** Get revenueq0.
		@return revenueq0	  */
	@Override
	public java.math.BigDecimal getrevenueq0 ()
	{
		final BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_revenueq0);
		if (bd == null)
		{
			return Env.ZERO;
		}
		return bd;
	}

	/** Set revenueq1.
		@param revenueq1 revenueq1	  */
	@Override
	public void setrevenueq1 (final java.math.BigDecimal revenueq1)
	{
		set_ValueNoCheck (COLUMNNAME_revenueq1, revenueq1);
	}

	/** Get revenueq1.
		@return revenueq1	  */
	@Override
	public java.math.BigDecimal getrevenueq1 ()
	{
		final BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_revenueq1);
		if (bd == null)
		{
			return Env.ZERO;
		}
		return bd;
	}

	/** Set revenueq2.
		@param revenueq2 revenueq2	  */
	@Override
	public void setrevenueq2 (final java.math.BigDecimal revenueq2)
	{
		set_ValueNoCheck (COLUMNNAME_revenueq2, revenueq2);
	}

	/** Get revenueq2.
		@return revenueq2	  */
	@Override
	public java.math.BigDecimal getrevenueq2 ()
	{
		final BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_revenueq2);
		if (bd == null)
		{
			return Env.ZERO;
		}
		return bd;
	}

	/** Set revenueq3.
		@param revenueq3 revenueq3	  */
	@Override
	public void setrevenueq3 (final java.math.BigDecimal revenueq3)
	{
		set_ValueNoCheck (COLUMNNAME_revenueq3, revenueq3);
	}

	/** Get revenueq3.
		@return revenueq3	  */
	@Override
	public java.math.BigDecimal getrevenueq3 ()
	{
		final BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_revenueq3);
		if (bd == null)
		{
			return Env.ZERO;
		}
		return bd;
	}

	/** Set revenueq4.
		@param revenueq4 revenueq4	  */
	@Override
	public void setrevenueq4 (final java.math.BigDecimal revenueq4)
	{
		set_ValueNoCheck (COLUMNNAME_revenueq4, revenueq4);
	}

	/** Get revenueq4.
		@return revenueq4	  */
	@Override
	public java.math.BigDecimal getrevenueq4 ()
	{
		final BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_revenueq4);
		if (bd == null)
		{
			return Env.ZERO;
		}
		return bd;
	}

	/** Set revenuey0.
		@param revenuey0 revenuey0	  */
	@Override
	public void setrevenuey0 (final java.math.BigDecimal revenuey0)
	{
		set_ValueNoCheck (COLUMNNAME_revenuey0, revenuey0);
	}

	/** Get revenuey0.
		@return revenuey0	  */
	@Override
	public java.math.BigDecimal getrevenuey0 ()
	{
		final BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_revenuey0);
		if (bd == null)
		{
			return Env.ZERO;
		}
		return bd;
	}

	/** Set revenuey1.
		@param revenuey1 revenuey1	  */
	@Override
	public void setrevenuey1 (final java.math.BigDecimal revenuey1)
	{
		set_ValueNoCheck (COLUMNNAME_revenuey1, revenuey1);
	}

	/** Get revenuey1.
		@return revenuey1	  */
	@Override
	public java.math.BigDecimal getrevenuey1 ()
	{
		final BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_revenuey1);
		if (bd == null)
		{
			return Env.ZERO;
		}
		return bd;
	}

	/** Set revenuey2.
		@param revenuey2 revenuey2	  */
	@Override
	public void setrevenuey2 (final java.math.BigDecimal revenuey2)
	{
		set_ValueNoCheck (COLUMNNAME_revenuey2, revenuey2);
	}

	/** Get revenuey2.
		@return revenuey2	  */
	@Override
	public java.math.BigDecimal getrevenuey2 ()
	{
		final BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_revenuey2);
		if (bd == null)
		{
			return Env.ZERO;
		}
		return bd;
	}

	/** Set revenuey3.
		@param revenuey3 revenuey3	  */
	@Override
	public void setrevenuey3 (final java.math.BigDecimal revenuey3)
	{
		set_ValueNoCheck (COLUMNNAME_revenuey3, revenuey3);
	}

	/** Get revenuey3.
		@return revenuey3	  */
	@Override
	public java.math.BigDecimal getrevenuey3 ()
	{
		final BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_revenuey3);
		if (bd == null)
		{
			return Env.ZERO;
		}
		return bd;
	}

	/** Set revenuey4.
		@param revenuey4 revenuey4	  */
	@Override
	public void setrevenuey4 (final java.math.BigDecimal revenuey4)
	{
		set_ValueNoCheck (COLUMNNAME_revenuey4, revenuey4);
	}

	/** Get revenuey4.
		@return revenuey4	  */
	@Override
	public java.math.BigDecimal getrevenuey4 ()
	{
		final BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_revenuey4);
		if (bd == null)
		{
			return Env.ZERO;
		}
		return bd;
	}

	/** Set revenuey5.
		@param revenuey5 revenuey5	  */
	@Override
	public void setrevenuey5 (final java.math.BigDecimal revenuey5)
	{
		set_ValueNoCheck (COLUMNNAME_revenuey5, revenuey5);
	}

	/** Get revenuey5.
		@return revenuey5	  */
	@Override
	public java.math.BigDecimal getrevenuey5 ()
	{
		final BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_revenuey5);
		if (bd == null)
		{
			return Env.ZERO;
		}
		return bd;
	}

	/** Set Sales Representative.
		@param SalesRep_ID
		Sales Representative or Company Agent
	  */
	@Override
	public void setSalesRep_ID (final java.lang.String SalesRep_ID)
	{
		set_ValueNoCheck (COLUMNNAME_SalesRep_ID, SalesRep_ID);
	}

	/** Get Sales Representative.
		@return Sales Representative or Company Agent
	  */
	@Override
	public java.lang.String getSalesRep_ID ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_SalesRep_ID);
	}

	/** Set Suche.
		@param Search Suche	  */
	@Override
	public void setSearch (final java.lang.String Search)
	{
		set_Value (COLUMNNAME_Search, Search);
	}

	/** Get Suche.
		@return Suche	  */
	@Override
	public java.lang.String getSearch ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Search);
	}

	/**
	 * Set Search Location.
	 *
	 * @param Search_Location
	 *            Search Location
	 */
	@Override
	public void setSearch_Location(final java.lang.String Search_Location)
	{
		set_Value(COLUMNNAME_Search_Location, Search_Location);
	}

	/**
	 * Get Search Location.
	 *
	 * @return Search Location
	 */
	@Override
	public java.lang.String getSearch_Location()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Search_Location);
	}

	/** Set Kredit gewährt.
		@param SO_CreditUsed
		Gegenwärtiger Aussenstand
	  */
	@Override
	public void setSO_CreditUsed (final java.math.BigDecimal SO_CreditUsed)
	{
		set_ValueNoCheck (COLUMNNAME_SO_CreditUsed, SO_CreditUsed);
	}

	/** Get Kredit gewährt.
		@return Gegenwärtiger Aussenstand
	  */
	@Override
	public java.math.BigDecimal getSO_CreditUsed ()
	{
		final BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SO_CreditUsed);
		if (bd == null)
		{
			return Env.ZERO;
		}
		return bd;
	}

	/** SOCreditStatus AD_Reference_ID=289 */
	public static final int SOCREDITSTATUS_AD_Reference_ID=289;
	/** Kredit Stop = S */
	public static final String SOCREDITSTATUS_KreditStop = "S";
	/** Kredit Halt = H */
	public static final String SOCREDITSTATUS_KreditHalt = "H";
	/** Kredit beobachten = W */
	public static final String SOCREDITSTATUS_KreditBeobachten = "W";
	/** Keine Kredit-Prüfung = X */
	public static final String SOCREDITSTATUS_KeineKredit_Pruefung = "X";
	/** Kredit OK = O */
	public static final String SOCREDITSTATUS_KreditOK = "O";
	/** Nur eine Rechnung = I */
	public static final String SOCREDITSTATUS_NurEineRechnung = "I";
	/** Set Kreditstatus.
		@param SOCreditStatus
		Kreditstatus des Geschäftspartners
	  */
	@Override
	public void setSOCreditStatus (final java.lang.String SOCreditStatus)
	{

		set_ValueNoCheck (COLUMNNAME_SOCreditStatus, SOCreditStatus);
	}

	/** Get Kreditstatus.
		@return Kreditstatus des Geschäftspartners
	  */
	@Override
	public java.lang.String getSOCreditStatus ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_SOCreditStatus);
	}

	/** Set Suchname.
		@param Suchname
		Alphanumeric identifier of the entity
	  */
	@Override
	public void setSuchname (final java.lang.String Suchname)
	{
		set_Value (COLUMNNAME_Suchname, Suchname);
	}

	/** Get Suchname.
		@return Alphanumeric identifier of the entity
	  */
	@Override
	public java.lang.String getSuchname ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Suchname);
	}

	/** Set Titel.
		@param Title
		Bezeichnung für diesen Eintrag
	  */
	@Override
	public void setTitle (final java.lang.String Title)
	{
		set_ValueNoCheck (COLUMNNAME_Title, Title);
	}

	/** Get Titel.
		@return Bezeichnung für diesen Eintrag
	  */
	@Override
	public java.lang.String getTitle ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Title);
	}

	/** Set Offener Saldo.
		@param TotalOpenBalance
		Gesamt der offenen Beträge in primärer Buchführungswährung
	  */
	@Override
	public void setTotalOpenBalance (final java.math.BigDecimal TotalOpenBalance)
	{
		set_Value (COLUMNNAME_TotalOpenBalance, TotalOpenBalance);
	}

	/** Get Offener Saldo.
		@return Gesamt der offenen Beträge in primärer Buchführungswährung
	  */
	@Override
	public java.math.BigDecimal getTotalOpenBalance ()
	{
		final BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalOpenBalance);
		if (bd == null)
		{
			return Env.ZERO;
		}
		return bd;
	}

	/** Set V_BPartnerCockpit_ID.
		@param V_BPartnerCockpit_ID V_BPartnerCockpit_ID	  */
	@Override
	public void setV_BPartnerCockpit_ID (final int V_BPartnerCockpit_ID)
	{
		if (V_BPartnerCockpit_ID < 1)
		{
			set_ValueNoCheck (COLUMNNAME_V_BPartnerCockpit_ID, null);
		}
		else
		{
			set_ValueNoCheck (COLUMNNAME_V_BPartnerCockpit_ID, Integer.valueOf(V_BPartnerCockpit_ID));
		}
	}

	/** Get V_BPartnerCockpit_ID.
		@return V_BPartnerCockpit_ID	  */
	@Override
	public int getV_BPartnerCockpit_ID ()
	{
		final Integer ii = (Integer)get_Value(COLUMNNAME_V_BPartnerCockpit_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	/** Set Suchschlüssel.
		@param value
		Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein
	  */
	@Override
	public void setvalue (final java.lang.String value)
	{
		set_Value (COLUMNNAME_value, value);
	}

	/** Get Suchschlüssel.
		@return Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein
	  */
	@Override
	public java.lang.String getvalue ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_value);
	}
}