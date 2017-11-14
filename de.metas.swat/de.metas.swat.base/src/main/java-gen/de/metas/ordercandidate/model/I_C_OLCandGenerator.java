package de.metas.ordercandidate.model;


/** Generated Interface for C_OLCandGenerator
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_OLCandGenerator 
{

    /** TableName=C_OLCandGenerator */
    public static final String Table_Name = "C_OLCandGenerator";

    /** AD_Table_ID=540247 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 2 - Client
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(2);

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
    public static final org.adempiere.model.ModelColumn<I_C_OLCandGenerator, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_OLCandGenerator, org.compiere.model.I_AD_Client>(I_C_OLCandGenerator.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_OLCandGenerator, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_OLCandGenerator, org.compiere.model.I_AD_Org>(I_C_OLCandGenerator.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Quell-Tabelle.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Table_Source_ID (int AD_Table_Source_ID);

	/**
	 * Get Quell-Tabelle.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Table_Source_ID();

	public org.compiere.model.I_AD_Table getAD_Table_Source();

	public void setAD_Table_Source(org.compiere.model.I_AD_Table AD_Table_Source);

    /** Column definition for AD_Table_Source_ID */
    public static final org.adempiere.model.ModelColumn<I_C_OLCandGenerator, org.compiere.model.I_AD_Table> COLUMN_AD_Table_Source_ID = new org.adempiere.model.ModelColumn<I_C_OLCandGenerator, org.compiere.model.I_AD_Table>(I_C_OLCandGenerator.class, "AD_Table_Source_ID", org.compiere.model.I_AD_Table.class);
    /** Column name AD_Table_Source_ID */
    public static final String COLUMNNAME_AD_Table_Source_ID = "AD_Table_Source_ID";

	/**
	 * Set Auftragskand. Erzeugen.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_OLCandGenerator_ID (int C_OLCandGenerator_ID);

	/**
	 * Get Auftragskand. Erzeugen.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_OLCandGenerator_ID();

    /** Column definition for C_OLCandGenerator_ID */
    public static final org.adempiere.model.ModelColumn<I_C_OLCandGenerator, Object> COLUMN_C_OLCandGenerator_ID = new org.adempiere.model.ModelColumn<I_C_OLCandGenerator, Object>(I_C_OLCandGenerator.class, "C_OLCandGenerator_ID", null);
    /** Column name C_OLCandGenerator_ID */
    public static final String COLUMNNAME_C_OLCandGenerator_ID = "C_OLCandGenerator_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_OLCandGenerator, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_OLCandGenerator, Object>(I_C_OLCandGenerator.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_OLCandGenerator, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_OLCandGenerator, org.compiere.model.I_AD_User>(I_C_OLCandGenerator.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_OLCandGenerator, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_C_OLCandGenerator, Object>(I_C_OLCandGenerator.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Entitäts-Art.
	 * Dictionary Entity Type;
 Determines ownership and synchronization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setEntityType (java.lang.String EntityType);

	/**
	 * Get Entitäts-Art.
	 * Dictionary Entity Type;
 Determines ownership and synchronization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEntityType();

    /** Column definition for EntityType */
    public static final org.adempiere.model.ModelColumn<I_C_OLCandGenerator, Object> COLUMN_EntityType = new org.adempiere.model.ModelColumn<I_C_OLCandGenerator, Object>(I_C_OLCandGenerator.class, "EntityType", null);
    /** Column name EntityType */
    public static final String COLUMNNAME_EntityType = "EntityType";

	/**
	 * Set Kommentar/Hilfe.
	 * Comment or Hint
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setHelp (java.lang.String Help);

	/**
	 * Get Kommentar/Hilfe.
	 * Comment or Hint
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getHelp();

    /** Column definition for Help */
    public static final org.adempiere.model.ModelColumn<I_C_OLCandGenerator, Object> COLUMN_Help = new org.adempiere.model.ModelColumn<I_C_OLCandGenerator, Object>(I_C_OLCandGenerator.class, "Help", null);
    /** Column name Help */
    public static final String COLUMNNAME_Help = "Help";

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
    public static final org.adempiere.model.ModelColumn<I_C_OLCandGenerator, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_OLCandGenerator, Object>(I_C_OLCandGenerator.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Sofort verarbeiten.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsProcessDirectly (boolean IsProcessDirectly);

	/**
	 * Get Sofort verarbeiten.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isProcessDirectly();

    /** Column definition for IsProcessDirectly */
    public static final org.adempiere.model.ModelColumn<I_C_OLCandGenerator, Object> COLUMN_IsProcessDirectly = new org.adempiere.model.ModelColumn<I_C_OLCandGenerator, Object>(I_C_OLCandGenerator.class, "IsProcessDirectly", null);
    /** Column name IsProcessDirectly */
    public static final String COLUMNNAME_IsProcessDirectly = "IsProcessDirectly";

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
    public static final org.adempiere.model.ModelColumn<I_C_OLCandGenerator, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_C_OLCandGenerator, Object>(I_C_OLCandGenerator.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Auftragskand.-Erzeuger.
	 * Java-Class
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setOCGeneratorImpl (java.lang.String OCGeneratorImpl);

	/**
	 * Get Auftragskand.-Erzeuger.
	 * Java-Class
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getOCGeneratorImpl();

    /** Column definition for OCGeneratorImpl */
    public static final org.adempiere.model.ModelColumn<I_C_OLCandGenerator, Object> COLUMN_OCGeneratorImpl = new org.adempiere.model.ModelColumn<I_C_OLCandGenerator, Object>(I_C_OLCandGenerator.class, "OCGeneratorImpl", null);
    /** Column name OCGeneratorImpl */
    public static final String COLUMNNAME_OCGeneratorImpl = "OCGeneratorImpl";

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
    public static final org.adempiere.model.ModelColumn<I_C_OLCandGenerator, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_OLCandGenerator, Object>(I_C_OLCandGenerator.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_OLCandGenerator, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_OLCandGenerator, org.compiere.model.I_AD_User>(I_C_OLCandGenerator.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
