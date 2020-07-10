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


/** Generated Interface for M_Material_Tracking_Report_Line
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_Material_Tracking_Report_Line 
{

    /** TableName=M_Material_Tracking_Report_Line */
    public static final String Table_Name = "M_Material_Tracking_Report_Line";

    /** AD_Table_ID=540693 */
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
    public static final org.adempiere.model.ModelColumn<I_M_Material_Tracking_Report_Line, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_Material_Tracking_Report_Line, org.compiere.model.I_AD_Client>(I_M_Material_Tracking_Report_Line.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_Material_Tracking_Report_Line, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_Material_Tracking_Report_Line, org.compiere.model.I_AD_Org>(I_M_Material_Tracking_Report_Line.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_Material_Tracking_Report_Line, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_Material_Tracking_Report_Line, Object>(I_M_Material_Tracking_Report_Line.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_Material_Tracking_Report_Line, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_Material_Tracking_Report_Line, org.compiere.model.I_AD_User>(I_M_Material_Tracking_Report_Line.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Differenz.
	 * Unterschiedsmenge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDifferenceQty (java.math.BigDecimal DifferenceQty);

	/**
	 * Get Differenz.
	 * Unterschiedsmenge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getDifferenceQty();

    /** Column definition for DifferenceQty */
    public static final org.adempiere.model.ModelColumn<I_M_Material_Tracking_Report_Line, Object> COLUMN_DifferenceQty = new org.adempiere.model.ModelColumn<I_M_Material_Tracking_Report_Line, Object>(I_M_Material_Tracking_Report_Line.class, "DifferenceQty", null);
    /** Column name DifferenceQty */
    public static final String COLUMNNAME_DifferenceQty = "DifferenceQty";

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
    public static final org.adempiere.model.ModelColumn<I_M_Material_Tracking_Report_Line, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_Material_Tracking_Report_Line, Object>(I_M_Material_Tracking_Report_Line.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Zeilen-Aggregationsmerkmal.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLineAggregationKey (java.lang.String LineAggregationKey);

	/**
	 * Get Zeilen-Aggregationsmerkmal.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getLineAggregationKey();

    /** Column definition for LineAggregationKey */
    public static final org.adempiere.model.ModelColumn<I_M_Material_Tracking_Report_Line, Object> COLUMN_LineAggregationKey = new org.adempiere.model.ModelColumn<I_M_Material_Tracking_Report_Line, Object>(I_M_Material_Tracking_Report_Line.class, "LineAggregationKey", null);
    /** Column name LineAggregationKey */
    public static final String COLUMNNAME_LineAggregationKey = "LineAggregationKey";

	/**
	 * Set Ausprägung Merkmals-Satz.
	 * Instanz des Merkmals-Satzes zum Produkt
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID);

	/**
	 * Get Ausprägung Merkmals-Satz.
	 * Instanz des Merkmals-Satzes zum Produkt
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_AttributeSetInstance_ID();

	public org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance();

	public void setM_AttributeSetInstance(org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance);

    /** Column definition for M_AttributeSetInstance_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Material_Tracking_Report_Line, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new org.adempiere.model.ModelColumn<I_M_Material_Tracking_Report_Line, org.compiere.model.I_M_AttributeSetInstance>(I_M_Material_Tracking_Report_Line.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
    /** Column name M_AttributeSetInstance_ID */
    public static final String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/**
	 * Set M_Material_Tracking_Report.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Material_Tracking_Report_ID (int M_Material_Tracking_Report_ID);

	/**
	 * Get M_Material_Tracking_Report.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Material_Tracking_Report_ID();

	public I_M_Material_Tracking_Report getM_Material_Tracking_Report();

	public void setM_Material_Tracking_Report(I_M_Material_Tracking_Report M_Material_Tracking_Report);

    /** Column definition for M_Material_Tracking_Report_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Material_Tracking_Report_Line, I_M_Material_Tracking_Report> COLUMN_M_Material_Tracking_Report_ID = new org.adempiere.model.ModelColumn<I_M_Material_Tracking_Report_Line, I_M_Material_Tracking_Report>(I_M_Material_Tracking_Report_Line.class, "M_Material_Tracking_Report_ID", I_M_Material_Tracking_Report.class);
    /** Column name M_Material_Tracking_Report_ID */
    public static final String COLUMNNAME_M_Material_Tracking_Report_ID = "M_Material_Tracking_Report_ID";

	/**
	 * Set M_Material_Tracking_Report_Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Material_Tracking_Report_Line_ID (int M_Material_Tracking_Report_Line_ID);

	/**
	 * Get M_Material_Tracking_Report_Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Material_Tracking_Report_Line_ID();

    /** Column definition for M_Material_Tracking_Report_Line_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Material_Tracking_Report_Line, Object> COLUMN_M_Material_Tracking_Report_Line_ID = new org.adempiere.model.ModelColumn<I_M_Material_Tracking_Report_Line, Object>(I_M_Material_Tracking_Report_Line.class, "M_Material_Tracking_Report_Line_ID", null);
    /** Column name M_Material_Tracking_Report_Line_ID */
    public static final String COLUMNNAME_M_Material_Tracking_Report_Line_ID = "M_Material_Tracking_Report_Line_ID";

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

	public org.compiere.model.I_M_Product getM_Product();

	public void setM_Product(org.compiere.model.I_M_Product M_Product);

    /** Column definition for M_Product_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Material_Tracking_Report_Line, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_M_Material_Tracking_Report_Line, org.compiere.model.I_M_Product>(I_M_Material_Tracking_Report_Line.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Ausgelagerte Menge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyIssued (java.math.BigDecimal QtyIssued);

	/**
	 * Get Ausgelagerte Menge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyIssued();

    /** Column definition for QtyIssued */
    public static final org.adempiere.model.ModelColumn<I_M_Material_Tracking_Report_Line, Object> COLUMN_QtyIssued = new org.adempiere.model.ModelColumn<I_M_Material_Tracking_Report_Line, Object>(I_M_Material_Tracking_Report_Line.class, "QtyIssued", null);
    /** Column name QtyIssued */
    public static final String COLUMNNAME_QtyIssued = "QtyIssued";

	/**
	 * Set Empfangene Menge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyReceived (java.math.BigDecimal QtyReceived);

	/**
	 * Get Empfangene Menge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyReceived();

    /** Column definition for QtyReceived */
    public static final org.adempiere.model.ModelColumn<I_M_Material_Tracking_Report_Line, Object> COLUMN_QtyReceived = new org.adempiere.model.ModelColumn<I_M_Material_Tracking_Report_Line, Object>(I_M_Material_Tracking_Report_Line.class, "QtyReceived", null);
    /** Column name QtyReceived */
    public static final String COLUMNNAME_QtyReceived = "QtyReceived";

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
    public static final org.adempiere.model.ModelColumn<I_M_Material_Tracking_Report_Line, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_Material_Tracking_Report_Line, Object>(I_M_Material_Tracking_Report_Line.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_Material_Tracking_Report_Line, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_Material_Tracking_Report_Line, org.compiere.model.I_AD_User>(I_M_Material_Tracking_Report_Line.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
