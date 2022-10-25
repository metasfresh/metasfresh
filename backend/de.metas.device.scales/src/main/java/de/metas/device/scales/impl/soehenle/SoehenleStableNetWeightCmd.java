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
 * Generates the command string <code><B>\r\n</code> which requests the "stable" weight from the scale.
 * If the scale is unable to measure a stable weight within a certain timeout, it returns an error.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public final class SoehenleStableNetWeightCmd extends AbstractSoehenleWeighCmd
{

	private static final SoehenleStableNetWeightCmd instance = new SoehenleStableNetWeightCmd();

	/**
	 * @return the string <code><B></code> that shall be send to the scale hardware in order to get the current weight.
	 */
	@Override
	public String getCmd()
	{
		return "<B>" + CMD_TERMINATOR;
	}

	public static SoehenleStableNetWeightCmd getInstance()
	{
		return instance;
	}

	@Override
	public int getStartingOffset()
	{
		return 8;
	}

	@Override
	public String toString()
	{
		return "SoehenleStableWeighCmd []";
	}
}
