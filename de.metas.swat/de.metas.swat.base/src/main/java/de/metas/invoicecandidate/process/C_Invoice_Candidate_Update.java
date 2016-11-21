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

import java.util.Properties;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.Env;

import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandInvalidUpdater;
import de.metas.invoicecandidate.api.InvoiceCandRecomputeTag;
import de.metas.process.RunOutOfTrx;
import de.metas.process.JavaProcess;

/**
 * @author tsa
 *
 */
public class C_Invoice_Candidate_Update extends JavaProcess
{
	// services
	private final transient IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);

	private boolean p_IsFullUpdate = false;

	@Override
	protected void prepare()
	{
		p_IsFullUpdate = getParameterAsIParams().getParameterAsBool("IsFullUpdate");
	}

	@Override
	@RunOutOfTrx
	protected String doIt() throws Exception
	{
		final Properties ctx = getCtx();
		Check.assume(Env.getAD_Client_ID(ctx) > 0, "No point in calling this process with AD_Client_ID=0");

		//
		// Create the updater
		final InvoiceCandRecomputeTag recomputeTag = InvoiceCandRecomputeTag.ofAD_PInstance_ID(getAD_PInstance_ID());
		final IInvoiceCandInvalidUpdater updater = invoiceCandBL.updateInvalid()
				.setContext(ctx, getTrxName())
				.setTaggedWithNoTag()
				.setRecomputeTagToUse(recomputeTag);
		if (p_IsFullUpdate)
		{
			updater.setTaggedWithAnyTag();
		}

		//
		// Execute the updater
		updater.update();

		return "@Success@";
	}
}
