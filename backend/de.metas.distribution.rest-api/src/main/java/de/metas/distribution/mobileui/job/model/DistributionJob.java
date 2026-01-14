package de.metas.distribution.mobileui.job.model;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.mobileui.external_services.sourcedoc.ManufacturingOrderRef;
import de.metas.distribution.mobileui.external_services.sourcedoc.PlantInfo;
import de.metas.distribution.mobileui.external_services.sourcedoc.SalesOrderRef;
import de.metas.distribution.mobileui.external_services.warehouse.LocatorInfo;
import de.metas.distribution.mobileui.external_services.warehouse.WarehouseInfo;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.product.ProductId;
import de.metas.quantity.MixedQuantity;
import de.metas.quantity.Quantity;
import de.metas.user.UserId;
import de.metas.util.GuavaCollectors;
import de.metas.util.collections.CollectionUtils;
import de.metas.util.lang.SeqNo;
import de.metas.workflow.rest_api.model.WFActivityStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.LocatorId;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

@ToString
@Getter
public class DistributionJob
{
	@NonNull private final DistributionJobId id;
	@NonNull private final String documentNo;
	@NonNull private final SeqNo seqNo;
	@NonNull private final BPartnerId customerId;
	@NonNull private final ZonedDateTime dateRequired;
	@NonNull private final ZonedDateTime pickDate;
	@NonNull private final WarehouseInfo pickFromWarehouse;
	@NonNull private final WarehouseInfo dropToWarehouse;
	@Nullable private final PlantInfo plantInfo;
	@NonNull private final String priority;
	@Nullable private final UserId responsibleId;
	private final boolean isClosed;
	@Nullable private final SalesOrderRef salesOrderRef;
	@Nullable private final ManufacturingOrderRef manufacturingOrderRef;
	@NonNull private final ITranslatableString pickingInstruction;
	private final boolean allowPickingAnyHU;
	@NonNull private final ImmutableList<DistributionJobLine> lines;

	@NonNull WFActivityStatus status;

	@Builder(toBuilder = true)
	private DistributionJob(
			@NonNull final DistributionJobId id,
			@NonNull final String documentNo,
			@NonNull final SeqNo seqNo,
			@NonNull final BPartnerId customerId,
			@NonNull final ZonedDateTime dateRequired,
			@NonNull final ZonedDateTime pickDate,
			@NonNull final WarehouseInfo pickFromWarehouse,
			@NonNull final WarehouseInfo dropToWarehouse,
			@Nullable final PlantInfo plantInfo,
			@NonNull final String priority,
			@Nullable final UserId responsibleId,
			final boolean isClosed,
			@Nullable final SalesOrderRef salesOrderRef,
			@Nullable final ManufacturingOrderRef manufacturingOrderRef,
			@Nullable final ITranslatableString pickingInstruction,
			final boolean allowPickingAnyHU,
			@NonNull final List<DistributionJobLine> lines)
	{
		this.id = id;
		this.documentNo = documentNo;
		this.seqNo = seqNo;
		this.customerId = customerId;
		this.dateRequired = dateRequired;
		this.pickDate = pickDate;
		this.pickFromWarehouse = pickFromWarehouse;
		this.dropToWarehouse = dropToWarehouse;
		this.plantInfo = plantInfo;
		this.priority = priority;
		this.responsibleId = responsibleId;
		this.isClosed = isClosed;
		this.salesOrderRef = salesOrderRef;
		this.manufacturingOrderRef = manufacturingOrderRef;
		this.pickingInstruction = pickingInstruction != null ? pickingInstruction : TranslatableStrings.empty();
		this.allowPickingAnyHU = allowPickingAnyHU;
		this.lines = ImmutableList.copyOf(lines);

		this.status = WFActivityStatus.computeStatusFromLines(lines, DistributionJobLine::getStatus);
	}

	public void assertCanEdit(final UserId userId)
	{
		if (!UserId.equals(this.responsibleId, userId))
		{
			throw new AdempiereException("Cannot edit " + this + " because it is not assigned to " + userId);
		}
	}

	@NonNull
	public DDOrderId getDdOrderId() {return id.toDDOrderId();}

	public boolean isJobAssigned() {return responsibleId != null;}

	public DistributionJob withNewStep(final DistributionJobLineId lineId, final DistributionJobStep step)
	{
		return withChangedLine(lineId, line -> line.withNewStep(step));
	}

	public DistributionJob withChangedStep(@NonNull final DistributionJobStepId id, @NonNull final DistributionJobStep changedStep)
	{
		return withChangedStep(id, ignored -> changedStep);
	}

	public DistributionJob removeStep(@NonNull final DistributionJobStepId id)
	{
		return withChangedLines(line -> line.removeStep(id));
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

	@NonNull
	public DistributionJobLine getLineByStepId(@NonNull final DistributionJobStepId stepId)
	{
		return lines.stream()
				.filter(line -> line.getStepById(stepId).isPresent())
				.findFirst()
				.orElseThrow(() -> new AdempiereException("No line found for " + stepId));
	}

	@Nullable
	public String getPlantName()
	{
		return plantInfo != null ? plantInfo.getCaption() : null;
	}

	public Stream<DistributionJobStep> streamSteps()
	{
		return lines.stream().flatMap(line -> line.getSteps().stream());
	}

	public boolean isFullyMoved()
	{
		return lines.stream().allMatch(DistributionJobLine::isFullyMoved);
	}

	public boolean isInTransit()
	{
		return lines.stream().anyMatch(DistributionJobLine::isInTransit);
	}

	@Nullable
	public LocatorId getSinglePickFromLocatorIdOrNull()
	{
		return lines.stream()
				.map(DistributionJobLine::getPickFromLocatorId)
				.distinct()
				.collect(GuavaCollectors.singleElementOrNull());
	}

	public Optional<LocatorInfo> getSinglePickFromLocator()
	{
		return lines.stream()
				.map(DistributionJobLine::getPickFromLocator)
				.distinct()
				.collect(GuavaCollectors.singleElementOrEmpty());
	}

	@Nullable
	public LocatorId getSingleDropToLocatorIdOrNull()
	{
		return lines.stream()
				.map(DistributionJobLine::getDropToLocatorId)
				.distinct()
				.collect(GuavaCollectors.singleElementOrNull());
	}

	public Optional<LocatorInfo> getSingleDropToLocator()
	{
		return lines.stream()
				.map(DistributionJobLine::getDropToLocator)
				.distinct()
				.collect(GuavaCollectors.singleElementOrEmpty());
	}

	@Nullable
	public ProductId getSingleProductIdOrNull()
	{
		return lines.stream()
				.map(DistributionJobLine::getProductId)
				.distinct()
				.collect(GuavaCollectors.singleElementOrNull());
	}

	@Nullable
	public Quantity getSingleUnitQuantityOrNull()
	{
		final MixedQuantity qty = lines.stream()
				.map(DistributionJobLine::getQtyToMove)
				.distinct()
				.collect(MixedQuantity.collectAndSum());

		return qty.toNoneOrSingleValue().orElse(null);
	}

	public Optional<DistributionJobLineId> getNextEligiblePickFromLineId(@NonNull final ProductId productId)
	{
		return lines.stream()
				.filter(line -> ProductId.equals(line.getProductId(), productId))
				.filter(DistributionJobLine::isEligibleForPicking)
				.map(DistributionJobLine::getId)
				.findFirst();

	}
}
