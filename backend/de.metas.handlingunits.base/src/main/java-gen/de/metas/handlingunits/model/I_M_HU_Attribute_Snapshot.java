package de.metas.handlingunits.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_HU_Attribute_Snapshot
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_HU_Attribute_Snapshot 
{

	String Table_Name = "M_HU_Attribute_Snapshot";

//	/** AD_Table_ID=540671 */
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

	ModelColumn<I_M_HU_Attribute_Snapshot, Object> COLUMN_Created = new ModelColumn<>(I_M_HU_Attribute_Snapshot.class, "Created", null);
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

	ModelColumn<I_M_HU_Attribute_Snapshot, Object> COLUMN_IsActive = new ModelColumn<>(I_M_HU_Attribute_Snapshot.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Merkmal.
	 * Produkt-Merkmal
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Attribute_ID (int M_Attribute_ID);

	/**
	 * Get Merkmal.
	 * Produkt-Merkmal
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Attribute_ID();

	String COLUMNNAME_M_Attribute_ID = "M_Attribute_ID";

	/**
	 * Set Handling Unit Attribute.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_HU_Attribute_ID (int M_HU_Attribute_ID);

	/**
	 * Get Handling Unit Attribute.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_HU_Attribute_ID();

	@Nullable de.metas.handlingunits.model.I_M_HU_Attribute getM_HU_Attribute();

	void setM_HU_Attribute(@Nullable de.metas.handlingunits.model.I_M_HU_Attribute M_HU_Attribute);

	ModelColumn<I_M_HU_Attribute_Snapshot, de.metas.handlingunits.model.I_M_HU_Attribute> COLUMN_M_HU_Attribute_ID = new ModelColumn<>(I_M_HU_Attribute_Snapshot.class, "M_HU_Attribute_ID", de.metas.handlingunits.model.I_M_HU_Attribute.class);
	String COLUMNNAME_M_HU_Attribute_ID = "M_HU_Attribute_ID";

	/**
	 * Set Handling Units Attribute.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_HU_Attribute_Snapshot_ID (int M_HU_Attribute_Snapshot_ID);

	/**
	 * Get Handling Units Attribute.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_HU_Attribute_Snapshot_ID();

	ModelColumn<I_M_HU_Attribute_Snapshot, Object> COLUMN_M_HU_Attribute_Snapshot_ID = new ModelColumn<>(I_M_HU_Attribute_Snapshot.class, "M_HU_Attribute_Snapshot_ID", null);
	String COLUMNNAME_M_HU_Attribute_Snapshot_ID = "M_HU_Attribute_Snapshot_ID";

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

	ModelColumn<I_M_HU_Attribute_Snapshot, de.metas.handlingunits.model.I_M_HU> COLUMN_M_HU_ID = new ModelColumn<>(I_M_HU_Attribute_Snapshot.class, "M_HU_ID", de.metas.handlingunits.model.I_M_HU.class);
	String COLUMNNAME_M_HU_ID = "M_HU_ID";

	/**
	 * Set Handling Units Packing Instructions Attribute.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_HU_PI_Attribute_ID (int M_HU_PI_Attribute_ID);

	/**
	 * Get Handling Units Packing Instructions Attribute.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_HU_PI_Attribute_ID();

	String COLUMNNAME_M_HU_PI_Attribute_ID = "M_HU_PI_Attribute_ID";

	/**
	 * Set Snapshot UUID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSnapshot_UUID (java.lang.String Snapshot_UUID);

	/**
	 * Get Snapshot UUID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getSnapshot_UUID();

	ModelColumn<I_M_HU_Attribute_Snapshot, Object> COLUMN_Snapshot_UUID = new ModelColumn<>(I_M_HU_Attribute_Snapshot.class, "Snapshot_UUID", null);
	String COLUMNNAME_Snapshot_UUID = "Snapshot_UUID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_HU_Attribute_Snapshot, Object> COLUMN_Updated = new ModelColumn<>(I_M_HU_Attribute_Snapshot.class, "Updated", null);
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

	ModelColumn<I_M_HU_Attribute_Snapshot, Object> COLUMN_Value = new ModelColumn<>(I_M_HU_Attribute_Snapshot.class, "Value", null);
	String COLUMNNAME_Value = "Value";

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

	ModelColumn<I_M_HU_Attribute_Snapshot, Object> COLUMN_ValueInitial = new ModelColumn<>(I_M_HU_Attribute_Snapshot.class, "ValueInitial", null);
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

	ModelColumn<I_M_HU_Attribute_Snapshot, Object> COLUMN_ValueNumber = new ModelColumn<>(I_M_HU_Attribute_Snapshot.class, "ValueNumber", null);
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

	ModelColumn<I_M_HU_Attribute_Snapshot, Object> COLUMN_ValueNumberInitial = new ModelColumn<>(I_M_HU_Attribute_Snapshot.class, "ValueNumberInitial", null);
	String COLUMNNAME_ValueNumberInitial = "ValueNumberInitial";
}
