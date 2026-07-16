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

import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.shipment.M_InOut_StepDefData;
import de.metas.edi.async.spi.impl.EDIWorkpackageProcessor;
import de.metas.edi.model.I_M_InOut;
import de.metas.esb.edi.model.I_EDI_Desadv;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.Env;

@RequiredArgsConstructor
public class EDI_M_InOut_StepDef
{
	@NonNull private final IWorkPackageQueueFactory workPackageQueueFactory = Services.get(IWorkPackageQueueFactory.class);
	@NonNull private final M_InOut_StepDefData inoutTable;

	@Then("^after not more than (.*)s, M_InOut records have the following export status$")
	public void validate_export_status(final int timeoutSec, @NonNull final DataTable table)
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

	@Then("M_InOut is enqueued for EDI export")
	public void enqueue_edi_export_shipment(@NonNull final DataTable table)
	{
		DataTableRows.of(table).forEach(this::enqueue_edi_export_shipment);
	}

	private void enqueue_edi_export_shipment(@NonNull final DataTableRow row)
	{
		final org.compiere.model.I_M_InOut shipment = row.getAsIdentifier(I_M_InOut.COLUMNNAME_M_InOut_ID).lookupNotNullIn(inoutTable);

		final IWorkPackageQueue queue = workPackageQueueFactory.getQueueForEnqueuing(Env.getCtx(), EDIWorkpackageProcessor.class);

		queue.newWorkPackage()
				.setPriority(IWorkPackageQueue.PRIORITY_AUTO)
				.addElement(shipment)
				.bindToThreadInheritedTrx()
				.buildAndEnqueue();
	}
}
