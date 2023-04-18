/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.externalsystem.producttype;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;

/**
 * ProductType_External_Mapping
 */
@Value
public class ProductTypeExternalMappingId implements RepoIdAware
{
	@JsonValue
	int repoId;

	@JsonCreator
	public static ProductTypeExternalMappingId ofRepoId(final int productTypeExternalMappingId)
	{
		return new ProductTypeExternalMappingId(productTypeExternalMappingId);
	}

	@Nullable
	public static ProductTypeExternalMappingId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new ProductTypeExternalMappingId(repoId) : null;
	}

	private ProductTypeExternalMappingId(final int productTypeExternalMappingId)
	{
		this.repoId = Check.assumeGreaterThanZero(productTypeExternalMappingId, "ProductType_External_Mapping_ID");
	}
}
