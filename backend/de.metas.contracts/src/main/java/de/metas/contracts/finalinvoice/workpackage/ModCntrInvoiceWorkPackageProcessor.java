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

package de.metas.contracts.finalinvoice.workpackage;

import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.common.util.Check;
import de.metas.invoicecandidate.api.CreateInvoiceForModelService;
import de.metas.invoicecandidate.process.params.InvoicingParams;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;

import javax.annotation.Nullable;
import java.util.List;

public class ModCntrInvoiceWorkPackageProcessor extends WorkpackageProcessorAdapter
{
	private final CreateInvoiceForModelService createInvoiceForModelService = SpringContextHolder.instance.getBean(CreateInvoiceForModelService.class);

	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workPackage, @Nullable final String localTrxName)
	{
		final List<TableRecordReference> modularContracts = retrieveItems(TableRecordReference.class);

		Check.assume(modularContracts.size() == 1, "Only one contract can be enqueued per workpackage !");

		createInvoiceForModelService.generateIcsAndInvoices(modularContracts, getInvoicingParams());

		return Result.SUCCESS;
	}

	@NonNull
	private InvoicingParams getInvoicingParams()
	{
		return InvoicingParams.ofParams(getParameters());
	}
}
