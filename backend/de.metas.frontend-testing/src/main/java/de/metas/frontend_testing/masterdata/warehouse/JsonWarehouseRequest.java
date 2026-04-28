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
	}
}
