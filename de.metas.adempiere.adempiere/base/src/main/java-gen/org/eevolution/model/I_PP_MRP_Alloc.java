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


/** Generated Interface for PP_MRP_Alloc
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_PP_MRP_Alloc 
{

    /** TableName=PP_MRP_Alloc */
    public static final String Table_Name = "PP_MRP_Alloc";

    /** AD_Table_ID=540632 */
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
    public static final org.adempiere.model.ModelColumn<I_PP_MRP_Alloc, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_PP_MRP_Alloc, org.compiere.model.I_AD_Client>(I_PP_MRP_Alloc.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_PP_MRP_Alloc, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_PP_MRP_Alloc, org.compiere.model.I_AD_Org>(I_PP_MRP_Alloc.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
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
    public static final org.adempiere.model.ModelColumn<I_PP_MRP_Alloc, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_PP_MRP_Alloc, Object>(I_PP_MRP_Alloc.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_PP_MRP_Alloc, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_PP_MRP_Alloc, org.compiere.model.I_AD_User>(I_PP_MRP_Alloc.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_PP_MRP_Alloc, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_PP_MRP_Alloc, Object>(I_PP_MRP_Alloc.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set MRP Demand.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPP_MRP_Demand_ID (int PP_MRP_Demand_ID);

	/**
	 * Get MRP Demand.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getPP_MRP_Demand_ID();

	public org.eevolution.model.I_PP_MRP getPP_MRP_Demand();

	public void setPP_MRP_Demand(org.eevolution.model.I_PP_MRP PP_MRP_Demand);

    /** Column definition for PP_MRP_Demand_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_MRP_Alloc, org.eevolution.model.I_PP_MRP> COLUMN_PP_MRP_Demand_ID = new org.adempiere.model.ModelColumn<I_PP_MRP_Alloc, org.eevolution.model.I_PP_MRP>(I_PP_MRP_Alloc.class, "PP_MRP_Demand_ID", org.eevolution.model.I_PP_MRP.class);
    /** Column name PP_MRP_Demand_ID */
    public static final String COLUMNNAME_PP_MRP_Demand_ID = "PP_MRP_Demand_ID";

	/**
	 * Set MRP Supply.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPP_MRP_Supply_ID (int PP_MRP_Supply_ID);

	/**
	 * Get MRP Supply.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getPP_MRP_Supply_ID();

	public org.eevolution.model.I_PP_MRP getPP_MRP_Supply();

	public void setPP_MRP_Supply(org.eevolution.model.I_PP_MRP PP_MRP_Supply);

    /** Column definition for PP_MRP_Supply_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_MRP_Alloc, org.eevolution.model.I_PP_MRP> COLUMN_PP_MRP_Supply_ID = new org.adempiere.model.ModelColumn<I_PP_MRP_Alloc, org.eevolution.model.I_PP_MRP>(I_PP_MRP_Alloc.class, "PP_MRP_Supply_ID", org.eevolution.model.I_PP_MRP.class);
    /** Column name PP_MRP_Supply_ID */
    public static final String COLUMNNAME_PP_MRP_Supply_ID = "PP_MRP_Supply_ID";

	/**
	 * Set Zugewiesene Menge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyAllocated (java.math.BigDecimal QtyAllocated);

	/**
	 * Get Zugewiesene Menge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyAllocated();

    /** Column definition for QtyAllocated */
    public static final org.adempiere.model.ModelColumn<I_PP_MRP_Alloc, Object> COLUMN_QtyAllocated = new org.adempiere.model.ModelColumn<I_PP_MRP_Alloc, Object>(I_PP_MRP_Alloc.class, "QtyAllocated", null);
    /** Column name QtyAllocated */
    public static final String COLUMNNAME_QtyAllocated = "QtyAllocated";

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
    public static final org.adempiere.model.ModelColumn<I_PP_MRP_Alloc, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_PP_MRP_Alloc, Object>(I_PP_MRP_Alloc.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_PP_MRP_Alloc, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_PP_MRP_Alloc, org.compiere.model.I_AD_User>(I_PP_MRP_Alloc.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
