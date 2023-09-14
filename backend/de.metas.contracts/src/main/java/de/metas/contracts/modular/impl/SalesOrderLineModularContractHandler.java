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

import de.metas.contracts.ConditionsId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.IModularContractTypeHandler;
import de.metas.contracts.modular.ModelAction;
import de.metas.contracts.modular.ModularContract_Constants;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.contracts.modular.settings.ModularContractSettings;
import de.metas.contracts.modular.settings.ModularContractSettingsDAO;
import de.metas.lang.SOTrx;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class SalesOrderLineModularContractHandler implements IModularContractTypeHandler<I_C_OrderLine>
{
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);

	@NonNull
	private final ModularContractSettingsDAO modularContractSettingsDAO;

	@NonNull
	@Override
	public Class<I_C_OrderLine> getType()
	{
		return I_C_OrderLine.class;
	}

	@Override
	public boolean applies(@NonNull final I_C_OrderLine orderLine)
	{
		final I_C_Order order = orderBL.getById(OrderId.ofRepoId(orderLine.getC_Order_ID()));
		return SOTrx.ofBoolean(order.isSOTrx()).isSales();
	}

	@Override
	public boolean applies(final @NonNull LogEntryContractType logEntryContractType)
	{
		return logEntryContractType.isModularContractType();
	}

	@Override
	public @NonNull Stream<FlatrateTermId> streamContractIds(@NonNull final I_C_OrderLine orderLine)
	{
		final I_C_Order order = orderBL.getById(OrderId.ofRepoId(orderLine.getC_Order_ID()));
		if (!order.isSOTrx())
		{
			return Stream.empty();
		}

		return flatrateBL.getByOrderLineId(OrderLineId.ofRepoId(orderLine.getC_OrderLine_ID()), TypeConditions.MODULAR_CONTRACT)
				.map(flatrateTerm -> FlatrateTermId.ofRepoId(flatrateTerm.getC_Flatrate_Term_ID()))
				.stream();
	}

	@Override
	public void validateAction(
			@NonNull final I_C_OrderLine ignored,
			@NonNull final ModelAction action)
	{
		switch (action)
		{
			case COMPLETED ->
			{
			}
			case VOIDED -> throw new AdempiereException(ModularContract_Constants.MSG_ERROR_DOC_ACTION_NOT_ALLOWED);
			case REACTIVATED, REVERSED -> throw new AdempiereException(ModularContract_Constants.MSG_REACTIVATE_NOT_ALLOWED);
			default -> throw new AdempiereException(ModularContract_Constants.MSG_ERROR_DOC_ACTION_UNSUPPORTED);
		}
	}

	@Override
	public void createContractIfRequired(final @NonNull I_C_OrderLine orderLine)
	{
		if (!isModularContractLine(orderLine))
		{
			return;
		}

		if (flatrateBL.existsTermForOrderLine(orderLine))
		{
			return;
		}

		createModularContract(orderLine);
	}

	private boolean isModularContractLine(@NonNull final I_C_OrderLine orderLine)
	{
		return Optional.ofNullable(ConditionsId.ofRepoIdOrNull(orderLine.getC_Flatrate_Conditions_ID()))
				.map(flatrateBL::isModularContract)
				.orElse(false);
	}

	private void createModularContract(@NonNull final I_C_OrderLine orderLine)
	{
		final ConditionsId conditionsId = ConditionsId.ofRepoId(orderLine.getC_Flatrate_Conditions_ID());
		validateContractSettingEligible(conditionsId);

		final I_C_Flatrate_Term modularContract = flatrateBL.createContractForOrderLine(orderLine);

		flatrateBL.complete(modularContract);
	}

	private void validateContractSettingEligible(@NonNull final ConditionsId conditionsId)
	{
		final ModularContractSettings settings = modularContractSettingsDAO.getByFlatrateConditionsId(conditionsId);
		if (settings.getModuleConfigs().isEmpty())
		{
			throw new AdempiereException("Could not create contract! Missing module configs for modular contract term")
					.appendParametersToMessage()
					.setParameter("ConditionsId", conditionsId)
					.markAsUserValidationError();
		}
	}
}
