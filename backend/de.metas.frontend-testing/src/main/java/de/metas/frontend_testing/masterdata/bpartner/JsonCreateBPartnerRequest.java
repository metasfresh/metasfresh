package de.metas.frontend_testing.masterdata.bpartner;

import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.handlingunits.grai.GRAIRequired;
import de.metas.order.InvoiceRule;
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

	/**
	 * The {@code C_BP_Group} (map key of the {@code bpGroups} section) this partner belongs to.
	 * If null, the "Standard" group is used.
	 */
	@Nullable Identifier bpGroup;

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
	 * Sets {@code C_BPartner.InvoiceRule} from an {@link InvoiceRule} reference-list code (e.g. {@code "I"} for
	 * {@link InvoiceRule#Immediate}). {@code null} (the default) leaves the column unset, so the effective rule
	 * ({@code BPartnerEffectiveBL}) falls through to the BP-group's own InvoiceRule and, since the "Standard" BP
	 * group carries none either, to the system default — After Delivery. A flow that invoices this bpartner via the
	 * ordinary invoice-candidate pipeline WITHOUT ever creating a shipment (e.g. settling an already-issued invoice
	 * at the till) needs {@code "I"} here, since After Delivery leaves {@code C_Invoice_Candidate.QtyToInvoice=0}
	 * until something is delivered. Carried as the raw code rather than the {@link InvoiceRule} enum so the domain
	 * enum stays free of Jackson annotations (whose global serialization change would break other consumers).
	 */
	@Nullable String invoiceRule;

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
