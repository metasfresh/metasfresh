package de.metas.contracts.refund;

import static java.math.BigDecimal.ZERO;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.compiere.model.I_C_UOM;
import org.junit.Before;
import org.junit.Test;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.ConditionsId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.refund.RefundConfig.RefundBase;
import de.metas.contracts.refund.RefundConfig.RefundInvoiceType;
import de.metas.contracts.refund.RefundConfig.RefundMode;
import de.metas.invoice.InvoiceSchedule;
import de.metas.invoice.InvoiceSchedule.Frequency;
import de.metas.util.lang.Percent;
import de.metas.invoice.InvoiceScheduleId;

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

public class RefundContractTest
{
	private static final LocalDate NOW = LocalDate.now();

	private final static BigDecimal FOUR = new BigDecimal("4");
	private final static BigDecimal FIVE = new BigDecimal("5");

	private RefundContract refundContract;

	private RefundConfig refundConfig1;

	private RefundConfig refundConfig2;

	private I_C_UOM uomRecord;

	@Before
	public void init()
	{
		final InvoiceSchedule invoiceSchedule = InvoiceSchedule.builder()
				.id(InvoiceScheduleId.ofRepoId(5))
				.frequency(Frequency.MONTLY)
				.invoiceDayOfMonth(1)
				.build();

		refundConfig1 = RefundConfig.builder()
				.conditionsId(ConditionsId.ofRepoId(20))
				.invoiceSchedule(invoiceSchedule)
				.refundInvoiceType(RefundInvoiceType.INVOICE)
				.refundBase(RefundBase.PERCENTAGE)
				.refundMode(RefundMode.APPLY_TO_ALL_QTIES)
				.minQty(ZERO)
				.percent(Percent.of(20))
				.build();

		refundConfig2 = refundConfig1.toBuilder()
				.minQty(FIVE)
				.percent(Percent.of(30))
				.build();

		refundContract = RefundContract.builder()
				.id(FlatrateTermId.ofRepoId(100))
				.bPartnerId(BPartnerId.ofRepoId(200))
				.startDate(NOW)
				.endDate(NOW.plusDays(10))
				.refundConfig(refundConfig2)
				.refundConfig(refundConfig1)
				.build();

		uomRecord = newInstance(I_C_UOM.class);
		saveRecord(uomRecord);
	}

	@Test
	public void getRefundConfig_qty_four()
	{
		// invoke the method under test
		final RefundConfig result = refundContract.getRefundConfig(FOUR);

		assertThat(result).isEqualTo(refundConfig1);
	}

	@Test
	public void getRefundConfig_qty_five()
	{
		// invoke the method under test
		final RefundConfig result = refundContract.getRefundConfig(FIVE);

		assertThat(result).isEqualTo(refundConfig2);
	}
}
