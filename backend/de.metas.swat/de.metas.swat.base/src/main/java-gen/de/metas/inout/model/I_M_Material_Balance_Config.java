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
package de.metas.inout.model;


/** Generated Interface for M_Material_Balance_Config
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_Material_Balance_Config 
{

    /** TableName=M_Material_Balance_Config */
    public static final String Table_Name = "M_Material_Balance_Config";

    /** AD_Table_ID=540675 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

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
    public static final org.adempiere.model.ModelColumn<I_M_Material_Balance_Config, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_Material_Balance_Config, org.compiere.model.I_AD_Client>(I_M_Material_Balance_Config.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_Material_Balance_Config, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_Material_Balance_Config, org.compiere.model.I_AD_Org>(I_M_Material_Balance_Config.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set AutoResetInterval.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAutoResetInterval (java.math.BigDecimal AutoResetInterval);

	/**
	 * Get AutoResetInterval.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getAutoResetInterval();

    /** Column definition for AutoResetInterval */
    public static final org.adempiere.model.ModelColumn<I_M_Material_Balance_Config, Object> COLUMN_AutoResetInterval = new org.adempiere.model.ModelColumn<I_M_Material_Balance_Config, Object>(I_M_Material_Balance_Config.class, "AutoResetInterval", null);
    /** Column name AutoResetInterval */
    public static final String COLUMNNAME_AutoResetInterval = "AutoResetInterval";

	/**
	 * Set Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner();

	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner);

    /** Column definition for C_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Material_Balance_Config, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_M_Material_Balance_Config, org.compiere.model.I_C_BPartner>(I_M_Material_Balance_Config.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Geschäftspartnergruppe.
	 * Geschäftspartnergruppe
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BP_Group_ID (int C_BP_Group_ID);

	/**
	 * Get Geschäftspartnergruppe.
	 * Geschäftspartnergruppe
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BP_Group_ID();

	public org.compiere.model.I_C_BP_Group getC_BP_Group();

	public void setC_BP_Group(org.compiere.model.I_C_BP_Group C_BP_Group);

    /** Column definition for C_BP_Group_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Material_Balance_Config, org.compiere.model.I_C_BP_Group> COLUMN_C_BP_Group_ID = new org.adempiere.model.ModelColumn<I_M_Material_Balance_Config, org.compiere.model.I_C_BP_Group>(I_M_Material_Balance_Config.class, "C_BP_Group_ID", org.compiere.model.I_C_BP_Group.class);
    /** Column name C_BP_Group_ID */
    public static final String COLUMNNAME_C_BP_Group_ID = "C_BP_Group_ID";

	/**
	 * Set Kalender.
	 * Bezeichnung des Buchführungs-Kalenders
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Calendar_ID (int C_Calendar_ID);

	/**
	 * Get Kalender.
	 * Bezeichnung des Buchführungs-Kalenders
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Calendar_ID();

	public org.compiere.model.I_C_Calendar getC_Calendar();

	public void setC_Calendar(org.compiere.model.I_C_Calendar C_Calendar);

    /** Column definition for C_Calendar_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Material_Balance_Config, org.compiere.model.I_C_Calendar> COLUMN_C_Calendar_ID = new org.adempiere.model.ModelColumn<I_M_Material_Balance_Config, org.compiere.model.I_C_Calendar>(I_M_Material_Balance_Config.class, "C_Calendar_ID", org.compiere.model.I_C_Calendar.class);
    /** Column name C_Calendar_ID */
    public static final String COLUMNNAME_C_Calendar_ID = "C_Calendar_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_Material_Balance_Config, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_Material_Balance_Config, Object>(I_M_Material_Balance_Config.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_Material_Balance_Config, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_Material_Balance_Config, org.compiere.model.I_AD_User>(I_M_Material_Balance_Config.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_Material_Balance_Config, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_Material_Balance_Config, Object>(I_M_Material_Balance_Config.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set IsAutoResetEnabled.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsAutoResetEnabled (boolean IsAutoResetEnabled);

	/**
	 * Get IsAutoResetEnabled.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAutoResetEnabled();

    /** Column definition for IsAutoResetEnabled */
    public static final org.adempiere.model.ModelColumn<I_M_Material_Balance_Config, Object> COLUMN_IsAutoResetEnabled = new org.adempiere.model.ModelColumn<I_M_Material_Balance_Config, Object>(I_M_Material_Balance_Config.class, "IsAutoResetEnabled", null);
    /** Column name IsAutoResetEnabled */
    public static final String COLUMNNAME_IsAutoResetEnabled = "IsAutoResetEnabled";

	/**
	 * Set Kunde.
	 * Zeigt an, ob dieser Geschäftspartner ein Kunde ist
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsCustomer (java.lang.String IsCustomer);

	/**
	 * Get Kunde.
	 * Zeigt an, ob dieser Geschäftspartner ein Kunde ist
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getIsCustomer();

    /** Column definition for IsCustomer */
    public static final org.adempiere.model.ModelColumn<I_M_Material_Balance_Config, Object> COLUMN_IsCustomer = new org.adempiere.model.ModelColumn<I_M_Material_Balance_Config, Object>(I_M_Material_Balance_Config.class, "IsCustomer", null);
    /** Column name IsCustomer */
    public static final String COLUMNNAME_IsCustomer = "IsCustomer";

	/**
	 * Set IsForFlatrate.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsForFlatrate (java.lang.String IsForFlatrate);

	/**
	 * Get IsForFlatrate.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getIsForFlatrate();

    /** Column definition for IsForFlatrate */
    public static final org.adempiere.model.ModelColumn<I_M_Material_Balance_Config, Object> COLUMN_IsForFlatrate = new org.adempiere.model.ModelColumn<I_M_Material_Balance_Config, Object>(I_M_Material_Balance_Config.class, "IsForFlatrate", null);
    /** Column name IsForFlatrate */
    public static final String COLUMNNAME_IsForFlatrate = "IsForFlatrate";

	/**
	 * Set Lieferant.
	 * Zeigt an, ob dieser Geschaäftspartner ein Lieferant ist
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsVendor (java.lang.String IsVendor);

	/**
	 * Get Lieferant.
	 * Zeigt an, ob dieser Geschaäftspartner ein Lieferant ist
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getIsVendor();

    /** Column definition for IsVendor */
    public static final org.adempiere.model.ModelColumn<I_M_Material_Balance_Config, Object> COLUMN_IsVendor = new org.adempiere.model.ModelColumn<I_M_Material_Balance_Config, Object>(I_M_Material_Balance_Config.class, "IsVendor", null);
    /** Column name IsVendor */
    public static final String COLUMNNAME_IsVendor = "IsVendor";

	/**
	 * Set M_Material_Balance_Config.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Material_Balance_Config_ID (int M_Material_Balance_Config_ID);

	/**
	 * Get M_Material_Balance_Config.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Material_Balance_Config_ID();

    /** Column definition for M_Material_Balance_Config_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Material_Balance_Config, Object> COLUMN_M_Material_Balance_Config_ID = new org.adempiere.model.ModelColumn<I_M_Material_Balance_Config, Object>(I_M_Material_Balance_Config.class, "M_Material_Balance_Config_ID", null);
    /** Column name M_Material_Balance_Config_ID */
    public static final String COLUMNNAME_M_Material_Balance_Config_ID = "M_Material_Balance_Config_ID";

	/**
	 * Set Produkt-Kategorie.
	 * Kategorie eines Produktes
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Product_Category_ID (int M_Product_Category_ID);

	/**
	 * Get Produkt-Kategorie.
	 * Kategorie eines Produktes
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Product_Category_ID();

	public org.compiere.model.I_M_Product_Category getM_Product_Category();

	public void setM_Product_Category(org.compiere.model.I_M_Product_Category M_Product_Category);

    /** Column definition for M_Product_Category_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Material_Balance_Config, org.compiere.model.I_M_Product_Category> COLUMN_M_Product_Category_ID = new org.adempiere.model.ModelColumn<I_M_Material_Balance_Config, org.compiere.model.I_M_Product_Category>(I_M_Material_Balance_Config.class, "M_Product_Category_ID", org.compiere.model.I_M_Product_Category.class);
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

	public org.compiere.model.I_M_Product getM_Product();

	public void setM_Product(org.compiere.model.I_M_Product M_Product);

    /** Column definition for M_Product_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Material_Balance_Config, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_M_Material_Balance_Config, org.compiere.model.I_M_Product>(I_M_Material_Balance_Config.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Lager.
	 * Lager oder Ort für Dienstleistung
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Warehouse_ID (int M_Warehouse_ID);

	/**
	 * Get Lager.
	 * Lager oder Ort für Dienstleistung
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Warehouse_ID();

	public org.compiere.model.I_M_Warehouse getM_Warehouse();

	public void setM_Warehouse(org.compiere.model.I_M_Warehouse M_Warehouse);

    /** Column definition for M_Warehouse_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Material_Balance_Config, org.compiere.model.I_M_Warehouse> COLUMN_M_Warehouse_ID = new org.adempiere.model.ModelColumn<I_M_Material_Balance_Config, org.compiere.model.I_M_Warehouse>(I_M_Material_Balance_Config.class, "M_Warehouse_ID", org.compiere.model.I_M_Warehouse.class);
    /** Column name M_Warehouse_ID */
    public static final String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/**
	 * Set ResetBufferInterval.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setResetBufferInterval (java.math.BigDecimal ResetBufferInterval);

	/**
	 * Get ResetBufferInterval.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getResetBufferInterval();

    /** Column definition for ResetBufferInterval */
    public static final org.adempiere.model.ModelColumn<I_M_Material_Balance_Config, Object> COLUMN_ResetBufferInterval = new org.adempiere.model.ModelColumn<I_M_Material_Balance_Config, Object>(I_M_Material_Balance_Config.class, "ResetBufferInterval", null);
    /** Column name ResetBufferInterval */
    public static final String COLUMNNAME_ResetBufferInterval = "ResetBufferInterval";

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
    public static final org.adempiere.model.ModelColumn<I_M_Material_Balance_Config, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_Material_Balance_Config, Object>(I_M_Material_Balance_Config.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_Material_Balance_Config, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_Material_Balance_Config, org.compiere.model.I_AD_User>(I_M_Material_Balance_Config.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
