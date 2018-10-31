package de.metas.fresh.picking;

import java.awt.Color;

import de.metas.adempiere.form.terminal.ITerminalKeyStatus;
import de.metas.fresh.picking.form.PackingStates;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

/**
 * Wraps {@link PackingStates} enumeration
 */
@ToString
@EqualsAndHashCode
final class FreshProductKeyStatus implements ITerminalKeyStatus
{
	public static FreshProductKeyStatus cast(final ITerminalKeyStatus status)
	{
		return (FreshProductKeyStatus)status;
	}

	public static FreshProductKeyStatus packed()
	{
		return of(PackingStates.packed);
	}

	public static FreshProductKeyStatus of(@NonNull final PackingStates state)
	{
		return new FreshProductKeyStatus(state);
	}

	private final PackingStates packStatus;

	private FreshProductKeyStatus(@NonNull final PackingStates state)
	{
		packStatus = state;
	}

	public PackingStates getPackStatus()
	{
		return packStatus;
	}

	@Override
	public int getAD_Image_ID()
	{
		return -1;
	}

	@Override
	public int getAD_PrintColor_ID()
	{
		return -1;
	}

	@Override
	public int getAD_PrintFont_ID()
	{
		return -1;
	}

	@Override
	public String getName()
	{
		return getPackStatus().name();
	}

	@Override
	public Color getColor()
	{
		return packStatus.getColor();
	}
}
