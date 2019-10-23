package org.compiere.model;


/** Generated Interface for C_Phonecall_Schedule
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Phonecall_Schedule 
{

    /** TableName=C_Phonecall_Schedule */
    public static final String Table_Name = "C_Phonecall_Schedule";

    /** AD_Table_ID=541176 */
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
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Schedule, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_Phonecall_Schedule, org.compiere.model.I_AD_Client>(I_C_Phonecall_Schedule.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Schedule, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_Phonecall_Schedule, org.compiere.model.I_AD_Org>(I_C_Phonecall_Schedule.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Contact.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BP_Contact_ID (int C_BP_Contact_ID);

	/**
	 * Get Contact.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BP_Contact_ID();

	public org.compiere.model.I_AD_User getC_BP_Contact();

	public void setC_BP_Contact(org.compiere.model.I_AD_User C_BP_Contact);

    /** Column definition for C_BP_Contact_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Schedule, org.compiere.model.I_AD_User> COLUMN_C_BP_Contact_ID = new org.adempiere.model.ModelColumn<I_C_Phonecall_Schedule, org.compiere.model.I_AD_User>(I_C_Phonecall_Schedule.class, "C_BP_Contact_ID", org.compiere.model.I_AD_User.class);
    /** Column name C_BP_Contact_ID */
    public static final String COLUMNNAME_C_BP_Contact_ID = "C_BP_Contact_ID";

	/**
	 * Set Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner();

	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner);

    /** Column definition for C_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Schedule, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_C_Phonecall_Schedule, org.compiere.model.I_C_BPartner>(I_C_Phonecall_Schedule.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Standort.
	 * Identifiziert die (Liefer-) Adresse des Geschäftspartners
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Standort.
	 * Identifiziert die (Liefer-) Adresse des Geschäftspartners
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_Location_ID();

	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location();

	public void setC_BPartner_Location(org.compiere.model.I_C_BPartner_Location C_BPartner_Location);

    /** Column definition for C_BPartner_Location_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Schedule, org.compiere.model.I_C_BPartner_Location> COLUMN_C_BPartner_Location_ID = new org.adempiere.model.ModelColumn<I_C_Phonecall_Schedule, org.compiere.model.I_C_BPartner_Location>(I_C_Phonecall_Schedule.class, "C_BPartner_Location_ID", org.compiere.model.I_C_BPartner_Location.class);
    /** Column name C_BPartner_Location_ID */
    public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/**
	 * Set Anrufliste.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Phonecall_Schedule_ID (int C_Phonecall_Schedule_ID);

	/**
	 * Get Anrufliste.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Phonecall_Schedule_ID();

    /** Column definition for C_Phonecall_Schedule_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Schedule, Object> COLUMN_C_Phonecall_Schedule_ID = new org.adempiere.model.ModelColumn<I_C_Phonecall_Schedule, Object>(I_C_Phonecall_Schedule.class, "C_Phonecall_Schedule_ID", null);
    /** Column name C_Phonecall_Schedule_ID */
    public static final String COLUMNNAME_C_Phonecall_Schedule_ID = "C_Phonecall_Schedule_ID";

	/**
	 * Set Anruf Planung.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Phonecall_Schema_ID (int C_Phonecall_Schema_ID);

	/**
	 * Get Anruf Planung.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Phonecall_Schema_ID();

	public org.compiere.model.I_C_Phonecall_Schema getC_Phonecall_Schema();

	public void setC_Phonecall_Schema(org.compiere.model.I_C_Phonecall_Schema C_Phonecall_Schema);

    /** Column definition for C_Phonecall_Schema_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Schedule, org.compiere.model.I_C_Phonecall_Schema> COLUMN_C_Phonecall_Schema_ID = new org.adempiere.model.ModelColumn<I_C_Phonecall_Schedule, org.compiere.model.I_C_Phonecall_Schema>(I_C_Phonecall_Schedule.class, "C_Phonecall_Schema_ID", org.compiere.model.I_C_Phonecall_Schema.class);
    /** Column name C_Phonecall_Schema_ID */
    public static final String COLUMNNAME_C_Phonecall_Schema_ID = "C_Phonecall_Schema_ID";

	/**
	 * Set Anruf Planung Version.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Phonecall_Schema_Version_ID (int C_Phonecall_Schema_Version_ID);

	/**
	 * Get Anruf Planung Version.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Phonecall_Schema_Version_ID();

	public org.compiere.model.I_C_Phonecall_Schema_Version getC_Phonecall_Schema_Version();

	public void setC_Phonecall_Schema_Version(org.compiere.model.I_C_Phonecall_Schema_Version C_Phonecall_Schema_Version);

    /** Column definition for C_Phonecall_Schema_Version_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Schedule, org.compiere.model.I_C_Phonecall_Schema_Version> COLUMN_C_Phonecall_Schema_Version_ID = new org.adempiere.model.ModelColumn<I_C_Phonecall_Schedule, org.compiere.model.I_C_Phonecall_Schema_Version>(I_C_Phonecall_Schedule.class, "C_Phonecall_Schema_Version_ID", org.compiere.model.I_C_Phonecall_Schema_Version.class);
    /** Column name C_Phonecall_Schema_Version_ID */
    public static final String COLUMNNAME_C_Phonecall_Schema_Version_ID = "C_Phonecall_Schema_Version_ID";

	/**
	 * Set Anruf Planung Version Position.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Phonecall_Schema_Version_Line_ID (int C_Phonecall_Schema_Version_Line_ID);

	/**
	 * Get Anruf Planung Version Position.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Phonecall_Schema_Version_Line_ID();

	public org.compiere.model.I_C_Phonecall_Schema_Version_Line getC_Phonecall_Schema_Version_Line();

	public void setC_Phonecall_Schema_Version_Line(org.compiere.model.I_C_Phonecall_Schema_Version_Line C_Phonecall_Schema_Version_Line);

    /** Column definition for C_Phonecall_Schema_Version_Line_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Schedule, org.compiere.model.I_C_Phonecall_Schema_Version_Line> COLUMN_C_Phonecall_Schema_Version_Line_ID = new org.adempiere.model.ModelColumn<I_C_Phonecall_Schedule, org.compiere.model.I_C_Phonecall_Schema_Version_Line>(I_C_Phonecall_Schedule.class, "C_Phonecall_Schema_Version_Line_ID", org.compiere.model.I_C_Phonecall_Schema_Version_Line.class);
    /** Column name C_Phonecall_Schema_Version_Line_ID */
    public static final String COLUMNNAME_C_Phonecall_Schema_Version_Line_ID = "C_Phonecall_Schema_Version_Line_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Schedule, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_Phonecall_Schedule, Object>(I_C_Phonecall_Schedule.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Schedule, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_Phonecall_Schedule, org.compiere.model.I_AD_User>(I_C_Phonecall_Schedule.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Schedule, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_Phonecall_Schedule, Object>(I_C_Phonecall_Schedule.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Anruf getätigt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsCalled (boolean IsCalled);

	/**
	 * Get Anruf getätigt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isCalled();

    /** Column definition for IsCalled */
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Schedule, Object> COLUMN_IsCalled = new org.adempiere.model.ModelColumn<I_C_Phonecall_Schedule, Object>(I_C_Phonecall_Schedule.class, "IsCalled", null);
    /** Column name IsCalled */
    public static final String COLUMNNAME_IsCalled = "IsCalled";

	/**
	 * Set Auftrag erteilt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsOrdered (boolean IsOrdered);

	/**
	 * Get Auftrag erteilt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isOrdered();

    /** Column definition for IsOrdered */
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Schedule, Object> COLUMN_IsOrdered = new org.adempiere.model.ModelColumn<I_C_Phonecall_Schedule, Object>(I_C_Phonecall_Schedule.class, "IsOrdered", null);
    /** Column name IsOrdered */
    public static final String COLUMNNAME_IsOrdered = "IsOrdered";

	/**
	 * Set Telefon.
	 * Beschreibt eine Telefon Nummer
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setPhone (java.lang.String Phone);

	/**
	 * Get Telefon.
	 * Beschreibt eine Telefon Nummer
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public java.lang.String getPhone();

    /** Column definition for Phone */
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Schedule, Object> COLUMN_Phone = new org.adempiere.model.ModelColumn<I_C_Phonecall_Schedule, Object>(I_C_Phonecall_Schedule.class, "Phone", null);
    /** Column name Phone */
    public static final String COLUMNNAME_Phone = "Phone";

	/**
	 * Set Anrufdatum.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPhonecallDate (java.sql.Timestamp PhonecallDate);

	/**
	 * Get Anrufdatum.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getPhonecallDate();

    /** Column definition for PhonecallDate */
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Schedule, Object> COLUMN_PhonecallDate = new org.adempiere.model.ModelColumn<I_C_Phonecall_Schedule, Object>(I_C_Phonecall_Schedule.class, "PhonecallDate", null);
    /** Column name PhonecallDate */
    public static final String COLUMNNAME_PhonecallDate = "PhonecallDate";

	/**
	 * Set PhonecallTimeMax.
	 *
	 * <br>Type: Time
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPhonecallTimeMax (java.sql.Timestamp PhonecallTimeMax);

	/**
	 * Get PhonecallTimeMax.
	 *
	 * <br>Type: Time
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getPhonecallTimeMax();

    /** Column definition for PhonecallTimeMax */
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Schedule, Object> COLUMN_PhonecallTimeMax = new org.adempiere.model.ModelColumn<I_C_Phonecall_Schedule, Object>(I_C_Phonecall_Schedule.class, "PhonecallTimeMax", null);
    /** Column name PhonecallTimeMax */
    public static final String COLUMNNAME_PhonecallTimeMax = "PhonecallTimeMax";

	/**
	 * Set PhonecallTimeMin.
	 *
	 * <br>Type: Time
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPhonecallTimeMin (java.sql.Timestamp PhonecallTimeMin);

	/**
	 * Get PhonecallTimeMin.
	 *
	 * <br>Type: Time
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getPhonecallTimeMin();

    /** Column definition for PhonecallTimeMin */
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Schedule, Object> COLUMN_PhonecallTimeMin = new org.adempiere.model.ModelColumn<I_C_Phonecall_Schedule, Object>(I_C_Phonecall_Schedule.class, "PhonecallTimeMin", null);
    /** Column name PhonecallTimeMin */
    public static final String COLUMNNAME_PhonecallTimeMin = "PhonecallTimeMin";

	/**
	 * Set Aussendienst.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSalesRep_ID (int SalesRep_ID);

	/**
	 * Get Aussendienst.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getSalesRep_ID();

	public org.compiere.model.I_AD_User getSalesRep();

	public void setSalesRep(org.compiere.model.I_AD_User SalesRep);

    /** Column definition for SalesRep_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Schedule, org.compiere.model.I_AD_User> COLUMN_SalesRep_ID = new org.adempiere.model.ModelColumn<I_C_Phonecall_Schedule, org.compiere.model.I_AD_User>(I_C_Phonecall_Schedule.class, "SalesRep_ID", org.compiere.model.I_AD_User.class);
    /** Column name SalesRep_ID */
    public static final String COLUMNNAME_SalesRep_ID = "SalesRep_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Schedule, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_Phonecall_Schedule, Object>(I_C_Phonecall_Schedule.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Schedule, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_Phonecall_Schedule, org.compiere.model.I_AD_User>(I_C_Phonecall_Schedule.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
