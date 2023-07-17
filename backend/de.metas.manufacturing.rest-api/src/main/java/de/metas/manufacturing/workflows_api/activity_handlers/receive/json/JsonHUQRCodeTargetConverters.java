package de.metas.manufacturing.workflows_api.activity_handlers.receive.json;

import de.metas.common.util.CoalesceUtil;
import de.metas.global_qrcodes.JsonDisplayableQRCode;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.manufacturing.job.model.ReceivingTarget;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;

@UtilityClass
public class JsonHUQRCodeTargetConverters
{
	@Nullable
	public static JsonHUQRCodeTarget fromNullable(
			@Nullable final ReceivingTarget receivingTarget,
			@NonNull final HUQRCodesService huQRCodeService)
	{
		return receivingTarget != null
				? from(receivingTarget, huQRCodeService)
				: null;
	}
	
	public static JsonHUQRCodeTarget from(
			@NonNull final ReceivingTarget receivingTarget,
			@NonNull final HUQRCodesService huQRCodeService)
	{
		return JsonHUQRCodeTarget.builder()
				.huQRCode(toJsonRenderedHUQRCode(
						CoalesceUtil.coalesceNotNull(
								receivingTarget.getLuId(),
								receivingTarget.getTuId()),
						huQRCodeService))
				.tuPIItemProductId(receivingTarget.getTuPIItemProductId())
				.build();
	}

	private static JsonDisplayableQRCode toJsonRenderedHUQRCode(
			@NonNull final HuId huId,
			@NonNull final HUQRCodesService huQRCodeService)
	{
		return huQRCodeService.getQRCodeByHuId(huId).toRenderedJson();
	}

}
