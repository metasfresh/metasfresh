package de.metas.invoicecandidate.api;

import static de.metas.util.Check.isEmpty;

import java.sql.Timestamp;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;

import de.metas.bpartner.BPartnerId;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.util.lang.ExternalHeaderIdWithExternalLineIds;
import lombok.Builder;
import lombok.Value;

/** all properties of this filter are {@code AND}ed. */
@Value
public class InvoiceCandidateQuery
{
	// It's mandatory to have at least one of the following query properties set.
	// Because we assume that any of these properties alone can ensure a to select only a limited number of invoice candidates.
	InvoiceCandidateId invoiceCandidateId;
	ExternalHeaderIdWithExternalLineIds externalIds;
	BPartnerId billBPartnerId;
	Timestamp dateToInvoice;
	String headerAggregationKey;

	// Any of the following properties may or may not be part of a valid query.
	InvoiceCandidateId excludeC_Invoice_Candidate_ID;
	InvoiceCandidateId maxManualC_Invoice_Candidate_ID;
	Boolean processed;
	Boolean error;

	@Builder
	private InvoiceCandidateQuery(
			@Nullable final InvoiceCandidateId invoiceCandidateId,
			@Nullable final ExternalHeaderIdWithExternalLineIds externalIds,
			@Nullable final BPartnerId billBPartnerId,
			@Nullable final Timestamp dateToInvoice,
			@Nullable final String headerAggregationKey,
			@Nullable final InvoiceCandidateId excludeC_Invoice_Candidate_ID,
			@Nullable final InvoiceCandidateId maxManualC_Invoice_Candidate_ID,
			@Nullable final Boolean processed,
			@Nullable final Boolean error)
	{
		this.invoiceCandidateId = invoiceCandidateId;
		this.externalIds = externalIds;
		this.billBPartnerId = billBPartnerId;
		this.dateToInvoice = dateToInvoice;
		this.headerAggregationKey = headerAggregationKey;
		this.excludeC_Invoice_Candidate_ID = excludeC_Invoice_Candidate_ID;
		this.maxManualC_Invoice_Candidate_ID = maxManualC_Invoice_Candidate_ID;
		this.processed = processed;
		this.error = error;

		if (invoiceCandidateId == null
				&& externalIds == null
				&& billBPartnerId == null
				&& dateToInvoice == null
				&& isEmpty(headerAggregationKey, true))
		{
			throw new AdempiereException("Invalid invoiceCandidateQuery. "
					+ "To restrict the number of results, at least one of the following properties has to be specified: "
					+ "invoiceCandidateId or externalIds or billBPartnerId or dateToInvoice or headerAggregationKey")
							.appendParametersToMessage()
							.setParameter("invoiceCandidateQuery", this);
		}
	}

	public InvoiceCandidateQuery copy()
	{
		final InvoiceCandidateQuery newQuery = InvoiceCandidateQuery.builder()
				.billBPartnerId(billBPartnerId)
				.dateToInvoice(new Timestamp(dateToInvoice.getTime()))
				.headerAggregationKey(headerAggregationKey)
				.excludeC_Invoice_Candidate_ID(excludeC_Invoice_Candidate_ID)
				.maxManualC_Invoice_Candidate_ID(maxManualC_Invoice_Candidate_ID)
				.processed(processed)
				.error(error)
				.build();
		return newQuery;
	}

}
