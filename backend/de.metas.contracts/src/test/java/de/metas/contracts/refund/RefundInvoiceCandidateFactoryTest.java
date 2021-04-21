package de.metas.contracts.refund;

import static java.math.BigDecimal.TEN;
import static org.adempiere.model.InterfaceWrapperHelper.getTableId;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import de.metas.common.util.time.SystemTime;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_InvoiceSchedule;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_C_InvoiceSchedule;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_RefundConfig;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.contracts.model.X_C_Flatrate_RefundConfig;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.currency.CurrencyCode;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.invoice.service.InvoiceScheduleRepository;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.money.CurrencyId;
import de.metas.util.lang.Percent;

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

public class RefundInvoiceCandidateFactoryTest
{
	private I_C_BPartner bPartnerRecord;
	private I_M_Product productRecord;
	private Timestamp dateToInvoiceOfAssignableCand;

	private static final BigDecimal THREE = new BigDecimal("3");

	private RefundInvoiceCandidateFactory refundInvoiceCandidateFactory;
	private I_C_Flatrate_Term refundContractRecord;
	private I_C_Invoice_Candidate refundContractIcRecord;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		bPartnerRecord = newInstance(I_C_BPartner.class);
		save(bPartnerRecord);

		final I_C_UOM uomRecord = newInstance(I_C_UOM.class);
		saveRecord(uomRecord);

		productRecord = newInstance(I_M_Product.class);
		productRecord.setC_UOM_ID(uomRecord.getC_UOM_ID());
		save(productRecord);

		final CurrencyId currencyId = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);

		final I_C_Flatrate_Conditions conditionsRecord = newInstance(I_C_Flatrate_Conditions.class);
		conditionsRecord.setType_Conditions(X_C_Flatrate_Conditions.TYPE_CONDITIONS_Refund);
		saveRecord(conditionsRecord);

		final I_C_InvoiceSchedule invoiceSchedule = newInstance(I_C_InvoiceSchedule.class);
		invoiceSchedule.setInvoiceFrequency(X_C_InvoiceSchedule.INVOICEFREQUENCY_Daily);
		invoiceSchedule.setInvoiceDistance(1);
		saveRecord(invoiceSchedule);

		final I_C_Flatrate_RefundConfig refundConfigRecord = newInstance(I_C_Flatrate_RefundConfig.class);
		refundConfigRecord.setC_Flatrate_Conditions(conditionsRecord);
		refundConfigRecord.setM_Product(productRecord);
		refundConfigRecord.setRefundInvoiceType(X_C_Flatrate_RefundConfig.REFUNDINVOICETYPE_Creditmemo);
		refundConfigRecord.setC_InvoiceSchedule(invoiceSchedule);
		refundConfigRecord.setRefundBase(X_C_Flatrate_RefundConfig.REFUNDBASE_Percentage);
		refundConfigRecord.setRefundPercent(THREE);
		refundConfigRecord.setRefundMode(X_C_Flatrate_RefundConfig.REFUNDMODE_Accumulated);
		saveRecord(refundConfigRecord);

		refundContractRecord = newInstance(I_C_Flatrate_Term.class);
		refundContractRecord.setType_Conditions(X_C_Flatrate_Term.TYPE_CONDITIONS_Refund);
		refundContractRecord.setBill_BPartner_ID(bPartnerRecord.getC_BPartner_ID());
		refundContractRecord.setC_Flatrate_Conditions(conditionsRecord);
		refundContractRecord.setM_Product_ID(productRecord.getM_Product_ID());
		refundContractRecord.setC_Currency_ID(currencyId.getRepoId());
		refundContractRecord.setStartDate(TimeUtil.asTimestamp(RefundTestTools.CONTRACT_START_DATE));
		refundContractRecord.setEndDate(TimeUtil.asTimestamp(RefundTestTools.CONTRACT_END_DATE));
		save(refundContractRecord);

		dateToInvoiceOfAssignableCand = SystemTime.asTimestamp();

		final I_C_BPartner_Location location = newInstance(I_C_BPartner_Location.class);
		location.setC_BPartner_ID(bPartnerRecord.getC_BPartner_ID());

		save(location);

		refundContractIcRecord = newInstance(I_C_Invoice_Candidate.class);
		refundContractIcRecord.setBill_BPartner_ID(bPartnerRecord.getC_BPartner_ID());
		refundContractIcRecord.setBill_Location_ID(location.getC_BPartner_Location_ID());
		refundContractIcRecord.setM_Product_ID(productRecord.getM_Product_ID());
		refundContractIcRecord.setDateToInvoice(dateToInvoiceOfAssignableCand);
		refundContractIcRecord.setAD_Table_ID(getTableId(I_C_Flatrate_Term.class));
		refundContractIcRecord.setRecord_ID(refundContractRecord.getC_Flatrate_Term_ID());
		refundContractIcRecord.setC_Currency_ID(currencyId.getRepoId());
		refundContractIcRecord.setPriceActual(TEN);
		save(refundContractIcRecord);

		final RefundConfigRepository refundConfigRepository = new RefundConfigRepository(new InvoiceScheduleRepository());
		final RefundContractRepository refundContractRepository = new RefundContractRepository(refundConfigRepository);
		final AssignmentAggregateService assignmentAggregateService = new AssignmentAggregateService(refundConfigRepository);

		refundInvoiceCandidateFactory = new RefundInvoiceCandidateFactory(refundContractRepository, assignmentAggregateService);
	}

	@Test
	public void ofRecord_RefundContractInvoiceCandidate()
	{
		// invoke the method under test
		final RefundInvoiceCandidate ofRecord = refundInvoiceCandidateFactory.ofRecord(refundContractIcRecord);

		assertThat(ofRecord.getBpartnerId().getRepoId()).isEqualTo(bPartnerRecord.getC_BPartner_ID());
		assertThat(ofRecord.getRefundContract().getId().getRepoId()).isEqualTo(refundContractRecord.getC_Flatrate_Term_ID());
		assertThat(ofRecord.getMoney().toBigDecimal()).isEqualByComparingTo(TEN);
		assertThat(ofRecord.getInvoiceableFrom()).isEqualTo(TimeUtil.asLocalDate(dateToInvoiceOfAssignableCand));

		final List<RefundConfig> refundConfigs = ofRecord.getRefundConfigs();
		assertThat(refundConfigs).hasSize(1);
		assertThat(refundConfigs.get(0).getProductId().getRepoId()).isEqualTo(productRecord.getM_Product_ID());
		assertThat(refundConfigs.get(0).getPercent()).isEqualTo(Percent.of(THREE));
	}
}
