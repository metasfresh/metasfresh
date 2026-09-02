package de.metas.frontend_testing.masterdata.mobile_configuration;

import com.google.common.collect.ImmutableList;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.manufacturing.config.MobileUIManufacturingConfig;
import de.metas.manufacturing.config.MobileUIManufacturingConfigRepository;
import de.metas.manufacturing.config.ReceiveUnitType;
import de.metas.user.UserId;
import de.metas.util.OptionalBoolean;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.service.ClientId;

import java.util.List;

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
		if (request.getReceiveUnitType() != null)
		{
			newConfigBuilder.receiveUnitType(ReceiveUnitType.ofCode(request.getReceiveUnitType()));
		}
		if (request.getIsAllowFinishedGoodsReceiveToLU() != null)
		{
			newConfigBuilder.isAllowFinishedGoodsReceiveToLU(OptionalBoolean.ofBoolean(request.getIsAllowFinishedGoodsReceiveToLU()));
		}
		if (request.getIsAllowFinishedGoodsReceiveToTU() != null)
		{
			newConfigBuilder.isAllowFinishedGoodsReceiveToTU(OptionalBoolean.ofBoolean(request.getIsAllowFinishedGoodsReceiveToTU()));
		}
		if (request.getIsSkipFinishedGoodsReceiveTargetStep() != null)
		{
			newConfigBuilder.isSkipFinishedGoodsReceiveTargetStep(OptionalBoolean.ofBoolean(request.getIsSkipFinishedGoodsReceiveTargetStep()));
		}
		if (request.getIsCaptureCatchWeightAtReceipt() != null)
		{
			newConfigBuilder.isCaptureCatchWeightAtReceipt(OptionalBoolean.ofBoolean(request.getIsCaptureCatchWeightAtReceipt()));
		}
		if (request.getIsAllowReceiveWithoutPackingItem() != null)
		{
			newConfigBuilder.isAllowReceiveWithoutPackingItem(OptionalBoolean.ofBoolean(request.getIsAllowReceiveWithoutPackingItem()));
		}

		final MobileUIManufacturingConfig newConfig = newConfigBuilder.build();
		mobileManufacturingConfigRepository.saveUserConfig(newConfig, loginUserId);

		// The editable-attribute list is global-only (v1) - it does NOT live on the per-user profile row saved
		// above (see MobileUIManufacturingConfigRepository#fromRecord, which always reads it back empty there),
		// so it is written through its own global-config path.
		if (request.getEditableAttributes() != null)
		{
			mobileManufacturingConfigRepository.saveGlobalEditableAttributeCodesInOrder(ClientId.METASFRESH, request.getEditableAttributes());
		}

		return JsonMobileConfigResponse.Manufacturing.builder()
				.isScanResourceRequired(newConfig.getIsScanResourceRequired().toBooleanOrNull())
				.isAllowIssuingAnyHU(newConfig.getIsAllowIssuingAnyHU().toBooleanOrNull())
				.receiveUnitType(newConfig.getReceiveUnitType() != null ? newConfig.getReceiveUnitType().getCode() : null)
				.isAllowFinishedGoodsReceiveToLU(newConfig.getIsAllowFinishedGoodsReceiveToLU().toBooleanOrNull())
				.isAllowFinishedGoodsReceiveToTU(newConfig.getIsAllowFinishedGoodsReceiveToTU().toBooleanOrNull())
				.isSkipFinishedGoodsReceiveTargetStep(newConfig.getIsSkipFinishedGoodsReceiveTargetStep().toBooleanOrNull())
				.isCaptureCatchWeightAtReceipt(newConfig.getIsCaptureCatchWeightAtReceipt().toBooleanOrNull())
				.isAllowReceiveWithoutPackingItem(newConfig.getIsAllowReceiveWithoutPackingItem().toBooleanOrNull())
				.editableAttributes(getGlobalEditableAttributes())
				.build();
	}

	@NonNull
	private List<AttributeCode> getGlobalEditableAttributes()
	{
		final MobileUIManufacturingConfig globalConfig = mobileManufacturingConfigRepository.getGlobalConfig(ClientId.METASFRESH);
		return globalConfig != null ? globalConfig.getEditableAttributeCodesInOrder() : ImmutableList.of();
	}

}
