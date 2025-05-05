/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.cucumber.stepdefs.contract;

import de.metas.contracts.model.I_I_ModCntr_Log;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.ItemProvider;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.ScenarioLifeCycleStepDef;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.compiere.model.I_M_Product;

import java.time.Instant;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.cucumber.stepdefs.StepDefUtil.writeRowAsString;
import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class I_ModCntr_Log_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull
	private final I_ModCntr_Log_StepDefData importModCntrTable;
	private final M_Product_StepDefData productTable;

	@NonNull
	private final ScenarioLifeCycleStepDef scenarioLifeCycleStepDef;

	@And("^after no more than (.*)s, I_ModCntr_Log are found by product$")
	public void loadImportModCntrLog(final int timeoutSec, @NonNull final DataTable table) throws InterruptedException
	{
		for (final Map<String, String> row : table.asMaps())
		{
			final String productIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_Product.COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_Product product = productTable.get(productIdentifier);

			final ItemProvider<I_I_ModCntr_Log> locateLog = () -> queryBL.createQueryBuilder(I_I_ModCntr_Log.class)
					.addEqualsFilter(I_I_ModCntr_Log.COLUMNNAME_M_Product_ID, product.getM_Product_ID())
					.orderBy(I_I_ModCntr_Log.COLUMNNAME_Created)
					.firstOnlyOptional()
					.map(ItemProvider.ProviderResult::resultWasFound)
					.orElseGet(() -> ItemProvider.ProviderResult.resultWasNotFound(buildMessageWitAllLogs(row)));

			final I_I_ModCntr_Log importLog = StepDefUtil.tryAndWaitForItem(timeoutSec, 500, locateLog);

			assertThat(importLog).as("I_I_ModCntr_Log").isNotNull();

			final String importModCntrLogIdentifier = DataTableUtil.extractStringForColumnName(row, I_I_ModCntr_Log.COLUMNNAME_I_ModCntr_Log_ID + "." + TABLECOLUMN_IDENTIFIER);
			importModCntrTable.putOrReplace(importModCntrLogIdentifier, importLog);
		}
	}

	@NonNull
	private String buildMessageWitAllLogs(@NonNull final Map<String, String> row)
	{
		final StringBuilder messageBuilder = new StringBuilder("No logs found for row: " + writeRowAsString(row) + "! See currently created messages:");

		queryBL.createQueryBuilder(I_I_ModCntr_Log.class)
				.addCompareFilter(I_I_ModCntr_Log.COLUMNNAME_Created, CompareQueryFilter.Operator.GREATER_OR_EQUAL, scenarioLifeCycleStepDef.getScenarioStartTimeOr(Instant.ofEpochMilli(0)))
				.create()
				.forEach(logRecord -> messageBuilder
						.append("\n")
						.append(I_I_ModCntr_Log.COLUMNNAME_C_Flatrate_Term_ID).append("=").append(logRecord.getC_Flatrate_Term_ID()).append(", ")
						.append(I_I_ModCntr_Log.COLUMNNAME_Qty).append("=").append(logRecord.getQty()).append(", ")
						.append(I_I_ModCntr_Log.COLUMNNAME_C_UOM_ID).append("=").append(logRecord.getC_UOM_ID()).append(", ")
						.append(I_I_ModCntr_Log.COLUMNNAME_M_Product_ID).append("=").append(logRecord.getM_Product_ID()).append(", ")
						.append(I_I_ModCntr_Log.COLUMNNAME_Processed).append("=").append(logRecord.isProcessed()).append(";")
				);

		return messageBuilder.toString();
	}
}
