package de.metas.hu_consolidation.mobile.job.scan;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.grai.GRAI;
import de.metas.handlingunits.qrcodes.mobile.MobileQRCodeMessages;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Resolves a scanned GRAI to the one active top-level crate of the job that carries it.
 */
@Component
public class GRAIScannedCodeResolver implements HUConsolidationScannedCodeResolver
{
	@NonNull private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

	@Override
	public Optional<HUConsolidationScannedCrate> resolve(@NonNull final String scannedCode, @NonNull final HUConsolidationJobCrates jobCrates)
	{
		final GRAI grai = GRAI.parse(scannedCode);
		if (grai == null)
		{
			return Optional.empty();
		}

		// Without this guard HUQueryBuilder.addOnlyHUIds(empty) drops the filter and the query would run over all active HUs
		if (jobCrates.isEmpty())
		{
			throw new AdempiereException(MobileQRCodeMessages.HU_NOT_FOUND).setParameter("grai", grai.toCanonicalString());
		}

		final ImmutableSet<HuId> huIds = handlingUnitsDAO
				.createHUQueryBuilder()
				.setOnlyActiveHUs(true)
				.setOnlyTopLevelHUs()
				.addOnlyHUIds(jobCrates.getHuIds())
				.addOnlyWithAttribute(AttributeConstants.ATTR_GRAI, grai.toCanonicalString())
				.listIds();

		if (huIds.isEmpty())
		{
			throw new AdempiereException(MobileQRCodeMessages.HU_NOT_FOUND).setParameter("grai", grai.toCanonicalString());
		}
		if (huIds.size() > 1)
		{
			throw new AdempiereException(MobileQRCodeMessages.HU_AMBIGUOUS).setParameter("grai", grai.toCanonicalString());
		}

		final HuId huId = huIds.iterator().next();
		return Optional.of(HUConsolidationScannedCrate.of(huId, jobCrates.getPickingSlotId(huId)));
	}
}
