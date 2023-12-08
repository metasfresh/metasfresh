package de.metas.document.approval_strategy;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Maps;
import de.metas.cache.CCache;
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

	private static DocApprovalStrategyLine fromRecord(@NonNull final I_C_Doc_Approval_Strategy_Line lineRecord)
	{
		final CurrencyId currencyId = CurrencyId.ofRepoIdOrNull(lineRecord.getC_Currency_ID());
		final Money minimumAmountThatRequiresApproval = currencyId != null ? Money.of(lineRecord.getMinimumAmt(), currencyId) : null;

		return DocApprovalStrategyLine.builder()
				.seqNo(SeqNo.ofInt(lineRecord.getSeqNo()))
				.type(DocApprovalStrategyLineType.ofCode(lineRecord.getType()))
				.isProjectManagerSet(OptionalBoolean.ofNullableString(lineRecord.getIsProjectManagerSet()))
				.jobId(JobId.ofRepoIdOrNull(lineRecord.getC_Job_ID()))
				.minimumAmountThatRequiresApproval(minimumAmountThatRequiresApproval)
				.build();
	}

	private static final class DocApprovalStrategyMap
	{
		private final ImmutableMap<DocApprovalStrategyId, DocApprovalStrategy> byId;

		private DocApprovalStrategyMap(final List<DocApprovalStrategy> list)
		{
			this.byId = Maps.uniqueIndex(list, DocApprovalStrategy::getId);
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
