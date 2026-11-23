package de.metas.hu_consolidation.mobile.job.scan;

import de.metas.handlingunits.picking.slot.PickingSlotQueues;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUQueryBuilder;
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
	public Optional<HUConsolidationScannedCrate> resolve(@NonNull final ScannedCode scannedCode, @NonNull final PickingSlotQueues queues)
	{
		final GRAI grai = GRAI.parse(scannedCode.getAsString());
		if (grai == null)
		{
			return Optional.empty();
		}

		final IHUQueryBuilder cratesQuery = HUConsolidationCrateQuery.ofHuIds(handlingUnitsDAO, queues.getHuIds())
				.orElseThrow(() -> huNotFound(grai))
				.addOnlyWithAttribute(AttributeConstants.ATTR_GRAI, grai.toCanonicalString());

		final HuId huId = HuId.ofRepoId(cratesQuery.firstOnlyNotNull(
				() -> huNotFound(grai),
				() -> new AdempiereException(MobileQRCodeMessages.HU_AMBIGUOUS).setParameter("grai", grai.toCanonicalString())
		).getM_HU_ID());
		return Optional.of(HUConsolidationScannedCrate.of(huId, queues.getPickingSlotId(huId)));
	}

	private static AdempiereException huNotFound(@NonNull final GRAI grai)
	{
		return new AdempiereException(MobileQRCodeMessages.HU_NOT_FOUND).setParameter("grai", grai.toCanonicalString());
	}
}
