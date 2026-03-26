package de.metas.handlingunits.rest_api.grai;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.attribute.IHUAttributesBL;
import de.metas.handlingunits.grai.GRAISet;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.AttributeConstants;

@Builder
public class SetHUGraiCommand
{
	@NonNull private final IHUAttributesBL huAttributesBL = Services.get(IHUAttributesBL.class);

	@NonNull private final HuId huId;
	@NonNull private final GRAISet graiSet;

	public void execute()
	{
		final HUGraiSnapshot snapshot = HUGraiSnapshotLoader.builder().huId(huId).build().load();
		final HUGraiDelta delta = snapshot.computeDelta(graiSet);
		applyDelta(delta);
	}

	private void applyDelta(@NonNull final HUGraiDelta delta)
	{
		if (delta.hasUnassignedGrais())
		{
			throw new AdempiereException("Not enough TU slots on HU " + huId + " for GRAIs: " + delta.getUnassignedGrais());
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
