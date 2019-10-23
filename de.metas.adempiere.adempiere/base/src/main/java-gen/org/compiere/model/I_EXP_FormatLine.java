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
}
