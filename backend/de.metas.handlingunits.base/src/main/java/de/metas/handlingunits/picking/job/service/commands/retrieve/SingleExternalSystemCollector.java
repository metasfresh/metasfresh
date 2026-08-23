/*
 * #%L
 * de.metas.handlingunits.base
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

package de.metas.handlingunits.picking.job.service.commands.retrieve;

import de.metas.externalsystem.ExternalSystemId;
import de.metas.handlingunits.picking.job.model.ScheduledPackageable;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Objects;

/**
 * Reduces the external systems of the schedules aggregated into one picking-job candidate to the
 * single distinct one among them, or null.
 * <p>
 * Semantics deliberately mirror {@code ScheduledPackageableList.getSingleValue}, which the STARTED
 * half of the launcher list goes through ({@code PickingJobCreateCommand} stores
 * {@code getSingleExternalSystemId()} on the job): nulls are ignored, two different systems collapse
 * to null. Diverging here would make a work item show one thing before it is started and another
 * after — the display/filter disagreement this feature exists to avoid.
 * <p>
 * Deliberately NOT part of any aggregation KEY: putting it there would split a delivery-location or
 * product aggregation whenever two of its orders came from different systems, which is a change to
 * how the launcher groups work items, which this feature does not set out to change.
 */
class SingleExternalSystemCollector
{
	private boolean diverged = false;
	@Nullable private ExternalSystemId externalSystemId = null;

	public void collect(@NonNull final ScheduledPackageable item)
	{
		final ExternalSystemId itemExternalSystemId = item.getExternalSystemId();
		if (diverged || itemExternalSystemId == null)
		{
			return;
		}

		if (externalSystemId == null)
		{
			externalSystemId = itemExternalSystemId;
		}
		else if (!Objects.equals(externalSystemId, itemExternalSystemId))
		{
			diverged = true;
			externalSystemId = null;
		}
	}

	@Nullable
	public ExternalSystemId getSingleExternalSystemIdOrNull() {return externalSystemId;}
}
