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
package de.metas.aggregation.model;


/** Generated Interface for C_Aggregation_Attribute
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Aggregation_Attribute 
{

    /** TableName=C_Aggregation_Attribute */
    public static final String Table_Name = "C_Aggregation_Attribute";

    /** AD_Table_ID=540651 */
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
    public static final org.adempiere.model.ModelColumn<I_C_Aggregation_Attribute, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_Aggregation_Attribute, org.compiere.model.I_AD_Client>(I_C_Aggregation_Attribute.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_Aggregation_Attribute, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_Aggregation_Attribute, org.compiere.model.I_AD_Org>(I_C_Aggregation_Attribute.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Table_ID();

	public org.compiere.model.I_AD_Table getAD_Table();

	public void setAD_Table(org.compiere.model.I_AD_Table AD_Table);

    /** Column definition for AD_Table_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Aggregation_Attribute, org.compiere.model.I_AD_Table> COLUMN_AD_Table_ID = new org.adempiere.model.ModelColumn<I_C_Aggregation_Attribute, org.compiere.model.I_AD_Table>(I_C_Aggregation_Attribute.class, "AD_Table_ID", org.compiere.model.I_AD_Table.class);
    /** Column name AD_Table_ID */
    public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * Set Aggregation Attribute.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Aggregation_Attribute_ID (int C_Aggregation_Attribute_ID);

	/**
	 * Get Aggregation Attribute.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Aggregation_Attribute_ID();

    /** Column definition for C_Aggregation_Attribute_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Aggregation_Attribute, Object> COLUMN_C_Aggregation_Attribute_ID = new org.adempiere.model.ModelColumn<I_C_Aggregation_Attribute, Object>(I_C_Aggregation_Attribute.class, "C_Aggregation_Attribute_ID", null);
    /** Column name C_Aggregation_Attribute_ID */
    public static final String COLUMNNAME_C_Aggregation_Attribute_ID = "C_Aggregation_Attribute_ID";

	/**
	 * Set Validierungscode.
	 * Validierungscode
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCode (java.lang.String Code);

	/**
	 * Get Validierungscode.
	 * Validierungscode
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCode();

    /** Column definition for Code */
    public static final org.adempiere.model.ModelColumn<I_C_Aggregation_Attribute, Object> COLUMN_Code = new org.adempiere.model.ModelColumn<I_C_Aggregation_Attribute, Object>(I_C_Aggregation_Attribute.class, "Code", null);
    /** Column name Code */
    public static final String COLUMNNAME_Code = "Code";

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
    public static final org.adempiere.model.ModelColumn<I_C_Aggregation_Attribute, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_Aggregation_Attribute, Object>(I_C_Aggregation_Attribute.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Aggregation_Attribute, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_Aggregation_Attribute, org.compiere.model.I_AD_User>(I_C_Aggregation_Attribute.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_Aggregation_Attribute, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_C_Aggregation_Attribute, Object>(I_C_Aggregation_Attribute.class, "Description", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Aggregation_Attribute, Object> COLUMN_EntityType = new org.adempiere.model.ModelColumn<I_C_Aggregation_Attribute, Object>(I_C_Aggregation_Attribute.class, "EntityType", null);
    /** Column name EntityType */
    public static final String COLUMNNAME_EntityType = "EntityType";

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
    public static final org.adempiere.model.ModelColumn<I_C_Aggregation_Attribute, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_Aggregation_Attribute, Object>(I_C_Aggregation_Attribute.class, "IsActive", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Aggregation_Attribute, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_C_Aggregation_Attribute, Object>(I_C_Aggregation_Attribute.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Art.
	 * Type of Validation (SQL, Java Script, Java Language)
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setType (java.lang.String Type);

	/**
	 * Get Art.
	 * Type of Validation (SQL, Java Script, Java Language)
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getType();

    /** Column definition for Type */
    public static final org.adempiere.model.ModelColumn<I_C_Aggregation_Attribute, Object> COLUMN_Type = new org.adempiere.model.ModelColumn<I_C_Aggregation_Attribute, Object>(I_C_Aggregation_Attribute.class, "Type", null);
    /** Column name Type */
    public static final String COLUMNNAME_Type = "Type";

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
    public static final org.adempiere.model.ModelColumn<I_C_Aggregation_Attribute, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_Aggregation_Attribute, Object>(I_C_Aggregation_Attribute.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Aggregation_Attribute, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_Aggregation_Attribute, org.compiere.model.I_AD_User>(I_C_Aggregation_Attribute.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
