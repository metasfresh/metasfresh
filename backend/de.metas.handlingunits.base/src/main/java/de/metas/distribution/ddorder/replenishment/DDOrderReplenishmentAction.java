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
	/**
	 * Close-out disposition (in-progress): same close-out trigger as {@link #CLOSE}, but a replenishment move is
	 * already in progress (closing would corrupt a half-done move). The DD_Order is <b>disconnected</b>
	 * (IsPickingDisconnected=Y, FKs retained) so the worker can finish it as a standalone replenishment.
	 */
	DISCONNECT,
}
