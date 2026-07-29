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
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.util.collections.CollectionUtils;
import de.metas.workflow.rest_api.model.WFActivityStatus;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
import org.eevolution.api.BOMComponentIssueMethod;
import org.eevolution.api.PPOrderBOMLineId;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;
import java.util.function.UnaryOperator;

@Value
public class RawMaterialsIssueLine
{
	@NonNull PPOrderBOMLineId orderBOMLineId;
	@NonNull ProductId productId;
	@NonNull ITranslatableString productName;
	@NonNull String productValue;
	boolean isWeightable;
	@NonNull BOMComponentIssueMethod issueMethod;
	@NonNull Quantity qtyToIssue;
	@Nullable IssuingToleranceSpec issuingToleranceSpec;
	@NonNull ImmutableList<RawMaterialsIssueStep> steps;

	// Provided by the caller so qtyIssued can be expressed in the BOM line's UOM (see computeQtyIssued).
	// Not part of the line's identity, hence excluded from equals/hashCode/toString.
	@EqualsAndHashCode.Exclude @ToString.Exclude
	@NonNull IUOMConversionBL uomConversionBL;

	@NonNull Quantity qtyIssued; // computed
	@NonNull WFActivityStatus status;
	int seqNo;

	@Builder(toBuilder = true)
	private RawMaterialsIssueLine(
			@NonNull final PPOrderBOMLineId orderBOMLineId,
			@NonNull final ProductId productId,
			@NonNull final ITranslatableString productName,
			@NonNull final String productValue,
			final boolean isWeightable,
			@Nullable BOMComponentIssueMethod issueMethod,
			@NonNull final Quantity qtyToIssue,
			@Nullable final IssuingToleranceSpec issuingToleranceSpec,
			@NonNull final ImmutableList<RawMaterialsIssueStep> steps,
			@NonNull final IUOMConversionBL uomConversionBL,
			final int seqNo)
	{
		this.orderBOMLineId = orderBOMLineId;
		this.productId = productId;
		this.productName = productName;
		this.productValue = productValue;
		this.isWeightable = isWeightable;
		this.issueMethod = issueMethod != null ? issueMethod : BOMComponentIssueMethod.Issue;
		this.qtyToIssue = qtyToIssue;
		this.issuingToleranceSpec = issuingToleranceSpec;
		this.steps = steps;
		this.uomConversionBL = uomConversionBL;

		this.qtyIssued = computeQtyIssued(this.steps, this.productId, this.qtyToIssue.getUomId(), this.uomConversionBL)
				.orElseGet(qtyToIssue::toZero);
		this.seqNo = seqNo;
		this.status = computeStatus(this.qtyToIssue, this.qtyIssued, this.steps);
	}

	/**
	 * Sums the steps' issued quantities, expressed in {@code targetUomId} (the BOM line's UOM). A step's
	 * issued qty comes back in the picked HU's UOM (e.g. Stk for a kg BOM line), so each is converted to
	 * {@code targetUomId} via the product's UOM conversion before summing — keeping {@code qtyIssued}
	 * comparable to {@code qtyToIssue}.
	 */
	private static Optional<Quantity> computeQtyIssued(
			final @NonNull ImmutableList<RawMaterialsIssueStep> steps,
			final @NonNull ProductId productId,
			final @NonNull UomId targetUomId,
			final @NonNull IUOMConversionBL uomConversionBL)
	{
		return steps.stream()
				.map(RawMaterialsIssueStep::getIssued)
				.filter(Objects::nonNull)
				.map(PPOrderIssueSchedule.Issued::getQtyIssued)
				.map(qtyIssued -> uomConversionBL.convertQuantityTo(qtyIssued, productId, targetUomId))
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

	@NonNull
	public RawMaterialsIssueLine withSteps(final ImmutableList<RawMaterialsIssueStep> stepsNew)
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

	@NonNull
	public Quantity getQtyLeftToIssue()
	{
		return qtyToIssue.subtract(qtyIssued);
	}

	/**
	 * The quantity still allowed to be issued (including the issuing tolerance), converted to {@code targetUomId}
	 * and rounded UP to that UOM's precision. Used to cap the mobile "Qty to issue" input, which the operator
	 * enters in the picked HU's stocking UOM (e.g. Stk), against the BOM line's remaining demand (e.g. kg):
	 * for a 35 kg/Stk product with 34.5 kg still to issue, this yields 1 Stk (0.986 rounded UP) — so the operator
	 * cannot enter more than one whole piece toward that demand. Without the conversion the frontend would compare
	 * the entered Stk value against a kg ceiling and silently accept a massive over-issue.
	 */
	@NonNull
	public Quantity getRemainingQtyToIssueMaxInUOM(@NonNull final UomId targetUomId)
	{
		final Quantity maxToIssue = getQtyToIssueMax().orElse(qtyToIssue); // BOM line UOM, incl. issuing tolerance
		final Quantity remaining = maxToIssue.subtract(qtyIssued).toZeroIfNegative();
		return uomConversionBL.convertQuantityTo(remaining, productId, targetUomId);
	}

	public boolean isAllowManualIssue()
	{
		return !issueMethod.isIssueOnlyForReceived();
	}

	public boolean isIssueOnlyForReceived() {return issueMethod.isIssueOnlyForReceived();}

}
