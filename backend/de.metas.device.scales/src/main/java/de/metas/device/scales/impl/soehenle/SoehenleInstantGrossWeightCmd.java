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

/**
 * Generates the command string <code><A></>\r\n</code> which requests the "instant" weight from the scale.
 * This result might be "stable" or "dynamic".
 *
 */
public final class SoehenleInstantGrossWeightCmd implements ISoehenleCmd
{
	private static final SoehenleInstantGrossWeightCmd instance = new SoehenleInstantGrossWeightCmd();

	/**
	 * @return the string <code><A></code> that shall be sent to the scale hardware in order to get the current weight.
	 */
	@Override
	public String getCmd()
	{
		return "<A>" + CMD_TERMINATOR;
	}

	public static SoehenleInstantGrossWeightCmd getInstance()
	{
		return instance;
	}

	@Override
	public String toString()
	{
		return "SoehenleInstantGrossWeighCmd []";
	}
}
