/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2021 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.remittanceadvice;

import de.metas.bpartner.BPartnerId;
import de.metas.currency.Amount;
import de.metas.invoice.InvoiceId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.tax.api.TaxId;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.stream.Stream;

@EqualsAndHashCode
@Getter
public class RemittanceAdviceLine
{
	@NonNull
	private final OrgId orgId;

	@NonNull
	private final RemittanceAdviceLineId remittanceAdviceLineId;

	@NonNull
	private final RemittanceAdviceId remittanceAdviceId;

	@NonNull
	private final Amount remittedAmount;

	@Nullable
	private final Amount invoiceGrossAmount;

	@Nullable
	private final Amount paymentDiscountAmount;

	@Nullable
	private final Amount serviceFeeAmount;

	@Nullable
	private final String externalInvoiceDocBaseType;

	@Nullable
	private final String invoiceIdentifier;

	@Nullable
	private final BPartnerId bpartnerIdentifier;

	@Nullable
	private final BigDecimal serviceFeeVatRate;

	private final boolean isLineAcknowledged;

	@Nullable
	private BigDecimal invoiceAmt;

	@Nullable
	private Amount invoiceAmtInREMADVCurrency;

	@Nullable
	private Amount overUnderAmtInREMADVCurrency;

	@Nullable
	private CurrencyId invoiceCurrencyId;

	@Nullable
	private BPartnerId billBPartnerId;

	@Nullable
	private Instant dateInvoiced;

	@Nullable
	private InvoiceId serviceFeeInvoiceId;

	@Nullable
	private BPartnerId serviceFeeBPartnerId;

	@Nullable
	private ProductId serviceFeeProductId;

	@Nullable
	private TaxId taxId;

	@Nullable
	private InvoiceId invoiceId;

	private boolean isBPartnerValid;

	private boolean isInvoiceResolved;

	private boolean isAmountValid;

	private boolean isInvoiceDocTypeValid;

	private boolean isInvoiceDateValid;

	private boolean isServiceFeeResolved;

	private boolean isServiceFeeVatRateValid;

	private boolean processed;

	@Builder
	public RemittanceAdviceLine(@NonNull final OrgId orgId, @NonNull final RemittanceAdviceLineId remittanceAdviceLineId, @NonNull final RemittanceAdviceId remittanceAdviceId, @NonNull final Amount remittedAmount, @Nullable final Amount invoiceGrossAmount, @Nullable final Amount paymentDiscountAmount, @Nullable final Amount serviceFeeAmount, @Nullable final String externalInvoiceDocBaseType,
			@Nullable final String invoiceIdentifier,
			@Nullable final BPartnerId bpartnerIdentifier, @Nullable final BigDecimal serviceFeeVatRate, final boolean isLineAcknowledged, @Nullable final BigDecimal invoiceAmt, @Nullable final Amount invoiceAmtInREMADVCurrency, @Nullable final Amount overUnderAmt, @Nullable final CurrencyId invoiceCurrencyId, @Nullable final BPartnerId billBPartnerId, @Nullable final Instant dateInvoiced,
			@Nullable final InvoiceId serviceFeeInvoiceId,
			@Nullable final BPartnerId serviceFeeBPartnerId, @Nullable final ProductId serviceFeeProductId, @Nullable final TaxId taxId, @Nullable final InvoiceId invoiceId, final boolean isBPartnerValid, final boolean isInvoiceResolved, final boolean isAmountValid, final boolean isInvoiceDocTypeValid, final boolean isInvoiceDateValid, final boolean isServiceFeeResolved, final boolean processed)
	{

		Amount.assertSameCurrency(Stream.of(remittedAmount, overUnderAmt, paymentDiscountAmount)
										  .filter(Objects::nonNull).toArray(Amount[]::new));

		if (invoiceAmt != null && invoiceAmt.signum() != 0 && invoiceCurrencyId == null)
		{
			throw new AdempiereException("Missing invoiceCurrencyId for invoiceAmount!")
					.appendParametersToMessage()
					.setParameter("RemittanceAdviceLineID", remittanceAdviceLineId);
		}

		this.orgId = orgId;
		this.remittanceAdviceLineId = remittanceAdviceLineId;
		this.remittanceAdviceId = remittanceAdviceId;
		this.remittedAmount = remittedAmount;
		this.invoiceGrossAmount = invoiceGrossAmount;
		this.paymentDiscountAmount = paymentDiscountAmount;
		this.serviceFeeAmount = serviceFeeAmount;
		this.externalInvoiceDocBaseType = externalInvoiceDocBaseType;
		this.invoiceIdentifier = invoiceIdentifier;
		this.bpartnerIdentifier = bpartnerIdentifier;
		this.serviceFeeVatRate = serviceFeeVatRate;
		this.isLineAcknowledged = isLineAcknowledged;
		this.invoiceAmt = invoiceAmt;
		this.invoiceAmtInREMADVCurrency = invoiceAmtInREMADVCurrency;
		this.overUnderAmtInREMADVCurrency = overUnderAmt;
		this.invoiceCurrencyId = invoiceCurrencyId;
		this.billBPartnerId = billBPartnerId;
		this.dateInvoiced = dateInvoiced;
		this.serviceFeeInvoiceId = serviceFeeInvoiceId;
		this.serviceFeeBPartnerId = serviceFeeBPartnerId;
		this.serviceFeeProductId = serviceFeeProductId;
		this.taxId = taxId;
		this.invoiceId = invoiceId;
		this.isBPartnerValid = isBPartnerValid;
		this.isInvoiceResolved = isInvoiceResolved;
		this.isAmountValid = isAmountValid;
		this.isInvoiceDocTypeValid = isInvoiceDocTypeValid;
		this.isInvoiceDateValid = isInvoiceDateValid;
		this.isServiceFeeResolved = isServiceFeeResolved;
		this.processed = processed;
	}

	public void syncWithInvoice(@NonNull final RemittanceAdviceLineInvoiceDetails remittanceAdviceLineInvoiceDetails)
	{
		Amount.assertSameCurrency(remittedAmount,
								  remittanceAdviceLineInvoiceDetails.getInvoiceAmtInREMADVCurrency(),
								  remittanceAdviceLineInvoiceDetails.getOverUnderAmtInREMADVCurrency());

		invoiceId = remittanceAdviceLineInvoiceDetails.getInvoiceId();
		billBPartnerId = remittanceAdviceLineInvoiceDetails.getBillBPartnerId();
		invoiceAmt = remittanceAdviceLineInvoiceDetails.getInvoiceAmt();
		invoiceCurrencyId = remittanceAdviceLineInvoiceDetails.getInvoiceCurrencyId();
		invoiceAmtInREMADVCurrency = remittanceAdviceLineInvoiceDetails.getInvoiceAmtInREMADVCurrency();
		overUnderAmtInREMADVCurrency = remittanceAdviceLineInvoiceDetails.getOverUnderAmtInREMADVCurrency();
		isInvoiceResolved = true;
		isAmountValid = remittanceAdviceLineInvoiceDetails.getOverUnderAmtInREMADVCurrency().signum() == 0;

		isInvoiceDocTypeValid = remittanceAdviceLineInvoiceDetails.getInvoiceDocType().equals(externalInvoiceDocBaseType);

		isInvoiceDateValid = remittanceAdviceLineInvoiceDetails.getInvoiceDate().equals(dateInvoiced);
	}

	public void validateBPartner(){
		isBPartnerValid = billBPartnerId != null && billBPartnerId.equals(bpartnerIdentifier);
	}

	public void validateInvoiceDocBaseType(@NonNull final String invoiceDocType){
		isInvoiceDocTypeValid = invoiceDocType.equalsIgnoreCase(externalInvoiceDocBaseType);
	}

	public void removeInvoice()
	{
		invoiceId = null;
		billBPartnerId = null;
		invoiceAmt = null;
		invoiceCurrencyId = null;
		invoiceAmtInREMADVCurrency = null;
		overUnderAmtInREMADVCurrency = null;

		isInvoiceResolved = false;
		isAmountValid = false;
		isInvoiceDocTypeValid = false;
		isBPartnerValid = false;
		isInvoiceDateValid = false;
	}

	public void setServiceFeeDetails(@NonNull final RemittanceAdviceLineServiceFee remittanceAdviceLineServiceFee)
	{

		serviceFeeInvoiceId = remittanceAdviceLineServiceFee.getServiceFeeInvoiceId();
		serviceFeeProductId = remittanceAdviceLineServiceFee.getServiceProductId();
		serviceFeeBPartnerId = remittanceAdviceLineServiceFee.getServiceBPartnerId();
		taxId = remittanceAdviceLineServiceFee.getServiceFeeTaxId();

		isServiceFeeVatRateValid = serviceFeeVatRate != null
				&& remittanceAdviceLineServiceFee.getServiceVatRate() != null
				&& remittanceAdviceLineServiceFee.getServiceVatRate().compareTo(serviceFeeVatRate) == 0;

		isServiceFeeResolved = true;
	}

	public boolean isReadyForCompletion()
	{
		return isInvoiceResolved && (isLineAcknowledged || isAmountValid);
	}

	public void setProcessed(final boolean processed)
	{
		this.processed = processed;
	}
}
