package de.metas.async.model;


/** Generated Interface for RV_Async_batch_statistics
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_RV_Async_batch_statistics 
{

    /** TableName=RV_Async_batch_statistics */
    public static final String Table_Name = "RV_Async_batch_statistics";

    /** AD_Table_ID=540621 */
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
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Table_ID();

    /** Column name AD_Table_ID */
    public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * Set Async Batch.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Async_Batch_ID (int C_Async_Batch_ID);

	/**
	 * Get Async Batch.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Async_Batch_ID();

	public de.metas.async.model.I_C_Async_Batch getC_Async_Batch();

	public void setC_Async_Batch(de.metas.async.model.I_C_Async_Batch C_Async_Batch);

    /** Column definition for C_Async_Batch_ID */
    public static final org.adempiere.model.ModelColumn<I_RV_Async_batch_statistics, de.metas.async.model.I_C_Async_Batch> COLUMN_C_Async_Batch_ID = new org.adempiere.model.ModelColumn<I_RV_Async_batch_statistics, de.metas.async.model.I_C_Async_Batch>(I_RV_Async_batch_statistics.class, "C_Async_Batch_ID", de.metas.async.model.I_C_Async_Batch.class);
    /** Column name C_Async_Batch_ID */
    public static final String COLUMNNAME_C_Async_Batch_ID = "C_Async_Batch_ID";

	/**
	 * Set WorkPackage Processor.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Queue_PackageProcessor_ID (int C_Queue_PackageProcessor_ID);

	/**
	 * Get WorkPackage Processor.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Queue_PackageProcessor_ID();

	public de.metas.async.model.I_C_Queue_PackageProcessor getC_Queue_PackageProcessor();

	public void setC_Queue_PackageProcessor(de.metas.async.model.I_C_Queue_PackageProcessor C_Queue_PackageProcessor);

    /** Column definition for C_Queue_PackageProcessor_ID */
    public static final org.adempiere.model.ModelColumn<I_RV_Async_batch_statistics, de.metas.async.model.I_C_Queue_PackageProcessor> COLUMN_C_Queue_PackageProcessor_ID = new org.adempiere.model.ModelColumn<I_RV_Async_batch_statistics, de.metas.async.model.I_C_Queue_PackageProcessor>(I_RV_Async_batch_statistics.class, "C_Queue_PackageProcessor_ID", de.metas.async.model.I_C_Queue_PackageProcessor.class);
    /** Column name C_Queue_PackageProcessor_ID */
    public static final String COLUMNNAME_C_Queue_PackageProcessor_ID = "C_Queue_PackageProcessor_ID";

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
    public static final org.adempiere.model.ModelColumn<I_RV_Async_batch_statistics, Object> COLUMN_CountEnqueued = new org.adempiere.model.ModelColumn<I_RV_Async_batch_statistics, Object>(I_RV_Async_batch_statistics.class, "CountEnqueued", null);
    /** Column name CountEnqueued */
    public static final String COLUMNNAME_CountEnqueued = "CountEnqueued";

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
    public static final org.adempiere.model.ModelColumn<I_RV_Async_batch_statistics, Object> COLUMN_CountProcessed = new org.adempiere.model.ModelColumn<I_RV_Async_batch_statistics, Object>(I_RV_Async_batch_statistics.class, "CountProcessed", null);
    /** Column name CountProcessed */
    public static final String COLUMNNAME_CountProcessed = "CountProcessed";

	/**
	 * Get Erstellt.
	 * Datum, an dem dieser Eintrag erstellt wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_RV_Async_batch_statistics, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_RV_Async_batch_statistics, Object>(I_RV_Async_batch_statistics.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * Nutzer, der diesen Eintrag erstellt hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Get Aktualisiert.
	 * Datum, an dem dieser Eintrag aktualisiert wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_RV_Async_batch_statistics, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_RV_Async_batch_statistics, Object>(I_RV_Async_batch_statistics.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * Nutzer, der diesen Eintrag aktualisiert hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
