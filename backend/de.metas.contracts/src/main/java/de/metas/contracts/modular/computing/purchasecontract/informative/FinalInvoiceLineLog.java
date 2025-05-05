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

package de.metas.contracts.modular.computing.purchasecontract.informative;

import de.metas.contracts.modular.ModularContractService;
import de.metas.contracts.modular.invgroup.interceptor.ModCntrInvoicingGroupRepository;
import de.metas.contracts.modular.log.LogEntryDocumentType;
import de.metas.contracts.modular.log.ModularContractLogDAO;
import de.metas.contracts.modular.log.ModularContractLogService;
import de.metas.contracts.modular.workpackage.impl.AbstractModularPurchaseInvoiceLineLog;
import de.metas.invoice.InvoiceLineId;
import de.metas.lang.SOTrx;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Invoice;
import org.springframework.stereotype.Component;

@Component
@Getter
public class FinalInvoiceLineLog extends AbstractModularPurchaseInvoiceLineLog
{
	@NonNull private final InformativeLogComputingMethod computingMethod;
	@NonNull private final LogEntryDocumentType logEntryDocumentType = LogEntryDocumentType.FINAL_INVOICE;

	public FinalInvoiceLineLog(
			@NonNull final ModularContractService modularContractService,
			@NonNull final ModularContractLogDAO contractLogDAO,
			@NonNull final ModularContractLogService modularContractLogService,
			@NonNull final ModCntrInvoicingGroupRepository modCntrInvoicingGroupRepository,
			@NonNull final InformativeLogComputingMethod computingMethod)
	{
		super(modularContractService, contractLogDAO, modularContractLogService, modCntrInvoicingGroupRepository);
		this.computingMethod = computingMethod;
	}

	@Override
	public boolean applies(final @NonNull CreateLogRequest request)
	{
		final TableRecordReference recordRef = request.getRecordRef();
		if (!recordRef.tableNameEqualsTo(getSupportedTableName()))
		{
			return false;
		}

		final I_C_Invoice invoiceRecord = invoiceBL.getByLineId(InvoiceLineId.ofRepoId(recordRef.getRecord_ID()));
		final SOTrx soTrx = SOTrx.ofBoolean(invoiceRecord.isSOTrx());

		return soTrx.isPurchase() && invoiceBL.isFinalInvoiceOrFinalCreditMemo(invoiceRecord);
	}
}
