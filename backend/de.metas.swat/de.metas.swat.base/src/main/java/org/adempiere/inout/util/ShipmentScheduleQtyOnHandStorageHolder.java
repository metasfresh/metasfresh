package org.adempiere.inout.util;

import com.google.common.collect.ImmutableList;
import de.metas.inoutcandidate.api.OlAndSched;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.material.cockpit.stock.StockDataQuery;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Loads stock details which are relevant to given {@link I_M_ShipmentSchedule}s.
 * Allows to change (in memory!) the qtyOnHand.
 */
@AllArgsConstructor(staticName="of")
public class ShipmentScheduleQtyOnHandStorageHolder
{
	private final ImmutableList<IShipmentScheduleQtyOnHandStorage> storages;

	@NonNull
	public Optional<StockDataQuery> toQuery(@NonNull final I_M_ShipmentSchedule sched)
	{
		return storages.stream()
				.map(storage -> storage.toQuery(sched))
				.filter(Objects::nonNull)
				.findFirst();
	}

	public ShipmentScheduleAvailableStock getStockDetailsMatching(@NonNull final OlAndSched olAndSched)
	{
		return ShipmentScheduleAvailableStock.of(storages.stream()
				.map(storage -> storage.getStockDetailsMatching(olAndSched.getSched()))
				.flatMap(List::stream)
				.collect(ImmutableList.toImmutableList()));
	}

}
