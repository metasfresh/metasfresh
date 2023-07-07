package org.adempiere.inout.util;

import com.google.common.collect.ImmutableList;
import de.metas.inoutcandidate.api.OlAndSched;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.material.cockpit.stock.StockDataQuery;
import lombok.Builder;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

/**
 * Loads stock details which are relevant to given {@link I_M_ShipmentSchedule}s.
 * Allows to change (in memory!) the qtyOnHand.
 */
@Builder
public class ShipmentScheduleQtyOnHandStorageHolder
{
	private final ImmutableList<IShipmentScheduleQtyOnHandStorage> storages;

	public ShipmentScheduleQtyOnHandStorageHolder(final ImmutableList<IShipmentScheduleQtyOnHandStorage> storages)
	{
		this.storages = storages;
	}

	@Nullable
	public StockDataQuery toQuery(@NonNull final I_M_ShipmentSchedule sched)
	{
		return storages.stream()
				.map(storage -> storage.toQuery(sched))
				.filter(Objects::nonNull)
				.findFirst()
				.orElse(null);
	}

	public ShipmentScheduleAvailableStock getStockDetailsMatching(@NonNull final OlAndSched olAndSched)
	{
		return ShipmentScheduleAvailableStock.of(storages.stream()
				.map(storage -> storage.getStockDetailsMatching(olAndSched))
				.flatMap(List::stream)
				.collect(ImmutableList.toImmutableList()));
	}

}
