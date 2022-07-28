package de.metas.contracts.commission.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_Customer_Trade_Margin
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Customer_Trade_Margin 
{

	String Table_Name = "C_Customer_Trade_Margin";

//	/** AD_Table_ID=541445 */
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
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Customer Margin Settings.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Customer_Trade_Margin_ID (int C_Customer_Trade_Margin_ID);

	/**
	 * Get Customer Margin Settings.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Customer_Trade_Margin_ID();

	ModelColumn<I_C_Customer_Trade_Margin, Object> COLUMN_C_Customer_Trade_Margin_ID = new ModelColumn<>(I_C_Customer_Trade_Margin.class, "C_Customer_Trade_Margin_ID", null);
	String COLUMNNAME_C_Customer_Trade_Margin_ID = "C_Customer_Trade_Margin_ID";

	/**
	 * Set Commission product.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCommission_Product_ID (int Commission_Product_ID);

	/**
	 * Get Commission product.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCommission_Product_ID();

	String COLUMNNAME_Commission_Product_ID = "Commission_Product_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_Customer_Trade_Margin, Object> COLUMN_Created = new ModelColumn<>(I_C_Customer_Trade_Margin.class, "Created", null);
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

	ModelColumn<I_C_Customer_Trade_Margin, Object> COLUMN_Description = new ModelColumn<>(I_C_Customer_Trade_Margin.class, "Description", null);
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

	ModelColumn<I_C_Customer_Trade_Margin, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Customer_Trade_Margin.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

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

	ModelColumn<I_C_Customer_Trade_Margin, Object> COLUMN_Name = new ModelColumn<>(I_C_Customer_Trade_Margin.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Points precision.
	 * Number of digits after the decimal point to which the system rounds when computing commission points.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPointsPrecision (int PointsPrecision);

	/**
	 * Get Points precision.
	 * Number of digits after the decimal point to which the system rounds when computing commission points.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPointsPrecision();

	ModelColumn<I_C_Customer_Trade_Margin, Object> COLUMN_PointsPrecision = new ModelColumn<>(I_C_Customer_Trade_Margin.class, "PointsPrecision", null);
	String COLUMNNAME_PointsPrecision = "PointsPrecision";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_Customer_Trade_Margin, Object> COLUMN_Updated = new ModelColumn<>(I_C_Customer_Trade_Margin.class, "Updated", null);
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
