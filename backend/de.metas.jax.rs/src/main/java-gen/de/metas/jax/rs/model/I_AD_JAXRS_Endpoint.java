package de.metas.jax.rs.model;


/** Generated Interface for AD_JAXRS_Endpoint
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_JAXRS_Endpoint 
{

    /** TableName=AD_JAXRS_Endpoint */
    public static final String Table_Name = "AD_JAXRS_Endpoint";

    /** AD_Table_ID=540750 */
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
    public static final org.adempiere.model.ModelColumn<I_AD_JAXRS_Endpoint, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_AD_JAXRS_Endpoint, org.compiere.model.I_AD_Client>(I_AD_JAXRS_Endpoint.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set AD_JavaClass.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_JavaClass_ID (int AD_JavaClass_ID);

	/**
	 * Get AD_JavaClass.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_JavaClass_ID();

	public de.metas.javaclasses.model.I_AD_JavaClass getAD_JavaClass();

	public void setAD_JavaClass(de.metas.javaclasses.model.I_AD_JavaClass AD_JavaClass);

    /** Column definition for AD_JavaClass_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_JAXRS_Endpoint, de.metas.javaclasses.model.I_AD_JavaClass> COLUMN_AD_JavaClass_ID = new org.adempiere.model.ModelColumn<I_AD_JAXRS_Endpoint, de.metas.javaclasses.model.I_AD_JavaClass>(I_AD_JAXRS_Endpoint.class, "AD_JavaClass_ID", de.metas.javaclasses.model.I_AD_JavaClass.class);
    /** Column name AD_JavaClass_ID */
    public static final String COLUMNNAME_AD_JavaClass_ID = "AD_JavaClass_ID";

	/**
	 * Set JAX-RS-Endpoint.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_JAXRS_Endpoint_ID (int AD_JAXRS_Endpoint_ID);

	/**
	 * Get JAX-RS-Endpoint.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_JAXRS_Endpoint_ID();

    /** Column definition for AD_JAXRS_Endpoint_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_JAXRS_Endpoint, Object> COLUMN_AD_JAXRS_Endpoint_ID = new org.adempiere.model.ModelColumn<I_AD_JAXRS_Endpoint, Object>(I_AD_JAXRS_Endpoint.class, "AD_JAXRS_Endpoint_ID", null);
    /** Column name AD_JAXRS_Endpoint_ID */
    public static final String COLUMNNAME_AD_JAXRS_Endpoint_ID = "AD_JAXRS_Endpoint_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_JAXRS_Endpoint, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_AD_JAXRS_Endpoint, org.compiere.model.I_AD_Org>(I_AD_JAXRS_Endpoint.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
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
    public static final org.adempiere.model.ModelColumn<I_AD_JAXRS_Endpoint, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_JAXRS_Endpoint, Object>(I_AD_JAXRS_Endpoint.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_JAXRS_Endpoint, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_AD_JAXRS_Endpoint, org.compiere.model.I_AD_User>(I_AD_JAXRS_Endpoint.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Endpoint type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setEndpointType (java.lang.String EndpointType);

	/**
	 * Get Endpoint type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEndpointType();

    /** Column definition for EndpointType */
    public static final org.adempiere.model.ModelColumn<I_AD_JAXRS_Endpoint, Object> COLUMN_EndpointType = new org.adempiere.model.ModelColumn<I_AD_JAXRS_Endpoint, Object>(I_AD_JAXRS_Endpoint.class, "EndpointType", null);
    /** Column name EndpointType */
    public static final String COLUMNNAME_EndpointType = "EndpointType";

	/**
	 * Set Entitäts-Art.
	 * Dictionary Entity Type;
 Determines ownership and synchronization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setEntityType (java.lang.String EntityType);

	/**
	 * Get Entitäts-Art.
	 * Dictionary Entity Type;
 Determines ownership and synchronization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEntityType();

    /** Column definition for EntityType */
    public static final org.adempiere.model.ModelColumn<I_AD_JAXRS_Endpoint, Object> COLUMN_EntityType = new org.adempiere.model.ModelColumn<I_AD_JAXRS_Endpoint, Object>(I_AD_JAXRS_Endpoint.class, "EntityType", null);
    /** Column name EntityType */
    public static final String COLUMNNAME_EntityType = "EntityType";

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
    public static final org.adempiere.model.ModelColumn<I_AD_JAXRS_Endpoint, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_JAXRS_Endpoint, Object>(I_AD_JAXRS_Endpoint.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Endpoint active.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsEndpointActive (boolean IsEndpointActive);

	/**
	 * Get Endpoint active.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isEndpointActive();

    /** Column definition for IsEndpointActive */
    public static final org.adempiere.model.ModelColumn<I_AD_JAXRS_Endpoint, Object> COLUMN_IsEndpointActive = new org.adempiere.model.ModelColumn<I_AD_JAXRS_Endpoint, Object>(I_AD_JAXRS_Endpoint.class, "IsEndpointActive", null);
    /** Column name IsEndpointActive */
    public static final String COLUMNNAME_IsEndpointActive = "IsEndpointActive";

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
    public static final org.adempiere.model.ModelColumn<I_AD_JAXRS_Endpoint, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_JAXRS_Endpoint, Object>(I_AD_JAXRS_Endpoint.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_JAXRS_Endpoint, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_AD_JAXRS_Endpoint, org.compiere.model.I_AD_User>(I_AD_JAXRS_Endpoint.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
