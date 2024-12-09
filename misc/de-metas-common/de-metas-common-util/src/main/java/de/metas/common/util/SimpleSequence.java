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

<<<<<<< HEAD
=======
import lombok.Builder;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import lombok.Data;

@Data
public class SimpleSequence
{
	//this field might be useful to know when debugging.
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

<<<<<<< HEAD
=======
	@Builder
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	private SimpleSequence(final int initial, final int increment)
	{

		this.initial = initial;
<<<<<<< HEAD
=======

		Check.errorIf(increment == 0, "The given increment may not be zero");
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		this.increment = increment;

		current = initial;
	}

	public int next()
	{
		current = current + increment;
		return current;
	}
}
