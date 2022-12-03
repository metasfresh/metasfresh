package de.metas.banking.payment.paymentallocation.service;

import de.metas.bpartner.BPartnerId;
import de.metas.invoice.invoiceProcessingServiceCompany.InvoiceProcessingFeeCalculation;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Immutable allocation candidate.
 *
 * @author tsa
 *
 */
@Value
public class AllocationLineCandidate
{
	public enum AllocationLineCandidateType
	{
		InvoiceToPayment, //
		SalesInvoiceToPurchaseInvoice, //
		InvoiceToCreditMemo, //
		InvoiceDiscountOrWriteOff, //
		InvoiceProcessingFee, //
		InboundPaymentToOutboundPayment, //
	}

	AllocationLineCandidateType type;
	OrgId orgId;
	BPartnerId bpartnerId;

	TableRecordReference payableDocumentRef;
	TableRecordReference paymentDocumentRef;

	LocalDate dateTrx;
	LocalDate dateAcct;

	//
	// Amounts
	CurrencyId currencyId;
	AllocationAmounts amounts;
	Money payableOverUnderAmt;
	Money paymentOverUnderAmt;
	InvoiceProcessingFeeCalculation invoiceProcessingFeeCalculation;

	@Builder(toBuilder = true)
	private AllocationLineCandidate(
			@NonNull final AllocationLineCandidateType type,
			//
			@NonNull final OrgId orgId,
			@Nullable final BPartnerId bpartnerId,
			//
			@NonNull final TableRecordReference payableDocumentRef,
			@Nullable final TableRecordReference paymentDocumentRef,
			//
			@NonNull final LocalDate dateTrx,
			@NonNull final LocalDate dateAcct,
			//
			// Amounts
			@NonNull final AllocationAmounts amounts,
			@Nullable final Money payableOverUnderAmt,
			@Nullable final Money paymentOverUnderAmt,
			@Nullable final InvoiceProcessingFeeCalculation invoiceProcessingFeeCalculation)
	{
		if (!orgId.isRegular())
		{
			throw new AdempiereException("Transactional organization expected: " + orgId);
		}
		if (Objects.equals(payableDocumentRef, paymentDocumentRef))
		{
			throw new AdempiereException("payable and payment shall not be the same but there are: " + payableDocumentRef);
		}

		if (amounts.getPayAmt().signum() != 0 && paymentDocumentRef == null)
		{
			throw new AdempiereException("paymentDocumentRef shall be not null when amount is not zero");
		}
		if (amounts.getInvoiceProcessingFee().signum() != 0 && invoiceProcessingFeeCalculation == null)
		{
			throw new AdempiereException("invoiceProcessingFeeCalculation shall be not null when processing fee is not zero");
		}

		if (payableOverUnderAmt != null && !CurrencyId.equals(payableOverUnderAmt.getCurrencyId(), amounts.getCurrencyId()))
		{
			throw new AdempiereException("payableOverUnderAmt shall bave " + amounts.getCurrencyId() + ": " + payableOverUnderAmt);
		}
		if (paymentOverUnderAmt != null && !CurrencyId.equals(paymentOverUnderAmt.getCurrencyId(), amounts.getCurrencyId()))
		{
			throw new AdempiereException("paymentOverUnderAmt shall bave " + amounts.getCurrencyId() + ": " + paymentOverUnderAmt);
		}

		this.type = type;

		this.orgId = orgId;
		this.bpartnerId = bpartnerId;

		this.payableDocumentRef = payableDocumentRef;
		this.paymentDocumentRef = paymentDocumentRef;

		this.dateTrx = dateTrx;
		this.dateAcct = dateAcct;

		this.currencyId = amounts.getCurrencyId();
		this.amounts = amounts;
		this.payableOverUnderAmt = payableOverUnderAmt != null ? payableOverUnderAmt : Money.zero(amounts.getCurrencyId());
		this.paymentOverUnderAmt = paymentOverUnderAmt != null ? paymentOverUnderAmt : Money.zero(amounts.getCurrencyId());
		this.invoiceProcessingFeeCalculation = invoiceProcessingFeeCalculation;
	}
}
