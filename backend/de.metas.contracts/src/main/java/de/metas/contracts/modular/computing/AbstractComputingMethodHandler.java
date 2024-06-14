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

package de.metas.contracts.modular.computing;

import com.google.common.collect.ImmutableSet;
import de.metas.contracts.modular.settings.ModularContractModuleId;
import de.metas.contracts.modular.settings.ModularContractSettingsRepository;
import de.metas.money.Money;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_UOM;

import java.math.BigDecimal;
import java.util.stream.Stream;

public abstract class AbstractComputingMethodHandler implements IComputingMethodHandler
{
	@NonNull ModularContractSettingsRepository modularContractSettingsRepository = SpringContextHolder.instance.getBean(ModularContractSettingsRepository.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);

	/**
	 * This is the default implementation, used by yet-unimplemented methods. Its result ensures the IC is not created.
	 */
	@Override
	 @NonNull
	public ComputingResponse compute(final @NonNull ComputingRequest request)
	{
		final I_C_UOM stockUOM = productBL.getStockUOM(request.getProductId());
		final Quantity qty = Quantity.of(BigDecimal.ZERO, stockUOM);

		return ComputingResponse.builder()
				.ids(ImmutableSet.of())
				.price(ProductPrice.builder()
							   .productId(request.getProductId())
							   .money(Money.zero(request.getCurrencyId()))
							   .uomId(UomId.ofRepoId(stockUOM.getC_UOM_ID()))
							   .build())
				.qty(qty)
				.build();
	}

	@Override
	@NonNull
	public Stream<ProductId> streamContractSpecificPricedProductIds(@NonNull final ModularContractModuleId moduleId)
	{
		return Stream.of(modularContractSettingsRepository.getByModuleId(moduleId)
								 .getProductId());
	}
}
