package de.metas.rest_api.invoicecandidates.impl;

import java.util.List;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.compiere.model.IQuery;

import com.google.common.collect.ImmutableList;

import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.rest_api.invoicecandidates.request.JsonInvoiceCandidate;
import de.metas.util.Services;
import de.metas.util.rest.ExternalId;

public class InvoiceCandidatesQueryBuilderService {

	public static IQuery<I_C_Invoice_Candidate> createICQueryBuilder(List<JsonInvoiceCandidate> jsonInvoices) {
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryBuilder<I_C_Invoice_Candidate> queryBuilder = queryBL
				.createQueryBuilder(I_C_Invoice_Candidate.class)
				.setOption(IQueryBuilder.OPTION_Explode_OR_Joins_To_SQL_Unions, true).setJoinOr();
		ImmutableList<String> ids = jsonInvoices.stream()
				.flatMap(jsonInvoice -> jsonInvoice.getExternalLineIds().stream()).map(ExternalId::getValue)
				.collect(ImmutableList.toImmutableList());
		for (final JsonInvoiceCandidate cand : jsonInvoices) {
			final ICompositeQueryFilter<I_C_Invoice_Candidate> invoiceCandidatesFilter = queryBL
					.createCompositeQueryFilter(I_C_Invoice_Candidate.class).addOnlyActiveRecordsFilter()
					.addInArrayOrAllFilter(I_C_Invoice_Candidate.COLUMN_ExternalLineId, ids)
					.addEqualsFilter(I_C_Invoice_Candidate.COLUMN_ExternalHeaderId, cand.getExternalHeaderId());

			queryBuilder.filter(invoiceCandidatesFilter);
		}
		return queryBuilder.create();
	}

}
