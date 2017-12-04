package de.metas.ui.web.view;

import java.util.HashMap;

import de.metas.ui.web.window.datatypes.WindowId;
import lombok.NonNull;
import lombok.ToString;

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

@ToString
public final class PlainDefaultViewProfileIdProvider implements DefaultViewProfileIdProvider
{
	private final HashMap<WindowId, ViewProfileId> defaultProfileIdByWindowId = new HashMap<>();

	@Override
	public ViewProfileId getDefaultProfileIdByWindowId(final WindowId windowId)
	{
		return defaultProfileIdByWindowId.get(windowId);
	}

	public void setDefaultProfileId(@NonNull final WindowId windowId, final ViewProfileId profileId)
	{
		if (ViewProfileId.isNull(profileId))
		{
			defaultProfileIdByWindowId.remove(windowId);
		}
		else
		{
			defaultProfileIdByWindowId.put(windowId, profileId);
		}
	}

	public void setDefaultProfileIdIfAbsent(@NonNull final WindowId windowId, @NonNull final ViewProfileId profileId)
	{
		defaultProfileIdByWindowId.putIfAbsent(windowId, profileId);
	}
}
