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

package de.metas.contracts.modular.computing.purchasecontract.subtractedvalue.coproduct;

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.modular.ComputingMethodType;
import de.metas.contracts.modular.computing.ComputingRequest;
import de.metas.contracts.modular.computing.ComputingResponse;
import de.metas.contracts.modular.computing.ComputingMethodService;
import de.metas.contracts.modular.computing.ComputingMethodHandler;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.contracts.modular.log.ModularContractLogEntry;
import de.metas.money.Money;
import de.metas.product.IProductBL;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_UOM;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class SVCoComputingMethod implements ComputingMethodHandler
{
	private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final ComputingMethodService computingMethodService;
	@Override
	public boolean applies(final @NonNull TableRecordReference recordRef, @NonNull final LogEntryContractType logEntryContractType)
	{
		return false;
	}

	@Override
	public @NonNull Stream<FlatrateTermId> streamContractIds(final @NonNull TableRecordReference tableRecordReference)
	{
		return null;
	}

	@Override
	public @NonNull ComputingMethodType getComputingMethodType()
	{
		return null;
	}

	@Override
	public @NonNull ComputingResponse compute(final @NonNull ComputingRequest request)
	{
		final I_C_UOM stockUOM = productBL.getStockUOM(request.getProductId());
		final Quantity qty = Quantity.of(BigDecimal.ONE, stockUOM);
		final List<ModularContractLogEntry> logs = new ArrayList<>();

		return ComputingResponse.builder()
				.ids(logs.stream().map(ModularContractLogEntry::getId).collect(Collectors.toSet()))
				.price(ProductPrice.builder()
							   .productId(request.getProductId())
							   .money(Money.of(BigDecimal.ONE, request.getCurrencyId()))
							   .uomId(UomId.ofRepoId(stockUOM.getC_UOM_ID()))
							   .build())
				.qty(qty)
				.build();
	}
}
