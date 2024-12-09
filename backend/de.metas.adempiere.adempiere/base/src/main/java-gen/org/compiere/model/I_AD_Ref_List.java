package org.compiere.model;

<<<<<<< HEAD

/** Generated Interface for AD_Ref_List
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_Ref_List 
{

    /** TableName=AD_Ref_List */
    public static final String Table_Name = "AD_Ref_List";

    /** AD_Table_ID=104 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 4 - System
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(4);

    /** Load Meta Data */

	/**
	 * Get Mandant.
=======
import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for AD_Ref_List
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_AD_Ref_List 
{

	String Table_Name = "AD_Ref_List";

//	/** AD_Table_ID=104 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getAD_Client_ID();

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Meldung.
=======
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set System Color.
	 * Color for backgrounds or indicators
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Color_ID (int AD_Color_ID);

	/**
	 * Get System Color.
	 * Color for backgrounds or indicators
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Color_ID();

	@Nullable org.compiere.model.I_AD_Color getAD_Color();

	void setAD_Color(@Nullable org.compiere.model.I_AD_Color AD_Color);

	ModelColumn<I_AD_Ref_List, org.compiere.model.I_AD_Color> COLUMN_AD_Color_ID = new ModelColumn<>(I_AD_Ref_List.class, "AD_Color_ID", org.compiere.model.I_AD_Color.class);
	String COLUMNNAME_AD_Color_ID = "AD_Color_ID";

	/**
	 * Set Message.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * System Message
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setAD_Message_ID (int AD_Message_ID);

	/**
	 * Get Meldung.
=======
	void setAD_Message_ID (int AD_Message_ID);

	/**
	 * Get Message.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * System Message
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getAD_Message_ID();

	public org.compiere.model.I_AD_Message getAD_Message();

	public void setAD_Message(org.compiere.model.I_AD_Message AD_Message);

    /** Column definition for AD_Message_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Ref_List, org.compiere.model.I_AD_Message> COLUMN_AD_Message_ID = new org.adempiere.model.ModelColumn<I_AD_Ref_List, org.compiere.model.I_AD_Message>(I_AD_Ref_List.class, "AD_Message_ID", org.compiere.model.I_AD_Message.class);
    /** Column name AD_Message_ID */
    public static final String COLUMNNAME_AD_Message_ID = "AD_Message_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
=======
	int getAD_Message_ID();

	String COLUMNNAME_AD_Message_ID = "AD_Message_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
=======
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getAD_Org_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Referenzliste.
	 * Reference List based on Table
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Ref_List_ID (int AD_Ref_List_ID);

	/**
	 * Get Referenzliste.
	 * Reference List based on Table
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Ref_List_ID();

    /** Column definition for AD_Ref_List_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Ref_List, Object> COLUMN_AD_Ref_List_ID = new org.adempiere.model.ModelColumn<I_AD_Ref_List, Object>(I_AD_Ref_List.class, "AD_Ref_List_ID", null);
    /** Column name AD_Ref_List_ID */
    public static final String COLUMNNAME_AD_Ref_List_ID = "AD_Ref_List_ID";
=======
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Referenz.
	 * System Reference and Validation
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setAD_Reference_ID (int AD_Reference_ID);
=======
	void setAD_Reference_ID (int AD_Reference_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Referenz.
	 * System Reference and Validation
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getAD_Reference_ID();

	public org.compiere.model.I_AD_Reference getAD_Reference();

	public void setAD_Reference(org.compiere.model.I_AD_Reference AD_Reference);

    /** Column definition for AD_Reference_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Ref_List, org.compiere.model.I_AD_Reference> COLUMN_AD_Reference_ID = new org.adempiere.model.ModelColumn<I_AD_Ref_List, org.compiere.model.I_AD_Reference>(I_AD_Ref_List.class, "AD_Reference_ID", org.compiere.model.I_AD_Reference.class);
    /** Column name AD_Reference_ID */
    public static final String COLUMNNAME_AD_Reference_ID = "AD_Reference_ID";

	/**
	 * Get Erstellt.
=======
	int getAD_Reference_ID();

	org.compiere.model.I_AD_Reference getAD_Reference();

	void setAD_Reference(org.compiere.model.I_AD_Reference AD_Reference);

	ModelColumn<I_AD_Ref_List, org.compiere.model.I_AD_Reference> COLUMN_AD_Reference_ID = new ModelColumn<>(I_AD_Ref_List.class, "AD_Reference_ID", org.compiere.model.I_AD_Reference.class);
	String COLUMNNAME_AD_Reference_ID = "AD_Reference_ID";

	/**
	 * Set Referenzliste.
	 * Reference List based on Table
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Ref_List_ID (int AD_Ref_List_ID);

	/**
	 * Get Referenzliste.
	 * Reference List based on Table
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Ref_List_ID();

	ModelColumn<I_AD_Ref_List, Object> COLUMN_AD_Ref_List_ID = new ModelColumn<>(I_AD_Ref_List.class, "AD_Ref_List_ID", null);
	String COLUMNNAME_AD_Ref_List_ID = "AD_Ref_List_ID";

	/**
	 * Get Created.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_AD_Ref_List, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_Ref_List, Object>(I_AD_Ref_List.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
=======
	java.sql.Timestamp getCreated();

	ModelColumn<I_AD_Ref_List, Object> COLUMN_Created = new ModelColumn<>(I_AD_Ref_List.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getCreatedBy();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Beschreibung.
=======
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Description.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setDescription (java.lang.String Description);

	/**
	 * Get Beschreibung.
=======
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_AD_Ref_List, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_AD_Ref_List, Object>(I_AD_Ref_List.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";
=======
	@Nullable java.lang.String getDescription();

	ModelColumn<I_AD_Ref_List, Object> COLUMN_Description = new ModelColumn<>(I_AD_Ref_List.class, "Description", null);
	String COLUMNNAME_Description = "Description";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Entitäts-Art.
	 * Dictionary Entity Type;
 Determines ownership and synchronization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setEntityType (java.lang.String EntityType);
=======
	void setEntityType (java.lang.String EntityType);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Entitäts-Art.
	 * Dictionary Entity Type;
 Determines ownership and synchronization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getEntityType();

    /** Column definition for EntityType */
    public static final org.adempiere.model.ModelColumn<I_AD_Ref_List, Object> COLUMN_EntityType = new org.adempiere.model.ModelColumn<I_AD_Ref_List, Object>(I_AD_Ref_List.class, "EntityType", null);
    /** Column name EntityType */
    public static final String COLUMNNAME_EntityType = "EntityType";

	/**
	 * Set Aktiv.
=======
	java.lang.String getEntityType();

	ModelColumn<I_AD_Ref_List, Object> COLUMN_EntityType = new ModelColumn<>(I_AD_Ref_List.class, "EntityType", null);
	String COLUMNNAME_EntityType = "EntityType";

	/**
	 * Set Active.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
=======
	void setIsActive (boolean IsActive);

	/**
	 * Get Active.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_AD_Ref_List, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_Ref_List, Object>(I_AD_Ref_List.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";
=======
	boolean isActive();

	ModelColumn<I_AD_Ref_List, Object> COLUMN_IsActive = new ModelColumn<>(I_AD_Ref_List.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setName (java.lang.String Name);
=======
	void setName (java.lang.String Name);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getName();

    /** Column definition for Name */
    public static final org.adempiere.model.ModelColumn<I_AD_Ref_List, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_AD_Ref_List, Object>(I_AD_Ref_List.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Get Aktualisiert.
=======
	java.lang.String getName();

	ModelColumn<I_AD_Ref_List, Object> COLUMN_Name = new ModelColumn<>(I_AD_Ref_List.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Get Updated.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_AD_Ref_List, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_Ref_List, Object>(I_AD_Ref_List.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
=======
	java.sql.Timestamp getUpdated();

	ModelColumn<I_AD_Ref_List, Object> COLUMN_Updated = new ModelColumn<>(I_AD_Ref_List.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getUpdatedBy();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Gültig ab.
	 * Valid from including this date (first day)
=======
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Valid From.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setValidFrom (java.sql.Timestamp ValidFrom);

	/**
	 * Get Gültig ab.
	 * Valid from including this date (first day)
=======
	void setValidFrom (@Nullable java.sql.Timestamp ValidFrom);

	/**
	 * Get Valid From.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.sql.Timestamp getValidFrom();

    /** Column definition for ValidFrom */
    public static final org.adempiere.model.ModelColumn<I_AD_Ref_List, Object> COLUMN_ValidFrom = new org.adempiere.model.ModelColumn<I_AD_Ref_List, Object>(I_AD_Ref_List.class, "ValidFrom", null);
    /** Column name ValidFrom */
    public static final String COLUMNNAME_ValidFrom = "ValidFrom";

	/**
	 * Set Gültig bis.
=======
	@Nullable java.sql.Timestamp getValidFrom();

	ModelColumn<I_AD_Ref_List, Object> COLUMN_ValidFrom = new ModelColumn<>(I_AD_Ref_List.class, "ValidFrom", null);
	String COLUMNNAME_ValidFrom = "ValidFrom";

	/**
	 * Set Valid to.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Valid to including this date (last day)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setValidTo (java.sql.Timestamp ValidTo);

	/**
	 * Get Gültig bis.
=======
	void setValidTo (@Nullable java.sql.Timestamp ValidTo);

	/**
	 * Get Valid to.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Valid to including this date (last day)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.sql.Timestamp getValidTo();

    /** Column definition for ValidTo */
    public static final org.adempiere.model.ModelColumn<I_AD_Ref_List, Object> COLUMN_ValidTo = new org.adempiere.model.ModelColumn<I_AD_Ref_List, Object>(I_AD_Ref_List.class, "ValidTo", null);
    /** Column name ValidTo */
    public static final String COLUMNNAME_ValidTo = "ValidTo";

	/**
	 * Set Suchschlüssel.
=======
	@Nullable java.sql.Timestamp getValidTo();

	ModelColumn<I_AD_Ref_List, Object> COLUMN_ValidTo = new ModelColumn<>(I_AD_Ref_List.class, "ValidTo", null);
	String COLUMNNAME_ValidTo = "ValidTo";

	/**
	 * Set Search Key.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setValue (java.lang.String Value);

	/**
	 * Get Suchschlüssel.
=======
	void setValue (java.lang.String Value);

	/**
	 * Get Search Key.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getValue();

    /** Column definition for Value */
    public static final org.adempiere.model.ModelColumn<I_AD_Ref_List, Object> COLUMN_Value = new org.adempiere.model.ModelColumn<I_AD_Ref_List, Object>(I_AD_Ref_List.class, "Value", null);
    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";
=======
	java.lang.String getValue();

	ModelColumn<I_AD_Ref_List, Object> COLUMN_Value = new ModelColumn<>(I_AD_Ref_List.class, "Value", null);
	String COLUMNNAME_Value = "Value";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Name (technical).
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setValueName (java.lang.String ValueName);
=======
	void setValueName (@Nullable java.lang.String ValueName);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Name (technical).
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getValueName();

    /** Column definition for ValueName */
    public static final org.adempiere.model.ModelColumn<I_AD_Ref_List, Object> COLUMN_ValueName = new org.adempiere.model.ModelColumn<I_AD_Ref_List, Object>(I_AD_Ref_List.class, "ValueName", null);
    /** Column name ValueName */
    public static final String COLUMNNAME_ValueName = "ValueName";
=======
	@Nullable java.lang.String getValueName();

	ModelColumn<I_AD_Ref_List, Object> COLUMN_ValueName = new ModelColumn<>(I_AD_Ref_List.class, "ValueName", null);
	String COLUMNNAME_ValueName = "ValueName";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}
