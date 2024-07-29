/*
 * #%L
 * de-metas-common-util
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.common.util;

import lombok.Data;

@Data
public class SimpleSequence
{
	final int initial;

	final int increment;

	int current;

	/**
	 * The first invocation of {@link #next()} will return 10, the second will return 20 and so on.
	 */
	public static SimpleSequence create()
	{
		return new SimpleSequence(0, 10);
	}

	/**
	 * The first invocation of {@link #next()} will return {code initial + 10}.
	 */
	public static SimpleSequence createWithInitial(final int initial)
	{
		return new SimpleSequence(initial, 10);
	}

	private SimpleSequence(final int initial, final int increment)
	{

		this.initial = initial;
		this.increment = increment;
	}

	public int next()
	{
		current = current + increment;
		return current;
	}
}
