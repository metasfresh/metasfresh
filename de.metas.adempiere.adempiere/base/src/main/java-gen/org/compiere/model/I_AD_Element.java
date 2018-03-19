package org.compiere.model;


/** Generated Interface for AD_Element
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_Element 
{

    /** TableName=AD_Element */
    public static final String Table_Name = "AD_Element";

    /** AD_Table_ID=276 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 4 - System
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(4);

    /** Load Meta Data */

	/**
	 * Get Mandant.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Element, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_AD_Element, org.compiere.model.I_AD_Client>(I_AD_Element.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set System-Element.
	 * System Element enables the central maintenance of column description and help.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Element_ID (int AD_Element_ID);

	/**
	 * Get System-Element.
	 * System Element enables the central maintenance of column description and help.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Element_ID();

    /** Column definition for AD_Element_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Element, Object> COLUMN_AD_Element_ID = new org.adempiere.model.ModelColumn<I_AD_Element, Object>(I_AD_Element.class, "AD_Element_ID", null);
    /** Column name AD_Element_ID */
    public static final String COLUMNNAME_AD_Element_ID = "AD_Element_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Element, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_AD_Element, org.compiere.model.I_AD_Org>(I_AD_Element.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Spaltenname.
	 * Name of the column in the database
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setColumnName (java.lang.String ColumnName);

	/**
	 * Get Spaltenname.
	 * Name of the column in the database
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getColumnName();

    /** Column definition for ColumnName */
    public static final org.adempiere.model.ModelColumn<I_AD_Element, Object> COLUMN_ColumnName = new org.adempiere.model.ModelColumn<I_AD_Element, Object>(I_AD_Element.class, "ColumnName", null);
    /** Column name ColumnName */
    public static final String COLUMNNAME_ColumnName = "ColumnName";

	/**
	 * Get Erstellt.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_AD_Element, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_Element, Object>(I_AD_Element.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_AD_Element, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_AD_Element, org.compiere.model.I_AD_User>(I_AD_Element.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Element, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_AD_Element, Object>(I_AD_Element.class, "Description", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Element, Object> COLUMN_EntityType = new org.adempiere.model.ModelColumn<I_AD_Element, Object>(I_AD_Element.class, "EntityType", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Element, Object> COLUMN_Help = new org.adempiere.model.ModelColumn<I_AD_Element, Object>(I_AD_Element.class, "Help", null);
    /** Column name Help */
    public static final String COLUMNNAME_Help = "Help";

	/**
	 * Set Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_AD_Element, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_Element, Object>(I_AD_Element.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Element, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_AD_Element, Object>(I_AD_Element.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set PO Description.
	 * Description in PO Screens
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPO_Description (java.lang.String PO_Description);

	/**
	 * Get PO Description.
	 * Description in PO Screens
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPO_Description();

    /** Column definition for PO_Description */
    public static final org.adempiere.model.ModelColumn<I_AD_Element, Object> COLUMN_PO_Description = new org.adempiere.model.ModelColumn<I_AD_Element, Object>(I_AD_Element.class, "PO_Description", null);
    /** Column name PO_Description */
    public static final String COLUMNNAME_PO_Description = "PO_Description";

	/**
	 * Set PO Help.
	 * Help for PO Screens
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPO_Help (java.lang.String PO_Help);

	/**
	 * Get PO Help.
	 * Help for PO Screens
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPO_Help();

    /** Column definition for PO_Help */
    public static final org.adempiere.model.ModelColumn<I_AD_Element, Object> COLUMN_PO_Help = new org.adempiere.model.ModelColumn<I_AD_Element, Object>(I_AD_Element.class, "PO_Help", null);
    /** Column name PO_Help */
    public static final String COLUMNNAME_PO_Help = "PO_Help";

	/**
	 * Set PO Name.
	 * Name on PO Screens
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPO_Name (java.lang.String PO_Name);

	/**
	 * Get PO Name.
	 * Name on PO Screens
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPO_Name();

    /** Column definition for PO_Name */
    public static final org.adempiere.model.ModelColumn<I_AD_Element, Object> COLUMN_PO_Name = new org.adempiere.model.ModelColumn<I_AD_Element, Object>(I_AD_Element.class, "PO_Name", null);
    /** Column name PO_Name */
    public static final String COLUMNNAME_PO_Name = "PO_Name";

	/**
	 * Set PO Print name.
	 * Print name on PO Screens/Reports
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPO_PrintName (java.lang.String PO_PrintName);

	/**
	 * Get PO Print name.
	 * Print name on PO Screens/Reports
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPO_PrintName();

    /** Column definition for PO_PrintName */
    public static final org.adempiere.model.ModelColumn<I_AD_Element, Object> COLUMN_PO_PrintName = new org.adempiere.model.ModelColumn<I_AD_Element, Object>(I_AD_Element.class, "PO_PrintName", null);
    /** Column name PO_PrintName */
    public static final String COLUMNNAME_PO_PrintName = "PO_PrintName";

	/**
	 * Set Drucktext.
	 * The label text to be printed on a document or correspondence.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPrintName (java.lang.String PrintName);

	/**
	 * Get Drucktext.
	 * The label text to be printed on a document or correspondence.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPrintName();

    /** Column definition for PrintName */
    public static final org.adempiere.model.ModelColumn<I_AD_Element, Object> COLUMN_PrintName = new org.adempiere.model.ModelColumn<I_AD_Element, Object>(I_AD_Element.class, "PrintName", null);
    /** Column name PrintName */
    public static final String COLUMNNAME_PrintName = "PrintName";

	/**
	 * Get Aktualisiert.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_AD_Element, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_Element, Object>(I_AD_Element.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_AD_Element, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_AD_Element, org.compiere.model.I_AD_User>(I_AD_Element.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Widget size.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setWidgetSize (java.lang.String WidgetSize);

	/**
	 * Get Widget size.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getWidgetSize();

    /** Column definition for WidgetSize */
    public static final org.adempiere.model.ModelColumn<I_AD_Element, Object> COLUMN_WidgetSize = new org.adempiere.model.ModelColumn<I_AD_Element, Object>(I_AD_Element.class, "WidgetSize", null);
    /** Column name WidgetSize */
    public static final String COLUMNNAME_WidgetSize = "WidgetSize";
}
