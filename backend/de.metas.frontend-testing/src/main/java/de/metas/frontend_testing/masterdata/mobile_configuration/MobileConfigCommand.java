package de.metas.frontend_testing.masterdata.mobile_configuration;

import de.metas.distribution.config.MobileUIDistributionConfig;
import de.metas.distribution.config.MobileUIDistributionConfig.MobileUIDistributionConfigBuilder;
import de.metas.distribution.config.MobileUIDistributionConfigRepository;
import de.metas.handlingunits.picking.config.mobileui.MobileUIPickingUserProfile;
import de.metas.handlingunits.picking.config.mobileui.MobileUIPickingUserProfileRepository;
import de.metas.handlingunits.picking.config.mobileui.PickingJobOptions;
import de.metas.handlingunits.picking.config.mobileui.PickingJobOptions.PickingJobOptionsBuilder;
import de.metas.mobile.MobileConfig;
import de.metas.mobile.MobileConfig.MobileConfigBuilder;
import de.metas.mobile.MobileConfigService;
import lombok.Builder;
import lombok.NonNull;

@Builder
public class MobileConfigCommand
{
	@NonNull private final MobileConfigService mobileConfigService;
	@NonNull private final MobileUIPickingUserProfileRepository mobilePickingConfigRepository;
	@NonNull private final MobileUIDistributionConfigRepository mobileDistributionConfigRepository;

	@NonNull private final JsonMobileConfigRequest request;

	public JsonMobileConfigResponse execute()
	{
		final MobileConfig config = updateMobileConfig();
		final JsonMobileConfigResponse.Picking picking = updatePickingConfig();
		final JsonMobileConfigResponse.Distribution distribution = updateDistributionConfig();

		return JsonMobileConfigResponse.builder()
				.defaultAuthMethod(config.getDefaultAuthMethod())
				.picking(picking)
				.distribution(distribution)
				.build();
	}

	private MobileConfig updateMobileConfig()
	{
		final MobileConfigBuilder configBuilder = mobileConfigService.getConfig()
				.orElse(MobileConfig.DEFAULT)
				.toBuilder();

		if (request.getDefaultAuthMethod() != null)
		{
			configBuilder.defaultAuthMethod(request.getDefaultAuthMethod());
		}

		final MobileConfig config = configBuilder.build();

		mobileConfigService.save(config);
		return config;
	}

	private JsonMobileConfigResponse.Picking updatePickingConfig()
	{
		final JsonMobileConfigRequest.Picking picking = request.getPicking();
		if (picking == null)
		{
			return null;
		}

		final MobileUIPickingUserProfile profile = mobilePickingConfigRepository.getProfile();
		final MobileUIPickingUserProfile.MobileUIPickingUserProfileBuilder newProfileBuilder = profile.toBuilder()
				.defaultPickingJobOptions(updatePickingJobOptions(profile.getDefaultPickingJobOptions(), picking));

		if (picking.getAllowPickingAnyCustomer() != null)
		{
			newProfileBuilder.isAllowPickingAnyCustomer(picking.getAllowPickingAnyCustomer());
		}

		final MobileUIPickingUserProfile newProfile = newProfileBuilder.build();
		mobilePickingConfigRepository.save(newProfile);

		return toJson(newProfile);
	}

	private static JsonMobileConfigResponse.Picking toJson(final MobileUIPickingUserProfile profile)
	{
		return JsonMobileConfigResponse.Picking.builder()
				.aggregationType(profile.getDefaultPickingJobOptions().getAggregationType())
				.allowPickingAnyCustomer(profile.isAllowPickingAnyCustomer())
				.allowPickingAnyHU(profile.getDefaultPickingJobOptions().isAllowPickingAnyHU())
				.createShipmentPolicy(profile.getDefaultPickingJobOptions().getCreateShipmentPolicy())
				.alwaysSplitHUsEnabled(profile.getDefaultPickingJobOptions().isAlwaysSplitHUsEnabled())
				.pickWithNewLU(profile.getDefaultPickingJobOptions().isPickWithNewLU())
				.allowNewTU(profile.getDefaultPickingJobOptions().isAllowNewTU())
				.build();
	}

	private static PickingJobOptions updatePickingJobOptions(final PickingJobOptions pickingJobOptions, final JsonMobileConfigRequest.Picking from)
	{
		final PickingJobOptionsBuilder builder = pickingJobOptions.toBuilder();
		if (from.getAggregationType() != null)
		{
			builder.aggregationType(from.getAggregationType());
		}
		if (from.getAllowPickingAnyHU() != null)
		{
			builder.isAllowPickingAnyHU(from.getAllowPickingAnyHU());
		}
		if (from.getCreateShipmentPolicy() != null)
		{
			builder.createShipmentPolicy(from.getCreateShipmentPolicy());
		}
		if (from.getAlwaysSplitHUsEnabled() != null)
		{
			builder.isAlwaysSplitHUsEnabled(from.getAlwaysSplitHUsEnabled());
		}
		if (from.getPickWithNewLU() != null)
		{
			builder.isPickWithNewLU(from.getPickWithNewLU());
		}
		if (from.getAllowNewTU() != null)
		{
			builder.isAllowNewTU(from.getAllowNewTU());
		}

		return builder.build();
	}

	private JsonMobileConfigResponse.Distribution updateDistributionConfig()
	{
		final JsonMobileConfigRequest.Distribution distribution = request.getDistribution();
		if (distribution == null)
		{
			return null;
		}

		final MobileUIDistributionConfigBuilder newConfigBuilder = mobileDistributionConfigRepository.getConfig().toBuilder();
		if (distribution.getAllowPickingAnyHU() != null)
		{
			newConfigBuilder.allowPickingAnyHU(distribution.getAllowPickingAnyHU());
		}

		final MobileUIDistributionConfig newConfig = newConfigBuilder.build();
		mobileDistributionConfigRepository.save(newConfig);

		return JsonMobileConfigResponse.Distribution.builder()
				.allowPickingAnyHU(newConfig.isAllowPickingAnyHU())
				.build();
	}
}
