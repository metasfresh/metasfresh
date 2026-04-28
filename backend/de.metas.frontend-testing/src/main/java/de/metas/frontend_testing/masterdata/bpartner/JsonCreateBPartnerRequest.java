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
	// Allow custom bpartner code and name (if null, use timestamp-based generation)
	@Nullable String bpartnerCode;
	@Nullable String name;

	@Nullable String gln;
	@Nullable Map<String, Location> locations;

	/**
	 * Contacts (AD_User records) to create for this business partner.
	 * Each contact is linked to the business partner via C_BPartner_ID.
	 */
	@Nullable Map<String, Contact> contacts;

	/**
	 * Whether this business partner is a vendor (purchase side).
	 * Default: false
	 */
	@Builder.Default boolean isVendor = false;

	/**
	 * Whether this business partner is a customer (sales side).
	 * Default: true
	 */
	@Builder.Default boolean isCustomer = true;

	/**
	 * Whether to create a sales price list (true) or purchase price list (false).
	 * For vendors, this should typically be false.
	 * For customers, this should typically be true.
	 * Default: true (sales price list)
	 */
	@Builder.Default boolean isSoPriceList = true;

	@Value
	@Builder
	@Jacksonized
	public static class Location
	{
		@Nullable String gln;
	}

	@Value
	@Builder
	@Jacksonized
	public static class Contact
	{
		@Nullable String firstName;
		@Nullable String lastName;
		@Nullable String email;
		@Nullable String phone;
		/**
		 * Description or title for the contact.
		 */
		@Nullable String description;
	}
}
