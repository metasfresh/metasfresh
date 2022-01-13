package de.metas.fresh.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for Fresh_QtyOnHand_Line
 *  @author metasfresh (generated)
 */
@SuppressWarnings("unused")
public interface I_Fresh_QtyOnHand_Line
{

	String Table_Name = "Fresh_QtyOnHand_Line";

	//	/** AD_Table_ID=540635 */
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
	 * Set ASI Key.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setASIKey (@Nullable java.lang.String ASIKey);

	/**
	 * Get ASI Key.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getASIKey();

	ModelColumn<I_Fresh_QtyOnHand_Line, Object> COLUMN_ASIKey = new ModelColumn<>(I_Fresh_QtyOnHand_Line.class, "ASIKey", null);
	String COLUMNNAME_ASIKey = "ASIKey";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_Fresh_QtyOnHand_Line, Object> COLUMN_Created = new ModelColumn<>(I_Fresh_QtyOnHand_Line.class, "Created", null);
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
	 * Set Document Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDateDoc (java.sql.Timestamp DateDoc);

	/**
	 * Get Document Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getDateDoc();

	ModelColumn<I_Fresh_QtyOnHand_Line, Object> COLUMN_DateDoc = new ModelColumn<>(I_Fresh_QtyOnHand_Line.class, "DateDoc", null);
	String COLUMNNAME_DateDoc = "DateDoc";

	/**
	 * Set Stock Control Purchase.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setFresh_QtyOnHand_ID (int Fresh_QtyOnHand_ID);

	/**
	 * Get Stock Control Purchase.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getFresh_QtyOnHand_ID();

	de.metas.fresh.model.I_Fresh_QtyOnHand getFresh_QtyOnHand();

	void setFresh_QtyOnHand(de.metas.fresh.model.I_Fresh_QtyOnHand Fresh_QtyOnHand);

	ModelColumn<I_Fresh_QtyOnHand_Line, de.metas.fresh.model.I_Fresh_QtyOnHand> COLUMN_Fresh_QtyOnHand_ID = new ModelColumn<>(I_Fresh_QtyOnHand_Line.class, "Fresh_QtyOnHand_ID", de.metas.fresh.model.I_Fresh_QtyOnHand.class);
	String COLUMNNAME_Fresh_QtyOnHand_ID = "Fresh_QtyOnHand_ID";

	/**
	 * Set Stock Control Purchase Record.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setFresh_QtyOnHand_Line_ID (int Fresh_QtyOnHand_Line_ID);

	/**
	 * Get Stock Control Purchase Record.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getFresh_QtyOnHand_Line_ID();

	ModelColumn<I_Fresh_QtyOnHand_Line, Object> COLUMN_Fresh_QtyOnHand_Line_ID = new ModelColumn<>(I_Fresh_QtyOnHand_Line.class, "Fresh_QtyOnHand_Line_ID", null);
	String COLUMNNAME_Fresh_QtyOnHand_Line_ID = "Fresh_QtyOnHand_Line_ID";

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

	ModelColumn<I_Fresh_QtyOnHand_Line, Object> COLUMN_IsActive = new ModelColumn<>(I_Fresh_QtyOnHand_Line.class, "IsActive", null);
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

	ModelColumn<I_Fresh_QtyOnHand_Line, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new ModelColumn<>(I_Fresh_QtyOnHand_Line.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
	String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

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
	 * Set Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Warehouse_ID (int M_Warehouse_ID);

	/**
	 * Get Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Warehouse_ID();

	String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/**
	 * Set Produktionsstätte.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPP_Plant_ID (int PP_Plant_ID);

	/**
	 * Get Produktionsstätte.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPP_Plant_ID();

	org.compiere.model.I_S_Resource getPP_Plant();

	void setPP_Plant(org.compiere.model.I_S_Resource PP_Plant);

	ModelColumn<I_Fresh_QtyOnHand_Line, org.compiere.model.I_S_Resource> COLUMN_PP_Plant_ID = new ModelColumn<>(I_Fresh_QtyOnHand_Line.class, "PP_Plant_ID", org.compiere.model.I_S_Resource.class);
	String COLUMNNAME_PP_Plant_ID = "PP_Plant_ID";

	/**
	 * Set Produktgruppe.
	 * This SQL-column is supposed to be used by the code, so pls don't set Lazy loading to true!
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setProductGroup (@Nullable java.lang.String ProductGroup);

	/**
	 * Get Produktgruppe.
	 * This SQL-column is supposed to be used by the code, so pls don't set Lazy loading to true!
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	@Nullable java.lang.String getProductGroup();

	ModelColumn<I_Fresh_QtyOnHand_Line, Object> COLUMN_ProductGroup = new ModelColumn<>(I_Fresh_QtyOnHand_Line.class, "ProductGroup", null);
	String COLUMNNAME_ProductGroup = "ProductGroup";

	/**
	 * Set Produktname.
	 * This SQL-column is supposed to be used by the code, so pls don't set Lazy loading to true!
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setProductName (@Nullable java.lang.String ProductName);

	/**
	 * Get Produktname.
	 * This SQL-column is supposed to be used by the code, so pls don't set Lazy loading to true!
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	@Nullable java.lang.String getProductName();

	ModelColumn<I_Fresh_QtyOnHand_Line, Object> COLUMN_ProductName = new ModelColumn<>(I_Fresh_QtyOnHand_Line.class, "ProductName", null);
	String COLUMNNAME_ProductName = "ProductName";

	/**
	 * Set Qty Count.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyCount (BigDecimal QtyCount);

	/**
	 * Get Qty Count.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyCount();

	ModelColumn<I_Fresh_QtyOnHand_Line, Object> COLUMN_QtyCount = new ModelColumn<>(I_Fresh_QtyOnHand_Line.class, "QtyCount", null);
	String COLUMNNAME_QtyCount = "QtyCount";

	/**
	 * Set SeqNo.
	 * Method of ordering records;
	 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSeqNo (int SeqNo);

	/**
	 * Get SeqNo.
	 * Method of ordering records;
	 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getSeqNo();

	ModelColumn<I_Fresh_QtyOnHand_Line, Object> COLUMN_SeqNo = new ModelColumn<>(I_Fresh_QtyOnHand_Line.class, "SeqNo", null);
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

	ModelColumn<I_Fresh_QtyOnHand_Line, Object> COLUMN_Updated = new ModelColumn<>(I_Fresh_QtyOnHand_Line.class, "Updated", null);
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
