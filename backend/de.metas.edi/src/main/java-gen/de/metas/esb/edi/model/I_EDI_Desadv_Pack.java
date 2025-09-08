package de.metas.esb.edi.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for EDI_Desadv_Pack
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_EDI_Desadv_Pack 
{

	String Table_Name = "EDI_Desadv_Pack";

//	/** AD_Table_ID=542170 */
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
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_EDI_Desadv_Pack, Object> COLUMN_Created = new ModelColumn<>(I_EDI_Desadv_Pack.class, "Created", null);
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
	 * Set DESADV.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setEDI_Desadv_ID (int EDI_Desadv_ID);

	/**
	 * Get DESADV.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getEDI_Desadv_ID();

	de.metas.esb.edi.model.I_EDI_Desadv getEDI_Desadv();

	void setEDI_Desadv(de.metas.esb.edi.model.I_EDI_Desadv EDI_Desadv);

	ModelColumn<I_EDI_Desadv_Pack, de.metas.esb.edi.model.I_EDI_Desadv> COLUMN_EDI_Desadv_ID = new ModelColumn<>(I_EDI_Desadv_Pack.class, "EDI_Desadv_ID", de.metas.esb.edi.model.I_EDI_Desadv.class);
	String COLUMNNAME_EDI_Desadv_ID = "EDI_Desadv_ID";

	/**
	 * Set EDI_Desadv_Pack.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setEDI_Desadv_Pack_ID (int EDI_Desadv_Pack_ID);

	/**
	 * Get EDI_Desadv_Pack.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getEDI_Desadv_Pack_ID();

	ModelColumn<I_EDI_Desadv_Pack, Object> COLUMN_EDI_Desadv_Pack_ID = new ModelColumn<>(I_EDI_Desadv_Pack.class, "EDI_Desadv_Pack_ID", null);
	String COLUMNNAME_EDI_Desadv_Pack_ID = "EDI_Desadv_Pack_ID";

	/**
	 * Set Parent Pack.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEDI_Desadv_Parent_Pack_ID (int EDI_Desadv_Parent_Pack_ID);

	/**
	 * Get Parent Pack.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getEDI_Desadv_Parent_Pack_ID();

	@Nullable de.metas.esb.edi.model.I_EDI_Desadv_Pack getEDI_Desadv_Parent_Pack();

	void setEDI_Desadv_Parent_Pack(@Nullable de.metas.esb.edi.model.I_EDI_Desadv_Pack EDI_Desadv_Parent_Pack);

	ModelColumn<I_EDI_Desadv_Pack, de.metas.esb.edi.model.I_EDI_Desadv_Pack> COLUMN_EDI_Desadv_Parent_Pack_ID = new ModelColumn<>(I_EDI_Desadv_Pack.class, "EDI_Desadv_Parent_Pack_ID", de.metas.esb.edi.model.I_EDI_Desadv_Pack.class);
	String COLUMNNAME_EDI_Desadv_Parent_Pack_ID = "EDI_Desadv_Parent_Pack_ID";

	/**
	 * Set Packingmaterial-GTIN.
	 * GTIN of the container used, e.g. pallet. Is determined automatically via the packing instruction from the product master data for the respective delivery recipient.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setGTIN_PackingMaterial (@Nullable java.lang.String GTIN_PackingMaterial);

	/**
	 * Get Packingmaterial-GTIN.
	 * GTIN of the container used, e.g. pallet. Is determined automatically via the packing instruction from the product master data for the respective delivery recipient.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getGTIN_PackingMaterial();

	ModelColumn<I_EDI_Desadv_Pack, Object> COLUMN_GTIN_PackingMaterial = new ModelColumn<>(I_EDI_Desadv_Pack.class, "GTIN_PackingMaterial", null);
	String COLUMNNAME_GTIN_PackingMaterial = "GTIN_PackingMaterial";

	/**
	 * Set SSCC18.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIPA_SSCC18 (java.lang.String IPA_SSCC18);

	/**
	 * Get SSCC18.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getIPA_SSCC18();

	ModelColumn<I_EDI_Desadv_Pack, Object> COLUMN_IPA_SSCC18 = new ModelColumn<>(I_EDI_Desadv_Pack.class, "IPA_SSCC18", null);
	String COLUMNNAME_IPA_SSCC18 = "IPA_SSCC18";

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

	ModelColumn<I_EDI_Desadv_Pack, Object> COLUMN_IsActive = new ModelColumn<>(I_EDI_Desadv_Pack.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set manual SSCC18.
	 * metasfresh ticks this flag if there is no HU assigned and the user can manually provide a SSCC18 value.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsManual_IPA_SSCC18 (boolean IsManual_IPA_SSCC18);

	/**
	 * Get manual SSCC18.
	 * metasfresh ticks this flag if there is no HU assigned and the user can manually provide a SSCC18 value.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isManual_IPA_SSCC18();

	ModelColumn<I_EDI_Desadv_Pack, Object> COLUMN_IsManual_IPA_SSCC18 = new ModelColumn<>(I_EDI_Desadv_Pack.class, "IsManual_IPA_SSCC18", null);
	String COLUMNNAME_IsManual_IPA_SSCC18 = "IsManual_IPA_SSCC18";

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

	ModelColumn<I_EDI_Desadv_Pack, Object> COLUMN_M_HU_ID = new ModelColumn<>(I_EDI_Desadv_Pack.class, "M_HU_ID", null);
	String COLUMNNAME_M_HU_ID = "M_HU_ID";

	/**
	 * Set Packaging code.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_HU_PackagingCode_ID (int M_HU_PackagingCode_ID);

	/**
	 * Get Packaging code.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_HU_PackagingCode_ID();

	ModelColumn<I_EDI_Desadv_Pack, Object> COLUMN_M_HU_PackagingCode_ID = new ModelColumn<>(I_EDI_Desadv_Pack.class, "M_HU_PackagingCode_ID", null);
	String COLUMNNAME_M_HU_PackagingCode_ID = "M_HU_PackagingCode_ID";

	/**
	 * Set M_HU_PackagingCode_Text.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setM_HU_PackagingCode_Text (@Nullable java.lang.String M_HU_PackagingCode_Text);

	/**
	 * Get M_HU_PackagingCode_Text.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	@Nullable java.lang.String getM_HU_PackagingCode_Text();

	ModelColumn<I_EDI_Desadv_Pack, Object> COLUMN_M_HU_PackagingCode_Text = new ModelColumn<>(I_EDI_Desadv_Pack.class, "M_HU_PackagingCode_Text", null);
	String COLUMNNAME_M_HU_PackagingCode_Text = "M_HU_PackagingCode_Text";

	/**
	 * Set Shipment/ Receipt.
	 * Material Shipment Document
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setM_InOut_ID (int M_InOut_ID);

	/**
	 * Get Shipment/ Receipt.
	 * Material Shipment Document
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	int getM_InOut_ID();

	@Deprecated
	@Nullable org.compiere.model.I_M_InOut getM_InOut();

	@Deprecated
	void setM_InOut(@Nullable org.compiere.model.I_M_InOut M_InOut);

	ModelColumn<I_EDI_Desadv_Pack, org.compiere.model.I_M_InOut> COLUMN_M_InOut_ID = new ModelColumn<>(I_EDI_Desadv_Pack.class, "M_InOut_ID", org.compiere.model.I_M_InOut.class);
	String COLUMNNAME_M_InOut_ID = "M_InOut_ID";

	/**
	 * Set SeqNo..
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSeqNo (int SeqNo);

	/**
	 * Get SeqNo..
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getSeqNo();

	ModelColumn<I_EDI_Desadv_Pack, Object> COLUMN_SeqNo = new ModelColumn<>(I_EDI_Desadv_Pack.class, "SeqNo", null);
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

	ModelColumn<I_EDI_Desadv_Pack, Object> COLUMN_Updated = new ModelColumn<>(I_EDI_Desadv_Pack.class, "Updated", null);
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
