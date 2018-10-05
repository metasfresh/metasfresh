package de.metas.ui.web.globalaction;

import org.springframework.stereotype.Component;

import de.metas.ui.web.view.ViewId;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Component
public class OpenViewGlobalActionHandler implements GlobalActionHandler
{

	@Override
	public GlobalActionType getTypeHandled()
	{
		return GlobalActionType.OPEN_VIEW;
	}

	@Override
	public GlobalActionHandlerResult handleEvent(final GlobalActionEvent event)
	{
		final ViewId viewId = ViewId.ofViewIdString(event.getPayload());
		return OpenViewGlobalActionHandlerResult.of(viewId);
	}

	public GlobalActionEvent createEvent(@NonNull final ViewId viewId)
	{
		return GlobalActionEvent.builder()
				.type(GlobalActionType.OPEN_VIEW)
				.payload(viewId.toJson())
				.build();
	}
}
