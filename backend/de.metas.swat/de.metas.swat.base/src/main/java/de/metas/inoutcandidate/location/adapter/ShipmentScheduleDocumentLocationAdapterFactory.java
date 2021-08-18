/*
 * #%L
 * de.metas.swat.base
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

package de.metas.inoutcandidate.location.adapter;

import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import lombok.NonNull;

public class ShipmentScheduleDocumentLocationAdapterFactory
{
	public static MainLocationAdapter mainLocationAdapter(@NonNull final I_M_ShipmentSchedule sched)
	{
		return new MainLocationAdapter(sched);
	}

	public static OverrideLocationAdapter overrideLocationAdapter(@NonNull final I_M_ShipmentSchedule sched)
	{
		return new OverrideLocationAdapter(sched);
	}

	public static BillLocationAdapter billLocationAdapter(@NonNull final I_M_ShipmentSchedule sched)
	{
		return new BillLocationAdapter(sched);
	}
}
