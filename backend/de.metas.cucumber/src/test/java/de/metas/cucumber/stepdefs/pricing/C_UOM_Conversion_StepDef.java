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

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
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
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_C_UOM_Conversion;
import org.compiere.model.I_M_Product;

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

	/**
	 * Create UOM conversions for a product.
	 * <p>
	 * NOTE: this step uses legacy {@link DataTableUtil} with explicit column suffixes.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_Product_ID.Identifier</b> — (required, identifier-ref) product alias from M_Product_StepDefData<br>
	 *   <b>FROM_C_UOM_ID.X12DE355</b> — (required) source UOM X12DE355 code (e.g. "PCE", "KGM")<br>
	 *   <b>TO_C_UOM_ID.X12DE355</b> — (required) target UOM X12DE355 code<br>
	 *   <b>MultiplyRate</b> — (required) conversion multiplier (from → to)<br>
	 *   <b>OPT.IsCatchUOMForProduct</b> — (optional, default false) catch UOM flag<br>
	 * @cucumber.example
	 * <pre>{@code
	 * And metasfresh contains C_UOM_Conversions
	 *   | M_Product_ID.Identifier | FROM_C_UOM_ID.X12DE355 | TO_C_UOM_ID.X12DE355 | MultiplyRate |
	 *   | product_1               | PCE                     | KGM                   | 0.5          |
	 * }</pre>
	 */
	@And("metasfresh contains C_UOM_Conversions")
	public void add_C_UOM_Conversions(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::create_C_UOM_Conversion);
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

	@And("validate C_UOM_Conversion:")
	public void validate_C_UOM_Conversions(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps();
		for (final Map<String, String> tableRow : tableRows)
		{
			validate_C_UOM_Conversions(tableRow);
		}
	}

	private void validate_C_UOM_Conversions(@NonNull final Map<String, String> tableRow)
	{
		final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_UOM_Conversion.COLUMNNAME_M_Product_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_M_Product product = productTable.get(productIdentifier);
		final ProductId productId = ProductId.ofRepoId(product.getM_Product_ID());

		final String fromX12de355Code = DataTableUtil.extractStringForColumnName(tableRow, I_C_UOM_Conversion.COLUMNNAME_C_UOM_ID + "." + X12DE355.class.getSimpleName());
		final X12DE355 fromX12DE355 = X12DE355.ofCode(fromX12de355Code);
		final UomId fromUomId = uomDAO.getUomIdByX12DE355(fromX12DE355);

		final String toX12de355Code = DataTableUtil.extractStringForColumnName(tableRow, I_C_UOM_Conversion.COLUMNNAME_C_UOM_To_ID + "." + X12DE355.class.getSimpleName());
		final X12DE355 toX12DE355 = X12DE355.ofCode(toX12de355Code);
		final UomId toUomId = uomDAO.getUomIdByX12DE355(toX12DE355);

		final UOMConversionRate rate = uomConversionDAO.getProductConversions(productId).getRate(fromUomId, toUomId);

		final SoftAssertions softly = new SoftAssertions();

		final BigDecimal fromToMultiplier = DataTableUtil.extractBigDecimalForColumnName(tableRow, I_C_UOM_Conversion.COLUMNNAME_MultiplyRate);
		softly.assertThat(rate.getFromToMultiplier()).as(I_C_UOM_Conversion.COLUMNNAME_MultiplyRate).isEqualByComparingTo(fromToMultiplier);

		final Boolean isCatchUomForProduct = DataTableUtil.extractBooleanForColumnNameOrNull(tableRow, "OPT." + I_C_UOM_Conversion.COLUMNNAME_IsCatchUOMForProduct);
		if (isCatchUomForProduct != null)
		{
			softly.assertThat(rate.isCatchUOMForProduct()).as(I_C_UOM_Conversion.COLUMNNAME_IsCatchUOMForProduct).isEqualTo(isCatchUomForProduct);
		}

		softly.assertAll();
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

	private void create_C_UOM_Conversion(@NonNull final DataTableRow row)
	{
		final I_M_Product product = row.getAsIdentifier(I_C_UOM_Conversion.COLUMNNAME_M_Product_ID).lookupNotNullIn(productTable);
		final ProductId productId = ProductId.ofRepoId(product.getM_Product_ID());

		final UomId fromUomId = uomDAO.getUomIdByX12DE355(X12DE355.ofCode(row.getAsString("FROM_C_UOM_ID.X12DE355")));
		final UomId toUomId   = uomDAO.getUomIdByX12DE355(X12DE355.ofCode(row.getAsString("TO_C_UOM_ID.X12DE355")));

		final BigDecimal fromToMultiplier = row.getAsBigDecimal(I_C_UOM_Conversion.COLUMNNAME_MultiplyRate);
		final boolean isCatchUomForProduct = row.getAsOptionalBoolean(I_C_UOM_Conversion.COLUMNNAME_IsCatchUOMForProduct).orElseFalse();

		final Optional<UOMConversionRate> rateIfExists = uomConversionDAO.getProductConversions(productId).getRateIfExists(fromUomId, toUomId);
		if (rateIfExists.isPresent())
		{
			uomConversionDAO.updateUOMConversion(UpdateUOMConversionRequest.builder()
					.productId(productId)
					.fromUomId(fromUomId)
					.toUomId(toUomId)
					.fromToMultiplier(fromToMultiplier)
					.catchUOMForProduct(isCatchUomForProduct)
					.build());
			return;
		}

		uomConversionDAO.createUOMConversion(CreateUOMConversionRequest.builder()
				.productId(productId)
				.fromUomId(fromUomId)
				.toUomId(toUomId)
				.fromToMultiplier(fromToMultiplier)
				.catchUOMForProduct(isCatchUomForProduct)
				.build());
	}
}
