package de.metas.handlingunits.grai;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.attribute.IHUAttributesBL;
import de.metas.i18n.ExplainedOptional;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class HUGraiService
{
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final IHUAttributesBL huAttributesBL = Services.get(IHUAttributesBL.class);

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
