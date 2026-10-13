package de.metas.frontend_testing.masterdata.bpartner;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
public class JsonCreateBPartnerRequest
{
	/** {@code C_BPartner.Value}. If null, the identifier (map key of the {@code bpartners} section) is used. */
	@Nullable String code;

	/** {@code C_BPartner.Name}. If null, the code is used. */
	@Nullable String name;

	/** Name of the {@code C_BP_Group} this partner belongs to. If null, the client's default group is used. */
	@Nullable String group;

	/** The partner's (single) location. If null, a location in the default country is created. */
	@Nullable Location location;

	@Builder.Default boolean isCustomer = true;
	@Builder.Default boolean isVendor = false;

	@Value
	@Builder
	@Jacksonized
	public static class Location
	{
		@Nullable String address1;
		@Nullable String postal;
		@Nullable String city;
		/** ISO 2-letter country code, e.g. {@code "DE"}. If null, the default country is used. */
		@Nullable String countryCode;
	}
}
