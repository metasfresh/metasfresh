/*
 *
 * * #%L
 * * %%
 * * Copyright (C) <current year> metas GmbH
 * * %%
 * * This program is free software: you can redistribute it and/or modify
 * * it under the terms of the GNU General Public License as
 * * published by the Free Software Foundation, either version 2 of the
 * * License, or (at your option) any later version.
 * *
 * * This program is distributed in the hope that it will be useful,
 * * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * * GNU General Public License for more details.
 * *
 * * You should have received a copy of the GNU General Public
 * * License along with this program. If not, see
 * * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * * #L%
 *
 */

package de.metas.vertical.pharma.securpharm.actions;

import de.metas.util.lang.ReferenceListAwareEnum;

public enum SecurPharmAction implements ReferenceListAwareEnum
{
	// TODO check if action for UNDO is ok
	// FIXME: add values to X_M_Securpharm_Action_Result.Action's list
	DECOMMISSION, //
	UNDO_DECOMMISSION //
	;

	@Override
	public String getCode()
	{
		return name();
	}

	public static SecurPharmAction ofCode(final String code)
	{
		return valueOf(code);
	}
}
