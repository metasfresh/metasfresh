/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2022 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.esb.edi.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for EDI_Desadv_Pack_Item
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_EDI_Desadv_Pack_Item 
{

	String Table_Name = "EDI_Desadv_Pack_Item";

//	/** AD_Table_ID=542171 */
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

	ModelColumn<I_EDI_Desadv_Pack_Item, Object> COLUMN_BestBeforeDate = new ModelColumn<>(I_EDI_Desadv_Pack_Item.class, "BestBeforeDate", null);
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

	ModelColumn<I_EDI_Desadv_Pack_Item, Object> COLUMN_Created = new ModelColumn<>(I_EDI_Desadv_Pack_Item.class, "Created", null);
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
	 * Set EDI_Desadv_Pack.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setEDI_Desadv_Pack_ID (int EDI_Desadv_Pack_ID);

	/**
	 * Get EDI_Desadv_Pack.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getEDI_Desadv_Pack_ID();

	I_EDI_Desadv_Pack getEDI_Desadv_Pack();

	void setEDI_Desadv_Pack(I_EDI_Desadv_Pack EDI_Desadv_Pack);

	ModelColumn<I_EDI_Desadv_Pack_Item, I_EDI_Desadv_Pack> COLUMN_EDI_Desadv_Pack_ID = new ModelColumn<>(I_EDI_Desadv_Pack_Item.class, "EDI_Desadv_Pack_ID", I_EDI_Desadv_Pack.class);
	String COLUMNNAME_EDI_Desadv_Pack_ID = "EDI_Desadv_Pack_ID";

	/**
	 * Set EDI_Desadv_Pack_Item.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setEDI_Desadv_Pack_Item_ID (int EDI_Desadv_Pack_Item_ID);

	/**
	 * Get EDI_Desadv_Pack_Item.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getEDI_Desadv_Pack_Item_ID();

	ModelColumn<I_EDI_Desadv_Pack_Item, Object> COLUMN_EDI_Desadv_Pack_Item_ID = new ModelColumn<>(I_EDI_Desadv_Pack_Item.class, "EDI_Desadv_Pack_Item_ID", null);
	String COLUMNNAME_EDI_Desadv_Pack_Item_ID = "EDI_Desadv_Pack_Item_ID";

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

	I_EDI_DesadvLine getEDI_DesadvLine();

	void setEDI_DesadvLine(I_EDI_DesadvLine EDI_DesadvLine);

	ModelColumn<I_EDI_Desadv_Pack_Item, I_EDI_DesadvLine> COLUMN_EDI_DesadvLine_ID = new ModelColumn<>(I_EDI_Desadv_Pack_Item.class, "EDI_DesadvLine_ID", I_EDI_DesadvLine.class);
	String COLUMNNAME_EDI_DesadvLine_ID = "EDI_DesadvLine_ID";

	/**
	 * Set TU packaging-GTIN.
	 * GTIN des verwendeten Gebindes, z.B. Karton. Wird automatisch über die Packvorschrift aus den Produkt-Stammdaten zum jeweiligen Lieferempfänger ermittelt.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setGTIN_TU_PackingMaterial (@Nullable String GTIN_TU_PackingMaterial);

	/**
	 * Get TU packaging-GTIN.
	 * GTIN des verwendeten Gebindes, z.B. Karton. Wird automatisch über die Packvorschrift aus den Produkt-Stammdaten zum jeweiligen Lieferempfänger ermittelt.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getGTIN_TU_PackingMaterial();

	ModelColumn<I_EDI_Desadv_Pack_Item, Object> COLUMN_GTIN_TU_PackingMaterial = new ModelColumn<>(I_EDI_Desadv_Pack_Item.class, "GTIN_TU_PackingMaterial", null);
	String COLUMNNAME_GTIN_TU_PackingMaterial = "GTIN_TU_PackingMaterial";

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

	ModelColumn<I_EDI_Desadv_Pack_Item, Object> COLUMN_IsActive = new ModelColumn<>(I_EDI_Desadv_Pack_Item.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Lot number.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLotNumber (@Nullable String LotNumber);

	/**
	 * Get Lot number.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getLotNumber();

	ModelColumn<I_EDI_Desadv_Pack_Item, Object> COLUMN_LotNumber = new ModelColumn<>(I_EDI_Desadv_Pack_Item.class, "LotNumber", null);
	String COLUMNNAME_LotNumber = "LotNumber";

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

	ModelColumn<I_EDI_Desadv_Pack_Item, Object> COLUMN_M_HU_PackagingCode_TU_ID = new ModelColumn<>(I_EDI_Desadv_Pack_Item.class, "M_HU_PackagingCode_TU_ID", null);
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
	void setM_HU_PackagingCode_TU_Text (@Nullable String M_HU_PackagingCode_TU_Text);

	/**
	 * Get M_HU_PackagingCode_TU_Text.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	@Nullable String getM_HU_PackagingCode_TU_Text();

	ModelColumn<I_EDI_Desadv_Pack_Item, Object> COLUMN_M_HU_PackagingCode_TU_Text = new ModelColumn<>(I_EDI_Desadv_Pack_Item.class, "M_HU_PackagingCode_TU_Text", null);
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

	ModelColumn<I_EDI_Desadv_Pack_Item, org.compiere.model.I_M_InOut> COLUMN_M_InOut_ID = new ModelColumn<>(I_EDI_Desadv_Pack_Item.class, "M_InOut_ID", org.compiere.model.I_M_InOut.class);
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

	ModelColumn<I_EDI_Desadv_Pack_Item, org.compiere.model.I_M_InOutLine> COLUMN_M_InOutLine_ID = new ModelColumn<>(I_EDI_Desadv_Pack_Item.class, "M_InOutLine_ID", org.compiere.model.I_M_InOutLine.class);
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

	ModelColumn<I_EDI_Desadv_Pack_Item, Object> COLUMN_MovementQty = new ModelColumn<>(I_EDI_Desadv_Pack_Item.class, "MovementQty", null);
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

	ModelColumn<I_EDI_Desadv_Pack_Item, Object> COLUMN_QtyCU = new ModelColumn<>(I_EDI_Desadv_Pack_Item.class, "QtyCU", null);
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

	ModelColumn<I_EDI_Desadv_Pack_Item, Object> COLUMN_QtyCUsPerLU = new ModelColumn<>(I_EDI_Desadv_Pack_Item.class, "QtyCUsPerLU", null);
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

	ModelColumn<I_EDI_Desadv_Pack_Item, Object> COLUMN_QtyItemCapacity = new ModelColumn<>(I_EDI_Desadv_Pack_Item.class, "QtyItemCapacity", null);
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

	ModelColumn<I_EDI_Desadv_Pack_Item, Object> COLUMN_QtyTU = new ModelColumn<>(I_EDI_Desadv_Pack_Item.class, "QtyTU", null);
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

	ModelColumn<I_EDI_Desadv_Pack_Item, Object> COLUMN_Updated = new ModelColumn<>(I_EDI_Desadv_Pack_Item.class, "Updated", null);
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
