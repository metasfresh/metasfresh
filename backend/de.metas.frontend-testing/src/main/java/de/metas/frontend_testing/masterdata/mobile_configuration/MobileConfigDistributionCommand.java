package de.metas.frontend_testing.masterdata.mobile_configuration;

import com.google.common.base.Splitter;
import de.metas.distribution.config.DistributionJobCaptionField;
import de.metas.distribution.config.DistributionJobCaptionFormat;
import de.metas.distribution.config.DistributionJobCaptionFormatItem;
import de.metas.distribution.config.DistributionJobSorting;
import de.metas.distribution.config.DistributionJobSortingField;
import de.metas.distribution.config.DistributionJobSortingItem;
import de.metas.distribution.config.MobileUIDistributionConfig;
import de.metas.distribution.config.MobileUIDistributionConfigRepository;
import de.metas.util.StringUtils;
import de.metas.workflow.rest_api.service.Constants;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.exceptions.AdempiereException;

@Builder
class MobileConfigDistributionCommand
{
	@NonNull private static final Splitter COMMA_SEPARATED_SPLITTER = Splitter.on(",").trimResults().omitEmptyStrings();

	@NonNull private final MobileUIDistributionConfigRepository mobileDistributionConfigRepository;

	@NonNull private final JsonMobileConfigRequest.Distribution request;

	public JsonMobileConfigResponse.Distribution execute()
	{
		final MobileUIDistributionConfig.MobileUIDistributionConfigBuilder newConfigBuilder = mobileDistributionConfigRepository.getConfig().toBuilder();
		if (request.getAllowPickingAnyHU() != null)
		{
			newConfigBuilder.allowPickingAnyHU(request.getAllowPickingAnyHU());
		}

		StringUtils.trimBlankToOptional(request.getCaptionFormat())
				.map(MobileConfigDistributionCommand::parseCaptionFormat)
				.ifPresent(newConfigBuilder::captionFormat);

		StringUtils.trimBlankToOptional(request.getOrderBys())
				.map(MobileConfigDistributionCommand::parseSorting)
				.ifPresent(newConfigBuilder::sorting);

		newConfigBuilder.maxLaunchers(request.getMaxLaunchers() != null ? request.getMaxLaunchers() : Constants.DEFAULT_LaunchersLimit);
		newConfigBuilder.maxStartedLaunchers(request.getMaxStartedLaunchers() != null ? request.getMaxStartedLaunchers() : QueryLimit.NO_LIMIT);
		newConfigBuilder.isAllowStartNextJobOnly(request.getAllowStartNextJobOnly() != null ? request.getAllowStartNextJobOnly() : false);

		final MobileUIDistributionConfig newConfig = newConfigBuilder.build();
		mobileDistributionConfigRepository.save(newConfig);

		return JsonMobileConfigResponse.Distribution.builder()
				.allowPickingAnyHU(newConfig.isAllowPickingAnyHU())
				.build();
	}

	private static DistributionJobCaptionFormat parseCaptionFormat(@NonNull final String captionFormatStr)
	{
		return COMMA_SEPARATED_SPLITTER.splitToList(captionFormatStr).stream()
				.map(fieldName -> DistributionJobCaptionFormatItem.builder()
						.field(DistributionJobCaptionField.ofCodeOrName(fieldName))
						.build())
				.collect(DistributionJobCaptionFormat.collect())
				.orElseThrow(() -> new AdempiereException("Invalid caption format: " + captionFormatStr));
	}

	private static DistributionJobSorting parseSorting(final String orderBys)
	{
		return COMMA_SEPARATED_SPLITTER.splitToList(orderBys).stream()
				.map(fieldName -> DistributionJobSortingItem.of(
						DistributionJobSortingField.ofCodeOrName(fieldName),
						IQueryOrderBy.Direction.Ascending))
				.collect(DistributionJobSorting.collect())
				.orElseThrow(() -> new AdempiereException("Invalid orderBys: " + orderBys));
	}
}
