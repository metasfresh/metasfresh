package de.metas.handlingunits.model;


/** Generated Interface for M_HU_Snapshot
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_HU_Snapshot 
{

    /** TableName=M_HU_Snapshot */
    public static final String Table_Name = "M_HU_Snapshot";

    /** AD_Table_ID=540669 */
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Snapshot, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_HU_Snapshot, org.compiere.model.I_AD_Client>(I_M_HU_Snapshot.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Snapshot, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_HU_Snapshot, org.compiere.model.I_AD_Org>(I_M_HU_Snapshot.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Snapshot, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_M_HU_Snapshot, org.compiere.model.I_C_BPartner>(I_M_HU_Snapshot.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Standort.
	 * Identifiziert die (Liefer-) Adresse des Geschäftspartners
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Standort.
	 * Identifiziert die (Liefer-) Adresse des Geschäftspartners
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_Location_ID();

	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location();

	public void setC_BPartner_Location(org.compiere.model.I_C_BPartner_Location C_BPartner_Location);

    /** Column definition for C_BPartner_Location_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Snapshot, org.compiere.model.I_C_BPartner_Location> COLUMN_C_BPartner_Location_ID = new org.adempiere.model.ModelColumn<I_M_HU_Snapshot, org.compiere.model.I_C_BPartner_Location>(I_M_HU_Snapshot.class, "C_BPartner_Location_ID", org.compiere.model.I_C_BPartner_Location.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Snapshot, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_HU_Snapshot, Object>(I_M_HU_Snapshot.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Snapshot, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_HU_Snapshot, org.compiere.model.I_AD_User>(I_M_HU_Snapshot.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set eigene Gebinde.
	 * If true, then the packing material's owner is "us" (the guys who ordered it). If false, then the packing material's owner is the PO's partner.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setHUPlanningReceiptOwnerPM (boolean HUPlanningReceiptOwnerPM);

	/**
	 * Get eigene Gebinde.
	 * If true, then the packing material's owner is "us" (the guys who ordered it). If false, then the packing material's owner is the PO's partner.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isHUPlanningReceiptOwnerPM();

    /** Column definition for HUPlanningReceiptOwnerPM */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Snapshot, Object> COLUMN_HUPlanningReceiptOwnerPM = new org.adempiere.model.ModelColumn<I_M_HU_Snapshot, Object>(I_M_HU_Snapshot.class, "HUPlanningReceiptOwnerPM", null);
    /** Column name HUPlanningReceiptOwnerPM */
    public static final String COLUMNNAME_HUPlanningReceiptOwnerPM = "HUPlanningReceiptOwnerPM";

	/**
	 * Set Gebinde Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setHUStatus (java.lang.String HUStatus);

	/**
	 * Get Gebinde Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getHUStatus();

    /** Column definition for HUStatus */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Snapshot, Object> COLUMN_HUStatus = new org.adempiere.model.ModelColumn<I_M_HU_Snapshot, Object>(I_M_HU_Snapshot.class, "HUStatus", null);
    /** Column name HUStatus */
    public static final String COLUMNNAME_HUStatus = "HUStatus";

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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Snapshot, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_HU_Snapshot, Object>(I_M_HU_Snapshot.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Gesperrt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setLocked (boolean Locked);

	/**
	 * Get Gesperrt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isLocked();

    /** Column definition for Locked */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Snapshot, Object> COLUMN_Locked = new org.adempiere.model.ModelColumn<I_M_HU_Snapshot, Object>(I_M_HU_Snapshot.class, "Locked", null);
    /** Column name Locked */
    public static final String COLUMNNAME_Locked = "Locked";

	/**
	 * Set Handling Units.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_HU_ID (int M_HU_ID);

	/**
	 * Get Handling Units.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_HU_ID();

	public de.metas.handlingunits.model.I_M_HU getM_HU();

	public void setM_HU(de.metas.handlingunits.model.I_M_HU M_HU);

    /** Column definition for M_HU_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Snapshot, de.metas.handlingunits.model.I_M_HU> COLUMN_M_HU_ID = new org.adempiere.model.ModelColumn<I_M_HU_Snapshot, de.metas.handlingunits.model.I_M_HU>(I_M_HU_Snapshot.class, "M_HU_ID", de.metas.handlingunits.model.I_M_HU.class);
    /** Column name M_HU_ID */
    public static final String COLUMNNAME_M_HU_ID = "M_HU_ID";

	/**
	 * Set Handling Units Item Parent ID.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_HU_Item_Parent_ID (int M_HU_Item_Parent_ID);

	/**
	 * Get Handling Units Item Parent ID.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_HU_Item_Parent_ID();

	public de.metas.handlingunits.model.I_M_HU_Item getM_HU_Item_Parent();

	public void setM_HU_Item_Parent(de.metas.handlingunits.model.I_M_HU_Item M_HU_Item_Parent);

    /** Column definition for M_HU_Item_Parent_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Snapshot, de.metas.handlingunits.model.I_M_HU_Item> COLUMN_M_HU_Item_Parent_ID = new org.adempiere.model.ModelColumn<I_M_HU_Snapshot, de.metas.handlingunits.model.I_M_HU_Item>(I_M_HU_Snapshot.class, "M_HU_Item_Parent_ID", de.metas.handlingunits.model.I_M_HU_Item.class);
    /** Column name M_HU_Item_Parent_ID */
    public static final String COLUMNNAME_M_HU_Item_Parent_ID = "M_HU_Item_Parent_ID";

	/**
	 * Set Gebindekonfiguration.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_HU_LUTU_Configuration_ID (int M_HU_LUTU_Configuration_ID);

	/**
	 * Get Gebindekonfiguration.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_HU_LUTU_Configuration_ID();

	public de.metas.handlingunits.model.I_M_HU_LUTU_Configuration getM_HU_LUTU_Configuration();

	public void setM_HU_LUTU_Configuration(de.metas.handlingunits.model.I_M_HU_LUTU_Configuration M_HU_LUTU_Configuration);

    /** Column definition for M_HU_LUTU_Configuration_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Snapshot, de.metas.handlingunits.model.I_M_HU_LUTU_Configuration> COLUMN_M_HU_LUTU_Configuration_ID = new org.adempiere.model.ModelColumn<I_M_HU_Snapshot, de.metas.handlingunits.model.I_M_HU_LUTU_Configuration>(I_M_HU_Snapshot.class, "M_HU_LUTU_Configuration_ID", de.metas.handlingunits.model.I_M_HU_LUTU_Configuration.class);
    /** Column name M_HU_LUTU_Configuration_ID */
    public static final String COLUMNNAME_M_HU_LUTU_Configuration_ID = "M_HU_LUTU_Configuration_ID";

	/**
	 * Set Packvorschrift-Produkt Zuordnung.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_HU_PI_Item_Product_ID (int M_HU_PI_Item_Product_ID);

	/**
	 * Get Packvorschrift-Produkt Zuordnung.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_HU_PI_Item_Product_ID();

	public de.metas.handlingunits.model.I_M_HU_PI_Item_Product getM_HU_PI_Item_Product();

	public void setM_HU_PI_Item_Product(de.metas.handlingunits.model.I_M_HU_PI_Item_Product M_HU_PI_Item_Product);

    /** Column definition for M_HU_PI_Item_Product_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Snapshot, de.metas.handlingunits.model.I_M_HU_PI_Item_Product> COLUMN_M_HU_PI_Item_Product_ID = new org.adempiere.model.ModelColumn<I_M_HU_Snapshot, de.metas.handlingunits.model.I_M_HU_PI_Item_Product>(I_M_HU_Snapshot.class, "M_HU_PI_Item_Product_ID", de.metas.handlingunits.model.I_M_HU_PI_Item_Product.class);
    /** Column name M_HU_PI_Item_Product_ID */
    public static final String COLUMNNAME_M_HU_PI_Item_Product_ID = "M_HU_PI_Item_Product_ID";

	/**
	 * Set Packvorschrift Version.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_HU_PI_Version_ID (int M_HU_PI_Version_ID);

	/**
	 * Get Packvorschrift Version.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_HU_PI_Version_ID();

	public de.metas.handlingunits.model.I_M_HU_PI_Version getM_HU_PI_Version();

	public void setM_HU_PI_Version(de.metas.handlingunits.model.I_M_HU_PI_Version M_HU_PI_Version);

    /** Column definition for M_HU_PI_Version_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Snapshot, de.metas.handlingunits.model.I_M_HU_PI_Version> COLUMN_M_HU_PI_Version_ID = new org.adempiere.model.ModelColumn<I_M_HU_Snapshot, de.metas.handlingunits.model.I_M_HU_PI_Version>(I_M_HU_Snapshot.class, "M_HU_PI_Version_ID", de.metas.handlingunits.model.I_M_HU_PI_Version.class);
    /** Column name M_HU_PI_Version_ID */
    public static final String COLUMNNAME_M_HU_PI_Version_ID = "M_HU_PI_Version_ID";

	/**
	 * Set Handling Units (snapshot).
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_HU_Snapshot_ID (int M_HU_Snapshot_ID);

	/**
	 * Get Handling Units (snapshot).
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_HU_Snapshot_ID();

    /** Column definition for M_HU_Snapshot_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Snapshot, Object> COLUMN_M_HU_Snapshot_ID = new org.adempiere.model.ModelColumn<I_M_HU_Snapshot, Object>(I_M_HU_Snapshot.class, "M_HU_Snapshot_ID", null);
    /** Column name M_HU_Snapshot_ID */
    public static final String COLUMNNAME_M_HU_Snapshot_ID = "M_HU_Snapshot_ID";

	/**
	 * Set Lagerort.
	 * Lagerort im Lager
	 *
	 * <br>Type: Locator
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Locator_ID (int M_Locator_ID);

	/**
	 * Get Lagerort.
	 * Lagerort im Lager
	 *
	 * <br>Type: Locator
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Locator_ID();

	public org.compiere.model.I_M_Locator getM_Locator();

	public void setM_Locator(org.compiere.model.I_M_Locator M_Locator);

    /** Column definition for M_Locator_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Snapshot, org.compiere.model.I_M_Locator> COLUMN_M_Locator_ID = new org.adempiere.model.ModelColumn<I_M_HU_Snapshot, org.compiere.model.I_M_Locator>(I_M_HU_Snapshot.class, "M_Locator_ID", org.compiere.model.I_M_Locator.class);
    /** Column name M_Locator_ID */
    public static final String COLUMNNAME_M_Locator_ID = "M_Locator_ID";

	/**
	 * Set Snapshot UUID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setSnapshot_UUID (java.lang.String Snapshot_UUID);

	/**
	 * Get Snapshot UUID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getSnapshot_UUID();

    /** Column definition for Snapshot_UUID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Snapshot, Object> COLUMN_Snapshot_UUID = new org.adempiere.model.ModelColumn<I_M_HU_Snapshot, Object>(I_M_HU_Snapshot.class, "Snapshot_UUID", null);
    /** Column name Snapshot_UUID */
    public static final String COLUMNNAME_Snapshot_UUID = "Snapshot_UUID";

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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Snapshot, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_HU_Snapshot, Object>(I_M_HU_Snapshot.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Snapshot, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_HU_Snapshot, org.compiere.model.I_AD_User>(I_M_HU_Snapshot.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Suchschlüssel.
	 * Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setValue (java.lang.String Value);

	/**
	 * Get Suchschlüssel.
	 * Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getValue();

    /** Column definition for Value */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Snapshot, Object> COLUMN_Value = new org.adempiere.model.ModelColumn<I_M_HU_Snapshot, Object>(I_M_HU_Snapshot.class, "Value", null);
    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";
}
