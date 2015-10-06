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
package de.metas.async.model;


/** Generated Interface for C_Queue_Element
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Queue_Element 
{

    /** TableName=C_Queue_Element */
    public static final String Table_Name = "C_Queue_Element";

    /** AD_Table_ID=540426 */
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
    public static final org.adempiere.model.ModelColumn<I_C_Queue_Element, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_Queue_Element, org.compiere.model.I_AD_Client>(I_C_Queue_Element.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_Queue_Element, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_Queue_Element, org.compiere.model.I_AD_Org>(I_C_Queue_Element.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Table_ID();

	public org.compiere.model.I_AD_Table getAD_Table();

	public void setAD_Table(org.compiere.model.I_AD_Table AD_Table);

    /** Column definition for AD_Table_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Queue_Element, org.compiere.model.I_AD_Table> COLUMN_AD_Table_ID = new org.adempiere.model.ModelColumn<I_C_Queue_Element, org.compiere.model.I_AD_Table>(I_C_Queue_Element.class, "AD_Table_ID", org.compiere.model.I_AD_Table.class);
    /** Column name AD_Table_ID */
    public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * Set Queue Block.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Queue_Block_ID (int C_Queue_Block_ID);

	/**
	 * Get Queue Block.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Queue_Block_ID();

	public de.metas.async.model.I_C_Queue_Block getC_Queue_Block();

	public void setC_Queue_Block(de.metas.async.model.I_C_Queue_Block C_Queue_Block);

    /** Column definition for C_Queue_Block_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Queue_Element, de.metas.async.model.I_C_Queue_Block> COLUMN_C_Queue_Block_ID = new org.adempiere.model.ModelColumn<I_C_Queue_Element, de.metas.async.model.I_C_Queue_Block>(I_C_Queue_Element.class, "C_Queue_Block_ID", de.metas.async.model.I_C_Queue_Block.class);
    /** Column name C_Queue_Block_ID */
    public static final String COLUMNNAME_C_Queue_Block_ID = "C_Queue_Block_ID";

	/**
	 * Set Element Queue.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Queue_Element_ID (int C_Queue_Element_ID);

	/**
	 * Get Element Queue.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Queue_Element_ID();

    /** Column definition for C_Queue_Element_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Queue_Element, Object> COLUMN_C_Queue_Element_ID = new org.adempiere.model.ModelColumn<I_C_Queue_Element, Object>(I_C_Queue_Element.class, "C_Queue_Element_ID", null);
    /** Column name C_Queue_Element_ID */
    public static final String COLUMNNAME_C_Queue_Element_ID = "C_Queue_Element_ID";

	/**
	 * Set WorkPackage Queue.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Queue_WorkPackage_ID (int C_Queue_WorkPackage_ID);

	/**
	 * Get WorkPackage Queue.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Queue_WorkPackage_ID();

	public de.metas.async.model.I_C_Queue_WorkPackage getC_Queue_WorkPackage();

	public void setC_Queue_WorkPackage(de.metas.async.model.I_C_Queue_WorkPackage C_Queue_WorkPackage);

    /** Column definition for C_Queue_WorkPackage_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Queue_Element, de.metas.async.model.I_C_Queue_WorkPackage> COLUMN_C_Queue_WorkPackage_ID = new org.adempiere.model.ModelColumn<I_C_Queue_Element, de.metas.async.model.I_C_Queue_WorkPackage>(I_C_Queue_Element.class, "C_Queue_WorkPackage_ID", de.metas.async.model.I_C_Queue_WorkPackage.class);
    /** Column name C_Queue_WorkPackage_ID */
    public static final String COLUMNNAME_C_Queue_WorkPackage_ID = "C_Queue_WorkPackage_ID";

	/**
	 * Set Ältestes nicht verarb. Vorgänger-Paket.
	 * Arbeitspaket mit einem Element, das den selben Datensatz referenziert, noch nicht verarbeitet wurde und somit die Verarbeitung dieses Elements verhindert
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public void setC_Queue_Workpackage_Preceeding_ID (int C_Queue_Workpackage_Preceeding_ID);

	/**
	 * Get Ältestes nicht verarb. Vorgänger-Paket.
	 * Arbeitspaket mit einem Element, das den selben Datensatz referenziert, noch nicht verarbeitet wurde und somit die Verarbeitung dieses Elements verhindert
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public int getC_Queue_Workpackage_Preceeding_ID();

	public de.metas.async.model.I_C_Queue_WorkPackage getC_Queue_Workpackage_Preceeding();

	public void setC_Queue_Workpackage_Preceeding(de.metas.async.model.I_C_Queue_WorkPackage C_Queue_Workpackage_Preceeding);

    /** Column definition for C_Queue_Workpackage_Preceeding_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Queue_Element, de.metas.async.model.I_C_Queue_WorkPackage> COLUMN_C_Queue_Workpackage_Preceeding_ID = new org.adempiere.model.ModelColumn<I_C_Queue_Element, de.metas.async.model.I_C_Queue_WorkPackage>(I_C_Queue_Element.class, "C_Queue_Workpackage_Preceeding_ID", de.metas.async.model.I_C_Queue_WorkPackage.class);
    /** Column name C_Queue_Workpackage_Preceeding_ID */
    public static final String COLUMNNAME_C_Queue_Workpackage_Preceeding_ID = "C_Queue_Workpackage_Preceeding_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_Queue_Element, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_Queue_Element, Object>(I_C_Queue_Element.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Queue_Element, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_Queue_Element, org.compiere.model.I_AD_User>(I_C_Queue_Element.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_Queue_Element, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_Queue_Element, Object>(I_C_Queue_Element.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Datensatz-ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setRecord_ID (int Record_ID);

	/**
	 * Get Datensatz-ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getRecord_ID();

    /** Column definition for Record_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Queue_Element, Object> COLUMN_Record_ID = new org.adempiere.model.ModelColumn<I_C_Queue_Element, Object>(I_C_Queue_Element.class, "Record_ID", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Queue_Element, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_Queue_Element, Object>(I_C_Queue_Element.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Queue_Element, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_Queue_Element, org.compiere.model.I_AD_User>(I_C_Queue_Element.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
