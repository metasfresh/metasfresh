package de.metas.frontend_testing.masterdata.warehouse;

import de.metas.frontend_testing.masterdata.Identifier;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.Map;

@Value
@Builder
@Jacksonized
public class JsonWarehouseRequest
{
	boolean inTransit;
	@Nullable String locatorCode;
	@Nullable Map<String, Locator> locators;

	/**
	 * Optional identifier of an {@code M_Warehouse_PickingGroup} this warehouse belongs to.
	 * All warehouses in the same masterdata request that name the same {@code pickingGroup} value are
	 * assigned to a single, shared {@code M_Warehouse_PickingGroup} (created once, looked up afterwards).
	 * <p>
	 * Warehouses sharing a picking group are treated as one picking scope: a picker working at a workplace in
	 * one warehouse of the group may pick demand and stock located in any warehouse of the same group.
	 */
	@Nullable String pickingGroup;

	/**
	 * Sets {@code M_Warehouse.IsAutoDistributionOrder}. The picking-replenishment service plans DD_Orders only for
	 * demand whose warehouse carries this flag, so a spec exercising that path must set it on the demand's warehouse.
	 */
	boolean autoDistributionOrder;

	/**
	 * Marks this warehouse as THE quality-return warehouse ({@code M_Warehouse.IsQualityReturnWarehouse}) —
	 * where a POS/customer return receives goods (see {@code ReturnedGoodsWarehouseType.QUALITY_ISSUE},
	 * resolved DB-wide, not per-org, by {@code IHUWarehouseDAO#retrieveFirstQualityReturnWarehouseId()}).
	 * Since that resolution is DB-wide and takes the first match, this is a find-or-create: if an active
	 * quality-return warehouse already exists (from an earlier run against the same shared DB), THAT one is
	 * reused under this request's identifier instead of creating a second one — a second one would leave the
	 * production code still resolving the original, while this request's identifier pointed at a warehouse the
	 * return never actually uses.
	 */
	boolean isQualityReturnWarehouse;

	/**
	 * Makes this warehouse the target of its own {@code DD_NetworkDistribution}, carrying a single line whose source is
	 * {@link Replenishment#getFromWarehouse()} — this is how the picking-replenishment service resolves the warehouse to
	 * pick from. Applied after the {@code shippers} and {@code warehouses} sections, both of which it references.
	 */
	@Nullable Replenishment replenishment;

	//
	//
	//
	//
	//

	@Value
	@Builder
	@Jacksonized
	public static class Replenishment
	{
		@NonNull Identifier fromWarehouse;
		/** Mandatory on {@code DD_NetworkDistributionLine}, irrelevant to the replenishment itself. */
		@NonNull Identifier shipper;
	}

	@Value
	@Builder
	@Jacksonized
	public static class Locator
	{
		@Nullable String x;
		@Nullable String y;
		@Nullable String z;
		@Nullable String x1;
		@Nullable Integer priorityNo;
		@Nullable Boolean isGroundLocator;
	}
}
