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

import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.compiere.model.PO;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

/**
 * @author Cristina Ghita, METAS.RO
 */
public interface CopyRecordSupport
{
	/**
	 * Recursively copy given PO and it's children
	 *
	 * @return copied PO or empty if given PO shall not be copied for some reason
	 */
	Optional<PO> copyToNew(@NonNull PO fromPO);

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
	CopyRecordSupport onRecordCopied(IOnRecordCopiedListener listener);

	default CopyRecordSupport onRecordCopied(List<IOnRecordCopiedListener> listeners)
	{
		listeners.forEach(this::onRecordCopied);
		return this;
	}

	CopyRecordSupport onChildRecordCopied(IOnRecordCopiedListener listener);

	default CopyRecordSupport oChildRecordCopied(List<IOnRecordCopiedListener> listeners)
	{
		listeners.forEach(this::onChildRecordCopied);
		return this;
	}

	@FunctionalInterface
	interface IOnRecordCopiedListener
	{
		/**
		 * Called after the record was copied, right before saving it (and before it's children are copied)
		 */
		void onRecordCopied(final PO to, final PO from);
	}
}
