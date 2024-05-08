package de.metas.handlingunits.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_HU_Attribute
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_HU_Attribute 
{

	String Table_Name = "M_HU_Attribute";

//	/** AD_Table_ID=540504 */
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

	ModelColumn<I_M_HU_Attribute, Object> COLUMN_Created = new ModelColumn<>(I_M_HU_Attribute.class, "Created", null);
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

	ModelColumn<I_M_HU_Attribute, Object> COLUMN_IsActive = new ModelColumn<>(I_M_HU_Attribute.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Merkmal.
	 * Produkt-Merkmal
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Attribute_ID (int M_Attribute_ID);

	/**
	 * Get Merkmal.
	 * Produkt-Merkmal
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Attribute_ID();

	String COLUMNNAME_M_Attribute_ID = "M_Attribute_ID";

	/**
	 * Set Handling Unit Attribute.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_HU_Attribute_ID (int M_HU_Attribute_ID);

	/**
	 * Get Handling Unit Attribute.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_HU_Attribute_ID();

	ModelColumn<I_M_HU_Attribute, Object> COLUMN_M_HU_Attribute_ID = new ModelColumn<>(I_M_HU_Attribute.class, "M_HU_Attribute_ID", null);
	String COLUMNNAME_M_HU_Attribute_ID = "M_HU_Attribute_ID";

	/**
	 * Set Handling Unit.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_HU_ID (int M_HU_ID);

	/**
	 * Get Handling Unit.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_HU_ID();

	de.metas.handlingunits.model.I_M_HU getM_HU();

	void setM_HU(de.metas.handlingunits.model.I_M_HU M_HU);

	ModelColumn<I_M_HU_Attribute, de.metas.handlingunits.model.I_M_HU> COLUMN_M_HU_ID = new ModelColumn<>(I_M_HU_Attribute.class, "M_HU_ID", de.metas.handlingunits.model.I_M_HU.class);
	String COLUMNNAME_M_HU_ID = "M_HU_ID";

	/**
	 * Set Handling Units Packing Instructions Attribute.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_HU_PI_Attribute_ID (int M_HU_PI_Attribute_ID);

	/**
	 * Get Handling Units Packing Instructions Attribute.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_HU_PI_Attribute_ID();

	String COLUMNNAME_M_HU_PI_Attribute_ID = "M_HU_PI_Attribute_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_HU_Attribute, Object> COLUMN_Updated = new ModelColumn<>(I_M_HU_Attribute.class, "Updated", null);
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
	 * Set Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setValue (@Nullable java.lang.String Value);

	/**
	 * Get Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getValue();

	ModelColumn<I_M_HU_Attribute, Object> COLUMN_Value = new ModelColumn<>(I_M_HU_Attribute.class, "Value", null);
	String COLUMNNAME_Value = "Value";

	/**
	 * Set Date Value.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setValueDate (@Nullable java.sql.Timestamp ValueDate);

	/**
	 * Get Date Value.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getValueDate();

	ModelColumn<I_M_HU_Attribute, Object> COLUMN_ValueDate = new ModelColumn<>(I_M_HU_Attribute.class, "ValueDate", null);
	String COLUMNNAME_ValueDate = "ValueDate";

	/**
	 * Set Datum (initial).
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setValueDateInitial (@Nullable java.sql.Timestamp ValueDateInitial);

	/**
	 * Get Datum (initial).
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getValueDateInitial();

	ModelColumn<I_M_HU_Attribute, Object> COLUMN_ValueDateInitial = new ModelColumn<>(I_M_HU_Attribute.class, "ValueDateInitial", null);
	String COLUMNNAME_ValueDateInitial = "ValueDateInitial";

	/**
	 * Set Merkmals-Wert (initial).
	 * Initial Value of the Attribute
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setValueInitial (@Nullable java.lang.String ValueInitial);

	/**
	 * Get Merkmals-Wert (initial).
	 * Initial Value of the Attribute
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getValueInitial();

	ModelColumn<I_M_HU_Attribute, Object> COLUMN_ValueInitial = new ModelColumn<>(I_M_HU_Attribute.class, "ValueInitial", null);
	String COLUMNNAME_ValueInitial = "ValueInitial";

	/**
	 * Set Numeric Value.
	 * Numeric Value
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setValueNumber (@Nullable BigDecimal ValueNumber);

	/**
	 * Get Numeric Value.
	 * Numeric Value
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getValueNumber();

	ModelColumn<I_M_HU_Attribute, Object> COLUMN_ValueNumber = new ModelColumn<>(I_M_HU_Attribute.class, "ValueNumber", null);
	String COLUMNNAME_ValueNumber = "ValueNumber";

	/**
	 * Set Zahlwert (initial).
	 * Initial Numeric Value
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setValueNumberInitial (@Nullable BigDecimal ValueNumberInitial);

	/**
	 * Get Zahlwert (initial).
	 * Initial Numeric Value
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getValueNumberInitial();

	ModelColumn<I_M_HU_Attribute, Object> COLUMN_ValueNumberInitial = new ModelColumn<>(I_M_HU_Attribute.class, "ValueNumberInitial", null);
	String COLUMNNAME_ValueNumberInitial = "ValueNumberInitial";
}
