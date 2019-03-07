package org.compiere.model;


/** Generated Interface for S_ResourceType
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_S_ResourceType 
{

    /** TableName=S_ResourceType */
    public static final String Table_Name = "S_ResourceType";

    /** AD_Table_ID=480 */
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
    public static final org.adempiere.model.ModelColumn<I_S_ResourceType, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_S_ResourceType, org.compiere.model.I_AD_Client>(I_S_ResourceType.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_S_ResourceType, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_S_ResourceType, org.compiere.model.I_AD_Org>(I_S_ResourceType.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Bruchteil der Mengeneinheit zulassen.
	 * Allow Unit of Measure Fractions
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAllowUoMFractions (boolean AllowUoMFractions);

	/**
	 * Get Bruchteil der Mengeneinheit zulassen.
	 * Allow Unit of Measure Fractions
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAllowUoMFractions();

    /** Column definition for AllowUoMFractions */
    public static final org.adempiere.model.ModelColumn<I_S_ResourceType, Object> COLUMN_AllowUoMFractions = new org.adempiere.model.ModelColumn<I_S_ResourceType, Object>(I_S_ResourceType.class, "AllowUoMFractions", null);
    /** Column name AllowUoMFractions */
    public static final String COLUMNNAME_AllowUoMFractions = "AllowUoMFractions";

	/**
	 * Set Maßeinheit.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get Maßeinheit.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_UOM_ID();

	public org.compiere.model.I_C_UOM getC_UOM();

	public void setC_UOM(org.compiere.model.I_C_UOM C_UOM);

    /** Column definition for C_UOM_ID */
    public static final org.adempiere.model.ModelColumn<I_S_ResourceType, org.compiere.model.I_C_UOM> COLUMN_C_UOM_ID = new org.adempiere.model.ModelColumn<I_S_ResourceType, org.compiere.model.I_C_UOM>(I_S_ResourceType.class, "C_UOM_ID", org.compiere.model.I_C_UOM.class);
    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Set Abrechenbare Menge.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setChargeableQty (int ChargeableQty);

	/**
	 * Get Abrechenbare Menge.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getChargeableQty();

    /** Column definition for ChargeableQty */
    public static final org.adempiere.model.ModelColumn<I_S_ResourceType, Object> COLUMN_ChargeableQty = new org.adempiere.model.ModelColumn<I_S_ResourceType, Object>(I_S_ResourceType.class, "ChargeableQty", null);
    /** Column name ChargeableQty */
    public static final String COLUMNNAME_ChargeableQty = "ChargeableQty";

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
    public static final org.adempiere.model.ModelColumn<I_S_ResourceType, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_S_ResourceType, Object>(I_S_ResourceType.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_S_ResourceType, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_S_ResourceType, org.compiere.model.I_AD_User>(I_S_ResourceType.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_S_ResourceType, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_S_ResourceType, Object>(I_S_ResourceType.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

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
    public static final org.adempiere.model.ModelColumn<I_S_ResourceType, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_S_ResourceType, Object>(I_S_ResourceType.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Tag.
	 * Resource has day slot availability
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsDateSlot (boolean IsDateSlot);

	/**
	 * Get Tag.
	 * Resource has day slot availability
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isDateSlot();

    /** Column definition for IsDateSlot */
    public static final org.adempiere.model.ModelColumn<I_S_ResourceType, Object> COLUMN_IsDateSlot = new org.adempiere.model.ModelColumn<I_S_ResourceType, Object>(I_S_ResourceType.class, "IsDateSlot", null);
    /** Column name IsDateSlot */
    public static final String COLUMNNAME_IsDateSlot = "IsDateSlot";

	/**
	 * Set Nur einmalige Zuordnung.
	 * Only one assignment at a time (no double-booking or overlapping)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsSingleAssignment (boolean IsSingleAssignment);

	/**
	 * Get Nur einmalige Zuordnung.
	 * Only one assignment at a time (no double-booking or overlapping)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isSingleAssignment();

    /** Column definition for IsSingleAssignment */
    public static final org.adempiere.model.ModelColumn<I_S_ResourceType, Object> COLUMN_IsSingleAssignment = new org.adempiere.model.ModelColumn<I_S_ResourceType, Object>(I_S_ResourceType.class, "IsSingleAssignment", null);
    /** Column name IsSingleAssignment */
    public static final String COLUMNNAME_IsSingleAssignment = "IsSingleAssignment";

	/**
	 * Set Zeitabschnitt.
	 * Resource has time slot availability
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsTimeSlot (boolean IsTimeSlot);

	/**
	 * Get Zeitabschnitt.
	 * Resource has time slot availability
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isTimeSlot();

    /** Column definition for IsTimeSlot */
    public static final org.adempiere.model.ModelColumn<I_S_ResourceType, Object> COLUMN_IsTimeSlot = new org.adempiere.model.ModelColumn<I_S_ResourceType, Object>(I_S_ResourceType.class, "IsTimeSlot", null);
    /** Column name IsTimeSlot */
    public static final String COLUMNNAME_IsTimeSlot = "IsTimeSlot";

	/**
	 * Set Produkt Kategorie.
	 * Kategorie eines Produktes
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Product_Category_ID (int M_Product_Category_ID);

	/**
	 * Get Produkt Kategorie.
	 * Kategorie eines Produktes
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Product_Category_ID();

	public org.compiere.model.I_M_Product_Category getM_Product_Category();

	public void setM_Product_Category(org.compiere.model.I_M_Product_Category M_Product_Category);

    /** Column definition for M_Product_Category_ID */
    public static final org.adempiere.model.ModelColumn<I_S_ResourceType, org.compiere.model.I_M_Product_Category> COLUMN_M_Product_Category_ID = new org.adempiere.model.ModelColumn<I_S_ResourceType, org.compiere.model.I_M_Product_Category>(I_S_ResourceType.class, "M_Product_Category_ID", org.compiere.model.I_M_Product_Category.class);
    /** Column name M_Product_Category_ID */
    public static final String COLUMNNAME_M_Product_Category_ID = "M_Product_Category_ID";

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
    public static final org.adempiere.model.ModelColumn<I_S_ResourceType, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_S_ResourceType, Object>(I_S_ResourceType.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Freitag.
	 * Available on Fridays
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setOnFriday (boolean OnFriday);

	/**
	 * Get Freitag.
	 * Available on Fridays
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isOnFriday();

    /** Column definition for OnFriday */
    public static final org.adempiere.model.ModelColumn<I_S_ResourceType, Object> COLUMN_OnFriday = new org.adempiere.model.ModelColumn<I_S_ResourceType, Object>(I_S_ResourceType.class, "OnFriday", null);
    /** Column name OnFriday */
    public static final String COLUMNNAME_OnFriday = "OnFriday";

	/**
	 * Set Montag.
	 * Available on Mondays
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setOnMonday (boolean OnMonday);

	/**
	 * Get Montag.
	 * Available on Mondays
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isOnMonday();

    /** Column definition for OnMonday */
    public static final org.adempiere.model.ModelColumn<I_S_ResourceType, Object> COLUMN_OnMonday = new org.adempiere.model.ModelColumn<I_S_ResourceType, Object>(I_S_ResourceType.class, "OnMonday", null);
    /** Column name OnMonday */
    public static final String COLUMNNAME_OnMonday = "OnMonday";

	/**
	 * Set Samstag.
	 * Available on Saturday
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setOnSaturday (boolean OnSaturday);

	/**
	 * Get Samstag.
	 * Available on Saturday
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isOnSaturday();

    /** Column definition for OnSaturday */
    public static final org.adempiere.model.ModelColumn<I_S_ResourceType, Object> COLUMN_OnSaturday = new org.adempiere.model.ModelColumn<I_S_ResourceType, Object>(I_S_ResourceType.class, "OnSaturday", null);
    /** Column name OnSaturday */
    public static final String COLUMNNAME_OnSaturday = "OnSaturday";

	/**
	 * Set Sonntag.
	 * Available on Sundays
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setOnSunday (boolean OnSunday);

	/**
	 * Get Sonntag.
	 * Available on Sundays
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isOnSunday();

    /** Column definition for OnSunday */
    public static final org.adempiere.model.ModelColumn<I_S_ResourceType, Object> COLUMN_OnSunday = new org.adempiere.model.ModelColumn<I_S_ResourceType, Object>(I_S_ResourceType.class, "OnSunday", null);
    /** Column name OnSunday */
    public static final String COLUMNNAME_OnSunday = "OnSunday";

	/**
	 * Set Donnerstag.
	 * Available on Thursdays
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setOnThursday (boolean OnThursday);

	/**
	 * Get Donnerstag.
	 * Available on Thursdays
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isOnThursday();

    /** Column definition for OnThursday */
    public static final org.adempiere.model.ModelColumn<I_S_ResourceType, Object> COLUMN_OnThursday = new org.adempiere.model.ModelColumn<I_S_ResourceType, Object>(I_S_ResourceType.class, "OnThursday", null);
    /** Column name OnThursday */
    public static final String COLUMNNAME_OnThursday = "OnThursday";

	/**
	 * Set Dienstag.
	 * Available on Tuesdays
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setOnTuesday (boolean OnTuesday);

	/**
	 * Get Dienstag.
	 * Available on Tuesdays
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isOnTuesday();

    /** Column definition for OnTuesday */
    public static final org.adempiere.model.ModelColumn<I_S_ResourceType, Object> COLUMN_OnTuesday = new org.adempiere.model.ModelColumn<I_S_ResourceType, Object>(I_S_ResourceType.class, "OnTuesday", null);
    /** Column name OnTuesday */
    public static final String COLUMNNAME_OnTuesday = "OnTuesday";

	/**
	 * Set Mittwoch.
	 * Available on Wednesdays
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setOnWednesday (boolean OnWednesday);

	/**
	 * Get Mittwoch.
	 * Available on Wednesdays
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isOnWednesday();

    /** Column definition for OnWednesday */
    public static final org.adempiere.model.ModelColumn<I_S_ResourceType, Object> COLUMN_OnWednesday = new org.adempiere.model.ModelColumn<I_S_ResourceType, Object>(I_S_ResourceType.class, "OnWednesday", null);
    /** Column name OnWednesday */
    public static final String COLUMNNAME_OnWednesday = "OnWednesday";

	/**
	 * Set Ressourcenart.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setS_ResourceType_ID (int S_ResourceType_ID);

	/**
	 * Get Ressourcenart.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getS_ResourceType_ID();

    /** Column definition for S_ResourceType_ID */
    public static final org.adempiere.model.ModelColumn<I_S_ResourceType, Object> COLUMN_S_ResourceType_ID = new org.adempiere.model.ModelColumn<I_S_ResourceType, Object>(I_S_ResourceType.class, "S_ResourceType_ID", null);
    /** Column name S_ResourceType_ID */
    public static final String COLUMNNAME_S_ResourceType_ID = "S_ResourceType_ID";

	/**
	 * Set Endzeitpunkt.
	 * Time when timeslot ends
	 *
	 * <br>Type: Time
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setTimeSlotEnd (java.sql.Timestamp TimeSlotEnd);

	/**
	 * Get Endzeitpunkt.
	 * Time when timeslot ends
	 *
	 * <br>Type: Time
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getTimeSlotEnd();

    /** Column definition for TimeSlotEnd */
    public static final org.adempiere.model.ModelColumn<I_S_ResourceType, Object> COLUMN_TimeSlotEnd = new org.adempiere.model.ModelColumn<I_S_ResourceType, Object>(I_S_ResourceType.class, "TimeSlotEnd", null);
    /** Column name TimeSlotEnd */
    public static final String COLUMNNAME_TimeSlotEnd = "TimeSlotEnd";

	/**
	 * Set Startzeitpunkt.
	 * Time when timeslot starts
	 *
	 * <br>Type: Time
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setTimeSlotStart (java.sql.Timestamp TimeSlotStart);

	/**
	 * Get Startzeitpunkt.
	 * Time when timeslot starts
	 *
	 * <br>Type: Time
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getTimeSlotStart();

    /** Column definition for TimeSlotStart */
    public static final org.adempiere.model.ModelColumn<I_S_ResourceType, Object> COLUMN_TimeSlotStart = new org.adempiere.model.ModelColumn<I_S_ResourceType, Object>(I_S_ResourceType.class, "TimeSlotStart", null);
    /** Column name TimeSlotStart */
    public static final String COLUMNNAME_TimeSlotStart = "TimeSlotStart";

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
    public static final org.adempiere.model.ModelColumn<I_S_ResourceType, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_S_ResourceType, Object>(I_S_ResourceType.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_S_ResourceType, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_S_ResourceType, org.compiere.model.I_AD_User>(I_S_ResourceType.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Suchschlüssel.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setValue (java.lang.String Value);

	/**
	 * Get Suchschlüssel.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getValue();

    /** Column definition for Value */
    public static final org.adempiere.model.ModelColumn<I_S_ResourceType, Object> COLUMN_Value = new org.adempiere.model.ModelColumn<I_S_ResourceType, Object>(I_S_ResourceType.class, "Value", null);
    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";
}
