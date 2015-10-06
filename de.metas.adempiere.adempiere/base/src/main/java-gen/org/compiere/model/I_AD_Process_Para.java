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


/** Generated Interface for AD_Process_Para
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_Process_Para 
{

    /** TableName=AD_Process_Para */
    public static final String Table_Name = "AD_Process_Para";

    /** AD_Table_ID=285 */
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

    /** Column name AD_Element_ID */
    public static final String COLUMNNAME_AD_Element_ID = "AD_Element_ID";

	/** Set System-Element.
	  * System Element enables the central maintenance of column description and help.
	  */
	public void setAD_Element_ID (int AD_Element_ID);

	/** Get System-Element.
	  * System Element enables the central maintenance of column description and help.
	  */
	public int getAD_Element_ID();

	public org.compiere.model.I_AD_Element getAD_Element() throws RuntimeException;

	public void setAD_Element(org.compiere.model.I_AD_Element AD_Element);

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

    /** Column name AD_Process_ID */
    public static final String COLUMNNAME_AD_Process_ID = "AD_Process_ID";

	/** Set Prozess.
	  * Process or Report
	  */
	public void setAD_Process_ID (int AD_Process_ID);

	/** Get Prozess.
	  * Process or Report
	  */
	public int getAD_Process_ID();

	public org.compiere.model.I_AD_Process getAD_Process() throws RuntimeException;

	public void setAD_Process(org.compiere.model.I_AD_Process AD_Process);

    /** Column name AD_Process_Para_ID */
    public static final String COLUMNNAME_AD_Process_Para_ID = "AD_Process_Para_ID";

	/** Set Prozess-Parameter	  */
	public void setAD_Process_Para_ID (int AD_Process_Para_ID);

	/** Get Prozess-Parameter	  */
	public int getAD_Process_Para_ID();

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

    /** Column name ColumnName */
    public static final String COLUMNNAME_ColumnName = "ColumnName";

	/** Set Spaltenname.
	  * Name of the column in the database
	  */
	public void setColumnName (java.lang.String ColumnName);

	/** Get Spaltenname.
	  * Name of the column in the database
	  */
	public java.lang.String getColumnName();

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

    /** Column name DefaultValue2 */
    public static final String COLUMNNAME_DefaultValue2 = "DefaultValue2";

	/** Set Default Logic 2.
	  * Default value hierarchy, separated by ;

	  */
	public void setDefaultValue2 (java.lang.String DefaultValue2);

	/** Get Default Logic 2.
	  * Default value hierarchy, separated by ;

	  */
	public java.lang.String getDefaultValue2();

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Beschreibung	  */
	public void setDescription (java.lang.String Description);

	/** Get Beschreibung	  */
	public java.lang.String getDescription();

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

    /** Column name FieldLength */
    public static final String COLUMNNAME_FieldLength = "FieldLength";

	/** Set Länge.
	  * Length of the column in the database
	  */
	public void setFieldLength (int FieldLength);

	/** Get Länge.
	  * Length of the column in the database
	  */
	public int getFieldLength();

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

    /** Column name IsAutocomplete */
    public static final String COLUMNNAME_IsAutocomplete = "IsAutocomplete";

	/** Set Autocomplete.
	  * Automatic completion for textfields
	  */
	public void setIsAutocomplete (boolean IsAutocomplete);

	/** Get Autocomplete.
	  * Automatic completion for textfields
	  */
	public boolean isAutocomplete();

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

    /** Column name IsRange */
    public static final String COLUMNNAME_IsRange = "IsRange";

	/** Set Range.
	  * The parameter is a range of values
	  */
	public void setIsRange (boolean IsRange);

	/** Get Range.
	  * The parameter is a range of values
	  */
	public boolean isRange();

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

    /** Column name ReadOnlyLogic */
    public static final String COLUMNNAME_ReadOnlyLogic = "ReadOnlyLogic";

	/** Set Read Only Logic.
	  * Logic to determine if field is read only (applies only when field is read-write)
	  */
	public void setReadOnlyLogic (java.lang.String ReadOnlyLogic);

	/** Get Read Only Logic.
	  * Logic to determine if field is read only (applies only when field is read-write)
	  */
	public java.lang.String getReadOnlyLogic();

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

    /** Column name ValueMax */
    public static final String COLUMNNAME_ValueMax = "ValueMax";

	/** Set Max. Wert.
	  * Maximum Value for a field
	  */
	public void setValueMax (java.lang.String ValueMax);

	/** Get Max. Wert.
	  * Maximum Value for a field
	  */
	public java.lang.String getValueMax();

    /** Column name ValueMin */
    public static final String COLUMNNAME_ValueMin = "ValueMin";

	/** Set Min. Wert.
	  * Minimum Value for a field
	  */
	public void setValueMin (java.lang.String ValueMin);

	/** Get Min. Wert.
	  * Minimum Value for a field
	  */
	public java.lang.String getValueMin();

    /** Column name VFormat */
    public static final String COLUMNNAME_VFormat = "VFormat";

	/** Set Value Format.
	  * Format of the value;
 Can contain fixed format elements, Variables: "_lLoOaAcCa09"
	  */
	public void setVFormat (java.lang.String VFormat);

	/** Get Value Format.
	  * Format of the value;
 Can contain fixed format elements, Variables: "_lLoOaAcCa09"
	  */
	public java.lang.String getVFormat();
}
