package de.metas.distribution.mobileui.job.model;

import com.google.common.collect.ImmutableList;
import de.metas.distribution.ddorder.DDOrderLineId;
import de.metas.distribution.mobileui.external_services.product.ProductInfo;
import de.metas.distribution.mobileui.external_services.warehouse.LocatorInfo;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.collections.CollectionUtils;
import de.metas.workflow.rest_api.model.WFActivityStatus;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.LocatorId;
import org.compiere.model.I_C_UOM;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.UnaryOperator;

@Value
public class DistributionJobLine
{
	@NonNull DistributionJobLineId id;

	@NonNull ProductInfo product;
	@NonNull Quantity qtyToMove;

	@NonNull LocatorInfo pickFromLocator;
	@NonNull LocatorInfo dropToLocator;

	@NonNull ImmutableList<DistributionJobStep> steps;

	@NonNull WFActivityStatus status;

	@Builder(toBuilder = true)
	private DistributionJobLine(
			@NonNull final DistributionJobLineId id,
			@NonNull final ProductInfo product,
			@NonNull final Quantity qtyToMove,
			@NonNull final LocatorInfo pickFromLocator,
			@NonNull final LocatorInfo dropToLocator,
			@NonNull final ImmutableList<DistributionJobStep> steps)
	{
		this.id = id;
		this.product = product;
		this.qtyToMove = qtyToMove;
		this.pickFromLocator = pickFromLocator;
		this.dropToLocator = dropToLocator;
		this.steps = steps;

		status = computeStatusFromSteps(steps);
	}

	public DDOrderLineId getDDOrderLineId() {return id.toDDOrderLineId();}

	public ProductId getProductId() {return product.getProductId();}

	public LocatorId getPickFromLocatorId() {return pickFromLocator.getLocatorId();}

	public LocatorId getDropToLocatorId() {return dropToLocator.getLocatorId();}

	public I_C_UOM getUOM() {return qtyToMove.getUOM();}

	public boolean isInTransit()
	{
		return !steps.isEmpty() && steps.stream().anyMatch(DistributionJobStep::isInTransit);
	}

	public boolean isEligibleForPicking() {return getQtyInTransit().isLessThan(qtyToMove);}

	private Quantity getQtyInTransit()
	{
		return steps.stream()
				.map(DistributionJobStep::getQtyInTransit)
				.reduce(Quantity::add)
				.orElseGet(qtyToMove::toZero);
	}

	public boolean isFullyMoved()
	{
		return !steps.isEmpty() && steps.stream().allMatch(DistributionJobStep::isDroppedToLocator);
	}

	/**
	 * Quantity actually moved: the picked quantity of the steps that were dropped at the destination locator.
	 * Quantity still in transit does not count as moved.
	 */
	public Quantity getQtyMoved()
	{
		return steps.stream()
				.filter(DistributionJobStep::isDroppedToLocator)
				.map(DistributionJobStep::getQtyPicked)
				.reduce(Quantity::add)
				.orElseGet(qtyToMove::toZero);
	}

	/**
	 * {@code true} when the line's whole planned {@link #qtyToMove} has been moved.
	 *
	 * <p>Not to be confused with {@link #isFullyMoved()}, which only asserts that every step that <i>exists</i> was
	 * dropped: steps are created when the mover picks, so picking and dropping 6 of a planned 15 satisfies
	 * {@code isFullyMoved()} but not this predicate. Use this one to decide whether the demand behind the line is
	 * served; {@code isFullyMoved()} stays the drop-all path's auto-complete trigger.</p>
	 */
	public boolean isPlannedQtyFullyMoved()
	{
		return getQtyMoved().compareTo(qtyToMove) >= 0;
	}

	/** The planned quantity that was not moved. Zero or negative once {@link #isPlannedQtyFullyMoved()} holds. */
	public Quantity getQtyOutstanding()
	{
		return qtyToMove.subtract(getQtyMoved());
	}

	/**
	 * What is still outstanding on this line, as "&lt;qty&gt; &lt;uom&gt; &lt;product&gt;", in the reader's language.
	 * Rendered on demand so the product caption is translated at render time, not in the base language.
	 */
	public ITranslatableString describeQtyOutstanding()
	{
		final Quantity qtyOutstanding = getQtyOutstanding();
		return TranslatableStrings.builder()
				.appendQty(qtyOutstanding.toBigDecimal(), qtyOutstanding.getUOMSymbol())
				.append(" ")
				.append(product.getCaption())
				.build();
	}

	private static WFActivityStatus computeStatusFromSteps(final @NonNull List<DistributionJobStep> steps)
	{
		return steps.isEmpty()
				? WFActivityStatus.NOT_STARTED
				: WFActivityStatus.computeStatusFromLines(steps, DistributionJobStep::getStatus);
	}

	public DDOrderLineId getDdOrderLineId() {return id.toDDOrderLineId();}

	public DistributionJobLine withNewStep(final DistributionJobStep stepToAdd)
	{
		final ArrayList<DistributionJobStep> changedSteps = new ArrayList<>(this.steps);
		boolean added = false;
		boolean changed = false;

		for (final DistributionJobStep step : steps)
		{
			if (DistributionJobStepId.equals(step.getId(), stepToAdd.getId()))
			{
				changedSteps.add(stepToAdd);
				added = true;

				if (!Objects.equals(step, stepToAdd))
				{
					changed = true;
				}
			}
			else
			{
				changedSteps.add(step);
			}
		}

		if (!added)
		{
			changedSteps.add(stepToAdd);
			changed = true;
		}

		return changed
				? toBuilder().steps(ImmutableList.copyOf(changedSteps)).build()
				: this;
	}

	public DistributionJobLine withChangedSteps(@NonNull final UnaryOperator<DistributionJobStep> stepMapper)
	{
		final ImmutableList<DistributionJobStep> changedSteps = CollectionUtils.map(steps, stepMapper);
		return changedSteps.equals(steps)
				? this
				: toBuilder().steps(changedSteps).build();
	}

	public DistributionJobLine removeStep(@NonNull final DistributionJobStepId stepId)
	{
		final ImmutableList<DistributionJobStep> updatedStepCollection = steps.stream()
				.filter(step -> !step.getId().equals(stepId))
				.collect(ImmutableList.toImmutableList());

		return updatedStepCollection.equals(steps)
				? this
				: toBuilder().steps(updatedStepCollection).build();
	}

	@NonNull
	public Optional<DistributionJobStep> getStepById(@NonNull final DistributionJobStepId stepId)
	{
		return getSteps().stream().filter(step -> step.getId().equals(stepId)).findFirst();
	}
}
