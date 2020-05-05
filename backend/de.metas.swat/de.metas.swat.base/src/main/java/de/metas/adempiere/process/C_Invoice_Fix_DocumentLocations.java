/**
 * 
 */
package de.metas.adempiere.process;

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


import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.ITrxRunConfig;
import org.adempiere.ad.trx.api.ITrxRunConfig.OnRunnableFail;
import org.adempiere.ad.trx.api.ITrxRunConfig.OnRunnableSuccess;
import org.adempiere.ad.trx.api.ITrxRunConfig.TrxPropagation;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.tools.AdempiereToolsHelper;
import org.compiere.model.Query;
import org.compiere.util.Env;
import org.compiere.util.Trx;
import org.compiere.util.TrxRunnable;

import ch.qos.logback.classic.Level;
import de.metas.adempiere.model.I_C_Invoice;
import de.metas.document.IDocumentLocationBL;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;

/**
 * @author ts
 * @deprecated this was just a dirty hack to get the data fast.
 */
@Deprecated
public class C_Invoice_Fix_DocumentLocations
{
	protected String doItWithTrxRunner() throws Exception
	{
		final String trxName = Trx.createTrxName("C_Invoice_Fix_DocumentLocations");
		final Properties ctx = Env.getCtx();
		Trx.get(trxName, true);

		final ITrxRunConfig trxRunConfig = Services.get(ITrxManager.class).createTrxRunConfig(TrxPropagation.NESTED, OnRunnableSuccess.COMMIT, OnRunnableFail.ASK_RUNNABLE);
		Services.get(ITrxManager.class).run(
				trxName,
				trxRunConfig,
				(TrxRunnable)trxName1 -> {
					List<I_C_Invoice> invoicesToFix = retrieveInvoices(ctx, trxName1);

					int counter = 0;
					final int commitSize = 100;

					while (!invoicesToFix.isEmpty())
					{
						for (final I_C_Invoice invoiceToFix : invoicesToFix)
						{
							Services.get(IDocumentLocationBL.class).setBPartnerAddress(invoiceToFix);
							InterfaceWrapperHelper.save(invoiceToFix);

							counter++;
							if (counter % commitSize == 0)
							{
								Check.assume(Trx.get(trxName1, false).commit(), "Commit of Trx '" + trxName1 + " returns true");
							}

						}
						invoicesToFix = retrieveInvoices(ctx, trxName1);
					}
				});

		return "@Success@";
	}

	private List<I_C_Invoice> retrieveInvoices(final Properties ctx, final String trxName)
	{
		final List<I_C_Invoice> invoiceToFix = new Query(ctx, I_C_Invoice.Table_Name, "DocStatus='CO' AND COALESCE(BPartnerAddress,'')=''", trxName)
				.setOnlyActiveRecords(true)
				.setLimit(50)
				// .setOrderBy(I_C_Invoice.COLUMNNAME_C_Invoice_ID)
				.list(I_C_Invoice.class);

		return invoiceToFix;
	}

	public static void main(String[] args)
	{
		AdempiereToolsHelper.getInstance().startupMinimal();
		LogManager.setLevel(Level.WARN);

		C_Invoice_Fix_DocumentLocations process = new C_Invoice_Fix_DocumentLocations();
		try
		{
			process.doItWithTrxRunner();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
