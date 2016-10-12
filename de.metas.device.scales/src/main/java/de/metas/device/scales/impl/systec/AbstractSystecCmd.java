package de.metas.device.scales.impl.systec;

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

public abstract class AbstractSystecCmd implements ISystecCmd
{
	@Override
	public final LinkedHashMap<String, SystecResultStringElement> getResultElements()
	{
		// final DecimalFormat fmt = new DecimalFormat();
		// fmt.applyPattern("#.#");

		// example return string: "<000002.07.1413:25  111     0.0    90.0   -90.0kgPT2  1   11540>"

		final LinkedHashMap<String, SystecResultStringElement> result = new LinkedHashMap<String, SystecResultStringElement>();
		result.put("Fehlercode", new SystecResultStringElement("Fehlercode", 2, 2, null));
		result.put("Waagen-Status", new SystecResultStringElement("Waagen-Status", 4, 2, null));
		result.put("Datum", new SystecResultStringElement("Datum", 6, 8, null));
		result.put("Zeit", new SystecResultStringElement("Zeit", 14, 5, null));
		result.put("Ident-Nummer", new SystecResultStringElement("Ident-Nummer",19, 4, null));
		result.put("Waagen-Nummer", new SystecResultStringElement("Waagen-Nummer", 23, 1, null));
		result.put("Bruttogewicht", new SystecResultStringElement("Bruttogewicht", 24, 8, null));
		result.put("Taragewicht", new SystecResultStringElement("Taragewicht", 32, 8, null));
		result.put("Nettogewicht", new SystecResultStringElement("Nettogewicht", 40, 8, null));
		result.put("Einheit", new SystecResultStringElement("Einheit", 48, 2, null));
		result.put("Taracode", new SystecResultStringElement("Taracode", 50, 2, null));
		result.put("Wägebereich", new SystecResultStringElement("Wägebereich", 52, 1, null));
		result.put("Terminal-Nr.", new SystecResultStringElement("Terminal-Nr.",53, 3, null));
		result.put("Prüfziffer", new SystecResultStringElement("Prüfziffer", 56, 8, null));

		return result;
	}
}
