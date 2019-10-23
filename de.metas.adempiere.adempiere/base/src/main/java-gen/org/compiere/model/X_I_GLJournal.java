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
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for I_GLJournal
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_I_GLJournal extends org.compiere.model.PO implements I_I_GLJournal, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 362856913L;

    /** Standard Constructor */
    public X_I_GLJournal (Properties ctx, int I_GLJournal_ID, String trxName)
    {
      super (ctx, I_GLJournal_ID, trxName);
      /** if (I_GLJournal_ID == 0)
        {
			setI_GLJournal_ID (0);
			setI_IsImported (false);
        } */
    }

    /** Load Constructor */
    public X_I_GLJournal (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_C_ElementValue getAccountFrom() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AccountFrom_ID, org.compiere.model.I_C_ElementValue.class);
	}

	@Override
	public void setAccountFrom(org.compiere.model.I_C_ElementValue AccountFrom)
	{
		set_ValueFromPO(COLUMNNAME_AccountFrom_ID, org.compiere.model.I_C_ElementValue.class, AccountFrom);
	}

	/** Set Konto Aus.
		@param AccountFrom_ID 
		Verwendetes Konto
	  */
	@Override
	public void setAccountFrom_ID (int AccountFrom_ID)
	{
		if (AccountFrom_ID < 1) 
			set_Value (COLUMNNAME_AccountFrom_ID, null);
		else 
			set_Value (COLUMNNAME_AccountFrom_ID, Integer.valueOf(AccountFrom_ID));
	}

	/** Get Konto Aus.
		@return Verwendetes Konto
	  */
	@Override
	public int getAccountFrom_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AccountFrom_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ElementValue getAccountTo() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AccountTo_ID, org.compiere.model.I_C_ElementValue.class);
	}

	@Override
	public void setAccountTo(org.compiere.model.I_C_ElementValue AccountTo)
	{
		set_ValueFromPO(COLUMNNAME_AccountTo_ID, org.compiere.model.I_C_ElementValue.class, AccountTo);
	}

	/** Set Konto Zu.
		@param AccountTo_ID 
		Verwendetes Konto
	  */
	@Override
	public void setAccountTo_ID (int AccountTo_ID)
	{
		if (AccountTo_ID < 1) 
			set_Value (COLUMNNAME_AccountTo_ID, null);
		else 
			set_Value (COLUMNNAME_AccountTo_ID, Integer.valueOf(AccountTo_ID));
	}

	/** Get Konto Zu.
		@return Verwendetes Konto
	  */
	@Override
	public int getAccountTo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AccountTo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Konto-Schlüssel Aus.
		@param AccountValueFrom Konto-Schlüssel Aus	  */
	@Override
	public void setAccountValueFrom (java.lang.String AccountValueFrom)
	{
		set_Value (COLUMNNAME_AccountValueFrom, AccountValueFrom);
	}

	/** Get Konto-Schlüssel Aus.
		@return Konto-Schlüssel Aus	  */
	@Override
	public java.lang.String getAccountValueFrom () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AccountValueFrom);
	}

	/** Set Konto-Schlüssel Zu.
		@param AccountValueTo Konto-Schlüssel Zu	  */
	@Override
	public void setAccountValueTo (java.lang.String AccountValueTo)
	{
		set_Value (COLUMNNAME_AccountValueTo, AccountValueTo);
	}

	/** Get Konto-Schlüssel Zu.
		@return Konto-Schlüssel Zu	  */
	@Override
	public java.lang.String getAccountValueTo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AccountValueTo);
	}

	/** Set Kontenschema-Bezeichnung.
		@param AcctSchemaName 
		Name of the Accounting Schema
	  */
	@Override
	public void setAcctSchemaName (java.lang.String AcctSchemaName)
	{
		set_Value (COLUMNNAME_AcctSchemaName, AcctSchemaName);
	}

	/** Get Kontenschema-Bezeichnung.
		@return Name of the Accounting Schema
	  */
	@Override
	public java.lang.String getAcctSchemaName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AcctSchemaName);
	}

	@Override
	public org.compiere.model.I_AD_Org getAD_OrgDoc() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_OrgDoc_ID, org.compiere.model.I_AD_Org.class);
	}

	@Override
	public void setAD_OrgDoc(org.compiere.model.I_AD_Org AD_OrgDoc)
	{
		set_ValueFromPO(COLUMNNAME_AD_OrgDoc_ID, org.compiere.model.I_AD_Org.class, AD_OrgDoc);
	}

	/** Set Beleg-Organisation.
		@param AD_OrgDoc_ID 
		Document Organization (independent from account organization)
	  */
	@Override
	public void setAD_OrgDoc_ID (int AD_OrgDoc_ID)
	{
		if (AD_OrgDoc_ID < 1) 
			set_Value (COLUMNNAME_AD_OrgDoc_ID, null);
		else 
			set_Value (COLUMNNAME_AD_OrgDoc_ID, Integer.valueOf(AD_OrgDoc_ID));
	}

	/** Get Beleg-Organisation.
		@return Document Organization (independent from account organization)
	  */
	@Override
	public int getAD_OrgDoc_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_OrgDoc_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Org getAD_OrgTrx() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_OrgTrx_ID, org.compiere.model.I_AD_Org.class);
	}

	@Override
	public void setAD_OrgTrx(org.compiere.model.I_AD_Org AD_OrgTrx)
	{
		set_ValueFromPO(COLUMNNAME_AD_OrgTrx_ID, org.compiere.model.I_AD_Org.class, AD_OrgTrx);
	}

	/** Set Buchende Organisation.
		@param AD_OrgTrx_ID 
		Performing or initiating organization
	  */
	@Override
	public void setAD_OrgTrx_ID (int AD_OrgTrx_ID)
	{
		if (AD_OrgTrx_ID < 1) 
			set_Value (COLUMNNAME_AD_OrgTrx_ID, null);
		else 
			set_Value (COLUMNNAME_AD_OrgTrx_ID, Integer.valueOf(AD_OrgTrx_ID));
	}

	/** Get Buchende Organisation.
		@return Performing or initiating organization
	  */
	@Override
	public int getAD_OrgTrx_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_OrgTrx_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Haben.
		@param AmtAcctCr 
		Ausgewiesener Forderungsbetrag
	  */
	@Override
	public void setAmtAcctCr (java.math.BigDecimal AmtAcctCr)
	{
		set_Value (COLUMNNAME_AmtAcctCr, AmtAcctCr);
	}

	/** Get Haben.
		@return Ausgewiesener Forderungsbetrag
	  */
	@Override
	public java.math.BigDecimal getAmtAcctCr () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtAcctCr);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Soll.
		@param AmtAcctDr 
		Ausgewiesener Verbindlichkeitsbetrag
	  */
	@Override
	public void setAmtAcctDr (java.math.BigDecimal AmtAcctDr)
	{
		set_Value (COLUMNNAME_AmtAcctDr, AmtAcctDr);
	}

	/** Get Soll.
		@return Ausgewiesener Verbindlichkeitsbetrag
	  */
	@Override
	public java.math.BigDecimal getAmtAcctDr () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtAcctDr);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Ausgangsforderung.
		@param AmtSourceCr 
		Source Credit Amount
	  */
	@Override
	public void setAmtSourceCr (java.math.BigDecimal AmtSourceCr)
	{
		set_Value (COLUMNNAME_AmtSourceCr, AmtSourceCr);
	}

	/** Get Ausgangsforderung.
		@return Source Credit Amount
	  */
	@Override
	public java.math.BigDecimal getAmtSourceCr () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtSourceCr);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Ausgangsverbindlichkeit.
		@param AmtSourceDr 
		Source Debit Amount
	  */
	@Override
	public void setAmtSourceDr (java.math.BigDecimal AmtSourceDr)
	{
		set_Value (COLUMNNAME_AmtSourceDr, AmtSourceDr);
	}

	/** Get Ausgangsverbindlichkeit.
		@return Source Debit Amount
	  */
	@Override
	public java.math.BigDecimal getAmtSourceDr () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtSourceDr);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Beschreibung Lauf.
		@param BatchDescription 
		Description of the Batch
	  */
	@Override
	public void setBatchDescription (java.lang.String BatchDescription)
	{
		set_Value (COLUMNNAME_BatchDescription, BatchDescription);
	}

	/** Get Beschreibung Lauf.
		@return Description of the Batch
	  */
	@Override
	public java.lang.String getBatchDescription () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_BatchDescription);
	}

	/** Set Beleg-Nr. Lauf.
		@param BatchDocumentNo 
		Document Number of the Batch
	  */
	@Override
	public void setBatchDocumentNo (java.lang.String BatchDocumentNo)
	{
		set_Value (COLUMNNAME_BatchDocumentNo, BatchDocumentNo);
	}

	/** Get Beleg-Nr. Lauf.
		@return Document Number of the Batch
	  */
	@Override
	public java.lang.String getBatchDocumentNo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_BatchDocumentNo);
	}

	/** Set Geschäftspartner-Schlüssel.
		@param BPartnerValue 
		Key of the Business Partner
	  */
	@Override
	public void setBPartnerValue (java.lang.String BPartnerValue)
	{
		set_Value (COLUMNNAME_BPartnerValue, BPartnerValue);
	}

	/** Get Geschäftspartner-Schlüssel.
		@return Key of the Business Partner
	  */
	@Override
	public java.lang.String getBPartnerValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_BPartnerValue);
	}

	@Override
	public org.compiere.model.I_C_AcctSchema getC_AcctSchema() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_AcctSchema_ID, org.compiere.model.I_C_AcctSchema.class);
	}

	@Override
	public void setC_AcctSchema(org.compiere.model.I_C_AcctSchema C_AcctSchema)
	{
		set_ValueFromPO(COLUMNNAME_C_AcctSchema_ID, org.compiere.model.I_C_AcctSchema.class, C_AcctSchema);
	}

	/** Set Buchführungs-Schema.
		@param C_AcctSchema_ID 
		Rules for accounting
	  */
	@Override
	public void setC_AcctSchema_ID (int C_AcctSchema_ID)
	{
		if (C_AcctSchema_ID < 1) 
			set_Value (COLUMNNAME_C_AcctSchema_ID, null);
		else 
			set_Value (COLUMNNAME_C_AcctSchema_ID, Integer.valueOf(C_AcctSchema_ID));
	}

	/** Get Buchführungs-Schema.
		@return Rules for accounting
	  */
	@Override
	public int getC_AcctSchema_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AcctSchema_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Kategorie-Bezeichnung.
		@param CategoryName 
		Name of the Category
	  */
	@Override
	public void setCategoryName (java.lang.String CategoryName)
	{
		set_Value (COLUMNNAME_CategoryName, CategoryName);
	}

	/** Get Kategorie-Bezeichnung.
		@return Name of the Category
	  */
	@Override
	public java.lang.String getCategoryName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CategoryName);
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
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
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
	public org.compiere.model.I_C_Campaign getC_Campaign() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Campaign_ID, org.compiere.model.I_C_Campaign.class);
	}

	@Override
	public void setC_Campaign(org.compiere.model.I_C_Campaign C_Campaign)
	{
		set_ValueFromPO(COLUMNNAME_C_Campaign_ID, org.compiere.model.I_C_Campaign.class, C_Campaign);
	}

	/** Set Werbemassnahme.
		@param C_Campaign_ID 
		Marketing Campaign
	  */
	@Override
	public void setC_Campaign_ID (int C_Campaign_ID)
	{
		if (C_Campaign_ID < 1) 
			set_Value (COLUMNNAME_C_Campaign_ID, null);
		else 
			set_Value (COLUMNNAME_C_Campaign_ID, Integer.valueOf(C_Campaign_ID));
	}

	/** Get Werbemassnahme.
		@return Marketing Campaign
	  */
	@Override
	public int getC_Campaign_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Campaign_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ConversionType getC_ConversionType() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_ConversionType_ID, org.compiere.model.I_C_ConversionType.class);
	}

	@Override
	public void setC_ConversionType(org.compiere.model.I_C_ConversionType C_ConversionType)
	{
		set_ValueFromPO(COLUMNNAME_C_ConversionType_ID, org.compiere.model.I_C_ConversionType.class, C_ConversionType);
	}

	/** Set Kursart.
		@param C_ConversionType_ID 
		Currency Conversion Rate Type
	  */
	@Override
	public void setC_ConversionType_ID (int C_ConversionType_ID)
	{
		if (C_ConversionType_ID < 1) 
			set_Value (COLUMNNAME_C_ConversionType_ID, null);
		else 
			set_Value (COLUMNNAME_C_ConversionType_ID, Integer.valueOf(C_ConversionType_ID));
	}

	/** Get Kursart.
		@return Currency Conversion Rate Type
	  */
	@Override
	public int getC_ConversionType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ConversionType_ID);
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
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
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
		Document type or rules
	  */
	@Override
	public void setC_DocType_ID (int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_Value (COLUMNNAME_C_DocType_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
	}

	/** Get Belegart.
		@return Document type or rules
	  */
	@Override
	public int getC_DocType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Mandanten-Schlüssel.
		@param ClientValue 
		Key of the Client
	  */
	@Override
	public void setClientValue (java.lang.String ClientValue)
	{
		set_Value (COLUMNNAME_ClientValue, ClientValue);
	}

	/** Get Mandanten-Schlüssel.
		@return Key of the Client
	  */
	@Override
	public java.lang.String getClientValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ClientValue);
	}

	@Override
	public org.compiere.model.I_C_Location getC_LocFrom() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_LocFrom_ID, org.compiere.model.I_C_Location.class);
	}

	@Override
	public void setC_LocFrom(org.compiere.model.I_C_Location C_LocFrom)
	{
		set_ValueFromPO(COLUMNNAME_C_LocFrom_ID, org.compiere.model.I_C_Location.class, C_LocFrom);
	}

	/** Set Von Ort.
		@param C_LocFrom_ID 
		Location that inventory was moved from
	  */
	@Override
	public void setC_LocFrom_ID (int C_LocFrom_ID)
	{
		if (C_LocFrom_ID < 1) 
			set_Value (COLUMNNAME_C_LocFrom_ID, null);
		else 
			set_Value (COLUMNNAME_C_LocFrom_ID, Integer.valueOf(C_LocFrom_ID));
	}

	/** Get Von Ort.
		@return Location that inventory was moved from
	  */
	@Override
	public int getC_LocFrom_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_LocFrom_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Location getC_LocTo() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_LocTo_ID, org.compiere.model.I_C_Location.class);
	}

	@Override
	public void setC_LocTo(org.compiere.model.I_C_Location C_LocTo)
	{
		set_ValueFromPO(COLUMNNAME_C_LocTo_ID, org.compiere.model.I_C_Location.class, C_LocTo);
	}

	/** Set Nach Ort.
		@param C_LocTo_ID 
		Location that inventory was moved to
	  */
	@Override
	public void setC_LocTo_ID (int C_LocTo_ID)
	{
		if (C_LocTo_ID < 1) 
			set_Value (COLUMNNAME_C_LocTo_ID, null);
		else 
			set_Value (COLUMNNAME_C_LocTo_ID, Integer.valueOf(C_LocTo_ID));
	}

	/** Get Nach Ort.
		@return Location that inventory was moved to
	  */
	@Override
	public int getC_LocTo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_LocTo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Kursart-Schlüssel.
		@param ConversionTypeValue 
		Key value for the Currency Conversion Rate Type
	  */
	@Override
	public void setConversionTypeValue (java.lang.String ConversionTypeValue)
	{
		set_Value (COLUMNNAME_ConversionTypeValue, ConversionTypeValue);
	}

	/** Get Kursart-Schlüssel.
		@return Key value for the Currency Conversion Rate Type
	  */
	@Override
	public java.lang.String getConversionTypeValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ConversionTypeValue);
	}

	@Override
	public org.compiere.model.I_C_Period getC_Period() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Period_ID, org.compiere.model.I_C_Period.class);
	}

	@Override
	public void setC_Period(org.compiere.model.I_C_Period C_Period)
	{
		set_ValueFromPO(COLUMNNAME_C_Period_ID, org.compiere.model.I_C_Period.class, C_Period);
	}

	/** Set Periode.
		@param C_Period_ID 
		Period of the Calendar
	  */
	@Override
	public void setC_Period_ID (int C_Period_ID)
	{
		if (C_Period_ID < 1) 
			set_Value (COLUMNNAME_C_Period_ID, null);
		else 
			set_Value (COLUMNNAME_C_Period_ID, Integer.valueOf(C_Period_ID));
	}

	/** Get Periode.
		@return Period of the Calendar
	  */
	@Override
	public int getC_Period_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Period_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Project getC_Project() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Project_ID, org.compiere.model.I_C_Project.class);
	}

	@Override
	public void setC_Project(org.compiere.model.I_C_Project C_Project)
	{
		set_ValueFromPO(COLUMNNAME_C_Project_ID, org.compiere.model.I_C_Project.class, C_Project);
	}

	/** Set Projekt.
		@param C_Project_ID 
		Financial Project
	  */
	@Override
	public void setC_Project_ID (int C_Project_ID)
	{
		if (C_Project_ID < 1) 
			set_Value (COLUMNNAME_C_Project_ID, null);
		else 
			set_Value (COLUMNNAME_C_Project_ID, Integer.valueOf(C_Project_ID));
	}

	/** Get Projekt.
		@return Financial Project
	  */
	@Override
	public int getC_Project_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Project_ID);
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

	@Override
	public org.compiere.model.I_C_UOM getC_UOM() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_UOM_ID, org.compiere.model.I_C_UOM.class);
	}

	@Override
	public void setC_UOM(org.compiere.model.I_C_UOM C_UOM)
	{
		set_ValueFromPO(COLUMNNAME_C_UOM_ID, org.compiere.model.I_C_UOM.class, C_UOM);
	}

	/** Set Maßeinheit.
		@param C_UOM_ID 
		Unit of Measure
	  */
	@Override
	public void setC_UOM_ID (int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
	}

	/** Get Maßeinheit.
		@return Unit of Measure
	  */
	@Override
	public int getC_UOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Wechselkurs.
		@param CurrencyRate 
		Currency Conversion Rate
	  */
	@Override
	public void setCurrencyRate (java.math.BigDecimal CurrencyRate)
	{
		set_Value (COLUMNNAME_CurrencyRate, CurrencyRate);
	}

	/** Get Wechselkurs.
		@return Currency Conversion Rate
	  */
	@Override
	public java.math.BigDecimal getCurrencyRate () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CurrencyRate);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	@Override
	public org.compiere.model.I_C_ElementValue getC_ValidCombinationFrom() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_ValidCombinationFrom_ID, org.compiere.model.I_C_ElementValue.class);
	}

	@Override
	public void setC_ValidCombinationFrom(org.compiere.model.I_C_ElementValue C_ValidCombinationFrom)
	{
		set_ValueFromPO(COLUMNNAME_C_ValidCombinationFrom_ID, org.compiere.model.I_C_ElementValue.class, C_ValidCombinationFrom);
	}

	/** Set Kombination Aus.
		@param C_ValidCombinationFrom_ID 
		Gültige Kontenkombination
	  */
	@Override
	public void setC_ValidCombinationFrom_ID (int C_ValidCombinationFrom_ID)
	{
		if (C_ValidCombinationFrom_ID < 1) 
			set_Value (COLUMNNAME_C_ValidCombinationFrom_ID, null);
		else 
			set_Value (COLUMNNAME_C_ValidCombinationFrom_ID, Integer.valueOf(C_ValidCombinationFrom_ID));
	}

	/** Get Kombination Aus.
		@return Gültige Kontenkombination
	  */
	@Override
	public int getC_ValidCombinationFrom_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ValidCombinationFrom_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ElementValue getC_ValidCombinationTo() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_ValidCombinationTo_ID, org.compiere.model.I_C_ElementValue.class);
	}

	@Override
	public void setC_ValidCombinationTo(org.compiere.model.I_C_ElementValue C_ValidCombinationTo)
	{
		set_ValueFromPO(COLUMNNAME_C_ValidCombinationTo_ID, org.compiere.model.I_C_ElementValue.class, C_ValidCombinationTo);
	}

	/** Set Kombination Zu.
		@param C_ValidCombinationTo_ID 
		Gültige Kontenkombination
	  */
	@Override
	public void setC_ValidCombinationTo_ID (int C_ValidCombinationTo_ID)
	{
		if (C_ValidCombinationTo_ID < 1) 
			set_Value (COLUMNNAME_C_ValidCombinationTo_ID, null);
		else 
			set_Value (COLUMNNAME_C_ValidCombinationTo_ID, Integer.valueOf(C_ValidCombinationTo_ID));
	}

	/** Get Kombination Zu.
		@return Gültige Kontenkombination
	  */
	@Override
	public int getC_ValidCombinationTo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ValidCombinationTo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Belegart-Bezeichnung.
		@param DocTypeName 
		Name of the Document Type
	  */
	@Override
	public void setDocTypeName (java.lang.String DocTypeName)
	{
		set_Value (COLUMNNAME_DocTypeName, DocTypeName);
	}

	/** Get Belegart-Bezeichnung.
		@return Name of the Document Type
	  */
	@Override
	public java.lang.String getDocTypeName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocTypeName);
	}

	@Override
	public org.compiere.model.I_GL_Budget getGL_Budget() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_GL_Budget_ID, org.compiere.model.I_GL_Budget.class);
	}

	@Override
	public void setGL_Budget(org.compiere.model.I_GL_Budget GL_Budget)
	{
		set_ValueFromPO(COLUMNNAME_GL_Budget_ID, org.compiere.model.I_GL_Budget.class, GL_Budget);
	}

	/** Set Budget.
		@param GL_Budget_ID 
		General Ledger Budget
	  */
	@Override
	public void setGL_Budget_ID (int GL_Budget_ID)
	{
		if (GL_Budget_ID < 1) 
			set_Value (COLUMNNAME_GL_Budget_ID, null);
		else 
			set_Value (COLUMNNAME_GL_Budget_ID, Integer.valueOf(GL_Budget_ID));
	}

	/** Get Budget.
		@return General Ledger Budget
	  */
	@Override
	public int getGL_Budget_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GL_Budget_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_GL_Category getGL_Category() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_GL_Category_ID, org.compiere.model.I_GL_Category.class);
	}

	@Override
	public void setGL_Category(org.compiere.model.I_GL_Category GL_Category)
	{
		set_ValueFromPO(COLUMNNAME_GL_Category_ID, org.compiere.model.I_GL_Category.class, GL_Category);
	}

	/** Set Hauptbuch - Kategorie.
		@param GL_Category_ID 
		General Ledger Category
	  */
	@Override
	public void setGL_Category_ID (int GL_Category_ID)
	{
		if (GL_Category_ID < 1) 
			set_Value (COLUMNNAME_GL_Category_ID, null);
		else 
			set_Value (COLUMNNAME_GL_Category_ID, Integer.valueOf(GL_Category_ID));
	}

	/** Get Hauptbuch - Kategorie.
		@return General Ledger Category
	  */
	@Override
	public int getGL_Category_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GL_Category_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_GL_JournalBatch getGL_JournalBatch() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_GL_JournalBatch_ID, org.compiere.model.I_GL_JournalBatch.class);
	}

	@Override
	public void setGL_JournalBatch(org.compiere.model.I_GL_JournalBatch GL_JournalBatch)
	{
		set_ValueFromPO(COLUMNNAME_GL_JournalBatch_ID, org.compiere.model.I_GL_JournalBatch.class, GL_JournalBatch);
	}

	/** Set Journal-Lauf.
		@param GL_JournalBatch_ID 
		General Ledger Journal Batch
	  */
	@Override
	public void setGL_JournalBatch_ID (int GL_JournalBatch_ID)
	{
		if (GL_JournalBatch_ID < 1) 
			set_Value (COLUMNNAME_GL_JournalBatch_ID, null);
		else 
			set_Value (COLUMNNAME_GL_JournalBatch_ID, Integer.valueOf(GL_JournalBatch_ID));
	}

	/** Get Journal-Lauf.
		@return General Ledger Journal Batch
	  */
	@Override
	public int getGL_JournalBatch_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GL_JournalBatch_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_GL_Journal getGL_Journal() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_GL_Journal_ID, org.compiere.model.I_GL_Journal.class);
	}

	@Override
	public void setGL_Journal(org.compiere.model.I_GL_Journal GL_Journal)
	{
		set_ValueFromPO(COLUMNNAME_GL_Journal_ID, org.compiere.model.I_GL_Journal.class, GL_Journal);
	}

	/** Set Journal.
		@param GL_Journal_ID 
		General Ledger Journal
	  */
	@Override
	public void setGL_Journal_ID (int GL_Journal_ID)
	{
		if (GL_Journal_ID < 1) 
			set_Value (COLUMNNAME_GL_Journal_ID, null);
		else 
			set_Value (COLUMNNAME_GL_Journal_ID, Integer.valueOf(GL_Journal_ID));
	}

	/** Get Journal.
		@return General Ledger Journal
	  */
	@Override
	public int getGL_Journal_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GL_Journal_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_GL_JournalLine getGL_JournalLine() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_GL_JournalLine_ID, org.compiere.model.I_GL_JournalLine.class);
	}

	@Override
	public void setGL_JournalLine(org.compiere.model.I_GL_JournalLine GL_JournalLine)
	{
		set_ValueFromPO(COLUMNNAME_GL_JournalLine_ID, org.compiere.model.I_GL_JournalLine.class, GL_JournalLine);
	}

	/** Set Journal-Position.
		@param GL_JournalLine_ID 
		General Ledger Journal Line
	  */
	@Override
	public void setGL_JournalLine_ID (int GL_JournalLine_ID)
	{
		if (GL_JournalLine_ID < 1) 
			set_Value (COLUMNNAME_GL_JournalLine_ID, null);
		else 
			set_Value (COLUMNNAME_GL_JournalLine_ID, Integer.valueOf(GL_JournalLine_ID));
	}

	/** Get Journal-Position.
		@return General Ledger Journal Line
	  */
	@Override
	public int getGL_JournalLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GL_JournalLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Import-Fehlermeldung.
		@param I_ErrorMsg 
		Messages generated from import process
	  */
	@Override
	public void setI_ErrorMsg (java.lang.String I_ErrorMsg)
	{
		set_Value (COLUMNNAME_I_ErrorMsg, I_ErrorMsg);
	}

	/** Get Import-Fehlermeldung.
		@return Messages generated from import process
	  */
	@Override
	public java.lang.String getI_ErrorMsg () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_I_ErrorMsg);
	}

	/** Set Import - Hauptbuchjournal.
		@param I_GLJournal_ID 
		Import General Ledger Journal
	  */
	@Override
	public void setI_GLJournal_ID (int I_GLJournal_ID)
	{
		if (I_GLJournal_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_I_GLJournal_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_I_GLJournal_ID, Integer.valueOf(I_GLJournal_ID));
	}

	/** Get Import - Hauptbuchjournal.
		@return Import General Ledger Journal
	  */
	@Override
	public int getI_GLJournal_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_I_GLJournal_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Importiert.
		@param I_IsImported 
		Has this import been processed
	  */
	@Override
	public void setI_IsImported (boolean I_IsImported)
	{
		set_Value (COLUMNNAME_I_IsImported, Boolean.valueOf(I_IsImported));
	}

	/** Get Importiert.
		@return Has this import been processed
	  */
	@Override
	public boolean isI_IsImported () 
	{
		Object oo = get_Value(COLUMNNAME_I_IsImported);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Neuen Lauf erstellen.
		@param IsCreateNewBatch 
		If selected a new batch is created
	  */
	@Override
	public void setIsCreateNewBatch (boolean IsCreateNewBatch)
	{
		set_Value (COLUMNNAME_IsCreateNewBatch, Boolean.valueOf(IsCreateNewBatch));
	}

	/** Get Neuen Lauf erstellen.
		@return If selected a new batch is created
	  */
	@Override
	public boolean isCreateNewBatch () 
	{
		Object oo = get_Value(COLUMNNAME_IsCreateNewBatch);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Neues Journal anlegen.
		@param IsCreateNewJournal 
		If selected a new journal within the batch is created
	  */
	@Override
	public void setIsCreateNewJournal (boolean IsCreateNewJournal)
	{
		set_Value (COLUMNNAME_IsCreateNewJournal, Boolean.valueOf(IsCreateNewJournal));
	}

	/** Get Neues Journal anlegen.
		@return If selected a new journal within the batch is created
	  */
	@Override
	public boolean isCreateNewJournal () 
	{
		Object oo = get_Value(COLUMNNAME_IsCreateNewJournal);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set ISO Währungscode.
		@param ISO_Code 
		Three letter ISO 4217 Code of the Currency
	  */
	@Override
	public void setISO_Code (java.lang.String ISO_Code)
	{
		set_Value (COLUMNNAME_ISO_Code, ISO_Code);
	}

	/** Get ISO Währungscode.
		@return Three letter ISO 4217 Code of the Currency
	  */
	@Override
	public java.lang.String getISO_Code () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ISO_Code);
	}

	/** Set Journalbeleg-Nr..
		@param JournalDocumentNo 
		Document number of the Journal
	  */
	@Override
	public void setJournalDocumentNo (java.lang.String JournalDocumentNo)
	{
		set_Value (COLUMNNAME_JournalDocumentNo, JournalDocumentNo);
	}

	/** Get Journalbeleg-Nr..
		@return Document number of the Journal
	  */
	@Override
	public java.lang.String getJournalDocumentNo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_JournalDocumentNo);
	}

	/** Set Zeile Nr..
		@param Line 
		Unique line for this document
	  */
	@Override
	public void setLine (int Line)
	{
		set_Value (COLUMNNAME_Line, Integer.valueOf(Line));
	}

	/** Get Zeile Nr..
		@return Unique line for this document
	  */
	@Override
	public int getLine () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Line);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Product_ID, org.compiere.model.I_M_Product.class);
	}

	@Override
	public void setM_Product(org.compiere.model.I_M_Product M_Product)
	{
		set_ValueFromPO(COLUMNNAME_M_Product_ID, org.compiere.model.I_M_Product.class, M_Product);
	}

	/** Set Produkt.
		@param M_Product_ID 
		Produkt, Leistung, Artikel
	  */
	@Override
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Produkt.
		@return Produkt, Leistung, Artikel
	  */
	@Override
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Schlüssel buchende Org.
		@param OrgTrxValue 
		Key of the Transaction Organization
	  */
	@Override
	public void setOrgTrxValue (java.lang.String OrgTrxValue)
	{
		set_Value (COLUMNNAME_OrgTrxValue, OrgTrxValue);
	}

	/** Get Schlüssel buchende Org.
		@return Key of the Transaction Organization
	  */
	@Override
	public java.lang.String getOrgTrxValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_OrgTrxValue);
	}

	/** Set Organisations-Schlüssel.
		@param OrgValue 
		Key of the Organization
	  */
	@Override
	public void setOrgValue (java.lang.String OrgValue)
	{
		set_Value (COLUMNNAME_OrgValue, OrgValue);
	}

	/** Get Organisations-Schlüssel.
		@return Key of the Organization
	  */
	@Override
	public java.lang.String getOrgValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_OrgValue);
	}

	/** 
	 * PostingType AD_Reference_ID=125
	 * Reference name: _Posting Type
	 */
	public static final int POSTINGTYPE_AD_Reference_ID=125;
	/** Actual = A */
	public static final String POSTINGTYPE_Actual = "A";
	/** Budget = B */
	public static final String POSTINGTYPE_Budget = "B";
	/** Commitment = E */
	public static final String POSTINGTYPE_Commitment = "E";
	/** Statistical = S */
	public static final String POSTINGTYPE_Statistical = "S";
	/** Reservation = R */
	public static final String POSTINGTYPE_Reservation = "R";
	/** Set Buchungsart.
		@param PostingType 
		The type of posted amount for the transaction
	  */
	@Override
	public void setPostingType (java.lang.String PostingType)
	{

		set_Value (COLUMNNAME_PostingType, PostingType);
	}

	/** Get Buchungsart.
		@return The type of posted amount for the transaction
	  */
	@Override
	public java.lang.String getPostingType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PostingType);
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

	/** Set Verarbeiten.
		@param Processing Verarbeiten	  */
	@Override
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Verarbeiten.
		@return Verarbeiten	  */
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

	/** Set Produktschlüssel.
		@param ProductValue 
		Key of the Product
	  */
	@Override
	public void setProductValue (java.lang.String ProductValue)
	{
		set_Value (COLUMNNAME_ProductValue, ProductValue);
	}

	/** Get Produktschlüssel.
		@return Key of the Product
	  */
	@Override
	public java.lang.String getProductValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ProductValue);
	}

	/** Set Projekt-Schlüssel.
		@param ProjectValue 
		Key of the Project
	  */
	@Override
	public void setProjectValue (java.lang.String ProjectValue)
	{
		set_Value (COLUMNNAME_ProjectValue, ProjectValue);
	}

	/** Get Projekt-Schlüssel.
		@return Key of the Project
	  */
	@Override
	public java.lang.String getProjectValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ProjectValue);
	}

	/** Set Menge.
		@param Qty 
		Quantity
	  */
	@Override
	public void setQty (java.math.BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	/** Get Menge.
		@return Quantity
	  */
	@Override
	public java.math.BigDecimal getQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set SKU.
		@param SKU 
		Stock Keeping Unit
	  */
	@Override
	public void setSKU (java.lang.String SKU)
	{
		set_Value (COLUMNNAME_SKU, SKU);
	}

	/** Get SKU.
		@return Stock Keeping Unit
	  */
	@Override
	public java.lang.String getSKU () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_SKU);
	}

	/** Set UPC/EAN.
		@param UPC 
		Bar Code (Universal Product Code or its superset European Article Number)
	  */
	@Override
	public void setUPC (java.lang.String UPC)
	{
		set_Value (COLUMNNAME_UPC, UPC);
	}

	/** Get UPC/EAN.
		@return Bar Code (Universal Product Code or its superset European Article Number)
	  */
	@Override
	public java.lang.String getUPC () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_UPC);
	}

	@Override
	public org.compiere.model.I_C_ElementValue getUser1() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_User1_ID, org.compiere.model.I_C_ElementValue.class);
	}

	@Override
	public void setUser1(org.compiere.model.I_C_ElementValue User1)
	{
		set_ValueFromPO(COLUMNNAME_User1_ID, org.compiere.model.I_C_ElementValue.class, User1);
	}

	/** Set Nutzer 1.
		@param User1_ID 
		User defined list element #1
	  */
	@Override
	public void setUser1_ID (int User1_ID)
	{
		if (User1_ID < 1) 
			set_Value (COLUMNNAME_User1_ID, null);
		else 
			set_Value (COLUMNNAME_User1_ID, Integer.valueOf(User1_ID));
	}

	/** Get Nutzer 1.
		@return User defined list element #1
	  */
	@Override
	public int getUser1_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_User1_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ElementValue getUser2() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_User2_ID, org.compiere.model.I_C_ElementValue.class);
	}

	@Override
	public void setUser2(org.compiere.model.I_C_ElementValue User2)
	{
		set_ValueFromPO(COLUMNNAME_User2_ID, org.compiere.model.I_C_ElementValue.class, User2);
	}

	/** Set Nutzer 2.
		@param User2_ID 
		User defined list element #2
	  */
	@Override
	public void setUser2_ID (int User2_ID)
	{
		if (User2_ID < 1) 
			set_Value (COLUMNNAME_User2_ID, null);
		else 
			set_Value (COLUMNNAME_User2_ID, Integer.valueOf(User2_ID));
	}

	/** Get Nutzer 2.
		@return User defined list element #2
	  */
	@Override
	public int getUser2_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_User2_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}