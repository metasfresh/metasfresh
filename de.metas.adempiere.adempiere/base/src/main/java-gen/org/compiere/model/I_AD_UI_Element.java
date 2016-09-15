package org.compiere.model;


/** Generated Interface for AD_UI_Element
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_UI_Element 
{

    /** TableName=AD_UI_Element */
    public static final String Table_Name = "AD_UI_Element";

    /** AD_Table_ID=540783 */
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
    public static final org.adempiere.model.ModelColumn<I_AD_UI_Element, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_AD_UI_Element, org.compiere.model.I_AD_Client>(I_AD_UI_Element.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_UI_Element, org.compiere.model.I_AD_Field> COLUMN_AD_Field_ID = new org.adempiere.model.ModelColumn<I_AD_UI_Element, org.compiere.model.I_AD_Field>(I_AD_UI_Element.class, "AD_Field_ID", org.compiere.model.I_AD_Field.class);
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
    public static final org.adempiere.model.ModelColumn<I_AD_UI_Element, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_AD_UI_Element, org.compiere.model.I_AD_Org>(I_AD_UI_Element.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Register.
	 * Register auf einem Fenster
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Tab_ID (int AD_Tab_ID);

	/**
	 * Get Register.
	 * Register auf einem Fenster
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Tab_ID();

	public org.compiere.model.I_AD_Tab getAD_Tab();

	public void setAD_Tab(org.compiere.model.I_AD_Tab AD_Tab);

    /** Column definition for AD_Tab_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_UI_Element, org.compiere.model.I_AD_Tab> COLUMN_AD_Tab_ID = new org.adempiere.model.ModelColumn<I_AD_UI_Element, org.compiere.model.I_AD_Tab>(I_AD_UI_Element.class, "AD_Tab_ID", org.compiere.model.I_AD_Tab.class);
    /** Column name AD_Tab_ID */
    public static final String COLUMNNAME_AD_Tab_ID = "AD_Tab_ID";

	/**
	 * Set UI Element.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_UI_Element_ID (int AD_UI_Element_ID);

	/**
	 * Get UI Element.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_UI_Element_ID();

    /** Column definition for AD_UI_Element_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_UI_Element, Object> COLUMN_AD_UI_Element_ID = new org.adempiere.model.ModelColumn<I_AD_UI_Element, Object>(I_AD_UI_Element.class, "AD_UI_Element_ID", null);
    /** Column name AD_UI_Element_ID */
    public static final String COLUMNNAME_AD_UI_Element_ID = "AD_UI_Element_ID";

	/**
	 * Set UI Element Group.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_UI_ElementGroup_ID (int AD_UI_ElementGroup_ID);

	/**
	 * Get UI Element Group.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_UI_ElementGroup_ID();

	public org.compiere.model.I_AD_UI_ElementGroup getAD_UI_ElementGroup();

	public void setAD_UI_ElementGroup(org.compiere.model.I_AD_UI_ElementGroup AD_UI_ElementGroup);

    /** Column definition for AD_UI_ElementGroup_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_UI_Element, org.compiere.model.I_AD_UI_ElementGroup> COLUMN_AD_UI_ElementGroup_ID = new org.adempiere.model.ModelColumn<I_AD_UI_Element, org.compiere.model.I_AD_UI_ElementGroup>(I_AD_UI_Element.class, "AD_UI_ElementGroup_ID", org.compiere.model.I_AD_UI_ElementGroup.class);
    /** Column name AD_UI_ElementGroup_ID */
    public static final String COLUMNNAME_AD_UI_ElementGroup_ID = "AD_UI_ElementGroup_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_UI_Element, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_UI_Element, Object>(I_AD_UI_Element.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_UI_Element, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_AD_UI_Element, org.compiere.model.I_AD_User>(I_AD_UI_Element.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Beschreibung.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescription (java.lang.String Description);

	/**
	 * Get Beschreibung.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_AD_UI_Element, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_AD_UI_Element, Object>(I_AD_UI_Element.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Kommentar/Hilfe.
	 * Comment or Hint
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setHelp (java.lang.String Help);

	/**
	 * Get Kommentar/Hilfe.
	 * Comment or Hint
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getHelp();

    /** Column definition for Help */
    public static final org.adempiere.model.ModelColumn<I_AD_UI_Element, Object> COLUMN_Help = new org.adempiere.model.ModelColumn<I_AD_UI_Element, Object>(I_AD_UI_Element.class, "Help", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_UI_Element, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_UI_Element, Object>(I_AD_UI_Element.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Advanced field.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsAdvancedField (boolean IsAdvancedField);

	/**
	 * Get Advanced field.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAdvancedField();

    /** Column definition for IsAdvancedField */
    public static final org.adempiere.model.ModelColumn<I_AD_UI_Element, Object> COLUMN_IsAdvancedField = new org.adempiere.model.ModelColumn<I_AD_UI_Element, Object>(I_AD_UI_Element.class, "IsAdvancedField", null);
    /** Column name IsAdvancedField */
    public static final String COLUMNNAME_IsAdvancedField = "IsAdvancedField";

	/**
	 * Set Displayed.
	 * Determines, if this field is displayed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsDisplayed (boolean IsDisplayed);

	/**
	 * Get Displayed.
	 * Determines, if this field is displayed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isDisplayed();

    /** Column definition for IsDisplayed */
    public static final org.adempiere.model.ModelColumn<I_AD_UI_Element, Object> COLUMN_IsDisplayed = new org.adempiere.model.ModelColumn<I_AD_UI_Element, Object>(I_AD_UI_Element.class, "IsDisplayed", null);
    /** Column name IsDisplayed */
    public static final String COLUMNNAME_IsDisplayed = "IsDisplayed";

	/**
	 * Set Displayed in Side List.
	 * Determines, if this field is displayed in Side list
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsDisplayed_SideList (boolean IsDisplayed_SideList);

	/**
	 * Get Displayed in Side List.
	 * Determines, if this field is displayed in Side list
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isDisplayed_SideList();

    /** Column definition for IsDisplayed_SideList */
    public static final org.adempiere.model.ModelColumn<I_AD_UI_Element, Object> COLUMN_IsDisplayed_SideList = new org.adempiere.model.ModelColumn<I_AD_UI_Element, Object>(I_AD_UI_Element.class, "IsDisplayed_SideList", null);
    /** Column name IsDisplayed_SideList */
    public static final String COLUMNNAME_IsDisplayed_SideList = "IsDisplayed_SideList";

	/**
	 * Set Displayed in Grid.
	 * Determines, if this field is displayed in grid mode
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsDisplayedGrid (boolean IsDisplayedGrid);

	/**
	 * Get Displayed in Grid.
	 * Determines, if this field is displayed in grid mode
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isDisplayedGrid();

    /** Column definition for IsDisplayedGrid */
    public static final org.adempiere.model.ModelColumn<I_AD_UI_Element, Object> COLUMN_IsDisplayedGrid = new org.adempiere.model.ModelColumn<I_AD_UI_Element, Object>(I_AD_UI_Element.class, "IsDisplayedGrid", null);
    /** Column name IsDisplayedGrid */
    public static final String COLUMNNAME_IsDisplayedGrid = "IsDisplayedGrid";

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
    public static final org.adempiere.model.ModelColumn<I_AD_UI_Element, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_AD_UI_Element, Object>(I_AD_UI_Element.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Reihenfolge.
	 * Zur Bestimmung der Reihenfolge der Einträge;
 die kleinste Zahl kommt zuerst
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setSeqNo (int SeqNo);

	/**
	 * Get Reihenfolge.
	 * Zur Bestimmung der Reihenfolge der Einträge;
 die kleinste Zahl kommt zuerst
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getSeqNo();

    /** Column definition for SeqNo */
    public static final org.adempiere.model.ModelColumn<I_AD_UI_Element, Object> COLUMN_SeqNo = new org.adempiere.model.ModelColumn<I_AD_UI_Element, Object>(I_AD_UI_Element.class, "SeqNo", null);
    /** Column name SeqNo */
    public static final String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Set Reihenfolge (Side List).
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setSeqNo_SideList (int SeqNo_SideList);

	/**
	 * Get Reihenfolge (Side List).
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getSeqNo_SideList();

    /** Column definition for SeqNo_SideList */
    public static final org.adempiere.model.ModelColumn<I_AD_UI_Element, Object> COLUMN_SeqNo_SideList = new org.adempiere.model.ModelColumn<I_AD_UI_Element, Object>(I_AD_UI_Element.class, "SeqNo_SideList", null);
    /** Column name SeqNo_SideList */
    public static final String COLUMNNAME_SeqNo_SideList = "SeqNo_SideList";

	/**
	 * Set Reihenfolge (grid).
	 * Zur Bestimmung der Reihenfolge der Einträge;
 die kleinste Zahl kommt zuerst
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setSeqNoGrid (int SeqNoGrid);

	/**
	 * Get Reihenfolge (grid).
	 * Zur Bestimmung der Reihenfolge der Einträge;
 die kleinste Zahl kommt zuerst
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getSeqNoGrid();

    /** Column definition for SeqNoGrid */
    public static final org.adempiere.model.ModelColumn<I_AD_UI_Element, Object> COLUMN_SeqNoGrid = new org.adempiere.model.ModelColumn<I_AD_UI_Element, Object>(I_AD_UI_Element.class, "SeqNoGrid", null);
    /** Column name SeqNoGrid */
    public static final String COLUMNNAME_SeqNoGrid = "SeqNoGrid";

	/**
	 * Set UI Style.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setUIStyle (java.lang.String UIStyle);

	/**
	 * Get UI Style.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getUIStyle();

    /** Column definition for UIStyle */
    public static final org.adempiere.model.ModelColumn<I_AD_UI_Element, Object> COLUMN_UIStyle = new org.adempiere.model.ModelColumn<I_AD_UI_Element, Object>(I_AD_UI_Element.class, "UIStyle", null);
    /** Column name UIStyle */
    public static final String COLUMNNAME_UIStyle = "UIStyle";

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
    public static final org.adempiere.model.ModelColumn<I_AD_UI_Element, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_UI_Element, Object>(I_AD_UI_Element.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_UI_Element, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_AD_UI_Element, org.compiere.model.I_AD_User>(I_AD_UI_Element.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
