package de.metas.bpartner;

import de.metas.lang.SOTrx;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public enum BPartnerType
{
	CUSTOMER, VENDOR, EMPLOYEE;

	public static BPartnerType ofSOTrx(final boolean isSOTrx)
	{
		return isSOTrx ? CUSTOMER : VENDOR;
	}

	public static BPartnerType ofSOTrx(final SOTrx soTrx)
	{
		return soTrx.toBoolean() ? CUSTOMER : VENDOR;
	}

	public SOTrx getSOTrx()
	{
		return SOTrx.ofBoolean(isSOTrx());
	}

	public boolean isSOTrx()
	{
		switch (this)
		{
			case CUSTOMER:
				return true;
			case VENDOR:
				return false;
			default:
				throw new IllegalStateException("Unknown IsSOTrx for " + this);
		}
	}
}
