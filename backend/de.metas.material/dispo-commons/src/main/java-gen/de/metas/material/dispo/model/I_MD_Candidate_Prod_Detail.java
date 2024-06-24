package de.metas.material.dispo.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for MD_Candidate_Prod_Detail
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_MD_Candidate_Prod_Detail 
{

	String Table_Name = "MD_Candidate_Prod_Detail";

//	/** AD_Table_ID=540810 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Istmenge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setActualQty (@Nullable BigDecimal ActualQty);

	/**
	 * Get Istmenge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getActualQty();

	ModelColumn<I_MD_Candidate_Prod_Detail, Object> COLUMN_ActualQty = new ModelColumn<>(I_MD_Candidate_Prod_Detail.class, "ActualQty", null);
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

	ModelColumn<I_MD_Candidate_Prod_Detail, Object> COLUMN_Created = new ModelColumn<>(I_MD_Candidate_Prod_Detail.class, "Created", null);
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
	void setDescription (@Nullable String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getDescription();

	ModelColumn<I_MD_Candidate_Prod_Detail, Object> COLUMN_Description = new ModelColumn<>(I_MD_Candidate_Prod_Detail.class, "Description", null);
	String COLUMNNAME_Description = "Description";

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

	ModelColumn<I_MD_Candidate_Prod_Detail, Object> COLUMN_IsActive = new ModelColumn<>(I_MD_Candidate_Prod_Detail.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Geplant.
	 * Ja bedeutet, dass es zumindest ursprünglich kein entsprechendes Dokument (z.B. Produktionsauftrag) gab, sondern dass das System einen Beleg vorgeschlagen hatte.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAdvised (boolean IsAdvised);

	/**
	 * Get Geplant.
	 * Ja bedeutet, dass es zumindest ursprünglich kein entsprechendes Dokument (z.B. Produktionsauftrag) gab, sondern dass das System einen Beleg vorgeschlagen hatte.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAdvised();

	ModelColumn<I_MD_Candidate_Prod_Detail, Object> COLUMN_IsAdvised = new ModelColumn<>(I_MD_Candidate_Prod_Detail.class, "IsAdvised", null);
	String COLUMNNAME_IsAdvised = "IsAdvised";

	/**
	 * Set Sofort Kommissionieren wenn möglich.
	 * Falls "Ja" und ein Bestand wird für einen bestimmten Lieferdispo-Eintrag bereit gestellt oder produziert, dann wird dieser sofort zugeordnet und als kommissioniert markiert.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsPickDirectlyIfFeasible (boolean IsPickDirectlyIfFeasible);

	/**
	 * Get Sofort Kommissionieren wenn möglich.
	 * Falls "Ja" und ein Bestand wird für einen bestimmten Lieferdispo-Eintrag bereit gestellt oder produziert, dann wird dieser sofort zugeordnet und als kommissioniert markiert.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPickDirectlyIfFeasible();

	ModelColumn<I_MD_Candidate_Prod_Detail, Object> COLUMN_IsPickDirectlyIfFeasible = new ModelColumn<>(I_MD_Candidate_Prod_Detail.class, "IsPickDirectlyIfFeasible", null);
	String COLUMNNAME_IsPickDirectlyIfFeasible = "IsPickDirectlyIfFeasible";

	/**
	 * Set Dispo Candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMD_Candidate_ID (int MD_Candidate_ID);

	/**
	 * Get Dispo Candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getMD_Candidate_ID();

	@Nullable I_MD_Candidate getMD_Candidate();

	void setMD_Candidate(@Nullable I_MD_Candidate MD_Candidate);

	ModelColumn<I_MD_Candidate_Prod_Detail, I_MD_Candidate> COLUMN_MD_Candidate_ID = new ModelColumn<>(I_MD_Candidate_Prod_Detail.class, "MD_Candidate_ID", I_MD_Candidate.class);
	String COLUMNNAME_MD_Candidate_ID = "MD_Candidate_ID";

	/**
	 * Set Dispo-Produktionsdetails.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMD_Candidate_Prod_Detail_ID (int MD_Candidate_Prod_Detail_ID);

	/**
	 * Get Dispo-Produktionsdetails.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getMD_Candidate_Prod_Detail_ID();

	ModelColumn<I_MD_Candidate_Prod_Detail, Object> COLUMN_MD_Candidate_Prod_Detail_ID = new ModelColumn<>(I_MD_Candidate_Prod_Detail.class, "MD_Candidate_Prod_Detail_ID", null);
	String COLUMNNAME_MD_Candidate_Prod_Detail_ID = "MD_Candidate_Prod_Detail_ID";

	/**
	 * Set Geplante Menge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPlannedQty (@Nullable BigDecimal PlannedQty);

	/**
	 * Get Geplante Menge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getPlannedQty();

	ModelColumn<I_MD_Candidate_Prod_Detail, Object> COLUMN_PlannedQty = new ModelColumn<>(I_MD_Candidate_Prod_Detail.class, "PlannedQty", null);
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

	@Nullable org.eevolution.model.I_PP_Order_BOMLine getPP_Order_BOMLine();

	void setPP_Order_BOMLine(@Nullable org.eevolution.model.I_PP_Order_BOMLine PP_Order_BOMLine);

	ModelColumn<I_MD_Candidate_Prod_Detail, org.eevolution.model.I_PP_Order_BOMLine> COLUMN_PP_Order_BOMLine_ID = new ModelColumn<>(I_MD_Candidate_Prod_Detail.class, "PP_Order_BOMLine_ID", org.eevolution.model.I_PP_Order_BOMLine.class);
	String COLUMNNAME_PP_Order_BOMLine_ID = "PP_Order_BOMLine_ID";

	/**
	 * Set Manufacturing Order Candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPP_Order_Candidate_ID (int PP_Order_Candidate_ID);

	/**
	 * Get Manufacturing Order Candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPP_Order_Candidate_ID();

	@Nullable org.eevolution.model.I_PP_Order_Candidate getPP_Order_Candidate();

	void setPP_Order_Candidate(@Nullable org.eevolution.model.I_PP_Order_Candidate PP_Order_Candidate);

	ModelColumn<I_MD_Candidate_Prod_Detail, org.eevolution.model.I_PP_Order_Candidate> COLUMN_PP_Order_Candidate_ID = new ModelColumn<>(I_MD_Candidate_Prod_Detail.class, "PP_Order_Candidate_ID", org.eevolution.model.I_PP_Order_Candidate.class);
	String COLUMNNAME_PP_Order_Candidate_ID = "PP_Order_Candidate_ID";

	/**
	 * Set Belegstatus.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPP_Order_DocStatus (@Nullable String PP_Order_DocStatus);

	/**
	 * Get Belegstatus.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getPP_Order_DocStatus();

	ModelColumn<I_MD_Candidate_Prod_Detail, Object> COLUMN_PP_Order_DocStatus = new ModelColumn<>(I_MD_Candidate_Prod_Detail.class, "PP_Order_DocStatus", null);
	String COLUMNNAME_PP_Order_DocStatus = "PP_Order_DocStatus";

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

	ModelColumn<I_MD_Candidate_Prod_Detail, org.eevolution.model.I_PP_Order> COLUMN_PP_Order_ID = new ModelColumn<>(I_MD_Candidate_Prod_Detail.class, "PP_Order_ID", org.eevolution.model.I_PP_Order.class);
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

	@Nullable org.eevolution.model.I_PP_OrderLine_Candidate getPP_OrderLine_Candidate();

	void setPP_OrderLine_Candidate(@Nullable org.eevolution.model.I_PP_OrderLine_Candidate PP_OrderLine_Candidate);

	ModelColumn<I_MD_Candidate_Prod_Detail, org.eevolution.model.I_PP_OrderLine_Candidate> COLUMN_PP_OrderLine_Candidate_ID = new ModelColumn<>(I_MD_Candidate_Prod_Detail.class, "PP_OrderLine_Candidate_ID", org.eevolution.model.I_PP_OrderLine_Candidate.class);
	String COLUMNNAME_PP_OrderLine_Candidate_ID = "PP_OrderLine_Candidate_ID";

	/**
	 * Set Produktionsstätte.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPP_Plant_ID (int PP_Plant_ID);

	/**
	 * Get Produktionsstätte.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPP_Plant_ID();

	@Nullable org.compiere.model.I_S_Resource getPP_Plant();

	void setPP_Plant(@Nullable org.compiere.model.I_S_Resource PP_Plant);

	ModelColumn<I_MD_Candidate_Prod_Detail, org.compiere.model.I_S_Resource> COLUMN_PP_Plant_ID = new ModelColumn<>(I_MD_Candidate_Prod_Detail.class, "PP_Plant_ID", org.compiere.model.I_S_Resource.class);
	String COLUMNNAME_PP_Plant_ID = "PP_Plant_ID";

	/**
	 * Set BOM Line.
	 * BOM Line
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPP_Product_BOMLine_ID (int PP_Product_BOMLine_ID);

	/**
	 * Get BOM Line.
	 * BOM Line
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPP_Product_BOMLine_ID();

	@Nullable org.eevolution.model.I_PP_Product_BOMLine getPP_Product_BOMLine();

	void setPP_Product_BOMLine(@Nullable org.eevolution.model.I_PP_Product_BOMLine PP_Product_BOMLine);

	ModelColumn<I_MD_Candidate_Prod_Detail, org.eevolution.model.I_PP_Product_BOMLine> COLUMN_PP_Product_BOMLine_ID = new ModelColumn<>(I_MD_Candidate_Prod_Detail.class, "PP_Product_BOMLine_ID", org.eevolution.model.I_PP_Product_BOMLine.class);
	String COLUMNNAME_PP_Product_BOMLine_ID = "PP_Product_BOMLine_ID";

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

	ModelColumn<I_MD_Candidate_Prod_Detail, org.eevolution.model.I_PP_Product_Planning> COLUMN_PP_Product_Planning_ID = new ModelColumn<>(I_MD_Candidate_Prod_Detail.class, "PP_Product_Planning_ID", org.eevolution.model.I_PP_Product_Planning.class);
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

	ModelColumn<I_MD_Candidate_Prod_Detail, Object> COLUMN_Updated = new ModelColumn<>(I_MD_Candidate_Prod_Detail.class, "Updated", null);
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
