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
package de.metas.materialtracking.ch.lagerkonf.model;


/** Generated Interface for M_QualityInsp_LagerKonf_Version
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_QualityInsp_LagerKonf_Version 
{

    /** TableName=M_QualityInsp_LagerKonf_Version */
    public static final String Table_Name = "M_QualityInsp_LagerKonf_Version";

    /** AD_Table_ID=540616 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org 
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(1);

    /** Load Meta Data */

	/**
	 * Get Mandant.
	 * Mandant für diese Installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_Version, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_Version, org.compiere.model.I_AD_Client>(I_M_QualityInsp_LagerKonf_Version.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_Version, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_Version, org.compiere.model.I_AD_Org>(I_M_QualityInsp_LagerKonf_Version.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Währung.
	 * Die Währung für diesen Eintrag
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Währung.
	 * Die Währung für diesen Eintrag
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Currency_ID();

	public org.compiere.model.I_C_Currency getC_Currency();

	public void setC_Currency(org.compiere.model.I_C_Currency C_Currency);

    /** Column definition for C_Currency_ID */
    public static final org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_Version, org.compiere.model.I_C_Currency> COLUMN_C_Currency_ID = new org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_Version, org.compiere.model.I_C_Currency>(I_M_QualityInsp_LagerKonf_Version.class, "C_Currency_ID", org.compiere.model.I_C_Currency.class);
    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Get Erstellt.
	 * Datum, an dem dieser Eintrag erstellt wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_Version, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_Version, Object>(I_M_QualityInsp_LagerKonf_Version.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * Nutzer, der diesen Eintrag erstellt hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_Version, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_Version, org.compiere.model.I_AD_User>(I_M_QualityInsp_LagerKonf_Version.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Einheit für Entsorgungskosten.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_UOM_Scrap_ID (int C_UOM_Scrap_ID);

	/**
	 * Get Einheit für Entsorgungskosten.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_UOM_Scrap_ID();

	public org.compiere.model.I_C_UOM getC_UOM_Scrap();

	public void setC_UOM_Scrap(org.compiere.model.I_C_UOM C_UOM_Scrap);

    /** Column definition for C_UOM_Scrap_ID */
    public static final org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_Version, org.compiere.model.I_C_UOM> COLUMN_C_UOM_Scrap_ID = new org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_Version, org.compiere.model.I_C_UOM>(I_M_QualityInsp_LagerKonf_Version.class, "C_UOM_Scrap_ID", org.compiere.model.I_C_UOM.class);
    /** Column name C_UOM_Scrap_ID */
    public static final String COLUMNNAME_C_UOM_Scrap_ID = "C_UOM_Scrap_ID";

	/**
	 * Set Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_Version, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_Version, Object>(I_M_QualityInsp_LagerKonf_Version.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Produkt für Sortierkosten.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Product_ProcessingFee_ID (int M_Product_ProcessingFee_ID);

	/**
	 * Get Produkt für Sortierkosten.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Product_ProcessingFee_ID();

	public org.compiere.model.I_M_Product getM_Product_ProcessingFee();

	public void setM_Product_ProcessingFee(org.compiere.model.I_M_Product M_Product_ProcessingFee);

    /** Column definition for M_Product_ProcessingFee_ID */
    public static final org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_Version, org.compiere.model.I_M_Product> COLUMN_M_Product_ProcessingFee_ID = new org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_Version, org.compiere.model.I_M_Product>(I_M_QualityInsp_LagerKonf_Version.class, "M_Product_ProcessingFee_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ProcessingFee_ID */
    public static final String COLUMNNAME_M_Product_ProcessingFee_ID = "M_Product_ProcessingFee_ID";

	/**
	 * Set Produkt für Auslagerung.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Product_RegularPPOrder_ID (int M_Product_RegularPPOrder_ID);

	/**
	 * Get Produkt für Auslagerung.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Product_RegularPPOrder_ID();

	public org.compiere.model.I_M_Product getM_Product_RegularPPOrder();

	public void setM_Product_RegularPPOrder(org.compiere.model.I_M_Product M_Product_RegularPPOrder);

    /** Column definition for M_Product_RegularPPOrder_ID */
    public static final org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_Version, org.compiere.model.I_M_Product> COLUMN_M_Product_RegularPPOrder_ID = new org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_Version, org.compiere.model.I_M_Product>(I_M_QualityInsp_LagerKonf_Version.class, "M_Product_RegularPPOrder_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_RegularPPOrder_ID */
    public static final String COLUMNNAME_M_Product_RegularPPOrder_ID = "M_Product_RegularPPOrder_ID";

	/**
	 * Set Produkt für Entsorgungskosten.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Product_Scrap_ID (int M_Product_Scrap_ID);

	/**
	 * Get Produkt für Entsorgungskosten.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Product_Scrap_ID();

	public org.compiere.model.I_M_Product getM_Product_Scrap();

	public void setM_Product_Scrap(org.compiere.model.I_M_Product M_Product_Scrap);

    /** Column definition for M_Product_Scrap_ID */
    public static final org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_Version, org.compiere.model.I_M_Product> COLUMN_M_Product_Scrap_ID = new org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_Version, org.compiere.model.I_M_Product>(I_M_QualityInsp_LagerKonf_Version.class, "M_Product_Scrap_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_Scrap_ID */
    public static final String COLUMNNAME_M_Product_Scrap_ID = "M_Product_Scrap_ID";

	/**
	 * Set Produkt für Einbehalt.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Product_Witholding_ID (int M_Product_Witholding_ID);

	/**
	 * Get Produkt für Einbehalt.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Product_Witholding_ID();

	public org.compiere.model.I_M_Product getM_Product_Witholding();

	public void setM_Product_Witholding(org.compiere.model.I_M_Product M_Product_Witholding);

    /** Column definition for M_Product_Witholding_ID */
    public static final org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_Version, org.compiere.model.I_M_Product> COLUMN_M_Product_Witholding_ID = new org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_Version, org.compiere.model.I_M_Product>(I_M_QualityInsp_LagerKonf_Version.class, "M_Product_Witholding_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_Witholding_ID */
    public static final String COLUMNNAME_M_Product_Witholding_ID = "M_Product_Witholding_ID";

	/**
	 * Set Lagerkonferenz.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_QualityInsp_LagerKonf_ID (int M_QualityInsp_LagerKonf_ID);

	/**
	 * Get Lagerkonferenz.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_QualityInsp_LagerKonf_ID();

	public I_M_QualityInsp_LagerKonf getM_QualityInsp_LagerKonf();

	public void setM_QualityInsp_LagerKonf(I_M_QualityInsp_LagerKonf M_QualityInsp_LagerKonf);

    /** Column definition for M_QualityInsp_LagerKonf_ID */
    public static final org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_Version, I_M_QualityInsp_LagerKonf> COLUMN_M_QualityInsp_LagerKonf_ID = new org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_Version, I_M_QualityInsp_LagerKonf>(I_M_QualityInsp_LagerKonf_Version.class, "M_QualityInsp_LagerKonf_ID", I_M_QualityInsp_LagerKonf.class);
    /** Column name M_QualityInsp_LagerKonf_ID */
    public static final String COLUMNNAME_M_QualityInsp_LagerKonf_ID = "M_QualityInsp_LagerKonf_ID";

	/**
	 * Set Lagerkonferenz-Version.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_QualityInsp_LagerKonf_Version_ID (int M_QualityInsp_LagerKonf_Version_ID);

	/**
	 * Get Lagerkonferenz-Version.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_QualityInsp_LagerKonf_Version_ID();

    /** Column definition for M_QualityInsp_LagerKonf_Version_ID */
    public static final org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_Version, Object> COLUMN_M_QualityInsp_LagerKonf_Version_ID = new org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_Version, Object>(I_M_QualityInsp_LagerKonf_Version.class, "M_QualityInsp_LagerKonf_Version_ID", null);
    /** Column name M_QualityInsp_LagerKonf_Version_ID */
    public static final String COLUMNNAME_M_QualityInsp_LagerKonf_Version_ID = "M_QualityInsp_LagerKonf_Version_ID";

	/**
	 * Set Anzahl Waschproben.
	 * Aus der hier festgelegte Anzahl an Waschproben ergibt sich der Akonto-Prozentsatz pro einzelner Waschprobe
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setNumberOfQualityInspections (int NumberOfQualityInspections);

	/**
	 * Get Anzahl Waschproben.
	 * Aus der hier festgelegte Anzahl an Waschproben ergibt sich der Akonto-Prozentsatz pro einzelner Waschprobe
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getNumberOfQualityInspections();

    /** Column definition for NumberOfQualityInspections */
    public static final org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_Version, Object> COLUMN_NumberOfQualityInspections = new org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_Version, Object>(I_M_QualityInsp_LagerKonf_Version.class, "NumberOfQualityInspections", null);
    /** Column name NumberOfQualityInspections */
    public static final String COLUMNNAME_NumberOfQualityInspections = "NumberOfQualityInspections";

	/**
	 * Set Schwelle % für Entsorgungskosten.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPercentage_Scrap_Treshhold (java.math.BigDecimal Percentage_Scrap_Treshhold);

	/**
	 * Get Schwelle % für Entsorgungskosten.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPercentage_Scrap_Treshhold();

    /** Column definition for Percentage_Scrap_Treshhold */
    public static final org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_Version, Object> COLUMN_Percentage_Scrap_Treshhold = new org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_Version, Object>(I_M_QualityInsp_LagerKonf_Version.class, "Percentage_Scrap_Treshhold", null);
    /** Column name Percentage_Scrap_Treshhold */
    public static final String COLUMNNAME_Percentage_Scrap_Treshhold = "Percentage_Scrap_Treshhold";

	/**
	 * Set Entsorgungskosten pro Einheit.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setScrap_Fee_Amt_Per_UOM (java.math.BigDecimal Scrap_Fee_Amt_Per_UOM);

	/**
	 * Get Entsorgungskosten pro Einheit.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getScrap_Fee_Amt_Per_UOM();

    /** Column definition for Scrap_Fee_Amt_Per_UOM */
    public static final org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_Version, Object> COLUMN_Scrap_Fee_Amt_Per_UOM = new org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_Version, Object>(I_M_QualityInsp_LagerKonf_Version.class, "Scrap_Fee_Amt_Per_UOM", null);
    /** Column name Scrap_Fee_Amt_Per_UOM */
    public static final String COLUMNNAME_Scrap_Fee_Amt_Per_UOM = "Scrap_Fee_Amt_Per_UOM";

	/**
	 * Get Aktualisiert.
	 * Datum, an dem dieser Eintrag aktualisiert wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_Version, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_Version, Object>(I_M_QualityInsp_LagerKonf_Version.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * Nutzer, der diesen Eintrag aktualisiert hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_Version, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_Version, org.compiere.model.I_AD_User>(I_M_QualityInsp_LagerKonf_Version.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Gültig ab.
	 * Gültig ab inklusiv (erster Tag)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setValidFrom (java.sql.Timestamp ValidFrom);

	/**
	 * Get Gültig ab.
	 * Gültig ab inklusiv (erster Tag)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getValidFrom();

    /** Column definition for ValidFrom */
    public static final org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_Version, Object> COLUMN_ValidFrom = new org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_Version, Object>(I_M_QualityInsp_LagerKonf_Version.class, "ValidFrom", null);
    /** Column name ValidFrom */
    public static final String COLUMNNAME_ValidFrom = "ValidFrom";

	/**
	 * Set Gültig bis.
	 * Gültig bis inklusiv (letzter Tag)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setValidTo (java.sql.Timestamp ValidTo);

	/**
	 * Get Gültig bis.
	 * Gültig bis inklusiv (letzter Tag)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getValidTo();

    /** Column definition for ValidTo */
    public static final org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_Version, Object> COLUMN_ValidTo = new org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_Version, Object>(I_M_QualityInsp_LagerKonf_Version.class, "ValidTo", null);
    /** Column name ValidTo */
    public static final String COLUMNNAME_ValidTo = "ValidTo";
}
