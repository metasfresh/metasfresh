/*
 * #%L
 * de.metas.workflow.rest-api
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

package de.metas.workflow.rest_api.service;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.workflow.rest_api.model.MobileApplicationId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

final class MobileApplicationsMap
{
	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	public static MobileApplicationsMap of(@NonNull final Optional<List<MobileApplication>> optionalMobileApplications)
	{
		final List<MobileApplication> applications = optionalMobileApplications.orElseGet(ImmutableList::of);
		return !applications.isEmpty()
				? new MobileApplicationsMap(applications)
				: EMPTY;
	}

	private static final MobileApplicationsMap EMPTY = new MobileApplicationsMap(ImmutableList.of());

	private final ImmutableList<MobileApplication> applications;
	private final ImmutableMap<MobileApplicationId, MobileApplication> applicationsById;

	private MobileApplicationsMap(@NonNull final List<MobileApplication> applications)
	{
		this.applications = ImmutableList.copyOf(applications);
		this.applicationsById = Maps.uniqueIndex(applications, MobileApplication::getApplicationId);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("applications", applications)
				.toString();
	}

	public Stream<MobileApplication> stream() {return applications.stream();}

	public MobileApplication getById(@NonNull final MobileApplicationId id)
	{
		final MobileApplication application = applicationsById.get(id);
		if (application == null)
		{
			throw new AdempiereException("No application found for " + id + ". Available applications are: " + applications);
		}
		return application;
	}
}
