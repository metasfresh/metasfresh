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
import de.metas.esb.edi.model.I_EDI_DesadvLine;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.Env;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class EDI_M_InOut_StepDef
{
	@NonNull private final IWorkPackageQueueFactory workPackageQueueFactory = Services.get(IWorkPackageQueueFactory.class);
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
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

	/**
	 * Assert that the EDI_Desadv referenced by the given M_InOut has expected properties.
	 * Used to verify aggregated DESADV semantics — e.g. when a shipment aggregates multiple
	 * source orders, the header POReference should be empty/null when the source orders disagree.
	 * <p>
	 * DataTable columns (DataTableRow smart-lookup handles {@code OPT.} prefix automatically):
	 * <ul>
	 *     <li>{@code M_InOut_ID} (required, identifier-ref) — the shipment whose linked DESADV is checked</li>
	 *     <li>{@code POReference} (optional) — expected header POReference; empty/blank cell means the DESADV's POReference must be null or blank</li>
	 *     <li>{@code LineCount} (optional, int) — expected number of active EDI_DesadvLine records on the DESADV</li>
	 * </ul>
	 * <p>
	 * Example:
	 * <pre>{@code
	 * Then the EDI_Desadv linked to M_InOut has the following properties:
	 *   | M_InOut_ID    | OPT.POReference | OPT.LineCount |
	 *   | io_S29231_060 |                 | 2             |
	 * }</pre>
	 * <p>
	 * The M_InOut's EDI_Desadv_ID link is created asynchronously by the EDI workpackage processor;
	 * this step retries the lookup for up to {@value #ASSERT_DESADV_LINKED_TIMEOUT_SEC}s to absorb
	 * the async-flush race on CI under load.
	 */
	@Then("the EDI_Desadv linked to M_InOut has the following properties:")
	public void assertDesadvLinkedToInOut(@NonNull final DataTable table)
	{
		DataTableRows.of(table).forEach(this::assertDesadvLinkedToInOut);
	}

	private static final int ASSERT_DESADV_LINKED_TIMEOUT_SEC = 60;

	private void assertDesadvLinkedToInOut(@NonNull final DataTableRow row)
	{
		final I_M_InOut inout = InterfaceWrapperHelper.create(
				row.getAsIdentifier(I_M_InOut.COLUMNNAME_M_InOut_ID).lookupNotNullIn(inoutTable),
				I_M_InOut.class);

		final int desadvId;
		try
		{
			desadvId = StepDefUtil.tryAndWaitForItem(
					ASSERT_DESADV_LINKED_TIMEOUT_SEC,
					1000,
					() -> {
						InterfaceWrapperHelper.refresh(inout);
						final int currentDesadvId = inout.getEDI_Desadv_ID();
						return currentDesadvId > 0 ? Optional.of(currentDesadvId) : Optional.empty();
					});
		}
		catch (final InterruptedException e)
		{
			Thread.currentThread().interrupt();
			throw new RuntimeException("Interrupted while waiting for M_InOut.EDI_Desadv_ID", e);
		}

		final I_EDI_Desadv desadv = InterfaceWrapperHelper.load(desadvId, I_EDI_Desadv.class);

		row.getAsOptionalString(I_EDI_Desadv.COLUMNNAME_POReference).ifPresent(expectedPO -> {
			final String expectedNorm = Check.isBlank(expectedPO) ? null : expectedPO.trim();
			final String actualNorm = Check.isBlank(desadv.getPOReference()) ? null : desadv.getPOReference().trim();
			assertThat(actualNorm)
					.as("Header POReference for EDI_Desadv %s", desadvId)
					.isEqualTo(expectedNorm);
		});

		row.getAsOptionalBigDecimal("LineCount").ifPresent(expectedCount -> {
			final long actualCount = queryBL.createQueryBuilder(I_EDI_DesadvLine.class)
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(I_EDI_DesadvLine.COLUMNNAME_EDI_Desadv_ID, desadvId)
					.create()
					.count();
			assertThat(actualCount)
					.as("Number of EDI_DesadvLine records for EDI_Desadv %s", desadvId)
					.isEqualTo(expectedCount.longValueExact());
		});
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
