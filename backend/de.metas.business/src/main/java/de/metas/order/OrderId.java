package de.metas.order;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

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
	@JsonCreator
	public static OrderId ofRepoId(final int repoId)
	{
		return new OrderId(repoId);
	}

	@Nullable
	public static OrderId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new OrderId(repoId) : null;
	}

	public static int toRepoId(@Nullable final OrderId orderId)
	{
		return orderId != null ? orderId.getRepoId() : -1;
	}

	int repoId;

	private OrderId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "C_Order_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static boolean equals(@Nullable final OrderId id1, @Nullable final OrderId id2)
	{
		return Objects.equals(id1, id2);
	}
}
