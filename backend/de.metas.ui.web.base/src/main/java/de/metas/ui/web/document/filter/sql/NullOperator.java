/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.ui.web.document.filter.sql;

import javax.annotation.Nullable;

enum NullOperator
{
	IS_NULL,
	IS_NOT_NULL,
	ANY;

	public static NullOperator ofNotNullFlag(@Nullable final Boolean notNullFlag)
	{
		if (notNullFlag == null)
		{
			return ANY;
		}
		else if (notNullFlag)
		{
			return IS_NOT_NULL;
		}
		else
		{
			return IS_NULL;
		}
	}

	public NullOperator negateIf(final boolean value)
	{
		return value ? negate() : this;
	}

	public NullOperator negate()
	{
		switch (this)
		{

			case IS_NULL:
				return IS_NOT_NULL;
			case IS_NOT_NULL:
				return IS_NULL;
			case ANY:
				return ANY;
			default:
				throw new IllegalStateException("Unhandled: " + this);
		}
	}
}
