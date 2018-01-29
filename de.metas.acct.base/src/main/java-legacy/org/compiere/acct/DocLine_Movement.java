package org.compiere.acct;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_MovementLine;

/*
 * #%L
 * de.metas.acct.base
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

public class DocLine_Movement extends DocLine<Doc_Movement>
{

	public DocLine_Movement(final I_M_MovementLine movementLine, final Doc_Movement doc)
	{
		super(InterfaceWrapperHelper.getPO(movementLine), doc);
	}

	/**
	 * @return Get Warehouse Locator To
	 */
	public final int getM_LocatorTo_ID()
	{
		final I_M_MovementLine movementLine = getModel(I_M_MovementLine.class);
		return movementLine.getM_LocatorTo_ID();
	}
}
