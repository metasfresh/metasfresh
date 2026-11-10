package de.metas.pos;

import com.google.common.collect.ImmutableList;
import de.metas.banking.BankAccountId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.currency.CurrencyRepository;
import de.metas.document.DocTypeId;
import de.metas.location.CountryId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.ClientAndOrgId;
import de.metas.pos.payment_gateway.POSPaymentProcessResponse;
import de.metas.pos.payment_gateway.POSPaymentProcessorType;
import de.metas.pos.repository.model.I_C_POS_Payment;
import de.metas.pricing.PricingSystemAndListId;
import de.metas.user.UserId;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.DBException;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_AD_Issue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Step 3 of the card checkout (applying SumUp's answer to the card payment) is retried while the database fails; when
 * all attempts fail, the result is logged and recorded on the payment, and the till still gets its order back.
 */
class POSOrdersServiceStepThreeRetryTest
{
	private static final CurrencyId EUR = CurrencyId.ofRepoId(318);
	private static final POSTerminalId TERMINAL_ID = POSTerminalId.ofRepoId(1);
	private static final UserId CASHIER_ID = UserId.ofRepoId(100);
	private static final String ACCEPTED_TRX = "accepted-trx";

	private POSOrdersRepository ordersRepository;
	private POSOrderProcessingServices posOrderProcessingServices;
	private final List<Duration> sleeps = new ArrayList<>();
	private POSOrdersService service;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		POSTestMessages.createCardResultNotStored();

		ordersRepository = spy(new POSOrdersRepository(new POSOrderEventDispatcher(Optional.empty())));
		posOrderProcessingServices = mock(POSOrderProcessingServices.class);
		service = new POSOrdersService(mock(POSTerminalService.class), ordersRepository, mock(CurrencyRepository.class), posOrderProcessingServices, sleeps::add);
	}

	private POSOrder newOrderWithNewCardPayment()
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
						.payments(ImmutableList.of(POSPayment.builder()
								.externalId(POSPaymentExternalId.ofString(UUID.randomUUID().toString()))
								.paymentMethod(POSPaymentMethod.CARD)
								.amount(Money.of(new BigDecimal("10.00"), EUR))
								.paymentProcessingStatus(POSPaymentProcessingStatus.NEW)
								.build()))
						.build(),
				o -> {});
	}

	private List<I_AD_Issue> retrieveIssues(final POSPaymentId paymentId)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_AD_Issue.class)
				.addEqualsFilter(I_AD_Issue.COLUMNNAME_AD_Table_ID, Services.get(IADTableDAO.class).retrieveTableId(I_C_POS_Payment.Table_Name))
				.addEqualsFilter(I_AD_Issue.COLUMNNAME_Record_ID, paymentId.getRepoId())
				.create()
				.list();
	}

	@Test
	void stepThree_retriedThreeTimes_thenLogsAndAnswersOrder()
	{
		final POSOrder order = newOrderWithNewCardPayment();
		final POSPayment cardPayment = order.getAllPayments().get(0);

		// SumUp accepted the checkout, but metasfresh could not save its transaction (cause i): the answer is indeterminate, with SumUp's id
		when(posOrderProcessingServices.sendCardPayment(any(), any()))
				.thenReturn(POSPaymentProcessResponse.errorIndeterminate(
						POSTerminalPaymentProcessorConfig.builder().type(POSPaymentProcessorType.SumUp).build(),
						"saving the SumUp transaction failed",
						null,
						ACCEPTED_TRX));
		// ... and the database keeps failing while step 3 writes
		doThrow(new DBException("database unavailable"))
				.when(ordersRepository).updatePaymentByIdForUpdate(any(), any());

		final POSOrder answered = service.checkoutPayment(POSPaymentCheckoutRequest.builder()
				.posTerminalId(TERMINAL_ID)
				.posOrderExternalId(order.getExternalId())
				.posPaymentExternalId(cardPayment.getExternalId())
				.userId(CASHIER_ID)
				.build());

		// 3 attempts, with a backoff of 200 ms, then 1 s
		verify(ordersRepository, times(3)).updatePaymentByIdForUpdate(any(), any());
		assertThat(sleeps).containsExactly(Duration.ofMillis(200), Duration.ofSeconds(1));

		// the till gets the order as saved by step 1: the card payment is still PENDING, without SumUp's id
		final POSPayment answeredPayment = answered.getAllPayments().get(0);
		assertThat(answeredPayment.getPaymentProcessingStatus()).isEqualTo(POSPaymentProcessingStatus.PENDING);
		assertThat(answeredPayment.getSumUpClientTransactionId()).isNull();
		assertThat(answeredPayment.getCardCheckoutStartedAt()).isNotNull();

		// one record on the payment for the back office, naming SumUp's id
		final List<I_AD_Issue> issues = retrieveIssues(cardPayment.getLocalIdNotNull());
		assertThat(issues).hasSize(1);
		assertThat(issues.get(0).getErrorCode()).isEqualTo("de.metas.pos.POSPayment.CardResultNotStored");
		assertThat(issues.get(0).getIssueSummary()).contains(ACCEPTED_TRX);
	}

	@Test
	void stepThree_nonDatabaseFailure_notRetried_rethrown()
	{
		final POSOrder order = newOrderWithNewCardPayment();
		final POSPayment cardPayment = order.getAllPayments().get(0);
		when(posOrderProcessingServices.sendCardPayment(any(), any()))
				.thenReturn(POSPaymentProcessResponse.errorIndeterminate(
						POSTerminalPaymentProcessorConfig.builder().type(POSPaymentProcessorType.SumUp).build(),
						"timeout",
						null,
						null));
		doThrow(new IllegalStateException("not a database failure"))
				.when(ordersRepository).updatePaymentByIdForUpdate(any(), any());

		assertThatThrownBy(() -> service.checkoutPayment(POSPaymentCheckoutRequest.builder()
				.posTerminalId(TERMINAL_ID)
				.posOrderExternalId(order.getExternalId())
				.posPaymentExternalId(cardPayment.getExternalId())
				.userId(CASHIER_ID)
				.build()))
				.hasMessageContaining("not a database failure");

		verify(ordersRepository, times(1)).updatePaymentByIdForUpdate(any(), any());
		assertThat(sleeps).isEmpty();
		assertThat(retrieveIssues(cardPayment.getLocalIdNotNull())).isEmpty();
	}
}
