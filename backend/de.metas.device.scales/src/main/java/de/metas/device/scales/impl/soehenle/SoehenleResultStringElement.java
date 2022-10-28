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

import java.text.Format;

public class SoehenleResultStringElement
{
	private final String name;

	private final int position;

	private final Format format;

	public SoehenleResultStringElement(final String name, final int position, final Format format)
	{
		this.name = name;
		this.position = position;
		this.format = format;
	}

	public String getName()
	{
		return name;
	}

	public int getPosition()
	{
		return position;
	}

	public Format getFormat()
	{
		return format;
	}

	@Override
	public String toString()
	{
		return "SoehenleResultStringElement [name=" + name + ", position=" + position + ", format=" + format + "]";
	}
}
