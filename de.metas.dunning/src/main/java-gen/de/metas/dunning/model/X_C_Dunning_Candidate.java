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

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.Env;

/** Generated Model for C_Dunning_Candidate
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Dunning_Candidate extends org.compiere.model.PO implements I_C_Dunning_Candidate, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1056863003L;

    /** Standard Constructor */
    public X_C_Dunning_Candidate (Properties ctx, int C_Dunning_Candidate_ID, String trxName)
    {
      super (ctx, C_Dunning_Candidate_ID, trxName);
      /** if (C_Dunning_Candidate_ID == 0)
        {
			setAD_Table_ID (0);
			setC_BPartner_ID (0);
			setC_BPartner_Location_ID (0);
			setC_Currency_ID (0);
			setC_Dunning_Candidate_ID (0);
			setC_DunningLevel_ID (0);
			setDueDate (new Timestamp( System.currentTimeMillis() ));
			setDunningDate (new Timestamp( System.currentTimeMillis() ));
			setIsWriteOff (false);
// N
			setProcessed (false);
// N
			setRecord_ID (0);
        } */
    }

    /** Load Constructor */
    public X_C_Dunning_Candidate (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_C_Dunning_Candidate[")
        .append(get_ID()).append("]");
      return sb.toString();
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
		Die Währung für diesen Eintrag
	  */
	@Override
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Währung.
		@return Die Währung für diesen Eintrag
	  */
	@Override
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Mahnungsdisposition.
		@param C_Dunning_Candidate_ID Mahnungsdisposition	  */
	@Override
	public void setC_Dunning_Candidate_ID (int C_Dunning_Candidate_ID)
	{
		if (C_Dunning_Candidate_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Dunning_Candidate_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Dunning_Candidate_ID, Integer.valueOf(C_Dunning_Candidate_ID));
	}

	/** Get Mahnungsdisposition.
		@return Mahnungsdisposition	  */
	@Override
	public int getC_Dunning_Candidate_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Dunning_Candidate_ID);
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

	/** Set Tage fällig.
		@param DaysDue 
		Anzahl der Tage der Fälligkeit (negativ: Fällig in Anzahl Tagen)
	  */
	@Override
	public void setDaysDue (int DaysDue)
	{
		set_Value (COLUMNNAME_DaysDue, Integer.valueOf(DaysDue));
	}

	/** Get Tage fällig.
		@return Anzahl der Tage der Fälligkeit (negativ: Fällig in Anzahl Tagen)
	  */
	@Override
	public int getDaysDue () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DaysDue);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
	
	
	
	/** Set Beleg Nr..
	@param DocumentNo 
	Document sequence number of the document
  */
	@Override
	public void setDocumentNo (java.lang.String DocumentNo)
	{
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}
	
	/** Get Beleg Nr..
		@return Document sequence number of the document
	  */
	@Override
	public java.lang.String getDocumentNo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocumentNo);
	}

	
	/** Set Datum Fälligkeit.
		@param DueDate 
		Datum, zu dem Zahlung fällig wird
	  */
	@Override
	public void setDueDate (java.sql.Timestamp DueDate)
	{
		set_Value (COLUMNNAME_DueDate, DueDate);
	}

	/** Get Datum Fälligkeit.
		@return Datum, zu dem Zahlung fällig wird
	  */
	@Override
	public java.sql.Timestamp getDueDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DueDate);
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

	/** Set Dunning Date Effective.
		@param DunningDateEffective 
		Effective Date of Dunning
	  */
	@Override
	public void setDunningDateEffective (java.sql.Timestamp DunningDateEffective)
	{
		set_Value (COLUMNNAME_DunningDateEffective, DunningDateEffective);
	}

	/** Get Dunning Date Effective.
		@return Effective Date of Dunning
	  */
	@Override
	public java.sql.Timestamp getDunningDateEffective () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DunningDateEffective);
	}

	/** Set Dunning Grace Date.
		@param DunningGrace Dunning Grace Date	  */
	@Override
	public void setDunningGrace (java.sql.Timestamp DunningGrace)
	{
		set_Value (COLUMNNAME_DunningGrace, DunningGrace);
	}

	/** Get Dunning Grace Date.
		@return Dunning Grace Date	  */
	@Override
	public java.sql.Timestamp getDunningGrace () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DunningGrace);
	}

	/** Set Mahnzins.
		@param DunningInterestAmt 
		Prozentualer Anteil der offenen Summe, der als zusätzliche Mahngebühr ausgewiesen wird.
	  */
	@Override
	public void setDunningInterestAmt (java.math.BigDecimal DunningInterestAmt)
	{
		set_Value (COLUMNNAME_DunningInterestAmt, DunningInterestAmt);
	}

	/** Get Mahnzins.
		@return Prozentualer Anteil der offenen Summe, der als zusätzliche Mahngebühr ausgewiesen wird.
	  */
	@Override
	public java.math.BigDecimal getDunningInterestAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DunningInterestAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Mahnpauschale.
		@param FeeAmt 
		Pauschale Mahngebühr
	  */
	@Override
	public void setFeeAmt (java.math.BigDecimal FeeAmt)
	{
		set_Value (COLUMNNAME_FeeAmt, FeeAmt);
	}

	/** Get Mahnpauschale.
		@return Pauschale Mahngebühr
	  */
	@Override
	public java.math.BigDecimal getFeeAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FeeAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set IsDunningDocProcessed.
		@param IsDunningDocProcessed IsDunningDocProcessed	  */
	@Override
	public void setIsDunningDocProcessed (boolean IsDunningDocProcessed)
	{
		set_Value (COLUMNNAME_IsDunningDocProcessed, Boolean.valueOf(IsDunningDocProcessed));
	}

	/** Get IsDunningDocProcessed.
		@return IsDunningDocProcessed	  */
	@Override
	public boolean isDunningDocProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_IsDunningDocProcessed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Zu aktualisieren.
		@param IsStaled Zu aktualisieren	  */
	@Override
	public void setIsStaled (boolean IsStaled)
	{
		throw new IllegalArgumentException ("IsStaled is virtual column");	}

	/** Get Zu aktualisieren.
		@return Zu aktualisieren	  */
	@Override
	public boolean isStaled () 
	{
		Object oo = get_Value(COLUMNNAME_IsStaled);
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
		set_Value (COLUMNNAME_IsWriteOff, Boolean.valueOf(IsWriteOff));
	}

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

	/** Set Offener Betrag.
		@param OpenAmt Offener Betrag	  */
	@Override
	public void setOpenAmt (java.math.BigDecimal OpenAmt)
	{
		set_Value (COLUMNNAME_OpenAmt, OpenAmt);
	}

	/** Get Offener Betrag.
		@return Offener Betrag	  */
	@Override
	public java.math.BigDecimal getOpenAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_OpenAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Gesamtbetrag.
		@param TotalAmt Gesamtbetrag	  */
	@Override
	public void setTotalAmt (java.math.BigDecimal TotalAmt)
	{
		set_Value (COLUMNNAME_TotalAmt, TotalAmt);
	}

	/** Get Gesamtbetrag.
		@return Gesamtbetrag	  */
	@Override
	public java.math.BigDecimal getTotalAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}