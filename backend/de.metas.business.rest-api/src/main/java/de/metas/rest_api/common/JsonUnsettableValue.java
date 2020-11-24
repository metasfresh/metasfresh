package de.metas.rest_api.common;

import static de.metas.common.util.CoalesceUtil.coalesce;

import de.metas.rest_api.common.SyncAdvise.IfExists;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business.rest-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

/**
 * Can be used in requests to indicate that a property shall be set to {@code null}, as opposed to merely leave it untouched.
 */
public abstract class JsonUnsettableValue
{
	public boolean computeUnsetValue(@NonNull final SyncAdvise syncAdvise)
	{
		final boolean explicitlyUnsetDiscount = coalesce(this.getUnsetValue(), false);
		if (explicitlyUnsetDiscount)
		{
			return true;
		}

		final boolean implicitlyUnsetPrice = this.getValue() == null && syncAdvise.getIfExists().isUpdateRemove();
		if (implicitlyUnsetPrice)
		{
			return true;
		}
		return false;
	}

	public boolean computeSetValue(@NonNull final SyncAdvise syncAdvise)
	{
		final IfExists isExistsAdvise = syncAdvise.getIfExists();

		final boolean dontChangeAtAll = !isExistsAdvise.isUpdate();
		if (dontChangeAtAll)
		{
			return false;
		}

		final boolean dontChangeIfNotSet = dontChangeAtAll || isExistsAdvise.isUpdateMerge();

		final boolean implicitlyDontSetPrice = this.getValue() == null && dontChangeIfNotSet;
		if (implicitlyDontSetPrice)
		{
			return false;
		}
		return true;
	}

	protected abstract Object getValue();

	protected abstract Boolean getUnsetValue();
}
