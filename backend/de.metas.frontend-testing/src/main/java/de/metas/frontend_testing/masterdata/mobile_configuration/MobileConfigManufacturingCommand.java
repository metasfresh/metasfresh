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

		return JsonMobileConfigResponse.Manufacturing.builder()
				.isScanResourceRequired(newConfig.getIsScanResourceRequired().toBooleanOrNull())
				.isAllowIssuingAnyHU(newConfig.getIsAllowIssuingAnyHU().toBooleanOrNull())
				.build();
	}

}
