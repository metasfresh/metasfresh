package de.metas.handlingunits.exceptions;

import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Version;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

@SuppressWarnings("serial")
public class HUPIInvalidConfigurationException extends HUException
{
	public HUPIInvalidConfigurationException(final String message, final I_M_HU_PI_Item piItem)
	{
		super(buildMsg(message, piItem));
	}

	private static String buildMsg(final String message, final I_M_HU_PI_Item piItem)
	{
		final I_M_HU_PI_Version piVersion = piItem == null ? null : piItem.getM_HU_PI_Version();
		final I_M_HU_PI pi = piVersion == null ? null : piVersion.getM_HU_PI();

		final StringBuilder sb = new StringBuilder();
		sb.append(message);

		if (pi != null)
		{
			sb.append("\n PI: ").append(pi);
		}
		if (piItem != null)
		{
			sb.append("\n PI item: ").append(piItem);
		}

		return sb.toString();
	}
}
