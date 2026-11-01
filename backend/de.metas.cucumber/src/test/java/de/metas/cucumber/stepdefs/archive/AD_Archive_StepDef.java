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

package de.metas.cucumber.stepdefs.archive;

import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.util.IdentifiersResolver;
import de.metas.util.Services;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Archive;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Verifies that an {@code AD_Archive} record (the generated document PDF) exists for a given document.
 */
@RequiredArgsConstructor
public class AD_Archive_StepDef
{
	@NonNull private final IdentifiersResolver identifiersResolver;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/**
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then an AD_Archive exists for the record identified by shipment
	 * </pre>
	 */
	@Then("an AD_Archive exists for the record identified by {string}")
	public void assert_archive_exists(@NonNull final String recordIdentifier)
	{
		final TableRecordReference recordRef = identifiersResolver.getTableRecordReference(StepDefDataIdentifier.ofString(recordIdentifier));

		assertThat(countArchives(recordRef))
				.as("AD_Archive count for record %s", recordRef)
				.isGreaterThanOrEqualTo(1);
	}

	/**
	 * Waits for the asynchronous archiving of the document PDF, which starts when the document is completed.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then after not more than 60s, an AD_Archive exists for the record identified by "salesInvoice1"
	 * </pre>
	 */
	@Then("after not more than {int}s, an AD_Archive exists for the record identified by {string}")
	public void assert_archive_exists_eventually(final int timeoutSec, @NonNull final String recordIdentifier) throws InterruptedException
	{
		final TableRecordReference recordRef = identifiersResolver.getTableRecordReference(StepDefDataIdentifier.ofString(recordIdentifier));

		StepDefUtil.tryAndWait(timeoutSec, 500, () -> countArchives(recordRef) >= 1);
	}

	/**
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then no AD_Archive exists for the record identified by "salesInvoice2"
	 * </pre>
	 */
	@Then("no AD_Archive exists for the record identified by {string}")
	public void assert_archive_does_not_exist(@NonNull final String recordIdentifier)
	{
		final TableRecordReference recordRef = identifiersResolver.getTableRecordReference(StepDefDataIdentifier.ofString(recordIdentifier));

		assertThat(countArchives(recordRef))
				.as("AD_Archive count for record %s", recordRef)
				.isZero();
	}

	private int countArchives(@NonNull final TableRecordReference recordRef)
	{
		return queryBL.createQueryBuilder(I_AD_Archive.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Archive.COLUMNNAME_AD_Table_ID, recordRef.getAD_Table_ID())
				.addEqualsFilter(I_AD_Archive.COLUMNNAME_Record_ID, recordRef.getRecord_ID())
				.create()
				.count();
	}
}
