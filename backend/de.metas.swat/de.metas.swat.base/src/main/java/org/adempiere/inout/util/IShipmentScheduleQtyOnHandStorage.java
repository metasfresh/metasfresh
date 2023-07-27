/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package org.adempiere.inout.util;

import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.material.cockpit.stock.StockDataQuery;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Implementations of this shall hold details of quantity that's available and ready to be shipped.
 */
public interface IShipmentScheduleQtyOnHandStorage
{
	/**
	 * @implSpec the calling API will not check if the returned storages overlap.
	 */
	List<ShipmentScheduleAvailableStockDetail> getStockDetailsMatching(@NonNull final I_M_ShipmentSchedule shipmentSchedule);

	@Nullable
	default StockDataQuery toQuery(@NonNull final I_M_ShipmentSchedule sched)
	{
		return null;
	}
}
