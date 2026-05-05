package de.metas.handlingunits.qrcodes.interceptor;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.List;

@Interceptor(I_M_HU.class)
@Component
@RequiredArgsConstructor
public class M_HU
{
	@NonNull private final HUQRCodesService huQRCodesService;

	/**
	 * When an HU is destroyed, drop the QR-code-to-HU assignment so future scans of the
	 * (still physically printed) sticker fail with a clear "unknown QR code" message rather
	 * than resolving to an HU with empty storage.
	 */
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = I_M_HU.COLUMNNAME_HUStatus)
	public void removeQRCodeAssignmentsOnDestroy(@NonNull final I_M_HU hu)
	{
		if (!X_M_HU.HUSTATUS_Destroyed.equals(hu.getHUStatus()))
		{
			return;
		}

		final HuId huId = HuId.ofRepoId(hu.getM_HU_ID());
		final List<HUQRCode> qrCodes = huQRCodesService.getExistingQRCodesByHuId(huId);
		if (qrCodes.isEmpty())
		{
			return;
		}

		final ImmutableSet<HuId> huIdSet = ImmutableSet.of(huId);
		for (final HUQRCode qrCode : qrCodes)
		{
			huQRCodesService.removeAssignment(qrCode, huIdSet);
		}
	}
}
