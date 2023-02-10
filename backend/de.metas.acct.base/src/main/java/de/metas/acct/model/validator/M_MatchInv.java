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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.acct.api.IFactAcctDAO;
import de.metas.acct.api.IPostingService;
import de.metas.costing.CostingDocumentRef;
import de.metas.costing.ICostingService;
import de.metas.document.DocBaseType;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.acct.DocLine_Invoice;
import org.compiere.acct.Doc_Invoice;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_M_MatchInv;
import org.compiere.model.MPeriod;
import org.compiere.model.ModelValidator;

import java.util.Properties;

@Interceptor(I_M_MatchInv.class)
public class M_MatchInv
{
	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	private final ICostingService costDetailService;
	private final IPostingService postingService;
	private final IFactAcctDAO factAcctDAO;

	public M_MatchInv(
			@NonNull final ICostingService costDetailService,
			@NonNull final IPostingService postingService,
			@NonNull final IFactAcctDAO factAcctDAO)
	{
		this.costDetailService = costDetailService;
		this.postingService = postingService;
		this.factAcctDAO = factAcctDAO;
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void afterNewOrChange(final I_M_MatchInv matchInv, final ModelChangeType changeType)
	{
		if (changeType.isNew() || isJustProcessed(matchInv))
		{
			unpostInvoiceIfNeeded(matchInv);
			postIt(matchInv);
		}
	}

	private static boolean isJustProcessed(final I_M_MatchInv matchInv)
	{
		return InterfaceWrapperHelper.isValueChanged(matchInv, I_M_MatchInv.COLUMNNAME_Processed)
				&& matchInv.isProcessed();
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void beforeDelete(final I_M_MatchInv matchInv)
	{
		unpostMatchInvIfNeeded(matchInv);
	}

	private void postIt(final I_M_MatchInv matchInv)
	{
		postingService.newPostingRequest()
				.setClientId(ClientId.ofRepoId(matchInv.getAD_Client_ID()))
				.setDocumentRef(TableRecordReference.of(matchInv))
				.setFailOnError(false)
				.onErrorNotifyUser(UserId.ofRepoId(matchInv.getUpdatedBy()))
				.postIt();

	}

	private void unpostMatchInvIfNeeded(final I_M_MatchInv matchInv)
	{
		//
		// Un-post the M_MatchInv
		if (matchInv.isPosted())
		{
			final Properties ctx = InterfaceWrapperHelper.getCtx(matchInv);
			MPeriod.testPeriodOpen(ctx, matchInv.getDateAcct(), DocBaseType.MatchInvoice, matchInv.getAD_Org_ID());

			matchInv.setPosted(false);
			factAcctDAO.deleteForDocumentModel(matchInv);
		}

		//
		// Un-post the C_Invoice
		unpostInvoiceIfNeeded(matchInv);
	}

	private void unpostInvoiceIfNeeded(final I_M_MatchInv matchInv)
	{
		// Do nothing if there is no need to report the invoice for this M_MatchInv
		if (!DocLine_Invoice.isInvoiceRepostingRequired(matchInv))
		{
			return;
		}

		costDetailService.voidAndDeleteForDocument(CostingDocumentRef.ofMatchInvoiceId(matchInv.getM_MatchInv_ID()));

		final I_C_Invoice invoice = invoiceBL.getById(InvoiceId.ofRepoId(matchInv.getC_Invoice_ID()));
		if (invoice.isPosted())
		{
			Doc_Invoice.unpost(invoice);
		}
	}
}
