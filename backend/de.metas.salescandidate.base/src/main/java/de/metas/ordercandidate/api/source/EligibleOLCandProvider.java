/*
 * #%L
 * de.metas.salescandidate.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.ordercandidate.api.source;

import com.google.common.collect.ImmutableList;
import de.metas.async.AsyncBatchId;
import de.metas.common.util.time.SystemTime;
import de.metas.impex.InputDataSourceId;
import de.metas.impex.api.IInputDataSourceDAO;
import de.metas.logging.LogManager;
import de.metas.ordercandidate.OrderCandidate_Constants;
import de.metas.ordercandidate.api.IOLCandBL;
import de.metas.ordercandidate.api.OLCand;
import de.metas.ordercandidate.api.OLCandAggregation;
import de.metas.ordercandidate.api.OLCandAggregationColumn;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class EligibleOLCandProvider
{
	private static final Logger logger = LogManager.getLogger(EligibleOLCandProvider.class);

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IInputDataSourceDAO inputDataSourceDAO = Services.get(IInputDataSourceDAO.class);

	private final LocalDate defaultDateDoc = SystemTime.asLocalDate();

	@NonNull
	public List<OLCand> getEligibleOLCand(@NonNull final GetEligibleOLCandRequest request)
	{
		final InputDataSourceId processorDataDestinationId = inputDataSourceDAO
				.retrieveInputDataSourceIdByInternalName(OrderCandidate_Constants.DATA_DESTINATION_INTERNAL_NAME);

		final IOLCandBL olCandBL = Services.get(IOLCandBL.class);

		return queryBL.createQueryBuilder(I_C_OLCand.class)
				.setOnlySelection(request.getSelection())
				.create()
				.stream()
				.map(candidateRecord -> olCandBL.toOLCand(candidateRecord, request.getOrderDefaults()))
				.filter(candidate -> isEligibleOrLog(candidate, processorDataDestinationId, request.getAsyncBatchId()))
				.map(candidate -> prepareOLCandBeforeProcessing(candidate, defaultDateDoc))
				.sorted(request.getAggregationInfo().getOrderingComparator())
				.collect(ImmutableList.toImmutableList());
	}

	/**
	 * Decides if there needs to be a new order for 'candidate'.
	 */
	public boolean isOrderSplit(
			@NonNull final OLCand candidate,
			@NonNull final OLCand previousCandidate,
			@NonNull final OLCandAggregation aggregationInfo)
	{
		// We keep this block for the time being because as of now we did not make sure that the aggAndOrderList is complete to ensure that all
		// C_OLCands with different C_Order-"header"-columns will be split into different orders (think of e.g. C_OLCands with different currencies).
		if (previousCandidate.getAD_Org_ID() != candidate.getAD_Org_ID()
				|| !Objects.equals(previousCandidate.getPOReference(), candidate.getPOReference())
				|| !Objects.equals(previousCandidate.getC_Currency_ID(), candidate.getC_Currency_ID())
				//
				|| !Objects.equals(previousCandidate.getBPartnerInfo(), candidate.getBPartnerInfo())
				|| !Objects.equals(previousCandidate.getBillBPartnerInfo(), candidate.getBillBPartnerInfo())
				//
				// task 06269: note that for now we set DatePromised only in the header, so different DatePromised values result in different orders, and all ols have the same DatePromised
				|| !Objects.equals(previousCandidate.getDateDoc(), candidate.getDateDoc())
				|| !Objects.equals(previousCandidate.getDatePromised(), candidate.getDatePromised())
				|| !Objects.equals(previousCandidate.getHandOverBPartnerInfo(), candidate.getHandOverBPartnerInfo())
				|| !Objects.equals(previousCandidate.getDropShipBPartnerInfo(), candidate.getDropShipBPartnerInfo())
				//
				|| !Objects.equals(previousCandidate.getDeliveryRule(), candidate.getDeliveryRule())
				|| !Objects.equals(previousCandidate.getDeliveryViaRule(), candidate.getDeliveryViaRule())
				|| !Objects.equals(previousCandidate.getFreightCostRule(), candidate.getFreightCostRule())
				|| !Objects.equals(previousCandidate.getInvoiceRule(), candidate.getInvoiceRule())
				|| !Objects.equals(previousCandidate.getPaymentRule(), candidate.getPaymentRule())
				|| !Objects.equals(previousCandidate.getPaymentTermId(), candidate.getPaymentTermId())
				|| !Objects.equals(previousCandidate.getPricingSystemId(), candidate.getPricingSystemId())
				|| !Objects.equals(previousCandidate.getShipperId(), candidate.getShipperId())
				|| !Objects.equals(previousCandidate.getSalesRepId(), candidate.getSalesRepId())
				|| !Objects.equals(previousCandidate.getOrderDocTypeId(), candidate.getOrderDocTypeId()))

		{
			return true;
		}

		for (final OLCandAggregationColumn column : aggregationInfo.getSplitOrderDiscriminatorColumns())
		{
			final Object value = candidate.getValueByColumn(column);
			final Object previousValue = previousCandidate.getValueByColumn(column);
			if (!Objects.equals(value, previousValue))
			{
				return true;
			}
		}

		// between 'candidate' and 'previousCandidate' there are no differences that require a new order to be created
		return false;
	}

	private boolean isEligibleOrLog(
			@NonNull final OLCand cand,
			@NonNull final InputDataSourceId inputDataSourceId,
			@Nullable final AsyncBatchId asyncBatchId)
	{
		if (cand.isProcessed())
		{
			logger.debug("Skipping processed C_OLCand: {}", cand);
			return false;
		}

		if (cand.isError())
		{
			logger.debug("Skipping C_OLCand with errors: {}", cand);
			return false;
		}

		if (cand.getOrderLineGroup() != null && cand.getOrderLineGroup().isGroupingError())
		{
			logger.debug("Skipping C_OLCand with grouping errors: {}", cand);
			return false;
		}

		final InputDataSourceId candDataDestinationId = InputDataSourceId.ofRepoIdOrNull(cand.getAD_DataDestination_ID());
		if (!Objects.equals(candDataDestinationId, inputDataSourceId))
		{
			logger.debug("Skipping C_OLCand with AD_DataDestination_ID={} but {} was expected: {}", candDataDestinationId, inputDataSourceId, cand);
			return false;
		}

		if (asyncBatchId != null && !cand.isAssignToBatch(asyncBatchId))
		{
			logger.debug("Skipping C_OLCand due to missing batch assignment: targetBatchId: {}, candidate: {}", asyncBatchId, cand);
			return false;
		}

		return true;
	}

	@NonNull
	private OLCand prepareOLCandBeforeProcessing(@NonNull final OLCand candidate, @NonNull final LocalDate defaultDateDoc)
	{
		if (candidate.getDateDoc() == null)
		{
			candidate.setDateDoc(defaultDateDoc);
		}

		return candidate;
	}
}
