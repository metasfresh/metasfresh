package de.metas.handlingunits.shipmentschedule.api;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.inout.ShipmentScheduleId;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBuilder;

import java.util.List;

public interface IHUShipmentScheduleDAO extends ISingletonService
{
	List<I_M_ShipmentSchedule_QtyPicked> retrieveSchedsQtyPickedForHU(I_M_HU hu);

	List<I_M_ShipmentSchedule_QtyPicked> retrieveByTopLevelHUAndShipmentScheduleId(
			@NonNull I_M_HU topLevelHU,
			@NonNull ShipmentScheduleId shipmentScheduleId);

	List<I_M_ShipmentSchedule_QtyPicked> retrieveSchedsQtyPickedForTU(int shipmentScheduleId, int tuHUId, String trxName);

	List<I_M_ShipmentSchedule_QtyPicked> retrieveSchedsQtyPickedForVHU(I_M_HU vhu);

	IQueryBuilder<I_M_ShipmentSchedule_QtyPicked> retrieveSchedsQtyPickedForVHUQuery(I_M_HU vhu);

	List<ShipmentScheduleWithHU> retrieveShipmentSchedulesWithHUsFromHUs(List<I_M_HU> hus);
}
