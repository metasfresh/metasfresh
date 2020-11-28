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


/** Generated Interface for M_QualityInsp_LagerKonf_ProcessingFee
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_QualityInsp_LagerKonf_ProcessingFee 
{

    /** TableName=M_QualityInsp_LagerKonf_ProcessingFee */
    public static final String Table_Name = "M_QualityInsp_LagerKonf_ProcessingFee";

    /** AD_Table_ID=540617 */
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
    public static final org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_ProcessingFee, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_ProcessingFee, org.compiere.model.I_AD_Client>(I_M_QualityInsp_LagerKonf_ProcessingFee.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_ProcessingFee, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_ProcessingFee, org.compiere.model.I_AD_Org>(I_M_QualityInsp_LagerKonf_ProcessingFee.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_ProcessingFee, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_ProcessingFee, Object>(I_M_QualityInsp_LagerKonf_ProcessingFee.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_ProcessingFee, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_ProcessingFee, org.compiere.model.I_AD_User>(I_M_QualityInsp_LagerKonf_ProcessingFee.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Maßeinheit.
	 * Maßeinheit
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get Maßeinheit.
	 * Maßeinheit
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_UOM_ID();

	public org.compiere.model.I_C_UOM getC_UOM() throws RuntimeException;

	public void setC_UOM(org.compiere.model.I_C_UOM C_UOM);

    /** Column definition for C_UOM_ID */
    public static final org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_ProcessingFee, org.compiere.model.I_C_UOM> COLUMN_C_UOM_ID = new org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_ProcessingFee, org.compiere.model.I_C_UOM>(I_M_QualityInsp_LagerKonf_ProcessingFee.class, "C_UOM_ID", org.compiere.model.I_C_UOM.class);
    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_ProcessingFee, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_ProcessingFee, Object>(I_M_QualityInsp_LagerKonf_ProcessingFee.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Verarbeitungskosten (Sortierkosten).
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_QualityInsp_LagerKonf_ProcessingFee_ID (int M_QualityInsp_LagerKonf_ProcessingFee_ID);

	/**
	 * Get Verarbeitungskosten (Sortierkosten).
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_QualityInsp_LagerKonf_ProcessingFee_ID();

    /** Column definition for M_QualityInsp_LagerKonf_ProcessingFee_ID */
    public static final org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_ProcessingFee, Object> COLUMN_M_QualityInsp_LagerKonf_ProcessingFee_ID = new org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_ProcessingFee, Object>(I_M_QualityInsp_LagerKonf_ProcessingFee.class, "M_QualityInsp_LagerKonf_ProcessingFee_ID", null);
    /** Column name M_QualityInsp_LagerKonf_ProcessingFee_ID */
    public static final String COLUMNNAME_M_QualityInsp_LagerKonf_ProcessingFee_ID = "M_QualityInsp_LagerKonf_ProcessingFee_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_ProcessingFee, I_M_QualityInsp_LagerKonf_Version> COLUMN_M_QualityInsp_LagerKonf_Version_ID = new org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_ProcessingFee, I_M_QualityInsp_LagerKonf_Version>(I_M_QualityInsp_LagerKonf_ProcessingFee.class, "M_QualityInsp_LagerKonf_Version_ID", I_M_QualityInsp_LagerKonf_Version.class);
    /** Column name M_QualityInsp_LagerKonf_Version_ID */
    public static final String COLUMNNAME_M_QualityInsp_LagerKonf_Version_ID = "M_QualityInsp_LagerKonf_Version_ID";

	/**
	 * Set % ab.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPercentFrom (java.math.BigDecimal PercentFrom);

	/**
	 * Get % ab.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPercentFrom();

    /** Column definition for PercentFrom */
    public static final org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_ProcessingFee, Object> COLUMN_PercentFrom = new org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_ProcessingFee, Object>(I_M_QualityInsp_LagerKonf_ProcessingFee.class, "PercentFrom", null);
    /** Column name PercentFrom */
    public static final String COLUMNNAME_PercentFrom = "PercentFrom";

	/**
	 * Set Betrag pro Einheit.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setProcessing_Fee_Amt_Per_UOM (java.math.BigDecimal Processing_Fee_Amt_Per_UOM);

	/**
	 * Get Betrag pro Einheit.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getProcessing_Fee_Amt_Per_UOM();

    /** Column definition for Processing_Fee_Amt_Per_UOM */
    public static final org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_ProcessingFee, Object> COLUMN_Processing_Fee_Amt_Per_UOM = new org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_ProcessingFee, Object>(I_M_QualityInsp_LagerKonf_ProcessingFee.class, "Processing_Fee_Amt_Per_UOM", null);
    /** Column name Processing_Fee_Amt_Per_UOM */
    public static final String COLUMNNAME_Processing_Fee_Amt_Per_UOM = "Processing_Fee_Amt_Per_UOM";

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
    public static final org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_ProcessingFee, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_ProcessingFee, Object>(I_M_QualityInsp_LagerKonf_ProcessingFee.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_ProcessingFee, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_QualityInsp_LagerKonf_ProcessingFee, org.compiere.model.I_AD_User>(I_M_QualityInsp_LagerKonf_ProcessingFee.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
