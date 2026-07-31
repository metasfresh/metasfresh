package de.metas.frontend_testing.masterdata.warehouse;

import de.metas.material.planning.ddorder.DistributionNetworkId;
import lombok.NonNull;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_Warehouse;

import javax.annotation.Nullable;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/**
 * Persistence for the warehouse-replenishment post-pass (see {@link ConfigureWarehouseReplenishmentCommand}):
 * loads the warehouse record and writes the {@code DD_NetworkDistribution_ID} and {@code IsAutoDistributionOrder}
 * columns.
 * <p>
 * The {@code InterfaceWrapperHelper.load}/{@code saveRecord} primitives live here (a {@code *Repository}) rather
 * than in the command, per the architecture rule that confines those primitives to {@code *Repository}/{@code *DAO}
 * classes (docs/coding-rules/service-injection.md §4 + java-general.md §18).
 */
public class WarehouseReplenishmentRepository
{
	public void updateReplenishment(
			@NonNull final WarehouseId warehouseId,
			@Nullable final DistributionNetworkId networkId,
			final boolean autoDistributionOrder)
	{
		final I_M_Warehouse warehouseRecord = load(warehouseId, I_M_Warehouse.class);

		if (networkId != null)
		{
			warehouseRecord.setDD_NetworkDistribution_ID(networkId.getRepoId());
		}
		warehouseRecord.setIsAutoDistributionOrder(autoDistributionOrder);

		// Both columns in one save: M_Warehouse_DDOrderPickingInterceptor rejects IsAutoDistributionOrder without a network.
		saveRecord(warehouseRecord);
	}
}
