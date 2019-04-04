/*
 *
 *  * #%L
 *  * %%
 *  * Copyright (C) <current year> metas GmbH
 *  * %%
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as
 *  * published by the Free Software Foundation, either version 2 of the
 *  * License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public
 *  * License along with this program. If not, see
 *  * <http://www.gnu.org/licenses/gpl-2.0.html>.
 *  * #L%
 *
 */

package de.metas.vertical.creditscore.base.spi.model;

import com.google.common.collect.Maps;

import java.util.Map;

public enum ResultCode
{
	P(0), N(1), E(-1), M(2);

	private final int resultCode;

	private static final Map<String, ResultCode> nameIndex =
			Maps.newHashMapWithExpectedSize(ResultCode.values().length);
	private static final Map<Integer, ResultCode> codeIndex =
			Maps.newHashMapWithExpectedSize(ResultCode.values().length);

	static
	{
		for (ResultCode code : ResultCode.values())
		{
			nameIndex.put(code.name(), code);
		}
	}

	static
	{
		for (ResultCode code : ResultCode.values())
		{
			codeIndex.put(code.resultCode, code);
		}
	}

	ResultCode(int resultCode)
	{
		this.resultCode = resultCode;
	}

	public int getResultCode()
	{
		return resultCode;
	}

	public static ResultCode fromCode(int resultCode)
	{
		return codeIndex.get(resultCode);
	}

	public static ResultCode fromName(String name)
	{
		return nameIndex.get(name);
	}
}
