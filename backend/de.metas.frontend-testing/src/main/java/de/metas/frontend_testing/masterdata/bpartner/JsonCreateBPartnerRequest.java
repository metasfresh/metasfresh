package de.metas.frontend_testing.masterdata.bpartner;

import de.metas.frontend_testing.masterdata.Identifier;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.Map;

/**
 * Strict subset of the same-named request of the later (new_dawn-based) releases: same field names and shapes, fewer fields.
 */
@Value
@Builder
@Jacksonized
public class JsonCreateBPartnerRequest
{
	/** {@code C_BPartner.Value}. If null, a unique value is generated. */
	@Nullable String bpartnerCode;

	/** {@code C_BPartner.Name}. If null, the value is used. */
	@Nullable String name;

	/**
	 * The {@code C_BP_Group} this partner belongs to, as an identifier from the {@code context}.
	 * If null, the client's default group is used.
	 */
	@Nullable Identifier bpGroup;

	/**
	 * Locations to create, by identifier. If null or empty, a single location in the default country is created
	 * and registered in the context under the partner identifier plus {@code MasterdataContext#SINGLE_BP_LOCATION_SUFFIX}.
	 */
	@Nullable Map<String, Location> locations;

	/** Default: false */
	@Builder.Default boolean isVendor = false;

	/** Default: true */
	@Builder.Default boolean isCustomer = true;

	@Value
	@Builder
	@Jacksonized
	public static class Location
	{
		@Nullable String city;
		@Nullable String postal;
		@Nullable String address1;
		/** ISO 2-letter country code, e.g. {@code "DE"}. If null, the default country is used. */
		@Nullable String countryCode;
	}
}
