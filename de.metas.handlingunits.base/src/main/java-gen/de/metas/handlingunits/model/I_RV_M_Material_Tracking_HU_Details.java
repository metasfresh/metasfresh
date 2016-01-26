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
package de.metas.handlingunits.model;


/** Generated Interface for RV_M_Material_Tracking_HU_Details
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_RV_M_Material_Tracking_HU_Details 
{

    /** TableName=RV_M_Material_Tracking_HU_Details */
    public static final String Table_Name = "RV_M_Material_Tracking_HU_Details";

    /** AD_Table_ID=540682 */
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
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_RV_M_Material_Tracking_HU_Details, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_RV_M_Material_Tracking_HU_Details, org.compiere.model.I_AD_Client>(I_RV_M_Material_Tracking_HU_Details.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

	public org.compiere.model.I_AD_Org getAD_Org();

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_RV_M_Material_Tracking_HU_Details, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_RV_M_Material_Tracking_HU_Details, org.compiere.model.I_AD_Org>(I_RV_M_Material_Tracking_HU_Details.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Get Erstellt.
	 * Datum, an dem dieser Eintrag erstellt wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_RV_M_Material_Tracking_HU_Details, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_RV_M_Material_Tracking_HU_Details, Object>(I_RV_M_Material_Tracking_HU_Details.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * Nutzer, der diesen Eintrag erstellt hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_RV_M_Material_Tracking_HU_Details, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_RV_M_Material_Tracking_HU_Details, org.compiere.model.I_AD_User>(I_RV_M_Material_Tracking_HU_Details.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Maßeinheit.
	 * Maßeinheit
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get Maßeinheit.
	 * Maßeinheit
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_UOM_ID();

	public org.compiere.model.I_C_UOM getC_UOM();

	public void setC_UOM(org.compiere.model.I_C_UOM C_UOM);

    /** Column definition for C_UOM_ID */
    public static final org.adempiere.model.ModelColumn<I_RV_M_Material_Tracking_HU_Details, org.compiere.model.I_C_UOM> COLUMN_C_UOM_ID = new org.adempiere.model.ModelColumn<I_RV_M_Material_Tracking_HU_Details, org.compiere.model.I_C_UOM>(I_RV_M_Material_Tracking_HU_Details.class, "C_UOM_ID", org.compiere.model.I_C_UOM.class);
    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Set Gebinde Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setHUStatus (java.lang.String HUStatus);

	/**
	 * Get Gebinde Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getHUStatus();

    /** Column definition for HUStatus */
    public static final org.adempiere.model.ModelColumn<I_RV_M_Material_Tracking_HU_Details, Object> COLUMN_HUStatus = new org.adempiere.model.ModelColumn<I_RV_M_Material_Tracking_HU_Details, Object>(I_RV_M_Material_Tracking_HU_Details.class, "HUStatus", null);
    /** Column name HUStatus */
    public static final String COLUMNNAME_HUStatus = "HUStatus";

	/**
	 * Set Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_RV_M_Material_Tracking_HU_Details, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_RV_M_Material_Tracking_HU_Details, Object>(I_RV_M_Material_Tracking_HU_Details.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Waschprobe.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsQualityInspection (boolean IsQualityInspection);

	/**
	 * Get Waschprobe.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isQualityInspection();

    /** Column definition for IsQualityInspection */
    public static final org.adempiere.model.ModelColumn<I_RV_M_Material_Tracking_HU_Details, Object> COLUMN_IsQualityInspection = new org.adempiere.model.ModelColumn<I_RV_M_Material_Tracking_HU_Details, Object>(I_RV_M_Material_Tracking_HU_Details.class, "IsQualityInspection", null);
    /** Column name IsQualityInspection */
    public static final String COLUMNNAME_IsQualityInspection = "IsQualityInspection";

	/**
	 * Set Los-Nr..
	 * Los-Nummer (alphanumerisch)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLot (java.lang.String Lot);

	/**
	 * Get Los-Nr..
	 * Los-Nummer (alphanumerisch)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getLot();

    /** Column definition for Lot */
    public static final org.adempiere.model.ModelColumn<I_RV_M_Material_Tracking_HU_Details, Object> COLUMN_Lot = new org.adempiere.model.ModelColumn<I_RV_M_Material_Tracking_HU_Details, Object>(I_RV_M_Material_Tracking_HU_Details.class, "Lot", null);
    /** Column name Lot */
    public static final String COLUMNNAME_Lot = "Lot";

	/**
	 * Set Handling Units.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_HU_ID (int M_HU_ID);

	/**
	 * Get Handling Units.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_HU_ID();

	public de.metas.handlingunits.model.I_M_HU getM_HU();

	public void setM_HU(de.metas.handlingunits.model.I_M_HU M_HU);

    /** Column definition for M_HU_ID */
    public static final org.adempiere.model.ModelColumn<I_RV_M_Material_Tracking_HU_Details, de.metas.handlingunits.model.I_M_HU> COLUMN_M_HU_ID = new org.adempiere.model.ModelColumn<I_RV_M_Material_Tracking_HU_Details, de.metas.handlingunits.model.I_M_HU>(I_RV_M_Material_Tracking_HU_Details.class, "M_HU_ID", de.metas.handlingunits.model.I_M_HU.class);
    /** Column name M_HU_ID */
    public static final String COLUMNNAME_M_HU_ID = "M_HU_ID";

	/**
	 * Set Lagerort.
	 * Lagerort im Lager
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Locator_ID (int M_Locator_ID);

	/**
	 * Get Lagerort.
	 * Lagerort im Lager
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Locator_ID();

	public org.compiere.model.I_M_Locator getM_Locator();

	public void setM_Locator(org.compiere.model.I_M_Locator M_Locator);

    /** Column definition for M_Locator_ID */
    public static final org.adempiere.model.ModelColumn<I_RV_M_Material_Tracking_HU_Details, org.compiere.model.I_M_Locator> COLUMN_M_Locator_ID = new org.adempiere.model.ModelColumn<I_RV_M_Material_Tracking_HU_Details, org.compiere.model.I_M_Locator>(I_RV_M_Material_Tracking_HU_Details.class, "M_Locator_ID", org.compiere.model.I_M_Locator.class);
    /** Column name M_Locator_ID */
    public static final String COLUMNNAME_M_Locator_ID = "M_Locator_ID";

	/**
	 * Set Material-Vorgang-ID.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Material_Tracking_ID (int M_Material_Tracking_ID);

	/**
	 * Get Material-Vorgang-ID.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Material_Tracking_ID();

	public de.metas.materialtracking.model.I_M_Material_Tracking getM_Material_Tracking();

	public void setM_Material_Tracking(de.metas.materialtracking.model.I_M_Material_Tracking M_Material_Tracking);

    /** Column definition for M_Material_Tracking_ID */
    public static final org.adempiere.model.ModelColumn<I_RV_M_Material_Tracking_HU_Details, de.metas.materialtracking.model.I_M_Material_Tracking> COLUMN_M_Material_Tracking_ID = new org.adempiere.model.ModelColumn<I_RV_M_Material_Tracking_HU_Details, de.metas.materialtracking.model.I_M_Material_Tracking>(I_RV_M_Material_Tracking_HU_Details.class, "M_Material_Tracking_ID", de.metas.materialtracking.model.I_M_Material_Tracking.class);
    /** Column name M_Material_Tracking_ID */
    public static final String COLUMNNAME_M_Material_Tracking_ID = "M_Material_Tracking_ID";

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
    public static final org.adempiere.model.ModelColumn<I_RV_M_Material_Tracking_HU_Details, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_RV_M_Material_Tracking_HU_Details, org.compiere.model.I_M_Product>(I_RV_M_Material_Tracking_HU_Details.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Zugeteilt zu Prod.-Auftrag.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPP_Order_Issue_ID (int PP_Order_Issue_ID);

	/**
	 * Get Zugeteilt zu Prod.-Auftrag.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getPP_Order_Issue_ID();

	public org.eevolution.model.I_PP_Order getPP_Order_Issue();

	public void setPP_Order_Issue(org.eevolution.model.I_PP_Order PP_Order_Issue);

    /** Column definition for PP_Order_Issue_ID */
    public static final org.adempiere.model.ModelColumn<I_RV_M_Material_Tracking_HU_Details, org.eevolution.model.I_PP_Order> COLUMN_PP_Order_Issue_ID = new org.adempiere.model.ModelColumn<I_RV_M_Material_Tracking_HU_Details, org.eevolution.model.I_PP_Order>(I_RV_M_Material_Tracking_HU_Details.class, "PP_Order_Issue_ID", org.eevolution.model.I_PP_Order.class);
    /** Column name PP_Order_Issue_ID */
    public static final String COLUMNNAME_PP_Order_Issue_ID = "PP_Order_Issue_ID";

	/**
	 * Set Empf. aus Prod.-Auftrag.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPP_Order_Receipt_ID (int PP_Order_Receipt_ID);

	/**
	 * Get Empf. aus Prod.-Auftrag.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getPP_Order_Receipt_ID();

	public org.eevolution.model.I_PP_Order getPP_Order_Receipt();

	public void setPP_Order_Receipt(org.eevolution.model.I_PP_Order PP_Order_Receipt);

    /** Column definition for PP_Order_Receipt_ID */
    public static final org.adempiere.model.ModelColumn<I_RV_M_Material_Tracking_HU_Details, org.eevolution.model.I_PP_Order> COLUMN_PP_Order_Receipt_ID = new org.adempiere.model.ModelColumn<I_RV_M_Material_Tracking_HU_Details, org.eevolution.model.I_PP_Order>(I_RV_M_Material_Tracking_HU_Details.class, "PP_Order_Receipt_ID", org.eevolution.model.I_PP_Order.class);
    /** Column name PP_Order_Receipt_ID */
    public static final String COLUMNNAME_PP_Order_Receipt_ID = "PP_Order_Receipt_ID";

	/**
	 * Set Menge.
	 * Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQty (java.math.BigDecimal Qty);

	/**
	 * Get Menge.
	 * Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQty();

    /** Column definition for Qty */
    public static final org.adempiere.model.ModelColumn<I_RV_M_Material_Tracking_HU_Details, Object> COLUMN_Qty = new org.adempiere.model.ModelColumn<I_RV_M_Material_Tracking_HU_Details, Object>(I_RV_M_Material_Tracking_HU_Details.class, "Qty", null);
    /** Column name Qty */
    public static final String COLUMNNAME_Qty = "Qty";

	/**
	 * Set RV_M_Material_Tracking_HU_Details_ID.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRV_M_Material_Tracking_HU_Details_ID (int RV_M_Material_Tracking_HU_Details_ID);

	/**
	 * Get RV_M_Material_Tracking_HU_Details_ID.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getRV_M_Material_Tracking_HU_Details_ID();

    /** Column definition for RV_M_Material_Tracking_HU_Details_ID */
    public static final org.adempiere.model.ModelColumn<I_RV_M_Material_Tracking_HU_Details, Object> COLUMN_RV_M_Material_Tracking_HU_Details_ID = new org.adempiere.model.ModelColumn<I_RV_M_Material_Tracking_HU_Details, Object>(I_RV_M_Material_Tracking_HU_Details.class, "RV_M_Material_Tracking_HU_Details_ID", null);
    /** Column name RV_M_Material_Tracking_HU_Details_ID */
    public static final String COLUMNNAME_RV_M_Material_Tracking_HU_Details_ID = "RV_M_Material_Tracking_HU_Details_ID";

	/**
	 * Get Aktualisiert.
	 * Datum, an dem dieser Eintrag aktualisiert wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_RV_M_Material_Tracking_HU_Details, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_RV_M_Material_Tracking_HU_Details, Object>(I_RV_M_Material_Tracking_HU_Details.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * Nutzer, der diesen Eintrag aktualisiert hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_RV_M_Material_Tracking_HU_Details, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_RV_M_Material_Tracking_HU_Details, org.compiere.model.I_AD_User>(I_RV_M_Material_Tracking_HU_Details.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
