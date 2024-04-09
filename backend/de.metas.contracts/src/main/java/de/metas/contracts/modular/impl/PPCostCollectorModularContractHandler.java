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

package de.metas.contracts.modular.impl;

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.modular.ComputingMethodType;
import de.metas.contracts.modular.ModularContractProvider;
import de.metas.contracts.modular.computing.CalculationRequest;
import de.metas.contracts.modular.computing.CalculationResponse;
import de.metas.contracts.modular.computing.IModularContractComputingMethodHandler;
import de.metas.contracts.modular.log.ModularContractLogService;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.eevolution.api.IPPCostCollectorBL;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.PPCostCollectorId;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Cost_Collector;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

import static de.metas.contracts.modular.ComputingMethodType.PPCOSTCOLLECTOR_MODULAR;

@Component
@RequiredArgsConstructor
public class PPCostCollectorModularContractHandler implements IModularContractComputingMethodHandler
{
	private final IPPOrderBL ppOrderBL = Services.get(IPPOrderBL.class);
	private final IPPCostCollectorBL ppCostCollectorBL = Services.get(IPPCostCollectorBL.class);

	@NonNull private final ModularContractLogService contractLogService;
	@NonNull private final ModularContractProvider contractProvider;

	@Override
	public boolean applies(@NonNull final TableRecordReference tableRecordReference)
	{
		if(tableRecordReference.getTableName().equals(I_PP_Cost_Collector.Table_Name))
		{
			return ppOrderBL.isModularOrder(PPOrderId.ofRepoId(ppCostCollectorBL.getById(PPCostCollectorId.ofRepoId(tableRecordReference.getRecord_ID())).getPP_Order_ID()));
		}
		return false;
	}

	@Override
	@NonNull
	public Stream<FlatrateTermId> streamContractIds(@NonNull final TableRecordReference tableRecordReference)
	{
		if(tableRecordReference.getTableName().equals(I_PP_Cost_Collector.Table_Name))
		{
			return contractProvider.streamModularPurchaseContractsForPPOrder(PPCostCollectorId.ofRepoId(tableRecordReference.getRecord_ID()));
		}
		return Stream.empty();
	}

	@Override
	public @NonNull ComputingMethodType getComputingMethodType()
	{
		return PPCOSTCOLLECTOR_MODULAR;
	}

	@Override
	public @NonNull CalculationResponse calculate(final @NonNull CalculationRequest request)
	{
		return null;
	}
}
