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
import java.util.function.Supplier;

/**
 * Captures a document model's state before a document action runs on it, so that a retry of that action can start from that state.
 */
@FunctionalInterface
interface RetryModelSnapshotter
{
	/**
	 * @return a supplier of new model instances that each carry the given model's current state, or {@code null} if the model does not support that
	 */
	@Nullable
	Supplier<Object> snapshot(@NonNull Object model);

	/**
	 * Supports saved {@link PO}s: the snapshot is a {@link PO#copy()} (column values incl. unsaved changes, plus the dynamic attributes)
	 * whose subclass fields (e.g. flags or cached lines) are in the state of a freshly loaded instance.
	 */
	RetryModelSnapshotter PO_SNAPSHOTTER = model -> {
		final PO po = POWrapper.getStrictPO(model);
		if (po == null || po.is_new() || po.get_ID() <= 0)
		{
			return null;
		}

		final PO snapshot = copyIncludingDynAttributes(po);
		return () -> copyIncludingDynAttributes(snapshot);
	};

	static PO copyIncludingDynAttributes(@NonNull final PO po)
	{
		final PO copy = po.copy();
		copy.copyDynAttributesFrom(po);
		return copy;
	}
}
