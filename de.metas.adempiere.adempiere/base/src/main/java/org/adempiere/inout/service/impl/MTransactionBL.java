package org.adempiere.inout.service.impl;

/*
 * #%L
 * ADempiere ERP - Base
 * %%
 * Copyright (C) 2015 metas GmbH
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


import org.adempiere.inout.service.IMTransactionBL;
import org.adempiere.util.Check;
import org.compiere.model.I_M_Transaction;

public class MTransactionBL implements IMTransactionBL
{
	@Override
	public boolean isInboundTransaction(final I_M_Transaction mtrx)
	{
		Check.assumeNotNull("mtrx", "mtrx not null");
		final String movementType = mtrx.getMovementType();
		if (movementType == null || movementType.length() != 2)
		{
			throw new IllegalArgumentException("Invalid movement type '" + movementType + "' for " + mtrx);
		}

		final boolean isInbound;
		final char sign = movementType.charAt(1);
		switch (sign)
		{
			case '+':
				isInbound = true;
				break;
			case '-':
				isInbound = false;
				break;
			default:
				throw new IllegalArgumentException("Invalid movement type '" + movementType + "' for " + mtrx);
		}

		return isInbound;
	}

}
