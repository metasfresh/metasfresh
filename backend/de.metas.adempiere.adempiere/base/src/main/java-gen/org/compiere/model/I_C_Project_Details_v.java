package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_Project_Details_v
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Project_Details_v 
{

	String Table_Name = "C_Project_Details_v";

//	/** AD_Table_ID=619 */
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
	 * Set Language.
	 * Language for this entity
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Language (@Nullable java.lang.String AD_Language);

	/**
	 * Get Language.
	 * Language for this entity
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAD_Language();

	ModelColumn<I_C_Project_Details_v, Object> COLUMN_AD_Language = new ModelColumn<>(I_C_Project_Details_v.class, "AD_Language", null);
	String COLUMNNAME_AD_Language = "AD_Language";

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
	 * Set Project.
	 * Financial Project
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Project_ID (int C_Project_ID);

	/**
	 * Get Project.
	 * Financial Project
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Project_ID();

	String COLUMNNAME_C_Project_ID = "C_Project_ID";

	/**
	 * Set Project Line.
	 * Task or step in a project
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_ProjectLine_ID (int C_ProjectLine_ID);

	/**
	 * Get Project Line.
	 * Task or step in a project
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_ProjectLine_ID();

	ModelColumn<I_C_Project_Details_v, org.compiere.model.I_C_ProjectLine> COLUMN_C_ProjectLine_ID = new ModelColumn<>(I_C_Project_Details_v.class, "C_ProjectLine_ID", org.compiere.model.I_C_ProjectLine.class);
	String COLUMNNAME_C_ProjectLine_ID = "C_ProjectLine_ID";

	/**
	 * Set Committed Amount.
	 * The (legal) commitment amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCommittedAmt (@Nullable BigDecimal CommittedAmt);

	/**
	 * Get Committed Amount.
	 * The (legal) commitment amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getCommittedAmt();

	ModelColumn<I_C_Project_Details_v, Object> COLUMN_CommittedAmt = new ModelColumn<>(I_C_Project_Details_v.class, "CommittedAmt", null);
	String COLUMNNAME_CommittedAmt = "CommittedAmt";

	/**
	 * Set Committed Quantity.
	 * The (legal) commitment Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCommittedQty (@Nullable BigDecimal CommittedQty);

	/**
	 * Get Committed Quantity.
	 * The (legal) commitment Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getCommittedQty();

	ModelColumn<I_C_Project_Details_v, Object> COLUMN_CommittedQty = new ModelColumn<>(I_C_Project_Details_v.class, "CommittedQty", null);
	String COLUMNNAME_CommittedQty = "CommittedQty";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_Project_Details_v, Object> COLUMN_Created = new ModelColumn<>(I_C_Project_Details_v.class, "Created", null);
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

	ModelColumn<I_C_Project_Details_v, Object> COLUMN_Description = new ModelColumn<>(I_C_Project_Details_v.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Notiz / Zeilentext.
	 * Additional information for a Document
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDocumentNote (@Nullable java.lang.String DocumentNote);

	/**
	 * Get Notiz / Zeilentext.
	 * Additional information for a Document
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDocumentNote();

	ModelColumn<I_C_Project_Details_v, Object> COLUMN_DocumentNote = new ModelColumn<>(I_C_Project_Details_v.class, "DocumentNote", null);
	String COLUMNNAME_DocumentNote = "DocumentNote";

	/**
	 * Set Invoiced Amount.
	 * The amount invoiced
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setInvoicedAmt (BigDecimal InvoicedAmt);

	/**
	 * Get Invoiced Amount.
	 * The amount invoiced
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getInvoicedAmt();

	ModelColumn<I_C_Project_Details_v, Object> COLUMN_InvoicedAmt = new ModelColumn<>(I_C_Project_Details_v.class, "InvoicedAmt", null);
	String COLUMNNAME_InvoicedAmt = "InvoicedAmt";

	/**
	 * Set Quantity Invoiced .
	 * The quantity invoiced
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setInvoicedQty (BigDecimal InvoicedQty);

	/**
	 * Get Quantity Invoiced .
	 * The quantity invoiced
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getInvoicedQty();

	ModelColumn<I_C_Project_Details_v, Object> COLUMN_InvoicedQty = new ModelColumn<>(I_C_Project_Details_v.class, "InvoicedQty", null);
	String COLUMNNAME_InvoicedQty = "InvoicedQty";

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

	ModelColumn<I_C_Project_Details_v, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Project_Details_v.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set SeqNo..
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setLine (int Line);

	/**
	 * Get SeqNo..
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getLine();

	ModelColumn<I_C_Project_Details_v, Object> COLUMN_Line = new ModelColumn<>(I_C_Project_Details_v.class, "Line", null);
	String COLUMNNAME_Line = "Line";

	/**
	 * Set Product Category.
	 * Category of a Product
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Product_Category_ID (int M_Product_Category_ID);

	/**
	 * Get Product Category.
	 * Category of a Product
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Product_Category_ID();

	String COLUMNNAME_M_Product_Category_ID = "M_Product_Category_ID";

	/**
	 * Set Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Product_ID();

	String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setName (@Nullable java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getName();

	ModelColumn<I_C_Project_Details_v, Object> COLUMN_Name = new ModelColumn<>(I_C_Project_Details_v.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set VK Total.
	 * Planned amount for this project
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPlannedAmt (BigDecimal PlannedAmt);

	/**
	 * Get VK Total.
	 * Planned amount for this project
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getPlannedAmt();

	ModelColumn<I_C_Project_Details_v, Object> COLUMN_PlannedAmt = new ModelColumn<>(I_C_Project_Details_v.class, "PlannedAmt", null);
	String COLUMNNAME_PlannedAmt = "PlannedAmt";

	/**
	 * Set DB1.
	 * Project's planned margin amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPlannedMarginAmt (BigDecimal PlannedMarginAmt);

	/**
	 * Get DB1.
	 * Project's planned margin amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getPlannedMarginAmt();

	ModelColumn<I_C_Project_Details_v, Object> COLUMN_PlannedMarginAmt = new ModelColumn<>(I_C_Project_Details_v.class, "PlannedMarginAmt", null);
	String COLUMNNAME_PlannedMarginAmt = "PlannedMarginAmt";

	/**
	 * Set Planned Price.
	 * Planned price for this project line
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPlannedPrice (BigDecimal PlannedPrice);

	/**
	 * Get Planned Price.
	 * Planned price for this project line
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getPlannedPrice();

	ModelColumn<I_C_Project_Details_v, Object> COLUMN_PlannedPrice = new ModelColumn<>(I_C_Project_Details_v.class, "PlannedPrice", null);
	String COLUMNNAME_PlannedPrice = "PlannedPrice";

	/**
	 * Set Menge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPlannedQty (BigDecimal PlannedQty);

	/**
	 * Get Menge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getPlannedQty();

	ModelColumn<I_C_Project_Details_v, Object> COLUMN_PlannedQty = new ModelColumn<>(I_C_Project_Details_v.class, "PlannedQty", null);
	String COLUMNNAME_PlannedQty = "PlannedQty";

	/**
	 * Set Product Value.
	 * Product identifier;
 "val-<search key>", "ext-<external id>" or internal M_Product_ID
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProductValue (@Nullable java.lang.String ProductValue);

	/**
	 * Get Product Value.
	 * Product identifier;
 "val-<search key>", "ext-<external id>" or internal M_Product_ID
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getProductValue();

	ModelColumn<I_C_Project_Details_v, Object> COLUMN_ProductValue = new ModelColumn<>(I_C_Project_Details_v.class, "ProductValue", null);
	String COLUMNNAME_ProductValue = "ProductValue";

	/**
	 * Set SKU.
	 * Stock Keeping Unit
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSKU (@Nullable java.lang.String SKU);

	/**
	 * Get SKU.
	 * Stock Keeping Unit
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getSKU();

	ModelColumn<I_C_Project_Details_v, Object> COLUMN_SKU = new ModelColumn<>(I_C_Project_Details_v.class, "SKU", null);
	String COLUMNNAME_SKU = "SKU";

	/**
	 * Set UPC.
	 * Bar Code (Universal Product Code or its superset European Article Number)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUPC (@Nullable java.lang.String UPC);

	/**
	 * Get UPC.
	 * Bar Code (Universal Product Code or its superset European Article Number)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUPC();

	ModelColumn<I_C_Project_Details_v, Object> COLUMN_UPC = new ModelColumn<>(I_C_Project_Details_v.class, "UPC", null);
	String COLUMNNAME_UPC = "UPC";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_Project_Details_v, Object> COLUMN_Updated = new ModelColumn<>(I_C_Project_Details_v.class, "Updated", null);
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
