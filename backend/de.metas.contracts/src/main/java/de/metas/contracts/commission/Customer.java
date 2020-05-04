package de.metas.contracts.commission;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.bpartner.BPartnerId;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.commission
 * %%
 * Copyright (C) 2019 metas GmbH
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

/** The entity that buys something and therefore injects the money into the commission system that is distributed to the {@link Beneficiary Beneficiaries} */
@Value
public class Customer
{
	public static Customer ofOrNull(@Nullable final BPartnerId bPartnerId)
	{
		if (bPartnerId == null)
		{
			return null;
		}
		return of(bPartnerId);
	}

	@JsonCreator
	public static Customer of(@JsonProperty("bPartnerId") @NonNull final BPartnerId bPartnerId)
	{
		return new Customer(bPartnerId);
	}

	BPartnerId bPartnerId;

	private Customer(BPartnerId bPartnerId)
	{
		this.bPartnerId = bPartnerId;
	}

	@JsonProperty("bPartnerId")
	public BPartnerId getBPartnerId()
	{
		return bPartnerId;
	}
}
