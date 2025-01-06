package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_DiscountSchema_Calculated_Surcharge_Price
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_DiscountSchema_Calculated_Surcharge_Price 
{

	String Table_Name = "M_DiscountSchema_Calculated_Surcharge_Price";

//	/** AD_Table_ID=542431 */
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
	 * Set Currency.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Currency.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Currency_ID();

	String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_DiscountSchema_Calculated_Surcharge_Price, Object> COLUMN_Created = new ModelColumn<>(I_M_DiscountSchema_Calculated_Surcharge_Price.class, "Created", null);
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
	 * Set Region.
	 * Identifies a geographical Region
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Region_ID (int C_Region_ID);

	/**
	 * Get Region.
	 * Identifies a geographical Region
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Region_ID();

	@Nullable org.compiere.model.I_C_Region getC_Region();

	void setC_Region(@Nullable org.compiere.model.I_C_Region C_Region);

	ModelColumn<I_M_DiscountSchema_Calculated_Surcharge_Price, org.compiere.model.I_C_Region> COLUMN_C_Region_ID = new ModelColumn<>(I_M_DiscountSchema_Calculated_Surcharge_Price.class, "C_Region_ID", org.compiere.model.I_C_Region.class);
	String COLUMNNAME_C_Region_ID = "C_Region_ID";

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

	ModelColumn<I_M_DiscountSchema_Calculated_Surcharge_Price, Object> COLUMN_Description = new ModelColumn<>(I_M_DiscountSchema_Calculated_Surcharge_Price.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Calculated Freight Costs.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setFreight_Cost_Calc_Price (BigDecimal Freight_Cost_Calc_Price);

	/**
	 * Get Calculated Freight Costs.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getFreight_Cost_Calc_Price();

	ModelColumn<I_M_DiscountSchema_Calculated_Surcharge_Price, Object> COLUMN_Freight_Cost_Calc_Price = new ModelColumn<>(I_M_DiscountSchema_Calculated_Surcharge_Price.class, "Freight_Cost_Calc_Price", null);
	String COLUMNNAME_Freight_Cost_Calc_Price = "Freight_Cost_Calc_Price";

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

	ModelColumn<I_M_DiscountSchema_Calculated_Surcharge_Price, Object> COLUMN_IsActive = new ModelColumn<>(I_M_DiscountSchema_Calculated_Surcharge_Price.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Price Schema - Calculated Surcharge Price.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_DiscountSchema_Calculated_Surcharge_Price_ID (int M_DiscountSchema_Calculated_Surcharge_Price_ID);

	/**
	 * Get Price Schema - Calculated Surcharge Price.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_DiscountSchema_Calculated_Surcharge_Price_ID();

	ModelColumn<I_M_DiscountSchema_Calculated_Surcharge_Price, Object> COLUMN_M_DiscountSchema_Calculated_Surcharge_Price_ID = new ModelColumn<>(I_M_DiscountSchema_Calculated_Surcharge_Price.class, "M_DiscountSchema_Calculated_Surcharge_Price_ID", null);
	String COLUMNNAME_M_DiscountSchema_Calculated_Surcharge_Price_ID = "M_DiscountSchema_Calculated_Surcharge_Price_ID";

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

	ModelColumn<I_M_DiscountSchema_Calculated_Surcharge_Price, Object> COLUMN_Name = new ModelColumn<>(I_M_DiscountSchema_Calculated_Surcharge_Price.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_DiscountSchema_Calculated_Surcharge_Price, Object> COLUMN_Updated = new ModelColumn<>(I_M_DiscountSchema_Calculated_Surcharge_Price.class, "Updated", null);
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
}
