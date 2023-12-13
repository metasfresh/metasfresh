package de.metas.workflow.execution.approval.strategy;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ListMultimap;
import de.metas.cache.CCache;
import de.metas.workflow.execution.approval.strategy.check_superior_strategy.CheckSupervisorStrategyType;
import de.metas.workflow.execution.approval.strategy.type_handlers.DocApprovalStrategyType;
import de.metas.job.JobId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.util.GuavaCollectors;
import de.metas.util.OptionalBoolean;
import de.metas.util.Services;
import de.metas.util.lang.SeqNo;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Doc_Approval_Strategy;
import org.compiere.model.I_C_Doc_Approval_Strategy_Line;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collector;

@Repository
public class DocApprovalStrategyRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, DocApprovalStrategyMap> cache = CCache.<Integer, DocApprovalStrategyMap>builder()
			.tableName(I_C_Doc_Approval_Strategy.Table_Name)
			.additionalTableNameToResetFor(I_C_Doc_Approval_Strategy_Line.Table_Name)
			.initialCapacity(1)
			.build();

	private static final DocApprovalStrategy DEFAULT = DocApprovalStrategy.builder()
			.id(DocApprovalStrategyId.DEFAULT_ID)
			.name("Default")
			.line(DocApprovalStrategyLine.builder().
					seqNo(SeqNo.ofInt(10))
					.type(DocApprovalStrategyType.WorkflowResponsible)
					.checkSupervisorStrategyType(CheckSupervisorStrategyType.FirstMatching).build())
			.build();

	public DocApprovalStrategy getById(final DocApprovalStrategyId id)
	{
		return getMap().getById(id);
	}

	private DocApprovalStrategyMap getMap() {return cache.getOrLoad(0, this::retrieveMap);}

	private DocApprovalStrategyMap retrieveMap()
	{
		final ListMultimap<DocApprovalStrategyId, I_C_Doc_Approval_Strategy_Line> lineRecords = queryBL.createQueryBuilder(I_C_Doc_Approval_Strategy_Line.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.listMultimap(lineRecord -> DocApprovalStrategyId.ofRepoId(lineRecord.getC_Doc_Approval_Strategy_ID()));

		return queryBL.createQueryBuilder(I_C_Doc_Approval_Strategy.class)
				.addOnlyActiveRecordsFilter()
				.stream()
				.map(headerRecord -> fromRecord(headerRecord, lineRecords))
				.collect(DocApprovalStrategyMap.collect());
	}

	private static DocApprovalStrategy fromRecord(
			@NonNull final I_C_Doc_Approval_Strategy headerRecord,
			@NonNull final ListMultimap<DocApprovalStrategyId, I_C_Doc_Approval_Strategy_Line> lineRecordsMap)
	{
		final DocApprovalStrategyId docApprovalStrategyId = DocApprovalStrategyId.ofRepoId(headerRecord.getC_Doc_Approval_Strategy_ID());

		return DocApprovalStrategy.builder()
				.id(docApprovalStrategyId)
				.name(headerRecord.getName())
				.lines(lineRecordsMap.get(docApprovalStrategyId)
						.stream()
						.map(DocApprovalStrategyRepository::fromRecord)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	private static DocApprovalStrategyLine fromRecord(@NonNull final I_C_Doc_Approval_Strategy_Line record)
	{
		final CurrencyId currencyId = CurrencyId.ofRepoIdOrNull(record.getC_Currency_ID());
		final Money minimumAmountThatRequiresApproval = currencyId != null ? Money.of(record.getMinimumAmt(), currencyId) : null;

		return DocApprovalStrategyLine.builder()
				.seqNo(SeqNo.ofInt(record.getSeqNo()))
				//
				.type(DocApprovalStrategyType.ofCode(record.getType()))
				.jobId(JobId.ofRepoIdOrNull(record.getC_Job_ID()))
				.checkSupervisorStrategyType(CheckSupervisorStrategyType.optionalOfNullableCode(record.getSupervisorCheckStrategy()).orElse(CheckSupervisorStrategyType.DoNotCheck))
				//
				// Condition
				.isProjectManagerSet(OptionalBoolean.ofNullableString(record.getIsProjectManagerSet()))
				.minimumAmountThatRequiresApproval(minimumAmountThatRequiresApproval)
				//
				.build();
	}

	private static final class DocApprovalStrategyMap
	{
		private final ImmutableMap<DocApprovalStrategyId, DocApprovalStrategy> byId;

		private DocApprovalStrategyMap(final List<DocApprovalStrategy> list)
		{
			final HashMap<DocApprovalStrategyId, DocApprovalStrategy> byId = new HashMap<>();
			byId.put(DEFAULT.getId(), DEFAULT);
			list.forEach(item -> byId.put(item.getId(), item));

			this.byId = ImmutableMap.copyOf(byId);
		}

		public static Collector<DocApprovalStrategy, ?, DocApprovalStrategyMap> collect()
		{
			return GuavaCollectors.collectUsingListAccumulator(DocApprovalStrategyMap::new);
		}

		public DocApprovalStrategy getById(@NonNull final DocApprovalStrategyId id)
		{
			final DocApprovalStrategy strategy = byId.get(id);
			if (strategy == null)
			{
				throw new AdempiereException("No strategy found for " + id);
			}
			return strategy;
		}
	}
}
