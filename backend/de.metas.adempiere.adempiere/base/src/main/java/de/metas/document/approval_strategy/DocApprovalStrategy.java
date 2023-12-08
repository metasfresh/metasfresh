package de.metas.document.approval_strategy;

import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.Collection;
import java.util.Comparator;

@Value
public class DocApprovalStrategy
{
	@NonNull DocApprovalStrategyId id;
	@NonNull String name;
	@NonNull ImmutableList<DocApprovalStrategyLine> linesInOrder;

	@Builder
	private DocApprovalStrategy(
			@NonNull final DocApprovalStrategyId id,
			@NonNull final String name,
			@NonNull final Collection<DocApprovalStrategyLine> lines)
	{
		this.id = id;
		this.name = name;
		this.linesInOrder = lines.stream()
				.sorted(Comparator.comparing(DocApprovalStrategyLine::getSeqNo))
				.collect(ImmutableList.toImmutableList());
	}
}
