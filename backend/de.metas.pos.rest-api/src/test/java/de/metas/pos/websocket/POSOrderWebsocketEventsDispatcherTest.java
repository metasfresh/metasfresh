package de.metas.pos.websocket;

import com.google.common.collect.ImmutableList;
import de.metas.banking.BankAccountId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.currency.CurrencyRepository;
import de.metas.document.DocTypeId;
import de.metas.location.CountryId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.ClientAndOrgId;
import de.metas.pos.POSCardReleaseWait;
import de.metas.pos.POSOrder;
import de.metas.pos.POSOrderExternalId;
import de.metas.pos.POSOrderStatus;
import de.metas.pos.POSPayment;
import de.metas.pos.POSPaymentExternalId;
import de.metas.pos.POSPaymentMethod;
import de.metas.pos.POSPaymentProcessingStatus;
import de.metas.pos.POSShipFrom;
import de.metas.pos.POSTerminalId;
import de.metas.pos.rest_api.json.JsonPOSOrderMapper;
import de.metas.pos.websocket.json.JsonPOSOrderChangedWebSocketEvent;
import de.metas.pricing.PricingSystemAndListId;
import de.metas.user.UserId;
import de.metas.websocket.WebsocketTopicName;
import de.metas.websocket.sender.WebsocketSender;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

/**
 * The websocket event is what delivers the async transition of a card payment to UNKNOWN, so it must carry the same
 * {@code releasableAt} as the REST responses.
 */
class POSOrderWebsocketEventsDispatcherTest
{
	private static final Instant CHECKOUT_STARTED_AT = Instant.parse("2026-11-10T10:00:00Z");

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	void onChange_unknownCardPayment_carriesReleasableAt()
	{
		final I_C_Currency currency = newInstance(I_C_Currency.class);
		currency.setISO_Code("EUR");
		currency.setCurSymbol("€");
		currency.setDescription("Euro");
		currency.setStdPrecision(2);
		currency.setCostingPrecision(2);
		saveRecord(currency);
		final CurrencyId currencyId = CurrencyId.ofRepoId(currency.getC_Currency_ID());

		final POSCardReleaseWait cardReleaseWait = Mockito.mock(POSCardReleaseWait.class);
		Mockito.when(cardReleaseWait.getReleasableAt(ClientAndOrgId.ofClientAndOrg(1, 1), CHECKOUT_STARTED_AT))
				.thenReturn(CHECKOUT_STARTED_AT.plusSeconds(180));
		final WebsocketSender websocketSender = Mockito.mock(WebsocketSender.class);
		final POSOrderWebsocketEventsDispatcher dispatcher = new POSOrderWebsocketEventsDispatcher(
				new CurrencyRepository(),
				new JsonPOSOrderMapper(cardReleaseWait),
				Optional.of(websocketSender));

		dispatcher.onChange(POSOrder.builder()
				.externalId(POSOrderExternalId.ofString(UUID.randomUUID().toString()))
				.status(POSOrderStatus.WaitingPayment)
				.salesOrderDocTypeId(DocTypeId.ofRepoId(1))
				.pricingSystemAndListId(PricingSystemAndListId.ofRepoIds(1, 1))
				.cashbookId(BankAccountId.ofRepoId(1))
				.cashierId(UserId.ofRepoId(1))
				.date(CHECKOUT_STARTED_AT)
				.shipToCustomerAndLocationId(BPartnerLocationAndCaptureId.ofRepoId(1, 1))
				.shipFrom(POSShipFrom.builder()
						.warehouseId(WarehouseId.ofRepoId(1))
						.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(1, 1))
						.countryId(CountryId.ofRepoId(101))
						.build())
				.currencyId(currencyId)
				.posTerminalId(POSTerminalId.ofRepoId(1))
				.isTaxIncluded(true)
				.payments(ImmutableList.of(POSPayment.builder()
						.externalId(POSPaymentExternalId.ofString(UUID.randomUUID().toString()))
						.paymentMethod(POSPaymentMethod.CARD)
						.amount(Money.of(new BigDecimal("10.00"), currencyId))
						.paymentProcessingStatus(POSPaymentProcessingStatus.UNKNOWN)
						.cardCheckoutStartedAt(CHECKOUT_STARTED_AT)
						.build()))
				.build());

		final ArgumentCaptor<Object> eventCaptor = ArgumentCaptor.forClass(Object.class);
		Mockito.verify(websocketSender).convertAndSend(any(WebsocketTopicName.class), eventCaptor.capture());
		final JsonPOSOrderChangedWebSocketEvent event = (JsonPOSOrderChangedWebSocketEvent)eventCaptor.getValue();
		assertThat(event.getPosOrder().getPayments().get(0).getReleasableAt()).isEqualTo(CHECKOUT_STARTED_AT.plusSeconds(180));
	}
}
