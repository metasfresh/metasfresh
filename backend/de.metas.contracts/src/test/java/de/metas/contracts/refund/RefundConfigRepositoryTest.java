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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;

import de.metas.contracts.ConditionsId;
import de.metas.contracts.model.I_C_Flatrate_RefundConfig;
import de.metas.contracts.model.X_C_Flatrate_RefundConfig;
import de.metas.invoice.service.InvoiceScheduleRepository;
import de.metas.product.ProductId;
import lombok.NonNull;

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
	private static final BigDecimal SIXTEEN = new BigDecimal("16");

	private static final BigDecimal TWELVE = new BigDecimal("12");

	private static final BigDecimal ELEVEN = new BigDecimal("11");

	private static final BigDecimal NINE = new BigDecimal("9");

	private RefundConfigRepository refundConfigRepository;

	private ConditionsId conditionsId;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		final I_C_UOM uomRecord = newInstance(I_C_UOM.class);
		saveRecord(uomRecord);

		conditionsId = ConditionsId.ofRepoId(20);
		refundConfigRepository = new RefundConfigRepository(new InvoiceScheduleRepository());
	}

	@Test
	public void getByQuery()
	{
		createThreeRefundConfigRecords(conditionsId);

		final RefundConfigQuery query = RefundConfigQuery.builder()
				.minQty(NINE)
				.conditionsId(conditionsId).build();

		// invoke the method under test
		final List<RefundConfig> result = refundConfigRepository.getByQuery(query);

		assertThat(result).hasSize(1);
		assertThat(result.get(0).getMinQty()).isEqualByComparingTo(ZERO);
		assertThat(result.get(0).getInvoiceSchedule()).isNotNull();
	}

	/**
	 * Use {@link #createThreeRefundConfigRecords(ConditionsId)} to create the standard records (without a product).
	 * Create another config record that has a productId, but minQty = 10.
	 * Query for that product and value=9.
	 * <p>
	 * Expect the result to be empty, because since there is at least one config record for that product,
	 * the repo may not fall back to the config records that have no product.
	 */
	@Test
	public void getByQuery_product_id()
	{
		final ProductId productId = ProductId.ofRepoId(30);

		final List<I_C_Flatrate_RefundConfig> threeRefundConfigRecords = createThreeRefundConfigRecords(conditionsId);
		assertThat(threeRefundConfigRecords).allMatch(r -> r.getM_Product_ID() <= 0); // guard

		final I_C_InvoiceSchedule invoiceScheduleRecord = createInvoiceSchedule();

		final I_C_Flatrate_RefundConfig configRecord = newInstance(I_C_Flatrate_RefundConfig.class);
		configRecord.setM_Product_ID(productId.getRepoId());
		configRecord.setC_Flatrate_Conditions_ID(conditionsId.getRepoId());
		configRecord.setC_InvoiceSchedule(invoiceScheduleRecord);
		configRecord.setRefundInvoiceType(X_C_Flatrate_RefundConfig.REFUNDINVOICETYPE_Invoice);
		configRecord.setRefundBase(X_C_Flatrate_RefundConfig.REFUNDBASE_Percentage);
		configRecord.setRefundPercent(ELEVEN);
		configRecord.setRefundMode(X_C_Flatrate_RefundConfig.REFUNDMODE_Accumulated);
		configRecord.setMinQty(TEN);
		saveRecord(configRecord);

		final RefundConfigQuery query = RefundConfigQuery.builder()
				.minQty(NINE)
				.productId(productId)
				.conditionsId(conditionsId).build();

		// invoke the method under test
		final List<RefundConfig> result = refundConfigRepository.getByQuery(query);

		assertThat(result.isEmpty());
	}

	public static List<I_C_Flatrate_RefundConfig> createThreeRefundConfigRecords(@NonNull final ConditionsId conditionsId)
	{
		final I_C_InvoiceSchedule invoiceScheduleRecord = createInvoiceSchedule();

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
		configRecord2.setRefundPercent(ELEVEN);
		configRecord2.setRefundMode(X_C_Flatrate_RefundConfig.REFUNDMODE_Accumulated);
		configRecord2.setMinQty(TEN);
		saveRecord(configRecord2);

		final I_C_Flatrate_RefundConfig configRecord3 = newInstance(I_C_Flatrate_RefundConfig.class);
		configRecord3.setC_Flatrate_Conditions_ID(conditionsId.getRepoId());
		configRecord3.setC_InvoiceSchedule(invoiceScheduleRecord);
		configRecord3.setRefundInvoiceType(X_C_Flatrate_RefundConfig.REFUNDINVOICETYPE_Invoice);
		configRecord3.setRefundBase(X_C_Flatrate_RefundConfig.REFUNDBASE_Percentage);
		configRecord3.setRefundPercent(TWELVE);
		configRecord3.setRefundMode(X_C_Flatrate_RefundConfig.REFUNDMODE_Accumulated);
		configRecord3.setMinQty(SIXTEEN); // we often test with assignable candidates that have qty=15, so let's have a 3rd record, that is just beyond that.
		saveRecord(configRecord3);

		return ImmutableList.of(configRecord1, configRecord2, configRecord3);
	}

	private static I_C_InvoiceSchedule createInvoiceSchedule()
	{
		final I_C_InvoiceSchedule invoiceScheduleRecord = newInstance(I_C_InvoiceSchedule.class);
		invoiceScheduleRecord.setInvoiceFrequency(X_C_InvoiceSchedule.INVOICEFREQUENCY_Monthly);
		invoiceScheduleRecord.setInvoiceDay(1);
		invoiceScheduleRecord.setInvoiceDistance(5);
		saveRecord(invoiceScheduleRecord);
		return invoiceScheduleRecord;
	}

}
