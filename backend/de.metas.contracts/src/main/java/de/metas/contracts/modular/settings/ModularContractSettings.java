/*
 * #%L
 * de.metas.contracts
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

package de.metas.contracts.modular.settings;

import com.google.common.collect.ImmutableList;
import de.metas.calendar.standard.CalendarId;
import de.metas.calendar.standard.YearAndCalendarId;
import de.metas.calendar.standard.YearId;
import de.metas.contracts.modular.ComputingMethodType;
import de.metas.i18n.AdMessageKey;
import de.metas.lang.SOTrx;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
import de.metas.pricing.PricingSystemId;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Value
@Builder
public class ModularContractSettings
{
	@NonNull ModularContractSettingsId id;
	@NonNull OrgId orgId;
	@NonNull String name;
	@NonNull YearAndCalendarId yearAndCalendarId;
	@NonNull PricingSystemId pricingSystemId;

	/**
	 * Basically, purchase order line with this product may start the contract-frenzy.
	 */
	@NonNull ProductId rawProductId;
	@Nullable ProductId processedProductId;
	@Nullable ProductId coProductId;
	@NonNull @Singular ImmutableList<ModuleConfig> moduleConfigs;

	@NonNull SOTrx soTrx;

	@NonNull LocalDateAndOrgId storageCostStartDate;
	int additionalInterestDays;
	@Builder.Default @Getter
	@NonNull Percent interestPercent = Percent.ZERO;

	private static final AdMessageKey MSG_ERROR_INVALID_MODULAR_CONTRACT_SETTINGS = AdMessageKey.of("de.metas.contracts.modular.interceptor.C_Flatrate_Conditions.INVALID_MODULAR_CONTRACT_SETTINGS");

	public CalendarId getCalendarId() {return getYearAndCalendarId().calendarId();}

	public void assertHasModules()
	{
		if (moduleConfigs.isEmpty())
		{
			throw new AdempiereException(MSG_ERROR_INVALID_MODULAR_CONTRACT_SETTINGS)
					.markAsUserValidationError();
		}
	}

	@NonNull
	public Optional<ModuleConfig> getSingleModuleConfig(@NonNull final ComputingMethodType computingMethodType)
	{
		final List<ModuleConfig> configs = getModuleConfigs(computingMethodType);
		if (configs.isEmpty())
		{
			return Optional.empty();
		}
		else if (configs.size() == 1)
		{
			return Optional.of(configs.get(0));
		}
		else
		{
			throw new AdempiereException("More than one module config found for computing method type " + computingMethodType + ": " + configs);
		}
	}

	public YearId getYearId() {return yearAndCalendarId.yearId();}

	@NonNull
	public List<ModuleConfig> getModuleConfigs(@NonNull final ComputingMethodType computingMethodType)
	{
		return getModuleConfigs()
				.stream()
				.filter(config -> config.isMatching(computingMethodType))
				.toList();
	}

	@NonNull
	public List<ModuleConfig> getModuleConfigs(@NonNull final Collection<ComputingMethodType> computingMethodTypes)
	{
		Check.assumeNotEmpty(computingMethodTypes, "ComputingMethodTypes shouldn't be empty");
		return getModuleConfigs()
				.stream()
				.filter(config -> config.isMatchingAnyOf(computingMethodTypes))
				.toList();
	}

	@NonNull
	public List<ModuleConfig> getModuleConfigsWithout(@NonNull final ComputingMethodType computingMethodType)
	{
		return getModuleConfigs()
				.stream()
				.filter(config -> !config.isMatching(computingMethodType))
				.toList();
	}

	public boolean isMatching(@NonNull final ComputingMethodType computingMethodType)
	{
		return moduleConfigs.stream().anyMatch(config -> config.isMatching(computingMethodType));
	}

	public long countMatching(@NonNull final ComputingMethodType computingMethodType, @NonNull final ProductId productId)
	{
		return moduleConfigs.stream()
				.filter(config -> config.isMatching(computingMethodType) && ProductId.equals(config.getProductId(), productId))
				.count();
	}

	public long countMatchingAnyOf(@NonNull final ComputingMethodType computingMethodType1, @NonNull final ComputingMethodType computingMethodType2)
	{
		return moduleConfigs.stream()
				.filter(config -> config.isMatchingAnyOf(computingMethodType1, computingMethodType2))
				.count();
	}

	@NonNull
	public Percent getBonusInterestRate()
	{
		return interestPercent;
	}
}
