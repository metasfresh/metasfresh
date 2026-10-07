package de.metas.order.compensationGroup.calibration;

import de.metas.order.model.I_C_CompensationGroup_CalibrationRule;
import org.adempiere.test.AdempiereTestHelper;
import de.metas.util.lang.Percent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

class CompensationGroupCalibrationRuleCacheTest
{
	private CompensationGroupCalibrationRuleRepository repository;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		repository = CompensationGroupCalibrationRuleRepository.newInstanceForUnitTesting();
	}

	@Test
	void getActiveRules_reflectsRuleChanges()
	{
		final I_C_CompensationGroup_CalibrationRule record = newInstance(I_C_CompensationGroup_CalibrationRule.class);
		record.setSeqNo(10);
		record.setC_BPartner_ID(1);
		record.setGroupCompensationCalibrationFactor(new BigDecimal("0.5"));
		saveRecord(record);
		assertThat(repository.getActiveRules().asList()).hasSize(1);
		assertThat(repository.getActiveRules().asList().get(0).getFactor()).isEqualTo(Percent.of(50));

		record.setGroupCompensationCalibrationFactor(new BigDecimal("0.8"));
		saveRecord(record);
		assertThat(repository.getActiveRules().asList().get(0).getFactor()).isEqualTo(Percent.of(80));

		record.setIsActive(false);
		saveRecord(record);
		assertThat(repository.getActiveRules().asList()).isEmpty();

		final I_C_CompensationGroup_CalibrationRule added = newInstance(I_C_CompensationGroup_CalibrationRule.class);
		added.setSeqNo(20);
		added.setC_BPartner_ID(2);
		added.setGroupCompensationCalibrationFactor(BigDecimal.ONE);
		saveRecord(added);
		assertThat(repository.getActiveRules().asList()).hasSize(1);
	}
}
