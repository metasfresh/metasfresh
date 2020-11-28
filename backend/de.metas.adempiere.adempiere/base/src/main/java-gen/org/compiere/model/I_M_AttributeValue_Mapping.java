package org.compiere.model;


/** Generated Interface for M_AttributeValue_Mapping
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_AttributeValue_Mapping 
{

    /** TableName=M_AttributeValue_Mapping */
    public static final String Table_Name = "M_AttributeValue_Mapping";

    /** AD_Table_ID=540631 */
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
	 * Get Erstellt.
	 * Datum, an dem dieser Eintrag erstellt wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_M_AttributeValue_Mapping, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_AttributeValue_Mapping, Object>(I_M_AttributeValue_Mapping.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_AttributeValue_Mapping, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_M_AttributeValue_Mapping, Object>(I_M_AttributeValue_Mapping.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

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
    public static final org.adempiere.model.ModelColumn<I_M_AttributeValue_Mapping, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_AttributeValue_Mapping, Object>(I_M_AttributeValue_Mapping.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Merkmals-Wert.
	 * Product Attribute Value
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_AttributeValue_ID (int M_AttributeValue_ID);

	/**
	 * Get Merkmals-Wert.
	 * Product Attribute Value
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_AttributeValue_ID();

    /** Column name M_AttributeValue_ID */
    public static final String COLUMNNAME_M_AttributeValue_ID = "M_AttributeValue_ID";

	/**
	 * Set Attribut substitute.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_AttributeValue_Mapping_ID (int M_AttributeValue_Mapping_ID);

	/**
	 * Get Attribut substitute.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_AttributeValue_Mapping_ID();

    /** Column definition for M_AttributeValue_Mapping_ID */
    public static final org.adempiere.model.ModelColumn<I_M_AttributeValue_Mapping, Object> COLUMN_M_AttributeValue_Mapping_ID = new org.adempiere.model.ModelColumn<I_M_AttributeValue_Mapping, Object>(I_M_AttributeValue_Mapping.class, "M_AttributeValue_Mapping_ID", null);
    /** Column name M_AttributeValue_Mapping_ID */
    public static final String COLUMNNAME_M_AttributeValue_Mapping_ID = "M_AttributeValue_Mapping_ID";

	/**
	 * Set Merkmals-Wert Nach.
	 * Product Attribute Value To
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_AttributeValue_To_ID (int M_AttributeValue_To_ID);

	/**
	 * Get Merkmals-Wert Nach.
	 * Product Attribute Value To
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_AttributeValue_To_ID();

    /** Column name M_AttributeValue_To_ID */
    public static final String COLUMNNAME_M_AttributeValue_To_ID = "M_AttributeValue_To_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_AttributeValue_Mapping, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_AttributeValue_Mapping, Object>(I_M_AttributeValue_Mapping.class, "Updated", null);
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
