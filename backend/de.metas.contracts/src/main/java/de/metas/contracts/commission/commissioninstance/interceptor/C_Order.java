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

import de.metas.logging.TableRecordMDC;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.model.I_C_Order;
import org.compiere.model.ModelValidator;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Interceptor(I_C_Order.class)
@Component
public class C_Order
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	private final C_OrderFacadeService orderFacadeService;

	public C_Order(@NonNull final C_OrderFacadeService orderFacadeService)
	{
		this.orderFacadeService = orderFacadeService;
	}

	@DocValidate(timings = {ModelValidator.TIMING_AFTER_COMPLETE, ModelValidator.TIMING_AFTER_CLOSE})
	public void createCommissionInstanceForMediatedOrder(@NonNull final I_C_Order order)
	{
		trxManager.getCurrentTrxListenerManagerOrAutoCommit()
				.runAfterCommit(() -> trxManager.runInNewTrx(() -> {
					try (final MDC.MDCCloseable ignored = TableRecordMDC.putTableRecordReference(order))
					{
						orderFacadeService.syncOrderToCommissionInstance(order, false /*isReactivated*/);
					}
				}));
	}

	@DocValidate(timings = ModelValidator.TIMING_AFTER_REACTIVATE)
	public void syncMediatedOrderWithCommissionInstance(@NonNull final I_C_Order order)
	{
		trxManager.getCurrentTrxListenerManagerOrAutoCommit()
				.runAfterCommit(() -> trxManager.runInNewTrx(() -> {
					try (final MDC.MDCCloseable ignored = TableRecordMDC.putTableRecordReference(order))
					{
						orderFacadeService.syncOrderToCommissionInstance(order, true /*isReactivated*/);
					}
				}));
	}
}
