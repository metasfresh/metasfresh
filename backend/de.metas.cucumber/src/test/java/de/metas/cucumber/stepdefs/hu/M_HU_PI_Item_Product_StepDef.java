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
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_HU_PackagingCode_StepDefData;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.PCE_UOM_ID;
import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.handlingunits.model.I_M_HU_PI_Item_Product.COLUMNNAME_GTIN;
import static de.metas.handlingunits.model.I_M_HU_PI_Item_Product.COLUMNNAME_GTIN_LU_PackingMaterial_Fallback;
import static de.metas.handlingunits.model.I_M_HU_PI_Item_Product.COLUMNNAME_M_HU_PI_Item_Product_ID;
import static de.metas.handlingunits.model.I_M_HU_PI_Item_Product.COLUMNNAME_M_HU_PackagingCode_LU_Fallback_ID;
import static org.adempiere.model.InterfaceWrapperHelper.COLUMNNAME_IsActive;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

public class M_HU_PI_Item_Product_StepDef
{
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final M_Product_StepDefData productTable;
	private final M_HU_PI_Item_StepDefData huPiItemTable;
	private final M_HU_PI_Item_Product_StepDefData huPiItemProductTable;
	private final M_HU_PackagingCode_StepDefData huPackagingCodeTable;
	private final C_BPartner_StepDefData bpartnerTable;

	public M_HU_PI_Item_Product_StepDef(
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final M_HU_PI_Item_StepDefData huPiItemTable,
			@NonNull final M_HU_PI_Item_Product_StepDefData huPiItemProductTable,
			@NonNull final M_HU_PackagingCode_StepDefData huPackagingCodeTable,
			@NonNull final C_BPartner_StepDefData bpartnerTable)
	{
		this.productTable = productTable;
		this.huPiItemTable = huPiItemTable;
		this.huPiItemProductTable = huPiItemProductTable;
		this.huPackagingCodeTable = huPackagingCodeTable;
		this.bpartnerTable = bpartnerTable;
	}

	@Given("metasfresh contains M_HU_PI_Item_Product:")
	public void metasfresh_contains_m_hu_pi_item_product(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> tableRow : rows)
		{
			createHUPIItemProduct(tableRow);
		}
	}

	private void createHUPIItemProduct(@NonNull final Map<String, String> tableRow)
		{
		final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_HU_PI_Item_Product.COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
		final BigDecimal qty = DataTableUtil.extractBigDecimalForColumnName(tableRow, I_M_HU_PI_Item_Product.COLUMNNAME_Qty);
		final Timestamp validFrom = DataTableUtil.extractDateTimestampForColumnName(tableRow, I_M_HU_PI_Item_Product.COLUMNNAME_ValidFrom);
		final Boolean isInfiniteCapacity = DataTableUtil.extractBooleanForColumnNameOr(tableRow, "OPT." + I_M_HU_PI_Item_Product.COLUMNNAME_IsInfiniteCapacity, false);
		final Boolean isAllowAnyProduct = DataTableUtil.extractBooleanForColumnNameOr(tableRow, "OPT." + I_M_HU_PI_Item_Product.COLUMNNAME_IsAllowAnyProduct, false);
		final String name = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_M_HU_PI_Item_Product.COLUMNNAME_Name);
		final Boolean isDefaultForProduct = DataTableUtil.extractBooleanForColumnNameOr(tableRow, "OPT." + I_M_HU_PI_Item_Product.COLUMNNAME_IsDefaultForProduct, false);
		final Boolean active = DataTableUtil.extractBooleanForColumnNameOr(tableRow, COLUMNNAME_IsActive, true);

		final String huPiItemIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_HU_PI_Item_Product.COLUMNNAME_M_HU_PI_Item_ID + "." + TABLECOLUMN_IDENTIFIER);
		final Integer huPiItemId = huPiItemTable.getOptional(huPiItemIdentifier)
				.map(I_M_HU_PI_Item::getM_HU_PI_Item_ID)
				.orElseGet(() -> Integer.parseInt(huPiItemIdentifier));

		final I_M_Product productRecord = productTable.get(productIdentifier);

		final String x12de355Code = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_UOM.COLUMNNAME_C_UOM_ID + "." + X12DE355.class.getSimpleName());

		final Integer mhupiItemProductID = DataTableUtil.extractIntegerOrNullForColumnName(tableRow, "OPT." + COLUMNNAME_M_HU_PI_Item_Product_ID);

		final I_M_HU_PI_Item_Product existingHuPiItemProduct;

		if (mhupiItemProductID != null)
		{
			existingHuPiItemProduct = queryBL.createQueryBuilder(I_M_HU_PI_Item_Product.class)
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(COLUMNNAME_M_HU_PI_Item_Product_ID, mhupiItemProductID)
					.create()
					.firstOnlyOrNull(I_M_HU_PI_Item_Product.class);

		}
		else
		{
			existingHuPiItemProduct = queryBL.createQueryBuilder(I_M_HU_PI_Item_Product.class)
					.addEqualsFilter(I_M_HU_PI_Item_Product.COLUMNNAME_M_Product_ID, productRecord.getM_Product_ID())
					.addEqualsFilter(I_M_HU_PI_Item_Product.COLUMNNAME_M_HU_PI_Item_ID, huPiItemId)
					.addEqualsFilter(I_M_HU_PI_Item_Product.COLUMNNAME_Qty, qty)
					.addEqualsFilter(COLUMNNAME_IsActive, active)
					.create()
					.firstOnlyOrNull(I_M_HU_PI_Item_Product.class);
		}

			final I_M_HU_PI_Item_Product huPiItemProductRecord = CoalesceUtil.coalesceSuppliers(() -> existingHuPiItemProduct,
																								() -> InterfaceWrapperHelper.newInstance(I_M_HU_PI_Item_Product.class));
			assertThat(huPiItemProductRecord).isNotNull();

		if (mhupiItemProductID != null)
		{
			huPiItemProductRecord.setM_HU_PI_Item_Product_ID(mhupiItemProductID);
		}

		huPiItemProductRecord.setM_Product_ID(productRecord.getM_Product_ID());
		huPiItemProductRecord.setM_HU_PI_Item_ID(huPiItemId);
			huPiItemProductRecord.setQty(qty);
			huPiItemProductRecord.setValidFrom(validFrom);
		huPiItemProductRecord.setIsActive(Boolean.TRUE.equals(active));

		if (Check.isNotBlank(x12de355Code))
		{
			final UomId uomId = uomDAO.getUomIdByX12DE355(X12DE355.ofCode(x12de355Code));
			huPiItemProductRecord.setC_UOM_ID(uomId.getRepoId());
		}
		else
		{
			huPiItemProductRecord.setC_UOM_ID(PCE_UOM_ID.getRepoId());
		}

		if (Check.isNotBlank(name))
		{
			huPiItemProductRecord.setName(name);
		}

		huPiItemProductRecord.setIsInfiniteCapacity(Boolean.TRUE.equals(isInfiniteCapacity));
		huPiItemProductRecord.setIsAllowAnyProduct(Boolean.TRUE.equals(isAllowAnyProduct));
		huPiItemProductRecord.setIsDefaultForProduct(Boolean.TRUE.equals(isDefaultForProduct));

		final String huPackagingCodeLUFallbackIdentifier = DataTableUtil.extractNullableStringForColumnName(tableRow, "OPT." + COLUMNNAME_M_HU_PackagingCode_LU_Fallback_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(huPackagingCodeLUFallbackIdentifier))
			{
				final int huPackagingCodeId = DataTableUtil.nullToken2Null(huPackagingCodeLUFallbackIdentifier) == null
						? -1
						: huPackagingCodeTable.get(huPackagingCodeLUFallbackIdentifier).getM_HU_PackagingCode_ID();

				huPiItemProductRecord.setM_HU_PackagingCode_LU_Fallback_ID(huPackagingCodeId);
			}

		final String gtinLuPackagingMaterialFallback = DataTableUtil.extractNullableStringForColumnName(tableRow, "OPT." + COLUMNNAME_GTIN_LU_PackingMaterial_Fallback);
			if (Check.isNotBlank(gtinLuPackagingMaterialFallback))
			{
				huPiItemProductRecord.setGTIN_LU_PackingMaterial_Fallback(DataTableUtil.nullToken2Null(gtinLuPackagingMaterialFallback));
			}

		final String bpartnerIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_M_HU_PI_Item_Product.COLUMNNAME_C_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(bpartnerIdentifier))
		{
			final I_C_BPartner bPartner = bpartnerTable.get(bpartnerIdentifier);
			assertThat(bPartner).isNotNull();

			huPiItemProductRecord.setC_BPartner_ID(bPartner.getC_BPartner_ID());
		}

		final String upc = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_M_HU_PI_Item_Product.COLUMNNAME_UPC);
		if (Check.isNotBlank(upc))
		{
			huPiItemProductRecord.setUPC(upc);
		}

			saveRecord(huPiItemProductRecord);

		final String huPiItemProductIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_HU_PI_Item_Product.COLUMNNAME_M_HU_PI_Item_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
		huPiItemProductTable.putOrReplace(huPiItemProductIdentifier, huPiItemProductRecord);
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

		final String gtin = DataTableUtil.extractNullableStringForColumnName(row, "OPT." + COLUMNNAME_GTIN);

		if (Check.isNotBlank(gtin))
		{
			mHuPiItemProductRecord.setGTIN(DataTableUtil.nullToken2Null(gtin));
		}

		saveRecord(mHuPiItemProductRecord);
	}
}
