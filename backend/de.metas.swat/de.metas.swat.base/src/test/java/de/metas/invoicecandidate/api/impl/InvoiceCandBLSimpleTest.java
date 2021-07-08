package de.metas.invoicecandidate.api.impl;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;

import de.metas.common.util.time.SystemTime;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.order.InvoiceRule;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

/** Simple tests for {@link InvoiceCandBL} that don't have a lot of dependencies. */
class InvoiceCandBLSimpleTest
{

	private InvoiceCandBL invoiceCandBL;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		invoiceCandBL = new InvoiceCandBL();
	}

	@Test
	void setQtyAndDateForFreightCost_noOtherICToWaitFor()
	{
		final I_C_Invoice_Candidate freightCostICRecord = newInstance(I_C_Invoice_Candidate.class);
		freightCostICRecord.setC_Order_ID(1);
		freightCostICRecord.setIsFreightCost(true);
		freightCostICRecord.setQtyToInvoice(TEN);
		saveRecord(freightCostICRecord);

		// invoke the method under test
		invoiceCandBL.setQtyAndDateForFreightCost(freightCostICRecord);

		assertThat(freightCostICRecord.getQtyToInvoice()).isEqualByComparingTo(TEN);
	}

	@Test
	void setQtyAndDateForFreightCost_otherICToWaitFor()
	{
		final Timestamp dateOrdered = de.metas.common.util.time.SystemTime.asTimestamp();

		final I_C_Invoice_Candidate freightCostICRecord = newInstance(I_C_Invoice_Candidate.class);
		freightCostICRecord.setC_Order_ID(1);
		freightCostICRecord.setIsFreightCost(true);
		freightCostICRecord.setQtyToInvoice(TEN);
		freightCostICRecord.setDateOrdered(dateOrdered);
		freightCostICRecord.setInvoiceRule(InvoiceRule.Immediate.getCode());
		saveRecord(freightCostICRecord);

		final I_C_Invoice_Candidate otherICRecord = newInstance(I_C_Invoice_Candidate.class);
		otherICRecord.setC_Order_ID(1);
		otherICRecord.setIsFreightCost(false);
		otherICRecord.setQtyToInvoice(ZERO);
		saveRecord(otherICRecord);

		// invoke the method under test
		invoiceCandBL.setQtyAndDateForFreightCost(freightCostICRecord);

		assertThat(freightCostICRecord.getQtyToInvoice()).isEqualByComparingTo(ZERO); // set to zero, in order to have freightCostICRecord wait for otherICRecord
	}

	@Test
	void setQtyAndDateForFreightCost_otherIC()
	{
		final Timestamp dateOrdered = SystemTime.asTimestamp();

		final Timestamp freightCostICDeliveryDate = TimeUtil.parseTimestamp("2020-02-15");
		final Timestamp otherICDeliveryDate = TimeUtil.parseTimestamp("2020-02-17");

		final I_C_Invoice_Candidate freightCostICRecord = newInstance(I_C_Invoice_Candidate.class);
		freightCostICRecord.setC_Order_ID(1);
		freightCostICRecord.setIsFreightCost(true);
		freightCostICRecord.setQtyToInvoice(TEN);
		freightCostICRecord.setDateOrdered(dateOrdered);
		freightCostICRecord.setInvoiceRule(InvoiceRule.Immediate.getCode());
		freightCostICRecord.setDeliveryDate(freightCostICDeliveryDate);
		freightCostICRecord.setC_OrderLine_ID(10);
		saveRecord(freightCostICRecord);

		final I_C_Invoice_Candidate otherICRecord = newInstance(I_C_Invoice_Candidate.class);
		otherICRecord.setC_Order_ID(1);
		otherICRecord.setIsFreightCost(false);
		otherICRecord.setQtyToInvoice(ONE);
		otherICRecord.setDeliveryDate(otherICDeliveryDate);
		saveRecord(otherICRecord);

		// invoke the method under test
		invoiceCandBL.setQtyAndDateForFreightCost(freightCostICRecord);

		assertThat(freightCostICRecord.getQtyToInvoice()).isEqualByComparingTo(TEN); // unchanged
		assertThat(freightCostICRecord.getDeliveryDate()).isEqualTo(otherICDeliveryDate); // was synched to the other IC
	}

}
