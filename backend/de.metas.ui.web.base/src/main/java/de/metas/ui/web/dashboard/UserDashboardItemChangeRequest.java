package de.metas.ui.web.dashboard;

import de.metas.printing.esb.base.util.Check;
import de.metas.ui.web.dashboard.UserDashboardRepository.DashboardItemPatchPath;
import de.metas.ui.web.dashboard.json.JsonUserDashboardItemAddRequest;
import de.metas.ui.web.dashboard.json.JsonUserDashboardItemAddRequest.JSONInterval;
import de.metas.ui.web.dashboard.json.JsonUserDashboardItemAddRequest.JSONWhen;
import de.metas.ui.web.window.datatypes.json.JSONPatchEvent;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

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

/**
 * Dashboard item change request
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@Builder
@Value
class UserDashboardItemChangeRequest
{
	public static UserDashboardItemChangeRequest of(
			final DashboardWidgetType widgetType,
			final UserDashboardItemId itemId,
			final String adLanguage,
			@NonNull final List<JSONPatchEvent<DashboardItemPatchPath>> events)
	{
		if (events.isEmpty())
		{
			throw new AdempiereException("no events");
		}

		final UserDashboardItemChangeRequest.UserDashboardItemChangeRequestBuilder changeRequestBuilder = UserDashboardItemChangeRequest.builder()
				.itemId(itemId)
				.widgetType(widgetType)
				.adLanguage(adLanguage)
				.position(-1);

		//
		// Extract change actions
		for (final JSONPatchEvent<DashboardItemPatchPath> event : events)
		{
			if (!event.isReplace())
			{
				throw new AdempiereException("Invalid event operation").setParameter("event", event);
			}

			final DashboardItemPatchPath path = event.getPath();
			if (DashboardItemPatchPath.caption.equals(path))
			{
				final String caption = event.getValueAsString();
				changeRequestBuilder.caption(caption);
			}
			else if (DashboardItemPatchPath.interval.equals(path))
			{
				final JSONInterval interval = event.getValueAsEnum(JsonUserDashboardItemAddRequest.JSONInterval.class);
				changeRequestBuilder.interval(interval);
			}
			else if (DashboardItemPatchPath.when.equals(path))
			{
				final JSONWhen when = event.getValueAsEnum(JsonUserDashboardItemAddRequest.JSONWhen.class);
				changeRequestBuilder.when(when);
			}
			else if (DashboardItemPatchPath.position.equals(path))
			{
				final int position = event.getValueAsInteger(-1);
				changeRequestBuilder.position(position);
			}
			else
			{
				throw new AdempiereException("Unknown path").setParameter("event", event).setParameter("availablePaths", Arrays.asList(DashboardItemPatchPath.values()));
			}
		}

		final UserDashboardItemChangeRequest changeRequest = changeRequestBuilder.build();
		if (changeRequest.isEmpty())
		{
			throw new AdempiereException("no changes to perform").setParameter("events", events);
		}
		return changeRequest;
	}

	@Nullable UserDashboardItemId itemId;
	@NonNull DashboardWidgetType widgetType;
	@NonNull String adLanguage;

	// Changes
	String caption;
	JSONInterval interval;
	JSONWhen when;
	int position;

	public boolean isEmpty()
	{
		return Check.isEmpty(caption, true)
				&& interval == null
				&& when == null
				&& position < 0;
	}
}
