package de.metas.handlingunits.model;


/** Generated Interface for M_HU_Process
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_HU_Process 
{

    /** TableName=M_HU_Process */
    public static final String Table_Name = "M_HU_Process";

    /** AD_Table_ID=540607 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 4 - System
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(4);

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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Process, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_HU_Process, org.compiere.model.I_AD_Client>(I_M_HU_Process.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Process, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_HU_Process, org.compiere.model.I_AD_Org>(I_M_HU_Process.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Prozess.
	 * Prozess oder Bericht
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Process_ID (int AD_Process_ID);

	/**
	 * Get Prozess.
	 * Prozess oder Bericht
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Process_ID();

	public org.compiere.model.I_AD_Process getAD_Process();

	public void setAD_Process(org.compiere.model.I_AD_Process AD_Process);

    /** Column definition for AD_Process_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Process, org.compiere.model.I_AD_Process> COLUMN_AD_Process_ID = new org.adempiere.model.ModelColumn<I_M_HU_Process, org.compiere.model.I_AD_Process>(I_M_HU_Process.class, "AD_Process_ID", org.compiere.model.I_AD_Process.class);
    /** Column name AD_Process_ID */
    public static final String COLUMNNAME_AD_Process_ID = "AD_Process_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Process, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_HU_Process, Object>(I_M_HU_Process.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Process, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_HU_Process, org.compiere.model.I_AD_User>(I_M_HU_Process.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Process, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_HU_Process, Object>(I_M_HU_Process.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Auf CUs anwenden.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsApplyToCUs (boolean IsApplyToCUs);

	/**
	 * Get Auf CUs anwenden.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isApplyToCUs();

    /** Column definition for IsApplyToCUs */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Process, Object> COLUMN_IsApplyToCUs = new org.adempiere.model.ModelColumn<I_M_HU_Process, Object>(I_M_HU_Process.class, "IsApplyToCUs", null);
    /** Column name IsApplyToCUs */
    public static final String COLUMNNAME_IsApplyToCUs = "IsApplyToCUs";

	/**
	 * Set Auf LUs anwenden.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsApplyToLUs (boolean IsApplyToLUs);

	/**
	 * Get Auf LUs anwenden.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isApplyToLUs();

    /** Column definition for IsApplyToLUs */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Process, Object> COLUMN_IsApplyToLUs = new org.adempiere.model.ModelColumn<I_M_HU_Process, Object>(I_M_HU_Process.class, "IsApplyToLUs", null);
    /** Column name IsApplyToLUs */
    public static final String COLUMNNAME_IsApplyToLUs = "IsApplyToLUs";

	/**
	 * Set Nur auf Top-Level HU anwenden.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsApplyToTopLevelHUsOnly (boolean IsApplyToTopLevelHUsOnly);

	/**
	 * Get Nur auf Top-Level HU anwenden.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isApplyToTopLevelHUsOnly();

    /** Column definition for IsApplyToTopLevelHUsOnly */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Process, Object> COLUMN_IsApplyToTopLevelHUsOnly = new org.adempiere.model.ModelColumn<I_M_HU_Process, Object>(I_M_HU_Process.class, "IsApplyToTopLevelHUsOnly", null);
    /** Column name IsApplyToTopLevelHUsOnly */
    public static final String COLUMNNAME_IsApplyToTopLevelHUsOnly = "IsApplyToTopLevelHUsOnly";

	/**
	 * Set Auf TUs anwenden.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsApplyToTUs (boolean IsApplyToTUs);

	/**
	 * Get Auf TUs anwenden.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isApplyToTUs();

    /** Column definition for IsApplyToTUs */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Process, Object> COLUMN_IsApplyToTUs = new org.adempiere.model.ModelColumn<I_M_HU_Process, Object>(I_M_HU_Process.class, "IsApplyToTUs", null);
    /** Column name IsApplyToTUs */
    public static final String COLUMNNAME_IsApplyToTUs = "IsApplyToTUs";

	/**
	 * Set Als Benutzeraktion verfügbar machen.
	 * Entscheidet, ob der Prozess als Aktion im Handling-Unit-Editor (WebUI und SwingUI) auswählbar sein soll.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsProvideAsUserAction (boolean IsProvideAsUserAction);

	/**
	 * Get Als Benutzeraktion verfügbar machen.
	 * Entscheidet, ob der Prozess als Aktion im Handling-Unit-Editor (WebUI und SwingUI) auswählbar sein soll.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isProvideAsUserAction();

    /** Column definition for IsProvideAsUserAction */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Process, Object> COLUMN_IsProvideAsUserAction = new org.adempiere.model.ModelColumn<I_M_HU_Process, Object>(I_M_HU_Process.class, "IsProvideAsUserAction", null);
    /** Column name IsProvideAsUserAction */
    public static final String COLUMNNAME_IsProvideAsUserAction = "IsProvideAsUserAction";

	/**
	 * Set Packvorschrift.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_HU_PI_ID (int M_HU_PI_ID);

	/**
	 * Get Packvorschrift.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_HU_PI_ID();

	public de.metas.handlingunits.model.I_M_HU_PI getM_HU_PI();

	public void setM_HU_PI(de.metas.handlingunits.model.I_M_HU_PI M_HU_PI);

    /** Column definition for M_HU_PI_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Process, de.metas.handlingunits.model.I_M_HU_PI> COLUMN_M_HU_PI_ID = new org.adempiere.model.ModelColumn<I_M_HU_Process, de.metas.handlingunits.model.I_M_HU_PI>(I_M_HU_Process.class, "M_HU_PI_ID", de.metas.handlingunits.model.I_M_HU_PI.class);
    /** Column name M_HU_PI_ID */
    public static final String COLUMNNAME_M_HU_PI_ID = "M_HU_PI_ID";

	/**
	 * Set Handling Unit Process.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_HU_Process_ID (int M_HU_Process_ID);

	/**
	 * Get Handling Unit Process.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_HU_Process_ID();

    /** Column definition for M_HU_Process_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Process, Object> COLUMN_M_HU_Process_ID = new org.adempiere.model.ModelColumn<I_M_HU_Process, Object>(I_M_HU_Process.class, "M_HU_Process_ID", null);
    /** Column name M_HU_Process_ID */
    public static final String COLUMNNAME_M_HU_Process_ID = "M_HU_Process_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Process, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_HU_Process, Object>(I_M_HU_Process.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Process, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_HU_Process, org.compiere.model.I_AD_User>(I_M_HU_Process.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
