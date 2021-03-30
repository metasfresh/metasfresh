package de.metas.contracts.commission.commissioninstance.interceptor;

import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.ModelValidator;
import org.slf4j.MDC.MDCCloseable;
import org.springframework.stereotype.Component;

import de.metas.logging.TableRecordMDC;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Interceptor(I_C_Invoice.class)
@Component
public class C_Invoice
{
	private final C_InvoiceFacadeService invoiceFacadeService;
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	public C_Invoice(@NonNull final C_InvoiceFacadeService invoiceFacadeService)
	{
		this.invoiceFacadeService = invoiceFacadeService;
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_COMPLETE, ModelValidator.TIMING_AFTER_CLOSE })
	public void createCommissionInstanceForInvoice(@NonNull final I_C_Invoice invoiceRecord)
	{
		trxManager.getCurrentTrxListenerManagerOrAutoCommit()
				.runAfterCommit(() -> trxManager.runInNewTrx(() -> {
					try (final MDCCloseable ignored = TableRecordMDC.putTableRecordReference(invoiceRecord))
					{
						invoiceFacadeService.syncInvoiceToCommissionInstance(invoiceRecord);
					}
				}));
	}
}
