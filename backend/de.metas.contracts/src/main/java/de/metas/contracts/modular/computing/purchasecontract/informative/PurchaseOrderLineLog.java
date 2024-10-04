package de.metas.contracts.modular.computing.purchasecontract.informative;/*
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

import de.metas.contracts.modular.ModularContractService;
import de.metas.contracts.modular.invgroup.interceptor.ModCntrInvoicingGroupRepository;
import de.metas.contracts.modular.log.LogEntryDocumentType;
import de.metas.contracts.modular.workpackage.impl.AbstractOrderLineLog;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.I_C_OrderLine;
import org.springframework.stereotype.Component;

@Getter
@Component
class PurchaseOrderLineLog extends AbstractOrderLineLog
{
	@NonNull private final InformativeLogComputingMethod computingMethod;
	@NonNull private final String supportedTableName = I_C_OrderLine.Table_Name;
	@NonNull private final LogEntryDocumentType logEntryDocumentType = LogEntryDocumentType.PURCHASE_ORDER;

	public PurchaseOrderLineLog(@NonNull final ModularContractService modularContractService,
			final @NonNull ModCntrInvoicingGroupRepository modCntrInvoicingGroupRepository,
			final @NonNull InformativeLogComputingMethod computingMethod)
	{
		super(modularContractService, modCntrInvoicingGroupRepository);
		this.computingMethod = computingMethod;
	}
}
