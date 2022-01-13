package de.metas.invoice.process;

import lombok.NonNull;

import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Invoice;

import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.report.ExecuteReportStrategy;
import de.metas.report.ReportStarter;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class C_Invoice_SalesInvoiceJasperWithAttachedDocuments extends ReportStarter implements IProcessPrecondition
{
	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(
			@NonNull final IProcessPreconditionsContext context)
	{
		return ProcessPreconditionsResolution
				.acceptIf(I_C_Invoice.Table_Name.equals(context.getTableName()));
	}

	@Override
	protected ExecuteReportStrategy getExecuteReportStrategy()
	{
		return SpringContextHolder.instance.getBean(C_Invoice_SalesInvoiceJasperWithAttachedDocumentsStrategy.class);
	}

}
