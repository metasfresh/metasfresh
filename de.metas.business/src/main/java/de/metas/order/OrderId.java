package de.metas.order;

import de.metas.lang.RepoIdAware;
import de.metas.util.Check;
import lombok.Value;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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
public class OrderId implements RepoIdAware
{
	public static OrderId ofRepoId(final int repoId)
	{
		return new OrderId(repoId);
	}

	public static OrderId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new OrderId(repoId) : null;
	}

	public static int getRepoIdOr(final OrderId orderId, final int defaultValue)
	{
		return orderId != null ? orderId.getRepoId() : defaultValue;
	}

	int repoId;

	private OrderId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "repoId");
	}
}
