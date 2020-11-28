package org.compiere.model;


/** Generated Interface for M_CostElement
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_CostElement 
{

    /** TableName=M_CostElement */
    public static final String Table_Name = "M_CostElement";

    /** AD_Table_ID=770 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 2 - Client
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(2);

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
    public static final org.adempiere.model.ModelColumn<I_M_CostElement, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_CostElement, org.compiere.model.I_AD_Client>(I_M_CostElement.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_CostElement, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_CostElement, org.compiere.model.I_AD_Org>(I_M_CostElement.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Kostenarttyp.
	 * Type of Cost Element
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setCostElementType (java.lang.String CostElementType);

	/**
	 * Get Kostenarttyp.
	 * Type of Cost Element
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCostElementType();

    /** Column definition for CostElementType */
    public static final org.adempiere.model.ModelColumn<I_M_CostElement, Object> COLUMN_CostElementType = new org.adempiere.model.ModelColumn<I_M_CostElement, Object>(I_M_CostElement.class, "CostElementType", null);
    /** Column name CostElementType */
    public static final String COLUMNNAME_CostElementType = "CostElementType";

	/**
	 * Set Kostenrechnungsmethode.
	 * Indicates how Costs will be calculated
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCostingMethod (java.lang.String CostingMethod);

	/**
	 * Get Kostenrechnungsmethode.
	 * Indicates how Costs will be calculated
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCostingMethod();

    /** Column definition for CostingMethod */
    public static final org.adempiere.model.ModelColumn<I_M_CostElement, Object> COLUMN_CostingMethod = new org.adempiere.model.ModelColumn<I_M_CostElement, Object>(I_M_CostElement.class, "CostingMethod", null);
    /** Column name CostingMethod */
    public static final String COLUMNNAME_CostingMethod = "CostingMethod";

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
    public static final org.adempiere.model.ModelColumn<I_M_CostElement, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_CostElement, Object>(I_M_CostElement.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_CostElement, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_CostElement, org.compiere.model.I_AD_User>(I_M_CostElement.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Beschreibung.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescription (java.lang.String Description);

	/**
	 * Get Beschreibung.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_M_CostElement, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_M_CostElement, Object>(I_M_CostElement.class, "Description", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_CostElement, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_CostElement, Object>(I_M_CostElement.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Berechnet.
	 * The value is calculated by the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsCalculated (boolean IsCalculated);

	/**
	 * Get Berechnet.
	 * The value is calculated by the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isCalculated();

    /** Column definition for IsCalculated */
    public static final org.adempiere.model.ModelColumn<I_M_CostElement, Object> COLUMN_IsCalculated = new org.adempiere.model.ModelColumn<I_M_CostElement, Object>(I_M_CostElement.class, "IsCalculated", null);
    /** Column name IsCalculated */
    public static final String COLUMNNAME_IsCalculated = "IsCalculated";

	/**
	 * Set Kostenart.
	 * Product Cost Element
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_CostElement_ID (int M_CostElement_ID);

	/**
	 * Get Kostenart.
	 * Product Cost Element
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_CostElement_ID();

    /** Column definition for M_CostElement_ID */
    public static final org.adempiere.model.ModelColumn<I_M_CostElement, Object> COLUMN_M_CostElement_ID = new org.adempiere.model.ModelColumn<I_M_CostElement, Object>(I_M_CostElement.class, "M_CostElement_ID", null);
    /** Column name M_CostElement_ID */
    public static final String COLUMNNAME_M_CostElement_ID = "M_CostElement_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_CostElement, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_M_CostElement, Object>(I_M_CostElement.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

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
    public static final org.adempiere.model.ModelColumn<I_M_CostElement, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_CostElement, Object>(I_M_CostElement.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_CostElement, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_CostElement, org.compiere.model.I_AD_User>(I_M_CostElement.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
