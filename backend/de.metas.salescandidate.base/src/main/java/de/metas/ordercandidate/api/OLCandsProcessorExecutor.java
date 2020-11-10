package de.metas.ordercandidate.api;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import de.metas.common.util.time.SystemTime;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.ArrayKeyBuilder;
import org.compiere.util.TimeUtil;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;
import org.slf4j.Logger;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ListMultimap;

import ch.qos.logback.classic.Level;
import de.metas.impex.InputDataSourceId;
import de.metas.impex.api.IInputDataSourceDAO;
import de.metas.logging.LogManager;
import de.metas.ordercandidate.OrderCandidate_Constants;
import de.metas.ordercandidate.api.OLCandAggregationColumn.Granularity;
import de.metas.ordercandidate.spi.IOLCandGroupingProvider;
import de.metas.ordercandidate.spi.IOLCandListener;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * Processes sales order candidates and produces sales orders
 */
public class OLCandsProcessorExecutor
{
	private static final Logger logger = LogManager.getLogger(OLCandsProcessorExecutor.class);
	private final ILoggable loggable;

	private final IOLCandListener olCandListeners;
	private final IOLCandGroupingProvider groupingValuesProviders;

	private final int olCandProcessorId;
	private final UserId userInChargeId;
	private final OLCandAggregation aggregationInfo;
	private final OLCandOrderDefaults orderDefaults;
	private final InputDataSourceId processorDataDestinationId;
	private final LocalDate defaultDateDoc = SystemTime.asLocalDate();

	private final OLCandSource candidatesSource;

	@Builder
	private OLCandsProcessorExecutor(
			@NonNull final OLCandProcessorDescriptor processorDescriptor,
			@NonNull final IOLCandListener olCandListeners,
			@NonNull final IOLCandGroupingProvider groupingValuesProviders,
			@NonNull final OLCandSource candidatesSource)
	{
		this.orderDefaults = processorDescriptor.getDefaults();
		this.olCandListeners = olCandListeners;
		this.aggregationInfo = processorDescriptor.getAggregationInfo();
		this.groupingValuesProviders = groupingValuesProviders;
		this.loggable = Loggables.withLogger(logger, Level.DEBUG);

		this.olCandProcessorId = processorDescriptor.getId();
		this.userInChargeId = processorDescriptor.getUserInChangeId();

		final IInputDataSourceDAO inputDataSourceDAO = Services.get(IInputDataSourceDAO.class);
		this.processorDataDestinationId = inputDataSourceDAO.retrieveInputDataSourceIdByInternalName(OrderCandidate_Constants.DATA_DESTINATION_INTERNAL_NAME);

		this.candidatesSource = candidatesSource;
	}

	public void process()
	{
		// Note: We could make life easier by constructing a ORDER and GROUP BY SQL statement,
		// but I'm afraid that grouping by time - granularity is not really portable. Also there might be other
		// granularity levels, that can't be put into an sql later on.

		//
		// Get the ol-candidates to process
		final List<OLCand> candidates = candidatesSource.streamOLCands()
				.filter(this::isEligibleOrLog)
				.map(this::prepareOLCandBeforeProcessing)
				.sorted(aggregationInfo.getOrderingComparator())
				.collect(ImmutableList.toImmutableList());
		loggable.addLog("Processing {} order line candidates", candidates.size());

		//
		// Compute a grouping key for each candidate and group them according to their key
		final Map<Integer, ArrayKey> toProcess = new HashMap<>();
		final ListMultimap<ArrayKey, OLCand> grouping = ArrayListMultimap.create();
		for (final OLCand candidate : candidates)
		{
			if (candidate.isProcessed())
			{
				continue; // ts: I don't see a need for this check, but let's keep it until we covered this by ait
			}

			//
			// Register grouping key
			final int olCandId = candidate.getId();
			final ArrayKey groupingKey = mkGroupingKey(candidate);
			toProcess.put(olCandId, groupingKey);
			grouping.put(groupingKey, candidate);
		}

		// 'processedIds' contains the candidates that have already been processed
		final Set<Integer> processedIds = new HashSet<>();

		OLCandOrderFactory currentOrder = null;

		// This variable is used to decide if the current candidate differs from the previous one in a way that requires a new order.
		OLCand previousCandidate = null;
		for (final OLCand candidate : candidates)
		{
			final int olCandId = candidate.getId();
			if (processedIds.contains(olCandId) || candidate.isProcessed())
			{
				// 'candidate' has already been processed
				continue;
			}

			// Each group shall go to a separate order line
			if (currentOrder != null)
			{
				currentOrder.closeCurrentOrderLine();
			}

			// get the group of the current unprocessed candidate
			final ArrayKey groupingKey = toProcess.get(olCandId);
			for (final OLCand candOfGroup : grouping.get(groupingKey))
			{
				if (currentOrder != null && isOrderSplit(candOfGroup, previousCandidate))
				{
					currentOrder.completeOrDelete();
					currentOrder = null;
				}
				if (currentOrder == null)
				{
					currentOrder = newOrderFactory();
				}

				currentOrder.addOLCand(candOfGroup);

				Check.assume(processedIds.add(candOfGroup.getId()), candOfGroup + " of grouping " + grouping + " is not processed twice");
				previousCandidate = candOfGroup;
			}
		}

		if (currentOrder != null)
		{
			currentOrder.completeOrDelete();
			currentOrder = null;
		}

		Check.assume(processedIds.size() == candidates.size(), "All candidates have been processed");
	}

	private OLCand prepareOLCandBeforeProcessing(final OLCand candidate)
	{
		if (candidate.getDateDoc() == null)
		{
			candidate.setDateDoc(defaultDateDoc);
		}

		return candidate;
	}

	private OLCandOrderFactory newOrderFactory()
	{
		return OLCandOrderFactory.builder()
				.orderDefaults(orderDefaults)
				.userInChargeId(userInChargeId)
				.loggable(loggable)
				.olCandProcessorId(olCandProcessorId)
				.olCandListeners(olCandListeners)
				.build();
	}

	/**
	 * Decides if there needs to be a new order for 'candidate'.
	 */
	private boolean isOrderSplit(@NonNull final OLCand candidate, @NonNull final OLCand previousCandidate)
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

	/**
	 * Computes an order-line-related grouping key for the given candidate. The key is made such that two different candidates that should be grouped (i.e. qtys aggregated) end up with the same grouping key.
	 */
	private ArrayKey mkGroupingKey(@NonNull final OLCand candidate)
	{
		if (aggregationInfo.getGroupByColumns().isEmpty())
		{
			// each candidate results in its own order line
			return Util.mkKey(candidate.getId());
		}

		final ArrayKeyBuilder groupingValues = ArrayKey.builder()
				.appendAll(groupingValuesProviders.provideLineGroupingValues(candidate));

		for (final OLCandAggregationColumn column : aggregationInfo.getColumns())
		{
			if (!column.isGroupByColumn())
			{
				// we don't group by the current column, so all different values result in their own order lines
				groupingValues.append(candidate.getValueByColumn(column));
			}
			else if (column.getGranularity() != null)
			{
				// create a grouping key based on the granularity level
				final Granularity granularity = column.getGranularity();
				final Timestamp timestamp = (Timestamp)candidate.getValueByColumn(column);
				final long groupingValue = truncateDateByGranularity(timestamp, granularity).getTime();
				groupingValues.append(groupingValue);
			}
		}

		return groupingValues.build();
	}

	private static Timestamp truncateDateByGranularity(final Timestamp date, final Granularity granularity)
	{
		if (granularity == Granularity.Day)
		{
			return TimeUtil.trunc(date, TimeUtil.TRUNC_DAY);
		}
		else if (granularity == Granularity.Week)
		{
			return TimeUtil.trunc(date, TimeUtil.TRUNC_WEEK);
		}
		else if (granularity == Granularity.Month)
		{
			return TimeUtil.trunc(date, TimeUtil.TRUNC_MONTH);
		}
		else
		{
			throw new AdempiereException("Unknown granularity: " + granularity);
		}
	}

	private final boolean isEligibleOrLog(final OLCand cand)
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

		final InputDataSourceId candDataDestinationId = InputDataSourceId.ofRepoIdOrNull(cand.getAD_DataDestination_ID());
		if (!Objects.equals(candDataDestinationId, processorDataDestinationId))
		{
			logger.debug("Skipping C_OLCand with AD_DataDestination_ID={} but {} was expected: {}", candDataDestinationId, processorDataDestinationId, cand);
			return false;
		}

		if (cand.isImportedWithIssues())
		{
			// FIXME: instead of having isImportedWithIssues() implemented here, add support for using a filter that is then registered from e.g. a model validator
			// this way, we would further decouple our modules
			logger.debug("Skipping C_OLCand with import issues: {}", cand);
			return false;
		}

		return true;
	}
}
