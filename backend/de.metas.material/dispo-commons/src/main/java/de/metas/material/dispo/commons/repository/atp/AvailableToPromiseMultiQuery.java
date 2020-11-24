package de.metas.material.dispo.commons.repository.atp;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.util.Check;
import de.metas.common.util.CoalesceUtil;
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
public class AvailableToPromiseMultiQuery
{
	/**
	 * Creates a multi-query with one query for the given {@code materialDescriptor}
	 * and - if that descriptor has a specific partner - another one for ATP stuff that has no specific partner.
	 */
	public static AvailableToPromiseMultiQuery forDescriptorAndAllPossibleBPartnerIds(@NonNull final MaterialDescriptor materialDescriptor)
	{
		final AvailableToPromiseQuery bPartnerQuery = AvailableToPromiseQuery.forMaterialDescriptor(materialDescriptor);

		final AvailableToPromiseMultiQueryBuilder multiQueryBuilder = AvailableToPromiseMultiQuery.builder()
				.addToPredefinedBuckets(false)
				.query(bPartnerQuery);

		if (bPartnerQuery.getBpartner().isSpecificBPartner())
		{
			final AvailableToPromiseQuery noPartnerQuery = bPartnerQuery
					.toBuilder()
					.bpartner(BPartnerClassifier.none())
					.build();
			multiQueryBuilder.query(noPartnerQuery);
		}
		return multiQueryBuilder.build();
	}

	public static final AvailableToPromiseMultiQuery of(@NonNull final AvailableToPromiseQuery query)
	{
		return builder()
				.query(query)
				.addToPredefinedBuckets(DEFAULT_addToPredefinedBuckets)
				.build();
	}

	private final Set<AvailableToPromiseQuery> queries;

	private static final boolean DEFAULT_addToPredefinedBuckets = true;
	private final boolean addToPredefinedBuckets;

	@Builder
	private AvailableToPromiseMultiQuery(
			@NonNull @Singular final ImmutableSet<AvailableToPromiseQuery> queries,
			final Boolean addToPredefinedBuckets)
	{
		Check.assumeNotEmpty(queries, "queries is not empty");
		this.queries = queries;
		this.addToPredefinedBuckets = CoalesceUtil.coalesce(addToPredefinedBuckets, DEFAULT_addToPredefinedBuckets);
	}

	/**
	 * {@code true} means that the system will initially create one empty result group for the {@link AttributesKey}s of each included {@link AvailableToPromiseQuery}.
	 * ATP quantity that are retrieved may be added to multiple result groups, depending on those groups attributes keys
	 * If no ATP quantity matches a particular group, it will remain empty.<br>
	 * {@code false} means that the system will create result groups on the fly for the respective ATP records that it finds.
	 */
	public boolean isAddToPredefinedBuckets()
	{
		return addToPredefinedBuckets;
	}
}
