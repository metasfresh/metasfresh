package de.metas.jax.rs.event;

import de.metas.util.Check;

/*
 * #%L
 * de.metas.jax.rs
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

public class JaxRsWasResetEvent
{
	public enum Advise
	{
		RESTART, DONT_RESTART
	}

	private final Advise advise;

	public Advise getAdvise()
	{
		return advise;
	}

	public JaxRsWasResetEvent(final Advise advise)
	{
		Check.assumeNotNull(advise, "Param advise not null");
		this.advise = advise;
	}
}
