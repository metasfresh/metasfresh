package de.metas.handlingunits.picking;

/** The context a qty-rejected/dispose reason list is served for. See {@link QtyRejectedReasonCode#reasonsFor(de.metas.ad_reference.ADRefList, QtyRejectedReasonContext)}. */
public enum QtyRejectedReasonContext
{
	Picking,
	Distribution,
	InventoryDisposal,
	ManufacturingIssue
}
