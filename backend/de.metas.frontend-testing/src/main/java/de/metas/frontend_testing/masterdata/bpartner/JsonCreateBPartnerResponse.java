package de.metas.frontend_testing.masterdata.bpartner;

import de.metas.bpartner.BPartnerId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.Map;

@Value
@Builder
@Jacksonized
public class JsonCreateBPartnerResponse
{
	@NonNull BPartnerId id;
	@NonNull String bpartnerCode;

	/** Set if the request had no {@code locations} (single default location). */
	@Nullable Integer bpartnerLocationId;

	/** The created locations by request identifier; set if the request had {@code locations}. */
	@Nullable Map<String, Location> locations;

	@Value
	@Builder
	@Jacksonized
	public static class Location
	{
		int id;
	}
}
