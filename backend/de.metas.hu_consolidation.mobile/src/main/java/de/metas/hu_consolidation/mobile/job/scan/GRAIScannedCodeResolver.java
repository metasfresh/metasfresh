package de.metas.hu_consolidation.mobile.job.scan;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.grai.GRAI;
import de.metas.handlingunits.qrcodes.mobile.MobileQRCodeMessages;
import de.metas.scannable_code.ScannedCode;
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
	public Optional<HUConsolidationScannedCrate> resolve(@NonNull final ScannedCode scannedCode, @NonNull final HUConsolidationJobCrates jobCrates)
	{
		final GRAI grai = GRAI.parse(scannedCode.getAsString());
		if (grai == null)
		{
			return Optional.empty();
		}

		final ImmutableSet<HuId> huIds = HUConsolidationCrateQuery.ofHuIds(handlingUnitsDAO, jobCrates.getHuIds())
				.map(query -> query.addOnlyWithAttribute(AttributeConstants.ATTR_GRAI, grai.toCanonicalString()).listIds())
				.orElse(ImmutableSet.of());

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
