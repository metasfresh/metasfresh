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
 * single value they agree on, or null.
 * <p>
 * Deliberately NOT part of any aggregation KEY: putting it there would split a delivery-location or
 * product aggregation whenever two of its orders came from different systems, which is a change to
 * how the launcher groups work items, which this feature does not set out to change. Disagreement therefore
 * shows as "no external system" rather than as an arbitrary pick between them — the same choice
 * {@code ScheduledPackageableList.getSingleValue} makes for every other header-level value.
 */
class SingleExternalSystemCollector
{
	private boolean anyCollected = false;
	@Nullable private ExternalSystemId externalSystemId = null;

	public void collect(@NonNull final ScheduledPackageable item)
	{
		final ExternalSystemId itemExternalSystemId = item.getExternalSystemId();
		if (!anyCollected)
		{
			anyCollected = true;
			externalSystemId = itemExternalSystemId;
		}
		else if (!Objects.equals(externalSystemId, itemExternalSystemId))
		{
			externalSystemId = null;
		}
	}

	@Nullable
	public ExternalSystemId getSingleExternalSystemIdOrNull() {return externalSystemId;}
}
