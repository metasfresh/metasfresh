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
package org.compiere.model;


/** Generated Interface for I_GLJournal
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_I_GLJournal 
{

    /** TableName=I_GLJournal */
    public static final String Table_Name = "I_GLJournal";

    /** AD_Table_ID=599 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 2 - Client
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(2);

    /** Load Meta Data */

	/**
	 * Set Konto Aus.
	 * Verwendetes Konto
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAccountFrom_ID (int AccountFrom_ID);

	/**
	 * Get Konto Aus.
	 * Verwendetes Konto
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAccountFrom_ID();

	public org.compiere.model.I_C_ElementValue getAccountFrom();

	public void setAccountFrom(org.compiere.model.I_C_ElementValue AccountFrom);

    /** Column definition for AccountFrom_ID */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, org.compiere.model.I_C_ElementValue> COLUMN_AccountFrom_ID = new org.adempiere.model.ModelColumn<I_I_GLJournal, org.compiere.model.I_C_ElementValue>(I_I_GLJournal.class, "AccountFrom_ID", org.compiere.model.I_C_ElementValue.class);
    /** Column name AccountFrom_ID */
    public static final String COLUMNNAME_AccountFrom_ID = "AccountFrom_ID";

	/**
	 * Set Konto Zu.
	 * Verwendetes Konto
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAccountTo_ID (int AccountTo_ID);

	/**
	 * Get Konto Zu.
	 * Verwendetes Konto
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAccountTo_ID();

	public org.compiere.model.I_C_ElementValue getAccountTo();

	public void setAccountTo(org.compiere.model.I_C_ElementValue AccountTo);

    /** Column definition for AccountTo_ID */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, org.compiere.model.I_C_ElementValue> COLUMN_AccountTo_ID = new org.adempiere.model.ModelColumn<I_I_GLJournal, org.compiere.model.I_C_ElementValue>(I_I_GLJournal.class, "AccountTo_ID", org.compiere.model.I_C_ElementValue.class);
    /** Column name AccountTo_ID */
    public static final String COLUMNNAME_AccountTo_ID = "AccountTo_ID";

	/**
	 * Set Konto-Schlüssel Aus.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAccountValueFrom (java.lang.String AccountValueFrom);

	/**
	 * Get Konto-Schlüssel Aus.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAccountValueFrom();

    /** Column definition for AccountValueFrom */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, Object> COLUMN_AccountValueFrom = new org.adempiere.model.ModelColumn<I_I_GLJournal, Object>(I_I_GLJournal.class, "AccountValueFrom", null);
    /** Column name AccountValueFrom */
    public static final String COLUMNNAME_AccountValueFrom = "AccountValueFrom";

	/**
	 * Set Konto-Schlüssel Zu.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAccountValueTo (java.lang.String AccountValueTo);

	/**
	 * Get Konto-Schlüssel Zu.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAccountValueTo();

    /** Column definition for AccountValueTo */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, Object> COLUMN_AccountValueTo = new org.adempiere.model.ModelColumn<I_I_GLJournal, Object>(I_I_GLJournal.class, "AccountValueTo", null);
    /** Column name AccountValueTo */
    public static final String COLUMNNAME_AccountValueTo = "AccountValueTo";

	/**
	 * Set Kontenschema-Bezeichnung.
	 * Name of the Accounting Schema
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAcctSchemaName (java.lang.String AcctSchemaName);

	/**
	 * Get Kontenschema-Bezeichnung.
	 * Name of the Accounting Schema
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAcctSchemaName();

    /** Column definition for AcctSchemaName */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, Object> COLUMN_AcctSchemaName = new org.adempiere.model.ModelColumn<I_I_GLJournal, Object>(I_I_GLJournal.class, "AcctSchemaName", null);
    /** Column name AcctSchemaName */
    public static final String COLUMNNAME_AcctSchemaName = "AcctSchemaName";

	/**
	 * Get Mandant.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_I_GLJournal, org.compiere.model.I_AD_Client>(I_I_GLJournal.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Beleg-Organisation.
	 * Document Organization (independent from account organization)
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_OrgDoc_ID (int AD_OrgDoc_ID);

	/**
	 * Get Beleg-Organisation.
	 * Document Organization (independent from account organization)
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_OrgDoc_ID();

	public org.compiere.model.I_AD_Org getAD_OrgDoc();

	public void setAD_OrgDoc(org.compiere.model.I_AD_Org AD_OrgDoc);

    /** Column definition for AD_OrgDoc_ID */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, org.compiere.model.I_AD_Org> COLUMN_AD_OrgDoc_ID = new org.adempiere.model.ModelColumn<I_I_GLJournal, org.compiere.model.I_AD_Org>(I_I_GLJournal.class, "AD_OrgDoc_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_OrgDoc_ID */
    public static final String COLUMNNAME_AD_OrgDoc_ID = "AD_OrgDoc_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

	public org.compiere.model.I_AD_Org getAD_Org();

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_I_GLJournal, org.compiere.model.I_AD_Org>(I_I_GLJournal.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Buchende Organisation.
	 * Performing or initiating organization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_OrgTrx_ID (int AD_OrgTrx_ID);

	/**
	 * Get Buchende Organisation.
	 * Performing or initiating organization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_OrgTrx_ID();

	public org.compiere.model.I_AD_Org getAD_OrgTrx();

	public void setAD_OrgTrx(org.compiere.model.I_AD_Org AD_OrgTrx);

    /** Column definition for AD_OrgTrx_ID */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, org.compiere.model.I_AD_Org> COLUMN_AD_OrgTrx_ID = new org.adempiere.model.ModelColumn<I_I_GLJournal, org.compiere.model.I_AD_Org>(I_I_GLJournal.class, "AD_OrgTrx_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_OrgTrx_ID */
    public static final String COLUMNNAME_AD_OrgTrx_ID = "AD_OrgTrx_ID";

	/**
	 * Set Haben.
	 * Ausgewiesener Forderungsbetrag
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAmtAcctCr (java.math.BigDecimal AmtAcctCr);

	/**
	 * Get Haben.
	 * Ausgewiesener Forderungsbetrag
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getAmtAcctCr();

    /** Column definition for AmtAcctCr */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, Object> COLUMN_AmtAcctCr = new org.adempiere.model.ModelColumn<I_I_GLJournal, Object>(I_I_GLJournal.class, "AmtAcctCr", null);
    /** Column name AmtAcctCr */
    public static final String COLUMNNAME_AmtAcctCr = "AmtAcctCr";

	/**
	 * Set Soll.
	 * Ausgewiesener Verbindlichkeitsbetrag
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAmtAcctDr (java.math.BigDecimal AmtAcctDr);

	/**
	 * Get Soll.
	 * Ausgewiesener Verbindlichkeitsbetrag
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getAmtAcctDr();

    /** Column definition for AmtAcctDr */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, Object> COLUMN_AmtAcctDr = new org.adempiere.model.ModelColumn<I_I_GLJournal, Object>(I_I_GLJournal.class, "AmtAcctDr", null);
    /** Column name AmtAcctDr */
    public static final String COLUMNNAME_AmtAcctDr = "AmtAcctDr";

	/**
	 * Set Ausgangsforderung.
	 * Source Credit Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAmtSourceCr (java.math.BigDecimal AmtSourceCr);

	/**
	 * Get Ausgangsforderung.
	 * Source Credit Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getAmtSourceCr();

    /** Column definition for AmtSourceCr */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, Object> COLUMN_AmtSourceCr = new org.adempiere.model.ModelColumn<I_I_GLJournal, Object>(I_I_GLJournal.class, "AmtSourceCr", null);
    /** Column name AmtSourceCr */
    public static final String COLUMNNAME_AmtSourceCr = "AmtSourceCr";

	/**
	 * Set Ausgangsverbindlichkeit.
	 * Source Debit Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAmtSourceDr (java.math.BigDecimal AmtSourceDr);

	/**
	 * Get Ausgangsverbindlichkeit.
	 * Source Debit Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getAmtSourceDr();

    /** Column definition for AmtSourceDr */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, Object> COLUMN_AmtSourceDr = new org.adempiere.model.ModelColumn<I_I_GLJournal, Object>(I_I_GLJournal.class, "AmtSourceDr", null);
    /** Column name AmtSourceDr */
    public static final String COLUMNNAME_AmtSourceDr = "AmtSourceDr";

	/**
	 * Set Beschreibung Lauf.
	 * Description of the Batch
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBatchDescription (java.lang.String BatchDescription);

	/**
	 * Get Beschreibung Lauf.
	 * Description of the Batch
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getBatchDescription();

    /** Column definition for BatchDescription */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, Object> COLUMN_BatchDescription = new org.adempiere.model.ModelColumn<I_I_GLJournal, Object>(I_I_GLJournal.class, "BatchDescription", null);
    /** Column name BatchDescription */
    public static final String COLUMNNAME_BatchDescription = "BatchDescription";

	/**
	 * Set Beleg-Nr. Lauf.
	 * Document Number of the Batch
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBatchDocumentNo (java.lang.String BatchDocumentNo);

	/**
	 * Get Beleg-Nr. Lauf.
	 * Document Number of the Batch
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getBatchDocumentNo();

    /** Column definition for BatchDocumentNo */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, Object> COLUMN_BatchDocumentNo = new org.adempiere.model.ModelColumn<I_I_GLJournal, Object>(I_I_GLJournal.class, "BatchDocumentNo", null);
    /** Column name BatchDocumentNo */
    public static final String COLUMNNAME_BatchDocumentNo = "BatchDocumentNo";

	/**
	 * Set Geschäftspartner-Schlüssel.
	 * Key of the Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBPartnerValue (java.lang.String BPartnerValue);

	/**
	 * Get Geschäftspartner-Schlüssel.
	 * Key of the Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getBPartnerValue();

    /** Column definition for BPartnerValue */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, Object> COLUMN_BPartnerValue = new org.adempiere.model.ModelColumn<I_I_GLJournal, Object>(I_I_GLJournal.class, "BPartnerValue", null);
    /** Column name BPartnerValue */
    public static final String COLUMNNAME_BPartnerValue = "BPartnerValue";

	/**
	 * Set Buchführungs-Schema.
	 * Rules for accounting
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_AcctSchema_ID (int C_AcctSchema_ID);

	/**
	 * Get Buchführungs-Schema.
	 * Rules for accounting
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_AcctSchema_ID();

	public org.compiere.model.I_C_AcctSchema getC_AcctSchema();

	public void setC_AcctSchema(org.compiere.model.I_C_AcctSchema C_AcctSchema);

    /** Column definition for C_AcctSchema_ID */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, org.compiere.model.I_C_AcctSchema> COLUMN_C_AcctSchema_ID = new org.adempiere.model.ModelColumn<I_I_GLJournal, org.compiere.model.I_C_AcctSchema>(I_I_GLJournal.class, "C_AcctSchema_ID", org.compiere.model.I_C_AcctSchema.class);
    /** Column name C_AcctSchema_ID */
    public static final String COLUMNNAME_C_AcctSchema_ID = "C_AcctSchema_ID";

	/**
	 * Set Kostenstelle.
	 * Kostenstelle
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Activity_ID (int C_Activity_ID);

	/**
	 * Get Kostenstelle.
	 * Kostenstelle
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Activity_ID();

	public org.compiere.model.I_C_Activity getC_Activity();

	public void setC_Activity(org.compiere.model.I_C_Activity C_Activity);

    /** Column definition for C_Activity_ID */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, org.compiere.model.I_C_Activity> COLUMN_C_Activity_ID = new org.adempiere.model.ModelColumn<I_I_GLJournal, org.compiere.model.I_C_Activity>(I_I_GLJournal.class, "C_Activity_ID", org.compiere.model.I_C_Activity.class);
    /** Column name C_Activity_ID */
    public static final String COLUMNNAME_C_Activity_ID = "C_Activity_ID";

	/**
	 * Set Kategorie-Bezeichnung.
	 * Name of the Category
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCategoryName (java.lang.String CategoryName);

	/**
	 * Get Kategorie-Bezeichnung.
	 * Name of the Category
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCategoryName();

    /** Column definition for CategoryName */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, Object> COLUMN_CategoryName = new org.adempiere.model.ModelColumn<I_I_GLJournal, Object>(I_I_GLJournal.class, "CategoryName", null);
    /** Column name CategoryName */
    public static final String COLUMNNAME_CategoryName = "CategoryName";

	/**
	 * Set Geschäftspartner.
	 * Identifies a Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Identifies a Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner();

	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner);

    /** Column definition for C_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_I_GLJournal, org.compiere.model.I_C_BPartner>(I_I_GLJournal.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Werbemassnahme.
	 * Marketing Campaign
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Campaign_ID (int C_Campaign_ID);

	/**
	 * Get Werbemassnahme.
	 * Marketing Campaign
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Campaign_ID();

	public org.compiere.model.I_C_Campaign getC_Campaign();

	public void setC_Campaign(org.compiere.model.I_C_Campaign C_Campaign);

    /** Column definition for C_Campaign_ID */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, org.compiere.model.I_C_Campaign> COLUMN_C_Campaign_ID = new org.adempiere.model.ModelColumn<I_I_GLJournal, org.compiere.model.I_C_Campaign>(I_I_GLJournal.class, "C_Campaign_ID", org.compiere.model.I_C_Campaign.class);
    /** Column name C_Campaign_ID */
    public static final String COLUMNNAME_C_Campaign_ID = "C_Campaign_ID";

	/**
	 * Set Kursart.
	 * Currency Conversion Rate Type
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_ConversionType_ID (int C_ConversionType_ID);

	/**
	 * Get Kursart.
	 * Currency Conversion Rate Type
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_ConversionType_ID();

	public org.compiere.model.I_C_ConversionType getC_ConversionType();

	public void setC_ConversionType(org.compiere.model.I_C_ConversionType C_ConversionType);

    /** Column definition for C_ConversionType_ID */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, org.compiere.model.I_C_ConversionType> COLUMN_C_ConversionType_ID = new org.adempiere.model.ModelColumn<I_I_GLJournal, org.compiere.model.I_C_ConversionType>(I_I_GLJournal.class, "C_ConversionType_ID", org.compiere.model.I_C_ConversionType.class);
    /** Column name C_ConversionType_ID */
    public static final String COLUMNNAME_C_ConversionType_ID = "C_ConversionType_ID";

	/**
	 * Set Währung.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Währung.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Currency_ID();

	public org.compiere.model.I_C_Currency getC_Currency();

	public void setC_Currency(org.compiere.model.I_C_Currency C_Currency);

    /** Column definition for C_Currency_ID */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, org.compiere.model.I_C_Currency> COLUMN_C_Currency_ID = new org.adempiere.model.ModelColumn<I_I_GLJournal, org.compiere.model.I_C_Currency>(I_I_GLJournal.class, "C_Currency_ID", org.compiere.model.I_C_Currency.class);
    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set Belegart.
	 * Document type or rules
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_DocType_ID (int C_DocType_ID);

	/**
	 * Get Belegart.
	 * Document type or rules
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_DocType_ID();

	public org.compiere.model.I_C_DocType getC_DocType();

	public void setC_DocType(org.compiere.model.I_C_DocType C_DocType);

    /** Column definition for C_DocType_ID */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, org.compiere.model.I_C_DocType> COLUMN_C_DocType_ID = new org.adempiere.model.ModelColumn<I_I_GLJournal, org.compiere.model.I_C_DocType>(I_I_GLJournal.class, "C_DocType_ID", org.compiere.model.I_C_DocType.class);
    /** Column name C_DocType_ID */
    public static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/**
	 * Set Mandanten-Schlüssel.
	 * Key of the Client
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setClientValue (java.lang.String ClientValue);

	/**
	 * Get Mandanten-Schlüssel.
	 * Key of the Client
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getClientValue();

    /** Column definition for ClientValue */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, Object> COLUMN_ClientValue = new org.adempiere.model.ModelColumn<I_I_GLJournal, Object>(I_I_GLJournal.class, "ClientValue", null);
    /** Column name ClientValue */
    public static final String COLUMNNAME_ClientValue = "ClientValue";

	/**
	 * Set Von Ort.
	 * Location that inventory was moved from
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_LocFrom_ID (int C_LocFrom_ID);

	/**
	 * Get Von Ort.
	 * Location that inventory was moved from
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_LocFrom_ID();

	public org.compiere.model.I_C_Location getC_LocFrom();

	public void setC_LocFrom(org.compiere.model.I_C_Location C_LocFrom);

    /** Column definition for C_LocFrom_ID */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, org.compiere.model.I_C_Location> COLUMN_C_LocFrom_ID = new org.adempiere.model.ModelColumn<I_I_GLJournal, org.compiere.model.I_C_Location>(I_I_GLJournal.class, "C_LocFrom_ID", org.compiere.model.I_C_Location.class);
    /** Column name C_LocFrom_ID */
    public static final String COLUMNNAME_C_LocFrom_ID = "C_LocFrom_ID";

	/**
	 * Set Nach Ort.
	 * Location that inventory was moved to
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_LocTo_ID (int C_LocTo_ID);

	/**
	 * Get Nach Ort.
	 * Location that inventory was moved to
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_LocTo_ID();

	public org.compiere.model.I_C_Location getC_LocTo();

	public void setC_LocTo(org.compiere.model.I_C_Location C_LocTo);

    /** Column definition for C_LocTo_ID */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, org.compiere.model.I_C_Location> COLUMN_C_LocTo_ID = new org.adempiere.model.ModelColumn<I_I_GLJournal, org.compiere.model.I_C_Location>(I_I_GLJournal.class, "C_LocTo_ID", org.compiere.model.I_C_Location.class);
    /** Column name C_LocTo_ID */
    public static final String COLUMNNAME_C_LocTo_ID = "C_LocTo_ID";

	/**
	 * Set Kursart-Schlüssel.
	 * Key value for the Currency Conversion Rate Type
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setConversionTypeValue (java.lang.String ConversionTypeValue);

	/**
	 * Get Kursart-Schlüssel.
	 * Key value for the Currency Conversion Rate Type
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getConversionTypeValue();

    /** Column definition for ConversionTypeValue */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, Object> COLUMN_ConversionTypeValue = new org.adempiere.model.ModelColumn<I_I_GLJournal, Object>(I_I_GLJournal.class, "ConversionTypeValue", null);
    /** Column name ConversionTypeValue */
    public static final String COLUMNNAME_ConversionTypeValue = "ConversionTypeValue";

	/**
	 * Set Periode.
	 * Period of the Calendar
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Period_ID (int C_Period_ID);

	/**
	 * Get Periode.
	 * Period of the Calendar
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Period_ID();

	public org.compiere.model.I_C_Period getC_Period();

	public void setC_Period(org.compiere.model.I_C_Period C_Period);

    /** Column definition for C_Period_ID */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, org.compiere.model.I_C_Period> COLUMN_C_Period_ID = new org.adempiere.model.ModelColumn<I_I_GLJournal, org.compiere.model.I_C_Period>(I_I_GLJournal.class, "C_Period_ID", org.compiere.model.I_C_Period.class);
    /** Column name C_Period_ID */
    public static final String COLUMNNAME_C_Period_ID = "C_Period_ID";

	/**
	 * Set Projekt.
	 * Financial Project
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Project_ID (int C_Project_ID);

	/**
	 * Get Projekt.
	 * Financial Project
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Project_ID();

	public org.compiere.model.I_C_Project getC_Project();

	public void setC_Project(org.compiere.model.I_C_Project C_Project);

    /** Column definition for C_Project_ID */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, org.compiere.model.I_C_Project> COLUMN_C_Project_ID = new org.adempiere.model.ModelColumn<I_I_GLJournal, org.compiere.model.I_C_Project>(I_I_GLJournal.class, "C_Project_ID", org.compiere.model.I_C_Project.class);
    /** Column name C_Project_ID */
    public static final String COLUMNNAME_C_Project_ID = "C_Project_ID";

	/**
	 * Get Erstellt.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_I_GLJournal, Object>(I_I_GLJournal.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_I_GLJournal, org.compiere.model.I_AD_User>(I_I_GLJournal.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Vertriebsgebiet.
	 * Sales coverage region
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_SalesRegion_ID (int C_SalesRegion_ID);

	/**
	 * Get Vertriebsgebiet.
	 * Sales coverage region
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_SalesRegion_ID();

	public org.compiere.model.I_C_SalesRegion getC_SalesRegion();

	public void setC_SalesRegion(org.compiere.model.I_C_SalesRegion C_SalesRegion);

    /** Column definition for C_SalesRegion_ID */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, org.compiere.model.I_C_SalesRegion> COLUMN_C_SalesRegion_ID = new org.adempiere.model.ModelColumn<I_I_GLJournal, org.compiere.model.I_C_SalesRegion>(I_I_GLJournal.class, "C_SalesRegion_ID", org.compiere.model.I_C_SalesRegion.class);
    /** Column name C_SalesRegion_ID */
    public static final String COLUMNNAME_C_SalesRegion_ID = "C_SalesRegion_ID";

	/**
	 * Set Maßeinheit.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get Maßeinheit.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_UOM_ID();

	public org.compiere.model.I_C_UOM getC_UOM();

	public void setC_UOM(org.compiere.model.I_C_UOM C_UOM);

    /** Column definition for C_UOM_ID */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, org.compiere.model.I_C_UOM> COLUMN_C_UOM_ID = new org.adempiere.model.ModelColumn<I_I_GLJournal, org.compiere.model.I_C_UOM>(I_I_GLJournal.class, "C_UOM_ID", org.compiere.model.I_C_UOM.class);
    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Set Wechselkurs.
	 * Currency Conversion Rate
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCurrencyRate (java.math.BigDecimal CurrencyRate);

	/**
	 * Get Wechselkurs.
	 * Currency Conversion Rate
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getCurrencyRate();

    /** Column definition for CurrencyRate */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, Object> COLUMN_CurrencyRate = new org.adempiere.model.ModelColumn<I_I_GLJournal, Object>(I_I_GLJournal.class, "CurrencyRate", null);
    /** Column name CurrencyRate */
    public static final String COLUMNNAME_CurrencyRate = "CurrencyRate";

	/**
	 * Set Kombination Aus.
	 * Gültige Kontenkombination
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_ValidCombinationFrom_ID (int C_ValidCombinationFrom_ID);

	/**
	 * Get Kombination Aus.
	 * Gültige Kontenkombination
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_ValidCombinationFrom_ID();

	public org.compiere.model.I_C_ElementValue getC_ValidCombinationFrom();

	public void setC_ValidCombinationFrom(org.compiere.model.I_C_ElementValue C_ValidCombinationFrom);

    /** Column definition for C_ValidCombinationFrom_ID */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, org.compiere.model.I_C_ElementValue> COLUMN_C_ValidCombinationFrom_ID = new org.adempiere.model.ModelColumn<I_I_GLJournal, org.compiere.model.I_C_ElementValue>(I_I_GLJournal.class, "C_ValidCombinationFrom_ID", org.compiere.model.I_C_ElementValue.class);
    /** Column name C_ValidCombinationFrom_ID */
    public static final String COLUMNNAME_C_ValidCombinationFrom_ID = "C_ValidCombinationFrom_ID";

	/**
	 * Set Kombination Zu.
	 * Gültige Kontenkombination
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_ValidCombinationTo_ID (int C_ValidCombinationTo_ID);

	/**
	 * Get Kombination Zu.
	 * Gültige Kontenkombination
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_ValidCombinationTo_ID();

	public org.compiere.model.I_C_ElementValue getC_ValidCombinationTo();

	public void setC_ValidCombinationTo(org.compiere.model.I_C_ElementValue C_ValidCombinationTo);

    /** Column definition for C_ValidCombinationTo_ID */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, org.compiere.model.I_C_ElementValue> COLUMN_C_ValidCombinationTo_ID = new org.adempiere.model.ModelColumn<I_I_GLJournal, org.compiere.model.I_C_ElementValue>(I_I_GLJournal.class, "C_ValidCombinationTo_ID", org.compiere.model.I_C_ElementValue.class);
    /** Column name C_ValidCombinationTo_ID */
    public static final String COLUMNNAME_C_ValidCombinationTo_ID = "C_ValidCombinationTo_ID";

	/**
	 * Set Buchungsdatum.
	 * Accounting Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateAcct (java.sql.Timestamp DateAcct);

	/**
	 * Get Buchungsdatum.
	 * Accounting Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateAcct();

    /** Column definition for DateAcct */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, Object> COLUMN_DateAcct = new org.adempiere.model.ModelColumn<I_I_GLJournal, Object>(I_I_GLJournal.class, "DateAcct", null);
    /** Column name DateAcct */
    public static final String COLUMNNAME_DateAcct = "DateAcct";

	/**
	 * Set Beschreibung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescription (java.lang.String Description);

	/**
	 * Get Beschreibung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_I_GLJournal, Object>(I_I_GLJournal.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Belegart-Bezeichnung.
	 * Name of the Document Type
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDocTypeName (java.lang.String DocTypeName);

	/**
	 * Get Belegart-Bezeichnung.
	 * Name of the Document Type
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocTypeName();

    /** Column definition for DocTypeName */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, Object> COLUMN_DocTypeName = new org.adempiere.model.ModelColumn<I_I_GLJournal, Object>(I_I_GLJournal.class, "DocTypeName", null);
    /** Column name DocTypeName */
    public static final String COLUMNNAME_DocTypeName = "DocTypeName";

	/**
	 * Set Budget.
	 * General Ledger Budget
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setGL_Budget_ID (int GL_Budget_ID);

	/**
	 * Get Budget.
	 * General Ledger Budget
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getGL_Budget_ID();

	public org.compiere.model.I_GL_Budget getGL_Budget();

	public void setGL_Budget(org.compiere.model.I_GL_Budget GL_Budget);

    /** Column definition for GL_Budget_ID */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, org.compiere.model.I_GL_Budget> COLUMN_GL_Budget_ID = new org.adempiere.model.ModelColumn<I_I_GLJournal, org.compiere.model.I_GL_Budget>(I_I_GLJournal.class, "GL_Budget_ID", org.compiere.model.I_GL_Budget.class);
    /** Column name GL_Budget_ID */
    public static final String COLUMNNAME_GL_Budget_ID = "GL_Budget_ID";

	/**
	 * Set Hauptbuch - Kategorie.
	 * General Ledger Category
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setGL_Category_ID (int GL_Category_ID);

	/**
	 * Get Hauptbuch - Kategorie.
	 * General Ledger Category
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getGL_Category_ID();

	public org.compiere.model.I_GL_Category getGL_Category();

	public void setGL_Category(org.compiere.model.I_GL_Category GL_Category);

    /** Column definition for GL_Category_ID */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, org.compiere.model.I_GL_Category> COLUMN_GL_Category_ID = new org.adempiere.model.ModelColumn<I_I_GLJournal, org.compiere.model.I_GL_Category>(I_I_GLJournal.class, "GL_Category_ID", org.compiere.model.I_GL_Category.class);
    /** Column name GL_Category_ID */
    public static final String COLUMNNAME_GL_Category_ID = "GL_Category_ID";

	/**
	 * Set Journal-Lauf.
	 * General Ledger Journal Batch
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setGL_JournalBatch_ID (int GL_JournalBatch_ID);

	/**
	 * Get Journal-Lauf.
	 * General Ledger Journal Batch
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getGL_JournalBatch_ID();

	public org.compiere.model.I_GL_JournalBatch getGL_JournalBatch();

	public void setGL_JournalBatch(org.compiere.model.I_GL_JournalBatch GL_JournalBatch);

    /** Column definition for GL_JournalBatch_ID */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, org.compiere.model.I_GL_JournalBatch> COLUMN_GL_JournalBatch_ID = new org.adempiere.model.ModelColumn<I_I_GLJournal, org.compiere.model.I_GL_JournalBatch>(I_I_GLJournal.class, "GL_JournalBatch_ID", org.compiere.model.I_GL_JournalBatch.class);
    /** Column name GL_JournalBatch_ID */
    public static final String COLUMNNAME_GL_JournalBatch_ID = "GL_JournalBatch_ID";

	/**
	 * Set Journal.
	 * General Ledger Journal
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setGL_Journal_ID (int GL_Journal_ID);

	/**
	 * Get Journal.
	 * General Ledger Journal
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getGL_Journal_ID();

	public org.compiere.model.I_GL_Journal getGL_Journal();

	public void setGL_Journal(org.compiere.model.I_GL_Journal GL_Journal);

    /** Column definition for GL_Journal_ID */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, org.compiere.model.I_GL_Journal> COLUMN_GL_Journal_ID = new org.adempiere.model.ModelColumn<I_I_GLJournal, org.compiere.model.I_GL_Journal>(I_I_GLJournal.class, "GL_Journal_ID", org.compiere.model.I_GL_Journal.class);
    /** Column name GL_Journal_ID */
    public static final String COLUMNNAME_GL_Journal_ID = "GL_Journal_ID";

	/**
	 * Set Journal-Position.
	 * General Ledger Journal Line
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setGL_JournalLine_ID (int GL_JournalLine_ID);

	/**
	 * Get Journal-Position.
	 * General Ledger Journal Line
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getGL_JournalLine_ID();

	public org.compiere.model.I_GL_JournalLine getGL_JournalLine();

	public void setGL_JournalLine(org.compiere.model.I_GL_JournalLine GL_JournalLine);

    /** Column definition for GL_JournalLine_ID */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, org.compiere.model.I_GL_JournalLine> COLUMN_GL_JournalLine_ID = new org.adempiere.model.ModelColumn<I_I_GLJournal, org.compiere.model.I_GL_JournalLine>(I_I_GLJournal.class, "GL_JournalLine_ID", org.compiere.model.I_GL_JournalLine.class);
    /** Column name GL_JournalLine_ID */
    public static final String COLUMNNAME_GL_JournalLine_ID = "GL_JournalLine_ID";

	/**
	 * Set Import-Fehlermeldung.
	 * Messages generated from import process
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setI_ErrorMsg (java.lang.String I_ErrorMsg);

	/**
	 * Get Import-Fehlermeldung.
	 * Messages generated from import process
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getI_ErrorMsg();

    /** Column definition for I_ErrorMsg */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, Object> COLUMN_I_ErrorMsg = new org.adempiere.model.ModelColumn<I_I_GLJournal, Object>(I_I_GLJournal.class, "I_ErrorMsg", null);
    /** Column name I_ErrorMsg */
    public static final String COLUMNNAME_I_ErrorMsg = "I_ErrorMsg";

	/**
	 * Set Import - Hauptbuchjournal.
	 * Import General Ledger Journal
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setI_GLJournal_ID (int I_GLJournal_ID);

	/**
	 * Get Import - Hauptbuchjournal.
	 * Import General Ledger Journal
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getI_GLJournal_ID();

    /** Column definition for I_GLJournal_ID */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, Object> COLUMN_I_GLJournal_ID = new org.adempiere.model.ModelColumn<I_I_GLJournal, Object>(I_I_GLJournal.class, "I_GLJournal_ID", null);
    /** Column name I_GLJournal_ID */
    public static final String COLUMNNAME_I_GLJournal_ID = "I_GLJournal_ID";

	/**
	 * Set Importiert.
	 * Has this import been processed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setI_IsImported (boolean I_IsImported);

	/**
	 * Get Importiert.
	 * Has this import been processed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isI_IsImported();

    /** Column definition for I_IsImported */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, Object> COLUMN_I_IsImported = new org.adempiere.model.ModelColumn<I_I_GLJournal, Object>(I_I_GLJournal.class, "I_IsImported", null);
    /** Column name I_IsImported */
    public static final String COLUMNNAME_I_IsImported = "I_IsImported";

	/**
	 * Set Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_I_GLJournal, Object>(I_I_GLJournal.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Neuen Lauf erstellen.
	 * If selected a new batch is created
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsCreateNewBatch (boolean IsCreateNewBatch);

	/**
	 * Get Neuen Lauf erstellen.
	 * If selected a new batch is created
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isCreateNewBatch();

    /** Column definition for IsCreateNewBatch */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, Object> COLUMN_IsCreateNewBatch = new org.adempiere.model.ModelColumn<I_I_GLJournal, Object>(I_I_GLJournal.class, "IsCreateNewBatch", null);
    /** Column name IsCreateNewBatch */
    public static final String COLUMNNAME_IsCreateNewBatch = "IsCreateNewBatch";

	/**
	 * Set Neues Journal anlegen.
	 * If selected a new journal within the batch is created
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsCreateNewJournal (boolean IsCreateNewJournal);

	/**
	 * Get Neues Journal anlegen.
	 * If selected a new journal within the batch is created
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isCreateNewJournal();

    /** Column definition for IsCreateNewJournal */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, Object> COLUMN_IsCreateNewJournal = new org.adempiere.model.ModelColumn<I_I_GLJournal, Object>(I_I_GLJournal.class, "IsCreateNewJournal", null);
    /** Column name IsCreateNewJournal */
    public static final String COLUMNNAME_IsCreateNewJournal = "IsCreateNewJournal";

	/**
	 * Set ISO Währungscode.
	 * Three letter ISO 4217 Code of the Currency
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setISO_Code (java.lang.String ISO_Code);

	/**
	 * Get ISO Währungscode.
	 * Three letter ISO 4217 Code of the Currency
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getISO_Code();

    /** Column definition for ISO_Code */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, Object> COLUMN_ISO_Code = new org.adempiere.model.ModelColumn<I_I_GLJournal, Object>(I_I_GLJournal.class, "ISO_Code", null);
    /** Column name ISO_Code */
    public static final String COLUMNNAME_ISO_Code = "ISO_Code";

	/**
	 * Set Journalbeleg-Nr..
	 * Document number of the Journal
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setJournalDocumentNo (java.lang.String JournalDocumentNo);

	/**
	 * Get Journalbeleg-Nr..
	 * Document number of the Journal
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getJournalDocumentNo();

    /** Column definition for JournalDocumentNo */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, Object> COLUMN_JournalDocumentNo = new org.adempiere.model.ModelColumn<I_I_GLJournal, Object>(I_I_GLJournal.class, "JournalDocumentNo", null);
    /** Column name JournalDocumentNo */
    public static final String COLUMNNAME_JournalDocumentNo = "JournalDocumentNo";

	/**
	 * Set Zeile Nr..
	 * Unique line for this document
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLine (int Line);

	/**
	 * Get Zeile Nr..
	 * Unique line for this document
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getLine();

    /** Column definition for Line */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, Object> COLUMN_Line = new org.adempiere.model.ModelColumn<I_I_GLJournal, Object>(I_I_GLJournal.class, "Line", null);
    /** Column name Line */
    public static final String COLUMNNAME_Line = "Line";

	/**
	 * Set Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Product_ID();

	public org.compiere.model.I_M_Product getM_Product();

	public void setM_Product(org.compiere.model.I_M_Product M_Product);

    /** Column definition for M_Product_ID */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_I_GLJournal, org.compiere.model.I_M_Product>(I_I_GLJournal.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Schlüssel buchende Org.
	 * Key of the Transaction Organization
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setOrgTrxValue (java.lang.String OrgTrxValue);

	/**
	 * Get Schlüssel buchende Org.
	 * Key of the Transaction Organization
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getOrgTrxValue();

    /** Column definition for OrgTrxValue */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, Object> COLUMN_OrgTrxValue = new org.adempiere.model.ModelColumn<I_I_GLJournal, Object>(I_I_GLJournal.class, "OrgTrxValue", null);
    /** Column name OrgTrxValue */
    public static final String COLUMNNAME_OrgTrxValue = "OrgTrxValue";

	/**
	 * Set Organisations-Schlüssel.
	 * Key of the Organization
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setOrgValue (java.lang.String OrgValue);

	/**
	 * Get Organisations-Schlüssel.
	 * Key of the Organization
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getOrgValue();

    /** Column definition for OrgValue */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, Object> COLUMN_OrgValue = new org.adempiere.model.ModelColumn<I_I_GLJournal, Object>(I_I_GLJournal.class, "OrgValue", null);
    /** Column name OrgValue */
    public static final String COLUMNNAME_OrgValue = "OrgValue";

	/**
	 * Set Buchungsart.
	 * The type of posted amount for the transaction
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPostingType (java.lang.String PostingType);

	/**
	 * Get Buchungsart.
	 * The type of posted amount for the transaction
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPostingType();

    /** Column definition for PostingType */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, Object> COLUMN_PostingType = new org.adempiere.model.ModelColumn<I_I_GLJournal, Object>(I_I_GLJournal.class, "PostingType", null);
    /** Column name PostingType */
    public static final String COLUMNNAME_PostingType = "PostingType";

	/**
	 * Set Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProcessed (boolean Processed);

	/**
	 * Get Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isProcessed();

    /** Column definition for Processed */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_I_GLJournal, Object>(I_I_GLJournal.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Verarbeiten.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProcessing (boolean Processing);

	/**
	 * Get Verarbeiten.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isProcessing();

    /** Column definition for Processing */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, Object> COLUMN_Processing = new org.adempiere.model.ModelColumn<I_I_GLJournal, Object>(I_I_GLJournal.class, "Processing", null);
    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Produktschlüssel.
	 * Key of the Product
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProductValue (java.lang.String ProductValue);

	/**
	 * Get Produktschlüssel.
	 * Key of the Product
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getProductValue();

    /** Column definition for ProductValue */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, Object> COLUMN_ProductValue = new org.adempiere.model.ModelColumn<I_I_GLJournal, Object>(I_I_GLJournal.class, "ProductValue", null);
    /** Column name ProductValue */
    public static final String COLUMNNAME_ProductValue = "ProductValue";

	/**
	 * Set Projekt-Schlüssel.
	 * Key of the Project
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProjectValue (java.lang.String ProjectValue);

	/**
	 * Get Projekt-Schlüssel.
	 * Key of the Project
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getProjectValue();

    /** Column definition for ProjectValue */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, Object> COLUMN_ProjectValue = new org.adempiere.model.ModelColumn<I_I_GLJournal, Object>(I_I_GLJournal.class, "ProjectValue", null);
    /** Column name ProjectValue */
    public static final String COLUMNNAME_ProjectValue = "ProjectValue";

	/**
	 * Set Menge.
	 * Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQty (java.math.BigDecimal Qty);

	/**
	 * Get Menge.
	 * Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQty();

    /** Column definition for Qty */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, Object> COLUMN_Qty = new org.adempiere.model.ModelColumn<I_I_GLJournal, Object>(I_I_GLJournal.class, "Qty", null);
    /** Column name Qty */
    public static final String COLUMNNAME_Qty = "Qty";

	/**
	 * Set SKU.
	 * Stock Keeping Unit
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSKU (java.lang.String SKU);

	/**
	 * Get SKU.
	 * Stock Keeping Unit
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getSKU();

    /** Column definition for SKU */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, Object> COLUMN_SKU = new org.adempiere.model.ModelColumn<I_I_GLJournal, Object>(I_I_GLJournal.class, "SKU", null);
    /** Column name SKU */
    public static final String COLUMNNAME_SKU = "SKU";

	/**
	 * Set UPC/EAN.
	 * Bar Code (Universal Product Code or its superset European Article Number)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setUPC (java.lang.String UPC);

	/**
	 * Get UPC/EAN.
	 * Bar Code (Universal Product Code or its superset European Article Number)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getUPC();

    /** Column definition for UPC */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, Object> COLUMN_UPC = new org.adempiere.model.ModelColumn<I_I_GLJournal, Object>(I_I_GLJournal.class, "UPC", null);
    /** Column name UPC */
    public static final String COLUMNNAME_UPC = "UPC";

	/**
	 * Get Aktualisiert.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_I_GLJournal, Object>(I_I_GLJournal.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_I_GLJournal, org.compiere.model.I_AD_User>(I_I_GLJournal.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Nutzer 1.
	 * User defined list element #1
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setUser1_ID (int User1_ID);

	/**
	 * Get Nutzer 1.
	 * User defined list element #1
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getUser1_ID();

	public org.compiere.model.I_C_ElementValue getUser1();

	public void setUser1(org.compiere.model.I_C_ElementValue User1);

    /** Column definition for User1_ID */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, org.compiere.model.I_C_ElementValue> COLUMN_User1_ID = new org.adempiere.model.ModelColumn<I_I_GLJournal, org.compiere.model.I_C_ElementValue>(I_I_GLJournal.class, "User1_ID", org.compiere.model.I_C_ElementValue.class);
    /** Column name User1_ID */
    public static final String COLUMNNAME_User1_ID = "User1_ID";

	/**
	 * Set Nutzer 2.
	 * User defined list element #2
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setUser2_ID (int User2_ID);

	/**
	 * Get Nutzer 2.
	 * User defined list element #2
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getUser2_ID();

	public org.compiere.model.I_C_ElementValue getUser2();

	public void setUser2(org.compiere.model.I_C_ElementValue User2);

    /** Column definition for User2_ID */
    public static final org.adempiere.model.ModelColumn<I_I_GLJournal, org.compiere.model.I_C_ElementValue> COLUMN_User2_ID = new org.adempiere.model.ModelColumn<I_I_GLJournal, org.compiere.model.I_C_ElementValue>(I_I_GLJournal.class, "User2_ID", org.compiere.model.I_C_ElementValue.class);
    /** Column name User2_ID */
    public static final String COLUMNNAME_User2_ID = "User2_ID";
}
