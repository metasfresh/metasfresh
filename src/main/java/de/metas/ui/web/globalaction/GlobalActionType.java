package de.metas.ui.web.globalaction;

import java.util.Arrays;

import org.adempiere.exceptions.AdempiereException;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import lombok.Getter;

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

public enum GlobalActionType
{
	OPEN_VIEW("open-view");

	@Getter
	private final String code;

	private GlobalActionType(String code)
	{
		this.code = code;
	}

	public static GlobalActionType forCode(final String code)
	{
		final GlobalActionType type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + GlobalActionType.class + " found for code: " + code);
		}
		return type;
	}

	private static final ImmutableMap<String, GlobalActionType> typesByCode = Maps.uniqueIndex(Arrays.asList(values()), GlobalActionType::getCode);
}
