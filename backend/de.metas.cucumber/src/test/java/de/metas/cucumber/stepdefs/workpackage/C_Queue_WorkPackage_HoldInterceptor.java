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
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;

import java.sql.Timestamp;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Holds new {@code C_Queue_WorkPackage}s of selected workpackage processors: a held workpackage is inserted already
 * claimed ({@code LockedAt} set, in the very transaction that creates it), so the background {@code QueueProcessorPlanner}
 * — which only polls rows with {@code LockedAt IS NULL} — never runs it; a step runs or releases it explicitly.
 * Setting the claim before the insert (rather than after the commit) leaves no window in which the planner could win.
 * <p>
 * Registered once (there is no API to unregister an interceptor) and does nothing unless a processor is held via
 * {@link #hold(Set, Timestamp)}. Not a Spring component, so it is never picked up outside of the steps that use it.
 */
@Interceptor(I_C_Queue_WorkPackage.class)
final class C_Queue_WorkPackage_HoldInterceptor
{
	private static final C_Queue_WorkPackage_HoldInterceptor INSTANCE = new C_Queue_WorkPackage_HoldInterceptor();
	private static boolean registered = false; // guarded by the class monitor, see registerOnce()

	private final Set<Integer> heldPackageProcessorIds = ConcurrentHashMap.newKeySet();
	private volatile Timestamp claimLockedAt;

	static void hold(@NonNull final Set<Integer> packageProcessorIds, @NonNull final Timestamp claimLockedAt)
	{
		registerOnce();
		INSTANCE.claimLockedAt = claimLockedAt;
		INSTANCE.heldPackageProcessorIds.addAll(packageProcessorIds);
	}

	static void stopHolding(@NonNull final Set<Integer> packageProcessorIds)
	{
		INSTANCE.heldPackageProcessorIds.removeAll(packageProcessorIds);
	}

	/** @return the processors currently held; all of them are released by {@link #stopHolding(Set)}. */
	static ImmutableSet<Integer> getHeldPackageProcessorIds()
	{
		return ImmutableSet.copyOf(INSTANCE.heldPackageProcessorIds);
	}

	private static synchronized void registerOnce()
	{
		if (!registered)
		{
			Services.get(IModelInterceptorRegistry.class).addModelInterceptor(INSTANCE);
			registered = true;
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_NEW)
	public void beforeNew(@NonNull final I_C_Queue_WorkPackage workPackage)
	{
		if (heldPackageProcessorIds.contains(workPackage.getC_Queue_PackageProcessor_ID()))
		{
			workPackage.setLockedAt(claimLockedAt);
		}
	}
}
