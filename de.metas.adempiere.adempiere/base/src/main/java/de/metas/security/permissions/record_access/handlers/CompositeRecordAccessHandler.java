package de.metas.security.permissions.record_access.handlers;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import de.metas.security.permissions.record_access.RecordAccess;
import de.metas.security.permissions.record_access.RecordAccessFeature;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

@ToString
public final class CompositeRecordAccessHandler implements RecordAccessHandler
{
	public static final CompositeRecordAccessHandler of(@NonNull final Optional<List<RecordAccessHandler>> handlers)
	{
		return handlers
				.map(CompositeRecordAccessHandler::of)
				.orElse(CompositeRecordAccessHandler.EMPTY);
	}

	public static final CompositeRecordAccessHandler of(@NonNull final Collection<RecordAccessHandler> handlers)
	{
		if (handlers.isEmpty())
		{
			return CompositeRecordAccessHandler.EMPTY;
		}
		else
		{
			return new CompositeRecordAccessHandler(handlers);
		}
	}

	public static final CompositeRecordAccessHandler EMPTY = new CompositeRecordAccessHandler();

	private final ImmutableSet<RecordAccessHandler> handlers;
	@Getter
	private final ImmutableSet<RecordAccessFeature> handledFeatures;
	@Getter
	private final ImmutableSet<String> handledTableNames;

	private CompositeRecordAccessHandler()
	{
		handlers = ImmutableSet.of();
		handledFeatures = ImmutableSet.of();
		handledTableNames = ImmutableSet.of();
	}

	private CompositeRecordAccessHandler(@NonNull final Collection<RecordAccessHandler> handlers)
	{
		this.handlers = ImmutableSet.copyOf(handlers);

		handledFeatures = handlers.stream()
				.flatMap(handler -> handler.getHandledFeatures().stream())
				.collect(ImmutableSet.toImmutableSet());

		handledTableNames = handlers.stream()
				.flatMap(handler -> handler.getHandledTableNames().stream())
				.collect(ImmutableSet.toImmutableSet());
	}

	public boolean isEmpty()
	{
		return handlers.isEmpty();
	}

	@Override
	public void onAccessGranted(final RecordAccess request)
	{
		for (final RecordAccessHandler handler : handlers)
		{
			handler.onAccessGranted(request);
		}
	}

	@Override
	public void onAccessRevoked(final RecordAccess request)
	{
		for (final RecordAccessHandler handler : handlers)
		{
			handler.onAccessRevoked(request);
		}
	}

	public ImmutableSet<RecordAccessHandler> handlingFeatureSet(final Set<RecordAccessFeature> features)
	{
		if (features.isEmpty())
		{
			return ImmutableSet.of();
		}

		return handlers.stream()
				.filter(handler -> isAnyFeatureHandled(handler, features))
				.collect(ImmutableSet.toImmutableSet());
	}

	private static boolean isAnyFeatureHandled(final RecordAccessHandler handler, final Set<RecordAccessFeature> features)
	{
		return !Sets.intersection(handler.getHandledFeatures(), features).isEmpty();
	}

	public boolean isTableHandled(@NonNull final String tableName)
	{
		return getHandledTableNames().contains(tableName);
	}
}
