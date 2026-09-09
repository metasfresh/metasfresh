package de.metas.frontend_testing.masterdata.mobile_configuration;

import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.manufacturing.config.MobileUIManufacturingConfig;
import de.metas.manufacturing.config.MobileUIManufacturingConfigRepository;
import de.metas.user.UserId;
import de.metas.util.OptionalBoolean;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.service.ClientId;

@Builder
class MobileConfigManufacturingCommand
{
	@NonNull private final MobileUIManufacturingConfigRepository mobileManufacturingConfigRepository;

	@NonNull private final MasterdataContext context;
	@NonNull private final JsonMobileConfigRequest.Manufacturing request;

	public JsonMobileConfigResponse.Manufacturing execute()
	{
		final UserId loginUserId = context.getIdOfType(UserId.class);
		final MobileUIManufacturingConfig.MobileUIManufacturingConfigBuilder newConfigBuilder = mobileManufacturingConfigRepository.getConfig(loginUserId, ClientId.METASFRESH).toBuilder();
		if (request.getIsScanResourceRequired() != null)
		{
			newConfigBuilder.isScanResourceRequired(OptionalBoolean.ofBoolean(request.getIsScanResourceRequired()));
		}
		if (request.getIsAllowIssuingAnyHU() != null)
		{
			newConfigBuilder.isAllowIssuingAnyHU(OptionalBoolean.ofBoolean(request.getIsAllowIssuingAnyHU()));
		}

		final MobileUIManufacturingConfig newConfig = newConfigBuilder.build();
		mobileManufacturingConfigRepository.saveUserConfig(newConfig, loginUserId);

		// IsAllowEmptyingHUs / IsConfirmEmptyingHU are client-level only (MobileUI_MFG_Config has no
		// per-user column for them, cf. RawMaterialsIssueActivityHandler#resolveEmptyingHUsConfig) —
		// route them to the global config, never to the per-user profile above.
		if (request.getIsAllowEmptyingHUs() != null || request.getIsConfirmEmptyingHU() != null)
		{
			updateGlobalEmptyingHUsConfig();
		}

		final MobileUIManufacturingConfig effectiveConfig = mobileManufacturingConfigRepository.getConfig(loginUserId, ClientId.METASFRESH);
		return JsonMobileConfigResponse.Manufacturing.builder()
				.isScanResourceRequired(effectiveConfig.getIsScanResourceRequired().toBooleanOrNull())
				.isAllowIssuingAnyHU(effectiveConfig.getIsAllowIssuingAnyHU().toBooleanOrNull())
				.isAllowEmptyingHUs(effectiveConfig.getIsAllowEmptyingHUs().toBooleanOrNull())
				.isConfirmEmptyingHU(effectiveConfig.getIsConfirmEmptyingHU().toBooleanOrNull())
				.build();
	}

	private void updateGlobalEmptyingHUsConfig()
	{
		// getGlobalConfigOrDefault, not a local copy of the defaults: the harness must create the global record
		// with exactly the values production falls back to.
		final MobileUIManufacturingConfig.MobileUIManufacturingConfigBuilder globalConfigBuilder =
				mobileManufacturingConfigRepository.getGlobalConfigOrDefault(ClientId.METASFRESH).toBuilder();

		if (request.getIsAllowEmptyingHUs() != null)
		{
			globalConfigBuilder.isAllowEmptyingHUs(OptionalBoolean.ofBoolean(request.getIsAllowEmptyingHUs()));
		}
		if (request.getIsConfirmEmptyingHU() != null)
		{
			globalConfigBuilder.isConfirmEmptyingHU(OptionalBoolean.ofBoolean(request.getIsConfirmEmptyingHU()));
		}

		mobileManufacturingConfigRepository.saveGlobalConfig(globalConfigBuilder.build(), ClientId.METASFRESH);
	}

}
