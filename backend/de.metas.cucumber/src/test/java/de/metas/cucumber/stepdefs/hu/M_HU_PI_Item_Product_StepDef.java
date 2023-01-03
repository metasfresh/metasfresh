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

import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_HU_PackagingCode_StepDefData;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.PCE_UOM_ID;
import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.handlingunits.model.I_M_HU_PI_Item_Product.COLUMNNAME_GTIN;
import static de.metas.handlingunits.model.I_M_HU_PI_Item_Product.COLUMNNAME_GTIN_LU_PackingMaterial_Fallback;
import static de.metas.handlingunits.model.I_M_HU_PI_Item_Product.COLUMNNAME_M_HU_PI_Item_ID;
import static de.metas.handlingunits.model.I_M_HU_PI_Item_Product.COLUMNNAME_M_HU_PI_Item_Product_ID;
import static de.metas.handlingunits.model.I_M_HU_PI_Item_Product.COLUMNNAME_M_HU_PackagingCode_LU_Fallback_ID;
import static de.metas.handlingunits.model.I_M_HU_PI_Item_Product.COLUMNNAME_M_Product_ID;
import static de.metas.handlingunits.model.I_M_HU_PI_Item_Product.COLUMNNAME_Qty;
import static de.metas.handlingunits.model.I_M_HU_PI_Item_Product.COLUMNNAME_ValidFrom;
import static org.adempiere.model.InterfaceWrapperHelper.COLUMNNAME_IsActive;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

public class M_HU_PI_Item_Product_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final M_Product_StepDefData productTable;
	private final M_HU_PI_Item_StepDefData huPiItemTable;
	private final M_HU_PI_Item_Product_StepDefData huPiItemProductTable;
	private final M_HU_PackagingCode_StepDefData huPackagingCodeTable;

	public M_HU_PI_Item_Product_StepDef(
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final M_HU_PI_Item_StepDefData huPiItemTable,
			@NonNull final M_HU_PI_Item_Product_StepDefData huPiItemProductTable,
			@NonNull final M_HU_PackagingCode_StepDefData huPackagingCodeTable)
	{
		this.productTable = productTable;
		this.huPiItemTable = huPiItemTable;
		this.huPiItemProductTable = huPiItemProductTable;
		this.huPackagingCodeTable = huPackagingCodeTable;
	}

	@And("metasfresh contains M_HU_PI_Item_Product:")
	public void add_M_HU_PI_Item_Product(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			final String productIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
			final int productID = productTable.get(productIdentifier).getM_Product_ID();

			final String huPiItemIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_HU_PI_Item_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_HU_PI_Item huPiItem = huPiItemTable.get(huPiItemIdentifier);

			final BigDecimal qty = DataTableUtil.extractBigDecimalForColumnName(row, COLUMNNAME_Qty);
			final Timestamp validFrom = DataTableUtil.extractDateTimestampForColumnName(row, COLUMNNAME_ValidFrom);
			final boolean active = DataTableUtil.extractBooleanForColumnNameOr(row, COLUMNNAME_IsActive, true);

			final I_M_HU_PI_Item_Product existingHuPiItemProduct = queryBL.createQueryBuilder(I_M_HU_PI_Item_Product.class)
					.addEqualsFilter(COLUMNNAME_M_Product_ID, productID)
					.addEqualsFilter(COLUMNNAME_M_HU_PI_Item_ID, huPiItem.getM_HU_PI_Item_ID())
					.addEqualsFilter(COLUMNNAME_Qty, qty)
					.addEqualsFilter(COLUMNNAME_IsActive, active)
					.create()
					.firstOnlyOrNull(I_M_HU_PI_Item_Product.class);

			final I_M_HU_PI_Item_Product huPiItemProductRecord = CoalesceUtil.coalesceSuppliers(() -> existingHuPiItemProduct,
																								() -> InterfaceWrapperHelper.newInstance(I_M_HU_PI_Item_Product.class));
			assertThat(huPiItemProductRecord).isNotNull();

			huPiItemProductRecord.setM_Product_ID(productID);
			huPiItemProductRecord.setM_HU_PI_Item_ID(huPiItem.getM_HU_PI_Item_ID());
			huPiItemProductRecord.setQty(qty);
			huPiItemProductRecord.setValidFrom(validFrom);
			huPiItemProductRecord.setC_UOM_ID(PCE_UOM_ID.getRepoId());
			huPiItemProductRecord.setIsActive(active);

			final String huPackagingCodeLUFallbackIdentifier = DataTableUtil.extractNullableStringForColumnName(row, "OPT." + COLUMNNAME_M_HU_PackagingCode_LU_Fallback_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(huPackagingCodeLUFallbackIdentifier))
			{
				final int huPackagingCodeId = DataTableUtil.nullToken2Null(huPackagingCodeLUFallbackIdentifier) == null
						? -1
						: huPackagingCodeTable.get(huPackagingCodeLUFallbackIdentifier).getM_HU_PackagingCode_ID();

				huPiItemProductRecord.setM_HU_PackagingCode_LU_Fallback_ID(huPackagingCodeId);
			}

			final String gtinLuPackagingMaterialFallback = DataTableUtil.extractNullableStringForColumnName(row, "OPT." + COLUMNNAME_GTIN_LU_PackingMaterial_Fallback);
			if (Check.isNotBlank(gtinLuPackagingMaterialFallback))
			{
				huPiItemProductRecord.setGTIN_LU_PackingMaterial_Fallback(DataTableUtil.nullToken2Null(gtinLuPackagingMaterialFallback));
			}

			saveRecord(huPiItemProductRecord);

			final String huPiItemProductIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_HU_PI_Item_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
			huPiItemProductTable.put(huPiItemProductIdentifier, huPiItemProductRecord);
		}
	}

	@And("update M_HU_PI_Item_Product:")
	public void update_M_HU_PI_Item_Product(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			updateItemProduct(row);
		}
	}

	private void updateItemProduct(@NonNull final Map<String, String> row)
	{
		final String itemProductIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_HU_PI_Item_Product_ID + "." + TABLECOLUMN_IDENTIFIER);

		final Integer itemProductId = huPiItemProductTable.getOptional(itemProductIdentifier)
				.map(I_M_HU_PI_Item_Product::getM_HU_PI_Item_Product_ID)
				.orElseGet(() -> Integer.parseInt(itemProductIdentifier));

		final I_M_HU_PI_Item_Product mHuPiItemProductRecord = InterfaceWrapperHelper.load(itemProductId, I_M_HU_PI_Item_Product.class);

		final String gtin = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_GTIN);

		if (Check.isNotBlank(gtin))
		{
			mHuPiItemProductRecord.setGTIN(gtin);
		}

		saveRecord(mHuPiItemProductRecord);
	}
}
