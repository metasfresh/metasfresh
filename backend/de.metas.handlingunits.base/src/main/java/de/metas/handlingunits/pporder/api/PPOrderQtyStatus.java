/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.handlingunits.pporder.api;

import javax.annotation.Nullable;

public enum PPOrderQtyStatus
{
	DRAFT, COMPLETED;

	public static PPOrderQtyStatus ofProcessedFlag(final boolean processed)
	{
		return processed ? COMPLETED : DRAFT;
	}

	public static boolean isDraft(@Nullable final PPOrderQtyStatus status)
	{
		return status == DRAFT;
	}

	public static boolean isProcessed(@Nullable final PPOrderQtyStatus status)
	{
		return status == COMPLETED;
	}
}
