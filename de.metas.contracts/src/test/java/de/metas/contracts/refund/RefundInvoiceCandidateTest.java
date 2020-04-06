package de.metas.contracts.refund;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.refund.RefundConfig.RefundConfigBuilder;
import de.metas.contracts.refund.RefundConfig.RefundMode;
import de.metas.money.Money;
import de.metas.quantity.Quantity;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class RefundInvoiceCandidateTest
{
	private static final LocalDate NOW = LocalDate.now();
	private static final BigDecimal TWENTY = new BigDecimal("20");
	private static final BigDecimal THIRTY = new BigDecimal("30");

	private static final BPartnerId BPARTNER_ID = BPartnerId.ofRepoId(10);

	private RefundTestTools refundTestTools;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		refundTestTools = RefundTestTools.newInstance();
	}

	/**
	 * Create a contract with {@link RefundMode#APPLY_TO_EXCEEDING_QTY} and minQties zero and 30.
	 * Create a {@link RefundInvoiceCandidate} with assigned quantity 20.
	 * Expect the candidate's assignable quantity to be 29 - 20 = 9
	 */
	@Test
	public void computeAssignableQuantity()
	{
		final RefundConfigBuilder configBuilder = refundTestTools
				.createAndInitConfigBuilder()
				.refundMode(RefundMode.APPLY_TO_EXCEEDING_QTY);
		final RefundConfig refundConfig1 = configBuilder.minQty(ZERO).build();
		final RefundConfig refundConfig2 = configBuilder.minQty(THIRTY).build();

		final RefundContract refundContract = RefundContract.builder()
				.startDate(NOW)
				.endDate(NOW.plusDays(5))
				.bPartnerId(BPARTNER_ID)
				.refundConfig(refundConfig1)
				.refundConfig(refundConfig2)
				.build();

		final RefundInvoiceCandidate refundInvoiceCandidate = RefundInvoiceCandidate
				.builder()
				.assignedQuantity(Quantity.of(TWENTY, refundTestTools.getUomRecord()))
				.refundConfigs(ImmutableList.of(refundConfig1))
				.bpartnerId(BPARTNER_ID)
				.bpartnerLocationId(refundTestTools.billBPartnerLocationId)
				.invoiceableFrom(NOW)
				.money(Money.of(ONE, refundTestTools.getCurrencyId()))
				.refundContract(refundContract)
				.build();

		// invoke the method under test
		final Quantity result = refundInvoiceCandidate.computeAssignableQuantity(refundConfig1);

		assertThat(result.toBigDecimal()).isEqualByComparingTo("9");
	}
}
