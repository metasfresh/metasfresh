package de.metas.acct.interceptor;

import com.google.common.collect.ImmutableList;
import de.metas.acct.api.IFactAcctDAO;
import de.metas.acct.api.IPostingService;
import de.metas.costing.CostingDocumentRef;
import de.metas.costing.ICostingService;
import de.metas.document.DocBaseType;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.matchinv.MatchInv;
import de.metas.invoice.matchinv.listeners.MatchInvListener;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.acct.Doc_Invoice;
import org.compiere.model.I_M_MatchInv;
import org.compiere.model.MPeriod;
import org.compiere.util.Env;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;

@Component
public class AcctMatchInvListener implements MatchInvListener
{
	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	private final ICostingService costDetailService;
	private final IPostingService postingService;
	private final IFactAcctDAO factAcctDAO;

	public AcctMatchInvListener(
			@NonNull final ICostingService costDetailService)
	{
		this.costDetailService = costDetailService;
		this.postingService = Services.get(IPostingService.class);
		this.factAcctDAO = Services.get(IFactAcctDAO.class);
	}

	@Override
	public void onAfterCreated(final MatchInv matchInv)
	{
		unpostInvoiceIfNeeded(ImmutableList.of(matchInv));
		postIt(matchInv);
	}

	@Override
	public void onAfterDeleted(final List<MatchInv> matchInvs)
	{
		deleteFactAcct(matchInvs);
		unpostInvoiceIfNeeded(matchInvs);
	}

	private void postIt(final MatchInv matchInv)
	{
		postingService.newPostingRequest()
				.setClientId(matchInv.getClientId())
				.setDocumentRef(toTableRecordReference(matchInv))
				.setFailOnError(false)
				.onErrorNotifyUser(matchInv.getUpdatedByUserId())
				.postIt();

	}

	@NonNull
	private static TableRecordReference toTableRecordReference(final MatchInv matchInv)
	{
		return TableRecordReference.of(I_M_MatchInv.Table_Name, matchInv.getId());
	}

	private void deleteFactAcct(final List<MatchInv> matchInvs)
	{
		for (final MatchInv matchInv : matchInvs)
		{
			if (matchInv.isPosted())
			{
				MPeriod.testPeriodOpen(Env.getCtx(), Timestamp.from(matchInv.getDateAcct()), DocBaseType.MatchInvoice, matchInv.getOrgId().getRepoId());
				factAcctDAO.deleteForRecordRef(toTableRecordReference(matchInv));
			}
		}
	}

	private void unpostInvoiceIfNeeded(final List<MatchInv> matchInvs)
	{
		final HashSet<InvoiceId> invoiceIds = new HashSet<>();

		for (final MatchInv matchInv : matchInvs)
		{
			// Reposting is required if a M_MatchInv was created for a purchase invoice.
			// ... because we book the matched quantity on InventoryClearing and on Expense the not matched quantity
			if (matchInv.getSoTrx().isPurchase())
			{
				costDetailService.voidAndDeleteForDocument(CostingDocumentRef.ofMatchInvoiceId(matchInv.getId()));
				invoiceIds.add(matchInv.getInvoiceId());
			}
		}

		invoiceBL.getByIds(invoiceIds)
				.forEach(Doc_Invoice::unpostIfNeeded);
	}
}
