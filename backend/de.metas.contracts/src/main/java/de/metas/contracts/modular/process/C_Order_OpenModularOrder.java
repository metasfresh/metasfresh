/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.contracts.modular.process;

import de.metas.contracts.modular.ModularContractService;
import de.metas.i18n.AdMessageKey;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.SpringContextHolder;

public class C_Order_OpenModularOrder extends JavaProcess implements IProcessPrecondition
{
	public static final AdMessageKey MSG_LINKED_PURCHASE_ORDER_NOT_OPEN = AdMessageKey.of("C_Order_OpenModularOrder_LinkedModularPurchaseOrderNotOpen");
	@NonNull final ModularContractService modularContractService = SpringContextHolder.instance.getBean(ModularContractService.class);
	@NonNull final IOrderBL orderBL = Services.get(IOrderBL.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		final OrderId orderId = context.getSingleSelectedRecordId(OrderId.class);
		if(!orderBL.isClosed(orderId))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Order isn't closed");
		}

		if(!modularContractService.isModularOrder(orderId) && !modularContractService.hasLinkedPurchaseModularContracts(orderId))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Order isn't in modular contract context");
		}

		if(modularContractService.hasLinkedClosedPurchaseModularContracts(orderId))
		{
			return ProcessPreconditionsResolution.reject(MSG_LINKED_PURCHASE_ORDER_NOT_OPEN);
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final OrderId orderId = OrderId.ofRepoId(getRecord_ID());
		modularContractService.openOrder(orderId);
		return MSG_OK;
	}
}
