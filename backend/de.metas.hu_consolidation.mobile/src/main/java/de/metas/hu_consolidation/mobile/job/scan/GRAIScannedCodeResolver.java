package de.metas.hu_consolidation.mobile.job.scan;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.grai.GRAI;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.slot.PickingSlotQueues;
import de.metas.handlingunits.qrcodes.mobile.MobileQRCodeMessages;
import de.metas.scannable_code.ScannedCode;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.springframework.stereotype.Component;

import java.util.List;
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

		return Optional.of(resolve(grai, queues));
	}

	/**
	 * @return the one crate among the HUs of {@code queues} that carries the GRAI; a HU with that GRAI outside {@code queues} is never looked at.
	 * @throws AdempiereException {@code HU_NOT_FOUND} if no crate matches (also when {@code queues} is empty, without any query),
	 *                            {@code HU_AMBIGUOUS} if more than one matches.
	 */
	@NonNull
	public HUConsolidationScannedCrate resolve(@NonNull final GRAI grai, @NonNull final PickingSlotQueues queues)
	{
		return resolveIfAny(grai, queues).orElseThrow(() -> huNotFound(grai));
	}

	/**
	 * Like {@link #resolve(GRAI, PickingSlotQueues)}, but no matching crate gives empty instead of {@code HU_NOT_FOUND}.
	 *
	 * @throws AdempiereException {@code HU_AMBIGUOUS} if more than one crate matches
	 */
	public Optional<HUConsolidationScannedCrate> resolveIfAny(@NonNull final GRAI grai, @NonNull final PickingSlotQueues queues)
	{
		final IHUQueryBuilder cratesQuery = HUConsolidationCrateQuery.ofHuIds(handlingUnitsDAO, queues.getHuIds()).orElse(null);
		if (cratesQuery == null)
		{
			return Optional.empty();
		}

		final List<I_M_HU> crates = cratesQuery
				.addOnlyWithAttribute(AttributeConstants.ATTR_GRAI, grai.toCanonicalString())
				.list(2);
		if (crates.isEmpty())
		{
			return Optional.empty();
		}
		if (crates.size() > 1)
		{
			throw new AdempiereException(MobileQRCodeMessages.HU_AMBIGUOUS).setParameter("grai", grai.toCanonicalString());
		}

		final HuId huId = HuId.ofRepoId(crates.get(0).getM_HU_ID());
		return Optional.of(HUConsolidationScannedCrate.of(huId, queues.getPickingSlotId(huId)));
	}

	private static AdempiereException huNotFound(@NonNull final GRAI grai)
	{
		return new AdempiereException(MobileQRCodeMessages.HU_NOT_FOUND).setParameter("grai", grai.toCanonicalString());
	}
}
