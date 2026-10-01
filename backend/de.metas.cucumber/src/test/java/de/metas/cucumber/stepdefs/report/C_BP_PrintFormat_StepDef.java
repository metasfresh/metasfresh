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

package de.metas.cucumber.stepdefs.report;

import de.metas.bpartner.BPartnerId;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.util.StringUtils;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BP_PrintFormat;

import static org.compiere.model.I_C_BP_PrintFormat.COLUMNNAME_C_BPartner_ID;
import static org.compiere.model.I_C_BP_PrintFormat.COLUMNNAME_IsAutoPrint;
import static org.compiere.model.I_C_BP_PrintFormat.COLUMNNAME_IsDropShip;

/**
 * Sets up per-business-partner print-format preferences ({@code C_BP_PrintFormat}) -- e.g. the drop-ship
 * auto-print override that {@code DocumentReportAdvisorUtil.resolveSuppressAutoPrint} reads.
 */
@RequiredArgsConstructor
public class C_BP_PrintFormat_StepDef
{
	@NonNull private final C_BPartner_StepDefData bpartnerTable;

	/**
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>C_BPartner_ID</b> — (required, identifier-ref) the business partner this preference applies to<br>
	 *   <b>IsDropShip</b> — (optional) restricts the row to drop-ship (true) or non-drop-ship (false) shipments; omitted matches either<br>
	 *   <b>IsAutoPrint</b> — (optional) "false" suppresses auto-print for matching documents; "true" or omitted leaves auto-print enabled<br>
	 * @cucumber.depends StepDefData: C_BPartner_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And metasfresh contains C_BP_PrintFormat:
	 *   | C_BPartner_ID | IsDropShip | IsAutoPrint |
	 *   | customer      | true       | false       |
	 * </pre>
	 */
	@Given("metasfresh contains C_BP_PrintFormat:")
	public void metasfresh_contains_C_BP_PrintFormat(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createC_BP_PrintFormat);
	}

	private void createC_BP_PrintFormat(@NonNull final DataTableRow row)
	{
		final BPartnerId bpartnerId = row.getAsIdentifier(COLUMNNAME_C_BPartner_ID).lookupIdIn(bpartnerTable);

		final I_C_BP_PrintFormat record = InterfaceWrapperHelper.newInstance(I_C_BP_PrintFormat.class);
		record.setC_BPartner_ID(bpartnerId.getRepoId());

		final Boolean isDropShip = row.getAsOptionalBoolean(COLUMNNAME_IsDropShip).toBooleanOrNull();
		if (isDropShip != null)
		{
			record.setIsDropShip(StringUtils.ofBoolean(isDropShip));
		}

		final Boolean isAutoPrint = row.getAsOptionalBoolean(COLUMNNAME_IsAutoPrint).toBooleanOrNull();
		if (isAutoPrint != null)
		{
			record.setIsAutoPrint(StringUtils.ofBoolean(isAutoPrint));
		}

		InterfaceWrapperHelper.save(record);
	}
}
