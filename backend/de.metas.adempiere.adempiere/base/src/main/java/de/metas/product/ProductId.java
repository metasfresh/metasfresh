package de.metas.product;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableSet;
import de.metas.adempiere.model.I_M_Product;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;
import org.adempiere.util.lang.impl.TableRecordReference;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

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
public class ProductId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	public static ProductId ofRepoId(final int repoId)
	{
		return new ProductId(repoId);
	}

	@Nullable
	public static ProductId ofRepoIdOrNull(@Nullable final Integer repoId)
	{
		return repoId != null && repoId > 0 ? new ProductId(repoId) : null;
	}

	@Nullable
	public static ProductId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new ProductId(repoId) : null;
	}

	public static Optional<ProductId> optionalOfRepoId(final int repoId) {return Optional.ofNullable(ofRepoIdOrNull(repoId));}

	public static Set<ProductId> ofRepoIds(final Collection<Integer> repoIds)
	{
		return repoIds.stream()
				.filter(repoId -> repoId != null && repoId > 0)
				.map(ProductId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
	}

	public static int toRepoId(@Nullable final ProductId productId)
	{
		return productId != null ? productId.getRepoId() : -1;
	}

	public static Set<Integer> toRepoIds(final Collection<ProductId> productIds)
	{
		return productIds.stream()
				.filter(Objects::nonNull)
				.map(ProductId::toRepoId)
				.collect(ImmutableSet.toImmutableSet());
	}

	public static boolean equals(@Nullable final ProductId o1, @Nullable final ProductId o2)
	{
		return Objects.equals(o1, o2);
	}

	private ProductId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "productId");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public String getAsString() {return String.valueOf(getRepoId());}

	public TableRecordReference toTableRecordReference()
	{
		return TableRecordReference.of(I_M_Product.Table_Name, getRepoId());
	}
}
