package de.metas.handlingunits.qrcodes.interceptor;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_M_HU.class)
@Component
@RequiredArgsConstructor
public class M_HU
{
	@NonNull private final HUQRCodesService huQRCodesService;

	/**
	 * When an HU is destroyed, deactivate (soft-delete) the QR-code-to-HU assignment so future
	 * scans of the (still physically printed) sticker fail with a clear "unknown QR code" message
	 * rather than resolving to an HU with empty storage. The row is kept around for traceability
	 * (Updated / UpdatedBy show when and by whom it was deactivated); scan-time lookups already
	 * filter on IsActive='Y'.
	 */
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = I_M_HU.COLUMNNAME_HUStatus)
	public void deactivateQRCodeAssignmentsOnDestroy(@NonNull final I_M_HU hu)
	{
		if (!X_M_HU.HUSTATUS_Destroyed.equals(hu.getHUStatus()))
		{
			return;
		}

		huQRCodesService.deactivateAssignmentsByHuId(HuId.ofRepoId(hu.getM_HU_ID()));
	}
}
