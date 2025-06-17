package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_ConversionRate_Rule
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_ConversionRate_Rule 
{

	String Table_Name = "C_ConversionRate_Rule";

//	/** AD_Table_ID=542504 */
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
	 * Set Conversion Rate Rule.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_ConversionRate_Rule_ID (int C_ConversionRate_Rule_ID);

	/**
	 * Get Conversion Rate Rule.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_ConversionRate_Rule_ID();

	ModelColumn<I_C_ConversionRate_Rule, Object> COLUMN_C_ConversionRate_Rule_ID = new ModelColumn<>(I_C_ConversionRate_Rule.class, "C_ConversionRate_Rule_ID", null);
	String COLUMNNAME_C_ConversionRate_Rule_ID = "C_ConversionRate_Rule_ID";

	/**
	 * Set Currency.
	 * The Currency for this record
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Currency.
	 * The Currency for this record
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Currency_ID();

	String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set Target Currency.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Currency_To_ID (int C_Currency_To_ID);

	/**
	 * Get Target Currency.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Currency_To_ID();

	String COLUMNNAME_C_Currency_To_ID = "C_Currency_To_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_ConversionRate_Rule, Object> COLUMN_Created = new ModelColumn<>(I_C_ConversionRate_Rule.class, "Created", null);
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

	ModelColumn<I_C_ConversionRate_Rule, Object> COLUMN_IsActive = new ModelColumn<>(I_C_ConversionRate_Rule.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Max. Multiply Rate.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMultiplyRate_Max (@Nullable BigDecimal MultiplyRate_Max);

	/**
	 * Get Max. Multiply Rate.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getMultiplyRate_Max();

	ModelColumn<I_C_ConversionRate_Rule, Object> COLUMN_MultiplyRate_Max = new ModelColumn<>(I_C_ConversionRate_Rule.class, "MultiplyRate_Max", null);
	String COLUMNNAME_MultiplyRate_Max = "MultiplyRate_Max";

	/**
	 * Set Min. Multiply Rate.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMultiplyRate_Min (@Nullable BigDecimal MultiplyRate_Min);

	/**
	 * Get Min. Multiply Rate.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getMultiplyRate_Min();

	ModelColumn<I_C_ConversionRate_Rule, Object> COLUMN_MultiplyRate_Min = new ModelColumn<>(I_C_ConversionRate_Rule.class, "MultiplyRate_Min", null);
	String COLUMNNAME_MultiplyRate_Min = "MultiplyRate_Min";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_ConversionRate_Rule, Object> COLUMN_Updated = new ModelColumn<>(I_C_ConversionRate_Rule.class, "Updated", null);
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
