package de.metas.handlingunits.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_HU_Instance_Properties_v
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_HU_Instance_Properties_v 
{

	String Table_Name = "M_HU_Instance_Properties_v";

//	/** AD_Table_ID=540538 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Attribute Name.
	 * Name of the Attribute
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAttributeName (@Nullable java.lang.String AttributeName);

	/**
	 * Get Attribute Name.
	 * Name of the Attribute
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAttributeName();

	ModelColumn<I_M_HU_Instance_Properties_v, Object> COLUMN_AttributeName = new ModelColumn<>(I_M_HU_Instance_Properties_v.class, "AttributeName", null);
	String COLUMNNAME_AttributeName = "AttributeName";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getCreated();

	ModelColumn<I_M_HU_Instance_Properties_v, Object> COLUMN_Created = new ModelColumn<>(I_M_HU_Instance_Properties_v.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Document Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDocumentName (@Nullable java.lang.String DocumentName);

	/**
	 * Get Document Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDocumentName();

	ModelColumn<I_M_HU_Instance_Properties_v, Object> COLUMN_DocumentName = new ModelColumn<>(I_M_HU_Instance_Properties_v.class, "DocumentName", null);
	String COLUMNNAME_DocumentName = "DocumentName";

	/**
	 * Set Document No.
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDocumentNo (@Nullable java.lang.String DocumentNo);

	/**
	 * Get Document No.
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDocumentNo();

	ModelColumn<I_M_HU_Instance_Properties_v, Object> COLUMN_DocumentNo = new ModelColumn<>(I_M_HU_Instance_Properties_v.class, "DocumentNo", null);
	String COLUMNNAME_DocumentNo = "DocumentNo";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isActive();

	ModelColumn<I_M_HU_Instance_Properties_v, Object> COLUMN_IsActive = new ModelColumn<>(I_M_HU_Instance_Properties_v.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Merkmal.
	 * Produkt-Merkmal
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Attribute_ID (int M_Attribute_ID);

	/**
	 * Get Merkmal.
	 * Produkt-Merkmal
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Attribute_ID();

	String COLUMNNAME_M_Attribute_ID = "M_Attribute_ID";

	/**
	 * Set Handling Unit.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_HU_ID (int M_HU_ID);

	/**
	 * Get Handling Unit.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_HU_ID();

	ModelColumn<I_M_HU_Instance_Properties_v, de.metas.handlingunits.model.I_M_HU> COLUMN_M_HU_ID = new ModelColumn<>(I_M_HU_Instance_Properties_v.class, "M_HU_ID", de.metas.handlingunits.model.I_M_HU.class);
	String COLUMNNAME_M_HU_ID = "M_HU_ID";

	/**
	 * Set HU PI Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPIName (@Nullable java.lang.String PIName);

	/**
	 * Get HU PI Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPIName();

	ModelColumn<I_M_HU_Instance_Properties_v, Object> COLUMN_PIName = new ModelColumn<>(I_M_HU_Instance_Properties_v.class, "PIName", null);
	String COLUMNNAME_PIName = "PIName";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getUpdated();

	ModelColumn<I_M_HU_Instance_Properties_v, Object> COLUMN_Updated = new ModelColumn<>(I_M_HU_Instance_Properties_v.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
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

	ModelColumn<I_M_HU_Instance_Properties_v, Object> COLUMN_Value = new ModelColumn<>(I_M_HU_Instance_Properties_v.class, "Value", null);
	String COLUMNNAME_Value = "Value";

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

	ModelColumn<I_M_HU_Instance_Properties_v, Object> COLUMN_ValueNumber = new ModelColumn<>(I_M_HU_Instance_Properties_v.class, "ValueNumber", null);
	String COLUMNNAME_ValueNumber = "ValueNumber";
}
