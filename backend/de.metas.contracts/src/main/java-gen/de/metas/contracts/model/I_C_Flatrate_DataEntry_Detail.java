package de.metas.contracts.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for C_Flatrate_DataEntry_Detail
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Flatrate_DataEntry_Detail 
{

	String Table_Name = "C_Flatrate_DataEntry_Detail";

//	/** AD_Table_ID=542411 */
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
	 * Set BPartner-Department.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Department_ID (int C_BPartner_Department_ID);

	/**
	 * Get BPartner-Department.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_Department_ID();

	@Nullable org.compiere.model.I_C_BPartner_Department getC_BPartner_Department();

	void setC_BPartner_Department(@Nullable org.compiere.model.I_C_BPartner_Department C_BPartner_Department);

	ModelColumn<I_C_Flatrate_DataEntry_Detail, org.compiere.model.I_C_BPartner_Department> COLUMN_C_BPartner_Department_ID = new ModelColumn<>(I_C_Flatrate_DataEntry_Detail.class, "C_BPartner_Department_ID", org.compiere.model.I_C_BPartner_Department.class);
	String COLUMNNAME_C_BPartner_Department_ID = "C_BPartner_Department_ID";

	/**
	 * Set C_Flatrate_DataEntry_Detail.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Flatrate_DataEntry_Detail_ID (int C_Flatrate_DataEntry_Detail_ID);

	/**
	 * Get C_Flatrate_DataEntry_Detail.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Flatrate_DataEntry_Detail_ID();

	ModelColumn<I_C_Flatrate_DataEntry_Detail, Object> COLUMN_C_Flatrate_DataEntry_Detail_ID = new ModelColumn<>(I_C_Flatrate_DataEntry_Detail.class, "C_Flatrate_DataEntry_Detail_ID", null);
	String COLUMNNAME_C_Flatrate_DataEntry_Detail_ID = "C_Flatrate_DataEntry_Detail_ID";

	/**
	 * Set Billing Record.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Flatrate_DataEntry_ID (int C_Flatrate_DataEntry_ID);

	/**
	 * Get Billing Record.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Flatrate_DataEntry_ID();

	de.metas.contracts.model.I_C_Flatrate_DataEntry getC_Flatrate_DataEntry();

	void setC_Flatrate_DataEntry(de.metas.contracts.model.I_C_Flatrate_DataEntry C_Flatrate_DataEntry);

	ModelColumn<I_C_Flatrate_DataEntry_Detail, de.metas.contracts.model.I_C_Flatrate_DataEntry> COLUMN_C_Flatrate_DataEntry_ID = new ModelColumn<>(I_C_Flatrate_DataEntry_Detail.class, "C_Flatrate_DataEntry_ID", de.metas.contracts.model.I_C_Flatrate_DataEntry.class);
	String COLUMNNAME_C_Flatrate_DataEntry_ID = "C_Flatrate_DataEntry_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_Flatrate_DataEntry_Detail, Object> COLUMN_Created = new ModelColumn<>(I_C_Flatrate_DataEntry_Detail.class, "Created", null);
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
	 * Set UOM.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get UOM.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_UOM_ID();

	String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

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

	ModelColumn<I_C_Flatrate_DataEntry_Detail, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Flatrate_DataEntry_Detail.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Attributes.
	 * Attribute Instances for Products
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID);

	/**
	 * Get Attributes.
	 * Attribute Instances for Products
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_AttributeSetInstance_ID();

	@Nullable org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance();

	void setM_AttributeSetInstance(@Nullable org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance);

	ModelColumn<I_C_Flatrate_DataEntry_Detail, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new ModelColumn<>(I_C_Flatrate_DataEntry_Detail.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
	String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/**
	 * Set Qty reported.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQty_Reported (@Nullable BigDecimal Qty_Reported);

	/**
	 * Get Qty reported.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQty_Reported();

	ModelColumn<I_C_Flatrate_DataEntry_Detail, Object> COLUMN_Qty_Reported = new ModelColumn<>(I_C_Flatrate_DataEntry_Detail.class, "Qty_Reported", null);
	String COLUMNNAME_Qty_Reported = "Qty_Reported";

	/**
	 * Set SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSeqNo (int SeqNo);

	/**
	 * Get SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getSeqNo();

	ModelColumn<I_C_Flatrate_DataEntry_Detail, Object> COLUMN_SeqNo = new ModelColumn<>(I_C_Flatrate_DataEntry_Detail.class, "SeqNo", null);
	String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_Flatrate_DataEntry_Detail, Object> COLUMN_Updated = new ModelColumn<>(I_C_Flatrate_DataEntry_Detail.class, "Updated", null);
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
