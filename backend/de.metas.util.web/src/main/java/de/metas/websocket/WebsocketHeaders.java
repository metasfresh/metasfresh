/*
 * #%L
 * de.metas.util.web
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.websocket;

import lombok.NonNull;
import lombok.Value;

import java.util.List;
import java.util.Map;

@Value
public class WebsocketHeaders
{
	public static WebsocketHeaders of(@NonNull final Map<String, List<String>> headers)
	{
		return new WebsocketHeaders(headers);
	}

	@NonNull
	Map<String, List<String>> headers;

	private WebsocketHeaders(@NonNull final Map<String, List<String>> headers)
	{
		this.headers = headers;
	}
}
