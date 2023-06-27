package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for GPLR_Report_Line
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_GPLR_Report_Line 
{

	String Table_Name = "GPLR_Report_Line";

//	/** AD_Table_ID=542346 */
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
	 * Set Amount (FC).
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAmount_FC (@Nullable BigDecimal Amount_FC);

	/**
	 * Get Amount (FC).
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getAmount_FC();

	ModelColumn<I_GPLR_Report_Line, Object> COLUMN_Amount_FC = new ModelColumn<>(I_GPLR_Report_Line.class, "Amount_FC", null);
	String COLUMNNAME_Amount_FC = "Amount_FC";

	/**
	 * Set Amount (LC).
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAmount_LC (@Nullable BigDecimal Amount_LC);

	/**
	 * Get Amount (LC).
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getAmount_LC();

	ModelColumn<I_GPLR_Report_Line, Object> COLUMN_Amount_LC = new ModelColumn<>(I_GPLR_Report_Line.class, "Amount_LC", null);
	String COLUMNNAME_Amount_LC = "Amount_LC";

	/**
	 * Set Batch.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBatch (@Nullable java.lang.String Batch);

	/**
	 * Get Batch.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getBatch();

	ModelColumn<I_GPLR_Report_Line, Object> COLUMN_Batch = new ModelColumn<>(I_GPLR_Report_Line.class, "Batch", null);
	String COLUMNNAME_Batch = "Batch";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_GPLR_Report_Line, Object> COLUMN_Created = new ModelColumn<>(I_GPLR_Report_Line.class, "Created", null);
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
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get UOM.
	 * Unit of Measure
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_UOM_ID();

	String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Set Description.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_GPLR_Report_Line, Object> COLUMN_Description = new ModelColumn<>(I_GPLR_Report_Line.class, "Description", null);
	String COLUMNNAME_Description = "Description";

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

	ModelColumn<I_GPLR_Report_Line, Object> COLUMN_DocumentNo = new ModelColumn<>(I_GPLR_Report_Line.class, "DocumentNo", null);
	String COLUMNNAME_DocumentNo = "DocumentNo";

	/**
	 * Set Foreign Currency.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setForeignCurrencyCode (@Nullable java.lang.String ForeignCurrencyCode);

	/**
	 * Get Foreign Currency.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getForeignCurrencyCode();

	ModelColumn<I_GPLR_Report_Line, Object> COLUMN_ForeignCurrencyCode = new ModelColumn<>(I_GPLR_Report_Line.class, "ForeignCurrencyCode", null);
	String COLUMNNAME_ForeignCurrencyCode = "ForeignCurrencyCode";

	/**
	 * Set GPLR Report.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setGPLR_Report_ID (int GPLR_Report_ID);

	/**
	 * Get GPLR Report.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getGPLR_Report_ID();

	org.compiere.model.I_GPLR_Report getGPLR_Report();

	void setGPLR_Report(org.compiere.model.I_GPLR_Report GPLR_Report);

	ModelColumn<I_GPLR_Report_Line, org.compiere.model.I_GPLR_Report> COLUMN_GPLR_Report_ID = new ModelColumn<>(I_GPLR_Report_Line.class, "GPLR_Report_ID", org.compiere.model.I_GPLR_Report.class);
	String COLUMNNAME_GPLR_Report_ID = "GPLR_Report_ID";

	/**
	 * Set GPLR Report - Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setGPLR_Report_Line_ID (int GPLR_Report_Line_ID);

	/**
	 * Get GPLR Report - Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getGPLR_Report_Line_ID();

	ModelColumn<I_GPLR_Report_Line, Object> COLUMN_GPLR_Report_Line_ID = new ModelColumn<>(I_GPLR_Report_Line.class, "GPLR_Report_Line_ID", null);
	String COLUMNNAME_GPLR_Report_Line_ID = "GPLR_Report_Line_ID";

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

	ModelColumn<I_GPLR_Report_Line, Object> COLUMN_IsActive = new ModelColumn<>(I_GPLR_Report_Line.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set SeqNo..
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setLine (java.lang.String Line);

	/**
	 * Get SeqNo..
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getLine();

	ModelColumn<I_GPLR_Report_Line, Object> COLUMN_Line = new ModelColumn<>(I_GPLR_Report_Line.class, "Line", null);
	String COLUMNNAME_Line = "Line";

	/**
	 * Set Local Currency.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLocalCurrencyCode (@Nullable java.lang.String LocalCurrencyCode);

	/**
	 * Get Local Currency.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getLocalCurrencyCode();

	ModelColumn<I_GPLR_Report_Line, Object> COLUMN_LocalCurrencyCode = new ModelColumn<>(I_GPLR_Report_Line.class, "LocalCurrencyCode", null);
	String COLUMNNAME_LocalCurrencyCode = "LocalCurrencyCode";

	/**
	 * Set Price (FC).
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPrice_FC (@Nullable BigDecimal Price_FC);

	/**
	 * Get Price (FC).
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getPrice_FC();

	ModelColumn<I_GPLR_Report_Line, Object> COLUMN_Price_FC = new ModelColumn<>(I_GPLR_Report_Line.class, "Price_FC", null);
	String COLUMNNAME_Price_FC = "Price_FC";

	/**
	 * Set Quantity.
	 * Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQty (@Nullable BigDecimal Qty);

	/**
	 * Get Quantity.
	 * Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQty();

	ModelColumn<I_GPLR_Report_Line, Object> COLUMN_Qty = new ModelColumn<>(I_GPLR_Report_Line.class, "Qty", null);
	String COLUMNNAME_Qty = "Qty";

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

	ModelColumn<I_GPLR_Report_Line, Object> COLUMN_SeqNo = new ModelColumn<>(I_GPLR_Report_Line.class, "SeqNo", null);
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

	ModelColumn<I_GPLR_Report_Line, Object> COLUMN_Updated = new ModelColumn<>(I_GPLR_Report_Line.class, "Updated", null);
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
