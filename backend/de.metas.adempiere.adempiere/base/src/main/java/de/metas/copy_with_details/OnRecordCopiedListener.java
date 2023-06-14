package de.metas.copy_with_details;

import org.compiere.model.PO;

@FunctionalInterface
public
interface OnRecordCopiedListener
{
	/**
	 * Called after the record was copied, right before saving it (and before it's children are copied)
	 */
	void onRecordCopied(final PO to, final PO from);
}
