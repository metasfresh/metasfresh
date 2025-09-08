/*
 * #%L
 * de.metas.device.scales
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.device.scales.impl.soehenle;

import de.metas.device.scales.impl.ICmd;

import java.util.LinkedHashMap;

public interface ISoehenleCmd extends ICmd
{
	String CMD_TERMINATOR = "\r\n"; // we need to terminate the command string with "Carriage Return, Line Feed"
	String RESULT_ELEMENT_STATUS = "Status";
	String RESULT_ELEMENT_WEIGHT_VALUE = "WeightValue";
	String RESULT_ELEMENT_UNIT = "Unit";

	default LinkedHashMap<String, SoehenleResultStringElement> getResultElements()
	{
		final LinkedHashMap<String, SoehenleResultStringElement> result = new LinkedHashMap<String, SoehenleResultStringElement>();
		result.put(RESULT_ELEMENT_STATUS, new SoehenleResultStringElement(RESULT_ELEMENT_STATUS, 1, null));
		result.put(RESULT_ELEMENT_WEIGHT_VALUE, new SoehenleResultStringElement(RESULT_ELEMENT_WEIGHT_VALUE, 2, null));
		result.put(RESULT_ELEMENT_UNIT, new SoehenleResultStringElement(RESULT_ELEMENT_UNIT, 3, null));

		return result;
	}
}
