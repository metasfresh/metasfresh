package de.metas.pos;

import com.google.common.collect.ImmutableList;
import de.metas.banking.BankAccountId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.currency.CurrencyRepository;
import de.metas.payment.sumup.SumUpService;
import de.metas.payment.sumup.repository.SumUpTransactionRepository;
import de.metas.document.DocTypeId;
import de.metas.location.CountryId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.ClientAndOrgId;
import de.metas.pricing.PricingSystemAndListId;
import de.metas.user.UserId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Every till call that may complete an order (and so update its cash journal, under the journal's row lock) takes the
 * order's row lock first, before the order is loaded and the updater runs. Thus all completers lock in the same order,
 * order then journal, as {@code POSOrdersService.tryCompleteNoFail} (SumUp status events, card checkout) does, and no
 * two of them can deadlock on the same order or complete it twice.
 * <p>
 * The in-memory test database takes no row locks, so a deadlock cannot be reproduced here; this pins which repository
 * method (the locking one, see {@link POSOrdersRepository#updateByExternalIdForUpdate}) each path uses.
 */
class POSOrdersServiceLockOrderTest
{
	private static final CurrencyId EUR = CurrencyId.ofRepoId(318);
	private static final POSTerminalId TERMINAL_ID = POSTerminalId.ofRepoId(1);
	private static final UserId CASHIER_ID = UserId.ofRepoId(100);

	private POSOrdersRepository ordersRepository;
	private POSOrdersService service;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		ordersRepository = spy(new POSOrdersRepository(new POSOrderEventDispatcher(Optional.empty())));
		final POSOrderProcessingServices processingServices = mock(POSOrderProcessingServices.class);
		when(processingServices.processPOSPayment(any())).thenAnswer(invocation -> invocation.getArgument(0));
		service = new POSOrdersService(mock(POSTerminalService.class), ordersRepository, mock(CurrencyRepository.class), processingServices, mock(SumUpService.class), mock(SumUpTransactionRepository.class));
	}

	private POSOrder newOrderWithNewPayment(final POSPaymentMethod paymentMethod)
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
								.paymentMethod(paymentMethod)
								.amount(Money.of(new BigDecimal("10.00"), EUR))
								.paymentProcessingStatus(POSPaymentProcessingStatus.NEW)
								.build()))
						.build(),
				o -> {});
	}

	@Test
	void changeStatusTo_locksTheOrderFirst()
	{
		final POSOrder order = newOrderWithNewPayment(POSPaymentMethod.CASH);

		try
		{
			service.changeStatusTo(TERMINAL_ID, order.getExternalId(), POSOrderStatus.Completed, CASHIER_ID);
		}
		catch (final RuntimeException ignored)
		{
			// the order has no lines, so it is not completed; only the lock taken before the updater ran matters here
		}

		verify(ordersRepository).updateByExternalIdForUpdate(eq(order.getExternalId()), any());
		verify(ordersRepository, never()).updateByExternalId(any(), any());
	}

	@Test
	void cashCheckout_locksTheOrderFirst()
	{
		final POSOrder order = newOrderWithNewPayment(POSPaymentMethod.CASH);

		service.checkoutPayment(POSPaymentCheckoutRequest.builder()
				.posTerminalId(TERMINAL_ID)
				.posOrderExternalId(order.getExternalId())
				.posPaymentExternalId(order.getAllPayments().get(0).getExternalId())
				.userId(CASHIER_ID)
				.cashTenderedAmount(new BigDecimal("10.00"))
				.build());

		verify(ordersRepository).updateByExternalIdForUpdate(eq(order.getExternalId()), any());
		verify(ordersRepository, never()).updateByExternalId(any(), any());
	}
}
