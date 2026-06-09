package de.metas.handlingunits.picking.job.model.facets.delivery_day;

import de.metas.handlingunits.picking.job.model.facets.PickingJobFacet;
import de.metas.handlingunits.picking.job.model.facets.PickingJobFacetGroup;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

import java.time.LocalDate;
import java.time.ZoneId;

@Value(staticConstructor = "of")
public class DeliveryDayFacet implements PickingJobFacet
{
	@NonNull PickingJobFacetGroup group = PickingJobFacetGroup.DELIVERY_DATE;
	@With boolean isActive;
	@NonNull LocalDate deliveryDate;
	@NonNull ZoneId timeZone;
}
