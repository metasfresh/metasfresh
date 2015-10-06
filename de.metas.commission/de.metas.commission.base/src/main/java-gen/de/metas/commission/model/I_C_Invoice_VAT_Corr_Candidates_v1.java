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
package de.metas.commission.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.compiere.model.MTable;
import org.compiere.util.KeyNamePair;

/** Generated Interface for C_Invoice_VAT_Corr_Candidates_v1
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_C_Invoice_VAT_Corr_Candidates_v1 
{

    /** TableName=C_Invoice_VAT_Corr_Candidates_v1 */
    public static final String Table_Name = "C_Invoice_VAT_Corr_Candidates_v1";

    /** AD_Table_ID=540271 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(1);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Mandant.
	  * Mandant für diese Installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organisation.
	  * Organisatorische Einheit des Mandanten
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organisation.
	  * Organisatorische Einheit des Mandanten
	  */
	public int getAD_Org_ID();

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/** Set Geschäftspartner.
	  * Bezeichnet einen Geschäftspartner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/** Get Geschäftspartner.
	  * Bezeichnet einen Geschäftspartner
	  */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException;

    /** Column name C_BPartner_Name */
    public static final String COLUMNNAME_C_BPartner_Name = "C_BPartner_Name";

	/** Set VP-Name	  */
	public void setC_BPartner_Name (String C_BPartner_Name);

	/** Get VP-Name	  */
	public String getC_BPartner_Name();

    /** Column name C_Invoice_ID */
    public static final String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/** Set Rechnung.
	  * Invoice Identifier
	  */
	public void setC_Invoice_ID (int C_Invoice_ID);

	/** Get Rechnung.
	  * Invoice Identifier
	  */
	public int getC_Invoice_ID();

	public org.compiere.model.I_C_Invoice getC_Invoice() throws RuntimeException;

    /** Column name C_InvoiceLine_ID */
    public static final String COLUMNNAME_C_InvoiceLine_ID = "C_InvoiceLine_ID";

	/** Set Rechnungsposition.
	  * Rechnungszeile
	  */
	public void setC_InvoiceLine_ID (int C_InvoiceLine_ID);

	/** Get Rechnungsposition.
	  * Rechnungszeile
	  */
	public int getC_InvoiceLine_ID();

	public org.compiere.model.I_C_InvoiceLine getC_InvoiceLine() throws RuntimeException;

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Erstellt.
	  * Datum, an dem dieser Eintrag erstellt wurde
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Erstellt durch.
	  * Nutzer, der diesen Eintrag erstellt hat
	  */
	public int getCreatedBy();

    /** Column name C_TaxCategory_ID */
    public static final String COLUMNNAME_C_TaxCategory_ID = "C_TaxCategory_ID";

	/** Set Steuerkategorie.
	  * Steuerkategorie
	  */
	public void setC_TaxCategory_ID (int C_TaxCategory_ID);

	/** Get Steuerkategorie.
	  * Steuerkategorie
	  */
	public int getC_TaxCategory_ID();

	public org.compiere.model.I_C_TaxCategory getC_TaxCategory() throws RuntimeException;

    /** Column name C_TaxCategory_Name */
    public static final String COLUMNNAME_C_TaxCategory_Name = "C_TaxCategory_Name";

	/** Set MwSt-Kategorie	  */
	public void setC_TaxCategory_Name (String C_TaxCategory_Name);

	/** Get MwSt-Kategorie	  */
	public String getC_TaxCategory_Name();

    /** Column name DateInvoiced */
    public static final String COLUMNNAME_DateInvoiced = "DateInvoiced";

	/** Set Rechnungsdatum.
	  * Datum auf der Rechnung
	  */
	public void setDateInvoiced (Timestamp DateInvoiced);

	/** Get Rechnungsdatum.
	  * Datum auf der Rechnung
	  */
	public Timestamp getDateInvoiced();

    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/** Set Document No.
	  * Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo);

	/** Get Document No.
	  * Document sequence number of the document
	  */
	public String getDocumentNo();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Aktiv.
	  * Der Eintrag ist im System aktiv
	  */
	public void setIsActive (boolean IsActive);

	/** Get Aktiv.
	  * Der Eintrag ist im System aktiv
	  */
	public boolean isActive();

    /** Column name IsSmallBusinessAtDate */
    public static final String COLUMNNAME_IsSmallBusinessAtDate = "IsSmallBusinessAtDate";

	/** Set Ist KU am Stichtag 	  */
	public void setIsSmallBusinessAtDate (boolean IsSmallBusinessAtDate);

	/** Get Ist KU am Stichtag 	  */
	public boolean isSmallBusinessAtDate();

    /** Column name Line */
    public static final String COLUMNNAME_Line = "Line";

	/** Set Zeile Nr..
	  * Einzelne Zeile in dem Dokument
	  */
	public void setLine (int Line);

	/** Get Zeile Nr..
	  * Einzelne Zeile in dem Dokument
	  */
	public int getLine();

    /** Column name LineNetAmt */
    public static final String COLUMNNAME_LineNetAmt = "LineNetAmt";

	/** Set Zeilennetto.
	  * Nettowert Zeile (Menge * Einzelpreis) ohne Fracht und Gebühren
	  */
	public void setLineNetAmt (BigDecimal LineNetAmt);

	/** Get Zeilennetto.
	  * Nettowert Zeile (Menge * Einzelpreis) ohne Fracht und Gebühren
	  */
	public BigDecimal getLineNetAmt();

    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/** Set Verarbeitet.
	  * The document has been processed
	  */
	public void setProcessed (boolean Processed);

	/** Get Verarbeitet.
	  * The document has been processed
	  */
	public boolean isProcessed();

    /** Column name Rate */
    public static final String COLUMNNAME_Rate = "Rate";

	/** Set Wechselkurs.
	  * Rate or Tax or Exchange
	  */
	public void setRate (BigDecimal Rate);

	/** Get Wechselkurs.
	  * Rate or Tax or Exchange
	  */
	public BigDecimal getRate();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Aktualisiert.
	  * Datum, an dem dieser Eintrag aktualisiert wurde
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Aktualisiert durch.
	  * Nutzer, der diesen Eintrag aktualisiert hat
	  */
	public int getUpdatedBy();
}
