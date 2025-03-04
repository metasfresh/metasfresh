package de.metas.distribution.workflows_api;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.user.UserId;
import de.metas.util.collections.CollectionUtils;
import de.metas.workflow.rest_api.model.WFActivityStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.function.UnaryOperator;

@ToString
@Getter
public class DistributionJob
{
	@NonNull private final DistributionJobId id;
	@NonNull private final String documentNo;
	@NonNull private final BPartnerId customerId;
	@NonNull private final ZonedDateTime dateRequired;
	@NonNull private final WarehouseInfo pickFromWarehouse;
	@NonNull private final WarehouseInfo dropToWarehouse;
	@Nullable private final UserId responsibleId;
	private final boolean isClosed;
	@Nullable private final String salesOrderDocumentNo;
	@Nullable private final String ppOrderDocumentNo;
	private final boolean allowPickingAnyHU;
	@NonNull private final ImmutableList<DistributionJobLine> lines;

	@NonNull WFActivityStatus status;

	@Builder(toBuilder = true)
	private DistributionJob(
			final @NonNull DistributionJobId id,
			final @NonNull String documentNo,
			final @NonNull BPartnerId customerId,
			final @NonNull ZonedDateTime dateRequired,
			final @NonNull WarehouseInfo pickFromWarehouse,
			final @NonNull WarehouseInfo dropToWarehouse,
			final @Nullable UserId responsibleId,
			final boolean isClosed,
			final @Nullable String salesOrderDocumentNo,
			final @Nullable String ppOrderDocumentNo,
			final boolean allowPickingAnyHU,
			final @NonNull List<DistributionJobLine> lines)
	{
		this.id = id;
		this.documentNo = documentNo;
		this.customerId = customerId;
		this.dateRequired = dateRequired;
		this.pickFromWarehouse = pickFromWarehouse;
		this.dropToWarehouse = dropToWarehouse;
		this.responsibleId = responsibleId;
		this.isClosed = isClosed;
		this.salesOrderDocumentNo = salesOrderDocumentNo;
		this.ppOrderDocumentNo = ppOrderDocumentNo;
		this.allowPickingAnyHU = allowPickingAnyHU;
		this.lines = ImmutableList.copyOf(lines);

		this.status = WFActivityStatus.computeStatusFromLines(lines, DistributionJobLine::getStatus);
	}

	@NonNull
	public DDOrderId getDdOrderId() {return id.toDDOrderId();}

	public DistributionJob withNewStep(final DistributionJobLineId lineId, final DistributionJobStep step)
	{
		return withChangedLine(lineId, line -> line.withNewStep(step));
	}

	public DistributionJob withChangedStep(@NonNull final DistributionJobStepId id, @NonNull final UnaryOperator<DistributionJobStep> stepMapper)
	{
		return withChangedSteps(step -> DistributionJobStepId.equals(step.getId(), id) ? stepMapper.apply(step) : step);
	}

	public DistributionJob withChangedSteps(final UnaryOperator<DistributionJobStep> stepMapper)
	{
		return withChangedLines(line -> line.withChangedSteps(stepMapper));
	}

	public DistributionJob withChangedLines(final UnaryOperator<DistributionJobLine> lineMapper)
	{
		final ImmutableList<DistributionJobLine> changedLines = CollectionUtils.map(lines, lineMapper);
		return changedLines.equals(lines)
				? this
				: toBuilder().lines(changedLines).build();
	}

	public DistributionJob withChangedLine(final DistributionJobLineId lineId, final UnaryOperator<DistributionJobLine> lineMapper)
	{
		return withChangedLines(line -> DistributionJobLineId.equals(line.getId(), lineId) ? lineMapper.apply(line) : line);
	}

	public DistributionJobLine getLineById(@NonNull final DistributionJobLineId lineId)
	{
		return lines.stream()
				.filter(line -> DistributionJobLineId.equals(line.getId(), lineId))
				.findFirst()
				.orElseThrow(() -> new AdempiereException("No line found for " + lineId));
	}
}
