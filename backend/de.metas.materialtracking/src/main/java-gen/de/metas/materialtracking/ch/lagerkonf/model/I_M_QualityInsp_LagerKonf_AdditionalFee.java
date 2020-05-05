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


/** Generated Interface for M_QualityInsp_LagerKonf_AdditionalFee
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_QualityInsp_LagerKonf_AdditionalFee 
{

    /** TableName=M_QualityInsp_LagerKonf_AdditionalFee */
    public static final String Table_Name = "M_QualityInsp_LagerKonf_AdditionalFee";

    /** AD_Table_ID=540619 */
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

	public org.compiere.model.I_AD_Client getAD_Client() throws RuntimeException;

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_AdditionalFee, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_AdditionalFee, org.compiere.model.I_AD_Client>(I_M_QualityInsp_LagerKonf_AdditionalFee.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Beitrag pro Einheit.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAdditional_Fee_Amt_Per_UOM (java.math.BigDecimal Additional_Fee_Amt_Per_UOM);

	/**
	 * Get Beitrag pro Einheit.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getAdditional_Fee_Amt_Per_UOM();

    /** Column definition for Additional_Fee_Amt_Per_UOM */
    public static final org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_AdditionalFee, Object> COLUMN_Additional_Fee_Amt_Per_UOM = new org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_AdditionalFee, Object>(I_M_QualityInsp_LagerKonf_AdditionalFee.class, "Additional_Fee_Amt_Per_UOM", null);
    /** Column name Additional_Fee_Amt_Per_UOM */
    public static final String COLUMNNAME_Additional_Fee_Amt_Per_UOM = "Additional_Fee_Amt_Per_UOM";

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
    public static final org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_AdditionalFee, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_AdditionalFee, org.compiere.model.I_AD_Org>(I_M_QualityInsp_LagerKonf_AdditionalFee.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_AdditionalFee, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_AdditionalFee, Object>(I_M_QualityInsp_LagerKonf_AdditionalFee.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_AdditionalFee, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_AdditionalFee, org.compiere.model.I_AD_User>(I_M_QualityInsp_LagerKonf_AdditionalFee.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

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
    public static final org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_AdditionalFee, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_AdditionalFee, Object>(I_M_QualityInsp_LagerKonf_AdditionalFee.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Product_ID();

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException;

	public void setM_Product(org.compiere.model.I_M_Product M_Product);

    /** Column definition for M_Product_ID */
    public static final org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_AdditionalFee, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_AdditionalFee, org.compiere.model.I_M_Product>(I_M_QualityInsp_LagerKonf_AdditionalFee.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Zusätzlicher Beitrag.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_QualityInsp_LagerKonf_AdditionalFee_ID (int M_QualityInsp_LagerKonf_AdditionalFee_ID);

	/**
	 * Get Zusätzlicher Beitrag.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_QualityInsp_LagerKonf_AdditionalFee_ID();

    /** Column definition for M_QualityInsp_LagerKonf_AdditionalFee_ID */
    public static final org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_AdditionalFee, Object> COLUMN_M_QualityInsp_LagerKonf_AdditionalFee_ID = new org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_AdditionalFee, Object>(I_M_QualityInsp_LagerKonf_AdditionalFee.class, "M_QualityInsp_LagerKonf_AdditionalFee_ID", null);
    /** Column name M_QualityInsp_LagerKonf_AdditionalFee_ID */
    public static final String COLUMNNAME_M_QualityInsp_LagerKonf_AdditionalFee_ID = "M_QualityInsp_LagerKonf_AdditionalFee_ID";

	/**
	 * Set Lagerkonferenz-Version.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_QualityInsp_LagerKonf_Version_ID (int M_QualityInsp_LagerKonf_Version_ID);

	/**
	 * Get Lagerkonferenz-Version.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_QualityInsp_LagerKonf_Version_ID();

	public I_M_QualityInsp_LagerKonf_Version getM_QualityInsp_LagerKonf_Version() throws RuntimeException;

	public void setM_QualityInsp_LagerKonf_Version(I_M_QualityInsp_LagerKonf_Version M_QualityInsp_LagerKonf_Version);

    /** Column definition for M_QualityInsp_LagerKonf_Version_ID */
    public static final org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_AdditionalFee, I_M_QualityInsp_LagerKonf_Version> COLUMN_M_QualityInsp_LagerKonf_Version_ID = new org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_AdditionalFee, I_M_QualityInsp_LagerKonf_Version>(I_M_QualityInsp_LagerKonf_AdditionalFee.class, "M_QualityInsp_LagerKonf_Version_ID", I_M_QualityInsp_LagerKonf_Version.class);
    /** Column name M_QualityInsp_LagerKonf_Version_ID */
    public static final String COLUMNNAME_M_QualityInsp_LagerKonf_Version_ID = "M_QualityInsp_LagerKonf_Version_ID";

	/**
	 * Set Reihenfolge.
	 * Zur Bestimmung der Reihenfolge der Einträge;
 die kleinste Zahl kommt zuerst
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setSeqNo (int SeqNo);

	/**
	 * Get Reihenfolge.
	 * Zur Bestimmung der Reihenfolge der Einträge;
 die kleinste Zahl kommt zuerst
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getSeqNo();

    /** Column definition for SeqNo */
    public static final org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_AdditionalFee, Object> COLUMN_SeqNo = new org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_AdditionalFee, Object>(I_M_QualityInsp_LagerKonf_AdditionalFee.class, "SeqNo", null);
    /** Column name SeqNo */
    public static final String COLUMNNAME_SeqNo = "SeqNo";

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
    public static final org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_AdditionalFee, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_AdditionalFee, Object>(I_M_QualityInsp_LagerKonf_AdditionalFee.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_AdditionalFee, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_AdditionalFee, org.compiere.model.I_AD_User>(I_M_QualityInsp_LagerKonf_AdditionalFee.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
