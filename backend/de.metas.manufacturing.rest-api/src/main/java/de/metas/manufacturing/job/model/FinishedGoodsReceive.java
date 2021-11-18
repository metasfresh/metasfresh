package de.metas.manufacturing.job.model;

import com.google.common.collect.ImmutableList;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

import java.util.function.UnaryOperator;

@Value
@Builder
public class FinishedGoodsReceive
{
	@With
	@NonNull ImmutableList<FinishedGoodsReceiveLine> lines;

	public FinishedGoodsReceive withChangedReceiveLine(final FinishedGoodsReceiveLineId id, final UnaryOperator<FinishedGoodsReceiveLine> mapper)
	{
		final ImmutableList<FinishedGoodsReceiveLine> linesNew = CollectionUtils.map(
				lines,
				line -> FinishedGoodsReceiveLineId.equals(line.getId(), id) ? mapper.apply(line) : line);
		return withLines(linesNew);
	}
}
