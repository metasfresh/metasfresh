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
package de.metas.flatrate.model;


/** Generated Interface for C_Flatrate_Matching
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Flatrate_Matching 
{

    /** TableName=C_Flatrate_Matching */
    public static final String Table_Name = "C_Flatrate_Matching";

    /** AD_Table_ID=540312 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */

	/** Get Mandant.
	  * Mandant für diese Installation.
	  */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client() throws RuntimeException;

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_Matching, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_Flatrate_Matching, org.compiere.model.I_AD_Client>(I_C_Flatrate_Matching.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Set Sektion.
	  * Organisatorische Einheit des Mandanten
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Sektion.
	  * Organisatorische Einheit des Mandanten
	  */
	public int getAD_Org_ID();

	public org.compiere.model.I_AD_Org getAD_Org() throws RuntimeException;

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_Matching, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_Flatrate_Matching, org.compiere.model.I_AD_Org>(I_C_Flatrate_Matching.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Kosten.
	  * Zusätzliche Kosten
	  */
	public void setC_Charge_ID (int C_Charge_ID);

	/** Get Kosten.
	  * Zusätzliche Kosten
	  */
	public int getC_Charge_ID();

	public org.compiere.model.I_C_Charge getC_Charge() throws RuntimeException;

	public void setC_Charge(org.compiere.model.I_C_Charge C_Charge);

    /** Column definition for C_Charge_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_Matching, org.compiere.model.I_C_Charge> COLUMN_C_Charge_ID = new org.adempiere.model.ModelColumn<I_C_Flatrate_Matching, org.compiere.model.I_C_Charge>(I_C_Flatrate_Matching.class, "C_Charge_ID", org.compiere.model.I_C_Charge.class);
    /** Column name C_Charge_ID */
    public static final String COLUMNNAME_C_Charge_ID = "C_Charge_ID";

	/** Set Vertragsbedingungen	  */
	public void setC_Flatrate_Conditions_ID (int C_Flatrate_Conditions_ID);

	/** Get Vertragsbedingungen	  */
	public int getC_Flatrate_Conditions_ID();

	public de.metas.flatrate.model.I_C_Flatrate_Conditions getC_Flatrate_Conditions() throws RuntimeException;

	public void setC_Flatrate_Conditions(de.metas.flatrate.model.I_C_Flatrate_Conditions C_Flatrate_Conditions);

    /** Column definition for C_Flatrate_Conditions_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_Matching, de.metas.flatrate.model.I_C_Flatrate_Conditions> COLUMN_C_Flatrate_Conditions_ID = new org.adempiere.model.ModelColumn<I_C_Flatrate_Matching, de.metas.flatrate.model.I_C_Flatrate_Conditions>(I_C_Flatrate_Matching.class, "C_Flatrate_Conditions_ID", de.metas.flatrate.model.I_C_Flatrate_Conditions.class);
    /** Column name C_Flatrate_Conditions_ID */
    public static final String COLUMNNAME_C_Flatrate_Conditions_ID = "C_Flatrate_Conditions_ID";

	/** Set Zuordnungszeile	  */
	public void setC_Flatrate_Matching_ID (int C_Flatrate_Matching_ID);

	/** Get Zuordnungszeile	  */
	public int getC_Flatrate_Matching_ID();

    /** Column definition for C_Flatrate_Matching_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_Matching, Object> COLUMN_C_Flatrate_Matching_ID = new org.adempiere.model.ModelColumn<I_C_Flatrate_Matching, Object>(I_C_Flatrate_Matching.class, "C_Flatrate_Matching_ID", null);
    /** Column name C_Flatrate_Matching_ID */
    public static final String COLUMNNAME_C_Flatrate_Matching_ID = "C_Flatrate_Matching_ID";

	/** Set Vertragsverlängerung/-übergang.
	  * Regelt z.B. die Vertragslaufzeit, Kündigungsfristen, autmatische Verlängerung usw.
	  */
	public void setC_Flatrate_Transition_ID (int C_Flatrate_Transition_ID);

	/** Get Vertragsverlängerung/-übergang.
	  * Regelt z.B. die Vertragslaufzeit, Kündigungsfristen, autmatische Verlängerung usw.
	  */
	public int getC_Flatrate_Transition_ID();

	public de.metas.flatrate.model.I_C_Flatrate_Transition getC_Flatrate_Transition() throws RuntimeException;

	public void setC_Flatrate_Transition(de.metas.flatrate.model.I_C_Flatrate_Transition C_Flatrate_Transition);

    /** Column definition for C_Flatrate_Transition_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_Matching, de.metas.flatrate.model.I_C_Flatrate_Transition> COLUMN_C_Flatrate_Transition_ID = new org.adempiere.model.ModelColumn<I_C_Flatrate_Matching, de.metas.flatrate.model.I_C_Flatrate_Transition>(I_C_Flatrate_Matching.class, "C_Flatrate_Transition_ID", de.metas.flatrate.model.I_C_Flatrate_Transition.class);
    /** Column name C_Flatrate_Transition_ID */
    public static final String COLUMNNAME_C_Flatrate_Transition_ID = "C_Flatrate_Transition_ID";

	/** Get Erstellt.
	  * Datum, an dem dieser Eintrag erstellt wurde
	  */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_Matching, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_Flatrate_Matching, Object>(I_C_Flatrate_Matching.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Erstellt durch.
	  * Nutzer, der diesen Eintrag erstellt hat
	  */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_Matching, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_Flatrate_Matching, org.compiere.model.I_AD_User>(I_C_Flatrate_Matching.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Set Aktiv.
	  * Der Eintrag ist im System aktiv
	  */
	public void setIsActive (boolean IsActive);

	/** Get Aktiv.
	  * Der Eintrag ist im System aktiv
	  */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_Matching, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_Flatrate_Matching, Object>(I_C_Flatrate_Matching.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Preissystem.
	  * Ein Preissystem enthält beliebig viele, Länder-abhängige Preislisten.
	  */
	public void setM_PricingSystem_ID (int M_PricingSystem_ID);

	/** Get Preissystem.
	  * Ein Preissystem enthält beliebig viele, Länder-abhängige Preislisten.
	  */
	public int getM_PricingSystem_ID();

	public org.compiere.model.I_M_PricingSystem getM_PricingSystem() throws RuntimeException;

	public void setM_PricingSystem(org.compiere.model.I_M_PricingSystem M_PricingSystem);

    /** Column definition for M_PricingSystem_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_Matching, org.compiere.model.I_M_PricingSystem> COLUMN_M_PricingSystem_ID = new org.adempiere.model.ModelColumn<I_C_Flatrate_Matching, org.compiere.model.I_M_PricingSystem>(I_C_Flatrate_Matching.class, "M_PricingSystem_ID", org.compiere.model.I_M_PricingSystem.class);
    /** Column name M_PricingSystem_ID */
    public static final String COLUMNNAME_M_PricingSystem_ID = "M_PricingSystem_ID";

	/** Set Produkt-Kategorie.
	  * Kategorie eines Produktes, dass dem Vertrag unterliegt
	  */
	public void setM_Product_Category_Matching_ID (int M_Product_Category_Matching_ID);

	/** Get Produkt-Kategorie.
	  * Kategorie eines Produktes, dass dem Vertrag unterliegt
	  */
	public int getM_Product_Category_Matching_ID();

	public org.compiere.model.I_M_Product_Category getM_Product_Category_Matching() throws RuntimeException;

	public void setM_Product_Category_Matching(org.compiere.model.I_M_Product_Category M_Product_Category_Matching);

    /** Column definition for M_Product_Category_Matching_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_Matching, org.compiere.model.I_M_Product_Category> COLUMN_M_Product_Category_Matching_ID = new org.adempiere.model.ModelColumn<I_C_Flatrate_Matching, org.compiere.model.I_M_Product_Category>(I_C_Flatrate_Matching.class, "M_Product_Category_Matching_ID", org.compiere.model.I_M_Product_Category.class);
    /** Column name M_Product_Category_Matching_ID */
    public static final String COLUMNNAME_M_Product_Category_Matching_ID = "M_Product_Category_Matching_ID";

	/** Set Produkt.
	  * Produkt, Leistung, Artikel
	  */
	public void setM_Product_ID (int M_Product_ID);

	/** Get Produkt.
	  * Produkt, Leistung, Artikel
	  */
	public int getM_Product_ID();

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException;

	public void setM_Product(org.compiere.model.I_M_Product M_Product);

    /** Column definition for M_Product_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_Matching, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_C_Flatrate_Matching, org.compiere.model.I_M_Product>(I_C_Flatrate_Matching.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/** Set Liefermenge pro Abolieferung	  */
	public void setQtyPerDelivery (java.math.BigDecimal QtyPerDelivery);

	/** Get Liefermenge pro Abolieferung	  */
	public java.math.BigDecimal getQtyPerDelivery();

    /** Column definition for QtyPerDelivery */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_Matching, Object> COLUMN_QtyPerDelivery = new org.adempiere.model.ModelColumn<I_C_Flatrate_Matching, Object>(I_C_Flatrate_Matching.class, "QtyPerDelivery", null);
    /** Column name QtyPerDelivery */
    public static final String COLUMNNAME_QtyPerDelivery = "QtyPerDelivery";

	/** Set Reihenfolge.
	  * Zur Bestimmung der Reihenfolge der Einträge;
 die kleinste Zahl kommt zuerst
	  */
	public void setSeqNo (int SeqNo);

	/** Get Reihenfolge.
	  * Zur Bestimmung der Reihenfolge der Einträge;
 die kleinste Zahl kommt zuerst
	  */
	public int getSeqNo();

    /** Column definition for SeqNo */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_Matching, Object> COLUMN_SeqNo = new org.adempiere.model.ModelColumn<I_C_Flatrate_Matching, Object>(I_C_Flatrate_Matching.class, "SeqNo", null);
    /** Column name SeqNo */
    public static final String COLUMNNAME_SeqNo = "SeqNo";

	/** Get Aktualisiert.
	  * Datum, an dem dieser Eintrag aktualisiert wurde
	  */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_Matching, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_Flatrate_Matching, Object>(I_C_Flatrate_Matching.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Aktualisiert durch.
	  * Nutzer, der diesen Eintrag aktualisiert hat
	  */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_Matching, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_Flatrate_Matching, org.compiere.model.I_AD_User>(I_C_Flatrate_Matching.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
