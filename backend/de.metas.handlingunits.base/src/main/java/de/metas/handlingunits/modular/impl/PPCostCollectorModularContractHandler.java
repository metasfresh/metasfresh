/*
 * #%L
 * de.metas.handlingunits.base
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

package de.metas.handlingunits.modular.impl;

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.modular.IModularContractTypeHandler;
import de.metas.contracts.modular.ModelAction;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Cost_Collector;
import org.eevolution.model.I_PP_Order;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Stream;

@Component
public class PPCostCollectorModularContractHandler implements IModularContractTypeHandler<I_PP_Cost_Collector>
{
	private final IPPOrderBL ppOrderBL = Services.get(IPPOrderBL.class);

	@Override
	@NonNull
	public Class<I_PP_Cost_Collector> getType()
	{
		return I_PP_Cost_Collector.class;
	}

	@Override
	public boolean applies(@NonNull final I_PP_Cost_Collector ppCostCollector)
	{
		return ppOrderBL.isModularOrder(PPOrderId.ofRepoId(ppCostCollector.getPP_Order_ID()));
	}

	@Override
	public boolean applies(final @NonNull LogEntryContractType logEntryContractType)
	{
		return logEntryContractType.isModularContractType();
	}

	@Override
	@NonNull
	public Stream<FlatrateTermId> streamContractIds(@NonNull final I_PP_Cost_Collector ppCostCollector)
	{
		return Optional.of(ppCostCollector.getPP_Order_ID())
				.map(PPOrderId::ofRepoId)
				.map(ppOrderBL::getById)
				.map(I_PP_Order::getModular_Flatrate_Term_ID)
				.map(FlatrateTermId::ofRepoIdOrNull)
				.stream();
	}

	@Override
	public void validateDocAction(@NonNull final I_PP_Cost_Collector ppCostCollector, @NonNull final ModelAction action)
	{
		if (action != ModelAction.COMPLETED)
		{
			throw new AdempiereException("Unsupported model action!")
					.appendParametersToMessage()
					.setParameter("Action", action)
					.setParameter("PP_Cost_Collector_ID", ppCostCollector.getPP_Cost_Collector_ID());
		}
	}
}
