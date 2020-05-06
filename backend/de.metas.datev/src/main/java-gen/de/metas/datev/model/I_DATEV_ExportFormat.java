package de.metas.datev.model;


/** Generated Interface for DATEV_ExportFormat
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_DATEV_ExportFormat 
{

    /** TableName=DATEV_ExportFormat */
    public static final String Table_Name = "DATEV_ExportFormat";

    /** AD_Table_ID=540938 */
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
    public static final org.adempiere.model.ModelColumn<I_DATEV_ExportFormat, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_DATEV_ExportFormat, org.compiere.model.I_AD_Client>(I_DATEV_ExportFormat.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_DATEV_ExportFormat, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_DATEV_ExportFormat, org.compiere.model.I_AD_Org>(I_DATEV_ExportFormat.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

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
    public static final org.adempiere.model.ModelColumn<I_DATEV_ExportFormat, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_DATEV_ExportFormat, Object>(I_DATEV_ExportFormat.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_DATEV_ExportFormat, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_DATEV_ExportFormat, org.compiere.model.I_AD_User>(I_DATEV_ExportFormat.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set CSV Encoding.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setCSVEncoding (java.lang.String CSVEncoding);

	/**
	 * Get CSV Encoding.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCSVEncoding();

    /** Column definition for CSVEncoding */
    public static final org.adempiere.model.ModelColumn<I_DATEV_ExportFormat, Object> COLUMN_CSVEncoding = new org.adempiere.model.ModelColumn<I_DATEV_ExportFormat, Object>(I_DATEV_ExportFormat.class, "CSVEncoding", null);
    /** Column name CSVEncoding */
    public static final String COLUMNNAME_CSVEncoding = "CSVEncoding";

	/**
	 * Set CSV Field Delimiter.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setCSVFieldDelimiter (java.lang.String CSVFieldDelimiter);

	/**
	 * Get CSV Field Delimiter.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCSVFieldDelimiter();

    /** Column definition for CSVFieldDelimiter */
    public static final org.adempiere.model.ModelColumn<I_DATEV_ExportFormat, Object> COLUMN_CSVFieldDelimiter = new org.adempiere.model.ModelColumn<I_DATEV_ExportFormat, Object>(I_DATEV_ExportFormat.class, "CSVFieldDelimiter", null);
    /** Column name CSVFieldDelimiter */
    public static final String COLUMNNAME_CSVFieldDelimiter = "CSVFieldDelimiter";

	/**
	 * Set CSV Field Quote.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCSVFieldQuote (java.lang.String CSVFieldQuote);

	/**
	 * Get CSV Field Quote.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCSVFieldQuote();

    /** Column definition for CSVFieldQuote */
    public static final org.adempiere.model.ModelColumn<I_DATEV_ExportFormat, Object> COLUMN_CSVFieldQuote = new org.adempiere.model.ModelColumn<I_DATEV_ExportFormat, Object>(I_DATEV_ExportFormat.class, "CSVFieldQuote", null);
    /** Column name CSVFieldQuote */
    public static final String COLUMNNAME_CSVFieldQuote = "CSVFieldQuote";

	/**
	 * Set DATEV Export Format.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDATEV_ExportFormat_ID (int DATEV_ExportFormat_ID);

	/**
	 * Get DATEV Export Format.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getDATEV_ExportFormat_ID();

    /** Column definition for DATEV_ExportFormat_ID */
    public static final org.adempiere.model.ModelColumn<I_DATEV_ExportFormat, Object> COLUMN_DATEV_ExportFormat_ID = new org.adempiere.model.ModelColumn<I_DATEV_ExportFormat, Object>(I_DATEV_ExportFormat.class, "DATEV_ExportFormat_ID", null);
    /** Column name DATEV_ExportFormat_ID */
    public static final String COLUMNNAME_DATEV_ExportFormat_ID = "DATEV_ExportFormat_ID";

	/**
	 * Set Decimal Separator.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDecimalSeparator (java.lang.String DecimalSeparator);

	/**
	 * Get Decimal Separator.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDecimalSeparator();

    /** Column definition for DecimalSeparator */
    public static final org.adempiere.model.ModelColumn<I_DATEV_ExportFormat, Object> COLUMN_DecimalSeparator = new org.adempiere.model.ModelColumn<I_DATEV_ExportFormat, Object>(I_DATEV_ExportFormat.class, "DecimalSeparator", null);
    /** Column name DecimalSeparator */
    public static final String COLUMNNAME_DecimalSeparator = "DecimalSeparator";

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
    public static final org.adempiere.model.ModelColumn<I_DATEV_ExportFormat, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_DATEV_ExportFormat, Object>(I_DATEV_ExportFormat.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

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
    public static final org.adempiere.model.ModelColumn<I_DATEV_ExportFormat, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_DATEV_ExportFormat, Object>(I_DATEV_ExportFormat.class, "IsActive", null);
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
    public static final org.adempiere.model.ModelColumn<I_DATEV_ExportFormat, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_DATEV_ExportFormat, Object>(I_DATEV_ExportFormat.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Number Grouping Separator.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setNumberGroupingSeparator (java.lang.String NumberGroupingSeparator);

	/**
	 * Get Number Grouping Separator.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getNumberGroupingSeparator();

    /** Column definition for NumberGroupingSeparator */
    public static final org.adempiere.model.ModelColumn<I_DATEV_ExportFormat, Object> COLUMN_NumberGroupingSeparator = new org.adempiere.model.ModelColumn<I_DATEV_ExportFormat, Object>(I_DATEV_ExportFormat.class, "NumberGroupingSeparator", null);
    /** Column name NumberGroupingSeparator */
    public static final String COLUMNNAME_NumberGroupingSeparator = "NumberGroupingSeparator";

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
    public static final org.adempiere.model.ModelColumn<I_DATEV_ExportFormat, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_DATEV_ExportFormat, Object>(I_DATEV_ExportFormat.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_DATEV_ExportFormat, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_DATEV_ExportFormat, org.compiere.model.I_AD_User>(I_DATEV_ExportFormat.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
