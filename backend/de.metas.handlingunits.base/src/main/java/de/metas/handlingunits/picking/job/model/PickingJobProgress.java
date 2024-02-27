/*
 * #%L
 * de.metas.picking.rest-api
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.handlingunits.picking.job.model;

import java.util.Set;

public enum PickingJobProgress
{
	NOT_STARTED,
	IN_PROGRESS,
	DONE;

	public boolean isNotStarted() {return NOT_STARTED.equals(this);}

	public boolean isDone() {return DONE.equals(this);}

	public static PickingJobProgress reduce(Set<PickingJobProgress> progresses)
	{
		if (progresses.isEmpty())
		{
			return NOT_STARTED;
		}
		else if (progresses.size() == 1)
		{
			return progresses.iterator().next();
		}
		else
		{
			return IN_PROGRESS;
		}
	}
}
