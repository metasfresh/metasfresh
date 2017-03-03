package de.metas.ui.web.dashboard;

import java.util.stream.Stream;

import org.adempiere.util.GuavaCollectors;

import com.google.common.collect.ImmutableMap;

import de.metas.ui.web.base.model.X_WEBUI_KPI_Field;

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

public enum KPIFieldType
{
	XAxis(X_WEBUI_KPI_Field.TYPE_XAxis);

	private String code;

	KPIFieldType(final String code)
	{
		this.code = code;
	}

	public String getCode()
	{
		return code;
	}

	public static KPIFieldType fromNullableCode(final String code)
	{
		if(code == null)
		{
			return null;
		}
		
		final KPIFieldType type = code2type.get(code);
		if (type == null)
		{
			throw new IllegalArgumentException("No " + KPIFieldType.class + " found for code: " + code);
		}
		return type;
	}

	private static ImmutableMap<String, KPIFieldType> code2type = Stream.of(values())
			.collect(GuavaCollectors.toImmutableMapByKey(KPIFieldType::getCode));
}
