package de.metas.acct.model.validator;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import org.adempiere.acct.api.IFactAcctDAO;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.acct.DocLine_Invoice;
import org.compiere.acct.Doc_Invoice;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_M_MatchInv;
import org.compiere.model.MPeriod;
import org.compiere.model.ModelValidator;
import org.compiere.model.X_C_DocType;

@Interceptor(I_M_MatchInv.class)
public class M_MatchInv
{
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_BEFORE_DELETE })
	public void unpostInvoiceIfNeeded(final I_M_MatchInv matchInv)
	{
		// Do nothing if there is no need to report the invoice for this M_MatchInv
		if (!DocLine_Invoice.isInvoiceRepostingRequired(matchInv))
		{
			return;
		}

		final I_C_Invoice invoice = matchInv.getC_InvoiceLine().getC_Invoice();

		// Do nothing if invoice is not posted yet
		if (!invoice.isPosted())
		{
			return;
		}

		Doc_Invoice.unpost(invoice);
	}

	/**
	 * Unpost the M_MatchInv and C_Invoice when M_MatchInv.Qty was changed.
	 * 
	 * NOTE: this is NOT a standard use case because usualy the M_MatchInv.Qty is NEVER changed. The only case when the Qty is changed is by the M_MatchInv migration processes.
	 * 
	 * @param matchInv
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE }
			, ifColumnsChanged = { I_M_MatchInv.COLUMNNAME_Qty })
	public void unpostMatchInvIfNeeded(final I_M_MatchInv matchInv)
	{
		//
		// Un-post the M_MatchInv
		if (matchInv.isPosted())
		{
			final Properties ctx = InterfaceWrapperHelper.getCtx(matchInv);
			MPeriod.testPeriodOpen(ctx, matchInv.getDateAcct(), X_C_DocType.DOCBASETYPE_MatchInvoice, matchInv.getAD_Org_ID());

			matchInv.setPosted(false);
			Services.get(IFactAcctDAO.class).deleteForDocumentModel(matchInv);
		}

		//
		// Un-post the C_Invoice
		unpostInvoiceIfNeeded(matchInv);
	}
}
