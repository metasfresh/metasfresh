package de.metas.pos;

import com.google.common.collect.ImmutableList;
import de.metas.banking.BankAccountId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.document.DocTypeId;
import de.metas.location.CountryId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.ClientAndOrgId;
import de.metas.payment.sumup.SumUpClientTransactionId;
import de.metas.payment.sumup.SumUpConfigId;
import de.metas.payment.sumup.SumUpMerchantCode;
import de.metas.payment.sumup.SumUpPOSRef;
import de.metas.payment.sumup.SumUpService;
import de.metas.payment.sumup.SumUpTransaction;
import de.metas.payment.sumup.SumUpTransactionExternalId;
import de.metas.payment.sumup.SumUpTransactionStatus;
import de.metas.pos.payment_gateway.POSPaymentProcessResponse;
import de.metas.pos.payment_gateway.POSPaymentProcessorType;
import de.metas.pos.payment_gateway.sumup.SumUpUtils;
import de.metas.pos.repository.model.I_C_POS_Payment;
import de.metas.pricing.PricingSystemAndListId;
import de.metas.user.UserId;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_AD_Issue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * A card payment is resolved by its own SumUp transaction as metasfresh stores it, also when that transaction's status event
 * was not applied to the payment (it arrived before the checkout's SumUp id was stored, or it was lost):
 * <ul>
 * <li>step 3 of the card checkout, storing the id, applies the stored transaction's final status;</li>
 * <li>the background sweep reconciles a PENDING card payment naming a SumUp transaction that is final.</li>
 * </ul>
 */
class POSOrdersServiceOwnSumUpResultTest
{
	private static final CurrencyId EUR = CurrencyId.ofRepoId(318);
	private static final POSTerminalId TERMINAL_ID = POSTerminalId.ofRepoId(1);
	private static final UserId CASHIER_ID = UserId.ofRepoId(100);
	private static final String CURRENT_TRX = "current-trx";
	private static final String EARLIER_TRX = "earlier-trx";

	private POSOrdersRepository ordersRepository;
	private POSOrderProcessingServices posOrderProcessingServices;
	private SumUpService sumUpService;
	private POSOrdersService service;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		POSTestMessages.createLateApprovalNotBooked();

		ordersRepository = new POSOrdersRepository(new POSOrderEventDispatcher(Optional.empty()));
		posOrderProcessingServices = mock(POSOrderProcessingServices.class);
		sumUpService = mock(SumUpService.class);
		when(sumUpService.findTransactionByClientTransactionId(any())).thenReturn(Optional.empty());
		when(sumUpService.listSuccessfulTransactionsByPOSRefUpdatedSince(any(), any())).thenReturn(ImmutableList.of());
		service = new POSOrdersService(mock(POSTerminalService.class), ordersRepository, mock(CurrencyRepository.class), posOrderProcessingServices, sumUpService, new POSCardReleaseWait(), new POSCardReaderCheckoutTerminator(mock(POSTerminalService.class), mock(SumUpService.class)), duration -> {});
	}

	private POSOrder newOrderWithCardPayment(final POSPayment.POSPaymentBuilder cardPayment)
	{
		return ordersRepository.createOrUpdateByExternalId(
				POSOrderExternalId.ofString(UUID.randomUUID().toString()),
				externalId -> POSOrder.builder()
						.externalId(externalId)
						.status(POSOrderStatus.WaitingPayment)
						.salesOrderDocTypeId(DocTypeId.ofRepoId(1))
						.pricingSystemAndListId(PricingSystemAndListId.ofRepoIds(1, 1))
						.cashbookId(BankAccountId.ofRepoId(1))
						.cashierId(CASHIER_ID)
						.date(Instant.parse("2026-11-10T10:00:00Z"))
						.shipToCustomerAndLocationId(BPartnerLocationAndCaptureId.ofRepoId(1, 1))
						.shipFrom(POSShipFrom.builder()
								.warehouseId(WarehouseId.ofRepoId(1))
								.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(0, 1))
								.countryId(CountryId.ofRepoId(101))
								.build())
						.currencyId(EUR)
						.posTerminalId(TERMINAL_ID)
						.payments(ImmutableList.of(cardPayment
								.externalId(POSPaymentExternalId.ofString(UUID.randomUUID().toString()))
								.paymentMethod(POSPaymentMethod.CARD)
								.amount(Money.of(new BigDecimal("10.00"), EUR))
								.build()))
						.build(),
				o -> {});
	}

	private static SumUpTransaction sumUpTransaction(final String clientTransactionId, final SumUpTransactionStatus status, final Instant createdAtSumUp)
	{
		return SumUpTransaction.builder()
				.configId(SumUpConfigId.ofRepoId(1))
				.externalId(SumUpTransactionExternalId.ofString("ext-" + clientTransactionId))
				.clientTransactionId(SumUpClientTransactionId.ofString(clientTransactionId))
				.merchantCode(SumUpMerchantCode.ofString("M1"))
				.timestamp(createdAtSumUp)
				.status(status)
				.amount(Amount.of(new BigDecimal("10.00"), CurrencyCode.EUR))
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(0, 1))
				.posRef(SumUpPOSRef.builder().posOrderId(1).posPaymentId(1).build())
				.build();
	}

	private static POSPaymentProcessResponse accepted(final String clientTransactionId)
	{
		return POSPaymentProcessResponse.builder()
				.status(POSPaymentProcessingStatus.PENDING)
				.config(POSTerminalPaymentProcessorConfig.builder().type(POSPaymentProcessorType.SumUp).sumUpConfigId(SumUpConfigId.ofRepoId(1)).build())
				.clientTransactionId(clientTransactionId)
				.build();
	}

	private POSPayment checkout(final POSOrder order)
	{
		final POSPayment cardPayment = order.getAllPayments().get(0);
		service.checkoutPayment(POSPaymentCheckoutRequest.builder()
				.posTerminalId(TERMINAL_ID)
				.posOrderExternalId(order.getExternalId())
				.posPaymentExternalId(cardPayment.getExternalId())
				.userId(CASHIER_ID)
				.build());
		return ordersRepository.getById(order.getLocalIdNotNull()).getPaymentById(cardPayment.getLocalIdNotNull());
	}

	private List<String> retrieveIssueErrorCodes(final POSPaymentId paymentId)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_AD_Issue.class)
				.addEqualsFilter(I_AD_Issue.COLUMNNAME_AD_Table_ID, Services.get(IADTableDAO.class).retrieveTableId(I_C_POS_Payment.Table_Name))
				.addEqualsFilter(I_AD_Issue.COLUMNNAME_Record_ID, paymentId.getRepoId())
				.create()
				.stream()
				.map(I_AD_Issue::getErrorCode)
				.collect(ImmutableList.toImmutableList());
	}

	@Test
	void stepThree_ownStoredTransactionFinal_appliedWithTheId()
	{
		final POSOrder order = newOrderWithCardPayment(POSPayment.builder().paymentProcessingStatus(POSPaymentProcessingStatus.NEW));
		when(posOrderProcessingServices.sendCardPayment(any(), any())).thenReturn(accepted(CURRENT_TRX));
		// SumUp's approval of this checkout was saved before step 3, but its status event was not applied to the payment
		// (SumUp's clock ran behind, so the approval looked older than the checkout)
		when(sumUpService.findTransactionByClientTransactionId(SumUpClientTransactionId.ofString(CURRENT_TRX)))
				.thenReturn(Optional.of(sumUpTransaction(CURRENT_TRX, SumUpTransactionStatus.SUCCESSFUL, Instant.parse("2026-01-01T00:00:00Z"))));

		final POSPayment payment = checkout(order);

		assertThat(payment.getPaymentProcessingStatus()).isEqualTo(POSPaymentProcessingStatus.SUCCESSFUL);
		assertThat(payment.getSumUpClientTransactionId()).isEqualTo(CURRENT_TRX);
		assertThat(retrieveIssueErrorCodes(payment.getLocalIdNotNull())).isEmpty();
	}

	@Test
	void stepThree_ownStoredTransactionPending_staysPending()
	{
		final POSOrder order = newOrderWithCardPayment(POSPayment.builder().paymentProcessingStatus(POSPaymentProcessingStatus.NEW));
		when(posOrderProcessingServices.sendCardPayment(any(), any())).thenReturn(accepted(CURRENT_TRX));
		when(sumUpService.findTransactionByClientTransactionId(SumUpClientTransactionId.ofString(CURRENT_TRX)))
				.thenReturn(Optional.of(sumUpTransaction(CURRENT_TRX, SumUpTransactionStatus.PENDING, Instant.now())));

		final POSPayment payment = checkout(order);

		assertThat(payment.getPaymentProcessingStatus()).isEqualTo(POSPaymentProcessingStatus.PENDING);
		assertThat(payment.getSumUpClientTransactionId()).isEqualTo(CURRENT_TRX);
	}

	@Test
	void stepThree_approvalOfAnEarlierCheckoutArrivedBeforeTheId_recordedAsLateApprovalNotBooked()
	{
		final POSOrder order = newOrderWithCardPayment(POSPayment.builder().paymentProcessingStatus(POSPaymentProcessingStatus.NEW));
		when(posOrderProcessingServices.sendCardPayment(any(), any())).thenReturn(accepted(CURRENT_TRX));
		// an earlier checkout's approval, created long before this checkout started, arrived while this checkout awaited its SumUp id
		when(sumUpService.listSuccessfulTransactionsByPOSRefUpdatedSince(any(), any()))
				.thenReturn(ImmutableList.of(
						sumUpTransaction(EARLIER_TRX, SumUpTransactionStatus.SUCCESSFUL, Instant.parse("2026-01-01T00:00:00Z")),
						sumUpTransaction(CURRENT_TRX, SumUpTransactionStatus.SUCCESSFUL, Instant.parse("2026-01-01T00:00:00Z"))));

		final POSPayment payment = checkout(order);

		assertThat(payment.getPaymentProcessingStatus()).isEqualTo(POSPaymentProcessingStatus.PENDING);
		assertThat(retrieveIssueErrorCodes(payment.getLocalIdNotNull())).containsExactly("de.metas.pos.POSPayment.LateApprovalNotBooked");
	}

	@Test
	void statusEvent_approvalOfAnEarlierCheckoutRecordedBeforeTheRetryStarted_whileTheRetryAwaitsItsSumUpId_notAppliedAndRecorded()
	{
		// checkout A was declined; the cashier retried (checkout B) 30 s after A started; B was sent, but its SumUp id is not
		// stored yet. A's late approval arrives: by SumUp's clock A was created only 30 s before B started (within the clock skew
		// allowance), but metasfresh recorded A's SumUp transaction when A was sent, before B started
		final Instant aStartedAt = Instant.parse("2026-11-10T10:00:00Z");
		final Instant bStartedAt = aStartedAt.plusSeconds(30);
		final POSOrder order = newOrderWithCardPayment(POSPayment.builder()
				.paymentProcessingStatus(POSPaymentProcessingStatus.PENDING)
				.cardCheckoutStartedAt(bStartedAt));
		final POSPayment cardPayment = order.getAllPayments().get(0);
		final POSOrderAndPaymentId orderAndPaymentId = POSOrderAndPaymentId.of(order.getLocalIdNotNull(), cardPayment.getLocalIdNotNull());
		when(sumUpService.findTransactionRecordedAt(SumUpClientTransactionId.ofString(EARLIER_TRX))).thenReturn(Optional.of(aStartedAt));

		service.updatePaymentStatusFromRemoteAndTryCompleteOrder(
				orderAndPaymentId,
				SumUpUtils.extractProcessResponse(sumUpTransaction(EARLIER_TRX, SumUpTransactionStatus.SUCCESSFUL, aStartedAt)));

		final POSPayment payment = ordersRepository.getById(order.getLocalIdNotNull()).getPaymentById(cardPayment.getLocalIdNotNull());
		assertThat(payment.getPaymentProcessingStatus()).isEqualTo(POSPaymentProcessingStatus.PENDING);
		assertThat(payment.getSumUpClientTransactionId()).isNull();
		assertThat(retrieveIssueErrorCodes(payment.getLocalIdNotNull())).containsExactly("de.metas.pos.POSPayment.LateApprovalNotBooked");
	}

	@Test
	void statusEvent_ownApprovalRecordedOnceTheCheckoutStarted_whileItAwaitsItsSumUpId_applied()
	{
		final Instant startedAt = Instant.parse("2026-11-10T10:00:30Z");
		final POSOrder order = newOrderWithCardPayment(POSPayment.builder()
				.paymentProcessingStatus(POSPaymentProcessingStatus.PENDING)
				.cardCheckoutStartedAt(startedAt));
		final POSPayment cardPayment = order.getAllPayments().get(0);
		final POSOrderAndPaymentId orderAndPaymentId = POSOrderAndPaymentId.of(order.getLocalIdNotNull(), cardPayment.getLocalIdNotNull());
		when(sumUpService.findTransactionRecordedAt(SumUpClientTransactionId.ofString(CURRENT_TRX))).thenReturn(Optional.of(startedAt.plusSeconds(1)));

		service.updatePaymentStatusFromRemoteAndTryCompleteOrder(
				orderAndPaymentId,
				SumUpUtils.extractProcessResponse(sumUpTransaction(CURRENT_TRX, SumUpTransactionStatus.SUCCESSFUL, startedAt.plusSeconds(1))));

		final POSPayment payment = ordersRepository.getById(order.getLocalIdNotNull()).getPaymentById(cardPayment.getLocalIdNotNull());
		assertThat(payment.getPaymentProcessingStatus()).isEqualTo(POSPaymentProcessingStatus.SUCCESSFUL);
		assertThat(payment.getSumUpClientTransactionId()).isEqualTo(CURRENT_TRX);
		assertThat(retrieveIssueErrorCodes(payment.getLocalIdNotNull())).isEmpty();
	}

	@Test
	void sweep_pendingPaymentNamingAFinalSumUpTransaction_reconciled()
	{
		final POSOrder order = newOrderWithCardPayment(POSPayment.builder()
				.paymentProcessingStatus(POSPaymentProcessingStatus.PENDING)
				.sumUpClientTransactionId(CURRENT_TRX)
				.cardCheckoutStartedAt(Instant.parse("2026-11-10T10:00:00Z")));
		final POSPayment cardPayment = order.getAllPayments().get(0);
		when(sumUpService.findTransactionByClientTransactionId(SumUpClientTransactionId.ofString(CURRENT_TRX)))
				.thenReturn(Optional.of(sumUpTransaction(CURRENT_TRX, SumUpTransactionStatus.SUCCESSFUL, Instant.parse("2026-11-10T10:00:01Z"))));

		service.reconcileCardPaymentWithItsSumUpTransaction(POSOrderAndPaymentId.of(order.getLocalIdNotNull(), cardPayment.getLocalIdNotNull()));

		final POSPayment payment = ordersRepository.getById(order.getLocalIdNotNull()).getPaymentById(cardPayment.getLocalIdNotNull());
		assertThat(payment.getPaymentProcessingStatus()).isEqualTo(POSPaymentProcessingStatus.SUCCESSFUL);
	}

	@Test
	void sweep_pendingPaymentWhoseSumUpTransactionIsStillPending_unchanged()
	{
		final POSOrder order = newOrderWithCardPayment(POSPayment.builder()
				.paymentProcessingStatus(POSPaymentProcessingStatus.PENDING)
				.sumUpClientTransactionId(CURRENT_TRX)
				.cardCheckoutStartedAt(Instant.parse("2026-11-10T10:00:00Z")));
		final POSPayment cardPayment = order.getAllPayments().get(0);
		when(sumUpService.findTransactionByClientTransactionId(SumUpClientTransactionId.ofString(CURRENT_TRX)))
				.thenReturn(Optional.of(sumUpTransaction(CURRENT_TRX, SumUpTransactionStatus.PENDING, Instant.parse("2026-11-10T10:00:01Z"))));

		service.reconcileCardPaymentWithItsSumUpTransaction(POSOrderAndPaymentId.of(order.getLocalIdNotNull(), cardPayment.getLocalIdNotNull()));

		final POSPayment payment = ordersRepository.getById(order.getLocalIdNotNull()).getPaymentById(cardPayment.getLocalIdNotNull());
		assertThat(payment.getPaymentProcessingStatus()).isEqualTo(POSPaymentProcessingStatus.PENDING);
	}

	@Test
	void sweep_paymentNoLongerNamingTheSumUpTransactionUnderTheLock_unchanged()
	{
		// the sweep read the payment naming CURRENT_TRX, whose SumUp transaction is final (FAILED) ...
		final Instant startedAt = Instant.parse("2026-11-10T10:00:00Z");
		final POSOrder order = newOrderWithCardPayment(POSPayment.builder()
				.paymentProcessingStatus(POSPaymentProcessingStatus.PENDING)
				.sumUpClientTransactionId(CURRENT_TRX)
				.cardCheckoutStartedAt(startedAt));
		final POSPayment cardPayment = order.getAllPayments().get(0);
		final POSOrderAndPaymentId orderAndPaymentId = POSOrderAndPaymentId.of(order.getLocalIdNotNull(), cardPayment.getLocalIdNotNull());
		final POSOrder orderAsReadBySweep = ordersRepository.getById(order.getLocalIdNotNull());
		when(sumUpService.findTransactionByClientTransactionId(SumUpClientTransactionId.ofString(CURRENT_TRX)))
				.thenReturn(Optional.of(sumUpTransaction(CURRENT_TRX, SumUpTransactionStatus.FAILED, startedAt.plusSeconds(1))));
		// ... but before it locked the order, the cashier retried: a new checkout, PENDING and naming no SumUp transaction yet
		ordersRepository.updatePaymentByIdForUpdate(orderAndPaymentId, (o, p) -> p.toBuilder()
				.sumUpClientTransactionId(null)
				.cardCheckoutStartedAt(startedAt.plusSeconds(30))
				.build());
		final POSOrdersRepository repositoryReturningTheOrderAsReadBySweep = spy(ordersRepository);
		doReturn(orderAsReadBySweep).when(repositoryReturningTheOrderAsReadBySweep).getById(order.getLocalIdNotNull());
		final POSOrdersService sweepingService = new POSOrdersService(mock(POSTerminalService.class), repositoryReturningTheOrderAsReadBySweep, mock(CurrencyRepository.class), posOrderProcessingServices, sumUpService, new POSCardReleaseWait(), new POSCardReaderCheckoutTerminator(mock(POSTerminalService.class), mock(SumUpService.class)), duration -> {});

		sweepingService.reconcileCardPaymentWithItsSumUpTransaction(orderAndPaymentId);

		// the earlier checkout's result is not applied to the new checkout
		final POSPayment payment = ordersRepository.getById(order.getLocalIdNotNull()).getPaymentById(cardPayment.getLocalIdNotNull());
		assertThat(payment.getPaymentProcessingStatus()).isEqualTo(POSPaymentProcessingStatus.PENDING);
		assertThat(payment.getSumUpClientTransactionId()).isNull();
	}
}
