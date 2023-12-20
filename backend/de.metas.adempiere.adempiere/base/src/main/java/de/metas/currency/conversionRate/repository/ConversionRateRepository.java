/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.currency.conversionRate.repository;

import de.metas.currency.conversionRate.model.ConversionRate;
import de.metas.currency.conversionRate.model.ConversionRateCreateRequest;
import de.metas.currency.conversionRate.model.ConversionRateId;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import lombok.NonNull;
import org.compiere.model.I_C_Conversion_Rate;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

@Repository
public class ConversionRateRepository
{
	@NonNull
	public ConversionRate create(@NonNull final ConversionRateCreateRequest request)
	{
		final I_C_Conversion_Rate conversionRateRecord = newInstance(I_C_Conversion_Rate.class);

		conversionRateRecord.setAD_Org_ID(request.getOrgId().getRepoId());
		conversionRateRecord.setC_Currency_ID(request.getCurrencyId().getRepoId());
		conversionRateRecord.setC_Currency_ID_To(request.getCurrencyToId().getRepoId());
		conversionRateRecord.setC_ConversionType_ID(request.getConversionTypeId().getRepoId());

		conversionRateRecord.setValidFrom(TimeUtil.asTimestampNotNull(request.getValidFrom()));
		conversionRateRecord.setValidTo(TimeUtil.asTimestamp(request.getValidTo()));

		conversionRateRecord.setDivideRate(request.getDivideRate());
		conversionRateRecord.setMultiplyRate(ConversionRate.invertRate(request.getDivideRate()));

		save(conversionRateRecord);

		return ofRecord(conversionRateRecord);
	}

	@NonNull
	private static ConversionRate ofRecord(@NonNull final I_C_Conversion_Rate conversionRate)
	{
		return ConversionRate.builder()
				.conversionRateId(ConversionRateId.ofRepoId(conversionRate.getC_Conversion_Rate_ID()))

				.currencyId(CurrencyId.ofRepoId(conversionRate.getC_Currency_ID()))
				.currencyToId(CurrencyId.ofRepoId(conversionRate.getC_Currency_ID_To()))
				.conversionTypeId(CurrencyConversionTypeId.ofRepoId(conversionRate.getC_ConversionType_ID()))

				.validFrom(TimeUtil.asInstantNonNull(conversionRate.getValidFrom()))
				.validTo(TimeUtil.asInstant(conversionRate.getValidTo()))

				.divideRate(conversionRate.getDivideRate())
				.build();
	}
}