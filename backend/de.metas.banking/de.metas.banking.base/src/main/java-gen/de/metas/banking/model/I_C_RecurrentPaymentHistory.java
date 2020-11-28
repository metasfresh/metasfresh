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
package de.metas.banking.model;


/** Generated Interface for C_RecurrentPaymentHistory
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_RecurrentPaymentHistory 
{

    /** TableName=C_RecurrentPaymentHistory */
    public static final String Table_Name = "C_RecurrentPaymentHistory";

    /** AD_Table_ID=540095 */
    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */

	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RecurrentPaymentHistory, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_RecurrentPaymentHistory, org.compiere.model.I_AD_Client>(I_C_RecurrentPaymentHistory.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_RecurrentPaymentHistory, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_RecurrentPaymentHistory, org.compiere.model.I_AD_Org>(I_C_RecurrentPaymentHistory.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Rechnung.
	 * Invoice Identifier
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Invoice_ID (int C_Invoice_ID);

	/**
	 * Get Rechnung.
	 * Invoice Identifier
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Invoice_ID();

	public org.compiere.model.I_C_Invoice getC_Invoice();

	public void setC_Invoice(org.compiere.model.I_C_Invoice C_Invoice);

    /** Column definition for C_Invoice_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RecurrentPaymentHistory, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_ID = new org.adempiere.model.ModelColumn<I_C_RecurrentPaymentHistory, org.compiere.model.I_C_Invoice>(I_C_RecurrentPaymentHistory.class, "C_Invoice_ID", org.compiere.model.I_C_Invoice.class);
    /** Column name C_Invoice_ID */
    public static final String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_C_RecurrentPaymentHistory, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_RecurrentPaymentHistory, Object>(I_C_RecurrentPaymentHistory.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_C_RecurrentPaymentHistory, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_RecurrentPaymentHistory, org.compiere.model.I_AD_User>(I_C_RecurrentPaymentHistory.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Recurrent Payment History.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_RecurrentPaymentHistory_ID (int C_RecurrentPaymentHistory_ID);

	/**
	 * Get Recurrent Payment History.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_RecurrentPaymentHistory_ID();

    /** Column definition for C_RecurrentPaymentHistory_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RecurrentPaymentHistory, Object> COLUMN_C_RecurrentPaymentHistory_ID = new org.adempiere.model.ModelColumn<I_C_RecurrentPaymentHistory, Object>(I_C_RecurrentPaymentHistory.class, "C_RecurrentPaymentHistory_ID", null);
    /** Column name C_RecurrentPaymentHistory_ID */
    public static final String COLUMNNAME_C_RecurrentPaymentHistory_ID = "C_RecurrentPaymentHistory_ID";

	/**
	 * Set Recurrent Payment Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_RecurrentPaymentLine_ID (int C_RecurrentPaymentLine_ID);

	/**
	 * Get Recurrent Payment Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_RecurrentPaymentLine_ID();

	public de.metas.banking.model.I_C_RecurrentPaymentLine getC_RecurrentPaymentLine();

	public void setC_RecurrentPaymentLine(de.metas.banking.model.I_C_RecurrentPaymentLine C_RecurrentPaymentLine);

    /** Column definition for C_RecurrentPaymentLine_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RecurrentPaymentHistory, de.metas.banking.model.I_C_RecurrentPaymentLine> COLUMN_C_RecurrentPaymentLine_ID = new org.adempiere.model.ModelColumn<I_C_RecurrentPaymentHistory, de.metas.banking.model.I_C_RecurrentPaymentLine>(I_C_RecurrentPaymentHistory.class, "C_RecurrentPaymentLine_ID", de.metas.banking.model.I_C_RecurrentPaymentLine.class);
    /** Column name C_RecurrentPaymentLine_ID */
    public static final String COLUMNNAME_C_RecurrentPaymentLine_ID = "C_RecurrentPaymentLine_ID";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_C_RecurrentPaymentHistory, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_RecurrentPaymentHistory, Object>(I_C_RecurrentPaymentHistory.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_C_RecurrentPaymentHistory, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_RecurrentPaymentHistory, Object>(I_C_RecurrentPaymentHistory.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_C_RecurrentPaymentHistory, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_RecurrentPaymentHistory, org.compiere.model.I_AD_User>(I_C_RecurrentPaymentHistory.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
