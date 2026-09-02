package de.metas.ui.web.view.descriptor;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.ui.web.window.datatypes.WindowId;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Collection;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2026 metas GmbH
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

/**
 * Indexes the spring-discovered {@link SqlViewBindingCustomizer}s by {@link WindowId}.
 */
@ToString(of = "customizersByWindowId")
public final class SqlViewBindingCustomizerMap
{
	public static SqlViewBindingCustomizerMap ofCollection(@NonNull final Collection<SqlViewBindingCustomizer> customizers)
	{
		return new SqlViewBindingCustomizerMap(customizers);
	}

	private final ImmutableMap<WindowId, SqlViewBindingCustomizer> customizersByWindowId;

	private SqlViewBindingCustomizerMap(@NonNull final Collection<SqlViewBindingCustomizer> customizers)
	{
		this.customizersByWindowId = makeMapAndHandleDuplicates(customizers);
	}

	private static ImmutableMap<WindowId, SqlViewBindingCustomizer> makeMapAndHandleDuplicates(
			@NonNull final Collection<SqlViewBindingCustomizer> customizers)
	{
		try
		{
			return Maps.uniqueIndex(customizers, SqlViewBindingCustomizer::getWindowId);
		}
		catch (final IllegalArgumentException e)
		{
			throw new AdempiereException("The given collection of " + SqlViewBindingCustomizer.class.getSimpleName()
					+ " implementors contains more than one element with the same window-id", e)
					.setParameter("customizers", customizers)
					.appendParametersToMessage();
		}
	}

	@Nullable
	public SqlViewBindingCustomizer getOrNull(@NonNull final WindowId windowId)
	{
		return customizersByWindowId.get(windowId);
	}
}
