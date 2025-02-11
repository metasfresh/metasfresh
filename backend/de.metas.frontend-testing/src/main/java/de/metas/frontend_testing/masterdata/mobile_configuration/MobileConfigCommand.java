package de.metas.frontend_testing.masterdata.mobile_configuration;

import de.metas.distribution.config.MobileUIDistributionConfig.MobileUIDistributionConfigBuilder;
import de.metas.distribution.config.MobileUIDistributionConfigRepository;
import de.metas.handlingunits.picking.config.MobileUIPickingUserProfile;
import de.metas.handlingunits.picking.config.MobileUIPickingUserProfile.MobileUIPickingUserProfileBuilder;
import de.metas.handlingunits.picking.config.MobileUIPickingUserProfileRepository;
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

	public void execute()
	{
		updateMobileConfig();
		updatePickingConfig();
		updateDistributionConfig();
	}

	private void updateMobileConfig()
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
	}

	public void updatePickingConfig()
	{
		final JsonMobileConfigRequest.Picking picking = request.getPicking();
		if (picking == null)
		{
			return;
		}

		final MobileUIPickingUserProfileBuilder newProfileBuilder = mobilePickingConfigRepository.getProfile().toBuilder();
		if (picking.getAllowPickingAnyHU() != null)
		{
			newProfileBuilder.isAllowPickingAnyHU(picking.getAllowPickingAnyHU());
		}
		if (picking.getCreateShipmentPolicy() != null)
		{
			newProfileBuilder.createShipmentPolicy(picking.getCreateShipmentPolicy());
		}
		if (picking.getAlwaysSplitHUsEnabled() != null)
		{
			newProfileBuilder.isAlwaysSplitHUsEnabled(picking.getAlwaysSplitHUsEnabled());
		}

		final MobileUIPickingUserProfile newProfile = newProfileBuilder.build();
		mobilePickingConfigRepository.save(newProfile);
	}

	private void updateDistributionConfig()
	{
		final JsonMobileConfigRequest.Distribution distribution = request.getDistribution();
		if (distribution == null)
		{
			return;
		}

		final MobileUIDistributionConfigBuilder configBuilder = mobileDistributionConfigRepository.getConfig().toBuilder();
		if (distribution.getAllowPickingAnyHU() != null)
		{
			configBuilder.allowPickingAnyHU(distribution.getAllowPickingAnyHU());
		}

		mobileDistributionConfigRepository.save(configBuilder.build());
	}
}
