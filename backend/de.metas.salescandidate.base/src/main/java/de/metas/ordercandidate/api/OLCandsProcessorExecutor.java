package de.metas.ordercandidate.api;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import de.metas.async.AsyncBatchId;
import de.metas.logging.LogManager;
import de.metas.logging.TableRecordMDC;
import de.metas.ordercandidate.api.OLCandAggregationColumn.Granularity;
import de.metas.ordercandidate.api.source.GetEligibleOLCandRequest;
import de.metas.ordercandidate.api.source.OLCandProcessingHelper;
import de.metas.ordercandidate.spi.IOLCandGroupingProvider;
import de.metas.ordercandidate.spi.IOLCandListener;
import de.metas.process.PInstanceId;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.ArrayKeyBuilder;
import org.compiere.util.TimeUtil;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;
import org.slf4j.Logger;
import org.slf4j.MDC.MDCCloseable;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	private final OLCandProcessingHelper olCandProcessingHelper;

	private final int olCandProcessorId;
	private final UserId userInChargeId;
	private final OLCandAggregation aggregationInfo;
	private final OLCandOrderDefaults orderDefaults;
	private final PInstanceId selectionId;
	private final AsyncBatchId asyncBatchId;

	@Builder
	private OLCandsProcessorExecutor(
			@NonNull final OLCandProcessorDescriptor processorDescriptor,
			@NonNull final IOLCandListener olCandListeners,
			@NonNull final IOLCandGroupingProvider groupingValuesProviders,
			@NonNull final OLCandProcessingHelper olCandProcessingHelper,
			@NonNull final PInstanceId selectionId,
			@Nullable final AsyncBatchId asyncBatchId)
	{
		this.orderDefaults = processorDescriptor.getDefaults();
		this.olCandListeners = olCandListeners;
		this.aggregationInfo = processorDescriptor.getAggregationInfo();
		this.groupingValuesProviders = groupingValuesProviders;
		this.olCandProcessingHelper = olCandProcessingHelper;

		this.selectionId = selectionId;
		this.asyncBatchId = asyncBatchId;
		this.loggable = Loggables.withLogger(logger, Level.DEBUG);

		this.olCandProcessorId = processorDescriptor.getId();
		this.userInChargeId = processorDescriptor.getUserInChangeId();
	}

	public void process()
	{
		// Note: We could make life easier by constructing a ORDER and GROUP BY SQL statement,
		// but I'm afraid that grouping by time - granularity is not really portable. Also there might be other
		// granularity levels, that can't be put into an sql later on.

		//
		// Get the ol-candidates to process
		final List<OLCand> candidates = getEligibleOLCand();

		loggable.addLog("Processing {} order line candidates", candidates.size());
		if (candidates.isEmpty())
		{
			return;
		}
		//
		// Compute a grouping key for each candidate and group them according to their key
		final Map<Integer, ArrayKey> toProcess = new HashMap<>();
		final ListMultimap<ArrayKey, OLCand> grouping = ArrayListMultimap.create();
		for (final OLCand candidate : candidates)
		{
			try (final MDCCloseable ignored = TableRecordMDC.putTableRecordReference(candidate.unbox()))
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
			catch (final AdempiereException e)
			{
				throw AdempiereException.wrapIfNeeded(e).appendParametersToMessage()
						.setParameter("C_OLCand_ID", candidate.getId());
			}
		}
		// 'processedIds' contains the candidates that have already been processed
		final Set<Integer> processedIds = new HashSet<>();

		OLCandOrderFactory currentOrder = null;

		// This variable is used to decide if the current candidate differs from the previous one in a way that requires a new order.
		OLCand previousCandidate = null;
		for (final OLCand candidate : candidates)
		{
			try (final MDCCloseable ignored = TableRecordMDC.putTableRecordReference(candidate.unbox()))
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
					if (currentOrder != null && OLCandProcessingHelper.isOrderSplit(candOfGroup, previousCandidate, aggregationInfo))
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
			catch (final AdempiereException e)
			{
				throw AdempiereException.wrapIfNeeded(e).appendParametersToMessage()
						.setParameter("C_OLCand_ID", candidate.getId());
			}
		}

		if (currentOrder != null)
		{
			currentOrder.completeOrDelete();
		}

		Check.assume(processedIds.size() == candidates.size(), "All candidates have been processed");
	}

	private OLCandOrderFactory newOrderFactory()
	{
		return OLCandOrderFactory.builder()
				.orderDefaults(orderDefaults)
				.userInChargeId(userInChargeId)
				.loggable(loggable)
				.olCandProcessorId(olCandProcessorId)
				.olCandListeners(olCandListeners)
				.aggregationInfo(aggregationInfo)
				.build();
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

	@NonNull
	private List<OLCand> getEligibleOLCand()
	{
		final GetEligibleOLCandRequest request = GetEligibleOLCandRequest.builder()
				.aggregationInfo(aggregationInfo)
				.orderDefaults(orderDefaults)
				.selection(selectionId)
				.asyncBatchId(asyncBatchId)
				.build();

		return olCandProcessingHelper.getOLCandsForProcessing(request);
	}
}
