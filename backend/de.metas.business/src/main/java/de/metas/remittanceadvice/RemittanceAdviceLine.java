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
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;

@Builder
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

	@Nullable
	private BigDecimal invoiceAmt;

	@Nullable
	private BigDecimal invoiceAmtInREMADVCurrency;

	@Nullable
	private BigDecimal overUnderAmt;

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

	public void syncWithInvoice(@NonNull final RemittanceAdviceLineInvoiceDetails remittanceAdviceLineInvoiceDetails)
	{
		invoiceId = remittanceAdviceLineInvoiceDetails.getInvoiceId();
		billBPartnerId = remittanceAdviceLineInvoiceDetails.getBillBPartnerId();
		invoiceAmt = remittanceAdviceLineInvoiceDetails.getInvoiceAmt();
		invoiceCurrencyId = remittanceAdviceLineInvoiceDetails.getInvoiceCurrencyId();
		invoiceAmtInREMADVCurrency = remittanceAdviceLineInvoiceDetails.getInvoiceAmtInREMADVCurrency();
		overUnderAmt = remittanceAdviceLineInvoiceDetails.getOverUnderAmt();
		isInvoiceResolved = true;
		isAmountValid = BigDecimal.ZERO.equals(remittanceAdviceLineInvoiceDetails.getOverUnderAmt());

		isInvoiceDocTypeValid = remittanceAdviceLineInvoiceDetails.getInvoiceDocType().equals(externalInvoiceDocBaseType);
		isBPartnerValid = bpartnerIdentifier != null && bpartnerIdentifier.equals(billBPartnerId);

		if (dateInvoiced == null)
		{
			dateInvoiced = remittanceAdviceLineInvoiceDetails.getInvoiceDate();
			isInvoiceDateValid = true;
		}
		else
		{
			isInvoiceDateValid = dateInvoiced.equals(remittanceAdviceLineInvoiceDetails.getInvoiceDate());
		}
	}

	public void removeInvoice()
	{
		invoiceId = null;
		billBPartnerId = null;
		invoiceAmt = null;
		invoiceCurrencyId = null;
		invoiceAmtInREMADVCurrency = null;
		overUnderAmt = null;
		dateInvoiced = null;

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
		isServiceFeeResolved = true;
	}
}
