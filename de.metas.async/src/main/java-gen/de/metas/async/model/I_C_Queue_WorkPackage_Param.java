package de.metas.async.model;


/** Generated Interface for C_Queue_WorkPackage_Param
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Queue_WorkPackage_Param 
{

    /** TableName=C_Queue_WorkPackage_Param */
    public static final String Table_Name = "C_Queue_WorkPackage_Param";

    /** AD_Table_ID=540655 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 7 - System - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(7);

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
	 * Set Organisation.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
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
	 * Set Referenz.
	 * Systemreferenz und Validierung
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Reference_ID (int AD_Reference_ID);

	/**
	 * Get Referenz.
	 * Systemreferenz und Validierung
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Reference_ID();

	public org.compiere.model.I_AD_Reference getAD_Reference();

	public void setAD_Reference(org.compiere.model.I_AD_Reference AD_Reference);

    /** Column definition for AD_Reference_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage_Param, org.compiere.model.I_AD_Reference> COLUMN_AD_Reference_ID = new org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage_Param, org.compiere.model.I_AD_Reference>(I_C_Queue_WorkPackage_Param.class, "AD_Reference_ID", org.compiere.model.I_AD_Reference.class);
    /** Column name AD_Reference_ID */
    public static final String COLUMNNAME_AD_Reference_ID = "AD_Reference_ID";

	/**
	 * Set WorkPackage Queue.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Queue_WorkPackage_ID (int C_Queue_WorkPackage_ID);

	/**
	 * Get WorkPackage Queue.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Queue_WorkPackage_ID();

	public de.metas.async.model.I_C_Queue_WorkPackage getC_Queue_WorkPackage();

	public void setC_Queue_WorkPackage(de.metas.async.model.I_C_Queue_WorkPackage C_Queue_WorkPackage);

    /** Column definition for C_Queue_WorkPackage_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage_Param, de.metas.async.model.I_C_Queue_WorkPackage> COLUMN_C_Queue_WorkPackage_ID = new org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage_Param, de.metas.async.model.I_C_Queue_WorkPackage>(I_C_Queue_WorkPackage_Param.class, "C_Queue_WorkPackage_ID", de.metas.async.model.I_C_Queue_WorkPackage.class);
    /** Column name C_Queue_WorkPackage_ID */
    public static final String COLUMNNAME_C_Queue_WorkPackage_ID = "C_Queue_WorkPackage_ID";

	/**
	 * Set Workpackage parameter.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Queue_WorkPackage_Param_ID (int C_Queue_WorkPackage_Param_ID);

	/**
	 * Get Workpackage parameter.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Queue_WorkPackage_Param_ID();

    /** Column definition for C_Queue_WorkPackage_Param_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage_Param, Object> COLUMN_C_Queue_WorkPackage_Param_ID = new org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage_Param, Object>(I_C_Queue_WorkPackage_Param.class, "C_Queue_WorkPackage_Param_ID", null);
    /** Column name C_Queue_WorkPackage_Param_ID */
    public static final String COLUMNNAME_C_Queue_WorkPackage_Param_ID = "C_Queue_WorkPackage_Param_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage_Param, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage_Param, Object>(I_C_Queue_WorkPackage_Param.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage_Param, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage_Param, Object>(I_C_Queue_WorkPackage_Param.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Process Date.
	 * Prozess-Parameter
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setP_Date (java.sql.Timestamp P_Date);

	/**
	 * Get Process Date.
	 * Prozess-Parameter
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getP_Date();

    /** Column definition for P_Date */
    public static final org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage_Param, Object> COLUMN_P_Date = new org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage_Param, Object>(I_C_Queue_WorkPackage_Param.class, "P_Date", null);
    /** Column name P_Date */
    public static final String COLUMNNAME_P_Date = "P_Date";

	/**
	 * Set Process Number.
	 * Prozess-Parameter
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setP_Number (java.math.BigDecimal P_Number);

	/**
	 * Get Process Number.
	 * Prozess-Parameter
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getP_Number();

    /** Column definition for P_Number */
    public static final org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage_Param, Object> COLUMN_P_Number = new org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage_Param, Object>(I_C_Queue_WorkPackage_Param.class, "P_Number", null);
    /** Column name P_Number */
    public static final String COLUMNNAME_P_Number = "P_Number";

	/**
	 * Set Process String.
	 * Prozess-Parameter
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setP_String (java.lang.String P_String);

	/**
	 * Get Process String.
	 * Prozess-Parameter
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getP_String();

    /** Column definition for P_String */
    public static final org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage_Param, Object> COLUMN_P_String = new org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage_Param, Object>(I_C_Queue_WorkPackage_Param.class, "P_String", null);
    /** Column name P_String */
    public static final String COLUMNNAME_P_String = "P_String";

	/**
	 * Set Parameter Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setParameterName (java.lang.String ParameterName);

	/**
	 * Get Parameter Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getParameterName();

    /** Column definition for ParameterName */
    public static final org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage_Param, Object> COLUMN_ParameterName = new org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage_Param, Object>(I_C_Queue_WorkPackage_Param.class, "ParameterName", null);
    /** Column name ParameterName */
    public static final String COLUMNNAME_ParameterName = "ParameterName";

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
    public static final org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage_Param, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage_Param, Object>(I_C_Queue_WorkPackage_Param.class, "Updated", null);
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
