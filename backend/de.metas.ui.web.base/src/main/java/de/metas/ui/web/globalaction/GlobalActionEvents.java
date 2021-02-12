package de.metas.ui.web.globalaction;

import javax.annotation.Nullable;

import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewProfileId;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

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

@UtilityClass
public class GlobalActionEvents
{
	public static GlobalActionEvent openView(@NonNull final ViewId viewId, @Nullable final ViewProfileId profileId)
	{
		final String payload = OpenViewGlobalActionHandlerResult.of(viewId, profileId)
				.toPayloadString();

		return GlobalActionEvent.builder()
				.type(GlobalActionType.OPEN_VIEW)
				.payload(payload)
				.build();
	}

}
