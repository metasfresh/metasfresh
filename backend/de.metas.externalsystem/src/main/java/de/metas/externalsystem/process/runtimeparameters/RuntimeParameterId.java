/*
 * #%L
 * de.metas.externalsystem
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

package de.metas.externalsystem.process.runtimeparameters;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.order.OrderId;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class RuntimeParameterId implements RepoIdAware
{
	@JsonCreator
	public static RuntimeParameterId ofRepoId(final int repoId)
	{
		return new RuntimeParameterId(repoId);
	}

	public static RuntimeParameterId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new RuntimeParameterId(repoId) : null;
	}

	public static int toRepoId(@Nullable final OrderId orderId)
	{
		return orderId != null ? orderId.getRepoId() : -1;
	}

	int repoId;

	private RuntimeParameterId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "ExternalSystem_RuntimeParameter_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
