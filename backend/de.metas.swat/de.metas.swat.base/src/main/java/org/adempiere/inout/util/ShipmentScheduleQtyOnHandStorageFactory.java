package org.adempiere.inout.util;

import com.google.common.collect.ImmutableList;
import de.metas.inoutcandidate.api.OlAndSched;
import lombok.NonNull;
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
	private final List<IShipmentScheduleQtyOnHandProvider> providers;

	public ShipmentScheduleQtyOnHandStorageFactory(@NonNull final List<IShipmentScheduleQtyOnHandProvider> providers)
	{
		this.providers = providers;
	}

	public final ShipmentScheduleQtyOnHandStorageHolder ofOlAndScheds(@NonNull final List<OlAndSched> lines)
	{
		final ImmutableList<IShipmentScheduleQtyOnHandStorage> shipmentScheduleQtyOnHandStorages = providers.stream()
				.map(provider -> provider.ofOlAndScheds(lines))
				.collect(ImmutableList.toImmutableList());
		return
				ShipmentScheduleQtyOnHandStorageHolder.builder()
						.storages(shipmentScheduleQtyOnHandStorages)
						.build();
	}

}
