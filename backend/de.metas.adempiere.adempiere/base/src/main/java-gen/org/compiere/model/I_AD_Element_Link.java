package org.compiere.model;


/** Generated Interface for AD_Element_Link
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_Element_Link 
{

    /** TableName=AD_Element_Link */
    public static final String Table_Name = "AD_Element_Link";

    /** AD_Table_ID=541153 */
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
    public static final org.adempiere.model.ModelColumn<I_AD_Element_Link, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_AD_Element_Link, org.compiere.model.I_AD_Client>(I_AD_Element_Link.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set System-Element.
	 * Das "System-Element" ermöglicht die zentrale  Verwaltung von Spaltenbeschreibungen und Hilfetexten.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Element_ID (int AD_Element_ID);

	/**
	 * Get System-Element.
	 * Das "System-Element" ermöglicht die zentrale  Verwaltung von Spaltenbeschreibungen und Hilfetexten.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Element_ID();

	public org.compiere.model.I_AD_Element getAD_Element();

	public void setAD_Element(org.compiere.model.I_AD_Element AD_Element);

    /** Column definition for AD_Element_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Element_Link, org.compiere.model.I_AD_Element> COLUMN_AD_Element_ID = new org.adempiere.model.ModelColumn<I_AD_Element_Link, org.compiere.model.I_AD_Element>(I_AD_Element_Link.class, "AD_Element_ID", org.compiere.model.I_AD_Element.class);
    /** Column name AD_Element_ID */
    public static final String COLUMNNAME_AD_Element_ID = "AD_Element_ID";

	/**
	 * Set AD_Element_Link.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Element_Link_ID (int AD_Element_Link_ID);

	/**
	 * Get AD_Element_Link.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Element_Link_ID();

    /** Column definition for AD_Element_Link_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Element_Link, Object> COLUMN_AD_Element_Link_ID = new org.adempiere.model.ModelColumn<I_AD_Element_Link, Object>(I_AD_Element_Link.class, "AD_Element_Link_ID", null);
    /** Column name AD_Element_Link_ID */
    public static final String COLUMNNAME_AD_Element_Link_ID = "AD_Element_Link_ID";

	/**
	 * Set Feld.
	 * Ein Feld einer Datenbanktabelle
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Field_ID (int AD_Field_ID);

	/**
	 * Get Feld.
	 * Ein Feld einer Datenbanktabelle
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Field_ID();

	public org.compiere.model.I_AD_Field getAD_Field();

	public void setAD_Field(org.compiere.model.I_AD_Field AD_Field);

    /** Column definition for AD_Field_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Element_Link, org.compiere.model.I_AD_Field> COLUMN_AD_Field_ID = new org.adempiere.model.ModelColumn<I_AD_Element_Link, org.compiere.model.I_AD_Field>(I_AD_Element_Link.class, "AD_Field_ID", org.compiere.model.I_AD_Field.class);
    /** Column name AD_Field_ID */
    public static final String COLUMNNAME_AD_Field_ID = "AD_Field_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Element_Link, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_AD_Element_Link, org.compiere.model.I_AD_Org>(I_AD_Element_Link.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Register.
	 * Register auf einem Fenster
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Tab_ID (int AD_Tab_ID);

	/**
	 * Get Register.
	 * Register auf einem Fenster
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Tab_ID();

	public org.compiere.model.I_AD_Tab getAD_Tab();

	public void setAD_Tab(org.compiere.model.I_AD_Tab AD_Tab);

    /** Column definition for AD_Tab_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Element_Link, org.compiere.model.I_AD_Tab> COLUMN_AD_Tab_ID = new org.adempiere.model.ModelColumn<I_AD_Element_Link, org.compiere.model.I_AD_Tab>(I_AD_Element_Link.class, "AD_Tab_ID", org.compiere.model.I_AD_Tab.class);
    /** Column name AD_Tab_ID */
    public static final String COLUMNNAME_AD_Tab_ID = "AD_Tab_ID";

	/**
	 * Set Fenster.
	 * Eingabe- oder Anzeige-Fenster
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Window_ID (int AD_Window_ID);

	/**
	 * Get Fenster.
	 * Eingabe- oder Anzeige-Fenster
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Window_ID();

	public org.compiere.model.I_AD_Window getAD_Window();

	public void setAD_Window(org.compiere.model.I_AD_Window AD_Window);

    /** Column definition for AD_Window_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Element_Link, org.compiere.model.I_AD_Window> COLUMN_AD_Window_ID = new org.adempiere.model.ModelColumn<I_AD_Element_Link, org.compiere.model.I_AD_Window>(I_AD_Element_Link.class, "AD_Window_ID", org.compiere.model.I_AD_Window.class);
    /** Column name AD_Window_ID */
    public static final String COLUMNNAME_AD_Window_ID = "AD_Window_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Element_Link, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_Element_Link, Object>(I_AD_Element_Link.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Element_Link, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_AD_Element_Link, org.compiere.model.I_AD_User>(I_AD_Element_Link.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Element_Link, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_Element_Link, Object>(I_AD_Element_Link.class, "IsActive", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Element_Link, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_Element_Link, Object>(I_AD_Element_Link.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Element_Link, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_AD_Element_Link, org.compiere.model.I_AD_User>(I_AD_Element_Link.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
