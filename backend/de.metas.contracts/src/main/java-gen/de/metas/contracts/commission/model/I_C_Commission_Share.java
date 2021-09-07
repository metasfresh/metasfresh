package de.metas.contracts.commission.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for C_Commission_Share
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Commission_Share 
{

	String Table_Name = "C_Commission_Share";

//	/** AD_Table_ID=541406 */
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
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Payer.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Payer_ID (int C_BPartner_Payer_ID);

	/**
	 * Get Payer.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_Payer_ID();

	ModelColumn<I_C_Commission_Share, Object> COLUMN_C_BPartner_Payer_ID = new ModelColumn<>(I_C_Commission_Share.class, "C_BPartner_Payer_ID", null);
	String COLUMNNAME_C_BPartner_Payer_ID = "C_BPartner_Payer_ID";

	/**
	 * Set Sales partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_SalesRep_ID (int C_BPartner_SalesRep_ID);

	/**
	 * Get Sales partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_SalesRep_ID();

	String COLUMNNAME_C_BPartner_SalesRep_ID = "C_BPartner_SalesRep_ID";

	/**
	 * Set Commission instance.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Commission_Instance_ID (int C_Commission_Instance_ID);

	/**
	 * Get Commission instance.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Commission_Instance_ID();

	I_C_Commission_Instance getC_Commission_Instance();

	void setC_Commission_Instance(I_C_Commission_Instance C_Commission_Instance);

	ModelColumn<I_C_Commission_Share, I_C_Commission_Instance> COLUMN_C_Commission_Instance_ID = new ModelColumn<>(I_C_Commission_Share.class, "C_Commission_Instance_ID", I_C_Commission_Instance.class);
	String COLUMNNAME_C_Commission_Instance_ID = "C_Commission_Instance_ID";

	/**
	 * Set Commission share.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Commission_Share_ID (int C_Commission_Share_ID);

	/**
	 * Get Commission share.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Commission_Share_ID();

	ModelColumn<I_C_Commission_Share, Object> COLUMN_C_Commission_Share_ID = new ModelColumn<>(I_C_Commission_Share.class, "C_Commission_Share_ID", null);
	String COLUMNNAME_C_Commission_Share_ID = "C_Commission_Share_ID";

	/**
	 * Set Settings detail.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_CommissionSettingsLine_ID (int C_CommissionSettingsLine_ID);

	/**
	 * Get Settings detail.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_CommissionSettingsLine_ID();

	@Nullable I_C_CommissionSettingsLine getC_CommissionSettingsLine();

	void setC_CommissionSettingsLine(@Nullable I_C_CommissionSettingsLine C_CommissionSettingsLine);

	ModelColumn<I_C_Commission_Share, I_C_CommissionSettingsLine> COLUMN_C_CommissionSettingsLine_ID = new ModelColumn<>(I_C_Commission_Share.class, "C_CommissionSettingsLine_ID", I_C_CommissionSettingsLine.class);
	String COLUMNNAME_C_CommissionSettingsLine_ID = "C_CommissionSettingsLine_ID";

	/**
	 * Set Flatrate Term.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Flatrate_Term_ID (int C_Flatrate_Term_ID);

	/**
	 * Get Flatrate Term.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Flatrate_Term_ID();

	ModelColumn<I_C_Commission_Share, Object> COLUMN_C_Flatrate_Term_ID = new ModelColumn<>(I_C_Commission_Share.class, "C_Flatrate_Term_ID", null);
	String COLUMNNAME_C_Flatrate_Term_ID = "C_Flatrate_Term_ID";

	/**
	 * Set C_MediatedCommissionSettingsLine.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_MediatedCommissionSettingsLine_ID (int C_MediatedCommissionSettingsLine_ID);

	/**
	 * Get C_MediatedCommissionSettingsLine.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_MediatedCommissionSettingsLine_ID();

	@Nullable I_C_MediatedCommissionSettingsLine getC_MediatedCommissionSettingsLine();

	void setC_MediatedCommissionSettingsLine(@Nullable I_C_MediatedCommissionSettingsLine C_MediatedCommissionSettingsLine);

	ModelColumn<I_C_Commission_Share, I_C_MediatedCommissionSettingsLine> COLUMN_C_MediatedCommissionSettingsLine_ID = new ModelColumn<>(I_C_Commission_Share.class, "C_MediatedCommissionSettingsLine_ID", I_C_MediatedCommissionSettingsLine.class);
	String COLUMNNAME_C_MediatedCommissionSettingsLine_ID = "C_MediatedCommissionSettingsLine_ID";

	/**
	 * Set Commission product.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCommission_Product_ID (int Commission_Product_ID);

	/**
	 * Get Commission product.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCommission_Product_ID();

	String COLUMNNAME_Commission_Product_ID = "Commission_Product_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_Commission_Share, Object> COLUMN_Created = new ModelColumn<>(I_C_Commission_Share.class, "Created", null);
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

	ModelColumn<I_C_Commission_Share, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Commission_Share.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Simulation.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSimulation (boolean IsSimulation);

	/**
	 * Get Simulation.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSimulation();

	ModelColumn<I_C_Commission_Share, Object> COLUMN_IsSimulation = new ModelColumn<>(I_C_Commission_Share.class, "IsSimulation", null);
	String COLUMNNAME_IsSimulation = "IsSimulation";

	/**
	 * Set Sales Transaction.
	 * This is a Sales Transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSOTrx (boolean IsSOTrx);

	/**
	 * Get Sales Transaction.
	 * This is a Sales Transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSOTrx();

	ModelColumn<I_C_Commission_Share, Object> COLUMN_IsSOTrx = new ModelColumn<>(I_C_Commission_Share.class, "IsSOTrx", null);
	String COLUMNNAME_IsSOTrx = "IsSOTrx";

	/**
	 * Set Level.
	 * Hierachy-Level of the respective record. The customer's direct sales rep has level 0. The higher in the hierachy a participating sales rep is, the higher is their level.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setLevelHierarchy (int LevelHierarchy);

	/**
	 * Get Level.
	 * Hierachy-Level of the respective record. The customer's direct sales rep has level 0. The higher in the hierachy a participating sales rep is, the higher is their level.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getLevelHierarchy();

	ModelColumn<I_C_Commission_Share, Object> COLUMN_LevelHierarchy = new ModelColumn<>(I_C_Commission_Share.class, "LevelHierarchy", null);
	String COLUMNNAME_LevelHierarchy = "LevelHierarchy";

	/**
	 * Set Ordered points.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPointsSum_Forecasted (BigDecimal PointsSum_Forecasted);

	/**
	 * Get Ordered points.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getPointsSum_Forecasted();

	ModelColumn<I_C_Commission_Share, Object> COLUMN_PointsSum_Forecasted = new ModelColumn<>(I_C_Commission_Share.class, "PointsSum_Forecasted", null);
	String COLUMNNAME_PointsSum_Forecasted = "PointsSum_Forecasted";

	/**
	 * Set Invoiceable points.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPointsSum_Invoiceable (BigDecimal PointsSum_Invoiceable);

	/**
	 * Get Invoiceable points.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getPointsSum_Invoiceable();

	ModelColumn<I_C_Commission_Share, Object> COLUMN_PointsSum_Invoiceable = new ModelColumn<>(I_C_Commission_Share.class, "PointsSum_Invoiceable", null);
	String COLUMNNAME_PointsSum_Invoiceable = "PointsSum_Invoiceable";

	/**
	 * Set Invoiced points.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPointsSum_Invoiced (BigDecimal PointsSum_Invoiced);

	/**
	 * Get Invoiced points.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getPointsSum_Invoiced();

	ModelColumn<I_C_Commission_Share, Object> COLUMN_PointsSum_Invoiced = new ModelColumn<>(I_C_Commission_Share.class, "PointsSum_Invoiced", null);
	String COLUMNNAME_PointsSum_Invoiced = "PointsSum_Invoiced";

	/**
	 * Set Settled points.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPointsSum_Settled (BigDecimal PointsSum_Settled);

	/**
	 * Get Settled points.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getPointsSum_Settled();

	ModelColumn<I_C_Commission_Share, Object> COLUMN_PointsSum_Settled = new ModelColumn<>(I_C_Commission_Share.class, "PointsSum_Settled", null);
	String COLUMNNAME_PointsSum_Settled = "PointsSum_Settled";

	/**
	 * Set Points to settle.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPointsSum_ToSettle (BigDecimal PointsSum_ToSettle);

	/**
	 * Get Points to settle.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getPointsSum_ToSettle();

	ModelColumn<I_C_Commission_Share, Object> COLUMN_PointsSum_ToSettle = new ModelColumn<>(I_C_Commission_Share.class, "PointsSum_ToSettle", null);
	String COLUMNNAME_PointsSum_ToSettle = "PointsSum_ToSettle";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_Commission_Share, Object> COLUMN_Updated = new ModelColumn<>(I_C_Commission_Share.class, "Updated", null);
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
