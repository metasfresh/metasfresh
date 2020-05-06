package de.metas.async.model;


/** Generated Interface for C_Queue_WorkPackage_Notified
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Queue_WorkPackage_Notified 
{

    /** TableName=C_Queue_WorkPackage_Notified */
    public static final String Table_Name = "C_Queue_WorkPackage_Notified";

    /** AD_Table_ID=540749 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 7 - System - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(7);

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

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Organisation.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Bach Workpackage SeqNo.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBachWorkpackageSeqNo (int BachWorkpackageSeqNo);

	/**
	 * Get Bach Workpackage SeqNo.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getBachWorkpackageSeqNo();

    /** Column definition for BachWorkpackageSeqNo */
    public static final org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage_Notified, Object> COLUMN_BachWorkpackageSeqNo = new org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage_Notified, Object>(I_C_Queue_WorkPackage_Notified.class, "BachWorkpackageSeqNo", null);
    /** Column name BachWorkpackageSeqNo */
    public static final String COLUMNNAME_BachWorkpackageSeqNo = "BachWorkpackageSeqNo";

	/**
	 * Set Async Batch.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Async_Batch_ID (int C_Async_Batch_ID);

	/**
	 * Get Async Batch.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Async_Batch_ID();

	public de.metas.async.model.I_C_Async_Batch getC_Async_Batch();

	public void setC_Async_Batch(de.metas.async.model.I_C_Async_Batch C_Async_Batch);

    /** Column definition for C_Async_Batch_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage_Notified, de.metas.async.model.I_C_Async_Batch> COLUMN_C_Async_Batch_ID = new org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage_Notified, de.metas.async.model.I_C_Async_Batch>(I_C_Queue_WorkPackage_Notified.class, "C_Async_Batch_ID", de.metas.async.model.I_C_Async_Batch.class);
    /** Column name C_Async_Batch_ID */
    public static final String COLUMNNAME_C_Async_Batch_ID = "C_Async_Batch_ID";

	/**
	 * Set WorkPackage Queue.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Queue_WorkPackage_ID (int C_Queue_WorkPackage_ID);

	/**
	 * Get WorkPackage Queue.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Queue_WorkPackage_ID();

	public de.metas.async.model.I_C_Queue_WorkPackage getC_Queue_WorkPackage();

	public void setC_Queue_WorkPackage(de.metas.async.model.I_C_Queue_WorkPackage C_Queue_WorkPackage);

    /** Column definition for C_Queue_WorkPackage_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage_Notified, de.metas.async.model.I_C_Queue_WorkPackage> COLUMN_C_Queue_WorkPackage_ID = new org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage_Notified, de.metas.async.model.I_C_Queue_WorkPackage>(I_C_Queue_WorkPackage_Notified.class, "C_Queue_WorkPackage_ID", de.metas.async.model.I_C_Queue_WorkPackage.class);
    /** Column name C_Queue_WorkPackage_ID */
    public static final String COLUMNNAME_C_Queue_WorkPackage_ID = "C_Queue_WorkPackage_ID";

	/**
	 * Set WorkPackage Notified.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Queue_WorkPackage_Notified_ID (int C_Queue_WorkPackage_Notified_ID);

	/**
	 * Get WorkPackage Notified.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Queue_WorkPackage_Notified_ID();

    /** Column definition for C_Queue_WorkPackage_Notified_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage_Notified, Object> COLUMN_C_Queue_WorkPackage_Notified_ID = new org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage_Notified, Object>(I_C_Queue_WorkPackage_Notified.class, "C_Queue_WorkPackage_Notified_ID", null);
    /** Column name C_Queue_WorkPackage_Notified_ID */
    public static final String COLUMNNAME_C_Queue_WorkPackage_Notified_ID = "C_Queue_WorkPackage_Notified_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage_Notified, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage_Notified, Object>(I_C_Queue_WorkPackage_Notified.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage_Notified, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage_Notified, Object>(I_C_Queue_WorkPackage_Notified.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Notified.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsNotified (boolean IsNotified);

	/**
	 * Get Notified.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isNotified();

    /** Column definition for IsNotified */
    public static final org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage_Notified, Object> COLUMN_IsNotified = new org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage_Notified, Object>(I_C_Queue_WorkPackage_Notified.class, "IsNotified", null);
    /** Column name IsNotified */
    public static final String COLUMNNAME_IsNotified = "IsNotified";

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
    public static final org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage_Notified, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage_Notified, Object>(I_C_Queue_WorkPackage_Notified.class, "Updated", null);
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

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
