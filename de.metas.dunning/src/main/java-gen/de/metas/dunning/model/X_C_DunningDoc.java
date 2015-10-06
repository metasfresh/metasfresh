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
	private static final long serialVersionUID = -449185018L;

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
			setDocumentNo (null);
			setDunningDate (new Timestamp( System.currentTimeMillis() ));
			setIsUseBPartnerAddress (false);
// N
			setProcessed (false);
// N
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

    @Override
    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_C_DunningDoc[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set Document No.
		@param DocumentNo 
		Document sequence number of the document
	  */
	@Override
	public void setDocumentNo (java.lang.String DocumentNo)
	{
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Document No.
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