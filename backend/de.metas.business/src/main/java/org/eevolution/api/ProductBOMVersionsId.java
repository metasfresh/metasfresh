/*
 * #%L
 * de.metas.business
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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class ProductBOMVersionsId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	public static ProductBOMVersionsId ofRepoId(final int repoId)
	{
		return new ProductBOMVersionsId(repoId);
	}

	@Nullable
	public static ProductBOMVersionsId ofRepoIdOrNull(@Nullable final Integer repoId)
	{
		return repoId != null && repoId > 0 ? new ProductBOMVersionsId(repoId) : null;
	}

	private ProductBOMVersionsId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "PP_Product_BOMVersions_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static int toRepoId(@Nullable ProductBOMVersionsId id) {return id != null ? id.getRepoId() : -1;}
}
