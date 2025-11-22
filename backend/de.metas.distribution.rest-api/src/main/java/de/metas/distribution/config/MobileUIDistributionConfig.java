package de.metas.distribution.config;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.QueryLimit;

@Value
@Builder(toBuilder = true)
public class MobileUIDistributionConfig
{
	boolean allowPickingAnyHU;
	@NonNull DistributionJobCaptionFormat captionFormat;
	@NonNull DistributionJobSorting sorting;

	@NonNull @Default QueryLimit maxLaunchers = QueryLimit.NO_LIMIT;
	@NonNull @Default QueryLimit maxStartedLaunchers = QueryLimit.NO_LIMIT;
	boolean isAllowStartNextJobOnly;
}
