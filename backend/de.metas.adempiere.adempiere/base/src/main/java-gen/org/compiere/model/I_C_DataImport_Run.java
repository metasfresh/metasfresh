package org.compiere.model;


/** Generated Interface for C_DataImport_Run
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_DataImport_Run 
{

    /** TableName=C_DataImport_Run */
    public static final String Table_Name = "C_DataImport_Run";

    /** AD_Table_ID=541409 */
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

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_C_DataImport_Run, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_DataImport_Run, org.compiere.model.I_AD_Client>(I_C_DataImport_Run.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Import-Format.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_ImpFormat_ID (int AD_ImpFormat_ID);

	/**
	 * Get Import-Format.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_ImpFormat_ID();

	public org.compiere.model.I_AD_ImpFormat getAD_ImpFormat();

	public void setAD_ImpFormat(org.compiere.model.I_AD_ImpFormat AD_ImpFormat);

    /** Column definition for AD_ImpFormat_ID */
    public static final org.adempiere.model.ModelColumn<I_C_DataImport_Run, org.compiere.model.I_AD_ImpFormat> COLUMN_AD_ImpFormat_ID = new org.adempiere.model.ModelColumn<I_C_DataImport_Run, org.compiere.model.I_AD_ImpFormat>(I_C_DataImport_Run.class, "AD_ImpFormat_ID", org.compiere.model.I_AD_ImpFormat.class);
    /** Column name AD_ImpFormat_ID */
    public static final String COLUMNNAME_AD_ImpFormat_ID = "AD_ImpFormat_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_C_DataImport_Run, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_DataImport_Run, org.compiere.model.I_AD_Org>(I_C_DataImport_Run.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Ansprechpartner.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_User_ID (int AD_User_ID);

	/**
	 * Get Ansprechpartner.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_User_ID();

    /** Column definition for AD_User_ID */
    public static final org.adempiere.model.ModelColumn<I_C_DataImport_Run, org.compiere.model.I_AD_User> COLUMN_AD_User_ID = new org.adempiere.model.ModelColumn<I_C_DataImport_Run, org.compiere.model.I_AD_User>(I_C_DataImport_Run.class, "AD_User_ID", org.compiere.model.I_AD_User.class);
    /** Column name AD_User_ID */
    public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/**
	 * Set Daten Import.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_DataImport_ID (int C_DataImport_ID);

	/**
	 * Get Daten Import.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_DataImport_ID();

	public org.compiere.model.I_C_DataImport getC_DataImport();

	public void setC_DataImport(org.compiere.model.I_C_DataImport C_DataImport);

    /** Column definition for C_DataImport_ID */
    public static final org.adempiere.model.ModelColumn<I_C_DataImport_Run, org.compiere.model.I_C_DataImport> COLUMN_C_DataImport_ID = new org.adempiere.model.ModelColumn<I_C_DataImport_Run, org.compiere.model.I_C_DataImport>(I_C_DataImport_Run.class, "C_DataImport_ID", org.compiere.model.I_C_DataImport.class);
    /** Column name C_DataImport_ID */
    public static final String COLUMNNAME_C_DataImport_ID = "C_DataImport_ID";

	/**
	 * Set Data Import Run.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_DataImport_Run_ID (int C_DataImport_Run_ID);

	/**
	 * Get Data Import Run.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_DataImport_Run_ID();

    /** Column definition for C_DataImport_Run_ID */
    public static final org.adempiere.model.ModelColumn<I_C_DataImport_Run, Object> COLUMN_C_DataImport_Run_ID = new org.adempiere.model.ModelColumn<I_C_DataImport_Run, Object>(I_C_DataImport_Run.class, "C_DataImport_Run_ID", null);
    /** Column name C_DataImport_Run_ID */
    public static final String COLUMNNAME_C_DataImport_Run_ID = "C_DataImport_Run_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_DataImport_Run, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_DataImport_Run, Object>(I_C_DataImport_Run.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_DataImport_Run, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_DataImport_Run, org.compiere.model.I_AD_User>(I_C_DataImport_Run.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_DataImport_Run, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_DataImport_Run, Object>(I_C_DataImport_Run.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Beleg fertig stellen.
	 * Legt fest, ob ggf erstellte Belege (z.B. Produktionsaufträge) auch direkt automatisch fertig gestellt werden sollen.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsDocComplete (boolean IsDocComplete);

	/**
	 * Get Beleg fertig stellen.
	 * Legt fest, ob ggf erstellte Belege (z.B. Produktionsaufträge) auch direkt automatisch fertig gestellt werden sollen.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isDocComplete();

    /** Column definition for IsDocComplete */
    public static final org.adempiere.model.ModelColumn<I_C_DataImport_Run, Object> COLUMN_IsDocComplete = new org.adempiere.model.ModelColumn<I_C_DataImport_Run, Object>(I_C_DataImport_Run.class, "IsDocComplete", null);
    /** Column name IsDocComplete */
    public static final String COLUMNNAME_IsDocComplete = "IsDocComplete";

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
    public static final org.adempiere.model.ModelColumn<I_C_DataImport_Run, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_DataImport_Run, Object>(I_C_DataImport_Run.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_DataImport_Run, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_DataImport_Run, org.compiere.model.I_AD_User>(I_C_DataImport_Run.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
