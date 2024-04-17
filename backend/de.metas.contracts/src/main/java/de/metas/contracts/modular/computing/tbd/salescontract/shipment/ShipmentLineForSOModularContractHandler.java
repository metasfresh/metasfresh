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

package de.metas.contracts.modular.computing.tbd.salescontract.shipment;

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.modular.ComputingMethodType;
import de.metas.contracts.modular.computing.IComputingMethodHandler;
import de.metas.contracts.modular.computing.ComputingRequest;
import de.metas.contracts.modular.computing.ComputingResponse;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

import static de.metas.contracts.modular.ComputingMethodType.SHIPMENT_LINE_FOR_SO_MODULAR_DEPRECATED;

/**
 * @deprecated If needed, please move/use code in the new computing methods in package de.metas.contracts.modular.computing.salescontract
 */
@Deprecated
@Component
@RequiredArgsConstructor
public class ShipmentLineForSOModularContractHandler implements IComputingMethodHandler
{
	private final IInOutDAO inOutDao = Services.get(IInOutDAO.class);
	private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);

	@Override
	public @NonNull ComputingMethodType getComputingMethodType()
	{
		return SHIPMENT_LINE_FOR_SO_MODULAR_DEPRECATED;
	}

	@Override
	public boolean applies(final @NonNull TableRecordReference recordRef, final @NonNull LogEntryContractType logEntryContractType)
	{
		if (recordRef.getTableName().equals(I_M_InOutLine.Table_Name) && logEntryContractType.isModularContractType())
		{
			final I_M_InOutLine inOutLineRecord = inOutDao.getLineByIdInTrx(InOutLineId.ofRepoId(recordRef.getRecord_ID()));
			final I_M_InOut inOutRecord = inOutDao.getById(InOutId.ofRepoId(inOutLineRecord.getM_InOut_ID()));
			final OrderId orderId = OrderId.ofRepoIdOrNull(inOutLineRecord.getC_Order_ID());
			if (orderId == null)
			{
				return false;
			}
			return inOutRecord.isSOTrx();
		}
		return false;
	}

	@Override
	public @NonNull Stream<FlatrateTermId> streamContractIds(@NonNull final TableRecordReference recordRef)
	{
		if (recordRef.getTableName().equals(I_M_InOutLine.Table_Name))
		{
			final I_M_InOutLine inOutLineRecord = inOutDao.getLineByIdInTrx(InOutLineId.ofRepoId(recordRef.getRecord_ID()));
			final I_C_Order order = orderBL.getById(OrderId.ofRepoId(inOutLineRecord.getC_Order_ID()));

			if (!order.isSOTrx())
			{
				return Stream.empty();
			}

			return flatrateBL.getByOrderLineId(OrderLineId.ofRepoId(inOutLineRecord.getC_OrderLine_ID()), TypeConditions.MODULAR_CONTRACT)
					.map(flatrateTerm -> FlatrateTermId.ofRepoId(flatrateTerm.getC_Flatrate_Term_ID()))
					.stream();
		}
		return Stream.empty();
	}

	@Override
	public @NonNull ComputingResponse compute(final @NonNull ComputingRequest request)
	{
		return null;
	}
}
