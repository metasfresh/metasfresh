package de.metas.rfq.model;


/** Generated Interface for C_RfQ_TopicSubscriber
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_RfQ_TopicSubscriber 
{

    /** TableName=C_RfQ_TopicSubscriber */
    public static final String Table_Name = "C_RfQ_TopicSubscriber";

    /** AD_Table_ID=670 */
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
    public static final org.adempiere.model.ModelColumn<I_C_RfQ_TopicSubscriber, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_RfQ_TopicSubscriber, org.compiere.model.I_AD_Client>(I_C_RfQ_TopicSubscriber.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_RfQ_TopicSubscriber, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_RfQ_TopicSubscriber, org.compiere.model.I_AD_Org>(I_C_RfQ_TopicSubscriber.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Ansprechpartner.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_User_ID (int AD_User_ID);

	/**
	 * Get Ansprechpartner.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_User_ID();

	public org.compiere.model.I_AD_User getAD_User();

	public void setAD_User(org.compiere.model.I_AD_User AD_User);

    /** Column definition for AD_User_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQ_TopicSubscriber, org.compiere.model.I_AD_User> COLUMN_AD_User_ID = new org.adempiere.model.ModelColumn<I_C_RfQ_TopicSubscriber, org.compiere.model.I_AD_User>(I_C_RfQ_TopicSubscriber.class, "AD_User_ID", org.compiere.model.I_AD_User.class);
    /** Column name AD_User_ID */
    public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/**
	 * Set Geschäftspartner.
	 * Identifies a Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Identifies a Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner();

	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner);

    /** Column definition for C_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQ_TopicSubscriber, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_C_RfQ_TopicSubscriber, org.compiere.model.I_C_BPartner>(I_C_RfQ_TopicSubscriber.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Standort.
	 * Identifies the (ship to) address for this Business Partner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Standort.
	 * Identifies the (ship to) address for this Business Partner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_Location_ID();

	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location();

	public void setC_BPartner_Location(org.compiere.model.I_C_BPartner_Location C_BPartner_Location);

    /** Column definition for C_BPartner_Location_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQ_TopicSubscriber, org.compiere.model.I_C_BPartner_Location> COLUMN_C_BPartner_Location_ID = new org.adempiere.model.ModelColumn<I_C_RfQ_TopicSubscriber, org.compiere.model.I_C_BPartner_Location>(I_C_RfQ_TopicSubscriber.class, "C_BPartner_Location_ID", org.compiere.model.I_C_BPartner_Location.class);
    /** Column name C_BPartner_Location_ID */
    public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/**
	 * Set Ausschreibungs-Thema.
	 * Topic for Request for Quotations
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_RfQ_Topic_ID (int C_RfQ_Topic_ID);

	/**
	 * Get Ausschreibungs-Thema.
	 * Topic for Request for Quotations
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_RfQ_Topic_ID();

	public de.metas.rfq.model.I_C_RfQ_Topic getC_RfQ_Topic();

	public void setC_RfQ_Topic(de.metas.rfq.model.I_C_RfQ_Topic C_RfQ_Topic);

    /** Column definition for C_RfQ_Topic_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQ_TopicSubscriber, de.metas.rfq.model.I_C_RfQ_Topic> COLUMN_C_RfQ_Topic_ID = new org.adempiere.model.ModelColumn<I_C_RfQ_TopicSubscriber, de.metas.rfq.model.I_C_RfQ_Topic>(I_C_RfQ_TopicSubscriber.class, "C_RfQ_Topic_ID", de.metas.rfq.model.I_C_RfQ_Topic.class);
    /** Column name C_RfQ_Topic_ID */
    public static final String COLUMNNAME_C_RfQ_Topic_ID = "C_RfQ_Topic_ID";

	/**
	 * Set RfQ Subscriber.
	 * Request for Quotation Topic Subscriber
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_RfQ_TopicSubscriber_ID (int C_RfQ_TopicSubscriber_ID);

	/**
	 * Get RfQ Subscriber.
	 * Request for Quotation Topic Subscriber
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_RfQ_TopicSubscriber_ID();

    /** Column definition for C_RfQ_TopicSubscriber_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQ_TopicSubscriber, Object> COLUMN_C_RfQ_TopicSubscriber_ID = new org.adempiere.model.ModelColumn<I_C_RfQ_TopicSubscriber, Object>(I_C_RfQ_TopicSubscriber.class, "C_RfQ_TopicSubscriber_ID", null);
    /** Column name C_RfQ_TopicSubscriber_ID */
    public static final String COLUMNNAME_C_RfQ_TopicSubscriber_ID = "C_RfQ_TopicSubscriber_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_RfQ_TopicSubscriber, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_RfQ_TopicSubscriber, Object>(I_C_RfQ_TopicSubscriber.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_RfQ_TopicSubscriber, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_RfQ_TopicSubscriber, org.compiere.model.I_AD_User>(I_C_RfQ_TopicSubscriber.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

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
    public static final org.adempiere.model.ModelColumn<I_C_RfQ_TopicSubscriber, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_RfQ_TopicSubscriber, Object>(I_C_RfQ_TopicSubscriber.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Datum der Abmeldung.
	 * Date the contact opted out
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setOptOutDate (java.sql.Timestamp OptOutDate);

	/**
	 * Get Datum der Abmeldung.
	 * Date the contact opted out
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getOptOutDate();

    /** Column definition for OptOutDate */
    public static final org.adempiere.model.ModelColumn<I_C_RfQ_TopicSubscriber, Object> COLUMN_OptOutDate = new org.adempiere.model.ModelColumn<I_C_RfQ_TopicSubscriber, Object>(I_C_RfQ_TopicSubscriber.class, "OptOutDate", null);
    /** Column name OptOutDate */
    public static final String COLUMNNAME_OptOutDate = "OptOutDate";

	/**
	 * Set Anmeldung.
	 * Date the contact actively subscribed
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSubscribeDate (java.sql.Timestamp SubscribeDate);

	/**
	 * Get Anmeldung.
	 * Date the contact actively subscribed
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getSubscribeDate();

    /** Column definition for SubscribeDate */
    public static final org.adempiere.model.ModelColumn<I_C_RfQ_TopicSubscriber, Object> COLUMN_SubscribeDate = new org.adempiere.model.ModelColumn<I_C_RfQ_TopicSubscriber, Object>(I_C_RfQ_TopicSubscriber.class, "SubscribeDate", null);
    /** Column name SubscribeDate */
    public static final String COLUMNNAME_SubscribeDate = "SubscribeDate";

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
    public static final org.adempiere.model.ModelColumn<I_C_RfQ_TopicSubscriber, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_RfQ_TopicSubscriber, Object>(I_C_RfQ_TopicSubscriber.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_RfQ_TopicSubscriber, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_RfQ_TopicSubscriber, org.compiere.model.I_AD_User>(I_C_RfQ_TopicSubscriber.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
