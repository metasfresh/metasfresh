package org.compiere.model;


/** Generated Interface for AD_PrintFormat
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_PrintFormat 
{

    /** TableName=AD_PrintFormat */
    public static final String Table_Name = "AD_PrintFormat";

    /** AD_Table_ID=493 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 7 - System - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(7);

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

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_PrintFormat, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_AD_PrintFormat, org.compiere.model.I_AD_Client>(I_AD_PrintFormat.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_AD_PrintFormat, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_AD_PrintFormat, org.compiere.model.I_AD_Org>(I_AD_PrintFormat.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Druck - Farbe.
	 * Color used for printing and display
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_PrintColor_ID (int AD_PrintColor_ID);

	/**
	 * Get Druck - Farbe.
	 * Color used for printing and display
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_PrintColor_ID();

	public org.compiere.model.I_AD_PrintColor getAD_PrintColor();

	public void setAD_PrintColor(org.compiere.model.I_AD_PrintColor AD_PrintColor);

    /** Column definition for AD_PrintColor_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_PrintFormat, org.compiere.model.I_AD_PrintColor> COLUMN_AD_PrintColor_ID = new org.adempiere.model.ModelColumn<I_AD_PrintFormat, org.compiere.model.I_AD_PrintColor>(I_AD_PrintFormat.class, "AD_PrintColor_ID", org.compiere.model.I_AD_PrintColor.class);
    /** Column name AD_PrintColor_ID */
    public static final String COLUMNNAME_AD_PrintColor_ID = "AD_PrintColor_ID";

	/**
	 * Set Druck - Schrift.
	 * Maintain Print Font
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_PrintFont_ID (int AD_PrintFont_ID);

	/**
	 * Get Druck - Schrift.
	 * Maintain Print Font
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_PrintFont_ID();

	public org.compiere.model.I_AD_PrintFont getAD_PrintFont();

	public void setAD_PrintFont(org.compiere.model.I_AD_PrintFont AD_PrintFont);

    /** Column definition for AD_PrintFont_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_PrintFormat, org.compiere.model.I_AD_PrintFont> COLUMN_AD_PrintFont_ID = new org.adempiere.model.ModelColumn<I_AD_PrintFormat, org.compiere.model.I_AD_PrintFont>(I_AD_PrintFormat.class, "AD_PrintFont_ID", org.compiere.model.I_AD_PrintFont.class);
    /** Column name AD_PrintFont_ID */
    public static final String COLUMNNAME_AD_PrintFont_ID = "AD_PrintFont_ID";

	/**
	 * Set Formatgruppe.
	 * Gruppe von Druckformaten, die die für den Druck des selben Beleges in Frage kommen
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Printformat_Group (java.lang.String AD_Printformat_Group);

	/**
	 * Get Formatgruppe.
	 * Gruppe von Druckformaten, die die für den Druck des selben Beleges in Frage kommen
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAD_Printformat_Group();

    /** Column definition for AD_Printformat_Group */
    public static final org.adempiere.model.ModelColumn<I_AD_PrintFormat, Object> COLUMN_AD_Printformat_Group = new org.adempiere.model.ModelColumn<I_AD_PrintFormat, Object>(I_AD_PrintFormat.class, "AD_Printformat_Group", null);
    /** Column name AD_Printformat_Group */
    public static final String COLUMNNAME_AD_Printformat_Group = "AD_Printformat_Group";

	/**
	 * Set Druck - Format.
	 * Data Print Format
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_PrintFormat_ID (int AD_PrintFormat_ID);

	/**
	 * Get Druck - Format.
	 * Data Print Format
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_PrintFormat_ID();

    /** Column definition for AD_PrintFormat_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_PrintFormat, Object> COLUMN_AD_PrintFormat_ID = new org.adempiere.model.ModelColumn<I_AD_PrintFormat, Object>(I_AD_PrintFormat.class, "AD_PrintFormat_ID", null);
    /** Column name AD_PrintFormat_ID */
    public static final String COLUMNNAME_AD_PrintFormat_ID = "AD_PrintFormat_ID";

	/**
	 * Set Druck - Papier.
	 * Printer paper definition
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_PrintPaper_ID (int AD_PrintPaper_ID);

	/**
	 * Get Druck - Papier.
	 * Printer paper definition
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_PrintPaper_ID();

	public org.compiere.model.I_AD_PrintPaper getAD_PrintPaper();

	public void setAD_PrintPaper(org.compiere.model.I_AD_PrintPaper AD_PrintPaper);

    /** Column definition for AD_PrintPaper_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_PrintFormat, org.compiere.model.I_AD_PrintPaper> COLUMN_AD_PrintPaper_ID = new org.adempiere.model.ModelColumn<I_AD_PrintFormat, org.compiere.model.I_AD_PrintPaper>(I_AD_PrintFormat.class, "AD_PrintPaper_ID", org.compiere.model.I_AD_PrintPaper.class);
    /** Column name AD_PrintPaper_ID */
    public static final String COLUMNNAME_AD_PrintPaper_ID = "AD_PrintPaper_ID";

	/**
	 * Set Druck - Tabellenformat.
	 * Table Format in Reports
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_PrintTableFormat_ID (int AD_PrintTableFormat_ID);

	/**
	 * Get Druck - Tabellenformat.
	 * Table Format in Reports
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_PrintTableFormat_ID();

	public org.compiere.model.I_AD_PrintTableFormat getAD_PrintTableFormat();

	public void setAD_PrintTableFormat(org.compiere.model.I_AD_PrintTableFormat AD_PrintTableFormat);

    /** Column definition for AD_PrintTableFormat_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_PrintFormat, org.compiere.model.I_AD_PrintTableFormat> COLUMN_AD_PrintTableFormat_ID = new org.adempiere.model.ModelColumn<I_AD_PrintFormat, org.compiere.model.I_AD_PrintTableFormat>(I_AD_PrintFormat.class, "AD_PrintTableFormat_ID", org.compiere.model.I_AD_PrintTableFormat.class);
    /** Column name AD_PrintTableFormat_ID */
    public static final String COLUMNNAME_AD_PrintTableFormat_ID = "AD_PrintTableFormat_ID";

	/**
	 * Set Berichts-View.
	 * View used to generate this report
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_ReportView_ID (int AD_ReportView_ID);

	/**
	 * Get Berichts-View.
	 * View used to generate this report
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_ReportView_ID();

	public org.compiere.model.I_AD_ReportView getAD_ReportView();

	public void setAD_ReportView(org.compiere.model.I_AD_ReportView AD_ReportView);

    /** Column definition for AD_ReportView_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_PrintFormat, org.compiere.model.I_AD_ReportView> COLUMN_AD_ReportView_ID = new org.adempiere.model.ModelColumn<I_AD_PrintFormat, org.compiere.model.I_AD_ReportView>(I_AD_PrintFormat.class, "AD_ReportView_ID", org.compiere.model.I_AD_ReportView.class);
    /** Column name AD_ReportView_ID */
    public static final String COLUMNNAME_AD_ReportView_ID = "AD_ReportView_ID";

	/**
	 * Set DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Table_ID();

	public org.compiere.model.I_AD_Table getAD_Table();

	public void setAD_Table(org.compiere.model.I_AD_Table AD_Table);

    /** Column definition for AD_Table_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_PrintFormat, org.compiere.model.I_AD_Table> COLUMN_AD_Table_ID = new org.adempiere.model.ModelColumn<I_AD_PrintFormat, org.compiere.model.I_AD_Table>(I_AD_PrintFormat.class, "AD_Table_ID", org.compiere.model.I_AD_Table.class);
    /** Column name AD_Table_ID */
    public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * Set Args.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setArgs (java.lang.String Args);

	/**
	 * Get Args.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getArgs();

    /** Column definition for Args */
    public static final org.adempiere.model.ModelColumn<I_AD_PrintFormat, Object> COLUMN_Args = new org.adempiere.model.ModelColumn<I_AD_PrintFormat, Object>(I_AD_PrintFormat.class, "Args", null);
    /** Column name Args */
    public static final String COLUMNNAME_Args = "Args";

	/**
	 * Set Create Copy.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCreateCopy (java.lang.String CreateCopy);

	/**
	 * Get Create Copy.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCreateCopy();

    /** Column definition for CreateCopy */
    public static final org.adempiere.model.ModelColumn<I_AD_PrintFormat, Object> COLUMN_CreateCopy = new org.adempiere.model.ModelColumn<I_AD_PrintFormat, Object>(I_AD_PrintFormat.class, "CreateCopy", null);
    /** Column name CreateCopy */
    public static final String COLUMNNAME_CreateCopy = "CreateCopy";

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
    public static final org.adempiere.model.ModelColumn<I_AD_PrintFormat, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_PrintFormat, Object>(I_AD_PrintFormat.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_PrintFormat, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_AD_PrintFormat, org.compiere.model.I_AD_User>(I_AD_PrintFormat.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_AD_PrintFormat, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_AD_PrintFormat, Object>(I_AD_PrintFormat.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Footer Margin.
	 * Margin of the Footer in 1/72 of an inch
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setFooterMargin (int FooterMargin);

	/**
	 * Get Footer Margin.
	 * Margin of the Footer in 1/72 of an inch
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getFooterMargin();

    /** Column definition for FooterMargin */
    public static final org.adempiere.model.ModelColumn<I_AD_PrintFormat, Object> COLUMN_FooterMargin = new org.adempiere.model.ModelColumn<I_AD_PrintFormat, Object>(I_AD_PrintFormat.class, "FooterMargin", null);
    /** Column name FooterMargin */
    public static final String COLUMNNAME_FooterMargin = "FooterMargin";

	/**
	 * Set Header Margin.
	 * Margin of the Header in 1/72 of an inch
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setHeaderMargin (int HeaderMargin);

	/**
	 * Get Header Margin.
	 * Margin of the Header in 1/72 of an inch
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getHeaderMargin();

    /** Column definition for HeaderMargin */
    public static final org.adempiere.model.ModelColumn<I_AD_PrintFormat, Object> COLUMN_HeaderMargin = new org.adempiere.model.ModelColumn<I_AD_PrintFormat, Object>(I_AD_PrintFormat.class, "HeaderMargin", null);
    /** Column name HeaderMargin */
    public static final String COLUMNNAME_HeaderMargin = "HeaderMargin";

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
    public static final org.adempiere.model.ModelColumn<I_AD_PrintFormat, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_PrintFormat, Object>(I_AD_PrintFormat.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Standard.
	 * Default value
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsDefault (boolean IsDefault);

	/**
	 * Get Standard.
	 * Default value
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isDefault();

    /** Column definition for IsDefault */
    public static final org.adempiere.model.ModelColumn<I_AD_PrintFormat, Object> COLUMN_IsDefault = new org.adempiere.model.ModelColumn<I_AD_PrintFormat, Object>(I_AD_PrintFormat.class, "IsDefault", null);
    /** Column name IsDefault */
    public static final String COLUMNNAME_IsDefault = "IsDefault";

	/**
	 * Set Fenster (nicht dynamisch).
	 * If Selected, a Form is printed, if not selected a columnar List report
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsForm (boolean IsForm);

	/**
	 * Get Fenster (nicht dynamisch).
	 * If Selected, a Form is printed, if not selected a columnar List report
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isForm();

    /** Column definition for IsForm */
    public static final org.adempiere.model.ModelColumn<I_AD_PrintFormat, Object> COLUMN_IsForm = new org.adempiere.model.ModelColumn<I_AD_PrintFormat, Object>(I_AD_PrintFormat.class, "IsForm", null);
    /** Column name IsForm */
    public static final String COLUMNNAME_IsForm = "IsForm";

	/**
	 * Set Standard Header/Footer.
	 * The standard Header and Footer is used
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsStandardHeaderFooter (boolean IsStandardHeaderFooter);

	/**
	 * Get Standard Header/Footer.
	 * The standard Header and Footer is used
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isStandardHeaderFooter();

    /** Column definition for IsStandardHeaderFooter */
    public static final org.adempiere.model.ModelColumn<I_AD_PrintFormat, Object> COLUMN_IsStandardHeaderFooter = new org.adempiere.model.ModelColumn<I_AD_PrintFormat, Object>(I_AD_PrintFormat.class, "IsStandardHeaderFooter", null);
    /** Column name IsStandardHeaderFooter */
    public static final String COLUMNNAME_IsStandardHeaderFooter = "IsStandardHeaderFooter";

	/**
	 * Set Table Based.
	 * Table based List Reporting
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsTableBased (boolean IsTableBased);

	/**
	 * Get Table Based.
	 * Table based List Reporting
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isTableBased();

    /** Column definition for IsTableBased */
    public static final org.adempiere.model.ModelColumn<I_AD_PrintFormat, Object> COLUMN_IsTableBased = new org.adempiere.model.ModelColumn<I_AD_PrintFormat, Object>(I_AD_PrintFormat.class, "IsTableBased", null);
    /** Column name IsTableBased */
    public static final String COLUMNNAME_IsTableBased = "IsTableBased";

	/**
	 * Set Jasper Process.
	 * The Jasper Process used by the printengine if any process defined
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setJasperProcess_ID (int JasperProcess_ID);

	/**
	 * Get Jasper Process.
	 * The Jasper Process used by the printengine if any process defined
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getJasperProcess_ID();

	public org.compiere.model.I_AD_Process getJasperProcess();

	public void setJasperProcess(org.compiere.model.I_AD_Process JasperProcess);

    /** Column definition for JasperProcess_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_PrintFormat, org.compiere.model.I_AD_Process> COLUMN_JasperProcess_ID = new org.adempiere.model.ModelColumn<I_AD_PrintFormat, org.compiere.model.I_AD_Process>(I_AD_PrintFormat.class, "JasperProcess_ID", org.compiere.model.I_AD_Process.class);
    /** Column name JasperProcess_ID */
    public static final String COLUMNNAME_JasperProcess_ID = "JasperProcess_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_PrintFormat, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_AD_PrintFormat, Object>(I_AD_PrintFormat.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Drucker.
	 * Name of the Printer
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPrinterName (java.lang.String PrinterName);

	/**
	 * Get Drucker.
	 * Name of the Printer
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPrinterName();

    /** Column definition for PrinterName */
    public static final org.adempiere.model.ModelColumn<I_AD_PrintFormat, Object> COLUMN_PrinterName = new org.adempiere.model.ModelColumn<I_AD_PrintFormat, Object>(I_AD_PrintFormat.class, "PrinterName", null);
    /** Column name PrinterName */
    public static final String COLUMNNAME_PrinterName = "PrinterName";

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
    public static final org.adempiere.model.ModelColumn<I_AD_PrintFormat, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_PrintFormat, Object>(I_AD_PrintFormat.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_PrintFormat, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_AD_PrintFormat, org.compiere.model.I_AD_User>(I_AD_PrintFormat.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
