package de.metas.banking.payment.paymentallocation.service;

import de.metas.bpartner.BPartnerId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentDirection;
import de.metas.payment.PaymentId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Payment;

import javax.annotation.Nullable;
import java.time.LocalDate;

/**
 * Mutable payment allocation candidate.
 * <p>
 * Used by {@link PaymentAllocationBuilder} internally.
 *
 * @author tsa
 */
@EqualsAndHashCode
@ToString
public class PaymentDocument implements IPaymentDocument
{
	@Getter
	private final OrgId orgId;

	@Getter
	private final PaymentId paymentId;
	@Getter
	private final BPartnerId bpartnerId;
	private final String documentNo;
	@Getter
	private final TableRecordReference reference;
	@Getter
	private final PaymentDirection paymentDirection;
	//
	private final Money openAmtInitial;
	@Getter
	private final Money amountToAllocateInitial;

	@Getter
	private Money amountToAllocate;
	private Money allocatedAmt;

	@Getter
	private final LocalDate dateTrx;

	@Builder
	private PaymentDocument(
			@NonNull final OrgId orgId,
			@NonNull final PaymentId paymentId,
			@Nullable final BPartnerId bpartnerId,
			@Nullable final String documentNo,
			@NonNull final PaymentDirection paymentDirection,
			//
			@NonNull final Money openAmt,
			@NonNull final Money amountToAllocate,
			@NonNull final LocalDate dateTrx
	)
	{
		if (!orgId.isRegular())
		{
			throw new AdempiereException("Transactional organization expected: " + orgId);
		}

		this.orgId = orgId;
		this.paymentId = paymentId;
		this.bpartnerId = bpartnerId;
		this.documentNo = documentNo;
		this.reference = TableRecordReference.of(I_C_Payment.Table_Name, paymentId);
		this.paymentDirection = paymentDirection;
		//
		Money.getCommonCurrencyIdOfAll(openAmt, amountToAllocate);
		this.openAmtInitial = openAmt;
		this.amountToAllocateInitial = amountToAllocate;
		this.amountToAllocate = amountToAllocate;
		this.allocatedAmt = amountToAllocate.toZero();
		this.dateTrx = dateTrx;
	}

	@Override
	public PaymentDocumentType getType()
	{
		return PaymentDocumentType.RegularPayment;
	}

	@Override
	public final String getDocumentNo()
	{
		if (!Check.isEmpty(documentNo, true))
		{
			return documentNo;
		}

		final TableRecordReference reference = getReference();
		if (reference != null)
		{
			return "<" + reference.getRecord_ID() + ">";
		}
		return "?";
	}

	@Override
	public CurrencyId getCurrencyId()
	{
		return amountToAllocateInitial.getCurrencyId();
	}

	@Override
	public void addAllocatedAmt(@NonNull final Money allocatedAmtToAdd)
	{
		allocatedAmt = allocatedAmt.add(allocatedAmtToAdd);
		amountToAllocate = amountToAllocate.subtract(allocatedAmtToAdd);
	}

	@Override
	public boolean isFullyAllocated()
	{
		return getAmountToAllocate().signum() == 0;
	}

	private Money getOpenAmtRemaining()
	{
		final Money totalAllocated = allocatedAmt;
		final Money openAmtRemaining = openAmtInitial.subtract(totalAllocated);
		return openAmtRemaining;
	}

	@Override
	public Money calculateProjectedOverUnderAmt(@NonNull final Money amountToAllocate)
	{
		final Money projectedOverUnderAmt = getOpenAmtRemaining()
				.subtract(amountToAllocate);
		return projectedOverUnderAmt;
	}

	@Override
	public boolean canPay(@NonNull final PayableDocument payable)
	{
		return true;
	}
}
