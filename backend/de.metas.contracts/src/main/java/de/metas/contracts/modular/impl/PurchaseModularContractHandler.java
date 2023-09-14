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
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.IModularContractTypeHandler;
import de.metas.contracts.modular.ModelAction;
import de.metas.contracts.modular.ModularContract_Constants;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.contracts.modular.log.ModularContractLogService;
import de.metas.lang.SOTrx;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

import static de.metas.contracts.modular.ModularContract_Constants.MSG_ERROR_PROCESSED_LOGS_CANNOT_BE_RECOMPUTED;

@Component
@RequiredArgsConstructor
public class PurchaseModularContractHandler implements IModularContractTypeHandler<I_C_Flatrate_Term>
{
	private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);

	@NonNull private final ModularContractLogService contractLogService;

	@Override
	public @NonNull Class<I_C_Flatrate_Term> getType()
	{
		return I_C_Flatrate_Term.class;
	}

	@Override
	public boolean applies(final @NonNull I_C_Flatrate_Term flatrateTermRecord)
	{
		if (!TypeConditions.ofCode(flatrateTermRecord.getType_Conditions()).isModularContractType())
		{
			return false;
		}

		final OrderLineId orderLineId = OrderLineId.ofRepoIdOrNull(flatrateTermRecord.getC_OrderLine_Term_ID());
		if (orderLineId == null)
		{
			return false;
		}

		final OrderId orderId = OrderId.ofRepoId(orderLineBL.getOrderLineById(orderLineId).getC_Order_ID());
		return SOTrx.ofBoolean(orderBL.getById(orderId).isSOTrx()).isPurchase();
	}

	@Override
	public boolean applies(final @NonNull LogEntryContractType logEntryContractType)
	{
		return logEntryContractType.isModularContractType();
	}

	@Override
	public @NonNull Stream<FlatrateTermId> streamContractIds(@NonNull final I_C_Flatrate_Term flatrateTermRecord)
	{
		return Stream.ofNullable(FlatrateTermId.ofRepoIdOrNull(flatrateTermRecord.getC_Flatrate_Term_ID()));
	}

	@Override
	public void validateAction(final @NonNull I_C_Flatrate_Term model, final @NonNull ModelAction action)
	{
		switch (action)
		{
			case COMPLETED -> {}
			case RECREATE_LOGS -> contractLogService.throwErrorIfProcessedLogsExistForRecord(TableRecordReference.of(model),
																							 MSG_ERROR_PROCESSED_LOGS_CANNOT_BE_RECOMPUTED);
			default -> throw new AdempiereException(ModularContract_Constants.MSG_ERROR_DOC_ACTION_UNSUPPORTED);
		}
	}
}
