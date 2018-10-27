package de.metas.dunning.invoice.model.validator;

import static org.adempiere.model.InterfaceWrapperHelper.getTableId;

/*
 * #%L
 * de.metas.dunning
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


import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;
import org.slf4j.Logger;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.dunning.api.IDunningBL;
import de.metas.dunning.api.IDunningContext;
import de.metas.dunning.api.IDunningDAO;
import de.metas.dunning.invoice.api.IInvoiceSourceBL;
import de.metas.dunning.model.I_C_Dunning_Candidate;
import de.metas.logging.LogManager;
import de.metas.util.Services;

@Validator(I_C_Invoice.class)
public class C_Invoice
{
	private static final Logger logger = LogManager.getLogger(C_Invoice.class);

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_PREPARE })
	public void setDunningGraceIfAutomatic(final I_C_Invoice invoice)
	{
		Services.get(IInvoiceSourceBL.class).setDunningGraceIfManaged(invoice);
		InterfaceWrapperHelper.save(invoice);
	}

	/**
	 * This method is triggered when DunningGrace field is changed.
	 *
	 * NOTE: to developer: please keep this method with only ifColumnsChanged=DunningGrace because we want to avoid update cycles between invoice and dunning candidate
	 *
	 * @param invoice
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
