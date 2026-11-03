package de.metas.handlingunits.rest_api;

import de.metas.handlingunits.mobileui.config.HUManagerProfileLayoutSectionList;
import de.metas.scannable_code.ScannedCode;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeCode;

import javax.annotation.Nullable;
import java.util.List;

@Value
@Builder
public class GetByQRCodeRequest
{
	@NonNull ScannedCode qrCode;
	@Nullable String upperLevelLocatingQrCode;

	boolean includeAllowedClearanceStatuses;
	@Nullable List<AttributeCode> orderedAttributeCodes;
	@Nullable HUManagerProfileLayoutSectionList layoutSections;

	public static GetByQRCodeRequestBuilder builderFrom(@NonNull final JsonGetByQRCodeRequest json)
	{
		return builder()
				.qrCode(ScannedCode.ofString(json.getQrCode()))
				.upperLevelLocatingQrCode(json.getUpperLevelLocatingQrCode())
				.includeAllowedClearanceStatuses(json.isIncludeAllowedClearanceStatuses());
	}

	public static GetByQRCodeRequest of(@NonNull final JsonGetByQRCodeRequest json) {return builderFrom(json).build();}

}
