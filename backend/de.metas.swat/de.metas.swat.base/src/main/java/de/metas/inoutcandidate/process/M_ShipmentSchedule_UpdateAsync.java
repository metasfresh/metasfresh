package de.metas.inoutcandidate.process;

import de.metas.inoutcandidate.async.UpdateInvalidShipmentSchedulesWorkpackageProcessor;
import de.metas.process.JavaProcess;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2020 metas GmbH
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
/**
 * Enqueue any M_ShipmentSchedules which are flagged for recomputation to {@link UpdateInvalidShipmentSchedulesWorkpackageProcessor}.
 * This is usually done automatically, but in some debug situations, you might have inserted {@code M_ShipmentSchedule_Recompute} records manually.
 */
public class M_ShipmentSchedule_UpdateAsync extends JavaProcess
{
	@Override
	protected String doIt()
	{
		UpdateInvalidShipmentSchedulesWorkpackageProcessor.schedule();
		return MSG_OK;
	}
}
