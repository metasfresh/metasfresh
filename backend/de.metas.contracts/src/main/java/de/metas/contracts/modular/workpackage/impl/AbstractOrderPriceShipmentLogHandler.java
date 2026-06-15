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

package de.metas.contracts.modular.workpackage.impl;

import de.metas.contracts.modular.ModularContractService;
import de.metas.contracts.modular.computing.IComputingMethodHandler;
import de.metas.contracts.modular.invgroup.interceptor.ModCntrInvoicingGroupRepository;
import de.metas.lang.SOTrx;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderLineId;
import de.metas.product.ProductPrice;
import de.metas.util.Services;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractOrderPriceShipmentLogHandler extends AbstractShipmentLogHandler
{
	@NonNull private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);

	public AbstractOrderPriceShipmentLogHandler(@NonNull final ModularContractService modularContractService,
			final @NonNull ModCntrInvoicingGroupRepository modCntrInvoicingGroupRepository,
			final @NonNull IComputingMethodHandler computingMethod)
	{
		super(modularContractService, modCntrInvoicingGroupRepository, computingMethod);
	}

	@NotNull
	@Override
	protected ProductPrice getProductPrice(@NonNull final CreateLogRequest createLogRequest, @NonNull final OrderLineId orderLineId)
	{
		return orderLineBL.getPriceActual(orderLineId);
	}

	@Override
	protected SOTrx getSOTrx()
	{
		return SOTrx.PURCHASE;
	}
}
