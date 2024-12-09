<<<<<<< HEAD
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


/** Generated Interface for EXP_FormatLine
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_EXP_FormatLine 
{

    /** TableName=EXP_FormatLine */
    public static final String Table_Name = "EXP_FormatLine";

    /** AD_Table_ID=53073 */
    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 6 - System - Client 
     */
    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(6);

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

    /** Column name AD_Reference_Override_ID */
    public static final String COLUMNNAME_AD_Reference_Override_ID = "AD_Reference_Override_ID";

	/** Set Referenz abw.	  */
	public void setAD_Reference_Override_ID (int AD_Reference_Override_ID);

	/** Get Referenz abw.	  */
	public int getAD_Reference_Override_ID();

	public org.compiere.model.I_AD_Reference getAD_Reference_Override() throws RuntimeException;

	public void setAD_Reference_Override(org.compiere.model.I_AD_Reference AD_Reference_Override);

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

    /** Column name DateFormat */
    public static final String COLUMNNAME_DateFormat = "DateFormat";

	/** Set Datums-Format.
	  * Date format used in the imput format
	  */
	public void setDateFormat (java.lang.String DateFormat);

	/** Get Datums-Format.
	  * Date format used in the imput format
	  */
	public java.lang.String getDateFormat();

    /** Column name DefaultValue */
    public static final String COLUMNNAME_DefaultValue = "DefaultValue";

	/** Set Standardwert-Logik.
	  * Alternativen zur Bestimmung eines Standardwertes, separiert durch Semikolon
	  */
	public void setDefaultValue (java.lang.String DefaultValue);

	/** Get Standardwert-Logik.
	  * Alternativen zur Bestimmung eines Standardwertes, separiert durch Semikolon
	  */
	public java.lang.String getDefaultValue();

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Beschreibung	  */
	public void setDescription (java.lang.String Description);

	/** Get Beschreibung	  */
	public java.lang.String getDescription();

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

    /** Column name EXP_EmbeddedFormat_ID */
    public static final String COLUMNNAME_EXP_EmbeddedFormat_ID = "EXP_EmbeddedFormat_ID";

	/** Set Embedded Format	  */
	public void setEXP_EmbeddedFormat_ID (int EXP_EmbeddedFormat_ID);

	/** Get Embedded Format	  */
	public int getEXP_EmbeddedFormat_ID();

	public org.compiere.model.I_EXP_Format getEXP_EmbeddedFormat() throws RuntimeException;

	public void setEXP_EmbeddedFormat(org.compiere.model.I_EXP_Format EXP_EmbeddedFormat);

    /** Column name EXP_Format_ID */
    public static final String COLUMNNAME_EXP_Format_ID = "EXP_Format_ID";

	/** Set Export Format	  */
	public void setEXP_Format_ID (int EXP_Format_ID);

	/** Get Export Format	  */
	public int getEXP_Format_ID();

	public org.compiere.model.I_EXP_Format getEXP_Format() throws RuntimeException;

	public void setEXP_Format(org.compiere.model.I_EXP_Format EXP_Format);

    /** Column name EXP_FormatLine_ID */
    public static final String COLUMNNAME_EXP_FormatLine_ID = "EXP_FormatLine_ID";

	/** Set Format Line	  */
	public void setEXP_FormatLine_ID (int EXP_FormatLine_ID);

	/** Get Format Line	  */
	public int getEXP_FormatLine_ID();

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

    /** Column name IsMandatory */
    public static final String COLUMNNAME_IsMandatory = "IsMandatory";

	/** Set Pflichtangabe.
	  * Data entry is required in this column
	  */
	public void setIsMandatory (boolean IsMandatory);

	/** Get Pflichtangabe.
	  * Data entry is required in this column
	  */
	public boolean isMandatory();

    /** Column name IsPartUniqueIndex */
    public static final String COLUMNNAME_IsPartUniqueIndex = "IsPartUniqueIndex";

	/** Set Is Part Unique Index	  */
	public void setIsPartUniqueIndex (boolean IsPartUniqueIndex);

	/** Get Is Part Unique Index	  */
	public boolean isPartUniqueIndex();

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

    /** Column name Position */
    public static final String COLUMNNAME_Position = "Position";

	/** Set Position	  */
	public void setPosition (int Position);

	/** Get Position	  */
	public int getPosition();

    /** Column name Type */
    public static final String COLUMNNAME_Type = "Type";

	/** Set Art.
	  * Type of Validation (SQL, Java Script, Java Language)
	  */
	public void setType (java.lang.String Type);

	/** Get Art.
	  * Type of Validation (SQL, Java Script, Java Language)
	  */
	public java.lang.String getType();

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

    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";

	/** Set Suchschlüssel.
	  * Search key for the record in the format required - must be unique
	  */
	public void setValue (java.lang.String Value);

	/** Get Suchschlüssel.
	  * Search key for the record in the format required - must be unique
	  */
	public java.lang.String getValue();
=======
package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for EXP_FormatLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_EXP_FormatLine 
{

	String Table_Name = "EXP_FormatLine";

//	/** AD_Table_ID=53073 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Link Column.
	 * Link Column for Multi-Parent tables
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Column_ID (int AD_Column_ID);

	/**
	 * Get Link Column.
	 * Link Column for Multi-Parent tables
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Column_ID();

	org.compiere.model.I_AD_Column getAD_Column();

	void setAD_Column(org.compiere.model.I_AD_Column AD_Column);

	ModelColumn<I_EXP_FormatLine, org.compiere.model.I_AD_Column> COLUMN_AD_Column_ID = new ModelColumn<>(I_EXP_FormatLine.class, "AD_Column_ID", org.compiere.model.I_AD_Column.class);
	String COLUMNNAME_AD_Column_ID = "AD_Column_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Reference Overwrite.
	 * System Reference - optional Overwrite
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setAD_Reference_ID (int AD_Reference_ID);

	/**
	 * Get Reference Overwrite.
	 * System Reference - optional Overwrite
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	int getAD_Reference_ID();

	@Deprecated
	@Nullable org.compiere.model.I_AD_Reference getAD_Reference();

	@Deprecated
	void setAD_Reference(@Nullable org.compiere.model.I_AD_Reference AD_Reference);

	ModelColumn<I_EXP_FormatLine, org.compiere.model.I_AD_Reference> COLUMN_AD_Reference_ID = new ModelColumn<>(I_EXP_FormatLine.class, "AD_Reference_ID", org.compiere.model.I_AD_Reference.class);
	String COLUMNNAME_AD_Reference_ID = "AD_Reference_ID";

	/**
	 * Set Referenz abw..
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Reference_Override_ID (int AD_Reference_Override_ID);

	/**
	 * Get Referenz abw..
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Reference_Override_ID();

	@Nullable org.compiere.model.I_AD_Reference getAD_Reference_Override();

	void setAD_Reference_Override(@Nullable org.compiere.model.I_AD_Reference AD_Reference_Override);

	ModelColumn<I_EXP_FormatLine, org.compiere.model.I_AD_Reference> COLUMN_AD_Reference_Override_ID = new ModelColumn<>(I_EXP_FormatLine.class, "AD_Reference_Override_ID", org.compiere.model.I_AD_Reference.class);
	String COLUMNNAME_AD_Reference_Override_ID = "AD_Reference_Override_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_EXP_FormatLine, Object> COLUMN_Created = new ModelColumn<>(I_EXP_FormatLine.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Date Format.
	 * Date format used in the input format
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateFormat (@Nullable java.lang.String DateFormat);

	/**
	 * Get Date Format.
	 * Date format used in the input format
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDateFormat();

	ModelColumn<I_EXP_FormatLine, Object> COLUMN_DateFormat = new ModelColumn<>(I_EXP_FormatLine.class, "DateFormat", null);
	String COLUMNNAME_DateFormat = "DateFormat";

	/**
	 * Set Default Logic.
	 * Default value hierarchy, separated by ;

	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDefaultValue (@Nullable java.lang.String DefaultValue);

	/**
	 * Get Default Logic.
	 * Default value hierarchy, separated by ;

	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDefaultValue();

	ModelColumn<I_EXP_FormatLine, Object> COLUMN_DefaultValue = new ModelColumn<>(I_EXP_FormatLine.class, "DefaultValue", null);
	String COLUMNNAME_DefaultValue = "DefaultValue";

	/**
	 * Set Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_EXP_FormatLine, Object> COLUMN_Description = new ModelColumn<>(I_EXP_FormatLine.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Entity Type.
	 * Entity Type
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setEntityType (java.lang.String EntityType);

	/**
	 * Get Entity Type.
	 * Entity Type
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getEntityType();

	ModelColumn<I_EXP_FormatLine, Object> COLUMN_EntityType = new ModelColumn<>(I_EXP_FormatLine.class, "EntityType", null);
	String COLUMNNAME_EntityType = "EntityType";

	/**
	 * Set Embedded Format.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEXP_EmbeddedFormat_ID (int EXP_EmbeddedFormat_ID);

	/**
	 * Get Embedded Format.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getEXP_EmbeddedFormat_ID();

	@Nullable org.compiere.model.I_EXP_Format getEXP_EmbeddedFormat();

	void setEXP_EmbeddedFormat(@Nullable org.compiere.model.I_EXP_Format EXP_EmbeddedFormat);

	ModelColumn<I_EXP_FormatLine, org.compiere.model.I_EXP_Format> COLUMN_EXP_EmbeddedFormat_ID = new ModelColumn<>(I_EXP_FormatLine.class, "EXP_EmbeddedFormat_ID", org.compiere.model.I_EXP_Format.class);
	String COLUMNNAME_EXP_EmbeddedFormat_ID = "EXP_EmbeddedFormat_ID";

	/**
	 * Set Export Format.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEXP_Format_ID (int EXP_Format_ID);

	/**
	 * Get Export Format.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getEXP_Format_ID();

	@Nullable org.compiere.model.I_EXP_Format getEXP_Format();

	void setEXP_Format(@Nullable org.compiere.model.I_EXP_Format EXP_Format);

	ModelColumn<I_EXP_FormatLine, org.compiere.model.I_EXP_Format> COLUMN_EXP_Format_ID = new ModelColumn<>(I_EXP_FormatLine.class, "EXP_Format_ID", org.compiere.model.I_EXP_Format.class);
	String COLUMNNAME_EXP_Format_ID = "EXP_Format_ID";

	/**
	 * Set Format Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setEXP_FormatLine_ID (int EXP_FormatLine_ID);

	/**
	 * Get Format Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getEXP_FormatLine_ID();

	ModelColumn<I_EXP_FormatLine, Object> COLUMN_EXP_FormatLine_ID = new ModelColumn<>(I_EXP_FormatLine.class, "EXP_FormatLine_ID", null);
	String COLUMNNAME_EXP_FormatLine_ID = "EXP_FormatLine_ID";

	/**
	 * Set Filter Operator.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setFilterOperator (java.lang.String FilterOperator);

	/**
	 * Get Filter Operator.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getFilterOperator();

	ModelColumn<I_EXP_FormatLine, Object> COLUMN_FilterOperator = new ModelColumn<>(I_EXP_FormatLine.class, "FilterOperator", null);
	String COLUMNNAME_FilterOperator = "FilterOperator";

	/**
	 * Set Help.
	 * Comment or Hint
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setHelp (@Nullable java.lang.String Help);

	/**
	 * Get Help.
	 * Comment or Hint
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getHelp();

	ModelColumn<I_EXP_FormatLine, Object> COLUMN_Help = new ModelColumn<>(I_EXP_FormatLine.class, "Help", null);
	String COLUMNNAME_Help = "Help";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isActive();

	ModelColumn<I_EXP_FormatLine, Object> COLUMN_IsActive = new ModelColumn<>(I_EXP_FormatLine.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set mandatory.
	 * Data entry is required in this column
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsMandatory (boolean IsMandatory);

	/**
	 * Get mandatory.
	 * Data entry is required in this column
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isMandatory();

	ModelColumn<I_EXP_FormatLine, Object> COLUMN_IsMandatory = new ModelColumn<>(I_EXP_FormatLine.class, "IsMandatory", null);
	String COLUMNNAME_IsMandatory = "IsMandatory";

	/**
	 * Set Is Part Unique Index.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsPartUniqueIndex (boolean IsPartUniqueIndex);

	/**
	 * Get Is Part Unique Index.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isPartUniqueIndex();

	ModelColumn<I_EXP_FormatLine, Object> COLUMN_IsPartUniqueIndex = new ModelColumn<>(I_EXP_FormatLine.class, "IsPartUniqueIndex", null);
	String COLUMNNAME_IsPartUniqueIndex = "IsPartUniqueIndex";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setName (java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getName();

	ModelColumn<I_EXP_FormatLine, Object> COLUMN_Name = new ModelColumn<>(I_EXP_FormatLine.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Position.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPosition (int Position);

	/**
	 * Get Position.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPosition();

	ModelColumn<I_EXP_FormatLine, Object> COLUMN_Position = new ModelColumn<>(I_EXP_FormatLine.class, "Position", null);
	String COLUMNNAME_Position = "Position";

	/**
	 * Set Type.
	 * Type of Validation (SQL, Java Script, Java Language)
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setType (java.lang.String Type);

	/**
	 * Get Type.
	 * Type of Validation (SQL, Java Script, Java Language)
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getType();

	ModelColumn<I_EXP_FormatLine, Object> COLUMN_Type = new ModelColumn<>(I_EXP_FormatLine.class, "Type", null);
	String COLUMNNAME_Type = "Type";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_EXP_FormatLine, Object> COLUMN_Updated = new ModelColumn<>(I_EXP_FormatLine.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setValue (java.lang.String Value);

	/**
	 * Get Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getValue();

	ModelColumn<I_EXP_FormatLine, Object> COLUMN_Value = new ModelColumn<>(I_EXP_FormatLine.class, "Value", null);
	String COLUMNNAME_Value = "Value";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}
