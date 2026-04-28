package de.metas.contracts.interceptor;

/*
 * #%L
 * de.metas.contracts
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

import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.model.I_C_Flatrate_DataEntry;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_Invoice_Clearing_Alloc;
import de.metas.contracts.model.X_C_Flatrate_DataEntry;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;

import java.util.List;

@Interceptor(I_C_Invoice_Clearing_Alloc.class)
public class C_Invoice_Clearing_Alloc
{
	/**
	 * If there is a <code>C_Flatrate_DataEntry </code> record for the given <code>ica</code>'s candidate and term, then retrieve and reference it.
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW })
	public void linkToFlatrateDataEntryIfExists(final I_C_Invoice_Clearing_Alloc ica)
	{
		final I_C_Flatrate_DataEntry dataEntry = retrieveDataEntry(ica.getC_Invoice_Cand_ToClear(), ica.getC_Flatrate_Term());
		if (dataEntry != null)
		{
			ica.setC_Flatrate_DataEntry(dataEntry);
		}
	}

	private I_C_Flatrate_DataEntry retrieveDataEntry(final I_C_Invoice_Candidate invoiceCand, final I_C_Flatrate_Term term)
	{
		final List<I_C_Flatrate_DataEntry> entries =
				Services.get(IFlatrateDAO.class).retrieveEntries(null, // I_C_Flatrate_Conditions
						term,
						invoiceCand.getDateOrdered(),
						X_C_Flatrate_DataEntry.TYPE_Invoicing_PeriodBased,
						UomId.ofRepoIdOrNull(term.getC_UOM_ID()),
						true); // onlyNonSim

		final I_C_Flatrate_DataEntry dataEntry;
		if (entries.isEmpty())
		{
			dataEntry = null;
		}
		else
		{
			dataEntry = entries.get(0);
			Check.assume(entries.size() == 1, "There is only one non-sim entry");
		}
		return dataEntry;
	}
}
