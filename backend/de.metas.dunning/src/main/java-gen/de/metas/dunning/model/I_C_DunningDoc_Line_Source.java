package de.metas.dunning.model;


/** Generated Interface for C_DunningDoc_Line_Source
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_DunningDoc_Line_Source 
{

    /** TableName=C_DunningDoc_Line_Source */
    public static final String Table_Name = "C_DunningDoc_Line_Source";

    /** AD_Table_ID=540403 */
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
    public static final org.adempiere.model.ModelColumn<I_C_DunningDoc_Line_Source, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_DunningDoc_Line_Source, org.compiere.model.I_AD_Client>(I_C_DunningDoc_Line_Source.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_DunningDoc_Line_Source, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_DunningDoc_Line_Source, org.compiere.model.I_AD_Org>(I_C_DunningDoc_Line_Source.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Mahnungsdisposition.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Dunning_Candidate_ID (int C_Dunning_Candidate_ID);

	/**
	 * Get Mahnungsdisposition.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Dunning_Candidate_ID();

	public de.metas.dunning.model.I_C_Dunning_Candidate getC_Dunning_Candidate();

	public void setC_Dunning_Candidate(de.metas.dunning.model.I_C_Dunning_Candidate C_Dunning_Candidate);

    /** Column definition for C_Dunning_Candidate_ID */
    public static final org.adempiere.model.ModelColumn<I_C_DunningDoc_Line_Source, de.metas.dunning.model.I_C_Dunning_Candidate> COLUMN_C_Dunning_Candidate_ID = new org.adempiere.model.ModelColumn<I_C_DunningDoc_Line_Source, de.metas.dunning.model.I_C_Dunning_Candidate>(I_C_DunningDoc_Line_Source.class, "C_Dunning_Candidate_ID", de.metas.dunning.model.I_C_Dunning_Candidate.class);
    /** Column name C_Dunning_Candidate_ID */
    public static final String COLUMNNAME_C_Dunning_Candidate_ID = "C_Dunning_Candidate_ID";

	/**
	 * Set Dunning Document Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_DunningDoc_Line_ID (int C_DunningDoc_Line_ID);

	/**
	 * Get Dunning Document Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_DunningDoc_Line_ID();

	public de.metas.dunning.model.I_C_DunningDoc_Line getC_DunningDoc_Line();

	public void setC_DunningDoc_Line(de.metas.dunning.model.I_C_DunningDoc_Line C_DunningDoc_Line);

    /** Column definition for C_DunningDoc_Line_ID */
    public static final org.adempiere.model.ModelColumn<I_C_DunningDoc_Line_Source, de.metas.dunning.model.I_C_DunningDoc_Line> COLUMN_C_DunningDoc_Line_ID = new org.adempiere.model.ModelColumn<I_C_DunningDoc_Line_Source, de.metas.dunning.model.I_C_DunningDoc_Line>(I_C_DunningDoc_Line_Source.class, "C_DunningDoc_Line_ID", de.metas.dunning.model.I_C_DunningDoc_Line.class);
    /** Column name C_DunningDoc_Line_ID */
    public static final String COLUMNNAME_C_DunningDoc_Line_ID = "C_DunningDoc_Line_ID";

	/**
	 * Set Dunning Document Line Source.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_DunningDoc_Line_Source_ID (int C_DunningDoc_Line_Source_ID);

	/**
	 * Get Dunning Document Line Source.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_DunningDoc_Line_Source_ID();

    /** Column definition for C_DunningDoc_Line_Source_ID */
    public static final org.adempiere.model.ModelColumn<I_C_DunningDoc_Line_Source, Object> COLUMN_C_DunningDoc_Line_Source_ID = new org.adempiere.model.ModelColumn<I_C_DunningDoc_Line_Source, Object>(I_C_DunningDoc_Line_Source.class, "C_DunningDoc_Line_Source_ID", null);
    /** Column name C_DunningDoc_Line_Source_ID */
    public static final String COLUMNNAME_C_DunningDoc_Line_Source_ID = "C_DunningDoc_Line_Source_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_DunningDoc_Line_Source, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_DunningDoc_Line_Source, Object>(I_C_DunningDoc_Line_Source.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_DunningDoc_Line_Source, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_DunningDoc_Line_Source, org.compiere.model.I_AD_User>(I_C_DunningDoc_Line_Source.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_DunningDoc_Line_Source, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_DunningDoc_Line_Source, Object>(I_C_DunningDoc_Line_Source.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Massenaustritt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsWriteOff (boolean IsWriteOff);

	/**
	 * Get Massenaustritt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isWriteOff();

    /** Column definition for IsWriteOff */
    public static final org.adempiere.model.ModelColumn<I_C_DunningDoc_Line_Source, Object> COLUMN_IsWriteOff = new org.adempiere.model.ModelColumn<I_C_DunningDoc_Line_Source, Object>(I_C_DunningDoc_Line_Source.class, "IsWriteOff", null);
    /** Column name IsWriteOff */
    public static final String COLUMNNAME_IsWriteOff = "IsWriteOff";

	/**
	 * Set Massenaustritt Applied.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsWriteOffApplied (boolean IsWriteOffApplied);

	/**
	 * Get Massenaustritt Applied.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isWriteOffApplied();

    /** Column definition for IsWriteOffApplied */
    public static final org.adempiere.model.ModelColumn<I_C_DunningDoc_Line_Source, Object> COLUMN_IsWriteOffApplied = new org.adempiere.model.ModelColumn<I_C_DunningDoc_Line_Source, Object>(I_C_DunningDoc_Line_Source.class, "IsWriteOffApplied", null);
    /** Column name IsWriteOffApplied */
    public static final String COLUMNNAME_IsWriteOffApplied = "IsWriteOffApplied";

	/**
	 * Set Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setProcessed (boolean Processed);

	/**
	 * Get Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isProcessed();

    /** Column definition for Processed */
    public static final org.adempiere.model.ModelColumn<I_C_DunningDoc_Line_Source, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_C_DunningDoc_Line_Source, Object>(I_C_DunningDoc_Line_Source.class, "Processed", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_DunningDoc_Line_Source, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_DunningDoc_Line_Source, Object>(I_C_DunningDoc_Line_Source.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_DunningDoc_Line_Source, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_DunningDoc_Line_Source, org.compiere.model.I_AD_User>(I_C_DunningDoc_Line_Source.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";


	// TODO 1: add real columns!!!
	// TODO 2: check/add DB INDEX on (Record_ID, AD_Table_ID)
	// TODO 3: migrate current data (set C_DunningDoc_ID, AD_Table_ID, Record_ID)
	String COLUMNNAME_C_DunningDoc_ID = "C_DunningDoc_ID";
	String COLUMNNAME_AD_Table_ID = "AD_Table_ID";
	String COLUMNNAME_Record_ID = "Record_ID";
	default void setC_DunningDoc_ID(int i) {}
	default void setAD_Table_ID(int i) {}
	default void setRecord_ID(int i) {}
}
