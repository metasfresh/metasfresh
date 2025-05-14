package de.metas.ui.web.view.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import de.metas.ui.web.view.ViewResultColumn;
import de.metas.ui.web.window.datatypes.json.JSONLayoutWidgetType;
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

@Value
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class JSONViewResultColumn
{
	String fieldName;
	JSONLayoutWidgetType widgetType;
	@JsonInclude(JsonInclude.Include.NON_NULL) Integer maxPrecision;
	@JsonInclude(JsonInclude.Include.NON_NULL) Boolean hidden;

	private JSONViewResultColumn(@NonNull final ViewResultColumn viewResultColumn)
	{
		fieldName = viewResultColumn.getFieldName();
		widgetType = JSONLayoutWidgetType.fromNullable(viewResultColumn.getWidgetType());
		maxPrecision = viewResultColumn.getMaxPrecision();
		hidden = viewResultColumn.getHidden();
	}

	public static JSONViewResultColumn of(final ViewResultColumn viewResultColumn)
	{
		return new JSONViewResultColumn(viewResultColumn);
	}
}
