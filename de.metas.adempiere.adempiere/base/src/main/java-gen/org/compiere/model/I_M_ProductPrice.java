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


/** Generated Interface for M_ProductPrice
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_ProductPrice 
{

    /** TableName=M_ProductPrice */
    public static final String Table_Name = "M_ProductPrice";

    /** AD_Table_ID=251 */
    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */

	/** Get Mandant.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client() throws RuntimeException;

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ProductPrice, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_ProductPrice, org.compiere.model.I_AD_Client>(I_M_ProductPrice.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_ProductPrice, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_ProductPrice, org.compiere.model.I_AD_Org>(I_M_ProductPrice.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Steuerkategorie.
	  * Steuerkategorie
	  */
	public void setC_TaxCategory_ID (int C_TaxCategory_ID);

	/** Get Steuerkategorie.
	  * Steuerkategorie
	  */
	public int getC_TaxCategory_ID();

	public org.compiere.model.I_C_TaxCategory getC_TaxCategory() throws RuntimeException;

	public void setC_TaxCategory(org.compiere.model.I_C_TaxCategory C_TaxCategory);

    /** Column definition for C_TaxCategory_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ProductPrice, org.compiere.model.I_C_TaxCategory> COLUMN_C_TaxCategory_ID = new org.adempiere.model.ModelColumn<I_M_ProductPrice, org.compiere.model.I_C_TaxCategory>(I_M_ProductPrice.class, "C_TaxCategory_ID", org.compiere.model.I_C_TaxCategory.class);
    /** Column name C_TaxCategory_ID */
    public static final String COLUMNNAME_C_TaxCategory_ID = "C_TaxCategory_ID";

	/** Set Maßeinheit.
	  * Maßeinheit
	  */
	public void setC_UOM_ID (int C_UOM_ID);

	/** Get Maßeinheit.
	  * Maßeinheit
	  */
	public int getC_UOM_ID();

	public org.compiere.model.I_C_UOM getC_UOM() throws RuntimeException;

	public void setC_UOM(org.compiere.model.I_C_UOM C_UOM);

    /** Column definition for C_UOM_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ProductPrice, org.compiere.model.I_C_UOM> COLUMN_C_UOM_ID = new org.adempiere.model.ModelColumn<I_M_ProductPrice, org.compiere.model.I_C_UOM>(I_M_ProductPrice.class, "C_UOM_ID", org.compiere.model.I_C_UOM.class);
    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/** Get Erstellt.
	  * Date this record was created
	  */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_M_ProductPrice, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_ProductPrice, Object>(I_M_ProductPrice.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Erstellt durch.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_M_ProductPrice, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_ProductPrice, org.compiere.model.I_AD_User>(I_M_ProductPrice.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Set Aktiv.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Aktiv.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_M_ProductPrice, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_ProductPrice, Object>(I_M_ProductPrice.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Version Preisliste.
	  * Identifies a unique instance of a Price List
	  */
	public void setM_PriceList_Version_ID (int M_PriceList_Version_ID);

	/** Get Version Preisliste.
	  * Identifies a unique instance of a Price List
	  */
	public int getM_PriceList_Version_ID();

	public org.compiere.model.I_M_PriceList_Version getM_PriceList_Version() throws RuntimeException;

	public void setM_PriceList_Version(org.compiere.model.I_M_PriceList_Version M_PriceList_Version);

    /** Column definition for M_PriceList_Version_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ProductPrice, org.compiere.model.I_M_PriceList_Version> COLUMN_M_PriceList_Version_ID = new org.adempiere.model.ModelColumn<I_M_ProductPrice, org.compiere.model.I_M_PriceList_Version>(I_M_ProductPrice.class, "M_PriceList_Version_ID", org.compiere.model.I_M_PriceList_Version.class);
    /** Column name M_PriceList_Version_ID */
    public static final String COLUMNNAME_M_PriceList_Version_ID = "M_PriceList_Version_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_ProductPrice, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_M_ProductPrice, org.compiere.model.I_M_Product>(I_M_ProductPrice.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/** Set M_ProductPrice_ID	  */
	public void setM_ProductPrice_ID (int M_ProductPrice_ID);

	/** Get M_ProductPrice_ID	  */
	public int getM_ProductPrice_ID();

    /** Column definition for M_ProductPrice_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ProductPrice, Object> COLUMN_M_ProductPrice_ID = new org.adempiere.model.ModelColumn<I_M_ProductPrice, Object>(I_M_ProductPrice.class, "M_ProductPrice_ID", null);
    /** Column name M_ProductPrice_ID */
    public static final String COLUMNNAME_M_ProductPrice_ID = "M_ProductPrice_ID";

	/** Set Mindestpreis.
	  * Lowest price for a product
	  */
	public void setPriceLimit (java.math.BigDecimal PriceLimit);

	/** Get Mindestpreis.
	  * Lowest price for a product
	  */
	public java.math.BigDecimal getPriceLimit();

    /** Column definition for PriceLimit */
    public static final org.adempiere.model.ModelColumn<I_M_ProductPrice, Object> COLUMN_PriceLimit = new org.adempiere.model.ModelColumn<I_M_ProductPrice, Object>(I_M_ProductPrice.class, "PriceLimit", null);
    /** Column name PriceLimit */
    public static final String COLUMNNAME_PriceLimit = "PriceLimit";

	/** Set Auszeichnungspreis.
	  * Auszeichnungspreis
	  */
	public void setPriceList (java.math.BigDecimal PriceList);

	/** Get Auszeichnungspreis.
	  * Auszeichnungspreis
	  */
	public java.math.BigDecimal getPriceList();

    /** Column definition for PriceList */
    public static final org.adempiere.model.ModelColumn<I_M_ProductPrice, Object> COLUMN_PriceList = new org.adempiere.model.ModelColumn<I_M_ProductPrice, Object>(I_M_ProductPrice.class, "PriceList", null);
    /** Column name PriceList */
    public static final String COLUMNNAME_PriceList = "PriceList";

	/** Set Standardpreis.
	  * Standard Price
	  */
	public void setPriceStd (java.math.BigDecimal PriceStd);

	/** Get Standardpreis.
	  * Standard Price
	  */
	public java.math.BigDecimal getPriceStd();

    /** Column definition for PriceStd */
    public static final org.adempiere.model.ModelColumn<I_M_ProductPrice, Object> COLUMN_PriceStd = new org.adempiere.model.ModelColumn<I_M_ProductPrice, Object>(I_M_ProductPrice.class, "PriceStd", null);
    /** Column name PriceStd */
    public static final String COLUMNNAME_PriceStd = "PriceStd";

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
    public static final org.adempiere.model.ModelColumn<I_M_ProductPrice, Object> COLUMN_SeqNo = new org.adempiere.model.ModelColumn<I_M_ProductPrice, Object>(I_M_ProductPrice.class, "SeqNo", null);
    /** Column name SeqNo */
    public static final String COLUMNNAME_SeqNo = "SeqNo";

	/** Get Aktualisiert.
	  * Date this record was updated
	  */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_M_ProductPrice, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_ProductPrice, Object>(I_M_ProductPrice.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Aktualisiert durch.
	  * User who updated this records
	  */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_M_ProductPrice, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_ProductPrice, org.compiere.model.I_AD_User>(I_M_ProductPrice.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
