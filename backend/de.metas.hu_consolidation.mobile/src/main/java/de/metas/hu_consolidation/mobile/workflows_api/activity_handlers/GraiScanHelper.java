package de.metas.hu_consolidation.mobile.workflows_api.activity_handlers;

import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.handlingunits.grai.GRAIRequired;
import de.metas.hu_consolidation.mobile.job.HUConsolidationJob;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.compiere.model.I_C_BPartner;

/**
 * Shared helper for GRAI-scan enablement logic used by both
 * {@link CompleteWFActivityHandler} and {@link HUConsolidateWFActivityHandler}.
 */
@UtilityClass
class GraiScanHelper
{
	/** {@code GRAIRequired != No} → GRAI scan is required (YesWithDummyGRAIs treated as Yes — no dummy-fill here). */
	static boolean isGraiScanEnabled(@NonNull final HUConsolidationJob job, @NonNull final IBPartnerDAO bpartnerDAO)
	{
		final I_C_BPartner bpartner = bpartnerDAO.getById(job.getCustomerId());
		final GRAIRequired graiRequired = GRAIRequired.optionalOfNullableCode(bpartner.getGRAIRequired())
				.orElse(GRAIRequired.No);
		return !graiRequired.isNo();
	}
}
