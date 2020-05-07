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
package de.metas.dimension.model;

/**
 * Generated Interface for DIM_Dimension_Spec_Assignment
 * 
 * @author Adempiere (generated)
 */
@SuppressWarnings("javadoc")
public interface I_DIM_Dimension_Spec_Assignment
{

	/** TableName=DIM_Dimension_Spec_Assignment */
	public static final String Table_Name = "DIM_Dimension_Spec_Assignment";

	/** AD_Table_ID=540664 */
	// public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

	// org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

	/**
	 * AccessLevel = 7 - System - Client - Org
	 */
	// java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(7);

	/** Load Meta Data */

	/**
	 * Get Mandant.
	 * Mandant für diese Installation.
	 *
	 * <br>
	 * Type: TableDir <br>
	 * Mandatory: true <br>
	 * Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

	/** Column definition for AD_Client_ID */
	public static final org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec_Assignment, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec_Assignment, org.compiere.model.I_AD_Client>(
			I_DIM_Dimension_Spec_Assignment.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
	/** Column name AD_Client_ID */
	public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Spalte.
	 * Spalte in der Tabelle
	 *
	 * <br>
	 * Type: Search <br>
	 * Mandatory: true <br>
	 * Virtual Column: false
	 */
	public void setAD_Column_ID(int AD_Column_ID);

	/**
	 * Get Spalte.
	 * Spalte in der Tabelle
	 *
	 * <br>
	 * Type: Search <br>
	 * Mandatory: true <br>
	 * Virtual Column: false
	 */
	public int getAD_Column_ID();

	public org.compiere.model.I_AD_Column getAD_Column();

	public void setAD_Column(org.compiere.model.I_AD_Column AD_Column);

	/** Column definition for AD_Column_ID */
	public static final org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec_Assignment, org.compiere.model.I_AD_Column> COLUMN_AD_Column_ID = new org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec_Assignment, org.compiere.model.I_AD_Column>(
			I_DIM_Dimension_Spec_Assignment.class, "AD_Column_ID", org.compiere.model.I_AD_Column.class);
	/** Column name AD_Column_ID */
	public static final String COLUMNNAME_AD_Column_ID = "AD_Column_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>
	 * Type: TableDir <br>
	 * Mandatory: true <br>
	 * Virtual Column: false
	 */
	public void setAD_Org_ID(int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>
	 * Type: TableDir <br>
	 * Mandatory: true <br>
	 * Virtual Column: false
	 */
	public int getAD_Org_ID();

	public org.compiere.model.I_AD_Org getAD_Org();

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

	/** Column definition for AD_Org_ID */
	public static final org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec_Assignment, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec_Assignment, org.compiere.model.I_AD_Org>(
			I_DIM_Dimension_Spec_Assignment.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
	/** Column name AD_Org_ID */
	public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Get Erstellt.
	 * Datum, an dem dieser Eintrag erstellt wurde
	 *
	 * <br>
	 * Type: DateTime <br>
	 * Mandatory: true <br>
	 * Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

	/** Column definition for Created */
	public static final org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec_Assignment, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec_Assignment, Object>(
			I_DIM_Dimension_Spec_Assignment.class, "Created", null);
	/** Column name Created */
	public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * Nutzer, der diesen Eintrag erstellt hat
	 *
	 * <br>
	 * Type: Table <br>
	 * Mandatory: true <br>
	 * Virtual Column: false
	 */
	public int getCreatedBy();

	/** Column definition for CreatedBy */
	public static final org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec_Assignment, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec_Assignment, org.compiere.model.I_AD_User>(
			I_DIM_Dimension_Spec_Assignment.class, "CreatedBy", org.compiere.model.I_AD_User.class);
	/** Column name CreatedBy */
	public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Dimensionsspezifikationszuordnung.
	 *
	 * <br>
	 * Type: ID <br>
	 * Mandatory: true <br>
	 * Virtual Column: false
	 */
	public void setDIM_Dimension_Spec_Assignment_ID(int DIM_Dimension_Spec_Assignment_ID);

	/**
	 * Get Dimensionsspezifikationszuordnung.
	 *
	 * <br>
	 * Type: ID <br>
	 * Mandatory: true <br>
	 * Virtual Column: false
	 */
	public int getDIM_Dimension_Spec_Assignment_ID();

	/** Column definition for DIM_Dimension_Spec_Assignment_ID */
	public static final org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec_Assignment, Object> COLUMN_DIM_Dimension_Spec_Assignment_ID = new org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec_Assignment, Object>(
			I_DIM_Dimension_Spec_Assignment.class, "DIM_Dimension_Spec_Assignment_ID", null);
	/** Column name DIM_Dimension_Spec_Assignment_ID */
	public static final String COLUMNNAME_DIM_Dimension_Spec_Assignment_ID = "DIM_Dimension_Spec_Assignment_ID";

	/**
	 * Set Dimensionsspezifikation.
	 *
	 * <br>
	 * Type: TableDir <br>
	 * Mandatory: true <br>
	 * Virtual Column: false
	 */
	public void setDIM_Dimension_Spec_ID(int DIM_Dimension_Spec_ID);

	/**
	 * Get Dimensionsspezifikation.
	 *
	 * <br>
	 * Type: TableDir <br>
	 * Mandatory: true <br>
	 * Virtual Column: false
	 */
	public int getDIM_Dimension_Spec_ID();

	public de.metas.dimension.model.I_DIM_Dimension_Spec getDIM_Dimension_Spec();

	public void setDIM_Dimension_Spec(de.metas.dimension.model.I_DIM_Dimension_Spec DIM_Dimension_Spec);

	/** Column definition for DIM_Dimension_Spec_ID */
	public static final org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec_Assignment, de.metas.dimension.model.I_DIM_Dimension_Spec> COLUMN_DIM_Dimension_Spec_ID = new org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec_Assignment, de.metas.dimension.model.I_DIM_Dimension_Spec>(
			I_DIM_Dimension_Spec_Assignment.class, "DIM_Dimension_Spec_ID", de.metas.dimension.model.I_DIM_Dimension_Spec.class);
	/** Column name DIM_Dimension_Spec_ID */
	public static final String COLUMNNAME_DIM_Dimension_Spec_ID = "DIM_Dimension_Spec_ID";

	/**
	 * Set Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>
	 * Type: YesNo <br>
	 * Mandatory: true <br>
	 * Virtual Column: false
	 */
	public void setIsActive(boolean IsActive);

	/**
	 * Get Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>
	 * Type: YesNo <br>
	 * Mandatory: true <br>
	 * Virtual Column: false
	 */
	public boolean isActive();

	/** Column definition for IsActive */
	public static final org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec_Assignment, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec_Assignment, Object>(
			I_DIM_Dimension_Spec_Assignment.class, "IsActive", null);
	/** Column name IsActive */
	public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Get Aktualisiert.
	 * Datum, an dem dieser Eintrag aktualisiert wurde
	 *
	 * <br>
	 * Type: DateTime <br>
	 * Mandatory: true <br>
	 * Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

	/** Column definition for Updated */
	public static final org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec_Assignment, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec_Assignment, Object>(
			I_DIM_Dimension_Spec_Assignment.class, "Updated", null);
	/** Column name Updated */
	public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * Nutzer, der diesen Eintrag aktualisiert hat
	 *
	 * <br>
	 * Type: Table <br>
	 * Mandatory: true <br>
	 * Virtual Column: false
	 */
	public int getUpdatedBy();

	/** Column definition for UpdatedBy */
	public static final org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec_Assignment, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec_Assignment, org.compiere.model.I_AD_User>(
			I_DIM_Dimension_Spec_Assignment.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
	/** Column name UpdatedBy */
	public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
