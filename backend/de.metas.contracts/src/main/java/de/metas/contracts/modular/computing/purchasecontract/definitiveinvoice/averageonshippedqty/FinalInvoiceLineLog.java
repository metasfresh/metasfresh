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

package de.metas.contracts.modular.computing.purchasecontract.definitiveinvoice.averageonshippedqty;

import de.metas.contracts.modular.ComputingMethodType;
import de.metas.contracts.modular.ModularContractService;
import de.metas.contracts.modular.invgroup.interceptor.ModCntrInvoicingGroupRepository;
import de.metas.contracts.modular.log.ModularContractLogDAO;
import de.metas.contracts.modular.log.ModularContractLogService;
import de.metas.contracts.modular.workpackage.impl.AbstractFinalInvoiceLineLog;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.InvoiceLineId;
import de.metas.lang.SOTrx;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.springframework.stereotype.Component;

@Component
@Getter
public class FinalInvoiceLineLog extends AbstractFinalInvoiceLineLog
{
	@NonNull private final DefinitiveInvoiceAverageAVOnShippedQtyComputingMethod computingMethod;
	@NonNull private final ModularContractService modularContractService;

	private final boolean billable = true;

	public FinalInvoiceLineLog(
			@NonNull final ModularContractService modularContractService,
			@NonNull final ModularContractLogDAO contractLogDAO,
			@NonNull final ModularContractLogService modularContractLogService,
			@NonNull final ModCntrInvoicingGroupRepository modCntrInvoicingGroupRepository,
			@NonNull final DefinitiveInvoiceAverageAVOnShippedQtyComputingMethod computingMethod)
	{
		super(modularContractService, contractLogDAO, modularContractLogService, modCntrInvoicingGroupRepository);
		this.computingMethod = computingMethod;
		this.modularContractService = modularContractService;
	}

	@Override
	public boolean applies(final @NonNull CreateLogRequest request)
	{
		final TableRecordReference recordRef = request.getRecordRef();
		if (!recordRef.tableNameEqualsTo(getSupportedTableName()))
		{
			return false;
		}

		final InvoiceLineId invoiceLineId = InvoiceLineId.ofRepoId(recordRef.getRecord_ID());
		final I_C_InvoiceLine invoiceLineRecord = invoiceBL.getLineById(invoiceLineId);
		final I_C_Invoice invoiceRecord = invoiceBL.getById(InvoiceId.ofRepoId(invoiceLineRecord.getC_Invoice_ID()));
		final SOTrx soTrx = SOTrx.ofBoolean(invoiceRecord.isSOTrx());
		if(soTrx.isSales() || !invoiceBL.isFinalInvoiceOrFinalCreditMemo(invoiceRecord))
		{
			return false;
		}

		return modularContractService.isFinalInvoiceLineForComputingMethod(invoiceLineId,
																		   ComputingMethodType.PurchaseAverageAddedValueOnShippedQuantity,
																		   request.getColumnOption());
	}
}
