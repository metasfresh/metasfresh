/*
 * #%L
 * de-metas-camel-externalsystems-core
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

package de.metas.camel.externalsystems.core.restapi.auth.request;

import lombok.NonNull;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class RequestWrapper extends HttpServletRequestWrapper
{
	private final Map<String, String> headerMap = new HashMap<>();

	/**
	 * Constructs a request object wrapping the given request.
	 *
	 * @param request The request to wrap
	 * @throws IllegalArgumentException if the request is null
	 */
	public RequestWrapper(final HttpServletRequest request)
	{
		super(request);
	}

	@Override
	public String getHeader(@NonNull final String name)
	{
		if (headerMap.containsKey(name))
		{
			return headerMap.get(name);
		}

		return super.getHeader(name);
	}

	@Override
	public Enumeration<String> getHeaderNames()
	{
		final HashSet<String> names = new HashSet<>(Collections.list(super.getHeaderNames()));
		names.addAll(headerMap.keySet());
		return Collections.enumeration(names);
	}

	@Override
	public Enumeration<String> getHeaders(@NonNull final String name)
	{
		final ArrayList<String> values = Collections.list(super.getHeaders(name));

		if (headerMap.containsKey(name))
		{
			values.add(headerMap.get(name));
		}

		return Collections.enumeration(values);
	}

	public void setHeader(@NonNull final String name, @NonNull final String value)
	{
		headerMap.put(name, value);
	}
}
