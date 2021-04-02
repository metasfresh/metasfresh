/*
 * #%L
 * de.metas.ui.web.base
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

package de.metas.ui.web.dashboard.websocket;

import de.metas.ui.web.dashboard.UserDashboardId;
import de.metas.ui.web.websocket.WebSocketProducer;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import lombok.NonNull;

class UserDashboardWebsocketProducer implements WebSocketProducer
{
	private final UserDashboardId userDashboardId;

	public UserDashboardWebsocketProducer(@NonNull final UserDashboardId userDashboardId)
	{
		this.userDashboardId = userDashboardId;
	}

	@Override
	public Object produceEvent(final JSONOptions jsonOpts)
	{
		// TODO
		return null;
	}
}
