/**
 *
 */
package de.metas.invoicecandidate.process;

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

import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.util.Env;

import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerBL;
import de.metas.invoicecandidate.model.I_C_ILCandHandler;
import de.metas.process.RunOutOfTrx;
import de.metas.process.JavaProcess;

/**
 * Creates missing invoice candidates. Note that the method {@link #retrieveHandlers()} can be overridden.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class C_Invoice_Candidate_Create_Missing extends JavaProcess
{
	// services
	private final transient IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);
	private final transient IInvoiceCandidateHandlerBL handlerBL = Services.get(IInvoiceCandidateHandlerBL.class);

	@Override
	protected void prepare()
	{
		// nothing to do
	}

	@Override
	@RunOutOfTrx
	protected final String doIt() throws Exception
	{
		final Properties ctx = getCtx();
		Check.assume(Env.getAD_Client_ID(ctx) > 0, "No point in calling this process with AD_Client_ID=0");

		try (final IAutoCloseable updateInProgressCloseable = invoiceCandBL.setUpdateProcessInProgress())
		{
			handlerBL.createMissingCandidates(ctx, retrieveHandlers());
		}
		return "@Success@";
	}

	/**
	 * May be overridden. Called by {@link #doIt()}. Returns a empty list, which will cause the system to invoke <b>all</b> the handlers we have.
	 *
	 * @return
	 */
	protected List<I_C_ILCandHandler> retrieveHandlers()
	{
		return Collections.emptyList();
	}
}
