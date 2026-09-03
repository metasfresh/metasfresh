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

import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.util.IdentifiersResolver;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.util.Services;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Archive;
import org.compiere.model.IQuery;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Verifies how many {@code C_Printing_Queue} items exist for the archive(s) of a given document -- i.e.
 * whether that document's printout was (auto-)enqueued for printing.
 */
@RequiredArgsConstructor
public class C_Printing_Queue_StepDef
{
	@NonNull private final IdentifiersResolver identifiersResolver;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/**
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then C_Printing_Queue contains 0 items for the record identified by shipment
	 * </pre>
	 */
	@Then("C_Printing_Queue contains {int} items for the record identified by {string}")
	public void assert_printing_queue_item_count(final int expectedCount, @NonNull final String recordIdentifier)
	{
		final TableRecordReference recordRef = identifiersResolver.getTableRecordReference(StepDefDataIdentifier.ofString(recordIdentifier));

		final IQuery<I_AD_Archive> archivesOfRecord = queryBL.createQueryBuilder(I_AD_Archive.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Archive.COLUMNNAME_AD_Table_ID, recordRef.getAD_Table_ID())
				.addEqualsFilter(I_AD_Archive.COLUMNNAME_Record_ID, recordRef.getRecord_ID())
				.create();

		final int count = queryBL.createQueryBuilder(I_C_Printing_Queue.class)
				.addOnlyActiveRecordsFilter()
				.addInSubQueryFilter(I_C_Printing_Queue.COLUMNNAME_AD_Archive_ID, I_AD_Archive.COLUMNNAME_AD_Archive_ID, archivesOfRecord)
				.create()
				.count();

		assertThat(count)
				.as("C_Printing_Queue item count for record %s", recordRef)
				.isEqualTo(expectedCount);
	}
}
