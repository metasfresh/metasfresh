package de.metas.frontend_testing.masterdata.mobile_configuration;

import de.metas.distribution.config.MobileUIDistributionConfigRepository;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.handlingunits.picking.config.mobileui.MobileUIPickingUserProfileService;
import de.metas.manufacturing.config.MobileUIManufacturingConfigRepository;
import de.metas.mobile.MobileConfig;
import de.metas.mobile.MobileConfig.MobileConfigBuilder;
import de.metas.mobile.MobileConfigService;
import lombok.Builder;
import lombok.NonNull;

@Builder
public class MobileConfigCommand
{
	@NonNull private final MobileConfigService mobileConfigService;
	@NonNull private final MobileUIPickingUserProfileService mobilePickingConfigService;
	@NonNull private final MobileUIDistributionConfigRepository mobileDistributionConfigRepository;
	@NonNull private final MobileUIManufacturingConfigRepository mobileManufacturingConfigRepository;

	@NonNull private final MasterdataContext context;
	@NonNull private final JsonMobileConfigRequest request;

	public JsonMobileConfigResponse execute()
	{
		final MobileConfig config = updateMobileConfig();
		final JsonMobileConfigResponse.Picking picking = updatePickingConfig();
		final JsonMobileConfigResponse.Distribution distribution = updateDistributionConfig();
		final JsonMobileConfigResponse.Manufacturing manufacturing = updateManufacturingConfig();

		return JsonMobileConfigResponse.builder()
				.defaultAuthMethod(config.getDefaultAuthMethod())
				.picking(picking)
				.distribution(distribution)
				.manufacturing(manufacturing)
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
		if (request.getPicking() == null)
		{
			return null;
		}

		return MobileConfigPickingCommand.builder()
				.mobilePickingConfigService(mobilePickingConfigService)
				.context(context)
				.request(request.getPicking())
				.build().execute();
	}

	private JsonMobileConfigResponse.Distribution updateDistributionConfig()
	{
		if (request.getDistribution() == null)
		{
			return null;
		}

		return MobileConfigDistributionCommand.builder()
				.mobileDistributionConfigRepository(mobileDistributionConfigRepository)
				.request(request.getDistribution())
				.build().execute();
	}

	private JsonMobileConfigResponse.Manufacturing updateManufacturingConfig()
	{
		if (request.getManufacturing() == null)
		{
			return null;
		}

		return MobileConfigManufacturingCommand.builder()
				.mobileManufacturingConfigRepository(mobileManufacturingConfigRepository)
				.context(context)
				.request(request.getManufacturing())
				.build().execute();
	}
}
