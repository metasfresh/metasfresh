package de.metas.manufacturing.job.model;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.pporder.api.issue_schedule.PPOrderIssueScheduleId;
import de.metas.i18n.ITranslatableString;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

import java.util.function.UnaryOperator;

@Value
@Builder
public class RawMaterialsIssueLine
{
	@NonNull ProductId productId;
	@NonNull ITranslatableString productName;
	@NonNull Quantity qtyToIssue;
	@NonNull Quantity qtyIssued;

	@With
	@NonNull ImmutableList<RawMaterialsIssueStep> steps;

	public RawMaterialsIssueLine withChangedRawMaterialsIssueStep(
			@NonNull final PPOrderIssueScheduleId issueScheduleId,
			@NonNull UnaryOperator<RawMaterialsIssueStep> mapper)
	{
		final ImmutableList<RawMaterialsIssueStep> stepsNew = CollectionUtils.map(
				steps,
				step -> PPOrderIssueScheduleId.equals(step.getId(), issueScheduleId) ? mapper.apply(step) : step);

		return withSteps(stepsNew);
	}
}
