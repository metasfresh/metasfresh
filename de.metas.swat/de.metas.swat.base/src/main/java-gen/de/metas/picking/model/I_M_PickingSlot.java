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
package de.metas.picking.model;


/** Generated Interface for M_PickingSlot
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_PickingSlot 
{

    /** TableName=M_PickingSlot */
    public static final String Table_Name = "M_PickingSlot";

    /** AD_Table_ID=540543 */
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
    public static final org.adempiere.model.ModelColumn<I_M_PickingSlot, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_PickingSlot, org.compiere.model.I_AD_Client>(I_M_PickingSlot.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_PickingSlot, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_PickingSlot, org.compiere.model.I_AD_Org>(I_M_PickingSlot.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Geschäftspartner.
	  * Bezeichnet einen Geschäftspartner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/** Get Geschäftspartner.
	  * Bezeichnet einen Geschäftspartner
	  */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException;

	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner);

    /** Column definition for C_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_M_PickingSlot, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_M_PickingSlot, org.compiere.model.I_C_BPartner>(I_M_PickingSlot.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/** Set Standort.
	  * Identifiziert die (Liefer-) Adresse des Geschäftspartners
	  */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/** Get Standort.
	  * Identifiziert die (Liefer-) Adresse des Geschäftspartners
	  */
	public int getC_BPartner_Location_ID();

	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException;

	public void setC_BPartner_Location(org.compiere.model.I_C_BPartner_Location C_BPartner_Location);

    /** Column definition for C_BPartner_Location_ID */
    public static final org.adempiere.model.ModelColumn<I_M_PickingSlot, org.compiere.model.I_C_BPartner_Location> COLUMN_C_BPartner_Location_ID = new org.adempiere.model.ModelColumn<I_M_PickingSlot, org.compiere.model.I_C_BPartner_Location>(I_M_PickingSlot.class, "C_BPartner_Location_ID", org.compiere.model.I_C_BPartner_Location.class);
    /** Column name C_BPartner_Location_ID */
    public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/** Get Erstellt.
	  * Datum, an dem dieser Eintrag erstellt wurde
	  */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_M_PickingSlot, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_PickingSlot, Object>(I_M_PickingSlot.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Erstellt durch.
	  * Nutzer, der diesen Eintrag erstellt hat
	  */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_M_PickingSlot, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_PickingSlot, org.compiere.model.I_AD_User>(I_M_PickingSlot.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_PickingSlot, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_PickingSlot, Object>(I_M_PickingSlot.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Is Dynamic	  */
	public void setIsDynamic (boolean IsDynamic);

	/** Get Is Dynamic	  */
	public boolean isDynamic();

    /** Column definition for IsDynamic */
    public static final org.adempiere.model.ModelColumn<I_M_PickingSlot, Object> COLUMN_IsDynamic = new org.adempiere.model.ModelColumn<I_M_PickingSlot, Object>(I_M_PickingSlot.class, "IsDynamic", null);
    /** Column name IsDynamic */
    public static final String COLUMNNAME_IsDynamic = "IsDynamic";

	/** Set Picking Slot	  */
	public void setM_PickingSlot_ID (int M_PickingSlot_ID);

	/** Get Picking Slot	  */
	public int getM_PickingSlot_ID();

    /** Column definition for M_PickingSlot_ID */
    public static final org.adempiere.model.ModelColumn<I_M_PickingSlot, Object> COLUMN_M_PickingSlot_ID = new org.adempiere.model.ModelColumn<I_M_PickingSlot, Object>(I_M_PickingSlot.class, "M_PickingSlot_ID", null);
    /** Column name M_PickingSlot_ID */
    public static final String COLUMNNAME_M_PickingSlot_ID = "M_PickingSlot_ID";

	/** Set Lager.
	  * Lager oder Ort für Dienstleistung
	  */
	public void setM_Warehouse_ID (int M_Warehouse_ID);

	/** Get Lager.
	  * Lager oder Ort für Dienstleistung
	  */
	public int getM_Warehouse_ID();

	public org.compiere.model.I_M_Warehouse getM_Warehouse() throws RuntimeException;

	public void setM_Warehouse(org.compiere.model.I_M_Warehouse M_Warehouse);

    /** Column definition for M_Warehouse_ID */
    public static final org.adempiere.model.ModelColumn<I_M_PickingSlot, org.compiere.model.I_M_Warehouse> COLUMN_M_Warehouse_ID = new org.adempiere.model.ModelColumn<I_M_PickingSlot, org.compiere.model.I_M_Warehouse>(I_M_PickingSlot.class, "M_Warehouse_ID", org.compiere.model.I_M_Warehouse.class);
    /** Column name M_Warehouse_ID */
    public static final String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/** Set PickingSlot	  */
	public void setPickingSlot (java.lang.String PickingSlot);

	/** Get PickingSlot	  */
	public java.lang.String getPickingSlot();

    /** Column definition for PickingSlot */
    public static final org.adempiere.model.ModelColumn<I_M_PickingSlot, Object> COLUMN_PickingSlot = new org.adempiere.model.ModelColumn<I_M_PickingSlot, Object>(I_M_PickingSlot.class, "PickingSlot", null);
    /** Column name PickingSlot */
    public static final String COLUMNNAME_PickingSlot = "PickingSlot";

	/** Get Aktualisiert.
	  * Datum, an dem dieser Eintrag aktualisiert wurde
	  */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_M_PickingSlot, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_PickingSlot, Object>(I_M_PickingSlot.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Aktualisiert durch.
	  * Nutzer, der diesen Eintrag aktualisiert hat
	  */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_M_PickingSlot, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_PickingSlot, org.compiere.model.I_AD_User>(I_M_PickingSlot.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
