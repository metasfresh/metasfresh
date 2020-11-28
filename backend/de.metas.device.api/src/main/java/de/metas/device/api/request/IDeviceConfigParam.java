package de.metas.device.api.request;

/*
 * #%L
 * de.metas.device.api
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


/**
 * Examples for config params:
 * <ul>
 * <li>USB-ID</li>
 * <li>IP-Adress or hostname</li>
 * <li>user name</li>
 * <li>PIN</li>
 * <li>...</li>
 * </ul>
 *
 * @author ts
 *
 */
public interface IDeviceConfigParam
{
	/**
	 * Should return a constant name that is used by the system.
	 *
	 * @return
	 */
	String getSystemName();

	/**
	 * Should return a user friendly name or a <code>AD_Message.Value</code> to localize.
	 *
	 * @return
	 */
	String getUserFriendlyName();

	/**
	 * Check for this value with <code>==</code>, not with <code>equals</code>.
	 */
	final String VALUE_NO_VALUE = "NoValue";

	/**
	 * Check for this value with <code>==</code>, not with <code>equals</code>.
	 */
	final String VALUE_UNSPECIFIED = "Unspecified";

	String getDefaultValue();

	/**
	 * The parameter value.
	 *
	 * @return
	 */
	String getValue();


	void setValue(String value);
}
