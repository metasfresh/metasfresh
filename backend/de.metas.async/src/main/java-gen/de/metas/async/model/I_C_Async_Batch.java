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


/** Generated Interface for C_Async_Batch
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Async_Batch 
{

    /** TableName=C_Async_Batch */
    public static final String Table_Name = "C_Async_Batch";

    /** AD_Table_ID=540620 */
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
    public static final org.adempiere.model.ModelColumn<I_C_Async_Batch, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_Async_Batch, org.compiere.model.I_AD_Client>(I_C_Async_Batch.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_Async_Batch, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_Async_Batch, org.compiere.model.I_AD_Org>(I_C_Async_Batch.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Prozess-Instanz.
	 * Instanz eines Prozesses
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_PInstance_ID (int AD_PInstance_ID);

	/**
	 * Get Prozess-Instanz.
	 * Instanz eines Prozesses
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_PInstance_ID();

	public org.compiere.model.I_AD_PInstance getAD_PInstance();

	public void setAD_PInstance(org.compiere.model.I_AD_PInstance AD_PInstance);

    /** Column definition for AD_PInstance_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Async_Batch, org.compiere.model.I_AD_PInstance> COLUMN_AD_PInstance_ID = new org.adempiere.model.ModelColumn<I_C_Async_Batch, org.compiere.model.I_AD_PInstance>(I_C_Async_Batch.class, "AD_PInstance_ID", org.compiere.model.I_AD_PInstance.class);
    /** Column name AD_PInstance_ID */
    public static final String COLUMNNAME_AD_PInstance_ID = "AD_PInstance_ID";

	/**
	 * Set Async Batch.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Async_Batch_ID (int C_Async_Batch_ID);

	/**
	 * Get Async Batch.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Async_Batch_ID();

    /** Column definition for C_Async_Batch_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Async_Batch, Object> COLUMN_C_Async_Batch_ID = new org.adempiere.model.ModelColumn<I_C_Async_Batch, Object>(I_C_Async_Batch.class, "C_Async_Batch_ID", null);
    /** Column name C_Async_Batch_ID */
    public static final String COLUMNNAME_C_Async_Batch_ID = "C_Async_Batch_ID";

	/**
	 * Set Async Batch Type.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Async_Batch_Type_ID (int C_Async_Batch_Type_ID);

	/**
	 * Get Async Batch Type.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Async_Batch_Type_ID();

	public de.metas.async.model.I_C_Async_Batch_Type getC_Async_Batch_Type();

	public void setC_Async_Batch_Type(de.metas.async.model.I_C_Async_Batch_Type C_Async_Batch_Type);

    /** Column definition for C_Async_Batch_Type_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Async_Batch, de.metas.async.model.I_C_Async_Batch_Type> COLUMN_C_Async_Batch_Type_ID = new org.adempiere.model.ModelColumn<I_C_Async_Batch, de.metas.async.model.I_C_Async_Batch_Type>(I_C_Async_Batch.class, "C_Async_Batch_Type_ID", de.metas.async.model.I_C_Async_Batch_Type.class);
    /** Column name C_Async_Batch_Type_ID */
    public static final String COLUMNNAME_C_Async_Batch_Type_ID = "C_Async_Batch_Type_ID";

	/**
	 * Set Enqueued.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCountEnqueued (int CountEnqueued);

	/**
	 * Get Enqueued.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getCountEnqueued();

    /** Column definition for CountEnqueued */
    public static final org.adempiere.model.ModelColumn<I_C_Async_Batch, Object> COLUMN_CountEnqueued = new org.adempiere.model.ModelColumn<I_C_Async_Batch, Object>(I_C_Async_Batch.class, "CountEnqueued", null);
    /** Column name CountEnqueued */
    public static final String COLUMNNAME_CountEnqueued = "CountEnqueued";

	/**
	 * Set Expected.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCountExpected (int CountExpected);

	/**
	 * Get Expected.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getCountExpected();

    /** Column definition for CountExpected */
    public static final org.adempiere.model.ModelColumn<I_C_Async_Batch, Object> COLUMN_CountExpected = new org.adempiere.model.ModelColumn<I_C_Async_Batch, Object>(I_C_Async_Batch.class, "CountExpected", null);
    /** Column name CountExpected */
    public static final String COLUMNNAME_CountExpected = "CountExpected";

	/**
	 * Set Verarbeitet.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCountProcessed (int CountProcessed);

	/**
	 * Get Verarbeitet.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getCountProcessed();

    /** Column definition for CountProcessed */
    public static final org.adempiere.model.ModelColumn<I_C_Async_Batch, Object> COLUMN_CountProcessed = new org.adempiere.model.ModelColumn<I_C_Async_Batch, Object>(I_C_Async_Batch.class, "CountProcessed", null);
    /** Column name CountProcessed */
    public static final String COLUMNNAME_CountProcessed = "CountProcessed";

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
    public static final org.adempiere.model.ModelColumn<I_C_Async_Batch, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_Async_Batch, Object>(I_C_Async_Batch.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * Nutzer, der diesen Eintrag erstellt hat
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_C_Async_Batch, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_Async_Batch, org.compiere.model.I_AD_User>(I_C_Async_Batch.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

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
    public static final org.adempiere.model.ModelColumn<I_C_Async_Batch, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_C_Async_Batch, Object>(I_C_Async_Batch.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set First Enqueued.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setFirstEnqueued (java.sql.Timestamp FirstEnqueued);

	/**
	 * Get First Enqueued.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getFirstEnqueued();

    /** Column definition for FirstEnqueued */
    public static final org.adempiere.model.ModelColumn<I_C_Async_Batch, Object> COLUMN_FirstEnqueued = new org.adempiere.model.ModelColumn<I_C_Async_Batch, Object>(I_C_Async_Batch.class, "FirstEnqueued", null);
    /** Column name FirstEnqueued */
    public static final String COLUMNNAME_FirstEnqueued = "FirstEnqueued";

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
    public static final org.adempiere.model.ModelColumn<I_C_Async_Batch, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_Async_Batch, Object>(I_C_Async_Batch.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Last Enqueued.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLastEnqueued (java.sql.Timestamp LastEnqueued);

	/**
	 * Get Last Enqueued.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getLastEnqueued();

    /** Column definition for LastEnqueued */
    public static final org.adempiere.model.ModelColumn<I_C_Async_Batch, Object> COLUMN_LastEnqueued = new org.adempiere.model.ModelColumn<I_C_Async_Batch, Object>(I_C_Async_Batch.class, "LastEnqueued", null);
    /** Column name LastEnqueued */
    public static final String COLUMNNAME_LastEnqueued = "LastEnqueued";

	/**
	 * Set Last Processed.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLastProcessed (java.sql.Timestamp LastProcessed);

	/**
	 * Get Last Processed.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getLastProcessed();

    /** Column definition for LastProcessed */
    public static final org.adempiere.model.ModelColumn<I_C_Async_Batch, Object> COLUMN_LastProcessed = new org.adempiere.model.ModelColumn<I_C_Async_Batch, Object>(I_C_Async_Batch.class, "LastProcessed", null);
    /** Column name LastProcessed */
    public static final String COLUMNNAME_LastProcessed = "LastProcessed";

	/**
	 * Set LastProcessed WorkPackage.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLastProcessed_WorkPackage_ID (int LastProcessed_WorkPackage_ID);

	/**
	 * Get LastProcessed WorkPackage.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getLastProcessed_WorkPackage_ID();

	public de.metas.async.model.I_C_Queue_WorkPackage getLastProcessed_WorkPackage();

	public void setLastProcessed_WorkPackage(de.metas.async.model.I_C_Queue_WorkPackage LastProcessed_WorkPackage);

    /** Column definition for LastProcessed_WorkPackage_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Async_Batch, de.metas.async.model.I_C_Queue_WorkPackage> COLUMN_LastProcessed_WorkPackage_ID = new org.adempiere.model.ModelColumn<I_C_Async_Batch, de.metas.async.model.I_C_Queue_WorkPackage>(I_C_Async_Batch.class, "LastProcessed_WorkPackage_ID", de.metas.async.model.I_C_Queue_WorkPackage.class);
    /** Column name LastProcessed_WorkPackage_ID */
    public static final String COLUMNNAME_LastProcessed_WorkPackage_ID = "LastProcessed_WorkPackage_ID";

	/**
	 * Set Name.
	 * Alphanumeric identifier of the entity
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setName (java.lang.String Name);

	/**
	 * Get Name.
	 * Alphanumeric identifier of the entity
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getName();

    /** Column definition for Name */
    public static final org.adempiere.model.ModelColumn<I_C_Async_Batch, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_C_Async_Batch, Object>(I_C_Async_Batch.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Parent_Async_Batch_ID.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setParent_Async_Batch_ID (int Parent_Async_Batch_ID);

	/**
	 * Get Parent_Async_Batch_ID.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getParent_Async_Batch_ID();

	public de.metas.async.model.I_C_Async_Batch getParent_Async_Batch();

	public void setParent_Async_Batch(de.metas.async.model.I_C_Async_Batch Parent_Async_Batch);

    /** Column definition for Parent_Async_Batch_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Async_Batch, de.metas.async.model.I_C_Async_Batch> COLUMN_Parent_Async_Batch_ID = new org.adempiere.model.ModelColumn<I_C_Async_Batch, de.metas.async.model.I_C_Async_Batch>(I_C_Async_Batch.class, "Parent_Async_Batch_ID", de.metas.async.model.I_C_Async_Batch.class);
    /** Column name Parent_Async_Batch_ID */
    public static final String COLUMNNAME_Parent_Async_Batch_ID = "Parent_Async_Batch_ID";

	/**
	 * Set Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProcessed (boolean Processed);

	/**
	 * Get Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isProcessed();

    /** Column definition for Processed */
    public static final org.adempiere.model.ModelColumn<I_C_Async_Batch, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_C_Async_Batch, Object>(I_C_Async_Batch.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

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
    public static final org.adempiere.model.ModelColumn<I_C_Async_Batch, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_Async_Batch, Object>(I_C_Async_Batch.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Async_Batch, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_Async_Batch, org.compiere.model.I_AD_User>(I_C_Async_Batch.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
