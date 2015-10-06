/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2007 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.model;


/** Generated Interface for AD_Field
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_Field 
{

    /** TableName=AD_Field */
    public static final String Table_Name = "AD_Field";

    /** AD_Table_ID=107 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 4 - System 
     */
    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(4);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Mandant.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client() throws RuntimeException;

    /** Column name AD_Column_ID */
    public static final String COLUMNNAME_AD_Column_ID = "AD_Column_ID";

	/** Set Spalte.
	  * Column in the table
	  */
	public void setAD_Column_ID (int AD_Column_ID);

	/** Get Spalte.
	  * Column in the table
	  */
	public int getAD_Column_ID();

	public org.compiere.model.I_AD_Column getAD_Column() throws RuntimeException;

	public void setAD_Column(org.compiere.model.I_AD_Column AD_Column);

    /** Column name AD_Field_ID */
    public static final String COLUMNNAME_AD_Field_ID = "AD_Field_ID";

	/** Set Feld.
	  * Field on a database table
	  */
	public void setAD_Field_ID (int AD_Field_ID);

	/** Get Feld.
	  * Field on a database table
	  */
	public int getAD_Field_ID();

    /** Column name AD_FieldGroup_ID */
    public static final String COLUMNNAME_AD_FieldGroup_ID = "AD_FieldGroup_ID";

	/** Set Feld-Gruppe.
	  * Logical grouping of fields
	  */
	public void setAD_FieldGroup_ID (int AD_FieldGroup_ID);

	/** Get Feld-Gruppe.
	  * Logical grouping of fields
	  */
	public int getAD_FieldGroup_ID();

	public org.compiere.model.I_AD_FieldGroup getAD_FieldGroup() throws RuntimeException;

	public void setAD_FieldGroup(org.compiere.model.I_AD_FieldGroup AD_FieldGroup);

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Sektion.
	  * Organisatorische Einheit des Mandanten
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Sektion.
	  * Organisatorische Einheit des Mandanten
	  */
	public int getAD_Org_ID();

	public org.compiere.model.I_AD_Org getAD_Org() throws RuntimeException;

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

    /** Column name AD_Reference_ID */
    public static final String COLUMNNAME_AD_Reference_ID = "AD_Reference_ID";

	/** Set Referenz.
	  * System Reference and Validation
	  */
	public void setAD_Reference_ID (int AD_Reference_ID);

	/** Get Referenz.
	  * System Reference and Validation
	  */
	public int getAD_Reference_ID();

	public org.compiere.model.I_AD_Reference getAD_Reference() throws RuntimeException;

	public void setAD_Reference(org.compiere.model.I_AD_Reference AD_Reference);

    /** Column name AD_Reference_Value_ID */
    public static final String COLUMNNAME_AD_Reference_Value_ID = "AD_Reference_Value_ID";

	/** Set Referenzschlüssel.
	  * Required to specify, if data type is Table or List
	  */
	public void setAD_Reference_Value_ID (int AD_Reference_Value_ID);

	/** Get Referenzschlüssel.
	  * Required to specify, if data type is Table or List
	  */
	public int getAD_Reference_Value_ID();

	public org.compiere.model.I_AD_Reference getAD_Reference_Value() throws RuntimeException;

	public void setAD_Reference_Value(org.compiere.model.I_AD_Reference AD_Reference_Value);

    /** Column name AD_Tab_ID */
    public static final String COLUMNNAME_AD_Tab_ID = "AD_Tab_ID";

	/** Set Register.
	  * Tab within a Window
	  */
	public void setAD_Tab_ID (int AD_Tab_ID);

	/** Get Register.
	  * Tab within a Window
	  */
	public int getAD_Tab_ID();

	public org.compiere.model.I_AD_Tab getAD_Tab() throws RuntimeException;

	public void setAD_Tab(org.compiere.model.I_AD_Tab AD_Tab);

    /** Column name AD_Val_Rule_ID */
    public static final String COLUMNNAME_AD_Val_Rule_ID = "AD_Val_Rule_ID";

	/** Set Dynamische Validierung.
	  * Dynamic Validation Rule
	  */
	public void setAD_Val_Rule_ID (int AD_Val_Rule_ID);

	/** Get Dynamische Validierung.
	  * Dynamic Validation Rule
	  */
	public int getAD_Val_Rule_ID();

	public org.compiere.model.I_AD_Val_Rule getAD_Val_Rule() throws RuntimeException;

	public void setAD_Val_Rule(org.compiere.model.I_AD_Val_Rule AD_Val_Rule);

    /** Column name ColumnDisplayLength */
    public static final String COLUMNNAME_ColumnDisplayLength = "ColumnDisplayLength";

	/** Set Column Display Length.
	  * Column display length for grid mode
	  */
	public void setColumnDisplayLength (int ColumnDisplayLength);

	/** Get Column Display Length.
	  * Column display length for grid mode
	  */
	public int getColumnDisplayLength();

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Erstellt.
	  * Date this record was created
	  */
	public java.sql.Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Erstellt durch.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name DefaultValue */
    public static final String COLUMNNAME_DefaultValue = "DefaultValue";

	/** Set Standardwert-Logik.
	  * Default value hierarchy, separated by ;

	  */
	public void setDefaultValue (java.lang.String DefaultValue);

	/** Get Standardwert-Logik.
	  * Default value hierarchy, separated by ;

	  */
	public java.lang.String getDefaultValue();

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Beschreibung	  */
	public void setDescription (java.lang.String Description);

	/** Get Beschreibung	  */
	public java.lang.String getDescription();

    /** Column name DisplayLength */
    public static final String COLUMNNAME_DisplayLength = "DisplayLength";

	/** Set Anzeigelänge.
	  * Length of the display in characters
	  */
	public void setDisplayLength (int DisplayLength);

	/** Get Anzeigelänge.
	  * Length of the display in characters
	  */
	public int getDisplayLength();

    /** Column name DisplayLogic */
    public static final String COLUMNNAME_DisplayLogic = "DisplayLogic";

	/** Set Anzeigelogik.
	  * If the Field is displayed, the result determines if the field is actually displayed
	  */
	public void setDisplayLogic (java.lang.String DisplayLogic);

	/** Get Anzeigelogik.
	  * If the Field is displayed, the result determines if the field is actually displayed
	  */
	public java.lang.String getDisplayLogic();

    /** Column name EntityType */
    public static final String COLUMNNAME_EntityType = "EntityType";

	/** Set Entitäts-Art.
	  * Dictionary Entity Type;
 Determines ownership and synchronization
	  */
	public void setEntityType (java.lang.String EntityType);

	/** Get Entitäts-Art.
	  * Dictionary Entity Type;
 Determines ownership and synchronization
	  */
	public java.lang.String getEntityType();

    /** Column name Help */
    public static final String COLUMNNAME_Help = "Help";

	/** Set Kommentar/Hilfe.
	  * Comment or Hint
	  */
	public void setHelp (java.lang.String Help);

	/** Get Kommentar/Hilfe.
	  * Comment or Hint
	  */
	public java.lang.String getHelp();

    /** Column name Included_Tab_ID */
    public static final String COLUMNNAME_Included_Tab_ID = "Included_Tab_ID";

	/** Set Included Tab.
	  * Included Tab in this Tab (Master Dateail)
	  */
	public void setIncluded_Tab_ID (int Included_Tab_ID);

	/** Get Included Tab.
	  * Included Tab in this Tab (Master Dateail)
	  */
	public int getIncluded_Tab_ID();

	public org.compiere.model.I_AD_Tab getIncluded_Tab() throws RuntimeException;

	public void setIncluded_Tab(org.compiere.model.I_AD_Tab Included_Tab);

    /** Column name InfoFactoryClass */
    public static final String COLUMNNAME_InfoFactoryClass = "InfoFactoryClass";

	/** Set Info Factory Class.
	  * Fully qualified class name that implements the InfoFactory interface
	  */
	public void setInfoFactoryClass (java.lang.String InfoFactoryClass);

	/** Get Info Factory Class.
	  * Fully qualified class name that implements the InfoFactory interface
	  */
	public java.lang.String getInfoFactoryClass();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Aktiv.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Aktiv.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name IsCentrallyMaintained */
    public static final String COLUMNNAME_IsCentrallyMaintained = "IsCentrallyMaintained";

	/** Set Zentral verwaltet.
	  * Information maintained in System Element table
	  */
	public void setIsCentrallyMaintained (boolean IsCentrallyMaintained);

	/** Get Zentral verwaltet.
	  * Information maintained in System Element table
	  */
	public boolean isCentrallyMaintained();

    /** Column name IsDisplayed */
    public static final String COLUMNNAME_IsDisplayed = "IsDisplayed";

	/** Set Displayed.
	  * Determines, if this field is displayed
	  */
	public void setIsDisplayed (boolean IsDisplayed);

	/** Get Displayed.
	  * Determines, if this field is displayed
	  */
	public boolean isDisplayed();

    /** Column name IsDisplayedGrid */
    public static final String COLUMNNAME_IsDisplayedGrid = "IsDisplayedGrid";

	/** Set Displayed in Grid.
	  * Determines, if this field is displayed in grid mode
	  */
	public void setIsDisplayedGrid (boolean IsDisplayedGrid);

	/** Get Displayed in Grid.
	  * Determines, if this field is displayed in grid mode
	  */
	public boolean isDisplayedGrid();

    /** Column name IsEncrypted */
    public static final String COLUMNNAME_IsEncrypted = "IsEncrypted";

	/** Set Encrypted.
	  * Display or Storage is encrypted
	  */
	public void setIsEncrypted (boolean IsEncrypted);

	/** Get Encrypted.
	  * Display or Storage is encrypted
	  */
	public boolean isEncrypted();

    /** Column name IsFieldOnly */
    public static final String COLUMNNAME_IsFieldOnly = "IsFieldOnly";

	/** Set Field Only.
	  * Label is not displayed
	  */
	public void setIsFieldOnly (boolean IsFieldOnly);

	/** Get Field Only.
	  * Label is not displayed
	  */
	public boolean isFieldOnly();

    /** Column name IsHeading */
    public static final String COLUMNNAME_IsHeading = "IsHeading";

	/** Set Heading only.
	  * Field without Column - Only label is displayed
	  */
	public void setIsHeading (boolean IsHeading);

	/** Get Heading only.
	  * Field without Column - Only label is displayed
	  */
	public boolean isHeading();

    /** Column name IsMandatory */
    public static final String COLUMNNAME_IsMandatory = "IsMandatory";

	/** Set Pflichtangabe.
	  * Data entry is required in this column
	  */
	public void setIsMandatory (java.lang.String IsMandatory);

	/** Get Pflichtangabe.
	  * Data entry is required in this column
	  */
	public java.lang.String getIsMandatory();

    /** Column name IsReadOnly */
    public static final String COLUMNNAME_IsReadOnly = "IsReadOnly";

	/** Set Schreibgeschützt.
	  * Field is read only
	  */
	public void setIsReadOnly (boolean IsReadOnly);

	/** Get Schreibgeschützt.
	  * Field is read only
	  */
	public boolean isReadOnly();

    /** Column name IsSameLine */
    public static final String COLUMNNAME_IsSameLine = "IsSameLine";

	/** Set Same Line.
	  * Displayed on same line as previous field
	  */
	public void setIsSameLine (boolean IsSameLine);

	/** Get Same Line.
	  * Displayed on same line as previous field
	  */
	public boolean isSameLine();

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (java.lang.String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public java.lang.String getName();

    /** Column name ObscureType */
    public static final String COLUMNNAME_ObscureType = "ObscureType";

	/** Set Obscure.
	  * Type of obscuring the data (limiting the display)
	  */
	public void setObscureType (java.lang.String ObscureType);

	/** Get Obscure.
	  * Type of obscuring the data (limiting the display)
	  */
	public java.lang.String getObscureType();

    /** Column name SeqNo */
    public static final String COLUMNNAME_SeqNo = "SeqNo";

	/** Set Reihenfolge.
	  * Method of ordering records;
 lowest number comes first
	  */
	public void setSeqNo (int SeqNo);

	/** Get Reihenfolge.
	  * Method of ordering records;
 lowest number comes first
	  */
	public int getSeqNo();

    /** Column name SeqNoGrid */
    public static final String COLUMNNAME_SeqNoGrid = "SeqNoGrid";

	/** Set Reihenfolge (grid).
	  * Zur Bestimmung der Reihenfolge der Einträge;
 die kleinste Zahl kommt zuerst
	  */
	public void setSeqNoGrid (int SeqNoGrid);

	/** Get Reihenfolge (grid).
	  * Zur Bestimmung der Reihenfolge der Einträge;
 die kleinste Zahl kommt zuerst
	  */
	public int getSeqNoGrid();

    /** Column name SortNo */
    public static final String COLUMNNAME_SortNo = "SortNo";

	/** Set Record Sort No.
	  * Determines in what order the records are displayed
	  */
	public void setSortNo (java.math.BigDecimal SortNo);

	/** Get Record Sort No.
	  * Determines in what order the records are displayed
	  */
	public java.math.BigDecimal getSortNo();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Aktualisiert.
	  * Date this record was updated
	  */
	public java.sql.Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Aktualisiert durch.
	  * User who updated this records
	  */
	public int getUpdatedBy();
}
