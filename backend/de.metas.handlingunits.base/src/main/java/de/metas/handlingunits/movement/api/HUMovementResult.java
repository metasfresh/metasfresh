package de.metas.handlingunits.movement.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.compiere.model.I_M_Movement;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.model.I_M_HU;
import lombok.Value;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

@Value
public final class HUMovementResult
{
	public static final HUMovementResult.Builder builder()
	{
		return new Builder();
	}

	private final List<I_M_Movement> movements;
	private final List<I_M_HU> husMoved;

	private HUMovementResult(final HUMovementResult.Builder builder)
	{
		movements = ImmutableList.copyOf(builder.movements);
		husMoved = ImmutableList.copyOf(builder.husMoved);
	}
	
	public boolean hasNoMovements()
	{
		return movements.isEmpty();
	}

	public static class Builder
	{
		private final List<I_M_Movement> movements = new ArrayList<>();
		private final List<I_M_HU> husMoved = new ArrayList<>();

		private Builder()
		{
		}

		public HUMovementResult build()
		{
			return new HUMovementResult(this);
		}

		public Builder addMovementAndHUs(final I_M_Movement movement, final Collection<I_M_HU> hus)
		{
			movements.add(movement);
			husMoved.addAll(hus);
			return this;
		}
	}
}
