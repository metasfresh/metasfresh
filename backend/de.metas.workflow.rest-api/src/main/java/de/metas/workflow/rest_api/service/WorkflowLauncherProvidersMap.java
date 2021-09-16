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
import de.metas.workflow.rest_api.model.WorkflowLauncherProviderId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

final class WorkflowLauncherProvidersMap
{
	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	public static WorkflowLauncherProvidersMap of(@NonNull final Optional<List<WorkflowLauncherProvider>> optionalProviders)
	{
		final List<WorkflowLauncherProvider> providers = optionalProviders.orElseGet(ImmutableList::of);
		return !providers.isEmpty()
				? new WorkflowLauncherProvidersMap(providers)
				: EMPTY;
	}

	private static final WorkflowLauncherProvidersMap EMPTY = new WorkflowLauncherProvidersMap(ImmutableList.of());

	private final ImmutableList<WorkflowLauncherProvider> providers;
	private final ImmutableMap<WorkflowLauncherProviderId, WorkflowLauncherProvider> providersById;

	private WorkflowLauncherProvidersMap(@NonNull final List<WorkflowLauncherProvider> providers)
	{
		this.providers = ImmutableList.copyOf(providers);
		this.providersById = Maps.uniqueIndex(providers, WorkflowLauncherProvider::getId);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("providers", providers)
				.toString();
	}

	public Stream<WorkflowLauncherProvider> stream() {return providers.stream();}

	public WorkflowLauncherProvider getById(@NonNull final WorkflowLauncherProviderId providerId)
	{
		final WorkflowLauncherProvider provider = providersById.get(providerId);
		if (provider == null)
		{
			throw new AdempiereException("No provider found for " + providerId + ". Available providers are: " + providers);
		}
		return provider;
	}
}
