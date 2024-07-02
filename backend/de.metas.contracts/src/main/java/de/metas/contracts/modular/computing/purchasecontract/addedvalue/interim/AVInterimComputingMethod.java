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

package de.metas.contracts.modular.computing.purchasecontract.addedvalue.interim;

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.ComputingMethodType;
import de.metas.contracts.modular.ModularContractProvider;
import de.metas.contracts.modular.computing.purchasecontract.AbstractInterestComputingMethod;
import de.metas.contracts.modular.interest.log.ModularLogInterestRepository;
import de.metas.contracts.modular.invgroup.interceptor.ModCntrInvoicingGroupRepository;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.contracts.modular.log.ModularContractLogService;
import de.metas.lang.SOTrx;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.shippingnotification.ShippingNotificationRepository;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.stereotype.Component;

@Component
public class AVInterimComputingMethod extends AbstractInterestComputingMethod
{

	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
	private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);

	public AVInterimComputingMethod(
			@NonNull final ShippingNotificationRepository shippingNotificationRepository,
			@NonNull final ModularContractProvider contractProvider,
			@NonNull final ModCntrInvoicingGroupRepository invoicingGroupRepository,
			@NonNull final ModularContractLogService modularContractLogService,
			@NonNull final ModularLogInterestRepository modularLogInterestRepository)
	{
		super(shippingNotificationRepository, contractProvider, invoicingGroupRepository, modularContractLogService, modularLogInterestRepository);
	}

	@Override
	public @NonNull ComputingMethodType getComputingMethodType()
	{
		return ComputingMethodType.AddValueOnInterim;
	}

	@Override
	public boolean applies(final @NonNull TableRecordReference recordRef, @NonNull final LogEntryContractType logEntryContractType)
	{
		if (!logEntryContractType.isModularContractType())
		{
			return false;
		}
		if (recordRef.tableNameEqualsTo(I_C_Flatrate_Term.Table_Name))
		{
			final I_C_Flatrate_Term flatrateTermRecord = flatrateBL.getById(FlatrateTermId.ofRepoId(recordRef.getRecord_ID()));
			if (!TypeConditions.ofCode(flatrateTermRecord.getType_Conditions()).isInterimContractType())
			{
				return false;
			}

			final OrderLineId orderLineId = OrderLineId.ofRepoIdOrNull(flatrateTermRecord.getC_OrderLine_Term_ID());
			if (orderLineId == null)
			{
				return false;
			}

			final OrderId orderId = orderLineBL.getOrderIdByOrderLineId(orderLineId);
			return SOTrx.ofBoolean(orderBL.getById(orderId).isSOTrx()).isPurchase();
		}

		return false;
	}
}
