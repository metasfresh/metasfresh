package org.adempiere.ad.modelvalidator;

import org.adempiere.model.InterfaceWrapperHelper;

import lombok.NonNull;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

public final class InterceptorUtil
{
	private InterceptorUtil()
	{
	}

	public static boolean isJustActivated(@NonNull final Object model)
	{
		final IIsActiveAware activeAware = InterfaceWrapperHelper.create(model, IIsActiveAware.class);
		if (!activeAware.isActive())
		{
			return false;
		}
		final IIsActiveAware oldActiveAware = InterfaceWrapperHelper.createOld(model, IIsActiveAware.class);
		return !oldActiveAware.isActive();
	}

	public static boolean isJustDeactivated(@NonNull final Object model)
	{
		final IIsActiveAware activeAware = InterfaceWrapperHelper.create(model, IIsActiveAware.class);
		if (activeAware.isActive())
		{
			return false;
		}
		final IIsActiveAware oldActiveAware = InterfaceWrapperHelper.createOld(model, IIsActiveAware.class);
		return oldActiveAware.isActive();
	}

	public static boolean isJustDeactivatedOrUnProcessed(@NonNull final Object model)
	{
		final IActiveAndProcessedAware newAware = InterfaceWrapperHelper.create(model, IActiveAndProcessedAware.class);
		final IActiveAndProcessedAware oldAware = InterfaceWrapperHelper.createOld(model, IActiveAndProcessedAware.class);

		final boolean deactivated = oldAware.isActive() && !newAware.isActive();
		final boolean unprocessed = oldAware.isProcessed() && !newAware.isProcessed();

		return deactivated || unprocessed;
	}

	public static boolean isJustActivatedOrProcessed(@NonNull final Object model)
	{
		final IActiveAndProcessedAware newAware = InterfaceWrapperHelper.create(model, IActiveAndProcessedAware.class);
		final IActiveAndProcessedAware oldAware = InterfaceWrapperHelper.createOld(model, IActiveAndProcessedAware.class);

		final boolean activated = !oldAware.isActive() && newAware.isActive();
		final boolean processed = !oldAware.isProcessed() && newAware.isProcessed();

		return activated || processed;
	}

	public static interface IIsActiveAware
	{
		boolean isActive();
	}

	public static interface IActiveAndProcessedAware
	{
		boolean isActive();

		boolean isProcessed();
	}
}
