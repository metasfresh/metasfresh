package de.metas.manufacturing.order.weighting.run;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Range;
import de.metas.i18n.AdMessageKey;
import de.metas.manufacturing.order.weighting.spec.WeightingSpecificationsId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.lang.Percent;
import de.metas.util.lang.SeqNo;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_UOM;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Comparator;

@Getter
@EqualsAndHashCode
@ToString
public class PPOrderWeightingRun
{
	private static final AdMessageKey MSG_LessChecksThanRequired = AdMessageKey.of("manufacturing.order.weighting.LessChecksThanRequired");
	private static final AdMessageKey MSG_ToleranceExceeded = AdMessageKey.of("manufacturing.order.weighting.ToleranceExceeded");

	@NonNull private final PPOrderWeightingRunId id;

	@NonNull private final PPOrderId ppOrderId;
	@Nullable private final PPOrderBOMLineId ppOrderBOMLineId;
	@NonNull private final Instant dateDoc;
	@Nullable private final String description;

	@NonNull private final ProductId productId;

	@NonNull private final WeightingSpecificationsId weightingSpecificationsId;
	@NonNull private final Percent tolerance;
	private final int weightChecksRequired;

	@NonNull private final Quantity targetWeight;
	@NonNull private Range<Quantity> targetWeightRange;

	private boolean isToleranceExceeded;
	private boolean isProcessed;

	@NonNull private final ImmutableList<PPOrderWeightingRunCheck> checks;

	@NonNull private final OrgId orgId;

	@Builder
	private PPOrderWeightingRun(
			@NonNull final PPOrderWeightingRunId id,
			@NonNull final PPOrderId ppOrderId,
			@Nullable final PPOrderBOMLineId ppOrderBOMLineId,
			@NonNull final Instant dateDoc,
			@Nullable final String description,
			@NonNull final ProductId productId,
			@NonNull final WeightingSpecificationsId weightingSpecificationsId,
			@NonNull final Percent tolerance,
			final int weightChecksRequired,
			@NonNull final Quantity targetWeight,
			@NonNull final Range<Quantity> targetWeightRange,
			final boolean isToleranceExceeded,
			final boolean isProcessed,
			@NonNull final ImmutableList<PPOrderWeightingRunCheck> checks,
			final @NonNull OrgId orgId)
	{
		this.id = id;
		this.ppOrderId = ppOrderId;
		this.ppOrderBOMLineId = ppOrderBOMLineId;
		this.dateDoc = dateDoc;
		this.description = description;
		this.productId = productId;
		this.weightingSpecificationsId = weightingSpecificationsId;
		this.tolerance = tolerance;
		this.weightChecksRequired = weightChecksRequired;
		this.targetWeight = targetWeight;
		this.targetWeightRange = targetWeightRange;
		this.isToleranceExceeded = isToleranceExceeded;
		this.isProcessed = isProcessed;
		this.checks = checks;
		this.orgId = orgId;
	}

	public void process()
	{
		assertNotProcessed();

		if (checks.size() < weightChecksRequired)
		{
			throw new AdempiereException(MSG_LessChecksThanRequired);
		}

		updateToleranceExceededFlag();
		if (isToleranceExceeded)
		{
			throw new AdempiereException(MSG_ToleranceExceeded);
		}

		this.isProcessed = true;
	}

	public void unprocess()
	{
		this.isProcessed = false;
	}

	public void updateToleranceExceededFlag()
	{
		assertNotProcessed();

		boolean isToleranceExceededNew = false;
		for (PPOrderWeightingRunCheck check : checks)
		{
			check.updateIsToleranceExceeded(targetWeightRange);
			if (check.isToleranceExceeded())
			{
				isToleranceExceededNew = true;
			}
		}

		this.isToleranceExceeded = isToleranceExceededNew;
	}

	public void updateTargetWeightRange()
	{
		assertNotProcessed();

		final Quantity toleranceQty = targetWeight.multiply(tolerance).abs();

		this.targetWeightRange = Range.closed(
				targetWeight.subtract(toleranceQty),
				targetWeight.add(toleranceQty)
		);

		updateToleranceExceededFlag();
	}

	public void updateUOMFromHeaderToChecks()
	{
		assertNotProcessed();

		for (final PPOrderWeightingRunCheck check : checks)
		{
			check.setUomId(targetWeight.getUomId());
		}
	}

	private void assertNotProcessed()
	{
		if (isProcessed)
		{
			throw new AdempiereException("Already processed");
		}
	}

	public I_C_UOM getUOM()
	{
		return targetWeight.getUOM();
	}

	public SeqNo getNextLineNo()
	{
		final SeqNo lastLineNo = checks.stream()
				.map(PPOrderWeightingRunCheck::getLineNo)
				.max(Comparator.naturalOrder())
				.orElseGet(() -> SeqNo.ofInt(0));

		return lastLineNo.next();
	}
}
