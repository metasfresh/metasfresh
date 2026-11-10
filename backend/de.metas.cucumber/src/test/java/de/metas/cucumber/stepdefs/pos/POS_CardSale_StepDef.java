/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.cucumber.stepdefs.pos;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.adempiere.model.I_C_Invoice;
import de.metas.common.util.time.SystemTime;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.invoice.C_Invoice_StepDefData;
import de.metas.cucumber.stepdefs.tax.C_TaxCategory_StepDefData;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.order.OrderId;
import de.metas.payment.sumup.SumUpClientTransactionId;
import de.metas.payment.sumup.SumUpService;
import de.metas.payment.sumup.SumUpTransaction;
import de.metas.payment.sumup.repository.SumUpTransactionQuery;
import de.metas.payment.sumup.repository.SumUpTransactionRepository;
import de.metas.pos.POSCardPaymentCheckResult;
import de.metas.pos.POSCardPaymentReleaseRequest;
import de.metas.pos.POSCardPaymentReleaseResult;
import de.metas.pos.POSOrder;
import de.metas.pos.POSOrderExternalId;
import de.metas.pos.POSOrderStatus;
import de.metas.pos.POSOrdersService;
import de.metas.pos.POSPayment;
import de.metas.pos.POSPaymentCheckoutRequest;
import de.metas.pos.POSPaymentExternalId;
import de.metas.pos.POSPaymentMethod;
import de.metas.pos.POSPaymentProcessingStatus;
import de.metas.pos.POSService;
import de.metas.pos.POSTerminalId;
import de.metas.pos.async.C_POSOrder_CreateInvoiceAndShipment;
import de.metas.pos.remote.RemotePOSOrder;
import de.metas.pos.remote.RemotePOSPayment;
import de.metas.pos.repository.model.I_C_POS_Order;
import de.metas.pos.repository.model.I_C_POS_Payment;
import de.metas.product.IProductBL;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Issue;
import org.compiere.model.I_C_Payment;
import org.compiere.util.DB;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static de.metas.cucumber.stepdefs.StepDefConstants.METASFRESH_VALUE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Step definitions for a card sale at the POS till whose SumUp result may be unknown (TC23): drives the till's calls
 * ({@link POSService} / {@link POSOrdersService}), programs the SumUp stub for the sale's transactions, and asserts
 * the card payment's state. Reuses the stub, terminal and poll steps of {@link POS_InvoiceCardSettlement_StepDef}.
 * <p>
 * A "sale" is addressed by an identifier chosen in the feature; this class keeps its terminal, order, payment, the
 * remote order the till sent and the SumUp client transaction ids the stub accepted for it. A step-def instance lives
 * for one scenario, so no state leaks between scenarios.
 *
 * @see POS_InvoiceCardSettlement_StepDef
 * @see C_POS_StepDef
 * @see SystemTime_StepDef the generic clock step {@code metasfresh's clock advances by <n> seconds}
 */
@RequiredArgsConstructor
public class POS_CardSale_StepDef
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final IMsgBL msgBL = Services.get(IMsgBL.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
	@NonNull private final POSService posService = SpringContextHolder.instance.getBean(POSService.class);
	@NonNull private final POSOrdersService posOrdersService = SpringContextHolder.instance.getBean(POSOrdersService.class);
	@NonNull private final SumUpService sumUpService = SpringContextHolder.instance.getBean(SumUpService.class);
	@NonNull private final SumUpTransactionRepository sumUpTransactionRepository = SpringContextHolder.instance.getBean(SumUpTransactionRepository.class);

	// test-only trigger that makes step 3's write of a card payment fail (see failCardPaymentUpdate), and its attempt counter
	static final String POS_PAYMENT_UPDATE_FAILURE = "cucumber_pos_payment_update_fails";
	static final String POS_PAYMENT_UPDATE_FAILURE_SEQ = "cucumber_pos_payment_update_fails_seq";

	/** Seconds to wait for the async {@code C_POSOrder_CreateInvoiceAndShipment} work package (sales order + invoice) to land. */
	private static final int ASYNC_INVOICE_TIMEOUT_SEC = 60;

	@NonNull private final C_POS_StepDefData posTable;
	@NonNull private final M_Product_StepDefData productTable;
	@NonNull private final C_TaxCategory_StepDefData taxCategoryTable;
	@NonNull private final C_Invoice_StepDefData invoiceTable;
	@NonNull private final POS_InvoiceCardSettlement_StepDef settlementStepDef;

	// the sales of the running scenario, by their feature identifier
	private final Map<String, Sale> salesByIdentifier = new HashMap<>();
	// how many of the stub's received checkouts were already assigned to a sale
	private int stubCheckoutsAssigned = 0;

	/**
	 * What this class knows about a sale of the scenario.
	 */
	private static class Sale
	{
		@NonNull final POSTerminalId terminalId;
		@NonNull final UserId cashierId;
		@NonNull final RemotePOSOrder remoteOrder;
		@NonNull final POSOrderExternalId orderExternalId;
		@NonNull final POSPaymentExternalId cardPaymentExternalId;
		// the client_transaction_id of every checkout the SumUp stub accepted for this sale, in order
		@NonNull final List<String> acceptedClientTransactionIds = new ArrayList<>();
		@Nullable POSCardPaymentReleaseResult lastReleaseResult;
		@Nullable POSCardPaymentCheckResult lastCheckResult;
		// the order the till got back from its last card checkout
		@Nullable POSOrder lastCheckoutAnswer;

		private Sale(
				@NonNull final POSTerminalId terminalId,
				@NonNull final UserId cashierId,
				@NonNull final RemotePOSOrder remoteOrder)
		{
			this.terminalId = terminalId;
			this.cashierId = cashierId;
			this.remoteOrder = remoteOrder;
			this.orderExternalId = remoteOrder.getUuid();
			this.cardPaymentExternalId = remoteOrder.getPayments().get(0).getUuid();
		}
	}

	//
	//
	// Creating and driving the sale
	//
	//

	/**
	 * The cashier builds a sale with a single card payment for the lines' total and sends the card charge to the
	 * terminal's SumUp reader (the till's "pay by card"), via {@link POSService#checkoutPayment}. Unlike
	 * {@code a POS card sale is made at ... and approved on the card reader} it does <b>not</b> approve the charge on
	 * the reader and does not wait for the sale to complete; the sale stays open, whatever the card payment's state is.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_Product_ID</b> — (required, identifier-ref) product sold on this line<br>
	 *   <b>Price</b> — (required) unit price, tax included as per the terminal's price list<br>
	 *   <b>Qty</b> — (optional) quantity; defaults to 1<br>
	 *   <b>C_TaxCategory_ID</b> — (required, identifier-ref) the product's line-level tax category<br>
	 * @cucumber.depends StepDefData: C_POS_StepDefData, M_Product_StepDefData, C_TaxCategory_StepDefData;
	 * the terminal must be configured with SumUp and a default card reader
	 * @cucumber.example
	 * <pre>
	 * When a POS card sale sale1 is checked out at shopTill for:
	 *   | M_Product_ID | Price | C_TaxCategory_ID |
	 *   | product1     | 2.50  | taxCategory07    |
	 * </pre>
	 */
	@And("^a POS card sale (\\S+) is checked out at (\\S+) for:$")
	public void cardSaleCheckedOut(
			@NonNull final String saleIdentifier,
			@NonNull final String terminalIdentifier,
			@NonNull final DataTable dataTable)
	{
		final Sale sale = createSale(saleIdentifier, terminalIdentifier, dataTable);
		sale.lastCheckoutAnswer = posService.checkoutPayment(newCheckoutRequest(sale));
		assignAcceptedStubCheckouts(sale);
	}

	/**
	 * The cashier builds a sale with a single card payment and puts it in "waiting for payment", but does not pay yet:
	 * no checkout is sent. The starting point for steps that drive the payment's checkout in parts.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_Product_ID</b> — (required, identifier-ref) product sold on this line<br>
	 *   <b>Price</b> — (required) unit price, tax included as per the terminal's price list<br>
	 *   <b>Qty</b> — (optional) quantity; defaults to 1<br>
	 *   <b>C_TaxCategory_ID</b> — (required, identifier-ref) the product's line-level tax category<br>
	 * @cucumber.depends StepDefData: C_POS_StepDefData, M_Product_StepDefData, C_TaxCategory_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Given a POS card sale sale1 is prepared at shopTill for:
	 *   | M_Product_ID | Price | C_TaxCategory_ID |
	 *   | product1     | 2.50  | taxCategory07    |
	 * </pre>
	 */
	@And("^a POS card sale (\\S+) is prepared at (\\S+) for:$")
	public void cardSalePrepared(
			@NonNull final String saleIdentifier,
			@NonNull final String terminalIdentifier,
			@NonNull final DataTable dataTable)
	{
		createSale(saleIdentifier, terminalIdentifier, dataTable);
	}

	private Sale createSale(
			@NonNull final String saleIdentifier,
			@NonNull final String terminalIdentifier,
			@NonNull final DataTable dataTable)
	{
		final POSTerminalId posTerminalId = posTable.getId(StepDefDataIdentifier.ofString(terminalIdentifier));
		final UserId cashierId = StepDefUtil.getUserIdByLogin(METASFRESH_VALUE);

		final RemotePOSOrder remoteOrder = C_POS_StepDef.newRemotePOSOrder(
				posTerminalId,
				POSOrderExternalId.ofString(UUID.randomUUID().toString()),
				POSPaymentExternalId.ofString(UUID.randomUUID().toString()),
				POSPaymentMethod.CARD,
				DataTableRows.of(dataTable).stream().collect(ImmutableList.toImmutableList()),
				productTable,
				taxCategoryTable,
				productBL);

		posService.updateOrderFromRemote(remoteOrder, cashierId);
		posService.changeStatusTo(posTerminalId, remoteOrder.getUuid(), POSOrderStatus.WaitingPayment, cashierId);

		final Sale sale = new Sale(posTerminalId, cashierId, remoteOrder);
		salesByIdentifier.put(saleIdentifier, sale);
		return sale;
	}

	/**
	 * The till's card checkout stops after its first part, which saves the card payment as pending (the database state
	 * after a stop of the system before the SumUp answer was handled): only
	 * {@link POSOrdersService#startCardCheckout} runs, nothing is sent to SumUp. Works for the first checkout and for a
	 * retry of a failed card payment.
	 *
	 * @cucumber.stepdef
	 * @cucumber.depends the sale was created by {@code a POS card sale ... is prepared at ...} or {@code ... is checked out at ...}
	 * @cucumber.example
	 * <pre>
	 * When the till's card checkout of POS sale sale1 stops after saving the payment
	 * </pre>
	 */
	@And("^the till's card checkout of POS sale (\\S+) stops after saving the payment$")
	public void cardCheckoutStopsAfterSavingThePayment(@NonNull final String saleIdentifier)
	{
		final Sale sale = getSale(saleIdentifier);
		posOrdersService.startCardCheckout(newCheckoutRequest(sale));
	}

	/**
	 * Makes the result of the next card checkout unknown, by cause: {@code HTTP 5xx} and {@code CONNECTION_RESET} (cause ii: no SumUp
	 * transaction id is known to metasfresh), or {@code DB_SAVE_FAILS} (cause i: SumUp accepts the checkout, but metasfresh's save
	 * of the SumUp transaction afterwards fails). Only for the outline that runs all causes; a scenario of one cause uses the
	 * visible levers directly ({@code SumUp API stub answers the next checkout with ...}, and for cause i
	 * {@code saving a SumUp transaction fails with a database error}, lifted by {@code saving a SumUp transaction works again}).
	 * {@code DB_SAVE_FAILS} installs that same test-only trigger and lets the stub answer {@code SUCCESS}.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Given the card checkout's result is made unknown by DB_SAVE_FAILS
	 * </pre>
	 */
	@And("^the card checkout's result is made unknown by (HTTP 5xx|CONNECTION_RESET|DB_SAVE_FAILS)$")
	public void cardCheckoutResultMadeUnknownBy(@NonNull final String cause)
	{
		if ("DB_SAVE_FAILS".equals(cause))
		{
			settlementStepDef.failSumUpTransactionInsert();
			settlementStepDef.stubAnswersNextCheckout("SUCCESS");
		}
		else
		{
			settlementStepDef.stubAnswersNextCheckout(cause);
		}
	}

	/**
	 * Asserts that metasfresh saved the sale's (latest) SumUp transaction, with the given status.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then the SumUp transaction of POS sale sale1 is saved with status PENDING
	 * </pre>
	 */
	@And("^the SumUp transaction of POS sale (\\S+) is saved with status (\\w+)$")
	public void assertSumUpTransactionSaved(@NonNull final String saleIdentifier, @NonNull final String expectedStatus)
	{
		final String clientTransactionId = getAcceptedClientTransactionId(getSale(saleIdentifier), false);
		final List<SumUpTransaction> trxs = sumUpTransactionRepository.list(SumUpTransactionQuery.ofClientTransactionId(SumUpClientTransactionId.ofString(clientTransactionId)));
		assertThat(trxs).as("SumUp transactions of POS sale %s saved by metasfresh", saleIdentifier).hasSize(1);
		assertThat(trxs.get(0).getStatus().getCode()).as("SumUp transaction status").isEqualTo(expectedStatus);
	}

	/**
	 * The cashier retries the card payment of the sale after the SumUp stub is told to accept the next checkout; as
	 * the stub answers {@code SUCCESS} by default, this only makes the answer explicit. The new SumUp transaction is
	 * PENDING at the stub until a step reports another status for it.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And the card payment of POS sale sale1 is retried and the stub answers SUCCESS
	 * </pre>
	 */
	@And("^the card payment of POS sale (\\S+) is retried and the stub answers SUCCESS$")
	public void cardPaymentRetried(@NonNull final String saleIdentifier)
	{
		final Sale sale = getSale(saleIdentifier);
		SumUpApiStub.setNextCheckoutBehavior(SumUpApiStub.CheckoutBehavior.SUCCESS);
		sale.lastCheckoutAnswer = posService.checkoutPayment(newCheckoutRequest(sale));
		assignAcceptedStubCheckouts(sale);
	}

	/**
	 * The cashier deletes the card payment in the till; the till sends the order without it. The call must be accepted.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And the card payment of POS sale sale1 is deleted
	 * </pre>
	 */
	@And("^the card payment of POS sale (\\S+) is deleted$")
	public void cardPaymentDeleted(@NonNull final String saleIdentifier)
	{
		sendOrderWithoutPayments(getSale(saleIdentifier));
	}

	/**
	 * The cashier pays the sale in cash: the till sends the order with a new cash payment of the given amount instead of the
	 * (deleted) card payment, and checks the cash payment out, which completes the sale.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And a cash payment of 2.50 completes POS sale sale1
	 * </pre>
	 */
	@And("^a cash payment of (\\S+) completes POS sale (\\S+)$")
	public void cashPaymentCompletesSale(@NonNull final String amount, @NonNull final String saleIdentifier)
	{
		final Sale sale = getSale(saleIdentifier);
		final POSPaymentExternalId cashPaymentExternalId = POSPaymentExternalId.ofString(UUID.randomUUID().toString());
		final BigDecimal cashAmount = new BigDecimal(amount);

		posService.updateOrderFromRemote(
				RemotePOSOrder.builder()
						.uuid(sale.orderExternalId)
						.posTerminalId(sale.terminalId)
						.lines(sale.remoteOrder.getLines())
						.payments(ImmutableList.of(RemotePOSPayment.builder()
								.uuid(cashPaymentExternalId)
								.paymentMethod(POSPaymentMethod.CASH)
								.amount(cashAmount)
								.build()))
						.build(),
				sale.cashierId);

		posService.checkoutPayment(POSPaymentCheckoutRequest.builder()
				.posTerminalId(sale.terminalId)
				.posOrderExternalId(sale.orderExternalId)
				.posPaymentExternalId(cashPaymentExternalId)
				.userId(sale.cashierId)
				.cashTenderedAmount(cashAmount)
				.build());
	}

	//
	//
	// Refused till actions
	//
	//

	/**
	 * The cashier retries the card payment (checks it out again); the call must be refused with the given AD_Message.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then retrying the card payment of POS sale sale1 fails with AD_Message 'de.metas.pos.POSPayment.CardPaymentResultUnknown'
	 * </pre>
	 */
	@And("^retrying the card payment of POS sale (\\S+) fails with AD_Message '(.*)'$")
	public void retryingCardPaymentFails(@NonNull final String saleIdentifier, @NonNull final String adMessage)
	{
		final Sale sale = getSale(saleIdentifier);
		assertRefusedWith(adMessage, () -> posService.checkoutPayment(newCheckoutRequest(sale)));
	}

	/**
	 * The cashier deletes the card payment in the till (the till sends the order without it); the call must be refused
	 * with the given AD_Message.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then deleting the card payment of POS sale sale1 fails with AD_Message 'de.metas.pos.POSPayment.CardPaymentResultUnknown'
	 * </pre>
	 */
	@And("^deleting the card payment of POS sale (\\S+) fails with AD_Message '(.*)'$")
	public void deletingCardPaymentFails(@NonNull final String saleIdentifier, @NonNull final String adMessage)
	{
		final Sale sale = getSale(saleIdentifier);
		assertRefusedWith(adMessage, () -> sendOrderWithoutPayments(sale));
	}

	/**
	 * The cashier voids the sale; the call must be refused with the given AD_Message.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then voiding POS sale sale1 fails with AD_Message 'de.metas.pos.POSPayment.CardPaymentResultUnknown'
	 * </pre>
	 */
	@And("^voiding POS sale (\\S+) fails with AD_Message '(.*)'$")
	public void voidingSaleFails(@NonNull final String saleIdentifier, @NonNull final String adMessage)
	{
		final Sale sale = getSale(saleIdentifier);
		assertRefusedWith(adMessage, () -> posService.changeStatusTo(sale.terminalId, sale.orderExternalId, POSOrderStatus.Voided, sale.cashierId));
	}

	/**
	 * The till sends the order without its card payment (the payment is removed from the till's order); the call must be
	 * refused with the given AD_Message.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then sending POS sale sale1 without its card payment fails with AD_Message 'de.metas.pos.POSPayment.CardPaymentResultUnknown'
	 * </pre>
	 */
	@And("^sending POS sale (\\S+) without its card payment fails with AD_Message '(.*)'$")
	public void sendingSaleWithoutCardPaymentFails(@NonNull final String saleIdentifier, @NonNull final String adMessage)
	{
		final Sale sale = getSale(saleIdentifier);
		assertRefusedWith(adMessage, () -> sendOrderWithoutPayments(sale));
	}

	/**
	 * The cashier tries to complete the sale (the order's transition to "completed"). Completing needs every payment of the
	 * sale to be successful, so the call is accepted but leaves the order waiting for payment ({@code WP}): the step asserts exactly
	 * that. Any exception, including a refusal, fails the step.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then completing POS sale sale1 fails
	 * </pre>
	 */
	@And("^completing POS sale (\\S+) fails$")
	public void completingSaleFails(@NonNull final String saleIdentifier)
	{
		final Sale sale = getSale(saleIdentifier);
		posService.changeStatusTo(sale.terminalId, sale.orderExternalId, POSOrderStatus.Completed, sale.cashierId);
		assertThat(loadOrderRecord(sale).getStatus())
				.as("status of POS sale %s after trying to complete it while a card payment is not successful", saleIdentifier)
				.isEqualTo(POSOrderStatus.WaitingPayment.getCode());
	}

	//
	//
	// Assertions on the sale and its card payment
	//
	//

	/**
	 * Asserts the stored processing status of the sale's card payment (read from {@code C_POS_Payment}, so also a status
	 * that the till's order model does not map) and, optionally, whether the payment names a SumUp client transaction id, or that it names the first / latest transaction
	 * the SumUp stub accepted for the sale (the payment always names its current attempt).
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then the card payment of POS sale sale1 has status UNKNOWN
	 * Then the card payment of POS sale sale1 has status UNKNOWN and sumUpClientTransactionId set
	 * Then the card payment of POS sale sale1 has status UNKNOWN and sumUpClientTransactionId empty
	 * Then the card payment of POS sale sale1 has status PENDING and sumUpClientTransactionId of the latest transaction
	 * </pre>
	 */
	@And("^the card payment of POS sale (\\S+) has status ([A-Z]+)(?: and sumUpClientTransactionId (set|empty|of the (?:first|latest) transaction))?$")
	public void assertCardPaymentStatus(
			@NonNull final String saleIdentifier,
			@NonNull final String expectedStatus,
			@Nullable final String expectedClientTransactionIdState)
	{
		final I_C_POS_Payment payment = loadCardPaymentRecord(getSale(saleIdentifier));
		assertThat(payment.getPOSPaymentProcessingStatus()).as("status of the card payment of POS sale %s", saleIdentifier).isEqualTo(expectedStatus);
		if (expectedClientTransactionIdState != null)
		{
			final String actualClientTransactionId = StringUtils.trimBlankToNull(payment.getSUMUP_ClientTransactionID());
			if (expectedClientTransactionIdState.startsWith("of the"))
			{
				final String expectedClientTransactionId = getAcceptedClientTransactionId(getSale(saleIdentifier), expectedClientTransactionIdState.contains("first"));
				assertThat(actualClientTransactionId).as("SumUp client transaction id named by the card payment of POS sale %s", saleIdentifier).isEqualTo(expectedClientTransactionId);
			}
			else
			{
				assertThat(actualClientTransactionId != null).as("card payment of POS sale %s names a SumUp client transaction id", saleIdentifier).isEqualTo("set".equals(expectedClientTransactionIdState));
			}
		}
	}

	/**
	 * Like {@code the card payment of POS sale ... has status ...}, but waits until the status is reached, for a status
	 * that is set by an asynchronous event handler; fails if it is not reached within the timeout.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then after not more than 30s, the card payment of POS sale sale1 has status SUCCESSFUL
	 * </pre>
	 */
	@And("^after not more than (\\d+)s, the card payment of POS sale (\\S+) has status ([A-Z]+)$")
	public void assertCardPaymentStatusEventually(
			final int timeoutSec,
			@NonNull final String saleIdentifier,
			@NonNull final String expectedStatus) throws InterruptedException
	{
		final Sale sale = getSale(saleIdentifier);
		StepDefUtil.tryAndWait(timeoutSec, 500, () -> expectedStatus.equals(loadCardPaymentRecord(sale).getPOSPaymentProcessingStatus()));
		assertThat(loadCardPaymentRecord(sale).getPOSPaymentProcessingStatus()).as("status of the card payment of POS sale %s", saleIdentifier).isEqualTo(expectedStatus);
	}

	/**
	 * Asserts whether the card payment is in an ongoing SumUp lookup outage: {@code C_POS_Payment.CardLookupFailingSince}
	 * is set (true) or null (false).
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then the card payment of POS sale sale1 is failing a SumUp lookup true
	 * </pre>
	 */
	@And("^the card payment of POS sale (\\S+) is failing a SumUp lookup (true|false)$")
	public void assertCardPaymentFailingSumUpLookup(@NonNull final String saleIdentifier, @NonNull final String expected)
	{
		final boolean isFailing = loadCardPaymentRecord(getSale(saleIdentifier)).getCardLookupFailingSince() != null;
		assertThat(isFailing).as("card payment of POS sale %s is failing a SumUp lookup", saleIdentifier).isEqualTo(Boolean.parseBoolean(expected));
	}

	/**
	 * Asserts that the sale is completed (its order is completed, or already closed after that).
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then POS sale sale1 is completed
	 * </pre>
	 */
	@And("^POS sale (\\S+) is completed$")
	public void assertSaleCompleted(@NonNull final String saleIdentifier)
	{
		assertThat(isCompleted(getSale(saleIdentifier))).as("POS sale %s is completed", saleIdentifier).isTrue();
	}

	/**
	 * Asserts that the sale is not completed (its order is still open for payment, or voided).
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then POS sale sale1 is not completed
	 * </pre>
	 */
	@And("^POS sale (\\S+) is not completed$")
	public void assertSaleNotCompleted(@NonNull final String saleIdentifier)
	{
		assertThat(isCompleted(getSale(saleIdentifier))).as("POS sale %s is not completed", saleIdentifier).isFalse();
	}

	/**
	 * Like {@code POS sale ... is completed}, but waits until it is, for a sale that an asynchronous SumUp status event
	 * completes; fails if it is not completed within the timeout.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then after not more than 30s, POS sale sale1 is completed
	 * </pre>
	 */
	@And("^after not more than (\\d+)s, POS sale (\\S+) is completed$")
	public void assertSaleCompletedEventually(final int timeoutSec, @NonNull final String saleIdentifier) throws InterruptedException
	{
		final Sale sale = getSale(saleIdentifier);
		StepDefUtil.tryAndWait(timeoutSec, 500, () -> isCompleted(sale));
		assertThat(isCompleted(sale)).as("POS sale %s is completed", saleIdentifier).isTrue();
	}

	/**
	 * Waits for the async {@code C_POSOrder_CreateInvoiceAndShipment} work package of the completed sale, asserts that its
	 * invoice is paid, and registers the invoice under the sale's identifier.
	 *
	 * @cucumber.stepdef
	 * @cucumber.depends StepDefData: C_Invoice_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then the invoice of POS sale sale1 is paid
	 * </pre>
	 */
	@And("^the invoice of POS sale (\\S+) is paid$")
	public void assertInvoicePaid(@NonNull final String saleIdentifier) throws InterruptedException
	{
		final Sale sale = getSale(saleIdentifier);
		final POSOrder completedOrder = StepDefUtil.tryAndWaitForItem(
				ASYNC_INVOICE_TIMEOUT_SEC,
				500L,
				() -> posService.getOpenOrders(sale.terminalId, sale.cashierId, ImmutableSet.of(sale.orderExternalId))
						.stream()
						.findFirst()
						.filter(order -> order.getSalesOrderId() != null));

		final OrderId salesOrderId = completedOrder.getSalesOrderId();
		final List<I_C_Invoice> invoices = invoiceDAO.getInvoicesForOrderIds(ImmutableList.of(salesOrderId));
		assertThat(invoices).as("invoices of the sales order of POS sale %s", saleIdentifier).hasSize(1);
		assertThat(invoices.get(0).isPaid()).as("POS sale %s invoice IsPaid", saleIdentifier).isTrue();
		invoiceTable.putOrReplace(StepDefDataIdentifier.ofString(saleIdentifier), invoices.get(0));
	}

	/**
	 * Asserts that no payment receipt ({@code C_Payment}) was booked for the sale's card payment, i.e. a SumUp approval was
	 * not booked: the payment has no {@code C_Payment} link, and no receipt of the sale's customer for the sale's amount exists.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then the card payment of POS sale sale1 has no payment receipt
	 * </pre>
	 */
	@And("^the card payment of POS sale (\\S+) has no payment receipt$")
	public void assertCardPaymentHasNoReceipt(@NonNull final String saleIdentifier)
	{
		final Sale sale = getSale(saleIdentifier);
		assertThat(loadCardPaymentRecord(sale).getC_Payment_ID())
				.as("C_Payment linked to the card payment of POS sale %s", saleIdentifier)
				.isLessThanOrEqualTo(0);

		// also no card receipt that was booked but not linked: none for the sale's customer (the walk-in customer of the scenario's terminal) and amount
		final int customerId = loadOrderRecord(sale).getC_BPartner_ID();
		final BigDecimal amount = sale.remoteOrder.getPayments().get(0).getAmount();
		final int receiptCount = queryBL.createQueryBuilder(I_C_Payment.class)
				.addEqualsFilter(I_C_Payment.COLUMNNAME_C_BPartner_ID, customerId)
				.addEqualsFilter(I_C_Payment.COLUMNNAME_IsReceipt, true)
				.addEqualsFilter(I_C_Payment.COLUMNNAME_PayAmt, amount)
				.create()
				.count();
		assertThat(receiptCount).as("card receipts (C_Payment) of POS sale %s's customer for %s", saleIdentifier, amount).isZero();
	}

	/**
	 * Asserts that the sale's card payment does not carry the card details (SumUp's transaction id) of the first SumUp
	 * transaction the stub accepted for it, as metasfresh saved that transaction: a result of that checkout was not applied.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then the card payment of POS sale sale1 does not carry the card details of the first SumUp transaction
	 * </pre>
	 */
	@And("^the card payment of POS sale (\\S+) does not carry the card details of the first SumUp transaction$")
	public void assertCardPaymentDoesNotCarryFirstTransaction(@NonNull final String saleIdentifier)
	{
		final Sale sale = getSale(saleIdentifier);
		final String firstClientTransactionId = getAcceptedClientTransactionId(sale, true);
		final List<SumUpTransaction> firstTrxs = sumUpTransactionRepository.list(SumUpTransactionQuery.ofClientTransactionId(SumUpClientTransactionId.ofString(firstClientTransactionId)));
		assertThat(firstTrxs).as("first SumUp transaction of POS sale %s saved by metasfresh", saleIdentifier).hasSize(1);

		assertThat(loadCardPaymentRecord(sale).getPOSPaymentProcessing_TrxId())
				.as("SumUp transaction id on the card payment of POS sale %s", saleIdentifier)
				.isNotEqualTo(firstTrxs.get(0).getExternalId().getAsString());
	}

	/**
	 * Asserts the open amount of the sale (its total minus its payments that are not deleted), as the till shows it.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then POS sale sale1 has open amount 0.00
	 * </pre>
	 */
	@And("^POS sale (\\S+) has open amount (\\S+)$")
	public void assertOpenAmount(@NonNull final String saleIdentifier, @NonNull final String expectedOpenAmount)
	{
		final Sale sale = getSale(saleIdentifier);
		final POSOrder order = posService.getOpenOrders(sale.terminalId, sale.cashierId, ImmutableSet.of(sale.orderExternalId))
				.stream()
				.findFirst()
				.orElseThrow(() -> new AdempiereException("POS sale " + saleIdentifier + " is not open"));
		assertThat(order.getOpenAmt().toBigDecimal()).as("open amount of POS sale %s", saleIdentifier).isEqualByComparingTo(new BigDecimal(expectedOpenAmount));
	}

	/**
	 * Counts the AD_Issue rows tagged with the sale's card payment ({@code AD_Table_ID} = C_POS_Payment, {@code Record_ID}
	 * = the payment) whose error code is the given AD_Message's.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then the card payment of POS sale sale1 has 1 AD_Issue with AD_Message 'de.metas.pos.POSPayment.ReleasedAsNotCharged'
	 * </pre>
	 */
	@And("^the card payment of POS sale (\\S+) has (\\d+) AD_Issues? with AD_Message '(.*)'$")
	public void assertCardPaymentIssues(
			@NonNull final String saleIdentifier,
			final int expectedCount,
			@NonNull final String adMessage)
	{
		final TableRecordReference paymentRef = TableRecordReference.of(
				I_C_POS_Payment.Table_Name,
				loadCardPaymentRecord(getSale(saleIdentifier)).getC_POS_Payment_ID());
		final int actualCount = queryBL.createQueryBuilder(I_AD_Issue.class)
				.addEqualsFilter(I_AD_Issue.COLUMNNAME_AD_Table_ID, paymentRef.getAD_Table_ID())
				.addEqualsFilter(I_AD_Issue.COLUMNNAME_Record_ID, paymentRef.getRecord_ID())
				.addEqualsFilter(I_AD_Issue.COLUMNNAME_ErrorCode, expectedErrorCode(adMessage))
				.create()
				.count();
		assertThat(actualCount).as("AD_Issues of the card payment of POS sale %s with AD_Message %s", saleIdentifier, adMessage).isEqualTo(expectedCount);
	}

	//
	//
	// Clock, SumUp stub and callbacks
	//
	//

	/**
	 * Sets the frozen clock to the given number of seconds after the start of the card checkout of the sale (the payment's
	 * {@code CardCheckoutStartedAt}). The time is <b>absolute</b> (start + n), not relative to the clock's current value, so
	 * using it twice with 149 and 151 pins a 150 s boundary. Scenario wording "started n s ago" means this step. Relies on the
	 * Background's frozen clock ({@code metasfresh has date and time ...}), which the generic {@code @After} hook resets.
	 * Fails if the card payment has no checkout start yet.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And metasfresh's clock is 200 seconds after the card checkout of POS sale sale1
	 * </pre>
	 */
	@And("^metasfresh's clock is (\\d+) seconds after the card checkout of POS sale (\\S+)$")
	public void clockIsAfterCardCheckout(final int seconds, @NonNull final String saleIdentifier)
	{
		final Timestamp startedAt = loadCardPaymentRecord(getSale(saleIdentifier)).getCardCheckoutStartedAt();
		assertThat(startedAt).as("CardCheckoutStartedAt of the card payment of POS sale %s", saleIdentifier).isNotNull();
		final ZonedDateTime start = startedAt.toInstant().atZone(SystemTime.zoneId());
		SystemTime.setFixedTimeSource(start.plusSeconds(seconds));
	}

	/**
	 * Waits until every SumUp status event that was fired is handled (the event handling is asynchronous).
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And metasfresh handled the SumUp events
	 * </pre>
	 */
	@And("^metasfresh handled the SumUp events$")
	public void sumUpEventsHandled() throws InterruptedException
	{
		SumUpEventsTestProbe.waitUntilFiredEventsAreHandled();
	}

	/**
	 * Programs what the stub reports for a SumUp transaction of the sale: the first one the stub accepted for it, or (without
	 * "first") the latest one. The stub, not the database, knows the transaction, so this works also when metasfresh could not
	 * save it. The refund uses the stub's refund-amount parameter.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And SumUp API stub reports the transaction of POS sale sale1 as SUCCESSFUL
	 * And SumUp API stub reports the first transaction of POS sale sale1 as CANCELLED
	 * And SumUp API stub reports the transaction of POS sale sale1 as SUCCESSFUL refunded 2.50
	 * </pre>
	 */
	@And("^SumUp API stub reports the (?:(first) )?transaction of POS sale (\\S+) as (PENDING|SUCCESSFUL|FAILED|CANCELLED|REFUNDED)(?: refunded (\\S+))?$")
	public void stubReportsTransaction(
			@Nullable final String first,
			@NonNull final String saleIdentifier,
			@NonNull final String status,
			@Nullable final String refundedAmount)
	{
		final String clientTransactionId = getAcceptedClientTransactionId(getSale(saleIdentifier), first != null);
		SumUpApiStub.setTransactionStatus(clientTransactionId, status, refundedAmount != null ? new BigDecimal(refundedAmount) : null);
	}

	/**
	 * SumUp calls metasfresh back for the sale's (latest, or with "first" the first) transaction: {@link SumUpService#updateTransactionFromRemote}, as
	 * the SumUp callback REST endpoint does. Returns after the status event it fired was handled. Unlike a poll, it re-reads
	 * the transaction also when it is not PENDING.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And SumUp calls back for the transaction of POS sale sale1
	 * And SumUp calls back for the first transaction of POS sale sale1
	 * </pre>
	 */
	@And("^SumUp calls back for the (?:(first) )?transaction of POS sale (\\S+)$")
	public void sumUpCallsBack(@Nullable final String first, @NonNull final String saleIdentifier) throws InterruptedException
	{
		final String clientTransactionId = getAcceptedClientTransactionId(getSale(saleIdentifier), first != null);
		sumUpService.updateTransactionFromRemote(SumUpClientTransactionId.ofString(clientTransactionId));
		SumUpEventsTestProbe.waitUntilFiredEventsAreHandled();
	}

	/**
	 * Asserts the refund amount of the first SumUp transaction the stub accepted for the sale, as metasfresh stored it
	 * (proof that the refund event was read).
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then the first SumUp transaction of POS sale sale1 has refund amount 2.50
	 * </pre>
	 */
	@And("^the first SumUp transaction of POS sale (\\S+) has refund amount (\\S+)$")
	public void assertFirstTransactionRefundAmount(@NonNull final String saleIdentifier, @NonNull final String expectedAmount)
	{
		final String clientTransactionId = getAcceptedClientTransactionId(getSale(saleIdentifier), true);
		final List<SumUpTransaction> trxs = sumUpTransactionRepository.list(SumUpTransactionQuery.ofClientTransactionId(SumUpClientTransactionId.ofString(clientTransactionId)));
		assertThat(trxs).as("SumUp transactions with client transaction id %s", clientTransactionId).hasSize(1);
		assertThat(trxs.get(0).getAmountRefunded().toBigDecimal()).as("refund amount of the first SumUp transaction of POS sale %s", saleIdentifier).isEqualByComparingTo(new BigDecimal(expectedAmount));
	}

	/**
	 * From now on, SumUp answers the status lookup of the sale's first transaction with something metasfresh cannot read (the
	 * stub answers HTTP 200 with a transaction whose timestamp is not a timestamp; see {@link SumUpApiStub#answerLookupUnreadable}).
	 * So the lookup call itself succeeds, but the import of that transaction then fails with an error (thrown), unlike a
	 * failed lookup ({@code SumUp API stub fails the status lookup}), which the import reports as its result.
	 *
	 * @cucumber.stepdef
	 * @cucumber.depends the stub accepted a checkout for the sale
	 * @cucumber.example
	 * <pre>
	 * And the SumUp lookup of the first transaction of POS sale sale1 throws
	 * </pre>
	 */
	@And("^the SumUp lookup of the first transaction of POS sale (\\S+) throws$")
	public void sumUpLookupOfFirstTransactionThrows(@NonNull final String saleIdentifier)
	{
		SumUpApiStub.answerLookupUnreadable(getAcceptedClientTransactionId(getSale(saleIdentifier), true));
	}

	/**
	 * From now on, SumUp answers the status lookup of the sale's first transaction with HTTP 404: SumUp does not know that
	 * transaction (see {@link SumUpApiStub#answerLookupNotFound}). Unlike {@code SumUp API stub fails the status lookup}, this is
	 * an answer from SumUp, not an outage.
	 *
	 * @cucumber.stepdef
	 * @cucumber.depends the stub accepted a checkout for the sale
	 * @cucumber.example
	 * <pre>
	 * And SumUp API stub does not know the first transaction of POS sale sale1
	 * </pre>
	 */
	@And("^SumUp API stub does not know the first transaction of POS sale (\\S+)$")
	public void stubDoesNotKnowFirstTransaction(@NonNull final String saleIdentifier)
	{
		SumUpApiStub.answerLookupNotFound(getAcceptedClientTransactionId(getSale(saleIdentifier), true));
	}

	/**
	 * Asserts that metasfresh has no SumUp transaction with the client transaction id of the sale's (latest) checkout the stub
	 * accepted, e.g. because saving it failed and it was not imported since.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then metasfresh has no SumUp transaction of POS sale sale1
	 * </pre>
	 */
	@And("^metasfresh has no SumUp transaction of POS sale (\\S+)$")
	public void assertNoSumUpTransactionSaved(@NonNull final String saleIdentifier)
	{
		final String clientTransactionId = getAcceptedClientTransactionId(getSale(saleIdentifier), false);
		assertThat(sumUpTransactionRepository.list(SumUpTransactionQuery.ofClientTransactionId(SumUpClientTransactionId.ofString(clientTransactionId))))
				.as("SumUp transactions of POS sale %s saved by metasfresh", saleIdentifier)
				.isEmpty();
	}

	/**
	 * Asserts how many status lookups ({@code GET /me/transactions}) of the sale's first transaction the stub received in this
	 * scenario, whatever they were answered.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then the SumUp stub received 1 status lookup of the first transaction of POS sale sale1
	 * </pre>
	 */
	@And("^the SumUp stub received (\\d+) status lookups? of the first transaction of POS sale (\\S+)$")
	public void assertStatusLookupsOfFirstTransaction(final int expectedCount, @NonNull final String saleIdentifier)
	{
		final String clientTransactionId = getAcceptedClientTransactionId(getSale(saleIdentifier), true);
		assertThat(SumUpApiStub.getStatusLookupCount(clientTransactionId))
				.as("status lookups of the first transaction of POS sale %s received by the SumUp stub", saleIdentifier)
				.isEqualTo(expectedCount);
	}

	/**
	 * Removes the test-only trigger of {@code saving a SumUp transaction fails with a database error}: the database has recovered, so
	 * later SumUp transactions can be saved again. Does nothing if the trigger is not installed.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And saving a SumUp transaction works again
	 * </pre>
	 */
	@And("^saving a SumUp transaction works again$")
	public void sumUpTransactionSavingWorksAgain()
	{
		DB.executeUpdateAndThrowExceptionOnFail(
				"DROP TRIGGER IF EXISTS " + POS_InvoiceCardSettlement_StepDef.SUMUP_TRX_INSERT_FAILURE + " ON SUMUP_Transaction",
				ITrx.TRXNAME_None);
	}

	/**
	 * The till checks out the card payment of the sale on a background thread while SumUp's approval is imported, in this order:
	 * <ol>
	 * <li>the stub holds the checkout's send; meanwhile another transaction locks the sale's {@code C_POS_Order} row (as the
	 * checkout's own steps do);</li>
	 * <li>the stub accepts the checkout; the checkout saves the new SumUp transaction, whose status event then waits on that
	 * lock, and waits on it itself to store SumUp's answer (its step 3);</li>
	 * <li>the stub reports the new transaction SUCCESSFUL and a SumUp poll imports it while step 3 still waits; the approval's
	 * status event is queued behind the lock, too;</li>
	 * <li>the lock is released; step 3 and the status events then each apply under the lock, and each may try to complete the sale.</li>
	 * </ol>
	 * Then it waits for the checkout (at most 60 s) and the SumUp events, and runs one more poll.
	 *
	 * @cucumber.stepdef
	 * @cucumber.depends the sale was created by {@code a POS card sale ... is prepared at ...}
	 * @cucumber.example
	 * <pre>
	 * When the card payment of POS sale sale1 is checked out while metasfresh polls SumUp, the stub approving the charge
	 * </pre>
	 */
	@And("^the card payment of POS sale (\\S+) is checked out while metasfresh polls SumUp, the stub approving the charge$")
	public void cardPaymentCheckedOutWhilePolling(@NonNull final String saleIdentifier) throws Exception
	{
		final Sale sale = getSale(saleIdentifier);
		final int orderId = loadOrderRecord(sale).getC_POS_Order_ID();
		final int checkoutsBefore = SumUpApiStub.getReceivedCheckouts().size();
		SumUpApiStub.setNextCheckoutBehavior(SumUpApiStub.CheckoutBehavior.SUCCESS);
		SumUpApiStub.holdNextCheckout();

		final ITrxManager trxManager = Services.get(ITrxManager.class);
		final Properties ctx = Env.getCtx();
		final ExecutorService executor = Executors.newSingleThreadExecutor();
		try
		{
			final Future<POSOrder> checkout = executor.submit(() -> {
				try (final IAutoCloseable ignored = Env.switchContext(ctx))
				{
					return posService.checkoutPayment(newCheckoutRequest(sale));
				}
			});

			// the checkout's step 1 has committed and its send reached the card reader (held there)
			SumUpApiStub.awaitHeldCheckoutReceived();

			// 1. lock the order, in the checkout's own lock mode (which still lets the SumUp transaction's insert reference the order)
			final String lockHolderTrxName = trxManager.createTrxName("cucumber-pos-order-lock-holder", true);
			try
			{
				DB.getSQLValueEx(lockHolderTrxName, "SELECT C_POS_Order_ID FROM C_POS_Order WHERE C_POS_Order_ID=? FOR NO KEY UPDATE", orderId);

				// 2. SumUp accepts; the checkout saves the SumUp transaction (whose status event waits for the lock), then waits
				// for the lock in its step 3: two sessions wait
				SumUpApiStub.answerHeldCheckout(SumUpApiStub.CheckoutBehavior.SUCCESS);
				StepDefUtil.tryAndWait(30, 100, () -> tryReportNewTransactionSuccessful(checkoutsBefore));
				StepDefUtil.tryAndWait(30, 100, () -> countSessionsWaitingForOrderLock() >= 2);

				// 3. SumUp's approval is imported while step 3 waits; its status event is queued behind the lock, too
				sumUpService.bulkUpdatePendingTransactions(false);
				Thread.sleep(500);
			}
			finally
			{
				// 4. release the lock
				rollbackAndClose(trxManager, lockHolderTrxName);
				SumUpApiStub.releaseHeldCheckout();
			}

			sale.lastCheckoutAnswer = checkout.get(60, TimeUnit.SECONDS);
		}
		finally
		{
			executor.shutdownNow();
		}

		SumUpEventsTestProbe.waitUntilFiredEventsAreHandled();
		settlementStepDef.pollSumUp(null);
		assignAcceptedStubCheckouts(sale);
	}

	/**
	 * @return how many database sessions wait for a row lock on {@code C_POS_Order} (the order lock of the till's card
	 * checkout and of the SumUp status events)
	 */
	private static int countSessionsWaitingForOrderLock()
	{
		return DB.getSQLValueEx(
				ITrx.TRXNAME_None,
				"SELECT COUNT(1) FROM pg_stat_activity WHERE wait_event_type='Lock' AND query ILIKE '%C_POS_Order%FOR NO KEY UPDATE%'");
	}

	private static void rollbackAndClose(@NonNull final ITrxManager trxManager, @NonNull final String trxName)
	{
		final ITrx trx = trxManager.getTrxOrNull(trxName);
		if (trx != null)
		{
			try
			{
				trx.rollback();
			}
			finally
			{
				trx.close();
			}
		}
	}

	/**
	 * @return true if the stub received a checkout since {@code checkoutsBefore} and now reports its transaction SUCCESSFUL
	 */
	private static boolean tryReportNewTransactionSuccessful(final int checkoutsBefore)
	{
		final List<SumUpApiStub.ReceivedCheckout> checkouts = SumUpApiStub.getReceivedCheckouts();
		if (checkouts.size() <= checkoutsBefore)
		{
			return false;
		}
		final String clientTransactionId = checkouts.get(checkouts.size() - 1).getClientTransactionId();
		if (clientTransactionId == null)
		{
			return false;
		}
		try
		{
			SumUpApiStub.setTransactionStatus(clientTransactionId, "SUCCESSFUL", null);
			return true;
		}
		catch (final IllegalStateException ex)
		{
			return false; // the stub records the checkout before it knows its transaction; try again
		}
	}

	/**
	 * Step 3 of the till's card checkout (storing SumUp's answer on the card payment) fails with a database error for its next
	 * {@code n} attempts, as when the database is still failing (a test-only trigger on {@code C_POS_Payment}). It is scoped to
	 * that write only: it fires on an update of a PENDING card payment that changes its status or its SumUp client transaction
	 * id, so step 1 (NEW to PENDING) and rewrites of unchanged rows are not counted. The attempts are counted by a sequence,
	 * which a failing transaction does not roll back. Needs one card payment per order. Removed by
	 * {@code saving the card payment of a POS sale works again} and after every scenario.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And saving the card payment of a POS sale fails with a database error for the next 2 attempts
	 * </pre>
	 */
	@And("^saving the card payment of a POS sale fails with a database error for the next (\\d+) attempts?$")
	public void failCardPaymentUpdate(final int attempts)
	{
		DB.executeUpdateAndThrowExceptionOnFail("DROP SEQUENCE IF EXISTS " + POS_PAYMENT_UPDATE_FAILURE_SEQ, ITrx.TRXNAME_None);
		DB.executeUpdateAndThrowExceptionOnFail("CREATE SEQUENCE " + POS_PAYMENT_UPDATE_FAILURE_SEQ, ITrx.TRXNAME_None);
		DB.executeUpdateAndThrowExceptionOnFail(
				"CREATE OR REPLACE FUNCTION " + POS_PAYMENT_UPDATE_FAILURE + "() RETURNS trigger LANGUAGE plpgsql AS $$"
						+ " BEGIN"
						+ " IF nextval('" + POS_PAYMENT_UPDATE_FAILURE_SEQ + "') <= " + attempts + " THEN RAISE EXCEPTION 'cucumber: C_POS_Payment cannot be saved'; END IF;"
						+ " RETURN NEW;"
						+ " END; $$",
				ITrx.TRXNAME_None);
		DB.executeUpdateAndThrowExceptionOnFail("DROP TRIGGER IF EXISTS " + POS_PAYMENT_UPDATE_FAILURE + " ON C_POS_Payment", ITrx.TRXNAME_None);
		DB.executeUpdateAndThrowExceptionOnFail(
				"CREATE TRIGGER " + POS_PAYMENT_UPDATE_FAILURE + " BEFORE UPDATE ON C_POS_Payment FOR EACH ROW"
						+ " WHEN (OLD.POSPaymentProcessingStatus = 'PENDING'"
						+ " AND (NEW.POSPaymentProcessingStatus IS DISTINCT FROM 'PENDING' OR NEW.SUMUP_ClientTransactionID IS DISTINCT FROM OLD.SUMUP_ClientTransactionID))"
						+ " EXECUTE PROCEDURE " + POS_PAYMENT_UPDATE_FAILURE + "()",
				ITrx.TRXNAME_None);
	}

	/**
	 * Removes the test-only trigger of {@code saving the card payment of a POS sale fails with a database error ...}: the database
	 * has recovered. Does nothing if it is not installed.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And saving the card payment of a POS sale works again
	 * </pre>
	 */
	@And("^saving the card payment of a POS sale works again$")
	public void cardPaymentSavingWorksAgain()
	{
		dropCardPaymentUpdateFailure();
	}

	/**
	 * Removes the trigger, function and sequence of {@link #failCardPaymentUpdate} after every scenario, whatever set it up (the
	 * table is shared by all scenarios). The trigger is dropped only if it exists (a catalog lookup), so that scenarios which
	 * never install it take no table lock.
	 */
	@After
	public void dropCardPaymentUpdateFailure()
	{
		final int triggerCount = DB.getSQLValueEx(ITrx.TRXNAME_None, "SELECT COUNT(1) FROM pg_trigger WHERE tgname=?", POS_PAYMENT_UPDATE_FAILURE);
		if (triggerCount > 0)
		{
			DB.executeUpdateAndThrowExceptionOnFail("DROP TRIGGER IF EXISTS " + POS_PAYMENT_UPDATE_FAILURE + " ON C_POS_Payment", ITrx.TRXNAME_None);
		}
		DB.executeUpdateAndThrowExceptionOnFail("DROP FUNCTION IF EXISTS " + POS_PAYMENT_UPDATE_FAILURE + "()", ITrx.TRXNAME_None);
		DB.executeUpdateAndThrowExceptionOnFail("DROP SEQUENCE IF EXISTS " + POS_PAYMENT_UPDATE_FAILURE_SEQ, ITrx.TRXNAME_None);
	}

	/**
	 * Asserts the order the till got back from its last card checkout of the sale: the card payment's status, and whether it
	 * names a SumUp client transaction id.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then the till's card checkout of POS sale sale1 answered the card payment with status PENDING and sumUpClientTransactionId empty
	 * </pre>
	 */
	@And("^the till's card checkout of POS sale (\\S+) answered the card payment with status ([A-Z]+) and sumUpClientTransactionId (set|empty)$")
	public void assertCheckoutAnswer(
			@NonNull final String saleIdentifier,
			@NonNull final String expectedStatus,
			@NonNull final String expectedClientTransactionIdState)
	{
		final Sale sale = getSale(saleIdentifier);
		final POSOrder answer = sale.lastCheckoutAnswer;
		assertThat(answer).as("order the till got back from the card checkout of POS sale %s", saleIdentifier).isNotNull();
		final POSPayment payment = answer.getAllPayments()
				.stream()
				.filter(p -> POSPaymentExternalId.equals(p.getExternalId(), sale.cardPaymentExternalId))
				.findFirst()
				.orElseThrow(() -> new AdempiereException("The order the till got back has no card payment of POS sale " + saleIdentifier));
		assertThat(payment.getPaymentProcessingStatus().name()).as("status of the card payment the till got back").isEqualTo(expectedStatus);
		assertThat(payment.getSumUpClientTransactionId() != null).as("the card payment the till got back names a SumUp client transaction id").isEqualTo("set".equals(expectedClientTransactionIdState));
	}

	/**
	 * Asserts that the card payment's AD_Issue with the given AD_Message (exactly one) names, in its summary, the client
	 * transaction id of the last checkout the SumUp stub accepted for the sale, so that back office can find the charge in the
	 * SumUp dashboard.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then the AD_Issue 'de.metas.pos.POSPayment.CardResultNotStored' of the card payment of POS sale sale1 names the client transaction id the SumUp stub accepted
	 * </pre>
	 */
	@And("^the AD_Issue '(.*)' of the card payment of POS sale (\\S+) names the client transaction id the SumUp stub accepted$")
	public void assertCardPaymentIssueNamesAcceptedClientTransactionId(@NonNull final String adMessage, @NonNull final String saleIdentifier)
	{
		final Sale sale = getSale(saleIdentifier);
		final String clientTransactionId = getAcceptedClientTransactionId(sale, false);
		final TableRecordReference paymentRef = TableRecordReference.of(I_C_POS_Payment.Table_Name, loadCardPaymentRecord(sale).getC_POS_Payment_ID());
		final List<I_AD_Issue> issues = queryBL.createQueryBuilder(I_AD_Issue.class)
				.addEqualsFilter(I_AD_Issue.COLUMNNAME_AD_Table_ID, paymentRef.getAD_Table_ID())
				.addEqualsFilter(I_AD_Issue.COLUMNNAME_Record_ID, paymentRef.getRecord_ID())
				.addEqualsFilter(I_AD_Issue.COLUMNNAME_ErrorCode, expectedErrorCode(adMessage))
				.create()
				.list();
		assertThat(issues).as("AD_Issues of the card payment of POS sale %s with AD_Message %s", saleIdentifier, adMessage).hasSize(1);
		assertThat(issues.get(0).getIssueSummary()).as("summary of the AD_Issue of the card payment of POS sale %s", saleIdentifier).contains(clientTransactionId);
	}

	/**
	 * Asserts how many payment receipts ({@code C_Payment}) were booked for the sale: receipts of the sale's customer (the
	 * scenario's walk-in customer) for the sale's card amount. Use after the sale's invoice is paid.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then POS sale sale1 has 1 payment receipt
	 * </pre>
	 */
	@And("^POS sale (\\S+) has (\\d+) payment receipts?$")
	public void assertPaymentReceiptCount(@NonNull final String saleIdentifier, final int expectedCount)
	{
		final Sale sale = getSale(saleIdentifier);
		final int customerId = loadOrderRecord(sale).getC_BPartner_ID();
		final BigDecimal amount = sale.remoteOrder.getPayments().get(0).getAmount();
		final int receiptCount = queryBL.createQueryBuilder(I_C_Payment.class)
				.addEqualsFilter(I_C_Payment.COLUMNNAME_C_BPartner_ID, customerId)
				.addEqualsFilter(I_C_Payment.COLUMNNAME_IsReceipt, true)
				.addEqualsFilter(I_C_Payment.COLUMNNAME_PayAmt, amount)
				.create()
				.count();
		assertThat(receiptCount).as("payment receipts (C_Payment) of POS sale %s's customer for %s", saleIdentifier, amount).isEqualTo(expectedCount);
	}

	/**
	 * Asserts how many times the sale was completed: each completion enqueues the sale for creating its sales order, invoice and
	 * shipment ({@link C_POSOrder_CreateInvoiceAndShipment}), so this counts the sale's queue elements of that processor.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then POS sale sale1 was completed 1 time
	 * </pre>
	 */
	@And("^POS sale (\\S+) was completed (\\d+) times?$")
	public void assertCompletedTimes(@NonNull final String saleIdentifier, final int expectedCount)
	{
		final int orderId = loadOrderRecord(getSale(saleIdentifier)).getC_POS_Order_ID();
		final int count = DB.getSQLValueEx(
				ITrx.TRXNAME_None,
				"SELECT COUNT(1) FROM C_Queue_Element e"
						+ " JOIN C_Queue_WorkPackage wp ON wp.C_Queue_WorkPackage_ID=e.C_Queue_WorkPackage_ID"
						+ " JOIN C_Queue_PackageProcessor p ON p.C_Queue_PackageProcessor_ID=wp.C_Queue_PackageProcessor_ID"
						+ " WHERE e.AD_Table_ID=get_table_id('C_POS_Order') AND e.Record_ID=? AND p.Classname=?",
				orderId,
				C_POSOrder_CreateInvoiceAndShipment.class.getName());
		assertThat(count).as("completions (async queue elements) of POS sale %s", saleIdentifier).isEqualTo(expectedCount);
	}

	//
	//
	// Release
	//
	//

	/**
	 * The cashier releases the card payment whose result is unknown, via {@link POSOrdersService#releaseCardPayment}. Without
	 * "with confirmation" it is the first request (the cashier's check); with it the cashier also confirms that the card was
	 * not charged, and the payment is released only if its status is still the expected one. The result is kept for the
	 * {@code the release of the card payment ... returned ...} step.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * When the card payment of POS sale sale1 is released by cashier metasfresh
	 * When the card payment of POS sale sale1 is released by cashier metasfresh with confirmation expecting UNKNOWN
	 * </pre>
	 */
	@And("^the card payment of POS sale (\\S+) is released by cashier (\\S+)(?: with confirmation expecting ([A-Z]+))?$")
	public void cardPaymentReleased(
			@NonNull final String saleIdentifier,
			@NonNull final String cashierLogin,
			@Nullable final String expectedStatus)
	{
		final Sale sale = getSale(saleIdentifier);
		sale.lastReleaseResult = posOrdersService.releaseCardPayment(POSCardPaymentReleaseRequest.builder()
				.posTerminalId(sale.terminalId)
				.posOrderExternalId(sale.orderExternalId)
				.posPaymentExternalId(sale.cardPaymentExternalId)
				.cashierId(StepDefUtil.getUserIdByLogin(cashierLogin))
				.confirmNotCharged(expectedStatus != null)
				.expectedStatus(expectedStatus != null ? POSPaymentProcessingStatus.valueOf(expectedStatus) : null)
				.build());
	}

	/**
	 * The till checks the sale's card payment whose result is unknown ({@link POSOrdersService#checkCardPayment}): metasfresh
	 * asks SumUp for the payment's own transaction, if it names one. The result is kept for
	 * {@code the check of the card payment of POS sale ... returned ...}.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * When the card payment of POS sale sale1 is checked
	 * </pre>
	 */
	@And("^the card payment of POS sale (\\S+) is checked$")
	public void cardPaymentChecked(@NonNull final String saleIdentifier)
	{
		final Sale sale = getSale(saleIdentifier);
		sale.lastCheckResult = posOrdersService.checkCardPayment(sale.terminalId, sale.orderExternalId, sale.cardPaymentExternalId, sale.cashierId);
	}

	/**
	 * Asserts whether the last check of the sale's card payment could not ask SumUp ({@code checkFailed}).
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then the check of the card payment of POS sale sale1 returned checkFailed true
	 * </pre>
	 */
	@And("^the check of the card payment of POS sale (\\S+) returned checkFailed (true|false)$")
	public void assertCheckResult(@NonNull final String saleIdentifier, @NonNull final String expectedCheckFailed)
	{
		final POSCardPaymentCheckResult result = getSale(saleIdentifier).lastCheckResult;
		assertThat(result).as("result of the last check of the card payment of POS sale %s", saleIdentifier).isNotNull();
		assertThat(result.isCheckFailed()).as("checkFailed returned by the check").isEqualTo(Boolean.parseBoolean(expectedCheckFailed));
	}

	/**
	 * Asserts the result of the last release of the sale's card payment: the payment's status in the returned order (only if given:
	 * leave it out where an asynchronous status event may still change it; assert the stored status with a waiting step instead), whether
	 * the cashier must confirm, whether SumUp could not be asked, and, if given, that the release was refused with the
	 * AD_Message (otherwise it must not have been refused).
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then the release of the card payment of POS sale sale1 returned status UNKNOWN with confirmationRequired true and checkFailed false
	 * Then the release of the card payment of POS sale sale1 returned status UNKNOWN with confirmationRequired false and checkFailed false and refused with AD_Message 'de.metas.pos.POSPayment.ReleaseNotYetAllowed'
	 * Then the release of the card payment of POS sale sale1 returned confirmationRequired false and checkFailed false and refused with AD_Message 'de.metas.pos.POSPayment.ReleaseRefusedApproved'
	 * </pre>
	 */
	@And("^the release of the card payment of POS sale (\\S+) returned (?:status ([A-Z]+) with )?confirmationRequired (true|false) and checkFailed (true|false)(?: and refused with AD_Message '(.*)')?$")
	public void assertReleaseResult(
			@NonNull final String saleIdentifier,
			@Nullable final String expectedStatus,
			@NonNull final String expectedConfirmationRequired,
			@NonNull final String expectedCheckFailed,
			@Nullable final String expectedRefusalAdMessage)
	{
		final Sale sale = getSale(saleIdentifier);
		final POSCardPaymentReleaseResult result = sale.lastReleaseResult;
		assertThat(result).as("result of the last release of the card payment of POS sale %s", saleIdentifier).isNotNull();

		if (expectedStatus != null)
		{
			final POSPayment payment = result.getOrder().getAllPayments()
					.stream()
					.filter(p -> POSPaymentExternalId.equals(p.getExternalId(), sale.cardPaymentExternalId))
					.findFirst()
					.orElseThrow(() -> new AdempiereException("The order returned by the release has no card payment of POS sale " + saleIdentifier));
			assertThat(payment.getPaymentProcessingStatus().name()).as("status returned by the release").isEqualTo(expectedStatus);
		}
		assertThat(result.isConfirmationRequired()).as("confirmationRequired returned by the release").isEqualTo(Boolean.parseBoolean(expectedConfirmationRequired));
		assertThat(result.isCheckFailed()).as("checkFailed returned by the release").isEqualTo(Boolean.parseBoolean(expectedCheckFailed));

		if (expectedRefusalAdMessage != null)
		{
			assertThat(result.getRefusedMessageKey()).as("refusal of the release").isEqualTo(AdMessageKey.of(expectedRefusalAdMessage));
			assertThat(result.getRefusedMessage()).as("refusal text of the release").isNotNull();
		}
		else
		{
			assertThat(result.isRefused()).as("the release was refused with %s", result.getRefusedMessageKey()).isFalse();
		}
	}

	//
	//
	// Helpers
	//
	//

	private Sale getSale(@NonNull final String saleIdentifier)
	{
		final Sale sale = salesByIdentifier.get(saleIdentifier);
		if (sale == null)
		{
			throw new AdempiereException("Unknown POS sale " + saleIdentifier + ": create it first with 'a POS card sale ... is checked out at ...'")
					.setParameter("known sales", salesByIdentifier.keySet());
		}
		return sale;
	}

	private POSPaymentCheckoutRequest newCheckoutRequest(@NonNull final Sale sale)
	{
		return POSPaymentCheckoutRequest.builder()
				.posTerminalId(sale.terminalId)
				.posOrderExternalId(sale.orderExternalId)
				.posPaymentExternalId(sale.cardPaymentExternalId)
				.userId(sale.cashierId)
				.build();
	}

	/**
	 * The till removes the payments from its order and sends it.
	 */
	private void sendOrderWithoutPayments(@NonNull final Sale sale)
	{
		posService.updateOrderFromRemote(
				RemotePOSOrder.builder()
						.uuid(sale.orderExternalId)
						.posTerminalId(sale.terminalId)
						.lines(sale.remoteOrder.getLines())
						.payments(ImmutableList.of())
						.build(),
				sale.cashierId);
	}

	/**
	 * Assigns the checkouts that the stub accepted since the last call to the given sale (the stub does not know the sale: a
	 * till card sale's checkout has no description). Called after every step that may send a checkout.
	 */
	private void assignAcceptedStubCheckouts(@NonNull final Sale sale)
	{
		final List<SumUpApiStub.ReceivedCheckout> checkouts = SumUpApiStub.getReceivedCheckouts();
		for (int i = stubCheckoutsAssigned; i < checkouts.size(); i++)
		{
			final String clientTransactionId = checkouts.get(i).getClientTransactionId();
			if (clientTransactionId != null)
			{
				sale.acceptedClientTransactionIds.add(clientTransactionId);
			}
		}
		stubCheckoutsAssigned = checkouts.size();
	}

	private String getAcceptedClientTransactionId(@NonNull final Sale sale, final boolean first)
	{
		assignAcceptedStubCheckouts(sale);
		final List<String> ids = sale.acceptedClientTransactionIds;
		assertThat(ids).as("client transaction ids the SumUp stub accepted for the sale").isNotEmpty();
		return first ? ids.get(0) : ids.get(ids.size() - 1);
	}

	private I_C_POS_Payment loadCardPaymentRecord(@NonNull final Sale sale)
	{
		return queryBL.createQueryBuilder(I_C_POS_Payment.class)
				.addEqualsFilter(I_C_POS_Payment.COLUMNNAME_ExternalId, sale.cardPaymentExternalId.getAsString())
				.create()
				.firstOnlyNotNull(I_C_POS_Payment.class);
	}

	private I_C_POS_Order loadOrderRecord(@NonNull final Sale sale)
	{
		return queryBL.createQueryBuilder(I_C_POS_Order.class)
				.addEqualsFilter(I_C_POS_Order.COLUMNNAME_ExternalId, sale.orderExternalId.getAsString())
				.create()
				.firstOnlyNotNull(I_C_POS_Order.class);
	}

	private boolean isCompleted(@NonNull final Sale sale)
	{
		final String status = loadOrderRecord(sale).getStatus();
		return POSOrderStatus.Completed.getCode().equals(status) || POSOrderStatus.Closed.getCode().equals(status);
	}

	private void assertRefusedWith(@NonNull final String adMessage, @NonNull final ThrowingCallable action)
	{
		assertThatThrownBy(action)
				.as("the till action must be refused with %s", adMessage)
				.isInstanceOfSatisfying(AdempiereException.class, ex -> assertThat(ex.getErrorCode()).as("AD_Message").isEqualTo(expectedErrorCode(adMessage)));
	}

	private String expectedErrorCode(@NonNull final String adMessage)
	{
		final AdMessageKey key = AdMessageKey.of(adMessage);
		return Optional.ofNullable(msgBL.getErrorCode(key)).orElseGet(key::toAD_Message);
	}
}
