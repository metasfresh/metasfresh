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


/** Generated Interface for C_Currency
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Currency 
{

    /** TableName=C_Currency */
    public static final String Table_Name = "C_Currency";

    /** AD_Table_ID=141 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 6 - System - Client
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(6);

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
    public static final org.adempiere.model.ModelColumn<I_C_Currency, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_Currency, org.compiere.model.I_AD_Client>(I_C_Currency.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_Currency, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_Currency, org.compiere.model.I_AD_Org>(I_C_Currency.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Währung.
	 * The Currency for this record
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Währung.
	 * The Currency for this record
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Currency_ID();

    /** Column definition for C_Currency_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Currency, Object> COLUMN_C_Currency_ID = new org.adempiere.model.ModelColumn<I_C_Currency, Object>(I_C_Currency.class, "C_Currency_ID", null);
    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set Kostenrechnungsgenauigkeit.
	 * Rounding used costing calculations
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setCostingPrecision (int CostingPrecision);

	/**
	 * Get Kostenrechnungsgenauigkeit.
	 * Rounding used costing calculations
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCostingPrecision();

    /** Column definition for CostingPrecision */
    public static final org.adempiere.model.ModelColumn<I_C_Currency, Object> COLUMN_CostingPrecision = new org.adempiere.model.ModelColumn<I_C_Currency, Object>(I_C_Currency.class, "CostingPrecision", null);
    /** Column name CostingPrecision */
    public static final String COLUMNNAME_CostingPrecision = "CostingPrecision";

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
    public static final org.adempiere.model.ModelColumn<I_C_Currency, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_Currency, Object>(I_C_Currency.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Currency, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_Currency, org.compiere.model.I_AD_User>(I_C_Currency.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Symbol.
	 * Symbol of the currency (opt used for printing only)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCurSymbol (java.lang.String CurSymbol);

	/**
	 * Get Symbol.
	 * Symbol of the currency (opt used for printing only)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCurSymbol();

    /** Column definition for CurSymbol */
    public static final org.adempiere.model.ModelColumn<I_C_Currency, Object> COLUMN_CurSymbol = new org.adempiere.model.ModelColumn<I_C_Currency, Object>(I_C_Currency.class, "CurSymbol", null);
    /** Column name CurSymbol */
    public static final String COLUMNNAME_CurSymbol = "CurSymbol";

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
    public static final org.adempiere.model.ModelColumn<I_C_Currency, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_C_Currency, Object>(I_C_Currency.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set EMU Entry Date.
	 * Date when the currency joined / will join the EMU
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEMUEntryDate (java.sql.Timestamp EMUEntryDate);

	/**
	 * Get EMU Entry Date.
	 * Date when the currency joined / will join the EMU
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getEMUEntryDate();

    /** Column definition for EMUEntryDate */
    public static final org.adempiere.model.ModelColumn<I_C_Currency, Object> COLUMN_EMUEntryDate = new org.adempiere.model.ModelColumn<I_C_Currency, Object>(I_C_Currency.class, "EMUEntryDate", null);
    /** Column name EMUEntryDate */
    public static final String COLUMNNAME_EMUEntryDate = "EMUEntryDate";

	/**
	 * Set EMU Rate.
	 * Official rate to the Euro
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEMURate (java.math.BigDecimal EMURate);

	/**
	 * Get EMU Rate.
	 * Official rate to the Euro
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getEMURate();

    /** Column definition for EMURate */
    public static final org.adempiere.model.ModelColumn<I_C_Currency, Object> COLUMN_EMURate = new org.adempiere.model.ModelColumn<I_C_Currency, Object>(I_C_Currency.class, "EMURate", null);
    /** Column name EMURate */
    public static final String COLUMNNAME_EMURate = "EMURate";

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
    public static final org.adempiere.model.ModelColumn<I_C_Currency, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_Currency, Object>(I_C_Currency.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set EMU Member.
	 * This currency is member if the European Monetary Union
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsEMUMember (boolean IsEMUMember);

	/**
	 * Get EMU Member.
	 * This currency is member if the European Monetary Union
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isEMUMember();

    /** Column definition for IsEMUMember */
    public static final org.adempiere.model.ModelColumn<I_C_Currency, Object> COLUMN_IsEMUMember = new org.adempiere.model.ModelColumn<I_C_Currency, Object>(I_C_Currency.class, "IsEMUMember", null);
    /** Column name IsEMUMember */
    public static final String COLUMNNAME_IsEMUMember = "IsEMUMember";

	/**
	 * Set The Euro Currency.
	 * This currency is the Euro
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsEuro (boolean IsEuro);

	/**
	 * Get The Euro Currency.
	 * This currency is the Euro
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isEuro();

    /** Column definition for IsEuro */
    public static final org.adempiere.model.ModelColumn<I_C_Currency, Object> COLUMN_IsEuro = new org.adempiere.model.ModelColumn<I_C_Currency, Object>(I_C_Currency.class, "IsEuro", null);
    /** Column name IsEuro */
    public static final String COLUMNNAME_IsEuro = "IsEuro";

	/**
	 * Set ISO Währungscode.
	 * Three letter ISO 4217 Code of the Currency
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setISO_Code (java.lang.String ISO_Code);

	/**
	 * Get ISO Währungscode.
	 * Three letter ISO 4217 Code of the Currency
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getISO_Code();

    /** Column definition for ISO_Code */
    public static final org.adempiere.model.ModelColumn<I_C_Currency, Object> COLUMN_ISO_Code = new org.adempiere.model.ModelColumn<I_C_Currency, Object>(I_C_Currency.class, "ISO_Code", null);
    /** Column name ISO_Code */
    public static final String COLUMNNAME_ISO_Code = "ISO_Code";

	/**
	 * Set Round Off Factor.
	 * Used to Round Off Payment Amount
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setRoundOffFactor (java.math.BigDecimal RoundOffFactor);

	/**
	 * Get Round Off Factor.
	 * Used to Round Off Payment Amount
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getRoundOffFactor();

    /** Column definition for RoundOffFactor */
    public static final org.adempiere.model.ModelColumn<I_C_Currency, Object> COLUMN_RoundOffFactor = new org.adempiere.model.ModelColumn<I_C_Currency, Object>(I_C_Currency.class, "RoundOffFactor", null);
    /** Column name RoundOffFactor */
    public static final String COLUMNNAME_RoundOffFactor = "RoundOffFactor";

	/**
	 * Set Standardgenauigkeit.
	 * Rule for rounding  calculated amounts
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setStdPrecision (int StdPrecision);

	/**
	 * Get Standardgenauigkeit.
	 * Rule for rounding  calculated amounts
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getStdPrecision();

    /** Column definition for StdPrecision */
    public static final org.adempiere.model.ModelColumn<I_C_Currency, Object> COLUMN_StdPrecision = new org.adempiere.model.ModelColumn<I_C_Currency, Object>(I_C_Currency.class, "StdPrecision", null);
    /** Column name StdPrecision */
    public static final String COLUMNNAME_StdPrecision = "StdPrecision";

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
    public static final org.adempiere.model.ModelColumn<I_C_Currency, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_Currency, Object>(I_C_Currency.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Currency, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_Currency, org.compiere.model.I_AD_User>(I_C_Currency.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
