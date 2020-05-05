package de.metas.javaclasses.model;

import org.compiere.model.I_AD_Column;

/** Generated Interface for AD_JavaClass
 *  @author Adempiere (generated)
 */
@SuppressWarnings("javadoc")
public interface I_AD_JavaClass
{

    /** TableName=AD_JavaClass */
    public static final String Table_Name = "AD_JavaClass";

    /** AD_Table_ID=540520 */
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
    public static final org.adempiere.model.ModelColumn<I_AD_JavaClass, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_AD_JavaClass, org.compiere.model.I_AD_Client>(I_AD_JavaClass.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";


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
    public static final org.adempiere.model.ModelColumn<I_AD_Column, Object> COLUMN_EntityType = new org.adempiere.model.ModelColumn<I_AD_Column, Object>(I_AD_Column.class, "EntityType", null);
    /** Column name EntityType */
    public static final String COLUMNNAME_EntityType = "EntityType";

	/**
	 * Set Java Klasse.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_JavaClass_ID (int AD_JavaClass_ID);

	/**
	 * Get Java Klasse.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_JavaClass_ID();

    /** Column definition for AD_JavaClass_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_JavaClass, Object> COLUMN_AD_JavaClass_ID = new org.adempiere.model.ModelColumn<I_AD_JavaClass, Object>(I_AD_JavaClass.class, "AD_JavaClass_ID", null);
    /** Column name AD_JavaClass_ID */
    public static final String COLUMNNAME_AD_JavaClass_ID = "AD_JavaClass_ID";

	/**
	 * Set Java Class Type.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_JavaClass_Type_ID (int AD_JavaClass_Type_ID);

	/**
	 * Get Java Class Type.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_JavaClass_Type_ID();

	public de.metas.javaclasses.model.I_AD_JavaClass_Type getAD_JavaClass_Type();

	public void setAD_JavaClass_Type(de.metas.javaclasses.model.I_AD_JavaClass_Type AD_JavaClass_Type);

    /** Column definition for AD_JavaClass_Type_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_JavaClass, de.metas.javaclasses.model.I_AD_JavaClass_Type> COLUMN_AD_JavaClass_Type_ID = new org.adempiere.model.ModelColumn<I_AD_JavaClass, de.metas.javaclasses.model.I_AD_JavaClass_Type>(I_AD_JavaClass.class, "AD_JavaClass_Type_ID", de.metas.javaclasses.model.I_AD_JavaClass_Type.class);
    /** Column name AD_JavaClass_Type_ID */
    public static final String COLUMNNAME_AD_JavaClass_Type_ID = "AD_JavaClass_Type_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_JavaClass, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_AD_JavaClass, org.compiere.model.I_AD_Org>(I_AD_JavaClass.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Java-Klasse.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setClassname (java.lang.String Classname);

	/**
	 * Get Java-Klasse.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getClassname();

    /** Column definition for Classname */
    public static final org.adempiere.model.ModelColumn<I_AD_JavaClass, Object> COLUMN_Classname = new org.adempiere.model.ModelColumn<I_AD_JavaClass, Object>(I_AD_JavaClass.class, "Classname", null);
    /** Column name Classname */
    public static final String COLUMNNAME_Classname = "Classname";

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
    public static final org.adempiere.model.ModelColumn<I_AD_JavaClass, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_JavaClass, Object>(I_AD_JavaClass.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_JavaClass, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_AD_JavaClass, org.compiere.model.I_AD_User>(I_AD_JavaClass.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Beschreibung.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescription (java.lang.String Description);

	/**
	 * Get Beschreibung.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_AD_JavaClass, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_AD_JavaClass, Object>(I_AD_JavaClass.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

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
    public static final org.adempiere.model.ModelColumn<I_AD_JavaClass, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_JavaClass, Object>(I_AD_JavaClass.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Interface.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsInterface (boolean IsInterface);

	/**
	 * Get Interface.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isInterface();

    /** Column definition for IsInterface */
    public static final org.adempiere.model.ModelColumn<I_AD_JavaClass, Object> COLUMN_IsInterface = new org.adempiere.model.ModelColumn<I_AD_JavaClass, Object>(I_AD_JavaClass.class, "IsInterface", null);
    /** Column name IsInterface */
    public static final String COLUMNNAME_IsInterface = "IsInterface";

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
    public static final org.adempiere.model.ModelColumn<I_AD_JavaClass, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_AD_JavaClass, Object>(I_AD_JavaClass.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

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
    public static final org.adempiere.model.ModelColumn<I_AD_JavaClass, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_JavaClass, Object>(I_AD_JavaClass.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_JavaClass, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_AD_JavaClass, org.compiere.model.I_AD_User>(I_AD_JavaClass.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
