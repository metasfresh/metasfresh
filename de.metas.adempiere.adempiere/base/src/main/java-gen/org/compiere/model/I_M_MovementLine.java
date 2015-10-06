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


/** Generated Interface for M_MovementLine
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_MovementLine 
{

    /** TableName=M_MovementLine */
    public static final String Table_Name = "M_MovementLine";

    /** AD_Table_ID=324 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org 
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(1);

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
    public static final org.adempiere.model.ModelColumn<I_M_MovementLine, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_MovementLine, org.compiere.model.I_AD_Client>(I_M_MovementLine.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_MovementLine, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_MovementLine, org.compiere.model.I_AD_Org>(I_M_MovementLine.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Kostenstelle.
	 * Kostenstelle
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Activity_ID (int C_Activity_ID);

	/**
	 * Get Kostenstelle.
	 * Kostenstelle
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Activity_ID();

	public org.compiere.model.I_C_Activity getC_Activity();

	public void setC_Activity(org.compiere.model.I_C_Activity C_Activity);

    /** Column definition for C_Activity_ID */
    public static final org.adempiere.model.ModelColumn<I_M_MovementLine, org.compiere.model.I_C_Activity> COLUMN_C_Activity_ID = new org.adempiere.model.ModelColumn<I_M_MovementLine, org.compiere.model.I_C_Activity>(I_M_MovementLine.class, "C_Activity_ID", org.compiere.model.I_C_Activity.class);
    /** Column name C_Activity_ID */
    public static final String COLUMNNAME_C_Activity_ID = "C_Activity_ID";

	/**
	 * Set Activity From.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_ActivityFrom_ID (int C_ActivityFrom_ID);

	/**
	 * Get Activity From.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_ActivityFrom_ID();

	public org.compiere.model.I_C_Activity getC_ActivityFrom();

	public void setC_ActivityFrom(org.compiere.model.I_C_Activity C_ActivityFrom);

    /** Column definition for C_ActivityFrom_ID */
    public static final org.adempiere.model.ModelColumn<I_M_MovementLine, org.compiere.model.I_C_Activity> COLUMN_C_ActivityFrom_ID = new org.adempiere.model.ModelColumn<I_M_MovementLine, org.compiere.model.I_C_Activity>(I_M_MovementLine.class, "C_ActivityFrom_ID", org.compiere.model.I_C_Activity.class);
    /** Column name C_ActivityFrom_ID */
    public static final String COLUMNNAME_C_ActivityFrom_ID = "C_ActivityFrom_ID";

	/**
	 * Set Bestätigte Menge.
	 * Confirmation of a received quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setConfirmedQty (java.math.BigDecimal ConfirmedQty);

	/**
	 * Get Bestätigte Menge.
	 * Confirmation of a received quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getConfirmedQty();

    /** Column definition for ConfirmedQty */
    public static final org.adempiere.model.ModelColumn<I_M_MovementLine, Object> COLUMN_ConfirmedQty = new org.adempiere.model.ModelColumn<I_M_MovementLine, Object>(I_M_MovementLine.class, "ConfirmedQty", null);
    /** Column name ConfirmedQty */
    public static final String COLUMNNAME_ConfirmedQty = "ConfirmedQty";

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
    public static final org.adempiere.model.ModelColumn<I_M_MovementLine, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_MovementLine, Object>(I_M_MovementLine.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_MovementLine, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_MovementLine, org.compiere.model.I_AD_User>(I_M_MovementLine.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Distribution Order Line Alternative.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDD_OrderLine_Alternative_ID (int DD_OrderLine_Alternative_ID);

	/**
	 * Get Distribution Order Line Alternative.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getDD_OrderLine_Alternative_ID();

	public org.eevolution.model.I_DD_OrderLine_Alternative getDD_OrderLine_Alternative();

	public void setDD_OrderLine_Alternative(org.eevolution.model.I_DD_OrderLine_Alternative DD_OrderLine_Alternative);

    /** Column definition for DD_OrderLine_Alternative_ID */
    public static final org.adempiere.model.ModelColumn<I_M_MovementLine, org.eevolution.model.I_DD_OrderLine_Alternative> COLUMN_DD_OrderLine_Alternative_ID = new org.adempiere.model.ModelColumn<I_M_MovementLine, org.eevolution.model.I_DD_OrderLine_Alternative>(I_M_MovementLine.class, "DD_OrderLine_Alternative_ID", org.eevolution.model.I_DD_OrderLine_Alternative.class);
    /** Column name DD_OrderLine_Alternative_ID */
    public static final String COLUMNNAME_DD_OrderLine_Alternative_ID = "DD_OrderLine_Alternative_ID";

	/**
	 * Set Distribution Order Line.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDD_OrderLine_ID (int DD_OrderLine_ID);

	/**
	 * Get Distribution Order Line.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getDD_OrderLine_ID();

	public org.eevolution.model.I_DD_OrderLine getDD_OrderLine();

	public void setDD_OrderLine(org.eevolution.model.I_DD_OrderLine DD_OrderLine);

    /** Column definition for DD_OrderLine_ID */
    public static final org.adempiere.model.ModelColumn<I_M_MovementLine, org.eevolution.model.I_DD_OrderLine> COLUMN_DD_OrderLine_ID = new org.adempiere.model.ModelColumn<I_M_MovementLine, org.eevolution.model.I_DD_OrderLine>(I_M_MovementLine.class, "DD_OrderLine_ID", org.eevolution.model.I_DD_OrderLine.class);
    /** Column name DD_OrderLine_ID */
    public static final String COLUMNNAME_DD_OrderLine_ID = "DD_OrderLine_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_MovementLine, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_M_MovementLine, Object>(I_M_MovementLine.class, "Description", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_MovementLine, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_MovementLine, Object>(I_M_MovementLine.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Zeile Nr..
	 * Unique line for this document
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setLine (int Line);

	/**
	 * Get Zeile Nr..
	 * Unique line for this document
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getLine();

    /** Column definition for Line */
    public static final org.adempiere.model.ModelColumn<I_M_MovementLine, Object> COLUMN_Line = new org.adempiere.model.ModelColumn<I_M_MovementLine, Object>(I_M_MovementLine.class, "Line", null);
    /** Column name Line */
    public static final String COLUMNNAME_Line = "Line";

	/**
	 * Set Ausprägung Merkmals-Satz.
	 * Product Attribute Set Instance
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID);

	/**
	 * Get Ausprägung Merkmals-Satz.
	 * Product Attribute Set Instance
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_AttributeSetInstance_ID();

	public org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance();

	public void setM_AttributeSetInstance(org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance);

    /** Column definition for M_AttributeSetInstance_ID */
    public static final org.adempiere.model.ModelColumn<I_M_MovementLine, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new org.adempiere.model.ModelColumn<I_M_MovementLine, org.compiere.model.I_M_AttributeSetInstance>(I_M_MovementLine.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
    /** Column name M_AttributeSetInstance_ID */
    public static final String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/**
	 * Set Attribute Set Instance To.
	 * Target Product Attribute Set Instance
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_AttributeSetInstanceTo_ID (int M_AttributeSetInstanceTo_ID);

	/**
	 * Get Attribute Set Instance To.
	 * Target Product Attribute Set Instance
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_AttributeSetInstanceTo_ID();

	public org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstanceTo();

	public void setM_AttributeSetInstanceTo(org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstanceTo);

    /** Column definition for M_AttributeSetInstanceTo_ID */
    public static final org.adempiere.model.ModelColumn<I_M_MovementLine, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstanceTo_ID = new org.adempiere.model.ModelColumn<I_M_MovementLine, org.compiere.model.I_M_AttributeSetInstance>(I_M_MovementLine.class, "M_AttributeSetInstanceTo_ID", org.compiere.model.I_M_AttributeSetInstance.class);
    /** Column name M_AttributeSetInstanceTo_ID */
    public static final String COLUMNNAME_M_AttributeSetInstanceTo_ID = "M_AttributeSetInstanceTo_ID";

	/**
	 * Set Versand-/Wareneingangsposition.
	 * Position auf Versand- oder Wareneingangsbeleg
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_InOutLine_ID (int M_InOutLine_ID);

	/**
	 * Get Versand-/Wareneingangsposition.
	 * Position auf Versand- oder Wareneingangsbeleg
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_InOutLine_ID();

	public org.compiere.model.I_M_InOutLine getM_InOutLine();

	public void setM_InOutLine(org.compiere.model.I_M_InOutLine M_InOutLine);

    /** Column definition for M_InOutLine_ID */
    public static final org.adempiere.model.ModelColumn<I_M_MovementLine, org.compiere.model.I_M_InOutLine> COLUMN_M_InOutLine_ID = new org.adempiere.model.ModelColumn<I_M_MovementLine, org.compiere.model.I_M_InOutLine>(I_M_MovementLine.class, "M_InOutLine_ID", org.compiere.model.I_M_InOutLine.class);
    /** Column name M_InOutLine_ID */
    public static final String COLUMNNAME_M_InOutLine_ID = "M_InOutLine_ID";

	/**
	 * Set Lagerort.
	 * Warehouse Locator
	 *
	 * <br>Type: Locator
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Locator_ID (int M_Locator_ID);

	/**
	 * Get Lagerort.
	 * Warehouse Locator
	 *
	 * <br>Type: Locator
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Locator_ID();

	public org.compiere.model.I_M_Locator getM_Locator();

	public void setM_Locator(org.compiere.model.I_M_Locator M_Locator);

    /** Column definition for M_Locator_ID */
    public static final org.adempiere.model.ModelColumn<I_M_MovementLine, org.compiere.model.I_M_Locator> COLUMN_M_Locator_ID = new org.adempiere.model.ModelColumn<I_M_MovementLine, org.compiere.model.I_M_Locator>(I_M_MovementLine.class, "M_Locator_ID", org.compiere.model.I_M_Locator.class);
    /** Column name M_Locator_ID */
    public static final String COLUMNNAME_M_Locator_ID = "M_Locator_ID";

	/**
	 * Set Lagerort An.
	 * Location inventory is moved to
	 *
	 * <br>Type: Locator
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_LocatorTo_ID (int M_LocatorTo_ID);

	/**
	 * Get Lagerort An.
	 * Location inventory is moved to
	 *
	 * <br>Type: Locator
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_LocatorTo_ID();

	public org.compiere.model.I_M_Locator getM_LocatorTo();

	public void setM_LocatorTo(org.compiere.model.I_M_Locator M_LocatorTo);

    /** Column definition for M_LocatorTo_ID */
    public static final org.adempiere.model.ModelColumn<I_M_MovementLine, org.compiere.model.I_M_Locator> COLUMN_M_LocatorTo_ID = new org.adempiere.model.ModelColumn<I_M_MovementLine, org.compiere.model.I_M_Locator>(I_M_MovementLine.class, "M_LocatorTo_ID", org.compiere.model.I_M_Locator.class);
    /** Column name M_LocatorTo_ID */
    public static final String COLUMNNAME_M_LocatorTo_ID = "M_LocatorTo_ID";

	/**
	 * Set Warenbewegung.
	 * Movement of Inventory
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Movement_ID (int M_Movement_ID);

	/**
	 * Get Warenbewegung.
	 * Movement of Inventory
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Movement_ID();

	public org.compiere.model.I_M_Movement getM_Movement();

	public void setM_Movement(org.compiere.model.I_M_Movement M_Movement);

    /** Column definition for M_Movement_ID */
    public static final org.adempiere.model.ModelColumn<I_M_MovementLine, org.compiere.model.I_M_Movement> COLUMN_M_Movement_ID = new org.adempiere.model.ModelColumn<I_M_MovementLine, org.compiere.model.I_M_Movement>(I_M_MovementLine.class, "M_Movement_ID", org.compiere.model.I_M_Movement.class);
    /** Column name M_Movement_ID */
    public static final String COLUMNNAME_M_Movement_ID = "M_Movement_ID";

	/**
	 * Set Warenbewegungs- Position.
	 * Inventory Move document Line
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_MovementLine_ID (int M_MovementLine_ID);

	/**
	 * Get Warenbewegungs- Position.
	 * Inventory Move document Line
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_MovementLine_ID();

    /** Column definition for M_MovementLine_ID */
    public static final org.adempiere.model.ModelColumn<I_M_MovementLine, Object> COLUMN_M_MovementLine_ID = new org.adempiere.model.ModelColumn<I_M_MovementLine, Object>(I_M_MovementLine.class, "M_MovementLine_ID", null);
    /** Column name M_MovementLine_ID */
    public static final String COLUMNNAME_M_MovementLine_ID = "M_MovementLine_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_MovementLine, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_M_MovementLine, org.compiere.model.I_M_Product>(I_M_MovementLine.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Bewegungs-Menge.
	 * Quantity of a product moved.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMovementQty (java.math.BigDecimal MovementQty);

	/**
	 * Get Bewegungs-Menge.
	 * Quantity of a product moved.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getMovementQty();

    /** Column definition for MovementQty */
    public static final org.adempiere.model.ModelColumn<I_M_MovementLine, Object> COLUMN_MovementQty = new org.adempiere.model.ModelColumn<I_M_MovementLine, Object>(I_M_MovementLine.class, "MovementQty", null);
    /** Column name MovementQty */
    public static final String COLUMNNAME_MovementQty = "MovementQty";

	/**
	 * Set Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setProcessed (boolean Processed);

	/**
	 * Get Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isProcessed();

    /** Column definition for Processed */
    public static final org.adempiere.model.ModelColumn<I_M_MovementLine, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_M_MovementLine, Object>(I_M_MovementLine.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Reversal Line.
	 * Use to keep the reversal line ID for reversing costing purpose
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setReversalLine_ID (int ReversalLine_ID);

	/**
	 * Get Reversal Line.
	 * Use to keep the reversal line ID for reversing costing purpose
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getReversalLine_ID();

	public org.compiere.model.I_M_MovementLine getReversalLine();

	public void setReversalLine(org.compiere.model.I_M_MovementLine ReversalLine);

    /** Column definition for ReversalLine_ID */
    public static final org.adempiere.model.ModelColumn<I_M_MovementLine, org.compiere.model.I_M_MovementLine> COLUMN_ReversalLine_ID = new org.adempiere.model.ModelColumn<I_M_MovementLine, org.compiere.model.I_M_MovementLine>(I_M_MovementLine.class, "ReversalLine_ID", org.compiere.model.I_M_MovementLine.class);
    /** Column name ReversalLine_ID */
    public static final String COLUMNNAME_ReversalLine_ID = "ReversalLine_ID";

	/**
	 * Set Verworfene Menge.
	 * The Quantity scrapped due to QA issues
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setScrappedQty (java.math.BigDecimal ScrappedQty);

	/**
	 * Get Verworfene Menge.
	 * The Quantity scrapped due to QA issues
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getScrappedQty();

    /** Column definition for ScrappedQty */
    public static final org.adempiere.model.ModelColumn<I_M_MovementLine, Object> COLUMN_ScrappedQty = new org.adempiere.model.ModelColumn<I_M_MovementLine, Object>(I_M_MovementLine.class, "ScrappedQty", null);
    /** Column name ScrappedQty */
    public static final String COLUMNNAME_ScrappedQty = "ScrappedQty";

	/**
	 * Set Zielmenge.
	 * Target Movement Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setTargetQty (java.math.BigDecimal TargetQty);

	/**
	 * Get Zielmenge.
	 * Target Movement Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getTargetQty();

    /** Column definition for TargetQty */
    public static final org.adempiere.model.ModelColumn<I_M_MovementLine, Object> COLUMN_TargetQty = new org.adempiere.model.ModelColumn<I_M_MovementLine, Object>(I_M_MovementLine.class, "TargetQty", null);
    /** Column name TargetQty */
    public static final String COLUMNNAME_TargetQty = "TargetQty";

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
    public static final org.adempiere.model.ModelColumn<I_M_MovementLine, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_MovementLine, Object>(I_M_MovementLine.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_MovementLine, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_MovementLine, org.compiere.model.I_AD_User>(I_M_MovementLine.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Suchschlüssel.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public void setValue (java.lang.String Value);

	/**
	 * Get Suchschlüssel.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public java.lang.String getValue();

    /** Column definition for Value */
    public static final org.adempiere.model.ModelColumn<I_M_MovementLine, Object> COLUMN_Value = new org.adempiere.model.ModelColumn<I_M_MovementLine, Object>(I_M_MovementLine.class, "Value", null);
    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";
}
