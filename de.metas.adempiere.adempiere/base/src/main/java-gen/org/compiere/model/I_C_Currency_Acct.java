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


/** Generated Interface for C_Currency_Acct
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Currency_Acct 
{

    /** TableName=C_Currency_Acct */
    public static final String Table_Name = "C_Currency_Acct";

    /** AD_Table_ID=638 */
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
    public static final org.adempiere.model.ModelColumn<I_C_Currency_Acct, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_Currency_Acct, org.compiere.model.I_AD_Client>(I_C_Currency_Acct.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_Currency_Acct, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_Currency_Acct, org.compiere.model.I_AD_Org>(I_C_Currency_Acct.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

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

	public org.compiere.model.I_C_AcctSchema getC_AcctSchema();

	public void setC_AcctSchema(org.compiere.model.I_C_AcctSchema C_AcctSchema);

    /** Column definition for C_AcctSchema_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Currency_Acct, org.compiere.model.I_C_AcctSchema> COLUMN_C_AcctSchema_ID = new org.adempiere.model.ModelColumn<I_C_Currency_Acct, org.compiere.model.I_C_AcctSchema>(I_C_Currency_Acct.class, "C_AcctSchema_ID", org.compiere.model.I_C_AcctSchema.class);
    /** Column name C_AcctSchema_ID */
    public static final String COLUMNNAME_C_AcctSchema_ID = "C_AcctSchema_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_Currency_Acct, org.compiere.model.I_C_Currency> COLUMN_C_Currency_ID = new org.adempiere.model.ModelColumn<I_C_Currency_Acct, org.compiere.model.I_C_Currency>(I_C_Currency_Acct.class, "C_Currency_ID", org.compiere.model.I_C_Currency.class);
    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_Currency_Acct, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_Currency_Acct, Object>(I_C_Currency_Acct.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Currency_Acct, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_Currency_Acct, org.compiere.model.I_AD_User>(I_C_Currency_Acct.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

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
    public static final org.adempiere.model.ModelColumn<I_C_Currency_Acct, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_Currency_Acct, Object>(I_C_Currency_Acct.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Realisierte Währungsgewinne.
	 * Konto für Realisierte Währungsgewinne
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setRealizedGain_Acct (int RealizedGain_Acct);

	/**
	 * Get Realisierte Währungsgewinne.
	 * Konto für Realisierte Währungsgewinne
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getRealizedGain_Acct();

	public org.compiere.model.I_C_ValidCombination getRealizedGain_A();

	public void setRealizedGain_A(org.compiere.model.I_C_ValidCombination RealizedGain_A);

    /** Column definition for RealizedGain_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_Currency_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_RealizedGain_Acct = new org.adempiere.model.ModelColumn<I_C_Currency_Acct, org.compiere.model.I_C_ValidCombination>(I_C_Currency_Acct.class, "RealizedGain_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name RealizedGain_Acct */
    public static final String COLUMNNAME_RealizedGain_Acct = "RealizedGain_Acct";

	/**
	 * Set Realisierte Währungsverluste.
	 * Konto für realisierte Währungsverluste
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setRealizedLoss_Acct (int RealizedLoss_Acct);

	/**
	 * Get Realisierte Währungsverluste.
	 * Konto für realisierte Währungsverluste
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getRealizedLoss_Acct();

	public org.compiere.model.I_C_ValidCombination getRealizedLoss_A();

	public void setRealizedLoss_A(org.compiere.model.I_C_ValidCombination RealizedLoss_A);

    /** Column definition for RealizedLoss_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_Currency_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_RealizedLoss_Acct = new org.adempiere.model.ModelColumn<I_C_Currency_Acct, org.compiere.model.I_C_ValidCombination>(I_C_Currency_Acct.class, "RealizedLoss_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name RealizedLoss_Acct */
    public static final String COLUMNNAME_RealizedLoss_Acct = "RealizedLoss_Acct";

	/**
	 * Set Nicht realisierte Währungsgewinne.
	 * Konto für nicht realisierte Währungsgewinne
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setUnrealizedGain_Acct (int UnrealizedGain_Acct);

	/**
	 * Get Nicht realisierte Währungsgewinne.
	 * Konto für nicht realisierte Währungsgewinne
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUnrealizedGain_Acct();

	public org.compiere.model.I_C_ValidCombination getUnrealizedGain_A();

	public void setUnrealizedGain_A(org.compiere.model.I_C_ValidCombination UnrealizedGain_A);

    /** Column definition for UnrealizedGain_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_Currency_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_UnrealizedGain_Acct = new org.adempiere.model.ModelColumn<I_C_Currency_Acct, org.compiere.model.I_C_ValidCombination>(I_C_Currency_Acct.class, "UnrealizedGain_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name UnrealizedGain_Acct */
    public static final String COLUMNNAME_UnrealizedGain_Acct = "UnrealizedGain_Acct";

	/**
	 * Set Nicht realisierte Währungsverluste.
	 * Konto für nicht realisierte Währungsverluste
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setUnrealizedLoss_Acct (int UnrealizedLoss_Acct);

	/**
	 * Get Nicht realisierte Währungsverluste.
	 * Konto für nicht realisierte Währungsverluste
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUnrealizedLoss_Acct();

	public org.compiere.model.I_C_ValidCombination getUnrealizedLoss_A();

	public void setUnrealizedLoss_A(org.compiere.model.I_C_ValidCombination UnrealizedLoss_A);

    /** Column definition for UnrealizedLoss_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_Currency_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_UnrealizedLoss_Acct = new org.adempiere.model.ModelColumn<I_C_Currency_Acct, org.compiere.model.I_C_ValidCombination>(I_C_Currency_Acct.class, "UnrealizedLoss_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name UnrealizedLoss_Acct */
    public static final String COLUMNNAME_UnrealizedLoss_Acct = "UnrealizedLoss_Acct";

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
    public static final org.adempiere.model.ModelColumn<I_C_Currency_Acct, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_Currency_Acct, Object>(I_C_Currency_Acct.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Currency_Acct, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_Currency_Acct, org.compiere.model.I_AD_User>(I_C_Currency_Acct.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
