package de.metas.order.compensationGroup.calibration;

import de.metas.order.model.I_C_CompensationGroup_CalibrationRule;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

class CompensationGroupCalibrationRuleRepositoryTest
{
	private CompensationGroupCalibrationRuleRepository repository;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		repository = CompensationGroupCalibrationRuleRepository.newInstanceForUnitTesting();
	}

	@Test
	void getActiveRules_skipsInactiveAndSortsBySeqNoThenId()
	{
		final int second = createRule(20, true);
		final int firstOfTie1 = createRule(10, true);
		final int firstOfTie2 = createRule(10, true);
		createRule(5, false);

		final List<Integer> ids = repository.getActiveRules().asList().stream()
				.map(rule -> rule.getId().getRepoId())
				.collect(Collectors.toList());

		assertThat(ids).containsExactly(firstOfTie1, firstOfTie2, second);
	}

	private static int createRule(final int seqNo, final boolean active)
	{
		final I_C_CompensationGroup_CalibrationRule record = newInstance(I_C_CompensationGroup_CalibrationRule.class);
		record.setSeqNo(seqNo);
		record.setIsActive(active);
		record.setC_BPartner_ID(1);
		record.setGroupCompensationCalibrationFactor(new BigDecimal("100"));
		saveRecord(record);
		return record.getC_CompensationGroup_CalibrationRule_ID();
	}
}
