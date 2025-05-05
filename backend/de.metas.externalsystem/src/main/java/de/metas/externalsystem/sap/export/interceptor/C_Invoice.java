/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.externalsystem.sap.export.interceptor;

import de.metas.externalsystem.sap.export.ExportAcctToSAPService;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_C_Invoice.class)
@Component
public class C_Invoice
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	@NonNull
	private final ExportAcctToSAPService exportAcctToSAPService;

	public C_Invoice(final @NonNull ExportAcctToSAPService exportAcctToSAPService)
	{
		this.exportAcctToSAPService = exportAcctToSAPService;
	}

	@DocValidate(timings = ModelValidator.TIMING_AFTER_POST)
	public void exportAfterPost(@NonNull final I_C_Invoice invoiceRecord)
	{
		trxManager.runAfterCommit(() -> exportAcctToSAPService.enqueueDocument(TableRecordReference.of(invoiceRecord)));
	}
}
