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

import de.metas.common.util.Check;
import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.generichumodel.PackagingCodeId;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static de.metas.cucumber.stepdefs.StepDefConstants.PCE_UOM_ID;
import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.handlingunits.model.I_M_HU_PI_Item_Product.COLUMNNAME_GTIN;
import static de.metas.handlingunits.model.I_M_HU_PI_Item_Product.COLUMNNAME_GTIN_LU_PackingMaterial_Fallback;
import static de.metas.handlingunits.model.I_M_HU_PI_Item_Product.COLUMNNAME_M_HU_PI_Item_Product_ID;
import static de.metas.handlingunits.model.I_M_HU_PI_Item_Product.COLUMNNAME_M_HU_PackagingCode_LU_Fallback_ID;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@RequiredArgsConstructor
public class M_HU_PI_Item_Product_StepDef
{
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull private final M_HU_PI_Item_StepDefData huPiItemTable;
	@NonNull private final M_Product_StepDefData productTable;
	@NonNull private final M_HU_PI_Item_Product_StepDefData huPiItemProductTable;
	@NonNull private final M_HU_PackagingCode_StepDefData huPackagingCodeTable;
	@NonNull private final C_BPartner_StepDefData bpartnerTable;
	@NonNull private final TestContext restTestContext;

	@Given("metasfresh contains M_HU_PI_Item_Product:")
	public void metasfresh_contains_m_hu_pi_item_product(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_M_HU_PI_Item_Product.COLUMNNAME_M_HU_PI_Item_Product_ID)
				.forEach(this::createOrUpdateHUPIItemProduct);
	}

	private void createOrUpdateHUPIItemProduct(@NonNull final DataTableRow tableRow)
	{
		final I_M_Product productRecord = tableRow.getAsIdentifier(I_M_HU_PI_Item_Product.COLUMNNAME_M_Product_ID).lookupNotNullIn(productTable);

		final BigDecimal qty = DataTableUtil.extractBigDecimalForColumnName(tableRow, I_M_HU_PI_Item_Product.COLUMNNAME_Qty);
		final Timestamp validFrom = DataTableUtil.extractDateTimestampForColumnName(tableRow, I_M_HU_PI_Item_Product.COLUMNNAME_ValidFrom);
		final boolean isInfiniteCapacity = tableRow.getAsOptionalBoolean(I_M_HU_PI_Item_Product.COLUMNNAME_IsInfiniteCapacity).orElse(false);
		final boolean isAllowAnyProduct = tableRow.getAsOptionalBoolean(I_M_HU_PI_Item_Product.COLUMNNAME_IsAllowAnyProduct).orElse(false);
		final String name = tableRow.getAsOptionalString(I_M_HU_PI_Item_Product.COLUMNNAME_Name).orElse(null);

		final boolean isDefaultForProduct = tableRow.getAsOptionalBoolean(I_M_HU_PI_Item_Product.COLUMNNAME_IsDefaultForProduct).orElse(false);
		final boolean active = tableRow.getAsOptionalBoolean(I_M_HU_PI_Item_Product.COLUMNNAME_IsActive).orElse(true);
		final boolean isOrderInTuUomWhenMatched = tableRow.getAsOptionalBoolean(I_M_HU_PI_Item_Product.COLUMNNAME_IsOrderInTuUomWhenMatched).orElse(false);

		final StepDefDataIdentifier huPiItemIdentifier = tableRow.getAsIdentifier(I_M_HU_PI_Item_Product.COLUMNNAME_M_HU_PI_Item_ID);
		final Integer huPiItemId = huPiItemTable.getOptional(huPiItemIdentifier)
				.map(I_M_HU_PI_Item::getM_HU_PI_Item_ID)
				.orElseGet(() -> Integer.parseInt(huPiItemIdentifier.getAsString()));
				
		final String x12de355Code = tableRow.getAsOptionalString(I_C_UOM.COLUMNNAME_C_UOM_ID + "." + X12DE355.class.getSimpleName()).orElse(null);

		final StepDefDataIdentifier identifier = tableRow.getAsIdentifier();
		final I_M_HU_PI_Item_Product huPiItemProductRecord = huPiItemProductTable.getOptional(identifier)
				.orElseGet(() ->
						{
							final I_M_HU_PI_Item_Product record = queryBL.createQueryBuilder(I_M_HU_PI_Item_Product.class)
									.addEqualsFilter(I_M_HU_PI_Item_Product.COLUMNNAME_M_Product_ID, productRecord.getM_Product_ID())
									.addEqualsFilter(I_M_HU_PI_Item_Product.COLUMNNAME_M_HU_PI_Item_ID, huPiItemId)
									.addEqualsFilter(I_M_HU_PI_Item_Product.COLUMNNAME_Qty, qty)
									.addEqualsFilter(I_M_HU_PI_Item_Product.COLUMNNAME_IsActive, active)
									.create()
									.firstOnlyOrNull(I_M_HU_PI_Item_Product.class);

							return CoalesceUtil.coalesceSuppliersNotNull(() -> record, () -> InterfaceWrapperHelper.newInstance(I_M_HU_PI_Item_Product.class));
						}
				);

		tableRow.getAsOptionalString(COLUMNNAME_GTIN).ifPresent(huPiItemProductRecord::setGTIN);
		
		huPiItemProductRecord.setM_Product_ID(productRecord.getM_Product_ID());
		huPiItemProductRecord.setM_HU_PI_Item_ID(huPiItemId);
		huPiItemProductRecord.setQty(qty);
		huPiItemProductRecord.setValidFrom(validFrom);
		huPiItemProductRecord.setIsActive(active);
		huPiItemProductRecord.setIsOrderInTuUomWhenMatched(isOrderInTuUomWhenMatched);

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

		huPiItemProductRecord.setIsInfiniteCapacity(isInfiniteCapacity);
		huPiItemProductRecord.setIsAllowAnyProduct(isAllowAnyProduct);
		huPiItemProductRecord.setIsDefaultForProduct(isDefaultForProduct);

		final Optional<StepDefDataIdentifier> huPackagingCodeLUFallbackIdentifier = tableRow.getAsOptionalIdentifier(COLUMNNAME_M_HU_PackagingCode_LU_Fallback_ID);
		if (huPackagingCodeLUFallbackIdentifier.isPresent())
		{
			final PackagingCodeId packagingCodeId = huPackagingCodeLUFallbackIdentifier.get().lookupIdIn(huPackagingCodeTable);
			huPiItemProductRecord.setM_HU_PackagingCode_LU_Fallback_ID(PackagingCodeId.toRepoId(packagingCodeId));
		}

		final String gtinLuPackagingMaterialFallback = tableRow.getAsOptionalString(COLUMNNAME_GTIN_LU_PackingMaterial_Fallback).orElse(null);
		if (Check.isNotBlank(gtinLuPackagingMaterialFallback))
		{
			huPiItemProductRecord.setGTIN_LU_PackingMaterial_Fallback(DataTableUtil.nullToken2Null(gtinLuPackagingMaterialFallback));
		}

		tableRow.getAsOptionalIdentifier(I_M_HU_PI_Item_Product.COLUMNNAME_C_BPartner_ID)
				.ifPresent(bpartnerIdentifier -> huPiItemProductRecord.setC_BPartner_ID(bpartnerIdentifier.lookupNotNullIdIn(bpartnerTable).getRepoId()));

		tableRow.getAsOptionalString(I_M_HU_PI_Item_Product.COLUMNNAME_UPC).ifPresent(huPiItemProductRecord::setUPC);
		
		saveRecord(huPiItemProductRecord);

		identifier.putOrReplace(huPiItemProductTable, huPiItemProductRecord);

		final HUPIItemProductId hupiItemProductId = HUPIItemProductId.ofRepoId(huPiItemProductRecord.getM_HU_PI_Item_Product_ID());
		restTestContext.setIdVariableFromRow(tableRow, () -> hupiItemProductId);
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
