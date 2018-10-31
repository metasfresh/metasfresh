package de.metas.ui.web.globalaction;

import javax.annotation.Nullable;

import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewProfileId;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.Value;

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

@Value(staticConstructor = "of")
public class OpenViewGlobalActionHandlerResult implements GlobalActionHandlerResult
{
	private static final String SEPARATOR = "#";

	public static final OpenViewGlobalActionHandlerResult ofPayload(final String payload)
	{
		Check.assumeNotEmpty(payload, "payload is not empty");
		final int idx = payload.indexOf(SEPARATOR);
		if (idx > 0)
		{
			final ViewId viewId = ViewId.ofViewIdString(payload.substring(0, idx));
			final ViewProfileId viewProfileId = ViewProfileId.fromJson(payload.substring(idx + SEPARATOR.length()));
			return of(viewId, viewProfileId);
		}
		else
		{
			final ViewId viewId = ViewId.ofViewIdString(payload);
			final ViewProfileId viewProfileId = null;
			return of(viewId, viewProfileId);
		}
	}

	public String toPayloadString()
	{
		if (viewProfileId != null)
		{
			return viewId.toJson() + SEPARATOR + viewProfileId.toJson();
		}
		else
		{
			return viewId.toJson();
		}
	}

	@NonNull
	ViewId viewId;
	@Nullable
	ViewProfileId viewProfileId;
}
