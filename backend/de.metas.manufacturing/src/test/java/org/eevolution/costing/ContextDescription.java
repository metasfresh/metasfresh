package org.eevolution.costing;

import java.util.Map;
import java.util.stream.Collectors;

import org.assertj.core.description.Description;

import de.metas.util.Check;
import lombok.Builder;
import lombok.Singular;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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

@Builder
final class ContextDescription extends Description
{
	private final String title;
	@Singular("context")
	private final Map<String, Object> context;

	@Override
	public String value()
	{
		final StringBuilder sb = new StringBuilder();
		if (!Check.isEmpty(title, true))
		{
			sb.append(title).append("\n");
		}

		final String contextStr = getContextAsString();
		if (!Check.isEmpty(contextStr, true))
		{
			if (sb.length() > 0)
			{
				sb.append("\n");
			}
			sb.append(contextStr);
		}

		return sb.toString();
	}

	private String getContextAsString()
	{
		if (context == null || context.isEmpty())
		{
			return null;
		}

		return context.entrySet()
				.stream()
				.map(this::toString)
				.collect(Collectors.joining("\n"));

	}

	private String toString(final Map.Entry<String, Object> entry)
	{
		return entry.getKey() + ": " + entry.getValue();
	}

}
