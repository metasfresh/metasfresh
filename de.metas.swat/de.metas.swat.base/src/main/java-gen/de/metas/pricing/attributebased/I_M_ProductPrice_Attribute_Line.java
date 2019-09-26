package de.metas.pricing.attributebased;


/** Generated Interface for M_ProductPrice_Attribute_Line
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_ProductPrice_Attribute_Line 
{

    /** TableName=M_ProductPrice_Attribute_Line */
    public static final String Table_Name = "M_ProductPrice_Attribute_Line";

    /** AD_Table_ID=540537 */
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
    public static final org.adempiere.model.ModelColumn<I_M_ProductPrice_Attribute_Line, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_ProductPrice_Attribute_Line, Object>(I_M_ProductPrice_Attribute_Line.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_ProductPrice_Attribute_Line, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_ProductPrice_Attribute_Line, Object>(I_M_ProductPrice_Attribute_Line.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Merkmal.
	 * Produkt-Merkmal
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Attribute_ID (int M_Attribute_ID);

	/**
	 * Get Merkmal.
	 * Produkt-Merkmal
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Attribute_ID();

    /** Column name M_Attribute_ID */
    public static final String COLUMNNAME_M_Attribute_ID = "M_Attribute_ID";

	/**
	 * Set Merkmals-Wert.
	 * Product Attribute Value
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_AttributeValue_ID (int M_AttributeValue_ID);

	/**
	 * Get Merkmals-Wert.
	 * Product Attribute Value
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_AttributeValue_ID();

    /** Column name M_AttributeValue_ID */
    public static final String COLUMNNAME_M_AttributeValue_ID = "M_AttributeValue_ID";

	/**
	 * Set Attribute price.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_ProductPrice_Attribute_ID (int M_ProductPrice_Attribute_ID);

	/**
	 * Get Attribute price.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_ProductPrice_Attribute_ID();

	public de.metas.pricing.attributebased.I_M_ProductPrice_Attribute getM_ProductPrice_Attribute();

	public void setM_ProductPrice_Attribute(de.metas.pricing.attributebased.I_M_ProductPrice_Attribute M_ProductPrice_Attribute);

    /** Column definition for M_ProductPrice_Attribute_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ProductPrice_Attribute_Line, de.metas.pricing.attributebased.I_M_ProductPrice_Attribute> COLUMN_M_ProductPrice_Attribute_ID = new org.adempiere.model.ModelColumn<I_M_ProductPrice_Attribute_Line, de.metas.pricing.attributebased.I_M_ProductPrice_Attribute>(I_M_ProductPrice_Attribute_Line.class, "M_ProductPrice_Attribute_ID", de.metas.pricing.attributebased.I_M_ProductPrice_Attribute.class);
    /** Column name M_ProductPrice_Attribute_ID */
    public static final String COLUMNNAME_M_ProductPrice_Attribute_ID = "M_ProductPrice_Attribute_ID";

	/**
	 * Set Attribute price line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_ProductPrice_Attribute_Line_ID (int M_ProductPrice_Attribute_Line_ID);

	/**
	 * Get Attribute price line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_ProductPrice_Attribute_Line_ID();

    /** Column definition for M_ProductPrice_Attribute_Line_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ProductPrice_Attribute_Line, Object> COLUMN_M_ProductPrice_Attribute_Line_ID = new org.adempiere.model.ModelColumn<I_M_ProductPrice_Attribute_Line, Object>(I_M_ProductPrice_Attribute_Line.class, "M_ProductPrice_Attribute_Line_ID", null);
    /** Column name M_ProductPrice_Attribute_Line_ID */
    public static final String COLUMNNAME_M_ProductPrice_Attribute_Line_ID = "M_ProductPrice_Attribute_Line_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_ProductPrice_Attribute_Line, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_ProductPrice_Attribute_Line, Object>(I_M_ProductPrice_Attribute_Line.class, "Updated", null);
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
