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

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.modular.settings.ModularContractModuleId;
import de.metas.money.Money;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.tax.api.TaxCategoryId;
import de.metas.uom.UomId;
import de.metas.util.lang.SeqNo;
import lombok.Builder;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Builder(toBuilder = true)
public record ModCntrSpecificPrice(@Nullable ModCntrSpecificPriceId id,
								   @NonNull FlatrateTermId flatrateTermId,
								   @NonNull Money amount,
								   @NonNull ModularContractModuleId modularContractModuleId,
								   @NonNull ProductId productId,
								   @NonNull TaxCategoryId taxCategoryId,
								   @NonNull UomId uomId,
								   boolean isScalePrice,
								   @Nullable BigDecimal minValue,
								   SeqNo seqNo)
{

	public ProductPrice getProductPrice()
	{
		return ProductPrice.builder()
				.productId(productId())
				.money(amount())
				.uomId(uomId())
				.build();
	}
}
