package de.metas.dunning.invoice.api.impl;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.Properties;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.Mutable;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Invoice;
import org.compiere.util.TimeUtil;
import org.compiere.util.TrxRunnable2;
import org.slf4j.Logger;

import de.metas.bpartner.BPartnerId;
import de.metas.dunning.api.IDunningBL;
import de.metas.dunning.api.IDunningContext;
import de.metas.dunning.api.IDunningDAO;
import de.metas.dunning.api.IDunningEventDispatcher;
import de.metas.dunning.interfaces.I_C_Dunning;
import de.metas.dunning.invoice.InvoiceDueDateProviderService;
import de.metas.dunning.invoice.api.IInvoiceSourceBL;
import de.metas.dunning.model.I_C_DunningDoc_Line_Source;
import de.metas.dunning.model.I_C_Dunning_Candidate;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;

public class InvoiceSourceBL implements IInvoiceSourceBL
{
	private final static transient Logger logger = LogManager.getLogger(InvoiceSourceBL.class);

	@Override
	public boolean setDunningGraceIfManaged(@NonNull final I_C_Invoice invoice)
	{
		final I_C_Dunning dunning = getDunningForInvoiceOrNull(invoice);
		if (dunning == null)
		{
			// No dunning registered on either BPartner or org. Do nothing.
			return false;
		}

		if (!dunning.isManageDunnableDocGraceDate())
		{
			// Dunning is not set to update automatically. Skip it
			return false;
		}

		final InvoiceDueDateProviderService invoiceDueDateProviderService = SpringContextHolder.instance.getBean(InvoiceDueDateProviderService.class);

		final LocalDate dueDate = invoiceDueDateProviderService.provideDueDateFor(InvoiceId.ofRepoId(invoice.getC_Invoice_ID()));
		final LocalDate dunningGraceDate = dueDate.plusDays(dunning.getGraceDays());

		invoice.setDunningGrace(TimeUtil.asTimestamp(dunningGraceDate));
		return true;
	}

	@Override
	public I_C_Dunning getDunningForInvoiceOrNull(@NonNull final I_C_Invoice invoiceRecord)
	{
		final IDunningDAO dunningDAO = Services.get(IDunningDAO.class);

		final I_C_Dunning dunning = dunningDAO.retrieveDunningForBPartner(BPartnerId.ofRepoId(invoiceRecord.getC_BPartner_ID()));
		if (dunning != null)
		{
			return dunning;
		}

		final OrgId orgId = OrgId.ofRepoId(invoiceRecord.getAD_Org_ID());
		Check.assume(orgId.isRegular(), "Param 'invoiceRecord' needs have AD_Org_ID > 0; invoiceRecord={}", invoiceRecord); // outside of unit tests this is always the case

		return dunningDAO.retrieveDunningByOrg(orgId);
	}

	@Override
	public int writeOffDunningDocs(final Properties ctx, final String writeOffDescription)
	{
		final IDunningDAO dunningDAO = Services.get(IDunningDAO.class);
		final ITrxManager trxManager = Services.get(ITrxManager.class);

		final Mutable<Integer> countWriteOff = new Mutable<>(0);

		// NOTE: is very important to fetch candidates out of transaction in order to process each of them in a separate transaction
		final IDunningContext dunningContext = Services.get(IDunningBL.class).createDunningContext(ctx,
				null, // dunningLevel
				null, // dunningDate
				ITrx.TRXNAME_None // make sure we are fetching everything out of trx
		);

		final Iterator<I_C_DunningDoc_Line_Source> sources = dunningDAO.retrieveDunningDocLineSourcesToWriteOff(dunningContext);
		while (sources.hasNext())
		{
			final I_C_DunningDoc_Line_Source source = sources.next();

			trxManager.runInNewTrx(new TrxRunnable2()
			{
				@Override
				public void run(final String localTrxName)
				{
					// WORKAROUND: add subsequent methods called from here depends on source's trxName, so we are loading the candidate in this transaction
					InterfaceWrapperHelper.refresh(source, localTrxName);

					final I_C_Dunning_Candidate candidate = source.getC_Dunning_Candidate();
					writeOffDunningCandidate(candidate, writeOffDescription);

					source.setIsWriteOffApplied(true);
					InterfaceWrapperHelper.save(source);

					countWriteOff.setValue(countWriteOff.getValue() + 1);
				}

				@Override
				public boolean doCatch(Throwable e) throws Throwable
				{
					final String errmsg = "Error processing: " + source;
					logger.error(errmsg, e);

					// notify monitor too about this issue
					Loggables.addLog(errmsg);

					return true; // rollback
				}

				@Override
				public void doFinally()
				{
					// nothing
				}
			});
		}

		return countWriteOff.getValue();
	}

	/**
	 *
	 * @param candidate
	 * @param writeOffDescription
	 * @return true if candidate's invoice was written-off
	 */
	protected boolean writeOffDunningCandidate(final I_C_Dunning_Candidate candidate, final String writeOffDescription)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(candidate);
		final String trxName = InterfaceWrapperHelper.getTrxName(candidate);

		// services
		final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
		final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
		final IDunningEventDispatcher dunningEventDispatcher = Services.get(IDunningEventDispatcher.class);

		if (candidate.getAD_Table_ID() == adTableDAO.retrieveTableId(I_C_Invoice.Table_Name))
		{
			final I_C_Invoice invoice = InterfaceWrapperHelper.create(ctx, candidate.getRecord_ID(), I_C_Invoice.class, trxName);
			Check.assumeNotNull(invoice, "No invoice found for candidate {}", candidate);

			invoiceBL.writeOffInvoice(invoice, candidate.getOpenAmt(), writeOffDescription);

			dunningEventDispatcher.fireDunningCandidateEvent(IInvoiceSourceBL.EVENT_AfterInvoiceWriteOff, candidate);

			return true;
		}

		// other document types are ignored for now

		return false;
	}

}
