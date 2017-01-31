package de.metas.handlingunits.model;


/** Generated Interface for C_POS_HUEditor_Filter
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_POS_HUEditor_Filter 
{

    /** TableName=C_POS_HUEditor_Filter */
    public static final String Table_Name = "C_POS_HUEditor_Filter";

    /** AD_Table_ID=540647 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 4 - System
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(4);

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
    public static final org.adempiere.model.ModelColumn<I_C_POS_HUEditor_Filter, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_POS_HUEditor_Filter, org.compiere.model.I_AD_Client>(I_C_POS_HUEditor_Filter.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set AD_JavaClass.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_JavaClass_ID (int AD_JavaClass_ID);

	/**
	 * Get AD_JavaClass.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_JavaClass_ID();

	public de.metas.javaclasses.model.I_AD_JavaClass getAD_JavaClass();

	public void setAD_JavaClass(de.metas.javaclasses.model.I_AD_JavaClass AD_JavaClass);

    /** Column definition for AD_JavaClass_ID */
    public static final org.adempiere.model.ModelColumn<I_C_POS_HUEditor_Filter, de.metas.javaclasses.model.I_AD_JavaClass> COLUMN_AD_JavaClass_ID = new org.adempiere.model.ModelColumn<I_C_POS_HUEditor_Filter, de.metas.javaclasses.model.I_AD_JavaClass>(I_C_POS_HUEditor_Filter.class, "AD_JavaClass_ID", de.metas.javaclasses.model.I_AD_JavaClass.class);
    /** Column name AD_JavaClass_ID */
    public static final String COLUMNNAME_AD_JavaClass_ID = "AD_JavaClass_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_POS_HUEditor_Filter, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_POS_HUEditor_Filter, org.compiere.model.I_AD_Org>(I_C_POS_HUEditor_Filter.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Referenz.
	 * Systemreferenz und Validierung
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Reference_ID (int AD_Reference_ID);

	/**
	 * Get Referenz.
	 * Systemreferenz und Validierung
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Reference_ID();

	public org.compiere.model.I_AD_Reference getAD_Reference();

	public void setAD_Reference(org.compiere.model.I_AD_Reference AD_Reference);

    /** Column definition for AD_Reference_ID */
    public static final org.adempiere.model.ModelColumn<I_C_POS_HUEditor_Filter, org.compiere.model.I_AD_Reference> COLUMN_AD_Reference_ID = new org.adempiere.model.ModelColumn<I_C_POS_HUEditor_Filter, org.compiere.model.I_AD_Reference>(I_C_POS_HUEditor_Filter.class, "AD_Reference_ID", org.compiere.model.I_AD_Reference.class);
    /** Column name AD_Reference_ID */
    public static final String COLUMNNAME_AD_Reference_ID = "AD_Reference_ID";

	/**
	 * Set Referenzschlüssel.
	 * Muss definiert werden, wenn die Validierungsart Tabelle oder Liste ist.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Reference_Value_ID (int AD_Reference_Value_ID);

	/**
	 * Get Referenzschlüssel.
	 * Muss definiert werden, wenn die Validierungsart Tabelle oder Liste ist.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Reference_Value_ID();

	public org.compiere.model.I_AD_Reference getAD_Reference_Value();

	public void setAD_Reference_Value(org.compiere.model.I_AD_Reference AD_Reference_Value);

    /** Column definition for AD_Reference_Value_ID */
    public static final org.adempiere.model.ModelColumn<I_C_POS_HUEditor_Filter, org.compiere.model.I_AD_Reference> COLUMN_AD_Reference_Value_ID = new org.adempiere.model.ModelColumn<I_C_POS_HUEditor_Filter, org.compiere.model.I_AD_Reference>(I_C_POS_HUEditor_Filter.class, "AD_Reference_Value_ID", org.compiere.model.I_AD_Reference.class);
    /** Column name AD_Reference_Value_ID */
    public static final String COLUMNNAME_AD_Reference_Value_ID = "AD_Reference_Value_ID";

	/**
	 * Set POS HU Editor Filter.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_POS_HUEditor_Filter_ID (int C_POS_HUEditor_Filter_ID);

	/**
	 * Get POS HU Editor Filter.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_POS_HUEditor_Filter_ID();

    /** Column definition for C_POS_HUEditor_Filter_ID */
    public static final org.adempiere.model.ModelColumn<I_C_POS_HUEditor_Filter, Object> COLUMN_C_POS_HUEditor_Filter_ID = new org.adempiere.model.ModelColumn<I_C_POS_HUEditor_Filter, Object>(I_C_POS_HUEditor_Filter.class, "C_POS_HUEditor_Filter_ID", null);
    /** Column name C_POS_HUEditor_Filter_ID */
    public static final String COLUMNNAME_C_POS_HUEditor_Filter_ID = "C_POS_HUEditor_Filter_ID";

	/**
	 * Set Spaltenname.
	 * Name der Spalte in der Datenbank
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setColumnName (java.lang.String ColumnName);

	/**
	 * Get Spaltenname.
	 * Name der Spalte in der Datenbank
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getColumnName();

    /** Column definition for ColumnName */
    public static final org.adempiere.model.ModelColumn<I_C_POS_HUEditor_Filter, Object> COLUMN_ColumnName = new org.adempiere.model.ModelColumn<I_C_POS_HUEditor_Filter, Object>(I_C_POS_HUEditor_Filter.class, "ColumnName", null);
    /** Column name ColumnName */
    public static final String COLUMNNAME_ColumnName = "ColumnName";

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
    public static final org.adempiere.model.ModelColumn<I_C_POS_HUEditor_Filter, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_POS_HUEditor_Filter, Object>(I_C_POS_HUEditor_Filter.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_POS_HUEditor_Filter, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_POS_HUEditor_Filter, org.compiere.model.I_AD_User>(I_C_POS_HUEditor_Filter.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_POS_HUEditor_Filter, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_POS_HUEditor_Filter, Object>(I_C_POS_HUEditor_Filter.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

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
    public static final org.adempiere.model.ModelColumn<I_C_POS_HUEditor_Filter, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_POS_HUEditor_Filter, Object>(I_C_POS_HUEditor_Filter.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_POS_HUEditor_Filter, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_POS_HUEditor_Filter, org.compiere.model.I_AD_User>(I_C_POS_HUEditor_Filter.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
