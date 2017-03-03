package de.metas.ui.web.dashboard;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public final class KPIDataSet
{
	@JsonProperty("name")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final String name;

	@JsonProperty("unit")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final String unit;

	@JsonProperty("values")
	private final List<KPIDataSetValue> values;

	KPIDataSet(final String name, final List<KPIDataSetValue> values)
	{
		super();
		this.name = name;
		unit = null;
		this.values = values;
	}

	public String getName()
	{
		return name;
	}

	public List<KPIDataSetValue> getValues()
	{
		return values;
	}
}
