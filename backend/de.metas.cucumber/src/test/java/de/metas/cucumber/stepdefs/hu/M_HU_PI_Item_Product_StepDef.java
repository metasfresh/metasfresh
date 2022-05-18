/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber.stepdefs.hu;

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class M_HU_PI_Item_Product_StepDef
{
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final M_HU_PI_Item_StepDefData huPiItemTable;
	private final M_Product_StepDefData productTable;
	private final M_HU_PI_Item_Product_StepDefData huPiItemProductTable;

	public M_HU_PI_Item_Product_StepDef(
			@NonNull final M_HU_PI_Item_StepDefData huPiItemTable,
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final M_HU_PI_Item_Product_StepDefData huPiItemProductTable)
	{
		this.huPiItemTable = huPiItemTable;
		this.productTable = productTable;
		this.huPiItemProductTable = huPiItemProductTable;
	}

	@Given("metasfresh contains M_HU_PI_Item_Product:")
	public void metasfresh_contains_m_hu_pi_item_product(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			createHUPIItemProduct(tableRow);
		}
	}

	private void createHUPIItemProduct(@NonNull final Map<String, String> tableRow)
	{
		final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_HU_PI_Item_Product.COLUMNNAME_M_Product_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final BigDecimal qty = DataTableUtil.extractBigDecimalForColumnName(tableRow, I_M_HU_PI_Item_Product.COLUMNNAME_Qty);
		final Timestamp validFrom = DataTableUtil.extractDateTimestampForColumnName(tableRow, I_M_HU_PI_Item_Product.COLUMNNAME_ValidFrom);
		final boolean isInfiniteCapacity = DataTableUtil.extractBooleanForColumnName(tableRow, I_M_HU_PI_Item_Product.COLUMNNAME_IsInfiniteCapacity);
		final boolean isAllowAnyProduct = DataTableUtil.extractBooleanForColumnName(tableRow, I_M_HU_PI_Item_Product.COLUMNNAME_IsAllowAnyProduct);
		final String name = DataTableUtil.extractStringForColumnName(tableRow, I_M_HU_PI_Item_Product.COLUMNNAME_Name);
		final boolean isDefaultForProduct = DataTableUtil.extractBooleanForColumnName(tableRow, I_M_HU_PI_Item_Product.COLUMNNAME_IsDefaultForProduct);

		final String huPiItemIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_HU_PI_Item_Product.COLUMNNAME_M_HU_PI_Item_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final Integer huPiItemId = huPiItemTable.getOptional(huPiItemIdentifier)
				.map(I_M_HU_PI_Item::getM_HU_PI_Item_ID)
				.orElseGet(() -> Integer.parseInt(huPiItemIdentifier));

		final I_M_Product productRecord = productTable.get(productIdentifier);

		final String x12de355Code = DataTableUtil.extractStringForColumnName(tableRow, I_C_UOM.COLUMNNAME_C_UOM_ID + "." + X12DE355.class.getSimpleName());
		final UomId uomId = uomDAO.getUomIdByX12DE355(X12DE355.ofCode(x12de355Code));

		final int mhupiItemProductID = DataTableUtil.extractIntForColumnName(tableRow, I_M_HU_PI_Item_Product.COLUMNNAME_M_HU_PI_Item_Product_ID);

		final I_M_HU_PI_Item_Product huPiItemProductRecord = queryBL.createQueryBuilder(I_M_HU_PI_Item_Product.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_HU_PI_Item_Product.COLUMNNAME_M_HU_PI_Item_Product_ID, mhupiItemProductID)
				.create()
				.firstOnlyOptional(I_M_HU_PI_Item_Product.class)
				.orElseGet(() -> newInstance(I_M_HU_PI_Item_Product.class));

		huPiItemProductRecord.setM_HU_PI_Item_Product_ID(mhupiItemProductID);
		huPiItemProductRecord.setM_Product_ID(productRecord.getM_Product_ID());
		huPiItemProductRecord.setM_HU_PI_Item_ID(huPiItemId);
		huPiItemProductRecord.setC_UOM_ID(uomId.getRepoId());
		huPiItemProductRecord.setQty(qty);
		huPiItemProductRecord.setValidFrom(validFrom);
		huPiItemProductRecord.setIsInfiniteCapacity(isInfiniteCapacity);
		huPiItemProductRecord.setIsAllowAnyProduct(isAllowAnyProduct);
		huPiItemProductRecord.setName(name);
		huPiItemProductRecord.setIsDefaultForProduct(isDefaultForProduct);

		saveRecord(huPiItemProductRecord);

		huPiItemProductTable.put(DataTableUtil.extractRecordIdentifier(tableRow, I_M_HU_PI_Item_Product.COLUMNNAME_M_HU_PI_Item_Product_ID), huPiItemProductRecord);
	}
}