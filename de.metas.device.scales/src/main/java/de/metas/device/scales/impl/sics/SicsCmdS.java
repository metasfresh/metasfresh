package de.metas.device.scales.impl.sics;

/*
 * #%L
 * de.metas.device.scales
 * %%
 * Copyright (C) 2015 metas GmbH
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


import java.util.LinkedHashMap;

public final class SicsCmdS implements ISiscCmd
{
	private static final SicsCmdS instance = new SicsCmdS();

	/**
	 * @return the string <code>S</code> that shall be send to the scale hardware in order to get the current weight.
	 */
	@Override
	public String getCmd()
	{
		return "S";
	}

	@Override
	public LinkedHashMap<String, SiscResultStringElement> getResultElements()
	{
		final LinkedHashMap<String, SiscResultStringElement> result = new LinkedHashMap<String, SiscResultStringElement>();
		result.put("ID", new SiscResultStringElement("ID", 1, null));
		result.put("Status", new SiscResultStringElement("Status", 2, null));
		result.put("WeightValue", new SiscResultStringElement("WeightValue", 3, null));
		result.put("Unit", new SiscResultStringElement("Unit", 4, null));

		return result;
	}

	public static SicsCmdS getInstance()
	{
		return instance;
	}

	@Override
	public String toString()
	{
		return String.format("SicsCmdS []");
	}
}
