package org.eevolution.api;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableSet;

import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

/*
 * #%L
 * de.metas.business
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
public class ProductBOMLineId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	public static ProductBOMLineId ofRepoId(final int repoId)
	{
		return new ProductBOMLineId(repoId);
	}

	public static ProductBOMLineId ofRepoIdOrNull(@Nullable final Integer repoId)
	{
		return repoId != null && repoId > 0 ? new ProductBOMLineId(repoId) : null;
	}

	public static ProductBOMLineId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new ProductBOMLineId(repoId) : null;
	}

	public static Optional<ProductBOMLineId> optionalOfRepoId(final int repoId)
	{
		return Optional.ofNullable(ofRepoIdOrNull(repoId));
	}

	public static Set<ProductBOMLineId> ofRepoIds(final Collection<Integer> repoIds)
	{
		return repoIds.stream()
				.filter(repoId -> repoId != null && repoId > 0)
				.map(ProductBOMLineId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
	}

	public static int toRepoId(final ProductBOMLineId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	public static boolean equals(final ProductBOMLineId o1, final ProductBOMLineId o2)
	{
		return Objects.equals(o1, o2);
	}

	private ProductBOMLineId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "PP_Product_BOMLine_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
