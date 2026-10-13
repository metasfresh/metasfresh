/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.document.engine.impl;


import lombok.NonNull;
import org.adempiere.model.POWrapper;
import org.compiere.model.PO;

import javax.annotation.Nullable;

/**
 * Captures a document model's in-memory state before a document action runs on it, so that the state can be put back before the action is retried.
 */
@FunctionalInterface
interface RetryStateSnapshotter
{
	/**
	 * @return an action that puts the given model back into its current state, or {@code null} if the model is not supported
	 */
	@Nullable
	Runnable snapshot(@NonNull Object model);

	/**
	 * Supports saved {@link PO}s, see {@link PO#snapshotStateForRetry()}. A new record, or a model that is not a PO (e.g. a POJO or a
	 * {@code GridTab}-backed model), is not supported.
	 */
	RetryStateSnapshotter PO_SNAPSHOTTER = model -> {
		final PO po = POWrapper.getStrictPO(model);
		if (po == null || po.is_new())
		{
			return null;
		}

		final PO.RetryStateSnapshot snapshot = po.snapshotStateForRetry();
		return () -> po.restoreStateForRetry(snapshot);
	};
}
