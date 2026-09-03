package de.metas.frontend_testing.masterdata.bpartner;

import de.metas.handlingunits.grai.GRAIRequired;
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
	 * Sets {@code C_BPartner.GRAIRequired}.
	 * (De)serialized by its code: {@code 'Y'}=Yes, {@code 'N'}=No, {@code 'D'}=YesWithDummyGRAIs.
	 * If null, the business partner is left unchanged.
	 */
	@Nullable GRAIRequired graiRequired;

	/**
	 * Sets {@code C_BPartner.IsEInvoiceRecipeint}.
	 * If null, the field is left unchanged (defaults to false for new records).
	 */
	@Nullable Boolean isEInvoiceRecipeint;

	/**
	 * Sets {@code C_BPartner.EInvoiceType} (e.g. {@code "Z"} for ZUGFeRD / Factur-X).
	 * If null, the field is left unchanged.
	 */
	@Nullable String eInvoiceType;

	/**
	 * Sets {@code C_BPartner.EInvoice_BuyerReference} (BuyerReference / Leitweg-ID in EN16931 CII).
	 * If null, the field is left unchanged.
	 */
	@Nullable String eInvoiceBuyerReference;

	/**
	 * Sets {@code C_BPartner.VATaxID} (VAT identification number, e.g. {@code "DE136695976"}).
	 * Required by EN16931 for both seller (resolved via org-bpartner) and buyer.
	 * If null, the field is left unchanged.
	 */
	@Nullable String vatTaxId;

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

	/**
	 * Bank accounts to create for this business partner.
	 * Used for EN16931 / ZUGFeRD CII CreditorFinancialAccount (seller IBAN).
	 */
	@Nullable Map<String, JsonBankAccountRequest> bankAccounts;

	@Value
	@Builder
	@Jacksonized
	public static class Location
	{
		@Nullable String gln;
		@Nullable String city;
		@Nullable String postal;
		@Nullable String address1;
		/** ISO 2-letter country code, e.g. {@code "DE"}. */
		@Nullable String countryCode;
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
		/**
		 * If true, sets {@code AD_User.IsDefaultContact=Y}.
		 * The CII mapper reads the seller contact via {@code retrieveDefaultContact}.
		 */
		@Nullable Boolean isDefaultContact;
	}
}
