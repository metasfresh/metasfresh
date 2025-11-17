package de.metas.handlingunits.picking.job.model;

public enum QtyAvailableStatus
{
	NOT_AVAILABLE,
	PARTIALLY_AVAILABLE,
	FULLY_AVAILABLE,
	;

	public boolean isPartialOrFullyAvailable() {return this == PARTIALLY_AVAILABLE || this == FULLY_AVAILABLE;}
}
