package de.metas.pos;

import com.google.common.collect.ImmutableList;
import de.metas.banking.BankAccountId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.document.DocTypeId;
import de.metas.location.CountryId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.ClientAndOrgId;
import de.metas.payment.sumup.SumUpCardReader;
import de.metas.payment.sumup.SumUpCardReaderExternalId;
import de.metas.payment.sumup.SumUpClientTransactionId;
import de.metas.payment.sumup.SumUpConfig;
import de.metas.payment.sumup.SumUpConfigId;
import de.metas.payment.sumup.SumUpMerchantCode;
import de.metas.payment.sumup.SumUpService;
import de.metas.payment.sumup.SumUpTransaction;
import de.metas.payment.sumup.SumUpTransactionExternalId;
import de.metas.payment.sumup.SumUpTransactionStatus;
import de.metas.pos.payment_gateway.POSPaymentProcessorType;
import de.metas.pricing.PricingSystemAndListId;
import de.metas.user.UserId;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * The cashier's release of a card payment whose result is unknown refreshes the payment's own SumUp transaction before it
 * cancels the checkout on the card reader: a charge already final at SumUp never cancels a checkout the reader runs for
 * another till.
 */
class POSCardPaymentReleaseTest
{
	private static final CurrencyId EUR = CurrencyId.ofRepoId(318);
	private static final POSTerminalId TERMINAL_ID = POSTerminalId.ofRepoId(1);
	private static final UserId CASHIER_ID = UserId.ofRepoId(100);
	private static final ClientAndOrgId CLIENT_AND_ORG_ID = ClientAndOrgId.ofClientAndOrg(0, 1);
	private static final SumUpConfigId SUMUP_CONFIG_ID = SumUpConfigId.ofRepoId(1);
	private static final SumUpCardReaderExternalId READER_ID = SumUpCardReaderExternalId.ofString("reader-1");
	private static final SumUpClientTransactionId OWN_TRX = SumUpClientTransactionId.ofString("own-trx");
	private static final Instant CHECKOUT_STARTED_AT = Instant.parse("2026-11-10T10:00:00Z");

	private POSOrdersRepository ordersRepository;
	private SumUpService sumUpService;
	private POSOrdersService service;
	/**
	 * The status of the payment's own SumUp transaction as metasfresh stores it.
	 */
	private final AtomicReference<SumUpTransactionStatus> storedStatus = new AtomicReference<>();

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		// past the default wait of 180 s
		SystemTime.setFixedTimeSource(CHECKOUT_STARTED_AT.plusSeconds(200).atZone(ZoneId.of("UTC")));

		ordersRepository = new POSOrdersRepository(new POSOrderEventDispatcher(Optional.empty()));
		sumUpService = mock(SumUpService.class);
		when(sumUpService.findTransactionByClientTransactionId(OWN_TRX)).thenAnswer(invocation -> Optional.of(ownTransaction(storedStatus.get())));
		when(sumUpService.getConfig(SUMUP_CONFIG_ID)).thenReturn(SumUpConfig.builder()
				.id(SUMUP_CONFIG_ID)
				.isActive(true)
				.apiKey("apiKey")
				.merchantCode(SumUpMerchantCode.ofString("M"))
				.cardReaders(ImmutableList.of(SumUpCardReader.builder().name("reader").externalId(READER_ID).isActive(true).build()))
				.defaultCardReaderExternalId(READER_ID)
				.build());

		final POSTerminal terminal = mock(POSTerminal.class);
		when(terminal.getPaymentProcessorConfigNotNull()).thenReturn(POSTerminalPaymentProcessorConfig.builder()
				.type(POSPaymentProcessorType.SumUp)
				.sumUpConfigId(SUMUP_CONFIG_ID)
				.build());
		final POSTerminalService posTerminalService = mock(POSTerminalService.class);
		when(posTerminalService.getPOSTerminalById(TERMINAL_ID)).thenReturn(terminal);

		service = new POSOrdersService(posTerminalService, ordersRepository, mock(CurrencyRepository.class), mock(POSOrderProcessingServices.class), sumUpService, new POSCardReleaseWait());
	}

	@AfterEach
	void afterEach()
	{
		SystemTime.resetTimeSource();
	}

	@Test
	void ownTransactionFinishedAfterRefresh_noTerminate()
	{
		final POSOrder order = newOrderWithUnknownCardPayment();
		storedStatus.set(SumUpTransactionStatus.PENDING);
		// SumUp reports the charge as FAILED meanwhile; the refresh stores it
		refreshFromSumUpStores(SumUpTransactionStatus.FAILED);

		final POSCardPaymentReleaseResult result = service.releaseCardPayment(firstReleaseRequest(order));

		verify(sumUpService, never()).terminateCardReaderCheckout(any(), any());
		assertThat(result.isConfirmationRequired()).isFalse();
		assertThat(result.isRefused()).isFalse();
	}

	@Test
	void ownTransactionPendingAfterRefresh_terminates()
	{
		final POSOrder order = newOrderWithUnknownCardPayment();
		storedStatus.set(SumUpTransactionStatus.PENDING);
		refreshFromSumUpStores(SumUpTransactionStatus.PENDING);

		final POSCardPaymentReleaseResult result = service.releaseCardPayment(firstReleaseRequest(order));

		verify(sumUpService, times(1)).terminateCardReaderCheckout(eq(SUMUP_CONFIG_ID), eq(READER_ID));
		assertThat(result.isConfirmationRequired()).isTrue();
		assertThat(result.isRefused()).isFalse();
	}

	private void refreshFromSumUpStores(@NonNull final SumUpTransactionStatus statusAtSumUp)
	{
		doAnswer(invocation -> {
			storedStatus.set(statusAtSumUp);
			return null;
		}).when(sumUpService).updateTransactionFromRemote(eq(OWN_TRX), anyBoolean());
	}

	private POSCardPaymentReleaseRequest firstReleaseRequest(@NonNull final POSOrder order)
	{
		return POSCardPaymentReleaseRequest.builder()
				.posTerminalId(TERMINAL_ID)
				.posOrderExternalId(order.getExternalId())
				.posPaymentExternalId(order.getAllPayments().get(0).getExternalId())
				.cashierId(CASHIER_ID)
				.confirmNotCharged(false)
				.build();
	}

	private static SumUpTransaction ownTransaction(@NonNull final SumUpTransactionStatus status)
	{
		return SumUpTransaction.builder()
				.configId(SUMUP_CONFIG_ID)
				.externalId(SumUpTransactionExternalId.ofString("ext-own-trx"))
				.clientTransactionId(OWN_TRX)
				.merchantCode(SumUpMerchantCode.ofString("M"))
				.timestamp(CHECKOUT_STARTED_AT)
				.status(status)
				.amount(Amount.of(new BigDecimal("10.00"), CurrencyCode.EUR))
				.clientAndOrgId(CLIENT_AND_ORG_ID)
				.build();
	}

	/**
	 * A card sale whose card payment is UNKNOWN and names its own SumUp transaction (SumUp accepted the checkout, but saving
	 * its transaction failed), checked out 200 s ago.
	 */
	private POSOrder newOrderWithUnknownCardPayment()
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
						.date(CHECKOUT_STARTED_AT)
						.shipToCustomerAndLocationId(BPartnerLocationAndCaptureId.ofRepoId(1, 1))
						.shipFrom(POSShipFrom.builder()
								.warehouseId(WarehouseId.ofRepoId(1))
								.clientAndOrgId(CLIENT_AND_ORG_ID)
								.countryId(CountryId.ofRepoId(101))
								.build())
						.currencyId(EUR)
						.posTerminalId(TERMINAL_ID)
						.payments(ImmutableList.of(POSPayment.builder()
								.externalId(POSPaymentExternalId.ofString(UUID.randomUUID().toString()))
								.paymentMethod(POSPaymentMethod.CARD)
								.amount(Money.of(new BigDecimal("10.00"), EUR))
								.paymentProcessingStatus(POSPaymentProcessingStatus.UNKNOWN)
								.sumUpClientTransactionId(OWN_TRX.getAsString())
								.cardCheckoutStartedAt(CHECKOUT_STARTED_AT)
								.build()))
						.build(),
				o -> {});
	}
}
