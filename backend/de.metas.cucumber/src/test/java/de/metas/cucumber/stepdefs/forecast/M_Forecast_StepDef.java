/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2025 metas GmbH
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
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.StepDefDocAction;
import de.metas.cucumber.stepdefs.ValueAndName;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_Forecast;
import org.compiere.model.I_M_Warehouse;
import org.slf4j.Logger;

import java.sql.Timestamp;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.compiere.model.I_M_Forecast.COLUMNNAME_M_Forecast_ID;
import static org.compiere.model.I_M_Forecast.COLUMNNAME_M_Warehouse_ID;

public class M_Forecast_StepDef
{
	private final Logger logger = LogManager.getLogger(M_Forecast_StepDef.class);
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);

	private final M_Warehouse_StepDefData warehouseTable;
	private final M_Forecast_StepDefData forecastTable;

	public M_Forecast_StepDef(
			@NonNull final M_Warehouse_StepDefData warehouseTable,
			@NonNull final M_Forecast_StepDefData forecastTable)
	{
		this.warehouseTable = warehouseTable;
		this.forecastTable = forecastTable;
	}

	@Given("metasfresh contains M_Forecasts:")
	public void metasfresh_contains_m_forecasts(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(COLUMNNAME_M_Forecast_ID)
				.forEach(tableRow -> {
					final ValueAndName valueAndName = tableRow.suggestValueAndName();
					final Timestamp datePromised = tableRow.getAsInstantTimestamp(I_M_Forecast.COLUMNNAME_DatePromised);
					final I_M_Forecast forecastRecord = newInstance(I_M_Forecast.class);

					forecastRecord.setName(valueAndName.getName());
					forecastRecord.setDatePromised(datePromised);

					final StepDefDataIdentifier warehouseIdentifier = tableRow.getAsIdentifier(COLUMNNAME_M_Warehouse_ID);
					final int warehouseId = warehouseTable.getOptional(warehouseIdentifier)
							.map(I_M_Warehouse::getM_Warehouse_ID)
							.orElseGet(warehouseIdentifier::getAsInt);

					forecastRecord.setM_Warehouse_ID(warehouseId);

					saveRecord(forecastRecord);

					forecastTable.putOrReplace(tableRow.getAsIdentifier(), forecastRecord);
				});

	}

	@And("^the forecast identified by (.*) is (reactivated|completed|closed|voided|reversed)$")
	public void forecast_action(@NonNull final String forecastIdentifier, @NonNull final String action)
	{
		final I_M_Forecast forecast = forecastTable.get(forecastIdentifier);

		switch (StepDefDocAction.valueOf(action))
		{
			case reactivated:
				forecast.setDocAction(IDocument.ACTION_Complete);
				documentBL.processEx(forecast, IDocument.ACTION_ReActivate, IDocument.STATUS_InProgress);
				logger.info("Forecast {} was reactivated", forecast);
				break;
			case completed:
				forecast.setDocAction(IDocument.ACTION_Complete);
				documentBL.processEx(forecast, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
				logger.info("Forecast {} was completed", forecast);
				break;
			case closed:
				forecast.setDocAction(IDocument.ACTION_Complete);
				documentBL.processEx(forecast, IDocument.ACTION_Close, IDocument.STATUS_Closed);
				break;
			case voided:
				forecast.setDocAction(IDocument.ACTION_Complete);
				documentBL.processEx(forecast, IDocument.ACTION_Void, IDocument.STATUS_Voided);
				break;
			case reversed:
				forecast.setDocAction(IDocument.ACTION_Complete);
				documentBL.processEx(forecast, IDocument.ACTION_Reverse_Correct, IDocument.STATUS_Reversed);
				break;
			default:
				throw new AdempiereException("Unhandled M_Forecast action")
						.appendParametersToMessage()
						.setParameter("action:", action);
		}
	}
}
