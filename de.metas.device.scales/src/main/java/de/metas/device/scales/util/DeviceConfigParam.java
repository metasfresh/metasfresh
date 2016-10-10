package de.metas.device.scales.util;

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


import de.metas.device.api.request.IDeviceConfigParam;

/**
 * Simple pojo implementation of {@link IDeviceConfigParam}. Consider to moves this class to a generic "device tools" project as soon as we start supporting devices other than scales.
 *
 * @author ts
 *
 */
public final class DeviceConfigParam implements IDeviceConfigParam
{

	private final String systemName;
	private final String userFriendlyName;
	private final String defaultValue;
	private String value;

	public DeviceConfigParam(final String systemName, final String userFriendlyName, final String defaultValue)
	{
		this.systemName = systemName;
		this.userFriendlyName = userFriendlyName;
		this.defaultValue = defaultValue;
	}

	@Override
	public String getSystemName()
	{
		return systemName;
	}

	@Override
	public String getUserFriendlyName()
	{
		return userFriendlyName;
	}

	@Override
	public String getDefaultValue()
	{
		return defaultValue;
	}

	@Override
	public String getValue()
	{
		return value;
	}

	@Override
	public void setValue(String value)
	{
		this.value = value;
	}

	@Override
	public String toString()
	{
		return "DeviceConfigParamPojo [systemName=" + systemName + ", userFriendlyName=" + userFriendlyName + ", defaultValue=" + defaultValue + ", value=" + value + "]";
	}

}
