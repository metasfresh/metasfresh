package de.metas.contracts.dunning;

import static org.adempiere.model.InterfaceWrapperHelper.getTableId;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Component;

import de.metas.cache.CCache;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.dunning.invoice.InvoiceDueDateProvider;
import de.metas.invoice.InvoiceId;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_C_Invoice_Line_Alloc;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Component
public class ContractBasedInvoiceDueDateProvider implements InvoiceDueDateProvider
{
	public final CCache<InvoiceId, List<I_C_Flatrate_Term>> invoiceId2flatrateTerms = CCache.<InvoiceId, List<I_C_Flatrate_Term>> builder()
			.tableName(I_C_Flatrate_Term.Table_Name)
			.additionalTableNameToResetFor(I_C_Invoice_Candidate.Table_Name)
			.additionalTableNameToResetFor(I_C_Invoice_Line_Alloc.Table_Name)
			.additionalTableNameToResetFor(I_C_InvoiceLine.Table_Name)
			.build();

	@Override
	public LocalDate provideDueDateOrNull(@NonNull final InvoiceId invoiceId)
	{
		final List<I_C_Flatrate_Term> flatrateTermsForInvoiceId = retrieveFlatrateTermsForInvoiceId(invoiceId);
		if (!applies(flatrateTermsForInvoiceId))
		{
			return null;
		}

		final Optional<LocalDate> smallestStartDate = flatrateTermsForInvoiceId
				.stream()
				.min(Comparator.comparing(I_C_Flatrate_Term::getStartDate))
				.map(I_C_Flatrate_Term::getStartDate)
				.map(TimeUtil::asLocalDate);

		return smallestStartDate.orElse(null);
	}

	private boolean applies(final List<I_C_Flatrate_Term> flatrateTermsForInvoiceId)
	{
		if (flatrateTermsForInvoiceId.isEmpty())
		{
			return false;
		}
		if (containsNewTerm(flatrateTermsForInvoiceId))
		{
			return false;
		}
		return true;
	}

	private boolean containsNewTerm(@NonNull final List<I_C_Flatrate_Term> flatrateTermsForInvoiceId)
	{
		for (final I_C_Flatrate_Term term : flatrateTermsForInvoiceId)
		{
			if (isNewTerm(term))
			{
				return true;
			}
		}
		return false;
	}

	private boolean isNewTerm(@NonNull final I_C_Flatrate_Term term)
	{
		return Objects.equals(term.getMasterStartDate(), term.getStartDate());
	}

	private List<I_C_Flatrate_Term> retrieveFlatrateTermsForInvoiceId(@NonNull final InvoiceId invoiceId)
	{
		return invoiceId2flatrateTerms.getOrLoad(invoiceId, () -> retrieveFlatrateTermsForInvoiceId0(invoiceId));
	}

	private List<I_C_Flatrate_Term> retrieveFlatrateTermsForInvoiceId0(@NonNull final InvoiceId invoiceId)
	{
		final List<I_C_Flatrate_Term> flatrateTerms = Services
				.get(IQueryBL.class)
				.createQueryBuilder(I_C_InvoiceLine.class)
				.addEqualsFilter(I_C_InvoiceLine.COLUMN_C_Invoice_ID, invoiceId)
				.andCollectChildren(I_C_Invoice_Line_Alloc.COLUMN_C_InvoiceLine_ID)
				.andCollect(I_C_Invoice_Line_Alloc.COLUMN_C_Invoice_Candidate_ID)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_AD_Table_ID, getTableId(I_C_Flatrate_Term.class))
				.andCollect(I_C_Invoice_Candidate.COLUMNNAME_Record_ID, I_C_Flatrate_Term.class)
				.create()
				.list();
		return flatrateTerms;
	}

}
