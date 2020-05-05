package de.metas.banking.payment.paymentallocation.service;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;

import de.metas.bpartner.BPartnerId;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.invoiceProcessingServiceCompany.InvoiceProcessingFeeCalculation;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/**
 * Mutable invoice allocation candidate.
 *
 * Used by {@link PaymentAllocationBuilder} internally.
 *
 * @author tsa
 *
 */
@EqualsAndHashCode
@ToString
public class PayableDocument
{
	public enum PayableDocumentType
	{
		Invoice, PrepaidOrder,
	}

	@Getter
	private final OrgId orgId;

	@Getter
	private final InvoiceId invoiceId;
	private final OrderId prepayOrderId;

	@Getter
	private final BPartnerId bpartnerId;
	@Getter
	private final String documentNo;
	@Getter
	private final SOTrx soTrx;
	@Getter
	private final TableRecordReference reference;
	@Getter
	private final PayableDocumentType type;
	@Getter
	private final boolean creditMemo;
	//
	private final Money openAmtInitial;

	@Getter
	private final AllocationAmounts amountsToAllocateInitial;
	@Getter
	private AllocationAmounts amountsToAllocate;
	private AllocationAmounts amountsAllocated;

	@Getter
	private InvoiceProcessingFeeCalculation invoiceProcessingFeeCalculation;

	@Builder
	private PayableDocument(
			@NonNull final OrgId orgId,
			@Nullable final InvoiceId invoiceId,
			@Nullable final OrderId prepayOrderId,
			@Nullable final BPartnerId bpartnerId,
			@NonNull final String documentNo,
			@NonNull final SOTrx soTrx,
			final boolean creditMemo,
			//
			@NonNull final Money openAmt,
			@NonNull AllocationAmounts amountsToAllocate,
			@Nullable final InvoiceProcessingFeeCalculation invoiceProcessingFeeCalculation)
	{
		if (!orgId.isRegular())
		{
			throw new AdempiereException("Transactional organization expected: " + orgId);
		}

		if (amountsToAllocate.getInvoiceProcessingFee().signum() != 0 && invoiceProcessingFeeCalculation == null)
		{
			throw new AdempiereException("invoiceProcessingFeeCalculation is required when the fee is not zero");
		}

		if (invoiceId != null)
		{
			this.invoiceId = invoiceId;
			this.prepayOrderId = null;
			this.type = PayableDocumentType.Invoice;
			this.reference = TableRecordReference.of(I_C_Invoice.Table_Name, invoiceId);
		}
		else if (prepayOrderId != null)
		{
			this.invoiceId = null;
			this.prepayOrderId = prepayOrderId;
			this.type = PayableDocumentType.PrepaidOrder;
			this.reference = TableRecordReference.of(I_C_Order.Table_Name, prepayOrderId);
		}
		else
		{
			throw new AdempiereException("Invoice or Prepay Order shall be set");
		}

		this.orgId = orgId;
		this.bpartnerId = bpartnerId;
		this.documentNo = documentNo;
		this.soTrx = soTrx;
		this.creditMemo = creditMemo;

		if (!CurrencyId.equals(openAmt.getCurrencyId(), amountsToAllocate.getCurrencyId()))
		{
			throw new AdempiereException("openAmt and amountsToAllocate shall have the same currency: " + openAmt + ", " + amountsToAllocate);
		}
		this.openAmtInitial = openAmt;
		this.amountsToAllocateInitial = amountsToAllocate;
		this.amountsToAllocate = amountsToAllocate;
		this.amountsAllocated = amountsToAllocate.toZero();

		this.invoiceProcessingFeeCalculation = invoiceProcessingFeeCalculation;
	}

	public CurrencyId getCurrencyId()
	{
		return getAmountsToAllocate().getCurrencyId();
	}

	public void addAllocatedAmounts(@NonNull final AllocationAmounts amounts)
	{
		amountsAllocated = amountsAllocated.add(amounts);
		amountsToAllocate = amountsToAllocate.subtract(amounts);
	}

	public void moveRemainingOpenAmtToDiscount()
	{
		amountsToAllocate = amountsToAllocate.movePayAmtToDiscount();
	}

	public void moveRemainingOpenAmtToWriteOff()
	{
		amountsToAllocate = amountsToAllocate.movePayAmtToWriteOff();
	}

	public Money computeProjectedOverUnderAmt(@NonNull final AllocationAmounts amountsToAllocate)
	{
		return computeOpenAmtRemainingToAllocate().subtract(amountsToAllocate.getTotalAmt());
	}

	private Money computeOpenAmtRemainingToAllocate()
	{
		return openAmtInitial.subtract(amountsAllocated.getTotalAmt());
	}

	public boolean isFullyAllocated()
	{
		return amountsToAllocate.getTotalAmt().isZero();
	}
}
