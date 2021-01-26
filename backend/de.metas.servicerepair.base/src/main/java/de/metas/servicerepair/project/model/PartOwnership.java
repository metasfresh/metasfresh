/*
 * #%L
 * de.metas.servicerepair.base
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

package de.metas.servicerepair.project.model;

import org.adempiere.exceptions.AdempiereException;

public enum PartOwnership
{
	OWNED_BY_COMPANY,
	OWNED_BY_CUSTOMER,
	;

	public boolean toIsOwnedByCustomerFlag()
	{
		switch (this)
		{
			case OWNED_BY_COMPANY:
				return false;
			case OWNED_BY_CUSTOMER:
				return true;
			default:
				throw new AdempiereException("Cannot convert " + this + " to IsOwnedByCustomer flag");
		}
	}

	public static PartOwnership ofIsOwnedByCustomerFlag(final boolean ownedByCustomer)
	{
		return ownedByCustomer ? OWNED_BY_CUSTOMER : OWNED_BY_COMPANY;
	}

	public boolean isOwnedByCustomer()
	{
		return OWNED_BY_CUSTOMER.equals(this);
	}

}
