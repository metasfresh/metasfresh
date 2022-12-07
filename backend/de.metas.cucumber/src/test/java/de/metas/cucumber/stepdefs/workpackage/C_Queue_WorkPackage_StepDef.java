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

package de.metas.cucumber.stepdefs.workpackage;

import com.google.common.collect.ImmutableSet;
import de.metas.async.model.I_C_Queue_Processor;
import de.metas.async.model.I_C_Queue_Processor_Assign;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.util.Services;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;

import java.util.Set;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.*;

public class C_Queue_WorkPackage_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	private final C_Queue_Processor_StepDefData processorTable;

	public C_Queue_WorkPackage_StepDef(final @NonNull C_Queue_Processor_StepDefData processorTable)
	{
		this.processorTable = processorTable;
	}

	@And("^after not more than (.*)s, there are no C_Queue_WorkPackage pending or running in queue (.*)$")
	public void there_are_no_C_Queue_WorkPackage_Pending_Running(final int nrOfSeconds, @NonNull final String queueProcessorIdentifier) throws InterruptedException
	{
		final I_C_Queue_Processor processor = processorTable.get(queueProcessorIdentifier);

		final Set<Integer> assignedPackageProcessorsIds = queryBL.createQueryBuilder(I_C_Queue_Processor_Assign.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Queue_Processor_Assign.COLUMNNAME_C_Queue_Processor_ID, processor.getC_Queue_Processor_ID())
				.create()
				.stream()
				.map(I_C_Queue_Processor_Assign::getC_Queue_PackageProcessor_ID)
				.collect(ImmutableSet.toImmutableSet());

		assertThat(assignedPackageProcessorsIds.size()).isGreaterThan(0);

		final Supplier<Boolean> noPendingOrRunningPackage = () -> {

			final IQueryFilter<I_C_Queue_WorkPackage> isNotDoneYet = queryBL.createCompositeQueryFilter(I_C_Queue_WorkPackage.class)
					.addEqualsFilter(I_C_Queue_WorkPackage.COLUMNNAME_Processed, false)
					.addEqualsFilter(I_C_Queue_WorkPackage.COLUMNNAME_IsError, false)
					.addEqualsFilter(I_C_Queue_WorkPackage.COLUMNNAME_IsReadyForProcessing, true);

			return queryBL.createQueryBuilder(I_C_Queue_WorkPackage.class)
					.addInArrayFilter(I_C_Queue_WorkPackage.COLUMNNAME_C_Queue_PackageProcessor_ID, assignedPackageProcessorsIds)
					.filter(isNotDoneYet)
					.create()
					.count() == 0;
		};

		StepDefUtil.tryAndWait(nrOfSeconds, 1000, noPendingOrRunningPackage);
	}
}
