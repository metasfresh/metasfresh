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

import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.interim.invoice.service.IInterimInvoiceFlatrateTermBL;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.contract.C_Flatrate_Term_StepDefData;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.*;

public class C_Flatrate_Term_CreateInterimContract_StepDef
{
	private final C_Flatrate_Term_StepDefData flatrateTermTable;
	private final IInterimInvoiceFlatrateTermBL interimInvoiceFlatrateTermBL = Services.get(IInterimInvoiceFlatrateTermBL.class);

	public C_Flatrate_Term_CreateInterimContract_StepDef(
			@NonNull final C_Flatrate_Term_StepDefData flatrateTermTable)
	{
		this.flatrateTermTable = flatrateTermTable;
	}

	@And("invoke \"C_BPartner_InterimContract_Create\" process:")
	public void invokeC_BPartner_InterimContract_Create_process(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final String flatrateTermTableIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Term_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_Flatrate_Term flatrateTerm = flatrateTermTable.get(flatrateTermTableIdentifier);

			final Timestamp dateFrom = DataTableUtil.extractDateTimestampForColumnName(tableRow, "DateFrom");
			final Timestamp dateTo = DataTableUtil.extractDateTimestampForColumnName(tableRow, "DateTo");

			interimInvoiceFlatrateTermBL.create(flatrateTerm, dateFrom, dateTo);
		}
	}

	@And("invoke \"C_BPartner_InterimContract_Create\" process for modular contract with error")
	public void invokeC_BPartner_InterimContract_Create_process_with_error(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final String flatrateTermTableIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Term_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_Flatrate_Term flatrateTerm = flatrateTermTable.get(flatrateTermTableIdentifier);

			final Timestamp dateFrom = DataTableUtil.extractDateTimestampForColumnName(tableRow, "DateFrom");
			final Timestamp dateTo = DataTableUtil.extractDateTimestampForColumnName(tableRow, "DateTo");

			final String errorCode = DataTableUtil.extractStringForColumnName(tableRow, "errorCode");

			try
			{
				interimInvoiceFlatrateTermBL.create(flatrateTerm, dateFrom, dateTo);
				assertThat(1).as("An Exception should have been thrown !").isEqualTo(2);
			}
			catch (final AdempiereException exception)
			{
				assertThat(exception.getErrorCode()).as("ErrorCode of %s", exception).isEqualTo(errorCode);
			}
		}

	}
}
