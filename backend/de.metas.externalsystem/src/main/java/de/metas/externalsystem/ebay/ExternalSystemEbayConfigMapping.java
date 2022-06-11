package de.metas.externalsystem.ebay;

import de.metas.document.DocTypeId;
import de.metas.payment.paymentterm.PaymentTermId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class ExternalSystemEbayConfigMapping {
	
	@NonNull
	Integer seqNo;

	@Nullable
	DocTypeId docTypeOrderId;

	@Nullable
	String paymentRule;

	@NonNull
	String bpartnerIfExists;

	@NonNull
	String bpartnerIfNotExists;

	@NonNull
	String bpartnerLocationIfExists;

	@NonNull
	String bpartnerLocationIfNotExists;

	@Nullable
	Boolean isInvoiceEmailEnabled;

	@Nullable
	PaymentTermId paymentTermId;

	@Nullable
	String ebayCustomerGroup;

	@Nullable
	String ebayPaymentMethod;

	@Nullable
	String description;

	@Builder
	public ExternalSystemEbayConfigMapping(
			@NonNull final Integer seqNo,
			final int docTypeOrderId,
			@Nullable final String paymentRule,
			final int paymentTermId,
			@NonNull final String bpartnerIfExists,
			@NonNull final String bpartnerIfNotExists,
			@NonNull final String bpartnerLocationIfExists,
			@NonNull final String bpartnerLocationIfNotExists,
			@Nullable final Boolean isInvoiceEmailEnabled,
			@Nullable final String ebayCustomerGroup,
			@Nullable final String ebayPaymentMethod,
			@Nullable final String description)
	{
		this.seqNo = seqNo;
		this.docTypeOrderId = DocTypeId.ofRepoIdOrNull(docTypeOrderId);
		this.paymentRule = paymentRule;
		this.paymentTermId = PaymentTermId.ofRepoIdOrNull(paymentTermId);
		this.ebayCustomerGroup = ebayCustomerGroup;
		this.ebayPaymentMethod = ebayPaymentMethod;
		this.description = description;
		this.bpartnerIfExists = bpartnerIfExists;
		this.bpartnerIfNotExists = bpartnerIfNotExists;
		this.bpartnerLocationIfExists = bpartnerLocationIfExists;
		this.bpartnerLocationIfNotExists = bpartnerLocationIfNotExists;
		this.isInvoiceEmailEnabled = isInvoiceEmailEnabled;
	}

}
