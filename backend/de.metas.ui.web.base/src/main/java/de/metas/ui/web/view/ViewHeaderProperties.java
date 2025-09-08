/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.ui.web.view;

import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class ViewHeaderProperties
{
	public static final ViewHeaderProperties EMPTY = builder().build();

	@NonNull
	@Singular
	ImmutableList<ViewHeaderPropertiesGroup> groups;

	public static ViewHeaderProperties ofEntries(@NonNull final List<ViewHeaderProperty> entries)
	{
		if (entries.isEmpty())
		{
			return EMPTY;
		}

		return builder()
				.group(ViewHeaderPropertiesGroup.builder()
						.entries(entries)
						.build())
				.build();
	}

	public boolean isEmpty()
	{
		return groups.isEmpty();
	}

	public ViewHeaderProperties combine(@NonNull final ViewHeaderProperties other)
	{
		if (isEmpty())
		{
			return other;
		}
		else if (other.isEmpty())
		{
			return this;
		}
		else
		{
			return ViewHeaderProperties.builder()
					.groups(ImmutableList.<ViewHeaderPropertiesGroup>builder()
							.addAll(this.groups)
							.addAll(other.groups)
							.build())
					.build();
		}
	}
}
