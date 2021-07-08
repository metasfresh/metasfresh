package de.metas.ui.web.shipment_candidates_editor;

import java.util.List;
import java.util.Optional;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Evaluatee;

import com.google.common.collect.ImmutableList;

import de.metas.cache.CCache.CCacheStats;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.model.lookup.DocumentZoomIntoInfo;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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
public class MockedLookupDataSource implements LookupDataSource
{
	public static MockedLookupDataSource withNamePrefix(final String name)
	{
		return new MockedLookupDataSource(name);
	}

	private final String name;

	private MockedLookupDataSource(@NonNull final String name)
	{
		this.name = name;

	}

	@Override
	public LookupValue findById(final Object idObj)
	{
		if (idObj == null)
		{
			return null;
		}
		else
		{
			final int idInt = convertIdToInt(idObj);
			return IntegerLookupValue.of(idInt, name + " " + idInt);
		}
	}

	private static int convertIdToInt(@NonNull final Object idObj)
	{
		if (idObj instanceof Number)
		{
			return ((Number)idObj).intValue();
		}
		else if (idObj instanceof RepoIdAware)
		{
			return ((RepoIdAware)idObj).getRepoId();
		}
		else
		{
			throw new AdempiereException("Cannot convert `" + idObj + "` (" + idObj.getClass() + ") to `int`.");
		}
	}

	@Override
	public LookupValuesList findEntities(Evaluatee ctx, String filter, int firstRow, int pageLength)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public LookupValuesList findEntities(Evaluatee ctx, int pageLength)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public List<CCacheStats> getCacheStats()
	{
		return ImmutableList.of();
	}

	@Override
	public DocumentZoomIntoInfo getDocumentZoomInto(int id)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Optional<WindowId> getZoomIntoWindowId()
	{
		return Optional.empty();
	}

	@Override
	public void cacheInvalidate()
	{
		// nothing
	}

}
