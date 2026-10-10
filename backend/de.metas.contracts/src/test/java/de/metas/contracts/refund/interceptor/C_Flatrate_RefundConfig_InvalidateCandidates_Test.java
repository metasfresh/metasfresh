package de.metas.contracts.refund.interceptor;

import de.metas.contracts.ConditionsId;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_RefundConfig;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.contracts.model.X_C_Flatrate_RefundConfig;
import de.metas.contracts.refund.RefundConfigRepository;
import de.metas.contracts.refund.RefundContractRepository;
import de.metas.contracts.refund.RefundInvoiceCandidateInvalidator;
import de.metas.currency.CurrencyCode;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.invoice.service.InvoiceScheduleRepository;
import de.metas.money.CurrencyId;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_InvoiceSchedule;
import org.compiere.model.I_C_UOM;
import org.compiere.model.X_C_InvoiceSchedule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.mockito.ArgumentMatchers.any;

/**
 * A changed per-unit refund amount (e.g. a corrected currency) must reach the invoice candidates: those of the completed contracts with these conditions are checked again.
 */
class C_Flatrate_RefundConfig_InvalidateCandidates_Test
{
	private RefundInvoiceCandidateInvalidator invalidator;
	private I_C_Flatrate_RefundConfig config;
	private CurrencyId eur;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
		saveRecord(newInstance(I_C_UOM.class));

		final CurrencyId chf = PlainCurrencyDAO.createCurrency(CurrencyCode.CHF).getId();
		eur = PlainCurrencyDAO.createCurrency(CurrencyCode.EUR).getId();

		final RefundConfigRepository refundConfigRepository = new RefundConfigRepository(new InvoiceScheduleRepository());
		invalidator = Mockito.mock(RefundInvoiceCandidateInvalidator.class);

		final I_C_Flatrate_Conditions conditions = newInstance(I_C_Flatrate_Conditions.class);
		conditions.setType_Conditions(X_C_Flatrate_Conditions.TYPE_CONDITIONS_Refund);
		saveRecord(conditions);

		final I_C_InvoiceSchedule schedule = newInstance(I_C_InvoiceSchedule.class);
		schedule.setInvoiceFrequency(X_C_InvoiceSchedule.INVOICEFREQUENCY_Monthly);
		schedule.setInvoiceDay(31);
		schedule.setInvoiceDistance(1);
		saveRecord(schedule);

		config = newInstance(I_C_Flatrate_RefundConfig.class);
		config.setC_Flatrate_Conditions_ID(conditions.getC_Flatrate_Conditions_ID());
		config.setC_InvoiceSchedule_ID(schedule.getC_InvoiceSchedule_ID());
		config.setM_Product_ID(30);
		config.setRefundInvoiceType(X_C_Flatrate_RefundConfig.REFUNDINVOICETYPE_Invoice);
		config.setRefundBase(X_C_Flatrate_RefundConfig.REFUNDBASE_Amount);
		config.setRefundAmt(new BigDecimal("0.50"));
		config.setC_Currency_ID(chf.getRepoId());
		config.setRefundMode(X_C_Flatrate_RefundConfig.REFUNDMODE_Accumulated);
		config.setMinQty(BigDecimal.ZERO);
		saveRecord(config);

		// registered once the config exists: its validation on save needs the record's id, like in the WebUI
		Services.get(IModelInterceptorRegistry.class).addModelInterceptor(new C_Flatrate_RefundConfig(refundConfigRepository, new RefundContractRepository(refundConfigRepository), invalidator));
	}

	@Test
	void correctingTheCurrency_invalidatesTheCandidatesOfTheConditions()
	{
		config.setC_Currency_ID(eur.getRepoId());
		saveRecord(config);

		Mockito.verify(invalidator).invalidateCandidatesOfConditionsAfterCommit(ConditionsId.ofRepoId(config.getC_Flatrate_Conditions_ID()));
	}

	@Test
	void changingTheAmount_invalidatesTheCandidatesOfTheConditions()
	{
		config.setRefundAmt(new BigDecimal("0.60"));
		saveRecord(config);

		Mockito.verify(invalidator).invalidateCandidatesOfConditionsAfterCommit(ConditionsId.ofRepoId(config.getC_Flatrate_Conditions_ID()));
	}

	/** deactivating the wrong line (after a corrected line was added) is a correction too */
	@Test
	void deactivating_invalidatesTheCandidatesOfTheConditions()
	{
		config.setIsActive(false);
		saveRecord(config);

		Mockito.verify(invalidator).invalidateCandidatesOfConditionsAfterCommit(ConditionsId.ofRepoId(config.getC_Flatrate_Conditions_ID()));
	}

	/** a corrected line that is added */
	@Test
	void addingALine_invalidatesTheCandidatesOfTheConditions()
	{
		final I_C_Flatrate_RefundConfig newConfig = newInstance(I_C_Flatrate_RefundConfig.class);
		newConfig.setC_Flatrate_Conditions_ID(config.getC_Flatrate_Conditions_ID());
		newConfig.setC_InvoiceSchedule_ID(config.getC_InvoiceSchedule_ID());
		newConfig.setM_Product_ID(31);
		newConfig.setRefundInvoiceType(X_C_Flatrate_RefundConfig.REFUNDINVOICETYPE_Invoice);
		newConfig.setRefundBase(X_C_Flatrate_RefundConfig.REFUNDBASE_Amount);
		newConfig.setRefundAmt(new BigDecimal("0.50"));
		newConfig.setC_Currency_ID(eur.getRepoId());
		newConfig.setRefundMode(X_C_Flatrate_RefundConfig.REFUNDMODE_Accumulated);
		newConfig.setMinQty(BigDecimal.ZERO);
		newConfig.setC_Flatrate_RefundConfig_ID(config.getC_Flatrate_RefundConfig_ID() + 1000); // the WebUI also has the id before the interceptors run
		saveRecord(newConfig);

		Mockito.verify(invalidator).invalidateCandidatesOfConditionsAfterCommit(ConditionsId.ofRepoId(config.getC_Flatrate_Conditions_ID()));
	}

	/** the control: a change that does not affect the refund amount */
	@Test
	void changingTheMinQty_doesNotInvalidate()
	{
		config.setMinQty(BigDecimal.ONE);
		saveRecord(config);

		Mockito.verify(invalidator, Mockito.never()).invalidateCandidatesOfConditionsAfterCommit(any());
	}
}
