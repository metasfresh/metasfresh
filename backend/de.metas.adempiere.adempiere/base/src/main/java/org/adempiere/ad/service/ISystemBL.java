package org.adempiere.ad.service;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.util.ISingletonService;

public interface ISystemBL extends ISingletonService
{
	ADSystemInfo get();

	/**
	 * Evaluate a sysconfig value and check it's value:
	 * <ul>
	 * <li>U - Allow remember the username
	 * <li>P - Allow remember the username and password (default for swing)
	 * <li>N - None
	 * </ul>
	 *
	 * @param sysConfigKey currently known/used values are {@code "SWING_LOGIN_ALLOW_REMEMBER_ME"} and {@code "ZK_LOGIN_ALLOW_REMEMBER_ME"} in a private legacy ZK UI.
	 */
	boolean isRememberUserAllowed(String sysConfigKey);

	/**
	 * See {@link #isRememberUserAllowed(String)}.
	 */
	boolean isRememberPasswordAllowed(String sysConfigKey);
}
