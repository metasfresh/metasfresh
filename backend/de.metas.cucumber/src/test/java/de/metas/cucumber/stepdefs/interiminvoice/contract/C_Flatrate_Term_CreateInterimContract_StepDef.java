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

package de.metas.cucumber.stepdefs.interiminvoice.contract;

import de.metas.contracts.ConditionsId;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.modular.interim.invoice.command.InterimInvoiceFlatrateTermCreateCommand;
import de.metas.cucumber.stepdefs.C_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.contract.C_Flatrate_Conditions_StepDefData;
import de.metas.order.OrderLineId;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.compiere.model.I_C_OrderLine;
import org.compiere.util.TimeUtil;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;

public class C_Flatrate_Term_CreateInterimContract_StepDef
{
	final C_Flatrate_Conditions_StepDefData flatrateConditionsTable;
	final C_OrderLine_StepDefData orderLineTable;

	public C_Flatrate_Term_CreateInterimContract_StepDef(
			@NonNull final C_Flatrate_Conditions_StepDefData flatrateConditionsTable,
			@NonNull final C_OrderLine_StepDefData orderLineTable)
	{
		this.flatrateConditionsTable = flatrateConditionsTable;
		this.orderLineTable = orderLineTable;
	}

	@And("create interim invoice contract for C_OrderLine")
	public void create_interim_contract(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final String flatrateConditionsIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_Flatrate_Conditions.COLUMNNAME_C_Flatrate_Conditions_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_Flatrate_Conditions flatrateConditions = flatrateConditionsTable.get(flatrateConditionsIdentifier);

			final String orderLineIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_OrderLine.COLUMNNAME_C_OrderLine_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_OrderLine orderLine = orderLineTable.get(orderLineIdentifier);

			final Timestamp dateFrom = DataTableUtil.extractDateTimestampForColumnName(tableRow, "DateFrom");
			final Timestamp dateTo = DataTableUtil.extractDateTimestampForColumnName(tableRow, "DateTo");

			InterimInvoiceFlatrateTermCreateCommand.builder()
					.conditionsId(ConditionsId.ofRepoId(flatrateConditions.getC_Flatrate_Conditions_ID()))
					.orderLineId(OrderLineId.ofRepoId(orderLine.getC_OrderLine_ID()))
					.dateFrom(TimeUtil.asInstantNonNull(dateFrom))
					.dateTo(TimeUtil.asInstantNonNull(dateTo))
					.build()
					.execute();
		}
	}
}
