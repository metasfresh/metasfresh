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

package de.metas.cucumber.stepdefs.edi;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.shipment.M_InOut_StepDefData;
import de.metas.esb.edi.model.I_EDI_Desadv_M_InOut;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for the {@code EDI_Desadv_M_InOut} junction table that records
 * the many-to-many link between {@code EDI_Desadv} and {@code M_InOut}.
 * Covers both the "N DESADVs → 1 M_InOut" (consolidated multi-source-order) and
 * "1 DESADV → N M_InOuts" (partial deliveries on the same source order) shapes.
 */
@RequiredArgsConstructor
public class EDI_Desadv_M_InOut_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final @NonNull EDI_Desadv_StepDefData ediDesadvTable;
	private final @NonNull M_InOut_StepDefData inoutTable;

	/**
	 * Asserts that the {@code EDI_Desadv_M_InOut} junction table contains a row linking the
	 * given {@code EDI_Desadv} to the given {@code M_InOut}, and (optionally) asserts the
	 * total number of junction rows for that {@code EDI_Desadv_ID}.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>EDI_Desadv_ID</b> — (required, identifier-ref) the DESADV whose junction row is asserted<br>
	 *   <b>M_InOut_ID</b> — (required, identifier-ref) the shipment that must be linked to the DESADV<br>
	 *   <b>ExpectedRowCountForDesadv</b> — (optional) total number of junction rows for the DESADV (covers the 1→N case)<br>
	 * @cucumber.depends StepDefData: EDI_Desadv_StepDefData, M_InOut_StepDefData
	 * @cucumber.example <pre>
	 * Then EDI_Desadv_M_InOut records are found:
	 *   | EDI_Desadv_ID | M_InOut_ID | ExpectedRowCountForDesadv |
	 *   | d_S29231_160  | io_A       | 2                         |
	 *   | d_S29231_160  | io_B       | 2                         |
	 * </pre>
	 */
	@Then("EDI_Desadv_M_InOut records are found:")
	public void junctionRecordsAreFound(@NonNull final DataTable table)
	{
		DataTableRows.of(table).forEach(this::assertJunctionRow);
	}

	private void assertJunctionRow(@NonNull final DataTableRow row)
	{
		final int ediDesadvId = row.getAsIdentifier(I_EDI_Desadv_M_InOut.COLUMNNAME_EDI_Desadv_ID)
				.lookupNotNullIn(ediDesadvTable)
				.getEDI_Desadv_ID();

		final int inOutId = row.getAsIdentifier(I_EDI_Desadv_M_InOut.COLUMNNAME_M_InOut_ID)
				.lookupNotNullIn(inoutTable)
				.getM_InOut_ID();

		final List<I_EDI_Desadv_M_InOut> match = queryBL.createQueryBuilder(I_EDI_Desadv_M_InOut.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_EDI_Desadv_M_InOut.COLUMNNAME_EDI_Desadv_ID, ediDesadvId)
				.addEqualsFilter(I_EDI_Desadv_M_InOut.COLUMNNAME_M_InOut_ID, inOutId)
				.create()
				.list(I_EDI_Desadv_M_InOut.class);

		assertThat(match)
				.as("EDI_Desadv_M_InOut row linking EDI_Desadv_ID=%d to M_InOut_ID=%d", ediDesadvId, inOutId)
				.hasSize(1);

		row.getAsOptionalInt("ExpectedRowCountForDesadv")
				.ifPresent(expectedCount -> {
					final long total = queryBL.createQueryBuilder(I_EDI_Desadv_M_InOut.class)
							.addOnlyActiveRecordsFilter()
							.addEqualsFilter(I_EDI_Desadv_M_InOut.COLUMNNAME_EDI_Desadv_ID, ediDesadvId)
							.create()
							.count();
					assertThat(total)
							.as("Total EDI_Desadv_M_InOut rows for EDI_Desadv_ID=%d", ediDesadvId)
							.isEqualTo((long)expectedCount);
				});
	}
}
