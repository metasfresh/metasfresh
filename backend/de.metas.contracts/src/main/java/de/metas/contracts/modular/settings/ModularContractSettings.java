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

import de.metas.calendar.standard.YearAndCalendarId;
import de.metas.calendar.standard.YearId;
import de.metas.contracts.modular.ComputingMethodType;
import de.metas.lang.SOTrx;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
import de.metas.pricing.PricingSystemId;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.List;

@Value
@Builder
public class ModularContractSettings
{
	@NonNull
	ModularContractSettingsId id;

	@NonNull
	OrgId orgId;

	@NonNull
	String name;

	@NonNull
	YearAndCalendarId yearAndCalendarId;

	@NonNull
	PricingSystemId pricingSystemId;

	/**
	 * Basically, purchase order line with this product may start the contract-frenzy.
	 */
	@NonNull
	ProductId rawProductId;
	@Nullable
	ProductId processedProductId;
	@Nullable
	ProductId coProductId;

	@NonNull
	@Singular
	List<ModuleConfig> moduleConfigs;

	@NonNull
	SOTrx soTrx;

	@NonNull
	LocalDateAndOrgId storageCostStartDate;

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

}
