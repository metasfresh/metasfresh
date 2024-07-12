package de.metas.invoicecandidate.modelvalidator;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerBL;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerDAO;
import de.metas.invoicecandidate.model.I_C_ILCandHandler;
import de.metas.invoicecandidate.modelvalidator.ilhandler.ILHandlerModelInterceptor;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.table.api.IADTableDAO;
import org.compiere.model.ModelValidator;
import org.compiere.util.Env;

@Interceptor(I_C_ILCandHandler.class)
public class C_ILCandHandler
{
	public static final transient C_ILCandHandler instance = new C_ILCandHandler();

	private C_ILCandHandler()
	{
		super();
	}

	@Init
	public void init(final IModelValidationEngine engine)
	{
		// Register one document interceptor for each table name/handler.
		{
			final IInvoiceCandidateHandlerBL invoiceCandidateHandlerBL = Services.get(IInvoiceCandidateHandlerBL.class);
			final IInvoiceCandidateHandlerDAO invoiceCandidateHandlerDAO = Services.get(IInvoiceCandidateHandlerDAO.class);
			final IADTableDAO tableDAO = Services.get(IADTableDAO.class);

			for (final I_C_ILCandHandler handlerDef : invoiceCandidateHandlerDAO.retrieveAll(Env.getCtx()))
			{
				final IInvoiceCandidateHandler handler = invoiceCandidateHandlerBL.mkInstance(handlerDef);
				final String tableName = handler.getSourceTable();

				// Skip if the handler is not about an actual existing table name (e.g. like Manual handler)
				if (!tableDAO.isExistingTable(tableName))
				{
					continue;
				}

				final ILHandlerModelInterceptor modelInterceptor = new ILHandlerModelInterceptor(handler);
				engine.addModelValidator(modelInterceptor);
			}
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }
			, ifColumnsChanged = { I_C_ILCandHandler.COLUMNNAME_Classname })
	public void validateClassname(final I_C_ILCandHandler handlerDef)
	{
		final boolean failIfClassNotFound = true;
		Services.get(IInvoiceCandidateHandlerBL.class).evalClassName(handlerDef, failIfClassNotFound);
	}
}
