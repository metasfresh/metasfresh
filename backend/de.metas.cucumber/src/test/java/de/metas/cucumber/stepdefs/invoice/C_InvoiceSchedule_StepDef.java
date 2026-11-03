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

package de.metas.cucumber.stepdefs.invoice;

import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefConstants;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.I_C_InvoiceSchedule;
import org.compiere.model.X_C_InvoiceSchedule;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/**
 * Responsible for {@link I_C_InvoiceSchedule} master data.
 */
@RequiredArgsConstructor
public class C_InvoiceSchedule_StepDef
{
	@NonNull private final C_InvoiceSchedule_StepDefData invoiceScheduleTable;

	/**
	 * Creates monthly invoice schedules.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Identifier</b> — (required) alias for cross-step reference<br>
	 *   <b>InvoiceDay</b> — (required) day of month on which the schedule invoices (a day beyond the month's end means its last day)<br>
	 *   <b>InvoiceDistance</b> — (optional, default 1) number of months between two invoicings<br>
	 *   <b>Name</b> — (optional) auto-generated when omitted<br>
	 * @cucumber.depends StepDefData: C_InvoiceSchedule_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And metasfresh contains C_InvoiceSchedules:
	 *   | Identifier       | InvoiceDay | InvoiceDistance |
	 *   | monthlySchedule  | 31         | 1               |
	 * </pre>
	 */
	@Given("metasfresh contains C_InvoiceSchedules:")
	public void metasfresh_contains_C_InvoiceSchedules(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final I_C_InvoiceSchedule schedule = newInstance(I_C_InvoiceSchedule.class);
			schedule.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());
			schedule.setName(row.suggestValueAndName().getName());
			schedule.setInvoiceFrequency(X_C_InvoiceSchedule.INVOICEFREQUENCY_Monthly);
			schedule.setInvoiceDay(row.getAsInt(I_C_InvoiceSchedule.COLUMNNAME_InvoiceDay));
			schedule.setInvoiceDistance(row.getAsOptionalInt(I_C_InvoiceSchedule.COLUMNNAME_InvoiceDistance).orElse(1));
			saveRecord(schedule);

			invoiceScheduleTable.putOrReplace(row.getAsIdentifier(), schedule);
		});
	}
}
