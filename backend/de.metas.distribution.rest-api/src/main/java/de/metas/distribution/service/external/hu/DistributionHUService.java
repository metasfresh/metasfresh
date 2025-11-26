package de.metas.distribution.service.external.hu;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.IHUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.scannable_code.ScannedCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DistributionHUService
{
	@NonNull private final HUQRCodesService huQRCodesService;

	@NonNull
	public HUInfo getHUInfoById(@NonNull HuId huId)
	{
		return HUInfo.builder()
				.id(huId)
				.qrCode(getQRCodeByHuId(huId))
				.build();
	}

	@NonNull
	public HUQRCode getQRCodeByHuId(@NonNull final HuId huId) {return huQRCodesService.getQRCodeByHuId(huId);}

	@NonNull
	public HUQRCode resolveHUQRCode(final @NonNull ScannedCode scannedCode)
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
