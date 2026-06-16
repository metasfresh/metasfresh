package de.metas.distribution.ddorder.replenishment;

public enum DDOrderReplenishmentAction
{
	NONE,
	CREATE,
	RECREATE,
	VOID,
	/**
	 * Close-out disposition (not-in-progress): the shipment schedule was closed out (Processed=Y) and the linked
	 * replenishment DD_Order is obsolete. It is <b>Closed</b> (not Voided) — delivered/moved stock is preserved,
	 * the open remainder is closed off — and the DD_Order-backed mobile DistributionJob is released.
	 */
	CLOSE,
}
