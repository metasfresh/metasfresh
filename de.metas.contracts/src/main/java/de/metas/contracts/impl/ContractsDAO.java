package de.metas.contracts.impl;

import static org.adempiere.model.InterfaceWrapperHelper.getTableId;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.IQuery;
import org.compiere.model.IQuery.Aggregate;

import de.metas.contracts.IContractsDAO;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_SubscriptionProgress;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.contracts.model.X_C_SubscriptionProgress;
import de.metas.contracts.subscription.ISubscriptionDAO;
import de.metas.contracts.subscription.ISubscriptionDAO.SubscriptionProgressQuery;
import de.metas.document.engine.IDocument;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import lombok.NonNull;

public class ContractsDAO implements IContractsDAO
{
	@Override
	public List<I_C_Flatrate_Term> retrieveSubscriptionTermsWithMissingCandidates(final int limit)
	{
		final Timestamp now = SystemTime.asTimestamp();

		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Flatrate_Term.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Flatrate_Term.COLUMN_DocStatus, IDocument.STATUS_Completed)
				.addEqualsFilter(I_C_Flatrate_Term.COLUMN_Type_Conditions, X_C_Flatrate_Term.TYPE_CONDITIONS_Subscription)

				.addInSubQueryFilter(I_C_Flatrate_Term.COLUMN_C_Flatrate_Conditions_ID, I_C_Flatrate_Conditions.COLUMN_C_Flatrate_Conditions_ID, flatrateConditionsThatRequireInvoicing())

				.addNotInSubQueryFilter(I_C_Flatrate_Term.COLUMN_C_Flatrate_Term_ID, I_C_Invoice_Candidate.COLUMN_Record_ID, invoiceCandidatesThatReferenceTerms())

				.filter(relevantTermDateAfterTimestamp(now))

				.orderBy().addColumn(I_C_Flatrate_Term.COLUMN_C_Flatrate_Term_ID).endOrderBy()
				.setLimit(limit)
				.create()
				.list();
	}

	private ICompositeQueryFilter<I_C_Flatrate_Term> relevantTermDateAfterTimestamp(final Timestamp timestamp)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final ICompositeQueryFilter<I_C_Flatrate_Term> termHasStartDateAfterTimestamp = queryBL.createCompositeQueryFilter(I_C_Flatrate_Term.class)
				.addOnlyActiveRecordsFilter()
				.addCompareFilter(I_C_Flatrate_Term.COLUMN_StartDate, Operator.LESS_OR_EQUAL, timestamp);

		final IQuery<I_C_Flatrate_Term> noticeDateAfterTimestamp = queryBL.createQueryBuilder(I_C_Flatrate_Term.class)
				.addOnlyActiveRecordsFilter()
				.addCompareFilter(I_C_Flatrate_Term.COLUMN_NoticeDate, Operator.LESS_OR_EQUAL, timestamp)
				.create();

		final ICompositeQueryFilter<I_C_Flatrate_Term> preceedingTermHasNoticeDateAfterTimestamp = queryBL.createCompositeQueryFilter(I_C_Flatrate_Term.class)
				.addOnlyActiveRecordsFilter()
				.addInSubQueryFilter(I_C_Flatrate_Term.COLUMN_C_Flatrate_Term_ID, I_C_Flatrate_Term.COLUMN_C_FlatrateTerm_Next_ID, noticeDateAfterTimestamp);

		return queryBL.createCompositeQueryFilter(I_C_Flatrate_Term.class)
				.setJoinOr()
				.addFilter(termHasStartDateAfterTimestamp)
				.addFilter(preceedingTermHasNoticeDateAfterTimestamp);
	}

	private IQuery<I_C_Flatrate_Conditions> flatrateConditionsThatRequireInvoicing()
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQuery<I_C_Flatrate_Conditions> flatrateConditionsThatRequireInvoicing = queryBL.createQueryBuilder(I_C_Flatrate_Conditions.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Flatrate_Conditions.COLUMNNAME_IsCreateNoInvoice, true)
				.create();
		return flatrateConditionsThatRequireInvoicing;
	}

	private IQuery<I_C_Invoice_Candidate> invoiceCandidatesThatReferenceTerms()
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		return queryBL.createQueryBuilder(I_C_Invoice_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMN_AD_Table_ID, getTableId(I_C_Flatrate_Term.class))
				.create();
	}

	@Override
	public boolean termHasAPredecessor(@NonNull final I_C_Flatrate_Term term)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Flatrate_Term.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Flatrate_Term.COLUMN_C_FlatrateTerm_Next_ID, term.getC_Flatrate_Term_ID())
				.create()
				.match();
	}

	@Override
	public BigDecimal retrieveSubscriptionProgressQtyForTerm(@NonNull final I_C_Flatrate_Term term)
	{
		final BigDecimal qty = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_SubscriptionProgress.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_SubscriptionProgress.COLUMN_C_Flatrate_Term_ID, term.getC_Flatrate_Term_ID())
				.addEqualsFilter(I_C_SubscriptionProgress.COLUMN_EventType, X_C_SubscriptionProgress.EVENTTYPE_Delivery)
				.create()
				.aggregate(I_C_SubscriptionProgress.COLUMN_Qty, Aggregate.SUM, BigDecimal.class);

		return qty;
	}

	@Override
	public List<I_C_SubscriptionProgress> getSubscriptionProgress(@NonNull final I_C_Flatrate_Term currentTerm)
	{
		final ISubscriptionDAO subscriptionDAO = Services.get(ISubscriptionDAO.class);
		final SubscriptionProgressQuery currentTermQuery = SubscriptionProgressQuery.term(currentTerm).build();
		return subscriptionDAO.retrieveSubscriptionProgresses(currentTermQuery);
	}
}
