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
package de.metas.letters.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Letter
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Letter extends org.compiere.model.PO implements I_C_Letter, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1182211361L;

    /** Standard Constructor */
    public X_C_Letter (Properties ctx, int C_Letter_ID, String trxName)
    {
      super (ctx, C_Letter_ID, trxName);
      /** if (C_Letter_ID == 0)
        {
			setC_Letter_ID (0);
			setIsMembershipBadgeToPrint (false);
// N
			setLetterBody (null);
        } */
    }

    /** Load Constructor */
    public X_C_Letter (Properties ctx, ResultSet rs, String trxName)
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
    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_C_Letter[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	@Override
	public de.metas.letters.model.I_AD_BoilerPlate getAD_BoilerPlate() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_BoilerPlate_ID, de.metas.letters.model.I_AD_BoilerPlate.class);
	}

	@Override
	public void setAD_BoilerPlate(de.metas.letters.model.I_AD_BoilerPlate AD_BoilerPlate)
	{
		set_ValueFromPO(COLUMNNAME_AD_BoilerPlate_ID, de.metas.letters.model.I_AD_BoilerPlate.class, AD_BoilerPlate);
	}

	/** Set Textbaustein.
		@param AD_BoilerPlate_ID Textbaustein	  */
	@Override
	public void setAD_BoilerPlate_ID (int AD_BoilerPlate_ID)
	{
		if (AD_BoilerPlate_ID < 1) 
			set_Value (COLUMNNAME_AD_BoilerPlate_ID, null);
		else 
			set_Value (COLUMNNAME_AD_BoilerPlate_ID, Integer.valueOf(AD_BoilerPlate_ID));
	}

	/** Get Textbaustein.
		@return Textbaustein	  */
	@Override
	public int getAD_BoilerPlate_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_BoilerPlate_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Table getAD_Table() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Table_ID, org.compiere.model.I_AD_Table.class);
	}

	@Override
	public void setAD_Table(org.compiere.model.I_AD_Table AD_Table)
	{
		set_ValueFromPO(COLUMNNAME_AD_Table_ID, org.compiere.model.I_AD_Table.class, AD_Table);
	}

	/** Set DB-Tabelle.
		@param AD_Table_ID 
		Database Table information
	  */
	@Override
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get DB-Tabelle.
		@return Database Table information
	  */
	@Override
	public int getAD_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
	public org.compiere.model.I_AD_User getC_BP_Contact() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BP_Contact_ID, org.compiere.model.I_AD_User.class);
	}

	@Override
	public void setC_BP_Contact(org.compiere.model.I_AD_User C_BP_Contact)
	{
		set_ValueFromPO(COLUMNNAME_C_BP_Contact_ID, org.compiere.model.I_AD_User.class, C_BP_Contact);
	}

	/** Set Contact.
		@param C_BP_Contact_ID Contact	  */
	@Override
	public void setC_BP_Contact_ID (int C_BP_Contact_ID)
	{
		if (C_BP_Contact_ID < 1) 
			set_Value (COLUMNNAME_C_BP_Contact_ID, null);
		else 
			set_Value (COLUMNNAME_C_BP_Contact_ID, Integer.valueOf(C_BP_Contact_ID));
	}

	/** Get Contact.
		@return Contact	  */
	@Override
	public int getC_BP_Contact_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BP_Contact_ID);
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

	/** Set Letter.
		@param C_Letter_ID Letter	  */
	@Override
	public void setC_Letter_ID (int C_Letter_ID)
	{
		if (C_Letter_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Letter_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Letter_ID, Integer.valueOf(C_Letter_ID));
	}

	/** Get Letter.
		@return Letter	  */
	@Override
	public int getC_Letter_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Letter_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Zu druckende Mitgliederausweise.
		@param IsMembershipBadgeToPrint Zu druckende Mitgliederausweise	  */
	@Override
	public void setIsMembershipBadgeToPrint (boolean IsMembershipBadgeToPrint)
	{
		set_Value (COLUMNNAME_IsMembershipBadgeToPrint, Boolean.valueOf(IsMembershipBadgeToPrint));
	}

	/** Get Zu druckende Mitgliederausweise.
		@return Zu druckende Mitgliederausweise	  */
	@Override
	public boolean isMembershipBadgeToPrint () 
	{
		Object oo = get_Value(COLUMNNAME_IsMembershipBadgeToPrint);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Body.
		@param LetterBody Body	  */
	@Override
	public void setLetterBody (java.lang.String LetterBody)
	{
		set_Value (COLUMNNAME_LetterBody, LetterBody);
	}

	/** Get Body.
		@return Body	  */
	@Override
	public java.lang.String getLetterBody () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_LetterBody);
	}

	/** Set Body (parsed).
		@param LetterBodyParsed Body (parsed)	  */
	@Override
	public void setLetterBodyParsed (java.lang.String LetterBodyParsed)
	{
		set_Value (COLUMNNAME_LetterBodyParsed, LetterBodyParsed);
	}

	/** Get Body (parsed).
		@return Body (parsed)	  */
	@Override
	public java.lang.String getLetterBodyParsed () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_LetterBodyParsed);
	}

	/** Set Subject.
		@param LetterSubject Subject	  */
	@Override
	public void setLetterSubject (java.lang.String LetterSubject)
	{
		set_Value (COLUMNNAME_LetterSubject, LetterSubject);
	}

	/** Get Subject.
		@return Subject	  */
	@Override
	public java.lang.String getLetterSubject () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_LetterSubject);
	}

	/** Set Datensatz-ID.
		@param Record_ID 
		Direct internal record ID
	  */
	@Override
	public void setRecord_ID (int Record_ID)
	{
		if (Record_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_Record_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
	}

	/** Get Datensatz-ID.
		@return Direct internal record ID
	  */
	@Override
	public int getRecord_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Record_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}