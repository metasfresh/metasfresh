package org.compiere.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for M_Maturing_Configuration_Line
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_Maturing_Configuration_Line 
{

	String Table_Name = "M_Maturing_Configuration_Line";

//	/** AD_Table_ID=542384 */
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

	ModelColumn<I_M_Maturing_Configuration_Line, Object> COLUMN_Created = new ModelColumn<>(I_M_Maturing_Configuration_Line.class, "Created", null);
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
	 * Set From Product.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setFrom_Product_ID (int From_Product_ID);

	/**
	 * Get From Product.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getFrom_Product_ID();

	String COLUMNNAME_From_Product_ID = "From_Product_ID";

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

	ModelColumn<I_M_Maturing_Configuration_Line, Object> COLUMN_IsActive = new ModelColumn<>(I_M_Maturing_Configuration_Line.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Matured Product.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMatured_Product_ID (int Matured_Product_ID);

	/**
	 * Get Matured Product.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getMatured_Product_ID();

	String COLUMNNAME_Matured_Product_ID = "Matured_Product_ID";

	/**
	 * Set Maturity Age.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMaturityAge (int MaturityAge);

	/**
	 * Get Maturity Age.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getMaturityAge();

	ModelColumn<I_M_Maturing_Configuration_Line, Object> COLUMN_MaturityAge = new ModelColumn<>(I_M_Maturing_Configuration_Line.class, "MaturityAge", null);
	String COLUMNNAME_MaturityAge = "MaturityAge";

	/**
	 * Set Maturing Configuration .
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Maturing_Configuration_ID (int M_Maturing_Configuration_ID);

	/**
	 * Get Maturing Configuration .
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Maturing_Configuration_ID();

	org.compiere.model.I_M_Maturing_Configuration getM_Maturing_Configuration();

	void setM_Maturing_Configuration(org.compiere.model.I_M_Maturing_Configuration M_Maturing_Configuration);

	ModelColumn<I_M_Maturing_Configuration_Line, org.compiere.model.I_M_Maturing_Configuration> COLUMN_M_Maturing_Configuration_ID = new ModelColumn<>(I_M_Maturing_Configuration_Line.class, "M_Maturing_Configuration_ID", org.compiere.model.I_M_Maturing_Configuration.class);
	String COLUMNNAME_M_Maturing_Configuration_ID = "M_Maturing_Configuration_ID";

	/**
	 * Set Maturing Configuration Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Maturing_Configuration_Line_ID (int M_Maturing_Configuration_Line_ID);

	/**
	 * Get Maturing Configuration Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Maturing_Configuration_Line_ID();

	ModelColumn<I_M_Maturing_Configuration_Line, Object> COLUMN_M_Maturing_Configuration_Line_ID = new ModelColumn<>(I_M_Maturing_Configuration_Line.class, "M_Maturing_Configuration_Line_ID", null);
	String COLUMNNAME_M_Maturing_Configuration_Line_ID = "M_Maturing_Configuration_Line_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_Maturing_Configuration_Line, Object> COLUMN_Updated = new ModelColumn<>(I_M_Maturing_Configuration_Line.class, "Updated", null);
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
