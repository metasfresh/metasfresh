/*
 * #%L
 * de.metas.business
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

package de.metas.invoice.process;

import de.metas.i18n.AdMessageKey;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.IQueryUpdater;
import org.compiere.model.I_C_Invoice;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.util.Collection;

public class C_Invoice_OverrideDueDate extends JavaProcess implements IProcessPrecondition, IProcessDefaultParametersProvider
{
	private final static String PARAM_OVERRIDE_DUE_DATE = "OverrideDueDate";
	public static final AdMessageKey PAID_INVOICE_MESSAGE = AdMessageKey.of("Invoice_already_paid");
	@Param(parameterName = PARAM_OVERRIDE_DUE_DATE, mandatory = true)
	private Timestamp p_OverrideDueDate;

	private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		if (context.isMoreThanOneSelected())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		final Collection<String> paidInvoiceDocNos = invoiceDAO.retrievePaidInvoiceDocNosForFilter(context.getQueryFilter(I_C_Invoice.class));
		if (!paidInvoiceDocNos.isEmpty())
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(PAID_INVOICE_MESSAGE, paidInvoiceDocNos));
		}
		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final IQueryUpdater<I_C_Invoice> queryUpdater = queryBL.createCompositeQueryUpdater(I_C_Invoice.class)
				.addSetColumnValue(I_C_Invoice.COLUMNNAME_DueDate, p_OverrideDueDate);

		final IQueryFilter<I_C_Invoice> filter = getProcessInfo().getQueryFilterOrElseFalse();
		queryBL.createQueryBuilder(I_C_Invoice.class)
				.addOnlyActiveRecordsFilter()
				.addFilter(filter)
				.create()
				.update(queryUpdater);
		return MSG_OK;
	}

	@Nullable
	@Override
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		if (PARAM_OVERRIDE_DUE_DATE.equals(parameter.getColumnName()))
		{
			return queryBL.createQueryBuilder(I_C_Invoice.class)
					.addOnlyActiveRecordsFilter()
					.addFilter(getProcessInfo().getQueryFilterOrElseFalse())
					.create()
					.first(I_C_Invoice.COLUMNNAME_DueDate, Timestamp.class);
		}
		return IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
	}
}
