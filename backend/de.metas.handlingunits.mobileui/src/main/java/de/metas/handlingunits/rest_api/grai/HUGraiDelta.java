package de.metas.handlingunits.rest_api.grai;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.grai.GRAISet;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class HUGraiDelta
{
	public static final HUGraiDelta EMPTY = new HUGraiDelta(ImmutableList.of(), GRAISet.EMPTY);

	@NonNull ImmutableList<AttributeChange> changes;
	@NonNull GRAISet unassignedGrais;

	public boolean isEmpty() {return changes.isEmpty() && unassignedGrais.isEmpty();}

	public boolean hasUnassignedGrais() {return !unassignedGrais.isEmpty();}

	@Value(staticConstructor = "of")
	public static class AttributeChange
	{
		@NonNull HuId huId;
		@NonNull GRAISet newValue; // GRAISet.EMPTY = clear the attribute
	}
}
