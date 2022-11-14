package de.metas.manufacturing.order.weighting.spec;

import de.metas.uom.UomId;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

@Value
@Builder
public class WeightingSpecifications
{
	@NonNull WeightingSpecificationsId id;
	@NonNull Percent tolerance;
	int weightChecksRequired;
	@NonNull UomId uomId;

	@Builder
	private WeightingSpecifications(
			final @NonNull WeightingSpecificationsId id,
			final @NonNull Percent tolerance,
			final int weightChecksRequired,
			final @NonNull UomId uomId)
	{
		this.uomId = uomId;
		if (weightChecksRequired < 1)
		{
			throw new AdempiereException("Required weight checks shall be >= 1");
		}
		if (tolerance.signum() < 0)
		{
			throw new AdempiereException("Tolerance shall be >= 0");
		}

		this.id = id;
		this.tolerance = tolerance;
		this.weightChecksRequired = weightChecksRequired;
	}
}
