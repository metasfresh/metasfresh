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


/** Generated Interface for GL_Journal
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_GL_Journal 
{

    /** TableName=GL_Journal */
    public static final String Table_Name = "GL_Journal";

    /** AD_Table_ID=224 */
    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(1);

    /** Load Meta Data */

	/**
	 * Get Mandant.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_GL_Journal, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_GL_Journal, org.compiere.model.I_AD_Client>(I_GL_Journal.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

	public org.compiere.model.I_AD_Org getAD_Org();

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_GL_Journal, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_GL_Journal, org.compiere.model.I_AD_Org>(I_GL_Journal.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Buchführungs-Schema.
	 * Rules for accounting
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_AcctSchema_ID (int C_AcctSchema_ID);

	/**
	 * Get Buchführungs-Schema.
	 * Rules for accounting
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_AcctSchema_ID();

//	public org.compiere.model.I_C_AcctSchema getC_AcctSchema();
//
//	public void setC_AcctSchema(org.compiere.model.I_C_AcctSchema C_AcctSchema);

    /** Column definition for C_AcctSchema_ID */
    public static final org.adempiere.model.ModelColumn<I_GL_Journal, org.compiere.model.I_C_AcctSchema> COLUMN_C_AcctSchema_ID = new org.adempiere.model.ModelColumn<I_GL_Journal, org.compiere.model.I_C_AcctSchema>(I_GL_Journal.class, "C_AcctSchema_ID", org.compiere.model.I_C_AcctSchema.class);
    /** Column name C_AcctSchema_ID */
    public static final String COLUMNNAME_C_AcctSchema_ID = "C_AcctSchema_ID";

	/**
	 * Set Kursart.
	 * Currency Conversion Rate Type
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_ConversionType_ID (int C_ConversionType_ID);

	/**
	 * Get Kursart.
	 * Currency Conversion Rate Type
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_ConversionType_ID();

	public org.compiere.model.I_C_ConversionType getC_ConversionType();

	public void setC_ConversionType(org.compiere.model.I_C_ConversionType C_ConversionType);

    /** Column definition for C_ConversionType_ID */
    public static final org.adempiere.model.ModelColumn<I_GL_Journal, org.compiere.model.I_C_ConversionType> COLUMN_C_ConversionType_ID = new org.adempiere.model.ModelColumn<I_GL_Journal, org.compiere.model.I_C_ConversionType>(I_GL_Journal.class, "C_ConversionType_ID", org.compiere.model.I_C_ConversionType.class);
    /** Column name C_ConversionType_ID */
    public static final String COLUMNNAME_C_ConversionType_ID = "C_ConversionType_ID";

	/**
	 * Set Währung.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Währung.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Currency_ID();

	public org.compiere.model.I_C_Currency getC_Currency();

	public void setC_Currency(org.compiere.model.I_C_Currency C_Currency);

    /** Column definition for C_Currency_ID */
    public static final org.adempiere.model.ModelColumn<I_GL_Journal, org.compiere.model.I_C_Currency> COLUMN_C_Currency_ID = new org.adempiere.model.ModelColumn<I_GL_Journal, org.compiere.model.I_C_Currency>(I_GL_Journal.class, "C_Currency_ID", org.compiere.model.I_C_Currency.class);
    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set Belegart.
	 * Document type or rules
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_DocType_ID (int C_DocType_ID);

	/**
	 * Get Belegart.
	 * Document type or rules
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_DocType_ID();

	public org.compiere.model.I_C_DocType getC_DocType();

	public void setC_DocType(org.compiere.model.I_C_DocType C_DocType);

    /** Column definition for C_DocType_ID */
    public static final org.adempiere.model.ModelColumn<I_GL_Journal, org.compiere.model.I_C_DocType> COLUMN_C_DocType_ID = new org.adempiere.model.ModelColumn<I_GL_Journal, org.compiere.model.I_C_DocType>(I_GL_Journal.class, "C_DocType_ID", org.compiere.model.I_C_DocType.class);
    /** Column name C_DocType_ID */
    public static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/**
	 * Set Kontrollbetrag.
	 * If not zero, the Debit amount of the document must be equal this amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setControlAmt (java.math.BigDecimal ControlAmt);

	/**
	 * Get Kontrollbetrag.
	 * If not zero, the Debit amount of the document must be equal this amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getControlAmt();

    /** Column definition for ControlAmt */
    public static final org.adempiere.model.ModelColumn<I_GL_Journal, Object> COLUMN_ControlAmt = new org.adempiere.model.ModelColumn<I_GL_Journal, Object>(I_GL_Journal.class, "ControlAmt", null);
    /** Column name ControlAmt */
    public static final String COLUMNNAME_ControlAmt = "ControlAmt";

	/**
	 * Get Erstellt.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_GL_Journal, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_GL_Journal, Object>(I_GL_Journal.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_GL_Journal, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_GL_Journal, org.compiere.model.I_AD_User>(I_GL_Journal.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Wechselkurs.
	 * Currency Conversion Rate
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setCurrencyRate (java.math.BigDecimal CurrencyRate);

	/**
	 * Get Wechselkurs.
	 * Currency Conversion Rate
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getCurrencyRate();

    /** Column definition for CurrencyRate */
    public static final org.adempiere.model.ModelColumn<I_GL_Journal, Object> COLUMN_CurrencyRate = new org.adempiere.model.ModelColumn<I_GL_Journal, Object>(I_GL_Journal.class, "CurrencyRate", null);
    /** Column name CurrencyRate */
    public static final String COLUMNNAME_CurrencyRate = "CurrencyRate";

	/**
	 * Set Buchungsdatum.
	 * Accounting Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDateAcct (java.sql.Timestamp DateAcct);

	/**
	 * Get Buchungsdatum.
	 * Accounting Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateAcct();

    /** Column definition for DateAcct */
    public static final org.adempiere.model.ModelColumn<I_GL_Journal, Object> COLUMN_DateAcct = new org.adempiere.model.ModelColumn<I_GL_Journal, Object>(I_GL_Journal.class, "DateAcct", null);
    /** Column name DateAcct */
    public static final String COLUMNNAME_DateAcct = "DateAcct";

	/**
	 * Set Belegdatum.
	 * Datum des Belegs
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDateDoc (java.sql.Timestamp DateDoc);

	/**
	 * Get Belegdatum.
	 * Datum des Belegs
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateDoc();

    /** Column definition for DateDoc */
    public static final org.adempiere.model.ModelColumn<I_GL_Journal, Object> COLUMN_DateDoc = new org.adempiere.model.ModelColumn<I_GL_Journal, Object>(I_GL_Journal.class, "DateDoc", null);
    /** Column name DateDoc */
    public static final String COLUMNNAME_DateDoc = "DateDoc";

	/**
	 * Set Beschreibung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDescription (java.lang.String Description);

	/**
	 * Get Beschreibung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_GL_Journal, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_GL_Journal, Object>(I_GL_Journal.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Belegverarbeitung.
	 * The targeted status of the document
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDocAction (java.lang.String DocAction);

	/**
	 * Get Belegverarbeitung.
	 * The targeted status of the document
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocAction();

    /** Column definition for DocAction */
    public static final org.adempiere.model.ModelColumn<I_GL_Journal, Object> COLUMN_DocAction = new org.adempiere.model.ModelColumn<I_GL_Journal, Object>(I_GL_Journal.class, "DocAction", null);
    /** Column name DocAction */
    public static final String COLUMNNAME_DocAction = "DocAction";

	/**
	 * Set Belegstatus.
	 * The current status of the document
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDocStatus (java.lang.String DocStatus);

	/**
	 * Get Belegstatus.
	 * The current status of the document
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocStatus();

    /** Column definition for DocStatus */
    public static final org.adempiere.model.ModelColumn<I_GL_Journal, Object> COLUMN_DocStatus = new org.adempiere.model.ModelColumn<I_GL_Journal, Object>(I_GL_Journal.class, "DocStatus", null);
    /** Column name DocStatus */
    public static final String COLUMNNAME_DocStatus = "DocStatus";

	/**
	 * Set Beleg Nr..
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDocumentNo (java.lang.String DocumentNo);

	/**
	 * Get Beleg Nr..
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocumentNo();

    /** Column definition for DocumentNo */
    public static final org.adempiere.model.ModelColumn<I_GL_Journal, Object> COLUMN_DocumentNo = new org.adempiere.model.ModelColumn<I_GL_Journal, Object>(I_GL_Journal.class, "DocumentNo", null);
    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

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
    public static final org.adempiere.model.ModelColumn<I_GL_Journal, org.compiere.model.I_GL_Budget> COLUMN_GL_Budget_ID = new org.adempiere.model.ModelColumn<I_GL_Journal, org.compiere.model.I_GL_Budget>(I_GL_Journal.class, "GL_Budget_ID", org.compiere.model.I_GL_Budget.class);
    /** Column name GL_Budget_ID */
    public static final String COLUMNNAME_GL_Budget_ID = "GL_Budget_ID";

	/**
	 * Set Hauptbuch - Kategorie.
	 * General Ledger Category
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setGL_Category_ID (int GL_Category_ID);

	/**
	 * Get Hauptbuch - Kategorie.
	 * General Ledger Category
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getGL_Category_ID();

	public org.compiere.model.I_GL_Category getGL_Category();

	public void setGL_Category(org.compiere.model.I_GL_Category GL_Category);

    /** Column definition for GL_Category_ID */
    public static final org.adempiere.model.ModelColumn<I_GL_Journal, org.compiere.model.I_GL_Category> COLUMN_GL_Category_ID = new org.adempiere.model.ModelColumn<I_GL_Journal, org.compiere.model.I_GL_Category>(I_GL_Journal.class, "GL_Category_ID", org.compiere.model.I_GL_Category.class);
    /** Column name GL_Category_ID */
    public static final String COLUMNNAME_GL_Category_ID = "GL_Category_ID";

	/**
	 * Set Journal-Lauf.
	 * General Ledger Journal Batch
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setGL_JournalBatch_ID (int GL_JournalBatch_ID);

	/**
	 * Get Journal-Lauf.
	 * General Ledger Journal Batch
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getGL_JournalBatch_ID();

	public org.compiere.model.I_GL_JournalBatch getGL_JournalBatch();

	public void setGL_JournalBatch(org.compiere.model.I_GL_JournalBatch GL_JournalBatch);

    /** Column definition for GL_JournalBatch_ID */
    public static final org.adempiere.model.ModelColumn<I_GL_Journal, org.compiere.model.I_GL_JournalBatch> COLUMN_GL_JournalBatch_ID = new org.adempiere.model.ModelColumn<I_GL_Journal, org.compiere.model.I_GL_JournalBatch>(I_GL_Journal.class, "GL_JournalBatch_ID", org.compiere.model.I_GL_JournalBatch.class);
    /** Column name GL_JournalBatch_ID */
    public static final String COLUMNNAME_GL_JournalBatch_ID = "GL_JournalBatch_ID";

	/**
	 * Set Journal.
	 * General Ledger Journal
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setGL_Journal_ID (int GL_Journal_ID);

	/**
	 * Get Journal.
	 * General Ledger Journal
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getGL_Journal_ID();

    /** Column definition for GL_Journal_ID */
    public static final org.adempiere.model.ModelColumn<I_GL_Journal, Object> COLUMN_GL_Journal_ID = new org.adempiere.model.ModelColumn<I_GL_Journal, Object>(I_GL_Journal.class, "GL_Journal_ID", null);
    /** Column name GL_Journal_ID */
    public static final String COLUMNNAME_GL_Journal_ID = "GL_Journal_ID";

	/**
	 * Set Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_GL_Journal, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_GL_Journal, Object>(I_GL_Journal.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Freigegeben.
	 * Indicates if this document requires approval
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsApproved (boolean IsApproved);

	/**
	 * Get Freigegeben.
	 * Indicates if this document requires approval
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isApproved();

    /** Column definition for IsApproved */
    public static final org.adempiere.model.ModelColumn<I_GL_Journal, Object> COLUMN_IsApproved = new org.adempiere.model.ModelColumn<I_GL_Journal, Object>(I_GL_Journal.class, "IsApproved", null);
    /** Column name IsApproved */
    public static final String COLUMNNAME_IsApproved = "IsApproved";

	/**
	 * Set andrucken.
	 * Indicates if this document / line is printed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsPrinted (boolean IsPrinted);

	/**
	 * Get andrucken.
	 * Indicates if this document / line is printed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isPrinted();

    /** Column definition for IsPrinted */
    public static final org.adempiere.model.ModelColumn<I_GL_Journal, Object> COLUMN_IsPrinted = new org.adempiere.model.ModelColumn<I_GL_Journal, Object>(I_GL_Journal.class, "IsPrinted", null);
    /** Column name IsPrinted */
    public static final String COLUMNNAME_IsPrinted = "IsPrinted";

	/**
	 * Set Verbucht.
	 * Posting status
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPosted (boolean Posted);

	/**
	 * Get Verbucht.
	 * Posting status
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isPosted();

    /** Column definition for Posted */
    public static final org.adempiere.model.ModelColumn<I_GL_Journal, Object> COLUMN_Posted = new org.adempiere.model.ModelColumn<I_GL_Journal, Object>(I_GL_Journal.class, "Posted", null);
    /** Column name Posted */
    public static final String COLUMNNAME_Posted = "Posted";

	/**
	 * Set Buchungsart.
	 * The type of posted amount for the transaction
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPostingType (java.lang.String PostingType);

	/**
	 * Get Buchungsart.
	 * The type of posted amount for the transaction
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPostingType();

    /** Column definition for PostingType */
    public static final org.adempiere.model.ModelColumn<I_GL_Journal, Object> COLUMN_PostingType = new org.adempiere.model.ModelColumn<I_GL_Journal, Object>(I_GL_Journal.class, "PostingType", null);
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
    public static final org.adempiere.model.ModelColumn<I_GL_Journal, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_GL_Journal, Object>(I_GL_Journal.class, "Processed", null);
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
    public static final org.adempiere.model.ModelColumn<I_GL_Journal, Object> COLUMN_Processing = new org.adempiere.model.ModelColumn<I_GL_Journal, Object>(I_GL_Journal.class, "Processing", null);
    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Reversal ID.
	 * ID of document reversal
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setReversal_ID (int Reversal_ID);

	/**
	 * Get Reversal ID.
	 * ID of document reversal
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getReversal_ID();

	public org.compiere.model.I_GL_Journal getReversal();

	public void setReversal(org.compiere.model.I_GL_Journal Reversal);

    /** Column definition for Reversal_ID */
    public static final org.adempiere.model.ModelColumn<I_GL_Journal, org.compiere.model.I_GL_Journal> COLUMN_Reversal_ID = new org.adempiere.model.ModelColumn<I_GL_Journal, org.compiere.model.I_GL_Journal>(I_GL_Journal.class, "Reversal_ID", org.compiere.model.I_GL_Journal.class);
    /** Column name Reversal_ID */
    public static final String COLUMNNAME_Reversal_ID = "Reversal_ID";

	/**
	 * Set Total Credit.
	 * Total Credit in document currency
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setTotalCr (java.math.BigDecimal TotalCr);

	/**
	 * Get Total Credit.
	 * Total Credit in document currency
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getTotalCr();

    /** Column definition for TotalCr */
    public static final org.adempiere.model.ModelColumn<I_GL_Journal, Object> COLUMN_TotalCr = new org.adempiere.model.ModelColumn<I_GL_Journal, Object>(I_GL_Journal.class, "TotalCr", null);
    /** Column name TotalCr */
    public static final String COLUMNNAME_TotalCr = "TotalCr";

	/**
	 * Set Total Debit.
	 * Total debit in document currency
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setTotalDr (java.math.BigDecimal TotalDr);

	/**
	 * Get Total Debit.
	 * Total debit in document currency
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getTotalDr();

    /** Column definition for TotalDr */
    public static final org.adempiere.model.ModelColumn<I_GL_Journal, Object> COLUMN_TotalDr = new org.adempiere.model.ModelColumn<I_GL_Journal, Object>(I_GL_Journal.class, "TotalDr", null);
    /** Column name TotalDr */
    public static final String COLUMNNAME_TotalDr = "TotalDr";

	/**
	 * Get Aktualisiert.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_GL_Journal, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_GL_Journal, Object>(I_GL_Journal.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_GL_Journal, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_GL_Journal, org.compiere.model.I_AD_User>(I_GL_Journal.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
