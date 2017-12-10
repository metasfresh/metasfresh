package org.compiere.model;


/** Generated Interface for M_Warehouse
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_Warehouse 
{

    /** TableName=M_Warehouse */
    public static final String Table_Name = "M_Warehouse";

    /** AD_Table_ID=190 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

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
    public static final org.adempiere.model.ModelColumn<I_M_Warehouse, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_Warehouse, org.compiere.model.I_AD_Client>(I_M_Warehouse.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_Warehouse, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_Warehouse, org.compiere.model.I_AD_Org>(I_M_Warehouse.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Kostenstelle.
	 * Kostenstelle
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Activity_ID (int C_Activity_ID);

	/**
	 * Get Kostenstelle.
	 * Kostenstelle
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Activity_ID();

	public org.compiere.model.I_C_Activity getC_Activity();

	public void setC_Activity(org.compiere.model.I_C_Activity C_Activity);

    /** Column definition for C_Activity_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Warehouse, org.compiere.model.I_C_Activity> COLUMN_C_Activity_ID = new org.adempiere.model.ModelColumn<I_M_Warehouse, org.compiere.model.I_C_Activity>(I_M_Warehouse.class, "C_Activity_ID", org.compiere.model.I_C_Activity.class);
    /** Column name C_Activity_ID */
    public static final String COLUMNNAME_C_Activity_ID = "C_Activity_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_Warehouse, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_M_Warehouse, org.compiere.model.I_C_BPartner>(I_M_Warehouse.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Standort.
	 * Identifiziert die (Liefer-) Adresse des Geschäftspartners
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Standort.
	 * Identifiziert die (Liefer-) Adresse des Geschäftspartners
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_Location_ID();

	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location();

	public void setC_BPartner_Location(org.compiere.model.I_C_BPartner_Location C_BPartner_Location);

    /** Column definition for C_BPartner_Location_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Warehouse, org.compiere.model.I_C_BPartner_Location> COLUMN_C_BPartner_Location_ID = new org.adempiere.model.ModelColumn<I_M_Warehouse, org.compiere.model.I_C_BPartner_Location>(I_M_Warehouse.class, "C_BPartner_Location_ID", org.compiere.model.I_C_BPartner_Location.class);
    /** Column name C_BPartner_Location_ID */
    public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/**
	 * Set Anschrift.
	 * Location or Address
	 *
	 * <br>Type: Location
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Location_ID (int C_Location_ID);

	/**
	 * Get Anschrift.
	 * Location or Address
	 *
	 * <br>Type: Location
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Location_ID();

	public org.compiere.model.I_C_Location getC_Location();

	public void setC_Location(org.compiere.model.I_C_Location C_Location);

    /** Column definition for C_Location_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Warehouse, org.compiere.model.I_C_Location> COLUMN_C_Location_ID = new org.adempiere.model.ModelColumn<I_M_Warehouse, org.compiere.model.I_C_Location>(I_M_Warehouse.class, "C_Location_ID", org.compiere.model.I_C_Location.class);
    /** Column name C_Location_ID */
    public static final String COLUMNNAME_C_Location_ID = "C_Location_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_Warehouse, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_Warehouse, Object>(I_M_Warehouse.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_Warehouse, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_Warehouse, org.compiere.model.I_AD_User>(I_M_Warehouse.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_Warehouse, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_M_Warehouse, Object>(I_M_Warehouse.class, "Description", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_Warehouse, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_Warehouse, Object>(I_M_Warehouse.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set In Transit.
	 * Movement is in transit
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsInTransit (boolean IsInTransit);

	/**
	 * Get In Transit.
	 * Movement is in transit
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isInTransit();

    /** Column definition for IsInTransit */
    public static final org.adempiere.model.ModelColumn<I_M_Warehouse, Object> COLUMN_IsInTransit = new org.adempiere.model.ModelColumn<I_M_Warehouse, Object>(I_M_Warehouse.class, "IsInTransit", null);
    /** Column name IsInTransit */
    public static final String COLUMNNAME_IsInTransit = "IsInTransit";

	/**
	 * Set Lager.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Warehouse_ID (int M_Warehouse_ID);

	/**
	 * Get Lager.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Warehouse_ID();

    /** Column definition for M_Warehouse_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Warehouse, Object> COLUMN_M_Warehouse_ID = new org.adempiere.model.ModelColumn<I_M_Warehouse, Object>(I_M_Warehouse.class, "M_Warehouse_ID", null);
    /** Column name M_Warehouse_ID */
    public static final String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/**
	 * Set Warehouse Picking Group.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Warehouse_PickingGroup_ID (int M_Warehouse_PickingGroup_ID);

	/**
	 * Get Warehouse Picking Group.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Warehouse_PickingGroup_ID();

	public org.compiere.model.I_M_Warehouse_PickingGroup getM_Warehouse_PickingGroup();

	public void setM_Warehouse_PickingGroup(org.compiere.model.I_M_Warehouse_PickingGroup M_Warehouse_PickingGroup);

    /** Column definition for M_Warehouse_PickingGroup_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Warehouse, org.compiere.model.I_M_Warehouse_PickingGroup> COLUMN_M_Warehouse_PickingGroup_ID = new org.adempiere.model.ModelColumn<I_M_Warehouse, org.compiere.model.I_M_Warehouse_PickingGroup>(I_M_Warehouse.class, "M_Warehouse_PickingGroup_ID", org.compiere.model.I_M_Warehouse_PickingGroup.class);
    /** Column name M_Warehouse_PickingGroup_ID */
    public static final String COLUMNNAME_M_Warehouse_PickingGroup_ID = "M_Warehouse_PickingGroup_ID";

	/**
	 * Set Source Warehouse.
	 * Optional Warehouse to replenish from
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_WarehouseSource_ID (int M_WarehouseSource_ID);

	/**
	 * Get Source Warehouse.
	 * Optional Warehouse to replenish from
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_WarehouseSource_ID();

	public org.compiere.model.I_M_Warehouse getM_WarehouseSource();

	public void setM_WarehouseSource(org.compiere.model.I_M_Warehouse M_WarehouseSource);

    /** Column definition for M_WarehouseSource_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Warehouse, org.compiere.model.I_M_Warehouse> COLUMN_M_WarehouseSource_ID = new org.adempiere.model.ModelColumn<I_M_Warehouse, org.compiere.model.I_M_Warehouse>(I_M_Warehouse.class, "M_WarehouseSource_ID", org.compiere.model.I_M_Warehouse.class);
    /** Column name M_WarehouseSource_ID */
    public static final String COLUMNNAME_M_WarehouseSource_ID = "M_WarehouseSource_ID";

	/**
	 * Set MRP ausschliessen.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMRP_Exclude (java.lang.String MRP_Exclude);

	/**
	 * Get MRP ausschliessen.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getMRP_Exclude();

    /** Column definition for MRP_Exclude */
    public static final org.adempiere.model.ModelColumn<I_M_Warehouse, Object> COLUMN_MRP_Exclude = new org.adempiere.model.ModelColumn<I_M_Warehouse, Object>(I_M_Warehouse.class, "MRP_Exclude", null);
    /** Column name MRP_Exclude */
    public static final String COLUMNNAME_MRP_Exclude = "MRP_Exclude";

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
    public static final org.adempiere.model.ModelColumn<I_M_Warehouse, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_M_Warehouse, Object>(I_M_Warehouse.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Produktionsstätte.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPP_Plant_ID (int PP_Plant_ID);

	/**
	 * Get Produktionsstätte.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getPP_Plant_ID();

	public org.compiere.model.I_S_Resource getPP_Plant();

	public void setPP_Plant(org.compiere.model.I_S_Resource PP_Plant);

    /** Column definition for PP_Plant_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Warehouse, org.compiere.model.I_S_Resource> COLUMN_PP_Plant_ID = new org.adempiere.model.ModelColumn<I_M_Warehouse, org.compiere.model.I_S_Resource>(I_M_Warehouse.class, "PP_Plant_ID", org.compiere.model.I_S_Resource.class);
    /** Column name PP_Plant_ID */
    public static final String COLUMNNAME_PP_Plant_ID = "PP_Plant_ID";

	/**
	 * Set Replenishment Class.
	 * Custom class to calculate Quantity to Order
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setReplenishmentClass (java.lang.String ReplenishmentClass);

	/**
	 * Get Replenishment Class.
	 * Custom class to calculate Quantity to Order
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getReplenishmentClass();

    /** Column definition for ReplenishmentClass */
    public static final org.adempiere.model.ModelColumn<I_M_Warehouse, Object> COLUMN_ReplenishmentClass = new org.adempiere.model.ModelColumn<I_M_Warehouse, Object>(I_M_Warehouse.class, "ReplenishmentClass", null);
    /** Column name ReplenishmentClass */
    public static final String COLUMNNAME_ReplenishmentClass = "ReplenishmentClass";

	/**
	 * Set Element-Trenner.
	 * Element Separator
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setSeparator (java.lang.String Separator);

	/**
	 * Get Element-Trenner.
	 * Element Separator
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getSeparator();

    /** Column definition for Separator */
    public static final org.adempiere.model.ModelColumn<I_M_Warehouse, Object> COLUMN_Separator = new org.adempiere.model.ModelColumn<I_M_Warehouse, Object>(I_M_Warehouse.class, "Separator", null);
    /** Column name Separator */
    public static final String COLUMNNAME_Separator = "Separator";

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
    public static final org.adempiere.model.ModelColumn<I_M_Warehouse, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_Warehouse, Object>(I_M_Warehouse.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_Warehouse, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_Warehouse, org.compiere.model.I_AD_User>(I_M_Warehouse.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Suchschlüssel.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setValue (java.lang.String Value);

	/**
	 * Get Suchschlüssel.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getValue();

    /** Column definition for Value */
    public static final org.adempiere.model.ModelColumn<I_M_Warehouse, Object> COLUMN_Value = new org.adempiere.model.ModelColumn<I_M_Warehouse, Object>(I_M_Warehouse.class, "Value", null);
    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";
}
