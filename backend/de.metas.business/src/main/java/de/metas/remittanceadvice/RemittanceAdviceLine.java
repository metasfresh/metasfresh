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
import de.metas.product.ProductId;
import de.metas.tax.api.TaxId;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;

@Builder
@Data
public class RemittanceAdviceLine
{
	@NonNull
	private final RemittanceAdviceLineId remittanceAdviceLineId;

	@NonNull
	private final RemittanceAdviceId remittanceAdviceId;

	@NonNull
	private final Amount remittedAmount;

	@Nullable
	private final Amount invoiceGrossAmount;

	@Nullable
	private BigDecimal invoiceAmt;

	@Nullable
	private BigDecimal invoiceAmtInREMADVCurrency;

	@Nullable
	private BigDecimal overUnderAmt;

	@Nullable
	private CurrencyId invoiceCurrencyId;

	@Nullable
	private Amount paymentDiscountAmount;

	@Nullable
	private Amount serviceFeeAmount;

	@Nullable
	private final String invoiceIdentifier;

	@Nullable
	private final BPartnerId bpartnerIdentifier;

	@Nullable
	private BPartnerId billBPartnerId;

	@Nullable
	private String externalInvoiceDocBaseType;

	@Nullable
	private Instant dateInvoiced;

	@Nullable
	private InvoiceId serviceFeeInvoiceId;

	@Nullable
	private BigDecimal serviceFeeVatRate;

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

	public void processIsBPartnerValid(final RemittanceAdviceLineInvoiceDetails remittanceAdviceLineInvoiceDetails, final BPartnerId remittanceAdviceBPartnerId){

		isBPartnerValid = remittanceAdviceBPartnerId.equals(remittanceAdviceLineInvoiceDetails.getBillBPartnerId());
	}

	public void syncWithInvoice(final RemittanceAdviceLineInvoiceDetails remittanceAdviceLineInvoiceDetails){

		billBPartnerId = remittanceAdviceLineInvoiceDetails.getBillBPartnerId();
		invoiceAmt = remittanceAdviceLineInvoiceDetails.getInvoiceAmt();
		invoiceCurrencyId = remittanceAdviceLineInvoiceDetails.getInvoiceCurrencyId();
		invoiceAmtInREMADVCurrency = remittanceAdviceLineInvoiceDetails.getInvoiceAmtInREMADVCurrency();
		overUnderAmt = remittanceAdviceLineInvoiceDetails.getOverUnderAmt();
		isInvoiceResolved = true;
		isAmountValid = remittanceAdviceLineInvoiceDetails.getOverUnderAmt() != null &&
				remittanceAdviceLineInvoiceDetails.getOverUnderAmt().compareTo(new BigDecimal(0)) == 0;

		isInvoiceDocTypeValid = externalInvoiceDocBaseType != null && externalInvoiceDocBaseType.equals(remittanceAdviceLineInvoiceDetails.getInvoiceDocType());

		if(dateInvoiced == null){
			dateInvoiced = TimeUtil.asInstant(remittanceAdviceLineInvoiceDetails.getInvoiceDate());
			isInvoiceDateValid = true;
		} else {
			isInvoiceDateValid = remittanceAdviceLineInvoiceDetails.getInvoiceDate() != null &&
					dateInvoiced.compareTo(TimeUtil.asInstant(remittanceAdviceLineInvoiceDetails.getInvoiceDate())) == 0;
		}
	}

	public void setServiceFeeDetails(final RemittanceAdviceLineServiceFee remittanceAdviceLineServiceFee){

		serviceFeeInvoiceId = remittanceAdviceLineServiceFee.getServiceFeeInvoiceId();
		serviceFeeProductId = remittanceAdviceLineServiceFee.getServiceProductId();
		serviceFeeBPartnerId = remittanceAdviceLineServiceFee.getServiceBPartnerId();
		taxId = remittanceAdviceLineServiceFee.getServiceFeeTaxId();
		isServiceFeeResolved = true;
	}
}
