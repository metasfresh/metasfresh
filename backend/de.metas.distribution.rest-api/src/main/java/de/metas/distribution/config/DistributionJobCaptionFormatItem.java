package de.metas.distribution.config;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class DistributionJobCaptionFormatItem
{
	@NonNull DistributionJobCaptionField field;
}
