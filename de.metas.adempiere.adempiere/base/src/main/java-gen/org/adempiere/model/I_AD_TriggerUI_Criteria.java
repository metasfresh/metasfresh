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
package org.adempiere.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Interface for AD_TriggerUI_Criteria
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a#333
 */
public interface I_AD_TriggerUI_Criteria 
{

    /** TableName=AD_TriggerUI_Criteria */
    public static final String Table_Name = "AD_TriggerUI_Criteria";

    /** AD_Table_ID=540278 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 7 - System - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(7);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Mandant.
	  * Mandant fuer diese Installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Column_ID */
    public static final String COLUMNNAME_AD_Column_ID = "AD_Column_ID";

	/** Set Spalte.
	  * Spalte in der Tabelle
	  */
	public void setAD_Column_ID (int AD_Column_ID);

	/** Get Spalte.
	  * Spalte in der Tabelle
	  */
	public int getAD_Column_ID();

	public org.compiere.model.I_AD_Column getAD_Column() throws RuntimeException;

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organisation.
	  * Organisatorische Einheit des Mandanten
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organisation.
	  * Organisatorische Einheit des Mandanten
	  */
	public int getAD_Org_ID();

    /** Column name AD_Reference_ID */
    public static final String COLUMNNAME_AD_Reference_ID = "AD_Reference_ID";

	/** Set Referenz.
	  * Systemreferenz und Validierung
	  */
	public void setAD_Reference_ID (int AD_Reference_ID);

	/** Get Referenz.
	  * Systemreferenz und Validierung
	  */
	public int getAD_Reference_ID();

	public org.compiere.model.I_AD_Reference getAD_Reference() throws RuntimeException;

    /** Column name AD_TriggerUI_Criteria_ID */
    public static final String COLUMNNAME_AD_TriggerUI_Criteria_ID = "AD_TriggerUI_Criteria_ID";

	/** Set UI Trigger Criteria	  */
	public void setAD_TriggerUI_Criteria_ID (int AD_TriggerUI_Criteria_ID);

	/** Get UI Trigger Criteria	  */
	public int getAD_TriggerUI_Criteria_ID();

    /** Column name AD_TriggerUI_ID */
    public static final String COLUMNNAME_AD_TriggerUI_ID = "AD_TriggerUI_ID";

	/** Set UI Trigger	  */
	public void setAD_TriggerUI_ID (int AD_TriggerUI_ID);

	/** Get UI Trigger	  */
	public int getAD_TriggerUI_ID();

	public I_AD_TriggerUI getAD_TriggerUI() throws RuntimeException;

    /** Column name AttributeName */
    public static final String COLUMNNAME_AttributeName = "AttributeName";

	/** Set Attribute Name.
	  * Name of the Attribute
	  */
	public void setAttributeName (String AttributeName);

	/** Get Attribute Name.
	  * Name of the Attribute
	  */
	public String getAttributeName();

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Erstellt.
	  * Datum, an dem dieser Eintrag erstellt wurde
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Erstellt durch.
	  * Nutzer, der diesen Eintrag erstellt hat
	  */
	public int getCreatedBy();

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Beschreibung.
	  * Optional short description of the record
	  */
	public void setDescription (String Description);

	/** Get Beschreibung.
	  * Optional short description of the record
	  */
	public String getDescription();

    /** Column name EntityType */
    public static final String COLUMNNAME_EntityType = "EntityType";

	/** Set Entitaets-Art.
	  * Dictionary Entity Type;
 Determines ownership and synchronization
	  */
	public void setEntityType (String EntityType);

	/** Get Entitaets-Art.
	  * Dictionary Entity Type;
 Determines ownership and synchronization
	  */
	public String getEntityType();

    /** Column name FieldValue */
    public static final String COLUMNNAME_FieldValue = "FieldValue";

	/** Set Field Value	  */
	public void setFieldValue (String FieldValue);

	/** Get Field Value	  */
	public String getFieldValue();

    /** Column name FieldValueFormat */
    public static final String COLUMNNAME_FieldValueFormat = "FieldValueFormat";

	/** Set Value Format	  */
	public void setFieldValueFormat (String FieldValueFormat);

	/** Get Value Format	  */
	public String getFieldValueFormat();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Aktiv.
	  * Der Eintrag ist im System aktiv
	  */
	public void setIsActive (boolean IsActive);

	/** Get Aktiv.
	  * Der Eintrag ist im System aktiv
	  */
	public boolean isActive();

    /** Column name IsMandatory */
    public static final String COLUMNNAME_IsMandatory = "IsMandatory";

	/** Set Mandatory.
	  * Data entry is required in this column
	  */
	public void setIsMandatory (boolean IsMandatory);

	/** Get Mandatory.
	  * Data entry is required in this column
	  */
	public boolean isMandatory();

    /** Column name IsNullFieldValue */
    public static final String COLUMNNAME_IsNullFieldValue = "IsNullFieldValue";

	/** Set Null Value	  */
	public void setIsNullFieldValue (boolean IsNullFieldValue);

	/** Get Null Value	  */
	public boolean isNullFieldValue();

    /** Column name Operation */
    public static final String COLUMNNAME_Operation = "Operation";

	/** Set Arbeitsvorgang .
	  * Compare Operation
	  */
	public void setOperation (String Operation);

	/** Get Arbeitsvorgang .
	  * Compare Operation
	  */
	public String getOperation();

    /** Column name Type */
    public static final String COLUMNNAME_Type = "Type";

	/** Set Art.
	  * Type of Validation (SQL, Java Script, Java Language)
	  */
	public void setType (String Type);

	/** Get Art.
	  * Type of Validation (SQL, Java Script, Java Language)
	  */
	public String getType();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Aktualisiert.
	  * Datum, an dem dieser Eintrag aktualisiert wurde
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Aktualisiert durch.
	  * Nutzer, der diesen Eintrag aktualisiert hat
	  */
	public int getUpdatedBy();
}
