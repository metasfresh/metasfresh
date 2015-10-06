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


/** Generated Interface for C_PaySelection
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_PaySelection 
{

    /** TableName=C_PaySelection */
    public static final String Table_Name = "C_PaySelection";

    /** AD_Table_ID=426 */
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
    public static final org.adempiere.model.ModelColumn<I_C_PaySelection, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_PaySelection, org.compiere.model.I_AD_Client>(I_C_PaySelection.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_PaySelection, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_PaySelection, org.compiere.model.I_AD_Org>(I_C_PaySelection.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Bankverbindung.
	  * Bankverbindung des Geschäftspartners
	  */
	public void setC_BP_BankAccount_ID (int C_BP_BankAccount_ID);

	/** Get Bankverbindung.
	  * Bankverbindung des Geschäftspartners
	  */
	public int getC_BP_BankAccount_ID();

	public org.compiere.model.I_C_BP_BankAccount getC_BP_BankAccount() throws RuntimeException;

	public void setC_BP_BankAccount(org.compiere.model.I_C_BP_BankAccount C_BP_BankAccount);

    /** Column definition for C_BP_BankAccount_ID */
    public static final org.adempiere.model.ModelColumn<I_C_PaySelection, org.compiere.model.I_C_BP_BankAccount> COLUMN_C_BP_BankAccount_ID = new org.adempiere.model.ModelColumn<I_C_PaySelection, org.compiere.model.I_C_BP_BankAccount>(I_C_PaySelection.class, "C_BP_BankAccount_ID", org.compiere.model.I_C_BP_BankAccount.class);
    /** Column name C_BP_BankAccount_ID */
    public static final String COLUMNNAME_C_BP_BankAccount_ID = "C_BP_BankAccount_ID";

	/** Set Zahlung auswählen.
	  * Payment Selection
	  */
	public void setC_PaySelection_ID (int C_PaySelection_ID);

	/** Get Zahlung auswählen.
	  * Payment Selection
	  */
	public int getC_PaySelection_ID();

    /** Column definition for C_PaySelection_ID */
    public static final org.adempiere.model.ModelColumn<I_C_PaySelection, Object> COLUMN_C_PaySelection_ID = new org.adempiere.model.ModelColumn<I_C_PaySelection, Object>(I_C_PaySelection.class, "C_PaySelection_ID", null);
    /** Column name C_PaySelection_ID */
    public static final String COLUMNNAME_C_PaySelection_ID = "C_PaySelection_ID";

	/** Get Erstellt.
	  * Date this record was created
	  */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_C_PaySelection, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_PaySelection, Object>(I_C_PaySelection.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Erstellt durch.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_C_PaySelection, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_PaySelection, org.compiere.model.I_AD_User>(I_C_PaySelection.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Set Position(en) kopieren von.
	  * Process which will generate a new document lines based on an existing document
	  */
	public void setCreateFrom (java.lang.String CreateFrom);

	/** Get Position(en) kopieren von.
	  * Process which will generate a new document lines based on an existing document
	  */
	public java.lang.String getCreateFrom();

    /** Column definition for CreateFrom */
    public static final org.adempiere.model.ModelColumn<I_C_PaySelection, Object> COLUMN_CreateFrom = new org.adempiere.model.ModelColumn<I_C_PaySelection, Object>(I_C_PaySelection.class, "CreateFrom", null);
    /** Column name CreateFrom */
    public static final String COLUMNNAME_CreateFrom = "CreateFrom";

	/** Set Beschreibung	  */
	public void setDescription (java.lang.String Description);

	/** Get Beschreibung	  */
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_C_PaySelection, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_C_PaySelection, Object>(I_C_PaySelection.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Aktiv.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Aktiv.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_C_PaySelection, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_PaySelection, Object>(I_C_PaySelection.class, "IsActive", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_PaySelection, Object> COLUMN_IsApproved = new org.adempiere.model.ModelColumn<I_C_PaySelection, Object>(I_C_PaySelection.class, "IsApproved", null);
    /** Column name IsApproved */
    public static final String COLUMNNAME_IsApproved = "IsApproved";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (java.lang.String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public java.lang.String getName();

    /** Column definition for Name */
    public static final org.adempiere.model.ModelColumn<I_C_PaySelection, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_C_PaySelection, Object>(I_C_PaySelection.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Payment date.
	  * Date Payment made
	  */
	public void setPayDate (java.sql.Timestamp PayDate);

	/** Get Payment date.
	  * Date Payment made
	  */
	public java.sql.Timestamp getPayDate();

    /** Column definition for PayDate */
    public static final org.adempiere.model.ModelColumn<I_C_PaySelection, Object> COLUMN_PayDate = new org.adempiere.model.ModelColumn<I_C_PaySelection, Object>(I_C_PaySelection.class, "PayDate", null);
    /** Column name PayDate */
    public static final String COLUMNNAME_PayDate = "PayDate";

	/** Set PaySelection_includedTab	  */
	public void setPaySelection_includedTab (java.lang.String PaySelection_includedTab);

	/** Get PaySelection_includedTab	  */
	public java.lang.String getPaySelection_includedTab();

    /** Column definition for PaySelection_includedTab */
    public static final org.adempiere.model.ModelColumn<I_C_PaySelection, Object> COLUMN_PaySelection_includedTab = new org.adempiere.model.ModelColumn<I_C_PaySelection, Object>(I_C_PaySelection.class, "PaySelection_includedTab", null);
    /** Column name PaySelection_includedTab */
    public static final String COLUMNNAME_PaySelection_includedTab = "PaySelection_includedTab";

	/** Set Verarbeitet.
	  * Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	public void setProcessed (boolean Processed);

	/** Get Verarbeitet.
	  * Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	public boolean isProcessed();

    /** Column definition for Processed */
    public static final org.adempiere.model.ModelColumn<I_C_PaySelection, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_C_PaySelection, Object>(I_C_PaySelection.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/** Set Verarbeiten	  */
	public void setProcessing (boolean Processing);

	/** Get Verarbeiten	  */
	public boolean isProcessing();

    /** Column definition for Processing */
    public static final org.adempiere.model.ModelColumn<I_C_PaySelection, Object> COLUMN_Processing = new org.adempiere.model.ModelColumn<I_C_PaySelection, Object>(I_C_PaySelection.class, "Processing", null);
    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/** Set Gesamtbetrag	  */
	public void setTotalAmt (java.math.BigDecimal TotalAmt);

	/** Get Gesamtbetrag	  */
	public java.math.BigDecimal getTotalAmt();

    /** Column definition for TotalAmt */
    public static final org.adempiere.model.ModelColumn<I_C_PaySelection, Object> COLUMN_TotalAmt = new org.adempiere.model.ModelColumn<I_C_PaySelection, Object>(I_C_PaySelection.class, "TotalAmt", null);
    /** Column name TotalAmt */
    public static final String COLUMNNAME_TotalAmt = "TotalAmt";

	/** Get Aktualisiert.
	  * Date this record was updated
	  */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_C_PaySelection, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_PaySelection, Object>(I_C_PaySelection.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Aktualisiert durch.
	  * User who updated this records
	  */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_C_PaySelection, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_PaySelection, org.compiere.model.I_AD_User>(I_C_PaySelection.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
