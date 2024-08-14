package de.metas.copy_with_details;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.copy_with_details.template.CopyTemplate;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.compiere.model.PO;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public interface CopyRecordSupport
{
	default Optional<PO> copyToNew(@NonNull PO fromPO) {return copyToNew(fromPO, null);}

	/**
	 * Recursively copy given PO and it's children
	 *
	 * @return copied PO or empty if given PO shall not be copied for some reason
	 */
	Optional<PO> copyToNew(@NonNull PO fromPO, @Nullable CopyTemplate template);

	/**
	 * Recursively copy all childrens of <code>fromPO</code> to given <code>toPO</code>
	 */
	void copyChildren(@NonNull PO toPO, @NonNull PO fromPO);

	CopyRecordSupport setParentLink(@NonNull PO parentPO, @NonNull String parentLinkColumnName);

	CopyRecordSupport setAdWindowId(@Nullable AdWindowId adWindowId);

	/**
	 * Allows other modules to install custom code to be executed each time a record was copied.
	 * <p>
	 * <b>Important:</b> usually it makes sense to register a listener not here, but by invoking {@link CopyRecordFactory#registerCopyRecordSupport(String, Class)}.
	 * A listener that is registered there will be added to each CopyRecordSupport instance created by that factory.
	 */
	@SuppressWarnings("UnusedReturnValue")
	CopyRecordSupport onRecordCopied(OnRecordCopiedListener listener);

	default CopyRecordSupport onRecordCopied(final List<OnRecordCopiedListener> listeners)
	{
		listeners.forEach(this::onRecordCopied);
		return this;
	}

	CopyRecordSupport onChildRecordCopied(OnRecordCopiedListener listener);

	default CopyRecordSupport oChildRecordCopied(final List<OnRecordCopiedListener> listeners)
	{
		listeners.forEach(this::onChildRecordCopied);
		return this;
	}
}
