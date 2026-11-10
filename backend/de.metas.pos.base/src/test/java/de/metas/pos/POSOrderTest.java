package de.metas.pos;

import com.google.common.collect.ImmutableList;
import de.metas.banking.BankAccountId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.document.DocTypeId;
import de.metas.location.CountryId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.ClientAndOrgId;
import de.metas.pricing.PricingSystemAndListId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.tax.api.TaxCategoryId;
import de.metas.tax.api.TaxId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import de.metas.user.UserId;
import org.adempiere.warehouse.WarehouseId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class POSOrderTest
{
	private static final CurrencyId EUR = CurrencyId.ofRepoId(318);

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	void unknownCardPayment_notRemovable_orderNotCompletable_openAmountZero()
	{
		final POSPayment unknown = POSPayment.builder()
				.externalId(POSPaymentExternalId.ofString(UUID.randomUUID().toString()))
				.localId(POSPaymentId.ofRepoId(1))
				.paymentMethod(POSPaymentMethod.CARD)
				.amount(Money.of(new BigDecimal("10.00"), EUR))
				.paymentProcessingStatus(POSPaymentProcessingStatus.UNKNOWN)
				.build();

		final I_C_UOM uom = InterfaceWrapperHelper.newInstance(I_C_UOM.class);
		uom.setUOMSymbol("Stk");
		InterfaceWrapperHelper.saveRecord(uom);

		final POSOrder order = POSOrder.builder()
				.externalId(POSOrderExternalId.ofString(UUID.randomUUID().toString()))
				.status(POSOrderStatus.WaitingPayment)
				.salesOrderDocTypeId(DocTypeId.ofRepoId(1))
				.pricingSystemAndListId(PricingSystemAndListId.ofRepoIds(1, 1))
				.cashbookId(BankAccountId.ofRepoId(1))
				.cashierId(UserId.ofRepoId(100))
				.date(Instant.parse("2026-11-10T10:00:00Z"))
				.shipToCustomerAndLocationId(BPartnerLocationAndCaptureId.ofRepoId(1, 1))
				.shipFrom(POSShipFrom.builder()
						.warehouseId(WarehouseId.ofRepoId(1))
						.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(1, 1))
						.countryId(CountryId.ofRepoId(101))
						.build())
				.currencyId(EUR)
				.posTerminalId(POSTerminalId.ofRepoId(1))
				.lines(ImmutableList.of(POSOrderLine.builder()
						.externalId(UUID.randomUUID().toString())
						.productId(ProductId.ofRepoId(1))
						.productName("product")
						.taxCategoryId(TaxCategoryId.ofRepoId(1))
						.taxId(TaxId.ofRepoId(1))
						.qty(Quantity.of(BigDecimal.ONE, uom))
						.price(Money.of(new BigDecimal("10.00"), EUR))
						.amount(Money.of(new BigDecimal("10.00"), EUR))
						.taxAmt(Money.zero(EUR))
						.build()))
				.isTaxIncluded(true)
				.payments(ImmutableList.of(unknown))
				.build();

		// the unknown payment counts as paid: no other payment is due for that amount (open amount stays 0)
		assertThat(order.getPaidAmt().toBigDecimal()).isEqualByComparingTo("10.00");
		assertThat(order.getOpenAmt().toBigDecimal()).isEqualByComparingTo("0");

		// not removable from the till's order
		assertThatThrownBy(() -> order.removePaymentsIf(payment -> true)).isInstanceOf(RuntimeException.class);
		assertThat(order.getAllPayments()).containsExactly(unknown);
		assertThat(order.getOpenAmt().toBigDecimal()).isEqualByComparingTo("0");

		// the order cannot be completed: completion requires every payment to be SUCCESSFUL
		assertThat(order.isPaymentsProcessedSuccessfully()).isFalse();

		// voiding the order (which drops the payment) is refused
		assertThat(order.checkCanTransitionToVoided().isFalse()).isTrue();
	}
}
