package de.metas.contracts.refund.interceptor;

import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_RefundConfig;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.contracts.model.X_C_Flatrate_RefundConfig;
import de.metas.contracts.refund.AssignmentToRefundCandidateRepository;
import de.metas.contracts.refund.RefundConfigId;
import de.metas.contracts.refund.RefundConfigRepository;
import de.metas.contracts.refund.RefundConfigs;
import de.metas.contracts.refund.RefundContractRepository;
import de.metas.contracts.refund.RefundInvoiceCandidateInvalidator;
import de.metas.currency.CurrencyCode;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.invoice.service.InvoiceScheduleRepository;
import de.metas.money.CurrencyId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_InvoiceSchedule;
import org.compiere.model.I_C_UOM;
import org.compiere.model.X_C_InvoiceSchedule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/*
 * #%L
 * de.metas.contracts
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

/**
 * The currency of a per-unit refund line can be corrected after the conditions are completed, until the line has issued a refund.
 */
class C_Flatrate_RefundConfig_CurrencyCorrection_Test
{
	private AssignmentToRefundCandidateRepository assignmentToRefundCandidateRepository;
	private I_C_Flatrate_Conditions conditions;
	private I_C_InvoiceSchedule schedule;
	private CurrencyId eur;
	private CurrencyId chf;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
		saveRecord(newInstance(I_C_UOM.class));

		chf = PlainCurrencyDAO.createCurrency(CurrencyCode.CHF).getId();
		eur = PlainCurrencyDAO.createCurrency(CurrencyCode.EUR).getId();

		conditions = newInstance(I_C_Flatrate_Conditions.class);
		conditions.setType_Conditions(X_C_Flatrate_Conditions.TYPE_CONDITIONS_Refund);
		saveRecord(conditions);

		schedule = newInstance(I_C_InvoiceSchedule.class);
		schedule.setInvoiceFrequency(X_C_InvoiceSchedule.INVOICEFREQUENCY_Monthly);
		schedule.setInvoiceDay(31);
		schedule.setInvoiceDistance(1);
		saveRecord(schedule);

		assignmentToRefundCandidateRepository = Mockito.mock(AssignmentToRefundCandidateRepository.class);
	}

	/** registered once the configs exist: its validation on save needs the record's id, like in the WebUI */
	private void registerInterceptor()
	{
		final RefundConfigRepository refundConfigRepository = new RefundConfigRepository(new InvoiceScheduleRepository());
		Services.get(IModelInterceptorRegistry.class).addModelInterceptor(new C_Flatrate_RefundConfig(
				refundConfigRepository,
				new RefundContractRepository(refundConfigRepository),
				Mockito.mock(RefundInvoiceCandidateInvalidator.class),
				assignmentToRefundCandidateRepository));
	}

	@Test
	void correctingTheCurrency_beforeARefundIsInvoiced_isSaved()
	{
		final I_C_Flatrate_RefundConfig config = createPerUnitConfig(0, chf);
		registerInterceptor();

		config.setC_Currency_ID(eur.getRepoId());
		saveRecord(config);

		assertThat(load(config.getC_Flatrate_RefundConfig_ID(), I_C_Flatrate_RefundConfig.class).getC_Currency_ID()).isEqualTo(eur.getRepoId());
	}

	@Test
	void correctingTheCurrency_afterARefundIsInvoiced_isRefused()
	{
		final I_C_Flatrate_RefundConfig config = createPerUnitConfig(0, chf);
		Mockito.doReturn(true).when(assignmentToRefundCandidateRepository).hasInvoicedRefund(RefundConfigId.ofRepoId(config.getC_Flatrate_RefundConfig_ID()));
		registerInterceptor();

		config.setC_Currency_ID(eur.getRepoId());
		assertRefusedWithCurrencyNotChangeable(config);
	}

	/** The amount of a line that has issued a refund may still be corrected: only its currency is fixed. */
	@Test
	void correctingTheAmount_afterARefundIsInvoiced_isSaved()
	{
		final I_C_Flatrate_RefundConfig config = createPerUnitConfig(0, chf);
		Mockito.doReturn(true).when(assignmentToRefundCandidateRepository).hasInvoicedRefund(RefundConfigId.ofRepoId(config.getC_Flatrate_RefundConfig_ID()));
		registerInterceptor();

		config.setRefundAmt(new BigDecimal("0.70"));
		saveRecord(config);
	}

	/**
	 * The per-unit lines of a condition refund in one currency, so correcting the currency of one line corrects the other lines too,
	 * instead of refusing the correction because the other lines are still in the old currency.
	 */
	@Test
	void correctingTheCurrency_correctsTheOtherPerUnitLinesOfTheCondition()
	{
		final I_C_Flatrate_RefundConfig fromZero = createPerUnitConfig(0, chf);
		final I_C_Flatrate_RefundConfig fromFive = createPerUnitConfig(5, chf);
		final I_C_Flatrate_RefundConfig fromTen = createPerUnitConfig(10, chf);
		registerInterceptor();

		fromZero.setC_Currency_ID(eur.getRepoId());
		saveRecord(fromZero);

		assertThat(load(fromFive.getC_Flatrate_RefundConfig_ID(), I_C_Flatrate_RefundConfig.class).getC_Currency_ID()).isEqualTo(eur.getRepoId());
		assertThat(load(fromTen.getC_Flatrate_RefundConfig_ID(), I_C_Flatrate_RefundConfig.class).getC_Currency_ID()).isEqualTo(eur.getRepoId());
	}

	/** If another line of the condition has issued a refund, then the correction would change that line's currency too, so it is refused. */
	@Test
	void correctingTheCurrency_otherLineHasInvoicedARefund_isRefused()
	{
		final I_C_Flatrate_RefundConfig fromZero = createPerUnitConfig(0, chf);
		final I_C_Flatrate_RefundConfig fromFive = createPerUnitConfig(5, chf);
		Mockito.doReturn(true).when(assignmentToRefundCandidateRepository).hasInvoicedRefund(RefundConfigId.ofRepoId(fromFive.getC_Flatrate_RefundConfig_ID()));
		registerInterceptor();

		fromZero.setC_Currency_ID(eur.getRepoId());
		assertRefusedWithCurrencyNotChangeable(fromZero);
		assertThat(load(fromFive.getC_Flatrate_RefundConfig_ID(), I_C_Flatrate_RefundConfig.class).getC_Currency_ID()).isEqualTo(chf.getRepoId());
	}

	/** A line in another currency than the condition's other per-unit lines is refused (validated like the existing lines are, see C_Flatrate_RefundConfig_Test). */
	@Test
	void lineInAnotherCurrency_isRefused()
	{
		createPerUnitConfig(0, chf);
		final I_C_Flatrate_RefundConfig eurLine = createPerUnitConfig(5, eur);
		final RefundConfigRepository refundConfigRepository = new RefundConfigRepository(new InvoiceScheduleRepository());
		final C_Flatrate_RefundConfig interceptor = new C_Flatrate_RefundConfig(
				refundConfigRepository,
				new RefundContractRepository(refundConfigRepository),
				Mockito.mock(RefundInvoiceCandidateInvalidator.class),
				assignmentToRefundCandidateRepository);

		assertThatThrownBy(() -> interceptor.assertValid(eurLine))
				.satisfies(ex -> assertThat(AdempiereException.extractErrorCodeOrNull(ex)).isEqualTo(RefundConfigs.MSG_REFUND_CONFIG_SAME_CURRENCY.toAD_Message()));
	}

	private void assertRefusedWithCurrencyNotChangeable(@NonNull final I_C_Flatrate_RefundConfig config)
	{
		assertThatThrownBy(() -> saveRecord(config))
				.satisfies(ex -> assertThat(AdempiereException.extractErrorCodeOrNull(ex)).isEqualTo(C_Flatrate_RefundConfig.MSG_REFUND_CONFIG_CURRENCY_NOT_CHANGEABLE.toAD_Message()));
	}

	private I_C_Flatrate_RefundConfig createPerUnitConfig(final int minQty, @NonNull final CurrencyId currencyId)
	{
		final I_C_Flatrate_RefundConfig config = newInstance(I_C_Flatrate_RefundConfig.class);
		config.setC_Flatrate_Conditions_ID(conditions.getC_Flatrate_Conditions_ID());
		config.setC_InvoiceSchedule_ID(schedule.getC_InvoiceSchedule_ID());
		config.setM_Product_ID(30);
		config.setRefundInvoiceType(X_C_Flatrate_RefundConfig.REFUNDINVOICETYPE_Invoice);
		config.setRefundBase(X_C_Flatrate_RefundConfig.REFUNDBASE_Amount);
		config.setRefundAmt(new BigDecimal("0.50"));
		config.setC_Currency_ID(currencyId.getRepoId());
		config.setRefundMode(X_C_Flatrate_RefundConfig.REFUNDMODE_Accumulated);
		config.setMinQty(BigDecimal.valueOf(minQty));
		saveRecord(config);
		return config;
	}
}
