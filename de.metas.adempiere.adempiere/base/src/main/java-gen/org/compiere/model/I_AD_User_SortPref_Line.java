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


/** Generated Interface for AD_User_SortPref_Line
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_User_SortPref_Line 
{

    /** TableName=AD_User_SortPref_Line */
    public static final String Table_Name = "AD_User_SortPref_Line";

    /** AD_Table_ID=540640 */
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
    public static final org.adempiere.model.ModelColumn<I_AD_User_SortPref_Line, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_AD_User_SortPref_Line, org.compiere.model.I_AD_Client>(I_AD_User_SortPref_Line.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Feld.
	 * Ein Feld einer Datenbanktabelle
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Field_ID (int AD_Field_ID);

	/**
	 * Get Feld.
	 * Ein Feld einer Datenbanktabelle
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Field_ID();

	public org.compiere.model.I_AD_Field getAD_Field();

	public void setAD_Field(org.compiere.model.I_AD_Field AD_Field);

    /** Column definition for AD_Field_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_User_SortPref_Line, org.compiere.model.I_AD_Field> COLUMN_AD_Field_ID = new org.adempiere.model.ModelColumn<I_AD_User_SortPref_Line, org.compiere.model.I_AD_Field>(I_AD_User_SortPref_Line.class, "AD_Field_ID", org.compiere.model.I_AD_Field.class);
    /** Column name AD_Field_ID */
    public static final String COLUMNNAME_AD_Field_ID = "AD_Field_ID";

	/**
	 * Set Info Column.
	 * Info Window Column
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_InfoColumn_ID (int AD_InfoColumn_ID);

	/**
	 * Get Info Column.
	 * Info Window Column
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_InfoColumn_ID();

	public org.compiere.model.I_AD_InfoColumn getAD_InfoColumn();

	public void setAD_InfoColumn(org.compiere.model.I_AD_InfoColumn AD_InfoColumn);

    /** Column definition for AD_InfoColumn_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_User_SortPref_Line, org.compiere.model.I_AD_InfoColumn> COLUMN_AD_InfoColumn_ID = new org.adempiere.model.ModelColumn<I_AD_User_SortPref_Line, org.compiere.model.I_AD_InfoColumn>(I_AD_User_SortPref_Line.class, "AD_InfoColumn_ID", org.compiere.model.I_AD_InfoColumn.class);
    /** Column name AD_InfoColumn_ID */
    public static final String COLUMNNAME_AD_InfoColumn_ID = "AD_InfoColumn_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_User_SortPref_Line, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_AD_User_SortPref_Line, org.compiere.model.I_AD_Org>(I_AD_User_SortPref_Line.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Sortierbegriff pro Benutzer.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_User_SortPref_Hdr_ID (int AD_User_SortPref_Hdr_ID);

	/**
	 * Get Sortierbegriff pro Benutzer.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_User_SortPref_Hdr_ID();

	public org.compiere.model.I_AD_User_SortPref_Hdr getAD_User_SortPref_Hdr();

	public void setAD_User_SortPref_Hdr(org.compiere.model.I_AD_User_SortPref_Hdr AD_User_SortPref_Hdr);

    /** Column definition for AD_User_SortPref_Hdr_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_User_SortPref_Line, org.compiere.model.I_AD_User_SortPref_Hdr> COLUMN_AD_User_SortPref_Hdr_ID = new org.adempiere.model.ModelColumn<I_AD_User_SortPref_Line, org.compiere.model.I_AD_User_SortPref_Hdr>(I_AD_User_SortPref_Line.class, "AD_User_SortPref_Hdr_ID", org.compiere.model.I_AD_User_SortPref_Hdr.class);
    /** Column name AD_User_SortPref_Hdr_ID */
    public static final String COLUMNNAME_AD_User_SortPref_Hdr_ID = "AD_User_SortPref_Hdr_ID";

	/**
	 * Set Sortierbegriff pro Benutzer Position.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_User_SortPref_Line_ID (int AD_User_SortPref_Line_ID);

	/**
	 * Get Sortierbegriff pro Benutzer Position.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_User_SortPref_Line_ID();

    /** Column definition for AD_User_SortPref_Line_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_User_SortPref_Line, Object> COLUMN_AD_User_SortPref_Line_ID = new org.adempiere.model.ModelColumn<I_AD_User_SortPref_Line, Object>(I_AD_User_SortPref_Line.class, "AD_User_SortPref_Line_ID", null);
    /** Column name AD_User_SortPref_Line_ID */
    public static final String COLUMNNAME_AD_User_SortPref_Line_ID = "AD_User_SortPref_Line_ID";

	/**
	 * Set Spaltenname.
	 * Name der Spalte in der Datenbank
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setColumnName (java.lang.String ColumnName);

	/**
	 * Get Spaltenname.
	 * Name der Spalte in der Datenbank
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getColumnName();

    /** Column definition for ColumnName */
    public static final org.adempiere.model.ModelColumn<I_AD_User_SortPref_Line, Object> COLUMN_ColumnName = new org.adempiere.model.ModelColumn<I_AD_User_SortPref_Line, Object>(I_AD_User_SortPref_Line.class, "ColumnName", null);
    /** Column name ColumnName */
    public static final String COLUMNNAME_ColumnName = "ColumnName";

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
    public static final org.adempiere.model.ModelColumn<I_AD_User_SortPref_Line, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_User_SortPref_Line, Object>(I_AD_User_SortPref_Line.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_User_SortPref_Line, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_AD_User_SortPref_Line, org.compiere.model.I_AD_User>(I_AD_User_SortPref_Line.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

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
    public static final org.adempiere.model.ModelColumn<I_AD_User_SortPref_Line, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_User_SortPref_Line, Object>(I_AD_User_SortPref_Line.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Aufsteigender.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsAscending (boolean IsAscending);

	/**
	 * Get Aufsteigender.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAscending();

    /** Column definition for IsAscending */
    public static final org.adempiere.model.ModelColumn<I_AD_User_SortPref_Line, Object> COLUMN_IsAscending = new org.adempiere.model.ModelColumn<I_AD_User_SortPref_Line, Object>(I_AD_User_SortPref_Line.class, "IsAscending", null);
    /** Column name IsAscending */
    public static final String COLUMNNAME_IsAscending = "IsAscending";

	/**
	 * Set Reihenfolge.
	 * Zur Bestimmung der Reihenfolge der Einträge;
 die kleinste Zahl kommt zuerst
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setSeqNo (int SeqNo);

	/**
	 * Get Reihenfolge.
	 * Zur Bestimmung der Reihenfolge der Einträge;
 die kleinste Zahl kommt zuerst
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getSeqNo();

    /** Column definition for SeqNo */
    public static final org.adempiere.model.ModelColumn<I_AD_User_SortPref_Line, Object> COLUMN_SeqNo = new org.adempiere.model.ModelColumn<I_AD_User_SortPref_Line, Object>(I_AD_User_SortPref_Line.class, "SeqNo", null);
    /** Column name SeqNo */
    public static final String COLUMNNAME_SeqNo = "SeqNo";

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
    public static final org.adempiere.model.ModelColumn<I_AD_User_SortPref_Line, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_User_SortPref_Line, Object>(I_AD_User_SortPref_Line.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_User_SortPref_Line, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_AD_User_SortPref_Line, org.compiere.model.I_AD_User>(I_AD_User_SortPref_Line.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
