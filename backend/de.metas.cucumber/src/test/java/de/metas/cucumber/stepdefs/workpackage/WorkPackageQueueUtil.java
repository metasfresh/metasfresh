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

package de.metas.cucumber.stepdefs.workpackage;

import com.google.common.collect.ImmutableSet;
import de.metas.async.model.I_C_Queue_PackageProcessor;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;

/**
 * Shared queries over {@link I_C_Queue_WorkPackage} for cucumber step defs that need to know how many
 * workpackages are still pending for a given {@link I_C_Queue_PackageProcessor}, identified by its short name
 * (e.g. {@code CreateMissingShipmentSchedules} for {@code CreateMissingShipmentSchedulesWorkpackageProcessor}).
 * <p>
 * A plain PicoContainer-managed instance (see {@code cucumber-picocontainer}), constructor-injected into step
 * defs the same way {@link de.metas.cucumber.stepdefs.util.IdentifiersResolver} is — never a
 * {@code @UtilityClass}/static-field shape (forbidden by {@code docs/coding-rules/service-injection.md} §2).
 */
public class WorkPackageQueueUtil
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private static final String WORKPACKAGE_PROCESSOR_CLASS_SUFFIX = "WorkpackageProcessor";

	/**
	 * Counts how many {@code C_Queue_WorkPackage} rows are currently pending (not yet processed, not errored,
	 * ready for processing) for the workpackage processor identified by its short name — i.e. how many runs of
	 * that processor are still waiting to happen, whether because none has run yet or because a run re-enqueued
	 * a follow-up workpackage for the remaining work.
	 * <p>
	 * Deliberately excludes not-ready workpackages ({@code IsReadyForProcessing=false}): a workpackage bound to
	 * a rolled-back transaction never becomes ready, so counting/waiting on it would turn an intermittent flake
	 * into a deterministic timeout. A workpackage that is currently BEING processed IS still counted — the queue
	 * clears {@code IsReadyForProcessing} only while building the workpackage, never during processing — so a
	 * caller waiting for this count to reach zero really does wait out an in-flight run. Keep it that way.
	 *
	 * @param processorShortName e.g. {@code CreateMissingShipmentSchedules} or {@code UpdateInvalidShipmentSchedules}
	 */
	public int countPendingWorkPackages(@NonNull final String processorShortName)
	{
		final ImmutableSet<Integer> packageProcessorIds = resolvePackageProcessorIds(processorShortName);
		if (packageProcessorIds.isEmpty())
		{
			return 0;
		}

		return pendingWorkPackagesQuery(packageProcessorIds)
				.create()
				.count();
	}

	IQueryBuilder<I_C_Queue_WorkPackage> pendingWorkPackagesQuery(@NonNull final ImmutableSet<Integer> packageProcessorIds)
	{
		return queryBL.createQueryBuilder(I_C_Queue_WorkPackage.class)
				.addInArrayFilter(I_C_Queue_WorkPackage.COLUMNNAME_C_Queue_PackageProcessor_ID, packageProcessorIds)
				.addEqualsFilter(I_C_Queue_WorkPackage.COLUMNNAME_Processed, false)
				.addEqualsFilter(I_C_Queue_WorkPackage.COLUMNNAME_IsError, false)
				.addEqualsFilter(I_C_Queue_WorkPackage.COLUMNNAME_IsReadyForProcessing, true);
	}

	/**
	 * Resolves the {@code C_Queue_PackageProcessor_ID}s whose {@code Classname} simple name matches the given
	 * short name (with a {@value WORKPACKAGE_PROCESSOR_CLASS_SUFFIX} suffix appended if not already present) —
	 * e.g. {@code CreateMissingShipmentSchedules} resolves {@code CreateMissingShipmentSchedulesWorkpackageProcessor}.
	 */
	ImmutableSet<Integer> resolvePackageProcessorIds(@NonNull final String processorShortName)
	{
		final String processorSimpleClassName = processorShortName.endsWith(WORKPACKAGE_PROCESSOR_CLASS_SUFFIX)
				? processorShortName
				: processorShortName + WORKPACKAGE_PROCESSOR_CLASS_SUFFIX;
		final String classnameSuffix = "." + processorSimpleClassName;

		return queryBL.createQueryBuilder(I_C_Queue_PackageProcessor.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.filter(packageProcessor -> packageProcessor.getClassname() != null && packageProcessor.getClassname().endsWith(classnameSuffix))
				.map(I_C_Queue_PackageProcessor::getC_Queue_PackageProcessor_ID)
				.collect(ImmutableSet.toImmutableSet());
	}
}
