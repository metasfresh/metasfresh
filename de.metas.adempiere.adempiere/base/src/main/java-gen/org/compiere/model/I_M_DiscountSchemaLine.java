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



/** Generated Interface for M_DiscountSchemaLine
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_DiscountSchemaLine 
{

    /** TableName=M_DiscountSchemaLine */
    public static final String Table_Name = "M_DiscountSchemaLine";

    /** AD_Table_ID=477 */
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

	public org.compiere.model.I_AD_Client getAD_Client() throws RuntimeException;

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, org.compiere.model.I_AD_Client>(I_M_DiscountSchemaLine.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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

	public org.compiere.model.I_AD_Org getAD_Org() throws RuntimeException;

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, org.compiere.model.I_AD_Org>(I_M_DiscountSchemaLine.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

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

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException;

	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner);

    /** Column definition for C_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, org.compiere.model.I_C_BPartner>(I_M_DiscountSchemaLine.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

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

	public org.compiere.model.I_C_ConversionType getC_ConversionType() throws RuntimeException;

	public void setC_ConversionType(org.compiere.model.I_C_ConversionType C_ConversionType);

    /** Column definition for C_ConversionType_ID */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, org.compiere.model.I_C_ConversionType> COLUMN_C_ConversionType_ID = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, org.compiere.model.I_C_ConversionType>(I_M_DiscountSchemaLine.class, "C_ConversionType_ID", org.compiere.model.I_C_ConversionType.class);
    /** Column name C_ConversionType_ID */
    public static final String COLUMNNAME_C_ConversionType_ID = "C_ConversionType_ID";

	/**
	 * Set Klassifizierung.
	 * Classification for grouping
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setClassification (java.lang.String Classification);

	/**
	 * Get Klassifizierung.
	 * Classification for grouping
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getClassification();

    /** Column definition for Classification */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, Object> COLUMN_Classification = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, Object>(I_M_DiscountSchemaLine.class, "Classification", null);
    /** Column name Classification */
    public static final String COLUMNNAME_Classification = "Classification";

	/**
	 * Set Konvertierungsdatum.
	 * Date for selecting conversion rate
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setConversionDate (java.sql.Timestamp ConversionDate);

	/**
	 * Get Konvertierungsdatum.
	 * Date for selecting conversion rate
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getConversionDate();

    /** Column definition for ConversionDate */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, Object> COLUMN_ConversionDate = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, Object>(I_M_DiscountSchemaLine.class, "ConversionDate", null);
    /** Column name ConversionDate */
    public static final String COLUMNNAME_ConversionDate = "ConversionDate";

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
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, Object>(I_M_DiscountSchemaLine.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, org.compiere.model.I_AD_User>(I_M_DiscountSchemaLine.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Steuerkategorie.
	 * Steuerkategorie
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_TaxCategory_ID (int C_TaxCategory_ID);

	/**
	 * Get Steuerkategorie.
	 * Steuerkategorie
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_TaxCategory_ID();

	public org.compiere.model.I_C_TaxCategory getC_TaxCategory() throws RuntimeException;

	public void setC_TaxCategory(org.compiere.model.I_C_TaxCategory C_TaxCategory);

    /** Column definition for C_TaxCategory_ID */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, org.compiere.model.I_C_TaxCategory> COLUMN_C_TaxCategory_ID = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, org.compiere.model.I_C_TaxCategory>(I_M_DiscountSchemaLine.class, "C_TaxCategory_ID", org.compiere.model.I_C_TaxCategory.class);
    /** Column name C_TaxCategory_ID */
    public static final String COLUMNNAME_C_TaxCategory_ID = "C_TaxCategory_ID";

	/**
	 * Set Ziel-Steuerkategorie.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_TaxCategory_Target_ID (int C_TaxCategory_Target_ID);

	/**
	 * Get Ziel-Steuerkategorie.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_TaxCategory_Target_ID();

	public org.compiere.model.I_C_TaxCategory getC_TaxCategory_Target() throws RuntimeException;

	public void setC_TaxCategory_Target(org.compiere.model.I_C_TaxCategory C_TaxCategory_Target);

    /** Column definition for C_TaxCategory_Target_ID */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, org.compiere.model.I_C_TaxCategory> COLUMN_C_TaxCategory_Target_ID = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, org.compiere.model.I_C_TaxCategory>(I_M_DiscountSchemaLine.class, "C_TaxCategory_Target_ID", org.compiere.model.I_C_TaxCategory.class);
    /** Column name C_TaxCategory_Target_ID */
    public static final String COLUMNNAME_C_TaxCategory_Target_ID = "C_TaxCategory_Target_ID";

	/**
	 * Set Group1.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setGroup1 (java.lang.String Group1);

	/**
	 * Get Group1.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getGroup1();

    /** Column definition for Group1 */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, Object> COLUMN_Group1 = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, Object>(I_M_DiscountSchemaLine.class, "Group1", null);
    /** Column name Group1 */
    public static final String COLUMNNAME_Group1 = "Group1";

	/**
	 * Set Group2.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setGroup2 (java.lang.String Group2);

	/**
	 * Get Group2.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getGroup2();

    /** Column definition for Group2 */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, Object> COLUMN_Group2 = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, Object>(I_M_DiscountSchemaLine.class, "Group2", null);
    /** Column name Group2 */
    public static final String COLUMNNAME_Group2 = "Group2";

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
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, Object>(I_M_DiscountSchemaLine.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Aufschlag auf Mindestpreis.
	 * Amount added to the converted/copied price before multiplying
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setLimit_AddAmt (java.math.BigDecimal Limit_AddAmt);

	/**
	 * Get Aufschlag auf Mindestpreis.
	 * Amount added to the converted/copied price before multiplying
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getLimit_AddAmt();

    /** Column definition for Limit_AddAmt */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, Object> COLUMN_Limit_AddAmt = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, Object>(I_M_DiscountSchemaLine.class, "Limit_AddAmt", null);
    /** Column name Limit_AddAmt */
    public static final String COLUMNNAME_Limit_AddAmt = "Limit_AddAmt";

	/**
	 * Set Basis Mindestpreis.
	 * Base price for calculation of the new price
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setLimit_Base (java.lang.String Limit_Base);

	/**
	 * Get Basis Mindestpreis.
	 * Base price for calculation of the new price
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getLimit_Base();

    /** Column definition for Limit_Base */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, Object> COLUMN_Limit_Base = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, Object>(I_M_DiscountSchemaLine.class, "Limit_Base", null);
    /** Column name Limit_Base */
    public static final String COLUMNNAME_Limit_Base = "Limit_Base";

	/**
	 * Set Abschlag % auf Mindestpreis.
	 * Discount in percent to be subtracted from base, if negative it will be added to base price
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setLimit_Discount (java.math.BigDecimal Limit_Discount);

	/**
	 * Get Abschlag % auf Mindestpreis.
	 * Discount in percent to be subtracted from base, if negative it will be added to base price
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getLimit_Discount();

    /** Column definition for Limit_Discount */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, Object> COLUMN_Limit_Discount = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, Object>(I_M_DiscountSchemaLine.class, "Limit_Discount", null);
    /** Column name Limit_Discount */
    public static final String COLUMNNAME_Limit_Discount = "Limit_Discount";

	/**
	 * Set Fixed Limit Price.
	 * Fixed Limit Price (not calculated)
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLimit_Fixed (java.math.BigDecimal Limit_Fixed);

	/**
	 * Get Fixed Limit Price.
	 * Fixed Limit Price (not calculated)
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getLimit_Fixed();

    /** Column definition for Limit_Fixed */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, Object> COLUMN_Limit_Fixed = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, Object>(I_M_DiscountSchemaLine.class, "Limit_Fixed", null);
    /** Column name Limit_Fixed */
    public static final String COLUMNNAME_Limit_Fixed = "Limit_Fixed";

	/**
	 * Set Limit price max Margin.
	 * Maximum difference to original limit price;
 ignored if zero
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setLimit_MaxAmt (java.math.BigDecimal Limit_MaxAmt);

	/**
	 * Get Limit price max Margin.
	 * Maximum difference to original limit price;
 ignored if zero
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getLimit_MaxAmt();

    /** Column definition for Limit_MaxAmt */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, Object> COLUMN_Limit_MaxAmt = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, Object>(I_M_DiscountSchemaLine.class, "Limit_MaxAmt", null);
    /** Column name Limit_MaxAmt */
    public static final String COLUMNNAME_Limit_MaxAmt = "Limit_MaxAmt";

	/**
	 * Set Limit price min Margin.
	 * Minimum difference to original limit price;
 ignored if zero
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setLimit_MinAmt (java.math.BigDecimal Limit_MinAmt);

	/**
	 * Get Limit price min Margin.
	 * Minimum difference to original limit price;
 ignored if zero
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getLimit_MinAmt();

    /** Column definition for Limit_MinAmt */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, Object> COLUMN_Limit_MinAmt = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, Object>(I_M_DiscountSchemaLine.class, "Limit_MinAmt", null);
    /** Column name Limit_MinAmt */
    public static final String COLUMNNAME_Limit_MinAmt = "Limit_MinAmt";

	/**
	 * Set Rundung Mindestpreis.
	 * Rounding of the final result
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setLimit_Rounding (java.lang.String Limit_Rounding);

	/**
	 * Get Rundung Mindestpreis.
	 * Rounding of the final result
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getLimit_Rounding();

    /** Column definition for Limit_Rounding */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, Object> COLUMN_Limit_Rounding = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, Object>(I_M_DiscountSchemaLine.class, "Limit_Rounding", null);
    /** Column name Limit_Rounding */
    public static final String COLUMNNAME_Limit_Rounding = "Limit_Rounding";

	/**
	 * Set Aufschlag auf Listenpreis.
	 * List Price Surcharge Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setList_AddAmt (java.math.BigDecimal List_AddAmt);

	/**
	 * Get Aufschlag auf Listenpreis.
	 * List Price Surcharge Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getList_AddAmt();

    /** Column definition for List_AddAmt */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, Object> COLUMN_List_AddAmt = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, Object>(I_M_DiscountSchemaLine.class, "List_AddAmt", null);
    /** Column name List_AddAmt */
    public static final String COLUMNNAME_List_AddAmt = "List_AddAmt";

	/**
	 * Set Basis Listenpreis.
	 * Price used as the basis for price list calculations
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setList_Base (java.lang.String List_Base);

	/**
	 * Get Basis Listenpreis.
	 * Price used as the basis for price list calculations
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getList_Base();

    /** Column definition for List_Base */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, Object> COLUMN_List_Base = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, Object>(I_M_DiscountSchemaLine.class, "List_Base", null);
    /** Column name List_Base */
    public static final String COLUMNNAME_List_Base = "List_Base";

	/**
	 * Set Abschlag % auf Listenpreis.
	 * Discount from list price as a percentage
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setList_Discount (java.math.BigDecimal List_Discount);

	/**
	 * Get Abschlag % auf Listenpreis.
	 * Discount from list price as a percentage
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getList_Discount();

    /** Column definition for List_Discount */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, Object> COLUMN_List_Discount = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, Object>(I_M_DiscountSchemaLine.class, "List_Discount", null);
    /** Column name List_Discount */
    public static final String COLUMNNAME_List_Discount = "List_Discount";

	/**
	 * Set Fixed List Price.
	 * Fixes List Price (not calculated)
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setList_Fixed (java.math.BigDecimal List_Fixed);

	/**
	 * Get Fixed List Price.
	 * Fixes List Price (not calculated)
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getList_Fixed();

    /** Column definition for List_Fixed */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, Object> COLUMN_List_Fixed = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, Object>(I_M_DiscountSchemaLine.class, "List_Fixed", null);
    /** Column name List_Fixed */
    public static final String COLUMNNAME_List_Fixed = "List_Fixed";

	/**
	 * Set List price max Margin.
	 * Maximum margin for a product
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setList_MaxAmt (java.math.BigDecimal List_MaxAmt);

	/**
	 * Get List price max Margin.
	 * Maximum margin for a product
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getList_MaxAmt();

    /** Column definition for List_MaxAmt */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, Object> COLUMN_List_MaxAmt = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, Object>(I_M_DiscountSchemaLine.class, "List_MaxAmt", null);
    /** Column name List_MaxAmt */
    public static final String COLUMNNAME_List_MaxAmt = "List_MaxAmt";

	/**
	 * Set List price min Margin.
	 * Minimum margin for a product
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setList_MinAmt (java.math.BigDecimal List_MinAmt);

	/**
	 * Get List price min Margin.
	 * Minimum margin for a product
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getList_MinAmt();

    /** Column definition for List_MinAmt */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, Object> COLUMN_List_MinAmt = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, Object>(I_M_DiscountSchemaLine.class, "List_MinAmt", null);
    /** Column name List_MinAmt */
    public static final String COLUMNNAME_List_MinAmt = "List_MinAmt";

	/**
	 * Set Rundung Listenpreis.
	 * Rounding rule for final list price
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setList_Rounding (java.lang.String List_Rounding);

	/**
	 * Get Rundung Listenpreis.
	 * Rounding rule for final list price
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getList_Rounding();

    /** Column definition for List_Rounding */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, Object> COLUMN_List_Rounding = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, Object>(I_M_DiscountSchemaLine.class, "List_Rounding", null);
    /** Column name List_Rounding */
    public static final String COLUMNNAME_List_Rounding = "List_Rounding";

	/**
	 * Set Rabatt-Schema.
	 * Schema to calculate the trade discount percentage
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_DiscountSchema_ID (int M_DiscountSchema_ID);

	/**
	 * Get Rabatt-Schema.
	 * Schema to calculate the trade discount percentage
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_DiscountSchema_ID();

	public org.compiere.model.I_M_DiscountSchema getM_DiscountSchema() throws RuntimeException;

	public void setM_DiscountSchema(org.compiere.model.I_M_DiscountSchema M_DiscountSchema);

    /** Column definition for M_DiscountSchema_ID */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, org.compiere.model.I_M_DiscountSchema> COLUMN_M_DiscountSchema_ID = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, org.compiere.model.I_M_DiscountSchema>(I_M_DiscountSchemaLine.class, "M_DiscountSchema_ID", org.compiere.model.I_M_DiscountSchema.class);
    /** Column name M_DiscountSchema_ID */
    public static final String COLUMNNAME_M_DiscountSchema_ID = "M_DiscountSchema_ID";

	/**
	 * Set Discount Pricelist.
	 * Line of the pricelist trade discount schema
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_DiscountSchemaLine_ID (int M_DiscountSchemaLine_ID);

	/**
	 * Get Discount Pricelist.
	 * Line of the pricelist trade discount schema
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_DiscountSchemaLine_ID();

    /** Column definition for M_DiscountSchemaLine_ID */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, Object> COLUMN_M_DiscountSchemaLine_ID = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, Object>(I_M_DiscountSchemaLine.class, "M_DiscountSchemaLine_ID", null);
    /** Column name M_DiscountSchemaLine_ID */
    public static final String COLUMNNAME_M_DiscountSchemaLine_ID = "M_DiscountSchemaLine_ID";

	/**
	 * Set Produkt-Kategorie.
	 * Category of a Product
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Product_Category_ID (int M_Product_Category_ID);

	/**
	 * Get Produkt-Kategorie.
	 * Category of a Product
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Product_Category_ID();

	public org.compiere.model.I_M_Product_Category getM_Product_Category() throws RuntimeException;

	public void setM_Product_Category(org.compiere.model.I_M_Product_Category M_Product_Category);

    /** Column definition for M_Product_Category_ID */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, org.compiere.model.I_M_Product_Category> COLUMN_M_Product_Category_ID = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, org.compiere.model.I_M_Product_Category>(I_M_DiscountSchemaLine.class, "M_Product_Category_ID", org.compiere.model.I_M_Product_Category.class);
    /** Column name M_Product_Category_ID */
    public static final String COLUMNNAME_M_Product_Category_ID = "M_Product_Category_ID";

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

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException;

	public void setM_Product(org.compiere.model.I_M_Product M_Product);

    /** Column definition for M_Product_ID */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, org.compiere.model.I_M_Product>(I_M_DiscountSchemaLine.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Reihenfolge.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setSeqNo (int SeqNo);

	/**
	 * Get Reihenfolge.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getSeqNo();

    /** Column definition for SeqNo */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, Object> COLUMN_SeqNo = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, Object>(I_M_DiscountSchemaLine.class, "SeqNo", null);
    /** Column name SeqNo */
    public static final String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Set Aufschlag auf Standardpreis.
	 * Amount added to a price as a surcharge
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setStd_AddAmt (java.math.BigDecimal Std_AddAmt);

	/**
	 * Get Aufschlag auf Standardpreis.
	 * Amount added to a price as a surcharge
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getStd_AddAmt();

    /** Column definition for Std_AddAmt */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, Object> COLUMN_Std_AddAmt = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, Object>(I_M_DiscountSchemaLine.class, "Std_AddAmt", null);
    /** Column name Std_AddAmt */
    public static final String COLUMNNAME_Std_AddAmt = "Std_AddAmt";

	/**
	 * Set Basis Standardpreis.
	 * Base price for calculating new standard price
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setStd_Base (java.lang.String Std_Base);

	/**
	 * Get Basis Standardpreis.
	 * Base price for calculating new standard price
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getStd_Base();

    /** Column definition for Std_Base */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, Object> COLUMN_Std_Base = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, Object>(I_M_DiscountSchemaLine.class, "Std_Base", null);
    /** Column name Std_Base */
    public static final String COLUMNNAME_Std_Base = "Std_Base";

	/**
	 * Set Abschlag % auf Standardpreis.
	 * Discount percentage to subtract from base price
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setStd_Discount (java.math.BigDecimal Std_Discount);

	/**
	 * Get Abschlag % auf Standardpreis.
	 * Discount percentage to subtract from base price
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getStd_Discount();

    /** Column definition for Std_Discount */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, Object> COLUMN_Std_Discount = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, Object>(I_M_DiscountSchemaLine.class, "Std_Discount", null);
    /** Column name Std_Discount */
    public static final String COLUMNNAME_Std_Discount = "Std_Discount";

	/**
	 * Set Fixed Standard Price.
	 * Fixed Standard Price (not calculated)
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setStd_Fixed (java.math.BigDecimal Std_Fixed);

	/**
	 * Get Fixed Standard Price.
	 * Fixed Standard Price (not calculated)
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getStd_Fixed();

    /** Column definition for Std_Fixed */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, Object> COLUMN_Std_Fixed = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, Object>(I_M_DiscountSchemaLine.class, "Std_Fixed", null);
    /** Column name Std_Fixed */
    public static final String COLUMNNAME_Std_Fixed = "Std_Fixed";

	/**
	 * Set Standard max Margin.
	 * Maximum margin allowed for a product
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setStd_MaxAmt (java.math.BigDecimal Std_MaxAmt);

	/**
	 * Get Standard max Margin.
	 * Maximum margin allowed for a product
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getStd_MaxAmt();

    /** Column definition for Std_MaxAmt */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, Object> COLUMN_Std_MaxAmt = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, Object>(I_M_DiscountSchemaLine.class, "Std_MaxAmt", null);
    /** Column name Std_MaxAmt */
    public static final String COLUMNNAME_Std_MaxAmt = "Std_MaxAmt";

	/**
	 * Set Standard price min Margin.
	 * Minimum margin allowed for a product
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setStd_MinAmt (java.math.BigDecimal Std_MinAmt);

	/**
	 * Get Standard price min Margin.
	 * Minimum margin allowed for a product
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getStd_MinAmt();

    /** Column definition for Std_MinAmt */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, Object> COLUMN_Std_MinAmt = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, Object>(I_M_DiscountSchemaLine.class, "Std_MinAmt", null);
    /** Column name Std_MinAmt */
    public static final String COLUMNNAME_Std_MinAmt = "Std_MinAmt";

	/**
	 * Set Rundung Standardpreis.
	 * Rounding rule for calculated price
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setStd_Rounding (java.lang.String Std_Rounding);

	/**
	 * Get Rundung Standardpreis.
	 * Rounding rule for calculated price
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getStd_Rounding();

    /** Column definition for Std_Rounding */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, Object> COLUMN_Std_Rounding = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, Object>(I_M_DiscountSchemaLine.class, "Std_Rounding", null);
    /** Column name Std_Rounding */
    public static final String COLUMNNAME_Std_Rounding = "Std_Rounding";

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
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, Object>(I_M_DiscountSchemaLine.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaLine, org.compiere.model.I_AD_User>(I_M_DiscountSchemaLine.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
 
}
