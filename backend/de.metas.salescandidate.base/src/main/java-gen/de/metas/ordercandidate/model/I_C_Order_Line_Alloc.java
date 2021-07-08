package de.metas.ordercandidate.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_Order_Line_Alloc
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Order_Line_Alloc 
{

	String Table_Name = "C_Order_Line_Alloc";

//	/** AD_Table_ID=540417 */
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
	 * Set Orderline Candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_OLCand_ID (int C_OLCand_ID);

	/**
	 * Get Orderline Candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_OLCand_ID();

	de.metas.ordercandidate.model.I_C_OLCand getC_OLCand();

	void setC_OLCand(de.metas.ordercandidate.model.I_C_OLCand C_OLCand);

	ModelColumn<I_C_Order_Line_Alloc, de.metas.ordercandidate.model.I_C_OLCand> COLUMN_C_OLCand_ID = new ModelColumn<>(I_C_Order_Line_Alloc.class, "C_OLCand_ID", de.metas.ordercandidate.model.I_C_OLCand.class);
	String COLUMNNAME_C_OLCand_ID = "C_OLCand_ID";

	/**
	 * Set Auftragskand. Verarb..
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_OLCandProcessor_ID (int C_OLCandProcessor_ID);

	/**
	 * Get Auftragskand. Verarb..
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_OLCandProcessor_ID();

	@Nullable de.metas.ordercandidate.model.I_C_OLCandProcessor getC_OLCandProcessor();

	void setC_OLCandProcessor(@Nullable de.metas.ordercandidate.model.I_C_OLCandProcessor C_OLCandProcessor);

	ModelColumn<I_C_Order_Line_Alloc, de.metas.ordercandidate.model.I_C_OLCandProcessor> COLUMN_C_OLCandProcessor_ID = new ModelColumn<>(I_C_Order_Line_Alloc.class, "C_OLCandProcessor_ID", de.metas.ordercandidate.model.I_C_OLCandProcessor.class);
	String COLUMNNAME_C_OLCandProcessor_ID = "C_OLCandProcessor_ID";

	/**
	 * Set Auftragskandidat - Auftragszeile.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Order_Line_Alloc_ID (int C_Order_Line_Alloc_ID);

	/**
	 * Get Auftragskandidat - Auftragszeile.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Order_Line_Alloc_ID();

	ModelColumn<I_C_Order_Line_Alloc, Object> COLUMN_C_Order_Line_Alloc_ID = new ModelColumn<>(I_C_Order_Line_Alloc.class, "C_Order_Line_Alloc_ID", null);
	String COLUMNNAME_C_Order_Line_Alloc_ID = "C_Order_Line_Alloc_ID";

	/**
	 * Set Orderline.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_OrderLine_ID (int C_OrderLine_ID);

	/**
	 * Get Orderline.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_OrderLine_ID();

	org.compiere.model.I_C_OrderLine getC_OrderLine();

	void setC_OrderLine(org.compiere.model.I_C_OrderLine C_OrderLine);

	ModelColumn<I_C_Order_Line_Alloc, org.compiere.model.I_C_OrderLine> COLUMN_C_OrderLine_ID = new ModelColumn<>(I_C_Order_Line_Alloc.class, "C_OrderLine_ID", org.compiere.model.I_C_OrderLine.class);
	String COLUMNNAME_C_OrderLine_ID = "C_OrderLine_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_Order_Line_Alloc, Object> COLUMN_Created = new ModelColumn<>(I_C_Order_Line_Alloc.class, "Created", null);
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
	 * Set Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setDocStatus (@Nullable java.lang.String DocStatus);

	/**
	 * Get Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	@Nullable java.lang.String getDocStatus();

	ModelColumn<I_C_Order_Line_Alloc, Object> COLUMN_DocStatus = new ModelColumn<>(I_C_Order_Line_Alloc.class, "DocStatus", null);
	String COLUMNNAME_DocStatus = "DocStatus";

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

	ModelColumn<I_C_Order_Line_Alloc, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Order_Line_Alloc.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Qty Ordered.
	 * Qty Ordered
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyOrdered (BigDecimal QtyOrdered);

	/**
	 * Get Qty Ordered.
	 * Qty Ordered
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyOrdered();

	ModelColumn<I_C_Order_Line_Alloc, Object> COLUMN_QtyOrdered = new ModelColumn<>(I_C_Order_Line_Alloc.class, "QtyOrdered", null);
	String COLUMNNAME_QtyOrdered = "QtyOrdered";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_Order_Line_Alloc, Object> COLUMN_Updated = new ModelColumn<>(I_C_Order_Line_Alloc.class, "Updated", null);
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
