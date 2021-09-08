/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.contracts.commission.commissioninstance.interceptor;

import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTriggerDocumentService;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.mediatedorder.MediatedOrder;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.mediatedorder.MediatedOrderFactory;
import de.metas.logging.LogManager;
import lombok.NonNull;
import org.compiere.model.I_C_Order;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class C_OrderFacadeService
{
	private static final Logger logger = LogManager.getLogger(C_InvoiceFacadeService.class);

	private final CommissionTriggerDocumentService commissionTriggerDocumentService;
	private final MediatedOrderFactory mediatedOrderFactory;

	public C_OrderFacadeService(
			final @NonNull CommissionTriggerDocumentService commissionTriggerDocumentService,
			final @NonNull MediatedOrderFactory mediatedOrderFactory)
	{
		this.commissionTriggerDocumentService = commissionTriggerDocumentService;
		this.mediatedOrderFactory = mediatedOrderFactory;
	}

	public void syncOrderToCommissionInstance(@NonNull final I_C_Order order, final boolean isReactivated)
	{
		final Optional<MediatedOrder> mediatedOrder = mediatedOrderFactory.forRecord(order);
		if (!mediatedOrder.isPresent())
		{
			logger.debug("The C_Order is not commission-relevant; -> nothing to do");
			return;
		}

		mediatedOrder.get().asCommissionTriggerDocuments()
				.forEach(mediatedOrderTrigger -> commissionTriggerDocumentService.syncTriggerDocumentToCommissionInstance(mediatedOrderTrigger, isReactivated));
	}
}
