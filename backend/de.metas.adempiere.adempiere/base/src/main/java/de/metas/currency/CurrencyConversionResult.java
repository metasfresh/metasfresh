package de.metas.currency;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.annotation.Nullable;

import org.adempiere.service.ClientId;

import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * The result of a currency conversion.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Value
@Builder
public class CurrencyConversionResult
{
	@NonNull
	private BigDecimal amount;
	@NonNull
	private CurrencyId currencyId;

	@NonNull
	private BigDecimal sourceAmount;
	@NonNull
	private CurrencyId sourceCurrencyId;

	// NOTE: it might be null when sourceAmount is ZERO and API decided to not fetch the conversionRate because it's pointless
	@Nullable
	private BigDecimal conversionRateOrNull;

	@NonNull
	private LocalDate conversionDate;
	@NonNull
	private CurrencyConversionTypeId conversionTypeId;
	@NonNull
	private ClientId clientId;
	@NonNull
	private OrgId orgId;
}
