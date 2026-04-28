package de.metas.distribution.mobileui.config;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class DistributionJobCaptionFormatItem
{
	@NonNull DistributionJobCaptionField field;
}
