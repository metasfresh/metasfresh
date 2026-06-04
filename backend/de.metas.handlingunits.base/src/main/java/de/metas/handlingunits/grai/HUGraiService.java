package de.metas.handlingunits.grai;

import de.metas.handlingunits.HUContextHolder;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.attribute.IHUAttributesBL;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.i18n.ExplainedOptional;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class HUGraiService
{
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final IHUAttributesBL huAttributesBL = Services.get(IHUAttributesBL.class);
	@NonNull private final HUPIGraiRepository huPIGraiRepository;

	/**
	 * Returns the TU packing instruction configured for the given GRAI (matched by company-prefix and asset-type).
	 *
	 * @throws AdempiereException keyed on {@code de.metas.handlingunits.picking.GRAINoMatchingTUType}
	 *                            when no active GRAI-to-TU mapping exists for the given GRAI.
	 */
	@NonNull
	public HuPackingInstructionsId resolveHuPackingInstructionsId(@NonNull final GRAI grai)
	{
		return huPIGraiRepository.resolveHuPackingInstructionsId(grai);
	}

	@NonNull
	public ExplainedOptional<HUGraiSnapshot> getSnapshot(@NonNull final HuId huId)
	{
		return newLoader().loadById(huId);
	}

	@NonNull
	public HUGraiSnapshotsCollection getSnapshots(@NonNull final Set<HuId> huIds)
	{
		return newLoader().loadByIds(huIds);
	}

	private HUGraiSnapshotLoader newLoader()
	{
		return HUGraiSnapshotLoader.builder()
				.handlingUnitsBL(handlingUnitsBL)
				.handlingUnitsDAO(handlingUnitsDAO)
				.huAttributesBL(huAttributesBL)
				.build();
	}

	public void setGrais(@NonNull final HuId huId, @NonNull final GRAISet graiSet)
	{
		final HUGraiSnapshot snapshot = getSnapshot(huId).orElseThrow();
		final HUGraiDelta delta = snapshot.computeDelta(graiSet);
		applyDelta(delta);
	}

	/**
	 * Stamps the given GRAIs onto the HU <b>inside the ambient pick HU context</b> ({@link HUContextHolder#getCurrent()}),
	 * so the attribute write is flushed together with the surrounding pick transaction's commit.
	 * <p>
	 * This is the durable counterpart of {@link #setGrais(HuId, GRAISet)} for the pick-time stamp path. The plain
	 * {@link #setGrais(HuId, GRAISet)} routes the write through {@link IHUAttributesBL#updateHUAttribute} which opens its
	 * <i>own</i> ephemeral HU context whose save-buffer is never committed when invoked from within the pick transaction —
	 * the GRAI write on the freshly-materialised TU is then silently lost. Writing through the ambient context with
	 * {@code setSaveOnChange(true)} mirrors the proven-durable sibling pattern in
	 * {@code PickedHUAttributesUpdater.updateHUPickAttributes} (lot/best-before/production-date at pick time) and
	 * {@code PackedHUWeightNetUpdater} (catch weight), both of which persist correctly.
	 * <p>
	 * MUST be called inside the pick transaction (with the pick HU context set in {@link HUContextHolder}). It is NOT a
	 * drop-in replacement for {@link #setGrais(HuId, GRAISet)} on paths that run without an ambient HU context (e.g. the
	 * mobileui REST {@code setGRAIs} endpoint, or the dummy-GRAI generation in {@code generateMissingGRAIs}).
	 */
	public void setGraisInAmbientContext(@NonNull final HuId huId, @NonNull final GRAISet graiSet)
	{
		final HUGraiSnapshot snapshot = getSnapshot(huId).orElseThrow();
		final HUGraiDelta delta = snapshot.computeDelta(graiSet);
		if (delta.hasUnassignedGrais())
		{
			throw new AdempiereException("Not enough TU slots for GRAIs: " + delta.getUnassignedGrais());
		}

		delta.getChanges().forEach(this::applyChangeInAmbientContext);
	}

	private void applyChangeInAmbientContext(@NonNull final HUGraiDelta.AttributeChange change)
	{
		final I_M_HU hu = handlingUnitsBL.getById(change.getHuId());
		final IAttributeStorage attributeStorage = HUContextHolder.getCurrent()
				.getHUAttributeStorageFactory()
				.getAttributeStorage(hu);
		attributeStorage.setSaveOnChange(true);

		if (attributeStorage.hasAttribute(AttributeConstants.ATTR_GRAI))
		{
			attributeStorage.setValue(
					AttributeConstants.ATTR_GRAI,
					GRAISet.toCommaSeparatedStringOrNull(change.getNewValue()));
		}
	}

	public void generateMissingGRAIs(@NonNull final HUGraiSnapshotsCollection snapshots, @NonNull final DummyGRAIProvider nextGraiProvider)
	{
		for (final HUGraiSnapshot snapshot : snapshots)
		{
			final HUGraiDelta delta = snapshot.generateMissingGRAIs(nextGraiProvider);
			applyDelta(delta);
		}
	}

	private void applyDelta(@NonNull final HUGraiDelta delta)
	{
		if (delta.hasUnassignedGrais())
		{
			throw new AdempiereException("Not enough TU slots for GRAIs: " + delta.getUnassignedGrais());
		}

		delta.getChanges().forEach(this::applyChange);
	}

	private void applyChange(@NonNull final HUGraiDelta.AttributeChange change)
	{
		huAttributesBL.updateHUAttribute(
				change.getHuId(),
				AttributeConstants.ATTR_GRAI,
				GRAISet.toCommaSeparatedStringOrNull(change.getNewValue()));
	}
}
