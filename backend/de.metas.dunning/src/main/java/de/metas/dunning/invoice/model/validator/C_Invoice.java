package de.metas.dunning.invoice.model.validator;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.dunning.api.IDunningBL;
import de.metas.dunning.api.IDunningContext;
import de.metas.dunning.api.IDunningDAO;
import de.metas.dunning.invoice.api.IInvoiceSourceBL;
import de.metas.dunning.model.I_C_Dunning_Candidate;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;
import org.slf4j.Logger;

import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import static org.adempiere.model.InterfaceWrapperHelper.getTableId;

@Validator(I_C_Invoice.class)
public class C_Invoice
{
	private static final Logger logger = LogManager.getLogger(C_Invoice.class);

	/**
	 * Runs at AFTER_COMPLETE (not AFTER_PREPARE) on purpose: the DueDate this computation
	 * depends on is filled by de.metas.business's C_Invoice interceptor at AFTER_PREPARE,
	 * and the execution order of two AFTER_PREPARE interceptors from different modules is
	 * not deterministic (Spring registration order; ModelValidationEngine has no priority
	 * mechanism). If grace ran first, DueDate was still null, the computation was silently
	 * skipped, and the eligibility check in DefaultDunningCandidateProducer.isEligible()
	 * was bypassed - invoices could be dunned during their grace period. Nothing reads
	 * DunningGrace during the completion window, so AFTER_COMPLETE is safe and guarantees
	 * DueDate is present. The null-DueDate guard in InvoiceSourceBL remains for legacy
	 * invoices whose DueDate was never back-filled.
	 */
	@DocValidate(timings = { ModelValidator.TIMING_AFTER_COMPLETE })
	public void setDunningGraceIfAutomatic(final I_C_Invoice invoice)
	{
		Services.get(IInvoiceSourceBL.class).setDunningGraceIfManaged(invoice);
		InterfaceWrapperHelper.save(invoice);
	}

	/**
	 * This method is triggered when DunningGrace field is changed.
	 *
	 * @implNote please keep this method with only ifColumnsChanged=DunningGrace because we want to avoid update cycles between invoice and dunning candidate
	 */
	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE
			, ifColumnsChanged = I_C_Invoice.COLUMNNAME_DunningGrace)
	public void validateCandidatesOnDunningGraceChange(final I_C_Invoice invoice)
	{
		final Timestamp dunningGraceDate = invoice.getDunningGrace();
		logger.debug("DunningGraceDate: {}", dunningGraceDate);

		final IDunningDAO dunningDAO = Services.get(IDunningDAO.class);
		final IDunningBL dunningBL = Services.get(IDunningBL.class);

		final Properties ctx = InterfaceWrapperHelper.getCtx(invoice);
		final String trxName = InterfaceWrapperHelper.getTrxName(invoice);
		final IDunningContext context = dunningBL.createDunningContext(ctx,
				null, // dunningLevel
				null, // dunningDate
				trxName);

		final I_C_Dunning_Candidate callerCandidate = InterfaceWrapperHelper.getDynAttribute(invoice, C_Dunning_Candidate.POATTR_CallerPO);

		//
		// Gets dunning candidates for specific invoice, to check if they are still viable.
		final List<I_C_Dunning_Candidate> candidates = dunningDAO.retrieveDunningCandidates(
				context,
				getTableId(I_C_Invoice.class),
				invoice.getC_Invoice_ID());

		for (final I_C_Dunning_Candidate candidate : candidates)
		{
			if (callerCandidate != null && callerCandidate.getC_Dunning_Candidate_ID() == candidate.getC_Dunning_Candidate_ID())
			{
				// skip the caller candidate to avoid cycles
				continue;
			}

			if (candidate.isProcessed())
			{
				logger.debug("Skip processed candidate: {}", candidate);
				continue;
			}

			if (dunningBL.isExpired(candidate, dunningGraceDate))
			{
				logger.debug("Deleting expired candidate: {}", candidate);
				InterfaceWrapperHelper.delete(candidate);
			}
			else
			{
				logger.debug("Updating DunningGrace for candidate: {}", candidate);
				candidate.setDunningGrace(invoice.getDunningGrace());
				InterfaceWrapperHelper.save(candidate);
			}
		}
	}
}
