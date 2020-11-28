package de.metas.serviceprovider.model;


/** Generated Interface for S_TimeBooking
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_S_TimeBooking 
{

    /** TableName=S_TimeBooking */
    public static final String Table_Name = "S_TimeBooking";

    /** AD_Table_ID=541443 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(1);

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

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

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

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Erbringende Person.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_User_Performing_ID (int AD_User_Performing_ID);

	/**
	 * Get Erbringende Person.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_User_Performing_ID();

    /** Column name AD_User_Performing_ID */
    public static final String COLUMNNAME_AD_User_Performing_ID = "AD_User_Performing_ID";

	/**
	 * Set Booked date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setBookedDate (java.sql.Timestamp BookedDate);

	/**
	 * Get Booked date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getBookedDate();

    /** Column definition for BookedDate */
    public static final org.adempiere.model.ModelColumn<I_S_TimeBooking, Object> COLUMN_BookedDate = new org.adempiere.model.ModelColumn<I_S_TimeBooking, Object>(I_S_TimeBooking.class, "BookedDate", null);
    /** Column name BookedDate */
    public static final String COLUMNNAME_BookedDate = "BookedDate";

	/**
	 * Set Booked seconds.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBookedSeconds (java.math.BigDecimal BookedSeconds);

	/**
	 * Get Booked seconds.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getBookedSeconds();

    /** Column definition for BookedSeconds */
    public static final org.adempiere.model.ModelColumn<I_S_TimeBooking, Object> COLUMN_BookedSeconds = new org.adempiere.model.ModelColumn<I_S_TimeBooking, Object>(I_S_TimeBooking.class, "BookedSeconds", null);
    /** Column name BookedSeconds */
    public static final String COLUMNNAME_BookedSeconds = "BookedSeconds";

	/**
	 * Set Bemerkungen.
	 * Kommentar oder zusätzliche Information
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setComments (java.lang.String Comments);

	/**
	 * Get Bemerkungen.
	 * Kommentar oder zusätzliche Information
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getComments();

    /** Column definition for Comments */
    public static final org.adempiere.model.ModelColumn<I_S_TimeBooking, Object> COLUMN_Comments = new org.adempiere.model.ModelColumn<I_S_TimeBooking, Object>(I_S_TimeBooking.class, "Comments", null);
    /** Column name Comments */
    public static final String COLUMNNAME_Comments = "Comments";

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
    public static final org.adempiere.model.ModelColumn<I_S_TimeBooking, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_S_TimeBooking, Object>(I_S_TimeBooking.class, "Created", null);
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

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Time (H:mm).
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setHoursAndMinutes (java.lang.String HoursAndMinutes);

	/**
	 * Get Time (H:mm).
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getHoursAndMinutes();

    /** Column definition for HoursAndMinutes */
    public static final org.adempiere.model.ModelColumn<I_S_TimeBooking, Object> COLUMN_HoursAndMinutes = new org.adempiere.model.ModelColumn<I_S_TimeBooking, Object>(I_S_TimeBooking.class, "HoursAndMinutes", null);
    /** Column name HoursAndMinutes */
    public static final String COLUMNNAME_HoursAndMinutes = "HoursAndMinutes";

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
    public static final org.adempiere.model.ModelColumn<I_S_TimeBooking, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_S_TimeBooking, Object>(I_S_TimeBooking.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Issue.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setS_Issue_ID (int S_Issue_ID);

	/**
	 * Get Issue.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getS_Issue_ID();

	public de.metas.serviceprovider.model.I_S_Issue getS_Issue();

	public void setS_Issue(de.metas.serviceprovider.model.I_S_Issue S_Issue);

    /** Column definition for S_Issue_ID */
    public static final org.adempiere.model.ModelColumn<I_S_TimeBooking, de.metas.serviceprovider.model.I_S_Issue> COLUMN_S_Issue_ID = new org.adempiere.model.ModelColumn<I_S_TimeBooking, de.metas.serviceprovider.model.I_S_Issue>(I_S_TimeBooking.class, "S_Issue_ID", de.metas.serviceprovider.model.I_S_Issue.class);
    /** Column name S_Issue_ID */
    public static final String COLUMNNAME_S_Issue_ID = "S_Issue_ID";

	/**
	 * Set S_TimeBooking.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setS_TimeBooking_ID (int S_TimeBooking_ID);

	/**
	 * Get S_TimeBooking.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getS_TimeBooking_ID();

    /** Column definition for S_TimeBooking_ID */
    public static final org.adempiere.model.ModelColumn<I_S_TimeBooking, Object> COLUMN_S_TimeBooking_ID = new org.adempiere.model.ModelColumn<I_S_TimeBooking, Object>(I_S_TimeBooking.class, "S_TimeBooking_ID", null);
    /** Column name S_TimeBooking_ID */
    public static final String COLUMNNAME_S_TimeBooking_ID = "S_TimeBooking_ID";

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
    public static final org.adempiere.model.ModelColumn<I_S_TimeBooking, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_S_TimeBooking, Object>(I_S_TimeBooking.class, "Updated", null);
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

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
