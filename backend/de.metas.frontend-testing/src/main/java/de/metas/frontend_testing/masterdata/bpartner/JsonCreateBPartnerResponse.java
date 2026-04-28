package de.metas.frontend_testing.masterdata.bpartner;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.GLN;
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
	
	@Nullable Integer bpartnerLocationId;
	@Nullable GLN gln;

	@Nullable Map<String, Location> locations;

	@Nullable Map<String, Contact> contacts;

	@Value
	@Builder
	@Jacksonized
	public static class Location
	{
		int id;
		@Nullable GLN gln;
	}

	@Value
	@Builder
	@Jacksonized
	public static class Contact
	{
		int id;
		@Nullable String firstName;
		@Nullable String lastName;
		@Nullable String email;
	}
}
