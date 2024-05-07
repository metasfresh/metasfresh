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

package de.metas.contracts.modular.computing.purchasecontract.interim;

import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.modular.ModularContractProvider;
import de.metas.contracts.modular.invgroup.interceptor.ModCntrInvoicingGroupRepository;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.contracts.modular.log.LogEntryDocumentType;
import de.metas.contracts.modular.log.ModularContractLogDAO;
import de.metas.contracts.modular.log.ModularContractLogService;
import de.metas.contracts.modular.workpackage.impl.AbstractInterimInvoiceLineLog;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.organization.IOrgDAO;
import de.metas.product.IProductBL;
import de.metas.util.Services;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.I_C_InvoiceLine;
import org.springframework.stereotype.Component;

@Component
@Getter
public class InterimInvoiceLineLog extends AbstractInterimInvoiceLineLog
{
	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);
	private final ModularContractLogDAO contractLogDAO;
	private final ModularContractLogService modularContractLogService;
	private final ModCntrInvoicingGroupRepository modCntrInvoicingGroupRepository;

	private static final AdMessageKey MSG_ON_COMPLETE_DESCRIPTION = AdMessageKey.of("de.metas.contracts.modular.interimInvoiceCompleteLogDescription");
	private static final AdMessageKey MSG_ON_REVERSE_DESCRIPTION = AdMessageKey.of("de.metas.contracts.modular.interimInvoiceReverseLogDescription");

	@Getter @NonNull private final InterimComputingMethod computingMethod;
	@Getter @NonNull private final String supportedTableName = I_C_InvoiceLine.Table_Name;
	@Getter @NonNull private final LogEntryDocumentType logEntryDocumentType = LogEntryDocumentType.INTERIM_INVOICE;
	@Getter @NonNull private final LogEntryContractType logEntryContractType = LogEntryContractType.INTERIM;

	public InterimInvoiceLineLog(
			@NonNull final InterimComputingMethod computingMethod,
			@NonNull final ModularContractLogDAO contractLogDAO,
			@NonNull final ModularContractLogService modularContractLogService,
			@NonNull final ModCntrInvoicingGroupRepository modCntrInvoicingGroupRepository,
			@NonNull final ModularContractProvider modularContractProvider)
	{
		super(contractLogDAO, modularContractLogService, modCntrInvoicingGroupRepository, modularContractProvider);
		this.computingMethod = computingMethod;
		this.contractLogDAO = contractLogDAO;
		this.modularContractLogService = modularContractLogService;
		this.modCntrInvoicingGroupRepository = modCntrInvoicingGroupRepository;
	}

}
