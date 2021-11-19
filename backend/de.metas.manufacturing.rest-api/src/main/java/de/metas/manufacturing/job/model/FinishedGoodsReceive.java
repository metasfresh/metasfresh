package de.metas.manufacturing.job.model;

import com.google.common.collect.ImmutableMap;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;
import org.adempiere.exceptions.AdempiereException;

import java.util.function.UnaryOperator;
import java.util.stream.Stream;

@Value
public class FinishedGoodsReceive
{
	@With
	@NonNull ImmutableMap<FinishedGoodsReceiveLineId, FinishedGoodsReceiveLine> linesById;

	@Builder
	public FinishedGoodsReceive(@NonNull final ImmutableMap<FinishedGoodsReceiveLineId, FinishedGoodsReceiveLine> linesById)
	{
		this.linesById = linesById;
	}

	public Stream<FinishedGoodsReceiveLine> streamLines() {return linesById.values().stream();}

	public FinishedGoodsReceiveLine getLineById(final FinishedGoodsReceiveLineId lineId)
	{
		final FinishedGoodsReceiveLine line = linesById.get(lineId);
		if (line == null)
		{
			throw new AdempiereException("Line " + lineId + " was not found in " + this);
		}
		return line;
	}

	public FinishedGoodsReceive withChangedReceiveLine(final FinishedGoodsReceiveLineId id, final UnaryOperator<FinishedGoodsReceiveLine> mapper)
	{
		return withLinesById(CollectionUtils.mapValue(this.linesById, id, mapper));
	}
}
