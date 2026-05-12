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


/** Generated Interface for C_TaxDeclarationLine
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_TaxDeclarationLine 
{

    /** TableName=C_TaxDeclarationLine */
    public static final String Table_Name = "C_TaxDeclarationLine";

    /** AD_Table_ID=819 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

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
    public static final org.adempiere.model.ModelColumn<I_C_TaxDeclarationLine, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_TaxDeclarationLine, org.compiere.model.I_AD_Client>(I_C_TaxDeclarationLine.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_TaxDeclarationLine, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_TaxDeclarationLine, org.compiere.model.I_AD_Org>(I_C_TaxDeclarationLine.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_TaxDeclarationLine, org.compiere.model.I_C_Currency> COLUMN_C_Currency_ID = new org.adempiere.model.ModelColumn<I_C_TaxDeclarationLine, org.compiere.model.I_C_Currency>(I_C_TaxDeclarationLine.class, "C_Currency_ID", org.compiere.model.I_C_Currency.class);
    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set Steuererklärung.
	 * Define the declaration to the tax authorities
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_TaxDeclaration_ID (int C_TaxDeclaration_ID);

	/**
	 * Get Steuererklärung.
	 * Define the declaration to the tax authorities
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_TaxDeclaration_ID();

	public org.compiere.model.I_C_TaxDeclaration getC_TaxDeclaration();

	public void setC_TaxDeclaration(org.compiere.model.I_C_TaxDeclaration C_TaxDeclaration);

    /** Column definition for C_TaxDeclaration_ID */
    public static final org.adempiere.model.ModelColumn<I_C_TaxDeclarationLine, org.compiere.model.I_C_TaxDeclaration> COLUMN_C_TaxDeclaration_ID = new org.adempiere.model.ModelColumn<I_C_TaxDeclarationLine, org.compiere.model.I_C_TaxDeclaration>(I_C_TaxDeclarationLine.class, "C_TaxDeclaration_ID", org.compiere.model.I_C_TaxDeclaration.class);
    /** Column name C_TaxDeclaration_ID */
    public static final String COLUMNNAME_C_TaxDeclaration_ID = "C_TaxDeclaration_ID";

	/**
	 * Set Tax Declaration Line.
	 * Tax Declaration Document Information
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_TaxDeclarationLine_ID (int C_TaxDeclarationLine_ID);

	/**
	 * Get Tax Declaration Line.
	 * Tax Declaration Document Information
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_TaxDeclarationLine_ID();

    /** Column definition for C_TaxDeclarationLine_ID */
    public static final org.adempiere.model.ModelColumn<I_C_TaxDeclarationLine, Object> COLUMN_C_TaxDeclarationLine_ID = new org.adempiere.model.ModelColumn<I_C_TaxDeclarationLine, Object>(I_C_TaxDeclarationLine.class, "C_TaxDeclarationLine_ID", null);
    /** Column name C_TaxDeclarationLine_ID */
    public static final String COLUMNNAME_C_TaxDeclarationLine_ID = "C_TaxDeclarationLine_ID";

	/**
	 * Set VAT Code.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_VAT_Code_ID (int C_VAT_Code_ID);

	/**
	 * Get VAT Code.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_VAT_Code_ID();

    /** Column definition for C_VAT_Code_ID */
    public static final org.adempiere.model.ModelColumn<I_C_TaxDeclarationLine, Object> COLUMN_C_VAT_Code_ID = new org.adempiere.model.ModelColumn<>(I_C_TaxDeclarationLine.class, "C_VAT_Code_ID", null);
    /** Column name C_VAT_Code_ID */
    public static final String COLUMNNAME_C_VAT_Code_ID = "C_VAT_Code_ID";

	/**
	 * Set Amount Type.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAmountType (java.lang.String AmountType);

	/**
	 * Get Amount Type.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAmountType();

    /** Column definition for AmountType */
    public static final org.adempiere.model.ModelColumn<I_C_TaxDeclarationLine, Object> COLUMN_AmountType = new org.adempiere.model.ModelColumn<>(I_C_TaxDeclarationLine.class, "AmountType", null);
    /** Column name AmountType */
    public static final String COLUMNNAME_AmountType = "AmountType";

	/**
	 * Set Amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAmount (java.math.BigDecimal Amount);

	/**
	 * Get Amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getAmount();

    /** Column definition for Amount */
    public static final org.adempiere.model.ModelColumn<I_C_TaxDeclarationLine, Object> COLUMN_Amount = new org.adempiere.model.ModelColumn<>(I_C_TaxDeclarationLine.class, "Amount", null);
    /** Column name Amount */
    public static final String COLUMNNAME_Amount = "Amount";

	/**
	 * Set Line Count.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLineCount (java.math.BigDecimal LineCount);

	/**
	 * Get Line Count.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getLineCount();

    /** Column definition for LineCount */
    public static final org.adempiere.model.ModelColumn<I_C_TaxDeclarationLine, Object> COLUMN_LineCount = new org.adempiere.model.ModelColumn<>(I_C_TaxDeclarationLine.class, "LineCount", null);
    /** Column name LineCount */
    public static final String COLUMNNAME_LineCount = "LineCount";

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
    public static final org.adempiere.model.ModelColumn<I_C_TaxDeclarationLine, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_TaxDeclarationLine, Object>(I_C_TaxDeclarationLine.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_TaxDeclarationLine, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_TaxDeclarationLine, org.compiere.model.I_AD_User>(I_C_TaxDeclarationLine.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

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
    public static final org.adempiere.model.ModelColumn<I_C_TaxDeclarationLine, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_C_TaxDeclarationLine, Object>(I_C_TaxDeclarationLine.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

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
    public static final org.adempiere.model.ModelColumn<I_C_TaxDeclarationLine, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_TaxDeclarationLine, Object>(I_C_TaxDeclarationLine.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Zeile Nr..
	 * Unique line for this document
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setLine (int Line);

	/**
	 * Get Zeile Nr..
	 * Unique line for this document
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getLine();

    /** Column definition for Line */
    public static final org.adempiere.model.ModelColumn<I_C_TaxDeclarationLine, Object> COLUMN_Line = new org.adempiere.model.ModelColumn<I_C_TaxDeclarationLine, Object>(I_C_TaxDeclarationLine.class, "Line", null);
    /** Column name Line */
    public static final String COLUMNNAME_Line = "Line";

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
    public static final org.adempiere.model.ModelColumn<I_C_TaxDeclarationLine, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_TaxDeclarationLine, Object>(I_C_TaxDeclarationLine.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_TaxDeclarationLine, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_TaxDeclarationLine, org.compiere.model.I_AD_User>(I_C_TaxDeclarationLine.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
