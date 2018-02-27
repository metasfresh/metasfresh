package de.metas.tourplanning.model;


/** Generated Interface for M_DeliveryDay
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_DeliveryDay 
{

    /** TableName=M_DeliveryDay */
    public static final String Table_Name = "M_DeliveryDay";

    /** AD_Table_ID=540297 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

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
    public static final org.adempiere.model.ModelColumn<I_M_DeliveryDay, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_DeliveryDay, org.compiere.model.I_AD_Client>(I_M_DeliveryDay.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_DeliveryDay, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_DeliveryDay, org.compiere.model.I_AD_Org>(I_M_DeliveryDay.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Puffer (Std).
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBufferHours (int BufferHours);

	/**
	 * Get Puffer (Std).
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getBufferHours();

    /** Column definition for BufferHours */
    public static final org.adempiere.model.ModelColumn<I_M_DeliveryDay, Object> COLUMN_BufferHours = new org.adempiere.model.ModelColumn<I_M_DeliveryDay, Object>(I_M_DeliveryDay.class, "BufferHours", null);
    /** Column name BufferHours */
    public static final String COLUMNNAME_BufferHours = "BufferHours";

	/**
	 * Set Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner();

	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner);

    /** Column definition for C_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_M_DeliveryDay, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_M_DeliveryDay, org.compiere.model.I_C_BPartner>(I_M_DeliveryDay.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Standort.
	 * Identifiziert die (Liefer-) Adresse des Geschäftspartners
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Standort.
	 * Identifiziert die (Liefer-) Adresse des Geschäftspartners
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_Location_ID();

	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location();

	public void setC_BPartner_Location(org.compiere.model.I_C_BPartner_Location C_BPartner_Location);

    /** Column definition for C_BPartner_Location_ID */
    public static final org.adempiere.model.ModelColumn<I_M_DeliveryDay, org.compiere.model.I_C_BPartner_Location> COLUMN_C_BPartner_Location_ID = new org.adempiere.model.ModelColumn<I_M_DeliveryDay, org.compiere.model.I_C_BPartner_Location>(I_M_DeliveryDay.class, "C_BPartner_Location_ID", org.compiere.model.I_C_BPartner_Location.class);
    /** Column name C_BPartner_Location_ID */
    public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_DeliveryDay, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_DeliveryDay, Object>(I_M_DeliveryDay.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_DeliveryDay, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_DeliveryDay, org.compiere.model.I_AD_User>(I_M_DeliveryDay.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Lieferdatum.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDeliveryDate (java.sql.Timestamp DeliveryDate);

	/**
	 * Get Lieferdatum.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDeliveryDate();

    /** Column definition for DeliveryDate */
    public static final org.adempiere.model.ModelColumn<I_M_DeliveryDay, Object> COLUMN_DeliveryDate = new org.adempiere.model.ModelColumn<I_M_DeliveryDay, Object>(I_M_DeliveryDay.class, "DeliveryDate", null);
    /** Column name DeliveryDate */
    public static final String COLUMNNAME_DeliveryDate = "DeliveryDate";

	/**
	 * Set Lieferzeit (inkl. Puffer).
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDeliveryDateTimeMax (java.sql.Timestamp DeliveryDateTimeMax);

	/**
	 * Get Lieferzeit (inkl. Puffer).
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDeliveryDateTimeMax();

    /** Column definition for DeliveryDateTimeMax */
    public static final org.adempiere.model.ModelColumn<I_M_DeliveryDay, Object> COLUMN_DeliveryDateTimeMax = new org.adempiere.model.ModelColumn<I_M_DeliveryDay, Object>(I_M_DeliveryDay.class, "DeliveryDateTimeMax", null);
    /** Column name DeliveryDateTimeMax */
    public static final String COLUMNNAME_DeliveryDateTimeMax = "DeliveryDateTimeMax";

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
    public static final org.adempiere.model.ModelColumn<I_M_DeliveryDay, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_DeliveryDay, Object>(I_M_DeliveryDay.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Manuell.
	 * Dies ist ein manueller Vorgang
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsManual (boolean IsManual);

	/**
	 * Get Manuell.
	 * Dies ist ein manueller Vorgang
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isManual();

    /** Column definition for IsManual */
    public static final org.adempiere.model.ModelColumn<I_M_DeliveryDay, Object> COLUMN_IsManual = new org.adempiere.model.ModelColumn<I_M_DeliveryDay, Object>(I_M_DeliveryDay.class, "IsManual", null);
    /** Column name IsManual */
    public static final String COLUMNNAME_IsManual = "IsManual";

	/**
	 * Set Abholung.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsToBeFetched (boolean IsToBeFetched);

	/**
	 * Get Abholung.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isToBeFetched();

    /** Column definition for IsToBeFetched */
    public static final org.adempiere.model.ModelColumn<I_M_DeliveryDay, Object> COLUMN_IsToBeFetched = new org.adempiere.model.ModelColumn<I_M_DeliveryDay, Object>(I_M_DeliveryDay.class, "IsToBeFetched", null);
    /** Column name IsToBeFetched */
    public static final String COLUMNNAME_IsToBeFetched = "IsToBeFetched";

	/**
	 * Set Delivery Days.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_DeliveryDay_ID (int M_DeliveryDay_ID);

	/**
	 * Get Delivery Days.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_DeliveryDay_ID();

    /** Column definition for M_DeliveryDay_ID */
    public static final org.adempiere.model.ModelColumn<I_M_DeliveryDay, Object> COLUMN_M_DeliveryDay_ID = new org.adempiere.model.ModelColumn<I_M_DeliveryDay, Object>(I_M_DeliveryDay.class, "M_DeliveryDay_ID", null);
    /** Column name M_DeliveryDay_ID */
    public static final String COLUMNNAME_M_DeliveryDay_ID = "M_DeliveryDay_ID";

	/**
	 * Set Tour.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Tour_ID (int M_Tour_ID);

	/**
	 * Get Tour.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Tour_ID();

	public de.metas.tourplanning.model.I_M_Tour getM_Tour();

	public void setM_Tour(de.metas.tourplanning.model.I_M_Tour M_Tour);

    /** Column definition for M_Tour_ID */
    public static final org.adempiere.model.ModelColumn<I_M_DeliveryDay, de.metas.tourplanning.model.I_M_Tour> COLUMN_M_Tour_ID = new org.adempiere.model.ModelColumn<I_M_DeliveryDay, de.metas.tourplanning.model.I_M_Tour>(I_M_DeliveryDay.class, "M_Tour_ID", de.metas.tourplanning.model.I_M_Tour.class);
    /** Column name M_Tour_ID */
    public static final String COLUMNNAME_M_Tour_ID = "M_Tour_ID";

	/**
	 * Set Tour Instance.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Tour_Instance_ID (int M_Tour_Instance_ID);

	/**
	 * Get Tour Instance.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Tour_Instance_ID();

	public de.metas.tourplanning.model.I_M_Tour_Instance getM_Tour_Instance();

	public void setM_Tour_Instance(de.metas.tourplanning.model.I_M_Tour_Instance M_Tour_Instance);

    /** Column definition for M_Tour_Instance_ID */
    public static final org.adempiere.model.ModelColumn<I_M_DeliveryDay, de.metas.tourplanning.model.I_M_Tour_Instance> COLUMN_M_Tour_Instance_ID = new org.adempiere.model.ModelColumn<I_M_DeliveryDay, de.metas.tourplanning.model.I_M_Tour_Instance>(I_M_DeliveryDay.class, "M_Tour_Instance_ID", de.metas.tourplanning.model.I_M_Tour_Instance.class);
    /** Column name M_Tour_Instance_ID */
    public static final String COLUMNNAME_M_Tour_Instance_ID = "M_Tour_Instance_ID";

	/**
	 * Set Tour Version.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_TourVersion_ID (int M_TourVersion_ID);

	/**
	 * Get Tour Version.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_TourVersion_ID();

	public de.metas.tourplanning.model.I_M_TourVersion getM_TourVersion();

	public void setM_TourVersion(de.metas.tourplanning.model.I_M_TourVersion M_TourVersion);

    /** Column definition for M_TourVersion_ID */
    public static final org.adempiere.model.ModelColumn<I_M_DeliveryDay, de.metas.tourplanning.model.I_M_TourVersion> COLUMN_M_TourVersion_ID = new org.adempiere.model.ModelColumn<I_M_DeliveryDay, de.metas.tourplanning.model.I_M_TourVersion>(I_M_DeliveryDay.class, "M_TourVersion_ID", de.metas.tourplanning.model.I_M_TourVersion.class);
    /** Column name M_TourVersion_ID */
    public static final String COLUMNNAME_M_TourVersion_ID = "M_TourVersion_ID";

	/**
	 * Set Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setProcessed (boolean Processed);

	/**
	 * Get Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isProcessed();

    /** Column definition for Processed */
    public static final org.adempiere.model.ModelColumn<I_M_DeliveryDay, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_M_DeliveryDay, Object>(I_M_DeliveryDay.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Reihenfolge.
	 * Zur Bestimmung der Reihenfolge der Einträge;
 die kleinste Zahl kommt zuerst
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSeqNo (int SeqNo);

	/**
	 * Get Reihenfolge.
	 * Zur Bestimmung der Reihenfolge der Einträge;
 die kleinste Zahl kommt zuerst
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getSeqNo();

    /** Column definition for SeqNo */
    public static final org.adempiere.model.ModelColumn<I_M_DeliveryDay, Object> COLUMN_SeqNo = new org.adempiere.model.ModelColumn<I_M_DeliveryDay, Object>(I_M_DeliveryDay.class, "SeqNo", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_DeliveryDay, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_DeliveryDay, Object>(I_M_DeliveryDay.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_DeliveryDay, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_DeliveryDay, org.compiere.model.I_AD_User>(I_M_DeliveryDay.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
