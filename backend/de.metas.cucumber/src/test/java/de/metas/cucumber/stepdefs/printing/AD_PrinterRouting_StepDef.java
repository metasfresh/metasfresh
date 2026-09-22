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

import de.metas.adempiere.model.I_AD_PrinterRouting;
import de.metas.cache.CacheMgt;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.doctype.C_DocType_StepDefData;
import de.metas.document.DocTypeId;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.model.InterfaceWrapperHelper;

import java.util.List;

/**
 * Fixture step for {@code AD_PrinterRouting} -- a scenario needing a doctype-specific printer routing (to prove
 * the doctype dimension wins over a catch-all routing) creates one here rather than fabricating it directly on
 * whatever step-def happens to need it.
 */
@RequiredArgsConstructor
public class AD_PrinterRouting_StepDef
{
	@NonNull private final AD_PrinterRouting_StepDefData printerRoutingTable;
	@NonNull private final C_DocType_StepDefData docTypeTable;

	/**
	 * Creates one {@code AD_PrinterRouting} row per DataTable row -- the same kind of row the customer would add
	 * via the window to route one document type to a specific printer. {@code AD_Org_ID} is left at "any"
	 * ({@code 0}), matching the shape of the routings already shipped on the target instance.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Identifier</b> — (required) alias for cross-step reference<br>
	 *   <b>C_DocType_ID</b> — (optional, identifier-ref) the routing's document-type dimension; omitted means a
	 *       catch-all routing (every dimension null)<br>
	 *   <b>AD_Printer_ID</b> — (required) the (pre-existing) printer to route to<br>
	 * @cucumber.depends StepDefData: C_DocType_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Given metasfresh contains AD_PrinterRouting:
	 *   | Identifier        | C_DocType_ID      | AD_Printer_ID |
	 *   | routingProduktion | docTypeProduktion | 1000000       |
	 * </pre>
	 */
	@Given("metasfresh contains AD_PrinterRouting:")
	public void metasfresh_contains_AD_PrinterRouting(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createPrinterRouting);
	}

	private void createPrinterRouting(@NonNull final DataTableRow row)
	{
		final I_AD_PrinterRouting record = InterfaceWrapperHelper.newInstance(I_AD_PrinterRouting.class);
		record.setAD_Org_ID(StepDefConstants.ORG_ID_SYSTEM.getRepoId());
		record.setAD_Printer_ID(row.getAsInt(I_AD_PrinterRouting.COLUMNNAME_AD_Printer_ID));

		row.getAsOptionalIdentifier(I_AD_PrinterRouting.COLUMNNAME_C_DocType_ID)
				.filter(StepDefDataIdentifier::isNotNullPlaceholder)
				.map(identifier -> identifier.lookupIdIn(docTypeTable))
				.map(DocTypeId::getRepoId)
				.ifPresent(record::setC_DocType_ID);

		record.setSeqNo(10);
		record.setIsActive(true);

		InterfaceWrapperHelper.save(record);
		CacheMgt.get().reset(I_AD_PrinterRouting.Table_Name);

		row.getAsIdentifier().putOrReplace(printerRoutingTable, record);
	}

	/**
	 * Guaranteed-execution cleanup for {@link #metasfresh_contains_AD_PrinterRouting} -- an {@code @After} hook
	 * rather than a trailing Gherkin step, since Cucumber skips remaining steps once one fails, i.e. on exactly
	 * the runs that need the cleanup. A no-op for every scenario that never called that step.
	 * <p>
	 * Unlike a step that MUTATES an existing shared row (which restores the captured prior value), every row
	 * here is one this scenario itself INSERTED -- there is no prior value to restore to, so cleanup is a plain
	 * delete of exactly the rows this scenario's own {@link #printerRoutingTable} holds.
	 */
	@After
	public void deleteCreatedPrinterRoutingsAfterScenario()
	{
		final List<I_AD_PrinterRouting> createdRoutings = printerRoutingTable.getRecords();
		if (createdRoutings.isEmpty())
		{
			return;
		}

		for (final I_AD_PrinterRouting routing : createdRoutings)
		{
			InterfaceWrapperHelper.delete(routing);
		}

		CacheMgt.get().reset(I_AD_PrinterRouting.Table_Name);
	}
}
