package org.adempiere.inout.util;

import com.google.common.collect.ImmutableList;
import de.metas.inoutcandidate.api.OlAndSched;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.logging.LogManager;
import lombok.NonNull;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Service
public class ShipmentScheduleQtyOnHandStorageFactory
{
	private static final Logger logger = LogManager.getLogger(ShipmentScheduleQtyOnHandStorageFactory.class);
	private final List<IShipmentScheduleQtyOnHandProvider> providers;

	public ShipmentScheduleQtyOnHandStorageFactory(@NonNull final List<IShipmentScheduleQtyOnHandProvider> providers)
	{
		logger.info("Providers: {}", providers);
		this.providers = providers;
	}

	public final ShipmentScheduleQtyOnHandStorageHolder getHolderForOlAndSched(@NonNull final List<OlAndSched> lines)
	{
		final List<I_M_ShipmentSchedule> shipmentSchedules = lines
				.stream()
				.map(OlAndSched::getSched)
				.collect(ImmutableList.toImmutableList());

		final ImmutableList<IShipmentScheduleQtyOnHandStorage> shipmentScheduleQtyOnHandStorages = providers.stream()
				.map(provider -> provider.getStorageFor(shipmentSchedules))
				.collect(ImmutableList.toImmutableList());
		return ShipmentScheduleQtyOnHandStorageHolder.of(shipmentScheduleQtyOnHandStorages);
	}

}
