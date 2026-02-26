package de.metas.handlingunits.picking.job.model.facets;

import lombok.NonNull;

import javax.annotation.Nullable;

public interface PickingJobFacet
{
	@NonNull
	PickingJobFacetGroup getGroup();

	boolean isActive();

	PickingJobFacet withActive(boolean isActive);

	@Nullable
	default <T extends PickingJobFacet> T asTypeOrNull(@NonNull Class<T> type) {return type.isInstance(this) ? type.cast(this) : null;}

	@NonNull
	default <T extends PickingJobFacet> T asType(@NonNull Class<T> type) {return type.cast(this);}
}
