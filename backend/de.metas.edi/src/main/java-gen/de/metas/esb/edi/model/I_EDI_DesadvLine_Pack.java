package de.metas.esb.edi.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for EDI_DesadvLine_Pack
 *  @author metasfresh (generated) 
 */
public interface I_EDI_DesadvLine_Pack 
{

	String Table_Name = "EDI_DesadvLine_Pack";

//	/** AD_Table_ID=540676 */
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
	 * Set Best before date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBestBeforeDate (@Nullable java.sql.Timestamp BestBeforeDate);

	/**
	 * Get Best before date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getBestBeforeDate();

	ModelColumn<I_EDI_DesadvLine_Pack, Object> COLUMN_BestBeforeDate = new ModelColumn<>(I_EDI_DesadvLine_Pack.class, "BestBeforeDate", null);
	String COLUMNNAME_BestBeforeDate = "BestBeforeDate";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_EDI_DesadvLine_Pack, Object> COLUMN_Created = new ModelColumn<>(I_EDI_DesadvLine_Pack.class, "Created", null);
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
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get UOM.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_UOM_ID();

	String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

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

	ModelColumn<I_EDI_DesadvLine_Pack, de.metas.esb.edi.model.I_EDI_Desadv> COLUMN_EDI_Desadv_ID = new ModelColumn<>(I_EDI_DesadvLine_Pack.class, "EDI_Desadv_ID", de.metas.esb.edi.model.I_EDI_Desadv.class);
	String COLUMNNAME_EDI_Desadv_ID = "EDI_Desadv_ID";

	/**
	 * Set DESADV Line.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setEDI_DesadvLine_ID (int EDI_DesadvLine_ID);

	/**
	 * Get DESADV Line.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getEDI_DesadvLine_ID();

	de.metas.esb.edi.model.I_EDI_DesadvLine getEDI_DesadvLine();

	void setEDI_DesadvLine(de.metas.esb.edi.model.I_EDI_DesadvLine EDI_DesadvLine);

	ModelColumn<I_EDI_DesadvLine_Pack, de.metas.esb.edi.model.I_EDI_DesadvLine> COLUMN_EDI_DesadvLine_ID = new ModelColumn<>(I_EDI_DesadvLine_Pack.class, "EDI_DesadvLine_ID", de.metas.esb.edi.model.I_EDI_DesadvLine.class);
	String COLUMNNAME_EDI_DesadvLine_ID = "EDI_DesadvLine_ID";

	/**
	 * Set EDI Lieferavis Pack (DESADV).
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setEDI_DesadvLine_Pack_ID (int EDI_DesadvLine_Pack_ID);

	/**
	 * Get EDI Lieferavis Pack (DESADV).
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getEDI_DesadvLine_Pack_ID();

	ModelColumn<I_EDI_DesadvLine_Pack, Object> COLUMN_EDI_DesadvLine_Pack_ID = new ModelColumn<>(I_EDI_DesadvLine_Pack.class, "EDI_DesadvLine_Pack_ID", null);
	String COLUMNNAME_EDI_DesadvLine_Pack_ID = "EDI_DesadvLine_Pack_ID";

	/**
	 * Set LU Gebinde-GTIN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setGTIN_LU_PackingMaterial (@Nullable java.lang.String GTIN_LU_PackingMaterial);

	/**
	 * Get LU Gebinde-GTIN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getGTIN_LU_PackingMaterial();

	ModelColumn<I_EDI_DesadvLine_Pack, Object> COLUMN_GTIN_LU_PackingMaterial = new ModelColumn<>(I_EDI_DesadvLine_Pack.class, "GTIN_LU_PackingMaterial", null);
	String COLUMNNAME_GTIN_LU_PackingMaterial = "GTIN_LU_PackingMaterial";

	/**
	 * Set TU Gebinde-GTIN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setGTIN_TU_PackingMaterial (@Nullable java.lang.String GTIN_TU_PackingMaterial);

	/**
	 * Get TU Gebinde-GTIN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getGTIN_TU_PackingMaterial();

	ModelColumn<I_EDI_DesadvLine_Pack, Object> COLUMN_GTIN_TU_PackingMaterial = new ModelColumn<>(I_EDI_DesadvLine_Pack.class, "GTIN_TU_PackingMaterial", null);
	String COLUMNNAME_GTIN_TU_PackingMaterial = "GTIN_TU_PackingMaterial";

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

	ModelColumn<I_EDI_DesadvLine_Pack, Object> COLUMN_IPA_SSCC18 = new ModelColumn<>(I_EDI_DesadvLine_Pack.class, "IPA_SSCC18", null);
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

	ModelColumn<I_EDI_DesadvLine_Pack, Object> COLUMN_IsActive = new ModelColumn<>(I_EDI_DesadvLine_Pack.class, "IsActive", null);
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

	ModelColumn<I_EDI_DesadvLine_Pack, Object> COLUMN_IsManual_IPA_SSCC18 = new ModelColumn<>(I_EDI_DesadvLine_Pack.class, "IsManual_IPA_SSCC18", null);
	String COLUMNNAME_IsManual_IPA_SSCC18 = "IsManual_IPA_SSCC18";

	/**
	 * Set Lot number.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLotNumber (@Nullable java.lang.String LotNumber);

	/**
	 * Get Lot number.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getLotNumber();

	ModelColumn<I_EDI_DesadvLine_Pack, Object> COLUMN_LotNumber = new ModelColumn<>(I_EDI_DesadvLine_Pack.class, "LotNumber", null);
	String COLUMNNAME_LotNumber = "LotNumber";

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

	ModelColumn<I_EDI_DesadvLine_Pack, Object> COLUMN_M_HU_ID = new ModelColumn<>(I_EDI_DesadvLine_Pack.class, "M_HU_ID", null);
	String COLUMNNAME_M_HU_ID = "M_HU_ID";

	/**
	 * Set LU packaging code.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_HU_PackagingCode_LU_ID (int M_HU_PackagingCode_LU_ID);

	/**
	 * Get LU packaging code.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_HU_PackagingCode_LU_ID();

	ModelColumn<I_EDI_DesadvLine_Pack, Object> COLUMN_M_HU_PackagingCode_LU_ID = new ModelColumn<>(I_EDI_DesadvLine_Pack.class, "M_HU_PackagingCode_LU_ID", null);
	String COLUMNNAME_M_HU_PackagingCode_LU_ID = "M_HU_PackagingCode_LU_ID";

	/**
	 * Set M_HU_PackagingCode_LU_Text.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setM_HU_PackagingCode_LU_Text (@Nullable java.lang.String M_HU_PackagingCode_LU_Text);

	/**
	 * Get M_HU_PackagingCode_LU_Text.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	@Nullable java.lang.String getM_HU_PackagingCode_LU_Text();

	ModelColumn<I_EDI_DesadvLine_Pack, Object> COLUMN_M_HU_PackagingCode_LU_Text = new ModelColumn<>(I_EDI_DesadvLine_Pack.class, "M_HU_PackagingCode_LU_Text", null);
	String COLUMNNAME_M_HU_PackagingCode_LU_Text = "M_HU_PackagingCode_LU_Text";

	/**
	 * Set TU packaging code.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_HU_PackagingCode_TU_ID (int M_HU_PackagingCode_TU_ID);

	/**
	 * Get TU packaging code.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_HU_PackagingCode_TU_ID();

	ModelColumn<I_EDI_DesadvLine_Pack, Object> COLUMN_M_HU_PackagingCode_TU_ID = new ModelColumn<>(I_EDI_DesadvLine_Pack.class, "M_HU_PackagingCode_TU_ID", null);
	String COLUMNNAME_M_HU_PackagingCode_TU_ID = "M_HU_PackagingCode_TU_ID";

	/**
	 * Set M_HU_PackagingCode_TU_Text.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setM_HU_PackagingCode_TU_Text (@Nullable java.lang.String M_HU_PackagingCode_TU_Text);

	/**
	 * Get M_HU_PackagingCode_TU_Text.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	@Nullable java.lang.String getM_HU_PackagingCode_TU_Text();

	ModelColumn<I_EDI_DesadvLine_Pack, Object> COLUMN_M_HU_PackagingCode_TU_Text = new ModelColumn<>(I_EDI_DesadvLine_Pack.class, "M_HU_PackagingCode_TU_Text", null);
	String COLUMNNAME_M_HU_PackagingCode_TU_Text = "M_HU_PackagingCode_TU_Text";

	/**
	 * Set Shipment/ Receipt.
	 * Material Shipment Document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_InOut_ID (int M_InOut_ID);

	/**
	 * Get Shipment/ Receipt.
	 * Material Shipment Document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_InOut_ID();

	@Nullable org.compiere.model.I_M_InOut getM_InOut();

	void setM_InOut(@Nullable org.compiere.model.I_M_InOut M_InOut);

	ModelColumn<I_EDI_DesadvLine_Pack, org.compiere.model.I_M_InOut> COLUMN_M_InOut_ID = new ModelColumn<>(I_EDI_DesadvLine_Pack.class, "M_InOut_ID", org.compiere.model.I_M_InOut.class);
	String COLUMNNAME_M_InOut_ID = "M_InOut_ID";

	/**
	 * Set Receipt Line.
	 * Line on Receipt document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_InOutLine_ID (int M_InOutLine_ID);

	/**
	 * Get Receipt Line.
	 * Line on Receipt document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_InOutLine_ID();

	@Nullable org.compiere.model.I_M_InOutLine getM_InOutLine();

	void setM_InOutLine(@Nullable org.compiere.model.I_M_InOutLine M_InOutLine);

	ModelColumn<I_EDI_DesadvLine_Pack, org.compiere.model.I_M_InOutLine> COLUMN_M_InOutLine_ID = new ModelColumn<>(I_EDI_DesadvLine_Pack.class, "M_InOutLine_ID", org.compiere.model.I_M_InOutLine.class);
	String COLUMNNAME_M_InOutLine_ID = "M_InOutLine_ID";

	/**
	 * Set Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMovementQty (BigDecimal MovementQty);

	/**
	 * Get Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getMovementQty();

	ModelColumn<I_EDI_DesadvLine_Pack, Object> COLUMN_MovementQty = new ModelColumn<>(I_EDI_DesadvLine_Pack.class, "MovementQty", null);
	String COLUMNNAME_MovementQty = "MovementQty";

	/**
	 * Set Qty CU per TU.
	 * Number of CUs per package (usually TU)
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyCU (@Nullable BigDecimal QtyCU);

	/**
	 * Get Qty CU per TU.
	 * Number of CUs per package (usually TU)
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyCU();

	ModelColumn<I_EDI_DesadvLine_Pack, Object> COLUMN_QtyCU = new ModelColumn<>(I_EDI_DesadvLine_Pack.class, "QtyCU", null);
	String COLUMNNAME_QtyCU = "QtyCU";

	/**
	 * Set Menge CU/LU.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyCUsPerLU (@Nullable BigDecimal QtyCUsPerLU);

	/**
	 * Get Menge CU/LU.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyCUsPerLU();

	ModelColumn<I_EDI_DesadvLine_Pack, Object> COLUMN_QtyCUsPerLU = new ModelColumn<>(I_EDI_DesadvLine_Pack.class, "QtyCUsPerLU", null);
	String COLUMNNAME_QtyCUsPerLU = "QtyCUsPerLU";

	/**
	 * Set Packaging capacity.
	 * Capacity in the respective product's unit of measuerement
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyItemCapacity (@Nullable BigDecimal QtyItemCapacity);

	/**
	 * Get Packaging capacity.
	 * Capacity in the respective product's unit of measuerement
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyItemCapacity();

	ModelColumn<I_EDI_DesadvLine_Pack, Object> COLUMN_QtyItemCapacity = new ModelColumn<>(I_EDI_DesadvLine_Pack.class, "QtyItemCapacity", null);
	String COLUMNNAME_QtyItemCapacity = "QtyItemCapacity";

	/**
	 * Set Number of TUs.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyTU (int QtyTU);

	/**
	 * Get Number of TUs.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getQtyTU();

	ModelColumn<I_EDI_DesadvLine_Pack, Object> COLUMN_QtyTU = new ModelColumn<>(I_EDI_DesadvLine_Pack.class, "QtyTU", null);
	String COLUMNNAME_QtyTU = "QtyTU";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_EDI_DesadvLine_Pack, Object> COLUMN_Updated = new ModelColumn<>(I_EDI_DesadvLine_Pack.class, "Updated", null);
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
