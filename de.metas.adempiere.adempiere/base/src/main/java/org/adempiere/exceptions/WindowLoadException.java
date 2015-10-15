package org.adempiere.exceptions;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.adempiere.util.Check;

/**
 * Exception thrown when a standard adempiere window (AD_Window) failed to load because of:
 * <ul>
 * <li>no user/role access
 * <li>no tabs
 * <li>etc
 * </ul>
 * 
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/08508_NPE_when_opening_Warenbewegung_-_%C3%9Cbersicht_with_role_Handel_%28109844897323%29
 */
public class WindowLoadException extends AdempiereException
{
	/**
	 *
	 */
	private static final long serialVersionUID = -5396156685197179469L;

	public WindowLoadException(final String message, final String roleName, final String windowName, final int adWindowId)
	{
		super(buildMsg(message, roleName, windowName, adWindowId));
	}

	private static String buildMsg(final String message, final String roleName, final String windowName, final int adWindowId)
	{
		final StringBuilder messageFinal = new StringBuilder("@AccessTableNoView@");
		if (!Check.isEmpty(message, true))
		{
			messageFinal.append(" (").append(message.trim()).append(")");
		}

		messageFinal.append("\n @AD_Role_ID@: ").append(roleName);
		messageFinal.append("\n @AD_Window_ID@: ").append(windowName).append(" (ID=").append(adWindowId).append(")");

		return messageFinal.toString();
	}
}
