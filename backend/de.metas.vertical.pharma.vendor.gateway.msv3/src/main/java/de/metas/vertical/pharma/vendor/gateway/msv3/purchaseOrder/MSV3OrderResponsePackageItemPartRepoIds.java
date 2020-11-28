package de.metas.vertical.pharma.vendor.gateway.msv3.purchaseOrder;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

import de.metas.vendor.gateway.api.order.MSV3OrderResponsePackageItemPartRepoId;
import de.metas.vertical.pharma.msv3.protocol.order.OrderResponsePackageItemPartId;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * metasfresh-pharma.vendor.gateway.msv3
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

@ToString
@EqualsAndHashCode
final class MSV3OrderResponsePackageItemPartRepoIds
{
	public static MSV3OrderResponsePackageItemPartRepoIds newMutableInstance()
	{
		return new MSV3OrderResponsePackageItemPartRepoIds(new HashMap<>());
	}

	private final Map<OrderResponsePackageItemPartId, MSV3OrderResponsePackageItemPartRepoId> repoIds;

	private MSV3OrderResponsePackageItemPartRepoIds(@NonNull final Map<OrderResponsePackageItemPartId, MSV3OrderResponsePackageItemPartRepoId> repoIds)
	{
		this.repoIds = repoIds;
	}

	public MSV3OrderResponsePackageItemPartRepoIds copyAsImmutable()
	{
		return new MSV3OrderResponsePackageItemPartRepoIds(ImmutableMap.copyOf(repoIds));
	}

	public MSV3OrderResponsePackageItemPartRepoId getRepoId(final OrderResponsePackageItemPartId partId)
	{
		return repoIds.get(partId);
	}

	public void putRepoId(@NonNull final OrderResponsePackageItemPartId partId, @NonNull final MSV3OrderResponsePackageItemPartRepoId repoId)
	{
		repoIds.put(partId, repoId);
	}
}
