package de.metas.order.copy;

import de.metas.copy_with_details.CopyRecordSupport;
import de.metas.copy_with_details.OnRecordCopiedListener;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.compiere.model.PO;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Basically does nothing.
 * The C_Order_Cost cloning is done after C_Order is closed,
 * and it's done for all C_Order_Cost records of that order.
 */
public class C_Order_Cost_CopyRecordSupport implements CopyRecordSupport
{
	@Override
	public Optional<PO> copyToNew(@NonNull final PO fromPO) {return Optional.empty();}

	@Override
	public void copyChildren(@NonNull final PO toPO, @NonNull final PO fromPO) {}

	@Override
	public CopyRecordSupport setParentLink(@NonNull final PO parentPO, @NonNull final String parentLinkColumnName) {return this;}

	@Override
	public CopyRecordSupport setAdWindowId(@Nullable final AdWindowId adWindowId) {return this;}

	@Override
	public CopyRecordSupport onRecordCopied(final OnRecordCopiedListener listener) {return this;}

	@Override
	public CopyRecordSupport onChildRecordCopied(final OnRecordCopiedListener listener) {return this;}
}
