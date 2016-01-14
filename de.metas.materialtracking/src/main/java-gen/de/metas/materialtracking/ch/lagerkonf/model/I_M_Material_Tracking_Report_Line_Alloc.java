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


/** Generated Interface for M_Material_Tracking_Report_Line_Alloc
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_Material_Tracking_Report_Line_Alloc 
{

    /** TableName=M_Material_Tracking_Report_Line_Alloc */
    public static final String Table_Name = "M_Material_Tracking_Report_Line_Alloc";

    /** AD_Table_ID=540694 */
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
    public static final org.adempiere.model.ModelColumn<I_M_Material_Tracking_Report_Line_Alloc, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_Material_Tracking_Report_Line_Alloc, org.compiere.model.I_AD_Client>(I_M_Material_Tracking_Report_Line_Alloc.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_Material_Tracking_Report_Line_Alloc, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_Material_Tracking_Report_Line_Alloc, org.compiere.model.I_AD_Org>(I_M_Material_Tracking_Report_Line_Alloc.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Table_ID();

	public org.compiere.model.I_AD_Table getAD_Table();

	public void setAD_Table(org.compiere.model.I_AD_Table AD_Table);

    /** Column definition for AD_Table_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Material_Tracking_Report_Line_Alloc, org.compiere.model.I_AD_Table> COLUMN_AD_Table_ID = new org.adempiere.model.ModelColumn<I_M_Material_Tracking_Report_Line_Alloc, org.compiere.model.I_AD_Table>(I_M_Material_Tracking_Report_Line_Alloc.class, "AD_Table_ID", org.compiere.model.I_AD_Table.class);
    /** Column name AD_Table_ID */
    public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_Material_Tracking_Report_Line_Alloc, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_Material_Tracking_Report_Line_Alloc, Object>(I_M_Material_Tracking_Report_Line_Alloc.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_Material_Tracking_Report_Line_Alloc, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_Material_Tracking_Report_Line_Alloc, org.compiere.model.I_AD_User>(I_M_Material_Tracking_Report_Line_Alloc.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_Material_Tracking_Report_Line_Alloc, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_Material_Tracking_Report_Line_Alloc, Object>(I_M_Material_Tracking_Report_Line_Alloc.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

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
    public static final org.adempiere.model.ModelColumn<I_M_Material_Tracking_Report_Line_Alloc, de.metas.materialtracking.model.I_M_Material_Tracking> COLUMN_M_Material_Tracking_ID = new org.adempiere.model.ModelColumn<I_M_Material_Tracking_Report_Line_Alloc, de.metas.materialtracking.model.I_M_Material_Tracking>(I_M_Material_Tracking_Report_Line_Alloc.class, "M_Material_Tracking_ID", de.metas.materialtracking.model.I_M_Material_Tracking.class);
    /** Column name M_Material_Tracking_ID */
    public static final String COLUMNNAME_M_Material_Tracking_ID = "M_Material_Tracking_ID";

	/**
	 * Set M_Material_Tracking_Report_Line_Alloc.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Material_Tracking_Report_Line_Alloc_ID (int M_Material_Tracking_Report_Line_Alloc_ID);

	/**
	 * Get M_Material_Tracking_Report_Line_Alloc.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Material_Tracking_Report_Line_Alloc_ID();

    /** Column definition for M_Material_Tracking_Report_Line_Alloc_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Material_Tracking_Report_Line_Alloc, Object> COLUMN_M_Material_Tracking_Report_Line_Alloc_ID = new org.adempiere.model.ModelColumn<I_M_Material_Tracking_Report_Line_Alloc, Object>(I_M_Material_Tracking_Report_Line_Alloc.class, "M_Material_Tracking_Report_Line_Alloc_ID", null);
    /** Column name M_Material_Tracking_Report_Line_Alloc_ID */
    public static final String COLUMNNAME_M_Material_Tracking_Report_Line_Alloc_ID = "M_Material_Tracking_Report_Line_Alloc_ID";

	/**
	 * Set M_Material_Tracking_Report_Line.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Material_Tracking_Report_Line_ID (int M_Material_Tracking_Report_Line_ID);

	/**
	 * Get M_Material_Tracking_Report_Line.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Material_Tracking_Report_Line_ID();

	public de.metas.materialtracking.ch.lagerkonf.model.I_M_Material_Tracking_Report_Line getM_Material_Tracking_Report_Line();

	public void setM_Material_Tracking_Report_Line(de.metas.materialtracking.ch.lagerkonf.model.I_M_Material_Tracking_Report_Line M_Material_Tracking_Report_Line);

    /** Column definition for M_Material_Tracking_Report_Line_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Material_Tracking_Report_Line_Alloc, de.metas.materialtracking.ch.lagerkonf.model.I_M_Material_Tracking_Report_Line> COLUMN_M_Material_Tracking_Report_Line_ID = new org.adempiere.model.ModelColumn<I_M_Material_Tracking_Report_Line_Alloc, de.metas.materialtracking.ch.lagerkonf.model.I_M_Material_Tracking_Report_Line>(I_M_Material_Tracking_Report_Line_Alloc.class, "M_Material_Tracking_Report_Line_ID", de.metas.materialtracking.ch.lagerkonf.model.I_M_Material_Tracking_Report_Line.class);
    /** Column name M_Material_Tracking_Report_Line_ID */
    public static final String COLUMNNAME_M_Material_Tracking_Report_Line_ID = "M_Material_Tracking_Report_Line_ID";

	/**
	 * Set Datensatz-ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRecord_ID (int Record_ID);

	/**
	 * Get Datensatz-ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getRecord_ID();

    /** Column definition for Record_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Material_Tracking_Report_Line_Alloc, Object> COLUMN_Record_ID = new org.adempiere.model.ModelColumn<I_M_Material_Tracking_Report_Line_Alloc, Object>(I_M_Material_Tracking_Report_Line_Alloc.class, "Record_ID", null);
    /** Column name Record_ID */
    public static final String COLUMNNAME_Record_ID = "Record_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_Material_Tracking_Report_Line_Alloc, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_Material_Tracking_Report_Line_Alloc, Object>(I_M_Material_Tracking_Report_Line_Alloc.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_Material_Tracking_Report_Line_Alloc, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_Material_Tracking_Report_Line_Alloc, org.compiere.model.I_AD_User>(I_M_Material_Tracking_Report_Line_Alloc.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
