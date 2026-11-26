package de.metas.handlingunits.picking.job.model.facets.preparation_day;

import de.metas.handlingunits.picking.job.model.facets.PickingJobFacet;
import de.metas.handlingunits.picking.job.model.facets.PickingJobFacetGroup;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

import java.time.LocalDate;
import java.time.ZoneId;

@Value(staticConstructor = "of")
public class PreparationDayFacet implements PickingJobFacet
{
	@NonNull PickingJobFacetGroup group = PickingJobFacetGroup.PREPARATION_DATE;
	@With boolean isActive;
	@NonNull LocalDate preparationDate;
	@NonNull ZoneId timeZone;
}
