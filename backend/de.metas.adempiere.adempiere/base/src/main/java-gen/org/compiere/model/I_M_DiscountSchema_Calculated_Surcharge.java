package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_DiscountSchema_Calculated_Surcharge
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_DiscountSchema_Calculated_Surcharge 
{

	String Table_Name = "M_DiscountSchema_Calculated_Surcharge";

//	/** AD_Table_ID=542432 */
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
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_DiscountSchema_Calculated_Surcharge, Object> COLUMN_Created = new ModelColumn<>(I_M_DiscountSchema_Calculated_Surcharge.class, "Created", null);
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

	ModelColumn<I_M_DiscountSchema_Calculated_Surcharge, Object> COLUMN_Description = new ModelColumn<>(I_M_DiscountSchema_Calculated_Surcharge.class, "Description", null);
	String COLUMNNAME_Description = "Description";

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

	ModelColumn<I_M_DiscountSchema_Calculated_Surcharge, Object> COLUMN_IsActive = new ModelColumn<>(I_M_DiscountSchema_Calculated_Surcharge.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Price Schema - Calculated Surcharge.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_DiscountSchema_Calculated_Surcharge_ID (int M_DiscountSchema_Calculated_Surcharge_ID);

	/**
	 * Get Price Schema - Calculated Surcharge.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_DiscountSchema_Calculated_Surcharge_ID();

	ModelColumn<I_M_DiscountSchema_Calculated_Surcharge, Object> COLUMN_M_DiscountSchema_Calculated_Surcharge_ID = new ModelColumn<>(I_M_DiscountSchema_Calculated_Surcharge.class, "M_DiscountSchema_Calculated_Surcharge_ID", null);
	String COLUMNNAME_M_DiscountSchema_Calculated_Surcharge_ID = "M_DiscountSchema_Calculated_Surcharge_ID";

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

	ModelColumn<I_M_DiscountSchema_Calculated_Surcharge, Object> COLUMN_Name = new ModelColumn<>(I_M_DiscountSchema_Calculated_Surcharge.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Surcharge Calculation SQL.
	 * The SQL needs to be entered without ";
" and needs to contain two parameter: "$1" ( Target_PriceList_Version_ID ) und "$2" ( Source_M_ProductPrice_ID )
	 *
	 * <br>Type: Text
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSurcharge_Calc_SQL (java.lang.String Surcharge_Calc_SQL);

	/**
	 * Get Surcharge Calculation SQL.
	 * The SQL needs to be entered without ";
" and needs to contain two parameter: "$1" ( Target_PriceList_Version_ID ) und "$2" ( Source_M_ProductPrice_ID )
	 *
	 * <br>Type: Text
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getSurcharge_Calc_SQL();

	ModelColumn<I_M_DiscountSchema_Calculated_Surcharge, Object> COLUMN_Surcharge_Calc_SQL = new ModelColumn<>(I_M_DiscountSchema_Calculated_Surcharge.class, "Surcharge_Calc_SQL", null);
	String COLUMNNAME_Surcharge_Calc_SQL = "Surcharge_Calc_SQL";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_DiscountSchema_Calculated_Surcharge, Object> COLUMN_Updated = new ModelColumn<>(I_M_DiscountSchema_Calculated_Surcharge.class, "Updated", null);
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
