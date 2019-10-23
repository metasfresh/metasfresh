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
package org.eevolution.model;


/** Generated Interface for DD_NetworkDistributionLine
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_DD_NetworkDistributionLine 
{

    /** TableName=DD_NetworkDistributionLine */
    public static final String Table_Name = "DD_NetworkDistributionLine";

    /** AD_Table_ID=53061 */
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

	public org.compiere.model.I_AD_Client getAD_Client() throws RuntimeException;

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_DD_NetworkDistributionLine, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_DD_NetworkDistributionLine, org.compiere.model.I_AD_Client>(I_DD_NetworkDistributionLine.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_DD_NetworkDistributionLine, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_DD_NetworkDistributionLine, org.compiere.model.I_AD_Org>(I_DD_NetworkDistributionLine.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

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
    public static final org.adempiere.model.ModelColumn<I_DD_NetworkDistributionLine, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_DD_NetworkDistributionLine, Object>(I_DD_NetworkDistributionLine.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_DD_NetworkDistributionLine, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_DD_NetworkDistributionLine, org.compiere.model.I_AD_User>(I_DD_NetworkDistributionLine.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Allow Push.
	 * Allow pushing materials from suppliers through this path.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDD_AllowPush (boolean DD_AllowPush);

	/**
	 * Get Allow Push.
	 * Allow pushing materials from suppliers through this path.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isDD_AllowPush();

    /** Column definition for DD_AllowPush */
    public static final org.adempiere.model.ModelColumn<I_DD_NetworkDistributionLine, Object> COLUMN_DD_AllowPush = new org.adempiere.model.ModelColumn<I_DD_NetworkDistributionLine, Object>(I_DD_NetworkDistributionLine.class, "DD_AllowPush", null);
    /** Column name DD_AllowPush */
    public static final String COLUMNNAME_DD_AllowPush = "DD_AllowPush";

	/**
	 * Set Network Distribution.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDD_NetworkDistribution_ID (int DD_NetworkDistribution_ID);

	/**
	 * Get Network Distribution.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getDD_NetworkDistribution_ID();

	public org.eevolution.model.I_DD_NetworkDistribution getDD_NetworkDistribution() throws RuntimeException;

	public void setDD_NetworkDistribution(org.eevolution.model.I_DD_NetworkDistribution DD_NetworkDistribution);

    /** Column definition for DD_NetworkDistribution_ID */
    public static final org.adempiere.model.ModelColumn<I_DD_NetworkDistributionLine, org.eevolution.model.I_DD_NetworkDistribution> COLUMN_DD_NetworkDistribution_ID = new org.adempiere.model.ModelColumn<I_DD_NetworkDistributionLine, org.eevolution.model.I_DD_NetworkDistribution>(I_DD_NetworkDistributionLine.class, "DD_NetworkDistribution_ID", org.eevolution.model.I_DD_NetworkDistribution.class);
    /** Column name DD_NetworkDistribution_ID */
    public static final String COLUMNNAME_DD_NetworkDistribution_ID = "DD_NetworkDistribution_ID";

	/**
	 * Set Network Distribution Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDD_NetworkDistributionLine_ID (int DD_NetworkDistributionLine_ID);

	/**
	 * Get Network Distribution Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getDD_NetworkDistributionLine_ID();

    /** Column definition for DD_NetworkDistributionLine_ID */
    public static final org.adempiere.model.ModelColumn<I_DD_NetworkDistributionLine, Object> COLUMN_DD_NetworkDistributionLine_ID = new org.adempiere.model.ModelColumn<I_DD_NetworkDistributionLine, Object>(I_DD_NetworkDistributionLine.class, "DD_NetworkDistributionLine_ID", null);
    /** Column name DD_NetworkDistributionLine_ID */
    public static final String COLUMNNAME_DD_NetworkDistributionLine_ID = "DD_NetworkDistributionLine_ID";

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
    public static final org.adempiere.model.ModelColumn<I_DD_NetworkDistributionLine, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_DD_NetworkDistributionLine, Object>(I_DD_NetworkDistributionLine.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Keep target plant.
	 * If set, the MRP demand of the distribution order which will be generated will have the same plant is target warehouse.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsKeepTargetPlant (boolean IsKeepTargetPlant);

	/**
	 * Get Keep target plant.
	 * If set, the MRP demand of the distribution order which will be generated will have the same plant is target warehouse.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isKeepTargetPlant();

    /** Column definition for IsKeepTargetPlant */
    public static final org.adempiere.model.ModelColumn<I_DD_NetworkDistributionLine, Object> COLUMN_IsKeepTargetPlant = new org.adempiere.model.ModelColumn<I_DD_NetworkDistributionLine, Object>(I_DD_NetworkDistributionLine.class, "IsKeepTargetPlant", null);
    /** Column name IsKeepTargetPlant */
    public static final String COLUMNNAME_IsKeepTargetPlant = "IsKeepTargetPlant";

	/**
	 * Set Lieferweg.
	 * Method or manner of product delivery
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Shipper_ID (int M_Shipper_ID);

	/**
	 * Get Lieferweg.
	 * Method or manner of product delivery
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Shipper_ID();

	public org.compiere.model.I_M_Shipper getM_Shipper() throws RuntimeException;

	public void setM_Shipper(org.compiere.model.I_M_Shipper M_Shipper);

    /** Column definition for M_Shipper_ID */
    public static final org.adempiere.model.ModelColumn<I_DD_NetworkDistributionLine, org.compiere.model.I_M_Shipper> COLUMN_M_Shipper_ID = new org.adempiere.model.ModelColumn<I_DD_NetworkDistributionLine, org.compiere.model.I_M_Shipper>(I_DD_NetworkDistributionLine.class, "M_Shipper_ID", org.compiere.model.I_M_Shipper.class);
    /** Column name M_Shipper_ID */
    public static final String COLUMNNAME_M_Shipper_ID = "M_Shipper_ID";

	/**
	 * Set Lager.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Warehouse_ID (int M_Warehouse_ID);

	/**
	 * Get Lager.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Warehouse_ID();

	public org.compiere.model.I_M_Warehouse getM_Warehouse() throws RuntimeException;

	public void setM_Warehouse(org.compiere.model.I_M_Warehouse M_Warehouse);

    /** Column definition for M_Warehouse_ID */
    public static final org.adempiere.model.ModelColumn<I_DD_NetworkDistributionLine, org.compiere.model.I_M_Warehouse> COLUMN_M_Warehouse_ID = new org.adempiere.model.ModelColumn<I_DD_NetworkDistributionLine, org.compiere.model.I_M_Warehouse>(I_DD_NetworkDistributionLine.class, "M_Warehouse_ID", org.compiere.model.I_M_Warehouse.class);
    /** Column name M_Warehouse_ID */
    public static final String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/**
	 * Set Source Warehouse.
	 * Optional Warehouse to replenish from
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_WarehouseSource_ID (int M_WarehouseSource_ID);

	/**
	 * Get Source Warehouse.
	 * Optional Warehouse to replenish from
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_WarehouseSource_ID();

	public org.compiere.model.I_M_Warehouse getM_WarehouseSource() throws RuntimeException;

	public void setM_WarehouseSource(org.compiere.model.I_M_Warehouse M_WarehouseSource);

    /** Column definition for M_WarehouseSource_ID */
    public static final org.adempiere.model.ModelColumn<I_DD_NetworkDistributionLine, org.compiere.model.I_M_Warehouse> COLUMN_M_WarehouseSource_ID = new org.adempiere.model.ModelColumn<I_DD_NetworkDistributionLine, org.compiere.model.I_M_Warehouse>(I_DD_NetworkDistributionLine.class, "M_WarehouseSource_ID", org.compiere.model.I_M_Warehouse.class);
    /** Column name M_WarehouseSource_ID */
    public static final String COLUMNNAME_M_WarehouseSource_ID = "M_WarehouseSource_ID";

	/**
	 * Set Percent.
	 * Percentage
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPercent (java.math.BigDecimal Percent);

	/**
	 * Get Percent.
	 * Percentage
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPercent();

    /** Column definition for Percent */
    public static final org.adempiere.model.ModelColumn<I_DD_NetworkDistributionLine, Object> COLUMN_Percent = new org.adempiere.model.ModelColumn<I_DD_NetworkDistributionLine, Object>(I_DD_NetworkDistributionLine.class, "Percent", null);
    /** Column name Percent */
    public static final String COLUMNNAME_Percent = "Percent";

	/**
	 * Set Relative Priorität.
	 * Where inventory should be picked from first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPriorityNo (int PriorityNo);

	/**
	 * Get Relative Priorität.
	 * Where inventory should be picked from first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getPriorityNo();

    /** Column definition for PriorityNo */
    public static final org.adempiere.model.ModelColumn<I_DD_NetworkDistributionLine, Object> COLUMN_PriorityNo = new org.adempiere.model.ModelColumn<I_DD_NetworkDistributionLine, Object>(I_DD_NetworkDistributionLine.class, "PriorityNo", null);
    /** Column name PriorityNo */
    public static final String COLUMNNAME_PriorityNo = "PriorityNo";

	/**
	 * Set Transfert Time.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setTransfertTime (java.math.BigDecimal TransfertTime);

	/**
	 * Get Transfert Time.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getTransfertTime();

    /** Column definition for TransfertTime */
    public static final org.adempiere.model.ModelColumn<I_DD_NetworkDistributionLine, Object> COLUMN_TransfertTime = new org.adempiere.model.ModelColumn<I_DD_NetworkDistributionLine, Object>(I_DD_NetworkDistributionLine.class, "TransfertTime", null);
    /** Column name TransfertTime */
    public static final String COLUMNNAME_TransfertTime = "TransfertTime";

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
    public static final org.adempiere.model.ModelColumn<I_DD_NetworkDistributionLine, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_DD_NetworkDistributionLine, Object>(I_DD_NetworkDistributionLine.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_DD_NetworkDistributionLine, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_DD_NetworkDistributionLine, org.compiere.model.I_AD_User>(I_DD_NetworkDistributionLine.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Gültig ab.
	 * Valid from including this date (first day)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setValidFrom (java.sql.Timestamp ValidFrom);

	/**
	 * Get Gültig ab.
	 * Valid from including this date (first day)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getValidFrom();

    /** Column definition for ValidFrom */
    public static final org.adempiere.model.ModelColumn<I_DD_NetworkDistributionLine, Object> COLUMN_ValidFrom = new org.adempiere.model.ModelColumn<I_DD_NetworkDistributionLine, Object>(I_DD_NetworkDistributionLine.class, "ValidFrom", null);
    /** Column name ValidFrom */
    public static final String COLUMNNAME_ValidFrom = "ValidFrom";

	/**
	 * Set Gültig bis.
	 * Valid to including this date (last day)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setValidTo (java.sql.Timestamp ValidTo);

	/**
	 * Get Gültig bis.
	 * Valid to including this date (last day)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getValidTo();

    /** Column definition for ValidTo */
    public static final org.adempiere.model.ModelColumn<I_DD_NetworkDistributionLine, Object> COLUMN_ValidTo = new org.adempiere.model.ModelColumn<I_DD_NetworkDistributionLine, Object>(I_DD_NetworkDistributionLine.class, "ValidTo", null);
    /** Column name ValidTo */
    public static final String COLUMNNAME_ValidTo = "ValidTo";
}
