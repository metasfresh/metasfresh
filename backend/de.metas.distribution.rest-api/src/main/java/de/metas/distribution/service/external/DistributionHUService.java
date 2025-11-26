package de.metas.distribution.service.external;

import de.metas.distribution.workflows_api.json.JsonHUInfo;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.IHUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.scannable_code.ScannedCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DistributionHUService
{
	@NonNull private final HUQRCodesService huQRCodesService;

	public @NonNull JsonHUInfo getHUInfoByQRCode(@NonNull final ScannedCode scannedCode)
	{
		final HUQRCode qrCode = toHUQRCode(scannedCode);
		return JsonHUInfo.of(qrCode.toRenderedJson());
	}

	private HUQRCode toHUQRCode(final @NotNull ScannedCode scannedCode)
	{
		final IHUQRCode parsedHUQRCode = huQRCodesService.parse(scannedCode);
		if (parsedHUQRCode instanceof HUQRCode)
		{
			return (HUQRCode)parsedHUQRCode;
		}
		else
		{
			throw new AdempiereException("Cannot convert " + scannedCode + " to actual HUQRCode")
					.setParameter("parsedHUQRCode", parsedHUQRCode)
					.setParameter("parsedHUQRCode type", parsedHUQRCode.getClass().getSimpleName());
		}
	}
}
