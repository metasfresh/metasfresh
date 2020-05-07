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


/** Generated Interface for M_Requisition
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_Requisition 
{

    /** TableName=M_Requisition */
    public static final String Table_Name = "M_Requisition";

    /** AD_Table_ID=702 */
    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org 
     */
    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(1);

    /** Load Meta Data */

	/** Get Mandant.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client() throws RuntimeException;

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Requisition, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_Requisition, org.compiere.model.I_AD_Client>(I_M_Requisition.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Set Sektion.
	  * Organisatorische Einheit des Mandanten
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Sektion.
	  * Organisatorische Einheit des Mandanten
	  */
	public int getAD_Org_ID();

	public org.compiere.model.I_AD_Org getAD_Org() throws RuntimeException;

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Requisition, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_Requisition, org.compiere.model.I_AD_Org>(I_M_Requisition.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Ansprechpartner.
	  * User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User_ID (int AD_User_ID);

	/** Get Ansprechpartner.
	  * User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID();

	public org.compiere.model.I_AD_User getAD_User() throws RuntimeException;

	public void setAD_User(org.compiere.model.I_AD_User AD_User);

    /** Column definition for AD_User_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Requisition, org.compiere.model.I_AD_User> COLUMN_AD_User_ID = new org.adempiere.model.ModelColumn<I_M_Requisition, org.compiere.model.I_AD_User>(I_M_Requisition.class, "AD_User_ID", org.compiere.model.I_AD_User.class);
    /** Column name AD_User_ID */
    public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/** Set Belegart.
	  * Document type or rules
	  */
	public void setC_DocType_ID (int C_DocType_ID);

	/** Get Belegart.
	  * Document type or rules
	  */
	public int getC_DocType_ID();

	public org.compiere.model.I_C_DocType getC_DocType() throws RuntimeException;

	public void setC_DocType(org.compiere.model.I_C_DocType C_DocType);

    /** Column definition for C_DocType_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Requisition, org.compiere.model.I_C_DocType> COLUMN_C_DocType_ID = new org.adempiere.model.ModelColumn<I_M_Requisition, org.compiere.model.I_C_DocType>(I_M_Requisition.class, "C_DocType_ID", org.compiere.model.I_C_DocType.class);
    /** Column name C_DocType_ID */
    public static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/** Get Erstellt.
	  * Date this record was created
	  */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_M_Requisition, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_Requisition, Object>(I_M_Requisition.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Erstellt durch.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_M_Requisition, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_Requisition, org.compiere.model.I_AD_User>(I_M_Requisition.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Set Belegdatum.
	  * Datum des Belegs
	  */
	public void setDateDoc (java.sql.Timestamp DateDoc);

	/** Get Belegdatum.
	  * Datum des Belegs
	  */
	public java.sql.Timestamp getDateDoc();

    /** Column definition for DateDoc */
    public static final org.adempiere.model.ModelColumn<I_M_Requisition, Object> COLUMN_DateDoc = new org.adempiere.model.ModelColumn<I_M_Requisition, Object>(I_M_Requisition.class, "DateDoc", null);
    /** Column name DateDoc */
    public static final String COLUMNNAME_DateDoc = "DateDoc";

	/** Set Date Required.
	  * Date when required
	  */
	public void setDateRequired (java.sql.Timestamp DateRequired);

	/** Get Date Required.
	  * Date when required
	  */
	public java.sql.Timestamp getDateRequired();

    /** Column definition for DateRequired */
    public static final org.adempiere.model.ModelColumn<I_M_Requisition, Object> COLUMN_DateRequired = new org.adempiere.model.ModelColumn<I_M_Requisition, Object>(I_M_Requisition.class, "DateRequired", null);
    /** Column name DateRequired */
    public static final String COLUMNNAME_DateRequired = "DateRequired";

	/** Set Beschreibung	  */
	public void setDescription (java.lang.String Description);

	/** Get Beschreibung	  */
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_M_Requisition, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_M_Requisition, Object>(I_M_Requisition.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Belegverarbeitung.
	  * The targeted status of the document
	  */
	public void setDocAction (java.lang.String DocAction);

	/** Get Belegverarbeitung.
	  * The targeted status of the document
	  */
	public java.lang.String getDocAction();

    /** Column definition for DocAction */
    public static final org.adempiere.model.ModelColumn<I_M_Requisition, Object> COLUMN_DocAction = new org.adempiere.model.ModelColumn<I_M_Requisition, Object>(I_M_Requisition.class, "DocAction", null);
    /** Column name DocAction */
    public static final String COLUMNNAME_DocAction = "DocAction";

	/** Set Belegstatus.
	  * The current status of the document
	  */
	public void setDocStatus (java.lang.String DocStatus);

	/** Get Belegstatus.
	  * The current status of the document
	  */
	public java.lang.String getDocStatus();

    /** Column definition for DocStatus */
    public static final org.adempiere.model.ModelColumn<I_M_Requisition, Object> COLUMN_DocStatus = new org.adempiere.model.ModelColumn<I_M_Requisition, Object>(I_M_Requisition.class, "DocStatus", null);
    /** Column name DocStatus */
    public static final String COLUMNNAME_DocStatus = "DocStatus";

	/** Set Beleg Nr..
	  * Document sequence number of the document
	  */
	public void setDocumentNo (java.lang.String DocumentNo);

	/** Get Beleg Nr..
	  * Document sequence number of the document
	  */
	public java.lang.String getDocumentNo();

    /** Column definition for DocumentNo */
    public static final org.adempiere.model.ModelColumn<I_M_Requisition, Object> COLUMN_DocumentNo = new org.adempiere.model.ModelColumn<I_M_Requisition, Object>(I_M_Requisition.class, "DocumentNo", null);
    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/** Set Kommentar/Hilfe.
	  * Comment or Hint
	  */
	public void setHelp (java.lang.String Help);

	/** Get Kommentar/Hilfe.
	  * Comment or Hint
	  */
	public java.lang.String getHelp();

    /** Column definition for Help */
    public static final org.adempiere.model.ModelColumn<I_M_Requisition, Object> COLUMN_Help = new org.adempiere.model.ModelColumn<I_M_Requisition, Object>(I_M_Requisition.class, "Help", null);
    /** Column name Help */
    public static final String COLUMNNAME_Help = "Help";

	/** Set Aktiv.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Aktiv.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_M_Requisition, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_Requisition, Object>(I_M_Requisition.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Freigegeben.
	  * Indicates if this document requires approval
	  */
	public void setIsApproved (boolean IsApproved);

	/** Get Freigegeben.
	  * Indicates if this document requires approval
	  */
	public boolean isApproved();

    /** Column definition for IsApproved */
    public static final org.adempiere.model.ModelColumn<I_M_Requisition, Object> COLUMN_IsApproved = new org.adempiere.model.ModelColumn<I_M_Requisition, Object>(I_M_Requisition.class, "IsApproved", null);
    /** Column name IsApproved */
    public static final String COLUMNNAME_IsApproved = "IsApproved";

	/** Set Preisliste.
	  * Unique identifier of a Price List
	  */
	public void setM_PriceList_ID (int M_PriceList_ID);

	/** Get Preisliste.
	  * Unique identifier of a Price List
	  */
	public int getM_PriceList_ID();

	public org.compiere.model.I_M_PriceList getM_PriceList() throws RuntimeException;

	public void setM_PriceList(org.compiere.model.I_M_PriceList M_PriceList);

    /** Column definition for M_PriceList_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Requisition, org.compiere.model.I_M_PriceList> COLUMN_M_PriceList_ID = new org.adempiere.model.ModelColumn<I_M_Requisition, org.compiere.model.I_M_PriceList>(I_M_Requisition.class, "M_PriceList_ID", org.compiere.model.I_M_PriceList.class);
    /** Column name M_PriceList_ID */
    public static final String COLUMNNAME_M_PriceList_ID = "M_PriceList_ID";

	/** Set Bedarf.
	  * Material Requisition
	  */
	public void setM_Requisition_ID (int M_Requisition_ID);

	/** Get Bedarf.
	  * Material Requisition
	  */
	public int getM_Requisition_ID();

    /** Column definition for M_Requisition_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Requisition, Object> COLUMN_M_Requisition_ID = new org.adempiere.model.ModelColumn<I_M_Requisition, Object>(I_M_Requisition.class, "M_Requisition_ID", null);
    /** Column name M_Requisition_ID */
    public static final String COLUMNNAME_M_Requisition_ID = "M_Requisition_ID";

	/** Set M_Requisition_includedTab	  */
	public void setM_Requisition_includedTab (java.lang.String M_Requisition_includedTab);

	/** Get M_Requisition_includedTab	  */
	public java.lang.String getM_Requisition_includedTab();

    /** Column definition for M_Requisition_includedTab */
    public static final org.adempiere.model.ModelColumn<I_M_Requisition, Object> COLUMN_M_Requisition_includedTab = new org.adempiere.model.ModelColumn<I_M_Requisition, Object>(I_M_Requisition.class, "M_Requisition_includedTab", null);
    /** Column name M_Requisition_includedTab */
    public static final String COLUMNNAME_M_Requisition_includedTab = "M_Requisition_includedTab";

	/** Set Lager.
	  * Storage Warehouse and Service Point
	  */
	public void setM_Warehouse_ID (int M_Warehouse_ID);

	/** Get Lager.
	  * Storage Warehouse and Service Point
	  */
	public int getM_Warehouse_ID();

	public org.compiere.model.I_M_Warehouse getM_Warehouse() throws RuntimeException;

	public void setM_Warehouse(org.compiere.model.I_M_Warehouse M_Warehouse);

    /** Column definition for M_Warehouse_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Requisition, org.compiere.model.I_M_Warehouse> COLUMN_M_Warehouse_ID = new org.adempiere.model.ModelColumn<I_M_Requisition, org.compiere.model.I_M_Warehouse>(I_M_Requisition.class, "M_Warehouse_ID", org.compiere.model.I_M_Warehouse.class);
    /** Column name M_Warehouse_ID */
    public static final String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/** Set Verbucht.
	  * Posting status
	  */
	public void setPosted (boolean Posted);

	/** Get Verbucht.
	  * Posting status
	  */
	public boolean isPosted();

    /** Column definition for Posted */
    public static final org.adempiere.model.ModelColumn<I_M_Requisition, Object> COLUMN_Posted = new org.adempiere.model.ModelColumn<I_M_Requisition, Object>(I_M_Requisition.class, "Posted", null);
    /** Column name Posted */
    public static final String COLUMNNAME_Posted = "Posted";

	/** Set Priorität.
	  * Priority of a document
	  */
	public void setPriorityRule (java.lang.String PriorityRule);

	/** Get Priorität.
	  * Priority of a document
	  */
	public java.lang.String getPriorityRule();

    /** Column definition for PriorityRule */
    public static final org.adempiere.model.ModelColumn<I_M_Requisition, Object> COLUMN_PriorityRule = new org.adempiere.model.ModelColumn<I_M_Requisition, Object>(I_M_Requisition.class, "PriorityRule", null);
    /** Column name PriorityRule */
    public static final String COLUMNNAME_PriorityRule = "PriorityRule";

	/** Set Verarbeitet.
	  * Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	public void setProcessed (boolean Processed);

	/** Get Verarbeitet.
	  * Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	public boolean isProcessed();

    /** Column definition for Processed */
    public static final org.adempiere.model.ModelColumn<I_M_Requisition, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_M_Requisition, Object>(I_M_Requisition.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/** Set Verarbeiten	  */
	public void setProcessing (boolean Processing);

	/** Get Verarbeiten	  */
	public boolean isProcessing();

    /** Column definition for Processing */
    public static final org.adempiere.model.ModelColumn<I_M_Requisition, Object> COLUMN_Processing = new org.adempiere.model.ModelColumn<I_M_Requisition, Object>(I_M_Requisition.class, "Processing", null);
    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/** Set Summe Zeilen.
	  * Total of all document lines
	  */
	public void setTotalLines (java.math.BigDecimal TotalLines);

	/** Get Summe Zeilen.
	  * Total of all document lines
	  */
	public java.math.BigDecimal getTotalLines();

    /** Column definition for TotalLines */
    public static final org.adempiere.model.ModelColumn<I_M_Requisition, Object> COLUMN_TotalLines = new org.adempiere.model.ModelColumn<I_M_Requisition, Object>(I_M_Requisition.class, "TotalLines", null);
    /** Column name TotalLines */
    public static final String COLUMNNAME_TotalLines = "TotalLines";

	/** Get Aktualisiert.
	  * Date this record was updated
	  */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_M_Requisition, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_Requisition, Object>(I_M_Requisition.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Aktualisiert durch.
	  * User who updated this records
	  */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_M_Requisition, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_Requisition, org.compiere.model.I_AD_User>(I_M_Requisition.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
