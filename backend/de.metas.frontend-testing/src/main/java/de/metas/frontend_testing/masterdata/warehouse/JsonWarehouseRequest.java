package de.metas.frontend_testing.masterdata.warehouse;

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

	//
	//
	//
	//
	//

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
