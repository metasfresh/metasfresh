package de.metas.manufacturing.order.weighting;

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

	@Builder
	private WeightingSpecifications(
			@NonNull final WeightingSpecificationsId id,
			@NonNull final Percent tolerance,
			final int weightChecksRequired)
	{
		if (weightChecksRequired < 1)
		{
			throw new AdempiereException("Required weight checks shall be >= 1");
		}

		this.id = id;
		this.tolerance = tolerance;
		this.weightChecksRequired = weightChecksRequired;
	}
}
