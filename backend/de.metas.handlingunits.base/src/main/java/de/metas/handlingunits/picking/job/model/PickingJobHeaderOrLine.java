package de.metas.handlingunits.picking.job.model;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;

import javax.annotation.Nullable;

interface PickingJobHeaderOrLine
{
	@Nullable
	BPartnerId getCustomerId();

	@Nullable
	BPartnerLocationId getDeliveryBPLocationId();

	@Nullable
	CurrentPickingTarget getCurrentPickingTarget();
}
