package de.metas.contracts.refund;

import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_InvoiceSchedule;
import org.compiere.model.I_C_UOM;
import org.compiere.model.X_C_InvoiceSchedule;
import org.junit.Before;
import org.junit.Test;

import de.metas.contracts.ConditionsId;
import de.metas.contracts.model.I_C_Flatrate_RefundConfig;
import de.metas.contracts.model.X_C_Flatrate_RefundConfig;
import de.metas.invoice.InvoiceScheduleRepository;

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

public class RefundConfigRepositoryTest
{
	private static final BigDecimal NINE = new BigDecimal("9");

	private I_C_UOM uomRecord;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		uomRecord = newInstance(I_C_UOM.class);
		saveRecord(uomRecord);
	}

	@Test
	public void getByQuery()
	{
		final I_C_InvoiceSchedule invoiceScheduleRecord = newInstance(I_C_InvoiceSchedule.class);
		invoiceScheduleRecord.setInvoiceFrequency(X_C_InvoiceSchedule.INVOICEFREQUENCY_Monthly);
		invoiceScheduleRecord.setInvoiceDay(1);
		saveRecord(invoiceScheduleRecord);

		final ConditionsId conditionsId = ConditionsId.ofRepoId(20);

		final I_C_Flatrate_RefundConfig configRecord1 = newInstance(I_C_Flatrate_RefundConfig.class);
		configRecord1.setC_Flatrate_Conditions_ID(conditionsId.getRepoId());
		configRecord1.setC_InvoiceSchedule(invoiceScheduleRecord);
		configRecord1.setRefundInvoiceType(X_C_Flatrate_RefundConfig.REFUNDINVOICETYPE_Invoice);
		configRecord1.setRefundBase(X_C_Flatrate_RefundConfig.REFUNDBASE_Percentage);
		configRecord1.setRefundPercent(TEN);
		configRecord1.setRefundMode(X_C_Flatrate_RefundConfig.REFUNDMODE_Accumulated);
		configRecord1.setMinQty(ZERO);
		saveRecord(configRecord1);

		final I_C_Flatrate_RefundConfig configRecord2 = newInstance(I_C_Flatrate_RefundConfig.class);
		configRecord2.setC_Flatrate_Conditions_ID(conditionsId.getRepoId());
		configRecord2.setC_InvoiceSchedule(invoiceScheduleRecord);
		configRecord2.setRefundInvoiceType(X_C_Flatrate_RefundConfig.REFUNDINVOICETYPE_Invoice);
		configRecord2.setRefundBase(X_C_Flatrate_RefundConfig.REFUNDBASE_Percentage);
		configRecord2.setRefundPercent(TEN);
		configRecord2.setRefundMode(X_C_Flatrate_RefundConfig.REFUNDMODE_Accumulated);
		configRecord2.setMinQty(TEN);
		saveRecord(configRecord2);

		final RefundConfigRepository refundConfigRepository = new RefundConfigRepository(new InvoiceScheduleRepository());

		final RefundConfigQuery query = RefundConfigQuery.builder()
				.minQty(NINE)
				.conditionsId(conditionsId).build();

		// invoke the method under test
		final List<RefundConfig> result = refundConfigRepository.getByQuery(query);

		assertThat(result).hasSize(1);
		assertThat(result.get(0).getMinQty()).isEqualByComparingTo(ZERO);
	}

}
