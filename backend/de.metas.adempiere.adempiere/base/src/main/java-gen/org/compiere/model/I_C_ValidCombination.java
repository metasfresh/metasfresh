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

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for C_ValidCombination
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_ValidCombination 
{

    /** TableName=C_ValidCombination */
    public static final String Table_Name = "C_ValidCombination";

    /** AD_Table_ID=176 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 2 - Client 
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(2);

    /** Load Meta Data */

	/**
	 * Set Konto.
	 * Account used
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAccount_ID (int Account_ID);

	/**
	 * Get Konto.
	 * Account used
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAccount_ID();

	public org.compiere.model.I_C_ElementValue getAccount() throws RuntimeException;

	public void setAccount(org.compiere.model.I_C_ElementValue Account);

    /** Column definition for Account_ID */
    public static final org.adempiere.model.ModelColumn<I_C_ValidCombination, org.compiere.model.I_C_ElementValue> COLUMN_Account_ID = new org.adempiere.model.ModelColumn<I_C_ValidCombination, org.compiere.model.I_C_ElementValue>(I_C_ValidCombination.class, "Account_ID", org.compiere.model.I_C_ElementValue.class);
    /** Column name Account_ID */
    public static final String COLUMNNAME_Account_ID = "Account_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_ValidCombination, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_ValidCombination, org.compiere.model.I_AD_Client>(I_C_ValidCombination.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

	public org.compiere.model.I_AD_Org getAD_Org() throws RuntimeException;

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_C_ValidCombination, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_ValidCombination, org.compiere.model.I_AD_Org>(I_C_ValidCombination.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
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

	public org.compiere.model.I_AD_Org getAD_OrgTrx() throws RuntimeException;

	public void setAD_OrgTrx(org.compiere.model.I_AD_Org AD_OrgTrx);

    /** Column definition for AD_OrgTrx_ID */
    public static final org.adempiere.model.ModelColumn<I_C_ValidCombination, org.compiere.model.I_AD_Org> COLUMN_AD_OrgTrx_ID = new org.adempiere.model.ModelColumn<I_C_ValidCombination, org.compiere.model.I_AD_Org>(I_C_ValidCombination.class, "AD_OrgTrx_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_OrgTrx_ID */
    public static final String COLUMNNAME_AD_OrgTrx_ID = "AD_OrgTrx_ID";

	/**
	 * Set Alias.
	 * Defines an alternate method of indicating an account combination.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAlias (java.lang.String Alias);

	/**
	 * Get Alias.
	 * Defines an alternate method of indicating an account combination.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAlias();

    /** Column definition for Alias */
    public static final org.adempiere.model.ModelColumn<I_C_ValidCombination, Object> COLUMN_Alias = new org.adempiere.model.ModelColumn<I_C_ValidCombination, Object>(I_C_ValidCombination.class, "Alias", null);
    /** Column name Alias */
    public static final String COLUMNNAME_Alias = "Alias";

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

//	public org.compiere.model.I_C_AcctSchema getC_AcctSchema() throws RuntimeException;
//
//	public void setC_AcctSchema(org.compiere.model.I_C_AcctSchema C_AcctSchema);

    /** Column definition for C_AcctSchema_ID */
    public static final org.adempiere.model.ModelColumn<I_C_ValidCombination, org.compiere.model.I_C_AcctSchema> COLUMN_C_AcctSchema_ID = new org.adempiere.model.ModelColumn<I_C_ValidCombination, org.compiere.model.I_C_AcctSchema>(I_C_ValidCombination.class, "C_AcctSchema_ID", org.compiere.model.I_C_AcctSchema.class);
    /** Column name C_AcctSchema_ID */
    public static final String COLUMNNAME_C_AcctSchema_ID = "C_AcctSchema_ID";

	/**
	 * Set Kostenstelle.
	 * Kostenstelle
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Activity_ID (int C_Activity_ID);

	/**
	 * Get Kostenstelle.
	 * Kostenstelle
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Activity_ID();

	public org.compiere.model.I_C_Activity getC_Activity() throws RuntimeException;

	public void setC_Activity(org.compiere.model.I_C_Activity C_Activity);

    /** Column definition for C_Activity_ID */
    public static final org.adempiere.model.ModelColumn<I_C_ValidCombination, org.compiere.model.I_C_Activity> COLUMN_C_Activity_ID = new org.adempiere.model.ModelColumn<I_C_ValidCombination, org.compiere.model.I_C_Activity>(I_C_ValidCombination.class, "C_Activity_ID", org.compiere.model.I_C_Activity.class);
    /** Column name C_Activity_ID */
    public static final String COLUMNNAME_C_Activity_ID = "C_Activity_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_ValidCombination, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_C_ValidCombination, org.compiere.model.I_C_BPartner>(I_C_ValidCombination.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Werbemassnahme.
	 * Marketing Campaign
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Campaign_ID (int C_Campaign_ID);

	/**
	 * Get Werbemassnahme.
	 * Marketing Campaign
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Campaign_ID();

	public org.compiere.model.I_C_Campaign getC_Campaign() throws RuntimeException;

	public void setC_Campaign(org.compiere.model.I_C_Campaign C_Campaign);

    /** Column definition for C_Campaign_ID */
    public static final org.adempiere.model.ModelColumn<I_C_ValidCombination, org.compiere.model.I_C_Campaign> COLUMN_C_Campaign_ID = new org.adempiere.model.ModelColumn<I_C_ValidCombination, org.compiere.model.I_C_Campaign>(I_C_ValidCombination.class, "C_Campaign_ID", org.compiere.model.I_C_Campaign.class);
    /** Column name C_Campaign_ID */
    public static final String COLUMNNAME_C_Campaign_ID = "C_Campaign_ID";

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

	public org.compiere.model.I_C_Location getC_LocFrom() throws RuntimeException;

	public void setC_LocFrom(org.compiere.model.I_C_Location C_LocFrom);

    /** Column definition for C_LocFrom_ID */
    public static final org.adempiere.model.ModelColumn<I_C_ValidCombination, org.compiere.model.I_C_Location> COLUMN_C_LocFrom_ID = new org.adempiere.model.ModelColumn<I_C_ValidCombination, org.compiere.model.I_C_Location>(I_C_ValidCombination.class, "C_LocFrom_ID", org.compiere.model.I_C_Location.class);
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

	public org.compiere.model.I_C_Location getC_LocTo() throws RuntimeException;

	public void setC_LocTo(org.compiere.model.I_C_Location C_LocTo);

    /** Column definition for C_LocTo_ID */
    public static final org.adempiere.model.ModelColumn<I_C_ValidCombination, org.compiere.model.I_C_Location> COLUMN_C_LocTo_ID = new org.adempiere.model.ModelColumn<I_C_ValidCombination, org.compiere.model.I_C_Location>(I_C_ValidCombination.class, "C_LocTo_ID", org.compiere.model.I_C_Location.class);
    /** Column name C_LocTo_ID */
    public static final String COLUMNNAME_C_LocTo_ID = "C_LocTo_ID";

	/**
	 * Set Projekt.
	 * Financial Project
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Project_ID (int C_Project_ID);

	/**
	 * Get Projekt.
	 * Financial Project
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Project_ID();

	public org.compiere.model.I_C_Project getC_Project() throws RuntimeException;

	public void setC_Project(org.compiere.model.I_C_Project C_Project);

    /** Column definition for C_Project_ID */
    public static final org.adempiere.model.ModelColumn<I_C_ValidCombination, org.compiere.model.I_C_Project> COLUMN_C_Project_ID = new org.adempiere.model.ModelColumn<I_C_ValidCombination, org.compiere.model.I_C_Project>(I_C_ValidCombination.class, "C_Project_ID", org.compiere.model.I_C_Project.class);
    /** Column name C_Project_ID */
    public static final String COLUMNNAME_C_Project_ID = "C_Project_ID";

	/**
	 * Set Vertriebsgebiet.
	 * Sales coverage region
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_SalesRegion_ID (int C_SalesRegion_ID);

	/**
	 * Get Vertriebsgebiet.
	 * Sales coverage region
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_SalesRegion_ID();

	public org.compiere.model.I_C_SalesRegion getC_SalesRegion() throws RuntimeException;

	public void setC_SalesRegion(org.compiere.model.I_C_SalesRegion C_SalesRegion);

    /** Column definition for C_SalesRegion_ID */
    public static final org.adempiere.model.ModelColumn<I_C_ValidCombination, org.compiere.model.I_C_SalesRegion> COLUMN_C_SalesRegion_ID = new org.adempiere.model.ModelColumn<I_C_ValidCombination, org.compiere.model.I_C_SalesRegion>(I_C_ValidCombination.class, "C_SalesRegion_ID", org.compiere.model.I_C_SalesRegion.class);
    /** Column name C_SalesRegion_ID */
    public static final String COLUMNNAME_C_SalesRegion_ID = "C_SalesRegion_ID";

	/**
	 * Set Unter-Konto.
	 * Sub account for Element Value
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_SubAcct_ID (int C_SubAcct_ID);

	/**
	 * Get Unter-Konto.
	 * Sub account for Element Value
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_SubAcct_ID();

	public org.compiere.model.I_C_SubAcct getC_SubAcct() throws RuntimeException;

	public void setC_SubAcct(org.compiere.model.I_C_SubAcct C_SubAcct);

    /** Column definition for C_SubAcct_ID */
    public static final org.adempiere.model.ModelColumn<I_C_ValidCombination, org.compiere.model.I_C_SubAcct> COLUMN_C_SubAcct_ID = new org.adempiere.model.ModelColumn<I_C_ValidCombination, org.compiere.model.I_C_SubAcct>(I_C_ValidCombination.class, "C_SubAcct_ID", org.compiere.model.I_C_SubAcct.class);
    /** Column name C_SubAcct_ID */
    public static final String COLUMNNAME_C_SubAcct_ID = "C_SubAcct_ID";

	/**
	 * Set Kombination.
	 * Valid Account Combination
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_ValidCombination_ID (int C_ValidCombination_ID);

	/**
	 * Get Kombination.
	 * Valid Account Combination
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_ValidCombination_ID();

    /** Column definition for C_ValidCombination_ID */
    public static final org.adempiere.model.ModelColumn<I_C_ValidCombination, Object> COLUMN_C_ValidCombination_ID = new org.adempiere.model.ModelColumn<I_C_ValidCombination, Object>(I_C_ValidCombination.class, "C_ValidCombination_ID", null);
    /** Column name C_ValidCombination_ID */
    public static final String COLUMNNAME_C_ValidCombination_ID = "C_ValidCombination_ID";

	/**
	 * Set Kombination.
	 * Unique combination of account elements
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCombination (java.lang.String Combination);

	/**
	 * Get Kombination.
	 * Unique combination of account elements
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCombination();

    /** Column definition for Combination */
    public static final org.adempiere.model.ModelColumn<I_C_ValidCombination, Object> COLUMN_Combination = new org.adempiere.model.ModelColumn<I_C_ValidCombination, Object>(I_C_ValidCombination.class, "Combination", null);
    /** Column name Combination */
    public static final String COLUMNNAME_Combination = "Combination";

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
    public static final org.adempiere.model.ModelColumn<I_C_ValidCombination, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_ValidCombination, Object>(I_C_ValidCombination.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_ValidCombination, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_ValidCombination, org.compiere.model.I_AD_User>(I_C_ValidCombination.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_ValidCombination, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_C_ValidCombination, Object>(I_C_ValidCombination.class, "Description", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_ValidCombination, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_ValidCombination, Object>(I_C_ValidCombination.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Fully Qualified.
	 * This account is fully qualified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsFullyQualified (boolean IsFullyQualified);

	/**
	 * Get Fully Qualified.
	 * This account is fully qualified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isFullyQualified();

    /** Column definition for IsFullyQualified */
    public static final org.adempiere.model.ModelColumn<I_C_ValidCombination, Object> COLUMN_IsFullyQualified = new org.adempiere.model.ModelColumn<I_C_ValidCombination, Object>(I_C_ValidCombination.class, "IsFullyQualified", null);
    /** Column name IsFullyQualified */
    public static final String COLUMNNAME_IsFullyQualified = "IsFullyQualified";

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
    public static final org.adempiere.model.ModelColumn<I_C_ValidCombination, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_C_ValidCombination, org.compiere.model.I_M_Product>(I_C_ValidCombination.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_ValidCombination, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_ValidCombination, Object>(I_C_ValidCombination.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_ValidCombination, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_ValidCombination, org.compiere.model.I_AD_User>(I_C_ValidCombination.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
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

	public org.compiere.model.I_C_ElementValue getUser1() throws RuntimeException;

	public void setUser1(org.compiere.model.I_C_ElementValue User1);

    /** Column definition for User1_ID */
    public static final org.adempiere.model.ModelColumn<I_C_ValidCombination, org.compiere.model.I_C_ElementValue> COLUMN_User1_ID = new org.adempiere.model.ModelColumn<I_C_ValidCombination, org.compiere.model.I_C_ElementValue>(I_C_ValidCombination.class, "User1_ID", org.compiere.model.I_C_ElementValue.class);
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

	public org.compiere.model.I_C_ElementValue getUser2() throws RuntimeException;

	public void setUser2(org.compiere.model.I_C_ElementValue User2);

    /** Column definition for User2_ID */
    public static final org.adempiere.model.ModelColumn<I_C_ValidCombination, org.compiere.model.I_C_ElementValue> COLUMN_User2_ID = new org.adempiere.model.ModelColumn<I_C_ValidCombination, org.compiere.model.I_C_ElementValue>(I_C_ValidCombination.class, "User2_ID", org.compiere.model.I_C_ElementValue.class);
    /** Column name User2_ID */
    public static final String COLUMNNAME_User2_ID = "User2_ID";

	/**
	 * Set Nutzer-Element 1.
	 * User defined accounting Element
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setUserElement1_ID (int UserElement1_ID);

	/**
	 * Get Nutzer-Element 1.
	 * User defined accounting Element
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getUserElement1_ID();

    /** Column definition for UserElement1_ID */
    public static final org.adempiere.model.ModelColumn<I_C_ValidCombination, Object> COLUMN_UserElement1_ID = new org.adempiere.model.ModelColumn<I_C_ValidCombination, Object>(I_C_ValidCombination.class, "UserElement1_ID", null);
    /** Column name UserElement1_ID */
    public static final String COLUMNNAME_UserElement1_ID = "UserElement1_ID";

	/**
	 * Set Nutzer-Element 2.
	 * User defined accounting Element
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setUserElement2_ID (int UserElement2_ID);

	/**
	 * Get Nutzer-Element 2.
	 * User defined accounting Element
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getUserElement2_ID();

    /** Column definition for UserElement2_ID */
    public static final org.adempiere.model.ModelColumn<I_C_ValidCombination, Object> COLUMN_UserElement2_ID = new org.adempiere.model.ModelColumn<I_C_ValidCombination, Object>(I_C_ValidCombination.class, "UserElement2_ID", null);
    /** Column name UserElement2_ID */
    public static final String COLUMNNAME_UserElement2_ID = "UserElement2_ID";


	/**
	 * Set UserElementString1.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElementString1 (@Nullable java.lang.String UserElementString1);

	/**
	 * Get UserElementString1.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUserElementString1();

	ModelColumn<I_C_ValidCombination, Object> COLUMN_UserElementString1 = new ModelColumn<>(I_C_ValidCombination.class, "UserElementString1", null);
	String COLUMNNAME_UserElementString1 = "UserElementString1";

	/**
	 * Set UserElementString2.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElementString2 (@Nullable java.lang.String UserElementString2);

	/**
	 * Get UserElementString2.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUserElementString2();

	ModelColumn<I_C_ValidCombination, Object> COLUMN_UserElementString2 = new ModelColumn<>(I_C_ValidCombination.class, "UserElementString2", null);
	String COLUMNNAME_UserElementString2 = "UserElementString2";

	/**
	 * Set UserElementString3.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElementString3 (@Nullable java.lang.String UserElementString3);

	/**
	 * Get UserElementString3.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUserElementString3();

	ModelColumn<I_C_ValidCombination, Object> COLUMN_UserElementString3 = new ModelColumn<>(I_C_ValidCombination.class, "UserElementString3", null);
	String COLUMNNAME_UserElementString3 = "UserElementString3";

	/**
	 * Set UserElementString4.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElementString4 (@Nullable java.lang.String UserElementString4);

	/**
	 * Get UserElementString4.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUserElementString4();

	ModelColumn<I_C_ValidCombination, Object> COLUMN_UserElementString4 = new ModelColumn<>(I_C_ValidCombination.class, "UserElementString4", null);
	String COLUMNNAME_UserElementString4 = "UserElementString4";

	/**
	 * Set UserElementString5.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElementString5 (@Nullable java.lang.String UserElementString5);

	/**
	 * Get UserElementString5.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUserElementString5();

	ModelColumn<I_C_ValidCombination, Object> COLUMN_UserElementString5 = new ModelColumn<>(I_C_ValidCombination.class, "UserElementString5", null);
	String COLUMNNAME_UserElementString5 = "UserElementString5";

	/**
	 * Set UserElementString6.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElementString6 (@Nullable java.lang.String UserElementString6);

	/**
	 * Get UserElementString6.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUserElementString6();

	ModelColumn<I_C_ValidCombination, Object> COLUMN_UserElementString6 = new ModelColumn<>(I_C_ValidCombination.class, "UserElementString6", null);
	String COLUMNNAME_UserElementString6 = "UserElementString6";

	/**
	 * Set UserElementString7.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElementString7 (@Nullable java.lang.String UserElementString7);

	/**
	 * Get UserElementString7.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUserElementString7();

	ModelColumn<I_C_ValidCombination, Object> COLUMN_UserElementString7 = new ModelColumn<>(I_C_ValidCombination.class, "UserElementString7", null);
	String COLUMNNAME_UserElementString7 = "UserElementString7";

}
