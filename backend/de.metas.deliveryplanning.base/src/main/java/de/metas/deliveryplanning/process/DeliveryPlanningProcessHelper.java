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
 * selected", "is too much selected", "is more than one row selected". Each is a plain reading of the
 * {@link IProcessPreconditionsContext} against a framework rejection, identical in every process that has it, so it
 * is kept once here and composed via
 * {@link ProcessPreconditionsResolution#firstRejectOrElseAccept(java.util.function.Supplier[])}.
 * <p>
 * What does NOT belong here:
 * <ul>
 *     <li>anything that needs {@link de.metas.deliveryplanning.DeliveryPlanningService} - loading the selection,
 *     querying release numbers, asking whether a partner is blocked;</li>
 *     <li>any business rule, even a one-liner, and even one two processes happen to share today.</li>
 * </ul>
 * Those stay as private methods on the owning process, so the rule sits next to the message it rejects with and a
 * reader of that process can see every reason it can be unavailable without leaving the file.
 */
public final class DeliveryPlanningProcessHelper
{
	private DeliveryPlanningProcessHelper()
	{
	}

	/**
	 * Rejects when the planner has selected nothing at all.
	 */
	public static ProcessPreconditionsResolution checkAnySelection(@NonNull final IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	/**
	 * Rejects when the selection is bigger than the given cap.
	 */
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

	/**
	 * Rejects when more than one row is selected, i.e. for the actions that operate on exactly one delivery planning.
	 */
	public static ProcessPreconditionsResolution checkSingleSelection(@NonNull final IProcessPreconditionsContext context)
	{
		if (context.isMoreThanOneSelected())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}
}
