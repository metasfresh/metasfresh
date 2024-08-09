package de.metas.material.dispo.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for MD_Candidate_Dist_Detail
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_MD_Candidate_Dist_Detail 
{

	String Table_Name = "MD_Candidate_Dist_Detail";

//	/** AD_Table_ID=540821 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setActualQty (@Nullable BigDecimal ActualQty);

	/**
	 * Get Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getActualQty();

	ModelColumn<I_MD_Candidate_Dist_Detail, Object> COLUMN_ActualQty = new ModelColumn<>(I_MD_Candidate_Dist_Detail.class, "ActualQty", null);
	String COLUMNNAME_ActualQty = "ActualQty";

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
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_MD_Candidate_Dist_Detail, Object> COLUMN_Created = new ModelColumn<>(I_MD_Candidate_Dist_Detail.class, "Created", null);
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
	 * Set Network Distribution.
	 * Identifies a distribution network, distribution networks are used to establish the source and target of the materials in the supply chain
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDD_NetworkDistribution_ID (int DD_NetworkDistribution_ID);

	/**
	 * Get Network Distribution.
	 * Identifies a distribution network, distribution networks are used to establish the source and target of the materials in the supply chain
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDD_NetworkDistribution_ID();

	@Nullable org.eevolution.model.I_DD_NetworkDistribution getDD_NetworkDistribution();

	void setDD_NetworkDistribution(@Nullable org.eevolution.model.I_DD_NetworkDistribution DD_NetworkDistribution);

	ModelColumn<I_MD_Candidate_Dist_Detail, org.eevolution.model.I_DD_NetworkDistribution> COLUMN_DD_NetworkDistribution_ID = new ModelColumn<>(I_MD_Candidate_Dist_Detail.class, "DD_NetworkDistribution_ID", org.eevolution.model.I_DD_NetworkDistribution.class);
	String COLUMNNAME_DD_NetworkDistribution_ID = "DD_NetworkDistribution_ID";

	/**
	 * Set Network Distribution Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDD_NetworkDistributionLine_ID (int DD_NetworkDistributionLine_ID);

	/**
	 * Get Network Distribution Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDD_NetworkDistributionLine_ID();

	@Nullable org.eevolution.model.I_DD_NetworkDistributionLine getDD_NetworkDistributionLine();

	void setDD_NetworkDistributionLine(@Nullable org.eevolution.model.I_DD_NetworkDistributionLine DD_NetworkDistributionLine);

	ModelColumn<I_MD_Candidate_Dist_Detail, org.eevolution.model.I_DD_NetworkDistributionLine> COLUMN_DD_NetworkDistributionLine_ID = new ModelColumn<>(I_MD_Candidate_Dist_Detail.class, "DD_NetworkDistributionLine_ID", org.eevolution.model.I_DD_NetworkDistributionLine.class);
	String COLUMNNAME_DD_NetworkDistributionLine_ID = "DD_NetworkDistributionLine_ID";

	/**
	 * Set Distribution Candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDD_Order_Candidate_ID (int DD_Order_Candidate_ID);

	/**
	 * Get Distribution Candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDD_Order_Candidate_ID();

	@Nullable org.eevolution.model.I_DD_Order_Candidate getDD_Order_Candidate();

	void setDD_Order_Candidate(@Nullable org.eevolution.model.I_DD_Order_Candidate DD_Order_Candidate);

	ModelColumn<I_MD_Candidate_Dist_Detail, org.eevolution.model.I_DD_Order_Candidate> COLUMN_DD_Order_Candidate_ID = new ModelColumn<>(I_MD_Candidate_Dist_Detail.class, "DD_Order_Candidate_ID", org.eevolution.model.I_DD_Order_Candidate.class);
	String COLUMNNAME_DD_Order_Candidate_ID = "DD_Order_Candidate_ID";

	/**
	 * Set Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDD_Order_DocStatus (@Nullable java.lang.String DD_Order_DocStatus);

	/**
	 * Get Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDD_Order_DocStatus();

	ModelColumn<I_MD_Candidate_Dist_Detail, Object> COLUMN_DD_Order_DocStatus = new ModelColumn<>(I_MD_Candidate_Dist_Detail.class, "DD_Order_DocStatus", null);
	String COLUMNNAME_DD_Order_DocStatus = "DD_Order_DocStatus";

	/**
	 * Set Distribution Order.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDD_Order_ID (int DD_Order_ID);

	/**
	 * Get Distribution Order.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDD_Order_ID();

	@Nullable org.eevolution.model.I_DD_Order getDD_Order();

	void setDD_Order(@Nullable org.eevolution.model.I_DD_Order DD_Order);

	ModelColumn<I_MD_Candidate_Dist_Detail, org.eevolution.model.I_DD_Order> COLUMN_DD_Order_ID = new ModelColumn<>(I_MD_Candidate_Dist_Detail.class, "DD_Order_ID", org.eevolution.model.I_DD_Order.class);
	String COLUMNNAME_DD_Order_ID = "DD_Order_ID";

	/**
	 * Set Distribution Order Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDD_OrderLine_ID (int DD_OrderLine_ID);

	/**
	 * Get Distribution Order Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDD_OrderLine_ID();

	@Nullable org.eevolution.model.I_DD_OrderLine getDD_OrderLine();

	void setDD_OrderLine(@Nullable org.eevolution.model.I_DD_OrderLine DD_OrderLine);

	ModelColumn<I_MD_Candidate_Dist_Detail, org.eevolution.model.I_DD_OrderLine> COLUMN_DD_OrderLine_ID = new ModelColumn<>(I_MD_Candidate_Dist_Detail.class, "DD_OrderLine_ID", org.eevolution.model.I_DD_OrderLine.class);
	String COLUMNNAME_DD_OrderLine_ID = "DD_OrderLine_ID";

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

	ModelColumn<I_MD_Candidate_Dist_Detail, Object> COLUMN_IsActive = new ModelColumn<>(I_MD_Candidate_Dist_Detail.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set System Advised.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAdvised (boolean IsAdvised);

	/**
	 * Get System Advised.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAdvised();

	ModelColumn<I_MD_Candidate_Dist_Detail, Object> COLUMN_IsAdvised = new ModelColumn<>(I_MD_Candidate_Dist_Detail.class, "IsAdvised", null);
	String COLUMNNAME_IsAdvised = "IsAdvised";

	/**
	 * Set Auto Picking.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsPickDirectlyIfFeasible (boolean IsPickDirectlyIfFeasible);

	/**
	 * Get Auto Picking.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPickDirectlyIfFeasible();

	ModelColumn<I_MD_Candidate_Dist_Detail, Object> COLUMN_IsPickDirectlyIfFeasible = new ModelColumn<>(I_MD_Candidate_Dist_Detail.class, "IsPickDirectlyIfFeasible", null);
	String COLUMNNAME_IsPickDirectlyIfFeasible = "IsPickDirectlyIfFeasible";

	/**
	 * Set Dispo Supplydetail.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMD_Candidate_Dist_Detail_ID (int MD_Candidate_Dist_Detail_ID);

	/**
	 * Get Dispo Supplydetail.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getMD_Candidate_Dist_Detail_ID();

	ModelColumn<I_MD_Candidate_Dist_Detail, Object> COLUMN_MD_Candidate_Dist_Detail_ID = new ModelColumn<>(I_MD_Candidate_Dist_Detail.class, "MD_Candidate_Dist_Detail_ID", null);
	String COLUMNNAME_MD_Candidate_Dist_Detail_ID = "MD_Candidate_Dist_Detail_ID";

	/**
	 * Set Dispo Candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMD_Candidate_ID (int MD_Candidate_ID);

	/**
	 * Get Dispo Candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getMD_Candidate_ID();

	de.metas.material.dispo.model.I_MD_Candidate getMD_Candidate();

	void setMD_Candidate(de.metas.material.dispo.model.I_MD_Candidate MD_Candidate);

	ModelColumn<I_MD_Candidate_Dist_Detail, de.metas.material.dispo.model.I_MD_Candidate> COLUMN_MD_Candidate_ID = new ModelColumn<>(I_MD_Candidate_Dist_Detail.class, "MD_Candidate_ID", de.metas.material.dispo.model.I_MD_Candidate.class);
	String COLUMNNAME_MD_Candidate_ID = "MD_Candidate_ID";

	/**
	 * Set Shipper.
	 * Method or manner of product delivery
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Shipper_ID (int M_Shipper_ID);

	/**
	 * Get Shipper.
	 * Method or manner of product delivery
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Shipper_ID();

	@Nullable org.compiere.model.I_M_Shipper getM_Shipper();

	void setM_Shipper(@Nullable org.compiere.model.I_M_Shipper M_Shipper);

	ModelColumn<I_MD_Candidate_Dist_Detail, org.compiere.model.I_M_Shipper> COLUMN_M_Shipper_ID = new ModelColumn<>(I_MD_Candidate_Dist_Detail.class, "M_Shipper_ID", org.compiere.model.I_M_Shipper.class);
	String COLUMNNAME_M_Shipper_ID = "M_Shipper_ID";

	/**
	 * Set Planned Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPlannedQty (@Nullable BigDecimal PlannedQty);

	/**
	 * Get Planned Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getPlannedQty();

	ModelColumn<I_MD_Candidate_Dist_Detail, Object> COLUMN_PlannedQty = new ModelColumn<>(I_MD_Candidate_Dist_Detail.class, "PlannedQty", null);
	String COLUMNNAME_PlannedQty = "PlannedQty";

	/**
	 * Set Manufacturing Order BOM Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPP_Order_BOMLine_ID (int PP_Order_BOMLine_ID);

	/**
	 * Get Manufacturing Order BOM Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPP_Order_BOMLine_ID();

	@Nullable org.eevolution.model.I_PP_Order getPP_Order_BOMLine();

	void setPP_Order_BOMLine(@Nullable org.eevolution.model.I_PP_Order PP_Order_BOMLine);

	ModelColumn<I_MD_Candidate_Dist_Detail, org.eevolution.model.I_PP_Order> COLUMN_PP_Order_BOMLine_ID = new ModelColumn<>(I_MD_Candidate_Dist_Detail.class, "PP_Order_BOMLine_ID", org.eevolution.model.I_PP_Order.class);
	String COLUMNNAME_PP_Order_BOMLine_ID = "PP_Order_BOMLine_ID";

	/**
	 * Set Manufacturing candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPP_Order_Candidate_ID (int PP_Order_Candidate_ID);

	/**
	 * Get Manufacturing candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPP_Order_Candidate_ID();

	@Nullable org.eevolution.model.I_PP_Order_Candidate getPP_Order_Candidate();

	void setPP_Order_Candidate(@Nullable org.eevolution.model.I_PP_Order_Candidate PP_Order_Candidate);

	ModelColumn<I_MD_Candidate_Dist_Detail, org.eevolution.model.I_PP_Order_Candidate> COLUMN_PP_Order_Candidate_ID = new ModelColumn<>(I_MD_Candidate_Dist_Detail.class, "PP_Order_Candidate_ID", org.eevolution.model.I_PP_Order_Candidate.class);
	String COLUMNNAME_PP_Order_Candidate_ID = "PP_Order_Candidate_ID";

	/**
	 * Set Manufacturing Order.
	 * Manufacturing Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPP_Order_ID (int PP_Order_ID);

	/**
	 * Get Manufacturing Order.
	 * Manufacturing Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPP_Order_ID();

	@Nullable org.eevolution.model.I_PP_Order getPP_Order();

	void setPP_Order(@Nullable org.eevolution.model.I_PP_Order PP_Order);

	ModelColumn<I_MD_Candidate_Dist_Detail, org.eevolution.model.I_PP_Order> COLUMN_PP_Order_ID = new ModelColumn<>(I_MD_Candidate_Dist_Detail.class, "PP_Order_ID", org.eevolution.model.I_PP_Order.class);
	String COLUMNNAME_PP_Order_ID = "PP_Order_ID";

	/**
	 * Set Manufacturing Order Line Candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPP_OrderLine_Candidate_ID (int PP_OrderLine_Candidate_ID);

	/**
	 * Get Manufacturing Order Line Candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPP_OrderLine_Candidate_ID();

	@Nullable org.eevolution.model.I_PP_Order_Candidate getPP_OrderLine_Candidate();

	void setPP_OrderLine_Candidate(@Nullable org.eevolution.model.I_PP_Order_Candidate PP_OrderLine_Candidate);

	ModelColumn<I_MD_Candidate_Dist_Detail, org.eevolution.model.I_PP_Order_Candidate> COLUMN_PP_OrderLine_Candidate_ID = new ModelColumn<>(I_MD_Candidate_Dist_Detail.class, "PP_OrderLine_Candidate_ID", org.eevolution.model.I_PP_Order_Candidate.class);
	String COLUMNNAME_PP_OrderLine_Candidate_ID = "PP_OrderLine_Candidate_ID";

	/**
	 * Set Plant.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPP_Plant_ID (int PP_Plant_ID);

	/**
	 * Get Plant.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPP_Plant_ID();

	@Nullable org.compiere.model.I_S_Resource getPP_Plant();

	void setPP_Plant(@Nullable org.compiere.model.I_S_Resource PP_Plant);

	ModelColumn<I_MD_Candidate_Dist_Detail, org.compiere.model.I_S_Resource> COLUMN_PP_Plant_ID = new ModelColumn<>(I_MD_Candidate_Dist_Detail.class, "PP_Plant_ID", org.compiere.model.I_S_Resource.class);
	String COLUMNNAME_PP_Plant_ID = "PP_Plant_ID";

	/**
	 * Set Product Planning.
	 * Product Planning
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPP_Product_Planning_ID (int PP_Product_Planning_ID);

	/**
	 * Get Product Planning.
	 * Product Planning
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPP_Product_Planning_ID();

	String COLUMNNAME_PP_Product_Planning_ID = "PP_Product_Planning_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_MD_Candidate_Dist_Detail, Object> COLUMN_Updated = new ModelColumn<>(I_MD_Candidate_Dist_Detail.class, "Updated", null);
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
