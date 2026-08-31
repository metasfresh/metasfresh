/*
 * #%L
 * de.metas.deliveryplanning.base
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

package de.metas.deliveryplanning.process;

import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.ProcessPreconditionsResolution;
import lombok.NonNull;

/**
 * The selection-shaped precondition guards the delivery planning gridactions repeat verbatim - "is anything
 * selected", "is too much selected", "is more than one row selected". Business rules stay on the owning process.
 */
public final class DeliveryPlanningProcessHelper
{
	private DeliveryPlanningProcessHelper()
	{
	}

	public static ProcessPreconditionsResolution checkAnySelection(@NonNull final IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	public static ProcessPreconditionsResolution checkAtMostSelected(
			@NonNull final IProcessPreconditionsContext context,
			final int maxSelectionSize)
	{
		if (context.isMoreThanAllowedSelected(maxSelectionSize))
		{
			return ProcessPreconditionsResolution.rejectBecauseTooManyRecordsSelected(maxSelectionSize);
		}

		return ProcessPreconditionsResolution.accept();
	}

	public static ProcessPreconditionsResolution checkSingleSelection(@NonNull final IProcessPreconditionsContext context)
	{
		if (context.isMoreThanOneSelected())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}
}
