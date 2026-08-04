/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.rest_api.v2.currencyconversion;

import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.currency.CurrencyConversionRates;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.service.ClientId;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * The immutable, fully-resolved single-direction rate to persist: the {@code C_Conversion_Rate} natural
 * key ({@code orgId, from, to, conversionType, validFrom}) plus the payload ({@code multiplyRate},
 * {@code validTo}) and the batch's {@link SyncAdvise}.
 * <p>
 * The {@code DivideRate} is <b>not</b> a field — it is derived from {@link #getMultiplyRate()} via
 * {@link #getDivideRate()} (the canonical {@link CurrencyConversionRates#reciprocal(BigDecimal)}), so a
 * caller never passes it separately. Distinct from {@link de.metas.currency.CurrencyRate}, which is the
 * conversion-execution type ({@code convertAmount}).
 */
@Value
@Builder
public class ConversionRateUpsertRequest
{
	@NonNull ClientId clientId;
	@NonNull OrgId orgId;
	@NonNull CurrencyId fromCurrencyId;
	@NonNull CurrencyId toCurrencyId;
	@NonNull CurrencyConversionTypeId conversionTypeId;
	@NonNull LocalDate validFrom;
	@Nullable LocalDate validTo;
	@NonNull BigDecimal multiplyRate;
	@NonNull SyncAdvise syncAdvise;

	/** {@code DivideRate = 1 / MultiplyRate} at the canonical scale/rounding. */
	@NonNull
	public BigDecimal getDivideRate()
	{
		return CurrencyConversionRates.reciprocal(multiplyRate);
	}
}
