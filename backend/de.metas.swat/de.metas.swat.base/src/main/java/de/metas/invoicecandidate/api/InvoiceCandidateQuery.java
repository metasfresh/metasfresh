package de.metas.invoicecandidate.api;

import de.metas.bpartner.BPartnerId;
import de.metas.document.DocTypeId;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.lang.SOTrx;
import de.metas.organization.OrgId;
import de.metas.util.lang.ExternalHeaderIdWithExternalLineIds;
import de.metas.util.time.InstantInterval;
import lombok.Builder;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;

import static de.metas.util.Check.isEmpty;

/**
 * All properties of this filter are {@code AND}ed.
 */
@Value
public class InvoiceCandidateQuery
{
	OrgId orgId;

	// It's mandatory to have at least one of the following query properties set.
	// Because we assume that any of these properties alone can ensure a to select only a limited number of invoice candidates.
	InvoiceCandidateId invoiceCandidateId;
	ExternalHeaderIdWithExternalLineIds externalIds;
	BPartnerId billBPartnerId;
	LocalDate dateToInvoice;
	String headerAggregationKey;
	BPartnerId salesRepBPartnerId;
	InstantInterval dateOrderedInterval;
	DocTypeId orderDocTypeId;
	String orderDocumentNo;
	List<String> orderLines;

	SOTrx soTrx;

	// Any of the following properties may or may not be part of a valid query.
	InvoiceCandidateId excludeC_Invoice_Candidate_ID;
	InvoiceCandidateId maxManualC_Invoice_Candidate_ID;
	Boolean processed;
	Boolean error;

	@Builder
	private InvoiceCandidateQuery(
			@Nullable final OrgId orgId,
			@Nullable final InvoiceCandidateId invoiceCandidateId,
			@Nullable final ExternalHeaderIdWithExternalLineIds externalIds,
			@Nullable final BPartnerId billBPartnerId,
			@Nullable final LocalDate dateToInvoice,
			@Nullable final BPartnerId salesRepBPartnerId,
			@Nullable final InstantInterval dateOrderedInterval,
			@Nullable final DocTypeId orderDocTypeId,
			@Nullable final String orderDocumentNo,
			@Nullable final List<String> orderLines,
			@Nullable final String headerAggregationKey,
			@Nullable final InvoiceCandidateId excludeC_Invoice_Candidate_ID,
			@Nullable final InvoiceCandidateId maxManualC_Invoice_Candidate_ID,
			@Nullable final SOTrx soTrx,
			@Nullable final Boolean processed,
			@Nullable final Boolean error)
	{
		this.orgId = orgId;
		this.invoiceCandidateId = invoiceCandidateId;
		this.externalIds = externalIds;
		this.billBPartnerId = billBPartnerId;
		this.dateToInvoice = dateToInvoice;
		this.salesRepBPartnerId = salesRepBPartnerId;
		this.dateOrderedInterval = dateOrderedInterval;
		this.orderDocTypeId = orderDocTypeId;
		this.orderDocumentNo = orderDocumentNo;
		this.orderLines = orderLines;
		this.headerAggregationKey = headerAggregationKey;
		this.excludeC_Invoice_Candidate_ID = excludeC_Invoice_Candidate_ID;
		this.maxManualC_Invoice_Candidate_ID = maxManualC_Invoice_Candidate_ID;
		this.soTrx = soTrx;
		this.processed = processed;
		this.error = error;

		if (dateToInvoice != null && orgId == null)
		{
			throw new AdempiereException("Invalid invoiceCandidateQuery. "
					+ "If dateToInvoice is specified, then also orgId nedds to be specified (to get the date's time zone)")
							.appendParametersToMessage()
							.setParameter("invoiceCandidateQuery", this);
		}

		if (invoiceCandidateId == null
				&& externalIds == null
				&& billBPartnerId == null
				&& dateToInvoice == null
				&& salesRepBPartnerId == null
				&& dateOrderedInterval == null
				&& isEmpty(headerAggregationKey, true)
				&& orderDocumentNo == null
				&& orderDocTypeId == null)
		{
			throw new AdempiereException("Invalid invoiceCandidateQuery. "
					+ "To restrict the number of results, at least one of the following properties has to be specified: "
					+ "invoiceCandidateId or externalIds or billBPartnerId or dateToInvoice or headerAggregationKey or " 
					+ "salesRepBPartnerId or dateOrderedInterval or orderDocumentNo or orderDocTypeId")
							.appendParametersToMessage()
							.setParameter("invoiceCandidateQuery", this);
		}
	}

	public InvoiceCandidateQuery copy()
	{
		return InvoiceCandidateQuery.builder()
				.orgId(orgId)
				.billBPartnerId(billBPartnerId)
				.dateToInvoice(dateToInvoice)
				.salesRepBPartnerId(salesRepBPartnerId)
				.dateOrderedInterval(dateOrderedInterval)
				.headerAggregationKey(headerAggregationKey)
				.excludeC_Invoice_Candidate_ID(excludeC_Invoice_Candidate_ID)
				.maxManualC_Invoice_Candidate_ID(maxManualC_Invoice_Candidate_ID)
				.soTrx(soTrx)
				.processed(processed)
				.error(error)
				.build();
	}

	public OrgId getOrgIdNotNull()
	{
		final OrgId orgId = getOrgId();
		if (orgId == null)
		{
			throw new AdempiereException("Invalid invoiceCandidateQuery. "
					+ "the calling method requires orgId to be not-null")
							.appendParametersToMessage()
							.setParameter("invoiceCandidateQuery", this);
		}
		return orgId;
	}
}
