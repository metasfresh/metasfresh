package de.metas.contracts.commission;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;

import de.metas.bpartner.BPartnerId;
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

/** Receives money from the commission system */
@Value
public class Beneficiary
{
	public static Beneficiary ofOrNull(@Nullable final BPartnerId bPartnerId)
	{
		if (bPartnerId == null)
		{
			return null;
		}
		return Beneficiary.of(bPartnerId);
	}

	@JsonCreator
	public static Beneficiary of(@JsonProperty("bPartnerId") @NonNull final BPartnerId bPartnerId)
	{
		return new Beneficiary(bPartnerId);
	}

	BPartnerId bPartnerId;

	private Beneficiary(BPartnerId bPartnerId)
	{
		this.bPartnerId = bPartnerId;
	}

	@JsonProperty("bPartnerId")
	public BPartnerId getBPartnerId()
	{
		return bPartnerId;
	}
}
