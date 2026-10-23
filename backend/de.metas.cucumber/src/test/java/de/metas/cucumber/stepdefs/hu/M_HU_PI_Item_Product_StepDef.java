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
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuPackingInstructionsItemId;
import de.metas.handlingunits.generichumodel.PackagingCodeId;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.uom.X12DE355;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.DEFAULT_ValidFrom;
import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.handlingunits.model.I_M_HU_PI_Item_Product.COLUMNNAME_GTIN;
import static de.metas.handlingunits.model.I_M_HU_PI_Item_Product.COLUMNNAME_GTIN_LU_PackingMaterial_Fallback;
import static de.metas.handlingunits.model.I_M_HU_PI_Item_Product.COLUMNNAME_IsOrderInTuUomWhenMatched;
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
		final ProductId productId = tableRow.getAsIdentifier(I_M_HU_PI_Item_Product.COLUMNNAME_M_Product_ID).lookupNotNullIdIn(productTable);
		final LocalDate validFrom = tableRow.getAsOptionalLocalDate(I_M_HU_PI_Item_Product.COLUMNNAME_ValidFrom).orElse(DEFAULT_ValidFrom);
		final boolean isAllowAnyProduct = tableRow.getAsOptionalBoolean(I_M_HU_PI_Item_Product.COLUMNNAME_IsAllowAnyProduct).orElse(false);
		final boolean isDefaultForProduct = tableRow.getAsOptionalBoolean(I_M_HU_PI_Item_Product.COLUMNNAME_IsDefaultForProduct).orElse(false);
		final boolean active = tableRow.getAsOptionalBoolean(I_M_HU_PI_Item_Product.COLUMNNAME_IsActive).orElse(true);

		final StepDefDataIdentifier huPiItemIdentifier = tableRow.getAsIdentifier(I_M_HU_PI_Item_Product.COLUMNNAME_M_HU_PI_Item_ID);
		final HuPackingInstructionsItemId huPiItemId = huPiItemTable.getIdOptional(huPiItemIdentifier)
				.orElseGet(() -> huPiItemIdentifier.getAsId(HuPackingInstructionsItemId.class));

		final boolean isInfiniteCapacity = tableRow.getAsOptionalBoolean(I_M_HU_PI_Item_Product.COLUMNNAME_IsInfiniteCapacity).orElse(false);
		final Quantity qtyCUsPerTU = !isInfiniteCapacity
				? tableRow.getAsQuantity(I_M_HU_PI_Item_Product.COLUMNNAME_Qty, I_C_UOM.COLUMNNAME_C_UOM_ID + ".X12DE355", X12DE355.EACH, uomDAO::getByX12DE355)
				: null;

		final StepDefDataIdentifier identifier = tableRow.getAsIdentifier();
		final I_M_HU_PI_Item_Product huPiItemProductRecord = huPiItemProductTable.getOptional(identifier)
				.orElseGet(() -> {
					final IQueryBuilder<I_M_HU_PI_Item_Product> queryBuilder = queryBL.createQueryBuilder(I_M_HU_PI_Item_Product.class)
							.addEqualsFilter(I_M_HU_PI_Item_Product.COLUMNNAME_M_Product_ID, productId)
							.addEqualsFilter(I_M_HU_PI_Item_Product.COLUMNNAME_M_HU_PI_Item_ID, huPiItemId)
							.addEqualsFilter(I_M_HU_PI_Item_Product.COLUMNNAME_IsActive, active);
					if (isInfiniteCapacity)
					{
						queryBuilder.addEqualsFilter(I_M_HU_PI_Item_Product.COLUMNNAME_IsInfiniteCapacity, true);
					}
					else
					{
						queryBuilder.addEqualsFilter(I_M_HU_PI_Item_Product.COLUMNNAME_Qty, qtyCUsPerTU.toBigDecimal());
						queryBuilder.addEqualsFilter(I_M_HU_PI_Item_Product.COLUMNNAME_C_UOM_ID, qtyCUsPerTU.getUomId());
					}

					final I_M_HU_PI_Item_Product record = queryBuilder.create().firstOnlyOrNull(I_M_HU_PI_Item_Product.class);
					return CoalesceUtil.coalesceSuppliersNotNull(() -> record, () -> InterfaceWrapperHelper.newInstance(I_M_HU_PI_Item_Product.class));
				});

		huPiItemProductRecord.setM_HU_PI_Item_ID(huPiItemId.getRepoId());
		huPiItemProductRecord.setIsAllowAnyProduct(isAllowAnyProduct);
		huPiItemProductRecord.setIsDefaultForProduct(isDefaultForProduct);
		huPiItemProductRecord.setM_Product_ID(productId.getRepoId());
		huPiItemProductRecord.setValidFrom(TimeUtil.asTimestamp(validFrom));
		huPiItemProductRecord.setIsActive(active);
		tableRow.getAsOptionalString(COLUMNNAME_GTIN).ifPresent(huPiItemProductRecord::setGTIN);
		tableRow.getAsOptionalBoolean(COLUMNNAME_IsOrderInTuUomWhenMatched).ifPresent(huPiItemProductRecord::setIsOrderInTuUomWhenMatched);

		huPiItemProductRecord.setIsInfiniteCapacity(isInfiniteCapacity);
		if (isInfiniteCapacity)
		{
			huPiItemProductRecord.setQty(BigDecimal.ZERO); // just because it's mandatory
		}
		else
		{
			huPiItemProductRecord.setQty(qtyCUsPerTU.toBigDecimal());
			huPiItemProductRecord.setC_UOM_ID(qtyCUsPerTU.getUomId().getRepoId());
		}

		tableRow.getAsOptionalString(I_M_HU_PI_Item_Product.COLUMNNAME_Name).ifPresent(huPiItemProductRecord::setName);

		tableRow.getAsOptionalIdentifier(COLUMNNAME_M_HU_PackagingCode_LU_Fallback_ID)
				.map(huPackagingCodeTable::getId)
				.ifPresent(packagingCodeId -> huPiItemProductRecord.setM_HU_PackagingCode_LU_Fallback_ID(PackagingCodeId.toRepoId(packagingCodeId)));

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

	/**
	 * Inserts a fixed-capacity packing instruction with quantity 0 and without the "infinite capacity" flag by SQL,
	 * the way a script or an import writes it, bypassing the model validation that rejects it on save.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_HU_PI_Item_Product_ID</b> — (required) alias for cross-step reference<br>
	 *   <b>M_HU_PI_Item_ID</b> — (required, identifier-ref) the TU item<br>
	 *   <b>M_Product_ID</b> — (required, identifier-ref) the packed product<br>
	 *   <b>ValidFrom</b> — (optional) defaults to the cucumber default valid-from date<br>
	 * @cucumber.depends StepDefData: M_HU_PI_Item_StepDefData, M_Product_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And metasfresh contains M_HU_PI_Item_Product with quantity 0 and no infinite capacity, inserted by SQL:
	 *   | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID |
	 *   | huPIP_zero              | huPiItemTU      | product_1    |
	 * </pre>
	 */
	@Given("metasfresh contains M_HU_PI_Item_Product with quantity 0 and no infinite capacity, inserted by SQL:")
	public void metasfresh_contains_m_hu_pi_item_product_with_quantity_0_inserted_by_sql(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_M_HU_PI_Item_Product.COLUMNNAME_M_HU_PI_Item_Product_ID)
				.forEach(this::insertHUPIItemProductWithZeroQtyBySql);
	}

	private void insertHUPIItemProductWithZeroQtyBySql(@NonNull final DataTableRow tableRow)
	{
		final I_M_Product product = tableRow.getAsIdentifier(I_M_HU_PI_Item_Product.COLUMNNAME_M_Product_ID).lookupNotNullIn(productTable);
		final StepDefDataIdentifier huPiItemIdentifier = tableRow.getAsIdentifier(I_M_HU_PI_Item_Product.COLUMNNAME_M_HU_PI_Item_ID);
		final HuPackingInstructionsItemId huPiItemId = huPiItemTable.getIdOptional(huPiItemIdentifier)
				.orElseGet(() -> huPiItemIdentifier.getAsId(HuPackingInstructionsItemId.class));
		final LocalDate validFrom = tableRow.getAsOptionalLocalDate(I_M_HU_PI_Item_Product.COLUMNNAME_ValidFrom).orElse(DEFAULT_ValidFrom);

		final int clientId = product.getAD_Client_ID();
		final int huPiItemProductId = DB.getNextID(clientId, I_M_HU_PI_Item_Product.Table_Name);

		DB.executeUpdateAndThrowExceptionOnFail(
				"INSERT INTO M_HU_PI_Item_Product (M_HU_PI_Item_Product_ID, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive,"
						+ " M_HU_PI_Item_ID, M_Product_ID, Qty, C_UOM_ID, ValidFrom, IsInfiniteCapacity)"
						+ " VALUES (?, ?, 0, now(), 100, now(), 100, 'Y', ?, ?, 0, ?, ?, 'N')",
				new Object[] { huPiItemProductId, clientId, huPiItemId.getRepoId(), product.getM_Product_ID(), product.getC_UOM_ID(), TimeUtil.asTimestamp(validFrom) },
				ITrx.TRXNAME_None);

		final I_M_HU_PI_Item_Product huPiItemProductRecord = InterfaceWrapperHelper.loadOutOfTrx(huPiItemProductId, I_M_HU_PI_Item_Product.class);
		tableRow.getAsIdentifier().putOrReplace(huPiItemProductTable, huPiItemProductRecord);
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
