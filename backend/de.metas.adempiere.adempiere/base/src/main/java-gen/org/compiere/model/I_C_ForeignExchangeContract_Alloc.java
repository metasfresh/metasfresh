package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for C_ForeignExchangeContract_Alloc
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_ForeignExchangeContract_Alloc 
{

	String Table_Name = "C_ForeignExchangeContract_Alloc";

//	/** AD_Table_ID=542283 */
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
	 * Set Allocated Amount.
	 * Amount allocated to this document
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAllocatedAmt (BigDecimal AllocatedAmt);

	/**
	 * Get Allocated Amount.
	 * Amount allocated to this document
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getAllocatedAmt();

	ModelColumn<I_C_ForeignExchangeContract_Alloc, Object> COLUMN_AllocatedAmt = new ModelColumn<>(I_C_ForeignExchangeContract_Alloc.class, "AllocatedAmt", null);
	String COLUMNNAME_AllocatedAmt = "AllocatedAmt";

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
	 * Set Foreign Exchange Contract Allocation.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_ForeignExchangeContract_Alloc_ID (int C_ForeignExchangeContract_Alloc_ID);

	/**
	 * Get Foreign Exchange Contract Allocation.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_ForeignExchangeContract_Alloc_ID();

	ModelColumn<I_C_ForeignExchangeContract_Alloc, Object> COLUMN_C_ForeignExchangeContract_Alloc_ID = new ModelColumn<>(I_C_ForeignExchangeContract_Alloc.class, "C_ForeignExchangeContract_Alloc_ID", null);
	String COLUMNNAME_C_ForeignExchangeContract_Alloc_ID = "C_ForeignExchangeContract_Alloc_ID";

	/**
	 * Set Foreign Exchange Contract.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_ForeignExchangeContract_ID (int C_ForeignExchangeContract_ID);

	/**
	 * Get Foreign Exchange Contract.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_ForeignExchangeContract_ID();

	org.compiere.model.I_C_ForeignExchangeContract getC_ForeignExchangeContract();

	void setC_ForeignExchangeContract(org.compiere.model.I_C_ForeignExchangeContract C_ForeignExchangeContract);

	ModelColumn<I_C_ForeignExchangeContract_Alloc, org.compiere.model.I_C_ForeignExchangeContract> COLUMN_C_ForeignExchangeContract_ID = new ModelColumn<>(I_C_ForeignExchangeContract_Alloc.class, "C_ForeignExchangeContract_ID", org.compiere.model.I_C_ForeignExchangeContract.class);
	String COLUMNNAME_C_ForeignExchangeContract_ID = "C_ForeignExchangeContract_ID";

	/**
	 * Set Sales order.
	 * Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Order_ID (int C_Order_ID);

	/**
	 * Get Sales order.
	 * Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Order_ID();

	org.compiere.model.I_C_Order getC_Order();

	void setC_Order(org.compiere.model.I_C_Order C_Order);

	ModelColumn<I_C_ForeignExchangeContract_Alloc, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new ModelColumn<>(I_C_ForeignExchangeContract_Alloc.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
	String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_ForeignExchangeContract_Alloc, Object> COLUMN_Created = new ModelColumn<>(I_C_ForeignExchangeContract_Alloc.class, "Created", null);
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
	 * Set Grand Total.
	 * Total amount of document
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setGrandTotal (BigDecimal GrandTotal);

	/**
	 * Get Grand Total.
	 * Total amount of document
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getGrandTotal();

	ModelColumn<I_C_ForeignExchangeContract_Alloc, Object> COLUMN_GrandTotal = new ModelColumn<>(I_C_ForeignExchangeContract_Alloc.class, "GrandTotal", null);
	String COLUMNNAME_GrandTotal = "GrandTotal";

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

	ModelColumn<I_C_ForeignExchangeContract_Alloc, Object> COLUMN_IsActive = new ModelColumn<>(I_C_ForeignExchangeContract_Alloc.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Section Code.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_SectionCode_ID (int M_SectionCode_ID);

	/**
	 * Get Section Code.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_SectionCode_ID();

	@Nullable org.compiere.model.I_M_SectionCode getM_SectionCode();

	void setM_SectionCode(@Nullable org.compiere.model.I_M_SectionCode M_SectionCode);

	ModelColumn<I_C_ForeignExchangeContract_Alloc, org.compiere.model.I_M_SectionCode> COLUMN_M_SectionCode_ID = new ModelColumn<>(I_C_ForeignExchangeContract_Alloc.class, "M_SectionCode_ID", org.compiere.model.I_M_SectionCode.class);
	String COLUMNNAME_M_SectionCode_ID = "M_SectionCode_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_ForeignExchangeContract_Alloc, Object> COLUMN_Updated = new ModelColumn<>(I_C_ForeignExchangeContract_Alloc.class, "Updated", null);
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
