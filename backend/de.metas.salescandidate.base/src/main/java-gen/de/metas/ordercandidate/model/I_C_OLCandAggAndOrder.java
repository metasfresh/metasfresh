package de.metas.ordercandidate.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_OLCandAggAndOrder
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_OLCandAggAndOrder 
{

	String Table_Name = "C_OLCandAggAndOrder";

//	/** AD_Table_ID=540246 */
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
	 * Set Auftragskand. Spalte.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Column_OLCand_ID (int AD_Column_OLCand_ID);

	/**
	 * Get Auftragskand. Spalte.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Column_OLCand_ID();

	org.compiere.model.I_AD_Column getAD_Column_OLCand();

	void setAD_Column_OLCand(org.compiere.model.I_AD_Column AD_Column_OLCand);

	ModelColumn<I_C_OLCandAggAndOrder, org.compiere.model.I_AD_Column> COLUMN_AD_Column_OLCand_ID = new ModelColumn<>(I_C_OLCandAggAndOrder.class, "AD_Column_OLCand_ID", org.compiere.model.I_AD_Column.class);
	String COLUMNNAME_AD_Column_OLCand_ID = "AD_Column_OLCand_ID";

	/**
	 * Set Inputsource.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_InputDataSource_ID (int AD_InputDataSource_ID);

	/**
	 * Get Inputsource.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_InputDataSource_ID();

	ModelColumn<I_C_OLCandAggAndOrder, Object> COLUMN_AD_InputDataSource_ID = new ModelColumn<>(I_C_OLCandAggAndOrder.class, "AD_InputDataSource_ID", null);
	String COLUMNNAME_AD_InputDataSource_ID = "AD_InputDataSource_ID";

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
	 * Set Referenz-ID der Auftragskand. Spalte.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setAD_Reference_OLCand_ID (int AD_Reference_OLCand_ID);

	/**
	 * Get Referenz-ID der Auftragskand. Spalte.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	int getAD_Reference_OLCand_ID();

	ModelColumn<I_C_OLCandAggAndOrder, Object> COLUMN_AD_Reference_OLCand_ID = new ModelColumn<>(I_C_OLCandAggAndOrder.class, "AD_Reference_OLCand_ID", null);
	String COLUMNNAME_AD_Reference_OLCand_ID = "AD_Reference_OLCand_ID";

	/**
	 * Set Auftragskand. Aggregieren.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_OLCandAggAndOrder_ID (int C_OLCandAggAndOrder_ID);

	/**
	 * Get Auftragskand. Aggregieren.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_OLCandAggAndOrder_ID();

	ModelColumn<I_C_OLCandAggAndOrder, Object> COLUMN_C_OLCandAggAndOrder_ID = new ModelColumn<>(I_C_OLCandAggAndOrder.class, "C_OLCandAggAndOrder_ID", null);
	String COLUMNNAME_C_OLCandAggAndOrder_ID = "C_OLCandAggAndOrder_ID";

	/**
	 * Set Auftragskand. Verarb..
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_OLCandProcessor_ID (int C_OLCandProcessor_ID);

	/**
	 * Get Auftragskand. Verarb..
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_OLCandProcessor_ID();

	de.metas.ordercandidate.model.I_C_OLCandProcessor getC_OLCandProcessor();

	void setC_OLCandProcessor(de.metas.ordercandidate.model.I_C_OLCandProcessor C_OLCandProcessor);

	ModelColumn<I_C_OLCandAggAndOrder, de.metas.ordercandidate.model.I_C_OLCandProcessor> COLUMN_C_OLCandProcessor_ID = new ModelColumn<>(I_C_OLCandAggAndOrder.class, "C_OLCandProcessor_ID", de.metas.ordercandidate.model.I_C_OLCandProcessor.class);
	String COLUMNNAME_C_OLCandProcessor_ID = "C_OLCandProcessor_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_OLCandAggAndOrder, Object> COLUMN_Created = new ModelColumn<>(I_C_OLCandAggAndOrder.class, "Created", null);
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

	ModelColumn<I_C_OLCandAggAndOrder, Object> COLUMN_Description = new ModelColumn<>(I_C_OLCandAggAndOrder.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Agg.-Ebene.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setGranularity (@Nullable java.lang.String Granularity);

	/**
	 * Get Agg.-Ebene.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getGranularity();

	ModelColumn<I_C_OLCandAggAndOrder, Object> COLUMN_Granularity = new ModelColumn<>(I_C_OLCandAggAndOrder.class, "Granularity", null);
	String COLUMNNAME_Granularity = "Granularity";

	/**
	 * Set Aggregieren.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setGroupBy (boolean GroupBy);

	/**
	 * Get Aggregieren.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isGroupBy();

	ModelColumn<I_C_OLCandAggAndOrder, Object> COLUMN_GroupBy = new ModelColumn<>(I_C_OLCandAggAndOrder.class, "GroupBy", null);
	String COLUMNNAME_GroupBy = "GroupBy";

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

	ModelColumn<I_C_OLCandAggAndOrder, Object> COLUMN_IsActive = new ModelColumn<>(I_C_OLCandAggAndOrder.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Sortier-Reihenfolge.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOrderBySeqNo (int OrderBySeqNo);

	/**
	 * Get Sortier-Reihenfolge.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getOrderBySeqNo();

	ModelColumn<I_C_OLCandAggAndOrder, Object> COLUMN_OrderBySeqNo = new ModelColumn<>(I_C_OLCandAggAndOrder.class, "OrderBySeqNo", null);
	String COLUMNNAME_OrderBySeqNo = "OrderBySeqNo";

	/**
	 * Set In eig. Auftrag.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSplitOrder (boolean SplitOrder);

	/**
	 * Get In eig. Auftrag.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSplitOrder();

	ModelColumn<I_C_OLCandAggAndOrder, Object> COLUMN_SplitOrder = new ModelColumn<>(I_C_OLCandAggAndOrder.class, "SplitOrder", null);
	String COLUMNNAME_SplitOrder = "SplitOrder";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_OLCandAggAndOrder, Object> COLUMN_Updated = new ModelColumn<>(I_C_OLCandAggAndOrder.class, "Updated", null);
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
