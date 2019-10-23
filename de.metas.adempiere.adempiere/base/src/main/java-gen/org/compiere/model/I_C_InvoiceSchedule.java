package org.compiere.model;


/** Generated Interface for C_InvoiceSchedule
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_InvoiceSchedule 
{

    /** TableName=C_InvoiceSchedule */
    public static final String Table_Name = "C_InvoiceSchedule";

    /** AD_Table_ID=257 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

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
    public static final org.adempiere.model.ModelColumn<I_C_InvoiceSchedule, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_InvoiceSchedule, org.compiere.model.I_AD_Client>(I_C_InvoiceSchedule.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_InvoiceSchedule, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_InvoiceSchedule, org.compiere.model.I_AD_Org>(I_C_InvoiceSchedule.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Betrag.
	 * Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAmt (java.math.BigDecimal Amt);

	/**
	 * Get Betrag.
	 * Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getAmt();

    /** Column definition for Amt */
    public static final org.adempiere.model.ModelColumn<I_C_InvoiceSchedule, Object> COLUMN_Amt = new org.adempiere.model.ModelColumn<I_C_InvoiceSchedule, Object>(I_C_InvoiceSchedule.class, "Amt", null);
    /** Column name Amt */
    public static final String COLUMNNAME_Amt = "Amt";

	/**
	 * Set Terminplan Rechnung.
	 * Schedule for generating Invoices
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_InvoiceSchedule_ID (int C_InvoiceSchedule_ID);

	/**
	 * Get Terminplan Rechnung.
	 * Schedule for generating Invoices
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_InvoiceSchedule_ID();

    /** Column definition for C_InvoiceSchedule_ID */
    public static final org.adempiere.model.ModelColumn<I_C_InvoiceSchedule, Object> COLUMN_C_InvoiceSchedule_ID = new org.adempiere.model.ModelColumn<I_C_InvoiceSchedule, Object>(I_C_InvoiceSchedule.class, "C_InvoiceSchedule_ID", null);
    /** Column name C_InvoiceSchedule_ID */
    public static final String COLUMNNAME_C_InvoiceSchedule_ID = "C_InvoiceSchedule_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_InvoiceSchedule, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_InvoiceSchedule, Object>(I_C_InvoiceSchedule.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_InvoiceSchedule, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_InvoiceSchedule, org.compiere.model.I_AD_User>(I_C_InvoiceSchedule.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_InvoiceSchedule, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_C_InvoiceSchedule, Object>(I_C_InvoiceSchedule.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Invoice on even weeks.
	 * Send invoices on even weeks
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEvenInvoiceWeek (boolean EvenInvoiceWeek);

	/**
	 * Get Invoice on even weeks.
	 * Send invoices on even weeks
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isEvenInvoiceWeek();

    /** Column definition for EvenInvoiceWeek */
    public static final org.adempiere.model.ModelColumn<I_C_InvoiceSchedule, Object> COLUMN_EvenInvoiceWeek = new org.adempiere.model.ModelColumn<I_C_InvoiceSchedule, Object>(I_C_InvoiceSchedule.class, "EvenInvoiceWeek", null);
    /** Column name EvenInvoiceWeek */
    public static final String COLUMNNAME_EvenInvoiceWeek = "EvenInvoiceWeek";

	/**
	 * Set Rechnungstag.
	 * Day of Invoice Generation
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setInvoiceDay (int InvoiceDay);

	/**
	 * Get Rechnungstag.
	 * Day of Invoice Generation
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getInvoiceDay();

    /** Column definition for InvoiceDay */
    public static final org.adempiere.model.ModelColumn<I_C_InvoiceSchedule, Object> COLUMN_InvoiceDay = new org.adempiere.model.ModelColumn<I_C_InvoiceSchedule, Object>(I_C_InvoiceSchedule.class, "InvoiceDay", null);
    /** Column name InvoiceDay */
    public static final String COLUMNNAME_InvoiceDay = "InvoiceDay";

	/**
	 * Set Letzter Tag Lieferungen.
	 * Last day for including shipments
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setInvoiceDayCutoff (int InvoiceDayCutoff);

	/**
	 * Get Letzter Tag Lieferungen.
	 * Last day for including shipments
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getInvoiceDayCutoff();

    /** Column definition for InvoiceDayCutoff */
    public static final org.adempiere.model.ModelColumn<I_C_InvoiceSchedule, Object> COLUMN_InvoiceDayCutoff = new org.adempiere.model.ModelColumn<I_C_InvoiceSchedule, Object>(I_C_InvoiceSchedule.class, "InvoiceDayCutoff", null);
    /** Column name InvoiceDayCutoff */
    public static final String COLUMNNAME_InvoiceDayCutoff = "InvoiceDayCutoff";

	/**
	 * Set Anz. Einheiten zwischen zwei Rechnungsstellungen.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setInvoiceDistance (int InvoiceDistance);

	/**
	 * Get Anz. Einheiten zwischen zwei Rechnungsstellungen.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getInvoiceDistance();

    /** Column definition for InvoiceDistance */
    public static final org.adempiere.model.ModelColumn<I_C_InvoiceSchedule, Object> COLUMN_InvoiceDistance = new org.adempiere.model.ModelColumn<I_C_InvoiceSchedule, Object>(I_C_InvoiceSchedule.class, "InvoiceDistance", null);
    /** Column name InvoiceDistance */
    public static final String COLUMNNAME_InvoiceDistance = "InvoiceDistance";

	/**
	 * Set Rechnungshäufigkeit.
	 * How often invoices will be generated
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setInvoiceFrequency (java.lang.String InvoiceFrequency);

	/**
	 * Get Rechnungshäufigkeit.
	 * How often invoices will be generated
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getInvoiceFrequency();

    /** Column definition for InvoiceFrequency */
    public static final org.adempiere.model.ModelColumn<I_C_InvoiceSchedule, Object> COLUMN_InvoiceFrequency = new org.adempiere.model.ModelColumn<I_C_InvoiceSchedule, Object>(I_C_InvoiceSchedule.class, "InvoiceFrequency", null);
    /** Column name InvoiceFrequency */
    public static final String COLUMNNAME_InvoiceFrequency = "InvoiceFrequency";

	/**
	 * Set Wochentag.
	 * Day to generate invoices
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setInvoiceWeekDay (java.lang.String InvoiceWeekDay);

	/**
	 * Get Wochentag.
	 * Day to generate invoices
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getInvoiceWeekDay();

    /** Column definition for InvoiceWeekDay */
    public static final org.adempiere.model.ModelColumn<I_C_InvoiceSchedule, Object> COLUMN_InvoiceWeekDay = new org.adempiere.model.ModelColumn<I_C_InvoiceSchedule, Object>(I_C_InvoiceSchedule.class, "InvoiceWeekDay", null);
    /** Column name InvoiceWeekDay */
    public static final String COLUMNNAME_InvoiceWeekDay = "InvoiceWeekDay";

	/**
	 * Set Letzter Wochentag Lieferungen.
	 * Last day in the week for shipments to be included
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setInvoiceWeekDayCutoff (java.lang.String InvoiceWeekDayCutoff);

	/**
	 * Get Letzter Wochentag Lieferungen.
	 * Last day in the week for shipments to be included
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getInvoiceWeekDayCutoff();

    /** Column definition for InvoiceWeekDayCutoff */
    public static final org.adempiere.model.ModelColumn<I_C_InvoiceSchedule, Object> COLUMN_InvoiceWeekDayCutoff = new org.adempiere.model.ModelColumn<I_C_InvoiceSchedule, Object>(I_C_InvoiceSchedule.class, "InvoiceWeekDayCutoff", null);
    /** Column name InvoiceWeekDayCutoff */
    public static final String COLUMNNAME_InvoiceWeekDayCutoff = "InvoiceWeekDayCutoff";

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
    public static final org.adempiere.model.ModelColumn<I_C_InvoiceSchedule, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_InvoiceSchedule, Object>(I_C_InvoiceSchedule.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Betragsgrenze.
	 * Send invoices only if the amount exceeds the limit
IMPORTANT: currently not used;

	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsAmount (boolean IsAmount);

	/**
	 * Get Betragsgrenze.
	 * Send invoices only if the amount exceeds the limit
IMPORTANT: currently not used;

	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAmount();

    /** Column definition for IsAmount */
    public static final org.adempiere.model.ModelColumn<I_C_InvoiceSchedule, Object> COLUMN_IsAmount = new org.adempiere.model.ModelColumn<I_C_InvoiceSchedule, Object>(I_C_InvoiceSchedule.class, "IsAmount", null);
    /** Column name IsAmount */
    public static final String COLUMNNAME_IsAmount = "IsAmount";

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
    public static final org.adempiere.model.ModelColumn<I_C_InvoiceSchedule, Object> COLUMN_IsDefault = new org.adempiere.model.ModelColumn<I_C_InvoiceSchedule, Object>(I_C_InvoiceSchedule.class, "IsDefault", null);
    /** Column name IsDefault */
    public static final String COLUMNNAME_IsDefault = "IsDefault";

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
    public static final org.adempiere.model.ModelColumn<I_C_InvoiceSchedule, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_C_InvoiceSchedule, Object>(I_C_InvoiceSchedule.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

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
    public static final org.adempiere.model.ModelColumn<I_C_InvoiceSchedule, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_InvoiceSchedule, Object>(I_C_InvoiceSchedule.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_InvoiceSchedule, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_InvoiceSchedule, org.compiere.model.I_AD_User>(I_C_InvoiceSchedule.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
