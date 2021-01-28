package de.metas.ui.web.menu.datatypes.json;

import java.util.List;
import java.util.Set;

import org.adempiere.exceptions.AdempiereException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableSet;

import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent.JSONOperation;

/*
 * #%L
 * metasfresh-webui-api
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

public class JSONPatchMenuNodeRequest
{
	public static final JSONPatchMenuNodeRequest ofChangeEvents(final List<JSONDocumentChangedEvent> events)
	{

		if (events == null || events.isEmpty())
		{
			throw new AdempiereException("No events");
		}

		Boolean favorite = null;
		if (events != null && !events.isEmpty())
		{
			for (final JSONDocumentChangedEvent event : events)
			{
				if (!event.isReplace())
				{
					throw new AdempiereException("Only " + JSONOperation.replace + " are supported")
							.setParameter("event", event);
				}

				if (PATH_Favorite.equals(event.getPath()))
				{
					favorite = event.getValueAsBoolean(null);
					if (favorite == null)
					{
						throw new AdempiereException("Invalid value for " + PATH_Favorite)
								.setParameter("event", event);
					}
				}
				else
				{
					throw new AdempiereException("Unknown path: " + event.getPath())
							.setParameter("event", event)
							.setParameter("availablePaths", PATHS);
				}
			}
		}

		// Make sure we have at least on actual change
		if (favorite == null)
		{
			throw new AdempiereException("None of the requested changes are supported")
					.setParameter("events", events);
		}

		return new JSONPatchMenuNodeRequest(favorite);
	}

	private static final String PATH_Favorite = "favorite";
	private static final Set<String> PATHS = ImmutableSet.of(PATH_Favorite);

	@JsonProperty("favorite")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final Boolean favorite;

	@JsonCreator
	private JSONPatchMenuNodeRequest(@JsonProperty("favorite") final Boolean favorite)
	{
		this.favorite = favorite;
	}

	public Boolean getFavorite()
	{
		return favorite;
	}
}
