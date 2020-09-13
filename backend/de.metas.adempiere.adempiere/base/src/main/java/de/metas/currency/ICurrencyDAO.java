/**
 *
 */
package de.metas.currency;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.NonNull;
import org.adempiere.service.ClientId;

import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.util.ISingletonService;

import javax.annotation.Nullable;

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

/**
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface ICurrencyDAO extends ISingletonService
{
	CurrencyPrecision DEFAULT_PRECISION = CurrencyPrecision.TWO;

	Currency getById(CurrencyId currencyId);

	/**
	 * retrieves currency by ISO code
	 *
	 * @return currency or <code>null</code>
	 */
	Currency getByCurrencyCode(CurrencyCode currencyCode);

	CurrencyCode getCurrencyCodeById(CurrencyId currencyId);

	CurrencyPrecision getStdPrecision(CurrencyId currencyId);

	CurrencyPrecision getCostingPrecision(CurrencyId currencyId);

	@NonNull CurrencyConversionTypeId getDefaultConversionTypeId(ClientId adClientId, OrgId adOrgId, LocalDate date);

	CurrencyConversionTypeId getConversionTypeId(ConversionTypeMethod type);

	@Nullable
	BigDecimal retrieveRateOrNull(CurrencyConversionContext conversionCtx, CurrencyId currencyFromId, CurrencyId currencyToId);
}
