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

import de.metas.contracts.modular.ModularContractService;
import de.metas.contracts.modular.invgroup.interceptor.ModCntrInvoicingGroupRepository;
import de.metas.contracts.modular.log.LogEntryDocumentType;
import de.metas.contracts.modular.log.ModularContractLogRepository;
import de.metas.contracts.modular.workpackage.impl.AbstractShippingNotificationLogHandler;
import de.metas.lang.SOTrx;
import de.metas.shippingnotification.ShippingNotificationService;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
@Getter
class ProformaShippingNotificationLineLog extends AbstractShippingNotificationLogHandler
{
	@NonNull private final AVInterimComputingMethod computingMethod;
	@NonNull private final LogEntryDocumentType logEntryDocumentType = LogEntryDocumentType.PROFORMA_SHIPPING_NOTIFICATION;

	public ProformaShippingNotificationLineLog(
			@NonNull final ShippingNotificationService notificationService,
			@NonNull final ModCntrInvoicingGroupRepository modCntrInvoicingGroupRepository,
			@NonNull final ModularContractLogRepository contractLogRepo,
			@NonNull final ModularContractService modularContractService,
			@NonNull final AVInterimComputingMethod computingMethod)
	{
		super(notificationService, modCntrInvoicingGroupRepository, contractLogRepo, modularContractService);
		this.computingMethod = computingMethod;
	}

	@Override
	protected SOTrx getSOTrx()
	{
		return SOTrx.PURCHASE;
	}
}
