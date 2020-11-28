package de.metas.async.model;


/** Generated Interface for C_Queue_Block
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Queue_Block 
{

    /** TableName=C_Queue_Block */
    public static final String Table_Name = "C_Queue_Block";

    /** AD_Table_ID=540422 */
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
	 * Set Erstellt durch Prozess-Instanz.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_PInstance_Creator_ID (int AD_PInstance_Creator_ID);

	/**
	 * Get Erstellt durch Prozess-Instanz.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_PInstance_Creator_ID();

	public org.compiere.model.I_AD_PInstance getAD_PInstance_Creator();

	public void setAD_PInstance_Creator(org.compiere.model.I_AD_PInstance AD_PInstance_Creator);

    /** Column definition for AD_PInstance_Creator_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Queue_Block, org.compiere.model.I_AD_PInstance> COLUMN_AD_PInstance_Creator_ID = new org.adempiere.model.ModelColumn<I_C_Queue_Block, org.compiere.model.I_AD_PInstance>(I_C_Queue_Block.class, "AD_PInstance_Creator_ID", org.compiere.model.I_AD_PInstance.class);
    /** Column name AD_PInstance_Creator_ID */
    public static final String COLUMNNAME_AD_PInstance_Creator_ID = "AD_PInstance_Creator_ID";

	/**
	 * Set Queue Block.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Queue_Block_ID (int C_Queue_Block_ID);

	/**
	 * Get Queue Block.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Queue_Block_ID();

    /** Column definition for C_Queue_Block_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Queue_Block, Object> COLUMN_C_Queue_Block_ID = new org.adempiere.model.ModelColumn<I_C_Queue_Block, Object>(I_C_Queue_Block.class, "C_Queue_Block_ID", null);
    /** Column name C_Queue_Block_ID */
    public static final String COLUMNNAME_C_Queue_Block_ID = "C_Queue_Block_ID";

	/**
	 * Set WorkPackage Processor.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Queue_PackageProcessor_ID (int C_Queue_PackageProcessor_ID);

	/**
	 * Get WorkPackage Processor.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Queue_PackageProcessor_ID();

	public de.metas.async.model.I_C_Queue_PackageProcessor getC_Queue_PackageProcessor();

	public void setC_Queue_PackageProcessor(de.metas.async.model.I_C_Queue_PackageProcessor C_Queue_PackageProcessor);

    /** Column definition for C_Queue_PackageProcessor_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Queue_Block, de.metas.async.model.I_C_Queue_PackageProcessor> COLUMN_C_Queue_PackageProcessor_ID = new org.adempiere.model.ModelColumn<I_C_Queue_Block, de.metas.async.model.I_C_Queue_PackageProcessor>(I_C_Queue_Block.class, "C_Queue_PackageProcessor_ID", de.metas.async.model.I_C_Queue_PackageProcessor.class);
    /** Column name C_Queue_PackageProcessor_ID */
    public static final String COLUMNNAME_C_Queue_PackageProcessor_ID = "C_Queue_PackageProcessor_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_Queue_Block, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_Queue_Block, Object>(I_C_Queue_Block.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Queue_Block, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_Queue_Block, Object>(I_C_Queue_Block.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

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
    public static final org.adempiere.model.ModelColumn<I_C_Queue_Block, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_Queue_Block, Object>(I_C_Queue_Block.class, "Updated", null);
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
