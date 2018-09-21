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


import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryUpdater;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.compiere.model.I_AD_Note;
import org.compiere.model.ModelValidator;

import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.util.Services;

@Validator(I_AD_Note.class)
public class AD_Note
{
	/**
	 * Reset error flag for all invoice candidates which are linked to this <code>note</code>.
	 * 
	 * @param note
	 */
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void resetInvoiceCandidatesWithErrors(final I_AD_Note note)
	{
		final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);

		Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Invoice_Candidate.class, note)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_AD_Note_ID, note.getAD_Note_ID())
				.create()
				.update(new IQueryUpdater<I_C_Invoice_Candidate>()
				{

					@Override
					public boolean update(final I_C_Invoice_Candidate ic)
					{
						invoiceCandBL.resetError(ic);
						return true;
					}
				});
	}
}
