/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.pricing.tax;

import de.metas.location.CountryId;
import de.metas.product.ProductId;
import de.metas.tax.api.TaxCategoryId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.Instant;

@Value
@Builder(toBuilder = true)
public class ProductTaxCategory
{
	@NonNull
	ProductTaxCategoryId productTaxCategoryId;

	@NonNull
	ProductId productId;

	@NonNull
	TaxCategoryId taxCategoryId;

	@NonNull
	Instant validFrom;

	@Nullable
	CountryId countryId;

	@Builder.Default
	boolean active = true;
}
