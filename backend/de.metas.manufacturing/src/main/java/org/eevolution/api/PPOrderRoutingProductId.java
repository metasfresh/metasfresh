/*
 * #%L
 * de.metas.manufacturing
 * %%
 * Copyright (C) 2021 metas GmbH
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

package org.eevolution.api;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

/**
 * PP_ORDER_NODE_PRODUCT_ID
 */
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
public class PPOrderRoutingProductId implements RepoIdAware
{
	PPOrderRoutingActivityId activityId;
	int repoId;

	public static PPOrderRoutingProductId ofRepoId(@NonNull final PPOrderRoutingActivityId orderId, final RepoIdAware repoId)
	{
		return ofRepoId(orderId, repoId.getRepoId());
	}

	@JsonCreator
	public static PPOrderRoutingProductId ofRepoId(@NonNull final PPOrderRoutingActivityId orderId, final int repoId)
	{
		return new PPOrderRoutingProductId(orderId, repoId);
	}

	@Nullable
	public static PPOrderRoutingProductId ofRepoIdOrNull(@NonNull final PPOrderRoutingActivityId orderId, final int repoId)
	{
		return repoId > 0 ? new PPOrderRoutingProductId(orderId, repoId) : null;
	}

	public static int toRepoId(@Nullable final PPOrderRoutingProductId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	private PPOrderRoutingProductId(@NonNull final PPOrderRoutingActivityId activityId, final int repoId)
	{
		this.activityId = activityId;
		this.repoId = Check.assumeGreaterThanZero(repoId, "PP_Order_Node_ID");
	}

	@JsonValue
	public int toJson()
	{
		return getRepoId();
	}

	public static boolean equals(@Nullable final PPOrderRoutingProductId id1, @Nullable final PPOrderRoutingProductId id2)
	{
		return Objects.equals(id1, id2);
	}
}
