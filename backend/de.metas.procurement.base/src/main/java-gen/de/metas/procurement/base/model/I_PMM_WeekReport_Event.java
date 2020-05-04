package de.metas.procurement.base.model;


/** Generated Interface for PMM_WeekReport_Event
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_PMM_WeekReport_Event 
{

    /** TableName=PMM_WeekReport_Event */
    public static final String Table_Name = "PMM_WeekReport_Event";

    /** AD_Table_ID=540752 */
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
    public static final org.adempiere.model.ModelColumn<I_PMM_WeekReport_Event, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_PMM_WeekReport_Event, org.compiere.model.I_AD_Client>(I_PMM_WeekReport_Event.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set System-Problem.
	 * Automatically created or manually entered System Issue
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Issue_ID (int AD_Issue_ID);

	/**
	 * Get System-Problem.
	 * Automatically created or manually entered System Issue
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Issue_ID();

	public org.compiere.model.I_AD_Issue getAD_Issue();

	public void setAD_Issue(org.compiere.model.I_AD_Issue AD_Issue);

    /** Column definition for AD_Issue_ID */
    public static final org.adempiere.model.ModelColumn<I_PMM_WeekReport_Event, org.compiere.model.I_AD_Issue> COLUMN_AD_Issue_ID = new org.adempiere.model.ModelColumn<I_PMM_WeekReport_Event, org.compiere.model.I_AD_Issue>(I_PMM_WeekReport_Event.class, "AD_Issue_ID", org.compiere.model.I_AD_Issue.class);
    /** Column name AD_Issue_ID */
    public static final String COLUMNNAME_AD_Issue_ID = "AD_Issue_ID";

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
    public static final org.adempiere.model.ModelColumn<I_PMM_WeekReport_Event, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_PMM_WeekReport_Event, org.compiere.model.I_AD_Org>(I_PMM_WeekReport_Event.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

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
    public static final org.adempiere.model.ModelColumn<I_PMM_WeekReport_Event, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_PMM_WeekReport_Event, org.compiere.model.I_C_BPartner>(I_PMM_WeekReport_Event.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

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
    public static final org.adempiere.model.ModelColumn<I_PMM_WeekReport_Event, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_PMM_WeekReport_Event, Object>(I_PMM_WeekReport_Event.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_PMM_WeekReport_Event, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_PMM_WeekReport_Event, org.compiere.model.I_AD_User>(I_PMM_WeekReport_Event.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Fehlermeldung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setErrorMsg (java.lang.String ErrorMsg);

	/**
	 * Get Fehlermeldung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getErrorMsg();

    /** Column definition for ErrorMsg */
    public static final org.adempiere.model.ModelColumn<I_PMM_WeekReport_Event, Object> COLUMN_ErrorMsg = new org.adempiere.model.ModelColumn<I_PMM_WeekReport_Event, Object>(I_PMM_WeekReport_Event.class, "ErrorMsg", null);
    /** Column name ErrorMsg */
    public static final String COLUMNNAME_ErrorMsg = "ErrorMsg";

	/**
	 * Set Event UUID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEvent_UUID (java.lang.String Event_UUID);

	/**
	 * Get Event UUID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEvent_UUID();

    /** Column definition for Event_UUID */
    public static final org.adempiere.model.ModelColumn<I_PMM_WeekReport_Event, Object> COLUMN_Event_UUID = new org.adempiere.model.ModelColumn<I_PMM_WeekReport_Event, Object>(I_PMM_WeekReport_Event.class, "Event_UUID", null);
    /** Column name Event_UUID */
    public static final String COLUMNNAME_Event_UUID = "Event_UUID";

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
    public static final org.adempiere.model.ModelColumn<I_PMM_WeekReport_Event, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_PMM_WeekReport_Event, Object>(I_PMM_WeekReport_Event.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Merkmale.
	 * Merkmals Ausprägungen zum Produkt
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID);

	/**
	 * Get Merkmale.
	 * Merkmals Ausprägungen zum Produkt
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_AttributeSetInstance_ID();

	public org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance();

	public void setM_AttributeSetInstance(org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance);

    /** Column definition for M_AttributeSetInstance_ID */
    public static final org.adempiere.model.ModelColumn<I_PMM_WeekReport_Event, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new org.adempiere.model.ModelColumn<I_PMM_WeekReport_Event, org.compiere.model.I_M_AttributeSetInstance>(I_PMM_WeekReport_Event.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
    /** Column name M_AttributeSetInstance_ID */
    public static final String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/**
	 * Set Packvorschrift.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_HU_PI_Item_Product_ID (int M_HU_PI_Item_Product_ID);

	/**
	 * Get Packvorschrift.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_HU_PI_Item_Product_ID();

    /** Column definition for M_HU_PI_Item_Product_ID */
    public static final org.adempiere.model.ModelColumn<I_PMM_WeekReport_Event, Object> COLUMN_M_HU_PI_Item_Product_ID = new org.adempiere.model.ModelColumn<I_PMM_WeekReport_Event, Object>(I_PMM_WeekReport_Event.class, "M_HU_PI_Item_Product_ID", null);
    /** Column name M_HU_PI_Item_Product_ID */
    public static final String COLUMNNAME_M_HU_PI_Item_Product_ID = "M_HU_PI_Item_Product_ID";

	/**
	 * Set Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Product_ID();

	public org.compiere.model.I_M_Product getM_Product();

	public void setM_Product(org.compiere.model.I_M_Product M_Product);

    /** Column definition for M_Product_ID */
    public static final org.adempiere.model.ModelColumn<I_PMM_WeekReport_Event, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_PMM_WeekReport_Event, org.compiere.model.I_M_Product>(I_PMM_WeekReport_Event.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Lieferprodukt.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPMM_Product_ID (int PMM_Product_ID);

	/**
	 * Get Lieferprodukt.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getPMM_Product_ID();

	public de.metas.procurement.base.model.I_PMM_Product getPMM_Product();

	public void setPMM_Product(de.metas.procurement.base.model.I_PMM_Product PMM_Product);

    /** Column definition for PMM_Product_ID */
    public static final org.adempiere.model.ModelColumn<I_PMM_WeekReport_Event, de.metas.procurement.base.model.I_PMM_Product> COLUMN_PMM_Product_ID = new org.adempiere.model.ModelColumn<I_PMM_WeekReport_Event, de.metas.procurement.base.model.I_PMM_Product>(I_PMM_WeekReport_Event.class, "PMM_Product_ID", de.metas.procurement.base.model.I_PMM_Product.class);
    /** Column name PMM_Product_ID */
    public static final String COLUMNNAME_PMM_Product_ID = "PMM_Product_ID";

	/**
	 * Set Trend.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPMM_Trend (java.lang.String PMM_Trend);

	/**
	 * Get Trend.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPMM_Trend();

    /** Column definition for PMM_Trend */
    public static final org.adempiere.model.ModelColumn<I_PMM_WeekReport_Event, Object> COLUMN_PMM_Trend = new org.adempiere.model.ModelColumn<I_PMM_WeekReport_Event, Object>(I_PMM_WeekReport_Event.class, "PMM_Trend", null);
    /** Column name PMM_Trend */
    public static final String COLUMNNAME_PMM_Trend = "PMM_Trend";

	/**
	 * Set Procurement Week.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPMM_Week_ID (int PMM_Week_ID);

	/**
	 * Get Procurement Week.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getPMM_Week_ID();

	public de.metas.procurement.base.model.I_PMM_Week getPMM_Week();

	public void setPMM_Week(de.metas.procurement.base.model.I_PMM_Week PMM_Week);

    /** Column definition for PMM_Week_ID */
    public static final org.adempiere.model.ModelColumn<I_PMM_WeekReport_Event, de.metas.procurement.base.model.I_PMM_Week> COLUMN_PMM_Week_ID = new org.adempiere.model.ModelColumn<I_PMM_WeekReport_Event, de.metas.procurement.base.model.I_PMM_Week>(I_PMM_WeekReport_Event.class, "PMM_Week_ID", de.metas.procurement.base.model.I_PMM_Week.class);
    /** Column name PMM_Week_ID */
    public static final String COLUMNNAME_PMM_Week_ID = "PMM_Week_ID";

	/**
	 * Set Week report event.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPMM_WeekReport_Event_ID (int PMM_WeekReport_Event_ID);

	/**
	 * Get Week report event.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getPMM_WeekReport_Event_ID();

    /** Column definition for PMM_WeekReport_Event_ID */
    public static final org.adempiere.model.ModelColumn<I_PMM_WeekReport_Event, Object> COLUMN_PMM_WeekReport_Event_ID = new org.adempiere.model.ModelColumn<I_PMM_WeekReport_Event, Object>(I_PMM_WeekReport_Event.class, "PMM_WeekReport_Event_ID", null);
    /** Column name PMM_WeekReport_Event_ID */
    public static final String COLUMNNAME_PMM_WeekReport_Event_ID = "PMM_WeekReport_Event_ID";

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
    public static final org.adempiere.model.ModelColumn<I_PMM_WeekReport_Event, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_PMM_WeekReport_Event, Object>(I_PMM_WeekReport_Event.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

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
    public static final org.adempiere.model.ModelColumn<I_PMM_WeekReport_Event, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_PMM_WeekReport_Event, Object>(I_PMM_WeekReport_Event.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_PMM_WeekReport_Event, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_PMM_WeekReport_Event, org.compiere.model.I_AD_User>(I_PMM_WeekReport_Event.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Wochenerster.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setWeekDate (java.sql.Timestamp WeekDate);

	/**
	 * Get Wochenerster.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getWeekDate();

    /** Column definition for WeekDate */
    public static final org.adempiere.model.ModelColumn<I_PMM_WeekReport_Event, Object> COLUMN_WeekDate = new org.adempiere.model.ModelColumn<I_PMM_WeekReport_Event, Object>(I_PMM_WeekReport_Event.class, "WeekDate", null);
    /** Column name WeekDate */
    public static final String COLUMNNAME_WeekDate = "WeekDate";
}
