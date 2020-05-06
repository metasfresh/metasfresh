package de.metas.product;

import java.util.Objects;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

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
public class ProductPlanningSchemaId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	public static ProductPlanningSchemaId ofRepoId(final int repoId)
	{
		return new ProductPlanningSchemaId(repoId);
	}

	public static ProductPlanningSchemaId ofRepoIdOrNull(@Nullable final Integer repoId)
	{
		return repoId != null && repoId > 0 ? new ProductPlanningSchemaId(repoId) : null;
	}

	public static ProductPlanningSchemaId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new ProductPlanningSchemaId(repoId) : null;
	}

	public static int toRepoId(final ProductPlanningSchemaId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	public static boolean equals(final ProductPlanningSchemaId o1, final ProductPlanningSchemaId o2)
	{
		return Objects.equals(o1, o2);
	}

	private ProductPlanningSchemaId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "M_Product_PlanningSchema_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
