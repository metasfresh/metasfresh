package de.metas.rest_api.invoicecandidates.request;

import static de.metas.util.lang.CoalesceUtil.coalesce;

import de.metas.rest_api.SyncAdvise;
import de.metas.rest_api.SyncAdvise.IfExists;
import io.swagger.annotations.ApiModelProperty;
import lombok.NonNull;
import lombok.Value;

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

@Value
public class JsonUnsettablePrice
{
	public static final JsonUnsettablePrice EMPTY = new JsonUnsettablePrice(null, null);

	@ApiModelProperty(position = 10, required = false)
	JsonPrice price;

	@ApiModelProperty(position = 20, required = false, //
			value = "Optional property to *explicitly* unset a candidate's override property.\n"
					+ "If set to `true`, it takes precedence to a possibly set `value`")
	Boolean unsetValue;

	public boolean computeUnsetValue(@NonNull final SyncAdvise syncAdvise)
	{
		final boolean explicitlyUnsetDiscount = coalesce(this.getUnsetValue(), false);
		if (explicitlyUnsetDiscount)
		{
			return true;
		}

		final boolean implicitlyUnsetPrice = this.getPrice() == null && syncAdvise.getIfExists().isUpdateRemove();
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

		final boolean implicitlyDontSetPrice = this.getPrice() == null && dontChangeIfNotSet;
		if (implicitlyDontSetPrice)
		{
			return false;
		}
		return true;
	}
}
