package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for PP_Maturing_Candidates_v
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_PP_Maturing_Candidates_v 
{

	String Table_Name = "PP_Maturing_Candidates_v";

//	/** AD_Table_ID=542385 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

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
	 * Set Date Start Schedule.
	 * Scheduled start date for this Order
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateStartSchedule (@Nullable java.sql.Timestamp DateStartSchedule);

	/**
	 * Get Date Start Schedule.
	 * Scheduled start date for this Order
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDateStartSchedule();

	ModelColumn<I_PP_Maturing_Candidates_v, Object> COLUMN_DateStartSchedule = new ModelColumn<>(I_PP_Maturing_Candidates_v.class, "DateStartSchedule", null);
	String COLUMNNAME_DateStartSchedule = "DateStartSchedule";

	/**
	 * Set Packing Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setHUStatus (@Nullable java.lang.String HUStatus);

	/**
	 * Get Packing Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getHUStatus();

	ModelColumn<I_PP_Maturing_Candidates_v, Object> COLUMN_HUStatus = new ModelColumn<>(I_PP_Maturing_Candidates_v.class, "HUStatus", null);
	String COLUMNNAME_HUStatus = "HUStatus";

	/**
	 * Set issue_m_product_id.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIssue_M_Product_ID (int Issue_M_Product_ID);

	/**
	 * Get issue_m_product_id.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getIssue_M_Product_ID();

	String COLUMNNAME_Issue_M_Product_ID = "Issue_M_Product_ID";

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

	ModelColumn<I_PP_Maturing_Candidates_v, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new ModelColumn<>(I_PP_Maturing_Candidates_v.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
	String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/**
	 * Set Handling Unit.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_HU_ID (int M_HU_ID);

	/**
	 * Get Handling Unit.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_HU_ID();

	ModelColumn<I_PP_Maturing_Candidates_v, Object> COLUMN_M_HU_ID = new ModelColumn<>(I_PP_Maturing_Candidates_v.class, "M_HU_ID", null);
	String COLUMNNAME_M_HU_ID = "M_HU_ID";

	/**
	 * Set Maturing Configuration .
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Maturing_Configuration_ID (int M_Maturing_Configuration_ID);

	/**
	 * Get Maturing Configuration .
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Maturing_Configuration_ID();

	@Nullable org.compiere.model.I_M_Maturing_Configuration getM_Maturing_Configuration();

	void setM_Maturing_Configuration(@Nullable org.compiere.model.I_M_Maturing_Configuration M_Maturing_Configuration);

	ModelColumn<I_PP_Maturing_Candidates_v, org.compiere.model.I_M_Maturing_Configuration> COLUMN_M_Maturing_Configuration_ID = new ModelColumn<>(I_PP_Maturing_Candidates_v.class, "M_Maturing_Configuration_ID", org.compiere.model.I_M_Maturing_Configuration.class);
	String COLUMNNAME_M_Maturing_Configuration_ID = "M_Maturing_Configuration_ID";

	/**
	 * Set Maturing Products Allocation.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Maturing_Configuration_Line_ID (int M_Maturing_Configuration_Line_ID);

	/**
	 * Get Maturing Products Allocation.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Maturing_Configuration_Line_ID();

	@Nullable org.compiere.model.I_M_Maturing_Configuration_Line getM_Maturing_Configuration_Line();

	void setM_Maturing_Configuration_Line(@Nullable org.compiere.model.I_M_Maturing_Configuration_Line M_Maturing_Configuration_Line);

	ModelColumn<I_PP_Maturing_Candidates_v, org.compiere.model.I_M_Maturing_Configuration_Line> COLUMN_M_Maturing_Configuration_Line_ID = new ModelColumn<>(I_PP_Maturing_Candidates_v.class, "M_Maturing_Configuration_Line_ID", org.compiere.model.I_M_Maturing_Configuration_Line.class);
	String COLUMNNAME_M_Maturing_Configuration_Line_ID = "M_Maturing_Configuration_Line_ID";

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
	 * Set Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Warehouse_ID (int M_Warehouse_ID);

	/**
	 * Get Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Warehouse_ID();

	String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/**
	 * Set PP_Maturing_Candidates_v.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPP_Maturing_Candidates_v_ID (int PP_Maturing_Candidates_v_ID);

	/**
	 * Get PP_Maturing_Candidates_v.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPP_Maturing_Candidates_v_ID();

	ModelColumn<I_PP_Maturing_Candidates_v, Object> COLUMN_PP_Maturing_Candidates_v_ID = new ModelColumn<>(I_PP_Maturing_Candidates_v.class, "PP_Maturing_Candidates_v_ID", null);
	String COLUMNNAME_PP_Maturing_Candidates_v_ID = "PP_Maturing_Candidates_v_ID";

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

	ModelColumn<I_PP_Maturing_Candidates_v, org.eevolution.model.I_PP_Order_Candidate> COLUMN_PP_Order_Candidate_ID = new ModelColumn<>(I_PP_Maturing_Candidates_v.class, "PP_Order_Candidate_ID", org.eevolution.model.I_PP_Order_Candidate.class);
	String COLUMNNAME_PP_Order_Candidate_ID = "PP_Order_Candidate_ID";

	/**
	 * Set BOM & Formula.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPP_Product_BOMVersions_ID (int PP_Product_BOMVersions_ID);

	/**
	 * Get BOM & Formula.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPP_Product_BOMVersions_ID();

	@Nullable org.eevolution.model.I_PP_Product_BOMVersions getPP_Product_BOMVersions();

	void setPP_Product_BOMVersions(@Nullable org.eevolution.model.I_PP_Product_BOMVersions PP_Product_BOMVersions);

	ModelColumn<I_PP_Maturing_Candidates_v, org.eevolution.model.I_PP_Product_BOMVersions> COLUMN_PP_Product_BOMVersions_ID = new ModelColumn<>(I_PP_Maturing_Candidates_v.class, "PP_Product_BOMVersions_ID", org.eevolution.model.I_PP_Product_BOMVersions.class);
	String COLUMNNAME_PP_Product_BOMVersions_ID = "PP_Product_BOMVersions_ID";

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

	ModelColumn<I_PP_Maturing_Candidates_v, Object> COLUMN_Qty = new ModelColumn<>(I_PP_Maturing_Candidates_v.class, "Qty", null);
	String COLUMNNAME_Qty = "Qty";
}
