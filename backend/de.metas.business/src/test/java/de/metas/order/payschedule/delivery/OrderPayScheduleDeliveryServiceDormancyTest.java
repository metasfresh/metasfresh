/*
 * #%L
 * de.metas.business
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

package de.metas.order.payschedule.delivery;

import de.metas.inout.InOutId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.order.payschedule.delivery.OrderPayScheduleDeliveryRepository.DesiredDeliveryRow;
import de.metas.payment.PaymentId;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Dormancy guard tests for {@link OrderPayScheduleDeliveryService#recomputeDeliverySteps(OrderId)}.
 *
 * <p>AC #22 / R13: iter-3 is dormant for orders without an active proforma prepayment allocation.
 * When {@code inputs.getProformaPrepaymentPaymentId() == null}, {@code writeDeliveryRows} must
 * NOT be called, regardless of whether receipts exist.
 *
 * @see <a href="https://github.com/metasfresh/me03/issues/29369">me03 #29369 Split-Payment Iter 3</a>
 */
@ExtendWith(MockitoExtension.class)
class OrderPayScheduleDeliveryServiceDormancyTest
{
	private static final CurrencyId EUR = CurrencyId.ofRepoId(318);
	private static final OrderId ORDER_ID = OrderId.ofRepoId(9001);
	private static final InOutId RECEIPT_ID = InOutId.ofRepoId(101);

	@Mock
	private OrderPayScheduleDeliveryRepository repo;

	@Captor
	private ArgumentCaptor<List<DesiredDeliveryRow>> writeRowsCaptor;

	private OrderPayScheduleDeliveryService service;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		service = new OrderPayScheduleDeliveryService(repo);
	}

	// -----------------------------------------------------------------------
	// Dormant cases — writeDeliveryRows must NOT be called
	// -----------------------------------------------------------------------

	/**
	 * No proforma + no receipts → dormant (trivial case).
	 */
	@Test
	void dormant_noProforma_noReceipts()
	{
		// given
		final DeliveryStepInputs inputs = DeliveryStepInputs.builder()
				.orderId(ORDER_ID)
				.orderGrandTotal(Money.of("100.00", EUR))
				.lcPercent(new BigDecimal("30"))
				.deliveryPercent(new BigDecimal("70"))
				.proformaPrepaymentPaymentId(null)  // no proforma
				.build();
		when(repo.loadInputs(ORDER_ID)).thenReturn(inputs);

		// when
		service.recomputeDeliverySteps(ORDER_ID);

		// then: writeDeliveryRows must NOT be called (dormancy guard fires)
		verify(repo, never()).writeDeliveryRows(any(), any());
	}

	/**
	 * AC #22 / R13: no proforma + 1 receipt → still dormant.
	 * Even though a receipt exists, the absence of the proforma prepayment means
	 * iter-3 has no allocation context and must leave the existing Delivery row untouched.
	 */
	@Test
	void dormant_noProforma_withReceipt()
	{
		// given
		final DeliveryStepInputs inputs = DeliveryStepInputs.builder()
				.orderId(ORDER_ID)
				.orderGrandTotal(Money.of("68654.40", EUR))
				.lcPercent(new BigDecimal("30"))
				.deliveryPercent(new BigDecimal("70"))
				.completedReceipt(DeliveryStepInputs.ReceiptInfo.builder()
						.mInOutId(RECEIPT_ID)
						.withTaxValue(Money.of("31808.00", EUR))
						.build())
				.proformaPrepaymentPaymentId(null)  // no proforma
				.build();
		when(repo.loadInputs(ORDER_ID)).thenReturn(inputs);

		// when
		service.recomputeDeliverySteps(ORDER_ID);

		// then: writeDeliveryRows must NOT be called
		verify(repo, never()).writeDeliveryRows(any(), any());
	}

	// -----------------------------------------------------------------------
	// Active case — writeDeliveryRows MUST be called when proforma is present
	// -----------------------------------------------------------------------

	/**
	 * Proforma present + no receipts → iter-3 active; writes 1 remainder row.
	 */
	@Test
	void active_withProforma_noReceipts_writes1RemainderRow()
	{
		// given
		final DeliveryStepInputs inputs = DeliveryStepInputs.builder()
				.orderId(ORDER_ID)
				.orderGrandTotal(Money.of("100.00", EUR))
				.lcPercent(new BigDecimal("30"))
				.deliveryPercent(new BigDecimal("70"))
				.proformaPrepaymentPaymentId(PaymentId.ofRepoId(5001))  // proforma present
				.build();
		when(repo.loadInputs(ORDER_ID)).thenReturn(inputs);

		// when
		service.recomputeDeliverySteps(ORDER_ID);

		// then: writeDeliveryRows MUST be called with 1 remainder row
		verify(repo).writeDeliveryRows(any(OrderId.class), writeRowsCaptor.capture());
		final List<DesiredDeliveryRow> written = writeRowsCaptor.getValue();
		assertThat(written).as("should write exactly 1 remainder row").hasSize(1);
		assertThat(written.get(0).getMInOutId()).as("remainder row has no InOut").isNull();
	}
}
