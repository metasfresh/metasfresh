package de.metas.manufacturing.job.model;

import com.google.common.collect.ImmutableMap;
import de.metas.util.collections.CollectionUtils;
import de.metas.workflow.rest_api.model.WFActivityStatus;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

@Value
public class FinishedGoodsReceive
{
	@NonNull ImmutableMap<FinishedGoodsReceiveLineId, FinishedGoodsReceiveLine> linesById;

	@NonNull WFActivityStatus status;

	@Builder(toBuilder = true)
	private FinishedGoodsReceive(@NonNull final ImmutableMap<FinishedGoodsReceiveLineId, FinishedGoodsReceiveLine> linesById)
	{
		this.linesById = linesById;
		this.status = WFActivityStatus.computeStatusFromLines(linesById.values(), FinishedGoodsReceiveLine::getStatus);
	}

	public Stream<FinishedGoodsReceiveLine> streamLines() {return linesById.values().stream();}

	public FinishedGoodsReceiveLine getLineById(final FinishedGoodsReceiveLineId lineId)
	{
		final FinishedGoodsReceiveLine line = getLineByIdOrNull(lineId);
		if (line == null)
		{
			throw new AdempiereException("Line " + lineId + " was not found in " + this);
		}
		return line;
	}

	@Nullable
	public FinishedGoodsReceiveLine getLineByIdOrNull(final FinishedGoodsReceiveLineId lineId)
	{
		return linesById.get(lineId);
	}

	public FinishedGoodsReceive withChangedReceiveLine(final FinishedGoodsReceiveLineId id, final UnaryOperator<FinishedGoodsReceiveLine> mapper)
	{
		return withLinesById(CollectionUtils.mapValue(this.linesById, id, mapper));
	}

	private FinishedGoodsReceive withLinesById(final ImmutableMap<FinishedGoodsReceiveLineId, FinishedGoodsReceiveLine> linesById)
	{
		return Objects.equals(this.linesById, linesById) ? this : toBuilder().linesById(linesById).build();
	}
}
