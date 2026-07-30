package de.metas.inoutcandidate.spi;

/*
 * #%L
 * de.metas.swat.base
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

import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import org.adempiere.ad.dao.IQueryFilter;

/**
 * Reports whether a shipment schedule still has picking work in progress.
 * <p>
 * The bridge exists because the process that needs this filter lives in a module that has no dependency on the module
 * owning the picking-job data, so it cannot query those tables directly; the implementation is resolved via Spring at
 * runtime.
 */
public interface IShipmentSchedulePickingInfoService
{
	/**
	 * @return a filter matching every {@link I_M_ShipmentSchedule} that still has an unfinished (Drafted) picking job
	 * 		(referenced via {@code M_Picking_Job_Line} OR {@code M_Picking_Job_Step}). Meant to be folded into the
	 * 		caller's own selection query so the offending schedules come from a single query — no id round-trip.
	 */
	IQueryFilter<I_M_ShipmentSchedule> newUnfinishedPickingFilter();
}
