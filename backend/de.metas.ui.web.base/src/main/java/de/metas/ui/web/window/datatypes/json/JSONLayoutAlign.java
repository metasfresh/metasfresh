package de.metas.ui.web.window.datatypes.json;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import de.metas.ui.web.window.descriptor.LayoutAlign;
import io.swagger.annotations.ApiModel;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@ApiModel("layout-align")
public enum JSONLayoutAlign
{
	left, center, right, justify;

	public static JSONLayoutAlign fromNullable(final LayoutAlign align)
	{
		if (align == null)
		{
			return null;
		}
		final JSONLayoutAlign jsonWidgetType = type2json.get(align);
		if (jsonWidgetType == null)
		{
			throw new IllegalArgumentException("Cannot convert " + align + " to " + JSONLayoutAlign.class);
		}
		return jsonWidgetType;
	}

	private static final Map<LayoutAlign, JSONLayoutAlign> type2json = ImmutableMap.<LayoutAlign, JSONLayoutAlign> builder()
			.put(LayoutAlign.Left, left)
			.put(LayoutAlign.Center, center)
			.put(LayoutAlign.Right, right)
			.put(LayoutAlign.Justify, justify)
			.build();
}
