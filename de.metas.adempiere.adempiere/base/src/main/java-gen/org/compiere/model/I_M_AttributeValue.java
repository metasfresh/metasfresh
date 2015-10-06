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


/** Generated Interface for M_AttributeValue
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_AttributeValue 
{

    /** TableName=M_AttributeValue */
    public static final String Table_Name = "M_AttributeValue";

    /** AD_Table_ID=558 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */

	/** Get Mandant.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client() throws RuntimeException;

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_M_AttributeValue, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_AttributeValue, org.compiere.model.I_AD_Client>(I_M_AttributeValue.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

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

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_M_AttributeValue, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_AttributeValue, org.compiere.model.I_AD_Org>(I_M_AttributeValue.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Referenzliste.
	  * Referenzliste basierend auf Tabelle
	  */
	public void setAD_Ref_List_ID (int AD_Ref_List_ID);

	/** Get Referenzliste.
	  * Referenzliste basierend auf Tabelle
	  */
	public int getAD_Ref_List_ID();

	public org.compiere.model.I_AD_Ref_List getAD_Ref_List() throws RuntimeException;

	public void setAD_Ref_List(org.compiere.model.I_AD_Ref_List AD_Ref_List);

    /** Column definition for AD_Ref_List_ID */
    public static final org.adempiere.model.ModelColumn<I_M_AttributeValue, org.compiere.model.I_AD_Ref_List> COLUMN_AD_Ref_List_ID = new org.adempiere.model.ModelColumn<I_M_AttributeValue, org.compiere.model.I_AD_Ref_List>(I_M_AttributeValue.class, "AD_Ref_List_ID", org.compiere.model.I_AD_Ref_List.class);
    /** Column name AD_Ref_List_ID */
    public static final String COLUMNNAME_AD_Ref_List_ID = "AD_Ref_List_ID";

	/** Set Available Transaction	  */
	public void setAvailableTrx (java.lang.String AvailableTrx);

	/** Get Available Transaction	  */
	public java.lang.String getAvailableTrx();

    /** Column definition for AvailableTrx */
    public static final org.adempiere.model.ModelColumn<I_M_AttributeValue, Object> COLUMN_AvailableTrx = new org.adempiere.model.ModelColumn<I_M_AttributeValue, Object>(I_M_AttributeValue.class, "AvailableTrx", null);
    /** Column name AvailableTrx */
    public static final String COLUMNNAME_AvailableTrx = "AvailableTrx";

	/** Get Erstellt.
	  * Date this record was created
	  */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_M_AttributeValue, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_AttributeValue, Object>(I_M_AttributeValue.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Erstellt durch.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_M_AttributeValue, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_AttributeValue, org.compiere.model.I_AD_User>(I_M_AttributeValue.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Set Beschreibung	  */
	public void setDescription (java.lang.String Description);

	/** Get Beschreibung	  */
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_M_AttributeValue, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_M_AttributeValue, Object>(I_M_AttributeValue.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Aktiv.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Aktiv.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_M_AttributeValue, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_AttributeValue, Object>(I_M_AttributeValue.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Null Value	  */
	public void setIsNullFieldValue (boolean IsNullFieldValue);

	/** Get Null Value	  */
	public boolean isNullFieldValue();

    /** Column definition for IsNullFieldValue */
    public static final org.adempiere.model.ModelColumn<I_M_AttributeValue, Object> COLUMN_IsNullFieldValue = new org.adempiere.model.ModelColumn<I_M_AttributeValue, Object>(I_M_AttributeValue.class, "IsNullFieldValue", null);
    /** Column name IsNullFieldValue */
    public static final String COLUMNNAME_IsNullFieldValue = "IsNullFieldValue";

	/** Set Merkmal.
	  * Product Attribute
	  */
	public void setM_Attribute_ID (int M_Attribute_ID);

	/** Get Merkmal.
	  * Product Attribute
	  */
	public int getM_Attribute_ID();

	public org.compiere.model.I_M_Attribute getM_Attribute() throws RuntimeException;

	public void setM_Attribute(org.compiere.model.I_M_Attribute M_Attribute);

    /** Column definition for M_Attribute_ID */
    public static final org.adempiere.model.ModelColumn<I_M_AttributeValue, org.compiere.model.I_M_Attribute> COLUMN_M_Attribute_ID = new org.adempiere.model.ModelColumn<I_M_AttributeValue, org.compiere.model.I_M_Attribute>(I_M_AttributeValue.class, "M_Attribute_ID", org.compiere.model.I_M_Attribute.class);
    /** Column name M_Attribute_ID */
    public static final String COLUMNNAME_M_Attribute_ID = "M_Attribute_ID";

	/** Set Merkmals-Wert.
	  * Product Attribute Value
	  */
	public void setM_AttributeValue_ID (int M_AttributeValue_ID);

	/** Get Merkmals-Wert.
	  * Product Attribute Value
	  */
	public int getM_AttributeValue_ID();

    /** Column definition for M_AttributeValue_ID */
    public static final org.adempiere.model.ModelColumn<I_M_AttributeValue, Object> COLUMN_M_AttributeValue_ID = new org.adempiere.model.ModelColumn<I_M_AttributeValue, Object>(I_M_AttributeValue.class, "M_AttributeValue_ID", null);
    /** Column name M_AttributeValue_ID */
    public static final String COLUMNNAME_M_AttributeValue_ID = "M_AttributeValue_ID";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (java.lang.String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public java.lang.String getName();

    /** Column definition for Name */
    public static final org.adempiere.model.ModelColumn<I_M_AttributeValue, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_M_AttributeValue, Object>(I_M_AttributeValue.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Get Aktualisiert.
	  * Date this record was updated
	  */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_M_AttributeValue, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_AttributeValue, Object>(I_M_AttributeValue.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Aktualisiert durch.
	  * User who updated this records
	  */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_M_AttributeValue, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_AttributeValue, org.compiere.model.I_AD_User>(I_M_AttributeValue.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Set Suchschlüssel.
	  * Search key for the record in the format required - must be unique
	  */
	public void setValue (java.lang.String Value);

	/** Get Suchschlüssel.
	  * Search key for the record in the format required - must be unique
	  */
	public java.lang.String getValue();

    /** Column definition for Value */
    public static final org.adempiere.model.ModelColumn<I_M_AttributeValue, Object> COLUMN_Value = new org.adempiere.model.ModelColumn<I_M_AttributeValue, Object>(I_M_AttributeValue.class, "Value", null);
    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";
}
