package de.metas.payment.api;

import com.google.common.collect.ImmutableSet;
import de.metas.banking.BankAccountId;
import de.metas.bpartner.BPartnerId;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentId;
import de.metas.util.ISingletonService;
import de.metas.util.lang.ExternalId;
import lombok.NonNull;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_PaySelection;
import org.compiere.model.I_C_PaySelectionLine;
import org.compiere.model.I_C_Payment;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Stream;

public interface IPaymentDAO extends ISingletonService
{
	I_C_Payment getById(PaymentId paymentId);

	Optional<I_C_Payment> getByExternalOrderId(@NonNull ExternalId externalId, @NonNull OrgId orgId);

	Optional<I_C_Payment> getByExternalId(@NonNull ExternalId externalId, @NonNull OrgId orgId);

	@Nullable
	ExternalId getExternalOrderId(@NonNull PaymentId paymentId);

	List<I_C_Payment> getByIds(Set<PaymentId> paymentIds);

	/**
	 * @return payment's available to allocate amount (i.e. open amount)
	 */
	BigDecimal getAvailableAmount(PaymentId paymentId);

	/**
	 * @param creditMemoAdjusted True if we want to get absolute values for Credit Memos
	 */
	BigDecimal getInvoiceOpenAmount(I_C_Payment payment, boolean creditMemoAdjusted);

	/**
	 * retrieve payment allocations for specific payment
	 */
	List<I_C_AllocationLine> retrieveAllocationLines(I_C_Payment payment);

	/**
	 * Get the allocated amount for the given payment.<br>
	 * If the payment references a C_Charge, then only return the pay-amount.
	 * Otherwise, return the amount plus payment-writeOff-amount from C_AllocationLines which reference the payment.
	 *
	 * @return never return <code>null</code>, even if there are no allocations
	 */
	BigDecimal getAllocatedAmt(I_C_Payment payment);

	BigDecimal getAllocatedAmt(PaymentId paymentId);

	/**
	 * Retrieve all the payments that are marked as posted but do not actually have fact accounts.
	 * Exclude the entries that don't have either PayAmt or OverUnderAmt. These entries will produce 0 in posting
	 */
	List<I_C_Payment> retrievePostedWithoutFactAcct(Properties ctx, Timestamp startTime);

	Stream<PaymentId> streamPaymentIdsByBPartnerId(BPartnerId bpartnerId);

	/**
	 * Updates the discount and the payment based on DateTrx and the payment term policy.
	 */
	void updateDiscountAndPayment(I_C_Payment payment, int c_Invoice_ID, I_C_DocType c_DocType);

	ImmutableSet<PaymentId> retrievePaymentIds(PaymentQuery query);

	void save(@NonNull final I_C_Payment payment);

	Iterator<I_C_Payment> retrieveEmployeePaymentsForTimeframe(OrgId orgId, BankAccountId bankAccountId, Instant startDate, Instant endDate);

	@NonNull
	Optional<CurrencyConversionTypeId> getCurrencyConversionTypeId(@NonNull PaymentId paymentId);
}
