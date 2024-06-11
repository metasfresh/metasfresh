/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.contracts.modular;

import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.settings.ModuleConfig;
import de.metas.pricing.IEditablePricingContext;
import de.metas.product.ProductId;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Builder
@Value
public class ModCntrSpecificPricesCreateRequest
{
	@NonNull I_C_Flatrate_Term flatrateTermRecord;
	@NonNull ProductId productId;
	@NonNull ModuleConfig moduleConfig;
	@NonNull IEditablePricingContext pricingContextTemplate;
	@Nullable Percent interimPricePercent;
}