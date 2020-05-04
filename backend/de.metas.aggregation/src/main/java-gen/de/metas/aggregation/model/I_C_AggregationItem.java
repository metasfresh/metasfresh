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


/** Generated Interface for C_AggregationItem
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_AggregationItem 
{

    /** TableName=C_AggregationItem */
    public static final String Table_Name = "C_AggregationItem";

    /** AD_Table_ID=540650 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 7 - System - Client - Org 
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(7);

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
    public static final org.adempiere.model.ModelColumn<I_C_AggregationItem, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_AggregationItem, org.compiere.model.I_AD_Client>(I_C_AggregationItem.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Spalte.
	 * Spalte in der Tabelle
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Column_ID (int AD_Column_ID);

	/**
	 * Get Spalte.
	 * Spalte in der Tabelle
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Column_ID();

	public org.compiere.model.I_AD_Column getAD_Column();

	public void setAD_Column(org.compiere.model.I_AD_Column AD_Column);

    /** Column definition for AD_Column_ID */
    public static final org.adempiere.model.ModelColumn<I_C_AggregationItem, org.compiere.model.I_AD_Column> COLUMN_AD_Column_ID = new org.adempiere.model.ModelColumn<I_C_AggregationItem, org.compiere.model.I_AD_Column>(I_C_AggregationItem.class, "AD_Column_ID", org.compiere.model.I_AD_Column.class);
    /** Column name AD_Column_ID */
    public static final String COLUMNNAME_AD_Column_ID = "AD_Column_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_AggregationItem, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_AggregationItem, org.compiere.model.I_AD_Org>(I_C_AggregationItem.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Aggregation Attribute.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Aggregation_Attribute_ID (int C_Aggregation_Attribute_ID);

	/**
	 * Get Aggregation Attribute.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Aggregation_Attribute_ID();

	public de.metas.aggregation.model.I_C_Aggregation_Attribute getC_Aggregation_Attribute();

	public void setC_Aggregation_Attribute(de.metas.aggregation.model.I_C_Aggregation_Attribute C_Aggregation_Attribute);

    /** Column definition for C_Aggregation_Attribute_ID */
    public static final org.adempiere.model.ModelColumn<I_C_AggregationItem, de.metas.aggregation.model.I_C_Aggregation_Attribute> COLUMN_C_Aggregation_Attribute_ID = new org.adempiere.model.ModelColumn<I_C_AggregationItem, de.metas.aggregation.model.I_C_Aggregation_Attribute>(I_C_AggregationItem.class, "C_Aggregation_Attribute_ID", de.metas.aggregation.model.I_C_Aggregation_Attribute.class);
    /** Column name C_Aggregation_Attribute_ID */
    public static final String COLUMNNAME_C_Aggregation_Attribute_ID = "C_Aggregation_Attribute_ID";

	/**
	 * Set Aggregation Definition.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Aggregation_ID (int C_Aggregation_ID);

	/**
	 * Get Aggregation Definition.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Aggregation_ID();

	public de.metas.aggregation.model.I_C_Aggregation getC_Aggregation();

	public void setC_Aggregation(de.metas.aggregation.model.I_C_Aggregation C_Aggregation);

    /** Column definition for C_Aggregation_ID */
    public static final org.adempiere.model.ModelColumn<I_C_AggregationItem, de.metas.aggregation.model.I_C_Aggregation> COLUMN_C_Aggregation_ID = new org.adempiere.model.ModelColumn<I_C_AggregationItem, de.metas.aggregation.model.I_C_Aggregation>(I_C_AggregationItem.class, "C_Aggregation_ID", de.metas.aggregation.model.I_C_Aggregation.class);
    /** Column name C_Aggregation_ID */
    public static final String COLUMNNAME_C_Aggregation_ID = "C_Aggregation_ID";

	/**
	 * Set Aggregation Item Definition.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_AggregationItem_ID (int C_AggregationItem_ID);

	/**
	 * Get Aggregation Item Definition.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_AggregationItem_ID();

    /** Column definition for C_AggregationItem_ID */
    public static final org.adempiere.model.ModelColumn<I_C_AggregationItem, Object> COLUMN_C_AggregationItem_ID = new org.adempiere.model.ModelColumn<I_C_AggregationItem, Object>(I_C_AggregationItem.class, "C_AggregationItem_ID", null);
    /** Column name C_AggregationItem_ID */
    public static final String COLUMNNAME_C_AggregationItem_ID = "C_AggregationItem_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_AggregationItem, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_AggregationItem, Object>(I_C_AggregationItem.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_AggregationItem, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_AggregationItem, org.compiere.model.I_AD_User>(I_C_AggregationItem.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_AggregationItem, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_C_AggregationItem, Object>(I_C_AggregationItem.class, "Description", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_AggregationItem, Object> COLUMN_EntityType = new org.adempiere.model.ModelColumn<I_C_AggregationItem, Object>(I_C_AggregationItem.class, "EntityType", null);
    /** Column name EntityType */
    public static final String COLUMNNAME_EntityType = "EntityType";

	/**
	 * Set Included Aggregation.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIncluded_Aggregation_ID (int Included_Aggregation_ID);

	/**
	 * Get Included Aggregation.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getIncluded_Aggregation_ID();

	public de.metas.aggregation.model.I_C_Aggregation getIncluded_Aggregation();

	public void setIncluded_Aggregation(de.metas.aggregation.model.I_C_Aggregation Included_Aggregation);

    /** Column definition for Included_Aggregation_ID */
    public static final org.adempiere.model.ModelColumn<I_C_AggregationItem, de.metas.aggregation.model.I_C_Aggregation> COLUMN_Included_Aggregation_ID = new org.adempiere.model.ModelColumn<I_C_AggregationItem, de.metas.aggregation.model.I_C_Aggregation>(I_C_AggregationItem.class, "Included_Aggregation_ID", de.metas.aggregation.model.I_C_Aggregation.class);
    /** Column name Included_Aggregation_ID */
    public static final String COLUMNNAME_Included_Aggregation_ID = "Included_Aggregation_ID";

	/**
	 * Set Include Logic.
	 * If expression is evaluated as true, the item will be included. If evaluated as false, the item will be excluded.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIncludeLogic (java.lang.String IncludeLogic);

	/**
	 * Get Include Logic.
	 * If expression is evaluated as true, the item will be included. If evaluated as false, the item will be excluded.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getIncludeLogic();

    /** Column definition for IncludeLogic */
    public static final org.adempiere.model.ModelColumn<I_C_AggregationItem, Object> COLUMN_IncludeLogic = new org.adempiere.model.ModelColumn<I_C_AggregationItem, Object>(I_C_AggregationItem.class, "IncludeLogic", null);
    /** Column name IncludeLogic */
    public static final String COLUMNNAME_IncludeLogic = "IncludeLogic";

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
    public static final org.adempiere.model.ModelColumn<I_C_AggregationItem, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_AggregationItem, Object>(I_C_AggregationItem.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

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
    public static final org.adempiere.model.ModelColumn<I_C_AggregationItem, Object> COLUMN_Type = new org.adempiere.model.ModelColumn<I_C_AggregationItem, Object>(I_C_AggregationItem.class, "Type", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_AggregationItem, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_AggregationItem, Object>(I_C_AggregationItem.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_AggregationItem, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_AggregationItem, org.compiere.model.I_AD_User>(I_C_AggregationItem.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
