package de.metas.frontend_testing.masterdata.compensation_group;

import de.metas.order.model.I_C_CompensationGroup_CalibrationRule;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.IQuery.Aggregate;

/**
 * Persistence for the masterdata API's {@code C_CompensationGroup_CalibrationRule} rows. Separate from
 * {@link CreateCalibrationRuleCommand} because persistence primitives ({@code InterfaceWrapperHelper.saveRecord})
 * belong in a {@code *Repository}/{@code *DAO} rather than in a command (docs/coding-rules/service-injection.md §4).
 * <p>
 * Repository Tables: C_CompensationGroup_CalibrationRule
 * Repository Cluster: CalibrationRuleMasterdataRepository, CompensationGroupCalibrationRuleRepository
 */
public class CalibrationRuleMasterdataRepository
{
	private static final int SEQNO_STEP = 10;

	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public void save(@NonNull final I_C_CompensationGroup_CalibrationRule rule)
	{
		InterfaceWrapperHelper.saveRecord(rule);
	}

	/**
	 * @return the highest existing {@code SeqNo} plus 10 (10 when there is no rule yet)
	 */
	public int nextSeqNo()
	{
		final Integer max = queryBL.createQueryBuilder(I_C_CompensationGroup_CalibrationRule.class)
				.create()
				.aggregate(I_C_CompensationGroup_CalibrationRule.COLUMNNAME_SeqNo, Aggregate.MAX, Integer.class);
		return (max == null ? 0 : max) + SEQNO_STEP;
	}
}
