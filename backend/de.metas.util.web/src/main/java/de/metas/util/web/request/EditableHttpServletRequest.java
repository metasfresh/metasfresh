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

package de.metas.util.web.request;

import de.metas.util.web.audit.dto.CachedBodyHttpServletRequest;
import lombok.NonNull;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

public class EditableHttpServletRequest extends CachedBodyHttpServletRequest
{
	private final Map<String, String> httpHeadersMap = new HashMap<>();

	public EditableHttpServletRequest(final HttpServletRequest httpServletRequest) throws IOException
	{
		super(httpServletRequest);
	}

	@Override
	public String getHeader(@NonNull final String name)
	{
		if (httpHeadersMap.containsKey(name))
		{
			return httpHeadersMap.get(name);
		}

		return super.getHeader(name);
	}

	@Override
	public Enumeration<String> getHeaderNames()
	{
		final HashSet<String> names = new HashSet<>(Collections.list(super.getHeaderNames()));
		names.addAll(httpHeadersMap.keySet());
		return Collections.enumeration(names);
	}

	@Override
	public Enumeration<String> getHeaders(@NonNull final String name)
	{
		final ArrayList<String> values = Collections.list(super.getHeaders(name));

		if (httpHeadersMap.containsKey(name))
		{
			values.add(httpHeadersMap.get(name));
		}

		return Collections.enumeration(values);
	}

	public void setHeader(@NonNull final String name, @NonNull final String value)
	{
		httpHeadersMap.put(name, value);
	}

	@Nullable
	public String getRequestBodyAsString() throws IOException
	{
		if (getContentLength() <= 0)
		{
			return null;
		}

		return new BufferedReader(
				new InputStreamReader(getInputStream(), StandardCharsets.UTF_8))
				.lines()
				.collect(Collectors.joining("\n"));
	}
}
