package de.metas.frontend_testing.masterdata.bpartner;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.Map;

@Value
@Builder
@Jacksonized
public class JsonCreateBPartnerRequest
{
	@Nullable String gln;
	@Nullable Map<String, Location> locations;

	@Value
	@Builder
	@Jacksonized
	public static class Location
	{
		@Nullable String gln;
	}
}
