package de.metas.order.compensationGroup.calibration;

import com.google.common.annotations.VisibleForTesting;
import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerId;
import de.metas.cache.CCache;
import de.metas.order.compensationGroup.GroupTemplateId;
import de.metas.order.model.I_C_CompensationGroup_CalibrationRule;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Repository Tables: C_CompensationGroup_CalibrationRule
 */
@Repository
public class CompensationGroupCalibrationRuleRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, CalibrationRules> activeRulesCache = CCache.<Integer, CalibrationRules>builder()
			.tableName(I_C_CompensationGroup_CalibrationRule.Table_Name)
			.initialCapacity(1)
			.build();

	@VisibleForTesting
	public static CompensationGroupCalibrationRuleRepository newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		//noinspection DataFlowIssue
		return SpringContextHolder.getBeanOrSupply(CompensationGroupCalibrationRuleRepository.class, CompensationGroupCalibrationRuleRepository::new);
	}

	public CalibrationRules getActiveRules()
	{
		return activeRulesCache.getOrLoad(0, this::retrieveActiveRules);
	}

	private CalibrationRules retrieveActiveRules()
	{
		final List<CalibrationRule> rules = queryBL.createQueryBuilder(I_C_CompensationGroup_CalibrationRule.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(CompensationGroupCalibrationRuleRepository::fromRecord)
				.collect(Collectors.toList());
		return new CalibrationRules(rules);
	}

	@NonNull
	private static CalibrationRule fromRecord(@NonNull final I_C_CompensationGroup_CalibrationRule record)
	{
		return CalibrationRule.builder()
				.id(CalibrationRuleId.ofRepoId(record.getC_CompensationGroup_CalibrationRule_ID()))
				.seqNo(record.getSeqNo())
				.bpartnerId(BPartnerId.ofRepoIdOrNull(record.getC_BPartner_ID()))
				.bpGroupId(BPGroupId.ofRepoIdOrNull(record.getC_BP_Group_ID()))
				.productId(ProductId.ofRepoIdOrNull(record.getM_Product_ID()))
				.productCategoryId(ProductCategoryId.ofRepoIdOrNull(record.getM_Product_Category_ID()))
				.schemaId(GroupTemplateId.ofRepoIdOrNull(record.getC_CompensationGroup_Schema_ID()))
				.factor(record.getGroupCompensationCalibrationFactor())
				.build();
	}
}
