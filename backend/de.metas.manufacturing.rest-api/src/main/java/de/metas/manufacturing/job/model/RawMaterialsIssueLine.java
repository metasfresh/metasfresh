package de.metas.manufacturing.job.model;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.pporder.api.issue_schedule.PPOrderIssueSchedule;
import de.metas.handlingunits.pporder.api.issue_schedule.PPOrderIssueScheduleId;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStringBuilder;
import de.metas.i18n.TranslatableStrings;
import de.metas.product.IssuingToleranceSpec;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.collections.CollectionUtils;
import de.metas.workflow.rest_api.model.WFActivityStatus;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;
import java.util.function.UnaryOperator;

@Value
public class RawMaterialsIssueLine
{
	@NonNull ProductId productId;
	@NonNull ITranslatableString productName;
	@NonNull String productValue;
	boolean isWeightable;
	@NonNull Quantity qtyToIssue;
	@Nullable IssuingToleranceSpec issuingToleranceSpec;
	@NonNull ImmutableList<RawMaterialsIssueStep> steps;
	@Nullable String userInstructions;

	@NonNull Quantity qtyIssued; // computed
	@NonNull WFActivityStatus status;
	int seqNo;

	@Builder(toBuilder = true)
	private RawMaterialsIssueLine(
			@NonNull final ProductId productId,
			@NonNull final ITranslatableString productName,
			@NonNull final String productValue,
			final boolean isWeightable,
			@NonNull final Quantity qtyToIssue,
			@Nullable final IssuingToleranceSpec issuingToleranceSpec,
			@NonNull final ImmutableList<RawMaterialsIssueStep> steps,
			@Nullable final String userInstructions,
			final int seqNo)
	{
		this.productId = productId;
		this.productName = productName;
		this.productValue = productValue;
		this.isWeightable = isWeightable;
		this.qtyToIssue = qtyToIssue;
		this.issuingToleranceSpec = issuingToleranceSpec;
		this.steps = steps;
		this.userInstructions = userInstructions;

		this.qtyIssued = computeQtyIssued(this.steps).orElseGet(qtyToIssue::toZero);
		this.seqNo = seqNo;
		this.status = computeStatus(this.qtyToIssue, this.qtyIssued, this.steps);
	}

	private static Optional<Quantity> computeQtyIssued(final @NonNull ImmutableList<RawMaterialsIssueStep> steps)
	{
		return steps.stream()
				.map(RawMaterialsIssueStep::getIssued)
				.filter(Objects::nonNull)
				.map(PPOrderIssueSchedule.Issued::getQtyIssued)
				.reduce(Quantity::add);
	}

	private static WFActivityStatus computeStatus(
			final @NonNull Quantity qtyToIssue,
			final @NonNull Quantity qtyIssued,
			final @NonNull ImmutableList<RawMaterialsIssueStep> steps)
	{
		if (qtyIssued.isZero())
		{
			return WFActivityStatus.NOT_STARTED;
		}
		else if (qtyToIssue.compareTo(qtyIssued) <= 0
				|| steps.stream().allMatch(RawMaterialsIssueStep::isIssued))
		{
			return WFActivityStatus.COMPLETED;
		}
		else
		{
			return WFActivityStatus.IN_PROGRESS;
		}
	}

	public Optional<Quantity> getQtyToIssueMin()
	{
		return issuingToleranceSpec != null
				? Optional.of(issuingToleranceSpec.subtractFrom(qtyToIssue))
				: Optional.empty();
	}

	public Optional<Quantity> getQtyToIssueMax()
	{
		return issuingToleranceSpec != null
				? Optional.of(issuingToleranceSpec.addTo(qtyToIssue))
				: Optional.empty();
	}

	public RawMaterialsIssueLine withChangedRawMaterialsIssueStep(
			@NonNull final PPOrderIssueScheduleId issueScheduleId,
			@NonNull UnaryOperator<RawMaterialsIssueStep> mapper)
	{
		final ImmutableList<RawMaterialsIssueStep> stepsNew = CollectionUtils.map(
				steps,
				step -> PPOrderIssueScheduleId.equals(step.getId(), issueScheduleId) ? mapper.apply(step) : step);

		return withSteps(stepsNew);
	}

	private RawMaterialsIssueLine withSteps(final ImmutableList<RawMaterialsIssueStep> stepsNew)
	{
		return !Objects.equals(this.steps, stepsNew)
				? toBuilder().steps(stepsNew).build()
				: this;
	}

	public boolean containsRawMaterialsIssueStep(final PPOrderIssueScheduleId issueScheduleId)
	{
		return steps.stream().anyMatch(step -> PPOrderIssueScheduleId.equals(step.getId(), issueScheduleId));
	}

	@NonNull
	public ITranslatableString getProductValueAndProductName()
	{
		final TranslatableStringBuilder message = TranslatableStrings.builder()
				.append(getProductValue())
				.append(" ")
				.append(getProductName());

		return message.build();
	}
}
