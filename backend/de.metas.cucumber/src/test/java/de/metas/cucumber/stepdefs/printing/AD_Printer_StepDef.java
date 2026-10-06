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

package de.metas.cucumber.stepdefs.printing;

import de.metas.adempiere.model.I_AD_Printer;
import de.metas.adempiere.model.X_AD_Printer;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.model.InterfaceWrapperHelper;

/**
 * Fixture step for {@code AD_Printer} (logical printer) -- a scenario needing a printer to route documents to
 * creates its own here rather than relying on whichever printer the database happens to ship.
 */
@RequiredArgsConstructor
public class AD_Printer_StepDef
{
	@NonNull private final AD_Printer_StepDefData printerTable;

	/**
	 * Creates one general-purpose {@code AD_Printer} per DataTable row -- the logical printer a customer adds via
	 * the window before routing documents to it.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Identifier</b> — (required) alias for cross-step reference<br>
	 *   <b>PrinterName</b> — (optional) {@code AD_Printer.PrinterName}; omitted means a per-run-unique name is
	 *       generated from the identifier<br>
	 * @cucumber.example
	 * <pre>
	 * Given metasfresh contains AD_Printer:
	 *   | Identifier |
	 *   | printer    |
	 * </pre>
	 */
	@Given("metasfresh contains AD_Printer:")
	public void metasfresh_contains_AD_Printer(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createPrinter);
	}

	private void createPrinter(@NonNull final DataTableRow row)
	{
		final I_AD_Printer record = InterfaceWrapperHelper.newInstance(I_AD_Printer.class);
		record.setPrinterName(row.getAsOptionalString(I_AD_Printer.COLUMNNAME_PrinterName)
				.orElseGet(() -> row.suggestValueAndName().getName()));
		record.setPrinterType(X_AD_Printer.PRINTERTYPE_General);
		record.setIsActive(true);
		InterfaceWrapperHelper.save(record);

		row.getAsIdentifier().putOrReplace(printerTable, record);
	}

	/**
	 * Deactivates every {@code AD_Printer} this scenario created, so they do not accumulate as active printers on a
	 * persistent local DB. Deactivated rather than deleted, because routings created on them may still reference
	 * them while the other {@code @After} hooks run.
	 */
	@After
	public void deactivateCreatedPrintersAfterScenario()
	{
		for (final I_AD_Printer printer : printerTable.getRecords())
		{
			printer.setIsActive(false);
			InterfaceWrapperHelper.save(printer);
		}
	}
}
