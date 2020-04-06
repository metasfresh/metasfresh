package de.metas.async.model;


/** Generated Interface for C_Queue_Processor
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Queue_Processor 
{

    /** TableName=C_Queue_Processor */
    public static final String Table_Name = "C_Queue_Processor";

    /** AD_Table_ID=540486 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 6 - System - Client
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(6);

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
	 * Set Queue Processor Definition.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Queue_Processor_ID (int C_Queue_Processor_ID);

	/**
	 * Get Queue Processor Definition.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Queue_Processor_ID();

    /** Column definition for C_Queue_Processor_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Queue_Processor, Object> COLUMN_C_Queue_Processor_ID = new org.adempiere.model.ModelColumn<I_C_Queue_Processor, Object>(I_C_Queue_Processor.class, "C_Queue_Processor_ID", null);
    /** Column name C_Queue_Processor_ID */
    public static final String COLUMNNAME_C_Queue_Processor_ID = "C_Queue_Processor_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_Queue_Processor, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_Queue_Processor, Object>(I_C_Queue_Processor.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Queue_Processor, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_Queue_Processor, Object>(I_C_Queue_Processor.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Keep Alive Time (millis).
	 * When the number of threads is greater than the core, this is the maximum time that excess idle threads will wait for new tasks before terminating.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setKeepAliveTimeMillis (int KeepAliveTimeMillis);

	/**
	 * Get Keep Alive Time (millis).
	 * When the number of threads is greater than the core, this is the maximum time that excess idle threads will wait for new tasks before terminating.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getKeepAliveTimeMillis();

    /** Column definition for KeepAliveTimeMillis */
    public static final org.adempiere.model.ModelColumn<I_C_Queue_Processor, Object> COLUMN_KeepAliveTimeMillis = new org.adempiere.model.ModelColumn<I_C_Queue_Processor, Object>(I_C_Queue_Processor.class, "KeepAliveTimeMillis", null);
    /** Column name KeepAliveTimeMillis */
    public static final String COLUMNNAME_KeepAliveTimeMillis = "KeepAliveTimeMillis";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setName (java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getName();

    /** Column definition for Name */
    public static final org.adempiere.model.ModelColumn<I_C_Queue_Processor, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_C_Queue_Processor, Object>(I_C_Queue_Processor.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Pool Size.
	 * The number of threads to keep in the pool, even if they are idle
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPoolSize (int PoolSize);

	/**
	 * Get Pool Size.
	 * The number of threads to keep in the pool, even if they are idle
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getPoolSize();

    /** Column definition for PoolSize */
    public static final org.adempiere.model.ModelColumn<I_C_Queue_Processor, Object> COLUMN_PoolSize = new org.adempiere.model.ModelColumn<I_C_Queue_Processor, Object>(I_C_Queue_Processor.class, "PoolSize", null);
    /** Column name PoolSize */
    public static final String COLUMNNAME_PoolSize = "PoolSize";

	/**
	 * Set Priority.
	 * Indicates if this request is of a high, medium or low priority.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPriority (java.lang.String Priority);

	/**
	 * Get Priority.
	 * Indicates if this request is of a high, medium or low priority.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPriority();

    /** Column definition for Priority */
    public static final org.adempiere.model.ModelColumn<I_C_Queue_Processor, Object> COLUMN_Priority = new org.adempiere.model.ModelColumn<I_C_Queue_Processor, Object>(I_C_Queue_Processor.class, "Priority", null);
    /** Column name Priority */
    public static final String COLUMNNAME_Priority = "Priority";

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
    public static final org.adempiere.model.ModelColumn<I_C_Queue_Processor, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_Queue_Processor, Object>(I_C_Queue_Processor.class, "Updated", null);
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
