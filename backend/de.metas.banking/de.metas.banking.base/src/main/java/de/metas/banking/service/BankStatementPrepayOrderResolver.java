package de.metas.banking.service;

import ch.qos.logback.classic.Level;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.document.DocBaseAndSubType;
import de.metas.document.DocBaseType;
import de.metas.document.DocSubType;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.DocStatus;
import de.metas.lang.SOTrx;
import de.metas.logging.LogManager;
import de.metas.money.Money;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.order.OrderQuery;
import de.metas.payment.api.IPaymentBL;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Order;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

/**
 * Finds the waiting prepay sales order that a bank statement line pays; shared by all bank statement imports (CAMT.053, standard import),
 * so that they all match the same way.
 * <p>
 * The caller decides what the text candidates of its source are and that the lookup only runs for a credit line without a referenced invoice.
 */
@Component
public class BankStatementPrepayOrderResolver
{
	private static final Logger logger = LogManager.getLogger(BankStatementPrepayOrderResolver.class);

	@VisibleForTesting
	public static BankStatementPrepayOrderResolver newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		//noinspection DataFlowIssue
		return SpringContextHolder.getBeanOrSupply(BankStatementPrepayOrderResolver.class, BankStatementPrepayOrderResolver::new);
	}

	private final IBankStatementDAO bankStatementDAO = Services.get(IBankStatementDAO.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final IPaymentBL paymentBL = Services.get(IPaymentBL.class);

	/**
	 * @param documentNos       the texts of the line that may be an order's document number
	 * @param amount            the amount of the line
	 * @param bpartnerId        the partner of the line, if already known; then only orders with this partner as {@code C_Order.Bill_BPartner_ID} or {@code C_Order.C_BPartner_ID} are found
	 * @param lineReferenceForLog identifies the line in the log message of an ambiguous match
	 * @return the one waiting prepay sales order that is named in the texts and totals exactly the amount (full prepayment only);
	 * empty if there is none or more than one.
	 */
	@NonNull
	public Optional<I_C_Order> findOrder(
			@NonNull final ImmutableSet<String> documentNos,
			@NonNull final Money amount,
			@Nullable final BPartnerId bpartnerId,
			@Nullable final String lineReferenceForLog)
	{
		if (documentNos.isEmpty())
		{
			return Optional.empty();
		}

		final List<OrderId> candidateOrderIds = orderDAO.getOrderIds(OrderQuery.builder()
				.soTrx(SOTrx.SALES)
				.docStatus(DocStatus.WaitingPayment)
				.docTypeIds(docTypeDAO.getDocTypeIdsByDocBaseAndSubType(DocBaseAndSubType.of(DocBaseType.SalesOrder, DocSubType.PrepayOrder)))
				.documentNos(documentNos)
				.currencyId(amount.getCurrencyId())
				.amount(amount.toBigDecimal())
				.bpartnerId(bpartnerId)
				.build());

		// an order that another unprocessed bank statement line or an active completed or closed payment already carries is not up for matching;
		// only what is left counts, so that exactly one remaining order is unambiguous
		final ImmutableSet<OrderId> carriedOrderIds = ImmutableSet.<OrderId>builder()
				.addAll(bankStatementDAO.getOrderIdsOnUnprocessedLines(candidateOrderIds))
				.addAll(paymentBL.getOrderIdsWithCompletedOrClosedPayments(candidateOrderIds))
				.build();
		final List<OrderId> orderIds = candidateOrderIds.stream()
				.filter(orderId -> !carriedOrderIds.contains(orderId))
				.collect(ImmutableList.toImmutableList());
		if (orderIds.size() > 1)
		{
			Loggables.withLogger(logger, Level.INFO).addLog(
					"Multiple waiting prepay orders found for bank statement line {}! MatchedOrderIds={}", lineReferenceForLog, orderIds);
			return Optional.empty();
		}

		return orderIds.isEmpty() ? Optional.empty() : Optional.of(orderDAO.getById(orderIds.get(0)));
	}
}
