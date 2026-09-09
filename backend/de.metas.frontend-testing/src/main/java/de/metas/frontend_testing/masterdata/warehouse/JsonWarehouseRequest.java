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
	 * Packing material emptied/issued in THIS warehouse is moved to {@link Empties#getToWarehouse()}: adds a line
	 * (this warehouse -> {@code toWarehouse}, {@link Empties#getShipper()}) to the client's single empties
	 * distribution network ({@code DD_NetworkDistribution.IsHUDestroyed}), creating that network if the client has
	 * none. Applied after {@code shippers} and all {@code warehouses}.
	 */
	@Nullable Empties empties;

	//
	//
	//
	//
	//

	@Value
	@Builder
	@Jacksonized
	public static class Empties
	{
		@NonNull Identifier toWarehouse;
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
	}
}
