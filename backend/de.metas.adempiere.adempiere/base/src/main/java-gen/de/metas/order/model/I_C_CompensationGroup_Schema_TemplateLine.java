package de.metas.order.model;

import org.adempiere.model.ModelColumn;

import java.math.BigDecimal;

/** Generated Interface for C_CompensationGroup_Schema_TemplateLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_CompensationGroup_Schema_TemplateLine 
{

	String Table_Name = "C_CompensationGroup_Schema_TemplateLine";

//	/** AD_Table_ID=541679 */
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
	 * Set Compensation Group Schema.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_CompensationGroup_Schema_ID (int C_CompensationGroup_Schema_ID);

	/**
	 * Get Compensation Group Schema.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_CompensationGroup_Schema_ID();

	de.metas.order.model.I_C_CompensationGroup_Schema getC_CompensationGroup_Schema();

	void setC_CompensationGroup_Schema(de.metas.order.model.I_C_CompensationGroup_Schema C_CompensationGroup_Schema);

	ModelColumn<I_C_CompensationGroup_Schema_TemplateLine, de.metas.order.model.I_C_CompensationGroup_Schema> COLUMN_C_CompensationGroup_Schema_ID = new ModelColumn<>(I_C_CompensationGroup_Schema_TemplateLine.class, "C_CompensationGroup_Schema_ID", de.metas.order.model.I_C_CompensationGroup_Schema.class);
	String COLUMNNAME_C_CompensationGroup_Schema_ID = "C_CompensationGroup_Schema_ID";

	/**
	 * Set Template Lines.
	 * Template lines are added automatically when using order batch entry with a product which is defined to trigger a compensation group creation
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_CompensationGroup_Schema_TemplateLine_ID (int C_CompensationGroup_Schema_TemplateLine_ID);

	/**
	 * Get Template Lines.
	 * Template lines are added automatically when using order batch entry with a product which is defined to trigger a compensation group creation
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_CompensationGroup_Schema_TemplateLine_ID();

	ModelColumn<I_C_CompensationGroup_Schema_TemplateLine, Object> COLUMN_C_CompensationGroup_Schema_TemplateLine_ID = new ModelColumn<>(I_C_CompensationGroup_Schema_TemplateLine.class, "C_CompensationGroup_Schema_TemplateLine_ID", null);
	String COLUMNNAME_C_CompensationGroup_Schema_TemplateLine_ID = "C_CompensationGroup_Schema_TemplateLine_ID";

	/**
	 * Set Contract Terms.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Flatrate_Conditions_ID (int C_Flatrate_Conditions_ID);

	/**
	 * Get Contract Terms.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Flatrate_Conditions_ID();

	ModelColumn<I_C_CompensationGroup_Schema_TemplateLine, Object> COLUMN_C_Flatrate_Conditions_ID = new ModelColumn<>(I_C_CompensationGroup_Schema_TemplateLine.class, "C_Flatrate_Conditions_ID", null);
	String COLUMNNAME_C_Flatrate_Conditions_ID = "C_Flatrate_Conditions_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_CompensationGroup_Schema_TemplateLine, Object> COLUMN_Created = new ModelColumn<>(I_C_CompensationGroup_Schema_TemplateLine.class, "Created", null);
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
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get UOM.
	 * Unit of Measure
	 *
	 * <br>Type: Search
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

	ModelColumn<I_C_CompensationGroup_Schema_TemplateLine, Object> COLUMN_IsActive = new ModelColumn<>(I_C_CompensationGroup_Schema_TemplateLine.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Separate invoicing.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAllowSeparateInvoicing (boolean IsAllowSeparateInvoicing);

	/**
	 * Get Separate invoicing.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAllowSeparateInvoicing();

	ModelColumn<I_C_CompensationGroup_Schema_TemplateLine, Object> COLUMN_IsAllowSeparateInvoicing = new ModelColumn<>(I_C_CompensationGroup_Schema_TemplateLine.class, "IsAllowSeparateInvoicing", null);
	String COLUMNNAME_IsAllowSeparateInvoicing = "IsAllowSeparateInvoicing";

	/**
	 * Set Hide when printing.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsHideWhenPrinting (boolean IsHideWhenPrinting);

	/**
	 * Get Hide when printing.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isHideWhenPrinting();

	ModelColumn<I_C_CompensationGroup_Schema_TemplateLine, Object> COLUMN_IsHideWhenPrinting = new ModelColumn<>(I_C_CompensationGroup_Schema_TemplateLine.class, "IsHideWhenPrinting", null);
	String COLUMNNAME_IsHideWhenPrinting = "IsHideWhenPrinting";

	/**
	 * Set Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Product_ID();

	String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Quantity.
	 * Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQty (BigDecimal Qty);

	/**
	 * Get Quantity.
	 * Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQty();

	ModelColumn<I_C_CompensationGroup_Schema_TemplateLine, Object> COLUMN_Qty = new ModelColumn<>(I_C_CompensationGroup_Schema_TemplateLine.class, "Qty", null);
	String COLUMNNAME_Qty = "Qty";

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

	ModelColumn<I_C_CompensationGroup_Schema_TemplateLine, Object> COLUMN_SeqNo = new ModelColumn<>(I_C_CompensationGroup_Schema_TemplateLine.class, "SeqNo", null);
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

	ModelColumn<I_C_CompensationGroup_Schema_TemplateLine, Object> COLUMN_Updated = new ModelColumn<>(I_C_CompensationGroup_Schema_TemplateLine.class, "Updated", null);
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
