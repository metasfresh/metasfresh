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

package de.metas.cucumber.stepdefs.edi;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.shipment.M_InOut_StepDefData;
import de.metas.edi.model.I_M_InOut;
import de.metas.esb.edi.model.I_EDI_Desadv;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.model.InterfaceWrapperHelper;

@RequiredArgsConstructor
public class EDI_M_InOut_StepDef
{
	private final @NonNull M_InOut_StepDefData inoutTable;

	@Then("^after not more than (.*)s, M_InOut records have the following export status$")
	public void validate_export_status(final int timeoutSec, @NonNull final DataTable table) throws InterruptedException
	{
		DataTableRows.of(table).forEach(row -> validateExportStatus(timeoutSec, row));
	}

	private void validateExportStatus(
			final int timeoutSec,
			@NonNull final DataTableRow tableRow) throws InterruptedException
	{
		final I_M_InOut inoutRecord =
				InterfaceWrapperHelper.create(
						tableRow.getAsIdentifier(I_M_InOut.COLUMNNAME_M_InOut_ID).lookupNotNullIn(inoutTable),
						I_M_InOut.class);

		final String exportStatus = tableRow.getAsString(I_EDI_Desadv.COLUMNNAME_EDI_ExportStatus);

		StepDefUtil.tryAndWait(timeoutSec, 1000, () -> {
			InterfaceWrapperHelper.refresh(inoutRecord);
			return exportStatus.equals(inoutRecord.getEDI_ExportStatus());
		});
	}

}
