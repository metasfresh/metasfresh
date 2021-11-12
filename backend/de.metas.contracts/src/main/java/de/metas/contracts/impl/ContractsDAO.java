package de.metas.contracts.impl;

import de.metas.bpartner.BPartnerId;
import de.metas.common.util.time.SystemTime;
import de.metas.contracts.ConditionsId;
import de.metas.contracts.IContractsDAO;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_SubscriptionProgress;
import de.metas.contracts.model.X_C_SubscriptionProgress;
import de.metas.contracts.order.model.I_C_OrderLine;
import de.metas.contracts.subscription.ISubscriptionDAO;
import de.metas.contracts.subscription.ISubscriptionDAO.SubscriptionProgressQuery;
import de.metas.document.engine.IDocument;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.order.OrderId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.IQuery;
import org.compiere.model.IQuery.Aggregate;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.getTableId;

public class ContractsDAO implements IContractsDAO
{
	@Override
	public List<I_C_Flatrate_Term> retrieveSubscriptionTermsWithMissingCandidates(
			@NonNull String typeConditions,
			final int limit)
	{
		return createTermWithMissingCandidateQueryBuilder(typeConditions, false /* ignoreDateFilters */ )
				.orderBy().addColumn(I_C_Flatrate_Term.COLUMN_C_Flatrate_Term_ID).endOrderBy()
				.setLimit(limit)
				.create()
				.list(I_C_Flatrate_Term.class);
	}

	@Override
	public IQueryBuilder<I_C_Flatrate_Term> createTermWithMissingCandidateQueryBuilder(@NonNull String typeConditions, final boolean ignoreDateFilter)
	{
		final Timestamp now = SystemTime.asTimestamp();

		final IQueryBuilder<I_C_Flatrate_Term> termWithMissingCandidateQueryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Flatrate_Term.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Flatrate_Term.COLUMN_DocStatus, IDocument.STATUS_Completed)
				.addEqualsFilter(I_C_Flatrate_Term.COLUMN_Type_Conditions, typeConditions)

				.addInSubQueryFilter(I_C_Flatrate_Term.COLUMN_C_Flatrate_Conditions_ID, I_C_Flatrate_Conditions.COLUMN_C_Flatrate_Conditions_ID, flatrateConditionsThatRequireInvoicing())

				.addNotInSubQueryFilter(I_C_Flatrate_Term.COLUMN_C_Flatrate_Term_ID, I_C_Invoice_Candidate.COLUMN_Record_ID, createQueryForICsThatReferenceTerms());

		if (!ignoreDateFilter)
		{
			termWithMissingCandidateQueryBuilder.filter(relevantTermDateBeforeTimestamp(now));
		}

		return termWithMissingCandidateQueryBuilder;
	}

	private ICompositeQueryFilter<I_C_Flatrate_Term> relevantTermDateBeforeTimestamp(final Timestamp timestamp)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final ICompositeQueryFilter<I_C_Flatrate_Term> termHasStartDateBeforeTimestamp = queryBL.createCompositeQueryFilter(I_C_Flatrate_Term.class)
				.addOnlyActiveRecordsFilter()
				.addCompareFilter(I_C_Flatrate_Term.COLUMN_StartDate, Operator.LESS_OR_EQUAL, timestamp);

		final IQuery<I_C_Flatrate_Term> noticeDateBeforeTimestamp = queryBL.createQueryBuilder(I_C_Flatrate_Term.class)
				.addOnlyActiveRecordsFilter()
				.addCompareFilter(I_C_Flatrate_Term.COLUMN_NoticeDate, Operator.LESS_OR_EQUAL, timestamp)
				.create();

		final ICompositeQueryFilter<I_C_Flatrate_Term> preceedingTermHasNoticeDateBeforeTimestamp = queryBL.createCompositeQueryFilter(I_C_Flatrate_Term.class)
				.addOnlyActiveRecordsFilter()
				.addInSubQueryFilter(I_C_Flatrate_Term.COLUMN_C_Flatrate_Term_ID, I_C_Flatrate_Term.COLUMN_C_FlatrateTerm_Next_ID, noticeDateBeforeTimestamp);

		return queryBL.createCompositeQueryFilter(I_C_Flatrate_Term.class)
				.setJoinOr()
				.addFilter(termHasStartDateBeforeTimestamp)
				.addFilter(preceedingTermHasNoticeDateBeforeTimestamp);
	}

	private IQuery<I_C_Flatrate_Conditions> flatrateConditionsThatRequireInvoicing()
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQuery<I_C_Flatrate_Conditions> flatrateConditionsThatRequireInvoicing = queryBL.createQueryBuilder(I_C_Flatrate_Conditions.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Flatrate_Conditions.COLUMNNAME_IsCreateNoInvoice, false) // the column name is Create*No*Invoice
				.create();
		return flatrateConditionsThatRequireInvoicing;
	}

	private IQuery<I_C_Invoice_Candidate> createQueryForICsThatReferenceTerms()
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		return queryBL.createQueryBuilder(I_C_Invoice_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_AD_Table_ID, getTableId(I_C_Flatrate_Term.class))
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
				.anyMatch();
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

	@Cached(cacheName = I_C_Flatrate_Term.Table_Name + "#by#OrderId")
	@Override
	public List<I_C_Flatrate_Term> retrieveFlatrateTermsForOrderIdLatestFirst(@NonNull final OrderId orderId)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_C_OrderLine.class)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addEqualsFilter(I_C_OrderLine.COLUMNNAME_C_Order_ID, orderId)
				.andCollectChildren(I_C_Flatrate_Term.COLUMN_C_OrderLine_Term_ID, I_C_Flatrate_Term.class)
				.orderByDescending(I_C_Flatrate_Term.COLUMN_EndDate)
				.create()
				.list();
	}

	@Cached(cacheName = I_C_Flatrate_Term.Table_Name + "#by#BPartnerId")
	@Override
	public I_C_Flatrate_Term retrieveLatestFlatrateTermForBPartnerId(@NonNull final BPartnerId bpartnerId)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_C_Flatrate_Term.class)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_Bill_BPartner_ID, bpartnerId.getRepoId())
				.orderBy()
					.addColumn(I_C_Flatrate_Term.COLUMNNAME_MasterEndDate, Direction.Descending, Nulls.Last)
					.addColumn(I_C_Flatrate_Term.COLUMNNAME_EndDate, Direction.Descending, Nulls.Last)
				.endOrderBy()
				.create()
				.first();
	}

	@Cached(cacheName = I_C_Flatrate_Term.Table_Name + "#by#BPartnerId")
	@Override
	public I_C_Flatrate_Term retrieveFirstFlatrateTermForBPartnerId(@NonNull final BPartnerId bpartnerId)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_C_Flatrate_Term.class)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_Bill_BPartner_ID, bpartnerId)
				.orderBy()
				.addColumn(I_C_Flatrate_Term.COLUMNNAME_MasterStartDate, Direction.Ascending, Nulls.Last)
				.addColumn(I_C_Flatrate_Term.COLUMNNAME_StartDate, Direction.Ascending, Nulls.Last)
				.endOrderBy()
				.create()
				.first();
	}


	@Override
	@NonNull
	public <T extends I_C_Flatrate_Conditions> T getConditionsById(@NonNull final ConditionsId conditionsId, @NonNull final  Class<T> modelClass)
	{
		return InterfaceWrapperHelper.load(conditionsId, modelClass);
	}
}
