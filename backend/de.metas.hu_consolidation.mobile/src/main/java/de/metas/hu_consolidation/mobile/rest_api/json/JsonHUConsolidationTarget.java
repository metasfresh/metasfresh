package de.metas.hu_consolidation.mobile.rest_api.json;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.hu_consolidation.mobile.job.HUConsolidationTarget;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.Contract;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
public class JsonHUConsolidationTarget
{
	@Nullable String id;
	@Nullable String caption;
	@Nullable HuPackingInstructionsId luPIId;
	@Nullable HuId luId;
	@Nullable String luQRCode;
	boolean printable;

	@Nullable
	@Contract("!null -> !null")
	public static JsonHUConsolidationTarget ofNullable(@Nullable final HUConsolidationTarget target)
	{
		return target != null ? of(target) : null;
	}

	@NonNull
	public static JsonHUConsolidationTarget of(@NonNull final HUConsolidationTarget target)
	{
		return builder()
				.id(target.getId())
				.caption(target.getCaption())
				.luPIId(target.getLuPIId())
				.luId(target.getLuId())
				.luQRCode(target.getLuQRCode() != null ? target.getLuQRCode().toGlobalQRCodeString() : null)
				.printable(target.isPrintable())
				.build();
	}
}
