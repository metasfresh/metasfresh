package de.metas.currency.impl;

import de.metas.money.CurrencyConversionTypeId;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.service.ClientId;

import java.time.Instant;
import java.util.Comparator;
import java.util.Objects;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

@Value
@Builder
class CurrencyConversionTypeRouting
{
	@NonNull
	ClientId clientId;

	@NonNull
	OrgId orgId;

	@NonNull
	Instant validFrom;

	@NonNull
	CurrencyConversionTypeId conversionTypeId;

	public boolean isMatching(
			@NonNull final ClientId clientId,
			@NonNull final OrgId orgId,
			@NonNull final Instant date)
	{
		return (this.clientId.isSystem() || ClientId.equals(this.clientId, clientId))
				&& (this.orgId.isAny() || OrgId.equals(this.orgId, orgId))
				&& this.validFrom.compareTo(date) <= 0;
	}

	public static Comparator<CurrencyConversionTypeRouting> moreSpecificFirstComparator()
	{
		return (routing1, routing2) -> {
			if (Objects.equals(routing1, routing2))
			{
				return 0;
			}
			else if (routing1.isMoreSpecificThan(routing2))
			{
				return -1;
			}
			else
			{
				return +1;
			}
		};
	}

	public boolean isMoreSpecificThan(@NonNull final CurrencyConversionTypeRouting other)
	{
		if (!ClientId.equals(this.getClientId(), other.getClientId()))
		{
			return this.getClientId().isRegular();
		}

		if (!OrgId.equals(this.getOrgId(), other.getOrgId()))
		{
			return this.getOrgId().isRegular();
		}

		return this.getValidFrom().compareTo(other.getValidFrom()) > 0;
	}
}
