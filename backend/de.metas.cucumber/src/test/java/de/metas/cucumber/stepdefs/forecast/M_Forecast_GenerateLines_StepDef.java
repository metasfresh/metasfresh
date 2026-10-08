/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.cucumber.stepdefs.forecast;

import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.organization.OrgId;
import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
import de.metas.process.ProcessInfo;
import de.metas.product.ProductId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Forecast;
import org.compiere.model.I_M_ForecastLine;
import org.compiere.model.I_M_Product;
import org.eevolution.model.I_PP_Product_Planning;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for invoking the M_Forecast_GenerateLines process
 * and verifying the generated forecast lines.
 *
 * <h3>Steps provided:</h3>
 * <ul>
 *   <li>{@code update PP_Product_Planning forecast columns for product <identifier>:} — sets forecast-specific columns on an existing PP_Product_Planning record</li>
 *   <li>{@code the M_Forecast_GenerateLines process is invoked for forecast <identifier>} — runs the forecast generator process</li>
 *   <li>{@code the forecast identified by <identifier> has forecast lines:} — asserts generated line quantities</li>
 *   <li>{@code the forecast identified by <identifier> has no forecast lines} — asserts no lines were generated</li>
 * </ul>
 */
@RequiredArgsConstructor
public class M_Forecast_GenerateLines_StepDef
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final IADProcessDAO processDAO = Services.get(IADProcessDAO.class);

	@NonNull private final M_Forecast_StepDefData forecastTable;
	@NonNull private final M_Product_StepDefData productTable;

	/**
	 * Updates the forecast-specific columns on an existing PP_Product_Planning record.
	 *
	 * <h4>Required columns:</h4>
	 * <ul>
	 *   <li>{@code Forecast_CalculationMethod} — calculation method (0-4)</li>
	 *   <li>{@code Forecast_PrecisionUnit} — W or M</li>
	 *   <li>{@code Forecast_Frequency} — order cycle in precision units</li>
	 *   <li>{@code Forecast_BufferTime} — buffer in precision units</li>
	 *   <li>{@code IsExcludeFromForecast} — Y/N</li>
	 * </ul>
	 */
	@And("update PP_Product_Planning forecast columns for product {string}:")
	public void updatePPProductPlanningForecastColumns(
			@NonNull final String productIdentifierStr,
			@NonNull final DataTable dataTable)
	{
		final StepDefDataIdentifier productIdentifier = StepDefDataIdentifier.ofString(productIdentifierStr);
		final I_M_Product product = productTable.get(productIdentifier);
		final ProductId productId = ProductId.ofRepoId(product.getM_Product_ID());
		final OrgId orgId = OrgId.ofRepoIdOrAny(product.getAD_Org_ID());

		// Find the existing PP_Product_Planning record
		final I_PP_Product_Planning ppRecord = queryBL.createQueryBuilder(I_PP_Product_Planning.class)
				.addEqualsFilter(I_PP_Product_Planning.COLUMNNAME_M_Product_ID, productId)
				.addEqualsFilter(I_PP_Product_Planning.COLUMNNAME_AD_Org_ID, orgId)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnlyNotNull(I_PP_Product_Planning.class);

		DataTableRows.of(dataTable).forEach(row -> {
			final String calculationMethod = row.getAsString("Forecast_CalculationMethod");
			final String precisionUnit = row.getAsString("Forecast_PrecisionUnit");
			final int frequency = row.getAsInt("Forecast_Frequency");
			final int bufferTime = row.getAsInt("Forecast_BufferTime");
			final boolean excludeFromForecast = row.getAsBoolean("IsExcludeFromForecast");

			ppRecord.setForecast_CalculationMethod(calculationMethod);
			ppRecord.setForecast_PrecisionUnit(precisionUnit);
			ppRecord.setForecast_Frequency(frequency);
			ppRecord.setForecast_BufferTime(bufferTime);
			ppRecord.setIsExcludeFromForecast(excludeFromForecast);

			InterfaceWrapperHelper.saveRecord(ppRecord);
		});
	}

	/**
	 * Invokes the M_Forecast_GenerateLines process for the given forecast.
	 * The process reads PP_Product_Planning records and generates M_ForecastLine entries
	 * based on historical sales data.
	 */
	@When("the M_Forecast_GenerateLines process is invoked for forecast {string}")
	public void invokeGenerateForecastLines(@NonNull final String forecastIdentifierStr)
	{
		final StepDefDataIdentifier forecastIdentifier = StepDefDataIdentifier.ofString(forecastIdentifierStr);
		final I_M_Forecast forecast = forecastTable.get(forecastIdentifier);

		final AdProcessId processId = processDAO.retrieveProcessIdByValue("M_Forecast_GenerateLines");
		assertThat(processId).as("AD_Process M_Forecast_GenerateLines must exist").isNotNull();

		ProcessInfo.builder()
				.setAD_Process_ID(processId.getRepoId())
				.setRecord(I_M_Forecast.Table_Name, forecast.getM_Forecast_ID())
				.buildAndPrepareExecution()
				.executeSync();
	}

	/**
	 * Verifies that the forecast has lines matching the expected product and quantity.
	 *
	 * <h4>Required columns:</h4>
	 * <ul>
	 *   <li>{@code M_Product_ID} — product identifier</li>
	 *   <li>{@code Qty} — expected forecast quantity (compared with tolerance of 0.01)</li>
	 * </ul>
	 */
	@Then("the forecast identified by {string} has forecast lines:")
	public void forecastHasLines(
			@NonNull final String forecastIdentifierStr,
			@NonNull final DataTable dataTable)
	{
		final StepDefDataIdentifier forecastIdentifier = StepDefDataIdentifier.ofString(forecastIdentifierStr);
		final I_M_Forecast forecast = forecastTable.get(forecastIdentifier);

		final List<I_M_ForecastLine> lines = queryBL.createQueryBuilder(I_M_ForecastLine.class)
				.addEqualsFilter(I_M_ForecastLine.COLUMNNAME_M_Forecast_ID, forecast.getM_Forecast_ID())
				.addOnlyActiveRecordsFilter()
				.create()
				.list(I_M_ForecastLine.class);

		DataTableRows.of(dataTable).forEach(row -> {
			final StepDefDataIdentifier productIdentifier = row.getAsIdentifier("M_Product_ID");
			final I_M_Product product = productTable.get(productIdentifier);
			final BigDecimal expectedQty = row.getAsBigDecimal("Qty");

			final I_M_ForecastLine matchingLine = lines.stream()
					.filter(line -> line.getM_Product_ID() == product.getM_Product_ID())
					.findFirst()
					.orElse(null);

			assertThat(matchingLine)
					.as("Expected a forecast line for product %s", productIdentifier.getAsString())
					.isNotNull();

			assertThat(matchingLine.getQty())
					.as("Forecast qty for product %s", productIdentifier.getAsString())
					.isCloseTo(expectedQty, org.assertj.core.data.Offset.offset(new BigDecimal("0.01")));
		});
	}

	/**
	 * Verifies that the forecast has no lines (e.g., because all products were excluded).
	 */
	@Then("the forecast identified by {string} has no forecast lines")
	public void forecastHasNoLines(@NonNull final String forecastIdentifierStr)
	{
		final StepDefDataIdentifier forecastIdentifier = StepDefDataIdentifier.ofString(forecastIdentifierStr);
		final I_M_Forecast forecast = forecastTable.get(forecastIdentifier);

		final int lineCount = queryBL.createQueryBuilder(I_M_ForecastLine.class)
				.addEqualsFilter(I_M_ForecastLine.COLUMNNAME_M_Forecast_ID, forecast.getM_Forecast_ID())
				.addOnlyActiveRecordsFilter()
				.create()
				.count();

		assertThat(lineCount)
				.as("Expected no forecast lines for forecast %s", forecastIdentifierStr)
				.isZero();
	}

	/**
	 * Verifies that the forecast has no line for a specific product
	 * (e.g., because the product was excluded via {@code IsExcludeFromForecast}).
	 */
	@Then("the forecast identified by {string} has no forecast line for product {string}")
	public void forecastHasNoLineForProduct(
			@NonNull final String forecastIdentifierStr,
			@NonNull final String productIdentifierStr)
	{
		final StepDefDataIdentifier forecastIdentifier = StepDefDataIdentifier.ofString(forecastIdentifierStr);
		final I_M_Forecast forecast = forecastTable.get(forecastIdentifier);

		final StepDefDataIdentifier productIdentifier = StepDefDataIdentifier.ofString(productIdentifierStr);
		final I_M_Product product = productTable.get(productIdentifier);

		final int lineCount = queryBL.createQueryBuilder(I_M_ForecastLine.class)
				.addEqualsFilter(I_M_ForecastLine.COLUMNNAME_M_Forecast_ID, forecast.getM_Forecast_ID())
				.addEqualsFilter(I_M_ForecastLine.COLUMNNAME_M_Product_ID, product.getM_Product_ID())
				.addOnlyActiveRecordsFilter()
				.create()
				.count();

		assertThat(lineCount)
				.as("Expected no forecast line for product %s in forecast %s", productIdentifierStr, forecastIdentifierStr)
				.isZero();
	}
}
