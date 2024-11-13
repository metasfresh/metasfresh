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

package de.metas.contracts.modular.computing.salescontract.informative;

import de.metas.contracts.modular.ModularContractService;
import de.metas.contracts.modular.invgroup.interceptor.ModCntrInvoicingGroupRepository;
import de.metas.contracts.modular.log.LogEntryDocumentType;
import de.metas.contracts.modular.log.ModularContractLogDAO;
import de.metas.contracts.modular.workpackage.impl.AbstractShippingNotificationLogHandler;
import de.metas.lang.SOTrx;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderLineId;
import de.metas.product.ProductPrice;
import de.metas.shippingnotification.ShippingNotificationService;
import de.metas.util.Services;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
class ShippingNotificationLineLog extends AbstractShippingNotificationLogHandler
{
	@NonNull private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);

	@Getter @NonNull private final SalesInformativeLogComputingMethod computingMethod;
	@Getter @NonNull private final LogEntryDocumentType logEntryDocumentType = LogEntryDocumentType.SHIPPING_NOTIFICATION;

	public ShippingNotificationLineLog(
			@NonNull final ShippingNotificationService notificationService,
			@NonNull final ModCntrInvoicingGroupRepository modCntrInvoicingGroupRepository,
			@NonNull final ModularContractLogDAO contractLogDAO,
			@NonNull final ModularContractService modularContractService,
			@NonNull final SalesInformativeLogComputingMethod computingMethod)
	{
		super(notificationService, modCntrInvoicingGroupRepository, contractLogDAO, modularContractService);
		this.computingMethod = computingMethod;
	}

	@Override
	protected SOTrx getSOTrx()
	{
		return SOTrx.SALES;
	}

	@Override
	protected ProductPrice getProductPrice(@NonNull final CreateLogRequest createLogRequest, @NonNull final OrderLineId orderLineId)
	{
		return orderLineBL.getPriceActual(orderLineId);
	}

	@Override
	protected boolean isBillable()
	{
		return false;
	}
}
