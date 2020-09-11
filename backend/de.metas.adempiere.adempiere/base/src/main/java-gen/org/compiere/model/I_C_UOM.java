package org.compiere.model;


/** Generated Interface for C_UOM
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_UOM 
{

    /** TableName=C_UOM */
    public static final String Table_Name = "C_UOM";

    /** AD_Table_ID=146 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
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
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set UOM.
	 * Unit of Measure
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get UOM.
	 * Unit of Measure
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_UOM_ID();

    /** Column definition for C_UOM_ID */
    public static final org.adempiere.model.ModelColumn<I_C_UOM, Object> COLUMN_C_UOM_ID = new org.adempiere.model.ModelColumn<I_C_UOM, Object>(I_C_UOM.class, "C_UOM_ID", null);
    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Set Kostenrechnungsgenauigkeit.
	 * Rounding used costing calculations
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setCostingPrecision (int CostingPrecision);

	/**
	 * Get Kostenrechnungsgenauigkeit.
	 * Rounding used costing calculations
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCostingPrecision();

    /** Column definition for CostingPrecision */
    public static final org.adempiere.model.ModelColumn<I_C_UOM, Object> COLUMN_CostingPrecision = new org.adempiere.model.ModelColumn<I_C_UOM, Object>(I_C_UOM.class, "CostingPrecision", null);
    /** Column name CostingPrecision */
    public static final String COLUMNNAME_CostingPrecision = "CostingPrecision";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_C_UOM, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_UOM, Object>(I_C_UOM.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescription (java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_C_UOM, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_C_UOM, Object>(I_C_UOM.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Imputed Unit.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setHF134_IsImputedUnit (boolean HF134_IsImputedUnit);

	/**
	 * Get Imputed Unit.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isHF134_IsImputedUnit();

    /** Column definition for HF134_IsImputedUnit */
    public static final org.adempiere.model.ModelColumn<I_C_UOM, Object> COLUMN_HF134_IsImputedUnit = new org.adempiere.model.ModelColumn<I_C_UOM, Object>(I_C_UOM.class, "HF134_IsImputedUnit", null);
    /** Column name HF134_IsImputedUnit */
    public static final String COLUMNNAME_HF134_IsImputedUnit = "HF134_IsImputedUnit";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_C_UOM, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_UOM, Object>(I_C_UOM.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Default.
	 * Default value
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsDefault (boolean IsDefault);

	/**
	 * Get Default.
	 * Default value
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isDefault();

    /** Column definition for IsDefault */
    public static final org.adempiere.model.ModelColumn<I_C_UOM, Object> COLUMN_IsDefault = new org.adempiere.model.ModelColumn<I_C_UOM, Object>(I_C_UOM.class, "IsDefault", null);
    /** Column name IsDefault */
    public static final String COLUMNNAME_IsDefault = "IsDefault";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setName (java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getName();

    /** Column definition for Name */
    public static final org.adempiere.model.ModelColumn<I_C_UOM, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_C_UOM, Object>(I_C_UOM.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Standardgenauigkeit.
	 * Rule for rounding  calculated amounts
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setStdPrecision (int StdPrecision);

	/**
	 * Get Standardgenauigkeit.
	 * Rule for rounding  calculated amounts
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getStdPrecision();

    /** Column definition for StdPrecision */
    public static final org.adempiere.model.ModelColumn<I_C_UOM, Object> COLUMN_StdPrecision = new org.adempiere.model.ModelColumn<I_C_UOM, Object>(I_C_UOM.class, "StdPrecision", null);
    /** Column name StdPrecision */
    public static final String COLUMNNAME_StdPrecision = "StdPrecision";

	/**
	 * Set Symbol.
	 * Symbol for a Unit of Measure
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setUOMSymbol (java.lang.String UOMSymbol);

	/**
	 * Get Symbol.
	 * Symbol for a Unit of Measure
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getUOMSymbol();

    /** Column definition for UOMSymbol */
    public static final org.adempiere.model.ModelColumn<I_C_UOM, Object> COLUMN_UOMSymbol = new org.adempiere.model.ModelColumn<I_C_UOM, Object>(I_C_UOM.class, "UOMSymbol", null);
    /** Column name UOMSymbol */
    public static final String COLUMNNAME_UOMSymbol = "UOMSymbol";

	/**
	 * Set Einheiten-Typ.
	 * Dient der Zusammenfassung ähnlicher Maßeinheiten
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setUOMType (java.lang.String UOMType);

	/**
	 * Get Einheiten-Typ.
	 * Dient der Zusammenfassung ähnlicher Maßeinheiten
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getUOMType();

    /** Column definition for UOMType */
    public static final org.adempiere.model.ModelColumn<I_C_UOM, Object> COLUMN_UOMType = new org.adempiere.model.ModelColumn<I_C_UOM, Object>(I_C_UOM.class, "UOMType", null);
    /** Column name UOMType */
    public static final String COLUMNNAME_UOMType = "UOMType";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_C_UOM, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_UOM, Object>(I_C_UOM.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Kodierung der Mengeneinheit.
	 * UOM EDI X12 Code
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setX12DE355 (java.lang.String X12DE355);

	/**
	 * Get Kodierung der Mengeneinheit.
	 * UOM EDI X12 Code
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getX12DE355();

    /** Column definition for X12DE355 */
    public static final org.adempiere.model.ModelColumn<I_C_UOM, Object> COLUMN_X12DE355 = new org.adempiere.model.ModelColumn<I_C_UOM, Object>(I_C_UOM.class, "X12DE355", null);
    /** Column name X12DE355 */
    public static final String COLUMNNAME_X12DE355 = "X12DE355";
}
