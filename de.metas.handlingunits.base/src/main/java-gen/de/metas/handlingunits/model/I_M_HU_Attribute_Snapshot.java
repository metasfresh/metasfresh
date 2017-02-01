package de.metas.handlingunits.model;


/** Generated Interface for M_HU_Attribute_Snapshot
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_HU_Attribute_Snapshot 
{

    /** TableName=M_HU_Attribute_Snapshot */
    public static final String Table_Name = "M_HU_Attribute_Snapshot";

    /** AD_Table_ID=540671 */
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Attribute_Snapshot, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_HU_Attribute_Snapshot, org.compiere.model.I_AD_Client>(I_M_HU_Attribute_Snapshot.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Attribute_Snapshot, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_HU_Attribute_Snapshot, org.compiere.model.I_AD_Org>(I_M_HU_Attribute_Snapshot.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Attribute_Snapshot, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_HU_Attribute_Snapshot, Object>(I_M_HU_Attribute_Snapshot.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Attribute_Snapshot, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_HU_Attribute_Snapshot, org.compiere.model.I_AD_User>(I_M_HU_Attribute_Snapshot.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Attribute_Snapshot, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_HU_Attribute_Snapshot, Object>(I_M_HU_Attribute_Snapshot.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Merkmal.
	 * Produkt-Merkmal
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Attribute_ID (int M_Attribute_ID);

	/**
	 * Get Merkmal.
	 * Produkt-Merkmal
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Attribute_ID();

	public org.compiere.model.I_M_Attribute getM_Attribute();

	public void setM_Attribute(org.compiere.model.I_M_Attribute M_Attribute);

    /** Column definition for M_Attribute_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Attribute_Snapshot, org.compiere.model.I_M_Attribute> COLUMN_M_Attribute_ID = new org.adempiere.model.ModelColumn<I_M_HU_Attribute_Snapshot, org.compiere.model.I_M_Attribute>(I_M_HU_Attribute_Snapshot.class, "M_Attribute_ID", org.compiere.model.I_M_Attribute.class);
    /** Column name M_Attribute_ID */
    public static final String COLUMNNAME_M_Attribute_ID = "M_Attribute_ID";

	/**
	 * Set Handling Unit Attribute.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_HU_Attribute_ID (int M_HU_Attribute_ID);

	/**
	 * Get Handling Unit Attribute.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_HU_Attribute_ID();

	public de.metas.handlingunits.model.I_M_HU_Attribute getM_HU_Attribute();

	public void setM_HU_Attribute(de.metas.handlingunits.model.I_M_HU_Attribute M_HU_Attribute);

    /** Column definition for M_HU_Attribute_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Attribute_Snapshot, de.metas.handlingunits.model.I_M_HU_Attribute> COLUMN_M_HU_Attribute_ID = new org.adempiere.model.ModelColumn<I_M_HU_Attribute_Snapshot, de.metas.handlingunits.model.I_M_HU_Attribute>(I_M_HU_Attribute_Snapshot.class, "M_HU_Attribute_ID", de.metas.handlingunits.model.I_M_HU_Attribute.class);
    /** Column name M_HU_Attribute_ID */
    public static final String COLUMNNAME_M_HU_Attribute_ID = "M_HU_Attribute_ID";

	/**
	 * Set Handling Units Attribute.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_HU_Attribute_Snapshot_ID (int M_HU_Attribute_Snapshot_ID);

	/**
	 * Get Handling Units Attribute.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_HU_Attribute_Snapshot_ID();

    /** Column definition for M_HU_Attribute_Snapshot_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Attribute_Snapshot, Object> COLUMN_M_HU_Attribute_Snapshot_ID = new org.adempiere.model.ModelColumn<I_M_HU_Attribute_Snapshot, Object>(I_M_HU_Attribute_Snapshot.class, "M_HU_Attribute_Snapshot_ID", null);
    /** Column name M_HU_Attribute_Snapshot_ID */
    public static final String COLUMNNAME_M_HU_Attribute_Snapshot_ID = "M_HU_Attribute_Snapshot_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Attribute_Snapshot, de.metas.handlingunits.model.I_M_HU> COLUMN_M_HU_ID = new org.adempiere.model.ModelColumn<I_M_HU_Attribute_Snapshot, de.metas.handlingunits.model.I_M_HU>(I_M_HU_Attribute_Snapshot.class, "M_HU_ID", de.metas.handlingunits.model.I_M_HU.class);
    /** Column name M_HU_ID */
    public static final String COLUMNNAME_M_HU_ID = "M_HU_ID";

	/**
	 * Set Handling Units Packing Instructions Attribute.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_HU_PI_Attribute_ID (int M_HU_PI_Attribute_ID);

	/**
	 * Get Handling Units Packing Instructions Attribute.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_HU_PI_Attribute_ID();

	public de.metas.handlingunits.model.I_M_HU_PI_Attribute getM_HU_PI_Attribute();

	public void setM_HU_PI_Attribute(de.metas.handlingunits.model.I_M_HU_PI_Attribute M_HU_PI_Attribute);

    /** Column definition for M_HU_PI_Attribute_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Attribute_Snapshot, de.metas.handlingunits.model.I_M_HU_PI_Attribute> COLUMN_M_HU_PI_Attribute_ID = new org.adempiere.model.ModelColumn<I_M_HU_Attribute_Snapshot, de.metas.handlingunits.model.I_M_HU_PI_Attribute>(I_M_HU_Attribute_Snapshot.class, "M_HU_PI_Attribute_ID", de.metas.handlingunits.model.I_M_HU_PI_Attribute.class);
    /** Column name M_HU_PI_Attribute_ID */
    public static final String COLUMNNAME_M_HU_PI_Attribute_ID = "M_HU_PI_Attribute_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Attribute_Snapshot, Object> COLUMN_Snapshot_UUID = new org.adempiere.model.ModelColumn<I_M_HU_Attribute_Snapshot, Object>(I_M_HU_Attribute_Snapshot.class, "Snapshot_UUID", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Attribute_Snapshot, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_HU_Attribute_Snapshot, Object>(I_M_HU_Attribute_Snapshot.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Attribute_Snapshot, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_HU_Attribute_Snapshot, org.compiere.model.I_AD_User>(I_M_HU_Attribute_Snapshot.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Suchschlüssel.
	 * Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setValue (java.lang.String Value);

	/**
	 * Get Suchschlüssel.
	 * Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getValue();

    /** Column definition for Value */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Attribute_Snapshot, Object> COLUMN_Value = new org.adempiere.model.ModelColumn<I_M_HU_Attribute_Snapshot, Object>(I_M_HU_Attribute_Snapshot.class, "Value", null);
    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";

	/**
	 * Set Merkmals-Wert (initial).
	 * Initial Value of the Attribute
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setValueInitial (java.lang.String ValueInitial);

	/**
	 * Get Merkmals-Wert (initial).
	 * Initial Value of the Attribute
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getValueInitial();

    /** Column definition for ValueInitial */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Attribute_Snapshot, Object> COLUMN_ValueInitial = new org.adempiere.model.ModelColumn<I_M_HU_Attribute_Snapshot, Object>(I_M_HU_Attribute_Snapshot.class, "ValueInitial", null);
    /** Column name ValueInitial */
    public static final String COLUMNNAME_ValueInitial = "ValueInitial";

	/**
	 * Set Zahlwert.
	 * Numeric Value
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setValueNumber (java.math.BigDecimal ValueNumber);

	/**
	 * Get Zahlwert.
	 * Numeric Value
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getValueNumber();

    /** Column definition for ValueNumber */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Attribute_Snapshot, Object> COLUMN_ValueNumber = new org.adempiere.model.ModelColumn<I_M_HU_Attribute_Snapshot, Object>(I_M_HU_Attribute_Snapshot.class, "ValueNumber", null);
    /** Column name ValueNumber */
    public static final String COLUMNNAME_ValueNumber = "ValueNumber";

	/**
	 * Set Zahlwert (initial).
	 * Initial Numeric Value
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setValueNumberInitial (java.math.BigDecimal ValueNumberInitial);

	/**
	 * Get Zahlwert (initial).
	 * Initial Numeric Value
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getValueNumberInitial();

    /** Column definition for ValueNumberInitial */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Attribute_Snapshot, Object> COLUMN_ValueNumberInitial = new org.adempiere.model.ModelColumn<I_M_HU_Attribute_Snapshot, Object>(I_M_HU_Attribute_Snapshot.class, "ValueNumberInitial", null);
    /** Column name ValueNumberInitial */
    public static final String COLUMNNAME_ValueNumberInitial = "ValueNumberInitial";
}
