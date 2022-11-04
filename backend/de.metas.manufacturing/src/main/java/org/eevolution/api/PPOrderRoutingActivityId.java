package org.eevolution.api;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.NumberUtils;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Objects;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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

/**
 * PP_Order_Node_ID
 */
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
public class PPOrderRoutingActivityId implements RepoIdAware
{
	@JsonCreator
	public static PPOrderRoutingActivityId ofRepoId(@NonNull final PPOrderId orderId, final int repoId)
	{
		return new PPOrderRoutingActivityId(orderId, repoId);
	}

	public static PPOrderRoutingActivityId ofRepoId(@NonNull final PPOrderId orderId, @NonNull final String repoIdString)
	{
		try
		{
			return new PPOrderRoutingActivityId(orderId, NumberUtils.asInt(repoIdString));
		}
		catch (Exception ex)
		{
			throw new AdempiereException("Invalid PPOrderRoutingActivityId for " + orderId + ", `" + repoIdString + "`", ex);
		}
	}

	@Nullable
	public static PPOrderRoutingActivityId ofRepoIdOrNull(@NonNull final PPOrderId orderId, final int repoId)
	{
		return repoId > 0 ? new PPOrderRoutingActivityId(orderId, repoId) : null;
	}

	public static int toRepoId(@Nullable final PPOrderRoutingActivityId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	PPOrderId orderId;
	int repoId;

	private PPOrderRoutingActivityId(@NonNull final PPOrderId orderId, final int repoId)
	{
		this.orderId = orderId;
		this.repoId = Check.assumeGreaterThanZero(repoId, "PP_Order_Node_ID");
	}

	@JsonValue
	public int toJson()
	{
		return getRepoId();
	}

	public static boolean equals(@Nullable final PPOrderRoutingActivityId id1, @Nullable final PPOrderRoutingActivityId id2)
	{
		return Objects.equals(id1, id2);
	}
}
