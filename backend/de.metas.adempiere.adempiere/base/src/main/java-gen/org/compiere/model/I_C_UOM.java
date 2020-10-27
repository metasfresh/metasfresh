package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_UOM
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_UOM 
{

	String Table_Name = "C_UOM";

//	/** AD_Table_ID=146 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set UOM.
	 * Unit of Measure
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get UOM.
	 * Unit of Measure
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_UOM_ID();

	ModelColumn<I_C_UOM, Object> COLUMN_C_UOM_ID = new ModelColumn<>(I_C_UOM.class, "C_UOM_ID", null);
	String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Set Kostenrechnungsgenauigkeit.
	 * Rounding used costing calculations
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCostingPrecision (int CostingPrecision);

	/**
	 * Get Kostenrechnungsgenauigkeit.
	 * Rounding used costing calculations
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCostingPrecision();

	ModelColumn<I_C_UOM, Object> COLUMN_CostingPrecision = new ModelColumn<>(I_C_UOM.class, "CostingPrecision", null);
	String COLUMNNAME_CostingPrecision = "CostingPrecision";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_UOM, Object> COLUMN_Created = new ModelColumn<>(I_C_UOM.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_C_UOM, Object> COLUMN_Description = new ModelColumn<>(I_C_UOM.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Imputed Unit.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setHF134_IsImputedUnit (boolean HF134_IsImputedUnit);

	/**
	 * Get Imputed Unit.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isHF134_IsImputedUnit();

	ModelColumn<I_C_UOM, Object> COLUMN_HF134_IsImputedUnit = new ModelColumn<>(I_C_UOM.class, "HF134_IsImputedUnit", null);
	String COLUMNNAME_HF134_IsImputedUnit = "HF134_IsImputedUnit";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isActive();

	ModelColumn<I_C_UOM, Object> COLUMN_IsActive = new ModelColumn<>(I_C_UOM.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Default.
	 * Default value
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDefault (boolean IsDefault);

	/**
	 * Get Default.
	 * Default value
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDefault();

	ModelColumn<I_C_UOM, Object> COLUMN_IsDefault = new ModelColumn<>(I_C_UOM.class, "IsDefault", null);
	String COLUMNNAME_IsDefault = "IsDefault";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setName (java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getName();

	ModelColumn<I_C_UOM, Object> COLUMN_Name = new ModelColumn<>(I_C_UOM.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Standardgenauigkeit.
	 * Rule for rounding  calculated amounts
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setStdPrecision (int StdPrecision);

	/**
	 * Get Standardgenauigkeit.
	 * Rule for rounding  calculated amounts
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getStdPrecision();

	ModelColumn<I_C_UOM, Object> COLUMN_StdPrecision = new ModelColumn<>(I_C_UOM.class, "StdPrecision", null);
	String COLUMNNAME_StdPrecision = "StdPrecision";

	/**
	 * Set Symbol.
	 * Symbol for a Unit of Measure
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUOMSymbol (@Nullable java.lang.String UOMSymbol);

	/**
	 * Get Symbol.
	 * Symbol for a Unit of Measure
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUOMSymbol();

	ModelColumn<I_C_UOM, Object> COLUMN_UOMSymbol = new ModelColumn<>(I_C_UOM.class, "UOMSymbol", null);
	String COLUMNNAME_UOMSymbol = "UOMSymbol";

	/**
	 * Set Einheiten-Typ.
	 * Dient der Zusammenfassung ähnlicher Maßeinheiten
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUOMType (@Nullable java.lang.String UOMType);

	/**
	 * Get Einheiten-Typ.
	 * Dient der Zusammenfassung ähnlicher Maßeinheiten
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUOMType();

	ModelColumn<I_C_UOM, Object> COLUMN_UOMType = new ModelColumn<>(I_C_UOM.class, "UOMType", null);
	String COLUMNNAME_UOMType = "UOMType";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_UOM, Object> COLUMN_Updated = new ModelColumn<>(I_C_UOM.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Kodierung der Mengeneinheit.
	 * UOM EDI X12 Code
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setX12DE355 (java.lang.String X12DE355);

	/**
	 * Get Kodierung der Mengeneinheit.
	 * UOM EDI X12 Code
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getX12DE355();

	ModelColumn<I_C_UOM, Object> COLUMN_X12DE355 = new ModelColumn<>(I_C_UOM.class, "X12DE355", null);
	String COLUMNNAME_X12DE355 = "X12DE355";
}
