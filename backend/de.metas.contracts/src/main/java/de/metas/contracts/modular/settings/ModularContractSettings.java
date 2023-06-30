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
import de.metas.organization.OrgId;
import de.metas.pricing.PricingSystemId;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

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
	ProductId productId;

	@NonNull
	@Singular
	List<ModuleConfig> moduleConfigs;
}
