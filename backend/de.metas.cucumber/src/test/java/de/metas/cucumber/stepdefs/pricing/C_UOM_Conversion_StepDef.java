/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.cucumber.stepdefs.pricing;

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.product.ProductId;
import de.metas.uom.CreateUOMConversionRequest;
import de.metas.uom.IUOMConversionDAO;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UOMConversionRate;
import de.metas.uom.UomId;
import de.metas.uom.UpdateUOMConversionRequest;
import de.metas.uom.X12DE355;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_C_UOM_Conversion;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_ProductPrice;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

public class C_UOM_Conversion_StepDef
{
	private final M_Product_StepDefData productTable;
	private final C_UOM_Conversion_StepDefData conversionTable;
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IUOMConversionDAO uomConversionDAO = Services.get(IUOMConversionDAO.class);

	public C_UOM_Conversion_StepDef(
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final C_UOM_Conversion_StepDefData conversionTable)
	{
		this.productTable = productTable;
		this.conversionTable = conversionTable;
	}

	@And("metasfresh contains C_UOM_Conversions")
	public void add_C_UOM_Conversions(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps();
		for (final Map<String, String> tableRow : tableRows)
		{
			create_C_UOM_Conversions(tableRow);
		}
	}

	@And("update C_UOM_Conversion:")
	public void update_C_UOM_Conversions(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps();
		for (final Map<String, String> tableRow : tableRows)
		{
			update_C_UOM_Conversions(tableRow);
		}
	}

	private void update_C_UOM_Conversions(@NonNull final Map<String, String> tableRow)
	{
		final String conversionIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_UOM_Conversion.COLUMNNAME_C_UOM_Conversion_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final Integer conversionID = conversionTable.getOptional(conversionIdentifier)
				.map(I_C_UOM_Conversion::getC_UOM_Conversion_ID)
				.orElseGet(() -> Integer.parseInt(conversionIdentifier));

		assertThat(conversionID).isNotNull();

		final I_C_UOM_Conversion conversionRecord = InterfaceWrapperHelper.load(conversionID, I_C_UOM_Conversion.class);

		final boolean isCatchUomForProduct = DataTableUtil.extractBooleanForColumnNameOr(tableRow, "OPT." + I_C_UOM_Conversion.COLUMNNAME_IsCatchUOMForProduct, false);
		conversionRecord.setIsCatchUOMForProduct(isCatchUomForProduct);

		saveRecord(conversionRecord);
	}

	private void create_C_UOM_Conversions(@NonNull final Map<String, String> tableRow)
	{
		final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_ProductPrice.COLUMNNAME_M_Product_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_M_Product product = productTable.get(productIdentifier);
		final ProductId productId = ProductId.ofRepoId(product.getM_Product_ID());

		final String fromX12de355Code = DataTableUtil.extractStringForColumnName(tableRow, "FROM_" + I_C_UOM.COLUMNNAME_C_UOM_ID + "." + X12DE355.class.getSimpleName());
		final X12DE355 fromX12DE355 = X12DE355.ofCode(fromX12de355Code);
		final UomId fromUomId = uomDAO.getUomIdByX12DE355(fromX12DE355);

		final String toX12de355Code = DataTableUtil.extractStringForColumnName(tableRow, "TO_" + I_C_UOM.COLUMNNAME_C_UOM_ID + "." + X12DE355.class.getSimpleName());
		final X12DE355 toX12DE355 = X12DE355.ofCode(toX12de355Code);
		final UomId toUomId = uomDAO.getUomIdByX12DE355(toX12DE355);

		final BigDecimal fromToMultiplier = DataTableUtil.extractBigDecimalForColumnName(tableRow, I_C_UOM_Conversion.COLUMNNAME_MultiplyRate);

		final boolean isCatchUomForProduct = DataTableUtil.extractBooleanForColumnNameOr(tableRow, "OPT." + I_C_UOM_Conversion.COLUMNNAME_IsCatchUOMForProduct, false);

		final Optional<UOMConversionRate> rateIfExists = uomConversionDAO.getProductConversions(productId).getRateIfExists(fromUomId, toUomId);
		if (rateIfExists.isPresent())
		{
			final UpdateUOMConversionRequest updateUOMConversionRequest = UpdateUOMConversionRequest.builder()
					.productId(productId)
					.fromUomId(fromUomId)
					.toUomId(toUomId)
					.fromToMultiplier(fromToMultiplier)
					.catchUOMForProduct(isCatchUomForProduct)
					.build();
			uomConversionDAO.updateUOMConversion(updateUOMConversionRequest);
			return;
		}

		// didn't exist yet -> create it
		final CreateUOMConversionRequest uomConversionRequest = CreateUOMConversionRequest.builder()
				.productId(productId)
				.fromUomId(fromUomId)
				.toUomId(toUomId)
				.fromToMultiplier(fromToMultiplier)
				.catchUOMForProduct(isCatchUomForProduct)
				.build();

		uomConversionDAO.createUOMConversion(uomConversionRequest);
	}
}
