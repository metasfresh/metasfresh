package org.compiere.model;


/** Generated Interface for C_Element
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Element 
{

    /** TableName=C_Element */
    public static final String Table_Name = "C_Element";

    /** AD_Table_ID=142 */
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
    public static final org.adempiere.model.ModelColumn<I_C_Element, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_Element, org.compiere.model.I_AD_Client>(I_C_Element.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_Element, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_Element, org.compiere.model.I_AD_Org>(I_C_Element.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Baum.
	 * Identifies a Tree
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Tree_ID (int AD_Tree_ID);

	/**
	 * Get Baum.
	 * Identifies a Tree
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Tree_ID();

	public org.compiere.model.I_AD_Tree getAD_Tree();

	public void setAD_Tree(org.compiere.model.I_AD_Tree AD_Tree);

    /** Column definition for AD_Tree_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Element, org.compiere.model.I_AD_Tree> COLUMN_AD_Tree_ID = new org.adempiere.model.ModelColumn<I_C_Element, org.compiere.model.I_AD_Tree>(I_C_Element.class, "AD_Tree_ID", org.compiere.model.I_AD_Tree.class);
    /** Column name AD_Tree_ID */
    public static final String COLUMNNAME_AD_Tree_ID = "AD_Tree_ID";

	/**
	 * Set Element.
	 * Accounting Element
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Element_ID (int C_Element_ID);

	/**
	 * Get Element.
	 * Accounting Element
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Element_ID();

    /** Column definition for C_Element_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Element, Object> COLUMN_C_Element_ID = new org.adempiere.model.ModelColumn<I_C_Element, Object>(I_C_Element.class, "C_Element_ID", null);
    /** Column name C_Element_ID */
    public static final String COLUMNNAME_C_Element_ID = "C_Element_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_Element, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_Element, Object>(I_C_Element.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Element, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_Element, org.compiere.model.I_AD_User>(I_C_Element.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_Element, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_C_Element, Object>(I_C_Element.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Art.
	 * Element Type (account or user defined)
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setElementType (java.lang.String ElementType);

	/**
	 * Get Art.
	 * Element Type (account or user defined)
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getElementType();

    /** Column definition for ElementType */
    public static final org.adempiere.model.ModelColumn<I_C_Element, Object> COLUMN_ElementType = new org.adempiere.model.ModelColumn<I_C_Element, Object>(I_C_Element.class, "ElementType", null);
    /** Column name ElementType */
    public static final String COLUMNNAME_ElementType = "ElementType";

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
    public static final org.adempiere.model.ModelColumn<I_C_Element, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_Element, Object>(I_C_Element.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Saldierung.
	 * All transactions within an element value must balance (e.g. cost centers)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsBalancing (boolean IsBalancing);

	/**
	 * Get Saldierung.
	 * All transactions within an element value must balance (e.g. cost centers)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isBalancing();

    /** Column definition for IsBalancing */
    public static final org.adempiere.model.ModelColumn<I_C_Element, Object> COLUMN_IsBalancing = new org.adempiere.model.ModelColumn<I_C_Element, Object>(I_C_Element.class, "IsBalancing", null);
    /** Column name IsBalancing */
    public static final String COLUMNNAME_IsBalancing = "IsBalancing";

	/**
	 * Set Basiskonto.
	 * The primary natural account
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsNaturalAccount (boolean IsNaturalAccount);

	/**
	 * Get Basiskonto.
	 * The primary natural account
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isNaturalAccount();

    /** Column definition for IsNaturalAccount */
    public static final org.adempiere.model.ModelColumn<I_C_Element, Object> COLUMN_IsNaturalAccount = new org.adempiere.model.ModelColumn<I_C_Element, Object>(I_C_Element.class, "IsNaturalAccount", null);
    /** Column name IsNaturalAccount */
    public static final String COLUMNNAME_IsNaturalAccount = "IsNaturalAccount";

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
    public static final org.adempiere.model.ModelColumn<I_C_Element, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_C_Element, Object>(I_C_Element.class, "Name", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Element, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_Element, Object>(I_C_Element.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Element, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_Element, org.compiere.model.I_AD_User>(I_C_Element.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Value Format.
	 * Format of the value;
 Can contain fixed format elements, Variables: "_lLoOaAcCa09"
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setVFormat (java.lang.String VFormat);

	/**
	 * Get Value Format.
	 * Format of the value;
 Can contain fixed format elements, Variables: "_lLoOaAcCa09"
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getVFormat();

    /** Column definition for VFormat */
    public static final org.adempiere.model.ModelColumn<I_C_Element, Object> COLUMN_VFormat = new org.adempiere.model.ModelColumn<I_C_Element, Object>(I_C_Element.class, "VFormat", null);
    /** Column name VFormat */
    public static final String COLUMNNAME_VFormat = "VFormat";
}
