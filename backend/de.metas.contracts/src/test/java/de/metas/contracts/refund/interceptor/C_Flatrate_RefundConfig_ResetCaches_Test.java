package de.metas.contracts.refund.interceptor;

import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_RefundConfig;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.contracts.model.X_C_Flatrate_RefundConfig;
import de.metas.contracts.refund.RefundConfigRepository;
import de.metas.contracts.refund.RefundContractRepository;
import de.metas.contracts.refund.RefundInvoiceCandidateInvalidator;
import org.mockito.Mockito;
import de.metas.invoice.service.InvoiceScheduleRepository;
import de.metas.util.Services;
import lombok.Getter;
import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_InvoiceSchedule;
import org.compiere.model.I_C_UOM;
import org.compiere.model.X_C_InvoiceSchedule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * The refund contract caches (e.g. whether there is any contract deducted at payment) are reset once a config change that affects them is committed.
 * <p>
 * Note: the effect on the cache itself cannot be observed in unit test mode, because there every save resets the caches of the saved table at once
 * ({@code POJOLookupMap} calls {@code CacheMgt.reset(tableName, id)}); so these tests count the resets that the interceptor triggers instead.
 */
class C_Flatrate_RefundConfig_ResetCaches_Test
{
	private CountingRefundContractRepository refundContractRepository;
	private I_C_Flatrate_RefundConfig config;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
		saveRecord(newInstance(I_C_UOM.class));

		final RefundConfigRepository refundConfigRepository = new RefundConfigRepository(new InvoiceScheduleRepository());
		refundContractRepository = new CountingRefundContractRepository(refundConfigRepository);

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
		config.setM_Product_Category_ID(40);
		config.setBonus_Product_ID(41);
		config.setRefundInvoiceType(X_C_Flatrate_RefundConfig.REFUNDINVOICETYPE_Invoice);
		config.setRefundBase(X_C_Flatrate_RefundConfig.REFUNDBASE_Percentage);
		config.setRefundPercent(BigDecimal.TEN);
		config.setRefundMode(X_C_Flatrate_RefundConfig.REFUNDMODE_Accumulated);
		config.setMinQty(BigDecimal.ZERO);
		saveRecord(config);

		// registered once the config exists: its validation on save needs the record's id, like in the WebUI
		Services.get(IModelInterceptorRegistry.class).addModelInterceptor(new C_Flatrate_RefundConfig(refundConfigRepository, refundContractRepository, Mockito.mock(RefundInvoiceCandidateInvalidator.class)));
		refundContractRepository.resetCount();
	}

	/** switching a condition to deducted at payment: the cached "there is none" must not survive */
	@Test
	void flaggingDeductedAtPayment_resetsTheCachesOnCommit()
	{
		saveInTrx(() -> config.setIsDeductedAtPayment(true));

		assertThat(refundContractRepository.getResetCount()).isEqualTo(1);
	}

	/** activating a line: it counts again */
	@Test
	void activating_resetsTheCachesOnCommit()
	{
		config.setIsActive(false);
		saveRecord(config);
		refundContractRepository.resetCount();

		saveInTrx(() -> config.setIsActive(true));

		assertThat(refundContractRepository.getResetCount()).isEqualTo(1);
	}

	/** the control: a change that does not affect which contracts are deducted at payment does not reset them */
	@Test
	void changingThePercentage_doesNotResetTheCaches()
	{
		saveInTrx(() -> config.setRefundPercent(new BigDecimal("12")));

		assertThat(refundContractRepository.getResetCount()).isZero();
	}

	private void saveInTrx(final Runnable change)
	{
		Services.get(ITrxManager.class).runInNewTrx(() -> {
			change.run();
			saveRecord(config);
			assertThat(refundContractRepository.getResetCount()).as("not before the commit").isZero();
		});
	}

	private static class CountingRefundContractRepository extends RefundContractRepository
	{
		@Getter
		private int resetCount = 0;

		CountingRefundContractRepository(final RefundConfigRepository refundConfigRepository)
		{
			super(refundConfigRepository);
		}

		@Override
		public void resetCaches()
		{
			resetCount++;
			super.resetCaches();
		}

		void resetCount()
		{
			resetCount = 0;
		}
	}
}
