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


/** Generated Interface for M_Locator
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_Locator 
{

    /** TableName=M_Locator */
    public static final String Table_Name = "M_Locator";

    /** AD_Table_ID=207 */
    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 7 - System - Client - Org 
     */
    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(7);

    /** Load Meta Data */

	/** Get Mandant.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client() throws RuntimeException;

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Locator, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_Locator, org.compiere.model.I_AD_Client>(I_M_Locator.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_Locator, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_Locator, org.compiere.model.I_AD_Org>(I_M_Locator.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Get Erstellt.
	  * Date this record was created
	  */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_M_Locator, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_Locator, Object>(I_M_Locator.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Erstellt durch.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_M_Locator, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_Locator, org.compiere.model.I_AD_User>(I_M_Locator.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_Locator, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_Locator, Object>(I_M_Locator.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Standard.
	  * Default value
	  */
	public void setIsDefault (boolean IsDefault);

	/** Get Standard.
	  * Default value
	  */
	public boolean isDefault();

    /** Column definition for IsDefault */
    public static final org.adempiere.model.ModelColumn<I_M_Locator, Object> COLUMN_IsDefault = new org.adempiere.model.ModelColumn<I_M_Locator, Object>(I_M_Locator.class, "IsDefault", null);
    /** Column name IsDefault */
    public static final String COLUMNNAME_IsDefault = "IsDefault";

	/** Set Lagerort.
	  * Warehouse Locator
	  */
	public void setM_Locator_ID (int M_Locator_ID);

	/** Get Lagerort.
	  * Warehouse Locator
	  */
	public int getM_Locator_ID();

    /** Column definition for M_Locator_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Locator, Object> COLUMN_M_Locator_ID = new org.adempiere.model.ModelColumn<I_M_Locator, Object>(I_M_Locator.class, "M_Locator_ID", null);
    /** Column name M_Locator_ID */
    public static final String COLUMNNAME_M_Locator_ID = "M_Locator_ID";

	/** Set Lager.
	  * Storage Warehouse and Service Point
	  */
	public void setM_Warehouse_ID (int M_Warehouse_ID);

	/** Get Lager.
	  * Storage Warehouse and Service Point
	  */
	public int getM_Warehouse_ID();

	public org.compiere.model.I_M_Warehouse getM_Warehouse() throws RuntimeException;

	public void setM_Warehouse(org.compiere.model.I_M_Warehouse M_Warehouse);

    /** Column definition for M_Warehouse_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Locator, org.compiere.model.I_M_Warehouse> COLUMN_M_Warehouse_ID = new org.adempiere.model.ModelColumn<I_M_Locator, org.compiere.model.I_M_Warehouse>(I_M_Locator.class, "M_Warehouse_ID", org.compiere.model.I_M_Warehouse.class);
    /** Column name M_Warehouse_ID */
    public static final String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/** Set Relative Priorität.
	  * Where inventory should be picked from first
	  */
	public void setPriorityNo (int PriorityNo);

	/** Get Relative Priorität.
	  * Where inventory should be picked from first
	  */
	public int getPriorityNo();

    /** Column definition for PriorityNo */
    public static final org.adempiere.model.ModelColumn<I_M_Locator, Object> COLUMN_PriorityNo = new org.adempiere.model.ModelColumn<I_M_Locator, Object>(I_M_Locator.class, "PriorityNo", null);
    /** Column name PriorityNo */
    public static final String COLUMNNAME_PriorityNo = "PriorityNo";

	/** Get Aktualisiert.
	  * Date this record was updated
	  */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_M_Locator, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_Locator, Object>(I_M_Locator.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Aktualisiert durch.
	  * User who updated this records
	  */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_M_Locator, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_Locator, org.compiere.model.I_AD_User>(I_M_Locator.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Set Suchschlüssel.
	  * Search key for the record in the format required - must be unique
	  */
	public void setValue (java.lang.String Value);

	/** Get Suchschlüssel.
	  * Search key for the record in the format required - must be unique
	  */
	public java.lang.String getValue();

    /** Column definition for Value */
    public static final org.adempiere.model.ModelColumn<I_M_Locator, Object> COLUMN_Value = new org.adempiere.model.ModelColumn<I_M_Locator, Object>(I_M_Locator.class, "Value", null);
    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";

	/** Set Gang (X).
	  * X dimension, e.g., Aisle
	  */
	public void setX (java.lang.String X);

	/** Get Gang (X).
	  * X dimension, e.g., Aisle
	  */
	public java.lang.String getX();

    /** Column definition for X */
    public static final org.adempiere.model.ModelColumn<I_M_Locator, Object> COLUMN_X = new org.adempiere.model.ModelColumn<I_M_Locator, Object>(I_M_Locator.class, "X", null);
    /** Column name X */
    public static final String COLUMNNAME_X = "X";

	/** Set Fach (Y).
	  * Y dimension, e.g., Bin
	  */
	public void setY (java.lang.String Y);

	/** Get Fach (Y).
	  * Y dimension, e.g., Bin
	  */
	public java.lang.String getY();

    /** Column definition for Y */
    public static final org.adempiere.model.ModelColumn<I_M_Locator, Object> COLUMN_Y = new org.adempiere.model.ModelColumn<I_M_Locator, Object>(I_M_Locator.class, "Y", null);
    /** Column name Y */
    public static final String COLUMNNAME_Y = "Y";

	/** Set Ebene (Z).
	  * Z dimension, e.g., Level
	  */
	public void setZ (java.lang.String Z);

	/** Get Ebene (Z).
	  * Z dimension, e.g., Level
	  */
	public java.lang.String getZ();

    /** Column definition for Z */
    public static final org.adempiere.model.ModelColumn<I_M_Locator, Object> COLUMN_Z = new org.adempiere.model.ModelColumn<I_M_Locator, Object>(I_M_Locator.class, "Z", null);
    /** Column name Z */
    public static final String COLUMNNAME_Z = "Z";
}
