package de.metas.materialtracking.ch.lagerkonf.model;

import org.adempiere.model.ModelColumn;

import java.math.BigDecimal;

/** Generated Interface for M_QualityInsp_LagerKonf_AdditionalFee
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_QualityInsp_LagerKonf_AdditionalFee 
{

	String Table_Name = "M_QualityInsp_LagerKonf_AdditionalFee";

//	/** AD_Table_ID=540619 */
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
	 * Set Beitrag pro Einheit.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAdditional_Fee_Amt_Per_UOM (BigDecimal Additional_Fee_Amt_Per_UOM);

	/**
	 * Get Beitrag pro Einheit.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getAdditional_Fee_Amt_Per_UOM();

	ModelColumn<I_M_QualityInsp_LagerKonf_AdditionalFee, Object> COLUMN_Additional_Fee_Amt_Per_UOM = new ModelColumn<>(I_M_QualityInsp_LagerKonf_AdditionalFee.class, "Additional_Fee_Amt_Per_UOM", null);
	String COLUMNNAME_Additional_Fee_Amt_Per_UOM = "Additional_Fee_Amt_Per_UOM";

	/**
	 * Set Apply Fee To.
	 * Apply Fee To Quality Inspection Line Type
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setApplyFeeTo (String ApplyFeeTo);

	/**
	 * Get Apply Fee To.
	 * Apply Fee To Quality Inspection Line Type
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	String getApplyFeeTo();

	ModelColumn<I_M_QualityInsp_LagerKonf_AdditionalFee, Object> COLUMN_ApplyFeeTo = new ModelColumn<>(I_M_QualityInsp_LagerKonf_AdditionalFee.class, "ApplyFeeTo", null);
	String COLUMNNAME_ApplyFeeTo = "ApplyFeeTo";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_QualityInsp_LagerKonf_AdditionalFee, Object> COLUMN_Created = new ModelColumn<>(I_M_QualityInsp_LagerKonf_AdditionalFee.class, "Created", null);
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

	ModelColumn<I_M_QualityInsp_LagerKonf_AdditionalFee, Object> COLUMN_IsActive = new ModelColumn<>(I_M_QualityInsp_LagerKonf_AdditionalFee.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

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
	 * Set Zusätzlicher Beitrag.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_QualityInsp_LagerKonf_AdditionalFee_ID (int M_QualityInsp_LagerKonf_AdditionalFee_ID);

	/**
	 * Get Zusätzlicher Beitrag.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_QualityInsp_LagerKonf_AdditionalFee_ID();

	ModelColumn<I_M_QualityInsp_LagerKonf_AdditionalFee, Object> COLUMN_M_QualityInsp_LagerKonf_AdditionalFee_ID = new ModelColumn<>(I_M_QualityInsp_LagerKonf_AdditionalFee.class, "M_QualityInsp_LagerKonf_AdditionalFee_ID", null);
	String COLUMNNAME_M_QualityInsp_LagerKonf_AdditionalFee_ID = "M_QualityInsp_LagerKonf_AdditionalFee_ID";

	/**
	 * Set Lagerkonferenz-Version.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_QualityInsp_LagerKonf_Version_ID (int M_QualityInsp_LagerKonf_Version_ID);

	/**
	 * Get Lagerkonferenz-Version.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_QualityInsp_LagerKonf_Version_ID();

	I_M_QualityInsp_LagerKonf_Version getM_QualityInsp_LagerKonf_Version();

	void setM_QualityInsp_LagerKonf_Version(I_M_QualityInsp_LagerKonf_Version M_QualityInsp_LagerKonf_Version);

	ModelColumn<I_M_QualityInsp_LagerKonf_AdditionalFee, I_M_QualityInsp_LagerKonf_Version> COLUMN_M_QualityInsp_LagerKonf_Version_ID = new ModelColumn<>(I_M_QualityInsp_LagerKonf_AdditionalFee.class, "M_QualityInsp_LagerKonf_Version_ID", I_M_QualityInsp_LagerKonf_Version.class);
	String COLUMNNAME_M_QualityInsp_LagerKonf_Version_ID = "M_QualityInsp_LagerKonf_Version_ID";

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

	ModelColumn<I_M_QualityInsp_LagerKonf_AdditionalFee, Object> COLUMN_SeqNo = new ModelColumn<>(I_M_QualityInsp_LagerKonf_AdditionalFee.class, "SeqNo", null);
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

	ModelColumn<I_M_QualityInsp_LagerKonf_AdditionalFee, Object> COLUMN_Updated = new ModelColumn<>(I_M_QualityInsp_LagerKonf_AdditionalFee.class, "Updated", null);
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
