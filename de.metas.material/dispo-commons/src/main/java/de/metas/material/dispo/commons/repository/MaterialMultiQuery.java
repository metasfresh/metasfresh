package de.metas.material.dispo.commons.repository;

import java.util.Set;

import javax.annotation.Nullable;

import org.adempiere.util.Check;

import com.google.common.collect.ImmutableSet;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

/*
 * #%L
 * metasfresh-material-dispo-commons
 * %%
 * Copyright (C) 2017 metas GmbH
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
public class MaterialMultiQuery
{
	public static final MaterialMultiQuery of(@NonNull final MaterialQuery query)
	{
		return builder().query(query).build();
	}

	private final Set<MaterialQuery> queries;

	public static enum AggregationLevel
	{
		NONE, ATTRIBUTES_KEY
	};

	private final AggregationLevel aggregationLevel;

	@Builder
	private MaterialMultiQuery(
			@NonNull @Singular final ImmutableSet<MaterialQuery> queries,
			@Nullable final AggregationLevel aggregationLevel)
	{
		Check.assumeNotEmpty(queries, "queries is not empty");
		this.queries = queries;
		this.aggregationLevel = aggregationLevel != null ? aggregationLevel : AggregationLevel.ATTRIBUTES_KEY;
	}
}
