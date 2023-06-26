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

package de.metas.contracts.modular.impl;

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.modular.IModularContractTypeHandler;
import de.metas.contracts.modular.log.LogEntryCreateRequest;
import de.metas.contracts.modular.log.LogEntryDeleteRequest;
import de.metas.contracts.modular.log.LogEntryReverseRequest;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Stream;

@Component
public class PurchaseOrderLineModularContractHandler implements IModularContractTypeHandler<I_C_OrderLine>
{
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);

	@Override
	public boolean probablyAppliesTo(@NonNull final Object model)
	{
		return model.getClass().isAssignableFrom(I_C_OrderLine.class);
	}

	@Override
	@NonNull
	public Optional<LogEntryCreateRequest> createLogEntryCreateRequest(@NonNull final I_C_OrderLine model, @NonNull final FlatrateTermId flatrateTermId)
	{
		return Optional.of(LogEntryCreateRequest.builder()
				.contractId(flatrateTermId)
				.productId(ProductId.ofRepoId(model.getM_Product_ID()))
				.build());
	}

	@Override
	public @NonNull Stream<LogEntryReverseRequest> createLogEntryReverseRequest(@NonNull final I_C_OrderLine model)
	{
		return Stream.of(LogEntryReverseRequest.builder()
				.referencedModel(TableRecordReference.of(I_C_OrderLine.Table_Name, model.getC_OrderLine_ID()))
				.build());
	}

	@Override
	public @NonNull Stream<LogEntryDeleteRequest> createLogEntryDeleteRequest(final I_C_OrderLine model)
	{
		return Stream.of(LogEntryDeleteRequest.builder()
				.referencedModel(TableRecordReference.of(I_C_OrderLine.Table_Name, model.getC_OrderLine_ID()))
				.build());
	}

	@Override
	public @NonNull Stream<FlatrateTermId> getContractIds(@NonNull final I_C_OrderLine model)
	{
		if (!probablyAppliesTo(model))
		{
			return Stream.empty();
		}

		final I_C_Order order = orderDAO.getById(OrderId.ofRepoId(model.getC_Order_ID()));
		if (order.isSOTrx())
		{
			return Stream.empty();
		}

		return Stream.of(FlatrateTermId.ofRepoId(model.getC_Flatrate_Term_ID()));
	}
}
