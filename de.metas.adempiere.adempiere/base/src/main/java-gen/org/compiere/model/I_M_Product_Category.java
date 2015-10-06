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


/** Generated Interface for M_Product_Category
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_Product_Category 
{

    /** TableName=M_Product_Category */
    public static final String Table_Name = "M_Product_Category";

    /** AD_Table_ID=209 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name A_Asset_Group_ID */
    public static final String COLUMNNAME_A_Asset_Group_ID = "A_Asset_Group_ID";

	/** Set Asset-Gruppe.
	  * Group of Assets
	  */
	public void setA_Asset_Group_ID (int A_Asset_Group_ID);

	/** Get Asset-Gruppe.
	  * Group of Assets
	  */
	public int getA_Asset_Group_ID();

	public org.compiere.model.I_A_Asset_Group getA_Asset_Group() throws RuntimeException;

	public void setA_Asset_Group(org.compiere.model.I_A_Asset_Group A_Asset_Group);

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Mandant.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client() throws RuntimeException;

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

    /** Column name AD_PrintColor_ID */
    public static final String COLUMNNAME_AD_PrintColor_ID = "AD_PrintColor_ID";

	/** Set Druck - Farbe.
	  * Color used for printing and display
	  */
	public void setAD_PrintColor_ID (int AD_PrintColor_ID);

	/** Get Druck - Farbe.
	  * Color used for printing and display
	  */
	public int getAD_PrintColor_ID();

	public org.compiere.model.I_AD_PrintColor getAD_PrintColor() throws RuntimeException;

	public void setAD_PrintColor(org.compiere.model.I_AD_PrintColor AD_PrintColor);

    /** Column name C_DocType_ID */
    public static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/** Set Belegart.
	  * Belegart oder Verarbeitungsvorgaben
	  */
	public void setC_DocType_ID (int C_DocType_ID);

	/** Get Belegart.
	  * Belegart oder Verarbeitungsvorgaben
	  */
	public int getC_DocType_ID();

	public org.compiere.model.I_C_DocType getC_DocType() throws RuntimeException;

	public void setC_DocType(org.compiere.model.I_C_DocType C_DocType);

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

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Beschreibung	  */
	public void setDescription (java.lang.String Description);

	/** Get Beschreibung	  */
	public java.lang.String getDescription();

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

    /** Column name IsDefault */
    public static final String COLUMNNAME_IsDefault = "IsDefault";

	/** Set Standard.
	  * Default value
	  */
	public void setIsDefault (boolean IsDefault);

	/** Get Standard.
	  * Default value
	  */
	public boolean isDefault();

    /** Column name IsSelfService */
    public static final String COLUMNNAME_IsSelfService = "IsSelfService";

	/** Set Selbstbedienung.
	  * This is a Self-Service entry or this entry can be changed via Self-Service
	  */
	public void setIsSelfService (boolean IsSelfService);

	/** Get Selbstbedienung.
	  * This is a Self-Service entry or this entry can be changed via Self-Service
	  */
	public boolean isSelfService();

    /** Column name IsSummary */
    public static final String COLUMNNAME_IsSummary = "IsSummary";

	/** Set Summary Level.
	  * This is a summary entity
	  */
	public void setIsSummary (boolean IsSummary);

	/** Get Summary Level.
	  * This is a summary entity
	  */
	public boolean isSummary();

    /** Column name M_AttributeSet_ID */
    public static final String COLUMNNAME_M_AttributeSet_ID = "M_AttributeSet_ID";

	/** Set Merkmals-Satz.
	  * Merkmals-Satz zum Produkt
	  */
	public void setM_AttributeSet_ID (int M_AttributeSet_ID);

	/** Get Merkmals-Satz.
	  * Merkmals-Satz zum Produkt
	  */
	public int getM_AttributeSet_ID();

	public org.compiere.model.I_M_AttributeSet getM_AttributeSet() throws RuntimeException;

	public void setM_AttributeSet(org.compiere.model.I_M_AttributeSet M_AttributeSet);

    /** Column name M_Product_Category_ID */
    public static final String COLUMNNAME_M_Product_Category_ID = "M_Product_Category_ID";

	/** Set Produkt-Kategorie.
	  * Category of a Product
	  */
	public void setM_Product_Category_ID (int M_Product_Category_ID);

	/** Get Produkt-Kategorie.
	  * Category of a Product
	  */
	public int getM_Product_Category_ID();

    /** Column name M_Product_Category_Parent_ID */
    public static final String COLUMNNAME_M_Product_Category_Parent_ID = "M_Product_Category_Parent_ID";

	/** Set Parent Product Category	  */
	public void setM_Product_Category_Parent_ID (int M_Product_Category_Parent_ID);

	/** Get Parent Product Category	  */
	public int getM_Product_Category_Parent_ID();

	public org.compiere.model.I_M_Product_Category getM_Product_Category_Parent() throws RuntimeException;

	public void setM_Product_Category_Parent(org.compiere.model.I_M_Product_Category M_Product_Category_Parent);

    /** Column name MMPolicy */
    public static final String COLUMNNAME_MMPolicy = "MMPolicy";

	/** Set Materialfluß.
	  * Material Movement Policy
	  */
	public void setMMPolicy (java.lang.String MMPolicy);

	/** Get Materialfluß.
	  * Material Movement Policy
	  */
	public java.lang.String getMMPolicy();

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

    /** Column name PlannedMargin */
    public static final String COLUMNNAME_PlannedMargin = "PlannedMargin";

	/** Set DB1 %.
	  * Project's planned margin as a percentage
	  */
	public void setPlannedMargin (java.math.BigDecimal PlannedMargin);

	/** Get DB1 %.
	  * Project's planned margin as a percentage
	  */
	public java.math.BigDecimal getPlannedMargin();

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

	/**
	 * Set Exclude from MRP.
	 * Exclude from MRP calculation
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMRP_Exclude (java.lang.String MRP_Exclude);

	/**
	 * Get Exclude from MRP.
	 * Exclude from MRP calculation
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getMRP_Exclude();

    /** Column definition for MRP_Exclude */
    public static final org.adempiere.model.ModelColumn<I_M_Product_Category, Object> COLUMN_MRP_Exclude = new org.adempiere.model.ModelColumn<>(I_M_Product_Category.class, "MRP_Exclude", null);
    /** Column name MRP_Exclude */
    public static final String COLUMNNAME_MRP_Exclude = "MRP_Exclude";
}
