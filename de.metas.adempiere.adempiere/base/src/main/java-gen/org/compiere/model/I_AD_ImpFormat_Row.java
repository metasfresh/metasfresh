package org.compiere.model;


/** Generated Interface for AD_ImpFormat_Row
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_ImpFormat_Row 
{

    /** TableName=AD_ImpFormat_Row */
    public static final String Table_Name = "AD_ImpFormat_Row";

    /** AD_Table_ID=382 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 6 - System - Client
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(6);

    /** Load Meta Data */

	/**
	 * Get Mandant.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_ImpFormat_Row, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_AD_ImpFormat_Row, org.compiere.model.I_AD_Client>(I_AD_ImpFormat_Row.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Spalte.
	 * Column in the table
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Column_ID (int AD_Column_ID);

	/**
	 * Get Spalte.
	 * Column in the table
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Column_ID();

	public org.compiere.model.I_AD_Column getAD_Column();

	public void setAD_Column(org.compiere.model.I_AD_Column AD_Column);

    /** Column definition for AD_Column_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_ImpFormat_Row, org.compiere.model.I_AD_Column> COLUMN_AD_Column_ID = new org.adempiere.model.ModelColumn<I_AD_ImpFormat_Row, org.compiere.model.I_AD_Column>(I_AD_ImpFormat_Row.class, "AD_Column_ID", org.compiere.model.I_AD_Column.class);
    /** Column name AD_Column_ID */
    public static final String COLUMNNAME_AD_Column_ID = "AD_Column_ID";

	/**
	 * Set Import-Format.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_ImpFormat_ID (int AD_ImpFormat_ID);

	/**
	 * Get Import-Format.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_ImpFormat_ID();

	public org.compiere.model.I_AD_ImpFormat getAD_ImpFormat();

	public void setAD_ImpFormat(org.compiere.model.I_AD_ImpFormat AD_ImpFormat);

    /** Column definition for AD_ImpFormat_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_ImpFormat_Row, org.compiere.model.I_AD_ImpFormat> COLUMN_AD_ImpFormat_ID = new org.adempiere.model.ModelColumn<I_AD_ImpFormat_Row, org.compiere.model.I_AD_ImpFormat>(I_AD_ImpFormat_Row.class, "AD_ImpFormat_ID", org.compiere.model.I_AD_ImpFormat.class);
    /** Column name AD_ImpFormat_ID */
    public static final String COLUMNNAME_AD_ImpFormat_ID = "AD_ImpFormat_ID";

	/**
	 * Set Format-Feld.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_ImpFormat_Row_ID (int AD_ImpFormat_Row_ID);

	/**
	 * Get Format-Feld.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_ImpFormat_Row_ID();

    /** Column definition for AD_ImpFormat_Row_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_ImpFormat_Row, Object> COLUMN_AD_ImpFormat_Row_ID = new org.adempiere.model.ModelColumn<I_AD_ImpFormat_Row, Object>(I_AD_ImpFormat_Row.class, "AD_ImpFormat_Row_ID", null);
    /** Column name AD_ImpFormat_Row_ID */
    public static final String COLUMNNAME_AD_ImpFormat_Row_ID = "AD_ImpFormat_Row_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_ImpFormat_Row, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_AD_ImpFormat_Row, org.compiere.model.I_AD_Org>(I_AD_ImpFormat_Row.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Callout.
	 * Fully qualified class names and method - separated by semicolons
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCallout (java.lang.String Callout);

	/**
	 * Get Callout.
	 * Fully qualified class names and method - separated by semicolons
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCallout();

    /** Column definition for Callout */
    public static final org.adempiere.model.ModelColumn<I_AD_ImpFormat_Row, Object> COLUMN_Callout = new org.adempiere.model.ModelColumn<I_AD_ImpFormat_Row, Object>(I_AD_ImpFormat_Row.class, "Callout", null);
    /** Column name Callout */
    public static final String COLUMNNAME_Callout = "Callout";

	/**
	 * Set Konstante.
	 * Constant value
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setConstantValue (java.lang.String ConstantValue);

	/**
	 * Get Konstante.
	 * Constant value
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getConstantValue();

    /** Column definition for ConstantValue */
    public static final org.adempiere.model.ModelColumn<I_AD_ImpFormat_Row, Object> COLUMN_ConstantValue = new org.adempiere.model.ModelColumn<I_AD_ImpFormat_Row, Object>(I_AD_ImpFormat_Row.class, "ConstantValue", null);
    /** Column name ConstantValue */
    public static final String COLUMNNAME_ConstantValue = "ConstantValue";

	/**
	 * Get Erstellt.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_AD_ImpFormat_Row, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_ImpFormat_Row, Object>(I_AD_ImpFormat_Row.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_AD_ImpFormat_Row, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_AD_ImpFormat_Row, org.compiere.model.I_AD_User>(I_AD_ImpFormat_Row.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Data Format.
	 * Format String in Java Notation, e.g. ddMMyy
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDataFormat (java.lang.String DataFormat);

	/**
	 * Get Data Format.
	 * Format String in Java Notation, e.g. ddMMyy
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDataFormat();

    /** Column definition for DataFormat */
    public static final org.adempiere.model.ModelColumn<I_AD_ImpFormat_Row, Object> COLUMN_DataFormat = new org.adempiere.model.ModelColumn<I_AD_ImpFormat_Row, Object>(I_AD_ImpFormat_Row.class, "DataFormat", null);
    /** Column name DataFormat */
    public static final String COLUMNNAME_DataFormat = "DataFormat";

	/**
	 * Set Daten-Typ.
	 * Type of data
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDataType (java.lang.String DataType);

	/**
	 * Get Daten-Typ.
	 * Type of data
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDataType();

    /** Column definition for DataType */
    public static final org.adempiere.model.ModelColumn<I_AD_ImpFormat_Row, Object> COLUMN_DataType = new org.adempiere.model.ModelColumn<I_AD_ImpFormat_Row, Object>(I_AD_ImpFormat_Row.class, "DataType", null);
    /** Column name DataType */
    public static final String COLUMNNAME_DataType = "DataType";

	/**
	 * Set Dezimal-Punkt.
	 * Decimal Point in the data file - if any
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDecimalPoint (java.lang.String DecimalPoint);

	/**
	 * Get Dezimal-Punkt.
	 * Decimal Point in the data file - if any
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDecimalPoint();

    /** Column definition for DecimalPoint */
    public static final org.adempiere.model.ModelColumn<I_AD_ImpFormat_Row, Object> COLUMN_DecimalPoint = new org.adempiere.model.ModelColumn<I_AD_ImpFormat_Row, Object>(I_AD_ImpFormat_Row.class, "DecimalPoint", null);
    /** Column name DecimalPoint */
    public static final String COLUMNNAME_DecimalPoint = "DecimalPoint";

	/**
	 * Set Durch 100 teilen.
	 * Divide number by 100 to get correct amount
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDivideBy100 (boolean DivideBy100);

	/**
	 * Get Durch 100 teilen.
	 * Divide number by 100 to get correct amount
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isDivideBy100();

    /** Column definition for DivideBy100 */
    public static final org.adempiere.model.ModelColumn<I_AD_ImpFormat_Row, Object> COLUMN_DivideBy100 = new org.adempiere.model.ModelColumn<I_AD_ImpFormat_Row, Object>(I_AD_ImpFormat_Row.class, "DivideBy100", null);
    /** Column name DivideBy100 */
    public static final String COLUMNNAME_DivideBy100 = "DivideBy100";

	/**
	 * Set End-Nr..
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEndNo (int EndNo);

	/**
	 * Get End-Nr..
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getEndNo();

    /** Column definition for EndNo */
    public static final org.adempiere.model.ModelColumn<I_AD_ImpFormat_Row, Object> COLUMN_EndNo = new org.adempiere.model.ModelColumn<I_AD_ImpFormat_Row, Object>(I_AD_ImpFormat_Row.class, "EndNo", null);
    /** Column name EndNo */
    public static final String COLUMNNAME_EndNo = "EndNo";

	/**
	 * Set Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_AD_ImpFormat_Row, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_ImpFormat_Row, Object>(I_AD_ImpFormat_Row.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setName (java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getName();

    /** Column definition for Name */
    public static final org.adempiere.model.ModelColumn<I_AD_ImpFormat_Row, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_AD_ImpFormat_Row, Object>(I_AD_ImpFormat_Row.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Skript.
	 * Dynamic Java Language Script to calculate result
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setScript (java.lang.String Script);

	/**
	 * Get Skript.
	 * Dynamic Java Language Script to calculate result
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getScript();

    /** Column definition for Script */
    public static final org.adempiere.model.ModelColumn<I_AD_ImpFormat_Row, Object> COLUMN_Script = new org.adempiere.model.ModelColumn<I_AD_ImpFormat_Row, Object>(I_AD_ImpFormat_Row.class, "Script", null);
    /** Column name Script */
    public static final String COLUMNNAME_Script = "Script";

	/**
	 * Set Reihenfolge.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setSeqNo (int SeqNo);

	/**
	 * Get Reihenfolge.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getSeqNo();

    /** Column definition for SeqNo */
    public static final org.adempiere.model.ModelColumn<I_AD_ImpFormat_Row, Object> COLUMN_SeqNo = new org.adempiere.model.ModelColumn<I_AD_ImpFormat_Row, Object>(I_AD_ImpFormat_Row.class, "SeqNo", null);
    /** Column name SeqNo */
    public static final String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Set Start No.
	 * Starting number/position
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setStartNo (int StartNo);

	/**
	 * Get Start No.
	 * Starting number/position
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getStartNo();

    /** Column definition for StartNo */
    public static final org.adempiere.model.ModelColumn<I_AD_ImpFormat_Row, Object> COLUMN_StartNo = new org.adempiere.model.ModelColumn<I_AD_ImpFormat_Row, Object>(I_AD_ImpFormat_Row.class, "StartNo", null);
    /** Column name StartNo */
    public static final String COLUMNNAME_StartNo = "StartNo";

	/**
	 * Get Aktualisiert.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_AD_ImpFormat_Row, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_ImpFormat_Row, Object>(I_AD_ImpFormat_Row.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_AD_ImpFormat_Row, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_AD_ImpFormat_Row, org.compiere.model.I_AD_User>(I_AD_ImpFormat_Row.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
